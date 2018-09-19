package com.lmmmowi.langame.api;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.model.UserRecord;
import com.lmmmowi.langame.service.UserRecordService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/19
 * @Description:
 */
public class UserRecordApi extends BaseApi {

    @Autowired
    private UserRecordService userRecordService;

    public void getUserRecordList() {
        Integer pageSize = getParaToInt("page_size", 10);
        Integer pageNumber = getParaToInt("page_number", 1);
        String projectId = getPara("project_id");
        Page<UserRecord> recordList = userRecordService.getUserRecordList(projectId, pageSize, pageNumber);
        setPageAttr(recordList);
    }
}
