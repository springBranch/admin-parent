package com.admin.service.mapper.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.admin.client.model.system.UserInfo;

import tk.mybatis.mapper.common.Mapper;
/**
 * 用户Mapper
 * @author LJ
 * @date 2017年3月17日 上午11:33:54 
 *
 */
public interface UserInfoMapper extends Mapper<UserInfo> {
	/**
	 * 用户登录查询
	 * @param mobile 手机号
	 * @param pwd	 密码
	 * @return UserInfo 用户DO
	 */
	UserInfo queryForLogin(@Param("userMobile")String userMobile, @Param("userPwd")String userPwd);
	/**
	 * 根据角色名查询用户列表
	 * @param roleName	角色名
	 * @return List<UserInfo> 用户列表
	 */
	List<UserInfo> queryByRoleName(@Param("roleName") String roleName);
	/**
	 * 根据角色名List查询用户列表
	 * @param roleNames	角色名List
	 * @return List<UserInfo> 用户列表
	 */
	List<UserInfo> queryByRoleNames(List<String> roleNames);
	/**
	 * 根据父ID集合查询子ID列表
	 * @param parentIds	父ID集合
	 * @return List<Integer> 子ID列表
	 */
	List<Integer> queryByParentIds(List<Integer> parentIds);
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
	List<UserInfo> query(Map<String, Object> map);
	/**
	 * 条件查询列表
	 * @param map
	 * @return List<RoleInfo>
	 */
	List<UserInfo> queryList(Map<String, Object> map);
}