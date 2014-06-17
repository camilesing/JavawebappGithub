<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>

    <%-- start Login Error Page --%>
    <div data-role="dialog" id="success">    
    	<div data-role="header" data-theme="b">
    		<h1>提示信息</h1>
    	</div>
    	
    	<div data-role="content" data-theme="b">
    		<center>操作失败,请返回</center>
    		<center>    		
    			<a href="#" data-role="button" data-rel="back" data-inline="true">返&nbsp;&nbsp;回</a>
    		</center>
    	</div>
    </div> <!-- end dialog -->
<%@include file="footer.jsp" %>