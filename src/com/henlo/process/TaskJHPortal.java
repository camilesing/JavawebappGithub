package com.henlo.process;

import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;


import com.authority.common.springmvc.DateConvertEditor;
import com.authority.common.utils.WebUtils;
import com.authority.web.controller.JHPortalController;

@Service
public class TaskJHPortal {
private static final Logger logger = LoggerFactory.getLogger(TaskJHPortal.class);
	
	@Autowired
	private JHPortalController jhportalcontroller;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	public void dataprocess(){
		String result = "",query="";
		WebUtils web = new WebUtils();
		try {
			System.out.println("TaskJHPortal Start …………………………");
			Calendar cal=Calendar.getInstance();
			cal.setTime(new Date());
			int nowminute = cal.get(Calendar.HOUR_OF_DAY)*60+cal.get(Calendar.MINUTE);
			String time = web.readValue("config/others/config.properties","jhportal.plantime");
			String[] setime= time.split("-");
			String  starttime[] = setime[0].split(":");
			int startminute = Integer.parseInt(starttime[0])*60+Integer.parseInt(starttime[1]);
			String  endtime[] = setime[1].split(":");
			int endminute = Integer.parseInt(endtime[0])*60+Integer.parseInt(endtime[1]);
			
			String status = web.readValue("config/others/config.properties","jhportal.planstatus");
			
			if(nowminute>=startminute&&nowminute<=endminute&&status.equalsIgnoreCase("Y")){
				System.out.println("TaskJHPortal ing …………………………");
				//执行计划任务
				String condition = web.readValue("config/others/config.properties","jhportal.condition");
				result = jhportalcontroller.datasyncprocess(condition);
				
			}
			
			System.out.println("TaskJHPortal End …………………………");
			
		} catch (Exception e) {
			// TODO: handle exception
			result = e.toString();
		}finally{
			if(result!=null&&!result.equalsIgnoreCase("")&&!result.equalsIgnoreCase("success")){
				//发送邮件提醒
				String Email = web.readValue("config/others/config.properties","jhportal.email");
				String title = "JHPortal <dataprocess> Failed";
				String[] listEmail = Email.split(",");
				try {
					for (String str : listEmail) {
						if(str.length()>0){
							String address = str;
							web.execSend(address, title, result);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.error(e.toString());
					e.printStackTrace();
				}
			}
		}
	}
}
