package com.lmmmowi.langame.service_impl.export;

import com.jfinal.kit.StrKit;
import com.lmmmowi.langame.cache.LgCache;
import com.lmmmowi.langame.config.LangameConfig;
import com.lmmmowi.langame.model.ExportSetting;
import com.lmmmowi.langame.model.LangEntry;
import com.lmmmowi.langame.model.PathNode;
import org.beetl.core.Template;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: mowi
 * @Date: 2018/9/21
 * @Description:
 */
public abstract class TemplateExporter implements IExporter {

    private static final String DEFAULT_NODE_CONNECTOR = LangameConfig.getInstance().getDefaultNodeConnector();

    protected ExportSetting exportSetting;

    public TemplateExporter(ExportSetting exportSetting) {
        this.exportSetting = exportSetting;
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

        String projectId = pathNodes.get(0).getProjectId();
        List<Integer> nodeIds = pathNodes.stream().map(o -> o.getId()).collect(Collectors.toList());
        List<LangEntry> entries = LangEntry.DAO.findByNode(nodeIds, language);

        Map<Integer, String> completePathMap = LgCache.use(projectId).getCache(LgCache.CACHE_COMPLETE_NODE_PATH);

        List<EntryExportItem> items = entries.stream().map(entry -> {
            // 去除根节点
            String key = completePathMap.get(entry.getNodeId()).substring(PathNode.ROOT_NODE_NAME.length() + 1);

            EntryExportItem item = new EntryExportItem();
            item.setKey(key);
            item.setContent(entry.getStr("content"));
            return item;
        }).collect(Collectors.toList());
        return items;
    }

    protected void beforeRender(List<EntryExportItem> items) {
        String nodeConnector = exportSetting.getNodeConnector();
        if (StrKit.notBlank(nodeConnector)) {
            items.forEach(item -> item.setKey(item.getKey().replace(DEFAULT_NODE_CONNECTOR, nodeConnector)));
        }

        Map<String, String> keyReplaceMap = exportSetting.getKeyReplaceMap();
        if (keyReplaceMap != null) {
            items.forEach(item -> {
                Set<String> mapKeys = keyReplaceMap.keySet();
                String key = item.getKey();
                for (String mapKey : mapKeys) {
                    String mapValue = keyReplaceMap.get(mapKey);
                    key = key.replace(mapKey, mapValue);
                }
                item.setKey(key);
            });
        }

        Map<String, String> valueReplaceMap = exportSetting.getValueReplaceMap();
        if (valueReplaceMap != null) {
            items.forEach(item -> {
                Set<String> mapKeys = valueReplaceMap.keySet();
                String content = item.getContent();
                for (String mapKey : mapKeys) {
                    String mapValue = valueReplaceMap.get(mapKey);
                    content = content.replace(mapKey, mapValue);
                }
                item.setContent(content);
            });
        }
    }

    protected String render(List<EntryExportItem> items) {
        Template template = loadTemplate();
        template.binding("items", items);
        return template.render();
    }

    abstract protected Template loadTemplate();
}
