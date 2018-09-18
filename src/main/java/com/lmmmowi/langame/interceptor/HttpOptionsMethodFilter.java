package com.lmmmowi.langame.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * Created by mowi on 17/3/16.
 */
public class HttpOptionsMethodFilter implements Interceptor {

    @Override
    public void intercept(Invocation invocation) {
        Controller controller = invocation.getController();
        String method = controller.getRequest().getMethod().toUpperCase();
        if ("OPTIONS".equals(method)) {
            controller.renderText("ok");
        } else {
            invocation.invoke();
        }
    }
}
