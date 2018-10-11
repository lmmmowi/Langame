package com.lmmmowi.langame.api;

import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.aop.Clear;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;
import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.config.LangameConfig;
import com.lmmmowi.langame.enums.ExportTaskState;
import com.lmmmowi.langame.enums.NodeType;
import com.lmmmowi.langame.interceptor.ApiResultOutput;
import com.lmmmowi.langame.interceptor.BaseUrlInterceptor;
import com.lmmmowi.langame.model.ExportSetting;
import com.lmmmowi.langame.model.ExportTask;
import com.lmmmowi.langame.model.PathNode;
import com.lmmmowi.langame.service_impl.exports.ExporterFactory;
import com.lmmmowi.langame.service_impl.exports.IExporter;
import com.lmmmowi.langame.util.TimeKit;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: mowi
 * @Date: 2018/9/21
 * @Description:
 */
public class ExportApi extends BaseApi {

    public void listSettings() {
        String projectId = getPara("project_id");
        List<ExportSetting> exportSettings = ExportSetting.DAO.findByProject(projectId);
        setAttr("settings", exportSettings);
    }

    public void createSetting() {
        String projectId = getPara("project_id");
        JSONObject filenameMapping = getParaToJSONObject("filename_mapping");

        List<ExportSetting> exportSettings = ExportSetting.DAO.findByProject(projectId);
        boolean isDefault = exportSettings.size() == 0;

        ExportSetting exportSetting = new ExportSetting();
        exportSetting.set("project", projectId);
        exportSetting.set("is_default", isDefault);
        exportSetting.set("name", getPara("name"));
        exportSetting.set("export_type", getPara("export_type"));
        exportSetting.set("template", getPara("template"));
        exportSetting.set("node_connector", getPara("node_connector"));
        exportSetting.set("filename_extension", getPara("filename_extension"));
        exportSetting.set("filename_mapping", filenameMapping == null || filenameMapping.isEmpty() ? null : filenameMapping.toJSONString());
        exportSetting.save();

        setAttr("setting", exportSetting);
    }

    public void deleteSetting() {
        Integer settingId = getParaToInt("id");
        ExportSetting exportSetting = ExportSetting.DAO.findById(settingId);
        if (exportSetting != null) {
            exportSetting.delete();
        }
    }

    public void updateSetting() {
        Integer settingId = getParaToInt("id");
        JSONObject filenameMapping = getParaToJSONObject("filename_mapping");

        ExportSetting exportSetting = ExportSetting.DAO.findById(settingId);
        if (exportSetting != null) {
            exportSetting.set("name", getPara("name"));
            exportSetting.set("export_type", getPara("export_type"));
            exportSetting.set("template", getPara("template"));
            exportSetting.set("node_connector", getPara("node_connector"));
            exportSetting.set("filename_extension", getPara("filename_extension"));
            exportSetting.set("filename_mapping", filenameMapping == null || filenameMapping.isEmpty() ? null : filenameMapping.toJSONString());
            exportSetting.update();
        }

        setAttr("setting", exportSetting);
    }

    public void listTasks() {
        String projectId = getPara("project_id");
        List<ExportTask> exportTasks = ExportTask.DAO.findByProject(projectId);
        setAttr("tasks", exportTasks);
    }

    public void createTask() {
        Integer exportSettingId = getParaToInt("export_setting");
        JSONArray languageArray = getParaToJSONArray("languages");
        JSONObject exportRange = getParaToJSONObject("export_range");

        ExportSetting exportSetting = ExportSetting.DAO.findById(exportSettingId);
        String languages = String.join(",", languageArray.stream().map(o -> (String) o).collect(Collectors.toList()));

        ExportTask task = new ExportTask();
        task.set("project", exportSetting.getStr("project"));
        task.set("export_setting", exportSettingId);
        task.set("export_range", exportRange.toJSONString());
        task.set("languages", languages);
        task.set("state", ExportTaskState.wait.name());
        task.set("create_time", TimeKit.now());
        task.save();

        setAttr("task", task);
    }

    public void getExportResult() {
        Integer taskId = getParaToInt("task_id");

        ExportTask exportTask = ExportTask.DAO.findById(taskId);
        File resultDir = new File(LangameConfig.getInstance().getExportDir(), exportTask.getId().toString());

        File[] files = resultDir.listFiles((file, name) -> !name.startsWith("."));
        if (files == null) {
            files = new File[0];
        }

        String exportFileBaseUrl = LangameConfig.getInstance().getExportFileBaseUrl();
        String baseUrl = StrKit.notBlank(exportFileBaseUrl) ? exportFileBaseUrl : BaseUrlInterceptor.get() + "/api/export/getExportFile?path=";

        List<Record> result = Arrays.stream(files).map(file -> {
            String fileKey = exportTask.getId() + "/" + file.getName();

            Record record = new Record();
            record.set("name", file.getName());
            record.set("key", fileKey);
            record.set("url", baseUrl + fileKey);
            return record;
        }).collect(Collectors.toList());

        setAttr("files", result);
    }

    @Clear(ApiResultOutput.class)
    public void getExportFile() {
        File file = new File(LangameConfig.getInstance().getExportDir(), getPara("path"));
        if (file.exists()) {
            renderFile(file);
        } else {
            renderError(404);
        }
    }

    @Clear(ApiResultOutput.class)
    public void exportProject() {
        String projectId = getPara("project_id");
        String language = getPara("language");

        List<PathNode> pathNodes = PathNode.DAO.findByProject(projectId);
        pathNodes = pathNodes.stream().filter(node -> NodeType.entry.equals(node.getType())).collect(Collectors.toList());

        ExportSetting exportSetting = ExportSetting.DAO.findById(1);

        IExporter exporter = ExporterFactory.getExporter(exportSetting);
        if (exporter == null) {
            renderError(404);
        } else {
            String result = exporter.export(pathNodes, language);
            renderText(result);
        }
    }

}
