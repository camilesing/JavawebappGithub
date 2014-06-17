<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
  
   <div id="reportview_ireport" data-role="page"  project_path="${ctx}">
   		<div data-role="header" data-theme="b">
        <a href="reportview.jsp" data-ajax="false">返回</a>
   			<h1 align="center"> Ireport </h1>
   		</div>
        <div data-role="content" data-theme="b">
            <label for="text-basic">分公司编码:</label>
			<input name="text-basic" id="store" value="03" type="text" data-clear-btn="true">
			<button id="refreshIreport" value="生成Ireport 图表" ></button>
   		</div> <!--  end content -->
   		
   		<jsp:include page="divFooter.jsp"></jsp:include>  <!--  end footer -->
   	</div> <!-- end page -->
   	
<%@include file="footer.jsp" %>
