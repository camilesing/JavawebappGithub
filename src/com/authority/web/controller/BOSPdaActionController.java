package com.authority.web.controller;

import java.io.File;
import java.io.FileInputStream;
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

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
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
import com.authority.pojo.BaseUsers;
import com.authority.pojo.ExceptionReturn;
import com.authority.pojo.ExtReturn;
import com.authority.pojo.PdaReturn;
import com.authority.service.BOSPdaService;
import com.authority.service.BaseUsersService;
import com.authority.service.GMQPdaService;
import com.authority.web.interseptor.WebConstants;
import com.henlo.process.HenloController;

@Controller
@RequestMapping("/bos/pdaaction")
public class BOSPdaActionController {
private static final Logger logger = LoggerFactory.getLogger(BOSPdaActionController.class);
	
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
	
	@RequestMapping(value="/login")
	@ResponseBody	
	public Object login(@RequestParam String account, @RequestParam String password, HttpSession session, HttpServletRequest request) {
		try {
					
			String result ="00",store=";";
			
			if (StringUtils.isBlank(account)) {
				return new PdaReturn("N", "帐号不能为空！");
			}
			if (StringUtils.isBlank(password)) {
				return new PdaReturn("N", "密码不能为空！");
			}
			
			password = DigestUtils.md5Hex(password);
			
			String passwordIn = encrypt(password, account);
			
			String query = "select count(*) from BASE_USERS " +
					"where account='"+account+"' and password='"+passwordIn+"'";
			
			//query = "select count(*) from TSysUser a where a.userid = '"+account+"' and remark = '"+password+"'";
			
			if(jdbcTemplate.queryForInt(query)>0)
				result ="01";
			if ("01".equals(result)) {
				session.setAttribute(WebConstants.CURRENT_USER, account);
				/*query = "select a.id,a.Store+';'+b.StoreName Store " +
						"from TDefEmp a " +
						"left join TDefStore b on a.Store = b.Store " +
						"where b.Closed = 0 and a.EmpId ='"+account+"'";
				List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
				Map<String,Object> map =  list.get(0);
				store = map.get("Store")==null?"":map.get("Store").toString();
				return new PdaReturn("Y", store);*/
				return new PdaReturn("Y", "Store;StoreName");
			} else if ("00".equals(result)) {
				return new PdaReturn("N", "用户名或密码错误！");
			} else {
				return new PdaReturn("N", result);
			}
			
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
	
	@RequestMapping(value="/stock")
	@ResponseBody	
	public Object stock(HttpSession session, HttpServletRequest request) {
		try{
			String skustyle = request.getParameter("skustyle");
			if(skustyle==null||skustyle.equals(""))
				skustyle="sku";
			
			String sku = request.getParameter("sku");
			String store = request.getParameter("store");
			
			String query = "",info="";
			
			if(skustyle.equals("sku"))
				query = "select * from TAccStock where sku = :sku  and store =:store";
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("sku", sku);
			params.put("store", store);
			
			List<Map<String,Object>> list = njdbcTemplate.queryForList(query, params);
			
			for (Map<String, Object> map : list) {
				info = info + ";" + map.get("store").toString()+","+map.get("sku").toString()+","+map.get("qty").toString();
			}
			
			return new PdaReturn("Y", info);
			
		}catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
		
	}
	
	@RequestMapping(value="/m_out")
	@ResponseBody	
	public Object m_out(HttpSession session, HttpServletRequest request) {
		try{
			String docno = request.getParameter("docno").toUpperCase();
			String query = "",info="";
			query = "select A.DOCNO,C.NAME M_PRODUCT_NAME,C.VALUE M_PRODUCT_VALUE,D.NO M_PRODUCT_ALIAS,B.QTY,B.QTYIN,B.QTYOUT "+  
					"from m_out a ,m_outitem b,m_product c,M_PRODUCT_ALIAS d "+
					"where A.ID = B.M_OUT_ID and b.M_PRODUCT_ID=c.id and b.M_PRODUCTALIAS_ID=d.ID "+
					"and A.DOCNO=:DOCNO and a.STATUS='1' ";
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("DOCNO", docno);
			
			List<Map<String,Object>> list = njdbcTemplate.queryForList(query, params);
			
			for (Map<String, Object> map : list) {
				info = info + ";" + map.get("M_PRODUCT_VALUE").toString()+","+map.get("M_PRODUCT_ALIAS").toString()+","+
									map.get("QTY").toString()+","+map.get("QTYOUT").toString();
			}
			info = StringUtils.removeStart(info, ";");
			System.out.println(info);
			
			return new PdaReturn("Y", info);
			
			
		}catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
		
	}
	
	@RequestMapping(value="/m_outitem_ac")
	@ResponseBody	
	public Object m_outitem_ac(HttpSession session, HttpServletRequest request) {
		try{
			String docno = request.getParameter("docno").toUpperCase();
			String tiaom = request.getParameter("tiaom").toUpperCase();
			String qty =   request.getParameter("qty");
			//插入数据
			String result = bospdaservice.m_outitem_ac(docno, tiaom,qty);
			if(result.startsWith("Y"))
				return new PdaReturn("Y", result);
			else
				return new PdaReturn("N", result);			
			
		}catch (Exception e) {
			logger.error("Exception: ", e);
			//return new ExceptionReturn(e);
			int start = StringUtils.indexOf(e.toString(), "ORA-");
			String result = StringUtils.substring(e.toString(), start);
			return new PdaReturn("N", result);
		}
		
	}
	
	@RequestMapping(value="/m_out_submit")
	@ResponseBody	
	public Object m_out_submit(HttpSession session, HttpServletRequest request) {
		try{
			String docno = request.getParameter("docno").toUpperCase();
			String chayyy = request.getParameter("chayyy");
			if(chayyy==null)
				chayyy="";
			//插入数据
			String result = bospdaservice.m_out_submit(docno,chayyy);
			if(result.startsWith("Y"))
				return new PdaReturn("Y", result);
			else
				return new PdaReturn("N", result);			
			
		}catch (Exception e) {
			logger.error("Exception: ", e);
			//return new ExceptionReturn(e);
			return new PdaReturn("N", e.toString());
		}
		
	}
	
	
	@RequestMapping(value="/m_in")
	@ResponseBody	
	public Object m_in(HttpSession session, HttpServletRequest request) {
		try{
			String docno = request.getParameter("docno").toUpperCase();
			String query = "",info="";
			query = "select distinct  A.DOCNO,C.NAME M_PRODUCT_NAME,C.VALUE M_PRODUCT_VALUE,D.NO M_PRODUCT_ALIAS,B.QTY,B.QTYIN,B.QTYOUT "+  
					"from m_in a ,m_initem b,m_product c,M_PRODUCT_ALIAS d "+
					"where A.ID = B.M_IN_ID and b.M_PRODUCT_ID=c.id and b.M_PRODUCTALIAS_ID=d.ID "+
					"and A.DOCNO=:DOCNO and a.STATUS='1' ";
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("DOCNO", docno);
			
			List<Map<String,Object>> list = njdbcTemplate.queryForList(query, params);
			
			for (Map<String, Object> map : list) {
				info = info + ";" + map.get("M_PRODUCT_VALUE").toString()+","+map.get("M_PRODUCT_ALIAS").toString()+","+
									map.get("QTYOUT").toString()+","+map.get("QTYIN").toString();
			}
			info = StringUtils.removeStart(info, ";");
			System.out.println(info);
			
			return new PdaReturn("Y", info);
			
			
		}catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
		
	}
	
	@RequestMapping(value="/m_initem_ac")
	@ResponseBody	
	public Object m_initem_ac(HttpSession session, HttpServletRequest request) {
		try{
			String docno = request.getParameter("docno").toUpperCase();
			String tiaom = request.getParameter("tiaom").toUpperCase();
			String qty =   request.getParameter("qty");
			//插入数据
			String result = bospdaservice.m_initem_ac(docno, tiaom,qty);
			if(result.startsWith("Y"))
				return new PdaReturn("Y", result);
			else
				return new PdaReturn("N", result);			
			
		}catch (Exception e) {
			logger.error("Exception: ", e);
			//return new ExceptionReturn(e);
			int start = StringUtils.indexOf(e.toString(), "ORA-");
			String result = StringUtils.substring(e.toString(), start);
			return new PdaReturn("N", result);
		}
		
	}
	
	@RequestMapping(value="/m_in_submit")
	@ResponseBody	
	public Object m_in_submit(HttpSession session, HttpServletRequest request) {
		try{
			String docno = request.getParameter("docno").toUpperCase();
			String chayyy = request.getParameter("chayyy");
			if(chayyy==null)
				chayyy="";
			//插入数据
			String result = bospdaservice.m_in_submit(docno,chayyy);
			if(result.startsWith("Y"))
				return new PdaReturn("Y", result);
			else
				return new PdaReturn("N", result);			
			
		}catch (Exception e) {
			logger.error("Exception: ", e);
			//return new ExceptionReturn(e);
			return new PdaReturn("N", e.toString());
		}
		
	}
	
	@RequestMapping(value="/datadownload")
	@ResponseBody	
	public Object datadownload(HttpSession session, HttpServletRequest request) {
	
		return null;
	}
	
	@RequestMapping(value="/dataupload")
	@ResponseBody	
	public Object dataupload(HttpSession session, HttpServletRequest request) {
	
		return null;
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
