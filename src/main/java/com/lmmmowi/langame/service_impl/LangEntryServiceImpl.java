package com.lmmmowi.langame.service_impl;

import com.jfinal.kit.Kv;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.lmmmowi.langame.bean.action.UpdateLangEntryAction;
import com.lmmmowi.langame.interceptor.ApiContext;
import com.lmmmowi.langame.model.LangEntry;
import com.lmmmowi.langame.model.PathNode;
import com.lmmmowi.langame.model.User;
import com.lmmmowi.langame.service.ActionRecordService;
import com.lmmmowi.langame.service.LangEntryService;
import com.lmmmowi.langame.vo.LangEntryQueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: mowi
 * @Date: 2018/9/20
 * @Description:
 */
@Service
public class LangEntryServiceImpl implements LangEntryService {

    @Autowired
    ActionRecordService actionRecordService;

    @Override
    public void update(List<LangEntry> entries) {
        ApiContext apiContext = ApiContext.get();
        User accessUser = apiContext.getAccessUser();

        for (LangEntry entry : entries) {
            if (StrKit.isBlank(entry.getStr("content"))) {
                entry.delete();
            } else {
                boolean ok = entry.update();
                if (!ok) {
                    entry.save();
                }
            }

            PathNode pathNode = PathNode.DAO.findById(entry.getNodeId());
            String projectId = pathNode.getStr("project");
            actionRecordService.record(new UpdateLangEntryAction(accessUser, entry, projectId));
        }
    }

    @Override
    public Page<LangEntry> getEntriesByQuery(LangEntryQueryCondition langEntryQueryCondition, Integer pageNumber, Integer pageSize) {
        Kv kv = Kv.by("content", langEntryQueryCondition.getContent());
        kv.set("beginTime", langEntryQueryCondition.getBeginTime())
                .set("endTime", langEntryQueryCondition.getEndTime())
                .set("language", langEntryQueryCondition.getLanguage())
                .set("nodeIds", langEntryQueryCondition.getNodeIds());
        SqlPara sqlPara = LangEntry.DAO.getModelSqlPara("queryUnion", kv);
        return LangEntry.DAO.paginate(pageNumber, pageSize, sqlPara);
    }
}
