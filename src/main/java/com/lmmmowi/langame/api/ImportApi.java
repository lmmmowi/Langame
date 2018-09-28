package com.lmmmowi.langame.api;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import com.lmmmowi.langame.common.BaseApi;
import com.lmmmowi.langame.exception.common.InvalidParameterException;
import com.lmmmowi.langame.service.ImportService;
import com.lmmmowi.langame.util.FileKit;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * @Author: mowi
 * @Date: 2018/9/25
 * @Description:
 */
public class ImportApi extends BaseApi {

    @Autowired
    ImportService importService;

    public void json() {
        UploadFile uploadFile = getFile();
        File file = uploadFile == null ? null : uploadFile.getFile();
        String jsonContent = null;
        try {
            if (file != null) {
                jsonContent = FileKit.readContent(file);
            }
        } finally {
            FileUtil.del(file);
        }

        if (StrKit.isBlank(jsonContent)) {
            throw new InvalidParameterException();
        }

        Integer parentNodeId = getParaToInt("parent");
        String language = getPara("language");
        JSONObject json = JSON.parseObject(jsonContent);

        importService.importFromJson(parentNodeId, language, json);
    }

}
