package com.lmmmowi.langame.exception.common;

import com.lmmmowi.langame.exception.LangicException;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class InvalidParameter extends LangicException {

    @Override
    public int getCode() {
        return 1;
    }
}
