package com.lmmmowi.langame.exception.tag;

import com.lmmmowi.langame.exception.LangameException;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/20
 * @Description:
 */
public class TagNotFound extends LangameException{
    @Override
    public int getCode() {
        return 5000;
    }
}
