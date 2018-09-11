package com.lmmmowi.langame.common;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class BaseApi extends Controller {

    private boolean inputDataCached;
    private String cacheInputData;

    private boolean inputJsonCached;
    private JSONObject cacheInputJson;

    protected String getInputData() {
        if (!inputDataCached) {
            inputDataCached = true;

            if ("GET".equals(getRequest().getMethod().toUpperCase())) {
                cacheInputData = getRequest().getQueryString();
            } else {
                cacheInputData = HttpKit.readData(getRequest());
            }
        }
        return cacheInputData;
    }

    protected JSONObject getInputJson() {
        if (!inputJsonCached) {
            inputJsonCached = true;

            String data = getInputData();
            try {
                if (StrKit.notBlank(data)) {
                    cacheInputJson = JSONObject.parseObject(data);
                }
            } catch (JSONException e) {
            } finally {
                if (cacheInputJson == null) {
                    cacheInputJson = new JSONObject();
                }
            }
        }
        return cacheInputJson;
    }

    @Override
    public String getPara(String name) {
        return this.getPara(name, null);
    }

    @Override
    public String getPara(String name, String defaultValue) {
        if ("GET".equals(getRequest().getMethod().toUpperCase())) {
            return super.getPara(name, defaultValue);
        } else {
            String s = getInputJson().getString(name);
            return s != null ? s : defaultValue;
        }
    }
}
