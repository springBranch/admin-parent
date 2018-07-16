package com.admin.client.utils;


import java.util.ArrayList;
import java.util.List;


/**
 * 分页Util
 * 
 * @author LJ
 * @date 2015年7月24日
 */
public class PageUtil {

	public static <T> List<T> getPageList(List<T> list, Integer page, Integer rows) {
		
		List<T> subList = new ArrayList<>();
		if(list!=null && list.size()>0){
			
			int intPage = page == null || page == 0 ? 1 : page;  
			int number = rows == null || rows == 0 ? 10 : rows;  
			int start = (intPage - 1) * number;  
			int totalCount = list.size();  
			int pageCount = totalCount % number == 0 ? totalCount / number : totalCount / number +1;   
			if(intPage < pageCount){  
				subList = list.subList(start,start+number); 
			}else {  
				subList = list.subList(start,totalCount );  
				
			}  
		}

		return subList;
	}

	/**
	 * 计算出起始值
	 * @param pageIndex 页码
	 * @param pageSize 页面大小
	 * @param total 总行数
	 * @return 起始位置
	 * 说明：mysql数据库start是以0开始的
	 */
	public static int calcStart(int pageIndex, int pageSize, int total) {
		int totalPage = (total%pageSize==0) ? total/pageSize : (total/pageSize+1);
		int start = 0;
		if(pageIndex < 1 || totalPage < 1){
			start = 0;
		} else if (pageIndex > totalPage){
			start = (totalPage - 1) * pageSize;
		} else {
			start = (pageIndex - 1) * pageSize;
		}
		return start;
	}
	
}
