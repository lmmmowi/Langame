package com.lmmmowi.langame.common;

import com.jfinal.plugin.activerecord.Model;

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
}
