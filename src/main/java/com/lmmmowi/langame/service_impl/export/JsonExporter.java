package com.lmmmowi.langame.service_impl.export;

import com.lmmmowi.langame.model.ExportSetting;
import org.beetl.core.Template;

/**
 * @Author: mowi
 * @Date: 2018/9/21
 * @Description:
 */
public class JsonExporter extends TemplateExporter {


    public JsonExporter(ExportSetting exportSetting) {
        super(exportSetting);
    }

    @Override
    protected Template loadTemplate() {
        String root = JsonExporter.class.getPackage().getName().replace(".", "/") + "/tpl";
        return TemplateLoader.fromClasspath(root, "json.tpl");
    }
}
