package com.lmmmowi.langame.model;

import cn.hutool.crypto.SecureUtil;
import com.lmmmowi.langame.common.BaseModel;
import com.lmmmowi.langame.util.TimeKit;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public class Project extends BaseModel<Project> {

    public static final Project DAO = new Project();

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public boolean save() {
        this.set("id", generateId());
        this.set("create_time", TimeKit.now());
        return super.save();
    }

    private String generateId() {
        String str = SecureUtil.md5("project_" + System.currentTimeMillis());
        return str.substring(8, 24);
    }
}
