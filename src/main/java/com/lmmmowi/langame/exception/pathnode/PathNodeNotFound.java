package com.lmmmowi.langame.exception.pathnode;

import com.lmmmowi.langame.exception.LangameException;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public class PathNodeNotFound extends LangameException {

    @Override
    public int getCode() {
        return 4000;
    }
}
