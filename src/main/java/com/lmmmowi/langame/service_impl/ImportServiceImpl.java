package com.lmmmowi.langame.service_impl;

import com.alibaba.fastjson.JSONObject;
import com.lmmmowi.langame.exception.pathnode.PathNodeNotFoundException;
import com.lmmmowi.langame.model.PathNode;
import com.lmmmowi.langame.service.ImportService;
import com.lmmmowi.langame.service_impl.imports.BaseImporter;
import com.lmmmowi.langame.service_impl.imports.JsonImporter;
import org.springframework.stereotype.Service;

/**
 * @Author: mowi
 * @Date: 2018/9/25
 * @Description:
 */
@Service
public class ImportServiceImpl implements ImportService {

    @Override
    public void importFromJson(Integer parentNodeId, String language, JSONObject json) {
        PathNode parentNode = PathNode.DAO.findById(parentNodeId);
        if (parentNode == null) {
            throw new PathNodeNotFoundException();
        }

        BaseImporter importer = new JsonImporter(json);
        importer.run(parentNode, language);
    }
}
