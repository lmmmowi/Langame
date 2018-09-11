package com.lmmmowi.langame.api;

import com.jfinal.kit.StrKit;
import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.exception.common.InvalidParameter;
import com.lmmmowi.langame.exception.user.EmailUsed;
import com.lmmmowi.langame.exception.user.InvalidPassword;
import com.lmmmowi.langame.exception.user.UserNotFound;
import com.lmmmowi.langame.model.User;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class UserApi extends BaseApi {

    public void signin() {
        String username = getPara("username");
        String password = getPara("password");

        User user = User.DAO.findByUsername(username);
        if (user == null) {
            throw new UserNotFound();
        }

        boolean match = user.matchPassword(password);
        if (!match) {
            throw new InvalidPassword();
        }

        user.remove("password");
        setAttr("user", user);
    }

    public void signup() {
        String email = getPara("email");
        String password = getPara("password");

        if (StrKit.isBlank(email) || StrKit.isBlank(password)) {
            throw new InvalidParameter();
        }

        User user = User.DAO.findByUsername(email);
        if (user != null) {
            throw new EmailUsed();
        }

        user = new User();
        user.set("username", email);
        user.set("email", email);
        user.set("password", password);
        user.save();

        setAttr("user", user);
    }

}
