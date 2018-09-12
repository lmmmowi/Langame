package com.lmmmowi.langame.service_impl;

import com.jfinal.kit.StrKit;
import com.lmmmowi.langame.exception.common.InvalidParameter;
import com.lmmmowi.langame.exception.user.EmailUsed;
import com.lmmmowi.langame.exception.user.InvalidPassword;
import com.lmmmowi.langame.exception.user.UserNotFound;
import com.lmmmowi.langame.model.User;
import com.lmmmowi.langame.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User signup(String email, String password) {
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

        user.remove("password");
        return user;
    }

    @Override
    public User signin(String username, String password) {
        User user = User.DAO.findByUsername(username);
        if (user == null) {
            throw new UserNotFound();
        }

        boolean match = user.matchPassword(password);
        if (!match) {
            throw new InvalidPassword();
        }

        user.remove("password");
        return user;
    }
}
