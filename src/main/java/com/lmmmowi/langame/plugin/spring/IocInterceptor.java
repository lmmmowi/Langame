package com.lmmmowi.langame.plugin.spring;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.lmmmowi.langame.common.BaseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;

/**
 * @Author: mowi
 * @Date: 2018/9/12
 * @Description: 
 */
public class IocInterceptor implements Interceptor {

    static ApplicationContext ctx;

    @Override
    public void intercept(Invocation invocation) {
        if (ctx != null) {
            Controller controller = invocation.getController();
            Class clz = controller.getClass();

            while (clz != null && !clz.equals(BaseApi.class)) {
                Field[] fields = clz.getDeclaredFields();
                for (Field field : fields) {
                    Object bean = null;

                    if (field.isAnnotationPresent(Autowired.class)) {
                        String beanName = null;
                        if (field.isAnnotationPresent(Qualifier.class)) {
                            Qualifier qualifier = field.getAnnotation(Qualifier.class);
                            beanName = qualifier.value();
                        }

                        if (StrKit.notBlank(beanName)) {
                            bean = ctx.getBean(beanName);
                        } else {
                            bean = ctx.getBean(field.getType());
                        }
                    }

                    if (bean != null) {
                        try {
                            field.setAccessible(true);
                            field.set(controller, bean);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                clz = clz.getSuperclass();
            }
        }

        invocation.invoke();
    }
}
