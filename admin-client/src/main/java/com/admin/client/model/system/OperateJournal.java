package com.admin.client.model.system;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统操作日志DO
 *
 * @author LJ
 * @date 2017年3月15日 上午10:10:38
 */
@Table(name = "system_operate_journal")
public class OperateJournal implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 日志类型：1操作日志,2异常日志
     */
    @Column(name = "journal_type")
    private Integer journalType;
    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;
    /**
     * 用户名称
     */
    @Column(name = "user_name")
    private String userName;
    /**
     * 用户手机号
     */
    @Column(name = "user_mobile")
    private String userMobile;
    /**
     * 操作名称
     */
    @Column(name = "operate_name")
    private String operateName;
    /**
     * IP地址
     */
    @Column(name = "ip_addr")
    private String ipAddr;
    /**
     * 请求路径
     */
    @Column(name = "request_path")
    private String requestPath;
    /**
     * 请求参数
     */
    @Column(name = "request_parameter")
    private String requestParameter;
    /**
     * 异常记录
     */
    @Column(name = "exception_journal")
    private String exceptionJournal;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getRequestParameter() {
        return requestParameter;
    }

    public void setRequestParameter(String requestParameter) {
        this.requestParameter = requestParameter;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getJournalType() {
        return journalType;
    }

    public void setJournalType(Integer journalType) {
        this.journalType = journalType;
    }

    public String getExceptionJournal() {
        return exceptionJournal;
    }

    public void setExceptionJournal(String exceptionJournal) {
        this.exceptionJournal = exceptionJournal;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

}
