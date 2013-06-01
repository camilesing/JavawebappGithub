package com.authority.dao;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;


public class DataSourceDao {
	
	@Autowired
	protected  JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected  NamedParameterJdbcTemplate  njdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public NamedParameterJdbcTemplate getNjdbcTemplate() {
		return njdbcTemplate;
	}

	
	
}
