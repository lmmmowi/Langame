package com.lmmmowi.langame.common;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.model.User;

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

    protected User accessUser;

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

    @Override
    public Integer getParaToInt(String name) {
        return this.getParaToInt(name, null);
    }

    @Override
    public Integer getParaToInt(String name, Integer defaultValue) {
        String s = this.getPara(name);
        return StrKit.isBlank(s) ? defaultValue : Integer.valueOf(s);
    }

    @Override
    public Boolean getParaToBoolean(String name) {
        return this.getParaToBoolean(name, null);
    }

    @Override
    public Boolean getParaToBoolean(String name, Boolean defaultValue) {
        String s = this.getPara(name);
        return StrKit.isBlank(s) ? defaultValue : Boolean.valueOf(s);
    }

    public JSONArray getParaToJSONArray(String name) {
        String s = this.getPara(name);
        return JSONArray.parseArray(s);
    }

    public JSONObject getParaToJSONObject(String name) {
        String s = this.getPara(name);
        return JSONObject.parseObject(s);
    }

    public void setAccessUser(User accessUser) {
        this.accessUser = accessUser;
    }

    protected void setPageAttr(Page page) {
        setAttr("list", page.getList());
        setAttr("page_number", page.getPageNumber());
        setAttr("page_size", page.getPageSize());
        setAttr("total_size", page.getTotalRow());
        setAttr("total_page", page.getTotalPage());
    }
}
