package com.lmmmowi.langame.routes;

import com.jfinal.config.Routes;
import com.lmmmowi.langame.api.LangEntryApi;
import com.lmmmowi.langame.api.PathNodeApi;
import com.lmmmowi.langame.api.ProjectApi;
import com.lmmmowi.langame.api.UserApi;
import com.lmmmowi.langame.api.*;

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
        add("api/member", MemberApi.class);
        add("api/tag", TagApi.class);
        add("api/action", ActionRecordApi.class);
        add("api/export", ExportApi.class);
        add("api/import", ImportApi.class);
    }
}
