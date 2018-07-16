package com.admin.client.service.system;

import com.admin.client.model.system.AdminMenu;
import com.admin.client.plugins.base.BaseService;
import com.admin.client.plugins.base.page.PageResult;

import java.util.List;

/**
 * 菜单Service
 *
 * @author LJ
 * @date 2017年3月15日 下午4:16:51
 */
public interface IAdminMenuService extends BaseService<AdminMenu> {
    /**
     * 根据父菜单ID查询 子菜单分页
     *
     * @param pageIndex 当前页
     * @param pageSize  条数
     * @param parentId  父菜单IDn
     * @return PageResult<AdminMenu>
     */
    PageResult<AdminMenu> queryPage(Integer pageIndex, Integer pageSize, Integer parentId);

    /**
     * 根据父菜单ID查询 子菜单列表
     *
     * @param parentId 父菜单ID
     * @return List<AdminMenu>
     */
    List<AdminMenu> queryChildList(Integer parentId);

    /**
     * 根据menuIds查询菜单
     *
     * @param menuIds 菜单Ids: 1,2,3,4
     * @return List<AdminMenu>
     */
    List<AdminMenu> queryByIds(String menuIds);

    /**
     * 获取menuIdArray的菜单
     *
     * @param menuIdArray
     * @return List<AdminMenu>
     */
    List<AdminMenu> queryPart(String[] menuIdArray);

    /**
     * 查询全部菜单 IDS
     */
    String queryAllIds();

}
