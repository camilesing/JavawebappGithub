<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
<%!
	String weixinid = "";
%>
<% 
	weixinid = request.getParameter("weixinid");
	weixinid = weixinid==null?"":weixinid;
%>
<script type="text/javascript" src="${ctx}/resources/js/bosapp/index.js?rnd="+Math.random() ></script>

<style>
<!--
.domain{position:absolute}
.domain{padding-left:5px;width:75px;height:33px;line-height:33px;color:#616161;font-size:14px;overflow:hidden;display:block;right:8px;top:4px;white-space:nowrap;}
-->
</style>



<%-- start Home Page --%>
    <div data-role="page" id="login" >
    	<div data-role="content">
    			<h1> <center> <b>管理系统</b> </center> </h1>
    			<form id="loginForm" action="${ctx}/login_bosapp" ProjectUrl="${ctx}" QuickLogin="${ctx}/login_bosapp">
    				<div data-role="fieldcontain">
    					<label for="userName">用户名:</label>
    					<input type="text" name="userName" id="userName" value="" /><span style="width: 75px;font-weight:lighter;color:gray;" class="domain">@lne.com</span>
    					<input type="hidden" name="weixinid" id="weixinid" value="<%=weixinid%>" >
    				</div>
    				
    				<div data-role="fieldcontain">
    					<label for="userPwd">密&nbsp;&nbsp;&nbsp;码:</label>
    					<input type="password" name="userPwd" id="userPwd" value=""/>
    				</div>
    				
    				<div class="ui-grid-a">
    					<div class="ui-block-a">
    						<button id="loginBtn" data-theme="a">登&nbsp;&nbsp;录</button>
    					</div>
    					<div class="ui-block-b">
    						<input type="reset" value="重&nbsp;&nbsp;置" id="resetBtn"/>
    					</div>    					    					
    				</div>
    				
    				<div >
    					<button id="quickBtn" data-theme="b">快速登陆</button>
    				</div>
    				
    			</form>    			
    	</div>	<!-- end content -->
    	<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
    </div> <!-- end page -->	
    
<%@include file="footer.jsp" %>