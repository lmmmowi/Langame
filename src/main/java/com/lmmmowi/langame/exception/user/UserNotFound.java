package com.lmmmowi.langame.exception.user;

import com.lmmmowi.langame.exception.LangameException;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class UserNotFound extends LangameException {

    @Override
    public int getCode() {
        return 1001;
    }

}
