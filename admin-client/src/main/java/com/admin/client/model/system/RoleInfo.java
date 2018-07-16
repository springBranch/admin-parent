package com.admin.client.model.system;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统权限DO
 *
 * @author LJ
 * @date 2017年3月15日 上午9:51:49
 */
@Table(name = "system_role_info")
public class RoleInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 父角色ID
     */
    @Column(name = "parent_id")
    private Integer parentId;
    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 描述
     */
    @Column(name = "description")
    private String description;
    /**
     * 菜单ids
     */
    @Column(name = "menu_ids")
    private String menuIds;

    /* 扩展字段如下 */
    /**
     * 父角色名
     */
    @Transient
    private String parentName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }


}