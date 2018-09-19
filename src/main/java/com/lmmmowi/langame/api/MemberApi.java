package com.lmmmowi.langame.api;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.model.Member;
import com.lmmmowi.langame.model.User;
import com.lmmmowi.langame.service.MemberService;
import com.lmmmowi.langame.service.UserRecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/19
 * @Description:
 */
public class MemberApi extends BaseApi{

    @Autowired
    private MemberService memberService;

    @Autowired
    private UserRecordService userRecordService;

    public void addMember() {
        String projectId = getPara("project_id");
        int userId = getParaToInt("user_id");
        Member member = memberService.addMember(projectId, userId);
        User user = User.DAO.findById(userId);
        userRecordService.addRecord(projectId, userId,UserRecordService.ADD_MEMBER,user.getStr("nickname"));
        setAttr("member", member);
    }

    public void getMemberList() {
        Integer pageSize = getParaToInt("page_size", 10);
        Integer pageNumber = getParaToInt("page_number", 1);
        String projectId = getPara("project_id");
        Page<Member> members = memberService.getMemberList(projectId,pageSize, pageNumber);
        List<Member> list = members.getList();
        List<User> users = new ArrayList<>();
        list.forEach(mem->{
            Integer userId = mem.getInt("user");
            User user= User.DAO.findById(userId);
            users.add(user);
        });
        setAttr("page_number", members.getPageNumber());
        setAttr("total_size", members.getPageSize());
        setAttr("total_page", members.getTotalPage());
        setAttr("list", users);
    }
}
