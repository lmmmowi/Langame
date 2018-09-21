package com.lmmmowi.langame.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: mowi
 * @Date: 2018/9/21
 * @Description:
 */
public class StringUtil {

    public static String escape(String s) {
        JSONObject json = new JSONObject();
        json.put("_", s);
        String r = json.toJSONString();
        return r.substring(6, r.length() - 2);
    }

}
