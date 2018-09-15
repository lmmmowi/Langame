package com.lmmmowi.langame.vo;

import com.lmmmowi.langame.enums.NodeType;

/**
 * @Author: mowi
 * @Date: 2018/9/14
 * @Description:
 */
public class PathNodeQueryCondition {

    private Integer nodeId;
    private Integer parentNodeId;
    private NodeType nodeType;
    private String matchValue;
    private boolean recursion;

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public Integer getParentNodeId() {
        return parentNodeId;
    }

    public void setParentNodeId(Integer parentNodeId) {
        this.parentNodeId = parentNodeId;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getMatchValue() {
        return matchValue;
    }

    public void setMatchValue(String matchValue) {
        this.matchValue = matchValue;
    }

    public boolean isRecursion() {
        return recursion;
    }

    public void setRecursion(boolean recursion) {
        this.recursion = recursion;
    }
}
