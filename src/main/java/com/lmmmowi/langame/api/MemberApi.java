package com.lmmmowi.langame.api;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.enums.MemberRole;
import com.lmmmowi.langame.exception.user.UserNotFoundException;
import com.lmmmowi.langame.model.Member;
import com.lmmmowi.langame.model.User;
import com.lmmmowi.langame.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/19
 * @Description:
 */
public class MemberApi extends BaseApi {

    @Autowired
    private MemberService memberService;

    public void addMember() {
        String projectId = getPara("project_id");
        String username = getPara("username");

        User user = User.DAO.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }

        Member member = memberService.addMember(projectId, user, MemberRole.member);
        setAttr("member", member);
    }

    public void getMembers() {
        Integer pageNumber = getParaToInt("page_number", 1);
        Integer pageSize = getParaToInt("page_size", 10);
        String projectId = getPara("project_id");
        Page<Member> memberPage = memberService.getMembers(projectId, pageNumber, pageSize);

        memberPage.getList().forEach(member -> {
            Integer userId = member.getInt("user");
            User user = User.DAO.findById(userId);
            member.set("user", user);
        });

        setPageAttr(memberPage);
    }
}
