package com.lmmmowi.langame.model;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.common.BaseModel;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/20
 * @Description:
 */
public class Tag extends BaseModel<Tag>{

    public static final Tag DAO = new Tag();

    public Page<Tag> pageByProject(String projectId, Integer pageNumber, Integer pageSize) {
        String sqlEx = String.format(" FROM %s WHERE project_id=? ORDER BY create_time", getTable());
        String sql = "SELECT * ";
        return Tag.DAO.paginate(pageNumber, pageSize, sql, sqlEx, projectId);
    }
}
