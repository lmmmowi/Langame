package com.lmmmowi.langame.service_impl;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.bean.action.CreateTagAction;
import com.lmmmowi.langame.exception.project.ProjectNotFound;
import com.lmmmowi.langame.exception.tag.TagNameExist;
import com.lmmmowi.langame.exception.tag.TagNotFound;
import com.lmmmowi.langame.interceptor.ApiContext;
import com.lmmmowi.langame.model.Project;
import com.lmmmowi.langame.model.Tag;
import com.lmmmowi.langame.model.User;
import com.lmmmowi.langame.service.ActionRecordService;
import com.lmmmowi.langame.service.TagService;
import com.lmmmowi.langame.util.TimeKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/20
 * @Description:
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    ActionRecordService actionRecordService;

    @Override
    public Tag createTag(String projectId, String tagName) {
        Project project = Project.DAO.findById(projectId);
        if (project == null) {
            throw new ProjectNotFound();
        }

        Tag tag = Tag.DAO.findTag(projectId, tagName);
        if (tag != null) {
            throw new TagNameExist();
        }

        tag = new Tag();
        tag.set("project", projectId);
        tag.set("name", tagName);
        tag.set("create_time", TimeKit.now());
        boolean success = tag.save();

        if (success) {
            ApiContext apiContext = ApiContext.get();
            User accessUser = apiContext.getAccessUser();
            actionRecordService.record(new CreateTagAction(accessUser, project));
        }

        return tag;
    }

    @Override
    public Tag updateTag(Integer tagId, String tagName) {
        Tag tag = Tag.DAO.findById(tagId);
        if (tag == null) {
            throw new TagNotFound();
        }

        Tag sameNameTag = Tag.DAO.findTag(tag.getStr("project"), tagName);
        if (sameNameTag != null) {
            throw new TagNameExist();
        }

        tag.set("name", tagName);
        tag.update();
        return tag;
    }
}
