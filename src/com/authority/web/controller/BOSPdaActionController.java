package com.authority.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

import oracle.jdbc.OracleTypes;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.tools.zip.ZipOutputStream;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.authority.common.springmvc.DateConvertEditor;
import com.authority.common.utils.FileOperateUtil;
import com.authority.pojo.BaseUsers;
import com.authority.pojo.ExceptionReturn;
import com.authority.pojo.ExtGridReturn;
import com.authority.pojo.ExtReturn;
import com.authority.pojo.PdaReturn;
import com.authority.service.BOSPdaService;
import com.authority.service.BaseUsersService;
import com.authority.service.GMQPdaService;
import com.authority.web.interseptor.WebConstants;
import com.henlo.process.HenloController;

@Controller
@RequestMapping("/bos/pdaaction")
public class BOSPdaActionController {
private static final Logger logger = LoggerFactory.getLogger(BOSPdaActionController.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;
	
	@Autowired
	private BOSPdaService bospdaservice;
	
		
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	@RequestMapping(value="/login_store")
	@ResponseBody
	public Object login_store(HttpSession session, HttpServletRequest request) {
		try {
			
			String query = "select id,name  from c_store a where a.pda='Y'  order by name";
			List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
			if(list.size()==0){
				return new PdaReturn("N","");
			}else{
				String info = "";
				for (Map<String, Object> map : list) {
					info = info+map.get("ID")+","+map.get("NAME")+";";
				}
				//info = StringUtils.removeEnd(info, ";");
				return new PdaReturn("Y",info);
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new PdaReturn("N",e.toString());
		}
		
	}
	
	@RequestMapping(value="/login_users")
	@ResponseBody
	public Object login_users(HttpSession session, HttpServletRequest request) {
		try {
			String c_store_id = request.getParameter("c_store_id").toString();
			String query = "select distinct ID,TRUENAME||'*'||substr(email,0,(case when instr(EMAIL,'@')=0 then length(email) else instr(EMAIL,'@')-1 end)) name " +
					"from USERS where C_STORE_ID='"+c_store_id+"' and pda='Y' ";
			List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
			if(list.size()==0){
				return new PdaReturn("N","");
			}else{
				String info = "";
				for (Map<String, Object> map : list) {
					info = info+map.get("ID")+","+map.get("NAME")+";";
				}
				return new PdaReturn("Y",info);
			}
						
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new PdaReturn("N",e.toString());
		}			
		
	}
	
	@RequestMapping(value="/login_c_customerup")
	@ResponseBody
	public Object login_c_customerup(HttpSession session, HttpServletRequest request) {
		try {
			String c_store_id = request.getParameter("c_store_id").toString();
			String FROM = request.getParameter("FROM")==null?"":request.getParameter("FROM").toString();
			String query = "select ID,NAME,C_CUSTOMER_ID,C_CUSTOMERUP_ID from c_store a where a.id='"+c_store_id+"'";
			List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
			int total = list.size();
			if(list.size()==0&&FROM.equals("PDA")){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("ID", "");
				list.add(map);
			}
			return new ExtGridReturn(total, list);
						
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}			
		
	}
	
	@RequestMapping(value="/login")
	@ResponseBody	
	public Object login(@RequestParam String account, @RequestParam String password, HttpSession session, HttpServletRequest request) {
		try {
					
			String result ="00",store=";";
			
			if (StringUtils.isBlank(account)) {
				return new PdaReturn("N", "帐号不能为空！");
			}
			if (StringUtils.isBlank(password)) {
				return new PdaReturn("N", "密码不能为空！");
			}
			
			String passwordTmp = DigestUtils.md5Hex(password);
			
			String passwordIn = encrypt(passwordTmp, account);
			
			String query = "select count(*) from BASE_USERS " +
					"where account='"+account+"' and password='"+passwordIn+"'";
			
			query = "select count(*) from USERS where id='"+account+"' and PASSWORDHASH='"+password+"'";
			//query = "select count(*) from TSysUser a where a.userid = '"+account+"' and remark = '"+password+"'";
			
			if(jdbcTemplate.queryForInt(query)>0)
				result ="01";
			
			if ("01".equals(result)) {
				session.setAttribute(WebConstants.CURRENT_USER, account);
				//uniform
				query = "select a.id,a.Store+';'+b.StoreName Store " +
						"from TDefEmp a " +
						"left join TDefStore b on a.Store = b.Store " +
						"where b.Closed = 0 and a.EmpId ='"+account+"'";
				//bos
				query = "select a.id,a.C_STORE_ID||';'||b.Name Store  " +
						"from USERS a " +
						"left join C_STORE b on a.C_STORE_ID = b.ID " +
						"where a.ID ='"+account+"'";
				List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
				Map<String,Object> map =  list.get(0);
				store = map.get("STORE")==null?"":map.get("STORE").toString();
				return new PdaReturn("Y", store);
				//return new PdaReturn("Y", "Store;StoreName");
			} else if ("00".equals(result)) {
				return new PdaReturn("N", "用户名或密码错误！");
			} else {
				return new PdaReturn("N", result);
			}
			
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
	
	@RequestMapping(value="/stock")
	@ResponseBody	
	public Object stock(HttpSession session, HttpServletRequest request) {
		try{
			String skustyle = request.getParameter("skustyle");
			if(skustyle==null||skustyle.equals(""))
				skustyle="sku";
			
			String sku = request.getParameter("sku");
			String store = request.getParameter("store");
			
			String query = "",info="";
			
			if(skustyle.equals("sku"))
				query = "select * from TAccStock where sku = :sku  and store =:store";
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("sku", sku);
			params.put("store", store);
			
			List<Map<String,Object>> list = njdbcTemplate.queryForList(query, params);
			
			for (Map<String, Object> map : list) {
				info = info + ";" + map.get("store").toString()+","+map.get("sku").toString()+","+map.get("qty").toString();
			}
			
			return new PdaReturn("Y", info);
			
		}catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
		
	}
	
	@RequestMapping(value="/m_out")
	@ResponseBody	
	public Object m_out(HttpSession session, HttpServletRequest request) {
		try{
			String docno = request.getParameter("docno").toUpperCase();
			String query = "",info="";
			query = "select A.DOCNO,C.NAME M_PRODUCT_NAME,C.VALUE M_PRODUCT_VALUE,D.NO M_PRODUCT_ALIAS,B.QTY,B.QTYIN,B.QTYOUT "+  
					"from m_out a ,m_outitem b,m_product c,M_PRODUCT_ALIAS d "+
					"where A.ID = B.M_OUT_ID and b.M_PRODUCT_ID=c.id and b.M_PRODUCTALIAS_ID=d.ID "+
					"and A.DOCNO=:DOCNO and a.STATUS='1' ";
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("DOCNO", docno);
			
			List<Map<String,Object>> list = njdbcTemplate.queryForList(query, params);
			
			for (Map<String, Object> map : list) {
				info = info + ";" + map.get("M_PRODUCT_VALUE").toString()+","+map.get("M_PRODUCT_ALIAS").toString()+","+
									map.get("QTY").toString()+","+map.get("QTYOUT").toString()+",0, ";
			}
			info = StringUtils.removeStart(info, ";");
			
			return new PdaReturn("Y", info);
			
			
		}catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
		
	}
	
	@RequestMapping(value="/m_outitem_ac")
	@ResponseBody	
	public Object m_outitem_ac(HttpSession session, HttpServletRequest request) {
		try{
			String docno = request.getParameter("docno").toUpperCase();
			String tiaom = request.getParameter("tiaom").toUpperCase();
			String qty =   request.getParameter("qty");
			//插入数据
			String result = bospdaservice.m_outitem_ac(docno, tiaom,qty,"insert");
			if(result.startsWith("Y"))
				return new PdaReturn("Y", result);
			else
				return new PdaReturn("N", result);			
			
		}catch (Exception e) {
			logger.error("Exception: ", e);
			//return new ExceptionReturn(e);
			int start = StringUtils.indexOf(e.toString(), "ORA-");
			String result = StringUtils.substring(e.toString(), start);
			return new PdaReturn("N", result);
		}
		
	}
	
	@RequestMapping(value="/m_out_submit")
	@ResponseBody	
	public Object m_out_submit(HttpSession session, HttpServletRequest request) {
		try{
			String docno = request.getParameter("docno").toUpperCase();
			String chayyy = request.getParameter("chayyy");
			String list = request.getParameter("list");
			chayyy = chayyy==null?"":chayyy;
			list = list==null?"":list;
			
			//先执行数据的插入，无异常的情况下，再执行提交存储过程
			String result = "";
			if(list.equalsIgnoreCase(""))
				result = bospdaservice.m_out_submit(docno,chayyy);
			else{
				String[] listSplit = list.split(";");
				for (int i = 0; i < listSplit.length; i++) {
					String[] child = listSplit[i].split(",");
					if(child.length>0){
						String Sku = child[0];
						String Qty = child[1];
						try {
							String resultchild = bospdaservice.m_outitem_ac(docno, Sku,Qty,"update");
							if(!resultchild.startsWith("Y")){
								result = result+Sku+","+resultchild+";";
							}
						} catch (Exception e) {
							int start = StringUtils.indexOf(e.toString(), "ORA-");
							String ReturnStr = StringUtils.substring(e.toString(), start);
							ReturnStr = ReturnStr.replace(";", "；");
							ReturnStr = ReturnStr.replace(",", "，");
							
							result = result+Sku+","+ReturnStr+";";
							// TODO: handle exception
						}
						
					}
					
				}
				
				if(result.equalsIgnoreCase("")){
					result = bospdaservice.m_out_submit(docno,chayyy);
				}
			}
				
			if(result.startsWith("Y"))
				return new PdaReturn("Y", result);
			else
				return new PdaReturn("N", result);			
			
		}catch (Exception e) {
			logger.error("Exception: ", e);
			//return new ExceptionReturn(e);
			int start = StringUtils.indexOf(e.toString(), "ORA-");
			String ReturnStr ="";
			if(start>0){
				ReturnStr = StringUtils.substring(e.toString(), start);
			}else{
				ReturnStr = e.toString();
			}
			
			ReturnStr = ReturnStr.replace(";", "；");
			ReturnStr = ReturnStr.replace(",", "，");
			return new PdaReturn("N", ReturnStr);
		}
		
	}
	
	
	@RequestMapping(value="/m_in")
	@ResponseBody	
	public Object m_in(HttpSession session, HttpServletRequest request) {
		try{
			String docno = request.getParameter("docno").toUpperCase();
			String query = "",info="";
			query = "select distinct  A.DOCNO,C.NAME M_PRODUCT_NAME,C.VALUE M_PRODUCT_VALUE,D.NO M_PRODUCT_ALIAS,B.QTY,B.QTYIN,B.QTYOUT "+  
					"from m_in a ,m_initem b,m_product c,M_PRODUCT_ALIAS d "+
					"where A.ID = B.M_IN_ID and b.M_PRODUCT_ID=c.id and b.M_PRODUCTALIAS_ID=d.ID "+
					"and A.DOCNO=:DOCNO and a.STATUS='1' ";
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("DOCNO", docno);
			
			List<Map<String,Object>> list = njdbcTemplate.queryForList(query, params);
			
			for (Map<String, Object> map : list) {
				info = info + ";" + map.get("M_PRODUCT_VALUE").toString()+","+map.get("M_PRODUCT_ALIAS").toString()+","+
									map.get("QTYOUT").toString()+","+map.get("QTYIN").toString()+",0, ";
			}
			
			info = StringUtils.removeStart(info, ";");
			
			return new PdaReturn("Y", info);
			
		}catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
		
	}
	
	@RequestMapping(value="/m_initem_ac")
	@ResponseBody	
	public Object m_initem_ac(HttpSession session, HttpServletRequest request) {
		try{
			String docno = request.getParameter("docno").toUpperCase();
			String tiaom = request.getParameter("tiaom").toUpperCase();
			String qty =   request.getParameter("qty");
			//插入数据
			String result = bospdaservice.m_initem_ac(docno, tiaom,qty,"insert");
			if(result.startsWith("Y"))
				return new PdaReturn("Y", result);
			else
				return new PdaReturn("N", result);			
			
		}catch (Exception e) {
			logger.error("Exception: ", e);
			//return new ExceptionReturn(e);
			int start = StringUtils.indexOf(e.toString(), "ORA-");
			String result = StringUtils.substring(e.toString(), start);
			return new PdaReturn("N", result);
		}
		
	}
	
	@RequestMapping(value="/m_in_submit")
	@ResponseBody	
	public Object m_in_submit(HttpSession session, HttpServletRequest request) {
		try{
			String docno = request.getParameter("docno").toUpperCase();
			String chayyy = request.getParameter("chayyy");
			String list = request.getParameter("list");
			chayyy = chayyy==null?"":chayyy;
			list = list==null?"":list;
			
			//先执行数据的插入，无异常的情况下，再执行提交存储过程
			String result = "";
			if(list.equalsIgnoreCase(""))
				result = bospdaservice.m_in_submit(docno,chayyy);
			else{
				String[] listSplit = list.split(";");
				for (int i = 0; i < listSplit.length; i++) {
					String[] child = listSplit[i].split(",");
					if(child.length>0){
						String Sku = child[0];
						String Qty = child[1];
						try {
							String resultchild = bospdaservice.m_initem_ac(docno, Sku,Qty,"update");
							if(!resultchild.startsWith("Y")){
								result = result+Sku+","+resultchild+";";
							}
						} catch (Exception e) {
							int start = StringUtils.indexOf(e.toString(), "ORA-");
							String ReturnStr = StringUtils.substring(e.toString(), start);
							ReturnStr = ReturnStr.replace(";", "；");
							ReturnStr = ReturnStr.replace(",", "，");
							
							result = result+Sku+","+ReturnStr+";";
							// TODO: handle exception
						}
						
					}
					
				}
				
				if(result.equalsIgnoreCase("")){
					result = bospdaservice.m_in_submit(docno,chayyy);
				}
			}
				
			if(result.startsWith("Y"))
				return new PdaReturn("Y", result);
			else
				return new PdaReturn("N", result);			
			
		}catch (Exception e) {
			logger.error("Exception: ", e);
			//return new ExceptionReturn(e);
			int start = StringUtils.indexOf(e.toString(), "ORA-");
			String ReturnStr ="";
			if(start>0){
				ReturnStr = StringUtils.substring(e.toString(), start);
			}else{
				ReturnStr = e.toString();
			}
			
			ReturnStr = ReturnStr.replace(";", "；");
			ReturnStr = ReturnStr.replace(",", "，");
			return new PdaReturn("N", ReturnStr);
		}
		
	}
	
	@RequestMapping(value="/mastercode")
	@ResponseBody	
	public Object mastercode(HttpSession session, HttpServletRequest request) {
		try {
			String query = "",ReturnStr="";
			String docno = request.getParameter("docno").toUpperCase();
			
			query=  "select A.ID,B.MASTERCODE "+
					"from B_BOXNOMASTERCODE a "+
					"left join C_MASTERCODE b on a.C_MASTERCODE_ID = b.ID "+
					"where exists( "+
					"   select  'x' from B_BOXNOCHK f where  A.B_PO_BOXNO_ID=F.ID and f.status!='2' and f.boxno='"+docno+"' "+
					") ";
			
			List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
			for (Map<String, Object> map : list) {
				ReturnStr = ReturnStr+map.get("ID").toString()+","+map.get("MASTERCODE").toString()+";";
			}
			
			return new PdaReturn("Y", ReturnStr);
			
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new PdaReturn("N", e.toString());
			// TODO: handle exception
		}
		
	}
	
	@RequestMapping(value="/boxnoitem")
	@ResponseBody	
	public Object boxnoitem(HttpSession session, HttpServletRequest request) {
		try {
			String query = "",ReturnStr="";
			String docno = request.getParameter("docno").toUpperCase();
			
			query=  "select A.ID,B.NAME M_PRODUCT_NAME,C.NO M_PRODUCT_ALIAS_NO,D.VALUE1 COLORNAME,D.VALUE2 SIZENAME ,A.QTY,nvl(E.QTY,0) QTY_QR "+
					"from b_po_boxitem a  "+
					"left join m_product b on A.M_PRODUCT_ID = b.id "+ 
					"left join M_PRODUCT_ALIAS c on A.M_PRODUCTALIAS_ID = C.ID "+ 
					"left join M_ATTRIBUTESETINSTANCE d on A.M_ATTRIBUTESETINSTANCE_ID = D.ID "+
					"left join B_BOXNOCHKPDAITEM e on A.B_PO_BOXNO_ID = E.B_PO_BOXNO_ID and A.M_PRODUCTALIAS_ID = E.M_PRODUCTALIAS_ID "+
					"where exists( "+
					"   select  'x' from B_BOXNOCHK f where  A.B_PO_BOXNO_ID=F.ID and f.status!='2' and f.boxno='"+docno+"' "+
					")";
			
			List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
			for (Map<String, Object> map : list) {
				ReturnStr = ReturnStr+map.get("ID").toString()+","+map.get("M_PRODUCT_NAME").toString()+","+
							map.get("M_PRODUCT_ALIAS_NO").toString()+","+map.get("COLORNAME").toString()+","+map.get("SIZENAME").toString()+","+
							map.get("QTY").toString()+","+map.get("QTY_QR").toString()+";";
			}
			
			return new PdaReturn("Y", ReturnStr);
			
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new PdaReturn("N", e.toString());
			// TODO: handle exception
		}
	}
	
	@RequestMapping(value="/mastercode_save")
	@ResponseBody	
	public Object mastercode_save(HttpSession session, HttpServletRequest request) {
		try{
			int continue_ornot = 1 ;
			String query = "",mastercode="",insert="",delete="";
			String docno = request.getParameter("docno").toUpperCase();
			String listTmp = request.getParameter("list").toUpperCase();
			String error = "";
			
			String[] list = listTmp.split(";");
			for (int i = 0; i < list.length; i++) {
				//执行插入存储过程
				query = "select get_sequences('B_BOXNOMASTERCODE') ID from dual ";
				String b_boxnomastercode_id = jdbcTemplate.queryForObject(query, String.class);
				mastercode = list[i].toString();
				
				insert = "insert into B_BOXNOMASTERCODE(ID, AD_CLIENT_ID, AD_ORG_ID, B_PO_BOXNO_ID, C_MASTERCODE_ID, MODIFIERID, CREATIONDATE) "+ 
						 "select '"+b_boxnomastercode_id+"',37,27,a.ID B_PO_BOXNO_ID , b.ID C_MASTERCODE_ID ,893 ,sysdate  "+
						 "from B_BOXNOCHK a ,C_MASTERCODE b where a.boxno ='"+docno+"' and b.MASTERCODE = '"+mastercode+"' "+ 
						 "and not exists(select 'x' from B_BOXNOMASTERCODE c where a.id=c.B_PO_BOXNO_ID and b.id=c.C_MASTERCODE_ID )"; 
				//out.print("Test:"+insert);
				int count = jdbcTemplate.update(insert);
				
				if(count>0){
					try{
						final String pid = b_boxnomastercode_id;
						String procedure = "{call b_boxnomastercode_ac(?)}";
						@SuppressWarnings("unchecked")
						Map<String,Object> MAP_PRO_TMP = (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {  
				            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
				                cs.setString(1, pid);
				                cs.execute();
				                Map<String,Object> map = new HashMap<String, Object>();  
				                map.put("r_message", "");
				                return map;
				            }
				        });
					}catch(Exception e){
						delete = "delete from B_BOXNOMASTERCODE where id ='"+b_boxnomastercode_id+"'";
						jdbcTemplate.update(delete);
						continue_ornot = 0;
						error = mastercode;
						break;
					}
				}
			}
			
		   if(continue_ornot==1){
				query = "select id  from B_BOXNOCHK where boxno='"+docno+"' ";
				final String pid = jdbcTemplate.queryForObject(query, String.class);
				
				String procedure = "{call b_boxnochk_submit(?,?,?)}";
				@SuppressWarnings("unchecked")
				Map<String,Object> MAP_PRO_TMP = (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {  
		            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
		                cs.setString(1, pid);
		                cs.registerOutParameter(2, OracleTypes.NUMBER);	
		                cs.registerOutParameter(3, OracleTypes.VARCHAR);
		                cs.execute();
		                Map<String,Object> map = new HashMap<String, Object>();  
		                map.put("r_message", "");
		                return map;
		            }
		        });
				return new PdaReturn("Y", "单据提交成功");
			}else{
				return new PdaReturn("N", error+"单据提交失败,请重试");
			}
			
		}catch(Exception e){
			logger.error("Exception: ", e);
			return new PdaReturn("N", e.toString());
		}
	}
	
	@RequestMapping(value="/skudownload")
	@ResponseBody	
	public Object skudownload(HttpSession session, HttpServletRequest request) {
		try {
			String query = "",line="";
			String Store = request.getParameter("store");
			
			query = "select B.NAME Style ,B.VALUE StyleName,A.NO Sku,C.VALUE1 Clr,C.VALUE2 SizeName,0 Qty "+
					"from M_PRODUCT_ALIAS a,m_product b ,M_ATTRIBUTESETINSTANCE c "+
					"where A.M_PRODUCT_ID= b.id and A.M_ATTRIBUTESETINSTANCE_ID = C.ID and A.ISACTIVE='Y' and B.ISACTIVE='Y'";
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("Store", Store);
			
			List<Map<String,Object>> list = njdbcTemplate.queryForList(query, params);
			
			FileOperateUtil fou = new FileOperateUtil();
			String savePath = request.getSession().getServletContext().getRealPath("/resources/download");
			String filename = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
			String finalPath = savePath +File.separator+"Sku.txt";
			String finalPathZip = savePath +File.separator+filename+".zip";
			
			File downloadfile = new File(finalPath);
			if (!downloadfile.getParentFile().exists()) {
				downloadfile.getParentFile().mkdirs();
			}
			FileOutputStream fos=null; 
			fos =  new FileOutputStream(downloadfile);
			line ="Style,StyleName,Sku,Clr,SizeName,Qty\r\n";
			fos.write(line.getBytes("GBK"));
			
			for (Map<String, Object> map : list) {
				//生成文本
				line = map.get("Style").toString()+","+map.get("StyleName").toString()+","+map.get("Sku").toString()+","+map.get("Clr").toString()+","+map.get("SizeName").toString()+","+map.get("Qty").toString()+"\r\n";
				fos.write(line.getBytes("GBK"));
			}
			fos.close();
			
			//压缩文件为 yyyyMMddHHmmss.zip
			FileOutputStream foszip=new FileOutputStream(finalPathZip);		
			ZipOutputStream zosm = new ZipOutputStream(foszip);
			fou.compressionFiles(zosm, downloadfile, "");
			zosm.setEncoding("GBK"); //解决压缩中文乱码问题
			zosm.close();
			foszip.close();
			
			downloadfile.delete();
			
			return new PdaReturn("Y", filename+".zip");
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: ", e);
			return new PdaReturn("N", "异常");
		}
	}
	
	@RequestMapping(value="/m_inventoryskudownload")
	@ResponseBody	
	public Object m_inventoryskudownload(HttpSession session, HttpServletRequest request) {
		try {
			String query = "",line="";
			String store = request.getParameter("store");
			
			query = "select A.DOCNO,C.NO SKU " +
					"from M_inventory a,M_inventoryItem b,M_PRODUCT_ALIAS c  "+
					"where A.ID=b.M_INVENTORY_ID and b.M_PRODUCTALIAS_ID = c.ID and A.STATUS='1' and a.isactive='Y' and a.c_store_id=:store ";
			
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("store", store);
			
			List<Map<String,Object>> list = njdbcTemplate.queryForList(query, params);
			
			FileOperateUtil fou = new FileOperateUtil();
			String savePath = request.getSession().getServletContext().getRealPath("/resources/download");
			String filename = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
			String finalPath = savePath +File.separator+"m_inventorysku.txt";
			String finalPathZip = savePath +File.separator+filename+".zip";
			
			File downloadfile = new File(finalPath);
			if (!downloadfile.getParentFile().exists()) {
				downloadfile.getParentFile().mkdirs();
			}
			FileOutputStream fos=null; 
			fos =  new FileOutputStream(downloadfile);
			line ="DOCNO,SKU\r\n";
			fos.write(line.getBytes("GBK"));
			
			for (Map<String, Object> map : list) {
				//生成文本
				line = map.get("DOCNO").toString()+","+map.get("SKU").toString()+"\r\n";
				fos.write(line.getBytes("GBK"));
			}
			fos.close();
			
			//压缩文件为 yyyyMMddHHmmss.zip
			FileOutputStream foszip=new FileOutputStream(finalPathZip);		
			ZipOutputStream zosm = new ZipOutputStream(foszip);
			fou.compressionFiles(zosm, downloadfile, "");
			zosm.setEncoding("GBK"); //解决压缩中文乱码问题
			zosm.close();
			foszip.close();
			
			downloadfile.delete();
			
			return new PdaReturn("Y", filename+".zip");
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: ", e);
			return new PdaReturn("N", "异常");
		}
	}
	
	@RequestMapping(value="/m_inventory_datadown")
	@ResponseBody	
	public Object m_inventory_datadown(HttpSession session, HttpServletRequest request) {
		try {
			String query= "",delete="",update="",insert="",ReturnStr="",ReturnMproduct="";
			String store = request.getParameter("store").toString();
			
			query = "select A.DOCNO,A.BILLDATE,b.DESCRIPTION,C.NAME C_STORE,A.STATUS,a.DOCTYPE "+ 
					"from M_INVENTORY a  "+
					"left join ( "+
					"    select b2.value,B2.DESCRIPTION from AD_LIMITVALUE_GROUP b1,AD_LIMITVALUE b2 "+ 
					"    where B1.ID = B2.AD_LIMITVALUE_GROUP_ID "+
					") b on A.DOCTYPE = b.value "+
					"left join c_store c on A.C_STORE_ID = C.ID "+
					"where A.STATUS='1' and a.isactive='Y' and a.c_store_id='"+store+"'";
			List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
			for (Map<String, Object> map : list) {
				ReturnStr = ReturnStr+map.get("DOCNO").toString()+","+map.get("BILLDATE").toString()+","+
							map.get("DESCRIPTION").toString()+","+map.get("C_STORE").toString()+","+map.get("STATUS").toString()+";";
				
				//若为历史抽盘，则顺带下载款号信息
				if(map.get("DOCTYPE").toString().equalsIgnoreCase("IHS")){
					query = "select to_char(get_fitler_sql(product_filter)) from M_INVENTORY where docno='"+map.get("DOCNO").toString()+"'";
					String product_filter = jdbcTemplate.queryForObject(query, String.class);
					query = "select name from m_product where id "+product_filter;
					List<Map<String,Object>> listProduct = jdbcTemplate.queryForList(query);
					for (Map<String, Object> map2 : listProduct) {
						ReturnMproduct = ReturnMproduct+map.get("DOCNO").toString()+","+map2.get("name").toString()+";";
					}
				}
			}
			
			logger.info("m_inventory_datadown:"+query);			
			
			return new PdaReturn("Y",ReturnStr,ReturnMproduct);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: ", e);
			return new PdaReturn("N", "异常");
		}
	}
	
	@RequestMapping(value="/m_inventory_dataupload")
	@ResponseBody	
	public Object m_inventory_dataupload(HttpSession session, HttpServletRequest request) {
		try {
			String query= "",delete="",update="",insert="",ReturnStr="",create="";
			String store="",opr="000",opdate="",account="",deviceid="",devicename="",rowdetail="",rownum="",status="Y",code="";
			
			store  = request.getParameter("store");
			account = request.getParameter("account");
			deviceid = request.getParameter("deviceid");
			devicename = request.getParameter("devicename");
			rowdetail = request.getParameter("rowdetail");
			rownum = request.getParameter("rownum");			
			code  = request.getParameter("code");
			
			String[] rows = rowdetail.split(";"); //每一行的数据
			
			if(rows.length!=Integer.parseInt(rownum)){ //数据上传不一致
				status = "N";
				ReturnStr = "上传失败,请重试";
			}else{
				/*//插入一张临时表
				query = "select count(*) from tabs where table_name ='M_INVENTORY_TMP'";
				int count = jdbcTemplate.queryForInt(query);
				if(count==0){
					//创建表 B_PO_BOXITEM_BACK
					create ="CREATE TABLE M_INVENTORY_TMP "+
							"( "+
							"  Docno     NVARCHAR2(100), "+
							"  location  NVARCHAR2(100), "+
							"  Sku       NVARCHAR2(100), "+
							"  qty       NUMBER(10), "+
							"  addwho    NVARCHAR2(100), "+
							"  addtime   NVARCHAR2(100), "+
							"  status    NVARCHAR2(100) "+
							")";
					jdbcTemplate.execute(create);
				}
				String addtime = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
				
				for (String str : rows) {
					insert = "insert into M_INVENTORY_TMP(docno,location,sku,qty,addwho,addtime,status) " +
							 "select :docno,:location,:sku,:qty,:addwho,:addtime,:status from dual ";
					String[] row = str.split(",");
					Map<String,Object> param = new HashMap<String, Object>();
					if(row.length>1){
						param.clear();
						param.put("docno", row[0]);
						param.put("location", row[1]);
						param.put("sku", row[2]);
						param.put("qty", row[3]);
						param.put("account", account);
						param.put("addtime", addtime);
						
						jdbcTemplate.update(insert, param);
					}
				}*/
				
				insert ="insert into M_INVENTORY_SHELFITEM(ID, AD_CLIENT_ID, AD_ORG_ID, M_INVENTORY_ID, M_PRODUCT_ID, QTY, M_ATTRIBUTESETINSTANCE_ID, OWNERID, CREATIONDATE, ISACTIVE, SHELFNO, M_PRODUCTALIAS_ID, INTSCODE) " +
						"select get_sequences('M_INVENTORY_SHELFITEM') ID,'37','27',A.ID M_INVENTORY_ID,B.M_PRODUCT_ID,:QTY, " +
						"B.M_ATTRIBUTESETINSTANCE_ID,:OWNERID,sysdate CREATIONDATE,'Y' ISACTIVE,:SHELFNO,B.ID M_PRODUCTALIAS_ID,INTSCODE " +
						"from M_INVENTORY a " +
						"left join M_PRODUCT_ALIAS b on B.NO = :NO " +
						"where A.DOCNO =:DOCNO ";
								
				for (String str : rows) {
					String[] row = str.split(",");
					Map<String,Object> param = new HashMap<String, Object>();
					param.clear();
					param.put("DOCNO", row[0]);
					param.put("SHELFNO", code+"#"+row[1]);
					param.put("NO", row[2]);
					param.put("QTY", row[3]);
					param.put("OWNERID", account);
					
					njdbcTemplate.update(insert, param);									
				}
				
				ReturnStr = "上传成功";
				
			}
			
			return new PdaReturn(status,ReturnStr);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: ", e);
			return new PdaReturn("N", "异常");
		}
	}
	
	@RequestMapping(value="/m_xiangck")
	@ResponseBody	
	public Object m_xiangck(HttpSession session, HttpServletRequest request) {
		try {
			String query= "",delete="",update="",insert="",result="";
			String account = request.getParameter("account");
			query = "select get_sequences('M_XIANGCK')||','||get_sequenceno('MXI','37') from dual";
			String m_xiangck = jdbcTemplate.queryForObject(query, String.class);
			String id = m_xiangck.split(",")[0];
			String docno = m_xiangck.split(",")[1];
			
			insert ="insert into M_XIANGCK(ID, AD_CLIENT_ID, AD_ORG_ID, BILLDATE, DESCRIPTION, OWNERID, MODIFIERID, CREATIONDATE, MODIFIEDDATE, ISACTIVE, STATUS, DOCNO) "+
					"select :ID,'37','27',to_char(sysdate,'yyyymmdd') BILLDATE,'PDA' DESCRIPTION,:ACCOUNT,:ACCOUNT,sysdate,sysdate,'Y' ISACTIVE,'1' STATUS,:DOCNO "+
					"from dual ";
			
			Map<String,Object> Param = new HashMap<String, Object>();
			Param.put("ID", id);
			Param.put("DOCNO", docno);
			Param.put("ACCOUNT", account);
			if(njdbcTemplate.update(insert, Param)>0)
				return new PdaReturn("Y",docno);
			else
				return new PdaReturn("N","更新失败");
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: ", e);
			return new PdaReturn("N", "异常");
		}
	}
	

	@RequestMapping(value="/m_xiangckitem")
	@ResponseBody	
	public Object m_xiangckitem(HttpSession session, HttpServletRequest request) {
		String query= "",delete="",update="",insert="",result="";
		try {
			
			String docno= request.getParameter("docno");
			docno=docno==null?"":docno;

			String account = request.getParameter("account");
			
			Map<String,Object> Param = new HashMap<String, Object>();
			Param.put("DOCNO", docno);
			Param.put("ACCOUNT", account);
						
			query = "select c.BOXNO "+
					"from M_XIANGCK a ,M_XIANGCKITEM b,B_PO_BOXNO c " +
					"where a.id=b.M_XIANGCK_ID and b.B_PO_BOXNO_ID=c.id and a.docno =:DOCNO ";
			
			List<Map<String,Object>> list = njdbcTemplate.queryForList(query,Param);
			for (Map<String, Object> map : list) {
				result = result+map.get("BOXNO").toString()+";";
			}
			
			return new PdaReturn("Y",result);
									
		} catch (Exception e) {
			int start = StringUtils.indexOf(e.toString(), "ORA-");
			result = StringUtils.substring(e.toString(), start);
			return new PdaReturn("N", result);
		}
	}
	
	@RequestMapping(value="/m_xiangckitem_acm")
	@ResponseBody	
	public Object m_xiangckitem_acm(HttpSession session, HttpServletRequest request) {
		try {
			String query= "",delete="",update="",insert="",result="";
			String boxno= request.getParameter("boxno");
			boxno=boxno==null?"":boxno;
			String docno= request.getParameter("docno");
			docno=docno==null?"":docno;
			String account = request.getParameter("account");
			
			query = "select get_sequences('M_XIANGCKITEM') from dual";			
			String id = jdbcTemplate.queryForObject(query, String.class);
			
			try {
				insert ="insert into M_XIANGCKITEM(ID, AD_CLIENT_ID, AD_ORG_ID, M_XIANGCK_ID, B_PO_BOXNO_ID, OWNERID, MODIFIERID, CREATIONDATE, MODIFIEDDATE, ISACTIVE) "+
						"select :ID,'37','27',a.id M_XIANGCK_ID,b.id M_XIANGCK_ID,:ACCOUNT,:ACCOUNT,sysdate,sysdate,'Y' ISACTIVE "+
						"from M_XIANGCK a ,B_PO_BOXNO b where a.DOCNO = :DOCNO and b.BOXNO=:BOXNO and a.STATUS!='2' ";
				
				Map<String,Object> Param = new HashMap<String, Object>();
				Param.put("ID", id);
				Param.put("DOCNO", docno);
				Param.put("BOXNO", boxno);
				Param.put("ACCOUNT", account);
				
				if(njdbcTemplate.update(insert,Param)>0){
					//执行存储过程
					final String pid = id;
					String procedure = "{call M_XIANGCKITEM_ACM(?)}";
					@SuppressWarnings("unchecked")
					Map<String,Object> MAP_PRO_TMP = (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {
			            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
			                cs.setString(1, pid);
			                cs.execute();
			                Map<String,Object> map = new HashMap<String, Object>();  
			                map.put("r_message", "");
			                return map;
			            }
			        });
					
					return new PdaReturn("Y","");
					
				}else{
					return new PdaReturn("N","无记录更新");
				}
				
			} catch (Exception e) {
				delete ="delete from M_XIANGCKITEM where id= :ID";
				Map<String,Object> Param = new HashMap<String, Object>();
				Param.put("ID", id);
				njdbcTemplate.update(delete,Param);
				
				int start = StringUtils.indexOf(e.toString(), "ORA-");
				result = StringUtils.substring(e.toString(), start);
				return new PdaReturn("N", result);
				// TODO: handle exception
			}
									
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: ", e);
			return new PdaReturn("N", "异常");
		}
	}
	
	
	@RequestMapping(value="/m_xiangckitem_del")
	@ResponseBody	
	public Object m_xiangckitem_del(HttpSession session, HttpServletRequest request) {
			
		String query= "",delete="",update="",insert="",result="";
		
		try {	
			String boxno= request.getParameter("boxno");
			boxno=boxno==null?"":boxno;
			
			delete ="delete from m_xiangckitem a where exists( " +
					"select 'x' from B_PO_BOXNO b where a.B_PO_BOXNO_ID = b.id  and b.boxno = :BOXNO ) and " +
					"exists(" +
					"select 'x' from m_xiangck c where a.M_XIANGCK_ID=c.id and c.status!='2' " +
					")";
			
			Map<String,Object> Param = new HashMap<String, Object>();
			Param.put("BOXNO", boxno);
			
			if(njdbcTemplate.update(delete, Param)>0)
				return new PdaReturn("Y", "");
			else
				return new PdaReturn("N", "");
											
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: ", e);
			int start = StringUtils.indexOf(e.toString(), "ORA-");
			result = StringUtils.substring(e.toString(), start);
			return new PdaReturn("N", result);
		}
	}
	
	
	@RequestMapping(value="/m_xiangckitem_submit")
	@ResponseBody	
	public Object m_xiangckitem_submit(HttpSession session, HttpServletRequest request) {
		String query= "",delete="",update="",insert="",result="";
		
		try {	
			String docno= request.getParameter("docno");
			docno=docno==null?"":docno;
			query ="select id from M_XIANGCK where docno = :DOCNO and status!='2' ";
			Map<String,Object> Param = new HashMap<String, Object>();
			Param.put("DOCNO", docno);
			
			String id = njdbcTemplate.queryForObject(query, Param, String.class);
			//执行存储过程
			final String pid = id;
			String procedure = "{call M_XIANGCK_SUBMIT(?,?,?)}";
			@SuppressWarnings("unchecked")
			Map<String,Object> MAP_PRO_TMP = (HashMap<String, Object>) jdbcTemplate.execute(procedure,new CallableStatementCallback() {
	            public Object doInCallableStatement(CallableStatement cs)throws SQLException,DataAccessException {  
	                cs.setString(1, pid);
	                cs.registerOutParameter(2, OracleTypes.NUMBER);	
	                cs.registerOutParameter(3, OracleTypes.VARCHAR);
	                cs.execute();
	                Map<String,Object> map = new HashMap<String, Object>();  
	                map.put("r_message", "");
	                return map;
	            }
	        });
			
			return new PdaReturn("Y", result);
											
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: ", e);
			int start = StringUtils.indexOf(e.toString(), "ORA-");
			result = StringUtils.substring(e.toString(), start);
			return new PdaReturn("N", result);
		}
	}
	
	@RequestMapping(value = "/timesyn")
	@ResponseBody
	public Object timesysn(HttpSession session, HttpServletRequest request){
		String sql="select to_char(sysdate-8/24,'YYYYMMDDHH24MISS') time from dual  ";
		return jdbcTemplate.queryForList(sql).get(0);				
	}
	
	@RequestMapping(value="/datadownload")
	@ResponseBody	
	public Object datadownload(HttpSession session, HttpServletRequest request) {
		try {
			String query= "",delete="",update="",insert="",result="";
			
			
			return new PdaReturn("Y","");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Exception: ", e);
			return new PdaReturn("N", "异常");
		}
	}
	
	@RequestMapping(value="/dataupload")
	@ResponseBody	
	public Object dataupload(HttpSession session, HttpServletRequest request) {
	
		return null;
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
	
	private String encrypt(String data, String salt) {
		// 可以更换算法:sha512Hex
		return DigestUtils.md5Hex(data + "{" + salt + "}");
	}
	
}
