<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
<script type="text/javascript" src="./js/reportview_table.js"></script>  

   <div data-role="page" id="reportview_table" project_path="${ctx}"> 
   		<div data-role="header" data-theme="b">
        	<a href="reportview.jsp" data-ajax="false">返回</a>
   			<h1 align="center"> Table </h1>
            <button id="refreshTable" value="加载数据" data-mini="true"></button>
   		</div>
   		   		
   		<div data-role="content" data-theme="b" id="reportview_table_div_id">
            <table style="BORDER-RIGHT: #linen 1px dashed; BORDER-TOP: #linen 1px dashed; BORDER-LEFT: #linen 1px dashed; BORDER-BOTTOM: #linen 1px dashed; BORDER-COLLAPSE: collapse" borderColor=#linen height=40 cellPadding=1 width=100% align=center border=1 id="reportview_table_id" >
				
           </table>
           
   		</div> <!--  end content -->
   		
   		
   		<jsp:include page="divFooter.jsp"></jsp:include>  <!--  end footer -->
   	</div> <!-- end page -->
   	
<%@include file="footer.jsp" %>
