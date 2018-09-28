package com.lmmmowi.langame.model;

import com.alibaba.fastjson.JSONObject;
import com.lmmmowi.langame.common.BaseModel;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: mowi
 * @Date: 2018/9/27
 * @Description:
 */
public class ExportTask extends BaseModel<ExportTask> {

    public static final ExportTask DAO = new ExportTask();

    @Override
    public Integer getId() {
        return super.getId();
    }

    public JSONObject getExportRange(){
        return getJSONObject("export_range");
    }

    public List<String> getLanguages(){
        return Arrays.asList(getStr("languages").split(","));
    }

    public List<ExportTask> findByProject(String projectId){
        String sql = String.format("SELECT * FROM %s WHERE project=? ORDER BY create_time DESC", getTable());
        return find(sql, projectId);
    }
}
