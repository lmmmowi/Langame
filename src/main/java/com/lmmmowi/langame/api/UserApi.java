package com.lmmmowi.langame.api;

import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.helper.UserTokenHelper;
import com.lmmmowi.langame.model.User;
import com.lmmmowi.langame.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class UserApi extends BaseApi {

    @Autowired
    UserService userService;

    public void signin() {
        String username = getPara("username");
        String password = getPara("password");

        User user = userService.signin(username, password);
        setAttr("user", user);
        setAttr("token", UserTokenHelper.getDefault().generate(user));
    }

    public void signup() {
        String email = getPara("email");
        String password = getPara("password");

        User user = userService.signup(email, password);
        setAttr("user", user);
        setAttr("token", UserTokenHelper.getDefault().generate(user));
    }

}
