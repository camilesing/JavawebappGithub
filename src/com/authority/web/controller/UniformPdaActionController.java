package com.authority.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.authority.common.springmvc.DateConvertEditor;
import com.authority.pojo.ExceptionReturn;
import com.authority.pojo.PdaReturn;

@Controller
@RequestMapping("/uniform/pdaaction")
public class UniformPdaActionController {
	private static final Logger logger = LoggerFactory.getLogger(UniformPdaActionController.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	@RequestMapping(value="/login")
	@ResponseBody	
	public Object login(@RequestParam String account, @RequestParam String password, HttpSession session, HttpServletRequest request) {
		try {
					
			String result ="00";
			
			if (StringUtils.isBlank(account)) {
				return new PdaReturn("N", "帐号不能为空！");
			}
			if (StringUtils.isBlank(password)) {
				return new PdaReturn("N", "密码不能为空！");
			}
			
			String query = "select count(*) from BASE_USERS " +
					"where account='"+account+"' and password='"+password+"'";
			
			String store ="id;name";
			
			if(jdbcTemplate.queryForInt(query)>0)
				result ="01";
			if ("01".equals(result)) {
				return new PdaReturn("Y", store);
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
}
