package com.lmmmowi.langame.service_impl.export;

import com.lmmmowi.langame.model.ExportSetting;
import org.beetl.core.Template;

/**
 * @Author: mowi
 * @Date: 2018/9/21
 * @Description:
 */
public class DiyExporter extends TemplateExporter {

    public DiyExporter(ExportSetting exportSetting) {
        super(exportSetting);
    }

    @Override
    protected Template loadTemplate() {
        String templateValue = exportSetting.getStr("template");
        return TemplateLoader.fromString(templateValue);
    }
}
