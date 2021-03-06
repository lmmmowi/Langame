package com.lmmmowi.langame.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.exception.LangameException;

import java.util.Enumeration;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class ApiResultOutput implements Interceptor {

    private static final int DEFAULT_SUCCESS_CODE = 0;
    private static final int DEFAULT_ERROR_CODE = -1;

    @Override
    public void intercept(Invocation invocation) {
        Exception exception = null;
        try {
            invocation.invoke();
        } catch (Exception e) {
            exception = e;
        }

        Controller controller = invocation.getController();
        if (controller instanceof BaseApi) {
            Record result = buildResult((BaseApi) controller, exception);
            controller.renderJson(result);
        }
    }

    private Record buildResult(BaseApi api, Exception e) {
        Record result = new Record();

        // 错误码
        int code = DEFAULT_SUCCESS_CODE;
        if (e != null) {
            String errorMessage;
            if (e instanceof LangameException) {
                LangameException le = (LangameException) e;
                code = le.getCode();
                errorMessage = le.getErrorMessage();
            } else {
                code = DEFAULT_ERROR_CODE;
                errorMessage = e.getMessage();
                if (errorMessage == null && e.getCause() != null) {
                    errorMessage = e.getCause().getMessage();
                }

                e.printStackTrace();
            }
            result.set("error", errorMessage);
        }
        result.set("code", code);

        // 数据
        Record data = new Record();
        Enumeration<String> attrNames = api.getAttrNames();
        while (attrNames.hasMoreElements()) {
            String attr = attrNames.nextElement();
            data.set(attr, api.getAttr(attr));
        }
        result.set("data", data);

        return result;
    }
}
