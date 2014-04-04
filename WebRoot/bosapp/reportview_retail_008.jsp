<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
	
<script type="text/javascript" src="./js/reportview_retail_008.js"></script>

   <div data-role="page" id="reportview_table" project_path="${ctx}"> 
   		<div data-role="header" data-theme="b">
        	<a href="reportview.jsp" data-ajax="false">返回</a>
   			<h1 align="center"> 店铺人效分析 </h1>
            <button id="refreshTable" value="加载数据" data-mini="true"></button>
   		</div>
   		   		
   		<div data-role="content" data-theme="c" id="reportview_table_div_id" style="margin: 0;border: 0;padding: 0">
	   		<table width=100%>
				<tr>
			      <td>统计年份</td>
			      <td><input type="text" name="date_start" id="date_start" /></td>
			    </tr> 
			    <tr>
			    <td><button id="refreshCus" value="加载经销商" ></td>
			    <td colspan="4">
			    	<select name="select_cus_id" id="select_cus_id" multiple="multiple" data-native-menu="false" data-icon="grid" data-iconpos="left">
					    <option>经销商选择(选择小于6个)</option>
					</select>
			    </td>
			    </tr>
			</table>
			<div data-role="collapsible-set" data-theme="c" data-content-theme="d" >
			    <div data-role="collapsible" data-collapsed="false">
			        <h3>年连带率分析(经销商)</h3>
			        <div id='ichartjs_content' style="margin: 0px 0px 0px 0px;border: 0;padding: 0" >点击加载按钮</div>
			    </div>
			    <div data-role="collapsible" >
			        <h3>客单量、单价(<span id="reportview_table_count"></span>)</h3>
					<table style="BORDER-RIGHT: #linen 1px dashed; BORDER-TOP: #linen 1px dashed; BORDER-LEFT: #linen 1px dashed; BORDER-BOTTOM: #linen 1px dashed; BORDER-COLLAPSE: collapse;font-size: 11px" borderColor=#linen height=40 cellPadding=1 width=100% align=center border=1 id="reportview_table_id_01" >
							
			        </table> 
			    </div>
			    <div data-role="collapsible" >
			        <h3>连带率、人效</h3>
					<table style="BORDER-RIGHT: #linen 1px dashed; BORDER-TOP: #linen 1px dashed; BORDER-LEFT: #linen 1px dashed; BORDER-BOTTOM: #linen 1px dashed; BORDER-COLLAPSE: collapse;font-size: 11px" borderColor=#linen height=40 cellPadding=1 width=100% align=center border=1 id="reportview_table_id_02" >
							
			        </table> 
			    </div>
			</div>
			
			
			
	        
           
   		</div> <!--  end content -->
   		
   		
   		<jsp:include page="divFooter.jsp"></jsp:include>  <!--  end footer -->
   	</div> <!-- end page -->
   	
<%@include file="footer.jsp" %>
