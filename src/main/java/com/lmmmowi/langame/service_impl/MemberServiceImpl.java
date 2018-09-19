package com.lmmmowi.langame.service_impl;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.model.Member;
import com.lmmmowi.langame.service.MemberService;
import org.springframework.stereotype.Service;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Override
    public Member addMember(String projectId, Integer userId) {
        Member member = new Member();
        member.set("project", projectId);
        member.set("user", userId);
        member.save();
        return member;
    }

    @Override
    public Page<Member> getMemberList(String projectId, Integer pageSize, Integer pageNumber) {
        String sqlEx = String.format(" FROM %s WHERE project=? ORDER BY create_time", new Object[]{Member.DAO.getTable()});
        String sql = "SELECT * ";
        return Member.DAO.paginate(pageNumber.intValue(), pageSize.intValue(), sql, sqlEx, new Object[]{projectId});
    }
}
