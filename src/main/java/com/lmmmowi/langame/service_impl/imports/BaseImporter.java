package com.lmmmowi.langame.service_impl.imports;

import com.jfinal.plugin.activerecord.Db;
import com.lmmmowi.langame.enums.NodeType;
import com.lmmmowi.langame.model.LangEntry;
import com.lmmmowi.langame.model.PathNode;
import com.lmmmowi.langame.service.PathNodeService;
import com.lmmmowi.langame.service_impl.imports.parser.IImporterParser;
import com.lmmmowi.langame.service_impl.imports.parser.ParseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: mowi
 * @Date: 2018/9/25
 * @Description:
 */
public abstract class BaseImporter {

    private static final Logger logger = LoggerFactory.getLogger(BaseImporter.class);

    @Autowired
    PathNodeService pathNodeService;

    public void run(PathNode parentNode, String language) {
        IImporterParser importerParser = this.getImportParser();
        List<ParseResult> results = importerParser.parse();

        Db.tx(() -> {
            saveResults(parentNode, language, results);
            return true;
        });
    }

    protected void saveResults(PathNode parentNode, String language, List<ParseResult> results) {
        for (ParseResult result : results) {
            PathNode pathNode = saveNode(parentNode, result.getKey());
            LangEntry langEntry = saveLangEntry(pathNode, language, result.getContent());
            logger.debug(String.format("Import %s success", result.getKey()));
        }
    }

    protected PathNode saveNode(PathNode parentNode, String name) {
        PathNode pathNode = PathNode.DAO.findNode(parentNode.getId(), name, NodeType.entry);
        if (pathNode == null) {
            pathNode = new PathNode();
            pathNode.set("project", parentNode.getStr("project"));
            pathNode.set("parent", parentNode.getId());
            pathNode.set("name", name);
            pathNode.set("type", NodeType.entry.name());
            pathNode.save();
        }
        return pathNode;
    }

    protected LangEntry saveLangEntry(PathNode pathNode, String language, String content) {
        LangEntry langEntry = LangEntry.DAO.findById(pathNode.getId(), language);
        if (langEntry == null) {
            langEntry = new LangEntry();
            langEntry.set("node", pathNode.getId());
            langEntry.set("language", language);
            langEntry.set("content", content);
            langEntry.save();
        } else {
            langEntry.set("content", content);
            langEntry.update();
        }
        return langEntry;
    }

    abstract protected IImporterParser getImportParser();


}
