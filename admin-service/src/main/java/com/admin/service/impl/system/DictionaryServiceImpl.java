package com.admin.service.impl.system;

import com.admin.client.model.system.Dictionary;
import com.admin.client.plugins.base.BaseServiceImpl;
import com.admin.client.plugins.base.page.PageResult;
import com.admin.client.service.system.IDictionaryService;
import com.admin.client.utils.PageUtil;
import com.admin.service.mapper.system.DictionaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary> implements IDictionaryService {

    @Autowired
    private DictionaryMapper dictionaryMapper;

    @Override
    public String queryValue(String dictKey) {
        Dictionary dictionary = queryObject(dictKey);

        if (dictionary == null) {
            return null;
        }

        return dictionary.getDictValue();
    }

    @Override
    public Dictionary queryObject(String dictKey) {
        return dictionaryMapper.queryByKey(dictKey);
    }

    @Override
    public PageResult<Dictionary> queryPage(Map<String, Object> map, Integer pageIndex, Integer pageSize) {

        int total = dictionaryMapper.queryCount(map);
        if (pageIndex != null && pageSize != null) {
            int start = PageUtil.calcStart(pageIndex, pageSize, total);
            map.put("start", start);
            map.put("length", pageSize);
        }
        List<Dictionary> list = dictionaryMapper.query(map);
        PageResult<Dictionary> result = new PageResult<Dictionary>();
        result.setRows(list);
        result.setTotal(new Long(total));
        return result;
    }
}
