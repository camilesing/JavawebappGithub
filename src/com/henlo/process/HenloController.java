package com.henlo.process;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nds.query.QueryEngine;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.record.formula.IntPtg;
import org.json.simple.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import com.alibaba.fastjson.JSON;

import com.authority.dao.DataSourceDao;

public class HenloController {
	public QueryEngine queryengine;
	private static DataSourceDao ds;
	private static ApplicationContext ctx;
	
	public HenloController(){
		
	}	
	public HenloController(ApplicationContext ctx){
		ds = ctx.getBean(DataSourceDao.class); 
	}
	public String handle(String tablename,String b_plan_id,String param){
		queryengine = QueryEngine.getInstance();
		String message = "失败";		
		String sty_stock = "";
		JSONObject json = new JSONObject();
		try{		
			
			/*String insert_ = "insert into HENLOCONTROLLER(ID, SQLCOMMAND, ADDTIME ) select sys_guid(),'BEGIN……',sysdate from dual";
			queryengine.executeUpdate(insert_);*/
			
			if(tablename.toUpperCase().equals("M_ISSUE_APPLY")&&param.trim().length()>1){
				int total_begin = 0,total_end = 0;
				Map<String,Object> map_evt = (Map<String, Object>) JSON.parseArray("["+param.trim()+"]").get(0);
								
				
				Map<String,Object> map_param = JSON.parseObject(map_evt.get("param").toString());
				
				Map<String,Object> map_param_box = JSON.parseObject(map_evt.get("param_box").toString());
				
	//			System.out.println(map_param.get("m_item").toString());
				
				List list_param_m_item = JSON.parseArray(map_param.get("m_item").toString());
				
				List list_param_box_m_item = JSON.parseArray(map_param_box.get("m_item").toString());
				
				String update = "",insert ="",query="";
				
				//检测是否已经被提交
				query = "select count(*) cnt from M_ISSUE_APPLY where status=2 and id='"+b_plan_id+"'";
				String cnt = queryengine.doQueryOne(query).toString();
				if(!cnt.equals("0")){
					message = "该单据已经提交！请不要重复提交";
				}else{
					
					for (Object obj_param : list_param_m_item) {
						Map<String,Object> map_param_m_item_child = (Map<String, Object>) obj_param;
		//				System.out.println("docno:"+map_param_m_item_child.get("docno"));
						String qty_ady = map_param_m_item_child.get("qty_ady").toString().trim();
						String m_product_alias_id = map_param_m_item_child.get("m_product_alias_id").toString(); //实际该值为表ID
						String store = map_param_m_item_child.get("store").toString();
						String sty =  map_param_m_item_child.get("sty").toString();
						String color = map_param_m_item_child.get("color").toString();
						String docNo = map_param_m_item_child.get("docNo").toString(); //订单编号
						String b_so_matchsize_id = map_param_m_item_child.get("b_so_matchsize_id").toString(); //配码ID
						String stycolorstoredocNomatchsize = sty+color+store+docNo+b_so_matchsize_id;
						
						total_begin ++ ;
						for (Object obj_param_box : list_param_box_m_item) { // 箱数数据信息
							Map<String,Object> map_param_box_m_item_child = (Map<String, Object>) obj_param_box;
							//款号+色号+经销商+订单号+配码ID
							String stycolorstoredocNomatchsize_box = map_param_box_m_item_child.get("sty").toString()+map_param_box_m_item_child.get("color").toString()+map_param_box_m_item_child.get("store").toString()+map_param_box_m_item_child.get("docNo").toString()+map_param_box_m_item_child.get("b_so_matchsize_id").toString();
							
							String boxqty = map_param_box_m_item_child.get("qty_ady").toString().trim();
							
							if(stycolorstoredocNomatchsize.equals(stycolorstoredocNomatchsize_box)){
								
								update ="update M_ISSUE_APPLYITEM a set A.QTY='"+qty_ady+"' " +
										"where  A.M_ISSUE_APPLY_ID= '"+b_plan_id+"' and A.id='"+m_product_alias_id+"' ";							
								
								if(queryengine.executeUpdate(update)>0)
									total_end++;							
								
								//更新箱数 款号、色号、经销商、订单号
								update ="update M_ISSUE_APPLYITEM a set a.BOXQTY= '" + boxqty+"' "+
										"where  A.M_ISSUE_APPLY_ID= '"+b_plan_id+"' and " +
										"exists( select 'x' from C_CUSTOMER b where A.C_CUSTOMER_ID=B.ID and B.NAME='"+store+"') and " +
										"exists( select 'x' from M_PRODUCT b where A.M_PRODUCT_ID=B.ID and B.NAME='"+sty+"' ) and " +
										"exists( select 'x' from m_attributesetinstance c where a.m_attributesetinstance_id = C.ID and c.value1_code||c.value1='"+color+"') and " +
										"exists( select 'x' from B_SO d where a.b_so_id=d.id and d.docno='"+docNo+"') and "+
										"a.B_SO_MATCHSIZE_ID='"+b_so_matchsize_id+"'";
								queryengine.executeUpdate(update);
								
								break;
							}
						}
					}
									
					message = "更新成功";	
				}
				
			} else if (tablename.toUpperCase().equals("M_ISSUE_TASKITEM")&&param.trim().length()>1) {
				int total_begin = 0,total_end = 0;
				Map<String,Object> map_evt = (Map<String, Object>) JSON.parseArray("["+param.trim()+"]").get(0);
				Map<String,Object> map_param = JSON.parseObject(map_evt.get("param").toString());
				Map<String,Object> map_param_box = JSON.parseObject(map_evt.get("param_box").toString());
				
				List list_param_m_item = JSON.parseArray(map_param.get("m_item").toString());
				
				List list_param_box_m_item = JSON.parseArray(map_param_box.get("m_item").toString());
				
				String update = "",insert ="",query="";
				
				//检测是否已经被提交
				query = "select count(*) cnt from M_ISSUE_TASK where status=2 and id='"+b_plan_id+"'";
				String cnt = queryengine.doQueryOne(query).toString();
				if(!cnt.equals("0")){
					message = "该单据已经提交！请不要重复提交";
				}else{
					for (Object obj_param : list_param_m_item) {
						Map<String,Object> map_param_m_item_child = (Map<String, Object>) obj_param;
						String qty_ady = map_param_m_item_child.get("qty_ady").toString().trim();
						String m_product_alias_id = map_param_m_item_child.get("m_product_alias_id").toString();
						String store = map_param_m_item_child.get("store").toString();
						String sty =  map_param_m_item_child.get("sty").toString();
						String color = map_param_m_item_child.get("color").toString();
						String docNo = map_param_m_item_child.get("docNo").toString(); //订单编号
						String b_so_matchsize_id = map_param_m_item_child.get("b_so_matchsize_id").toString(); //配码ID
						String stycolorstoredocNomatchsize = sty+color+store+docNo+b_so_matchsize_id;
						
						total_begin ++ ;
						for (Object obj_param_box : list_param_box_m_item) { // 箱数数据信息
							Map<String,Object> map_param_box_m_item_child = (Map<String, Object>) obj_param_box;
							//款号+色号+经销商+订单号+配码ID
							String stycolorstoredocNomatchsize_box = map_param_box_m_item_child.get("sty").toString()+map_param_box_m_item_child.get("color").toString()+map_param_box_m_item_child.get("store").toString()+map_param_box_m_item_child.get("docNo").toString()+map_param_box_m_item_child.get("b_so_matchsize_id").toString(); 
							
							String boxqty = map_param_box_m_item_child.get("qty_ady").toString().trim();
							
							if(stycolorstoredocNomatchsize.equals(stycolorstoredocNomatchsize_box)){
								update ="update M_ISSUE_TASKITEM a set A.QTY='"+qty_ady+"' " +
										"where  A.M_ISSUE_TASK_ID= '"+b_plan_id+"' and A.id='"+m_product_alias_id+"' ";							
								
								if(queryengine.executeUpdate(update)>0)
									total_end++;
								
								//更新箱数      款号、色号、经销商、订单号
								update ="update M_ISSUE_TASKITEM a set a.BOXQTY= '" + boxqty+"' "+
										"where  A.M_ISSUE_TASK_ID= '"+b_plan_id+"' and " +
										"exists( select 'x' from C_CUSTOMER b where A.C_CUSTOMER_ID=B.ID and B.NAME='"+store+"') and " +
										"exists( select 'x' from M_PRODUCT b where A.M_PRODUCT_ID=B.ID and B.NAME='"+sty+"' ) and " +
										"exists( select 'x' from m_attributesetinstance c where a.m_attributesetinstance_id = C.ID and c.value1_code||c.value1='"+color+"') and "+
										"exists( select 'x' from B_SO d where a.b_so_id=d.id and d.docno='"+docNo+"') and " +
										"a.B_SO_MATCHSIZE_ID='"+b_so_matchsize_id+"'";
								queryengine.executeUpdate(update);
								
								break;
							}
						}
					}
					 message = "更新成功";
				}
			}
		}catch(Exception e){
			
			message=message+e.toString();
		}
		
		json.put("message", message);
		json.put("sty_stock", sty_stock);
		
		return json.toString();
	}
	
	
	public static void main(String[] args) {
		JSONObject evt = new JSONObject();
		evt.put("callbackEvent", "DO_SAVE");
		evt.put("table", "m_allot");
		
		JSONObject param = new JSONObject();
		param.put("type","sav");
		param.put("m_allot_id","208");
		
		List param_m_item = new ArrayList();
		for(int i=0;i<=2;i++){
			JSONObject param_m_item_child = new JSONObject();
			param_m_item_child.put("qty_ady", "1");
			param_m_item_child.put("m_product_alias_id", "815594");
			param_m_item_child.put("docno", "SO1308130000004");
			param_m_item.add(param_m_item_child);
			
			param.put("m_item", param_m_item);
		}	
		
		evt.put("param", param.toJSONString());		
		
		String str = evt.toString();
		
		System.out.println(str);
		
		Map<String,Object> map_evt = (Map<String, Object>) JSON.parseArray("["+str+"]").get(0);
		
		Map<String,Object> map_param = JSON.parseObject(map_evt.get("param").toString());
		
		System.out.println(map_param.get("m_item").toString());
		
		List list_param_m_item = JSON.parseArray(map_param.get("m_item").toString());
		
		for (Object object : list_param_m_item) {
			Map<String,Object> map_param_m_item_child = (Map<String, Object>) object;
			System.out.println("docno:"+map_param_m_item_child.get("docno"));
		}
		
		
		
		
		/*ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:config/spring/spring-common.xml");
		HenloController ad = new HenloController(ctx);
		JdbcTemplate jt_oracle_henlo = ds.getJt_oracle_henlo();
		
		String b_plan_id = "1312"; //模拟ID
		String product_name = "";
		String color="";
		
		JSONObject data =new JSONObject();		
		JSONObject jsonResult = new JSONObject();
		JSONObject jsonResult_data = new JSONObject();		
				
		jsonResult.put("Billdateend","20130808");
		jsonResult.put("Product_Filter","");
		jsonResult.put("saletypeid","");
		jsonResult.put("status","1");
		jsonResult.put("Billdatebeg","20130808");
		jsonResult.put("data",jsonResult_data);
		jsonResult.put("isarray", "Y");
		jsonResult.put("searchord", "包含(SO1308080000005,SO1308080000004)");
		jsonResult.put("p_status", "-1");
		jsonResult.put("p_display", "0");
		jsonResult.put("DEST_FILTER", "");
		jsonResult.put("description", "tlw");
		jsonResult.put("m_allot_id","61");
		jsonResult.put("feeallot","0");
		jsonResult.put("distdate","20130808");
		jsonResult.put("ISAUTOSUBSAL","Y");
		jsonResult.put("notes","");
		jsonResult.put("C_ORIG","总部仓库");
		
		List data_m_product = new ArrayList();
		jsonResult_data.put("m_product", data_m_product);
		
		int xmlns=1;
		
		//商品信息
		String sql_product="select distinct b.name,a.M_PRODUCT_ID,b.value " +
				"from b_planitem a,m_product b " +
				"where a.b_plan_id='"+b_plan_id+"' and a.m_product_id=b.id"; 
		//  jt_oracle_henlo.queryForList  QueryEngine.getInstance().doQueryList
		List<Map<String,Object>> list_product = jt_oracle_henlo.queryForList(sql_product);
		for (Map<String, Object> map_product : list_product) {
			JSONObject data_m_product_child = new JSONObject();	
			data_m_product.add(data_m_product_child);
			
			data_m_product_child.put("M_PRODUCT_LIST", map_product.get("M_PRODUCT_ID").toString());
			data_m_product_child.put("value", map_product.get("VALUE").toString());
			data_m_product_child.put("xmlns", map_product.get("NAME").toString());
			
			List m_product_color = new ArrayList();
			data_m_product_child.put("color", m_product_color);
			
			product_name = map_product.get("NAME").toString();
			//商品信息下的色号信息
			String sql_color = "select c.value1_code from b_planitem a,m_attributesetinstance c,m_product b " +
					"where a.b_plan_id='"+b_plan_id+"' and a.m_attributesetinstance_id=c.id and a.m_product_id=b.id " +
					"group by b.name,c.value1_code";
			
			List<Map<String,Object>> list_color = jt_oracle_henlo.queryForList(sql_color);
			for (Map<String, Object> map_color : list_color) {
				color = map_color.get("VALUE1_CODE").toString();
				
				JSONObject m_product_color_child = new JSONObject();
				m_product_color.add(m_product_color_child);
				
				m_product_color_child.put("xmlns", map_color.get("VALUE1_CODE").toString());
				
				//色号下经销商\店仓的信息
				String sql_store = "select c.value2_code as content,a.m_productalias_id, "+
						"sum(a.soqty) AS QTY_SO,sum(a.planqty) AS DESTQTY,sum(a.qty) AS QTYCAN,sum(a.qtyrem) as QTYREM "+
						"from b_planitem a,m_product b,m_attributesetinstance c,m_size d "+
						"where  a.m_product_id=b.id and  "+
						"a.b_plan_id='"+b_plan_id+"' and b.name = '"+product_name+"' and c.value1_code = '"+color+"' and "+ 
						"a.m_attributesetinstance_id=c.id and  "+
						"c.value2_id=d.ID "+
						"group by  c.value2_code,a.m_productalias_id "+ 
						"order by c.value2_code ASC";
				List color_c_store = new ArrayList();
				m_product_color_child.put("c_store", color_c_store);
				
				List<Map<String,Object>> list_store = jt_oracle_henlo.queryForList(sql_store);
				for (Map<String, Object> map_store : list_store) {
					
					JSONObject color_c_store_child = new JSONObject();
					
					JSONObject c_store_w = new JSONObject();
					JSONObject w_docno = new JSONObject();	
					JSONObject docno_q = new JSONObject();
					JSONObject q_array = new JSONObject();
					List array_tag_c = new ArrayList();
					
					color_c_store.add(color_c_store_child);
					
					color_c_store_child.put("w", c_store_w);
					color_c_store_child.put("xmlns", "兰州外购成品仓");
					
					c_store_w.put("docno", w_docno);
					c_store_w.put("xmlns", xmlns); //加入后增加
					
					w_docno.put("billdate", "20130807");
					w_docno.put("q", docno_q);
					w_docno.put("sotype", "FWD");
					w_docno.put("xmlns", "SO1308070000007");
					w_docno.put("b_so_id", "1747");
					
					docno_q.put("xmlns", xmlns); //加入后增加
					docno_q.put("array",q_array);
					
					q_array.put("tag_c", array_tag_c);
					
					xmlns++;
					//商品 色号 下详细信息
					String sql_color_detail = "select c.value2_code as content,a.m_productalias_id, "+
							"sum(a.soqty) AS QTY_SO,sum(a.planqty) AS DESTQTY,sum(a.qty) AS QTYCAN,sum(a.qtyrem) as QTYREM "+
							"from b_planitem a,m_product b,m_attributesetinstance c,m_size d "+
							"where  a.m_product_id=b.id and  "+
							"a.b_plan_id='"+b_plan_id+"' and b.name = '"+product_name+"' and c.value1_code = '"+color+"' and "+ 
							"a.m_attributesetinstance_id=c.id and  "+
							"c.value2_id=d.ID "+
							"group by  c.value2_code,a.m_productalias_id "+ 
							"order by c.value2_code ASC";
					
					List<Map<String,Object>> list_color_detail = jt_oracle_henlo.queryForList(sql_color_detail);
					for (Map<String, Object> map_color_detail : list_color_detail) {
						JSONObject array_tag_c_child = new JSONObject();
						array_tag_c.add(array_tag_c_child);
						
						array_tag_c_child.put("content", map_color_detail.get("CONTENT").toString());
						array_tag_c_child.put("QTYREM", map_color_detail.get("QTYREM").toString());
						array_tag_c_child.put("m_product_alias_id", map_color_detail.get("M_PRODUCTALIAS_ID").toString());
						array_tag_c_child.put("QTY_SO", map_color_detail.get("QTY_SO").toString());	
						array_tag_c_child.put("QTYCAN", map_color_detail.get("QTYCAN").toString());
						array_tag_c_child.put("QTY_ALLOT", map_color_detail.get("QTYREM").toString());
						array_tag_c_child.put("DESTQTY", map_color_detail.get("DESTQTY").toString());
						
					}
				}
				
			}					
		}
		
		data.put("jsonResult", jsonResult.toJSONString());		
		
		System.out.println(data.toString());*/
		
	}
	
}
