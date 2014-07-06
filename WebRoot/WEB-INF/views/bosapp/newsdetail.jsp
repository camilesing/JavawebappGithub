<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>

<script type="text/javascript" src="${ctx}/resources/js/bosapp/newsdetail.js" ></script>
  
   <div data-role="page" id="newsdetail" project_path="${ctx}"> 
   		<div data-role="header" data-theme="b">
        	<a href="news" data-ajax="false">返回</a>
   			<h1 align="center">消息详情</h1>
   		</div>
   		
   		<div data-role="content" >
			<h3 id="newsdetail_title" align="center">1111111</h3>
			<p class="notep"></p>
			<p id="newsdetail_content">1111111</p>
   		</div> <!--  end content -->
   		
   		<jsp:include page="divFooter.jsp"></jsp:include>  <!--  end footer -->
   	</div> <!-- end page -->
   	
<%@include file="footer.jsp" %>
