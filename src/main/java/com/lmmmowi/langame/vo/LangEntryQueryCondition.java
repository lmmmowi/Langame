package com.lmmmowi.langame.vo;

import java.util.List;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/26
 * @Description: getEntriesByQuery接口VO
 */
public class LangEntryQueryCondition {

    private String language;
    private String content;
    private String beginTime;
    private String endTime;
    private List<Integer> nodeIds;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public List<Integer> getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(List<Integer> nodeIds) {
        this.nodeIds = nodeIds;
    }

}
