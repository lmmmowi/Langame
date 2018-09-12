package com.lmmmowi.langame.exception;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public abstract class LangameException extends RuntimeException implements LangameError {

    @Override
    public String getErrorMessage() {
        return getClass().getSimpleName();
    }
}
