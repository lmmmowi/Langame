package com.lmmmowi.langame.config;

import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.lmmmowi.langame.interceptor.ApiResultOutput;
import com.lmmmowi.langame.routes.ApiRoutes;
import com.lmmmowi.langame.model.ModelMapping;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class AppConfig extends JFinalConfig {

    public AppConfig() {
        LangameConfig.getInstance().init();
        loadPropertyFile(LangameConfig.ConfigFileName);
    }

    @Override
    public void configConstant(Constants constants) {
        constants.setDevMode(getPropertyToBoolean("dev_mode"));
        constants.setJsonFactory(new LangameJsonFactory());
    }

    @Override
    public void configRoute(Routes routes) {
        routes.add(new ApiRoutes());
    }

    @Override
    public void configEngine(Engine engine) {

    }

    @Override
    public void configPlugin(Plugins plugins) {
        DruidPlugin druidPlugin = new DruidPlugin(getProperty("db.url"), getProperty("db.username"), getProperty("db.password"));
        druidPlugin.setMaxActive(getPropertyToInt("db.max_connections"));
        plugins.add(druidPlugin);

        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin("langame", druidPlugin);
        ModelMapping.doMapping(activeRecordPlugin);
        plugins.add(activeRecordPlugin);
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {
        interceptors.addGlobalActionInterceptor(new ApiResultOutput());
    }

    @Override
    public void configHandler(Handlers handlers) {

    }
}
