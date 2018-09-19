package com.lmmmowi.langame.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.model.LangEntry;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: mowi
 * @Date: 2018/9/19
 * @Description:
 */
public class LangEntryApi extends BaseApi {

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

        for (LangEntry entry : entries) {
            if (StrKit.isBlank(entry.getStr("content"))) {
                entry.delete();
            } else {
                boolean ok = entry.update();
                if (!ok) {
                    entry.save();
                }
            }
        }
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
            List<LangEntry> entries = LangEntry.DAO.findByNode(nodeIds, language);
            setLangEntriesAttr(entries);
        }
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
