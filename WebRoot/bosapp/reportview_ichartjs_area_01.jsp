<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
<script type="text/javascript" src="./js/reportview_ichartjs_area_01.js"></script>
  
   <div data-role="page" id="reportview_ichartjs_area_01" project_path="${ctx}"> 
   		<div data-role="header" data-theme="b">
        	<a href="reportview_ichartjs.jsp" data-ajax="false">Back Ichartjs</a>
   			<h1 align="center">报表浏览 </h1>
   		</div>
   		   		
   		<div data-role="content" data-theme="b">
   			<div id='ichartjs_area_01DIV' ></div>
            <label for="text-basic">分公司编码:</label>
			<input name="text-basic" id="store" value="03" type="text" data-clear-btn="true">
			<button id="refreshIchartjs" value="生成Ichartjs 图表" ></button>                   
   		</div> <!--  end content -->
   		
   		
   		<jsp:include page="divFooter.jsp"></jsp:include>  <!--  end footer -->
   	</div> <!-- end page -->
   	
<%@include file="footer.jsp" %>
