package com.lmmmowi.langame.api;

import com.jfinal.aop.Clear;
import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.enums.NodeType;
import com.lmmmowi.langame.interceptor.ApiResultOutput;
import com.lmmmowi.langame.model.ExportSetting;
import com.lmmmowi.langame.model.PathNode;
import com.lmmmowi.langame.service_impl.export.ExporterFactory;
import com.lmmmowi.langame.service_impl.export.IExporter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: mowi
 * @Date: 2018/9/21
 * @Description:
 */
public class ExportApi extends BaseApi {

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
