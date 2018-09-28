package com.lmmmowi.langame.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: mowi
 * @Date: 2018/9/25
 * @Description:
 */
public interface ImportService {

    void importFromJson(Integer parentNodeId, String language, JSONObject json);

}
