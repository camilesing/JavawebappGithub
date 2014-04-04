package com.authority.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.authority.common.springmvc.DateConvertEditor;
import com.authority.pojo.BaseUsers;
import com.authority.pojo.ExceptionReturn;
import com.authority.pojo.ExtReturn;
import com.authority.service.BaseUsersService;
import com.authority.web.interseptor.WebConstants;
import com.henlo.process.HenloController;

@Controller
@RequestMapping("/bosappaction")
public class BOSAppActionController {
private static final Logger logger = LoggerFactory.getLogger(BOSAppActionController.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;
	
	
	@Resource(name="jdbcTemplate_henlo")
	private JdbcTemplate jdbcTemplate_henlo;
	
	@Resource(name="njdbcTemplate_henlo")
	private NamedParameterJdbcTemplate njdbcTemplate_henlo;
		
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Object login(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		Boolean success = false ;
		String callbackfun="", msg="";
		try {
			String account = request.getParameter("account");
			String password= request.getParameter("password");
			//用户验证
			if(account.equals("admin")&&password.equals("admin")){
				success=true;
			}
			else{
				success = false;
				msg = "用户名或密码错误";
			}
						
			return new ExtReturn(success,msg);
			
		} catch (Exception e) {
			msg = e.toString();
			// TODO: handle exception
		} 
		
		return new ExtReturn(false, msg);
		
	}
	
	@RequestMapping("/workflowcount")
	public void workflowcount(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		String result = "",callbackfun="";
		PrintWriter writer = null ;
		response.setContentType("text/html;charset=UTF-8");
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		int nowminute = cal.get(Calendar.MINUTE);
		
		try {
			writer = response.getWriter();
			
			callbackfun = request.getParameter("callbackfun");
			result = JSON.toJSONString(new ExtReturn(true, String.valueOf(nowminute)));
			
			if(callbackfun!=null&&!callbackfun.equals("")){
				
				result = callbackfun+"("+result+")";
			}
			
		} catch (Exception e) {
			result = JSON.toJSONString(new ExceptionReturn(e));
			result = callbackfun+"("+result+")";
			// TODO: handle exception
		} finally{
			writer.write(result);
			writer.flush();
		}
		
	}
	
	@RequestMapping("/workflowdetail")
	public void workflowdetail(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		String result = "",callbackfun="";
		PrintWriter writer = null ;
		response.setContentType("text/html;charset=UTF-8");
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		int nowminute = cal.get(Calendar.MINUTE);
		List list = new ArrayList();
		
		try {
			writer = response.getWriter();
			for(int i=1;i<=2;i++){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("type", "经销商还款计划单");
				map.put("docno", "BRP131108000000"+i);
				map.put("note", "20131107 PPE仁怀黄倩:"+i);
				list.add(map);
			}
			
			callbackfun = request.getParameter("callbackfun");
			result = JSON.toJSONString(new ExtReturn(true, list));
			
			if(callbackfun!=null&&!callbackfun.equals("")){
				
				result = callbackfun+"("+result+")";
			}
			
		} catch (Exception e) {
			result = JSON.toJSONString(new ExceptionReturn(e));
			result = callbackfun+"("+result+")";
			// TODO: handle exception
		} finally{
			writer.write(result);
			writer.flush();
		}
		
	}
	
	@RequestMapping("/workflowdeal")
	public void workflowdeal(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		String result = "",callbackfun="",docno="",oper="",update ="";
		PrintWriter writer = null ;
		response.setContentType("text/html;charset=UTF-8");
		
		try {
			writer = response.getWriter();
			
			callbackfun = request.getParameter("callbackfun");
			docno = request.getParameter("docno");
			oper  = request.getParameter("oper");
			result = JSON.toJSONString(new ExtReturn(true,"1" ));
			
			if(callbackfun!=null&&!callbackfun.equals("")){
				
				result = callbackfun+"("+result+")";
			}
			
		} catch (Exception e) {
			result = JSON.toJSONString(new ExceptionReturn(e));
			result = callbackfun+"("+result+")";
			// TODO: handle exception
		} finally{
			writer.write(result);
			writer.flush();
		}
		
	}
	
	@RequestMapping(value="/reportview_bardetail",method=RequestMethod.POST)
	@ResponseBody
	public Object reportview_bardetail(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		String result = "",callbackfun="",docno="",oper="",update ="",store="",date_start="",date_end="";
		List list = new ArrayList();
		try {
			store = request.getParameter("store");
			store = store==null?"":URLDecoder.decode(store, "UTF-8"); 
			date_start = request.getParameter("date_start");
			date_end = request.getParameter("date_end");
			System.out.println("store:"+store+" date:"+date_start+"-"+date_end);
			String[] stores = {"温州店","上海店","瑞安店","杭州店"};
			for (int i = 0; i < stores.length; i++) {
				String str = stores[i];
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("store", str);
				map.put("sales", i+10);
				list.add(map);
			}
			
			return new ExtReturn(true, list);
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ExtReturn(false, e.toString()); 
		} 
		
	}
	
	@RequestMapping( value="/reportview_tabledetail",method=RequestMethod.POST)
	@ResponseBody
	public Object reportview_tabledetail(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		String result = "",callbackfun="",docno="",oper="",update ="",store="",date_start="",date_end="";
		List list = new ArrayList();
		try {
			store = request.getParameter("store");
			store = store==null?"":URLDecoder.decode(store, "UTF-8"); 
			date_start = request.getParameter("date_start");
			date_end = request.getParameter("date_end");
			System.out.println("store:"+store+" date:"+date_start+"-"+date_end);
			String[] stores = {"温州店","上海店","瑞安店","杭州店","苍南店"};
			int cell_count = 3 ;
			for (int i = 0; i < stores.length; i++) {
				String str = stores[i];
				Map<String,Object> map = new HashMap<String, Object>();
				if(i==0){
					Map<String,Object> map_head = new HashMap<String, Object>();
					map_head.put("cell_1", "Rank");
					map_head.put("cell_2", "店仓");
					map_head.put("cell_3", "销售量");
					list.add(map_head);					
				}
				map.put("cell_1", i+1);
				map.put("cell_2", str);
				map.put("cell_3", i+10);
				list.add(map);
			}
			return new ExtReturn(true, list,cell_count);
			
		} catch (Exception e) {
			return new ExtReturn(false, e.toString());
		}
		
	}
	
	@SuppressWarnings("finally")
	@RequestMapping( value="/reportview_ireport",method=RequestMethod.POST)
	@ResponseBody
	public Object reportview_ireport_post(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		StringBuffer sbuffer = new StringBuffer(); 
		try {			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("yxgsm", request.getParameter("param"));
			// JasperPrint jasperPrint = new
			// JasperPrintWithConnection(reportFilePath, params,
			// con).getJasperPrint();

			String JASPER_FILE_NAME = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/reports/Report_maic_count.jasper");
			// JasperReport jasperReport =
			// (JasperReport)JRLoader.loadObject(JASPER_FILE_NAME);
			File reportFile = new File(JASPER_FILE_NAME);
			InputStream in = new FileInputStream(reportFile);
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			JasperPrint print = JasperFillManager.fillReport(in, params,conn);
			conn.close();
			// 使用JRHtmlExproter导出Html格式
			JRHtmlExporter exporter = new JRHtmlExporter();
			
			//设置图片文件存放路径，此路径为服务器上的绝对路径 			
		//	System.out.println(request.getSession().getServletContext().getRealPath("/"));
			
			String imageDIR = request.getSession().getServletContext().getRealPath("/")+"/resources/report_temp_file/";
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, imageDIR);
			
			//设置图片请求URI 
			
			String imageURI =  request.getContextPath() + "/resources/report_temp_file/"; 
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "../servlets/image?image="); 
			
			//设置导出图片到图片存放路径 
			exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.TRUE); 
			exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
			
