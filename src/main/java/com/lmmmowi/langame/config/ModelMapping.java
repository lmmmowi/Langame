package com.lmmmowi.langame.config;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.template.source.ClassPathSourceFactory;
import com.lmmmowi.langame.common.BaseModel;
import com.lmmmowi.langame.model.*;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class ModelMapping {

    public static void doMapping(ActiveRecordPlugin arp) {
        arp.addMapping("lg_user", "id", User.class);
        arp.addMapping("lg_project", "id", Project.class);
        arp.addMapping("lg_member", "project,user", Member.class);
        arp.addMapping("lg_path_node", "id", PathNode.class);
        arp.addMapping("lg_lang_entry", "node,language", LangEntry.class);
        arp.addMapping("lg_action_record", "id", ActionRecord.class);
        arp.addMapping("lg_tag", "id", Tag.class);
        arp.addMapping("lg_tag_bind", "tag_id,object_type,object_ref", TagBind.class);

        arp.getEngine().setSourceFactory(new ClassPathSourceFactory());
        arp.addSqlTemplate(getModelSql(PathNode.class));
        arp.addSqlTemplate(getModelSql(LangEntry.class));
        arp.addSqlTemplate(getModelSql(ActionRecord.class));

//        arp.setShowSql(true);
    }

    private static String getModelSql(Class<? extends BaseModel> modelClz) {
        return modelClz.getName().replace(".", "/") + ".sql";
    }
}
