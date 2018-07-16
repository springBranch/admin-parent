package com.admin.client.model.system;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表DO
 *
 * @author LJ
 * @date 2017年3月15日 上午10:00:29
 */
@Table(name = "system_user_info")
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 中文名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 用户登录名
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 用户邮箱
     */
    @Column(name = "user_email")
    private String userEmail;
    /**
     * 用户手机号
     */
    @Column(name = "user_mobile")
    private String userMobile;

    /**
     * 用户登录密码
     */
    @Column(name = "user_pwd")
    private String userPwd;

    /**
     * 最后一次登录时间
     */
    @Column(name = "login_time")
    private Date loginTime;
    /**
     * 创建人
     */
    @Column(name = "creator")
    private Integer creator;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 所属角色
     */
    @Column(name = "role_id")
    private Integer roleId;

    /* 扩展字段如下 */
    /**
     * 角色名
     */
    @Transient
    private String roleName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}