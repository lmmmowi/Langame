package com.lmmmowi.langame.model;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.lmmmowi.langame.common.BaseModel;
import com.lmmmowi.langame.enums.ActionType;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/19
 * @Description:
 */
public class ActionRecord extends BaseModel<ActionRecord> {

    public static final ActionRecord DAO = new ActionRecord();

    public ActionType getActionType() {
        return ActionType.valueOf(getStr("action"));
    }

    public String getSubject(){
        return getStr("subject");
    }

    public String getTarget(){
        return getStr("target");
    }

    public Page<ActionRecord> pageByProject(String projectId, Integer pageNumber, Integer pageSize) {
        Kv kv = Kv.by("projectId", projectId);
        SqlPara sqlPara = getModelSqlPara("query", kv);
        return paginate(pageNumber, pageSize, sqlPara);
    }
}
