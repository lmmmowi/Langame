package com.lmmmowi.langame.model;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.common.BaseModel;
import com.lmmmowi.langame.util.TimeKit;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public class Member extends BaseModel<Member> {

    public static final Member DAO = new Member();

    public String getProjectId() {
        return getStr("project");
    }

    public Page<Member> pageByProject(String projectId, Integer pageNumber, Integer pageSize) {
        String sqlEx = String.format(" FROM %s WHERE project=? ORDER BY create_time", getTable());
        String sql = "SELECT * ";
        return Member.DAO.paginate(pageNumber, pageSize, sql, sqlEx, projectId);
    }

    @Override
    public boolean save() {
        this.set("create_time", TimeKit.now());
        return super.save();
    }
}
