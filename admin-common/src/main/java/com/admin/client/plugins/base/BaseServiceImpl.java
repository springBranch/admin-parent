package com.admin.client.plugins.base;

import com.admin.client.plugins.base.page.PageResult;
import com.admin.client.plugins.base.page.PageSearch;
import com.admin.client.plugins.util.*;
import com.admin.client.result.BaseResult;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseServiceImpl<T extends Serializable> implements BaseService<T> {

    protected Class<T> entityClazz;
    @Autowired(required = false)
    private Mapper<T> mapper;

    public BaseServiceImpl() {
        this.entityClazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public BaseServiceImpl(Class<T> clazz) {
        this.entityClazz = clazz;
    }

    @Override
    public T getById(Object id, String... selectColumns) {
        Example example = MyBatisExampleUtil.createFindByIdsAndSelectColumnsExample(entityClazz, selectColumns, id);
        List<T> list = mapper.selectByExample(example);
        return EmptyUtil.isNotEmpty(list) && list.size() > 0 ? list.get(0) : null;
    }


    @Override
    public List<T> getByCondition(SearchCondition condition) {
        Example example = MyBatisExampleUtil.getExampleBySearchCondition(condition, entityClazz);
        if (EmptyUtil.isNotEmpty(condition.getTop())) {
            PageHelper.startPage(0, condition.getTop(), false);
        }
        return MyBatisServiceUtil.getByExample(example, mapper);
    }

    @Override
    public T getOneByCondition(SearchCondition condition) {
        Example example = MyBatisExampleUtil.getExampleBySearchCondition(condition, entityClazz);
        List<T> list = mapper.selectByExample(example);
        return EmptyUtil.isNotEmpty(list) && list.size() > 0 ? list.get(0) : null;
    }


    @Override
    public long countByCondition(SearchCondition condition) {
        Example example = MyBatisExampleUtil.getBaseExampleBySearchCondition(condition, entityClazz);
        return MyBatisServiceUtil.countByExample(example, mapper);
    }

    @Override
    public PageResult<T> getByPage(SearchCondition condition) {
        if (EmptyUtil.isEmpty(condition.getPageSearch())) {
            condition.setPageSearch(new PageSearch());
        }
        Example example = MyBatisExampleUtil.getExampleBySearchCondition(condition, entityClazz);
        return MyBatisServiceUtil.getByPage(condition.getPageSearch(), example, mapper);
    }

    @Override
    public boolean exists(SearchCondition condition) {
        Example example = MyBatisExampleUtil.getBaseExampleBySearchCondition(condition, entityClazz);
        return MyBatisServiceUtil.countByExample(example, mapper) > 0;
    }


    @Override
    public List<T> getALL() {
        Example example = new Example(entityClazz);
        return MyBatisServiceUtil.getByExample(example, mapper);
    }

    @Override
    public BaseResult save(T entity) {
        ModelSetFieldDefaultValueUtil.setFieldDefaultValue(entity);
        Integer successNum = MyBatisServiceUtil.saveSelective(entity, this.mapper);
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }

    @Override
    public <E> E saveAndReturnId(T entity) {
        ModelSetFieldDefaultValueUtil.setFieldDefaultValue(entity);
        Integer successNum = MyBatisServiceUtil.saveSelective(entity, this.mapper);
        return BaseServiceUtil.createResultByOperationResult(successNum, entity);
    }

    @Override
    public BaseResult deleteById(Object id) {
        Integer successNum = MyBatisServiceUtil.deleteByPrimaryKey(id, this.mapper);
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }

    @Override
    public BaseResult delete(T entity) {
        Integer successNum = MyBatisServiceUtil.delete(entity, this.mapper);
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }

    @Override
    public BaseResult deleteByCondition(SearchCondition condition) {
        Example example = MyBatisExampleUtil.getBaseExampleBySearchCondition(condition, entityClazz);
        Integer successNum = MyBatisServiceUtil.deleteByExample(example, this.mapper);
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }

    @Override
    public BaseResult updateEntity(T entity) {
        Integer successNum = MyBatisServiceUtil.updateByPrimaryKey(entity, this.mapper);
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }

    @Override
    public BaseResult updateEntityByCondition(T newEntity, SearchCondition condition) {
        Example example = MyBatisExampleUtil.getBaseExampleBySearchCondition(condition, entityClazz);
        Integer successNum = MyBatisServiceUtil.updateByExampleSelective(newEntity, example, this.mapper);
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }


    @Override
    public T getOneByField(String field, Object value) {
        List<T> list = this.getByField(field, value);
        return EmptyUtil.isNotEmpty(list) ? list.get(0) : null;
    }

    @Override
    public List<T> getByField(String field, Object value) {
        Example example = MyBatisExampleUtil.createFindByFieldExampleByClass(this.entityClazz, field, value);
        return mapper.selectByExample(example);
    }

    @Override
    public BaseResult updateBySelective(T entity, String[] nullProperties) {
        Integer successNum = MyBatisServiceUtil.updateByPrimaryKeySelective(entity, this.mapper, nullProperties);
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }

    @Override
    public BaseResult updateBySelective(T entity) {
        Integer successNum = MyBatisServiceUtil.updateByPrimaryKeySelective(entity, this.mapper);
        return BaseServiceUtil.createResultByOperationResult(successNum);
    }

}
