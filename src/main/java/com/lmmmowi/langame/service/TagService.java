package com.lmmmowi.langame.service;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.model.Tag;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/20
 * @Description:
 */
public interface TagService {

    Page<Tag> getAllLabel(String projectId, Integer pageSize, Integer pageNumber);
}
