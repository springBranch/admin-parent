package com.admin.client.service.system;

import com.admin.client.model.system.Dictionary;
import com.admin.client.plugins.base.BaseService;
import com.admin.client.plugins.base.page.PageResult;

import java.util.Map;

/**
 * 数据字典Service
 *
 * @author LJ
 * @date 2017年3月17日 上午11:51:29
 */
public interface IDictionaryService extends BaseService<Dictionary> {
    /**
     * 根据dictKey 查询字典列表
     *
     * @param dictKey
     * @return List<Dictionary>
     */
    String queryValue(String dictKey);

    /**
     * 根据dictKey 查询字典列表
     *
     * @param dictKey
     * @return List<Dictionary>
     */
    Dictionary queryObject(String dictKey);

    /**
     * 根据dictKeyName 分页查询
     *
     * @param map
     * @param pageIndex
     * @param pageSize
     * @return PageResult<Dictionary>
     */
    PageResult<Dictionary> queryPage(Map<String, Object> map, Integer pageIndex, Integer pageSize);


}
