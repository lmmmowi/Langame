package com.lmmmowi.langame.exception.project;

import com.lmmmowi.langame.exception.LangameException;

/**
 * @Author: mowi
 * @Date: 2018/9/20
 * @Description:
 */
public class ProjectNotFound extends LangameException {

    @Override
    public int getCode() {
        return 2000;
    }
}
