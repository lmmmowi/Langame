package com.lmmmowi.langame.service_impl;

import com.lmmmowi.langame.bean.action.IAction;
import com.lmmmowi.langame.model.ActionRecord;
import com.lmmmowi.langame.service.ActionRecordService;
import com.lmmmowi.langame.util.TimeKit;
import org.springframework.stereotype.Service;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/19
 * @Description:
 */
@Service
public class ActionRecordServiceImpl implements ActionRecordService {

    @Override
    public ActionRecord record(IAction action) {
        ActionRecord userRecord = new ActionRecord();
        userRecord.set("project_id", action.getProjectId());
        userRecord.set("subject", action.getSubjectRef());
        userRecord.set("action", action.getActionType().name());
        userRecord.set("target", action.getTargetRef());
        userRecord.set("create_time", TimeKit.now());
        userRecord.save();
        return userRecord;
    }
}
