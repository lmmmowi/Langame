package com.lmmmowi.langame.service_impl.export;

import com.lmmmowi.langame.model.PathNode;

import java.util.List;

/**
 * @Author: mowi
 * @Date: 2018/9/21
 * @Description:
 */
public interface IExporter {

    String export(List<PathNode> pathNodes, String language);

}
