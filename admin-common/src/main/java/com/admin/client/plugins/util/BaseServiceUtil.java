package com.admin.client.plugins.util;


import com.admin.client.plugins.base.SearchCondition;
import com.admin.client.result.BaseResult;
import com.admin.client.plugins.util.annotation.UpdateTime;

import javax.persistence.Id;
import java.util.Date;

public class BaseServiceUtil {


    /**
     * 根据操作结果创建BaseResult 对象
     *
     * @param modifyNum 修改成功的条数
     * @return ContainReturnValueResult<T>
     */
    public static BaseResult createResultByOperationResult(Integer modifyNum) {
        if (EmptyUtil.isNotEmpty(modifyNum) && modifyNum > 0) {
            return BaseResult.success(modifyNum);
        } else {
            return BaseResult.failed("操作失败");
        }
    }

    public static <T, E> E createResultByOperationResult(Integer modifyNum, T entity) {
        if (EmptyUtil.isNotEmpty(modifyNum) && modifyNum > 0) {
            Object id = ReflectUtil.getValueByAnnotation(entity, Id.class);
            if (EmptyUtil.isNotEmpty(id)) {
                return (E) id;
            }
        }
        return null;
    }

    public static <T> void doUpdateTime(T entity) {
        try {
            String[] fieldNames = ReflectUtil.getFieldNameByAnnotation(entity.getClass(), UpdateTime.class);
            if (EmptyUtil.isNotEmpty(fieldNames) && fieldNames.length > 0) {
                ReflectUtil.setValueByFieldName(entity, fieldNames[0], new Date());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static SearchCondition createSaveOrUpdateCondition(String[] conditionFields, Object entity) {
        SearchCondition condition = null;
        for (String field : conditionFields) {
            try {
                Object value = ReflectUtil.getValueByFieldName(entity, field);
                if (EmptyUtil.isNotEmpty(value)) {
                    condition = SearchCondition.instance().buildEqualConditions(field, value);
                }
            } catch (Exception e) {
            }
        }
        return condition;
    }



}
