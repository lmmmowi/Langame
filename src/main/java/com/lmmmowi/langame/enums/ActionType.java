package com.lmmmowi.langame.enums;

import com.lmmmowi.langame.bean.action.*;

public enum ActionType {

    // 创建项目
    CREATE_PROJECT(CreateProjectAction.class),

    // 添加成员
    MEMBER_JOIN(MemberJoinAction.class),

    // 创建节点
    CREATE_NODE(CreateNodeAction.class),

    // 重命名节点
    RENAME_NODE(RenameNodeAction.class),

    //新建标签
    CREATE_TAG(CreateTagAction.class),

    //删除标签
    DELETE_TAG(DeleteTagAction.class),

    //更新标签
    UPDATE_TAG(UpdateTagAction.class),

    // 更新词条文案
    UPDATE_LANG_ENTRY(UpdateLangEntryAction.class);

    private Class<? extends BaseAction> actionClass;

    ActionType(Class<? extends BaseAction> actionClass) {
        this.actionClass = actionClass;
    }


    public Class<? extends BaseAction> getActionClass() {
        return actionClass;
    }
}
