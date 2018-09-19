package com.lmmmowi.langame.model;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.SqlPara;
import com.lmmmowi.langame.common.BaseModel;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public class LangEntry extends BaseModel<LangEntry> {

    public static final LangEntry DAO = new LangEntry();

    public Integer getNodeId() {
        return getInt("node");
    }

    public String getLanguage() {
        return getStr("language");
    }

    public List<LangEntry> findByNode(Integer nodeId, String language) {
        Kv kv = Kv.by("nodeIds", Arrays.asList(nodeId))
                .set("language", language);
        SqlPara sqlPara = getModelSqlPara("query", kv);
        return find(sqlPara);
    }

    public List<LangEntry> findByNode(List<Integer> nodeIds, String language) {
        Kv kv = Kv.by("nodeIds", nodeIds)
                .set("language", language);
        SqlPara sqlPara = getModelSqlPara("query", kv);
        return find(sqlPara);
    }
}
