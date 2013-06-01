package com.authority.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

/**
 * Web层相关的实用工具类
 * 
 * @author 
 * @date 2011-12-1 下午3:14:59
 */
public class WebUtils {
	/**
	 * 将请求参数封装为Map<br>
	 * request中的参数t1=1&t1=2&t2=3<br>
	 * 形成的map结构：<br>
	 * key=t1;value[0]=1,value[1]=2<br>
	 * key=t2;value[0]=3<br>
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static HashMap<String, String> getPraramsAsMap(HttpServletRequest request) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		Map map = request.getParameterMap();
		Iterator keyIterator = (Iterator) map.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			String value = ((String[]) (map.get(key)))[0];
			hashMap.put(key, value);
		}
		return hashMap;
	}
	
	/**传入参数 SqlRowSet rs 
	 * return Extjs grid store所需的 metaData ( fields & columns)
	 * */
	public Map<?,?> getmetaData(SqlRowSet rs){
		
		SqlRowSetMetaData data=rs.getMetaData();
		List<Map<String, Object>> list_field=new ArrayList(); //返回数据列名 store 中
		List<Map<String, Object>> list_column=new ArrayList(); //返回显示的列名 grid中
		while(rs.next()){
			for(int i = 1 ; i<= data.getColumnCount() ; i++){    
			//获得所有列的数目及实际列数    
			int columnCount=data.getColumnCount();			
			//获得指定列的列名    
			String columnName = data.getColumnName(i);
			//获得指定列的列值    
			String columnValue = rs.getString(i);
			
			if(!columnName.equals("TITLE")){
				Map<String,Object> map_column=new HashMap<String, Object>();
				map_column.put("header", columnValue);
				map_column.put("dataIndex", columnName);
				list_column.add(map_column);
				
				Map<String,Object> map_field=new HashMap<String, Object>();
				map_field.put("name", columnName);
				list_field.add(map_field);
			}

			/*//获得指定列的数据类型    
			int columnType=data.getColumnType(i);    
			//获得指定列的数据类型名    
			String columnTypeName=data.getColumnTypeName(i);    
			//所在的Catalog名字    
			String catalogName=data.getCatalogName(i);    
			//对应数据类型的类    
			String columnClassName=data.getColumnClassName(i);    
			//在数据库中类型的最大字符个数    
			int columnDisplaySize=data.getColumnDisplaySize(i);    
			//默认的列的标题    
			String columnLabel=data.getColumnLabel(i);    
			//获得列的模式    
			String schemaName=data.getSchemaName(i);    
			//某列类型的精确度(类型的长度)    
			int precision= data.getPrecision(i);    
			//小数点后的位数    
			int scale=data.getScale(i);    
			//获取某列对应的表名    
			String tableName=data.getTableName(i);    
			
			//在数据库中是否为货币型    
			boolean isCurrency=data.isCurrency(i);    
			  
			
			System.out.println(columnCount);
			System.out.println("获得列"+i+"的字段名称:"+columnName);
			System.out.println("获得列"+i+"的字段值:"+columnValue);
			System.out.println("获得列"+i+"的类型,返回SqlType中的编号:"+columnType);
			System.out.println("获得列"+i+"的数据类型名:"+columnTypeName);
			System.out.println("获得列"+i+"所在的Catalog名字:"+catalogName);
			System.out.println("获得列"+i+"对应数据类型的类:"+columnClassName);
			System.out.println("获得列"+i+"在数据库中类型的最大字符个数:"+columnDisplaySize);
			System.out.println("获得列"+i+"的默认的列的标题:"+columnLabel);
			System.out.println("获得列"+i+"的模式:"+schemaName);
			System.out.println("获得列"+i+"类型的精确度(类型的长度):"+precision);
			System.out.println("获得列"+i+"小数点后的位数:"+scale);
			System.out.println("获得列"+i+"对应的表名:"+tableName);
			System.out.println("获得列"+i+"在数据库中是否为货币型:"+isCurrency);*/
			
			}    
		}
		
		Map<String,Object> metaData=new HashMap<String, Object>();
		
		metaData.put("root", "rows");
		
		/*Map<String,Object> totalProperty =new HashMap<String, Object>();
		totalProperty.put("totalProperty", "results");
		
		Map<String,Object> successProperty =new HashMap<String, Object>();
		successProperty.put("successProperty", "success");*/
		
		metaData.put("fields", list_field);
		metaData.put("columns", list_column);
		
		return metaData;
	}
	
}
