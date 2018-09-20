package com.lmmmowi.langame.service_impl;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.bean.action.MemberJoinAction;
import com.lmmmowi.langame.enums.MemberRole;
import com.lmmmowi.langame.exception.member.MemberExist;
import com.lmmmowi.langame.exception.project.ProjectNotFound;
import com.lmmmowi.langame.exception.user.UserNotFoundException;
import com.lmmmowi.langame.model.Member;
import com.lmmmowi.langame.model.Project;
import com.lmmmowi.langame.model.User;
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
    public Member addMember(String projectId, User user, MemberRole memberRole) {
        if (user == null) {
            throw new UserNotFoundException();
        }

        Project project = Project.DAO.findById(projectId);
        if (project == null) {
            throw new ProjectNotFound();
        }

        Member member = Member.DAO.findById(projectId, user.getId());
        if (member != null) {
            throw new MemberExist();
        }

        member = new Member();
        member.set("project", projectId);
        member.set("user", user.getId());
        member.set("role", memberRole.name());
        boolean success = member.save();

        if (success) {
            // 记录行为
            userRecordService.record(new MemberJoinAction(user, project));
        }

        return member;
    }

    @Override
    public Page<Member> getMembers(String projectId, Integer pageNumber, Integer pageSize) {
        return Member.DAO.pageByProject(projectId, pageNumber, pageSize);
    }
}
