package com.lmmmowi.langame.service_impl.imports.parser;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @Author: mowi
 * @Date: 2018/9/25
 * @Description:
 */
public class JsonImportParser implements IImporterParser {

    private JSONObject json;

    public JsonImportParser(JSONObject json) {
        this.json = json;
    }

    @Override
    public List<ParseResult> parse() {
        List<ParseResult> results = new ArrayList<>();

        Iterator<String> keyIterator = json.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            String value = json.getString(key);

            ParseResult parseResult = new ParseResult();
            parseResult.setKey(key);
            parseResult.setContent(value);

            results.add(parseResult);
        }

        return results;
    }
}
