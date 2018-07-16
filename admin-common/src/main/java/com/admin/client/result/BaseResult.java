package com.admin.client.result;


import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author by songjie
 */
public class BaseResult implements Serializable {
    private static final String SUC_MSG = "OK";
    private boolean status;
    private String code;
    private String message;
    private Object data;


    public BaseResult(String code, String message) {
        this.status = true;
        this.code = code;
        this.message = message;
    }

    public BaseResult(boolean status, String statusCode, String message, Object data) {
        this.status = status;
        this.code = statusCode;
        this.message = message;
        this.data = data;
    }

    public BaseResult(Object data) {
        this.data = data;
        this.status = true;
    }



    public static BaseResult failed(String code, String message) {
        return new BaseResult(false, code, message, null);
    }
    public static BaseResult failed( String message) {
        return new BaseResult(false, null, message, null);
    }


    public static BaseResult success() {
        return new BaseResult(true, null, SUC_MSG, null);
    }

    public static BaseResult success(Object data) {
        return new BaseResult(true, null, SUC_MSG, data);
    }

    public static BaseResult sucMsg(String message) {
        return new BaseResult(true, null, message, null);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
