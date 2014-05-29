package com.authority.web.controller;

import java.util.ArrayList;
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
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.config.AlipayConfig;
import com.alipay.util.httpClient.HttpProtocolHandler;
import com.alipay.util.httpClient.HttpRequest;
import com.alipay.util.httpClient.HttpResponse;
import com.alipay.util.httpClient.HttpResultType;
import com.authority.common.springmvc.DateConvertEditor;
import com.authority.pojo.ExtGridReturn;
import com.authority.pojo.ExtPager;
import com.authority.pojo.ExtReturn;

@Controller
@RequestMapping("/portal/server")
public class PortalServerActionController {
private static final Logger logger = LoggerFactory.getLogger(PortalServerActionController.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	/**
	 * 查找所有的消息
	 */
	@RequestMapping(value="/all", method = RequestMethod.POST)
	@ResponseBody
	public Object all(ExtPager pager, HttpSession session, HttpServletRequest request) {
		
		Map<String,Object> paramMap=new HashMap<String, Object>();
		
		String query_list = "select * from HENLO_SERVER_URL where isdisplay='1' ";
		
		String query_count = "select count(*) from ("+query_list+")";
		
		/*// 排序信息
		if (StringUtils.isNotBlank(pager.getDir()) && StringUtils.isNotBlank(pager.getSort())) {
			query_list=query_list+" order by "+pager.getSort()+" "+pager.getDir();				
		}*/
					
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
			String OracleEnd=String.valueOf(pager.getLimit() + pager.getStart());
			String OracleStart=String.valueOf(pager.getStart());
			
			String Oracle_Pagination_Head="select y.* from ( select z.*, rownum as oracleStart from (";
			String Oracle_Pagination_Tail=") z where rownum <= to_number(:OracleEnd) ) y where y.oracleStart > to_number(:OracleStart)";

			query_list=Oracle_Pagination_Head+query_list+Oracle_Pagination_Tail;
			
			paramMap.put("OracleStart", OracleStart);
			paramMap.put("OracleEnd", OracleEnd);
						
		}
		
		List<Map<String,Object>> list = njdbcTemplate.queryForList(query_list, paramMap);
		int total = njdbcTemplate.getJdbcOperations().queryForInt(query_count);
		
		logger.debug("total:{}", total);
		return new ExtGridReturn(total, list);
		
	}
	
	
	@RequestMapping(value="/detection",method=RequestMethod.POST)
	@ResponseBody
	public Object detection(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		Boolean success = false ;
		String callbackfun="", msg="",update="",query="";
		try {
			query = "select * from HENLO_SERVER_URL where isdisplay='1'";
			List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
			Map<String,Object> param = new HashMap<String, Object>();
			for (Map<String, Object> map : list) {
				String url = map.get("url").toString();
				String id =  map.get("id").toString();
				String status = "failed";
				
				long startTime=System.currentTimeMillis(); //获取开始时间
				
				try {
					HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
					HttpRequest req = new HttpRequest(HttpResultType.BYTES);
					//设置编码集
					req.setCharset(AlipayConfig.input_charset);
					req.setUrl(url);
			        HttpResponse rep;
			        rep = httpProtocolHandler.execute(req,"","");
					String strResult ="";
			        if (rep == null) {
			        	System.out.println("response is null");
			        }else{
			        	strResult = rep.getStringResult();
			        	status = "success";
			        	//System.out.println("strResult:"+strResult);
			        }
			        
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				long endTime=System.currentTimeMillis(); //获取结束时间

				param.clear();
				update ="update HENLO_SERVER_URL " +
						"set status =:status,timeout=:timeout,edittime = sysdate " +
						"where id = :id";
				
				param.put("status", status);
				param.put("timeout", String.valueOf(endTime-startTime));
				param.put("id", id);
				
				njdbcTemplate.update(update, param);
				
			}
						
			return new ExtReturn(true,msg);
			
		} catch (Exception e) {
			msg = e.toString();
			// TODO: handle exception
		} 
		
		return new ExtReturn(false, msg);
		
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
	
}
