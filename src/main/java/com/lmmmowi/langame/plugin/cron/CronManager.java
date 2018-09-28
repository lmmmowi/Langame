package com.lmmmowi.langame.plugin.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: mowi
 * @Date: 2018/9/27
 * @Description:
 */
public class CronManager {

    public static final Logger logger = LoggerFactory.getLogger(CronManager.class);

    private List<CronConfig> cronConfigs = new ArrayList<>();
    private Map<String, CronThread> threadMap = new HashMap<>();

    public void addCron(CronConfig cronConfig) {
        cronConfigs.add(cronConfig);

        CronThread cronThread = new CronThread(cronConfig);
        threadMap.put(cronConfig.getName(), cronThread);
    }

    public void start() {
        for (CronThread cronThread : threadMap.values()) {
            cronThread.start();
        }
    }

    public void stop() {
        for (CronThread cronThread : threadMap.values()) {
            cronThread.running = false;
        }
    }

    private class CronThread extends Thread {

        CronConfig cronConfig;
        boolean running;

        public CronThread(CronConfig cronConfig) {
            this.cronConfig = cronConfig;
        }

        @Override
        public void run() {
            running = true;
            int seconds = cronConfig.getIntervalSeconds();

            while (running) {
                try {
                    Runnable runnable = getJobRunnable();
                    runnable.run();
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage());
                }

                try {
                    Thread.sleep(seconds * 1000L);
                } catch (InterruptedException e) {
                }
            }
        }

        private Runnable getJobRunnable() {
            Class<? extends Runnable> jobClass = cronConfig.getJobClass();
            try {
                return jobClass.newInstance();
            } catch (InstantiationException e) {
            } catch (IllegalAccessException e) {
            }
            return null;
        }
    }
}
