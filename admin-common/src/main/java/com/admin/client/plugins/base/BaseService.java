package com.admin.client.plugins.base;


import com.admin.client.plugins.base.page.PageResult;
import com.admin.client.result.BaseResult;

import java.io.Serializable;
import java.util.List;


public interface BaseService<T extends Serializable> {

    /**
     * 根据Id值查询
     *
     * @param id            id
     * @param selectColumns 要查询的列
     * @return
     */
    T getById(Object id, String... selectColumns);


    /**
     * 据条件查询数据
     *
     * @param condition 条件
     * @return 查询到的集合列表
     */
    List<T> getByCondition(SearchCondition condition);

    /**
     * 据条件查询数据
     *
     * @param condition 条件
     * @return 查询到的集合列表
     */
    T getOneByCondition(SearchCondition condition);

    /**
     * 根据字段和值查询一条数据
     *
     * @param field 字段
     * @param value 字段对应的值
     * @return 结果数据
     */
    T getOneByField(String field, Object value);


    /**
     * 根据字段和值查询列表数据
     *
     * @param field 字段
     * @param value 字段对应的值
     * @return 结果数据
     */
    List<T> getByField(String field, Object value);


    List<T> getALL();


    /**
     * 根据条件查询总数
     *
     * @param condition 条件
     * @return 查询到的总数
     */
    long countByCondition(SearchCondition condition);

    /**
     * 分页查询
     *
     * @param condition 条件查询对象
     * @return 根据条件查询的分页结果封装对象
     */
    PageResult<T> getByPage(SearchCondition condition);


    /**
     * 根据条件查询记录是否存在
     *
     * @param condition
     * @return
     */
    boolean exists(SearchCondition condition);

    /**
     * 保存实体对象
     *
     * @param entity 实体对象
     * @return
     */
    BaseResult save(T entity);

    /**
     * 保存实体对象并返回主键值
     *
     * @param entity 实体对象
     * @return 主键值
     */
    <E> E saveAndReturnId(T entity);

    /**
     * 根据StringId 删除数据
     *
     * @param id string类型的id
     * @return BaseResult 里面的obj对象是影响行数
     */
    BaseResult deleteById(Object id);


    /**
     * 根据实体对象删除数据
     *
     * @param entity 实体对象
     * @return BaseResult 里面的obj对象是影响行数
     */
    BaseResult delete(T entity);

    /**
     * 根据条件删除
     *
     * @param condition 条件
     * @return BaseResult 里面的obj对象是影响行数
     */
    BaseResult deleteByCondition(SearchCondition condition);

    /**
     * 修改全部数据，属性值为null是也会更新
     *
     * @param entity 实体对象
     * @return BaseResult 里面的obj对象是影响行数
     */
    BaseResult updateEntity(T entity);

    /**
     * 修改部分数据，如果实体对象的某个属性为null则不更新
     *
     * @param nullProperties 指定为null时也要更新的属性名称
     * @param entity
     * @return
     */
    BaseResult updateBySelective(T entity, String[] nullProperties);

    /**
     * 修改部分数据，如果实体对象的某个属性为null则不更新
     *
     * @param entity
     * @return
     */
    BaseResult updateBySelective(T entity);

    /**
     * 根据条件部分更新，属性值为null不更新
     *
     * @param newEntity 新的实体对象
     * @param condition 查询条
     * @return
     */
    BaseResult updateEntityByCondition(T newEntity, SearchCondition condition);


}
