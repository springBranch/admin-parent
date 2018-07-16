package com.admin.client.plugins.base.page;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询结果集封装对象
 *
 * @param <T>
 * @author
 */
public class PageResult<T> implements Serializable {

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 每页的数据
     */
    private List<T> rows;

    /**
     * @param
     * @return 页数
     * @Title：getPageCount
     * @Description：获取分页数
     */
    public long getPageCount(int pageSize) {

        if (total == null){
            return 0;
        }
        return total / pageSize + ((total % pageSize) > 0 ? 1 : 0);
    }

    /**
     * 解决因Dubbo协议传输将对象List转换为json对象的问题
     *
     * @param clazz
     */
    public void resetList(Class clazz) {
        List<T> newList = new ArrayList<T>();
        if (rows != null && rows.size() > 0) {
            if (rows.get(0) instanceof JSONObject) {
                for (Object obj : rows) {
                    newList.add((T) JSON.toJavaObject((JSONObject) obj, clazz));
                }
                this.setRows(newList);
            }
        }
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void setTotal(int total) {
        this.total = Long.valueOf(total);
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

}
