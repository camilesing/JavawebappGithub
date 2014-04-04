package com.henlo.process;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;


import com.authority.common.springmvc.DateConvertEditor;
import com.authority.common.utils.WebUtils;
import com.authority.web.controller.YqgdRequestdataController;

@Service
public class TaskYQGD_ChinaPay {
private static final Logger logger = LoggerFactory.getLogger(TaskYQGD_ChinaPay.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private YqgdRequestdataController yqgdRequestdataController;
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	public void dataprocess(){
		String result = "",query="";
		try {
			System.out.println("TaskYQGD_ChinaPay Start …………………………");
			Calendar cal=Calendar.getInstance();
			cal.setTime(new Date());
			int nowminute = cal.get(Calendar.HOUR_OF_DAY)*60+cal.get(Calendar.MINUTE);
			WebUtils web = new WebUtils();
			
			String time = web.readValue("config/others/config.properties","yqgd.plantime");
			String[] setime= time.split("-");
			String  starttime[] = setime[0].split(":");
			int startminute = Integer.parseInt(starttime[0])*60+Integer.parseInt(starttime[1]);
			String  endtime[] = setime[1].split(":");
			int endminute = Integer.parseInt(endtime[0])*60+Integer.parseInt(endtime[1]);
			
			String status = web.readValue("config/others/config.properties","yqgd.planstatus");
			
			if(nowminute>=startminute&&nowminute<=endminute&&status.equalsIgnoreCase("Y")){
				System.out.println("TaskYQGD_ChinaPay ing …………………………");
				//执行计划任务
				result = yqgdRequestdataController.plan_query_request();
			}
			
			System.out.println("TaskYQGD_ChinaPay End …………………………");
			
		} catch (Exception e) {
			// TODO: handle exception
			result = e.toString();
		}finally{
			if(result!=null&&!result.equalsIgnoreCase("")&&!result.equalsIgnoreCase("success")){
				//发送邮件提醒
				query = "select * from BASE_Interface_Email where isactive='Y' ";
				List<Map<String, Object>> EmailMap = jdbcTemplate.queryForList(query);
				String title = "YQGD <dataprocess> Failed";
				WebUtils webutils = new WebUtils();
				try {
					for (Map<String, Object> map : EmailMap) {
						String address = map.get("MAIL").toString();
						webutils.execSend(address, title, result);
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
