package com.lmmmowi.langame.service_impl.helper;

import com.jfinal.kit.StrKit;
import com.lmmmowi.langame.cache.LgCache;
import com.lmmmowi.langame.config.LangameConfig;
import com.lmmmowi.langame.model.ExportSetting;
import com.lmmmowi.langame.model.LangEntry;
import com.lmmmowi.langame.model.PathNode;

import java.util.Map;
import java.util.Set;

/**
 * @Author: mowi
 * @Date: 2018/10/11
 * @Description:
 */
public class ExportRenderer {

    private static final String DEFAULT_NODE_CONNECTOR = LangameConfig.getInstance().getDefaultNodeConnector();

    private ExportSetting exportSetting;
    private Map<Integer, String> completePathMap;

    public ExportRenderer(ExportSetting exportSetting) {
        this(exportSetting.getProjectId());
        this.exportSetting = exportSetting;
    }

    public ExportRenderer(String projectId) {
        this.completePathMap = LgCache.use(projectId).getCache(LgCache.CACHE_COMPLETE_NODE_PATH);
    }

    public String renderKey(Integer nodeId) {
        String key = completePathMap.get(nodeId);

        // 去除根节点
        key = key.substring(PathNode.ROOT_NODE_NAME.length() + 1);

        if (exportSetting != null) {
            // 替换连接符
            String nodeConnector = exportSetting.getNodeConnector();
            if (StrKit.notBlank(nodeConnector)) {
                key = key.replace(DEFAULT_NODE_CONNECTOR, nodeConnector);
            }

            key = replaceMapValue(key, exportSetting.getKeyReplaceMap());
        }

        return key;
    }

    public String renderContent(LangEntry entry) {
        if (entry == null) {
            return "";
        }

        String content = entry.getStr("content");

        if (exportSetting != null) {
            content = replaceMapValue(content, exportSetting.getValueReplaceMap());
        }

        return content;
    }

    private String replaceMapValue(String s, Map<String, String> map) {
        if (map != null) {
            Set<String> mapKeys = map.keySet();
            for (String mapKey : mapKeys) {
                String mapValue = map.get(mapKey);
                s = s.replace(mapKey, mapValue);
            }
        }
        return s;
    }
}
