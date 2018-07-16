package com.admin.service.impl.system;

import com.admin.client.constant.RoleInfoEnum;
import com.admin.client.model.system.RoleInfo;
import com.admin.client.plugins.base.BaseServiceImpl;
import com.admin.client.plugins.base.page.PageResult;
import com.admin.client.service.system.IRoleInfoService;
import com.admin.client.utils.PageUtil;
import com.admin.service.mapper.system.RoleInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleInfoServiceImpl extends BaseServiceImpl<RoleInfo> implements IRoleInfoService {

    @Autowired
    private RoleInfoMapper roleInfoMapper;

    @Override
    public List<RoleInfo> queryByRoleName(String roleName) {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setRoleName(roleName);
        return roleInfoMapper.select(roleInfo);
    }

    @Override
    public PageResult<RoleInfo> queryPage(Integer roleId, Integer pageIndex, Integer pageSize) {
        PageResult<RoleInfo> page = new PageResult<RoleInfo>();
        List<RoleInfo> roleList = this.queryList(roleId);
        if (roleList != null && roleList.size() > 0) {
            page.setTotal(new Long(roleList.size()));
            page.setRows(PageUtil.getPageList(roleList, pageIndex, pageSize));
        }
        return page;
    }

    @Override
    public List<RoleInfo> queryList(Integer roleId) {
        List<RoleInfo> roleList = new ArrayList<RoleInfo>();
        if (roleId == null || RoleInfoEnum.ROLE_INFO_1.getValue().equals(roleId)) {
            roleList.add(roleInfoMapper.queryById(RoleInfoEnum.ROLE_INFO_1.getValue()));
        }
        this.queryByParentId(roleId, roleList);
        return roleList;
    }

    private void queryByParentId(Integer roleId, List<RoleInfo> roleList) {
        List<RoleInfo> list = this.roleInfoMapper.queryByParentId(roleId);
        if (list != null && list.size() > 0) {
            roleList.addAll(list);
            for (RoleInfo role : list) {
                this.queryByParentId(role.getId(), roleList);
            }
        }
    }

}