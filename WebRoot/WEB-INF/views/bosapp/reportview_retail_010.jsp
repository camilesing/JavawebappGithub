<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
	
<script type="text/javascript" src="${ctx}/resources/js/bosapp/reportview_retail_010.js" ></script>

   <div data-role="page" id="reportview_table" project_path="${ctx}"> 
   		<div data-role="header" data-theme="b">
        	<a href="reportview" data-ajax="false">返回</a>
   			<h1 align="center"> 零售周数据分析 </h1>
            <a href="#" id="refreshTable" class="ui-btn-right ui-btn ui-btn-inline ui-mini ui-corner-all ui-btn-icon-right ui-icon-refresh">加载数据</a>
   		</div>
   		   		
   		<div data-role="content" data-theme="c" id="reportview_table_div_id" style="margin: 0;border: 0;padding: 0">
			<div data-role="collapsible-set" data-theme="c" data-content-theme="d" >
			    <div data-role="collapsible" data-collapsed="false">
			        <h3>图表</h3>
			        <div id='ichartjs_content' style="margin: 0px 0px 0px 0px;border: 0;padding: 0" >点击加载按钮</div>
			        <div style="margin: 0px 0px 0px 0px;border: 0;padding: 0" >
			        	<table width=100% >
			        		<tr>
			        			<td><span id="Data_T">&nbsp;</span></td>
			        			<td><span id="Data_Y">&nbsp;</span></td>
			        		</tr>
			        	</table>
			        </div>
			    </div>
			    <div data-role="collapsible" >
			        <h3>详细数据</h3>
			        <span id="reportview_table_count"></span>
					<table style="BORDER-RIGHT: #linen 1px dashed; BORDER-TOP: #linen 1px dashed; BORDER-LEFT: #linen 1px dashed; BORDER-BOTTOM: #linen 1px dashed; BORDER-COLLAPSE: collapse;font-size: 11px" borderColor=#linen height=40 cellPadding=1 width=100% align=center border=1 id="reportview_table_id" >
							
			        </table> 
			    </div>
			</div>
			
   		</div> <!--  end content -->
   		
   		
   		<jsp:include page="divFooter.jsp"></jsp:include>  <!--  end footer -->
   	</div> <!-- end page -->
   	
<%@include file="footer.jsp" %>
