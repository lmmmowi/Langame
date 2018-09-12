package com.lmmmowi.langame.plugin.spring;

import com.jfinal.plugin.IPlugin;
import org.springframework.context.ApplicationContext;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description: 
 */
public class SpringPlugin implements IPlugin {

    private ApplicationContext ctx;

    public SpringPlugin(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public boolean start() {
        IocInterceptor.ctx = ctx;
        return true;
    }

    @Override
    public boolean stop() {
        return true;
    }
}
