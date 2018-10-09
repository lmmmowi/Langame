package com.lmmmowi.langame.service;

import com.lmmmowi.langame.model.Tag;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/20
 * @Description:
 */
public interface TagService {

    Tag createTag(String projectId, String tagName);

    Tag updateTag(Integer tagId, String tagName);
}
