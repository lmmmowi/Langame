package com.lmmmowi.langame.cron;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.lmmmowi.langame.config.LangameConfig;
import com.lmmmowi.langame.enums.ExportTaskState;
import com.lmmmowi.langame.enums.NodeType;
import com.lmmmowi.langame.model.ExportSetting;
import com.lmmmowi.langame.model.ExportTask;
import com.lmmmowi.langame.model.PathNode;
import com.lmmmowi.langame.service_impl.exports.ExportRangeParser;
import com.lmmmowi.langame.service_impl.exports.ExporterFactory;
import com.lmmmowi.langame.service_impl.exports.IExporter;
import com.lmmmowi.langame.util.FileKit;
import com.lmmmowi.langame.util.TimeKit;
import org.omg.PortableServer.THREAD_POLICY_ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: mowi
 * @Date: 2018/9/27
 * @Description:
 */
public class ExportTaskRunner implements Runnable {

    public static final Logger logger = LoggerFactory.getLogger(ExportTaskRunner.class);

    private File outputDir;
    private ExportTask executionTask;
    private List<String> languages;
    private ExportSetting exportSetting;
    private List<PathNode> pathNodes;
    private String executionMessage;

    @Override
    public void run() {
        String sql = String.format("SELECT * FROM %s WHERE state=? ORDER BY create_time", ExportTask.DAO.getTable());
        executionTask = ExportTask.DAO.findFirst(sql, ExportTaskState.wait.name());
        if (executionTask == null) {
            logger.debug("No Waiting ExportTask");
            return;
        }

        executionTask.set("state", ExportTaskState.running.name());
        executionTask.update();

        this.prepare();
        boolean success = this.execute(pathNodes, exportSetting, languages);

        ExportTaskState taskState = success ? ExportTaskState.success : ExportTaskState.fail;
        executionTask.set("state", taskState.name());
        executionTask.set("execution_message", executionMessage);
        executionTask.set("execution_time", TimeKit.now());
        executionTask.update();

        logger.info("ExportTask Execution Result: " + success);
    }

    private void prepare() {
        outputDir = LangameConfig.getInstance().getExportDir();

        pathNodes = new ExportRangeParser(executionTask).parseRange();
        pathNodes = pathNodes.stream().filter(node -> NodeType.entry.equals(node.getType())).collect(Collectors.toList());

        exportSetting = ExportSetting.DAO.findById(executionTask.getInt("export_setting"));

        languages = executionTask.getLanguages();
    }

    private boolean execute(List<PathNode> pathNodes, ExportSetting exportSetting, List<String> languages) {
        IExporter exporter = ExporterFactory.getExporter(exportSetting);
        if (exporter == null) {
            executionMessage = "No Available Exporter";
            return false;
        }

        Map<String, String> filenameMapping = exportSetting.getFilenameMapping();
        String filenameExtension = exportSetting.getFilenameExtension();
        String pathSuffix = StrKit.isBlank(filenameExtension) ? "" : "." + filenameExtension;

        for (String language : languages) {
            String result = exporter.export(pathNodes, language);
            if (result == null) {
                result = "";
            }

            String filename = filenameMapping == null || filenameMapping.get(language) == null ? language : filenameMapping.get(language);
            File outputFile = new File(outputDir, String.format("%d/%s%s", executionTask.getId(), filename, pathSuffix));
            FileKit.writeContent(outputFile, result);
        }

        return true;
    }
}
