package com.lmmmowi.langame.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.lmmmowi.langame.common.BaseApi;

/**
 * @Author: mowi
 * @Date: 2018/9/20
 * @Description:
 */
public class ApiContextInitializer implements Interceptor {

    @Override
    public void intercept(Invocation invocation) {
        Controller controller = invocation.getController();
        boolean isApi = controller instanceof BaseApi;

        if (isApi) {
            ApiContext context = new ApiContext();
            ApiContext.set(context);
        }

        try {
            invocation.invoke();
        } finally {
            ApiContext.remove();
        }
    }
}
