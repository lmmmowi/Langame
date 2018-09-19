package com.lmmmowi.langame.api;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.model.Member;
import com.lmmmowi.langame.service.MemberService;
import com.lmmmowi.langame.service.UserRecordService;
import org.springframework.beans.factory.annotation.Autowired;

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
        userRecordService.addRecord(projectId, userId,UserRecordService.ADD_MEMBER);
        setAttr("member", member);
    }

    public void getMemberList() {
        Integer pageSize = getParaToInt("page_size", 10);
        Integer pageNumber = getParaToInt("page_number", 1);
        String projectId = getPara("project_id");
        Page<Member> members = memberService.getMemberList(projectId,pageSize, pageNumber);
        setPageAttr(members);
    }
}
