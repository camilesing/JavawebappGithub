package com.authority.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

import com.authority.common.springmvc.DateConvertEditor;
import com.authority.pojo.ExtReturn;
import com.authority.service.JHPortalService;
import com.authority.service.TransactionManagerService;


@Controller
@RequestMapping("/jhportal")
public class JHPortalController {
	
	private static final Logger logger = LoggerFactory.getLogger(JHPortalController.class);
	
	@Autowired
	private JHPortalService jHPortalService;
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;
	
	
	@Resource(name="jdbcTemplate2")
	private JdbcTemplate jdbcTemplate2;
	
	@Resource(name="njdbcTemplate2")
	private NamedParameterJdbcTemplate njdbcTemplate2;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@RequestMapping("/datasync")
	@ResponseBody
	public Object datasync(HttpSession session, HttpServletRequest request) {
		try {
			String condition = request.getParameter("condition");
			String result = datasyncprocess(condition);
			
			if(result.equalsIgnoreCase("success")||result.equals(""))	
				return new ExtReturn(true,"success");
			else
				return new ExtReturn(false,result);
		} catch (Exception e) {
			// TODO: handle exception
			return new ExtReturn(false,e.toString());
		}
		
	}	
	
	public String datasyncprocess(String condition){
		String query ="",insert="",delete="",update="",result="";
		try {
			query = "select * from H_JHAO where ISACTIVE='Y'";
			condition = condition==null?"":condition;
			if(!condition.equalsIgnoreCase(""))
				query = "select * from H_JHAO where ISACTIVE='Y' and "+condition;
			
			List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
			for (Map<String, Object> map : list) {
				String TABLENNAME = map.get("TABLENNAME")==null?"":map.get("TABLENNAME").toString();
				String COLUMNLIST = map.get("COLUMNLIST")==null?"":map.get("COLUMNLIST").toString();
				String SOURCENAME = map.get("SOURCENAME")==null?"":map.get("SOURCENAME").toString(); 
				String SYNCTYPE   = map.get("SYNCTYPE")==null?"":map.get("SYNCTYPE").toString();
				String SYNCSIGN	  = map.get("SYNCSIGN")==null?"":map.get("SYNCSIGN").toString();
				String ID = map.get("ID").toString();
				query = "select "+COLUMNLIST+" from ";
				
				if(SYNCTYPE.equalsIgnoreCase("OUT")){
					query = query+ SOURCENAME;
					List<Map<String,Object>> listData = jdbcTemplate.queryForList(query);
					update = "update "+SOURCENAME+" set "+SYNCSIGN+"='NY' where nvl("+SYNCSIGN+",'N')='N' ";
					if(!SYNCSIGN.equals(""))
						jdbcTemplate.update(update);
					try {
						jHPortalService.Oracle2Sqlserver(listData, COLUMNLIST, TABLENNAME);
						update = "update "+SOURCENAME+" set "+SYNCSIGN+"='Y' where "+SYNCSIGN+"='NY' ";
					} catch (Exception e) {
						result = result + e.toString();
						update = "update "+SOURCENAME+" set "+SYNCSIGN+"='N' where "+SYNCSIGN+"='NY' ";
						// TODO: handle exception
					}finally{
						if(!SYNCSIGN.equals(""))
							jdbcTemplate.update(update);
					}
				}				
				else if(SYNCTYPE.equalsIgnoreCase("IN")){
					query = query+ TABLENNAME +" where isnull(ifsync,'N')='N' ";
					List<Map<String,Object>> listData = jdbcTemplate2.queryForList(query);
					update = "update "+TABLENNAME+" set "+SYNCSIGN+"='NY' where isnull("+SYNCSIGN+",'N')='N' ";
					if(!SYNCSIGN.equals(""))
						jdbcTemplate2.update(update);
					try {
						jHPortalService.Sqlserver2Oracle(listData, COLUMNLIST, SOURCENAME);
						update = "update "+TABLENNAME+" set "+SYNCSIGN+"='Y' where "+SYNCSIGN+"='NY' "; 
					} catch (Exception e) {
						result = result + e.toString();
						update = "update "+TABLENNAME+" set "+SYNCSIGN+"='N' where "+SYNCSIGN+"='NY' ";
						// TODO: handle exception
					}finally{
						if(!SYNCSIGN.equals(""))
							jdbcTemplate2.update(update);
					}
				}
				
				update = "update H_JHAO set Qty=Qty+1,LASTDATE=sysdate where id ='"+ID+"'";
				jdbcTemplate.update(update);
				
			}
			
			return result;
			
		} catch (Exception e) {
			// TODO: handle exception
			return e.toString();
		}
	}
		
}
