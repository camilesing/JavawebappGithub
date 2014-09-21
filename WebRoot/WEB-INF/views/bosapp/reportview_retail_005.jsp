<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
	
<script type="text/javascript" src="${ctx}/resources/js/bosapp/reportview_retail_005.js" ></script>

   <div data-role="page" id="reportview_table" project_path="${ctx}"> 
   		<div data-role="header" data-theme="b">
        	<a href="reportview" data-ajax="false">返回</a>
   			<h1 align="center"> 销售出库分析(同环比) </h1>
            <a href="#" id="refreshTable" class="ui-btn-right ui-btn ui-btn-inline ui-mini ui-corner-all ui-btn-icon-right ui-icon-refresh">加载数据</a>
   		</div>
   		   		
   		<div data-role="content" data-theme="c" id="reportview_table_div_id" style="margin: 0;border: 0;padding: 0">
	   		<table width=100%>
				<tr>
			      <td>统计年月</td>
			      <td><input type="text" name="date_start" id="date_start" /></td>
			      <td>-</td>
			      <td>-</td>
			    </tr> 
			</table>
			<div data-role="collapsible-set" data-theme="c" data-content-theme="d" >
			    <span id="reportview_table_count"></span>			
					<table style="BORDER-RIGHT: #linen 1px dashed; BORDER-TOP: #linen 1px dashed; BORDER-LEFT: #linen 1px dashed; BORDER-BOTTOM: #linen 1px dashed; BORDER-COLLAPSE: collapse;font-size: 11px" borderColor=#linen height=40 cellPadding=1 width=100% align=center border=1 id="reportview_table_id" >
							
			        </table>
			</div>
           
   		</div> <!--  end content -->
   		
   		
   		<jsp:include page="divFooter.jsp"></jsp:include>  <!--  end footer -->
   	</div> <!-- end page -->
   	
<%@include file="footer.jsp" %>
