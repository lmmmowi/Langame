package com.lmmmowi.langame.config;

import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.lmmmowi.langame.interceptor.*;
import com.lmmmowi.langame.plugin.spring.IocInterceptor;
import com.lmmmowi.langame.plugin.spring.SpringPlugin;
import com.lmmmowi.langame.routes.ApiRoutes;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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

    private void initSpring() {

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
        SpringPlugin springPlugin = new SpringPlugin(new ClassPathXmlApplicationContext("spring/application-context.xml"));
        plugins.add(springPlugin);

        DruidPlugin druidPlugin = new DruidPlugin(getProperty("db.url"), getProperty("db.username"), getProperty("db.password"));
        druidPlugin.setMaxActive(getPropertyToInt("db.max_connections"));
        plugins.add(druidPlugin);

        ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin("langame", druidPlugin);
        ModelMapping.doMapping(activeRecordPlugin);
        plugins.add(activeRecordPlugin);
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {
        interceptors.addGlobalActionInterceptor(new CrossDomain());
        interceptors.addGlobalActionInterceptor(new HttpOptionsMethodFilter());
        interceptors.addGlobalActionInterceptor(new ApiContextInitializer());
        interceptors.addGlobalActionInterceptor(new ApiResultOutput());
        interceptors.addGlobalActionInterceptor(new UserAuthorization());
        interceptors.addGlobalActionInterceptor(new IocInterceptor());
    }

    @Override
    public void configHandler(Handlers handlers) {

    }
}
