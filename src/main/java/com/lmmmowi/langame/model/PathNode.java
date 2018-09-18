package com.lmmmowi.langame.model;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.common.BaseModel;
import com.lmmmowi.langame.enums.NodeType;

import java.util.List;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public class PathNode extends BaseModel<PathNode> {

    public static final Integer ROOT_NODE_ID = 0;

    public static final PathNode DAO = new PathNode();

    @Override
    public Integer getId() {
        return super.getId();
    }

    public boolean isRootNode() {
        return ROOT_NODE_ID.equals(getInt("parent"));
    }

    public String getProjectId(){
        return getStr("project");
    }

    public NodeType getType(){
        return NodeType.valueOf(getStr("type"));
    }

    public PathNode findNode(Integer parentNodeId, String name, NodeType nodeType) {
        String sql = String.format("SELECT * FROM %s WHERE parent=? AND name=? AND type=?", getTable());
        return findFirst(sql, parentNodeId, name, nodeType.name());
    }

    public List<PathNode> findByParent(Integer nodeId) {
        String sql = String.format("SELECT * FROM %s WHERE parent=? ORDER BY type DESC, name", getTable());
        return find(sql, nodeId);
    }

    public List<PathNode> findByProject(String projectId) {
        String sql = String.format("SELECT * FROM %s WHERE project=?", getTable());
        return find(sql, projectId);
    }

    public PathNode findProjectRootNode(String projectId) {
        String sql = String.format("SELECT * FROM %s WHERE project=? AND parent=0", getTable());
        return findFirst(sql, projectId);
    }

    public Page<PathNode> findByParent(Integer nodeId, int pageNumber, int pageSize) {
        String sql = String.format("FROM %s WHERE parent=? ORDER BY type DESC, name", getTable());
        return paginate(pageNumber, pageSize, "SELECT *", sql, nodeId);
    }
}
