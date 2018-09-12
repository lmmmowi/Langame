package com.lmmmowi.langame.api;

import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.enums.NodeType;
import com.lmmmowi.langame.exception.pathnode.PathNodeNotFound;
import com.lmmmowi.langame.model.PathNode;
import com.lmmmowi.langame.service.PathNodeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public class PathNodeApi extends BaseApi {

    @Autowired
    PathNodeService pathNodeService;

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

    public void rename() {
        Integer nodeId = getParaToInt("id");
        String name = getPara("name");

        PathNode pathNode = pathNodeService.rename(nodeId, name);
        setAttr("node", pathNode);
    }

    public void setDescription() {
        Integer nodeId = getParaToInt("id");
        String description = getPara("description");

        PathNode pathNode = PathNode.DAO.findById(nodeId);
        if (pathNode == null) {
            throw new PathNodeNotFound();
        }

        pathNode.set("description", description);
        pathNode.update();
        setAttr("node", pathNode);
    }

    public void listChildren() {
        Integer nodeId = getParaToInt("id");
        List<PathNode> children = PathNode.DAO.findByParent(nodeId);
        setAttr("nodes", children);
    }

}
