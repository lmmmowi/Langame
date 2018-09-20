package com.lmmmowi.langame.api;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.bean.action.ActionParser;
import com.lmmmowi.langame.bean.action.IAction;
import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.model.ActionRecord;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/19
 * @Description:
 */
public class ActionRecordApi extends BaseApi {

    public void getProjectActionRecords() {
        Integer pageSize = getParaToInt("page_size", 10);
        Integer pageNumber = getParaToInt("page_number", 1);
        String projectId = getPara("project_id");

        Page<ActionRecord> recordList = ActionRecord.DAO.pageByProject(projectId, pageNumber, pageSize);

        recordList.getList().forEach(record -> {
            IAction action = new ActionParser(record).parseAction();
            record.set("subject", action.getSubject());
            record.set("target", action.getTarget());
        });

        setPageAttr(recordList);
    }
}
