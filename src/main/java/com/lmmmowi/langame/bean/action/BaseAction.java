package com.lmmmowi.langame.bean.action;

import com.lmmmowi.langame.common.BaseModel;

/**
 * @Author: mowi
 * @Date: 2018/9/20
 * @Description:
 */
public abstract class BaseAction<S, T> implements IAction<S, T> {

    protected S subject;
    protected T target;

    public BaseAction(S subject, T target) {
        this.subject = subject;
        this.target = target;
    }

    @Override
    public S getSubject() {
        return subject;
    }

    @Override
    public T getTarget() {
        return target;
    }

    @Override
    public String getSubjectRef() {
        if (subject == null) {
            return null;
        }

        if (subject instanceof BaseModel) {
            return ((BaseModel) subject).getId().toString();
        }

        return subject.toString();
    }

    @Override
    public String getTargetRef() {
        if (target == null) {
            return null;
        }

        if (target instanceof BaseModel) {
            return ((BaseModel) target).getId().toString();
        }

        return target.toString();
    }
}
