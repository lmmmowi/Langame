package com.lmmmowi.langame.interceptor;

import com.lmmmowi.langame.model.User;

/**
 * @Author: mowi
 * @Date: 2018/9/20
 * @Description:
 */
public class ApiContext {

    private static final ThreadLocal<ApiContext> THREAD_LOCAL = new ThreadLocal<>();

    public static void set(ApiContext apiContext) {
        THREAD_LOCAL.set(apiContext);
    }

    public static ApiContext get() {
        return THREAD_LOCAL.get();
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }

    private User accessUser;

    ApiContext() {
    }

    public User getAccessUser() {
        return accessUser;
    }

    public void setAccessUser(User accessUser) {
        this.accessUser = accessUser;
    }
}
