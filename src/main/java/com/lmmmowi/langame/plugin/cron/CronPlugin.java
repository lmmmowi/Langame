package com.lmmmowi.langame.plugin.cron;

import com.jfinal.plugin.IPlugin;

/**
 * @Author: mowi
 * @Date: 2018/9/27
 * @Description:
 */
public class CronPlugin implements IPlugin {

    private CronManager cronManager = new CronManager();

    public void addCron(Class<? extends Runnable> cron, int intervalSeconds) {
        CronConfig cronConfig = new CronConfig(cron, intervalSeconds);
        cronManager.addCron(cronConfig);
    }

    @Override
    public boolean start() {
        cronManager.start();
        return true;
    }

    @Override
    public boolean stop() {
        cronManager.stop();
        return true;
    }
}
