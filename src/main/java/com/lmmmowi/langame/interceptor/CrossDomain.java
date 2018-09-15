package com.lmmmowi.langame.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.lmmmowi.langame.common.ApiHeaders;

/**
 * @Author: mowi
 * @Date: 2018/8/18
 * @Description: 
 */
public class CrossDomain implements Interceptor {

    @Override
    public void intercept(Invocation invocation) {
        StringBuilder allowHeadersStringBuilder = new StringBuilder();
        allowHeadersStringBuilder.append("Origin, X-Requested-With, Content-Type, Accept");
        for (String apiHeader : ApiHeaders.ALL) {
            allowHeadersStringBuilder.append(", " + apiHeader);
        }
        String allowHeaders = allowHeadersStringBuilder.toString();

        invocation.getController().getResponse().setHeader("Access-Control-Allow-Origin", "*");
        invocation.getController().getResponse().setHeader("Access-Control-Allow-Headers", allowHeaders);
        invocation.invoke();
    }
}
