package com.lmmmowi.langame.plugin.cron;

/**
 * @Author: mowi
 * @Date: 2018/9/27
 * @Description:
 */
public class CronConfig {

    private String name;
    private Class<? extends Runnable> jobClass;
    private int intervalSeconds;

    public CronConfig(Class<? extends Runnable> jobClass, int intervalSeconds) {
        this.jobClass = jobClass;
        this.intervalSeconds = intervalSeconds;
        this.name = jobClass.getName();
    }

    public String getName() {
        return name;
    }

    public Class<? extends Runnable> getJobClass() {
        return jobClass;
    }

    public int getIntervalSeconds() {
        return intervalSeconds;
    }
}
