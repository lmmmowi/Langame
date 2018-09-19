package com.lmmmowi.langame.service_impl;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.model.User;
import com.lmmmowi.langame.model.UserRecord;
import com.lmmmowi.langame.service.UserRecordService;
import org.springframework.stereotype.Service;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/19
 * @Description:
 */
@Service
public class UserRecordServiceImpl implements UserRecordService {
    @Override
    public void addRecord(String projectId, int userId, String recordType) {
        User user = User.DAO.findById(userId);
        UserRecord userRecord = new UserRecord();
        userRecord.set("project_id", projectId);
        userRecord.set("nickname", user.getStr("nickname"));
        userRecord.set("content", recordType);
        userRecord.save();
    }

    @Override
    public Page<UserRecord> getUserRecordList(String projectId, Integer pageSize, Integer pageNumber) {
        String sqlEx = String.format(" FROM %s WHERE project_id=? ORDER BY create_time", new Object[]{UserRecord.DAO.getTable()});
        String sql = "SELECT * ";
        return UserRecord.DAO.paginate(pageNumber.intValue(), pageSize.intValue(), sql, sqlEx, new Object[]{projectId});
    }
}
