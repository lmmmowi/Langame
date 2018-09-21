package com.lmmmowi.langame.model;

import com.alibaba.fastjson.JSON;
import com.lmmmowi.langame.common.BaseModel;

import java.util.Map;

/**
 * @Author: mowi
 * @Date: 2018/9/21
 * @Description:
 */
public class ExportSetting extends BaseModel<ExportSetting> {

    public static final ExportSetting DAO = new ExportSetting();

    public String getNodeConnector(){
        return getStr("node_connector");
    }

    public Map<String, String> getKeyReplaceMap() {
        return getMap("key_replace");
    }

    public Map<String, String> getValueReplaceMap() {
        return getMap("value_replace");
    }

    private Map<String, String> getMap(String attr) {
        String s = getStr(attr);
        if (s == null) {
            return null;
        }
        return (Map<String, String>) JSON.parse(s);
    }
}
