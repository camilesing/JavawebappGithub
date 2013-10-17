package com.authority.web.controller;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.authority.common.springmvc.DateConvertEditor;
import com.chinamobile.openmas.client.Sms;

@Controller
@RequestMapping("/openmas")
public class OpenMasController {
	private static final Logger logger = LoggerFactory.getLogger(OpenMasController.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;
	
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
	
	@RequestMapping("/sendmessage")
	@ResponseBody
	public Object sendmessage(HttpSession session, HttpServletRequest request) {
		String result ="";
		try {
			String address = request.getParameter("address")==null?"":request.getParameter("address").toString();
			String message = request.getParameter("message")==null?"":request.getParameter("message").toString();
			String tablename = request.getParameter("tablename")==null?"":request.getParameter("tablename").toString();
			String tablerowid = request.getParameter("tablerowid")==null?"":request.getParameter("tablerowid").toString();
			
		//	message = java.net.URLDecoder.decode(message,"GB2312");
			message = new String(message.getBytes("ISO-8859-1"),"utf-8");
			
			Sms sms = new Sms(webservice);
			String[] destinationAddresses = address.split(",");
			String GateWayid = "";
			
			GateWayid = sms.SendMessage(destinationAddresses, message, extendCode, ApplicationID, Password);			 
			
			if(!tablename.equals("")){
				String update ="update "+tablename+" set messageid = '"+GateWayid+"' where id='"+tablerowid+"'";
				jdbcTemplate.update(update);
			}
			
			result = "address:"+address+"  message:"+message+ " GateWayid:"+GateWayid;
			
			System.out.println(result);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failed";
		}
		return result;
	}
}
