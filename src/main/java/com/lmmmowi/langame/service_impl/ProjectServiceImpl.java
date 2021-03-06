package com.lmmmowi.langame.service_impl;

import com.jfinal.plugin.activerecord.Db;
import com.lmmmowi.langame.bean.action.CreateProjectAction;
import com.lmmmowi.langame.enums.MemberRole;
import com.lmmmowi.langame.model.Member;
import com.lmmmowi.langame.model.Project;
import com.lmmmowi.langame.model.User;
import com.lmmmowi.langame.service.MemberService;
import com.lmmmowi.langame.service.PathNodeService;
import com.lmmmowi.langame.service.ProjectService;
import com.lmmmowi.langame.service.ActionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    MemberService memberService;

    @Autowired
    PathNodeService pathNodeService;

    @Autowired
    ActionRecordService actionRecordService;

    @Override
    public Project create(User user, String name, String defaultLanguage) {
        Project project = new Project();
        project.set("owner", user.getId());
        project.set("name", name);
        project.set("default_language", defaultLanguage);

        boolean success = Db.tx(() -> {
            project.save();
            pathNodeService.createRootNode(project);
            memberService.addMember(project.getId(), user, MemberRole.owner);
            return true;
        });

        if (success) {
            actionRecordService.record(new CreateProjectAction(user, project));
        }

        return project;
    }

    @Override
    public List<Project> listUserProjects(User user) {
        String sql = "SELECT * FROM %s P JOIN %s M ON P.id=M.project WHERE M.user=? ORDER BY M.create_time DESC";
        sql = String.format(sql, Project.DAO.getTable(), Member.DAO.getTable());
        return Project.DAO.find(sql, user.getId());
    }
}
