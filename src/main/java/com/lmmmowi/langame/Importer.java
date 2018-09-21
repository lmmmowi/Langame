package com.lmmmowi.langame;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import com.lmmmowi.langame.enums.NodeType;
import com.lmmmowi.langame.model.LangEntry;
import com.lmmmowi.langame.model.PathNode;
import com.lmmmowi.langame.service.LangEntryService;
import com.lmmmowi.langame.service.PathNodeService;
import com.lmmmowi.langame.service_impl.LangEntryServiceImpl;
import com.lmmmowi.langame.service_impl.PathNodeServiceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * @Author: mowi
 * @Date: 2018/9/21
 * @Description:
 */
public class Importer {

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/mowi/Desktop/temp.json");
        new Importer(file).run();
//        String a = new Importer(file).formatLanguage("zh_cn");
//        System.out.println(a);
    }

    private PathNodeService pathNodeService = new PathNodeServiceImpl();
    private LangEntryService langEntryService = new LangEntryServiceImpl();
    private File file;

    public Importer(File file) {
        this.file = file;
    }

    public void run() throws Exception {
        String s = readFile();
        JSONObject json = JSONObject.parseObject(s);
        handle("", json);
    }

    private void handle(String prefix, JSONObject json) {
        Set<String> keys = json.keySet();
        boolean isNode = keys.contains("zh_cn") || keys.contains("en_us");

        if (isNode) {
            handleNode(prefix, json);
        } else {
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                JSONObject item = json.getJSONObject(key);
                handle(prefix + "/" + key, item);
            }
        }

    }

    private void handleNode(String prefix, JSONObject json) {
        PathNode parentNode = PathNode.DAO.findById(63);

        String[] parts = prefix.split("/");
        for (int i = 0; i < parts.length; i++) {
            String name = parts[i];
            if (StrKit.isBlank(name)) {
                continue;
            }

            if (i == parts.length - 1) {
                System.out.println(parentNode + "   " + name);
                // 词条
                PathNode node = pathNodeService.createNode(parentNode, name, NodeType.entry);

                List<LangEntry> langEntries = new ArrayList<>();
                Set<String> keys = json.keySet();
                Iterator<String> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String language = formatLanguage(key);

                    String content = json.get(key).toString();

                    LangEntry langEntry = new LangEntry();
                    langEntry.set("node", node.getId());
                    langEntry.set("language", language);
                    langEntry.set("content", content);

                    langEntries.add(langEntry);
                }

                langEntryService.update(langEntries);
            } else {
                parentNode = findNode(parentNode, name);
            }

        }
    }

    private Map<String, PathNode> nodeMap = new HashMap<>();

    private PathNode findNode(PathNode parentNode, String name) {
        String key = parentNode.getId() + "," + name;
        PathNode node = nodeMap.get(key);
        if (node == null) {
            node = PathNode.DAO.findByParent(parentNode.getId(), name);

            if (node == null) {
                node = pathNodeService.createNode(parentNode, name, NodeType.path);
            }
            nodeMap.put(key, node);
        }
        return node;
    }

    private String readFile() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        try {
            StringBuilder stringBuilder = new StringBuilder();
            String s;
            while ((s = reader.readLine()) != null) {
                stringBuilder.append(s).append("\n");
            }
            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    private String formatLanguage(String s) {
        int index = s.indexOf("_");
        return s.substring(0, index) + "-" + s.substring(index + 1, s.length()).toUpperCase();
    }


}
