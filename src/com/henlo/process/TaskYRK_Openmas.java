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
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	public void openmas(){
		try {			
			System.out.println("================== Task Start ================== "+new Date());
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
				System.out.println("===============SMS START=====================");
				//要发送的短信信息
				String query =  "select a.id,a.tablename,a.tablerowid,A.PHONE,A.CONTENT "+
								"from SMS_OUTMSG a "+ 
								"where trim(PHONE) is not null "+  
								"and A.STATE ='A' order by CONTENT " ;
				Sms sms = new Sms(webservice);
				List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
				String id ="",address="",message="",GateWayid="",update="";
				//更新现有的状态为D 待发送
				update = "update SMS_OUTMSG a set a.STATE='D' where a.STATE='A' ";
				jdbcTemplate.update(update);
				String ids="",addresss="",send_message="";
				int list_size = list.size();
				boolean sendsms = false ; int smsnum = 0; int listgrow=0;
				for (Map<String, Object> map : list) {
					message = map.get("CONTENT").toString();
					id = map.get("ID").toString();
					address = map.get("PHONE").toString();
					
					sendsms = false ;listgrow++;
					//验证该记录是否已经发送成功或等待状态返回或发送失败,即状态为 B C E ,则不重复发送
					query = "select count(*) from SMS_OUTMSG a where a.state in('B','C','E') and a.id='"+id+"'";
					if(jdbcTemplate.queryForInt(query)==0){
						if(message.equals(send_message)||send_message.equals("")){
							ids = ids +"','"+id;
							addresss = addresss +","+address;
							smsnum++ ;
						}else{
							//开始发送短信,因为内容已经有所不同
							sendsms = true ;
						}
					}
					
					//到达一百条，可以发送
					if(smsnum/100>0)
						sendsms = true ;
					
					//发送短信
					if(sendsms){
						addresss = StringUtils.removeStart(addresss, ",");
						String[] destinationAddresses = addresss.split(",");
						if(send_message.equals(""))
							send_message = message ;
						
						if(destinationAddresses.length>0){
							cal.setTime(new Date());
							nowminute = cal.get(Calendar.HOUR_OF_DAY)*60+cal.get(Calendar.MINUTE);
							//如果时间不符合,则不发送,将记录转入待发送
							if(nowminute>=startminute&&nowminute<=endminute){
								GateWayid = sms.SendMessage(destinationAddresses, send_message, extendCode, ApplicationID, Password);
								update = "update SMS_OUTMSG a set a.state='C',a.MESSAGEID='"+GateWayid+"',MODIFIEDDATE=sysdate where id in('"+ids+"')";
								//update = "update SMS_OUTMSG a set a.state='C',a.MESSAGEID=MESSAGEID||'henlo',MODIFIEDDATE=sysdate where id in('"+ids+"')";
							}else{
								update = "update SMS_OUTMSG a set a.state='A',a.MESSAGEID='"+GateWayid+"',MODIFIEDDATE=sysdate where id in('"+ids+"')";
								//update = "update SMS_OUTMSG a set a.state='A',a.MESSAGEID=MESSAGEID||'henlo',MODIFIEDDATE=sysdate where id in('"+ids+"')";
							}
							jdbcTemplate.update(update);
						}
						//系统参数初始化， 短信累积条数、发送号码、所有记录的id集合
						addresss="";
						ids="";
						
						if(smsnum/100>0){
							
						}else{
							ids = ids +"','"+id;
							addresss = addresss +","+address;
						}
						smsnum = 0;
					}
					
					send_message = message ;
					
					//已经到达列表末尾，可以发送
					if(listgrow==list_size){
						addresss = StringUtils.removeStart(addresss, ",");
						String[] destinationAddresses = addresss.split(",");
						if(send_message.equals(""))
							send_message = message ;
						
						if(destinationAddresses.length>0&&addresss.length()>5){
							cal.setTime(new Date());
							nowminute = cal.get(Calendar.HOUR_OF_DAY)*60+cal.get(Calendar.MINUTE);
							//如果时间不符合,则不发送,将记录转入待发送
							if(nowminute>=startminute&&nowminute<=endminute){
								GateWayid = sms.SendMessage(destinationAddresses, send_message, extendCode, ApplicationID, Password);
								update = "update SMS_OUTMSG a set a.state='C',a.MESSAGEID='"+GateWayid+"',MODIFIEDDATE=sysdate where id in('"+ids+"')";
								//update = "update SMS_OUTMSG a set a.state='C',a.MESSAGEID=MESSAGEID||'henlo',MODIFIEDDATE=sysdate where id in('"+ids+"')";
							}else{
								update = "update SMS_OUTMSG a set a.state='A',a.MESSAGEID='"+GateWayid+"',MODIFIEDDATE=sysdate where id in('"+ids+"')";
								//update = "update SMS_OUTMSG a set a.state='A',a.MESSAGEID=MESSAGEID||'henlo',MODIFIEDDATE=sysdate where id in('"+ids+"')";
							}
							jdbcTemplate.update(update);
						}
						break ;
					}
						
					/*
					String[] destinationAddresses = address.split(",");
					//验证该记录是否已经发送成功或等待状态返回或发送失败,即状态为 B C E ,则不重复发送				
					query = "select count(*) from SMS_OUTMSG a where a.state in('B','C','E') and a.id='"+id+"'";
					if(jdbcTemplate.queryForInt(query)==0){
						GateWayid = sms.SendMessage(destinationAddresses, message, extendCode, ApplicationID, Password);
						update = "update SMS_OUTMSG a set a.state='C',a.MESSAGEID='"+GateWayid+"',CREATIONDATE=sysdate where id ='"+id+"'";
						jdbcTemplate.update(update);
					}*/
				}
				System.out.println("===============SMS END=====================");
			}
			
			System.out.println("================== Task End ================== "+new Date());
			
		}catch(Exception e){
			System.out.println(e);
		}		
	}	
}
