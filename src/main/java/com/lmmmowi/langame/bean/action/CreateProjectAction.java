package com.lmmmowi.langame.bean.action;

import com.lmmmowi.langame.enums.ActionType;
import com.lmmmowi.langame.model.Project;
import com.lmmmowi.langame.model.User;

/**
 * @Author: mowi
 * @Date: 2018/9/20
 * @Description:
 */
public class CreateProjectAction extends BaseAction<User, Project> {

    public CreateProjectAction(User subject, Project target) {
        super(subject, target);
    }

    @Override
    public String getProjectId() {
        return target.getId();
    }

    @Override
    public ActionType getActionType() {
        return ActionType.CREATE_PROJECT;
    }
}
