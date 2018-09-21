package com.lmmmowi.langame.model;

import com.lmmmowi.langame.common.BaseModel;

import java.util.List;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/21
 * @Description:
 */
public class TagBind extends BaseModel<TagBind> {

    public static final TagBind DAO = new TagBind();

    public List<TagBind> getByObj(String objectType, String objectRef) {
        String sql = String.format("SELECT * FROM %s WHERE object_type=? AND object_ref=?", getTable());
        return find(sql, objectType, objectRef);
    }
}
