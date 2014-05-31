<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>  
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    <script src="${ctx}/resources/jquery/jquery-1.7.1.js" type="text/javascript"></script>
    <script src="${ctx}/resources/jquery/jquery.json-2.2-min.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(function () {
            $("#jsonsubmit").click(function () {
                var data = $("#json_form").serializeArray(); //自动将form表单封装成json
				

            	$.ajax({
	                type: "POST",   //访问WebService使用Post方式请求
	                contentType: "application/json", //WebService 会返回Json类型
	                url: "${ctx}/form_action_requestbody", //调用WebService的地址和方法名称组合 ---- WsURL/方法名
	                data: data,         //这里是要传递的参数，格式为 data: "{paraName:paraValue}",下面将会看到      
	                dataType: 'json',
	                success: function (result) {     //回调函数，result，返回值
	                    alert(result.UserName + result.Mobile + result.Pwd);
	                }
                });

               // $.post("/form_action_requestbody", data, RateArticleSuccess, "json");
            });
        })
	
	</script>
	
  </head>
  
  <body>
    This is my JSP page... 1 <br>
    
    add from com1   
     
    <a href="HelloWorld.do" >HelloWorld</a>
    
    <form:form name="form_common" modelAttribute="form_model" action="${ctx}/form_action/pojo_value" method="post">  
	    <input type="text" name="username" value="" />  
	    <input type="text" name="password" value="" />  
	  	<input type="submit" value="提交" > 
  	</form:form>
  	
  	<form action="" id="json_form" >
  		<input type="text" name="_instance.username" value="" />
	    <input type="text" name="_instance.password" value="" />  
	  	<input type="button" value="JSON提交"  id="jsonsubmit">
  	</form>
  	
  	<form action="${ctx}/form_action_pojo"  >
  		<input type="text" name="username" value="" />
	    <input type="text" name="password" value="" />  
	  	<input type="submit" value="POJO提交" >
  	</form>
  	
  </body>
</html>
