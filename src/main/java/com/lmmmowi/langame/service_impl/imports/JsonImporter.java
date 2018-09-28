package com.lmmmowi.langame.service_impl.imports;

import com.alibaba.fastjson.JSONObject;
import com.lmmmowi.langame.service_impl.imports.parser.IImporterParser;
import com.lmmmowi.langame.service_impl.imports.parser.JsonImportParser;

/**
 * @Author: mowi
 * @Date: 2018/9/25
 * @Description:
 */
public class JsonImporter extends BaseImporter {

    private JSONObject json;

    public JsonImporter(JSONObject json) {
        this.json = json;
    }

    @Override
    protected IImporterParser getImportParser() {
        return new JsonImportParser(json);
    }
}
