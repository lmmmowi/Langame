package com.lmmmowi.langame.service;

import com.lmmmowi.langame.model.Member;
import com.lmmmowi.langame.model.Project;
import com.lmmmowi.langame.model.User;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public interface MemberService {

    Member addMember(Project project, User user);

}
