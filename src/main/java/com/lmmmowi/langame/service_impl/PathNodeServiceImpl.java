package com.lmmmowi.langame.service_impl;

import com.lmmmowi.langame.enums.NodeType;
import com.lmmmowi.langame.exception.pathnode.DuplicatedPathNode;
import com.lmmmowi.langame.exception.pathnode.PathNodeNotFound;
import com.lmmmowi.langame.model.PathNode;
import com.lmmmowi.langame.model.Project;
import com.lmmmowi.langame.service.PathNodeService;
import org.springframework.stereotype.Service;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
@Service
public class PathNodeServiceImpl implements PathNodeService {

    @Override
    public PathNode createRootNode(Project project) {
        PathNode pathNode = new PathNode();
        pathNode.set("project", project.getId());
        pathNode.set("name", "root");
        pathNode.set("parent", PathNode.ROOT_NODE_ID);
        pathNode.set("type", NodeType.path.name());
        pathNode.save();
        return pathNode;
    }

    @Override
    public PathNode createNode(Integer parentNodeId, String name, NodeType nodeType) {
        PathNode parentNode = PathNode.DAO.findById(parentNodeId);
        return createNode(parentNode, name, nodeType);
    }

    @Override
    public PathNode createNode(PathNode parentNode, String name, NodeType nodeType) {
        if (parentNode == null) {
            throw new PathNodeNotFound();
        }

        Integer parentNodeId = parentNode.getId();
        PathNode pathNode = PathNode.DAO.findNode(parentNodeId, name, nodeType);
        if (pathNode != null) {
            throw new DuplicatedPathNode();
        }

        pathNode = new PathNode();
        pathNode.set("project", parentNode.getStr("project"));
        pathNode.set("name", name);
        pathNode.set("parent", parentNodeId);
        pathNode.set("type", nodeType.name());
        pathNode.save();
        return pathNode;
    }

    @Override
    public PathNode rename(Integer nodeId, String name) {
        PathNode pathNode = PathNode.DAO.findById(nodeId);
        if (pathNode == null || pathNode.isRootNode()) {
            throw new PathNodeNotFound();
        }

        pathNode.set("name", name);
        pathNode.update();
        return pathNode;
    }
}
