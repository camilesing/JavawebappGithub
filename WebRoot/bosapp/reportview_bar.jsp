<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
	  
   <div id="reportview_bar" data-role="page" data-rockncoder-jspage="manageBarChart" project_path="${ctx}">
   		<div data-role="header" data-theme="b">
        <a href="reportview.jsp" data-ajax="false">返回</a>
   			<h1 align="center"> 柱状图 </h1>
   		</div>
        <div data-role="content" data-theme="b">
            <div id="barChart" class="myChart"></div>
			<button id="refreshBarChart" value="加载数据" data-mini="true"></button>
            <label for="text-basic">店仓:</label>
			<input name="text-basic" id="store" value="" type="text" data-clear-btn="true">
			<label for="date">开始日期-结束日期:</label>
            <input type="date" data-clear-btn="true" name="date_start" id="date_start" value="">
            <input type="date" data-clear-btn="true" name="date_end" id="date_end" value="">			           
   		</div> <!--  end content -->
   		
   		<jsp:include page="divFooter.jsp"></jsp:include>  <!--  end footer -->
   	</div> <!-- end page -->
   	
<%@include file="footer.jsp" %>
