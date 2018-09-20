package com.lmmmowi.langame.service_impl;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.bean.action.MemberJoinAction;
import com.lmmmowi.langame.model.Member;
import com.lmmmowi.langame.service.ActionRecordService;
import com.lmmmowi.langame.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private ActionRecordService userRecordService;

    @Override
    public Member addMember(String projectId, Integer userId) {
        Member member = new Member();
        member.set("project", projectId);
        member.set("user", userId);
        boolean success = member.save();

        if (success) {
            // 记录行为
            userRecordService.record(new MemberJoinAction(member));
        }

        return member;
    }

    @Override
    public Page<Member> getMembers(String projectId,  Integer pageNumber, Integer pageSize) {
        return Member.DAO.pageByProject(projectId, pageNumber, pageSize);
    }
}
