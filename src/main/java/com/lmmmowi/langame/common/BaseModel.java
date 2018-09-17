package com.lmmmowi.langame.common;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.SqlPara;

import java.util.Map;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class BaseModel<M extends BaseModel> extends Model<M> {

    public String getTable() {
        return super._getTable().getName();
    }

    public <T> T getId() {
        return get("id");
    }

    public SqlPara getModelSqlPara(String key, Map data) {
        key = getClass().getName() + "." + key;
        return this._getConfig().getSqlKit().getSqlPara(key, data);
    }

    public SqlPara getModelSqlPara(String key, Model model) {
        key = getClass().getName() + "." + key;
        return super.getSqlPara(key, model);
    }

    public SqlPara getModelSqlPara(String key, Object... paras) {
        key = getClass().getName() + "." + key;
        return super.getSqlPara(key, paras);
    }

    @Override
    public M set(String attr, Object value) {
        if (!_getTable().hasColumnLabel(attr)) {
            return super.put(attr, value);
        }
        return super.set(attr, value);
    }
}