			//设置导出对象 
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			
			//设置导出方法 
			 
			exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, sbuffer);
			exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, "");  
		    exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML, "");  
		    exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, "");
		    
		    request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,print);

			// 导出
			exporter.exportReport();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return new ExtReturn(true, sbuffer);
		}
		
	}
	
	@RequestMapping( value="/reportview_ireport",method=RequestMethod.GET)
	@ResponseBody
	public void reportview_ireport_get(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException {
		PrintWriter writer = response.getWriter();
		try {
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("yxgsm", request.getParameter("param"));
			// JasperPrint jasperPrint = new
			// JasperPrintWithConnection(reportFilePath, params,
			// con).getJasperPrint();

			String JASPER_FILE_NAME = request.getSession().getServletContext()
					.getRealPath("/WEB-INF/reports/Report_maic_count.jasper");
			// JasperReport jasperReport =
			// (JasperReport)JRLoader.loadObject(JASPER_FILE_NAME);
			File reportFile = new File(JASPER_FILE_NAME);
			InputStream in = new FileInputStream(reportFile);
			Connection conn = jdbcTemplate.getDataSource().getConnection();
			JasperPrint print = JasperFillManager.fillReport(in, params,conn);
			conn.close();
			// 使用JRHtmlExproter导出Html格式
			JRHtmlExporter exporter = new JRHtmlExporter();
			
			//设置图片文件存放路径，此路径为服务器上的绝对路径 			
		//	System.out.println(request.getSession().getServletContext().getRealPath("/"));
			
			String imageDIR = request.getSession().getServletContext().getRealPath("/")+"/resources/report_temp_file/";
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, imageDIR);
			
			//设置图片请求URI 
			
			String imageURI =  request.getContextPath() + "/resources/report_temp_file/"; 
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "../servlets/image?image="); 
			
			//设置导出图片到图片存放路径 
			exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.TRUE); 
			exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);
			
			//设置导出对象 
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			
			//设置导出方法 
			exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
			
			//设置HTTP Head 
			response.setContentType("text/html");
			request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,print);

			// 导出
			exporter.exportReport();
			
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("{success:false,errors:{msg:'打开失败'}}");
			writer.close();
			return;
		}
		// writer.write("{success:false,errors:{msg:'保存失败'}}");
		writer.close();
		
	}
	
	
	@RequestMapping( value="/reportview_ichartjs",method=RequestMethod.POST)
	@ResponseBody
	public Object reportview_ichartjs(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result = "",callbackfun="",docno="",oper="",update ="",store="",date_start="",date_end="";
		List list = new ArrayList();
		try {
			store = request.getParameter("store");
			store = store==null?"":URLDecoder.decode(store, "UTF-8"); 
			date_start = request.getParameter("date_start");
			date_end = request.getParameter("date_end");
			System.out.println("store:"+store+" date:"+date_start+"-"+date_end);
			String[] stores = {"温州店","上海店","瑞安店","杭州店","苍南店"};
			int cell_count = 3 ;
			for (int i = 0; i < stores.length; i++) {
				String str = stores[i];
				Map<String,Object> map = new HashMap<String, Object>();
				if(i==0){
					Map<String,Object> map_head = new HashMap<String, Object>();
					map_head.put("cell_1", "Rank");
					map_head.put("cell_2", "店仓");
					map_head.put("cell_3", "销售量");
					list.add(map_head);					
				}
				map.put("cell_1", i+1);
				map.put("cell_2", str);
				map.put("cell_3", i+10);
				list.add(map);
			}
			return new ExtReturn(true, list,cell_count);
			
		} catch (Exception e) {
			return new ExtReturn(false, e.toString());
		}
		
	}
	
	@RequestMapping( value="/reportview_retail_001",method=RequestMethod.POST)
	@ResponseBody
	public Object reportview_retail_001(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result = "",query="",insert="",procedure="",callbackfun="",docno="",oper="",update ="",store="",date_start="",date_end="";
		int count = 0;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listArray = new ArrayList();
		
		try {
			date_start = request.getParameter("date_start");
			date_end = request.getParameter("date_end");
			if(date_start==null||date_start.equals(""))
				date_start="17990101";
			
			if(date_end==null||date_end.equals(""))
				date_end="20991231";
			
			
			String INFO = " ( BILLDATE BETWEEN "+date_start+" AND "+date_end+") ";
			
			
			query = "select get_sequences('AD_PINSTANCE') AD_PINSTANCE_ID  from dual ";
			String AD_PINSTANCE_ID = jdbcTemplate_henlo.queryForObject(query, String.class);
			System.out.println("AD_PINSTANCE_ID:"+AD_PINSTANCE_ID);
			insert = "insert into AD_PINSTANCE (ID, AD_CLIENT_ID, AD_ORG_ID, ORDERNO, AD_PROCESSQUEUE_ID, AD_PROCESS_ID, AD_USER_ID, STATE, RESULT, ERRORMSG, AD_TABLE_ID, RECORD_ID, OWNERID, MODIFIERID, CREATIONDATE, MODIFIEDDATE, ISACTIVE, RECORD_NO) "+
					 "select :AD_PINSTANCE_ID, AD_CLIENT_ID, AD_ORG_ID, ORDERNO, AD_PROCESSQUEUE_ID, AD_PROCESS_ID, AD_USER_ID, STATE, RESULT, ERRORMSG, AD_TABLE_ID, RECORD_ID, OWNERID, MODIFIERID, CREATIONDATE, MODIFIEDDATE, ISACTIVE, RECORD_NO "+
					 "from AD_PINSTANCE where id='44447' ";
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("AD_PINSTANCE_ID", AD_PINSTANCE_ID);
			paramMap.put("INFO", INFO);
			count = njdbcTemplate_henlo.update(insert, paramMap);
			if(count>0){
				insert ="insert into AD_PINSTANCE_PARA (ID, AD_CLIENT_ID, AD_ORG_ID, AD_PINSTANCE_ID, ORDERNO, NAME, P_STRING, P_STRING_TO, P_NUMBER, P_NUMBER_TO, P_DATE, P_DATE_TO, INFO_TO, OWNERID, MODIFIERID, CREATIONDATE, MODIFIEDDATE, ISACTIVE, OLDPSTR, P_CLOB, OLDINFO, INFO) "+
						"select get_sequences('ad_pinstance_para') ID, AD_CLIENT_ID, AD_ORG_ID,:AD_PINSTANCE_ID, ORDERNO, NAME, P_STRING, P_STRING_TO, P_NUMBER, P_NUMBER_TO, P_DATE, P_DATE_TO, INFO_TO, OWNERID, MODIFIERID, CREATIONDATE, MODIFIEDDATE, ISACTIVE, OLDPSTR, P_CLOB, OLDINFO, INFO "+
						"from ad_pinstance_para where  ad_pinstance_id ='44447' and ORDERNO>1000";
				
				count = njdbcTemplate_henlo.update(insert, paramMap);
				
				
				update = "update AD_PINSTANCE_PARA a set a.info = :INFO where AD_PINSTANCE_ID=:AD_PINSTANCE_ID and name='BILLDATE' ";
				njdbcTemplate_henlo.update(update, paramMap);
				
				if(count>0){
					final int p_pi_id = Integer.parseInt(AD_PINSTANCE_ID);
					//执行存储过程
					procedure = "{call rp_retail001_generate(?)}";  		
					@SuppressWarnings("unchecked")
					Map<String,Object> map_PChkSale = (HashMap<String, Object>) jdbcTemplate_henlo.execute(procedure,new CallableStatementCallback() {  
			            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
			                cs.setInt(1, p_pi_id);
			                cs.execute();
			                Map<String,Object> map = new HashMap<String, Object>();  
			                map.put("r_code", "0");
			                return map;
			            }
			        }); 
					
					String pro_query = " select a.BILLDATE,a.C_STORE_ID,B.NAME C_STORE_NAME, "+
										" sum(a.QTYCOST) QTYCOST,sum(a.TOT_AMT_ACTUAL) TOT_AMT_ACTUAL, sum(a.TOT_AMT_LIST) TOT_AMT_LIST, "+
										" sum(a.PROFIT) PROFIT,a.RATE,a.VIP_RETAIL_PER "+
										" from rp_retail001 a ,c_store b  "+
										" where A.C_STORE_ID = b.id  and AD_PI_ID = :AD_PINSTANCE_ID "+
										" group by a.BILLDATE,a.C_STORE_ID,B.NAME,VIP_RETAIL_PER,a.RATE "+
										" order by BILLDATE,C_STORE_NAME ";
					 list = njdbcTemplate_henlo.queryForList(pro_query, paramMap);
					 
					 for (int i = 0; i < list.size(); i++) {
							Map<String,Object> listMap = list.get(i);
							Map<String,Object> map = new LinkedHashMap<String,Object>();
							if(i==0){
								Map<String,Object> map_head = new LinkedHashMap<String,Object>();
								map_head.put("BILLDATE", "单据日期");
								map_head.put("C_STORE_NAME", "店仓");
								map_head.put("QTYCOST", "成本金额");
								map_head.put("TOT_AMT_ACTUAL", "成交金额");
								map_head.put("TOT_AMT_LIST", "零售金额");
								map_head.put("PROFIT", "毛利");
								map_head.put("RATE", "毛利率");
								map_head.put("VIP_RETAIL_PER", "VIP占比");
								listArray.add(map_head);
							}
							map.put("BILLDATE", listMap.get("BILLDATE").toString());
							map.put("C_STORE_NAME", listMap.get("C_STORE_NAME").toString());
							map.put("QTYCOST", listMap.get("QTYCOST").toString());
							map.put("TOT_AMT_ACTUAL", listMap.get("TOT_AMT_ACTUAL").toString());
							map.put("TOT_AMT_LIST", listMap.get("TOT_AMT_LIST").toString());
							map.put("PROFIT", listMap.get("PROFIT").toString());
							map.put("RATE", listMap.get("RATE").toString());
							map.put("VIP_RETAIL_PER", listMap.get("RATE").toString());
							listArray.add(map);
						}
					 count = list.size();
				}
			}
			
			return new ExtReturn(true, listArray,count);
			
		} catch (Exception e) {
			return new ExtReturn(false, e.toString());
		}
		
	}
	
	/**
	 * 销售排行分析(款)TOP10
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping( value="/reportview_retail_002",method=RequestMethod.POST)
	@ResponseBody
	public Object reportview_retail_002(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result = "",query="",insert="",procedure="",callbackfun="",docno="",oper="",update ="",store="",date_start="",date_end="";
		int count = 0;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listArray = new ArrayList();
		
		try {
			date_start = request.getParameter("date_start");
			date_end = request.getParameter("date_end");
			if(date_start==null||date_start.equals(""))
				date_start="17990101";
			
			if(date_end==null||date_end.equals(""))
				date_end="20991231";
			
			String INFO = " ( DATE BETWEEN "+date_start+" AND "+date_end+") ";
			
			
			query = "select get_sequences('AD_PINSTANCE') AD_PINSTANCE_ID  from dual ";
			String AD_PINSTANCE_ID = jdbcTemplate_henlo.queryForObject(query, String.class);
			System.out.println("AD_PINSTANCE_ID:"+AD_PINSTANCE_ID);
			insert = "insert into AD_PINSTANCE (ID, AD_CLIENT_ID, AD_ORG_ID, ORDERNO, AD_PROCESSQUEUE_ID, AD_PROCESS_ID, AD_USER_ID, STATE, RESULT, ERRORMSG, AD_TABLE_ID, RECORD_ID, OWNERID, MODIFIERID, CREATIONDATE, MODIFIEDDATE, ISACTIVE, RECORD_NO) "+
					 "select :AD_PINSTANCE_ID, AD_CLIENT_ID, AD_ORG_ID, ORDERNO, AD_PROCESSQUEUE_ID, AD_PROCESS_ID, AD_USER_ID, STATE, RESULT, ERRORMSG, AD_TABLE_ID, RECORD_ID, OWNERID, MODIFIERID, CREATIONDATE, MODIFIEDDATE, ISACTIVE, RECORD_NO "+
					 "from AD_PINSTANCE where id='44641' ";
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("AD_PINSTANCE_ID", AD_PINSTANCE_ID);
			paramMap.put("INFO", INFO);
			count = njdbcTemplate_henlo.update(insert, paramMap);
			if(count>0){
				insert ="insert into AD_PINSTANCE_PARA (ID, AD_CLIENT_ID, AD_ORG_ID, AD_PINSTANCE_ID, ORDERNO, NAME, P_STRING, P_STRING_TO, P_NUMBER, P_NUMBER_TO, P_DATE, P_DATE_TO, INFO_TO, OWNERID, MODIFIERID, CREATIONDATE, MODIFIEDDATE, ISACTIVE, OLDPSTR, P_CLOB, OLDINFO, INFO) "+
						"select get_sequences('ad_pinstance_para') ID, AD_CLIENT_ID, AD_ORG_ID,:AD_PINSTANCE_ID, ORDERNO, NAME, P_STRING, P_STRING_TO, P_NUMBER, P_NUMBER_TO, P_DATE, P_DATE_TO, INFO_TO, OWNERID, MODIFIERID, CREATIONDATE, MODIFIEDDATE, ISACTIVE, OLDPSTR, P_CLOB, OLDINFO, INFO "+
						"from ad_pinstance_para where  ad_pinstance_id ='44641' and ORDERNO>1000";
				
				count = njdbcTemplate_henlo.update(insert, paramMap);
				
				
				update = "update AD_PINSTANCE_PARA a set a.info = :INFO where AD_PINSTANCE_ID=:AD_PINSTANCE_ID and name='DATE' ";
				njdbcTemplate_henlo.update(update, paramMap);
				
				if(count>0){
					final int p_pi_id = Integer.parseInt(AD_PINSTANCE_ID);
					//执行存储过程
					procedure = "{call RP_RETAILSTYLEORD_MKL_GENERATE(?)}";  		
					@SuppressWarnings("unchecked")
					Map<String,Object> map_PChkSale = (HashMap<String, Object>) jdbcTemplate_henlo.execute(procedure,new CallableStatementCallback() {  
			            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
			                cs.setInt(1, p_pi_id);
			                cs.execute();
			                Map<String,Object> map = new HashMap<String, Object>();  
			                map.put("r_code", "0");
			                return map;
			            }
			        }); 
					
					String pro_query = "select A.ORDERNO,B.NAME M_PRODUCT_NAME,B.VALUE M_PRODUCT_VALUE,A.QTY,A.AMT_ACTUAL,A.QTY_STORAGE,A.C_DATE "+
									   "from RP_RETAILSTYLEORD_MKL a  "+
									   "left join M_PRODUCT b on A.M_PRODUCT_ID=B.ID "+
									   "where A.AD_PI_ID=:AD_PINSTANCE_ID  order by A.ORDERNO,M_PRODUCT_NAME ";
					 
					list = njdbcTemplate_henlo.queryForList(pro_query, paramMap);
					 
					for (int i = 0; i < list.size(); i++) {
							Map<String,Object> listMap = list.get(i);
							Map<String,Object> map = new LinkedHashMap<String,Object>();
							if(i==0){
								Map<String,Object> map_head = new LinkedHashMap<String,Object>();
								map_head.put("ORDERNO", "排名");
								map_head.put("M_PRODUCT_NAME", "款号");
								map_head.put("M_PRODUCT_VALUE", "品名");
								map_head.put("QTY", "销售数量");
								map_head.put("AMT_ACTUAL", "销售金额");
								map_head.put("QTY_STORAGE", "库存数量");
								map_head.put("C_DATE", "可周转天数");
								listArray.add(map_head);
							}
							map.put("ORDERNO", listMap.get("ORDERNO").toString());
							map.put("M_PRODUCT_NAME", listMap.get("M_PRODUCT_NAME").toString());
							map.put("M_PRODUCT_VALUE", listMap.get("M_PRODUCT_VALUE").toString());
							map.put("QTY", listMap.get("QTY").toString());
							map.put("AMT_ACTUAL", listMap.get("AMT_ACTUAL").toString());
							map.put("QTY_STORAGE", listMap.get("QTY_STORAGE").toString());
							map.put("C_DATE", listMap.get("C_DATE").toString());
							listArray.add(map);
						}
					 count = list.size();
				}
			}
			
			return new ExtReturn(true, listArray,count);
			
		} catch (Exception e) {
			return new ExtReturn(false, e.toString());
		}
		
	}
	
	/**
	 * 销售出库(类别)
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping( value="/reportview_retail_004",method=RequestMethod.POST)
	@ResponseBody
	public Object reportview_retail_004(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result = "",query="",insert="",procedure="",callbackfun="",docno="",oper="",update ="",store="",date_start="",date_end="";
		int count = 0;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listArray = new ArrayList();
		
		try {
			date_start = request.getParameter("date_start");
			date_end = request.getParameter("date_end");
			if(date_start==null||date_start.equals(""))
				date_start="17990101";
			
			if(date_end==null||date_end.equals(""))
				date_end="20991231";

			query = "SELECT ATTRIBNAME,SUM(QTYSALEOUT)-SUM(QTYRETSALEIN) QTYSALEOUT,SUM(AMTSALEOUT)-SUM(AMTRETSALEIN) AMTSALEOUT " +
					"FROM ( "+
					"select A.BILLDATE,A.C_CUSTOMER_ID,A.C_STORE_ID,A.C_DEST_ID,A.QTYSALEOUT,A.AMTSALEOUT,A.AMTLISTSALEOUT,A.AMTLISTRETSALEIN ,A.QTYRETSALEIN,A.AMTRETSALEIN,A.DISCOUNTSALE,A.DISCOUNTRETSALE,C.NAME,C.ATTRIBNAME "+
					"from RP_SALE001 A  "+
					"LEFT JOIN ( "+
					"SELECT  A.ID,A.NAME,NVL(B.ATTRIBNAME,'其他') ATTRIBNAME FROM M_PRODUCT  A LEFT JOIN ( "+ 
					"select T.ID,T.ATTRIBNAME from M_DIM t ,M_DIMDEF w where t.DIMFLAG='DIM5' and t.M_DIMDEF_ID=w.id and w.isactive='Y' "+
					") B ON A.M_DIM5_ID = B.ID  "+
					") C ON A.M_PRODUCT_ID=C.ID   "+
					") D WHERE BILLDATE BETWEEN :date_start AND :date_end " +
					"GROUP BY ATTRIBNAME ORDER BY ATTRIBNAME";
			
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("date_start", date_start);
			paramMap.put("date_end", date_end);
			
			list = njdbcTemplate_henlo.queryForList(query, paramMap);
			
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> listMap = list.get(i);
				Map<String,Object> map = new LinkedHashMap<String,Object>();
				if(i==0){
					Map<String,Object> map_head = new LinkedHashMap<String,Object>();
					map_head.put("ATTRIBNAME", "大类");
					map_head.put("QTYSALEOUT", "出库数量");
					map_head.put("AMTSALEOUT", "出库金额");
					listArray.add(map_head);
				}
				map.put("ATTRIBNAME", listMap.get("ATTRIBNAME").toString());
				map.put("QTYSALEOUT", listMap.get("QTYSALEOUT").toString());
				map.put("AMTSALEOUT", listMap.get("AMTSALEOUT").toString());
				listArray.add(map);
			}
			count = list.size();
			
			return new ExtReturn(true, listArray,count);
			
		} catch (Exception e) {
			return new ExtReturn(false, e.toString());
		}
		
	}
	
	/**
	 * 销售出库分析(经销商)
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping( value="/reportview_retail_005",method=RequestMethod.POST)
	@ResponseBody
	public Object reportview_retail_005(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result = "",query="",insert="",procedure="",callbackfun="",docno="",oper="",update ="",store="",date_start="",date_end="";
		int count = 0;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listArray = new ArrayList();
		
		try {
			date_start = request.getParameter("date_start");
			if(date_start==null||date_start.equals(""))
				date_start=DateFormatUtils.format(new Date(), "yyyyMM");

			query = "SELECT CST.NAME CST_NAME, M1.C_CUSTOMER_ID,M1.BILLDATE, "+
					"M1.QTYSALEOUT, "+
					"ROUND( (M1.QTYSALEOUT-NVL(M2.QTYSALEOUT,0))/(CASE WHEN NVL(M2.QTYSALEOUT,1)=0 THEN 1 ELSE NVL(M2.QTYSALEOUT,1) END),4)*100 QTYSALEOUT_HB_ , "+
					"ROUND( (M1.QTYSALEOUT-NVL(M3.QTYSALEOUT,0))/(CASE WHEN NVL(M3.QTYSALEOUT,1)=0 THEN 1 ELSE NVL(M3.QTYSALEOUT,1) END),4)*100 QTYSALEOUT_TB_ , "+
					"M1.AMTSALEOUT, "+
					"ROUND( (M1.AMTSALEOUT-NVL(M2.AMTSALEOUT,0))/(CASE WHEN NVL(M2.AMTSALEOUT,1)=0 THEN 1 ELSE NVL(M2.AMTSALEOUT,1) END),4)*100 AMTSALEOUT_HB_ , "+
					"ROUND( (M1.AMTSALEOUT-NVL(M3.AMTSALEOUT,0))/(CASE WHEN NVL(M3.AMTSALEOUT,1)=0 THEN 1 ELSE NVL(M3.AMTSALEOUT,1) END),4)*100 AMTSALEOUT_TB_ , "+
					"M2.BILLDATE BILLDATE_HB,NVL(M2.QTYSALEOUT,0) QTYSALEOUT_HB,NVL(M2.AMTSALEOUT,0) AMTSALEOUT_HB, "+
					"M3.BILLDATE BILLDATE_TB,NVL(M3.QTYSALEOUT,0) QTYSALEOUT_TB,NVL(M3.AMTSALEOUT,0) AMTSALEOUT_TB  "+
					"FROM ( "+
					"SELECT C_CUSTOMER_ID,BILLDATE,SUM(QTYSALEOUT)-SUM(QTYRETSALEIN) QTYSALEOUT,SUM(AMTSALEOUT)-SUM(AMTRETSALEIN) AMTSALEOUT FROM ( "+
					"select SUBSTR(A.BILLDATE,1,6) BILLDATE,A.C_CUSTOMER_ID,A.C_STORE_ID,A.C_DEST_ID,A.QTYSALEOUT,A.AMTSALEOUT,A.AMTLISTSALEOUT,A.AMTLISTRETSALEIN ,A.QTYRETSALEIN,A.AMTRETSALEIN,A.DISCOUNTSALE,A.DISCOUNTRETSALE "+
					"from RP_SALE001 A  "+
					") D GROUP BY C_CUSTOMER_ID,BILLDATE ORDER BY C_CUSTOMER_ID,BILLDATE "+
					") M1 LEFT JOIN ( "+
					"SELECT C_CUSTOMER_ID,BILLDATE,SUM(QTYSALEOUT)-SUM(QTYRETSALEIN) QTYSALEOUT,SUM(AMTSALEOUT)-SUM(AMTRETSALEIN) AMTSALEOUT FROM ( "+
					"select SUBSTR(A.BILLDATE,1,6) BILLDATE,A.C_CUSTOMER_ID,A.C_STORE_ID,A.C_DEST_ID,A.QTYSALEOUT,A.AMTSALEOUT,A.AMTLISTSALEOUT,A.AMTLISTRETSALEIN ,A.QTYRETSALEIN,A.AMTRETSALEIN,A.DISCOUNTSALE,A.DISCOUNTRETSALE "+
					"from RP_SALE001 A  "+
					") D GROUP BY C_CUSTOMER_ID,BILLDATE "+ 
					") M2 ON M1.C_CUSTOMER_ID=M2.C_CUSTOMER_ID AND M1.BILLDATE=to_char(add_months(trunc(to_date(M2.BILLDATE,'yyyyMM')),+1),'yyyymm') "+
					"LEFT JOIN ( "+
					"SELECT C_CUSTOMER_ID,BILLDATE,SUM(QTYSALEOUT)-SUM(QTYRETSALEIN) QTYSALEOUT,SUM(AMTSALEOUT)-SUM(AMTRETSALEIN) AMTSALEOUT FROM ( "+
					"select SUBSTR(A.BILLDATE,1,6) BILLDATE,A.C_CUSTOMER_ID,A.C_STORE_ID,A.C_DEST_ID,A.QTYSALEOUT,A.AMTSALEOUT,A.AMTLISTSALEOUT,A.AMTLISTRETSALEIN ,A.QTYRETSALEIN,A.AMTRETSALEIN,A.DISCOUNTSALE,A.DISCOUNTRETSALE "+
					"from RP_SALE001 A "+ 
					") D GROUP BY C_CUSTOMER_ID,BILLDATE "+ 
					") M3 ON M1.C_CUSTOMER_ID=M3.C_CUSTOMER_ID AND M1.BILLDATE=to_char(add_months(trunc(to_date(M3.BILLDATE,'yyyyMM')),+12),'yyyymm') "+
					"LEFT JOIN C_CUSTOMER CST ON M1.C_CUSTOMER_ID = CST.ID " + 
					"WHERE M1.BILLDATE = :BILLDATE "; 
			
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("BILLDATE", date_start);
			
			list = njdbcTemplate_henlo.queryForList(query, paramMap);
			
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> listMap = list.get(i);
				Map<String,Object> map = new LinkedHashMap<String,Object>();
				if(i==0){
					Map<String,Object> map_head = new LinkedHashMap<String,Object>();
					map_head.put("CST_NAME", "经销商");
					map_head.put("QTYSALEOUT", "出库数量");
					map_head.put("QTYSALEOUT_HB_", "环比增长%");
					map_head.put("QTYSALEOUT_TB_", "同比增长%");
					map_head.put("AMTSALEOUT", "出库金额");
					map_head.put("AMTSALEOUT_HB_", "环比增长%");
					map_head.put("AMTSALEOUT_TB_", "同比增长%");
/*					map_head.put("QTYSALEOUT_HB", "上月数量");
					map_head.put("AMTSALEOUT_HB", "上月金额");
					map_head.put("QTYSALEOUT_TB", "去年同期数量");
					map_head.put("AMTSALEOUT_TB", "去年同期金额");*/
					listArray.add(map_head);
				}
				map.put("CST_NAME", listMap.get("CST_NAME").toString());
				map.put("QTYSALEOUT", listMap.get("QTYSALEOUT").toString());
				map.put("QTYSALEOUT_HB_", listMap.get("QTYSALEOUT_HB_").toString());
				map.put("QTYSALEOUT_TB_", listMap.get("QTYSALEOUT_TB_").toString());
				map.put("AMTSALEOUT", listMap.get("AMTSALEOUT").toString());
				map.put("AMTSALEOUT_HB_", listMap.get("AMTSALEOUT_HB_").toString());
				map.put("AMTSALEOUT_TB_", listMap.get("AMTSALEOUT_TB_").toString());
