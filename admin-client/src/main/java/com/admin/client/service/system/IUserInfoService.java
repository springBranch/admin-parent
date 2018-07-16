package com.admin.client.service.system;

import com.admin.client.model.system.UserInfo;
import com.admin.client.plugins.base.BaseService;
import com.admin.client.plugins.base.page.PageResult;

import java.util.List;
import java.util.Map;

/**
 * 用户Service
 *
 * @author songj
 * @date 2017年3月15日 上午11:39:10
 */
public interface IUserInfoService extends BaseService<UserInfo> {
    /**
     * 用户登录查询
     *
     * @param userMobile 手机号
     * @param userPwd    密码
     * @return UserInfo 用户DO
     */
    UserInfo queryForLogin(String userMobile, String userPwd);

    /**
     * 根据角色名查询用户列表
     *
     * @param roleName 角色名
     * @return List<UserInfo> 用户列表
     */
    List<UserInfo> queryByRoleName(String roleName);

    /**
     * 根据角色名List查询用户列表
     *
     * @param roleNames 角色名List
     * @return List<UserInfo> 用户列表
     */
    List<UserInfo> queryByRoleNames(List<String> roleNames);

    /**
     * 根据角色ID查询用户列表
     *
     * @param roleId 角色ID
     * @return List<UserInfo> 	用户列表
     */
    List<UserInfo> queryByRoleId(Integer roleId);

    /**
     * 根据上级ID查询用户列表
     *
     * @param creatorId 上级ID
     * @return List<UserInfo> 户列表
     */
    List<UserInfo> queryByCreator(Integer creatorId);

    /**
     * 根据手机号查询用户列表
     *
     * @param mobile 手机号(模糊查询)
     * @return List<UserInfo>  户列表
     */
    List<UserInfo> queryByMobile(String mobile);

    /**
     * 根据上级ID查询子用户ID列表
     *
     * @param pointId 上级ID
     * @return List<Integer> 子用户ID列表
     */
    List<Integer> queryAllSubTree(Integer pointId);

    /**
     * 分页查询用户列表
     *
     * @param pageIndex
     * @param pageSize
     * @param map       (必填,条件为UserInfo属性,条件可为空)
     * @return PageResult<UserInfo> 分页结果用户列表
     */
    PageResult<UserInfo> queryPage(Integer pageIndex, Integer pageSize,
                                   Map<String, Object> map);

    /**
     * 条件查询列表
     *
     * @param map
     * @return List<RoleInfo>
     */
    List<UserInfo> queryList(Map<String, Object> map);


}