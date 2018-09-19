package com.lmmmowi.langame.routes;

import com.jfinal.config.Routes;
import com.lmmmowi.langame.api.LangEntryApi;
import com.lmmmowi.langame.api.PathNodeApi;
import com.lmmmowi.langame.api.ProjectApi;
import com.lmmmowi.langame.api.UserApi;
import com.lmmmowi.langame.model.LangEntry;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class ApiRoutes extends Routes {

    @Override
    public void config() {
        add("api/user", UserApi.class);
        add("api/project", ProjectApi.class);
        add("api/node", PathNodeApi.class);
        add("api/entry", LangEntryApi.class);
    }
}
