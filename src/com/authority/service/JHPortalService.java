package com.authority.service;

import java.util.List;
import java.util.Map;

public interface JHPortalService {
	
	String Oracle2Sqlserver(List<Map<String,Object>> listData,String listColumn,String tableName, String PRIMARY_KEY);
	
	
	String Sqlserver2Oracle(List<Map<String,Object>> listData,String listColumn,String tableName, String PRIMARY_KEY);

}
