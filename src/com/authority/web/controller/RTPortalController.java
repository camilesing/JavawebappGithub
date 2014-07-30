package com.authority.web.controller;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.config.AlipayConfig;
import com.alipay.util.httpClient.HttpProtocolHandler;
import com.alipay.util.httpClient.HttpRequest;
import com.alipay.util.httpClient.HttpResponse;
import com.alipay.util.httpClient.HttpResultType;
import com.authority.common.springmvc.DateConvertEditor;
import com.authority.common.utils.WebUtils;
import com.authority.pojo.ExtReturn;
import com.authority.service.JHPortalService;
import com.authority.service.TransactionManagerService;
import com.chinamobile.openmas.client.Sms;


@Controller
@RequestMapping("/rtportal")
public class RTPortalController {
	
	private static final Logger logger = LoggerFactory.getLogger(RTPortalController.class);
	
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@RequestMapping("/smssendaction")
	@ResponseBody
	public Object smssendaction(HttpSession session, HttpServletRequest request) {
		try {
			String address = request.getParameter("address");
			String message = request.getParameter("message");
			String plantime = request.getParameter("plantime");
			Random r = new Random();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			
			plantime=plantime==null?"":"";
			if(address==null||message==null)
				return new ExtReturn(false,"参数错误");
			else{
				//发送短信
				int r_int = r.nextInt(1000000);
				String GateWayid = sdf.format(new Date()) + StringUtils.leftPad(String.valueOf(r_int), 6, "0"); //流水号
				String smsresult = smssend(address, message, GateWayid, plantime);
				return new ExtReturn(true,smsresult);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return new ExtReturn(false,e.toString());
		}
	}	
	
	public String smssend(String address,String send_message,String SerialNumber,String ScheduleTime){
		try {
			
			HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
			HttpRequest request = new HttpRequest(HttpResultType.BYTES);
			//设置编码集
			AlipayConfig.input_charset="gbk";
	        request.setCharset(AlipayConfig.input_charset);
	        request.setMethod("POST");
	        
	        WebUtils webUtils = new WebUtils();
	        String smsurl = webUtils.readValue("config/others/config.properties","rt.smsurl");
	        String smsspcode = webUtils.readValue("config/others/config.properties","rt.smsspcode");
	        String smsloginname = webUtils.readValue("config/others/config.properties","rt.smsloginname");
	        String smspassword = webUtils.readValue("config/others/config.properties","rt.smspassword");
	        
	        Map<String, String> paramTemp = new HashMap<String, String>();
	        paramTemp.put("SpCode", smsspcode);
	        paramTemp.put("LoginName", smsloginname);
	        paramTemp.put("Password", smspassword);
	        paramTemp.put("MessageContent", send_message);
	        paramTemp.put("UserNumber", address);
	        paramTemp.put("SerialNumber", SerialNumber);
	        paramTemp.put("ScheduleTime", ScheduleTime); //
	        paramTemp.put("ExtendAccessNum", "");
	        paramTemp.put("f", "1");
	        
	        request.setParameters(generatNameValuePair(paramTemp));
//	        request.setUrl(BASE_URL+"report.do");
	        request.setUrl(smsurl+"Send.do");
	        HttpResponse response;
			response = httpProtocolHandler.execute(request,"","");
	        if (response == null) {
	        	return "";
	        }else{
	        	return response.getStringResult();
	        }
		} catch (Exception e) {
			return e.toString();
			// TODO: handle exception
		}
	}
	
	@RequestMapping("/smssendtaskaction")
	@ResponseBody
	public Object smssendtaskaction(HttpSession session, HttpServletRequest request) {
		try {
			smssendtask(1,9999);
			return new ExtReturn(true,"success");
		} catch (Exception e) {
			// TODO: handle exception
			return new ExtReturn(false,e.toString());
		}
	}
	
	public String smssendtask(int startminute,int endminute ){
		String query ="",insert="",delete="",update="",result="";
		try {
			Random r = new Random();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar cal=Calendar.getInstance();
			cal.setTime(new Date());
			logger.info("===============SMS START=====================");
			//要发送的短信信息
			query = "select a.id,a.tablename,a.tablerowid,A.PHONE,A.CONTENT,PLANTIME "+
					"from SMS_OUTMSG a "+ 
					"where trim(PHONE) is not null and ISACTIVE='Y' "+  
					"and A.STATE ='A' order by CONTENT " ;
			
			List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
			String id ="",address="",message="",GateWayid="",PlanTime="",smsresult="";int nowminute=0;
			//更新现有的状态为D 待发送
			update = "update SMS_OUTMSG a set a.STATE='D' where a.STATE='A' and ISACTIVE='Y' ";
			jdbcTemplate.update(update);
			String ids="",addresss="",send_message="";
			int list_size = list.size();
			boolean sendsms = false ; int smsnum = 0; int listgrow=0;
			for (Map<String, Object> map : list) {
				message = map.get("CONTENT")==null?"":map.get("CONTENT").toString();
				id = map.get("ID")==null?"":map.get("ID").toString();
				address = map.get("PHONE")==null?"":map.get("PHONE").toString();
				PlanTime = map.get("PLANTIME")==null?"":map.get("PLANTIME").toString();
				
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
							//发送短信
							int r_int = r.nextInt(1000000);
							GateWayid = sdf.format(new Date()) + StringUtils.leftPad(String.valueOf(r_int), 6, "0"); //流水号
							smsresult = smssend(address, send_message, GateWayid, PlanTime);
							update = "update SMS_OUTMSG a set a.state='C',a.MESSAGEID='"+GateWayid+"',MODIFIEDDATE=sysdate where id in('"+ids+"')";
							jdbcTemplate.update(update);
														
							//检测返回结果中是否有失败的号码
							String[] smsresults = smsresult.split("&");
							for (String str : smsresults) {
								String[] tmp = str.split("=");
								if(tmp[0].equalsIgnoreCase("faillist")){
									if(tmp.length>1){
										String[] faillist = tmp[1].split(",");
										for (String failphone : faillist) {
											update = "update SMS_OUTMSG a set a.state='E' where MESSAGEID='"+GateWayid+"' and phone ='"+failphone+"'";
											jdbcTemplate.update(update);
										}
									}
								}
							}
							
						}else{
							update = "update SMS_OUTMSG a set a.state='A',MODIFIEDDATE=sysdate where id in('"+ids+"')";
							jdbcTemplate.update(update);
						}
						
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
							//发送短信
							int r_int = r.nextInt(1000000);
							GateWayid = sdf.format(new Date()) + StringUtils.leftPad(String.valueOf(r_int), 6, "0"); //流水号
							smsresult = smssend(address, send_message, GateWayid, PlanTime);
							update = "update SMS_OUTMSG a set a.state='C',a.MESSAGEID='"+GateWayid+"',MODIFIEDDATE=sysdate where id in('"+ids+"')";
							jdbcTemplate.update(update);
							
							//检测返回结果中是否有失败的号码
							String[] smsresults = smsresult.split("&");
							for (String str : smsresults) {
								String[] tmp = str.split("=");
								if(tmp[0].equalsIgnoreCase("faillist")){
									if(tmp.length>1){
										String[] faillist = tmp[1].split(",");
										for (String failphone : faillist) {
											update = "update SMS_OUTMSG a set a.state='E' where MESSAGEID='"+GateWayid+"' and phone ='"+failphone+"'";
											jdbcTemplate.update(update);
										}
									}
								}
							}
						}else{
							update = "update SMS_OUTMSG a set a.state='A',MODIFIEDDATE=sysdate where id in('"+ids+"')";
						}
						jdbcTemplate.update(update);
					}
					break ;
				}
			}
			logger.info("===============SMS END=====================");
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			return e.toString();
		}
	}
	
	@RequestMapping("/smsreporttaskaction")
	@ResponseBody
	public Object smsreporttaskaction(HttpSession session, HttpServletRequest request) {
		try {
			smsreporttask();
			return new ExtReturn(true,"success");
		} catch (Exception e) {
			// TODO: handle exception
			return new ExtReturn(false,e.toString());
		}
	}
	
	public String smsreporttask(){
		String query ="",insert="",delete="",update="",result="";
		try {
			query = "select distinct MESSAGEID from SMS_OUTMSG where STATE='C' ";
			List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
			String MESSAGEID="",ID="",PHONE="";
			for (Map<String, Object> map : list) {
				MESSAGEID = map.get("MESSAGEID")==null?"":map.get("MESSAGEID").toString();
				//根据mid获取信息
				String smsresult = smsreport(MESSAGEID);
				String[] smsresults = smsresult.split("&");
				for (String smsresults_child : smsresults) {
					if(smsresults_child.startsWith("out")){
						String[] outs = smsresults_child.split("=");
						if(outs.length>1){
							String[] sendresult = outs[1].split(";");
							for (String sendresult_child : sendresult) {
								String[] phoneresults = sendresult_child.split(",");
								if(phoneresults.length>2){
									if(phoneresults[2].equalsIgnoreCase("0")){
										update = "update SMS_OUTMSG set STATE='B' where MESSAGEID='"+phoneresults[1]+"' and PHONE='"+phoneresults[2]+"'";
										jdbcTemplate.update(update);
									}else{
										update = "update SMS_OUTMSG set STATE='E' where MESSAGEID='"+phoneresults[1]+"' and PHONE='"+phoneresults[2]+"'";
										jdbcTemplate.update(update);
									}
								}
							}
						}
					}
				}
			}
			
			return "success";
		}catch (Exception e) {
			// TODO: handle exception
			return e.toString();
		}
	}
	
	public String smsreport(String SerialNumber){
		try {
			
			HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
			HttpRequest request = new HttpRequest(HttpResultType.BYTES);
			//设置编码集
			AlipayConfig.input_charset="gbk";
	        request.setCharset(AlipayConfig.input_charset);
	        request.setMethod("POST");
	        
	        WebUtils webUtils = new WebUtils();
	        String smsurl = webUtils.readValue("config/others/config.properties","rt.smsurl");
	        String smsspcode = webUtils.readValue("config/others/config.properties","rt.smsspcode");
	        String smsloginname = webUtils.readValue("config/others/config.properties","rt.smsloginname");
	        String smspassword = webUtils.readValue("config/others/config.properties","rt.smspassword");
	        
	        Map<String, String> paramTemp = new HashMap<String, String>();
	        paramTemp.put("SpCode", smsspcode);
	        paramTemp.put("LoginName", smsloginname);
	        paramTemp.put("Password", smspassword);
	        paramTemp.put("SerialNumber", SerialNumber);
	        
	        request.setParameters(generatNameValuePair(paramTemp));
	        request.setUrl(smsurl+"report.do");
//	        request.setUrl(smsurl+"Send.do");
	        HttpResponse response;
			response = httpProtocolHandler.execute(request,"","");
	        if (response == null) {
	        	return "";
	        }else{
	        	return response.getStringResult();
	        }
		} catch (Exception e) {
			return e.toString();
			// TODO: handle exception
		}
	}
	
	/**
	 * 数据从视图中抽取,并将当前短信的状态更新到视图中
	 * @return
	 */
	@RequestMapping("/dataiotaskaction")
	@ResponseBody
	public Object dataiotaskaction(HttpSession session, HttpServletRequest request) {
		try {
			dataiotask();
			return new ExtReturn(true,"success");
		} catch (Exception e) {
			// TODO: handle exception
			return new ExtReturn(false,e.toString());
		}
	}
	
	public String dataiotask() {
		try {
			String insert="",query="";
			query = "select TABLENAME,TABLEROWID,case when (state='B' or state='C') then '1' else '0' end STATE from SMS_OUTMSG where isactive='Y' and state in('B','C','E') ";
			List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
			//执行存储过程 返写状态
			for (Map<String, Object> map : list) {
				try {
					final int p_id = Integer.parseInt(map.get("TABLEROWID").toString());
					final String v_type = map.get("TABLENAME").toString();
					final int r_code = Integer.parseInt(map.get("STATE").toString());
					//执行存储过程
					String procedure = "{call V_SMSDATA_AM(?,?,?)}";  		
					@SuppressWarnings("unchecked")
					Map<String,Object> map_P= (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {  
			            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
			                cs.setInt(1, p_id);
			                cs.setString(2, v_type);
			                cs.setInt(3, r_code);
			                cs.execute();
			                Map<String,Object> map = new HashMap<String, Object>();  
			                map.put("r_code", "0");
			                return map;
			            }
			        }); 
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			//抽取 V_SMSDATA 数据到 SMS_OUTMSG
			insert ="insert into SMS_OUTMSG(ID, AD_CLIENT_ID, AD_ORG_ID,OWNERID, CREATIONDATE,  ISACTIVE, STATE,  TABLENAME, TABLEROWID,PHONE,CONTENT,PLANTIME) "+
					"select GET_SEQUENCES('SMS_OUTMSG') id,'37' AD_CLIENT_ID,'27' AD_ORG_ID ,'893' OWNERID ,sysdate CREATIONDATE,'Y' ISACTIVE,'A' STATE, "+
					"TABLENAME,TABLEROWID,A.ADDR,body_text,to_char(a.SCHEDULETIME,'yyyyMMddHH24Miss') SCHEDULETIME "+
					"from V_U_MESSAGE a where not exists ( "+
					"	select 'x' from SMS_OUTMSG b where a.TABLEROWID=b.TABLEROWID and a.TABLENAME = B.TABLENAME "+
					") ";
			
			jdbcTemplate.update(insert);
			
			return "success";
		} catch (Exception e) {
			return e.toString();
			// TODO: handle exception
		}
	}
	
	
	/**
     * MAP类型数组转换成NameValuePair类型
     * @param properties  MAP类型数组
     * @return NameValuePair类型数组
     */
    private static NameValuePair[] generatNameValuePair(Map<String, String> properties) {
        NameValuePair[] nameValuePair = new NameValuePair[properties.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            nameValuePair[i++] = new NameValuePair(entry.getKey(), entry.getValue());
        }

        return nameValuePair;
    }
		
}
