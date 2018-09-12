package com.lmmmowi.langame.service;

import com.lmmmowi.langame.model.User;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public interface UserService {

    User signup(String email, String password);

    User signin(String username, String password);

}
