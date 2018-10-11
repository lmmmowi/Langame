package com.lmmmowi.langame.model;

import com.alibaba.fastjson.JSON;
import com.jfinal.kit.StrKit;
import com.lmmmowi.langame.common.BaseModel;
import com.lmmmowi.langame.service_impl.exports.ExportType;

import java.util.List;
import java.util.Map;

/**
 * @Author: mowi
 * @Date: 2018/9/21
 * @Description:
 */
public class ExportSetting extends BaseModel<ExportSetting> {

    public static final ExportSetting DAO = new ExportSetting();

    public String getProjectId(){
        return getStr("project");
    }

    public ExportType getExportType() {
        return ExportType.valueOf(getStr("export_type"));
    }

    public String getFilenameExtension() {
        String s = getStr("filename_extension");
        if (StrKit.isBlank(s)) {
            s = getExportType().getDefaultExtension();
        }
        return s;
    }

    public Map<String, String> getFilenameMapping() {
        return getMap("filename_mapping");
    }

    public String getNodeConnector() {
        return getStr("node_connector");
    }

    public Map<String, String> getKeyReplaceMap() {
        return getMap("key_replace");
    }

    public Map<String, String> getValueReplaceMap() {
        return getMap("value_replace");
    }

    private Map<String, String> getMap(String attr) {
        String s = getStr(attr);
        if (s == null) {
            return null;
        }
        return (Map<String, String>) JSON.parse(s);
    }

    public ExportSetting findDefaultByProject(String projectId) {
        String sql = String.format("SELECT * FROM %s WHERE project=? AND is_default=?", getTable());
        return findFirst(sql, projectId, true);
    }

    public List<ExportSetting> findByProject(String projectId) {
        String sql = String.format("SELECT * FROM %s WHERE project=? ORDER BY id", getTable());
        return find(sql, projectId);
    }
}
