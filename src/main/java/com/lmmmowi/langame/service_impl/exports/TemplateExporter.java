package com.lmmmowi.langame.service_impl.exports;

import com.lmmmowi.langame.model.ExportSetting;
import com.lmmmowi.langame.model.LangEntry;
import com.lmmmowi.langame.model.PathNode;
import com.lmmmowi.langame.service_impl.helper.ExportRenderer;
import org.beetl.core.Template;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: mowi
 * @Date: 2018/9/21
 * @Description:
 */
public abstract class TemplateExporter implements IExporter {

    protected ExportSetting exportSetting;
    protected ExportRenderer exportRenderer;

    public TemplateExporter(ExportSetting exportSetting) {
        this.exportSetting = exportSetting;
        this.exportRenderer = new ExportRenderer(exportSetting);
    }

    @Override
    public String export(List<PathNode> pathNodes, String language) {
        List<EntryExportItem> items = this.loadExportEntryItems(pathNodes, language);
        if (items == null || items.isEmpty()) {
            return null;
        }

        this.beforeRender(items);

        return this.render(items);
    }

    protected List<EntryExportItem> loadExportEntryItems(List<PathNode> pathNodes, String language) {
        if (pathNodes == null || pathNodes.isEmpty()) {
            return null;
        }

        List<Integer> nodeIds = pathNodes.stream().map(o -> o.getId()).collect(Collectors.toList());
        List<LangEntry> entries = LangEntry.DAO.query(nodeIds, language);
        Map<Integer, LangEntry> entryMap = entries.stream().collect(Collectors.toMap(o -> o.getNodeId(), o -> o));

        List<EntryExportItem> items = pathNodes.stream().map(pathNode -> {
            String key = exportRenderer.renderKey(pathNode.getId());

            LangEntry entry = entryMap.get(pathNode.getId());
            String content = exportRenderer.renderContent(entry);

            EntryExportItem item = new EntryExportItem();
            item.setKey(key);
            item.setContent(content);
            return item;
        }).collect(Collectors.toList());

        return items;
    }

    protected void beforeRender(List<EntryExportItem> items) {
    }

    protected String render(List<EntryExportItem> items) {
        Template template = loadTemplate();
        template.binding("items", items);
        return template.render();
    }

    abstract protected Template loadTemplate();
}
