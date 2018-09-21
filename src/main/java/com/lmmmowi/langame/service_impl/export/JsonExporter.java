package com.lmmmowi.langame.service_impl.export;

import com.lmmmowi.langame.model.ExportSetting;
import com.lmmmowi.langame.util.StringUtil;
import org.beetl.core.Template;

import java.util.List;

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
    protected void beforeRender(List<EntryExportItem> items) {
        items.forEach(item -> {
            item.setKey(StringUtil.escape(item.getKey()));
            item.setContent(StringUtil.escape(item.getContent()));
        });

        super.beforeRender(items);
    }

    @Override
    protected Template loadTemplate() {
        String root = JsonExporter.class.getPackage().getName().replace(".", "/") + "/tpl";
        return TemplateLoader.fromClasspath(root, "json.tpl");
    }
}
