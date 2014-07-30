package com.henlo.process;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;


import com.alipay.config.AlipayConfig;
import com.alipay.util.httpClient.HttpProtocolHandler;
import com.alipay.util.httpClient.HttpRequest;
import com.alipay.util.httpClient.HttpResponse;
import com.alipay.util.httpClient.HttpResultType;
import com.authority.common.springmvc.DateConvertEditor;
import com.authority.pojo.ExtReturn;
import com.authority.web.controller.RTPortalController;
import com.chinamobile.openmas.client.Sms;

@Service
public class TaskRTPortal {
private static final Logger logger = LoggerFactory.getLogger(TaskRTPortal.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;
	
	@Autowired
	private RTPortalController rtportalcontroller;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	public void smssendtask(){
		try {			
			System.out.println("================== Task Start ================== smssendtask "+new Date());
			int startminute=0,endminute=1500;
			String status ="Y";
			try{
				String query ="select nvl(VALUE,DEFAULTVALUE) smssendtime from AD_PARAM where name='henlo.smssendtime'";
				String time = jdbcTemplate.queryForObject(query, String.class); //默认： 08:00-23:00
				String[] setime= time.split("-");
				String  starttime[] = setime[0].split(":");
				startminute = Integer.parseInt(starttime[0])*60+Integer.parseInt(starttime[1]);
				String  endtime[] = setime[1].split(":");
				endminute = Integer.parseInt(endtime[0])*60+Integer.parseInt(endtime[1]);
				
				//短信是否启用参数
				query = "select nvl(VALUE,DEFAULTVALUE) smsstatus from AD_PARAM where name='henlo.smsstatus'";
				status = jdbcTemplate.queryForObject(query,String.class);
				if(status==null||status.equals("")||status.toLowerCase().equals("true"))
					status="Y";
				else
					status="N";
				
			} catch (Exception e){
				System.out.println(e);
			}
			
			Calendar cal=Calendar.getInstance();
			cal.setTime(new Date());
			int nowminute = cal.get(Calendar.HOUR_OF_DAY)*60+cal.get(Calendar.MINUTE);
			if(nowminute>=startminute&&nowminute<=endminute&&status.equals("Y"))
				status="Y";
			else
				status="N";
			
			System.out.println(startminute+":"+endminute+":"+nowminute+":"+status);
			
			if(status!=null&&status.equals("Y")){
				rtportalcontroller.smssendtask(startminute, endminute);
			}
			
			System.out.println("================== Task End ================== smssendtask "+new Date());
			
		}catch(Exception e){
			logger.error("Exception:{} ", e);
			System.out.println(e);
		}		
	}
	
	public void smsreporttask(){
		try {			
			System.out.println("================== Task Start ================ smsreporttask "+new Date());
			rtportalcontroller.smsreporttask();			
			System.out.println("================== Task End   ================ smsreporttask "+new Date());			
		}catch(Exception e){
			logger.error("Exception:{} ", e);
			System.out.println(e);
		}		
	}
	
	public void dataiotask(){
		try {			
			System.out.println("================== Task Start ================ dataiotask "+new Date());
			rtportalcontroller.dataiotask();			
			System.out.println("================== Task End   ================ dataiotask "+new Date());			
		}catch(Exception e){
			logger.error("Exception:{} ", e);
			System.out.println(e);
		}
	}
		
}
