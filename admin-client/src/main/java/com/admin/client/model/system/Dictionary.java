package com.admin.client.model.system;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据字典表DO
 *
 * @author LJ
 * @date 2017年3月15日 上午10:10:38
 */
@Table(name = "system_dictionary")
public class Dictionary implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 字典key
     */
    @Column(name = "dict_key")
    private String dictKey;
    /**
     * 字典vlue
     */
    @Column(name = "dict_value")
    private String dictValue;
    /**
     * 注释
     */
    @Column(name = "comments")
    private String comments;
    /**
     *
     */
    @Column(name = "create_time")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }


    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}