package com.lmmmowi.langame.service;

import com.lmmmowi.langame.enums.NodeType;
import com.lmmmowi.langame.model.PathNode;
import com.lmmmowi.langame.model.Project;

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
}
