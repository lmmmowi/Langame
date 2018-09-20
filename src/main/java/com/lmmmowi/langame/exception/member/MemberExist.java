package com.lmmmowi.langame.exception.member;

import com.lmmmowi.langame.exception.LangameException;

/**
 * @Author: mowi
 * @Date: 2018/9/20
 * @Description:
 */
public class MemberExist extends LangameException {

    @Override
    public int getCode() {
        return 3000;
    }
}
