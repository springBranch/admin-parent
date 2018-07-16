package com.admin.client.plugins.util;


import com.admin.client.plugins.base.SearchCondition;
import com.admin.client.plugins.base.search.OrCondition;
import com.admin.client.plugins.base.search.RangeCondition;
import com.admin.client.plugins.base.search.RangeConditionType;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class MyBatisExampleUtil {


    public static Example createFindByFieldExampleByClass(Class<?> clazz, String field, Object value) {
        Example example = new Example(clazz);
        example.createCriteria().andEqualTo(field, value);
        return example;
    }


    /**
     * 根据主键、联合主键查询
     *
     * @param clazz
     * @param fields
     * @param ids
     * @return
     */
    public static Example createFindByIdsAndSelectColumnsExample(Class<?> clazz, String[] fields, Object... ids) {
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        String[] fieldNames = ReflectUtil.getFieldNameByAnnotation(clazz, Id.class);
        if (fieldNames.length == ids.length) {
            for (int i = 0; i < fieldNames.length; i++) {
                criteria.andEqualTo(fieldNames[i], ids[i]);
            }
        } else {
            criteria.andEqualTo(fieldNames[0], ids[0]);
        }
        if (EmptyUtil.isNotEmpty(fields) && fields.length > 0) {
            example.selectProperties(fields);
        }
        return example;
    }

    public static Example getExampleBySearchCondition(SearchCondition condition, Class<?> clazz) {
        Example example = getBaseExampleBySearchCondition(condition, clazz);
        if (EmptyUtil.isNotEmpty(condition)) {
            //排序处理
            doOrderByCondition(example, condition.getOrderByConditions());
            //筛选字段
            doSelectColumns(condition, example);
        }
        return example;
    }

    public static Example getBaseExampleBySearchCondition(SearchCondition condition, Class<?> clazz) {
        Example example = new Example(clazz);
        if (EmptyUtil.isNotEmpty(condition)) {
            Example.Criteria criteria = example.createCriteria();
            Object exampleEntity = condition.getModelExample();
            if (EmptyUtil.isNotEmpty(exampleEntity)) {
                doEntityCondition(criteria, exampleEntity);
            }
            doEqualCondition(criteria, condition.getEqualConditions());
            doNotEqualCondition(criteria, condition.getNotEqualConditions());
            //区间条件处理
            doRangeCondition(criteria, condition.getRangeConditions());
            //模糊查询处理
            doLikeCondition(criteria, condition.getLikeConditions());

            //模糊查询处理（以xx开头）
            doStartWithCondition(criteria, condition.getStartWithConditions());

            //模糊查询处理（以xx结尾）
            doEndWithCondition(criteria, condition.getEndWithConditions());

            //In查询处理
            doInCondition(criteria, condition.getInConditions());
            //NotIn查询处理
            doNotInCondition(criteria, condition.getNotInConditions());
            //Or查询
            doOrCondition(example, condition.getOrCondition());
        }

        return example;
    }


    private static void doEntityCondition(Example.Criteria criteria, Object exampleEntity) {
        Field[] fields = ReflectUtil.getAllFields(exampleEntity);
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!"serialVersionUID".equals(fieldName)) {
                Transient tr = field.getAnnotation(Transient.class);
                if (EmptyUtil.isEmpty(tr)) {
                    Object value;
                    try {
                        value = ReflectUtil.getValueByFieldName(exampleEntity, fieldName);
                        if (EmptyUtil.isNotEmpty(value)) {
                            if (field.getType() != Date.class) {
                                criteria.andEqualTo(fieldName, value);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void doRangeCondition(Example.Criteria criteria, List<RangeCondition> rangeConditions) {
        if (EmptyUtil.isNotEmpty(rangeConditions) && rangeConditions.size() > 0) {
            for (RangeCondition dateCondition : rangeConditions) {
                if (EmptyUtil.isNotEmpty(dateCondition.getStartValue()) || EmptyUtil.isNotEmpty(dateCondition.getEndValue())) {
                    if (EmptyUtil.isNotEmpty(dateCondition.getType())) {
                        RangeConditionType dateConditionType = dateCondition.getType();
                        switch (dateConditionType) {
                            case GreaterThan:
                                criteria.andGreaterThan(dateCondition.getField(), dateCondition.getStartValue());
                                break;
                            case GreaterThanOrEqual:
                                criteria.andGreaterThanOrEqualTo(dateCondition.getField(), dateCondition.getStartValue());
                                break;
                            case LessThan:
                                criteria.andLessThan(dateCondition.getField(), dateCondition.getStartValue());
                                break;
                            case LessThanOrEqual:
                                criteria.andLessThanOrEqualTo(dateCondition.getField(), dateCondition.getStartValue());
                                break;
                            case Equal:
                                criteria.andEqualTo(dateCondition.getField(), dateCondition.getStartValue());
                                break;
                            case Between:
                                if (EmptyUtil.isNotEmpty(dateCondition.getEndValue()) && EmptyUtil.isNotEmpty(dateCondition.getStartValue())) {
                                    criteria.andBetween(dateCondition.getField(), dateCondition.getStartValue(), dateCondition.getEndValue());
                                } else if (EmptyUtil.isNotEmpty(dateCondition.getEndValue()) && EmptyUtil.isEmpty(dateCondition.getStartValue())) {
                                    criteria.andLessThanOrEqualTo(dateCondition.getField(), dateCondition.getEndValue());
                                } else if (EmptyUtil.isEmpty(dateCondition.getEndValue()) && EmptyUtil.isNotEmpty(dateCondition.getStartValue())) {
                                    criteria.andGreaterThanOrEqualTo(dateCondition.getField(), dateCondition.getEndValue());
                                }
                                break;
                        }
                    }
                }
            }
        }
    }

    private static void doOrderByCondition(Example example, Map<String, String> orderByCondition) {
        if (EmptyUtil.isNotEmpty(orderByCondition)) {
            for (String key : orderByCondition.keySet()) {
                if ("desc".equals(orderByCondition.get(key))) {
                    example.orderBy(key).desc();
                } else {
                    example.orderBy(key).asc();
                }
            }

        }
    }


    private static void doLikeCondition(Example.Criteria criteria, Map<String, String> likeCondition) {
        if (EmptyUtil.isNotEmpty(likeCondition)) {
            for (String key : likeCondition.keySet()) {
                criteria.andLike(key, "%" + likeCondition.get(key) + "%");
            }
        }
    }


    private static void doStartWithCondition(Example.Criteria criteria, Map<String, String> startWithCondition) {
        if (EmptyUtil.isNotEmpty(startWithCondition)) {
            for (String key : startWithCondition.keySet()) {
                criteria.andLike(key, startWithCondition.get(key) + "%");
            }
        }
    }

    private static void doEndWithCondition(Example.Criteria criteria, Map<String, String> endWithCondition) {
        if (EmptyUtil.isNotEmpty(endWithCondition)) {
            for (String key : endWithCondition.keySet()) {
                criteria.andLike(key, "%" + endWithCondition.get(key));
            }
        }
    }

    private static void doInCondition(Example.Criteria criteria, Map<String, List> inCondition) {
        if (EmptyUtil.isNotEmpty(inCondition)) {
            for (String key : inCondition.keySet()) {
                criteria.andIn(key, inCondition.get(key));
            }
        }
    }

    private static void doNotInCondition(Example.Criteria criteria, Map<String, List> notInCondition) {
        if (EmptyUtil.isNotEmpty(notInCondition)) {
            for (String key : notInCondition.keySet()) {
                criteria.andNotIn(key, notInCondition.get(key));
            }
        }
    }

    private static void doEqualCondition(Example.Criteria criteria, Map<String, Object> equalCondition) {
        if (EmptyUtil.isNotEmpty(equalCondition)) {
            for (String key : equalCondition.keySet()) {
                criteria.andEqualTo(key, equalCondition.get(key));
            }
        }
    }

    private static void doNotEqualCondition(Example.Criteria criteria, Map<String, Object> notEqualCondition) {
        if (EmptyUtil.isNotEmpty(notEqualCondition)) {
            for (String key : notEqualCondition.keySet()) {
                criteria.andNotEqualTo(key, notEqualCondition.get(key));
            }
        }
    }


    private static void doOrCondition(Example example, OrCondition orCondition) {
        if (EmptyUtil.isNotEmpty(orCondition)) {
            Example.Criteria criteria = example.createCriteria();
            if (EmptyUtil.isNotEmpty(orCondition.getEqualConditions())) {
                for (String key : orCondition.getEqualConditions().keySet()) {
                    criteria.andEqualTo(key, orCondition.getEqualConditions().get(key));
                }
            }
            if (EmptyUtil.isNotEmpty(orCondition.getNotEqualConditions())) {
                for (String key : orCondition.getNotEqualConditions().keySet()) {
                    criteria.andNotEqualTo(key, orCondition.getNotEqualConditions().get(key));
                }
            }
            if (EmptyUtil.isNotEmpty(orCondition.getInConditions())) {
                for (String key : orCondition.getInConditions().keySet()) {
                    criteria.andIn(key, orCondition.getInConditions().get(key));
                }
            }
            if (EmptyUtil.isNotEmpty(orCondition.getNotInConditions())) {
                for (String key : orCondition.getNotInConditions().keySet()) {
                    criteria.andNotIn(key, orCondition.getNotInConditions().get(key));
                }
            }
            if (EmptyUtil.isNotEmpty(orCondition.getStartWithConditions())) {
                for (String key : orCondition.getStartWithConditions().keySet()) {
                    criteria.andLike(key, orCondition.getStartWithConditions().get(key) + "%");
                }
            }
            if (EmptyUtil.isNotEmpty(orCondition.getEndWithConditions())) {
                for (String key : orCondition.getStartWithConditions().keySet()) {
                    criteria.andLike(key, "%" + orCondition.getStartWithConditions().get(key));
                }
            }
            example.or(criteria);
        }
    }


    private static void doSelectColumns(SearchCondition condition, Example example) {
        if (EmptyUtil.isNotEmpty(condition.getSelectColumns()) && condition.getSelectColumns().length > 0) {
            example.selectProperties(condition.getSelectColumns());
        }
    }


}
