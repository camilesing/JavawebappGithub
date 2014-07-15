package com.authority.service.impl;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.authority.service.TransactionManagerService;

@Service
public class TransactionManagerImpl implements TransactionManagerService {
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;
	
	
	@Resource(name="jdbcTemplate2")
	private JdbcTemplate jdbcTemplate2;
	
	@Resource(name="njdbcTemplate2")
	private NamedParameterJdbcTemplate njdbcTemplate2;
	

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String action_trans_1() {
		try {
			String insert = "insert into Henlo_test_trans select 7 from dual";
			jdbcTemplate.update(insert);		
			try {
				action_trans_2();
			} catch (Exception e) {
				
				// TODO: handle exception
			}
			
			
		} catch (Exception e) {
			
			throw new RuntimeException(e);
			// TODO: handle exception
		}
		
		// TODO Auto-generated method stub
		return "success";
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class },value="transactionManager2")
	public String action_trans_2() {
		try {
			String insert = "insert into Henlo_test_trans select 7 ";
			jdbcTemplate2.update(insert);			
			int i=1/0;
		} catch (Exception e) {
			throw new RuntimeException(e);
			// TODO: handle exception
		}
		// TODO Auto-generated method stub
		return "success";
	}
	
}
