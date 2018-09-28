package com.lmmmowi.langame.service_impl.exports;

import com.lmmmowi.langame.model.ExportSetting;
import org.beetl.core.Template;

/**
 * @Author: mowi
 * @Date: 2018/9/25
 * @Description:
 */
public class PropertiesExporter extends TemplateExporter {

    public PropertiesExporter(ExportSetting exportSetting) {
        super(exportSetting);
    }

    @Override
    protected Template loadTemplate() {
        String root = JsonExporter.class.getPackage().getName().replace(".", "/") + "/tpl";
        return TemplateLoader.fromClasspath(root, "properties.tpl");
    }
}
