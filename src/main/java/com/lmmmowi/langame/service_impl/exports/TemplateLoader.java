package com.lmmmowi.langame.service_impl.exports;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.core.resource.FileResourceLoader;
import org.beetl.core.resource.StringTemplateResourceLoader;

/**
 * @Author: mowi
 * @Date: 2018/9/21
 * @Description:
 */
public class TemplateLoader {

    public static final String CHARSET = "utf-8";

    public static Template fromString(String templateValue) {
        try {
            StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
            Configuration cfg = Configuration.defaultConfiguration();
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            Template t = gt.getTemplate(templateValue);
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    public static Template fromFile(String root, String templateKey) {
        try {
            FileResourceLoader resourceLoader = new FileResourceLoader(root, CHARSET);
            Configuration cfg = Configuration.defaultConfiguration();
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            Template t = gt.getTemplate(templateKey);
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    public static Template fromClasspath(String root, String templateKey) {
        try {
            ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader(root, CHARSET);
            Configuration cfg = Configuration.defaultConfiguration();
            GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
            Template t = gt.getTemplate(templateKey);
            return t;
        } catch (Exception e) {
            return null;
        }
    }
}
