package com.admin.client.model.system;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单表DO
 *
 * @author LJ
 * @date 2017年3月15日 上午9:41:07
 */
@Table(name = "system_admin_menu")
public class AdminMenu implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 父菜单ID
     */
    @Column(name = "parent_id")
    private Integer parentId;
    /**
     * 名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 链接
     */
    @Column(name = "link")
    private String link;
    /**
     * 是否记录日志：0否，1是
     */
    @Column(name = "is_journal")
    private Integer isJournal;

    /* 扩展字段如下 */
    /**
     *
     */
    @Transient
    private String menuPath;
    /**
     * 子菜单
     */
    @Transient
    private List<AdminMenu> children;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link
     */
    public void setLink(String link) {
        this.link = link;
    }

    public Integer getIsJournal() {
        return isJournal;
    }

    public void setIsJournal(Integer isJournal) {
        this.isJournal = isJournal;
    }

    public List<AdminMenu> getChildren() {
        return children;
    }

    public void setChildren(List<AdminMenu> children) {
        this.children = children;
    }

    public String getMenuPath() {
        return menuPath;
    }

    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }
}