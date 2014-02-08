package com.authority.web.controller;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.authority.common.springmvc.DateConvertEditor;
import com.authority.pojo.ExceptionReturn;
import com.authority.pojo.ExtReturn;
import com.authority.service.BaseUsersService;

@Controller
@RequestMapping("/jqmaction")
public class JQMActionController {
private static final Logger logger = LoggerFactory.getLogger(JQMActionController.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;
		
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@RequestMapping("/login")
	public void login(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		String result = "",callbackfun="";
		PrintWriter writer = null ;
		response.setContentType("text/html;charset=UTF-8");
		try {
			writer = response.getWriter();
			String account = request.getParameter("account");
			String password= request.getParameter("password");
			account = URLDecoder.decode(account, "UTF-8"); 
			password = URLDecoder.decode(password, "UTF-8"); 
			System.out.println("account:"+account);
			callbackfun = request.getParameter("callbackfun");
			//用户验证
			if(account.equals("admin")&&password.equals("admin")){
				result = JSON.toJSONString(new ExtReturn(true, "success"));
			}
			else{
				result = JSON.toJSONString(new ExtReturn(false, "用户或密码错误"));
			}
			if(callbackfun!=null&&!callbackfun.equals("")){
				result = callbackfun+"("+result+")";
			}
			
		} catch (Exception e) {
			result = JSON.toJSONString(new ExceptionReturn(e));
			result = callbackfun+"("+result+")";
			// TODO: handle exception
		} finally{
			writer.write(result);
			writer.flush();
		}
		
	}
	
	@RequestMapping("/workflowcount")
	public void workflowcount(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		String result = "",callbackfun="";
		PrintWriter writer = null ;
		response.setContentType("text/html;charset=UTF-8");
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		int nowminute = cal.get(Calendar.MINUTE);
		
		try {
			writer = response.getWriter();
			
			callbackfun = request.getParameter("callbackfun");
			result = JSON.toJSONString(new ExtReturn(true, String.valueOf(nowminute)));
			
			if(callbackfun!=null&&!callbackfun.equals("")){
				
				result = callbackfun+"("+result+")";
			}
			
		} catch (Exception e) {
			result = JSON.toJSONString(new ExceptionReturn(e));
			result = callbackfun+"("+result+")";
			// TODO: handle exception
		} finally{
			writer.write(result);
			writer.flush();
		}
		
	}
	
	@RequestMapping("/workflowdetail")
	public void workflowdetail(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		String result = "",callbackfun="";
		PrintWriter writer = null ;
		response.setContentType("text/html;charset=UTF-8");
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		int nowminute = cal.get(Calendar.MINUTE);
		List list = new ArrayList();
		
		try {
			writer = response.getWriter();
			for(int i=1;i<=2;i++){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("type", "经销商还款计划单");
				map.put("docno", "BRP131108000000"+i);
				map.put("note", "20131107 PPE仁怀黄倩:"+i);
				list.add(map);
			}
			
			callbackfun = request.getParameter("callbackfun");
			result = JSON.toJSONString(new ExtReturn(true, list));
			
			if(callbackfun!=null&&!callbackfun.equals("")){
				
				result = callbackfun+"("+result+")";
			}
			
		} catch (Exception e) {
			result = JSON.toJSONString(new ExceptionReturn(e));
			result = callbackfun+"("+result+")";
			// TODO: handle exception
		} finally{
			writer.write(result);
			writer.flush();
		}
		
	}
	
	@RequestMapping("/workflowdeal")
	public void workflowdeal(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		String result = "",callbackfun="",docno="",oper="",update ="";
		PrintWriter writer = null ;
		response.setContentType("text/html;charset=UTF-8");
		
		try {
			writer = response.getWriter();
			
			callbackfun = request.getParameter("callbackfun");
			docno = request.getParameter("docno");
			oper  = request.getParameter("oper");
			result = JSON.toJSONString(new ExtReturn(true,"1" ));
			
			if(callbackfun!=null&&!callbackfun.equals("")){
				
				result = callbackfun+"("+result+")";
			}
			
		} catch (Exception e) {
			result = JSON.toJSONString(new ExceptionReturn(e));
			result = callbackfun+"("+result+")";
			// TODO: handle exception
		} finally{
			writer.write(result);
			writer.flush();
		}
		
	}
	
	@RequestMapping("/reportview_bardetail")
	public void reportview_bardetail(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		String result = "",callbackfun="",docno="",oper="",update ="",store="",date_start="",date_end="";
		PrintWriter writer = null ;
		response.setContentType("text/html;charset=UTF-8");
		List list = new ArrayList();
		try {
			writer = response.getWriter();
			
			callbackfun = request.getParameter("callbackfun");
			store = request.getParameter("store");
			store = store==null?"":URLDecoder.decode(store, "UTF-8"); 
			date_start = request.getParameter("date_start");
			date_end = request.getParameter("date_end");
			System.out.println("store:"+store+" date:"+date_start+"-"+date_end);
			String[] stores = {"温州店","上海店","瑞安店","杭州店"};
			for (int i = 0; i < stores.length; i++) {
				String str = stores[i];
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("store", str);
				map.put("sales", i+10);
				list.add(map);
			}
			
			callbackfun = request.getParameter("callbackfun");
			result = JSON.toJSONString(new ExtReturn(true, list));
			
			if(callbackfun!=null&&!callbackfun.equals("")){
				
				result = callbackfun+"("+result+")";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			result = JSON.toJSONString(new ExceptionReturn(e));
			result = callbackfun+"("+result+")";
		} finally{
			writer.write(result);
			writer.flush();
		}
		
	}
	
	@RequestMapping("/reportview_tabledetail")
	public void reportview_tabledetail(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		String result = "",callbackfun="",docno="",oper="",update ="",store="",date_start="",date_end="";
		PrintWriter writer = null ;
		response.setContentType("text/html;charset=UTF-8");
		List list = new ArrayList();
		try {
			writer = response.getWriter();
			
			callbackfun = request.getParameter("callbackfun");
			store = request.getParameter("store");
			store = store==null?"":URLDecoder.decode(store, "UTF-8"); 
			date_start = request.getParameter("date_start");
			date_end = request.getParameter("date_end");
			System.out.println("store:"+store+" date:"+date_start+"-"+date_end);
			String[] stores = {"温州店","上海店","瑞安店","杭州店","苍南店"};
			int cell_count = 3 ;
			for (int i = 0; i < stores.length; i++) {
				String str = stores[i];
				Map<String,Object> map = new HashMap<String, Object>();
				if(i==0){
					Map<String,Object> map_head = new HashMap<String, Object>();
					map_head.put("cell_1", "Rank");
					map_head.put("cell_2", "店仓");
					map_head.put("cell_3", "销售量");
					list.add(map_head);					
				}
				map.put("cell_1", i+1);
				map.put("cell_2", str);
				map.put("cell_3", i+10);
				list.add(map);
			}
			
			callbackfun = request.getParameter("callbackfun");
			result = JSON.toJSONString(new ExtReturn(true, list,cell_count));
			
			if(callbackfun!=null&&!callbackfun.equals("")){
				
				result = callbackfun+"("+result+")";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			result = JSON.toJSONString(new ExceptionReturn(e));
			result = callbackfun+"("+result+")";
		} finally{
			writer.write(result);
			writer.flush();
		}
		
	}
	
	@RequestMapping("/requestobject")
	@ResponseBody
	public Object requestobject(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	@RequestMapping("/requestvoid")
	public void requestvoid(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			
		}
		
	}
	
}
