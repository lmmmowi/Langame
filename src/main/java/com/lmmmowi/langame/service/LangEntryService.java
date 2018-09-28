package com.lmmmowi.langame.service;

import com.jfinal.plugin.activerecord.Page;
import com.lmmmowi.langame.model.LangEntry;
import com.lmmmowi.langame.vo.LangEntryQueryCondition;

import java.util.List;

/**
 * @Author: mowi
 * @Date: 2018/9/20
 * @Description:
 */
public interface LangEntryService {

    void update(List<LangEntry> entries);

    Page<LangEntry> getEntriesByQuery(LangEntryQueryCondition langEntryQueryCondition, Integer pageNumber, Integer pageSize);

}
