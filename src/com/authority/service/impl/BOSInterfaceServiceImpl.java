package com.authority.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.oracore.OracleType;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.authority.common.utils.WebUtils;
import com.authority.service.BOSInterfaceService;
import com.googlecode.jtype.Types;
import com.sun.faces.renderkit.html_basic.HtmlBasicRenderer.Param;

@Service
public class BOSInterfaceServiceImpl implements BOSInterfaceService {

	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;
	
	private static final Logger logger = LoggerFactory.getLogger(BOSInterfaceServiceImpl.class);
	
	private final int len = 500;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String M_SALE(List<String[]> DataArray,Map<String,Object> fieldmatchMap,String Account) {
		String msg = "";
		int result = 0;
		try{
			String insert ="",query="",update="",delete="";
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String addtime =  sdf.format(new Date());
			SimpleDateFormat format_date_1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format_date_2 = new SimpleDateFormat("yyyyMMdd");
			
			//TBusRetail 抬头明细表 // 先直接插入数据
			for (String[] strings : DataArray) {
				
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("DOCNO", strings[Integer.parseInt(fieldmatchMap.get("DOCNO").toString())]);
				paramMap.put("BILLDATE", format_date_2.format(format_date_1.parse(strings[Integer.parseInt(fieldmatchMap.get("BILLDATE").toString())])));
				paramMap.put("DOCTYPE", strings[Integer.parseInt(fieldmatchMap.get("DOCTYPE").toString())]);
				paramMap.put("C_SALETYPE_ID", strings[Integer.parseInt(fieldmatchMap.get("C_SALETYPE_ID").toString())]);
				paramMap.put("SALETYPE", strings[Integer.parseInt(fieldmatchMap.get("SALETYPE").toString())]);
				paramMap.put("C_STORE_ID", strings[Integer.parseInt(fieldmatchMap.get("C_STORE_ID").toString())]);
				paramMap.put("C_DEST_ID", strings[Integer.parseInt(fieldmatchMap.get("C_DEST_ID").toString())]);
				paramMap.put("DATEOUT", format_date_2.format(format_date_1.parse(strings[Integer.parseInt(fieldmatchMap.get("DATEOUT").toString())])));
				paramMap.put("DATEIN", format_date_2.format(format_date_1.parse(strings[Integer.parseInt(fieldmatchMap.get("DATEIN").toString())])));
				paramMap.put("STATUSERID", strings[Integer.parseInt(fieldmatchMap.get("STATUSERID").toString())]);
				paramMap.put("STATUSTIME", strings[Integer.parseInt(fieldmatchMap.get("STATUSTIME").toString())]);
				paramMap.put("SKU", strings[Integer.parseInt(fieldmatchMap.get("SKU").toString())]); 
				paramMap.put("QTY", strings[Integer.parseInt(fieldmatchMap.get("QTY").toString())]);
				paramMap.put("QTYOUT", strings[Integer.parseInt(fieldmatchMap.get("QTYOUT").toString())]);
				paramMap.put("QTYIN", strings[Integer.parseInt(fieldmatchMap.get("QTYIN").toString())]);
				paramMap.put("STATUS", "0");
				paramMap.put("NOTE", "");
				paramMap.put("ADDWHO", Account);
				
				//判断明细插入是否重复
				insert ="insert into M_SALE_TMP(ID, DOCNO, BILLDATE, SALETYPE, C_STORE_ID, C_DEST_ID, DATEOUT, DATEIN, STATUSERID, STATUSTIME, SKU, QTY, QTYOUT, QTYIN, DOCTYPE, C_SALETYPE_ID, ADDWHO, ADDTIME, NOTE, STATUS)  " +
						"select sys_guid() ID, :DOCNO, :BILLDATE, :SALETYPE, :C_STORE_ID, :C_DEST_ID, :DATEOUT, :DATEIN, :STATUSERID, :STATUSTIME, :SKU, :QTY, :QTYOUT, :QTYIN, :DOCTYPE, :C_SALETYPE_ID, :ADDWHO,sysdate ADDTIME, :NOTE, :STATUS " +
						"from dual where not exists(" +
						"select 'x' from M_SALE_TMP where DOCNO = :DOCNO and STATUS ='1' )";
				if(njdbcTemplate.update(insert, paramMap)==0){
					insert ="insert into M_SALE_TMP(ID, DOCNO, BILLDATE, SALETYPE, C_STORE_ID, C_DEST_ID, DATEOUT, DATEIN, STATUSERID, STATUSTIME, SKU, QTY, QTYOUT, QTYIN, DOCTYPE, C_SALETYPE_ID, ADDWHO, ADDTIME, NOTE, STATUS)  " +
							"select sys_guid() ID, :DOCNO, :BILLDATE, :SALETYPE, :C_STORE_ID, :C_DEST_ID, :DATEOUT, :DATEIN, :STATUSERID, :STATUSTIME, :SKU, :QTY, :QTYOUT, :QTYIN, :DOCTYPE, :C_SALETYPE_ID, :ADDWHO,sysdate ADDTIME, :NOTE, :STATUS " +
							"from dual ";
					paramMap.put("NOTE", "销售单插入重复");		
					paramMap.put("STATUS", "2");
					njdbcTemplate.update(insert, paramMap);
				}
				
			}
			
			//循环 m_retail_tmp 表,执行事务----插入零售单，子事务中运行
			query = "SELECT DISTINCT DOCNO,ADDWHO FROM M_SALE_TMP WHERE STATUS='0' AND ADDWHO =:ADDWHO ";
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ADDWHO", Account);
			List<Map<String,Object>> list_m_retail_tmp = njdbcTemplate.queryForList(query, paramMap);
			for (Map<String, Object> map : list_m_retail_tmp) {
				String DOCNO = map.get("DOCNO").toString();
				//执行存储过程事务,嵌套事务
				try {
					String r_message = M_SALE_CHILD(DOCNO);
					update = "update M_SALE_TMP set status ='1',note = :NOTE where docno =:DOCNO and addwho =:ADDWHO and status ='0' ";
					map.put("NOTE", r_message);
				} catch (Exception e) {
					if(e.toString().length()>len)						
						map.put("NOTE",StringUtils.substring(e.toString(), 0, len));
					else 
						map.put("NOTE",e.toString());
					update = "update M_SALE_TMP set status ='2',note = :NOTE where docno =:DOCNO and addwho =:ADDWHO ";
					// TODO: handle exception
				}
				
				njdbcTemplate.update(update, map);
				
			}
			
			result = 1 ;
		}catch(Exception e){
			String str = "处理执行过程中出错`请检查数据是否有误或联系管理员";
			msg = str ;
			System.out.println(e.toString());
			logger.error("M_SALE:"+e.toString());
			throw new RuntimeException(e);
		}
		// TODO Auto-generated method stub
		return result > 0 ? "1" : msg;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.NESTED, rollbackFor = { Exception.class })
	public String  M_SALE_CHILD(final String DOCNO){
		String msg = "";
		int result = 0;
		try {
			String procedure = "{call P_BASE_M_SALE(?,?)}";
			@SuppressWarnings("unchecked")
			Map<String,Object> MAP_P_BASE_M_SALE = (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {  
	            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
	                cs.setString(1, DOCNO);
	                cs.registerOutParameter(2, OracleTypes.VARCHAR);	
	                cs.execute();
	                Map<String,Object> map = new HashMap<String, Object>();  
	                map.put("r_message", cs.getString(2));
	                return map;
	            }
	        }); 
			
			msg = MAP_P_BASE_M_SALE.get("r_message").toString();
			
			result = 1;
			
		} catch (Exception e) {
			msg = e.toString();
			throw new RuntimeException(e);
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return msg;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String M_RETAIL(List<String[]> DataArray,Map<String,Object> fieldmatchMap,String Account) {
		String msg = "";
		int result = 0;
		try{
			String insert ="",query="",update="",delete="";
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String addtime =  sdf.format(new Date());
			SimpleDateFormat format_date_1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format_date_2 = new SimpleDateFormat("yyyyMMdd");
			
			//TBusRetail 抬头明细表 // 先直接插入数据
			for (String[] strings : DataArray) {
				
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("DOCNO", strings[Integer.parseInt(fieldmatchMap.get("DOCNO").toString())]);
				paramMap.put("BILLDATE", format_date_2.format(format_date_1.parse(strings[Integer.parseInt(fieldmatchMap.get("BILLDATE").toString())])));
				paramMap.put("RETAILBILLTYPE", strings[Integer.parseInt(fieldmatchMap.get("RETAILBILLTYPE").toString())]);
				paramMap.put("STORE", strings[Integer.parseInt(fieldmatchMap.get("STORE").toString())]);
				paramMap.put("STATUSERID", strings[Integer.parseInt(fieldmatchMap.get("STATUSERID").toString())]);
				paramMap.put("STATUSTIME", strings[Integer.parseInt(fieldmatchMap.get("STATUSTIME").toString())]);
				paramMap.put("SKU", strings[Integer.parseInt(fieldmatchMap.get("SKU").toString())]); 
				paramMap.put("QTY", strings[Integer.parseInt(fieldmatchMap.get("QTY").toString())]);
				paramMap.put("STATUS", "0");
				paramMap.put("NOTE", "");
				paramMap.put("ADDWHO", Account);
				
				//判断明细插入是否重复
				insert ="insert into m_retail_tmp(ID, DOCNO, BILLDATE, RETAILBILLTYPE, STORE, STATUSERID, STATUSTIME, SKU, QTY, ADDTIME, ADDWHO, NOTE, STATUS)  " +
						"select sys_guid() ID, :DOCNO, :BILLDATE, :RETAILBILLTYPE, :STORE, :STATUSERID, :STATUSTIME, :SKU, :QTY,sysdate ADDTIME, :ADDWHO, :NOTE, :STATUS " +
						"from dual where not exists(" +
						"select 'x' from m_retail_tmp where DOCNO = :DOCNO and STATUS ='1' )";
				if(njdbcTemplate.update(insert, paramMap)==0){
					insert ="insert into m_retail_tmp(ID, DOCNO, BILLDATE, RETAILBILLTYPE, STORE, STATUSERID, STATUSTIME, SKU, QTY, ADDTIME, ADDWHO, NOTE, STATUS)  " +
							"select sys_guid() ID, :DOCNO, :BILLDATE, :RETAILBILLTYPE, :STORE, :STATUSERID, :STATUSTIME, :SKU, :QTY,sysdate ADDTIME, :ADDWHO, :NOTE, :STATUS " +
							"from dual ";
					paramMap.put("NOTE", "零售单插入重复");		
					paramMap.put("STATUS", "2");
					njdbcTemplate.update(insert, paramMap);
				}
				
			}
			
			//循环 m_retail_tmp 表,执行事务----插入零售单，子事务中运行
			query = "SELECT DISTINCT DOCNO,ADDWHO FROM M_RETAIL_TMP WHERE STATUS='0' AND ADDWHO =:ADDWHO ";
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ADDWHO", Account);
			List<Map<String,Object>> list_m_retail_tmp = njdbcTemplate.queryForList(query, paramMap);
			for (Map<String, Object> map : list_m_retail_tmp) {
				String DOCNO = map.get("DOCNO").toString();
				//执行存储过程事务,嵌套事务
				/*ConnectionHolder conHolder = (ConnectionHolder) TransactionSynchronizationManager.getResource(jdbcTemplate.getDataSource());
				Connection conn =conHolder.getConnection();
				Savepoint sp=null;
				try {
					sp = conn.setSavepoint();
					M_RETAIL_CHILD(docno);
					update = "update M_RETAIL_TMP set status ='1' where docno =:DOCNO ";
					
				} catch (Exception e) {
					try {
						conn.rollback(sp);
					} catch (Exception e2) {
						// TODO: handle exception
					}
					
					map.put("NOTE", e.toString());
					update = "update M_RETAIL_TMP set status ='2',note = :NOTE where docno =:DOCNO ";
					
					// TODO: handle exception
				}*/
				
				try {
					String r_message = M_RETAIL_CHILD(DOCNO);
					update = "update M_RETAIL_TMP set status ='1',note = :NOTE where docno =:DOCNO and addwho =:ADDWHO  and status ='0' ";
					map.put("NOTE", r_message);
				} catch (Exception e) {
					if(e.toString().length()>len)						
						map.put("NOTE",StringUtils.substring(e.toString(), 0, len));
					else 
						map.put("NOTE", e.toString());
					update = "update M_RETAIL_TMP set status ='2',note = :NOTE where docno =:DOCNO and addwho =:ADDWHO ";
					// TODO: handle exception
				}
				
				njdbcTemplate.update(update, map);
				
			}
			
			result = 1 ;
		}catch(Exception e){
			String str = "处理执行过程中出错`请检查数据是否有误或联系管理员";
			msg = str ;
			System.out.println(e.toString());
			logger.error("M_RETAIL:"+e.toString());
			throw new RuntimeException(e);
		}
		// TODO Auto-generated method stub
		return result > 0 ? "1" : msg;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.NESTED, rollbackFor = { Exception.class })
	public String  M_RETAIL_CHILD(final String DOCNO){
		String msg = "";
		int result = 0;
		try {
			String procedure = "{call P_BASE_M_RETAIL(?,?)}";
			@SuppressWarnings("unchecked")
			Map<String,Object> MAP_P_BASE_M_RETAIL = (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {  
	            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
	                cs.setString(1, DOCNO);
	                cs.registerOutParameter(2, OracleTypes.VARCHAR);	
	                cs.execute();
	                Map<String,Object> map = new HashMap<String, Object>();  
	                map.put("r_message", cs.getString(2));
	                return map;
	            }
	        }); 
			
			msg = MAP_P_BASE_M_RETAIL.get("r_message").toString();
			
			result = 1;
			
		} catch (Exception e) {
			msg = e.toString();
			throw new RuntimeException(e);
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return msg;
	}
	
	

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String M_RET_SALE(List<String[]> DataArray,Map<String,Object> fieldmatchMap,String Account) {
		String msg = "";
		int result = 0;
		try{
			String insert ="",query="",update="",delete="";
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String addtime =  sdf.format(new Date());
			SimpleDateFormat format_date_1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format_date_2 = new SimpleDateFormat("yyyyMMdd");
			
			//TBusRetail 抬头明细表 // 先直接插入数据
			for (String[] strings : DataArray) {
				
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("DOCNO", strings[Integer.parseInt(fieldmatchMap.get("DOCNO").toString())]);
				paramMap.put("BILLDATE", format_date_2.format(format_date_1.parse(strings[Integer.parseInt(fieldmatchMap.get("BILLDATE").toString())])));
				paramMap.put("RETSALETYPE", strings[Integer.parseInt(fieldmatchMap.get("RETSALETYPE").toString())]);
				paramMap.put("C_ORIG_ID", strings[Integer.parseInt(fieldmatchMap.get("C_ORIG_ID").toString())]);
				paramMap.put("C_STORE_ID", strings[Integer.parseInt(fieldmatchMap.get("C_STORE_ID").toString())]);
				paramMap.put("DATEOUT",  format_date_2.format(format_date_1.parse(strings[Integer.parseInt(fieldmatchMap.get("DATEOUT").toString())])));
				paramMap.put("DATEIN",  format_date_2.format(format_date_1.parse(strings[Integer.parseInt(fieldmatchMap.get("DATEIN").toString())])));
				paramMap.put("STATUSERID", strings[Integer.parseInt(fieldmatchMap.get("STATUSERID").toString())]);
				paramMap.put("STATUSTIME", strings[Integer.parseInt(fieldmatchMap.get("STATUSTIME").toString())]);
				paramMap.put("SKU", strings[Integer.parseInt(fieldmatchMap.get("SKU").toString())]); 
				paramMap.put("QTY", strings[Integer.parseInt(fieldmatchMap.get("QTY").toString())]);
				paramMap.put("QTYOUT", strings[Integer.parseInt(fieldmatchMap.get("QTYOUT").toString())]);
				paramMap.put("QTYIN", strings[Integer.parseInt(fieldmatchMap.get("QTYIN").toString())]);
				paramMap.put("STATUS", "0");
				paramMap.put("NOTE", "");
				paramMap.put("ADDWHO", Account);
				
				//判断明细插入是否重复
				insert ="insert into M_RET_SALE_TMP(ID, DOCNO,BILLDATE, RETSALETYPE, C_ORIG_ID, C_STORE_ID, DATEOUT, DATEIN, STATUSERID, STATUSTIME, SKU, QTY, QTYOUT, QTYIN, ADDWHO, ADDTIME, NOTE, STATUS)  " +
						"select sys_guid() ID, :DOCNO, :BILLDATE, :RETSALETYPE, :C_ORIG_ID, :C_STORE_ID, :DATEOUT, :DATEIN, :STATUSERID, :STATUSTIME, :SKU, :QTY, :QTYOUT, :QTYIN, :ADDWHO,sysdate ADDTIME, :NOTE, :STATUS " +
						"from dual where not exists(" +
						"select 'x' from M_RET_SALE_TMP where DOCNO = :DOCNO and STATUS ='1' )";
				if(njdbcTemplate.update(insert, paramMap)==0){
					insert ="insert into M_RET_SALE_TMP(ID, DOCNO,BILLDATE, RETSALETYPE, C_ORIG_ID, C_STORE_ID, DATEOUT, DATEIN, STATUSERID, STATUSTIME, SKU, QTY, QTYOUT, QTYIN, ADDWHO, ADDTIME, NOTE, STATUS)  " +
							"select sys_guid() ID, :DOCNO, :BILLDATE, :RETSALETYPE, :C_ORIG_ID, :C_STORE_ID, :DATEOUT, :DATEIN, :STATUSERID, :STATUSTIME, :SKU, :QTY, :QTYOUT, :QTYIN, :ADDWHO,sysdate ADDTIME, :NOTE, :STATUS " +
							"from dual ";
					paramMap.put("NOTE", "销售退货单插入重复");		
					paramMap.put("STATUS", "2");
					njdbcTemplate.update(insert, paramMap);
				}
				
			}
			
			//循环 m_retail_tmp 表,执行事务----插入零售单，子事务中运行
			query = "SELECT DISTINCT DOCNO,ADDWHO FROM M_RET_SALE_TMP WHERE STATUS='0' AND ADDWHO =:ADDWHO ";
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ADDWHO", Account);
			List<Map<String,Object>> list_m_retail_tmp = njdbcTemplate.queryForList(query, paramMap);
			for (Map<String, Object> map : list_m_retail_tmp) {
				String DOCNO = map.get("DOCNO").toString();
				//执行存储过程事务,嵌套事务
				try {
					String r_message = M_RET_SALE_CHILD(DOCNO);
					update = "update M_RET_SALE_TMP set status ='1',note = :NOTE where docno =:DOCNO and addwho =:ADDWHO  and status ='0' ";
					map.put("NOTE", r_message);
				} catch (Exception e) {
					if(e.toString().length()>len)						
						map.put("NOTE",StringUtils.substring(e.toString(), 0, len));
					else 
						map.put("NOTE",e.toString());
					update = "update M_RET_SALE_TMP set status ='2',note = :NOTE where docno =:DOCNO and addwho =:ADDWHO ";
					// TODO: handle exception
				}
				
				njdbcTemplate.update(update, map);
				
			}
			
			result = 1 ;
		}catch(Exception e){
			String str = "处理执行过程中出错`请检查数据是否有误或联系管理员";
			msg = str ;
			System.out.println(e.toString());
			logger.error("M_RET_SALE:"+e.toString());
			throw new RuntimeException(e);
		}
		// TODO Auto-generated method stub
		return result > 0 ? "1" : msg;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.NESTED, rollbackFor = { Exception.class })
	public String  M_RET_SALE_CHILD(final String DOCNO){
		String msg = "";
		int result = 0;
		try {
			String procedure = "{call P_BASE_M_RET_SALE(?,?)}";
			@SuppressWarnings("unchecked")
			Map<String,Object> MAP_P_BASE_M_RET_SALE = (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {  
	            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
	                cs.setString(1, DOCNO);
	                cs.registerOutParameter(2, OracleTypes.VARCHAR);	
	                cs.execute();
	                Map<String,Object> map = new HashMap<String, Object>();  
	                map.put("r_message", cs.getString(2));
	                return map;
	            }
	        }); 
			
			msg = MAP_P_BASE_M_RET_SALE.get("r_message").toString();
			
			result = 1;
			
		} catch (Exception e) {
			msg = e.toString();
			throw new RuntimeException(e);
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return msg;
	}

	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String M_RET_PUR(List<String[]> DataArray,Map<String,Object> fieldmatchMap,String Account) {
		String msg = "";
		int result = 0;
		try{
			String insert ="",query="",update="",delete="";
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String addtime =  sdf.format(new Date());
			SimpleDateFormat format_date_1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format_date_2 = new SimpleDateFormat("yyyyMMdd");
			
			//TBusRetail 抬头明细表 // 先直接插入数据
			for (String[] strings : DataArray) {
				
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("DOCNO", strings[Integer.parseInt(fieldmatchMap.get("DOCNO").toString())]);
				paramMap.put("BILLDATE", format_date_2.format(format_date_1.parse(strings[Integer.parseInt(fieldmatchMap.get("BILLDATE").toString())])));
				paramMap.put("RETSALETYPE", strings[Integer.parseInt(fieldmatchMap.get("RETSALETYPE").toString())]);
				paramMap.put("C_ORIG_ID", strings[Integer.parseInt(fieldmatchMap.get("C_ORIG_ID").toString())]);
				paramMap.put("C_STORE_ID", strings[Integer.parseInt(fieldmatchMap.get("C_STORE_ID").toString())]);
				paramMap.put("DATEOUT",  format_date_2.format(format_date_1.parse(strings[Integer.parseInt(fieldmatchMap.get("DATEOUT").toString())])));
				paramMap.put("DATEIN",  format_date_2.format(format_date_1.parse(strings[Integer.parseInt(fieldmatchMap.get("DATEIN").toString())])));
				paramMap.put("STATUSERID", strings[Integer.parseInt(fieldmatchMap.get("STATUSERID").toString())]);
				paramMap.put("STATUSTIME", strings[Integer.parseInt(fieldmatchMap.get("STATUSTIME").toString())]);
				paramMap.put("SKU", strings[Integer.parseInt(fieldmatchMap.get("SKU").toString())]); 
				paramMap.put("QTY", strings[Integer.parseInt(fieldmatchMap.get("QTY").toString())]);
				paramMap.put("QTYOUT", strings[Integer.parseInt(fieldmatchMap.get("QTYOUT").toString())]);
				paramMap.put("QTYIN", strings[Integer.parseInt(fieldmatchMap.get("QTYIN").toString())]);
				paramMap.put("STATUS", "0");
				paramMap.put("NOTE", "");
				paramMap.put("ADDWHO", Account);
				
				//判断明细插入是否重复
				insert ="insert into M_RET_PUR_TMP(ID, DOCNO,BILLDATE, RETSALETYPE, C_ORIG_ID, C_STORE_ID, DATEOUT, DATEIN, STATUSERID, STATUSTIME, SKU, QTY, QTYOUT, QTYIN, ADDWHO, ADDTIME, NOTE, STATUS)  " +
						"select sys_guid() ID, :DOCNO, :BILLDATE, :RETSALETYPE, :C_ORIG_ID, :C_STORE_ID, :DATEOUT, :DATEIN, :STATUSERID, :STATUSTIME, :SKU, :QTY, :QTYOUT, :QTYIN, :ADDWHO,sysdate ADDTIME, :NOTE, :STATUS " +
						"from dual where not exists(" +
						"select 'x' from M_RET_PUR_TMP where DOCNO = :DOCNO and STATUS ='1' )";
				if(njdbcTemplate.update(insert, paramMap)==0){
					insert ="insert into M_RET_PUR_TMP(ID, DOCNO,BILLDATE, RETSALETYPE, C_ORIG_ID, C_STORE_ID, DATEOUT, DATEIN, STATUSERID, STATUSTIME, SKU, QTY, QTYOUT, QTYIN, ADDWHO, ADDTIME, NOTE, STATUS)  " +
							"select sys_guid() ID, :DOCNO, :BILLDATE, :RETSALETYPE, :C_ORIG_ID, :C_STORE_ID, :DATEOUT, :DATEIN, :STATUSERID, :STATUSTIME, :SKU, :QTY, :QTYOUT, :QTYIN, :ADDWHO,sysdate ADDTIME, :NOTE, :STATUS " +
							"from dual ";
					paramMap.put("NOTE", "采购退货单插入重复");		
					paramMap.put("STATUS", "2");
					njdbcTemplate.update(insert, paramMap);
				}
				
			}
			
			//循环 m_retail_tmp 表,执行事务----插入零售单，子事务中运行
			query = "SELECT DISTINCT DOCNO,ADDWHO FROM M_RET_PUR_TMP WHERE STATUS='0' AND ADDWHO =:ADDWHO ";
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ADDWHO", Account);
			List<Map<String,Object>> list_m_retail_tmp = njdbcTemplate.queryForList(query, paramMap);
			for (Map<String, Object> map : list_m_retail_tmp) {
				String DOCNO = map.get("DOCNO").toString();
				//执行存储过程事务,嵌套事务
				try {
					String r_message = M_RET_PUR_CHILD(DOCNO);
					update = "update M_RET_PUR_TMP set status ='1',note = :NOTE where docno =:DOCNO and addwho =:ADDWHO  and status ='0' ";
					map.put("NOTE", r_message);
				} catch (Exception e) {
					if(e.toString().length()>len)						
						map.put("NOTE",StringUtils.substring(e.toString(), 0, len));
					else 
						map.put("NOTE",e.toString());
					update = "update M_RET_PUR_TMP set status ='2',note = :NOTE where docno =:DOCNO and addwho =:ADDWHO ";
					// TODO: handle exception
				}
				
				njdbcTemplate.update(update, map);
				
			}
			
			result = 1 ;
		}catch(Exception e){
			String str = "处理执行过程中出错`请检查数据是否有误或联系管理员";
			msg = str ;
			System.out.println(e.toString());
			logger.error("M_RET_PUR:"+e.toString());
			throw new RuntimeException(e);
		}
		// TODO Auto-generated method stub
		return result > 0 ? "1" : msg;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.NESTED, rollbackFor = { Exception.class })
	public String  M_RET_PUR_CHILD(final String DOCNO){
		String msg = "";
		int result = 0;
		try {
			String procedure = "{call P_BASE_M_RET_PUR(?,?)}";
			@SuppressWarnings("unchecked")
			Map<String,Object> MAP_P_BASE_M_RET_PUR = (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {  
	            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
	                cs.setString(1, DOCNO);
	                cs.registerOutParameter(2, OracleTypes.VARCHAR);	
	                cs.execute();
	                Map<String,Object> map = new HashMap<String, Object>();  
	                map.put("r_message", cs.getString(2));
	                return map;
	            }
	        }); 
			
			msg = MAP_P_BASE_M_RET_PUR.get("r_message").toString();
			
			result = 1;
			
		} catch (Exception e) {
			msg = e.toString();
			throw new RuntimeException(e);
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return msg;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String M_TRANSFER(List<String[]> DataArray,Map<String,Object> fieldmatchMap,String Account) {
		String msg = "";
		int result = 0;
		try{
			String insert ="",query="",update="",delete="";
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String addtime =  sdf.format(new Date());
			SimpleDateFormat format_date_1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format_date_2 = new SimpleDateFormat("yyyyMMdd");
			
			//TBusRetail 抬头明细表 // 先直接插入数据
			for (String[] strings : DataArray) {
				
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("DOCNO", strings[Integer.parseInt(fieldmatchMap.get("DOCNO").toString())]);
				paramMap.put("BILLDATE", format_date_2.format(format_date_1.parse(strings[Integer.parseInt(fieldmatchMap.get("BILLDATE").toString())])));
				paramMap.put("TRANSFERTYPE", strings[Integer.parseInt(fieldmatchMap.get("TRANSFERTYPE").toString())]);
				paramMap.put("C_ORIG_ID", strings[Integer.parseInt(fieldmatchMap.get("C_ORIG_ID").toString())]);
				paramMap.put("C_DEST_ID", strings[Integer.parseInt(fieldmatchMap.get("C_DEST_ID").toString())]);
				paramMap.put("DATEOUT", format_date_2.format(format_date_1.parse(strings[Integer.parseInt(fieldmatchMap.get("DATEOUT").toString())])));
				paramMap.put("DATEIN", format_date_2.format(format_date_1.parse(strings[Integer.parseInt(fieldmatchMap.get("DATEIN").toString())])));
				paramMap.put("STATUSERID", strings[Integer.parseInt(fieldmatchMap.get("STATUSERID").toString())]);
				paramMap.put("STATUSTIME", strings[Integer.parseInt(fieldmatchMap.get("STATUSTIME").toString())]);
				paramMap.put("SKU", strings[Integer.parseInt(fieldmatchMap.get("SKU").toString())]); 
				paramMap.put("QTY", strings[Integer.parseInt(fieldmatchMap.get("QTY").toString())]);
				paramMap.put("QTYOUT", strings[Integer.parseInt(fieldmatchMap.get("QTYOUT").toString())]);
				paramMap.put("QTYIN", strings[Integer.parseInt(fieldmatchMap.get("QTYIN").toString())]);
				paramMap.put("STATUS", "0");
				paramMap.put("NOTE", "");
				paramMap.put("ADDWHO", Account);
				
				//判断明细插入是否重复
				insert ="insert into M_TRANSFER_TMP(ID, DOCNO,BILLDATE, TRANSFERTYPE, C_ORIG_ID, C_DEST_ID, DATEOUT, DATEIN, STATUSERID, STATUSTIME, SKU, QTY, QTYOUT, QTYIN, ADDWHO, ADDTIME, NOTE, STATUS)  " +
						"select sys_guid() ID, :DOCNO, :BILLDATE, :TRANSFERTYPE, :C_ORIG_ID, :C_DEST_ID, :DATEOUT, :DATEIN, :STATUSERID, :STATUSTIME, :SKU, :QTY, :QTYOUT, :QTYIN, :ADDWHO,sysdate ADDTIME, :NOTE, :STATUS " +
						"from dual where not exists(" +
						"select 'x' from M_TRANSFER_TMP where DOCNO = :DOCNO and STATUS ='1' )";
				if(njdbcTemplate.update(insert, paramMap)==0){
					insert ="insert into M_TRANSFER_TMP(ID, DOCNO,BILLDATE, TRANSFERTYPE, C_ORIG_ID, C_DEST_ID, DATEOUT, DATEIN, STATUSERID, STATUSTIME, SKU, QTY, QTYOUT, QTYIN, ADDWHO, ADDTIME, NOTE, STATUS)  " +
							"select sys_guid() ID, :DOCNO, :BILLDATE, :TRANSFERTYPE, :C_ORIG_ID, :C_DEST_ID, :DATEOUT, :DATEIN, :STATUSERID, :STATUSTIME, :SKU, :QTY, :QTYOUT, :QTYIN, :ADDWHO,sysdate ADDTIME, :NOTE, :STATUS " +
							"from dual ";
					paramMap.put("NOTE", "调拨单插入重复");		
					paramMap.put("STATUS", "2");
					njdbcTemplate.update(insert, paramMap);
				}
				
			}
			
			//循环 m_retail_tmp 表,执行事务----插入零售单，子事务中运行
			query = "SELECT DISTINCT DOCNO,ADDWHO FROM M_TRANSFER_TMP WHERE STATUS='0' AND ADDWHO =:ADDWHO ";
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ADDWHO", Account);
			List<Map<String,Object>> list_m_retail_tmp = njdbcTemplate.queryForList(query, paramMap);
			for (Map<String, Object> map : list_m_retail_tmp) {
				String DOCNO = map.get("DOCNO").toString();
				//执行存储过程事务,嵌套事务
				try {
					String r_message = M_TRANSFER_CHILD(DOCNO);
					update = "update M_TRANSFER_TMP set status ='1',note = :NOTE where docno =:DOCNO and addwho =:ADDWHO  and status ='0'";
					map.put("NOTE", r_message);
				} catch (Exception e) {
					if(e.toString().length()>len)						
						map.put("NOTE",StringUtils.substring(e.toString(), 0, len));
					else 
						map.put("NOTE",e.toString());
					update = "update M_TRANSFER_TMP set status ='2',note = :NOTE where docno =:DOCNO and addwho =:ADDWHO ";
					// TODO: handle exception
				}
				
				njdbcTemplate.update(update, map);
				
			}
			
			result = 1 ;
		}catch(Exception e){
			String str = "处理执行过程中出错`请检查数据是否有误或联系管理员";
			msg = str ;
			System.out.println(e.toString());
			logger.error("M_TRANSFER:"+e.toString());
			throw new RuntimeException(e);
		}
		// TODO Auto-generated method stub
		return result > 0 ? "1" : msg;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.NESTED, rollbackFor = { Exception.class })
	public String  M_TRANSFER_CHILD(final String DOCNO){
		String msg = "";
		int result = 0;
		try {
			String procedure = "{call P_BASE_M_TRANSFER(?,?)}";
			@SuppressWarnings("unchecked")
			Map<String,Object> MAP_P_BASE_M_TRANSFER = (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {  
	            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
	                cs.setString(1, DOCNO);
	                cs.registerOutParameter(2, OracleTypes.VARCHAR);	
	                cs.execute();
	                Map<String,Object> map = new HashMap<String, Object>();  
	                map.put("r_message", cs.getString(2));
	                return map;
	            }
	        }); 
			
			msg = MAP_P_BASE_M_TRANSFER.get("r_message").toString();
			
			result = 1;
			
		} catch (Exception e) {
			msg = e.toString();
			throw new RuntimeException(e);
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return msg;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String M_OTHER_INOUT(List<String[]> DataArray,Map<String,Object> fieldmatchMap,String Account) {
		String msg = "";
		int result = 0;
		try{
			String insert ="",query="",update="",delete="";
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String addtime =  sdf.format(new Date());
			SimpleDateFormat format_date = new SimpleDateFormat("yyyy-MM-dd");
			//TBusAdj 抬头明细表
			for (String[] strings : DataArray) {
				String AdjType =InterfaceValue("M_OTHER_INOUT","AdjType",strings[Integer.parseInt(fieldmatchMap.get("ADJTYPE").toString())],false);
				String Store = InterfaceValue("C_STORE","Store",strings[Integer.parseInt(fieldmatchMap.get("STORE").toString())],true);
				String Remark = strings[Integer.parseInt(fieldmatchMap.get("REMARK").toString())];
				int Remark_len = Remark.length()>9?9:Remark.length();
				Remark = Remark.substring(0,Remark_len);
				
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("MasterId", strings[Integer.parseInt(fieldmatchMap.get("MASTERID").toString())]);
				paramMap.put("BillDate", format_date.format(format_date.parse(strings[Integer.parseInt(fieldmatchMap.get("BILLDATE").toString())])));
				paramMap.put("AdjType", AdjType);
				paramMap.put("Store", Store);
				paramMap.put("Remark", Remark);
				paramMap.put("Opr", strings[Integer.parseInt(fieldmatchMap.get("OPR").toString())]);
				paramMap.put("OpDate", strings[Integer.parseInt(fieldmatchMap.get("OPDATE").toString())]);
				paramMap.put("Sku", strings[Integer.parseInt(fieldmatchMap.get("SKU").toString())]);
				paramMap.put("Qty", strings[Integer.parseInt(fieldmatchMap.get("QTY").toString())]);
				paramMap.put("addwho", Account);
				paramMap.put("addtime", addtime);
				
				
				//盘点日期判断
				query = "select count(*) from TDefStore " +
						"where (Store = :Store and isnull(panddate,'1990-01-01')<:BillDate) ";
				if(njdbcTemplate.queryForInt(query, paramMap)<1){
					insert ="insert into TBusAdj_TMP(MasterId, BillDate, Store, Remark, Opr, OpDate, Sku, Qty, status, note, addwho, addtime)  " +
							"select :MasterId, :BillDate, :Store, :Remark, :Opr, :OpDate, :Sku, :Qty, '2', '与盘点日期不符', :addwho, :addtime " ;
					njdbcTemplate.update(insert, paramMap);
					continue;
				}
				
				//明细是否重复
				insert ="insert into TBusAdj_TMP(MasterId, BillDate,AdjType, Store, Remark, Opr, OpDate, Sku, Qty, status, note, addwho, addtime)  " +
						"select :MasterId, :BillDate, :AdjType, :Store, :Remark, :Opr, :OpDate, :Sku, :Qty, '0', '', :addwho, :addtime " +
						"where not exists(" +
						"select 'x' from TBusAdj_TMP where (  (MasterId = :MasterId and status ='1' ) )  )";
				//(MasterId = :MasterId and Sku = :Sku and status !='2') or
				if(njdbcTemplate.update(insert, paramMap)==0){
					insert ="insert into TBusAdj_TMP(MasterId, BillDate, AdjType, Store, Remark, Opr, OpDate, Sku, Qty, status, note, addwho, addtime)  " +
							"select :MasterId, :BillDate, :AdjType, :Store, :Remark, :Opr, :OpDate, :Sku, :Qty, '2', '明细插入重复', :addwho, :addtime " ;
					njdbcTemplate.update(insert, paramMap);
				}
				
			}
					
			//更新不能插入的明细记录状态为 2 
			/*update ="update TBusAdj_tmp set status = 2,note='1.明细重复' " +
					"where exists(select 'x' from TBusAdjDt where TBusAdj_tmp.MasterId=TBusAdjDt.MasterId and TBusAdj_tmp.Sku=TBusAdjDt.Sku )";
			jdbcTemplate.update(update);*/
			
			//读取临时表 插入到正式表 ， 同步
			//获取masterid 
			query = "select distinct MasterId,BillDate,AdjType,Store,Remark,Opr,OpDate " +
					"from TBusAdj_TMP where status = 0 and addwho ='"+Account+"'";
			List<Map<String,Object>> list_data = jdbcTemplate.queryForList(query);
			
			//读取是否执行相应存储过程
			boolean PChkAdj = true ,PChgAdj=true;
			query = "select * from BASE_Interface_Procedure where TableName='TBusAdj' and Run='N' ";
			List<Map<String,Object>> ProcedureMap = jdbcTemplate.queryForList(query);
			for (Map<String, Object> map : ProcedureMap) {
				String ProName = map.get("PRONAME")==null?"":map.get("PRONAME").toString();
				if(ProName.toUpperCase().equals("PCHKADJ"))
					PChkAdj = false ;
				if(ProName.toUpperCase().equals("PCHGADJ"))
					PChgAdj = false ;
			}
			
			for (Map<String, Object> map_data : list_data) {
				String MasterId = map_data.get("MASTERID").toString();
				String BillDate = map_data.get("BillDate").toString();
				String AdjType = map_data.get("AdjType").toString();
				String Store = map_data.get("Store").toString();
				String Remark = map_data.get("Remark").toString();
				String Opr = map_data.get("Opr").toString();
				String OpDate = map_data.get("OpDate").toString();
				if(Remark.equals(""))
					Remark = "无调整原因";
				
				String MasterId_sys = "";
				Boolean procedure_exec = true ;
				synchronized (this) {
					//获取masterid
					query = "select MasterId from FGetNewMasterId('"+Store+"','TBusAdj')";
					MasterId_sys = jdbcTemplate.queryForObject(query, String.class);
					//插入数据到 抬头
					insert = "insert into TBusAdj(MasterId,BillDate,AdjType,Store,Remark,Opr,OpDate) " +
							 "select :MasterId_sys,:BillDate,:AdjType,:Store,:Remark,:Opr,:OpDate " +
							 "where not exists( select 'x' from TBusAdj where MasterId=:MasterId_sys )";
					Map<String,Object> paramMap = new HashMap<String, Object>();
					paramMap.put("MasterId_sys", MasterId_sys);
					paramMap.put("MasterId", MasterId);
					paramMap.put("BillDate", BillDate);
					paramMap.put("AdjType", AdjType);
					paramMap.put("Store", Store);
					paramMap.put("Remark", Remark);
					paramMap.put("Opr", Opr);
					paramMap.put("OpDate", OpDate);
					
					if(njdbcTemplate.update(insert, paramMap)>0){
						update = "update  TBusAdj_TMP set MasterId_sys = :MasterId_sys,status=1 " +
								 "where MasterId = :MasterId and status =0 and BillDate = :BillDate and Store = :Store ";
					}else{
						procedure_exec = false; //不执行存储过程
						update = "update  TBusAdj_TMP set MasterId_sys = :MasterId_sys,status=2,note='2.插入TBusAdj失败' " +
								 "where MasterId = :MasterId and status =0 and BillDate = :BillDate and Store = :Store ";
					}
					njdbcTemplate.update(update, paramMap);
				}
				
				if(!procedure_exec)
					continue;
				
				//插入数据到明细表
				insert = "insert into TBusAdjDt(MasterId,Sku,Qty,DPrice) " +
						 "select a.masterid_sys,a.Sku,a.Qty,c.Price " +
						 "from TBusAdj_tmp a,TDefSku b,TDefStyle c "+
						 "where a.status=1 and a.Sku = b.Sku and b.Style = c.Style and " +
						 "a.MasterId = :MasterId and a.BillDate = :BillDate and a.Store = :Store and " +
						 "not exists(" +
						 "select 'x' from TBusAdjDt where MasterId = a.masterid_sys and Sku =a.Sku )";
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("MasterId", MasterId);
				paramMap.put("BillDate", BillDate);
				paramMap.put("Store", Store);
				if(njdbcTemplate.update(insert, paramMap)==0){
					paramMap.put("MasterId_sys", MasterId_sys);
					
					update = "update  TBusAdj_tmp set status=2,note='插入TBusAdjDt失败' " +
							 "where MasterId = :MasterId and status = 1 and BillDate = :BillDate and Store = :Store ";
					njdbcTemplate.update(update, paramMap);
					
					delete ="delete from TBusAdj where MasterId = :MasterId_sys and exists(" +
							"select 'x' from TBusAdj_tmp where TBusAdj.MasterId = TBusAdj_tmp.MasterId_sys )";
					njdbcTemplate.update(delete, paramMap);
					
					continue;
				}
				
				//执行相应的存储过程 
				String procedure = "";
				final String MasterId_sys_p = MasterId_sys;
				//没有数据插入，跳过本次存储过程执行
				/*if(!procedure_exec){
					//手工更新 TBusAdj 表 中 审核、验收、记账
					update = "update TBusAdj set " +
							"Checker='000',CheckDate=:CheckDate,Checked=1," +
							"Charger='000',ChargeDate=:ChargeDate,Charged='1'," +
							"Remark='盘点日期不对,手工更新' " +
							"where MasterId = :MasterId_sys ";
					paramMap.clear();
					paramMap.put("CheckDate", addtime);
					paramMap.put("ChargeDate", addtime);
					paramMap.put("AcceptDate", addtime);
					paramMap.put("MasterId_sys", MasterId_sys);
					njdbcTemplate.update(update, paramMap);
					
					continue; 
				}*/
				
				if(PChkAdj){
					// 物理调整单审核存储过程	
					procedure = "{call PChkAdj(?,?)}";  		
					@SuppressWarnings("unchecked")
					Map<String,Object> map_PChkAdj = (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {  
			            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
			                cs.setString(1, MasterId_sys_p);
			                cs.setString(2, "000");	
			                cs.execute();
			                Map<String,Object> map = new HashMap<String, Object>();  
			                map.put("r_code", "0");
			                return map;
			            }
			        }); 
				}
				
				if(PChgAdj){
					// 物理调整单记账存储过程	
					procedure = "{call PChgAdj(?,?)}";
					@SuppressWarnings("unchecked")
					Map<String,Object> map_PChgAdj = (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {  
			            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
			            	cs.setString(1, MasterId_sys_p);
			                cs.setString(2, "000");	
			                cs.execute();
			                Map<String,Object> map = new HashMap<String, Object>();  
			                map.put("r_code", "0");
			                return map;
			            }
			        });
				}
								
			}
			result = 1 ;
		}catch(Exception e){
			String str = "处理执行过程中出错`请检查数据是否有误或联系管理员";
			msg = str ;
			logger.error("TBusAdj:"+e.toString());
			throw new RuntimeException(e);
		}
		// TODO Auto-generated method stub
		return result > 0 ? "1" : msg;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String M_INVENTORY(List<String[]> DataArray,Map<String,Object> fieldmatchMap,String Account) {
		String msg = "";
		int result = 0;
		try{
			String insert ="",query="",update="",delete="";
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String addtime =  sdf.format(new Date());
			SimpleDateFormat format_date_1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format_date_2 = new SimpleDateFormat("yyyyMMdd");
			
			//TBusRetail 抬头明细表 // 先直接插入数据
			for (String[] strings : DataArray) {
				
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("DOCNO", strings[Integer.parseInt(fieldmatchMap.get("DOCNO").toString())]);
				paramMap.put("BILLDATE", format_date_2.format(format_date_1.parse(strings[Integer.parseInt(fieldmatchMap.get("BILLDATE").toString())])));
				paramMap.put("C_STORE_ID", strings[Integer.parseInt(fieldmatchMap.get("C_STORE_ID").toString())]);
				paramMap.put("STATUSERID", strings[Integer.parseInt(fieldmatchMap.get("STATUSERID").toString())]);
				paramMap.put("STATUSTIME", strings[Integer.parseInt(fieldmatchMap.get("STATUSTIME").toString())]);
				paramMap.put("SKU", strings[Integer.parseInt(fieldmatchMap.get("SKU").toString())]); 
				paramMap.put("QTY", strings[Integer.parseInt(fieldmatchMap.get("QTY").toString())]);
				paramMap.put("STATUS", "0");
				paramMap.put("NOTE", "");
				paramMap.put("ADDWHO", Account);
				
				//判断明细插入是否重复
				insert ="insert into M_INVENTORY_TMP(ID, DOCNO,BILLDATE,  C_STORE_ID, STATUSERID, STATUSTIME, SKU, QTY, ADDWHO, ADDTIME, NOTE, STATUS)  " +
						"select sys_guid() ID, :DOCNO, :BILLDATE, :C_STORE_ID, :STATUSERID, :STATUSTIME, :SKU, :QTY, :ADDWHO,sysdate ADDTIME, :NOTE, :STATUS " +
						"from dual where not exists(" +
						"select 'x' from M_INVENTORY_TMP where DOCNO = :DOCNO and STATUS ='1' )";
				if(njdbcTemplate.update(insert, paramMap)==0){
					insert ="insert into M_INVENTORY_TMP(ID, DOCNO,BILLDATE,  C_STORE_ID, STATUSERID, STATUSTIME, SKU, QTY, ADDWHO, ADDTIME, NOTE, STATUS)  " +
							"select sys_guid() ID, :DOCNO, :BILLDATE, :C_STORE_ID, :STATUSERID, :STATUSTIME, :SKU, :QTY, :ADDWHO,sysdate ADDTIME, :NOTE, :STATUS " +
							"from dual ";
					paramMap.put("NOTE", "盘点单插入重复");		
					paramMap.put("STATUS", "2");
					njdbcTemplate.update(insert, paramMap);
				}
				
			}
			
			//循环 m_retail_tmp 表,执行事务----插入零售单，子事务中运行
			query = "SELECT DISTINCT DOCNO,ADDWHO,C_STORE_ID FROM M_INVENTORY_TMP WHERE STATUS='0' AND ADDWHO =:ADDWHO ";
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("ADDWHO", Account);
			
			//是否更新盘点日期   0 不更新   1 更新
			WebUtils web = new WebUtils();
			String PandDate_Update = web.readValue("config/others/config.properties","Bos.PandDate_Update");
			if(PandDate_Update==null||PandDate_Update.equals("")){
				PandDate_Update = "0";
			}
			
			List<Map<String,Object>> list_m_retail_tmp = njdbcTemplate.queryForList(query, paramMap);
			logger.info("list_m_retail_tmp.size:"+list_m_retail_tmp.size());
			for (Map<String, Object> map : list_m_retail_tmp) {
				String DOCNO = map.get("DOCNO").toString();
				String C_STORE_ID = map.get("C_STORE_ID").toString();
				logger.info("list_m_retail_tmp:DOCNO:"+DOCNO+",C_STORE_ID:"+C_STORE_ID);
				//执行存储过程事务,嵌套事务
				try {
					String r_message = M_INVENTORY_CHILD(DOCNO);
					update = "update M_INVENTORY_TMP set status ='1',note = :NOTE where docno =:DOCNO and addwho =:ADDWHO  and status ='0'";
					map.put("NOTE", r_message);
					
					//是否更新店仓的盘点日期
					query = "select max(nvl(dateblock,'20130101')) dateblock from c_store where Code = :CODE ";
					String STORE_CODE = InterfaceValue("C_STORE","CODE",C_STORE_ID,true);
					map.put("CODE", STORE_CODE);
					String PandDate = njdbcTemplate.queryForObject(query, map, String.class);
					
					if(PandDate_Update.equalsIgnoreCase("0")){
						//更新店仓的最后盘点日期为 前一天，这样不影响今天单据的录入，如果后续有补单的情况，就无法录入
						update = "update Store set dateblock = :BillDate where Code = :CODE ";
						Map<String,Object> paramPandDateMap = new HashMap<String, Object>();
						paramPandDateMap.put("BillDate", PandDate);
						paramPandDateMap.put("CODE", STORE_CODE);
						njdbcTemplate.update(update, paramPandDateMap);
					}
					
				} catch (Exception e) {
					if(e.toString().length()>len)						
						map.put("NOTE",StringUtils.substring(e.toString(), 0, len));
					else 
						map.put("NOTE",e.toString());
					update = "update M_INVENTORY_TMP set status ='2',note = :NOTE where docno =:DOCNO and addwho =:ADDWHO ";
					// TODO: handle exception
				}
				
				njdbcTemplate.update(update, map);
				
			}
			
			result = 1 ;
		}catch(Exception e){
			String str = "处理执行过程中出错`请检查数据是否有误或联系管理员";
			msg = str ;
			System.out.println(e.toString());
			logger.error("M_INVENTORY:"+e.toString());
			throw new RuntimeException(e);
		}
		// TODO Auto-generated method stub
		return result > 0 ? "1" : msg;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.NESTED, rollbackFor = { Exception.class })
	public String  M_INVENTORY_CHILD(final String DOCNO){
		String msg = "";
		int result = 0;
		try {
			String procedure = "{call P_BASE_M_INVENTORY(?,?)}";
			@SuppressWarnings("unchecked")
			Map<String,Object> MAP_P_BASE_M_INVENTORY = (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {  
	            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
	                cs.setString(1, DOCNO);
	                cs.registerOutParameter(2, OracleTypes.VARCHAR);	
	                cs.execute();
	                Map<String,Object> map = new HashMap<String, Object>();  
	                map.put("r_message", cs.getString(2));
	                return map;
	            }
	        }); 
			
			msg = MAP_P_BASE_M_INVENTORY.get("r_message").toString();
			
			result = 1;
			
		} catch (Exception e) {
			msg = e.toString();
			throw new RuntimeException(e);
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return msg;
	}

	
	public String InterfaceValue(String TableName,String Type,String TargetKey,Boolean force ){
		String query=""; 
		query = "select max(LocalKey) from BASE_Interface_Value where tablename =:tablename and type =:type and targetkey =:targetkey and isactive='Y'";
		Map<String,Object> ParamMap = new HashMap<String, Object>();
		ParamMap.put("tablename", StringUtils.upperCase(TableName));
		ParamMap.put("type", StringUtils.upperCase(Type));
		ParamMap.put("targetkey", StringUtils.upperCase(TargetKey));
		String LocalKey = njdbcTemplate.queryForObject(query, ParamMap, String.class);
		if(LocalKey==null||LocalKey.equals("")){
			if(force)
				query = "select max(DefaultValue) from BASE_Interface_Value where upper(tablename) =:tablename and upper(type) =:type and upper(targetkey) =:targetkey and isactive='Y' ";
			else
				query = "select max(DefaultValue) from BASE_Interface_Value where upper(tablename) =:tablename and upper(type) =:type and isactive='Y' ";
			LocalKey = njdbcTemplate.queryForObject(query, ParamMap, String.class);
		}
				
		return LocalKey==null||LocalKey.equals("")?TargetKey:LocalKey;
	}

	
}
