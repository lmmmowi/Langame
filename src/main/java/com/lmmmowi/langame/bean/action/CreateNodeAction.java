package com.lmmmowi.langame.bean.action;

import com.lmmmowi.langame.enums.ActionType;
import com.lmmmowi.langame.model.PathNode;
import com.lmmmowi.langame.model.User;

/**
 * @Author: mowi
 * @Date: 2018/9/20
 * @Description:
 */
public class CreateNodeAction extends BaseAction<User, PathNode> {

    public CreateNodeAction(User subject, PathNode target) {
        super(subject, target);
    }

    @Override
    public String getProjectId() {
        return target.getProjectId();
    }

    @Override
    public ActionType getActionType() {
        return ActionType.CREATE_NODE;
    }
}
