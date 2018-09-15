package com.lmmmowi.langame.service;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.enums.NodeType;
import com.lmmmowi.langame.model.PathNode;
import com.lmmmowi.langame.model.Project;
import com.lmmmowi.langame.vo.PathNodeQueryCondition;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public interface PathNodeService {

    PathNode createRootNode(Project project);

    PathNode createNode(Integer parentNodeId, String name, NodeType nodeType);

    PathNode createNode(PathNode parentNode, String name, NodeType nodeType);

    PathNode rename(Integer nodeId, String name);

    PathNode rename(PathNode pathNode, String name);

    Page<PathNode> query(PathNodeQueryCondition condition, int pageNumber, int pageSize);

    boolean deleteNode(Integer nodeId);

    boolean deleteNode(PathNode pathNode);
}
