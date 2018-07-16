package com.admin.client.plugins.base.search;


import com.google.common.collect.Maps;
import com.admin.client.plugins.util.EmptyUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class OrCondition implements Serializable {

    private Map<String, List> inConditions;
    private Map<String, List> notInConditions;
    private Map<String, Object> equalConditions;
    private Map<String, Object> notEqualConditions;
    private Map<String, String> startWithConditions;
    private Map<String, String> endWithConditions;

    public static OrCondition instance() {
        return new OrCondition();
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


    public Map<String, Object> getEqualConditions() {
        return equalConditions;
    }

    public OrCondition setEqualConditions(Map<String, Object> equalConditions) {
        this.equalConditions = equalConditions;
        return this;
    }

    public Map<String, Object> getNotEqualConditions() {
        return notEqualConditions;
    }

    public OrCondition setNotEqualConditions(Map<String, Object> notEqualConditions) {
        this.notEqualConditions = notEqualConditions;
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

    /***
     * ------------------------------build部分 -----------------
     **/


    public OrCondition buildInConditions(String field, List values) {
        if (EmptyUtil.isEmpty(inConditions)) {
            this.inConditions = Maps.newHashMap();
        }
        this.inConditions.put(field, values);
        return this;
    }

    public OrCondition buildNotInConditions(String field, List values) {
        if (EmptyUtil.isEmpty(notInConditions)) {
            this.notInConditions = Maps.newHashMap();
        }
        this.notInConditions.put(field, values);
        return this;
    }


    public OrCondition buildEqualConditions(String field, Object values) {
        if (EmptyUtil.isEmpty(equalConditions)) {
            this.equalConditions = Maps.newHashMap();
        }
        this.equalConditions.put(field, values);
        return this;
    }

    public OrCondition buildNotEqualConditions(String field, Object values) {
        if (EmptyUtil.isEmpty(notEqualConditions)) {
            this.notEqualConditions = Maps.newHashMap();
        }
        this.notEqualConditions.put(field, values);
        return this;
    }

    public OrCondition buildStartWithConditions(String field, String value) {
        if (EmptyUtil.isEmpty(startWithConditions)) {
            this.startWithConditions = Maps.newHashMap();
        }
        this.startWithConditions.put(field, value);
        return this;
    }


    public OrCondition buildEndWithConditions(String field, String value) {
        if (EmptyUtil.isEmpty(endWithConditions)) {
            this.endWithConditions = Maps.newHashMap();
        }
        this.endWithConditions.put(field, value);
        return this;
    }

}
