package com.lmmmowi.langame.service;

import com.lmmmowi.langame.bean.action.IAction;
import com.lmmmowi.langame.model.ActionRecord;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/19
 * @Description: 用户操作记录服务
 */
public interface ActionRecordService {

    ActionRecord record(IAction action);

}
