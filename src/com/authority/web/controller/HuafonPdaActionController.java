package com.authority.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import oracle.jdbc.OracleTypes;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.authority.common.springmvc.DateConvertEditor;
import com.authority.common.utils.FileOperateUtil;
import com.authority.pojo.BaseUsers;
import com.authority.pojo.ExceptionReturn;
import com.authority.pojo.ExtGridReturn;
import com.authority.pojo.ExtReturn;
import com.authority.pojo.PdaReturn;
import com.authority.service.BOSPdaService;
import com.authority.service.BaseUsersService;
import com.authority.service.GMQPdaService;
import com.authority.web.interseptor.WebConstants;
import com.henlo.process.HenloController;

@Controller
@RequestMapping("/huafon/pda")
public class HuafonPdaActionController {
private static final Logger logger = LoggerFactory.getLogger(HuafonPdaActionController.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;
	
	@Autowired
	private BOSPdaService bospdaservice;
	
		
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	
	@RequestMapping(value="/pand/datainit_docnodown")
	@ResponseBody
	public Object pand_datainit_docnodown(HttpSession session, HttpServletRequest request) {
		try {
			//获取基础信息
			String deptcode = request.getParameter("deptcode").toString();
			String plantformcode = request.getParameter("plantformcode").toString();
			String username = request.getParameter("username").toString();
			
			String c_store_id = request.getParameter("c_store_id").toString();
			String query = "select distinct ID,TRUENAME||'*'||substr(email,0,(case when instr(EMAIL,'@')=0 then length(email) else instr(EMAIL,'@')-1 end)) name " +
					"from USERS where C_STORE_ID='"+c_store_id+"' and pda='Y' ";
			List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
			if(list.size()==0){
				return new PdaReturn("N","");
			}else{
				String info = "";
				for (Map<String, Object> map : list) {
					info = info+map.get("ID")+","+map.get("NAME")+";";
				}
				return new PdaReturn("Y",info);
			}
						
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new PdaReturn("N",e.toString());
		}			
		
	}
	
	@RequestMapping("/requestobject")
	@ResponseBody
	public Object requestobject(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		try {
			String result = "somethings";
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("test", "test_value");
			list.add(map);
			
			return new ExtReturn(true, result, list);
		} catch (Exception e) {
			// TODO: handle exception
			return new ExtReturn(true, e.toString(), "....");
		}
		
	}
	
	@RequestMapping("/requestvoid")
	public void requestvoid(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			
		}
		
	}
	
	private String encrypt(String data, String salt) {
		// 可以更换算法:sha512Hex
		return DigestUtils.md5Hex(data + "{" + salt + "}");
	}
	
}
