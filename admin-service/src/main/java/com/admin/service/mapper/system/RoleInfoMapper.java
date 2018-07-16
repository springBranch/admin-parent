package com.admin.service.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.admin.client.model.system.RoleInfo;

import tk.mybatis.mapper.common.Mapper;
/**
 * 系统角色Mapper
 * @author LJ
 * @date 2017年3月17日 上午11:31:46 
 *
 */
public interface RoleInfoMapper extends Mapper<RoleInfo> {
	/**
	 * 根据ID查询角色
	 * @param id
	 * @return List<RoleInfo>
	 */
	RoleInfo queryById(@Param("id") Integer id);
	/**
	 * 根据父ID查询子角色列表
	 * @param parentId	父ID
	 * @return List<RoleInfo>
	 */
	List<RoleInfo> queryByParentId(@Param("parentId") Integer parentId);
}