<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
<%!
	String weixinid = "";
%>
<% 
	weixinid = request.getParameter("weixinid");
%>
<script type="text/javascript" src="./jquery-mobile/jquery.md5.js"></script> 
<script type="text/javascript" src="./js/index.js"></script>
<%-- start Home Page --%>
    <div data-role="page" id="login" >
    	<div data-role="content">
    			<h1> <center> <b>管理系统</b> </center> </h1>
    			<form id="loginForm" action="${ctx}/login" project_path="${ctx}" quick_login="${ctx}/login_bosapp">
    				<div data-role="fieldcontain">
    					<label for="labUserName">用户名:</label>
    					<input type="text" name="userName" id="userName" value="admin"/>
    					<input type="hidden" name="weixinid" id="weixinid" value="<%=weixinid%>" >
    				</div>
    				
    				<div data-role="fieldcontain">
    					<label for="labUserPwd">密&nbsp;&nbsp;&nbsp;码:</label>
    					<input type="password" name="userPwd" id="userPwd" value="1"/>
    				</div>
    				
    				<div class="ui-grid-a">
    					<div class="ui-block-a">
    						<button id="loginBtn" data-theme="b">登&nbsp;&nbsp;录</button>
    					</div>
    					<div class="ui-block-b">
    						<input type="reset" value="重&nbsp;&nbsp;置" id="resetBtn"/>
    					</div>    					    					
    				</div>
    				
    				<div >
    					<button id="quickBtn" data-theme="b">已绑定登陆</button>   					    					
    				</div>
    				
    			</form>    			
    	</div>	<!-- end content -->
    	<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
    </div> <!-- end page -->	
<%@include file="footer.jsp" %>