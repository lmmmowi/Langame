package com.lmmmowi.langame.util;

import java.sql.Timestamp;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class TimeKit {

    public static Timestamp now(){
        return new Timestamp(System.currentTimeMillis());
    }

}
