package com.admin.client.constant;

/**
 * 角色枚举 
 * @author LJ
 * @date 2017年3月17日 上午9:41:26 
 *
 */
public enum RoleInfoEnum {
	
	ROLE_INFO_1("超级管理员", 1),
	ROLE_INFO_2("产品运营", 2);  
	
	private String key;     
	private Integer value;
	
	private RoleInfoEnum(String key, Integer value) {       
		this.key = key;       
		this.value = value;     
	}       
	public String getKey() {
		return key;     
	}  
	public static String getKey(Integer value) {  
        for (RoleInfoEnum p : RoleInfoEnum.values()) {  
            if (p.getValue() == value) {  
                return p.key;  
            }  
        }  
        return null;  
    }  
	
	public Integer getValue() {
		return value;     
	}    
}
