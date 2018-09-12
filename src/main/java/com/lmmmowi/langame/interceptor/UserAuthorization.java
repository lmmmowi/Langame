package com.lmmmowi.langame.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.lmmmowi.langame.common.ApiHeaders;
import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.common.RequireSignin;
import com.lmmmowi.langame.helper.UserTokenHelper;
import com.lmmmowi.langame.model.User;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public class UserAuthorization implements Interceptor {

    @Override
    public void intercept(Invocation invocation) {
        Controller controller = invocation.getController();

        if (controller instanceof BaseApi) {
            BaseApi api = (BaseApi) controller;
            User accessUser = null;

            String token = controller.getRequest().getHeader(ApiHeaders.HEADER_USER_TOKEN);
            if (token != null) {
                Integer userId = UserTokenHelper.getDefault().verify(token);
                accessUser = User.DAO.findById(userId);
            }

            if (accessUser != null) {
                api.setAccessUser(accessUser);
            } else {
                RequireSignin annotation = invocation.getMethod().getAnnotation(RequireSignin.class);
                if (annotation != null){
                    throw new com.lmmmowi.langame.exception.user.RequireSignin();
                }
            }
        }

        invocation.invoke();
    }
}
