package com.lmmmowi.langame.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;

/**
 * @Author: mowi
 * @Date: 2018/8/18
 * @Description:
 */
public class BaseUrlInterceptor implements Interceptor {

    private static ThreadLocal<String> baseUrlThreadLocal = new ThreadLocal<>();

    public static String get() {
        return baseUrlThreadLocal.get();
    }

    private String proxy;

    public BaseUrlInterceptor(String proxy) {
        this.proxy = proxy;
    }

    @Override
    public void intercept(Invocation invocation) {
        if (StrKit.isBlank(proxy)) {
            String actionKey = invocation.getActionKey();
            String url = invocation.getController().getRequest().getRequestURL().toString();
            if (StrKit.notBlank(url)) {
                // ActionForward时可能为-1，不重新设置BaseUrl
                int index = url.indexOf(actionKey);
                if (index >= 0) {
                    String baseUrl = url.substring(0, index);

                    baseUrlThreadLocal.set(baseUrl);
                }
            }
        } else {
            baseUrlThreadLocal.set(proxy);
        }

        try {
            invocation.invoke();
        } finally {
            baseUrlThreadLocal.remove();
        }
    }
}
