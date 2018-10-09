package com.lmmmowi.langame.api;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.enums.TagObjectType;
import com.lmmmowi.langame.model.*;
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

    public void createTag() {
        String projectId = getPara("project_id");
        String tagName = getPara("name");

        Tag tag = tagService.createTag(projectId, tagName);
        setAttr("tag", tag);
    }

    public void deleteTag() {
        Integer tagId = getParaToInt("id");
        Tag.DAO.deleteById(tagId);
    }

    public void updateTag() {
        Integer tagId = getParaToInt("id");
        String tagName = getPara("name");

        Tag tag = tagService.updateTag(tagId, tagName);
        setAttr("tag", tag);
    }

    public void listTags() {
        Integer pageNumber = getParaToInt("page_number", 1);
        Integer pageSize = getParaToInt("page_size", 10);
        String projectId = getPara("project_id");

        Page<Tag> tagPage = Tag.DAO.pageByProject(projectId, pageNumber, pageSize);
        setPageAttr(tagPage);
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

    public void getObjByTag() {
        Integer tagId = getParaToInt("tag_id");
        List<TagBind> tagBinds = TagBind.DAO.getByTag(tagId);
        List<PathNode> pathNodeList = new ArrayList<>();
        List<Project> projectList = new ArrayList<>();
        //遍历每一个tag(tagId相同，type不同)
        tagBinds.forEach(tagBind -> {
            String objectType = tagBind.getStr("object_type");
            TagObjectType tagObjectType = TagObjectType.parse(objectType);
            String objectRef = tagBind.getStr("object_ref");
            if (tagObjectType != null) {
                switch (tagObjectType) {
                    case node:
                        PathNode pathNode = PathNode.DAO.findById(objectRef);
                        pathNodeList.add(pathNode);
                        break;
                    case project:
                        Project project = Project.DAO.findById(objectRef);
                        projectList.add(project);
                        break;
                    default:
                        System.out.println("未匹配");
                }
            }
        });
        setAttr("pathNodeList", pathNodeList);
        setAttr("projectList", projectList);
    }
}
