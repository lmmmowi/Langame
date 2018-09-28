package com.lmmmowi.langame.service_impl.exports;

import com.alibaba.fastjson.JSONObject;
import com.lmmmowi.langame.model.ExportTask;
import com.lmmmowi.langame.model.PathNode;

import java.util.List;

/**
 * @Author: mowi
 * @Date: 2018/9/27
 * @Description:
 */
public class ExportRangeParser {

    private ExportTask exportTask;
    private JSONObject range;

    public ExportRangeParser(ExportTask exportTask) {
        this.exportTask = exportTask;
        this.range = exportTask.getExportRange();
    }

    public List<PathNode> parseRange() {
        String type = range.getString("type");
        switch (type) {
            case "project":
                return parseForProject();
            default:
                return null;
        }
    }

    private List<PathNode> parseForProject() {
        return PathNode.DAO.findByProject(exportTask.getStr("project"));
    }
}
