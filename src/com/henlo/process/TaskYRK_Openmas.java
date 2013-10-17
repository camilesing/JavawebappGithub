package com.henlo.process;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
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
public class TaskYRK_Openmas {
private static final Logger logger = LoggerFactory.getLogger(TaskYRK_Openmas.class);
	
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
	
	@Value("${openmas.status}")
	private String status;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	public void openmas(){
		try {			
			//要发送的短信信息
			String query =  "select a.id,a.tablename,a.tablerowid,B.ADDR_PHONE,A.CONTENT "+
							"from SMS_OUTMSG a ,U_RECIPIENT b "+ 
							"where A.U_RECIPIENT_ID=b.id and trim(B.ADDR_PHONE) is not null "+  
							"and A.STATE ='A' " ;
			System.out.println("Task Start ==================================== "+new Date());
			if(status!=null&&status.equals("Y")){
				Sms sms = new Sms(webservice);
				List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
				String id ="",address="",message="",GateWayid="",update="";
				for (Map<String, Object> map : list) {
					id = map.get("ID").toString();
					address = map.get("ADDR_PHONE").toString();
					message = map.get("CONTENT").toString();
					String[] destinationAddresses = address.split(",");
					GateWayid = sms.SendMessage(destinationAddresses, message, extendCode, ApplicationID, Password);
					update = "update SMS_OUTMSG a set a.state='C',a.MESSAGEID='"+GateWayid+"',CREATIONDATE=sysdate where id ='"+id+"'";
					jdbcTemplate.update(update);
				}
			}
			System.out.println("Task End ==================================== "+new Date());
			
		}catch(Exception e){
			System.out.println(e);
		}		
	}	
}
