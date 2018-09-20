package com.lmmmowi.langame.bean.action;

import com.lmmmowi.langame.enums.ActionType;
import com.lmmmowi.langame.model.Member;

/**
 * @Author: mowi
 * @Date: 2018/9/20
 * @Description:
 */
public class MemberJoinAction extends BaseAction<Integer, Member> {

    public MemberJoinAction(Member target) {
        super(target.getInt("user"), target);
    }

    @Override
    public String getProjectId() {
        return target.getProjectId();
    }

    @Override
    public ActionType getActionType() {
        return ActionType.MEMBER_JOIN;
    }
}
