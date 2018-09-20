package com.lmmmowi.langame.bean.action;

import com.lmmmowi.langame.enums.ActionType;
import com.lmmmowi.langame.model.LangEntry;
import com.lmmmowi.langame.model.User;

/**
 * @Author: mowi
 * @Date: 2018/9/20
 * @Description:
 */
public class UpdateLangEntryAction extends BaseAction<User, LangEntry> {

    private String projectId;

    public UpdateLangEntryAction(User subject, LangEntry target, String projectId) {
        super(subject, target);
        this.projectId = projectId;
    }

    @Override
    public String getProjectId() {
        return projectId;
    }

    @Override
    public ActionType getActionType() {
        return ActionType.UPDATE_LANG_ENTRY;
    }
}
