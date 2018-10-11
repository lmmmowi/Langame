package com.lmmmowi.langame.api;

import com.alibaba.fastjson.JSONArray;
import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.cache.LgCache;
import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.enums.NodeType;
import com.lmmmowi.langame.exception.pathnode.PathNodeNotFoundException;
import com.lmmmowi.langame.model.ExportSetting;
import com.lmmmowi.langame.model.PathNode;
import com.lmmmowi.langame.service.PathNodeService;
import com.lmmmowi.langame.service_impl.helper.ExportRenderer;
import com.lmmmowi.langame.vo.PathNodeQueryCondition;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public class PathNodeApi extends BaseApi {

    @Autowired
    private PathNodeService pathNodeService;

    public void createDir() {
        Integer parentNodeId = getParaToInt("parent");
        String name = getPara("name");

        PathNode pathNode = pathNodeService.createNode(parentNodeId, name, NodeType.path);
        setAttr("node", pathNode);
    }

    public void createEntry() {
        Integer parentNodeId = getParaToInt("parent");
        String name = getPara("name");
        PathNode pathNode = pathNodeService.createNode(parentNodeId, name, NodeType.entry);
        setAttr("node", pathNode);
    }

    public void getNode() {
        Integer nodeId = getParaToInt("id");
        PathNode pathNode = PathNode.DAO.findById(nodeId);
        if (pathNode == null) {
            throw new PathNodeNotFoundException();
        }

        Map<Integer, String> completePathMap = LgCache.use(pathNode.getProjectId()).getCache(LgCache.CACHE_COMPLETE_NODE_PATH);
        pathNode.set("complete_path", completePathMap.get(pathNode.getId()));
        setAttr("node", pathNode);
    }

    public void getProjectRootNode() {
        String projectId = getPara("project_id");
        PathNode pathNode = PathNode.DAO.findProjectRootNode(projectId);
        if (pathNode == null) {
            throw new PathNodeNotFoundException();
        }

        Map<Integer, String> completePathMap = LgCache.use(pathNode.getProjectId()).getCache(LgCache.CACHE_COMPLETE_NODE_PATH);
        pathNode.set("complete_path", completePathMap.get(pathNode.getId()));
        setAttr("node", pathNode);
    }

    public void update() {
        Integer nodeId = getParaToInt("id");
        String name = getPara("name");
        String description = getPara("description");

        PathNode pathNode = PathNode.DAO.findById(nodeId);
        if (pathNode == null) {
            throw new PathNodeNotFoundException();
        }

        pathNode = pathNodeService.rename(nodeId, name);
        pathNode.set("description", description);
        pathNode.update();
        setAttr("node", pathNode);
    }

    public void delete() {
        JSONArray nodeIds = getParaToJSONArray("ids");
        for (int i = 0; i < nodeIds.size(); i++) {
            Integer nodeId = nodeIds.getInteger(i);
            pathNodeService.deleteNode(nodeId);
        }
    }

    public void listChildren() {
        Integer nodeId = getParaToInt("id");
        List<PathNode> nodes = PathNode.DAO.findByParent(nodeId);
        setAttr("nodes", nodes);
    }

    public void query() {
        Integer pageNumber = getParaToInt("page_number", 1);
        Integer pageSize = getParaToInt("page_size", 10);

        PathNodeQueryCondition queryCondition = new PathNodeQueryCondition();
        queryCondition.setNodeId(getParaToInt("id"));
        queryCondition.setParentNodeId(getParaToInt("parent"));
        queryCondition.setMatchValue(getPara("match_value"));
        queryCondition.setNodeType(NodeType.parse(getPara("type")));
        queryCondition.setRecursion(getParaToBoolean("recursion", false));

        Page<PathNode> pathNodePage = pathNodeService.query(queryCondition, pageNumber, pageSize);

        List<PathNode> pathNodes = pathNodePage.getList();
        if (pathNodes != null && !pathNodes.isEmpty()) {
            String projectId = pathNodes.get(0).getProjectId();
            ExportSetting exportSetting = ExportSetting.DAO.findDefaultByProject(projectId);

            Map<Integer, String> completePathMap = LgCache.use(projectId).getCache(LgCache.CACHE_COMPLETE_NODE_PATH);
            ExportRenderer exportRenderer = exportSetting == null ? new ExportRenderer(projectId) : new ExportRenderer(exportSetting);

            pathNodes.forEach(node -> {
                node.set("key", exportRenderer.renderKey(node.getId()));
                node.set("complete_path", completePathMap.get(node.getId()));
            });
        }

        setPageAttr(pathNodePage);
    }

    public void getAllEntry() {
        String projectId = getPara("project_id");
        int allEntry = pathNodeService.getAllEntry(projectId);
        setAttr("entryCounts", allEntry);
    }

//    public void test(){
//        try{
//            File file = new File("/Users/mowi/Desktop/temp.json");
//            new Importer(file).run();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}
