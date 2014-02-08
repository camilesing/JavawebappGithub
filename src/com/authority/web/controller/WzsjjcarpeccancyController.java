package com.authority.web.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.authority.common.springmvc.DateConvertEditor;
import com.authority.common.utils.ChinaMobileMessageService;
import com.authority.common.utils.WebUtils;
import com.authority.common.utils.XmlOperateUtil;
import com.authority.pojo.BaseUsers;
import com.authority.pojo.Criteria;
import com.authority.pojo.ExceptionReturn;
import com.authority.pojo.ExtGridReturn;
import com.authority.pojo.ExtPager;
import com.authority.pojo.ExtReturn;
import com.authority.pojo.WzsjjSmsreturn;
import com.authority.pojo.WzsjjSmssend;
import com.authority.pojo.Wzsjjcarpeccancy;
import com.authority.service.WzsjjSmsreturnService;
import com.authority.service.WzsjjSmssendService;
import com.authority.service.WzsjjcarpeccancyService;
import com.authority.web.interseptor.WebConstants;
import com.unionpay.upop.sdk.QuickPayConf;
import com.unionpay.upop.sdk.QuickPayQuery;
import com.unionpay.upop.sdk.QuickPaySampleServLet;
import com.unionpay.upop.sdk.QuickPayUtils;

/**
 * 后台资源、系统菜单相关
 * 
 * @author poe
 * @date 2014年1月2日
 */
@Controller
@RequestMapping("/wzsjjcarpeccancy")
public class WzsjjcarpeccancyController {

	private static final Logger logger = LoggerFactory.getLogger(WzsjjcarpeccancyController.class);
	@Autowired
	private WzsjjcarpeccancyService wzsjjcarpeccancyService;
	
	@Autowired
	private WzsjjSmssendService wzsjjSmssendService;
	
	@Autowired
	private WzsjjSmsreturnService wzsjjSmsreturnService;
	
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
	 * index
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String wzsjjcarpeccancy() {
		return "user/wzsjj_carpeccancy";
	}

