package com.lmmmowi.langame.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.model.LangEntry;
import com.lmmmowi.langame.model.TagBind;
import com.lmmmowi.langame.service.LangEntryService;
import com.lmmmowi.langame.vo.LangEntryQueryCondition;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: mowi
 * @Date: 2018/9/19
 * @Description:
 */
public class LangEntryApi extends BaseApi {

    @Autowired
    LangEntryService langEntryService;

    public void update() {
        JSONArray entryArray = getParaToJSONArray("entries");
        List<LangEntry> entries = entryArray.stream().map(o -> {
            JSONObject entryJson = (JSONObject) o;
            LangEntry langEntry = new LangEntry();
            langEntry.set("node", entryJson.getInteger("node"));
            langEntry.set("language", entryJson.getString("language"));
            langEntry.set("content", entryJson.getString("content"));
            return langEntry;
        }).collect(Collectors.toList());

        langEntryService.update(entries);
    }

    public void getByNode(){
        Integer nodeId = getParaToInt("node");
        List<LangEntry> entries = LangEntry.DAO.findByNode(nodeId);
        setLangEntriesAttr(entries);
    }

    public void getNodeEntries() {
        String language = getPara("language");

        List<Integer> nodeIds;
        Integer nodeId = getParaToInt("node");
        if (nodeId != null) {
            nodeIds = Arrays.asList(nodeId);
        } else {
            JSONArray nodeIdArray = getParaToJSONArray("nodes");
            nodeIds = nodeIdArray == null ? null : nodeIdArray.stream().map(o -> (Integer) o).collect(Collectors.toList());
        }

        if (nodeIds != null) {
            List<LangEntry> entries = LangEntry.DAO.query(nodeIds, language);
            setLangEntriesAttr(entries);
        }
    }

    public void getEntriesByQuery() {
        Integer pageNumber = getParaToInt("page_number", 1);
        Integer pageSize = getParaToInt("page_size", 10);

        Integer tagId = getParaToInt("tag_id");
        List<TagBind> tagBinds = TagBind.DAO.getByTag(tagId);
        List<Integer> nodeIds = new ArrayList<>();
        LangEntryQueryCondition langEntryQueryCondition = new LangEntryQueryCondition();
        langEntryQueryCondition.setBeginTime(getPara("beginTime"));
        langEntryQueryCondition.setContent(getPara("content"));
        langEntryQueryCondition.setEndTime(getPara("endTime"));
        langEntryQueryCondition.setLanguage(getPara("language"));
        tagBinds.forEach(t-> nodeIds.add(Integer.valueOf(t.getStr("object_ref"))));
        langEntryQueryCondition.setNodeIds(nodeIds);
        Page<LangEntry> langEntryList = langEntryService.getEntriesByQuery(langEntryQueryCondition,pageNumber,pageSize);
        setPageAttr(langEntryList);
    }

    private void setLangEntriesAttr(List<LangEntry> entries) {
        Map<Integer, Map<String, LangEntry>> resultMap = new HashMap<>();

        entries.forEach(langEntry -> {
            Integer nodeId = langEntry.getNodeId();
            Map<String, LangEntry> map = resultMap.get(nodeId);
            if (map == null) {
                map = new HashMap<>();
                resultMap.put(nodeId, map);
            }
            map.put(langEntry.getLanguage(), langEntry);
        });

        setAttr("entries", resultMap);
    }

}
