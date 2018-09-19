package com.lmmmowi.langame.service;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.model.Member;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public interface MemberService {

    Member addMember(String projectId, Integer userId);

    Page<Member> getMemberList(String projectId, Integer pageSize, Integer pageNumber);

}
