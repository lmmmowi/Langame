package com.lmmmowi.langame.service_impl;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.SqlPara;
import com.lmmmowi.langame.bean.action.CreateNodeAction;
import com.lmmmowi.langame.bean.action.RenameNodeAction;
import com.lmmmowi.langame.enums.NodeType;
import com.lmmmowi.langame.exception.pathnode.DuplicatedPathNodeException;
import com.lmmmowi.langame.exception.pathnode.PathNodeNotFoundException;
import com.lmmmowi.langame.helper.PathNodeHelper;
import com.lmmmowi.langame.interceptor.ApiContext;
import com.lmmmowi.langame.model.PathNode;
import com.lmmmowi.langame.model.Project;
import com.lmmmowi.langame.service.ActionRecordService;
import com.lmmmowi.langame.service.PathNodeService;
import com.lmmmowi.langame.service.TagService;
import com.lmmmowi.langame.vo.PathNodeQueryCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
@Service
public class PathNodeServiceImpl implements PathNodeService {

    private static final String ENTRY = "entry";

    @Autowired
    ActionRecordService actionRecordService;

    @Autowired
    private ActionRecordService userRecordService;

    @Autowired
    private TagService tagService;

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
        PathNode pathNode = this.thisCreateNode(parentNode, name, nodeType);
        boolean success = pathNode.save();
        if (success) {
            ApiContext apiContext = ApiContext.get();
            actionRecordService.record(new CreateNodeAction(apiContext.getAccessUser(), parentNode));
        }
        return pathNode;
    }

    private PathNode thisCreateNode(PathNode parentNode, String name, NodeType nodeType) {
        if (parentNode == null) {
            throw new PathNodeNotFoundException();
        }
        Integer parentNodeId = parentNode.getId();
        PathNode pathNode = PathNode.DAO.findNode(parentNodeId, name, nodeType);
        if (pathNode != null) {
            throw new DuplicatedPathNodeException();
        }
        pathNode = new PathNode();
        pathNode.set("project", parentNode.getStr("project"));
        pathNode.set("name", name);
        pathNode.set("parent", parentNodeId);
        pathNode.set("type", nodeType.name());
        return pathNode;
    }

    @Override
    public PathNode rename(Integer nodeId, String name) {
        PathNode pathNode = PathNode.DAO.findById(nodeId);
        return rename(pathNode, name);
    }

    @Override
    public PathNode rename(PathNode pathNode, String name) {
        if (pathNode == null || pathNode.isRootNode()) {
            throw new PathNodeNotFoundException();
        }

        PathNode existNode = PathNode.DAO.findNode(pathNode.get("parent"), pathNode.get("name"), pathNode.getType());
        if (existNode != null && !existNode.getId().equals(pathNode.getId())) {
            throw new DuplicatedPathNodeException();
        }

        pathNode.set("name", name);
        boolean success = pathNode.update();

        if (success) {
            // 记录行为
            ApiContext apiContext = ApiContext.get();
            userRecordService.record(new RenameNodeAction(apiContext.getAccessUser(), pathNode));
        }

        return pathNode;
    }

    @Override
    public Page<PathNode> query(PathNodeQueryCondition condition, int pageNumber, int pageSize) {
        // Name匹配串
        Kv kv = Kv.by("matchValue", condition.getMatchValue());

        // 节点ID
        if (condition.getNodeId() != null) {
            PathNode pathNode = PathNode.DAO.findById(condition.getNodeId());
            if (pathNode != null) {
                String projectId = pathNode.getStr("project");
                kv.set("nodeId", condition.getNodeId()).set("projectId", projectId);
            }
        }

        // 父节点ID
        if (condition.getParentNodeId() != null) {
            PathNode pathNode = PathNode.DAO.findById(condition.getParentNodeId());
            if (pathNode != null) {
                String projectId = pathNode.getStr("project");
                kv.set("projectId", projectId);

                // 递归
                if (condition.isRecursion()) {
                    System.out.println("aa");
                    List<Integer> dirIds = PathNodeHelper.getRecursionPath(pathNode);
                    System.out.println(dirIds);
                    kv.set("parentNodeIds", dirIds);
                }
                // 只获取一级目录
                else {
                    System.out.println("bb");
                    kv.set("parentNodeId", condition.getParentNodeId());
                }
            }
        }

        // 节点类型
        NodeType nodeType = condition.getNodeType();
        String type = nodeType == null ? null : nodeType.name();
        kv.set("type", type);


        SqlPara sqlPara = PathNode.DAO.getModelSqlPara("query", kv);
        return PathNode.DAO.paginate(pageNumber, pageSize, sqlPara);
    }

    @Override
    public boolean deleteNode(Integer nodeId) {
        PathNode pathNode = PathNode.DAO.findById(nodeId);
        return deleteNode(pathNode);
    }

    @Override
    public boolean deleteNode(PathNode pathNode) {
        if (pathNode == null) {
            return true;
        }

        if (NodeType.entry.equals(pathNode.getType())) {
            // TODO 删除LangEntry
            pathNode.delete();
        } else {
            // 递归删除子节点
            List<PathNode> pathNodes = PathNode.DAO.findByParent(pathNode.getId());
            for (PathNode childNode : pathNodes) {
                deleteNode(childNode);
            }
            pathNode.delete();
        }
        return true;
    }

    @Override
    public int getAllEntry(String projectId) {
        return PathNode.DAO.getAllEntry(projectId);
    }

}
