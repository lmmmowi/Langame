package com.lmmmowi.langame.config;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;

import java.io.File;
import java.util.Properties;

/**
 * @Author: mowi
 * @Date: 2018/9/11
 * @Description:
 */
public class LangameConfig {

    public static final String ConfigFileName = "langame-config.properties";

    private final static LangameConfig instance = new LangameConfig();

    private Properties properties;

    public static LangameConfig getInstance() {
        return instance;
    }

    private LangameConfig() {
    }

    public void init() {
        properties = PropKit.use(ConfigFileName).getProperties();
    }

    public String getDefaultNodeConnector() {
        return "/";
    }

    public File getExportDir() {
        String configExportDir = properties.getProperty("export_dir");
        if (StrKit.notBlank(configExportDir)) {
            return new File(configExportDir);
        } else {
            return new File(PathKit.getWebRootPath() + "/export/");
        }
    }

    public String getExportFileBaseUrl() {
        String url = properties.getProperty("export_file_base_url");
        if (StrKit.notBlank(url) && !url.endsWith("/")) {
            url += "/";
        }
        return url;
    }
}
