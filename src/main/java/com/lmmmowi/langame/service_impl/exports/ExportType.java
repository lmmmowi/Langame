package com.lmmmowi.langame.service_impl.exports;

public enum ExportType {

    // JSON
    json("json"),

    // Android
    android("xml"),

    // Properties
    properties("properties"),

    // 自定义
    custom(null);

    private String defaultExtension;

    ExportType(String defaultExtension) {
        this.defaultExtension = defaultExtension;
    }

    public String getDefaultExtension() {
        return defaultExtension;
    }
}
