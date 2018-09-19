package com.lmmmowi.langame.service;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.model.UserRecord;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/19
 * @Description: 用户操作记录服务
 */
public interface UserRecordService {

    String NEW_PROJECT = "新建了项目";
    String ADD_MEMBER= "添加了成员";
    String ADD_WORDS = "添加了词条";
    String UPDATE_WORDS = "更新了词条";

    void addRecord(String projectId, int userId,String recordType,String content);

    Page<UserRecord> getUserRecordList(String projectId, Integer pageSize, Integer pageNumber);
}
