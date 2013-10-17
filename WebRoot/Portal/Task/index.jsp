<%@ page language="java"  pageEncoding="utf-8"%>
<%@ page import="java.util.Date,java.util.regex.Pattern,java.util.regex.Matcher" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="nds.control.web.UserWebImpl" %>
<%@ page import="nds.query.QueryEngine" %>
<%@ page import="nds.control.web.WebUtils" %>
<%@ page import="nds.schema.Table" %>
<%@ page import="nds.schema.TableManager" %>
<%@ page import="nds.schema.TableImpl" %>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<%@ page language="java" import="java.util.*"%>
<%@page language="java" import="java.util.Map" %>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    UserWebImpl userWeb =null;
    try{
        userWeb= ((UserWebImpl)WebUtils.getSessionContextManager(session).getActor(nds.util.WebKeys.USER));
    }catch(Throwable userWebException){
        System.out.println("########## found userWeb=null##########"+userWebException);
    }
    String idS=request.getParameter("id");
    int id=-1;
    if (idS != null){
        id=Integer.parseInt(idS);
    }
    if(userWeb==null || userWeb.getUserId()==userWeb.GUEST_ID){
        response.sendRedirect("/c/portal/login");
        return;
    }
    if(!userWeb.isActive()){
        session.invalidate();
        com.liferay.util.servlet.SessionErrors.add(request,"USER_NOT_ACTIVE");
        response.sendRedirect("/login.jsp");
        return;
    }
    Table t=TableManager.getInstance().getTable("M_ISSUE_TASK");
     String directory=t.getSecurityDirectory();
    int permission=userWeb.getPermission(directory);
     //if((permission&nds.security.Directory.READ)==0){
%>
<!--<script type="text/javascript">
    document.write("<span color='red' algin='center'>您没有权限！</span>")
</script>  -->

<%
      //      return;
     //   }
    String tableName=t.getName();
    int distributionTableId=t.getId();
    String comp=String.valueOf(QueryEngine.getInstance().doQueryOne("select VALUE from AD_PARAM where NAME='portal.company'"));
    String orgStore=String.valueOf(QueryEngine.getInstance().doQueryOne("SELECT b.ad_table_id from ad_column a,ad_column b where a.name= 'M_ALLOT.C_ORIG_ID' and a.ref_column_id=b.id"));
    String destStore=String.valueOf(QueryEngine.getInstance().doQueryOne("select a.REGEXPRESSION from ad_column a where a.name= 'M_ALLOT.DEST_FILTER'"));
    String column=String.valueOf(QueryEngine.getInstance().doQueryOne("select id from ad_column where name='M_ALLOT.B_SO_FILTER'"));
   	Pattern p=Pattern.compile("\"table\":\"(\\w+)\"");
    Matcher m=p.matcher(destStore);
    if(m.find()){
        destStore=m.group(1);
     }
