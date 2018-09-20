package com.lmmmowi.langame.api;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.common.BaseApi;
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
        int userId = getParaToInt("user_id");
        Member member = memberService.addMember(projectId, userId);
        setAttr("member", member);
    }

    public void getMemberList() {
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
