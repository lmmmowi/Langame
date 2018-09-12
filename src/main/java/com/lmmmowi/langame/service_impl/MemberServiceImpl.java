package com.lmmmowi.langame.service_impl;

import com.lmmmowi.langame.model.Member;
import com.lmmmowi.langame.model.Project;
import com.lmmmowi.langame.model.User;
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
    public Member addMember(Project project, User user) {
        Member member = new Member();
        member.set("project", project.getId());
        member.set("user", user.getId());
        member.save();
        return member;
    }
}
