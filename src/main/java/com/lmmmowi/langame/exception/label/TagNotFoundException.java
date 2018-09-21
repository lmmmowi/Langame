package com.lmmmowi.langame.exception.label;

import com.lmmmowi.langame.exception.LangameException;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/20
 * @Description:
 */
public class TagNotFoundException extends LangameException{
    @Override
    public int getCode() {
        return 3001;
    }
}
