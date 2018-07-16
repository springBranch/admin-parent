package com.admin.service.mapper.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.admin.client.model.system.Dictionary;

import tk.mybatis.mapper.common.Mapper;

/**
 * 数据字典Mapper
 * @author LJ
 * @date 2017年3月17日 上午11:40:24 
 *
 */
public interface DictionaryMapper extends Mapper<Dictionary> {
	/**
	 * 根据dictKey 查询字典列表
	 * @param dictKey
	 * @return List<Dictionary>
	 */
	Dictionary queryByKey(@Param("dictKey")String dictKey);
	/**
	 * 根据dictKey集合 查询字典列表
	 * @param dictKeys
	 * @return List<Dictionary>
	 */
	List<Dictionary> queryByKeys(List<String> dictKeys);
	/**
	 * 条件查询总条数
	 * @param map
	 * @return int
	 */
	int queryCount(Map<String, Object> map);
	/**
	 * 条件查询列表
	 * @param map
	 * @return List<RoleInfo>
	 */
	List<Dictionary> query(Map<String, Object> map);
}