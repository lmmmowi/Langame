package com.lmmmowi.langame.enums;

/**
 * @Author: Braydenwong
 * @Date: 2018/9/26
 * @Description: 待补充...
 */
public enum TagObjectType {

    /**
     * 节点
     */
    node,

    /**
     * 项目
     */
    project;

    public static TagObjectType parse(String s) {
        try {
            return valueOf(s);
        } catch (Exception e) {
            return null;
        }
    }
}
