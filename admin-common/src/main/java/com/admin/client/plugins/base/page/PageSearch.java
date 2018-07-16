package com.admin.client.plugins.base.page;


import java.io.Serializable;

/**
 * 分页查询条件封装类
 *
 * @author
 */
public class PageSearch implements Serializable {

    public PageSearch() {
    }

    public PageSearch(Integer page, Integer rows) {
        this.page = page;
        this.rows = rows;
    }


    public PageSearch(Integer page, Integer rows, Boolean count) {
        this.page = page;
        this.rows = rows;
        this.count = count;
    }

    /**
     * 分页请求时当前页变量
     */
    protected Integer page = 1;

    /**
     * 分页请求时每页显示数量变量
     */
    protected Integer rows = 10;


    /**
     * 是否查询总数
     */
    protected Boolean count = true;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        if (page == null || page < 1) {
            page = 1;
        }
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        if (rows == null || rows < 1) {
            rows = 1;
        }
        this.rows = rows;

    }

    public Boolean getCount() {
        return count;
    }

    public void setCount(Boolean count) {
        this.count = count;
    }

    public int getCurrentResult() {
        return (this.page - 1) * this.rows;
    }

}
