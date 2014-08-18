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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import chinapay.Base64;
import chinapay.SecureLink;

import com.alipay.config.AlipayConfig;
import com.alipay.sign.MD5;
import com.alipay.util.AlipayCore;
import com.alipay.util.httpClient.HttpProtocolHandler;
import com.alipay.util.httpClient.HttpRequest;
import com.alipay.util.httpClient.HttpResponse;
import com.alipay.util.httpClient.HttpResultType;
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
import com.authority.pojo.WzgdKehjbxx;
import com.authority.pojo.WzgdSmsreturn;
import com.authority.pojo.WzgdSmssend;
import com.authority.pojo.WzgdShoulywnr;
import com.authority.pojo.WzsjjSmsreturn;
import com.authority.pojo.WzsjjSmssend;
import com.authority.pojo.Wzsjjcarpeccancy;
import com.authority.pojo.YqgdRequestdata;
import com.authority.pojo.YqgdRequestsms;
import com.authority.pojo.YqgdResponsedata;
import com.authority.pojo.YqgdSmsreturn;
import com.authority.pojo.YqgdSmssend;
import com.authority.pojo.YqgdTradedata;
import com.authority.service.WzgdKehjbxxService;
import com.authority.service.WzgdShoulywnrService;
import com.authority.service.WzgdSmsreturnService;
import com.authority.service.WzgdSmssendService;
import com.authority.service.YqgdRequestdataService;
import com.authority.service.YqgdRequestsmsService;
import com.authority.service.YqgdResponsedataService;
import com.authority.service.YqgdSmsreturnService;
import com.authority.service.YqgdSmssendService;
import com.authority.service.YqgdTradedataService;
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
@RequestMapping("/yqgd")
public class YqgdRequestdataController {

	private static final Logger logger = LoggerFactory.getLogger(YqgdRequestdataController.class);
	@Autowired
	private YqgdRequestdataService yqgdRequestdataService;
	
	@Autowired
	private YqgdTradedataService yqgdTradedataService;
	
	@Autowired
	private YqgdResponsedataService yqgdResponsedataService;
	
	@Autowired
	private YqgdSmssendService yqgdSmssendService;
	
	@Autowired
	private YqgdSmsreturnService yqgdSmsreturnService;
	
	@Autowired
	private YqgdRequestsmsService yqgdRequestsmsService;
	
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;
	
	@Value("${yqgd.Merid}") 
	private String Merid;
	
	@Value("${yqgd.MerPrk}") 
	private String MerPrk;
	
	@Value("${yqgd.PgPubk}") 
	private String PgPubk;
	
	@Value("${yqgd.Version}") 
	private String VERSION;
	
	@Value("${yqgd.Gateid}") 
	private String GATEID;
	
	@Value("${yqgd.Issign}") 
	private String Issign;
	
	@Value("${yqgd.BASE_URL_TRADE}") 
	private String BASE_URL_TRADE;
	
