package com.henlo.process;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;


import com.alipay.config.AlipayConfig;
import com.alipay.util.httpClient.HttpProtocolHandler;
import com.alipay.util.httpClient.HttpRequest;
import com.alipay.util.httpClient.HttpResultType;
import com.authority.common.springmvc.DateConvertEditor;
import com.chinamobile.openmas.client.Sms;

@Service
public class TaskGMQ {
private static final Logger logger = LoggerFactory.getLogger(TaskGMQ.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Value("${openmas.sms}")
	private String webservice;
	
	@Value("${openmas.extendCode}")
	private String extendCode;
	
	@Value("${openmas.ApplicationID}")
	private String ApplicationID;
	
	@Value("${openmas.Password}")
	private String Password;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	public void openmas(){
		try {
			/*String query = "select  count(*) from b_po_boxno where TEST_STATUS=2 ";
			int count = jdbcTemplate.queryForInt(query);
			System.out.println("=============开始执行 OPENMAS============="+new Date());
			System.out.println(count);	
			//设置编码集
	        String address = "18857846128";
	        String message = "短信测试";
	        Sms sms = new Sms(webservice);
			String[] destinationAddresses = address.split(",");
			String GateWayid = "";
			GateWayid = sms.SendMessage(destinationAddresses, message, extendCode, ApplicationID, Password);
			System.out.println("=============结束执行 OPENMAS============="+new Date());*/
		}catch(Exception e){
			System.out.println(e);
		}
		
	}	
}
