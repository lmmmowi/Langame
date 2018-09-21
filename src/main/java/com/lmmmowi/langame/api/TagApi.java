package com.lmmmowi.langame.api;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.bean.action.CreateTagAction;
import com.lmmmowi.langame.bean.action.DeleteTagAction;
import com.lmmmowi.langame.bean.action.UpdateTagAction;
import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.exception.label.TagNotFoundException;
import com.lmmmowi.langame.model.Project;
import com.lmmmowi.langame.model.Tag;
import com.lmmmowi.langame.model.TagBind;
import com.lmmmowi.langame.model.User;
import com.lmmmowi.langame.service.ActionRecordService;
import com.lmmmowi.langame.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/20
 * @Description:
 */
public class TagApi extends BaseApi {

    @Autowired
    private TagService tagService;
    @Autowired
    private ActionRecordService actionRecordService;

    public void createTag() {
        String projectId = getPara("project_id");
        String tagName = getPara("tag_name");
        Integer userId = getParaToInt("user_id");
        User user = User.DAO.findById(userId);
        Project project = Project.DAO.findById(projectId);
        Tag tag = new Tag();
        tag.set("project_id", projectId);
        tag.set("tag_name", tagName);
        boolean success = tag.save();
        if (success) {
            actionRecordService.record(new CreateTagAction(user, project));
            setAttr("tag", tag);
        }

    }

    public void deleteTag() {
        Integer labelId = getParaToInt("label_id");
        String projectId = getPara("project_id");
        Integer userId = getParaToInt("user_id");
        Tag tag = Tag.DAO.findById(labelId);
        User user = User.DAO.findById(userId);
        Project project = Project.DAO.findById(projectId);
        if (tag != null) {
            boolean success = tag.delete();
            if (success) {
                actionRecordService.record(new DeleteTagAction(user, project));
                setAttr("isSuccess", true);
            }
        } else {
            throw new TagNotFoundException();
        }
    }

    public void updateTag() {
        Integer labelId = getParaToInt("label_id");
        String labelName = getPara("label_name");
        Integer userId = getParaToInt("user_id");
        String projectId = getPara("project_id");
        User user = User.DAO.findById(userId);
        Tag tag = Tag.DAO.findById(labelId);
        Project project = Project.DAO.findById(projectId);
        if (tag != null) {
            tag.set("label_name", labelName);
            boolean success = tag.update();
            if (success) {
                actionRecordService.record(new UpdateTagAction(user, project));
                setAttr("tag", tag);
            }
        } else {
            throw new TagNotFoundException();
        }
    }

    public void getAllTags() {
        Integer pageNumber = getParaToInt("page_number", 1);
        Integer pageSize = getParaToInt("page_size", 10);
        String projectId = getPara("project_id");
        Page<Tag> allLabel = tagService.getAllLabel(projectId, pageSize, pageNumber);
        setPageAttr(allLabel);
    }

    public void getTagByObj() {
        String objectType = getPara("object_type");
        String orbejctRef = getPara("orbejct_ref");
        List<TagBind> tagBind = TagBind.DAO.getByObj(objectType, orbejctRef);
        List<Tag> tags = new ArrayList<>();
        tagBind.forEach(bindObj -> {
            Integer tagId = bindObj.getInt("tag_id");
            Tag tag = Tag.DAO.findById(tagId);
            tags.add(tag);
        });
        setAttr("tags", tags);
    }
}
