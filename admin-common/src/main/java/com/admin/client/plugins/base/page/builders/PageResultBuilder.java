package com.admin.client.plugins.base.page.builders;


import com.admin.client.plugins.base.page.PageResult;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 分页结果对象的构造器
 *
 * @param <T>
 * @author LiuYiJun
 * @date 2015年7月20日
 */
public class PageResultBuilder<T> {

    private PageResult<T> result = null;

    /**
     * 构建分页结果对象的总记录数
     *
     * @param total
     * @return
     * @author LiuYiJun
     * @date 2015年7月20日
     */
    public PageResultBuilder<T> buildPageData(Long total) {
        if (StringUtils.isEmpty(result)) {
            result = new PageResult<T>();
        }
        if (!StringUtils.isEmpty(total) && total > 0) {
            result.setTotal(total);
        }
        return this;
    }

    /**
     * 构建分页结果对象的分页数据信息
     *
     * @param rows
     * @return
     * @author LiuYiJun
     * @date 2015年7月20日
     */
    public PageResultBuilder<T> buildPageData(List<T> rows) {
        if (StringUtils.isEmpty(result)) {
            result = new PageResult<T>();
        }
        if (!StringUtils.isEmpty(rows)) {
            result.setRows(rows);
        }
        return this;
    }

    /**
     * 构建分页结果对象的总记录数和分页数据信息
     *
     * @param total
     * @param rows
     * @return
     * @author LiuYiJun
     * @date 2015年7月20日
     */
    public PageResultBuilder<T> buildPageData(Long total, List<T> rows) {
        if (StringUtils.isEmpty(result)) {
            result = new PageResult<T>();
        }
        if (!StringUtils.isEmpty(total) && total > 0) {
            result.setTotal(total);
        }
        if (!StringUtils.isEmpty(rows)) {
            result.setRows(rows);
        }
        return this;
    }

    /**
     * 返回分页结果对象信息
     *
     * @return
     * @author LiuYiJun
     * @date 2015年7月20日
     */
    public PageResult<T> getPageResultInstance() {
        return result;
    }

}
