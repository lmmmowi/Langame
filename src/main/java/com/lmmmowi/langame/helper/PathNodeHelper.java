package com.lmmmowi.langame.helper;

import com.jfinal.kit.Kv;
import com.jfinal.plugin.activerecord.SqlPara;
import com.lmmmowi.langame.enums.NodeType;
import com.lmmmowi.langame.model.PathNode;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 * @Author: mowi
 * @Date: 2018/9/15
 * @Description:
 */
public class PathNodeHelper {

    public static List<Integer> getRecursionPath(PathNode pathNode) {
        if (NodeType.entry.equals(pathNode.getType())) {
            return null;
        }

        Kv kv = Kv.by("projectId", pathNode.getStr("project"))
                .set("type", NodeType.path.name());

        SqlPara sqlPara = PathNode.DAO.getModelSqlPara("query", kv);
        List<PathNode> nodes = PathNode.DAO.find(sqlPara);
        Map<Integer, List<PathNode>> map = buildParentReferenceMap(nodes);

        Queue<PathNode> queue = new LinkedBlockingQueue<>();
        queue.add(pathNode);

        List<Integer> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            PathNode queueNode = queue.remove();
            result.add(queueNode.getId());

            List<PathNode> list = map.get(queueNode.getId());
            if (list != null) {
                queue.addAll(list);
            }
        }
        return result;
    }

    public static Map<Integer, String> getCompletePathMap(String projectId) {
        Map<Integer, String> pathMap = new HashMap<>();

        List<PathNode> nodes = PathNode.DAO.findByProject(projectId);
        Map<Integer, List<PathNode>> parentReferenceMap = buildParentReferenceMap(nodes);

        Queue<Integer> queue = new LinkedBlockingQueue<>();
        queue.add(0);
        while (!queue.isEmpty()) {
            Integer parentNodeId = queue.remove();
            List<PathNode> children = parentReferenceMap.get(parentNodeId);
            if (children == null) {
                continue;
            }

            for (PathNode child : children) {
                String path = child.getStr("name");
                if (!child.isRootNode()) {
                    path = pathMap.get(parentNodeId) + "/" + path;
                }
                pathMap.put(child.getId(), path);
                queue.add(child.getId());
            }
        }

        return pathMap;
    }

    private static Map<Integer, List<PathNode>> buildParentReferenceMap(List<PathNode> nodes) {
        Map<Integer, List<PathNode>> map = new HashMap<>();
        nodes.forEach(node -> {
            Integer parentId = node.getInt("parent");
            List<PathNode> list = map.get(parentId);
            if (list == null) {
                list = new ArrayList<>();
                map.put(parentId, list);
            }
            list.add(node);
        });
        return map;
    }
}
