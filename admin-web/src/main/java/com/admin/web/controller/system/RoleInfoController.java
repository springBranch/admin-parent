package com.admin.web.controller.system;

import com.admin.client.constant.Constant;
import com.admin.client.constant.RoleInfoEnum;
import com.admin.client.model.system.AdminMenu;
import com.admin.client.model.system.RoleInfo;
import com.admin.client.model.system.UserInfo;
import com.admin.client.service.system.IAdminMenuService;
import com.admin.client.service.system.IRoleInfoService;
import com.admin.client.service.system.IUserInfoService;
import com.admin.client.result.BaseResult;
import com.admin.client.plugins.base.SearchCondition;
import com.admin.client.plugins.base.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 角色管理
 *
 * @author LJ
 * @date 2017年3月17日 上午11:08:00
 */
@Controller
@RequestMapping("/system/roleinfo")
public class RoleInfoController {

    @Autowired
    IUserInfoService iUserInfoService;

    @Autowired
    IRoleInfoService iRoleInfoService;

    @Autowired
    IAdminMenuService iAdminMenuService;

    /**
     * 角色列表
     */
    @RequestMapping("/list")
    public String list(HttpServletRequest request, Integer pageIndex, Integer pageSize, String roleName) {
        pageIndex = pageIndex == null ? 1 : pageIndex;
        pageSize = pageSize == null ? Constant.PAGE_SIZE : pageSize;
        UserInfo loginer = (UserInfo) request.getSession().getAttribute(Constant.SESSION_USER);
        PageResult<RoleInfo> page = iRoleInfoService.queryPage(loginer.getRoleId(), pageIndex,
                pageSize);
        request.setAttribute("page", page);
        request.setAttribute("pageCount", page.getPageCount(pageSize));
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("pageSize", pageSize);
        return "system/role/roleList";
    }

    /**
     * 添加角色页
     */
    @RequestMapping("/toadd")
    public String toadd(HttpServletRequest request) {
        RoleInfo role = (RoleInfo) request.getSession().getAttribute("USER_ROLE");
        List<RoleInfo> roleList = new ArrayList<RoleInfo>();
        if (role != null && !role.getId().equals(RoleInfoEnum.ROLE_INFO_1.getValue())) {
            roleList.add(role);
        }
        List<RoleInfo> list = iRoleInfoService.queryList(role.getId());
        if (list != null && list.size() > 0) {
            roleList.addAll(list);
        }
        request.setAttribute("role", role);
        request.setAttribute("roleList", roleList);
        return "system/role/roleAdd";
    }

    /**
     * 修改角色页
     */
    @RequestMapping("/toedit")
    public String toedit(HttpServletRequest request, @RequestParam Integer id) {
        RoleInfo roleInfo = iRoleInfoService.getById(id);

        List<RoleInfo> list = iRoleInfoService.queryList(roleInfo.getId());
        RoleInfo parentRole = null;
        List<RoleInfo> roleList = new ArrayList<RoleInfo>();
        if (roleInfo.getId().equals(RoleInfoEnum.ROLE_INFO_1.getValue())) {
            //超级管理员
            parentRole = roleInfo;
        } else {
            parentRole = iRoleInfoService.getById(roleInfo.getParentId());
            if (list != null && list.size() > 0) {
                //角色有子角色不允许重新分配上级，防止角色层级死循环
                roleList.add(parentRole);
            } else {
                RoleInfo userRole = (RoleInfo) request.getSession().getAttribute("USER_ROLE");
                if (!userRole.getId().equals(RoleInfoEnum.ROLE_INFO_1.getValue())) {
                    roleList.add(userRole);
                }
                List<RoleInfo> suList = iRoleInfoService.queryList(userRole.getId());
                if (suList != null && suList.size() > 0) {
                    roleList.addAll(suList);
                }
                //去除自身角色，防止角色层级死循环
                for (Iterator<RoleInfo> iterator = roleList.iterator(); iterator.hasNext(); ) {
                    if (iterator.next().getId().equals(roleInfo.getId())) {
                        iterator.remove();
                    }
                }
            }
            request.setAttribute("roleList", roleList);
        }

        request.setAttribute("roleInfo", roleInfo);
        request.setAttribute("parentRole", parentRole);
        return "system/role/roleEdit";
    }

    // 选中已经角色分配的菜单
    public String init(List<AdminMenu> treeMenu, String[] roleId) {
        roleId = roleId == null ? new String[0] : roleId;
        StringBuffer zNodes = new StringBuffer("");
        boolean isFirst = false;
        boolean isChecked = false;
        for (int i = 0; i < treeMenu.size(); i++) {
            isFirst = zNodes.toString().equals("") ? true : false;
            for (int j = 0; j < roleId.length; j++) {
                if (treeMenu.get(i).getId().equals(Integer.parseInt(roleId[j].trim()))) {
                    // checked:true
                    zNodes.append(isFirst ? "[" : ",");
                    zNodes.append("{id:").append(treeMenu.get(i).getId());
                    zNodes.append(", pId:").append(treeMenu.get(i).getParentId());
                    zNodes.append(", name:'").append(treeMenu.get(i).getName()).append("',checked:true}");
                    isChecked = true;
                    break;
                }
            }
            if (!isChecked) {
                zNodes.append(isFirst ? "[" : ",");
                zNodes.append("{id:").append(treeMenu.get(i).getId());
                zNodes.append(", pId:").append(treeMenu.get(i).getParentId());
                zNodes.append(", name:'").append(treeMenu.get(i).getName()).append("'}");
            }
            isChecked = false;
        }
        zNodes.append("]");
        return zNodes.toString();
    }

