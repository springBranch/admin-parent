package com.admin.client.service.system;


import com.admin.client.model.system.RoleInfo;
import com.admin.client.plugins.base.BaseService;
import com.admin.client.plugins.base.page.PageResult;

import java.util.List;

/**
 * 系统角色Service
 *
 * @author songj
 * @date 2017年3月17日 上午11:23:25
 */
public interface IRoleInfoService extends BaseService<RoleInfo> {
    /**
     * 根据角色名查询角色列表(模糊查询)
     *
     * @param roleName 角色名
     * @return List<RoleInfo> 角色列表
     */
    List<RoleInfo> queryByRoleName(String roleName);

    /**
     * 分页查询角色列表（递归查询子角色）
     *
     * @param roleId    角色ID
     * @param pageIndex
     * @param pageSize
     * @return PageResult<RoleInfo> 角色列表分页结果
     */
    PageResult<RoleInfo> queryPage(Integer roleId, Integer pageIndex, Integer pageSize);

    /**
     * 查询角色列表（递归查询子角色）
     *
     * @param roleId 角色ID
     * @return List<RoleInfo>
     */
    List<RoleInfo> queryList(Integer roleId);

}