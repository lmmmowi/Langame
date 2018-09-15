package com.lmmmowi.langame.enums;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description:
 */
public enum NodeType {

    // 文件夹节点
    path,

    // 词条节点
    entry;

    public static NodeType parse(String s) {
        try {
            return valueOf(s);
        } catch (Exception e) {
            return null;
        }
    }
}