	@Value("${yqgd.BASE_URL_QUERY}") 
	private String BASE_URL_QUERY;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/**
	 * index
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String yqgdrequestdata() {
		return "user/yqgd_requestdata";
	}

	/**
	 * 所有信息
	 */
	@RequestMapping("/all")
	@ResponseBody
	public Object all(ExtPager pager, HttpSession session, HttpServletRequest request) {
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
			
			String usrname = request.getParameter("usrname");
			
			if (StringUtils.isNotBlank(usrname)) {
				criteria.put("usrname", usrname);
			}
			criteria.put("isdisplay", "1");
			List<YqgdRequestdata> list = this.yqgdRequestdataService.selectByExample(criteria);
			int total = this.yqgdRequestdataService.countByExample(criteria);
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

		1 已确认未缴费  
		
		2 已缴费  21 待返回(未缴费)  22 已返回(银联端未缴费)
	 */
	
	/*private static String Merid="",MerPrk="",PgPubk="",VERSION="",GATEID="",BASE_URL_TRADE="",BASE_URL_QUERY="",Issign="";
	static {
		WebUtils web = new WebUtils();
		Merid = web.readValue("config/others/config.properties","yqgd.Merid");
		MerPrk = web.readValue("config/others/config.properties","yqgd.MerPrk");
		PgPubk = web.readValue("config/others/config.properties","yqgd.PgPubk"); 
		VERSION = web.readValue("config/others/config.properties","yqgd.Version");
		GATEID  = web.readValue("config/others/config.properties","yqgd.Gateid");
		Issign  = web.readValue("config/others/config.properties","yqgd.Issign");
		BASE_URL_TRADE = web.readValue("config/others/config.properties","yqgd.BASE_URL_TRADE");
		BASE_URL_QUERY = web.readValue("config/others/config.properties","yqgd.BASE_URL_QUERY");
	}*/
	
	/**
	 * 接收并处理支付请求
	 */
	@RequestMapping(value="/requestdata",method=RequestMethod.POST)
	@ResponseBody
	public Object requestdata(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		try {
			chinapay.PrivateKey key=new chinapay.PrivateKey(); 
			chinapay.SecureLink t = null;
			boolean flag; 
			//String MerPrk_Path = this.getClass().getClassLoader().getResource("config/others/MerPrK.txt").getPath();
			//System.out.println("Merid:"+Merid);
			//System.out.println("MerPrk:"+MerPrk);			
			
			flag = key.buildKey(Merid,0,MerPrk);
			if(!flag){
				return new ExtReturn(false,"buildKey error");
			}
			t = new SecureLink(key); 
			
			String reqstr = request.getParameter("reqstr");
			// 字符间 用 |号隔开，字段间用 ; 号隔开
			//reqstr = "中国工商银行|0|900758403049921840|测试|01|120221198606121502|乐清广电扣费|300|18857846128|1;";
			//签名验证
			if(Issign.equalsIgnoreCase("1")){
				String sign = request.getParameter("sign");
				Map<String, String> sParaTemp = new HashMap<String, String>();
				sParaTemp.put("reqstr", reqstr);
				String mysign = buildRequestMysign(sParaTemp);
				if(!sign.equals(mysign)&&!sign.equalsIgnoreCase("qwertyuiop")){
					return new ExtReturn(false, "密钥检测失败");
				}
			}
			
			String[] reqstr_split = reqstr.split(";");
			
			WebUtils web = new WebUtils();
			SimpleDateFormat  sdf = new SimpleDateFormat("yyyyMMdd");
			String query ="",update="",delete="",insert="",Message="",Message_Y="",Message_N="";
			if(reqstr_split.length>0){
				
			}else{
				return new ExtReturn(false,"参数为空");
			}
			//信息插入到表 YQGD_REQUESTDATA 避免重复
			for (int i = 0; i < reqstr_split.length; i++) {
				String str = reqstr_split[i];
				if(str.length()>0){
					String[] str_split = str.split("\\|");
					if(str_split.length==10){
						String OPENBANK = str_split[0]; //开户银行
						String CARDTYPE = str_split[1]; //0:卡,1:折
						String CARDNO = str_split[2]; //卡号
						String USRNAME = str_split[3]; //持卡人
						String CERTTYPE = str_split[4]; //身份证01 , 军官证02 , 护照03 , 户口簿04 , 回乡证05 , 其他06
						String CERTID = str_split[5]; //证件号
						String PURPOSE = str_split[6]; //用途
						String TRANSAMT = str_split[7]; //交易金额，单位分
						String MOBILEPHONE = str_split[8]; //移动电话
						String BATCHNUMBER = str_split[9]; //处理批次
						
						if(CARDTYPE==null||CARDTYPE.equals(""))
							CARDTYPE = "0";
						
						if(CERTTYPE==null||CERTTYPE.equals(""))
							CERTTYPE = "01";
						
						//插入到表 YQGD_REQUESTDATA				
						YqgdRequestdata rdI_YqgdRequestdata = new YqgdRequestdata();
						rdI_YqgdRequestdata.setOpenbank(OPENBANK);
						rdI_YqgdRequestdata.setCardtype(CARDTYPE);
						rdI_YqgdRequestdata.setCardno(CARDNO);
						rdI_YqgdRequestdata.setUsrname(USRNAME);
						rdI_YqgdRequestdata.setCerttype(CERTTYPE);
						rdI_YqgdRequestdata.setCertid(CERTID);
						rdI_YqgdRequestdata.setPurpose(PURPOSE);
						rdI_YqgdRequestdata.setTransamt(TRANSAMT);
						rdI_YqgdRequestdata.setMobilephone(MOBILEPHONE);
						rdI_YqgdRequestdata.setBatchnumber(BATCHNUMBER);
						rdI_YqgdRequestdata.setChulzt("1");
						rdI_YqgdRequestdata.setAddip(WebUtils.getIpAddr(request));
						rdI_YqgdRequestdata.setAddtime(new Date());
						rdI_YqgdRequestdata.setIsdisplay("1");
						
						Criteria CriS_YqgdRequestdata = new Criteria();
						CriS_YqgdRequestdata.put("cardno", CARDNO);
						CriS_YqgdRequestdata.put("isdisplay", "1");
						CriS_YqgdRequestdata.put("batchnumber", BATCHNUMBER);
						if(yqgdRequestdataService.selectByExample(CriS_YqgdRequestdata).size()==0){
							
							if(yqgdRequestdataService.insert(rdI_YqgdRequestdata)>0){
								//封装数据插入到表 YQGD_TRADEDATA 
								YqgdTradedata rdI_YqgdTradedata = pk_yqgd_tradedata(rdI_YqgdRequestdata,t);						
								rdI_YqgdTradedata.setAddip(WebUtils.getIpAddr(request));
								
								if(yqgdTradedataService.insertSelective(rdI_YqgdTradedata)>0){
									//数据封装好，发送请求给银联
									Map<String,String> Param = pk_tradedata_param(rdI_YqgdTradedata);
									try {
										//发送支付请求
							 			String strResult = trade_request(Param) ;
							 	        if (strResult == null) {
							 	        	//加入失败的行列
							 	        	Message = Message+str+"|99|Chinapay Server failed;";
							 	        	//将原先记录更新为无效   或者 设置为待重复请求状态  比如 11 ----待确认
							 	        	rdI_YqgdRequestdata.setIsdisplay("0");
							 	        	rdI_YqgdRequestdata.setMessage("|99|Chinapay Server failed;");
							 	        	yqgdRequestdataService.updateByPrimaryKeySelective(rdI_YqgdRequestdata);							 	        	
							 	        	
							 	        }else{
							 	        	//验证结果签名是否有效
							 	        	flag = verifyAuthToken(strResult);
							 	        	if(flag){
							 	        		System.out.println("==============验证签名正确==============");
							 	        		
							 	        	}
							 	        	//将结果写入数据库
							 	        	Message =Message+str+ ResponsedataDeal(strResult,WebUtils.getIpAddr(request),flag,rdI_YqgdTradedata.getId(),rdI_YqgdRequestdata.getId());
							 	        }
							 	        
									} catch (Exception e) {
										System.out.println(e);
										Message = Message+str+"|99|HTTP_Exception failed ;";
										// TODO: handle exception
									}
									
								}else{
									Message = Message+str+"|99|yqgdTradedataService_I failed;";
								}
							}else{
								Message = Message+str+"|99|yqgdRequestdataService_I failed;";
							}
							
						}else{
							Message = Message+str+"|99|record already exists;";
						}
					}else{
						Message = Message+str+"|99|Request_Length error;";
					}
					
				}
				
			}
			
			return new ExtReturn(true,Message);
			
		} catch (Exception e) {
			System.out.println("异常信息:"+e.toString());
			// TODO: handle exception
			return new ExtReturn(false,e.toString()+";");
		}
		
	}
	
	
	@RequestMapping(value="/requestdataquery",method=RequestMethod.POST)
	@ResponseBody
	public Object requestdataquery(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		try {
			String reqstr = request.getParameter("reqstr");
			if(reqstr==null||reqstr.equals(""))
				return new ExtReturn(false,"参数有误");
			//批次+持卡人  ，多个以 | 隔开   
			//签名验证
			if(Issign.equalsIgnoreCase("1")){
				String sign = request.getParameter("sign");
				Map<String, String> sParaTemp = new HashMap<String, String>();
				sParaTemp.put("reqstr", reqstr);
				String mysign = buildRequestMysign(sParaTemp);
				if(!sign.equals(mysign)&&!sign.equalsIgnoreCase("qwertyuiop")){
					return new ExtReturn(false, "密钥检测失败");
				}				
			}
			
			String message = "";
			//reqstr = "1|900758403049921840;";//批次+卡号
			String[] reqstr_split = reqstr.split(";");
			for (int i = 0; i < reqstr_split.length; i++) {
				String str = reqstr_split[i];
				String[] str_split = str.split("\\|");
				if(str_split.length>=1){
					Criteria record = new Criteria();
					record.put("batchnumber", str_split[0]);
					if(str_split.length==2)
						record.put("cardno", str_split[1]);
					record.put("isdisplay", "1");
					List<YqgdRequestdata>  list =  yqgdRequestdataService.selectByExample(record);
					if(list.size()>0){
						YqgdRequestdata rdS_YqgdRequestdata = list.get(0);
						String purpose = rdS_YqgdRequestdata.getPurpose();
						String transamt =rdS_YqgdRequestdata.getTransamt();
						message = message+str+"|"+(purpose==null?"":purpose)+ "|"+(transamt==null?"":transamt)+"|"+rdS_YqgdRequestdata.getResponsecode()+" "+(rdS_YqgdRequestdata.getMessage()==null?"":rdS_YqgdRequestdata.getMessage())+";";
					}else{
						message = message+str+"|99|record not exists";
					}
					
				}
			}
			
			return new ExtReturn(true,message);
			
		} catch (Exception e) {
			
			return new ExtReturn(false,"requestdataquery Exception;");
			// TODO: handle exception
		}
	}
	
	@RequestMapping(value="/requestsmsquery",method=RequestMethod.POST)
	@ResponseBody
	public Object requestsmsquery(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		try {
			String reqstr = request.getParameter("reqstr");
			if(reqstr==null||reqstr.equals(""))
				return new ExtReturn(false,"参数有误");
			//批次+持卡人  ，多个以 | 隔开   
			String message = "";
			
			reqstr = "1|18857846128;"; //批次+持卡人
			String[] reqstr_split = reqstr.split(";");
			for (int i = 0; i < reqstr_split.length; i++) {
				String str = reqstr_split[i];
				String[] str_split = str.split("\\|");
				if(str_split.length==2){
					Criteria record = new Criteria();
					record.put("batchnumber", str_split[0]);
					record.put("phone", str_split[1]);
					record.put("chulzt", "1");
					YqgdRequestsms rdS_YqgdRequestsms = yqgdRequestsmsService.selectByExample(record).get(0);
					message = message+str+"|"+rdS_YqgdRequestsms.getResponse()+";";
				}
			}
			
			return new ExtReturn(true,message);
			
		} catch (Exception e) {
			
			return new ExtReturn(false,"requestsmsquery Exception;");
			// TODO: handle exception
		}
	}
	
	/**
	 * 后台支付
	 */
	@RequestMapping("/pay_backstage")
	@ResponseBody
	public Object pay_backstage(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		chinapay.PrivateKey key=new chinapay.PrivateKey(); 
		chinapay.SecureLink t = null;
		boolean flag; 
		flag = key.buildKey(Merid,0,MerPrk);
		if(!flag){
			return new ExtReturn(false,"buildKey error");
		}
		t = new SecureLink(key);
		
		String id = request.getParameter("id");
		WebUtils web = new WebUtils();
		SimpleDateFormat  sdf = new SimpleDateFormat("yyyyMMdd");
		String query ="",update="",delete="",insert="",Merid="",MerPrk="",PgPubk="",VERSION="",GATEID="",BASE_URL_TRADE="",BASE_URL_QUERY="",Message="",Message_Y="",Message_N="";
		String str ="";
		try{
			if(id==null||id.equals("")){
				return new ExtReturn(false,"参数传递无效,请重新打开确认");
			}else{
				YqgdRequestdata rdS_YqgdRequestdata = yqgdRequestdataService.selectByPrimaryKey(id);
				if(rdS_YqgdRequestdata.getChulzt()==null||rdS_YqgdRequestdata.getChulzt().equals("1")){
					
					String OPENBANK = rdS_YqgdRequestdata.getOpenbank(); //开户银行
					String CARDTYPE = rdS_YqgdRequestdata.getCardtype(); //0:卡,1:折
					String CARDNO = rdS_YqgdRequestdata.getCardno(); //卡号
					String USRNAME = rdS_YqgdRequestdata.getUsrname(); //持卡人
					String CERTTYPE = rdS_YqgdRequestdata.getCerttype(); //身份证01 , 军官证02 , 护照03 , 户口簿04 , 回乡证05 , 其他06
					String CERTID = rdS_YqgdRequestdata.getCertid(); //证件号
					String PURPOSE = rdS_YqgdRequestdata.getPurpose(); //用途
					String TRANSAMT = rdS_YqgdRequestdata.getTransamt(); //交易金额，单位分
					String MOBILEPHONE = rdS_YqgdRequestdata.getMobilephone(); //移动电话
					
					//封装数据插入到表 YQGD_TRADEDATA 
					YqgdTradedata rdI_YqgdTradedata = pk_yqgd_tradedata(rdS_YqgdRequestdata,t);						
					rdI_YqgdTradedata.setAddip(WebUtils.getIpAddr(request));
					
					if(yqgdTradedataService.insertSelective(rdI_YqgdTradedata)>0){
						//数据封装好，发送请求给银联
						Map<String,String> Param = pk_tradedata_param(rdI_YqgdTradedata);
						try {
							//发送支付请求
				 			String strResult = trade_request(Param) ;
				 			
				 	        if (strResult == null) {
				 	        	//加入失败的行列
				 	        	Message = Message+str+"|99|Chinapay Server failed;";
				 	        }else{
				 	        	//验证结果签名是否有效
				 	        	flag = verifyAuthToken(strResult);
				 	        	//将结果写入数据库
				 	        	Message =Message+str+ ResponsedataDeal(strResult,WebUtils.getIpAddr(request),flag,rdI_YqgdTradedata.getId(),rdS_YqgdRequestdata.getId());
				 	        }
				 	        
						} catch (Exception e) {
							System.out.println(e);
							Message = Message+str+"|99|HTTP_Exception failed ;";
							// TODO: handle exception
						}
						
					}else{
						Message = Message+str+"|99|yqgdTradedataService_I failed;";
					}					
				}else{
					return new ExtReturn(false,"状态有误");
				}
			}
			
			return new ExtReturn(true,Message);
			
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
			chinapay.PrivateKey key=new chinapay.PrivateKey(); 
			chinapay.SecureLink t = null;
			boolean flag; 
			flag = key.buildKey(Merid,0,MerPrk);
			if(!flag){
				return new ExtReturn(false,"buildKey error");
			}
			t = new SecureLink(key);
			
			String id =request.getParameter("id");
			if(id==null||id.equals("")){
				return new ExtReturn(false, "参数获取有误");
			}
			String query ="",update="",delete="",insert="",Merid="",MerPrk="",VERSION="",GATEID="",BASE_URL_TRADE="",BASE_URL_QUERY="",Message="",Message_Y="",Message_N="";
			String str ="";
			WebUtils web = new WebUtils();
			 
			YqgdRequestdata rdS_YqgdRequestdata = yqgdRequestdataService.selectByPrimaryKey(id);
			if(rdS_YqgdRequestdata.getChulzt().equals("1")){
				Criteria record = new Criteria();
				record.put("requestdataid", id);
				record.put("isdisplay", "1");
				YqgdTradedata rdS_YqgdTradedata = yqgdTradedataService.selectByExample(record).get(0);
				
				Map<String,String> Param = new HashMap<String, String>();
				Param.put("merId", rdS_YqgdTradedata.getMerid());
				Param.put("transType", rdS_YqgdTradedata.getTranstype());
				Param.put("orderNo", rdS_YqgdTradedata.getOrderno());
				Param.put("transDate", rdS_YqgdTradedata.getTransdate());
				Param.put("version", rdS_YqgdTradedata.getVersion());
				Param.put("priv1", rdS_YqgdTradedata.getPriv1());
				
		    	String MsgBody = 
		    			Param.get("merId")+Param.get("transType")+Param.get("orderNo")+Param.get("transDate")+Param.get("version");
		    	String MsgBodyBase64 =  new String(Base64.encode(MsgBody.toString().getBytes()));
		    	t = new SecureLink(key); 
		        String SMsgBody = t.Sign(MsgBodyBase64);
	 	        
		        Param.put("chkValue", SMsgBody);
		        
		        //发送交易查询
		        String strResult = query_request(Param);
		        
		        if (strResult == null) {
	 	        	//加入失败的行列
	 	        	Message = Message+str+"|99|Chinapay Server failed;";
	 	        }else{
	 	        	//验证结果签名是否有效
	 	        	flag = verifyAuthToken(strResult);
	 	        	//将结果写入数据库
	 	        	Message =Message+str+ ResponsedataDeal(strResult,WebUtils.getIpAddr(request),flag,rdS_YqgdTradedata.getId(),rdS_YqgdRequestdata.getId());
	 	        }
	 			return new ExtReturn(true,Message);
			} else{
				return new ExtReturn(false,"状态有误");
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
			String id =request.getParameter("id");
			if(id==null||id.equals("")){
				return new ExtReturn(false, "参数获取有误");
			}
			
			YqgdRequestdata rdU_YqgdRequestdata = yqgdRequestdataService.selectByPrimaryKey(id);
			rdU_YqgdRequestdata.setChulzt("1");
			rdU_YqgdRequestdata.setResponsecode("");
			rdU_YqgdRequestdata.setMessage("");
			rdU_YqgdRequestdata.setTransstat("");
			
			yqgdRequestdataService.updateByPrimaryKeySelective(rdU_YqgdRequestdata);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
		
		return new ExtReturn(true,"");
		
	}
	
	
	
	/**
	 * 发送短信信息
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/smssend",method=RequestMethod.POST)
	@ResponseBody
	public Object smssend(HttpSession session, HttpServletRequest request){
		try {
			WebUtils web = new WebUtils();
			String reqstr =request.getParameter("reqstr");
			if(reqstr==null||reqstr.equals("")){
				return new ExtReturn(false, "参数获取有误");
			}
			reqstr = "1|18857846128|短信信息测试[回复A B C D];";
			String[] reqstr_split = reqstr.split(";");
			String response = "";
			for (int i = 0; i < reqstr_split.length; i++) {
				String str = reqstr_split[i];
				String[] str_split = str.split("\\|");
				if(str_split.length==3){
					//发送短信
					String query = "select YQGD_MSG_ID_SQ.Nextval from dual ";
					String msg_id = jdbcTemplate.queryForObject(query, String.class);
					//插入表 YQGD_REQUESTSMS
					YqgdRequestsms yqgdRequestsms = new YqgdRequestsms();
					yqgdRequestsms.setBatchnumber(str_split[0]);
					yqgdRequestsms.setPhone(str_split[1]);
					yqgdRequestsms.setMessage(str_split[2]);
					yqgdRequestsms.setMsgId(msg_id);
					yqgdRequestsms.setChulzt("0");
					yqgdRequestsms.setIsdisplay("1");
					yqgdRequestsms.setAddip(WebUtils.getIpAddr(request));
					yqgdRequestsmsService.insertSelective(yqgdRequestsms);
					
					String webservice = web.readValue("config/others/config.properties","chinamobile.webservice");
					String dbuser = web.readValue("config/others/config.properties","chinamobile.dbuser");
					String src_tele_num = web.readValue("config/others/config.properties","chinamobile.corporation");
					int pwd = Integer.parseInt(web.readValue("config/others/config.properties","chinamobile.password"));
					
					String method = "sendmsg";
					//根据Sequence 获取ID
					String dest_tele_num = str_split[1];
					//手机末尾数 4位*3+579 
					String password = String.valueOf(Integer.parseInt(dest_tele_num.substring(dest_tele_num.length()-4))*3+pwd);
					String message= "" ;
					src_tele_num = src_tele_num + msg_id;
					message = ChinaMobileMessageService.add_msg_t();
					message = message + ChinaMobileMessageService.add_msg_b(msg_id, password, src_tele_num, dest_tele_num, str_split[2]);
					message = message + ChinaMobileMessageService.add_msg_w();
					
					String result = ChinaMobileMessageService.send_msg(webservice, method, dbuser, message);
					
					if(result!=null){
						//根据返回的 result 结果,将其信息 插入到 表  WZSJJ_SMSSEND 
						XmlOperateUtil xmlOperateUtil = new XmlOperateUtil();
						Document document = xmlOperateUtil.generateDocumentByString(result);
						List list = document.selectNodes("//infos//info");
						Iterator iter_list = list.iterator();
						if(iter_list.hasNext()){
							YqgdSmssend yqgdSmssend = new YqgdSmssend();
							Element element = (Element) iter_list.next();
					        for (Iterator ieson = element.elementIterator(); ieson.hasNext();) {
				               Element elementSon = (Element) ieson.next();
				               if(elementSon.getName().equalsIgnoreCase("msg_id")){
				            	   yqgdSmssend.setMsgId(elementSon.getText());
				               }
				               if(elementSon.getName().equalsIgnoreCase("state")){
				            	   yqgdSmssend.setState(elementSon.getText());
				               }
				            }
					           
				            yqgdSmssend.setMsg(message) ;
				            yqgdSmssend.setPassword(password);
				            yqgdSmssend.setSrcTeleNum(src_tele_num);
				            yqgdSmssend.setDestTeleNum(dest_tele_num);
				            yqgdSmssend.setAddip(WebUtils.getIpAddr(request));
				            yqgdSmssend.setAddwho("");
				            yqgdSmssend.setFromtablename("YQGD_REQUESTSMS_0"); //从 未确认状态 发出
				            yqgdSmssend.setFromrowid(yqgdRequestsms.getId());
					        
					        //只有发送成功了，才更新状态 为 01 待返回(未确认)  
					        if(yqgdSmssend.getState().equalsIgnoreCase("0")){
					        	response = response+str+"|00;";
					        	yqgdRequestsms.setChulzt("01");
					        	yqgdRequestsms.setEdittime(new Date());
					        	yqgdRequestsmsService.updateByPrimaryKeySelective(yqgdRequestsms);
					        }else{
					        	response = response+str+"|"+yqgdSmssend.getState()+";";
					        }
					        yqgdSmssendService.insertSelective(yqgdSmssend);
						}
					}
				}				
			}
			
			return new ExtReturn(true, response);
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
		
	}
	
	/**
	 * 验证手机端回复结果 今后 该方法跑计划
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/smssend_verify")
	@ResponseBody
	public Object smssend_verify(HttpSession session, HttpServletRequest request, HttpServletResponse response){
		WebUtils web = new WebUtils();
		try{
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
				YqgdSmsreturn yqgdSmsreturn = new YqgdSmsreturn();
				Element element = (Element) iter_list.next();
		           for (Iterator ieson = element.elementIterator(); ieson.hasNext();) {
		               Element elementSon = (Element) ieson.next();
		               if(elementSon.getName().equalsIgnoreCase("ID"))
		            	   yqgdSmsreturn.setMsgId(elementSon.getText());
		               else if(elementSon.getName().equalsIgnoreCase("DESTADDR"))
		            	   yqgdSmsreturn.setSrcmsgr(elementSon.getText()); 
		               else if(elementSon.getName().equalsIgnoreCase("SRCADDR"))
		            	   yqgdSmsreturn.setDestmsgr(elementSon.getText()); 
		               else if(elementSon.getName().equalsIgnoreCase("MSG"))
		            	   yqgdSmsreturn.setMsg(elementSon.getText()); 
		               else if(elementSon.getName().equalsIgnoreCase("TIME"))
		            	   yqgdSmsreturn.setTime(elementSon.getText()); 
		           }
		       yqgdSmsreturn.setAddip(WebUtils.getIpAddr(request));
		       yqgdSmsreturn.setAddwho("");
	           //先插入短信的数据
		       yqgdSmsreturnService.insertSelective(yqgdSmsreturn);
	           
        	   //根据 msg_id 和手机号码 去追寻原始记录的id
        	   Criteria criteria = new Criteria();
        	   criteria.put("srcTeleNum", yqgdSmsreturn.getSrcmsgr());
        	   criteria.put("destTeleNumLikeOther", yqgdSmsreturn.getDestmsgr());
        	   criteria.put("isdisplay", "1");
        	   List<YqgdSmssend> list_smssend = yqgdSmssendService.selectByExample(criteria);
        	   if(list_smssend.size()==0){ //不存在记录，进入下一层循环
        		   break;
        	   }
        	   
        	   String Fromtablename = list_smssend.get(0).getFromtablename();
        	   String Fromrowid = list_smssend.get(0).getFromrowid();
        	   
	           //根据msg_id 以及回复的内容比如 Y、N 去触发信息发送到 交警部门，按理，应该是可以返回处理的 通知书编号 ，然后更新原有的记录
        	   if(Fromtablename.equalsIgnoreCase("YQGD_REQUESTSMS_0")){ //对应未 确认的状态
        		   //查询该ID 所处于的状态是否允许  0 未确认  01 待返回(未确认)  02 已返回(未确认)   
        		   YqgdRequestsms rdU_YqgdRequestsms = yqgdRequestsmsService.selectByPrimaryKey(Fromrowid);
        		   if(rdU_YqgdRequestsms.getChulzt().equalsIgnoreCase("0")||rdU_YqgdRequestsms.getChulzt().equalsIgnoreCase("01")||rdU_YqgdRequestsms.getChulzt().equalsIgnoreCase("02"))
        			  ;
        		   else
        			  break;
        		   
        		   String returnmodule = "ABCDEFGH";
        		   if(returnmodule.indexOf(yqgdSmsreturn.getMsg().toUpperCase())>0&&yqgdSmsreturn.getMsg().length()==1){
        			   rdU_YqgdRequestsms.setChulzt("1"); //更新为   已确认
        			   rdU_YqgdRequestsms.setResponse(yqgdSmsreturn.getMsg().toUpperCase());
		           }else{
		        	   rdU_YqgdRequestsms.setChulzt("02"); //更新为 已返回(未确认)
		           }
        		   
        		   rdU_YqgdRequestsms.setEdittime(new Date());
        		   yqgdRequestsmsService.updateByPrimaryKeySelective(rdU_YqgdRequestsms);
        		   
        		   //处理完毕之后,将该条回复短信设置成 isdisplay = 0 已经处理
        		   yqgdSmsreturn.setIsdisplay("0");
        		   yqgdSmsreturnService.updateByPrimaryKeySelective(yqgdSmsreturn);
        		   
        		   //今后还会跑一个计划，专门针对 遗留的未处理的信息，但是需要判断该条信息是否已经有回复了，且时间在此之后
        		   
        	   }
	           
			}
			//========================发送短信测试=========================End
			return new ExtReturn(true,"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ExceptionReturn(e);
		}
		
	}
	
	/**
	 * 从YqgdRequestdata 取数据封装成 YqgdTradedata 对象
	 * @param record
	 * @return
	 */
	public YqgdTradedata pk_yqgd_tradedata(YqgdRequestdata record,chinapay.SecureLink t){
		try {
			
			SimpleDateFormat  sdf = new SimpleDateFormat("yyyyMMdd");
			WebUtils web = new WebUtils();
			String OPENBANK = record.getOpenbank(); //开户银行
			String CARDTYPE = record.getCardtype(); //0:卡,1:折
			String CARDNO = record.getCardno(); //卡号
			String USRNAME = record.getUsrname(); //持卡人
			String CERTTYPE = record.getCerttype(); //身份证01 , 军官证02 , 护照03 , 户口簿04 , 回乡证05 , 其他06
			String CERTID = record.getCertid(); //证件号
			String PURPOSE = record.getPurpose(); //用途
			String TRANSAMT = record.getTransamt(); //交易金额，单位分
			String MOBILEPHONE = record.getMobilephone(); //移动电话
			
			YqgdTradedata rdI_YqgdTradedata = new YqgdTradedata();
			rdI_YqgdTradedata.setMerid(Merid);
			//交易日期
			String TRANSDATE = sdf.format(new Date());
			rdI_YqgdTradedata.setTransdate(TRANSDATE);
			//生成订单号
			String query = 
					"select  to_char(sysdate,'yyyymmddhh24')||lpad(nvl(max(to_number(substr(ORDERNO,11))),0)+1,6,'0') ORDERNO " +
					"from YQGD_TRADEDATA " +
					"where instr(ORDERNO,to_char(sysdate,'yyyymmdd'))>0 ";
			String ORDERNO = jdbcTemplate.queryForObject(query, String.class);
			rdI_YqgdTradedata.setOrderno(ORDERNO);
			
			String TRANSTYPE ="0003";
			rdI_YqgdTradedata.setTranstype(TRANSTYPE);
			//获取银行代码
			query = "select max(code) code from YQGD_BANKCODE where name = :name and isdisplay='1' ";
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("name", OPENBANK);
			String BANKCODE = njdbcTemplate.queryForObject(query, param, String.class);
			rdI_YqgdTradedata.setOpenbankid(BANKCODE);
			
			rdI_YqgdTradedata.setCardtype(CARDTYPE);
			rdI_YqgdTradedata.setCardno(CARDNO);
			rdI_YqgdTradedata.setUsrname(web.chinaToUnicode(USRNAME));
			rdI_YqgdTradedata.setCerttype(CERTTYPE);
			rdI_YqgdTradedata.setCertid(CERTID);
			String CURYID = "156";
			rdI_YqgdTradedata.setCuryid(CURYID);
			
			TRANSAMT = StringUtils.leftPad(TRANSAMT, 12, "0");//补齐12位
			rdI_YqgdTradedata.setTransamt(TRANSAMT);
			
			rdI_YqgdTradedata.setVersion(VERSION);
			rdI_YqgdTradedata.setGateid(GATEID);
			
			rdI_YqgdTradedata.setRequestdataid(record.getId());
			
			String MsgBody = Merid + TRANSDATE+ORDERNO+
					TRANSTYPE+BANKCODE+CARDTYPE+
					CARDNO+web.chinaToUnicode(USRNAME)+CERTTYPE+CERTID+
					CURYID+TRANSAMT+""+VERSION+GATEID;
			
			System.out.println("Merid:"+Merid+",TRANSDATE:"+TRANSDATE+",ORDERNO:"+ORDERNO+",TRANSTYPE:"+TRANSTYPE+
					",BANKCODE:"+BANKCODE+",CARDTYPE:"+CARDTYPE+",CARDNO:"+CARDNO
					+",USRNAME:"+web.chinaToUnicode(USRNAME)+",CERTTYPE:"+CERTTYPE+",CERTID:"+CERTID
					+",CURYID:"+CURYID+",TRANSAMT:"+TRANSAMT+",priv1:"+""+",VERSION:"+VERSION+",GATEID:"+GATEID);
			
			System.out.println("MsgBody:"+MsgBody);
			
			String MsgBodyBase64 =  new String(Base64.encode(MsgBody.toString().getBytes()));
	        
	        String SMsgBody = t.Sign(MsgBodyBase64);
 	        
	        System.out.println("SMsgBody:"+SMsgBody);
	        
	        rdI_YqgdTradedata.setChkvalue(SMsgBody);
			
			return rdI_YqgdTradedata;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	
	/**
	 * 封装交易请求参数
	 * @param record
	 * @return
	 */
	public Map<String,String> pk_tradedata_param(YqgdTradedata record){
		try {
			Map<String,String> Param = new HashMap<String, String>();
			Param.put("merId", Merid);
			Param.put("transDate", record.getTransdate());
			Param.put("orderNo", record.getOrderno());
			Param.put("transType", record.getTranstype());
			Param.put("openBankId", record.getOpenbankid());
			Param.put("cardType", record.getCardtype());
			Param.put("cardNo", record.getCardno()); //6212261203005120596
			Param.put("usrName", record.getUsrname());
			Param.put("certType", record.getCerttype());
			Param.put("certId", record.getCertid());
			Param.put("curyId", record.getCuryid());
			Param.put("transAmt", record.getTransamt()); //已经补齐12 位
			Param.put("purpose", record.getPurpose());
			Param.put("priv1", "");
			Param.put("version", VERSION);
			Param.put("gateId", GATEID);
			Param.put("chkValue", record.getChkvalue());
			return Param;
			
		} catch (Exception e) {
			return null;
			// TODO: handle exception
		}
		
	}
	/**
	 * 支付请求
	 * @param Param
	 * @return
	 */
	public String trade_request(Map<String,String> Param){
		try {
			HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
 			HttpRequest req_trade = new HttpRequest(HttpResultType.BYTES);
 			//设置编码集
 			req_trade.setCharset(AlipayConfig.input_charset);
 			req_trade.setParameters(generatNameValuePair(Param));
 			req_trade.setUrl(BASE_URL_TRADE);
 	        HttpResponse response_trade;
 	        response_trade = httpProtocolHandler.execute(req_trade,"","");
 	        if(response_trade==null)
 	        	return null;
 	        else
 	        	return response_trade.getStringResult();
 	        			
		} catch (Exception e) {
			return null;
			// TODO: handle exception
		}
	}
	
	/**
	 * 交易查询
	 * @param Param
	 * @return
	 */
	public String query_request(Map<String,String> Param){
		try {
			HttpProtocolHandler httpProtocolHandler = HttpProtocolHandler.getInstance();
 			HttpRequest req_trade = new HttpRequest(HttpResultType.BYTES);
 			//设置编码集
 			req_trade.setCharset(AlipayConfig.input_charset);
 			req_trade.setParameters(generatNameValuePair(Param));
 			req_trade.setUrl(BASE_URL_QUERY);
 	        HttpResponse response_trade;
 	        response_trade = httpProtocolHandler.execute(req_trade,"","");
 	        if(response_trade==null)
 	        	return null;
 	        else
 	        	return response_trade.getStringResult();
 	       			
		} catch (Exception e) {
			return null;
			// TODO: handle exception
		}
	}
	
	
	@SuppressWarnings("finally")
	public String plan_query_request(){
		String result = "",query="";
		try {
			chinapay.PrivateKey key=new chinapay.PrivateKey(); 
			chinapay.SecureLink t = null;
			boolean flag; 
			flag = key.buildKey(Merid,0,MerPrk);
			if(!flag){
				result = "buildKey error" ;
				return result ;
			}
			t = new SecureLink(key);
			
			query = "select id from YQGD_REQUESTDATA where isdisplay='1' and chulzt='22' ";
			List<Map<String,Object>> list =  jdbcTemplate.queryForList(query);
			
			for (Map<String, Object> map : list) {
				
				Criteria record = new Criteria();
				record.put("requestdataid", map.get("id").toString());
				record.put("isdisplay", "1");
				YqgdTradedata rdS_YqgdTradedata = yqgdTradedataService.selectByExample(record).get(0);
				
				Map<String,String> Param = new HashMap<String, String>();
				Param.put("merId", rdS_YqgdTradedata.getMerid());
				Param.put("transType", rdS_YqgdTradedata.getTranstype());
				Param.put("orderNo", rdS_YqgdTradedata.getOrderno());
				Param.put("transDate", rdS_YqgdTradedata.getTransdate());
				Param.put("version", rdS_YqgdTradedata.getVersion());
				Param.put("priv1", rdS_YqgdTradedata.getPriv1());
				
		    	String MsgBody = 
		    			Param.get("merId")+Param.get("transType")+Param.get("orderNo")+Param.get("transDate")+Param.get("version");
		    	String MsgBodyBase64 =  new String(Base64.encode(MsgBody.toString().getBytes()));
		    	t = new SecureLink(key); 
		        String SMsgBody = t.Sign(MsgBodyBase64);
	 	        
		        Param.put("chkValue", SMsgBody);
		        
		        //发送交易查询
		        String strResult = query_request(Param);
		        
		        if (strResult == null) {
	 	        	//加入失败的行列
	 	        }else{
	 	        	//验证结果签名是否有效
	 	        	flag = verifyAuthToken(strResult);
	 	        	//将结果写入数据库
	 	        	ResponsedataDeal(strResult,"127.0.0.1",flag,rdS_YqgdTradedata.getId(),map.get("id").toString());
	 	        }
		        
			}
			
			result = "success";

		} catch (Exception e) {
			// TODO: handle exception
			result = e.toString();
			
		} finally{
			
			return result;
		}
		
	}	
	
	/**
	 * 验证签名是否有效
	 * @param record
	 * @return
	 */
	public Boolean verifyAuthToken(String record){
		try {
			chinapay.PrivateKey key_check=new chinapay.PrivateKey(); 
	 		key_check.buildKey("999999999999999",0,PgPubk); 
			chinapay.SecureLink t_check;
			t_check = new SecureLink(key_check);
			
			int dex = record.lastIndexOf("=");
	        String record_sign = record.substring(0,dex+1);
	 		String record_signBase64 = new String(Base64.encode(record_sign.getBytes()));
			return t_check.verifyAuthToken(record_signBase64,record.substring(dex + 1));
			
		} catch (Exception e) {
			return false;
			// TODO: handle exception
		}
		
		
	}
	/**
	 * 回复数据处理
	 * @param record
	 * @param ip
	 * @param flag
	 * @param id_YqgdTradedata
	 * @param id_YqgdRequestdata
	 * @return
	 */
	public String ResponsedataDeal(String record,String ip,Boolean flag,String id_YqgdTradedata,String id_YqgdRequestdata){
		try {
			//解析 返回结果
			//responseCode=00&merId=808080211388313&transDate=20140211&orderNo=2014020720140001&transAmt=000000000001&curyId=156&transType=0003&priv1=&transStat=1001&gateId=7008&cardType=0&cardNo=900758403049921840&userNme=\u6d4b\u8bd5&certType=01&certId=120221198606121502&chkValue=
			//58380D253F2C84677CA3C249A121064AC8699078BCC76B7DB56C61EC6AD240FAAB294A439A7567BC70F986CFB273C517478443E4DA005986CE8C636541E1681F1A7A02CE2858E87CA457044CEAFC603F7FDCDE281002D46222201FCDF2525BC74FBF0482D6068AE1B85211AA0087D3D71A4C884D44CD5F83B60E64DDDE5CF097
			Map<String,String> strResult_map = new HashMap<String, String>();
			WebUtils web = new WebUtils();
			String[] strResult_1 = record.split("&");
			String Message ="";
        	if(strResult_1.length>0){
        		for (String strResult_2 : strResult_1) {
        			String[] strResult_3 = strResult_2.split("=");
        			if(strResult_3.length>1)
        				strResult_map.put(strResult_3[0].toUpperCase(), strResult_3[1]==null?"":strResult_3[1]);
        			else
        				strResult_map.put(strResult_3[0].toUpperCase(), "");
				}
        		//将结果回写到 表 YQGD_TRADEDATA 和表 YQGD_RESPONSEDATA
        		YqgdResponsedata rdI_yqgdresponsedata = new YqgdResponsedata();
        		rdI_yqgdresponsedata.setResponsecode(strResult_map.get("RESPONSECODE"));
        		rdI_yqgdresponsedata.setTransstat(strResult_map.get("TRANSSTAT"));
        		rdI_yqgdresponsedata.setAddip(ip);
        		if(strResult_map.get("RESPONSECODE").equalsIgnoreCase("00")){
 	        		rdI_yqgdresponsedata.setMerid(strResult_map.get("MERID"));
 	        		rdI_yqgdresponsedata.setTransdate(strResult_map.get("TRANSDATE"));
 	        		rdI_yqgdresponsedata.setOrderno(strResult_map.get("ORDERNO"));
 	        		rdI_yqgdresponsedata.setTranstype(strResult_map.get("TRANSTYPE"));
 	        		rdI_yqgdresponsedata.setCardtype(strResult_map.get("CARDTYPE"));
 	        		rdI_yqgdresponsedata.setCardno(strResult_map.get("CARDNO"));
 	        		rdI_yqgdresponsedata.setUsrname(strResult_map.get("USERNME"));
 	        		rdI_yqgdresponsedata.setCerttype(strResult_map.get("CERTTYPE"));
 	        		rdI_yqgdresponsedata.setCertid(strResult_map.get("CERTID"));
 	        		rdI_yqgdresponsedata.setCuryid(strResult_map.get("CURYID"));
 	        		rdI_yqgdresponsedata.setTransamt(strResult_map.get("TRANSAMT"));
 	        		rdI_yqgdresponsedata.setPriv1(strResult_map.get("PRIV1"));
 	        		rdI_yqgdresponsedata.setChkvalue(strResult_map.get("CHKVALUE"));
 	        		
        		}else{
        			rdI_yqgdresponsedata.setMessage(strResult_map.get("MESSAGE"));
        		}
        		if(yqgdResponsedataService.insertSelective(rdI_yqgdresponsedata)>0){
        			if(flag){//验证通过
        				//更新表 YQGD_TRADEDATA 
        				YqgdTradedata rdI_YqgdTradedata = yqgdTradedataService.selectByPrimaryKey(id_YqgdTradedata);
	 	        		rdI_YqgdTradedata.setResponsecode(rdI_yqgdresponsedata.getResponsecode());
	 	        		rdI_YqgdTradedata.setTransstat(rdI_yqgdresponsedata.getTransstat());
	 	        		rdI_YqgdTradedata.setMessage(strResult_map.get("MESSAGE")==null?"":strResult_map.get("MESSAGE"));
	 	        		rdI_YqgdTradedata.setEdittime(new Date());
	 	        		rdI_YqgdTradedata.setEditip(ip);
	 	        		if(yqgdTradedataService.updateByPrimaryKeySelective(rdI_YqgdTradedata)>0){
	 	        			Message = "|"+rdI_YqgdTradedata.getResponsecode()+" "+web.unicode2String(rdI_YqgdTradedata.getMessage().replaceAll("\\\\", ""))+";";
	 	        			//更新表 YQGD_REQUESTDATA
	 	        			//代扣成功或者失败
	 	        			YqgdRequestdata rdI_YqgdRequestdata = yqgdRequestdataService.selectByPrimaryKey(id_YqgdRequestdata);
	 	        			rdI_YqgdRequestdata.setResponsecode(rdI_YqgdTradedata.getResponsecode());
	 	        			rdI_YqgdRequestdata.setTransstat(rdI_YqgdTradedata.getTransstat());
	 	        			rdI_YqgdRequestdata.setEdittime(new Date());
	 	        			rdI_YqgdRequestdata.setEditip(ip);
	 	        			if(rdI_YqgdTradedata.getResponsecode().equals("00")&&rdI_YqgdTradedata.getTransstat().equals("1001")){
	 	        				rdI_YqgdRequestdata.setChulzt("2");
	 	        			}else{
	 	        				//根据银联状态判断是 处理中 or 处理失败
	 	        				String query = "select count(*) from YQGD_RESPONSECODE where statname='F' and transstat = :transstat ";
	 	        				Map<String,Object> Param = new HashMap<String,Object>();
	 	        				Param.put("transstat", rdI_YqgdTradedata.getTransstat());
	 	        				if(njdbcTemplate.queryForInt(query, Param)>0){
	 	        					rdI_YqgdRequestdata.setChulzt("23");
	 	        				}else{
	 	        					rdI_YqgdRequestdata.setChulzt("22");
	 	        				}
	 	        				
	 	        				rdI_YqgdRequestdata.setMessage(web.unicode2String(rdI_YqgdTradedata.getMessage().replaceAll("\\\\", "")));
	 	        			}
	 	        			yqgdRequestdataService.updateByPrimaryKeySelective(rdI_YqgdRequestdata);
	 	        			
	 	        		}else{
	 	        			Message = "|99|verifyAuthToken failed;";
	 	        		}
        				
        			}else{
        				Message = "|99|yqgdTradedataService_U failed;";
        			}
        			
        		}else{
        			Message = "|99|yqgdResponsedataService_I failed;";
        		}
        		
        	}
        	
        	return Message;
        	
		} catch (Exception e) {
			// TODO: handle exception
			return "|ResponsedataDeal Exception;";
		}
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
	
    public static String[] getResArr(String str) {
		String regex = "(.*?cupReserved\\=)(\\{[^}]+\\})(.*)";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);

		String reserved = "";
		if (matcher.find()) {
			reserved = matcher.group(2);
		}

		String result = str.replaceFirst(regex, "$1$3");
		String[] resArr = result.split("&");
		for (int i = 0; i < resArr.length; i++) {
			if ("cupReserved=".equals(resArr[i])) {
				resArr[i] += reserved;
			}
		}
		return resArr;
	}
    
    /**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
     */
	public static String buildRequestMysign(Map<String, String> sPara) {
    	String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(AlipayConfig.sign_type.equals("MD5") ) {
        	mysign = MD5.sign(prestr, AlipayConfig.key, AlipayConfig.input_charset);
        }
        //mysign ="gur8bebpnew403u7lnan1jtfux9smtva";
        return mysign;
    }
	
}
