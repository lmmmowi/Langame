package com.lmmmowi.langame.service;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.enums.MemberRole;
import com.lmmmowi.langame.model.Member;
import com.lmmmowi.langame.model.User;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public interface MemberService {

    Member addMember(String projectId, User user, MemberRole memberRole);

    Page<Member> getMembers(String projectId, Integer pageSize, Integer pageNumber);

}
