package com.admin.web.controller.system;

import com.admin.client.constant.Constant;
import com.admin.client.model.system.AdminMenu;
import com.admin.client.service.system.IAdminMenuService;
import com.admin.client.result.BaseResult;
import com.admin.client.plugins.base.page.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 菜单管理
 *
 * @author LJ
 * @date 2017年3月17日 上午11:06:56
 */
@Controller
@RequestMapping("/system/adminmenu")
public class AdminMenuController {

    @Autowired
    IAdminMenuService iAdminMenuService;

    /**
     * 菜单列表
     */
    @RequestMapping("/list")
    public String list(HttpServletRequest request, Integer pageIndex, Integer pageSize, Integer parentId) {
        pageIndex = (pageIndex == null || pageIndex == 0) ? 1 : pageIndex;
        pageSize = (pageSize == null || pageSize == 0) ? Constant.PAGE_SIZE : pageSize;
        parentId = parentId == null ? 0 : parentId;
        PageResult<AdminMenu> page = iAdminMenuService.queryPage(pageIndex, pageSize, parentId);
        request.setAttribute("page", page);
        request.setAttribute("pageCount", page.getPageCount(pageSize));
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("parentId", parentId);

        // 得到当前菜单列表所属上级菜单的id
        if (parentId != 0) {
            int grandParentId = iAdminMenuService.getById(parentId).getParentId();
            request.setAttribute("grandParentId", grandParentId);
        }

        return "system/menu/menuList";
    }

    /**
     * 创建菜单页面
     */
    @RequestMapping(value = "/toadd")
    public String toadd(HttpServletRequest request, Integer parentId) {
        request.setAttribute("parentId", parentId);
        return "system/menu/menuAdd";
    }

    /**
     * 创建菜单
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult add(HttpServletResponse response, Integer parentId, String link, @RequestParam String name,
                          Integer isJournal) {
        parentId = parentId == null ? 0 : parentId;
        AdminMenu menu = iAdminMenuService.getOneByField("link", link);
        if (menu == null || StringUtils.isBlank(link)) {
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.setParentId(parentId);
            adminMenu.setName(name);
            if (StringUtils.isNotBlank(link)) {
                adminMenu.setLink(link.trim());
            }
            adminMenu.setIsJournal(isJournal == null ? 0 : isJournal);
            iAdminMenuService.save(adminMenu);
            return new BaseResult("ok", "创建菜单成功");
        }
        return BaseResult.failed("fail", "创建菜单失败,菜单链接已存在：  " + menu.getName());
    }

    /**
     * 修改菜单页面
     */
    @RequestMapping(value = "/toedit")
    public String toedit(HttpServletRequest request, @RequestParam Integer id) {
        AdminMenu adminMenu = iAdminMenuService.getById(id);
        request.setAttribute("adminMenu", adminMenu);
        return "system/menu/menuEdit";
    }

    /**
     * 修改菜单
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult edit(HttpServletResponse response, @RequestParam Integer id, @RequestParam String name, String link,
                        Integer isJournal) {
        AdminMenu menu = iAdminMenuService.getById(id);
        if (StringUtils.isNotBlank(link)) {
            AdminMenu menu2 = iAdminMenuService.getOneByField("link", link.trim());
            if (menu != null && menu2 != null && !menu.getId().equals(menu2.getId())) {
                return BaseResult.failed("fail", "菜单链接已存在：  (" + menu2.getName() + ")");
            }
        }
        if (menu != null) {
            menu.setName(name);
            menu.setLink(StringUtils.isBlank(link) ? null : link.trim());
            menu.setIsJournal(isJournal == null ? 0 : isJournal);
            String[] arr = {"link"};
            iAdminMenuService.updateBySelective(menu, arr);
        }
        return new BaseResult("ok", "修改菜单成功");
    }

    /**
     * 删除菜单
     */
    @RequestMapping("/delete")
    @ResponseBody
    public BaseResult delete(HttpServletResponse response, @RequestParam Integer id) {
        List<AdminMenu> list = iAdminMenuService.queryChildList(id);
        if (list != null && list.size() > 0) {
            return BaseResult.failed("fail", "不能删除,存在子菜单");
        }
        iAdminMenuService.deleteById(id);
        return new BaseResult("ok", "删除菜单成功");
    }

}