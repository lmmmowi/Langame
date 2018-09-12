package com.lmmmowi.langame.service;

import com.lmmmowi.langame.model.Project;
import com.lmmmowi.langame.model.User;

import java.util.List;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public interface ProjectService {

    Project create(User user, String name, String defaultLanguage);

    List<Project> listUserProjects(User user);
}