%>
<%
	String b_plan_id = request.getParameter("mIssueTaskId");
	//获取单据状态
	String docnoState = String.valueOf(QueryEngine.getInstance().doQueryOne("select STATUS from M_ISSUE_TASK where id="+b_plan_id));
	//查询款号、商品ID、商品名
	List styleList = QueryEngine.getInstance().doQueryList("select distinct b.name,a.M_PRODUCT_ID,b.value from M_ISSUE_TASKITEM a,m_product b where a.M_ISSUE_TASK_ID="+b_plan_id+" and a.m_product_id=b.id order by b.name asc");
	
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
	
	
	
	for(int i=0;i<styleList.size();i++){
		JSONObject data_m_product_child = new JSONObject();	
		data_m_product.add(data_m_product_child);
		
		List styList = (List)styleList.get(i);
		data_m_product_child.put("M_PRODUCT_LIST", styList.get(1));
		data_m_product_child.put("value",  styList.get(2));
		data_m_product_child.put("xmlns",  styList.get(0));
		
		List m_product_color = new ArrayList();
		data_m_product_child.put("color", m_product_color);
	
		//查询当前款号下的颜色
		List colorList = QueryEngine.getInstance().doQueryList("select c.value1_code,c.value1  from M_ISSUE_TASKITEM a,m_attributesetinstance c,m_product b where a.M_ISSUE_TASK_ID='"+b_plan_id+"'"+
						" and a.m_attributesetinstance_id=c.id and a.m_product_id=b.id and b.name='"+styList.get(0)+"' group by c.value1_code,c.value1");
		
		//查询当前款号下的装箱数量
		List boxQtyList = QueryEngine.getInstance().doQueryList("select BOX_QTY  from  m_product t where t.name ='"+styList.get(0)+"'");
		
		for(int g=0;g<colorList.size();g++){
			JSONObject m_product_color_child = new JSONObject();
			m_product_color.add(m_product_color_child);
			
			List color = (List)colorList.get(g);
			m_product_color_child.put("xmlns", color.get(0).toString()+color.get(1).toString());
			
			List color_c_store = new ArrayList();
			
			m_product_color_child.put("c_store", color_c_store);

			//取经销商List
			List storeList = QueryEngine.getInstance().doQueryList("select d.id,d.name,t.docno,t.billdate,a.b_so_id||a.B_SO_MATCHSIZE_ID,sum(a.soqty*a.so_boxqty) boxqty_total,a.B_SO_MATCHSIZE_ID from b_so t,M_ISSUE_TASKITEM a,m_attributesetinstance c,m_product b,C_CUSTOMER d where a.M_ISSUE_TASK_ID="+b_plan_id+
			         " and a.m_attributesetinstance_id=c.id and a.m_product_id=b.id and b.name='"+styList.get(0)+"' and c.value1_code ="+color.get(0)+
			         " and a.c_customer_id = d.id and t.id=a.b_so_id group by d.name,d.id,t.docno,a.b_so_id||a.B_SO_MATCHSIZE_ID,t.billdate,a.B_SO_MATCHSIZE_ID");
			for(int store=0;store<storeList.size();store++){
				List stoList = (List)storeList.get(store);
				
				JSONObject color_c_store_child = new JSONObject();
				color_c_store.add(color_c_store_child);
				JSONObject c_store_w = new JSONObject();
				JSONObject w_docno = new JSONObject();	
				JSONObject docno_q = new JSONObject();
				JSONObject q_array = new JSONObject();
				List array_tag_c = new ArrayList();
				
				
				color_c_store_child.put("w", c_store_w);
				color_c_store_child.put("xmlns", stoList.get(1));
				
				c_store_w.put("docno", w_docno);
				c_store_w.put("xmlns", g+1); //加入后增加
				
				w_docno.put("billdate", stoList.get(3));
				//款号
				w_docno.put("product_name",styList.get(2));
				w_docno.put("q", docno_q);
				w_docno.put("sotype", "FWD");
				int boxQty = Integer.parseInt(boxQtyList.get(0).toString());
				w_docno.put("xmlns", stoList.get(2));//订单号
				w_docno.put("b_so_matchsize_id",stoList.get(6));//配码ID
				w_docno.put("boxqty",boxQty);//装箱数量
				//w_docno.put("b_so_id", stoList.get(2));
				w_docno.put("boxqty_total", stoList.get(5));//配码总量
				
				docno_q.put("xmlns", g+1); //加入后增加
				docno_q.put("array",q_array);
				
				q_array.put("tag_c", array_tag_c);
				
				List colorcodeList  =  QueryEngine.getInstance().doQueryList("select a.* from (select c.value2_code as content "+
						" from M_ISSUE_TASKITEM a,m_product b,m_attributesetinstance c,m_size d"+
						" where a.M_ISSUE_TASK_ID='"+b_plan_id+"'"+
						" and a.m_product_id=b.id and b.name = '"+styList.get(0)+"'"+
						" and a.m_attributesetinstance_id=c.id"+
						" and c.value2_id=d.ID"+
						" group by  c.value2_code order by c.value2_code ASC) a union all select '箱数' from dual ");  
				
				//查询当前款号下的颜色下的所有尺寸的计划量、已配量、库存、剩余量
				List sizeList = QueryEngine.getInstance().doQueryList("select a.* from (select c.value2_code as content,a.m_productalias_id,"+
						" sum(a.QTY) AS QTY_SO,sum(a.QTY*a.BOXQTY) AS DESTQTY,sum(a.SOQTY) AS QTYCAN,sum(a.STOCKQTY) as QTYREM,sum(a.QTY) AS QTY_ALLOT,a.id"+
						" from M_ISSUE_TASKITEM a,m_product b,m_attributesetinstance c,m_size d"+
						" where a.M_ISSUE_TASK_ID='"+b_plan_id+"'"+
						" and a.m_product_id=b.id and b.name = '"+styList.get(0)+"' and c.value1_code = '"+color.get(0)+"' and a.c_customer_id="+stoList.get(0)+ " and a.b_so_id||a.B_SO_MATCHSIZE_ID="+stoList.get(4)+
						" and a.m_attributesetinstance_id=c.id"+
						" and c.value2_id=d.ID"+
						" group by  c.value2_code,a.m_productalias_id,a.id order by c.value2_code ASC) a "+
						" union all select distinct '箱数' ,to_number(b.ID||c.ID),null,null,a.SO_BOXQTY,null,a.BOXQTY,null from"+
						" M_ISSUE_TASKITEM a,m_product b,m_attributesetinstance c,m_size d "+
						" where a.M_ISSUE_TASK_ID='"+b_plan_id+"'"+
						" and a.m_product_id=b.id and b.name = '"+styList.get(0)+"' and c.value1_code = '"+color.get(0)+"' and a.c_customer_id="+stoList.get(0)+ " and a.b_so_id||a.B_SO_MATCHSIZE_ID="+stoList.get(4)+
						" and a.m_attributesetinstance_id=c.id"+
						" and c.value2_id=d.ID");
					
				for(int j=0;j<colorcodeList.size();j++){  
					if(sizeList.size()==0){
						array_tag_c.add(colorcodeList.get(j).toString());
					}else {
						boolean exist = false;
						for(int h=0;h<sizeList.size();h++){															
							List sizeCountList = (List)sizeList.get(h);						
							if(colorcodeList.get(j).toString().equals(sizeCountList.get(0).toString())){
								/**
								String qtyRemSql = "select nvl(sum(sum(a.destqty)),0) as qtyrem from ("+
								   "select a.M_ISSUE_TASK_ID,sum(a.QTY*a.BOXQTY) as destqty,sum(a.STOCKQTY) as STOCKQTY"+
						           " from M_ISSUE_TASKITEM a,m_product b,m_attributesetinstance c,m_size d"+
						           " where a.M_ISSUE_TASK_ID in (select distinct M_ISSUE_TASK_ID from M_ISSUE_TASKITEM where STATUS = 1 and ISACTIVE='Y' and M_ISSUE_TASK_ID <> '"+b_plan_id+"')"+
						           " and a.m_product_id=b.id and b.name = '"+styList.get(0)+"' and c.value1_code = '"+color.get(0)+"' and c.value2_code='"+sizeCountList.get(0)+"'"+
						           " and a.m_attributesetinstance_id=c.id"+
						           " and c.value2_id=d.ID"+
						           " group by a.M_ISSUE_TASK_ID) a group by a.M_ISSUE_TASK_ID";
								*/
								if(sizeCountList.get(0).toString().equals("箱数")){
									JSONObject array_tag_c_child = new JSONObject();
									array_tag_c.add(array_tag_c_child);
									array_tag_c_child.put("content", sizeCountList.get(0));
									array_tag_c_child.put("QTYREM",sizeCountList.get(5));
									array_tag_c_child.put("m_product_alias_id", sizeCountList.get(1));
									array_tag_c_child.put("QTY_SO", 9999);	
									array_tag_c_child.put("QTYCAN", sizeCountList.get(4));
									array_tag_c_child.put("QTY_ALLOT", sizeCountList.get(6));
									array_tag_c_child.put("DESTQTY", sizeCountList.get(3));
									array_tag_c_child.put("BARCODE_ID", sizeCountList.get(7));
									exist = true ;
									break;
								}else{
									//List qtyRemList = QueryEngine.getInstance().doQueryList(qtyRemSql);
									//int qtyR = Integer.parseInt(qtyRemList.get(0).toString());
									//int qtyRemChiled = Integer.parseInt(sizeCountList.get(5).toString());
									JSONObject array_tag_c_child = new JSONObject();
									array_tag_c.add(array_tag_c_child);
									array_tag_c_child.put("content", sizeCountList.get(0));
									//out.print(qtyR+"-----");
									//out.print("+++"+qtyRemChiled+"+++");
									array_tag_c_child.put("QTYREM",sizeCountList.get(5));
									array_tag_c_child.put("m_product_alias_id", sizeCountList.get(1));
									array_tag_c_child.put("QTY_SO", 9999);	
									array_tag_c_child.put("QTYCAN", sizeCountList.get(4));
									array_tag_c_child.put("QTY_ALLOT", sizeCountList.get(6));
									array_tag_c_child.put("DESTQTY", sizeCountList.get(3));
									array_tag_c_child.put("BARCODE_ID", sizeCountList.get(7));
									exist = true ;
									break;
								}
							}
						}
						if(!exist){
							array_tag_c.add(colorcodeList.get(j).toString());
						}
					}
				}
			}
		}
	}
	
	data.put("jsonResult", jsonResult.toJSONString());

	String query = " select distinct b.Name "+
	" from M_ISSUE_TASKITEM A,M_PRODUCT b "+ 
	" where  A.M_PRODUCT_ID=B.ID and  "+
	" A.M_ISSUE_TASK_ID='"+b_plan_id+"'"+
	" group by M_ISSUE_TASK_ID,M_PRODUCTALIAS_ID,b.Name  "+
	" having max(STOCKQTY)<sum(QTY*BOXQTY) ";
	List list = QueryEngine.getInstance().doQueryList(query);
	String sty_stock= "";
	for(int i=0;i<list.size();i++){
		sty_stock = sty_stock+list.get(i).toString()+",";
	}
	
	//库存可配量
	String query_Rem = "select M_PRODUCTALIAS_ID, "+
			"sum(QTY*BOXQTY) QtyDest, "+
			"max(STOCKQTY)-sum(QTY*BOXQTY) QtyRem "+
			"from M_ISSUE_TASKITEM a  "+
			"where M_ISSUE_TASK_ID='"+b_plan_id+"' and "+ 
			"exists(select 'x' from M_ISSUE_TASK b where a.M_ISSUE_TASK_ID=b.id and b.ISACTIVE='Y' and b.STATUS=1 ) "+
			"group by M_PRODUCTALIAS_ID ";	
	List list_DR = QueryEngine.getInstance().doQueryList(query_Rem);
	
	JSONObject json_Rem =new JSONObject();	
	JSONObject json_Dest =new JSONObject();	
	for(int i=0;i<list_DR.size();i++){
		List list_DR_child = (List)list_DR.get(i);
		String barCode = list_DR_child.get(0).toString();
		json_Dest.put(barCode+"FWD",list_DR_child.get(1));
		json_Rem.put(barCode+"FWD",list_DR_child.get(2));		
	}
	JSONObject data_Rem =new JSONObject();
	data_Rem.put("json_Rem", json_Rem.toString());
	data_Rem.put("json_Dest", json_Dest.toString());
	
	//单据信息
	String Docinfo = "";
	String Query_Docinfo = "select DOCNO,BILLDATE from M_ISSUE_TASK where id='"+b_plan_id+"'";
	List list_Docinfo = QueryEngine.getInstance().doQueryList(Query_Docinfo);
	if(list_Docinfo.size()>0){
		Docinfo = "[单据编号]:"+((List)list_Docinfo.get(0)).get(0).toString()+" [单据日期]:"+((List)list_Docinfo.get(0)).get(1).toString();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>配货单</title>
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
    <script language="javascript" src="/html/nds/js/top_css_ext.js"></script>
    <script language="javascript" language="javascript1.5" src="/html/nds/js/ieemu.js"></script>
    <script language="javascript" src="/html/nds/js/cb2.js"></script>
    <script language="javascript" src="/html/nds/js/common.js"></script>
    <script language="javascript" src="/html/nds/js/print.js"></script>
    <script language="javascript" src="/html/nds/js/prototype.js"></script>
    <script language="javascript" src="/html/nds/js/jquery1.2.3/jquery.js"></script>
    <script language="javascript" src="/html/nds/js/jquery1.2.3/hover_intent.js"></script>
    <script language="javascript" src="/html/nds/js/jquery1.2.3/ui.tabs.js"></script>
    <script>
        jQuery.noConflict();
    </script>
    <script language="javascript" src="/html/js/sniffer.js"></script>
    <script language="javascript" src="/html/js/ajax.js"></script>
    <script language="javascript" src="/html/js/util.js"></script>
    <script type="text/javascript" src="/html/nds/js/selectableelements.js"></script>
    <script type="text/javascript" src="/html/nds/js/selectabletablerows.js"></script>
    <script language="javascript" src="/html/nds/js/calendar.js"></script>
    
    <script type="text/javascript" src="/html/nds/js/dwr.Controller.js"></script>
    <script type="text/javascript" src="/html/nds/js/dwr.engine.js"></script>
    <script type="text/javascript" src="/html/nds/js/dwr.util.js"></script>
    
	<script type="text/javascript" src="/html/nds/js/HenloController.js"></script>
	
    <script language="javascript" src="/html/nds/js/application.js"></script>
    <script language="javascript" src="/html/nds/js/alerts.js"></script>
    <script language="javascript" src="/html/nds/js/init_objcontrol_zh_CN.js"></script>
    <script type="text/javascript" src="/html/nds/js/object_query.js"></script>
    <script language="javascript" src="distribution.js"></script>
    <link type="text/css" rel="stylesheet" href="/html/nds/themes/classic/01/css/portal.css"/>
    <link type="text/css" rel="stylesheet" href="/html/nds/themes/classic/01/css/ui.tabs.css"/>
    <link type="text/css" rel="stylesheet" href="/html/nds/themes/classic/01/css/object.css"/>
    <link type="text/css" rel="stylesheet" href="/html/nds/css/cb2.css"/>
    <link href="ph.css" rel="stylesheet" type="text/css"/>
    <link type="text/css" rel="stylesheet" href="/html/nds/themes/classic/01/css/nds_portal.css"/>
    
   <!-- <link type="text/css" rel="stylesheet" href="/html/nds/themes/classic/01/css/header_aio_min.css"/>-->
   <!-- <link typ e="text/css" rel="stylesheet" href="/html/nds/css/nds_header.css"/>-->
    
</head>
<script language="javascript">
<!--
var retDate = <%=data%>;
var b_plan_id = <%=b_plan_id%>;
var docnoState = <%=docnoState%>;
var sty_stock = '<%=sty_stock%>';
var data_Rem = <%=data_Rem%>;
var Docinfo = '<%=Docinfo%>' ;
//--!>

jQuery(document).ready(function(){
	document.getElementById('button2').disabled=false;
	document.getElementById('saveImage').disabled=false; 
	dist._onLoadMetrix();
	if(docnoState=='2'){
		var aInput = document.getElementsByTagName('input');
		for(var j = 0; j < aInput.length; j++){
		  aInput[j].readOnly = true;
		}
	}
});
var check = 10;  
function remainTime(){
	document.getElementById('button2').disabled=true;
	if(check>0) {
		document.getElementById('button2').value="刷新"+check; 
		check-=1; 
		setTimeout("remainTime()",1000); 
		return null;
	}
	document.getElementById('button2').disabled=false;
	document.getElementById('button2').value="刷新"; 
	check = 10;
}
function clickImage(){
	document.getElementById('saveImage').disabled=true; 
	remainTime();
	//if(check>0) {
	//	check-=1; 
	//	setTimeout("clickImage()",1000); 
	//	return null;
	//}
	//document.getElementById('saveImage').disabled=false;
}
</script>
<style type="text/css">
.red
{
background-color:red;
}
</style>
<body id="boo">
<input type="hidden" id="load_model" value="metrix"/>
<input type="hidden" id="load_type" value="<%=id==-1?"load":"reload"%>"/>
<input type="hidden" id="showStyle" value="list">
<input type="hidden" id="orderStatus" value="1"/>
<input type="hidden" id="isChanged" value="false"/>
<input type="hidden" id="currSty" value=""/>
<iframe id="CalFrame" name="CalFrame" frameborder=0 src=/html/nds/common/calendar.jsp style="display:none;position:absolute; z-index:9999"></iframe>
<div id="ph-btn">
    <div id="ph-from-btn">
        <input type="hidden" id="fund_balance" value="<%=id!=-1?id:""%>"/>
        <input type="image" name="imageField3" id="saveImage" src="images/ph-btn-bc.gif" onclick="javascript:{clickImage();dist.saveDate('sav');}"/>
         <input type="button" id="button2" value="刷新" onclick="javascript:{window.location.reload();remainTime();}"/>
        <input type="image" name="imageField4" src="images/ph-btn-gb.gif" onclick="window.close();"/>
		<div><font color="#FF0000" size="10" face="arial"><b>关闭窗口后请点击配货申请单上的刷新按钮</b></font><span id="msg" ></span></div>
    </div>
</div>
<div id="ph-container">
<table  border="0" cellspacing="0" cellpadding="0">

<tr style="display:none">
    <td colspan="2" class="ph-td-bg">
        <div id="ph-serach">
            <div id="ph-serach-title">
                <div id="menu">
                    <a href="#" onclick="dist.showDetail();">
                        <span class="left"></span>
                       筛选条件 </a>
                    <a href="#" onclick="dist.showDocuments()">
                        <span class="left"></span>
                        单据号查询 </a>
                </div>
            </div>
            <div id="ph-serach-bg">
                <div id="Details" class="obj">
                    <table style="padding-left:10px" border="0" cellspacing="1" cellpadding="0" class="obj" align="left">
                        <tr>
                            <!--<td class="ph-desc" width="75" valign="top" nowrap="" align="right"><div class="desc-txt">订单类型<font color="red">*</font>：</div></td>
                           <td class="ph-value" width="180" valign="top" nowrap="" align="left"><select id="column_26991" class="objsl" tabindex="1" name="doctype">
                                <option value="0">--请选择--</option>
                                <option value="FWD">新货订单</option>
                                <option value="INS">补货订单</option>
                                <option selected="selected" value="ALL">全部</option>
                            </select></td> -->
                            <!--发货店仓-->
                            <td class="ph-desc" valign="top" nowrap="" align="right"><div class="desc-txt">单据编号：</div></td><td class="ph-value" valign="top" nowrap="" align="left"><input class="ipt-4-2" type="text" value="" readonly="" /></td>
                            <td class="ph-desc" valign="top" nowrap="" align="right"><div class="desc-txt">单据日期：</div></td><td class="ph-value" valign="top" nowrap="" align="left"><input id="column_date" type="text" value="" id="distdate" title="8位日期，如20070823" size="20" maxlength="10" tabindex="5" class="ipt-4-2" name="" /><span class="coolButton">
							<a href="javascript:showCalendar('imageCalendar23',false,'column_date',null,null,true);" onclick="event.cancelBubble=true;">
							<img id="imageCalendar23" width="16" border="0" align="absmiddle" height="18" src="images/datenum.gif" title="Find" />
							</a>
							</span></td>
                            <td class="ph-desc" width="75" valign="top" nowrap="" align="right"><div class="desc-txt">发货店仓<font color="red">*</font>：</div></td>
                            <td class="ph-value" width="180" valign="top" nowrap="" align="left"><input name="c_orig_id__name" readonly="" type="text" class="ipt-4-2"  id="column_26992"  value="" />
                                <input type="hidden" id="fk_column_26992" name="C_ORIG_ID" value="">
                  <span  class="coolButton" id="cbt_26992" onaction="oq.toggle('/html/nds/query/search.jsp?table=<%=orgStore%>&return_type=s&column=26992&accepter_id=column_26992&qdata='+encodeURIComponent(document.getElementById('column_26992').value)+'&queryindex='+encodeURIComponent(document.getElementById('queryindex_-1').value),'column_26992')"><img width="16" height="16" border="0" align="absmiddle" title="Find" src="images/find.gif"/></span>
                      <script type="text/javascript" >createButton(document.getElementById("cbt_26992"));</script>
                            </td>
                            <!--收货店仓-->
                            <td class="ph-desc" width="75" valign="top" nowrap="" align="right"><div class="desc-txt">收货店仓<font color="red">*</font>：</div></td>
                            <td class="ph-value" width="180" valign="top" nowrap="" align="left">
                                <input type='hidden' id='column_26993' name="column_26993" value=''>
                                <input name="" readonly type="text" class="ipt-4-2" id='column_26993_fd' value="" >
                                    <span  class="coolButton" id="column_26993_link" title=popup onaction="oq.toggle_m('/html/nds/query/search.jsp?table=<%=destStore%>&return_type=f&accepter_id=column_26993', 'column_26993');"><img id='column_26993_img' width="16" height="16" border="0" align="absmiddle" title="Find" src="images/filterobj.gif"/></span>
                                <script type="text/javascript" >createButton(document.getElementById('column_26993_link'));</script>
                            </td>
                            
                            <!--<td class="ph-desc" width="75" valign="top" nowrap="" align="right"><div class="desc-txt">自动提交销售单<input type="checkbox" name="canModify" id="isautosubsal" checked="checked"><font color="red"></font></div></td>
                            --><!--
                            <td class="ph-value" width="80" valign="top" nowrap="" align="left"><input id="switchModel" type="button" value="切换为矩阵模式" style="font-size:14px;" onclick="dist.switchModel();"></td>
                            -->
                            <td></td>
                        </tr>
                        <tr>
                            <!--选择款号-->
                            <td class="ph-desc" valign="top" nowrap="" align="right"><div class="desc-txt">选择款号<font color="red">*</font>：</div></td>
                            <td class="ph-value"  valign="top" nowrap="" align="left">
                                <input type='hidden' id='column_26994' name="product_filter" value=''>
                                <input type="text" class="ipt-4-2"  readonly id='column_26994_fd' value="" />
        <span  class="coolButton" id="column_26994_link" title=popup onaction="oq.toggle_m('/html/nds/query/search.jsp?table='+'m_product'+'&return_type=f&accepter_id=column_26994', 'column_26994');"><img id='column_26994_img' width="16" height="16" border="0" align="absmiddle" title="Find" src="images/filterobj.gif"/></span>
                                <script type="text/javascript" >createButton(document.getElementById('column_26994_link'));</script>
                            </td>
                            <!--起止时间-->
                            <%
                                Date tody=new Date();
                                SimpleDateFormat fmt=new SimpleDateFormat("yyyyMMdd");
                                String end=fmt.format(tody);
                                Long stL=tody.getTime()-24*60*60*1000*10l;
                                Date std=new Date(stL);
                                String st=fmt.format(std);
                            %>
                            <td class="ph-desc" valign="top" nowrap="" align="right"><div class="desc-txt"> 订单时间(起)<font color="red">*</font>：</div></td>
                            <td class="ph-value"  valign="top" nowrap="" align="left">
                                <input type="text" class="ipt-4-2" name="billdatebeg"  tabIndex="5" maxlength="10" size="20" title="8位日期，如20070823" id="column_26995" value="<%=st%>" />
                                <span  class="coolButton">
                                    <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageCalendar23',false,'column_26995',null,null,true);"><img id="imageCalendar23" width="16" height="18" border="0" align="absmiddle" title="Find" src="images/datenum.gif"/></a>
                                </span>
                            </td>
                            <td class="ph-desc" valign="top" nowrap="" align="right"><div class="desc-txt">订单时间(止)<font color="red">*</font>：</div></td>
                            <td class="ph-value" valign="top" nowrap="" align="left">
                                <input name="billdateend" type="text"  class="ipt-4-2" maxlength="10" size="20" title="8位日期，如20070823" id="column_269966"  value="<%=end%>"/>
        <span  class="coolButton">
            <a onclick="event.cancelBubble=true;" href="javascript:showCalendar('imageCalendar144',false,'column_269966',null,null,true);"><img id='imageCalendar144' width="16" height="18" border="0" align="absmiddle" title="Find" src="images/datenum.gif"/></a>
        </span>
                            </td>
                            <!--查询条件提交按钮-->
                            <td class="ph-value" width="80" valign="top" nowrap="" align="left"><%if(id==-1){%><input type="image" name="imageField5" src="images/btn-search01.gif" onclick="dist.queryObject()" /><%}%>
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr><!--
                            <td class="ph-desc"  valign="top" nowrap="nowrap" align="right">
                                <div class="desc-txt" align="center" style="color:blue;">物流备注*：</div>
                            </td>
                            <td class="ph-value" valign="top" align="left" >
                                <input type="text" name="canModify" class="notes" id="notes"/>
                            </td>
                            <td class="ph-desc" valign="top" nowrap="" align="right"><div class="desc-txt">配单日期<font color="red">*</font>：</div></td>
                            <td class="ph-value"  valign="top" nowrap="" align="left">
                                <input type="text" name="canModify" class="ipt-4-2" name="billdatebeg"  tabIndex="5" maxlength="10" size="20" title="8位日期，如20070823" id="distdate" value="<%=end%>" />
                                <span  class="coolButton" name="canShow">
                                    <a onclick="window.event.cancelBubble=true;" href="javascript:showCalendar('imageCalendar3',false,'distdate',null,null,true);"><img id="imageCalendar3" width="16" height="18" border="0" align="absmiddle" title="Find" src="images/datenum.gif"/></a>
                                </span>
                            </td>-->
                             <td class="ph-desc" valign="top" align="right">
                                <div class="desc-txt" align="center" id="idocnoType" style="color:red;font-size:14px;display:none;">*期货优先</div>
                            </td><!--
                            <td class="ph-desc" valign="top" align="left">
                                  <div class="desc-txt" align="left" style="color:blue;float:left;font-size:15px;font-weight:bold;vertical-align:bottom;">本单金额：</div><div id="amount1" style="color:black;float:left;font-size:15px;margin-top:3px;"></div>
                            </td>
                        --></tr>
                    </table>
                </div>
                <!--单据号查询表格-->
                <div id="Documents" class="djh-table" style="display:none">
                    <table style="padding-left:12px" border="0" cellspacing="1" cellpadding="0" class="obj" align="left">
                        <tr>
                            <td align="right" valign="top" nowrap="nowrap" class="ph-desc"><div class="desc-txt">单据号<font color="red">*</font>：</div></td>
                            <td class="ph-value" width="165" valign="top" nowrap="" align="left">
                                <input name="Input2" type="text" readonly="true" class="ipt-4-2" id="column_41520_fd" value=""/>
                                <input type="hidden" id="column_41520" name="DOCUMENT_ID" value="">
                        <span id="column_41520_link" class="coolButton" title=popup onaction="oq.toggle_m('/html/nds/query/search.jsp?table='+'b_so'+'&return_type=f&column=<%=column%>&accepter_id=column_41520', 'column_41520');"><img id='column_41520_img' width="16" height="16" border="0" align="absmiddle" title="Find" src="images/filterobj.gif"/></span>
                                <script type="text/javascript" >createButton(document.getElementById('column_41520_link'));</script>
                            </td>
                            <td align="right" valign="top" nowrap="nowrap" class="ph-desc"><div class="desc-txt">备&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  注：</div></td>
                            <td class="ph-value" width="185" valign="top" nowrap="" align="left">
                                <input type="text" readonly="true" class="notes" id="commonNotes"/>
                            </td>
                            <td class="ph-desc" width="75" valign="top" nowrap="" align="right"><div class="desc-txt">自动提交销售单<input type="checkbox" name="canModify" id="isautosubsal2" checked="checked"><font color="red"></font></div></td>
                            <td class="ph-value" width="150" valign="top" nowrap="nowrap" align="center">
                                <%if(id==-1){%><input type="image" name="imageField5" src="images/btn-search01.gif" onclick="dist.queryObject('doc')" /><%}%>
                            </td>
                        </tr>
                        <tr>
                            <td class="ph-desc"  valign="top" nowrap="" align="right">
                                <div class="desc-txt" align="center" style="color:blue;">物流备注*：</div>
                            </td>
                            <td class="ph-value" valign="top" align="left">
                                <input type="text" name="canModify" class="notes" id="orderNotes"/>
                            </td>
                            <td class="ph-desc" valign="top" nowrap="" align="right"><div class="desc-txt">配单日期<font color="red">*</font>：</div></td>
                            <td class="ph-value"  valign="top" nowrap="" align="left">
                                <input type="text" name="canModify" class="ipt-4-2" name="billdatebeg"  tabIndex="5" maxlength="10" size="20" title="8位日期，如20070823" id="distdate1" value="<%=end%>" />
                                <span  class="coolButton" name="canShow">
                                    <a onclick="window.event.cancelBubble=true;" href="javascript:showCalendar('imageCalendar31',false,'distdate1',null,null,true);"><img id="imageCalendar31" width="16" height="18" border="0" align="absmiddle" title="Find" src="images/datenum.gif"/></a>
                                </span>
                            </td>
                            <td class="ph-desc" valign="top" width="200">
                                <span name="canShow" id="docnoType" style="color:red;font-weight:bold;font-size:14px;display:none;">*期货优先</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span name="canShow"  style="color:blue;font-size:15px;font-weight:bold;vertical-align:bottom;" >本单金额：</span><span name="canShow" id="amount" style="color:black;font-size:15px;"></span>
                            </td>
                          
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </td>
</tr>
<tr>
    <td colspan="2"><div class="ph-height"></div></td>
</tr>
<tr  style="display:none">
    <td colspan="2" bgcolor="#e6edf1">
    	<div id='ph-piother' style="position:relative;z-index:2;background-color:#e6edf1;width:auto">
        <div id="ph-pic">
        <div id="ph-pic-img">
            <div id="ph-pic-img-width">
                <div id="ph-pic-img-border"><img id="pdt-img" width="90" height="75" /></div>
                <div id="ph-pic-img-txt" hidden="true"></div>
            </div></div>
        <div id="ph-pic-left">
            <div id="ph-pic-txt">
                <ul>
                    <li>
                        <div class="left">可用库存：</div>
                        <div class="right" id="tot-can"></div>
                    </li>
                    <li>
                        <div class="left">订单余量：</div>
                        <div class="right" id="tot-rem"></div>
                    </li>
                    <li>
                        <div class="left-red">当前已配：</div>
                        <div class="right-red" id="tot-ready"></div>
                    </li>
                </ul>
            </div>
        </div>
        <div id="ph-pic-left01">
            <div id="ph-pic-txt">
                <ul>
                    <li>
                        <div class="left">可用库存：</div>
                        <div class="right" id="input-5"></div>
                    </li>
                    <li>
                        <div class="left">订单余量：</div>
                        <div class="right" id="input-4"></div>
                    </li>
                    <li>
                        <div class="left-red">当前已配：</div>
                        <div class="right-red" id="input-2"></div>
                    </li>
                </ul>
            </div>
        </div>
        <div id="ph-pic-right">
            <div id="ph-pic-txt">
                <ul>
                    <li>
                        <div class="left">订单余量：</div>
                        <div class="right" id="rs"></div>
                    </li>
                    <li>
                        <div class="left-red">当前可配：</div>
                        <div class="right-red" id="input-1"></div>
                    </li>
                </ul>
            </div>
        </div>
    </div></div></td>
</tr>
<tr>
    <td colspan="2"><div class="ph-height"></div></td>
</tr>
<tr>
    <td valign="top" align="left">
        <div id="ph-from-left">
            <div id="ph-from-left-bg">
                <div class="left-search">
                    <div><input name="textfield" type="text" class="left-search-input" id="pdt-search"/></div>
                </div>
                <div id="left-section-height"></div>
                <div id="left-section">
                    <ul id="category_manu"></ul>
                </div>
            </div>
        </div></td>
    <td width="97%"  valign="top" align="left">
        <div class="ph-from-right">
            <div id="ph-from-right-border">
                <div id="ph-from-right-b">
                    <div id="ph-from-right-table"></div>
                    <div style="height:17px"></div>
                </div>
            </div>
        </div>
    </td>
</tr>
<tr>
    <td colspan="2">
        <div id="ph-footer">
            <div id="ph-footer-bg"></div>
            <div id="ph-footer-txt">&copy;2008 上海伯俊软件科技有限公司 版权所有 保留所有权 | 商标 | 隐私权声明 </div>
        </div>
    </td>
</tr>
</table>
</div>
<div id="submitImge" style="left:30px;top:80px;z-index:111;position:absolute;display:none;">
    <img src="/html/nds/images/submitted.gif"/>
</div>
<div id="obj-bottom">
    <iframe id="print_iframe" name="print_iframe" width="1" height="1" src="/html/common/null.html"></iframe>
</div>
<input type='hidden' name='queryindex_-1' id='queryindex_-1' value="-1" />
<table><tr><td>
    <script>
        jQuery(document).ready(function(){dcq.createdynlist([])});
        var ti=setInterval("dcq.dynquery();",500);
    </script>
</td></tr>
</table>
	<div id="alert-auto-dist" style="position:absolute;top:0pt;left:0pt;z-index:100;background-color:#024770;height:100%;width:100%;display:none;">
		<div id="auto-bg">
		<div id="auto-menu"><input name="" type="image" src="images/btn-gb.gif" width="21" height="21" onclick="dist.closeAuto();" /></div>
			<div id="auto-main">
				<div id="tabsG">
  				<ul>
    				<li>请选择配货模式</li>
	<li><input id="all-order" name="auto-model" type="radio" value="all-order" can-dist="" checked="checked" onclick='dist.allOrderDist();' />整单</li>
    <li><input id="current-style" name="auto-model" type="radio" value="current-style" can-dist="" onclick='dist.currentStyleDist();' />当前款</li>
  				</ul>
			 </div>
			<div id="auto-border">
				<div id="auto-bl">
					<div id="auto-bl-title">设置可配总量比例</div>
					<div id="auto-bl-txt">
						<table width="440" border="0" align="center" cellpadding="0" cellspacing="0">
  						<tr>
								<td width="100"><div class="ph-left-txt">当前可配总量：</div></td>
								<td width="40"><div id="all-can-dist" class="ph-right-txt"></div></td>
								<td width="100"><div class="ph-left-txt" title="填入小数如：0.66">本次可配比例：</div></td>
								<td width="60"><div class="ph-right-txt"><input type="text" class="ipt-4-1" value="1" id="percentage" onblur="generateCan();"/></div></td>
								<td width="100"><div class="ph-left-txt">本次可配数量：</div></td>
								<td width="40"><div class="ph-right-txt" id="currentCan"></div></td>
							</tr>
						</table>
					</div>
        </div>
				<div class="auto-height"></div>
				<div id="auto-cl">
					<div id="auto-bl-title">选择配货策略</div>
					<div id="auto-cl-txt">
							<table width="440" border="0" align="center" cellpadding="0" cellspacing="0">
							  <tr>
							    <td width="13" align="right"><label>
      <input name="dist_type" onclick="checkType(event);" type="radio" value="spec_number" checked="checked" />
      </label></td>
							    <td width="150"><div class="ph-left-txt">指定配货数量<font color="red">*</font>：</div></td>
							    <td width="297"><div class="ph-right-txt"><input name="" id="specNumber" type="text" class="right-input" /></div></td>
							  </tr>
							  <tr>
							    <td width="13">&nbsp;</td>
							    <td colspan="2"><div class="ph-right-txt">说明：所有商品均按此处指定的数量配货。</div></td>
							  </tr>
							  <tr>
							    <td width="13" align="right"><label>
      <input type="radio" onclick="checkType(event);" name="dist_type" value="not_order" />
      </label></td>
							    <td width="150"><div class="ph-left-txt">按未配量比例配货<font color="red">*</font>：</div></td>
							    <td width="297"><div class="ph-right-txt"><input onblur="checkFloat(event)" disabled="true" id="fowNotOrderPercent" name="" type="text" value="1" class="right-input" /></div></td>
							  </tr>
							  <tr>
							    <td width="13">&nbsp;</td>
							    <td colspan="2"><div class="ph-right-txt">说明：按照未配量此处指定的比例为所有商品配货。</div></td>
							  </tr>
							  <tr>
    							<td width="13" align="right">
    								<label>
      								<input type="radio" value="order" name="dist_type" onclick="checkType(event);">
      							</label>
      						</td>
   							 <td width="160" align=left colspan=2><font color="red">*</font>按订单订货比例配货<font color="red">*</font></td>
  							</tr>
							  <tr>
							    <td width="13">&nbsp;</td>
							    <td colspan="2"><div class="ph-right-txt">说明：按照可配量订货比例为所有商品配货(订货比例指同一商品不同订单订
							货数量占所有订单订货总量的比例)。</div></td>
							  </tr>
							  <tr>
							    <td colspan="3" height="10"></td>
							  </tr>
							  <tr>
							    <td colspan="3"><div class="ph-right-notes">注意：无论选择哪种配货策略，均受到可配货总量的限制，即配货总数量不能大于
							      可配货总量。</div></td>
							  </tr>
							</table>
						</div>
					</div>
					<div class="auto-height"></div>
					<div id="auto-btn"><input name="" type="image" src="images/btn-cd.gif" onclick="dist.exec_dist();" width="34" height="20" />&nbsp;&nbsp;<input name="" type="image" src="images/btn-qx.gif" width="34" height="20" onclick="dist.closeAuto();" /></div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="dist_type" value="specNumber"/>
	<script type="text/javascript">
		if(b_plan_id==-1){
			alert("请重新刷新页面！");
		}
        function  generateCan(){
            var totCan=parseInt(jQuery("#all-can-dist").html());
            var percentage=jQuery("#percentage").val();
            percentage=isNaN(parseFloat(percentage))?0:parseFloat(percentage);
            if(percentage>1||percentage<0){
                alert("请输入小于1的正小数！");
                jQuery("#percentage").val("1");
                jQuery("#percentage").focus();
            }
            jQuery("#currentCan").html(Math.round(totCan*parseFloat(jQuery("#percentage").val())));
        }
        function checkFloat(event){
        	var e=Event.element(event);
        	var percent=jQuery(e).val();
        	percent=isNaN(parseFloat(percent))?0:parseFloat(percent);
		      if(percent>1||percent<0){
		          alert("请输入小于1的正小数！");
		          jQuery(e).val("1");
		          jQuery(e).focus();
		      }
		  }
        function checkType(event){
        	var e=Event.element(event);
        	jQuery("#specNumber,#fowNotOrderPercent,#fowOrderPercent").attr("disabled","true").val("");
        	if(e.value=="spec_number"){
        		jQuery("#specNumber").removeAttr("disabled");
        		jQuery("#dist_type").val("specNumber");
        	}else if(e.value=="not_order"){
        		jQuery("#fowNotOrderPercent").removeAttr("disabled");
        		jQuery("#dist_type").val("fowNotOrderPercent");
        	}else if(e.value=="order"){
        		jQuery("#fowOrderPercent").removeAttr("disabled");
        		jQuery("#dist_type").val("fowOrderPercent");
        	}
        }
    </script>
</body>
</html>
