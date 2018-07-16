package com.admin.service.mapper.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.admin.client.model.system.AdminMenu;

import tk.mybatis.mapper.common.Mapper;
/**
 * 菜单Mapper 
 * @author LJ
 * @date 2017年3月15日 下午4:16:51 
 *
 */
public interface AdminMenuMapper extends Mapper<AdminMenu> {
	/**
	 * 
	 * 根据父菜单ID 查询 子菜单列表 
	 * @param start  	起始位置
	 * @param length		长度
	 * @param parentId   父菜单ID
	 * @return List<AdminMenu>
	 */
	List<AdminMenu> query(Map<String,Object> map);
	/**
	 * 根据父菜单ID 查询 子菜单 总条数 
	 * @param parentid	父菜单ID
	 * @return int    总条数 
	 */
	int queryCount(Map<String,Object> map);
	/**
	 * 根据menuIds 	查询菜单 
	 * @param menuIds 	菜单Ids: 1,2,3,4
	 * @return List<AdminMenu>   
	 */
	List<AdminMenu> queryByIds(@Param("menuIds") String menuIds);
	/**
	 * 根据菜单ID数组 查询菜单 
	 * @param menuIdArray 菜单ID数组
	 * @return List<AdminMenu> 
	 */
	List<AdminMenu> queryPart(String[] menuIdArray);
	/**
	 * 查询全部菜单 ID
	 */
	List<Integer> queryAllIds();
}