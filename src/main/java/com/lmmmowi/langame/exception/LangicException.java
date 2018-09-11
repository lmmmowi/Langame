package com.lmmmowi.langame.exception;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public abstract class LangicException extends RuntimeException implements LangicError {

    @Override
    public String getErrorMessage() {
        return getClass().getSimpleName();
    }
}
