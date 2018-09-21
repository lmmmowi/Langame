package com.lmmmowi.langame.model;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.common.BaseModel;
import com.lmmmowi.langame.enums.MemberRole;
import com.lmmmowi.langame.util.TimeKit;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public class Member extends BaseModel<Member> {

    public static final Member DAO = new Member();

    @Override
    public String getId() {
        return getProjectId() + "-" + getInt("user");
    }

    public String getProjectId() {
        return getStr("project");
    }

    public MemberRole getRole() {
        return MemberRole.valueOf(getStr("role"));
    }

    public Page<Member> pageByProject(String projectId, Integer pageNumber, Integer pageSize) {
        String sqlEx = String.format(" FROM %s WHERE project=? ORDER BY create_time", getTable());
        String sql = "SELECT * ";
        return Member.DAO.paginate(pageNumber, pageSize, sql, sqlEx, projectId);
    }

    public Member findById(String s) {
        String[] arr = s.split("-");
        String projectId = arr[0];
        Integer userId = Integer.valueOf(arr[1]);
        return findById(projectId, userId);
    }

    @Override
    public boolean save() {
        this.set("create_time", TimeKit.now());
        return super.save();
    }
}
