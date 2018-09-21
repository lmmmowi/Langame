package com.lmmmowi.langame.service_impl.export;

import com.lmmmowi.langame.model.ExportSetting;

/**
 * @Author: mowi
 * @Date: 2018/9/21
 * @Description:
 */
public class ExporterFactory {

    public static IExporter getExporter(ExportSetting exportSetting) {
        try {
            ExportType exportType = exportSetting.getExportType();
            switch (exportType) {
                case json:
                    return new JsonExporter(exportSetting);
                case diy:
                    return new DiyExporter(exportSetting);
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
