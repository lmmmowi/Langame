package com.lmmmowi.langame.bean.action;

import com.lmmmowi.langame.enums.ActionType;
import com.lmmmowi.langame.model.Project;
import com.lmmmowi.langame.model.User;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/20
 * @Description:
 */
public class CreateTagAction extends BaseAction<User, Project> {

    public CreateTagAction(User subject, Project target) {
        super(subject, target);
    }

    @Override
    public String getProjectId() {
        return target.getId();
    }

    @Override
    public ActionType getActionType() {
        return ActionType.CREATE_LABEL;
    }
}
