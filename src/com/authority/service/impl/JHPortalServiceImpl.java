package com.authority.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.authority.service.JHPortalService;

@Service
public class JHPortalServiceImpl implements JHPortalService {
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;
	
	
	@Resource(name="jdbcTemplate2")
	private JdbcTemplate jdbcTemplate2;
	
	@Resource(name="njdbcTemplate2")
	private NamedParameterJdbcTemplate njdbcTemplate2;
	
	@Override	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class },value="transactionManager2")
	public String Oracle2Sqlserver(List<Map<String, Object>> listData,String listColumn,String tableName) {
		try {
			String insert = "insert into "+tableName+"(";
			String update = "update "+tableName+ " set ";
			String Field1 ="",Field2="",Field3="";
			String[] Columns = listColumn.split(",");
			for (int i = 0; i < Columns.length; i++) {
				Field1 = Field1+Columns[i]+",";
				Field2 = Field2+"convert(nvarchar(500),:"+Columns[i].toUpperCase()+"),";
				Field3 = Field3+Columns[i]+"=convert(nvarchar(500),:"+Columns[i]+"),";
			}
			insert = insert + StringUtils.removeEnd(Field1, ",")+") " +
					"select "+StringUtils.removeEnd(Field2, ",")+" where not exists(" +
					"select 'x' from "+tableName+" where id = :ID )";
			
			update = update + StringUtils.removeEnd(Field3, ",")+" where ID = :ID ";
			
			for (Map<String, Object> map : listData) {
				if(njdbcTemplate2.update(insert, map)==0){
					njdbcTemplate2.update(update, map);
				}
			}
			
		} catch (Exception e) {
			System.out.println(e);
			throw new RuntimeException(e);
			// TODO: handle exception
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String Sqlserver2Oracle(List<Map<String, Object>> listData,String listColumn,String tableName) {
		try {
			String insert = "insert into "+tableName+"(";
			String update = "update "+tableName+ " set ";
			String Field1 ="",Field2="",Field3="";
			String[] Columns = listColumn.split(",");
			for (int i = 0; i < Columns.length; i++) {
				Field1 = Field1+Columns[i]+",";
				Field2 = Field2+":"+Columns[i].toUpperCase()+",";
				Field3 = Field3+Columns[i]+"=:"+Columns[i]+",";
			}
			insert = insert + StringUtils.removeEnd(Field1, ",")+") " +
					"select "+StringUtils.removeEnd(Field2, ",")+" from dual where not exists(" +
					"select 'x' from "+tableName+" where id = :ID )";
			
			update = update + StringUtils.removeEnd(Field3, ",")+" where ID = :ID ";
			
			for (Map<String, Object> map : listData) {
				if(njdbcTemplate.update(insert, map)==0){
					njdbcTemplate.update(update, map);
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
			// TODO: handle exception
		}
		return null;
	}

}
