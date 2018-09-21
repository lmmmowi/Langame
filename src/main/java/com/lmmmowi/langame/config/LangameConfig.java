package com.lmmmowi.langame.config;

import com.jfinal.kit.PropKit;

import java.util.Properties;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class LangameConfig {

    public static final String ConfigFileName = "langame-config.properties";

    private final static LangameConfig instance = new LangameConfig();

    private Properties properties;

    public static LangameConfig getInstance() {
        return instance;
    }

    private LangameConfig() {
    }

    public void init() {
        properties = PropKit.use(ConfigFileName).getProperties();
    }

    public String getDefaultNodeConnector(){
        return "/";
    }
}
