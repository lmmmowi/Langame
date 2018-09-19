package com.lmmmowi.langame.api;

import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.common.RequireSignin;
import com.lmmmowi.langame.model.Project;
import com.lmmmowi.langame.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class ProjectApi extends BaseApi {

    @Autowired
    private ProjectService projectService;

    @RequireSignin
    public void create() {
        String name = getPara("name");
        String defaultLanguage = getPara("default_language");
        Project project = projectService.create(accessUser, name, defaultLanguage);
        setAttr("project", project);
    }

    @RequireSignin
    public void listUserProjects() {
        List<Project> projects = projectService.listUserProjects(accessUser);
        setAttr("projects", projects);
    }
}
