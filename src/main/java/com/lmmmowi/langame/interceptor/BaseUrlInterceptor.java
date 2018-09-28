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

    private boolean useHttps;

    public BaseUrlInterceptor() {
        this(false);
    }

    public BaseUrlInterceptor(boolean userHttps) {
        this.useHttps = userHttps;
    }

    @Override
    public void intercept(Invocation invocation) {
        String actionKey = invocation.getActionKey();
        String url = invocation.getController().getRequest().getRequestURL().toString();
        if (StrKit.notBlank(url)) {
            // ActionForward时可能为-1，不重新设置BaseUrl
            int index = url.indexOf(actionKey);
            if (index >= 0) {
                String baseUrl = url.substring(0, index);

                if (useHttps) {
                    baseUrl = baseUrl.replace("http://", "https://");
                }

                baseUrlThreadLocal.set(baseUrl);
            }
        }

        try {
            invocation.invoke();
        } finally {
            baseUrlThreadLocal.remove();
        }
    }
}
