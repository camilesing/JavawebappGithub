package com.authority.service.impl;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.authority.service.BOSPdaService;
import com.authority.service.GMQXiangjyService;

@Service
public class BOSPdaServiceImpl implements BOSPdaService {

	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String m_outitem_ac(String docno, String tiaom,String qty) {
		String query = "",insert="",update="",result="";
		try {
			//更新表 m_outitem 中的数量
			update ="update m_outitem a set A.QTYOUT = A.QTYOUT + to_number(:qty) "+ 
					"where exists( "+
					"    select 'x' from M_PRODUCT_ALIAS b where A.M_PRODUCTALIAS_ID=B.ID and B.NO = :tiaom "+
					") and exists( "+
					"    select 'x' from m_out c where A.M_OUT_ID=C.ID and C.DOCNO = :docno and c.isactive='Y'"+
					") ";
			
			insert ="insert into m_outitem(M_OUT_ID,M_PRODUCT_ID,M_ATTRIBUTESETINSTANCE_ID,QTYOUT) "+
					"select M_OUT_ID,M_PRODUCT_ID,M_ATTRIBUTESETINSTANCE_ID, :qty  "+
					"from m_outitem a where  exists( "+
					"select 'x' from m_out b where A.M_OUT_ID=B.ID and B.DOCNO=:docno "+
					") and exists( "+
					"select 'x' from M_PRODUCT_ALIAS c  "+
					"where A.M_PRODUCT_ID=C.M_PRODUCT_ID and A.M_ATTRIBUTESETINSTANCE_ID=C.M_ATTRIBUTESETINSTANCE_ID "+
					"and C.NO=:tiaom "+
					")";
			
			
			Map<String,Object> Param = new HashMap<String, Object>();
			Param.put("docno", docno);
			Param.put("tiaom", tiaom);
			Param.put("qty", qty);
			
			if(njdbcTemplate.update(insert, Param)==0)
				return "条码不存在";
			
			//执行 m_out_am
			/*query = "select id from m_out a where a.docno=:docno and a.isactive='Y' ";
			final String m_out_id = njdbcTemplate.queryForObject(query, Param, String.class);
			
			String procedure = "{call M_OUT_AM(?)}";
			@SuppressWarnings("unchecked")
			Map<String,Object> MAP_M_OUT_AM = (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {  
	            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
	                cs.setString(1, m_out_id);
	                cs.execute();
	                Map<String,Object> map = new HashMap<String, Object>();  
	                map.put("r_message", "");
	                return map;
	            }
	        }); */
			
			result = "Y;";
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
		
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String m_out_submit(String docno,String chayyy) {
		String query = "",insert="",update="",result="";
		try {
			
			Map<String,Object> Param = new HashMap<String, Object>();
			Param.put("docno", docno);
			Param.put("diff_description", chayyy);
			
			//执行 m_out_am
			query = "select count(*) from m_out a where a.docno=:docno and a.isactive='Y' and a.STATUS!='2' ";
			int m_out_id_cnt = njdbcTemplate.queryForInt(query, Param);
			if(m_out_id_cnt==0){
				return "N;出库单已经提交";
			}
			
			update = "update m_out a set a.DIFF_DESCRIPTION =:diff_description where a.docno = :docno and a.isactive='Y' ";
			njdbcTemplate.update(update, Param);
			
			
			query = "select id from m_out a where a.docno=:docno and a.isactive='Y' and a.STATUS!='2' ";
			final String m_out_id = njdbcTemplate.queryForObject(query, Param, String.class);
			
			String procedure = "{call M_OUT_SUBMIT(?,?,?)}";
			@SuppressWarnings("unchecked")
			Map<String,Object> MAP_M_OUT_SUBMIT = (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {  
	            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
	                cs.setString(1, m_out_id);
	                cs.registerOutParameter(2, OracleTypes.NUMBER);	
	                cs.registerOutParameter(3, OracleTypes.VARCHAR);
	                cs.execute();
	                Map<String,Object> map = new HashMap<String, Object>();  
	                map.put("r_message", cs.getString(3));
	                return map;
	            }
	        }); 
			
			result = MAP_M_OUT_SUBMIT.get("r_message").toString()  ;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
		
		// TODO Auto-generated method stub
		return "Y;"+result;
	}
	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String m_initem_ac(String docno, String tiaom,String qty) {
		String query = "",insert="",update="",result="";
		try {
			//更新表 m_initem 中的数量
			
			insert ="insert into m_initem(AD_CLIENT_ID,AD_ORG_ID,M_IN_ID,M_PRODUCT_ID,M_ATTRIBUTESETINSTANCE_ID,QTYIN) "+
					"select AD_CLIENT_ID,AD_ORG_ID,M_IN_ID,M_PRODUCT_ID,M_ATTRIBUTESETINSTANCE_ID, :qty  "+
					"from m_initem a where  exists( "+
					"select 'x' from m_in b where A.M_IN_ID=B.ID and B.DOCNO=:docno "+
					") and exists( "+
					"select 'x' from M_PRODUCT_ALIAS c  "+
					"where A.M_PRODUCT_ID=C.M_PRODUCT_ID and A.M_ATTRIBUTESETINSTANCE_ID=C.M_ATTRIBUTESETINSTANCE_ID "+
					"and C.NO=:tiaom "+
					") and rownum=1";
			
			
			Map<String,Object> Param = new HashMap<String, Object>();
			Param.put("docno", docno);
			Param.put("tiaom", tiaom);
			Param.put("qty", qty);
			
			if(njdbcTemplate.update(insert, Param)==0)
				return "条码不存在";
			
			/*//执行 m_in_am
			query = "select id from m_in a where a.docno=:docno and a.isactive='Y' ";
			final String m_in_id = njdbcTemplate.queryForObject(query, Param, String.class);
			
			String procedure = "{call M_IN_AM(?)}";
			@SuppressWarnings("unchecked")
			Map<String,Object> MAP_M_IN_AM = (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {  
	            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
	                cs.setString(1, m_in_id);
	                cs.execute();
	                Map<String,Object> map = new HashMap<String, Object>();  
	                map.put("r_message", "");
	                return map;
	            }
	        }); */
			
			result = "Y;";
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
		
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String m_in_submit(String docno,String chayyy) {
		String query = "",insert="",update="",result="";
		try {
			
			Map<String,Object> Param = new HashMap<String, Object>();
			Param.put("docno", docno);
			Param.put("diffreason", chayyy);
			
			//执行 m_in_am
			query = "select count(*) from m_in a where a.docno=:docno and a.isactive='Y' and a.STATUS!='2' ";
			int m_in_id_cnt = njdbcTemplate.queryForInt(query, Param);
			if(m_in_id_cnt==0){
				return "N;入库单已经提交";
			}
			
			update = "update m_in a set a.DIFFREASON  =:diffreason  where a.docno = :docno and a.isactive='Y' ";
			njdbcTemplate.update(update, Param);
			
			
			query = "select max(id) from m_in a where a.docno=:docno and a.isactive='Y' and a.STATUS!='2' ";
			final String m_in_id = njdbcTemplate.queryForObject(query, Param, String.class);
			
			String procedure = "{call M_IN_SUBMIT(?,?,?)}";
			@SuppressWarnings("unchecked")
			Map<String,Object> MAP_M_IN_SUBMIT = (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {  
	            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
	                cs.setString(1, m_in_id);
	                cs.registerOutParameter(2, OracleTypes.NUMBER);	
	                cs.registerOutParameter(3, OracleTypes.VARCHAR);
	                cs.execute();
	                Map<String,Object> map = new HashMap<String, Object>();  
	                map.put("r_message", cs.getString(3));
	                return map;
	            }
	        }); 
			
			result = MAP_M_IN_SUBMIT.get("r_message").toString()  ;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
		
		// TODO Auto-generated method stub
		return "Y;"+result;
	}

}
