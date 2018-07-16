package com.admin.service.impl.system;

import com.admin.client.model.system.AdminMenu;
import com.admin.client.plugins.base.BaseServiceImpl;
import com.admin.client.plugins.base.page.PageResult;
import com.admin.client.service.system.IAdminMenuService;
import com.admin.client.utils.PageUtil;
import com.admin.service.mapper.system.AdminMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminMenuServiceImpl extends BaseServiceImpl<AdminMenu> implements IAdminMenuService {
    @Autowired
    private AdminMenuMapper adminMenuMapper;

    @Override
    public PageResult<AdminMenu> queryPage(Integer pageIndex, Integer pageSize, Integer parentId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("parentId", parentId);
        // 查询总数量
        int total = adminMenuMapper.queryCount(map);
        if (pageIndex != null && pageSize != null) {
            int start = PageUtil.calcStart(pageIndex, pageSize, total);
            map.put("start", start);
            map.put("length", pageSize);
        }
        List<AdminMenu> list = adminMenuMapper.query(map);
        PageResult<AdminMenu> result = new PageResult<AdminMenu>();
        result.setRows(list);
        result.setTotal(new Long(total));
        return result;
    }

    @Override
    public List<AdminMenu> queryChildList(Integer parentId) {
        AdminMenu adminMenu = new AdminMenu();
        adminMenu.setParentId(parentId);
        return adminMenuMapper.select(adminMenu);
    }

    @Override
    public List<AdminMenu> queryByIds(String menuIds) {
        return adminMenuMapper.queryByIds(menuIds);
    }

    @Override
    public List<AdminMenu> queryPart(String[] menuIdArray) {
        return adminMenuMapper.queryPart(menuIdArray);
    }

    @Override
    public String queryAllIds() {
        List<Integer> queryAllIds = adminMenuMapper.queryAllIds();
        if (queryAllIds != null) {
            String ids = queryAllIds.toString();
            return ids.substring(1, queryAllIds.toString().length() - 1);
        }
        return "";
    }
}