    /**
     * 菜单列表
     */
    @RequestMapping(value = "/inittree", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult initTree(HttpServletRequest request, @RequestParam Integer roleId, String rIds) {
        RoleInfo role = iRoleInfoService.getById(roleId);
        String menuIds = role.getMenuIds();
        if (role.getId().equals(RoleInfoEnum.ROLE_INFO_1.getValue())) {
            //超级管理员
            menuIds = iAdminMenuService.queryAllIds();
        }
        List<AdminMenu> list = iAdminMenuService.queryByIds(menuIds);
        if (list == null || list.size() == 0) {
            return BaseResult.failed("fail", "无菜单.");
        }
        List<AdminMenu> targetList = menuTreeSort(null, list, true, 0);
        if (targetList == null || targetList.size() == 0) {
            return BaseResult.failed("fail", "无菜单.");
        }
        String[] rId = (rIds == null || rIds == "") ? null : rIds.split(",");
        return new BaseResult(init(targetList, rId));
    }

    /**
     * 添加角色
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult add(@ModelAttribute RoleInfo roleInfo, String[] menuIds) {
        RoleInfo role = iRoleInfoService.getOneByField("roleName", roleInfo.getRoleName());
        if (role != null) {
            return BaseResult.failed("fail", "角色名称已经存在.");
        }

        roleInfo.setMenuIds("");
        if (menuIds != null && menuIds.length > 0) {
            StringBuffer ids = new StringBuffer("");
            boolean isFirst = true; // 第一个元素
            for (String str : menuIds) {
                if (isFirst) {
                    ids.append(str);
                    isFirst = false;
                    continue;
                }
                ids.append(",").append(str);
            }
            roleInfo.setMenuIds(ids.toString());
        }
        iRoleInfoService.save(roleInfo);
        return BaseResult.success();
    }

    /**
     * 修改角色
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult edit(@ModelAttribute RoleInfo roleInfo, String[] menuIds) {
        RoleInfo role = iRoleInfoService.getOneByField("roleName", roleInfo.getRoleName());
        if (role != null && !role.getId().equals(roleInfo.getId())) {
            return BaseResult.failed("fail", "角色名称已经存在.");
        }
        if ((roleInfo.getParentId() == null || roleInfo.getParentId() == 0) && !roleInfo.getId().equals(RoleInfoEnum.ROLE_INFO_1.getValue())) {
            return BaseResult.failed("fail", "请选择上级角色.");
        }
        if (roleInfo.getId().equals(RoleInfoEnum.ROLE_INFO_1.getValue())) {
            roleInfo.setParentId(0);
        }

        if (menuIds != null && menuIds.length > 0) {
            StringBuffer ids = new StringBuffer("");
            boolean isFirst = true; // 第一个元素
            for (String str : menuIds) {
                if (isFirst) {
                    ids.append(str);
                    isFirst = false;
                    continue;
                }
                ids.append(",").append(str);
            }
            roleInfo.setMenuIds(ids.toString());
        }
        iRoleInfoService.updateBySelective(roleInfo);
        return new BaseResult("ok", "修改角色成功");
    }

    /**
     * 删除角色
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult delete(@RequestParam Integer id) {
        SearchCondition condition = SearchCondition.instance().buildEqualConditions("roleId", id);
        List<UserInfo> list = iUserInfoService.getByCondition(condition);
        if (list != null && list.size() > 0) {
            return BaseResult.failed("fail", "不能删除,该角色下还存在用户.");
        }
        iRoleInfoService.deleteById(id);
        return new BaseResult("ok", "删除角色成功");
    }

    private List<AdminMenu> menuTreeSort(ArrayList<AdminMenu> tempList, List<AdminMenu> sourceList, boolean isRootMenu,
                                         int initPid) {
        ArrayList<AdminMenu> targetList = null;
        if (isRootMenu) { // 第一级
            targetList = new ArrayList<AdminMenu>();
            for (int i = 0; i < sourceList.size(); i++) {
                if (sourceList.get(i).getParentId().equals(initPid)) {
                    sourceList.get(i).setMenuPath(sourceList.get(i).getName());
                    targetList.add(sourceList.get(i));
                    sourceList.remove(i);
                    i--;
                }
            }
            isRootMenu = false;
        } else {
            targetList = tempList;
            AdminMenu adminMenu = null;
            for (int i = 0; i < tempList.size(); i++) {
                for (int j = 0; j < sourceList.size(); j++) {
                    adminMenu = sourceList.get(j);
                    if (adminMenu.getParentId().equals(tempList.get(i).getId())) {
                        adminMenu.setMenuPath(tempList.get(i).getMenuPath() + "/" + adminMenu.getName());
                        targetList.add(i + 1, adminMenu);
                        sourceList.remove(j);
                        j--;
                    }
                }
            }
        }
        if (sourceList != null && sourceList.size() > 0) {
            menuTreeSort(targetList, sourceList, isRootMenu, initPid);
        }
        return targetList;
    }

}
