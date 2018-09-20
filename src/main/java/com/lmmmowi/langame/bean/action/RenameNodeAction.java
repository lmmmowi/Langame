package com.lmmmowi.langame.bean.action;

import com.lmmmowi.langame.enums.ActionType;
import com.lmmmowi.langame.model.PathNode;
import com.lmmmowi.langame.model.User;

/**
 * @Author: mowi
 * @Date: 2018/9/20
 * @Description:
 */
public class RenameNodeAction extends BaseAction<User, PathNode> {

    public RenameNodeAction(User subject, PathNode target) {
        super(subject, target);
    }

    @Override
    public String getProjectId() {
        return target.getProjectId();
    }

    @Override
    public ActionType getActionType() {
        return ActionType.RENAME_NODE;
    }
}
