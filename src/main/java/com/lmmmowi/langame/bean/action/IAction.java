package com.lmmmowi.langame.bean.action;

import com.lmmmowi.langame.enums.ActionType;

/**
 * @Author: mowi
 * @Date: 2018/9/20
 * @Description:
 */
public interface IAction<S, T> {

    String getProjectId();

    ActionType getActionType();

    String getSubjectRef();

    String getTargetRef();

    S getSubject();

    T getTarget();

}
