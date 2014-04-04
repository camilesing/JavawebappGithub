<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
  
   <div data-role="page" id="positionList"> 
   		<div data-role="header" data-theme="b" data-position="fixed">
   			<a href="#" data-rel="back" data-role="button">返&nbsp;&nbsp;&nbsp;&nbsp;回</a>
   			<h1 align="center">职务详细信息列表</h1>
   		</div>   
   		
   		<div data-role="content"  data-theme="b" >
   			<table width="100%">
   				<tbody id="txt">
   					
   				</tbody>
   			</table>	
   		</div> <!--  end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   	</div> <!--  end page -->	
   		
<%@include file="footer.jsp" %>