package com.admin.client.plugins.base;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.admin.client.plugins.base.page.PageSearch;
import com.admin.client.plugins.base.search.OrCondition;
import com.admin.client.plugins.base.search.RangeCondition;
import com.admin.client.plugins.util.EmptyUtil;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 查询条件封装类，实体类的属性值都是等于的条件
 * Created by  on 2016/7/28.
 */
public class SearchCondition implements Serializable {


    private Object modelExample;
    private Integer top;
    private String[] selectColumns;
    /**
     * 排序条件，排序字段为KEY，方向为值，可以添加多个
     */
    private Map<String, String> orderByConditions;
    private List<RangeCondition> rangeConditions;
    private Map<String, String> likeConditions;
    private Map<String, String> startWithConditions;
    private Map<String, String> endWithConditions;
    private Map<String, List> inConditions;
    private Map<String, List> notInConditions;
    private Map<String, Object> equalConditions;
    private Map<String, Object> notEqualConditions;

    private OrCondition orCondition;
    private PageSearch pageSearch;

    public static SearchCondition instance(Object example, PageSearch search) {
        return new SearchCondition().setModelExample(example).setPageSearch(search);
    }

    public static SearchCondition instance(Object example) {
        return new SearchCondition().setModelExample(example);
    }

    public static SearchCondition instance(PageSearch search) {
        return new SearchCondition().setPageSearch(search);
    }

    public static SearchCondition instance() {
        return new SearchCondition();
    }

    public Object getModelExample() {
        return modelExample;
    }

    public SearchCondition setModelExample(Object modelExample) {
        this.modelExample = modelExample;
        return this;
    }

    public Map<String, String> getOrderByConditions() {
        return orderByConditions;
    }

    public SearchCondition setOrderByConditions(Map<String, String> orderByConditions) {
        this.orderByConditions = orderByConditions;
        return this;
    }

    public List<RangeCondition> getRangeConditions() {
        return rangeConditions;
    }

    public SearchCondition setRangeConditions(List<RangeCondition> rangeConditions) {
        this.rangeConditions = rangeConditions;
        return this;
    }

    public PageSearch getPageSearch() {
        return pageSearch;
    }

    public SearchCondition setPageSearch(PageSearch pageSearch) {
        this.pageSearch = pageSearch;
        return this;
    }

    public Integer getTop() {
        return top;
    }

    public SearchCondition setTop(Integer top) {
        this.top = top;
        return this;
    }

    public Map<String, String> getLikeConditions() {
        return likeConditions;
    }

    public SearchCondition setLikeConditions(Map<String, String> likeConditions) {
        this.likeConditions = likeConditions;
        return this;
    }

    public Map<String, String> getStartWithConditions() {
        return startWithConditions;
    }

    public void setStartWithConditions(Map<String, String> startWithConditions) {
        this.startWithConditions = startWithConditions;
    }

    public Map<String, String> getEndWithConditions() {
        return endWithConditions;
    }

    public void setEndWithConditions(Map<String, String> endWithConditions) {
        this.endWithConditions = endWithConditions;
    }

    public Map<String, List> getInConditions() {
        return inConditions;
    }

    public void setInConditions(Map<String, List> inConditions) {
        this.inConditions = inConditions;
    }

    public Map<String, List> getNotInConditions() {
        return notInConditions;
    }

    public void setNotInConditions(Map<String, List> notInConditions) {
        this.notInConditions = notInConditions;
    }

    public String[] getSelectColumns() {
        return selectColumns;
    }

    public SearchCondition setSelectColumns(String... selectColumns) {
        if (EmptyUtil.isNotEmpty(selectColumns) && selectColumns.length > 0) {
            this.selectColumns = selectColumns;
        }
        return this;
    }


    public Map<String, Object> getEqualConditions() {
        return equalConditions;
    }

    public SearchCondition setEqualConditions(Map<String, Object> equalConditions) {
        this.equalConditions = equalConditions;
        return this;
    }

    public Map<String, Object> getNotEqualConditions() {
        return notEqualConditions;
    }

    public SearchCondition setNotEqualConditions(Map<String, Object> notEqualConditions) {
        this.notEqualConditions = notEqualConditions;
        return this;
    }

    public OrCondition getOrCondition() {
        return orCondition;
    }

    public SearchCondition setOrCondition(OrCondition orCondition) {
        this.orCondition = orCondition;
        return this;
    }

    /***
     * ------------------------------build部分 -----------------
     **/

    public SearchCondition buildLikeConditions(String field, String value) {
        if (EmptyUtil.isEmpty(likeConditions)) {
            this.likeConditions = Maps.newHashMap();
        }
        this.likeConditions.put(field, value);
        return this;
    }

    public SearchCondition buildRangeConditions(RangeCondition rangeCondition) {
        if (EmptyUtil.isEmpty(rangeConditions)) {
            this.rangeConditions = Lists.newArrayList();
        }
        this.rangeConditions.add(rangeCondition);
        return this;
    }

    public SearchCondition buildOrderByConditions(String field, String value) {
        if (EmptyUtil.isEmpty(orderByConditions)) {
            this.orderByConditions = Maps.newHashMap();
        }
        this.orderByConditions.put(field, value);
        return this;
    }

    public SearchCondition buildStartWithConditions(String field, String value) {
        if (EmptyUtil.isEmpty(startWithConditions)) {
            this.startWithConditions = Maps.newHashMap();
        }
        this.startWithConditions.put(field, value);
        return this;
    }


    public SearchCondition buildEndWithConditions(String field, String value) {
        if (EmptyUtil.isEmpty(endWithConditions)) {
            this.endWithConditions = Maps.newHashMap();
        }
        this.endWithConditions.put(field, value);
        return this;
    }

    public SearchCondition buildInConditions(String field, List values) {
        if (EmptyUtil.isEmpty(inConditions)) {
            this.inConditions = Maps.newHashMap();
        }
        this.inConditions.put(field, values);
        return this;
    }

    public SearchCondition buildNotInConditions(String field, List values) {
        if (EmptyUtil.isEmpty(notInConditions)) {
            this.notInConditions = Maps.newHashMap();
        }
        this.notInConditions.put(field, values);
        return this;
    }


    public SearchCondition buildEqualConditions(String field, Object values) {
        if (EmptyUtil.isEmpty(equalConditions)) {
            this.equalConditions = Maps.newHashMap();
        }
        this.equalConditions.put(field, values);
        return this;
    }

    public SearchCondition buildNotEqualConditions(String field, Object values) {
        if (EmptyUtil.isEmpty(notEqualConditions)) {
            this.notEqualConditions = Maps.newHashMap();
        }
        this.notEqualConditions.put(field, values);
        return this;
    }

    public void clearAll() {
        Field[] allFields = this.getClass().getDeclaredFields();
        for (Field field : allFields) {
            try {
                field.set(this, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
