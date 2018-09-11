package com.lmmmowi.langame;

import com.jfinal.core.JFinal;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class Bootstrap {

    public static void main(String[] args) {
        JFinal.start("web", 6080, "/");
    }
}
