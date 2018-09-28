package com.lmmmowi.langame.bean.action;

import com.lmmmowi.langame.cache.LgCache;
import com.lmmmowi.langame.enums.ActionType;
import com.lmmmowi.langame.model.*;

import java.util.Map;

/**
 * @Author: mowi
 * @Date: 2018/9/20
 * @Description:
 */
public class ActionParser {

    private ActionRecord actionRecord;

    public ActionParser(ActionRecord actionRecord) {
        this.actionRecord = actionRecord;
    }

    public IAction parseAction() {
        ActionType actionType = actionRecord.getActionType();

        switch (actionType) {
            case CREATE_PROJECT:
                return new CreateProjectAction(parseSubjectUser(), parseTargetProject());
            case MEMBER_JOIN:
                return new MemberJoinAction(parseSubjectUser(), parseTargetProject());
            case CREATE_NODE:
                return new CreateNodeAction(parseSubjectUser(), parseTargetPathNode());
            case RENAME_NODE:
                return new RenameNodeAction(parseSubjectUser(), parseTargetPathNode());
            case UPDATE_LANG_ENTRY:
                return new UpdateLangEntryAction(parseSubjectUser(), parseTargetLangEntry(), null);
        }

        return null;
    }

    private User parseSubjectUser() {
        Integer userId = Integer.valueOf(actionRecord.getSubject());
        User user = User.DAO.findById(userId);
        if (user != null) {
            user.remove("password");
        }
        return user;
    }

    private Project parseTargetProject() {
        return Project.DAO.findById(actionRecord.getTarget());
    }

    private PathNode parseTargetPathNode() {
        Integer nodeId = Integer.valueOf(actionRecord.getTarget());
        PathNode pathNode = PathNode.DAO.findById(nodeId);
        Map<Integer, String> completePathMap = LgCache.use(pathNode.getProjectId()).getCache(LgCache.CACHE_COMPLETE_NODE_PATH);
        pathNode.set("complete_path", completePathMap.get(pathNode.getId()));
        return pathNode;
    }

    private LangEntry parseTargetLangEntry() {
        String[] arr = actionRecord.getTarget().split(",");
        Integer nodeId = Integer.valueOf(arr[0]);
        PathNode pathNode = PathNode.DAO.findById(nodeId);
        if (pathNode != null) {
            Map<Integer, String> completePathMap = LgCache.use(pathNode.getProjectId()).getCache(LgCache.CACHE_COMPLETE_NODE_PATH);
            pathNode.set("complete_path", completePathMap.get(pathNode.getId()));
        }

        LangEntry langEntry = new LangEntry();
        langEntry.set("node", pathNode);
        langEntry.set("language", arr[1]);
        return langEntry;
    }
}