	/**
	 * 所有信息
	 */
	@RequestMapping("/all")
	@ResponseBody
	public Object all(ExtPager pager, @RequestParam(required = false) String chep,HttpSession session, HttpServletRequest request) {
		try {
			Criteria criteria = new Criteria();
			// 设置分页信息
			if (pager.getLimit() != null && pager.getStart() != null) {
				criteria.setOracleEnd(pager.getLimit() + pager.getStart());
				criteria.setOracleStart(pager.getStart());
			}
			// 排序信息
			if (StringUtils.isNotBlank(pager.getDir()) && StringUtils.isNotBlank(pager.getSort())) {
				criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
			} else {
				criteria.setOrderByClause(" edittime desc nulls last,addtime desc ");
			}
			if (StringUtils.isNotBlank(chep)) {
				criteria.put("chepLike", chep);
			}
			BaseUsers baseUser = (BaseUsers)session.getAttribute(WebConstants.CURRENT_USER);
			
			criteria.put("isdisplay", "1");
			criteria.put("userid", baseUser.getUserId());
			List<Wzsjjcarpeccancy> list = this.wzsjjcarpeccancyService.selectByExample(criteria);
			int total = this.wzsjjcarpeccancyService.countByExample(criteria);
			logger.debug("total:{}", total);
			return new ExtGridReturn(total, list);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
	/**
	 * 处理状态 
		0 未确认  01 待返回(未确认)  02 已返回(未确认)

		1 已确认未缴费  11 待返回(已确认未缴费) 12 已返回(已确认未缴费)
		
		2 已缴费  21 待返回(未缴费)  22 已返回(银联端未缴费)
	 */
	
	/**
	 * 违章确认动作 ,发送到手机端
	 */
	@RequestMapping("/makesure")
	@ResponseBody
	public Object makesure(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		WebUtils web = new WebUtils();
		try{
			if(id==null||id.equals("")){
				return new ExtReturn(false,"参数传递无效,请重新打开确认");
			}else{
				Wzsjjcarpeccancy record = wzsjjcarpeccancyService.selectByPrimaryKey(id);
				if(record.getChulzt()==null||record.getChulzt().equals("0")){
					//return new ExtReturn(false,"违章已经确认");
					BaseUsers baseUser = (BaseUsers)session.getAttribute(WebConstants.CURRENT_USER);
					//========================发送短信测试=========================Begin
					String webservice = web.readValue("config/others/config.properties","chinamobile.webservice");
					String dbuser = web.readValue("config/others/config.properties","chinamobile.dbuser");
					String src_tele_num = web.readValue("config/others/config.properties","chinamobile.corporation");
					int pwd = Integer.parseInt(web.readValue("config/others/config.properties","chinamobile.password"));
					
					String method = "sendmsg";
					String msg_id = "-1";
					//根据Sequence 获取ID
					String query = "select WZSJJ_MSG_ID_SQ.Nextval from dual ";
					msg_id = jdbcTemplate.queryForObject(query, String.class);
					String dest_tele_num =record.getLianxfs();
					//手机末尾数 4位*3+579 
					String password = String.valueOf(Integer.parseInt(dest_tele_num.substring(dest_tele_num.length()-4))*3+pwd);
					String msg = record.getWeifxx()+web.readValue("config/others/config.properties", "chinamobile.makesure");
					String message= "" ;
					src_tele_num = src_tele_num + msg_id;
					message = ChinaMobileMessageService.add_msg_t();
					message = message + ChinaMobileMessageService.add_msg_b(msg_id, password, src_tele_num, dest_tele_num, msg);
					message = message + ChinaMobileMessageService.add_msg_w();
					
					String result = ChinaMobileMessageService.send_msg(webservice, method, dbuser, message);
					System.out.println("ChinaMobileMessageService:"+result);
					//发送成功之后，将状态改为 01 待返回(未确认) 
					record.setChulzt("01");
					record.setEdittime(new Date());
					
					//根据返回的 result 结果,将其信息 插入到 表  WZSJJ_SMSSEND 
					XmlOperateUtil xmlOperateUtil = new XmlOperateUtil();
					Document document = xmlOperateUtil.generateDocumentByString(result);
					
					List list = document.selectNodes("//infos//info");
					Iterator iter_list = list.iterator();
					if(iter_list.hasNext()){
						WzsjjSmssend wzsjjSmssend = new WzsjjSmssend();
						Element element = (Element) iter_list.next();
				           for (Iterator ieson = element.elementIterator(); ieson.hasNext();) {
				               Element elementSon = (Element) ieson.next();
				               if(elementSon.getName().equalsIgnoreCase("msg_id")){
				            	   wzsjjSmssend.setMsgId(elementSon.getText());
				               }
				               if(elementSon.getName().equalsIgnoreCase("state")){
				            	   wzsjjSmssend.setState(elementSon.getText());
				               }
				           }
				           
				        wzsjjSmssend.setMsg(message) ;
				        wzsjjSmssend.setPassword(password);
				        wzsjjSmssend.setSrcTeleNum(src_tele_num);
				        wzsjjSmssend.setDestTeleNum(dest_tele_num);
				        wzsjjSmssend.setAddip(WebUtils.getIpAddr(request));
				        wzsjjSmssend.setAddwho(baseUser.getAccount());
				        wzsjjSmssend.setFromtablename("WZSJJ_CARPECCANCY_0"); //从 未确认状态 发出
				        wzsjjSmssend.setFromrowid(record.getId());
				        
				        //只有发送成功了，才更新状态 为 01 待返回(未确认)  
				        if(wzsjjSmssend.getState().equalsIgnoreCase("0"))
				        	wzsjjcarpeccancyService.updateByPrimaryKeySelective(record);
				        
				        wzsjjSmssendService.insertSelective(wzsjjSmssend);
					}
					//========================发送短信测试=========================End
				}else{
					return new ExtReturn(false,"违章已经确认");
				}
			}
			return new ExtReturn(true,"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ExceptionReturn(e);
		}
		
	}
	
	
	/**
	 * 验证手机端回复结果
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/makesure_verify")
	@ResponseBody
	public Object makesure_verify(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		WebUtils web = new WebUtils();
		try{
			if(id==null||id.equals("")){
				return new ExtReturn(false,"参数传递无效,请重新打开确认");
			}else{
				Wzsjjcarpeccancy record = wzsjjcarpeccancyService.selectByPrimaryKey(id);
				if(record.getChulzt()==null||record.getChulzt().equals("01")||record.getChulzt().equals("02")){
					//return new ExtReturn(false,"违章已经确认");
					BaseUsers baseUser = (BaseUsers)session.getAttribute(WebConstants.CURRENT_USER);
					//========================发送短信测试=========================Begin
					String webservice = web.readValue("config/others/config.properties","chinamobile.webservice");
					String dbuser = web.readValue("config/others/config.properties","chinamobile.dbuser");
					String method = "deliver";
					String result = ChinaMobileMessageService.deliver(webservice, method, dbuser);
					System.out.println("ChinaMobileMessageService:"+result);
					
					//模拟返回result 
					/*result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+ 
							  "<infos>"+ 
							   "<info>"+ 
							    "<ID><![CDATA[4]]></ID>"+  
							    "<SRCMSGR><![CDATA[106573077996]]></SRCMSGR>"+ 
							    "<DESTMSGR><![CDATA[18857846128]]></DESTMSGR>"+ 
							    "<MSG><![CDATA[N]]></MSG>"+ 
							    "<TIME><![CDATA[2014-01-07 09:00:06]]></TIME>"+  
							   "</info>"+ 
							  "</infos>";*/
					
					//根据返回的 result 结果,将其信息 插入到 表  WZSJJ_SMSSEND 
					XmlOperateUtil xmlOperateUtil = new XmlOperateUtil();
					Document document = xmlOperateUtil.generateDocumentByString(result);
					
					//循环读取短信返回的结果集
					List list = document.selectNodes("//infos//info");
					Iterator iter_list = list.iterator();
					while(iter_list.hasNext()){
						WzsjjSmsreturn wzsjjSmsreturn = new WzsjjSmsreturn();
						Element element = (Element) iter_list.next();
				           for (Iterator ieson = element.elementIterator(); ieson.hasNext();) {
				               Element elementSon = (Element) ieson.next();
				               if(elementSon.getName().equalsIgnoreCase("ID"))
				            	   wzsjjSmsreturn.setMsgId(elementSon.getText());
				               else if(elementSon.getName().equalsIgnoreCase("DESTADDR"))
				            	   wzsjjSmsreturn.setSrcmsgr(elementSon.getText()); 
				               else if(elementSon.getName().equalsIgnoreCase("SRCADDR"))
				            	   wzsjjSmsreturn.setDestmsgr(elementSon.getText()); 
				               else if(elementSon.getName().equalsIgnoreCase("MSG"))
				            	   wzsjjSmsreturn.setMsg(elementSon.getText()); 
				               else if(elementSon.getName().equalsIgnoreCase("TIME"))
				            	   wzsjjSmsreturn.setTime(elementSon.getText()); 
				           }
			           wzsjjSmsreturn.setAddip(WebUtils.getIpAddr(request));
			           wzsjjSmsreturn.setAddwho(baseUser.getAccount());
			           //先插入短信的数据
			           wzsjjSmsreturnService.insertSelective(wzsjjSmsreturn); 
			           
		        	   //根据 msg_id 和手机号码 去追寻原始记录的id
		        	   Criteria criteria = new Criteria();
		        	   criteria.put("srcTeleNum", wzsjjSmsreturn.getSrcmsgr());
		        	   criteria.put("destTeleNumLikeOther", wzsjjSmsreturn.getDestmsgr());
		        	   criteria.put("isdisplay", "1");
		        	   List<WzsjjSmssend> list_smssend = wzsjjSmssendService.selectByExample(criteria);
		        	   if(list_smssend.size()==0){ //不存在记录，进入下一层循环
		        		   break;
		        	   }
		        	   
		        	   String Fromtablename = list_smssend.get(0).getFromtablename();
		        	   String Fromrowid = list_smssend.get(0).getFromrowid();
		        	   
			           //根据msg_id 以及回复的内容比如 Y、N 去触发信息发送到 交警部门，按理，应该是可以返回处理的 通知书编号 ，然后更新原有的记录
		        	   if(Fromtablename.equalsIgnoreCase("WZSJJ_CARPECCANCY_0")){ //对应未 确认的状态
		        		   /*Wzsjjcarpeccancy record_update = new Wzsjjcarpeccancy();
		        		   record_update.setId(Fromrowid);*/
		        		   
		        		   //查询该ID 所处于的状态是否允许  0 未确认  01 待返回(未确认)  02 已返回(未确认)   
		        		   Wzsjjcarpeccancy record_update = wzsjjcarpeccancyService.selectByPrimaryKey(Fromrowid);
		        		   if(record_update.getChulzt().equalsIgnoreCase("0")||record_update.getChulzt().equalsIgnoreCase("01")||record_update.getChulzt().equalsIgnoreCase("02"))
		        			  ;
		        		   else
		        			  break;
		        		   
		        		   if(wzsjjSmsreturn.getMsg().equalsIgnoreCase("Y")){
			        		   record_update.setChulzt("1"); //更新为   已确认未缴费
				           }else{
				        	   record_update.setChulzt("02"); //更新为 已返回(未确认)
				           }
		        		   
		        		   
		        		   if(record_update.getChulzt().equalsIgnoreCase("1")){
		        			   //已经确认发送数据给交警网站，返回 通知书编号 与交警接口对接
		        			   
		        			   //模拟返回通知书编号
					           SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
							   Date date = new Date();
							   String date_str = sdf.format(new Date());
					           String tongzsbh="3303"+date_str;
					           
					           record_update.setTongzsbh(tongzsbh);
		        		   }
		        		   
		        		   record_update.setEdittime(new Date());
		        		   wzsjjcarpeccancyService.updateByPrimaryKeySelective(record_update);
		        		   
		        		   //处理完毕之后,将该条回复短信设置成 isdisplay = 0 已经处理
		        		   wzsjjSmsreturn.setIsdisplay("0");
		        		   wzsjjSmsreturnService.updateByPrimaryKeySelective(wzsjjSmsreturn);
		        		   
		        		   //今后还会跑一个计划，专门针对 遗留的未处理的信息，但是需要判断该条信息是否已经有回复了，且时间在此之后
		        		   
		        	   }
			           
					}
					//========================发送短信测试=========================End
				}else{
					return new ExtReturn(false,"状态有误");
				}
			}
			return new ExtReturn(true,"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ExceptionReturn(e);
		}
		
	}
	
	
	/**
	 * 跳转到支付页面 在线
	 */
	@RequestMapping("/pay_online")
	@ResponseBody
	public void pay_online(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		String tongzsbh = request.getParameter("tongzsbh");
		if(tongzsbh==null||tongzsbh.equals("")){
			StringBuilder html = new StringBuilder();
			html.append("页面参数信息传值有误,请登录重试！");
			response.setContentType("text/html;charset="+QuickPayConf.charset);   
			response.setCharacterEncoding("UTF-8");
			try {
				response.getWriter().write(html.toString());
			} catch (IOException e) {
				
			}
			response.setStatus(200);
		}
		try {
			
			BaseUsers baseUser = (BaseUsers)session.getAttribute(WebConstants.CURRENT_USER);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date = new Date();
			String date_str = sdf.format(new Date());
			
			//根据通知书编号读取 缴费信息
			Criteria criteria = new Criteria();
			criteria.put("tongzsbh", tongzsbh);
			List<Wzsjjcarpeccancy> list = this.wzsjjcarpeccancyService.selectByExample(criteria);
			String orderAmount = list.get(0).getFakje().toString();
			String orderNumber = list.get(0).getTongzsbh();
			String orderTime = date_str;
			String customerIp = WebUtils.getIpAddr(request);
			String customerName = baseUser.getAccount();
			BigDecimal bigDecimal = new BigDecimal(orderAmount);
			bigDecimal = bigDecimal.multiply(new BigDecimal(100));
			orderAmount = String.valueOf(bigDecimal.intValue());
			Map<String, Object> Param;
		
			Param = new HashMap<String, Object>();
			Param.put("orderAmount", orderAmount);
			Param.put("orderNumber", orderNumber);
			Param.put("customerIp", customerIp);
			Param.put("customerName", customerName);
			Param.put("orderTime", orderTime);
			Param.put("merFrontEndUrl", "http://www.henlo.net");
			
			QuickPaySampleServLet Payservice = new QuickPaySampleServLet();
			Payservice.service(request, response ,Param);	
			
			Wzsjjcarpeccancy record = new Wzsjjcarpeccancy();
			record.setId(list.get(0).getId());
			record.setEdittime(date);
			record.setEditip(WebUtils.getIpAddr(request));
			record.setEditwho(baseUser.getAccount());
			record.setChulzt("21"); // 待返回(未缴费)
			this.wzsjjcarpeccancyService.updateByPrimaryKeySelective(record);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*发送支付请求到手机端*/
	@RequestMapping("/pay_mobilephone")
	@ResponseBody
	public Object pay_mobilephone(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		//1.如果银联的扣费不需要用户来确认,则由短信平台发出确认支付的请求，收到用户的确认之后，再发送支付请求给银联端，完成扣款
		//2.如果银联的支付需要用户手机来确认，则直接发送请求给银联，银联完成扣款之后，再更新系统中的扣款状态，再去发送已经扣款的状态给交警那边
		WebUtils web = new WebUtils();
		String id = request.getParameter("id");
		try{
			if(id==null||id.equals("")){
				return new ExtReturn(false,"参数传递无效,请重新打开确认");
			}else{
				Wzsjjcarpeccancy record = wzsjjcarpeccancyService.selectByPrimaryKey(id);
				if(record.getChulzt()==null||record.getChulzt().equals("1")){
					//return new ExtReturn(false,"违章已经确认");
					BaseUsers baseUser = (BaseUsers)session.getAttribute(WebConstants.CURRENT_USER);
					//========================发送短信测试=========================Begin
					String webservice = web.readValue("config/others/config.properties","chinamobile.webservice");
					String dbuser = web.readValue("config/others/config.properties","chinamobile.dbuser");
					String src_tele_num = web.readValue("config/others/config.properties","chinamobile.corporation");
					int pwd = Integer.parseInt(web.readValue("config/others/config.properties","chinamobile.password"));
					
					String method = "sendmsg";
					String msg_id = "-1";
					//根据Sequence 获取ID
					String query = "select WZSJJ_MSG_ID_SQ.Nextval from dual ";
					msg_id = jdbcTemplate.queryForObject(query, String.class);
					src_tele_num = src_tele_num + msg_id;
					
					String dest_tele_num =record.getLianxfs();
					//手机末尾数 4位*3+579 
					String password = String.valueOf(Integer.parseInt(dest_tele_num.substring(dest_tele_num.length()-4))*3+pwd);
					String msg = record.getWeifxx()+web.readValue("config/others/config.properties", "chinamobile.pay_mobilephone");;
					String message= "" ;
					
					message = ChinaMobileMessageService.add_msg_t();
					message = message + ChinaMobileMessageService.add_msg_b(msg_id, password, src_tele_num, dest_tele_num, msg);
					message = message + ChinaMobileMessageService.add_msg_w();
					
					String result = ChinaMobileMessageService.send_msg(webservice, method, dbuser, message);
					System.out.println("ChinaMobileMessageService:"+result);
					//发送之后，将状态改为11  待返回(已确认未缴费)
					record.setChulzt("11");
					record.setEdittime(new Date());
					
					//根据返回的 result 结果,将其信息 插入到 表  WZSJJ_SMSSEND 
					XmlOperateUtil xmlOperateUtil = new XmlOperateUtil();
					Document document = xmlOperateUtil.generateDocumentByString(result);
					
					List list = document.selectNodes("//infos//info");
					Iterator iter_list = list.iterator();
					if(iter_list.hasNext()){
						WzsjjSmssend wzsjjSmssend = new WzsjjSmssend();
						Element element = (Element) iter_list.next();
				           for (Iterator ieson = element.elementIterator(); ieson.hasNext();) {
				               Element elementSon = (Element) ieson.next();
				               if(elementSon.getName().equalsIgnoreCase("msg_id")){
				            	   wzsjjSmssend.setMsgId(elementSon.getText());
				               }
				               if(elementSon.getName().equalsIgnoreCase("state")){
				            	   wzsjjSmssend.setState(elementSon.getText());
				               }
				           }
				        wzsjjSmssend.setMsg(message) ;
				        wzsjjSmssend.setPassword(password);
				        wzsjjSmssend.setSrcTeleNum(src_tele_num);
				        wzsjjSmssend.setDestTeleNum(dest_tele_num);
				        wzsjjSmssend.setAddip(WebUtils.getIpAddr(request));
				        wzsjjSmssend.setAddwho(baseUser.getAccount());
				        wzsjjSmssend.setFromtablename("WZSJJ_CARPECCANCY_1"); //从已经确认状态发出
				        wzsjjSmssend.setFromrowid(record.getId());
				        
				        //只有发送成功了，才更新状态 为 01 待返回(未确认)  
				        if(wzsjjSmssend.getState().equalsIgnoreCase("0"))
				        	wzsjjcarpeccancyService.updateByPrimaryKeySelective(record);
				        
				        wzsjjSmssendService.insertSelective(wzsjjSmssend);
				        
					}
					//========================发送短信测试=========================End
				}else{
					return new ExtReturn(false,"状态有误");
				}
			}
			return new ExtReturn(true,"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ExceptionReturn(e);
		}
		
	}
	
	/*发送支付请求验证*/
	@RequestMapping("/pay_mobilephone_verify")
	@ResponseBody
	public Object pay_mobilephone_verify(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		//1.如果银联的扣费不需要用户来确认,则由短信平台发出确认支付的请求，收到用户的确认之后，再发送支付请求给银联端，完成扣款
		//2.如果银联的支付需要用户手机来确认，则直接发送请求给银联，银联完成扣款之后，再更新系统中的扣款状态，再去发送已经扣款的状态给交警那边
		String id = request.getParameter("id");
		WebUtils web = new WebUtils();
		try{
			if(id==null||id.equals("")){
				return new ExtReturn(false,"参数传递无效,请重新打开确认");
			}else{
				Wzsjjcarpeccancy record = wzsjjcarpeccancyService.selectByPrimaryKey(id);
				if(record.getChulzt()==null||record.getChulzt().equals("11")||record.getChulzt().equals("12")){
					//return new ExtReturn(false,"违章已经确认");
					BaseUsers baseUser = (BaseUsers)session.getAttribute(WebConstants.CURRENT_USER);
					//========================发送短信测试=========================Begin
					String webservice = web.readValue("config/others/config.properties","chinamobile.webservice");
					String dbuser = web.readValue("config/others/config.properties","chinamobile.dbuser");
					
					String method = "deliver";
					String result = ChinaMobileMessageService.deliver(webservice, method, dbuser);
					System.out.println("ChinaMobileMessageService:"+result);
					//根据返回的 result 结果,将其信息 插入到 表  WZSJJ_SMSSEND 
					XmlOperateUtil xmlOperateUtil = new XmlOperateUtil();
					Document document = xmlOperateUtil.generateDocumentByString(result);
					
					//循环读取短信返回的结果集
					List list = document.selectNodes("//infos//info");
					Iterator iter_list = list.iterator();
					while(iter_list.hasNext()){
						WzsjjSmsreturn wzsjjSmsreturn = new WzsjjSmsreturn();
						Element element = (Element) iter_list.next();
				           for (Iterator ieson = element.elementIterator(); ieson.hasNext();) {
				               Element elementSon = (Element) ieson.next();
				               if(elementSon.getName().equalsIgnoreCase("ID"))
				            	   wzsjjSmsreturn.setMsgId(elementSon.getText());
				               else if(elementSon.getName().equalsIgnoreCase("DESTADDR"))
				            	   wzsjjSmsreturn.setSrcmsgr(elementSon.getText()); 
				               else if(elementSon.getName().equalsIgnoreCase("SRCADDR"))
				            	   wzsjjSmsreturn.setDestmsgr(elementSon.getText()); 
				               else if(elementSon.getName().equalsIgnoreCase("MSG"))
				            	   wzsjjSmsreturn.setMsg(elementSon.getText()); 
				               else if(elementSon.getName().equalsIgnoreCase("TIME"))
				            	   wzsjjSmsreturn.setTime(elementSon.getText()); 
				           }
			           wzsjjSmsreturn.setAddip(WebUtils.getIpAddr(request));
			           wzsjjSmsreturn.setAddwho(baseUser.getAccount());
			           //先插入短信的数据
			           wzsjjSmsreturnService.insertSelective(wzsjjSmsreturn); 
			           
		        	   //根据 msg_id 和手机号码 去追寻原始记录的id
		        	   Criteria criteria = new Criteria();
		        	   criteria.put("srcTeleNum", wzsjjSmsreturn.getSrcmsgr());
		        	   criteria.put("destTeleNumLikeOther", wzsjjSmsreturn.getDestmsgr());
		        	   List<WzsjjSmssend> list_smssend = wzsjjSmssendService.selectByExample(criteria);
		        	   if(list_smssend.size()==0){ //不存在记录，进入下一层循环
		        		   break;
		        	   }
		        	   String Fromtablename = list_smssend.get(0).getFromtablename();
		        	   String Fromrowid = list_smssend.get(0).getFromrowid();
			           //根据msg_id 以及回复的内容比如 Y、N 去触发信息发送到 交警部门，按理，应该是可以返回处理的 通知书编号 ，然后更新原有的记录
		        	   if(Fromtablename.equalsIgnoreCase("WZSJJ_CARPECCANCY_1")){
		        		   /*Wzsjjcarpeccancy record_update = new Wzsjjcarpeccancy();
		        		   record_update.setId(Fromrowid);*/
		        		   
		        		   //查询该记录所处于的状态是否被允许  11 待返回(已确认未缴费)  12 已返回(已确认未缴费)
		        		   Wzsjjcarpeccancy record_update = wzsjjcarpeccancyService.selectByPrimaryKey(Fromrowid);
		        		   if(record_update.getChulzt().equalsIgnoreCase("11")||record_update.getChulzt().equalsIgnoreCase("12"))
		        			  ;
		        		   else
		        			  break;
		        		   
		        		   if(wzsjjSmsreturn.getMsg().equalsIgnoreCase("Y")){
			        		   record_update.setChulzt("21"); //更新为 待返回(未缴费)
				           }else{
				        	   record_update.setChulzt("12"); //更新为 已返回(已确认未缴费)
				           }
		        		   
		        		   if(record_update.getChulzt().equalsIgnoreCase("21")){
		        			   //已经确认数据   发送给银联 ，与银联的接口 ，未必能直接返回数据
		        			   
		        			   //假设返回已缴费数据
		        			   record_update.setChulzt("2");//已经缴费 或者  22 已返回(银联端未缴费)
		        			   
		        			   
		        		   }
		        		   record_update.setEdittime(new Date());
		        		   wzsjjcarpeccancyService.updateByPrimaryKeySelective(record_update);
		        		   
		        		   //处理完毕之后,将该条回复短信设置成 isdisplay = 0 已经处理
		        		   wzsjjSmsreturn.setIsdisplay("0");
		        		   wzsjjSmsreturnService.updateByPrimaryKeySelective(wzsjjSmsreturn);
		        		   
		        	   }
			           
					}
					//========================发送短信测试=========================End
				}else{
					return new ExtReturn(false,"状态有误");
				}
			}
			return new ExtReturn(true,"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ExceptionReturn(e);
		}
		
	}
	
	/**
	 * 缴费确认
	 * */
	@RequestMapping("/verify")
	@ResponseBody
	public Object verify(HttpSession session, HttpServletRequest request){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			
			String tongzsbh =request.getParameter("tongzsbh");
			String chelbm   =request.getParameter("chelbm");
			if(tongzsbh==null||tongzsbh.equals("")){
				return new ExtReturn(false, "通知书编号不能为空");
			}
			if(chelbm==null||chelbm.equals("")){
				return new ExtReturn(false, "车辆编码不能为空");
			}
			Criteria search = new Criteria();
			search.put("tongzsbh", tongzsbh);
			search.put("chelbm", chelbm);
			List<Wzsjjcarpeccancy> list = this.wzsjjcarpeccancyService.selectByExample(search);
			Wzsjjcarpeccancy record = list.get(0);
			
			//发送银联端查询交易记录
			QuickPayQuery quickPayQuery = new QuickPayQuery();
			String res = quickPayQuery.query("01", tongzsbh,sdf.format(record.getEdittime()));
			if (res != null && !"".equals(res)) {
				String[] arr = QuickPayUtils.getResArr(res);
				if(checkSecurity(arr)){//验证签名
					String queryResult = "";
					for (int i = 0; i < arr.length; i++) {
						String[] queryResultArr = arr[i].split("=");
						// 处理商户业务逻辑
						if (queryResultArr.length >= 2 && "queryResult".equals(queryResultArr[0])) {
							queryResult = arr[i].substring(queryResultArr[0].length()+1);
							break;
						}
					}
					if(queryResult!=""){
						if ("0".equals(queryResult)) {
							record.setChulzt("2");
							System.out.println("交易成功");
						}
						if ("1".equals(queryResult)) {
							record.setChulzt("22");
							System.out.println("交易失败");
						}
						if ("2".equals(queryResult)) {
							record.setChulzt("21");
							System.out.println("交易处理中");
						}
						if ("3".equals(queryResult)) {
							record.setChulzt("21");
							System.out.println("无此交易");
						}
					}else{
						record.setChulzt("21");
						System.out.println("报文格式错误");
					}
					
					this.wzsjjcarpeccancyService.updateByPrimaryKeySelective(record);
					
				}
			}else{
				System.out.println("报文格式为空");
			}
			
			Criteria criteria = new Criteria();
			criteria.put("tongzsbh", tongzsbh);
			criteria.put("chelbm", chelbm);
			criteria.put("chulzt", "2");
			int count = this.wzsjjcarpeccancyService.countByExample(criteria);
			if(count>0){
				return new ExtReturn(true,"缴费成功");
			}else{
				return new ExtReturn(false,"未成功缴费");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
		
	}
	
	/**
	 * 状态清空
	 * */
	@RequestMapping("/clear")
	@ResponseBody
	public Object clear(HttpSession session, HttpServletRequest request){
		try {
			String id = request.getParameter("id"); 
			Wzsjjcarpeccancy wzsjjcarpeccancy = new Wzsjjcarpeccancy();
			wzsjjcarpeccancy.setId(id);
			wzsjjcarpeccancy.setTongzsbh("");
			wzsjjcarpeccancy.setChulzt("0");
			
			wzsjjcarpeccancyService.updateByPrimaryKeySelective(wzsjjcarpeccancy);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
		
		return new ExtReturn(true,"");
		
	}
	
	
	//验证签名 
	public boolean checkSecurity(String[] arr){
		//验证签名
		int checkedRes = new QuickPayUtils().checkSecurity(arr);
		if(checkedRes==1){
			return true;
		}else if(checkedRes == 0){
			System.out.println("验证签名失败");
			return false;
		}else if(checkedRes == 2){
			System.out.println("报文格式错误");
			return false;
		}
		return false;
	}
	
	
	
}
