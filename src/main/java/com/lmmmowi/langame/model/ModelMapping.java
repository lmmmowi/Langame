package com.lmmmowi.langame.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class ModelMapping {

    public static void doMapping(ActiveRecordPlugin arp) {
        arp.addMapping("langame_user", "id", User.class);
    }

}