/*				map.put("QTYSALEOUT_HB", listMap.get("QTYSALEOUT_HB").toString());
				map.put("AMTSALEOUT_HB", listMap.get("AMTSALEOUT_HB").toString());
				map.put("QTYSALEOUT_TB", listMap.get("QTYSALEOUT_TB").toString());
				map.put("AMTSALEOUT_TB", listMap.get("AMTSALEOUT_TB").toString());*/
				listArray.add(map);
			}
			count = list.size();
			
			return new ExtReturn(true, listArray,count);
			
		} catch (Exception e) {
			return new ExtReturn(false, e.toString());
		}
		
	}
	
	/**
	 * 门店销售排行(TOP20)
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping( value="/reportview_retail_006",method=RequestMethod.POST)
	@ResponseBody
	public Object reportview_retail_006(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result = "",query="",insert="",procedure="",callbackfun="",docno="",oper="",update ="",store="",date_start="",date_end="";
		int count = 0;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listArray = new ArrayList();
		
		try {
			date_start = request.getParameter("date_start");
			if(date_start==null||date_start.equals(""))
				date_start=DateFormatUtils.format(new Date(), "yyyyMM");
			
			
			query = "select C_STORE_ID,C_STORE_NAME,MONTHDATE,TOT_AMT_MARK,DAY_AMT, 100*round(DAY_AMT/( case when nvl(TOT_AMT_MARK,0)=0 then 1 else TOT_AMT_MARK end ),2)  WCL, TOT_AMT_MARK-DAY_AMT SYL from ( "+
					"select a.C_STORE_ID,B.NAME C_STORE_NAME ,Substr(A.MONTHDATE,1,6) MONTHDATE,round(SUM(A.TOT_AMT_MARK)) TOT_AMT_MARK,round(sum(A.DAY_AMT),1) DAY_AMT    "+
					"from RP_RETAILMARK04 a, C_STORE b  "+
					"where A.C_STORE_ID=B.ID "+
					"group by a.C_STORE_ID,B.NAME ,Substr(A.MONTHDATE,1,6) "+ 
					") a  "+
					"WHERE a.MONTHDATE = :BILLDATE " +
					"order by MONTHDATE, DAY_AMT desc " ; 
					
			
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("BILLDATE", date_start);
			
			list = njdbcTemplate_henlo.queryForList(query, paramMap);
			
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> listMap = list.get(i);
				Map<String,Object> map = new LinkedHashMap<String,Object>();
				if(i==0){
					Map<String,Object> map_head = new LinkedHashMap<String,Object>();
					map_head.put("C_STORE_NAME", "店仓名称");
					map_head.put("TOT_AMT_MARK", "指标");
					map_head.put("DAY_AMT", "完成量");
					map_head.put("WCL", "完成率%");
					map_head.put("SYL", "剩余量");
					listArray.add(map_head);
				}
				map.put("C_STORE_NAME", listMap.get("C_STORE_NAME").toString());
				map.put("TOT_AMT_MARK", listMap.get("TOT_AMT_MARK").toString());
				map.put("DAY_AMT", listMap.get("DAY_AMT").toString());
				map.put("WCL", listMap.get("WCL").toString());
				map.put("SYL", listMap.get("SYL").toString());
				listArray.add(map);
			}
			count = list.size();
			
			return new ExtReturn(true, listArray,count);
			
		} catch (Exception e) {
			return new ExtReturn(false, e.toString());
		}
		
	}
	
	
	@RequestMapping( value="/reportview_retail_007",method=RequestMethod.POST)
	@ResponseBody
	public Object reportview_retail_007(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result = "",query="",insert="",procedure="",callbackfun="",docno="",oper="",update ="",store="",date_start="",date_end="";
		int count = 0;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listArray = new ArrayList();
		
		try {
			date_start = request.getParameter("date_start");
			if(date_start==null||date_start.equals(""))
				date_start=DateFormatUtils.format(new Date(), "yyyyMM");
			
			
			query = "select C_STORE_ID,C_STORE_NAME,MONTHDATE,TOT_AMT_MARK,DAY_AMT, 100*round(DAY_AMT/( case when nvl(TOT_AMT_MARK,0)=0 then 1 else TOT_AMT_MARK end ),2)  WCL, TOT_AMT_MARK-DAY_AMT SYL from ( "+
					"select a.C_STORE_ID,B.NAME C_STORE_NAME ,Substr(A.MONTHDATE,1,6) MONTHDATE,round(SUM(A.TOT_AMT_MARK)) TOT_AMT_MARK,round(sum(A.DAY_AMT),1) DAY_AMT    "+
					"from RP_RETAILMARK04 a, C_STORE b  "+
					"where A.C_STORE_ID=B.ID "+
					"group by a.C_STORE_ID,B.NAME ,Substr(A.MONTHDATE,1,6) "+ 
					") a  "+
					"WHERE a.MONTHDATE = :BILLDATE " +
					"order by MONTHDATE, DAY_AMT " ; 
			
			Map<String,Object> paramMap = new HashMap<String, Object>();
			paramMap.put("BILLDATE", date_start);
			
			list = njdbcTemplate_henlo.queryForList(query, paramMap);
			
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> listMap = list.get(i);
				Map<String,Object> map = new LinkedHashMap<String,Object>();
				if(i==0){
					Map<String,Object> map_head = new LinkedHashMap<String,Object>();
					map_head.put("C_STORE_NAME", "店仓名称");
					map_head.put("TOT_AMT_MARK", "指标");
					map_head.put("DAY_AMT", "完成量");
					map_head.put("WCL", "完成率%");
					map_head.put("SYL", "剩余量");
					listArray.add(map_head);
				}
				map.put("C_STORE_NAME", listMap.get("C_STORE_NAME").toString());
				map.put("TOT_AMT_MARK", listMap.get("TOT_AMT_MARK").toString());
				map.put("DAY_AMT", listMap.get("DAY_AMT").toString());
				map.put("WCL", listMap.get("WCL").toString());
				map.put("SYL", listMap.get("SYL").toString());
				listArray.add(map);
			}
			
			count = list.size();
			
			return new ExtReturn(true, listArray,count);
			
		} catch (Exception e) {
			return new ExtReturn(false, e.toString());
		}
		
	}
	
	
	@RequestMapping( value="/reportview_retail_008",method=RequestMethod.POST)
	@ResponseBody
	public Object reportview_retail_008(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result = "",query="",insert="",procedure="",callbackfun="",docno="",oper="",update ="",store="",date_start="",date_end="",c_customer_id="";
		int count = 0;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listArray = new ArrayList();
		
		try {
			date_start = request.getParameter("date_start");
			if(date_start==null||date_start.equals(""))
				date_start=DateFormatUtils.format(new Date(), "yyyy");
			
			String[] c_customer_list = request.getParameterValues("c_customer_id[]");
			
			String type = request.getParameter("type");
			
			if(c_customer_list==null)
				c_customer_id="";
			else{
				for (int i = 0; i < c_customer_list.length; i++) {
					c_customer_id = c_customer_id+c_customer_list[i]+"','";
				}
				c_customer_id = StringUtils.removeEnd(c_customer_id, "','");
				
			}
			
			if(type==null||type.equals("")){
				query = "select substr(BILLDATE,5,2) BILLDATE,C_CUSTOMER_ID,C_CUSTOMER_NAME,C_STORE_ID,C_STORE_NAME,count(distinct SALESREP_ID) SALESREP_ID,sum(TOT_AMT_ACTUAL) TOT_AMT_ACTUAL,SUM(TOT_QTY)  TOT_QTY, "+
						"count(distinct DOCNO) DOCNO_CNT, round(sum(TOT_AMT_ACTUAL)/count(distinct DOCNO),2) DOCNO_AMT_ACTUAL,Round(SUM(TOT_QTY)/count(distinct DOCNO),2) DOCNO_TOT_QTY, "+
						"round(sum(TOT_AMT_ACTUAL)/count(distinct SALESREP_ID),2) SALESREP_AMT_ACTUAL "+
						"from ( "+
						"SELECT  substr(A.BILLDATE,1,6) BILLDATE ,B.C_CUSTOMER_ID,C.NAME C_CUSTOMER_NAME ,a.C_STORE_ID,B.NAME C_STORE_NAME,nvl(a.SALESREP_ID,'0') SALESREP_ID,a.TOT_AMT_ACTUAL ,a.TOT_QTY,A.DOCNO "+
						"FROM m_retail a  "+
						"left join C_STORE b on A.C_STORE_ID=B.ID "+ 
						"left join C_CUSTOMER c on B.C_CUSTOMER_ID=C.ID "+
						"WHERE a.status = 2  and a.TOT_QTY>0 and substr(A.BILLDATE,1,4) = :BILLDATE "+
						") a where ( C_CUSTOMER_ID in ('"+c_customer_id+"') or 0="+c_customer_id.length()+ ") " +
						"group by substr(BILLDATE,5,2),C_CUSTOMER_ID,C_CUSTOMER_NAME,C_STORE_ID,C_STORE_NAME " +
						"order by BILLDATE,C_CUSTOMER_NAME,C_STORE_NAME";
					
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("BILLDATE", date_start);
				
				list = njdbcTemplate_henlo.queryForList(query, paramMap);
				
				for (int i = 0; i < list.size(); i++) {
					Map<String,Object> listMap = list.get(i);
					Map<String,Object> map = new LinkedHashMap<String,Object>();
					if(i==0){
						Map<String,Object> map_head = new LinkedHashMap<String,Object>();
						map_head.put("BILLDATE", "月份");
						map_head.put("C_CUSTOMER_NAME", "经销商");
						map_head.put("C_STORE_NAME", "店仓名称");
						map_head.put("TOT_AMT_ACTUAL", "零售额(元)");
						map_head.put("SALESREP_ID", "店员数");
						map_head.put("DOCNO_CNT", "客单量");
						map_head.put("DOCNO_AMT_ACTUAL", "客单价");
						map_head.put("DOCNO_TOT_QTY", "连带率");
						map_head.put("SALESREP_AMT_ACTUAL", "人效");
						listArray.add(map_head);
					}
					map.put("BILLDATE", listMap.get("BILLDATE").toString());
					map.put("C_CUSTOMER_NAME", listMap.get("C_CUSTOMER_NAME").toString());
					map.put("C_STORE_NAME", listMap.get("C_STORE_NAME").toString());
					map.put("TOT_AMT_ACTUAL", listMap.get("TOT_AMT_ACTUAL").toString());
					map.put("SALESREP_ID", listMap.get("SALESREP_ID").toString());
					map.put("DOCNO_CNT", listMap.get("DOCNO_CNT").toString());
					map.put("DOCNO_AMT_ACTUAL", listMap.get("DOCNO_AMT_ACTUAL").toString());
					map.put("DOCNO_TOT_QTY", listMap.get("DOCNO_TOT_QTY").toString());
					map.put("SALESREP_AMT_ACTUAL", listMap.get("SALESREP_AMT_ACTUAL").toString());
					listArray.add(map);
				}
				
				count = list.size();
			}else{
				//经销商统计
				query = 
						"select base.mon,base.C_CUSTOMER_ID,base.C_CUSTOMER_NAME,nvl(ldl.DOCNO_TOT_QTY,0) DOCNO_TOT_QTY from ( "+
						"    select a.Mon,b.id C_CUSTOMER_ID,b.name C_CUSTOMER_NAME  from ( "+
						"    select rownum Mon from dual connect by rownum < =12 "+
						"    ) a ,C_CUSTOMER b  "+
						"    where ( b.ID in ('"+c_customer_id+"') or 0="+c_customer_id.length()+ ")  "+ 
						") base left join ( "+
						"    select to_number(substr(BILLDATE,5,2)) BILLDATE,C_CUSTOMER_ID,C_CUSTOMER_NAME,Round(SUM(TOT_QTY)/count(distinct DOCNO),2) DOCNO_TOT_QTY "+
						"     from (  "+
						"     SELECT  substr(A.BILLDATE,1,6) BILLDATE ,B.C_CUSTOMER_ID,C.NAME C_CUSTOMER_NAME ,a.C_STORE_ID,B.NAME C_STORE_NAME,nvl(a.SALESREP_ID,'0') SALESREP_ID,a.TOT_AMT_ACTUAL ,a.TOT_QTY,A.DOCNO "+ 
						"     FROM m_retail a   "+
						"     left join C_STORE b on A.C_STORE_ID=B.ID "+ 
						"     left join C_CUSTOMER c on B.C_CUSTOMER_ID=C.ID WHERE a.status = 2  and a.TOT_QTY>0 and substr(A.BILLDATE,1,4) = '2014' "+ 
						"     ) a where ( C_CUSTOMER_ID in ('"+c_customer_id+"') or 0="+c_customer_id.length()+ ") "+
						"    group by to_number(substr(BILLDATE,5,2)),C_CUSTOMER_ID,C_CUSTOMER_NAME "+
						") ldl on base.mon=ldl.BILLDATE and base.C_CUSTOMER_ID=ldl.C_CUSTOMER_ID "+
						"order by C_CUSTOMER_ID,Mon";
				
				Map<String,Object> paramMap = new HashMap<String, Object>();
				paramMap.put("BILLDATE", date_start);
				
				listArray = njdbcTemplate_henlo.queryForList(query, paramMap);
				
				query = "select count(distinct name) from C_CUSTOMER b  where ( b.ID in ('"+c_customer_id+"') or 0="+c_customer_id.length()+ ")  ";
				
				count = jdbcTemplate_henlo.queryForInt(query);
				
			}
			
			return new ExtReturn(true, listArray,count);
			
		} catch (Exception e) {
			System.out.println("异常:"+e.toString());
			return new ExtReturn(false, e.toString());
		}
		
	}
	
	
	@RequestMapping( value="/reportview_retail_101",method=RequestMethod.POST)
	@ResponseBody
	public Object reportview_retail_101(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result = "",query="",insert="",procedure="",callbackfun="",docno="",oper="",update ="",store="",date_start="",date_end="",c_customer_id="";
		int count = 0;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listArray = new ArrayList();
		
		try {
			
			String[] c_customer_list = request.getParameterValues("c_customer_id[]");
			
			if(c_customer_list==null)
				c_customer_id="";
			else{
				for (int i = 0; i < c_customer_list.length; i++) {
					c_customer_id = c_customer_id+c_customer_list[i]+"','";
				}
				c_customer_id = StringUtils.removeEnd(c_customer_id, "','");
				
			}
		
			query = 
					"select A.C_CUSTOMER_ID,B.NAME C_CUSTOMER_NAME,AMT_RECEIVABLE,AMT_RECEIVE,AMT_RECEIVABLE-AMT_RECEIVE AMT "+ 
					"from FA_CUSTOMER a, C_CUSTOMER b  "+
					"where A.C_CUSTOMER_ID=b.id and " +
					"( A.C_CUSTOMER_ID in ('"+c_customer_id+"') or 0="+c_customer_id.length()+ ") "+
					"order by AMT desc"	;
			
			list = jdbcTemplate_henlo.queryForList(query);
			
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> listMap = list.get(i);
				Map<String,Object> map = new LinkedHashMap<String,Object>();
				if(i==0){
					Map<String,Object> map_head = new LinkedHashMap<String,Object>();
					map_head.put("C_CUSTOMER_NAME", "经销商");
					map_head.put("AMT_RECEIVABLE", "应收款");
					map_head.put("AMT_RECEIVE", "已收款");
					map_head.put("AMT", "未收金额");
					listArray.add(map_head);
				}
				map.put("C_CUSTOMER_NAME", listMap.get("C_CUSTOMER_NAME").toString());
				map.put("AMT_RECEIVABLE", listMap.get("AMT_RECEIVABLE").toString());
				map.put("AMT_RECEIVE", listMap.get("AMT_RECEIVE").toString());
				map.put("AMT", listMap.get("AMT").toString());
				listArray.add(map);
			}
			
			count = list.size();
				
			return new ExtReturn(true, listArray,count);
			
		} catch (Exception e) {
			System.out.println("异常:"+e.toString());
			return new ExtReturn(false, e.toString());
		}
		
	}
	
	
	@RequestMapping( value="/reportview_retail_201",method=RequestMethod.POST)
	@ResponseBody
	public Object reportview_retail_201(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException {
		String result = "",query="",insert="",procedure="",callbackfun="",docno="",oper="",update ="",store="",date_start="",date_end="";
		int count = 0;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> listArray = new ArrayList();
		
		try {
			date_start = request.getParameter("date_start");
			date_end = request.getParameter("date_end");
			if(date_start==null||date_start.equals(""))
				date_start="17990101";
			
			if(date_end==null||date_end.equals(""))
				date_end="20991231";
			
			
			
			return new ExtReturn(true, listArray,count);
			
		} catch (Exception e) {
			return new ExtReturn(false, e.toString());
		}
		
	}
	
	
	@RequestMapping("/c_customer")
	@ResponseBody
	public Object c_customer(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		try {
			String query = "select id C_CUSTOMER_ID,name C_CUSTOMER_NAME from C_CUSTOMER where isactive='Y' ";
			List<Map<String,Object>> list = jdbcTemplate_henlo.queryForList(query);
			
			return new ExtReturn(true, list, list.size());
		} catch (Exception e) {
			// TODO: handle exception
			return new ExtReturn(true, e.toString(), "....");
		}
		
	}
	
	@RequestMapping("/news")
	@ResponseBody
	public Object news(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for(int i=1;i<100;i++){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("ID", i);
				map.put("HEAD", "第"+i+" 条信息,From Analog data");
				map.put("READ", "NO");
				list.add(map);
			}
			
			return new ExtReturn(true, list, list.size());
		} catch (Exception e) {
			// TODO: handle exception
			return new ExtReturn(true, e.toString(), "....");
		}
		
	}
	
	
	@RequestMapping("/newsdetail")
	@ResponseBody
	public Object newsdetail(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		try {
			String newsid = request.getParameter("newsid");
			
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("DETAIL", "第"+newsid+"条信息的内容, From Analog data");
			list.add(map);
			
			return new ExtReturn(true, list, list.size());
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ExtReturn(true, e.toString(), "....");
		}
		
	}
	

	
	
	@RequestMapping("/wenjuan")
	@ResponseBody
	public Object wenjuan(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		try {
			String result = "somethings";
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("test", "test_value");
			
			String dianpmc = request.getParameter("dianpmc");
			String fuzr = request.getParameter("fuzr");
			String xingb = request.getParameter("xingb");
			String lianxdh = request.getParameter("lianxdh");
			String dianpxxdz = request.getParameter("dianpxxdz");
			String dianpkysj = request.getParameter("dianpkysj");
			String kaiynx = request.getParameter("kaiynx");
			String dianpxz = request.getParameter("dianpxz");
			String suossq = request.getParameter("suossq");
			String nianzj = request.getParameter("nianzj");
			String nianyye = request.getParameter("nianyye");
			String yingymj = request.getParameter("yingymj");
			String yuangrs = request.getParameter("yuangrs");
			String vipsl = request.getParameter("vipsl");
			String zhuangxsj = request.getParameter("zhuangxsj");
			String shifzxxxx = request.getParameter("shifzxxxx");
			String shifxkd = request.getParameter("shifxkd");
			String userid = session.getAttribute("userid").toString();
			
			String insert = "insert into WENJUAN_RESULT(ID, DIANPMC, FUZR, XINGB, LIANXDH, DIANPXXDZ, DIANPKYSJ, KAIYNX, DIANPXZ, SUOSSQ, NIANZJ, NIANYYE, YINGYMJ, YUANGRS, VIPSL, ZHUANGXSJ, SHIFZXXXX, SHIFXKD,USERID) " +
					"select sys_guid(), :dianpmc, :fuzr, :xingb, :lianxdh, :dianpxxdz, :dianpkysj, :kaiynx, :dianpxz, :suossq, :nianzj, :nianyye, :yingymj, :yuangrs, :vipsl, :zhuangxsj, :shifzxxxx, :shifxkd,:userid from dual ";
			Map<String,Object> param = new HashMap<String, Object>();
			
			param.put("dianpmc", dianpmc);
			param.put("fuzr", fuzr);
			param.put("xingb", xingb);
			param.put("lianxdh", lianxdh);
			param.put("dianpxxdz", dianpxxdz);
			param.put("dianpkysj", dianpkysj);
			param.put("kaiynx", kaiynx);
			param.put("dianpxz", dianpxz);
			param.put("suossq", suossq);
			param.put("nianzj", nianzj);
			param.put("nianyye", nianyye);
			param.put("yingymj", yingymj);
			param.put("yuangrs", yuangrs);
			param.put("vipsl", vipsl);
			param.put("zhuangxsj", zhuangxsj);
			param.put("shifzxxxx", shifzxxxx);
			param.put("shifxkd", shifxkd);
			param.put("userid", userid);
			
			njdbcTemplate.update(insert, param);
			
			String update  = "update fdfair.users a set a.isactive='Y' where id =:userid ";
			njdbcTemplate.update(update, param);
			
			return new ExtReturn(true, "处理成功");
		} catch (Exception e) {
			System.out.println(e.toString());
			// TODO: handle exception
			return new ExtReturn(true, e.toString(), "....");
		}
		
	}
	
	
	@RequestMapping("/requestobject")
	@ResponseBody
	public Object requestobject(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		try {
			String result = "somethings";
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("test", "test_value");
			list.add(map);
			
			return new ExtReturn(true, result, list);
		} catch (Exception e) {
			// TODO: handle exception
			return new ExtReturn(true, e.toString(), "....");
		}
		
	}
	
	@RequestMapping("/requestvoid")
	public void requestvoid(HttpSession session, HttpServletRequest request,HttpServletResponse response) {
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		} finally{
			
		}
		
	}
	
}
