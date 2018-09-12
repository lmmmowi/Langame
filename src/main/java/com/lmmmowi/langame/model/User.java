package com.lmmmowi.langame.model;

import cn.hutool.crypto.SecureUtil;
import com.lmmmowi.langame.common.BaseModel;
import com.lmmmowi.langame.util.TimeKit;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class User extends BaseModel<User> {

    public static final User DAO = new User();

    @Override
    public Integer getId() {
        return super.getId();
    }

    public boolean matchPassword(String raw) {
        String password = saltPassword(raw);
        return password.equals(this.getStr("password"));
    }

    public User findByUsername(String username) {
        String sql = String.format("SELECT * FROM %s WHERE username=?", getTable());
        return findFirst(sql, username);
    }

    @Override
    public boolean save() {
        String raw = this.getStr("password");
        this.set("password", saltPassword(raw));
        this.set("create_time", TimeKit.now());
        return super.save();
    }

    private String saltPassword(String raw) {
        return SecureUtil.md5("langame" + raw);
    }
}
