package com.lmmmowi.langame.service_impl;

import com.jfinal.kit.StrKit;
import com.lmmmowi.langame.exception.user.EmailUsedException;
import com.lmmmowi.langame.exception.user.InvalidPasswordException;
import com.lmmmowi.langame.exception.user.UserNotFoundException;
import com.lmmmowi.langame.helper.ResourceRandom;
import com.lmmmowi.langame.model.User;
import com.lmmmowi.langame.service.UserService;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User signup(String nickname, String email, String password) {
        if (StrKit.isBlank(email) || StrKit.isBlank(password)) {
            throw new InvalidParameterException();
        }

        User user = User.DAO.findByUsername(email);
        if (user != null) {
            throw new EmailUsedException();
        }

        if (nickname == null) {
            nickname = ResourceRandom.getRandomNickname();
        }

        user = new User();
        user.set("nickname", nickname);
        user.set("username", email);
        user.set("email", email);
        user.set("password", password);
        user.set("avatar", ResourceRandom.getRandomAvatar());
        user.save();

        user.remove("password");
        return user;
    }

    @Override
    public User signin(String username, String password) {
        User user = User.DAO.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }

        boolean match = user.matchPassword(password);
        if (!match) {
            throw new InvalidPasswordException();
        }

        user.remove("password");
        return user;
    }
}
