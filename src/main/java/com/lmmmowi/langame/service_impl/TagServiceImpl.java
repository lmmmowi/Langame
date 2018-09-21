package com.lmmmowi.langame.service_impl;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.model.Tag;
import com.lmmmowi.langame.service.TagService;
import org.springframework.stereotype.Service;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/20
 * @Description:
 */
@Service
public class TagServiceImpl implements TagService {

    @Override
    public Page<Tag> getAllLabel(String projectId, Integer pageSize, Integer pageNumber) {
        return Tag.DAO.pageByProject(projectId,pageNumber,pageSize);
    }
}
