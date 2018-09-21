package com.lmmmowi.langame.cache;

import com.lmmmowi.langame.service_impl.helper.PathNodeHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mowi
 * @Date: 2018/9/17
 * @Description:
 */
public class LgCache {

    public static final String CACHE_COMPLETE_NODE_PATH = "CACHE_COMPLETE_NODE_PATH";

    private static Map<String, LgCache> CACHE_MAP = new HashMap<>();

    public static synchronized LgCache use(String projectId) {
        LgCache lgCache = CACHE_MAP.get(projectId);
        if (lgCache == null) {
            lgCache = new LgCache(projectId);
            CACHE_MAP.put(projectId, lgCache);
        }
        return lgCache;
    }

    private String projectId;
    private Map<String, Object> cacheContent = new HashMap<>();

    private LgCache(String projectId) {
        this.projectId = projectId;
    }

    public <T> T getCache(String key) {
        Object cacheObject = cacheContent.get(key);
        if (cacheObject == null) {
            cacheObject = refresh(key);
            System.out.println("refresh cache");
        }
        else{
            System.out.println("get from cache");
        }
        return (T) cacheObject;
    }

    public <T> T refresh(String key) {
        Object cacheData = loadCacheData(key);
        if (cacheData != null) {
            cacheContent.put(key, cacheData);
            return (T) cacheData;
        }
        return null;
    }

    private Object loadCacheData(String key) {
        switch (key) {
            case CACHE_COMPLETE_NODE_PATH:
                return PathNodeHelper.getCompletePathMap(projectId);
        }
        return null;
    }
}
