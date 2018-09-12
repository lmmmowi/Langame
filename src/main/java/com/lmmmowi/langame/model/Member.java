package com.lmmmowi.langame.model;

import com.lmmmowi.langame.common.BaseModel;
import com.lmmmowi.langame.util.TimeKit;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public class Member extends BaseModel<Member> {

    public static final Member DAO = new Member();

    @Override
    public boolean save() {
        this.set("create_time", TimeKit.now());
        return super.save();
    }
}
