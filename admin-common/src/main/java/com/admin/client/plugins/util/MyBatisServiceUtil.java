package com.admin.client.plugins.util;


import com.admin.client.plugins.base.page.PageResult;
import com.admin.client.plugins.base.page.PageSearch;
import org.apache.ibatis.session.RowBounds;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.Id;
import java.util.List;

public class MyBatisServiceUtil {


//    public static <T> T getById(Object id, Class<T> entityClazz,Mapper<T> mapper, String... selectColumns) {
//        Example example = MyBatisExampleUtil.createFindByIdsAndSelectColumnsExample(entityClazz, selectColumns, id);
//        return mapper.selectByExample(example).get(0);
//    }

    public static <T> long countByExample(Example example, Mapper<T> mapper) {
        return mapper.selectCountByExample(example);
    }


    public static <T> int save(T entity, Mapper<T> mapper) {
        return mapper.insert(entity);
    }


    public static <T> int saveSelective(T entity, Mapper<T> mapper) {
        return mapper.insertSelective(entity);
    }


    public static <T> int delete(T entity, Mapper<T> mapper) {
        return mapper.delete(entity);
    }


    public static <T> int deleteByPrimaryKey(Object primaryKey, Mapper<T> mapper) {
        return mapper.deleteByPrimaryKey(primaryKey);
    }


    public static <T> int deleteByExample(Example example, Mapper<T> mapper) {
        return mapper.deleteByExample(example);
    }


    public static <T> List<T> getByExample(Example example, Mapper<T> mapper) {
        return mapper.selectByExample(example);
    }


    public static <T> int updateByExampleSelective(T entity, Example example, Mapper<T> mapper) {
        BaseServiceUtil.doUpdateTime(entity);
        return mapper.updateByExampleSelective(entity, example);
    }


    public static <T> int updateByExample(T entity, Example example, Mapper<T> mapper) {
        BaseServiceUtil.doUpdateTime(entity);
        return mapper.updateByExample(entity, example);
    }


    public static <T> int updateByPrimaryKeySelective(T entity, Mapper<T> mapper, String... nullProperties) {
        BaseServiceUtil.doUpdateTime(entity);
        if (EmptyUtil.isNotEmpty(nullProperties)) {
            Object id = ReflectUtil.getValueByAnnotation(entity, Id.class);
            T old = mapper.selectByPrimaryKey(id);
            MyBeanUtils.copyPropertiesNotNull(entity, old, nullProperties);
            return mapper.updateByPrimaryKey(old);
        }
        return mapper.updateByPrimaryKeySelective(entity);
    }


    public static <T> int updateByPrimaryKey(T entity, Mapper<T> mapper) {
        BaseServiceUtil.doUpdateTime(entity);
        return mapper.updateByPrimaryKey(entity);
    }


    public static <T> PageResult<T> getByPage(PageSearch search, Example example, Mapper<T> mapper) {
        PageResult<T> result = new PageResult<T>();
        if (search.getCount() == null) {
            search.setCount(true);
        }
        int start = (search.getPage() - 1) * search.getRows();
        RowBounds rowBounds = new RowBounds(start < 1 ? 0 : start, search.getRows());
        result.setRows(mapper.selectByExampleAndRowBounds(example, rowBounds));
        if (search.getCount()) {
            result.setTotal(mapper.selectCountByExample(example));
        } else {
            result.setTotal(0L);
        }
        return result;
    }


}
