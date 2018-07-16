package com.admin.service.mapper.system;

import java.util.List;
import java.util.Map;

import com.admin.client.model.system.OperateJournal;

import tk.mybatis.mapper.common.Mapper;
/**
 * 操作日志Mapper 
 * @author LJ
 * @date 2017年3月15日 下午4:16:51 
 *
 */
public interface OperateJournalMapper extends Mapper<OperateJournal> {
	/**
	 * 条件查询总条数
	 * @param map
	 * @return int
	 */
	int queryCount(Map<String, Object> map);
	/**
	 * 条件查询列表
	 * @param map
	 * @return List<OperateJournal>
	 */
	List<OperateJournal> query(Map<String, Object> map);
}