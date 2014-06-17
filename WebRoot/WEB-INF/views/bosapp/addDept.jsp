<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
  
   <div data-role="page" id="addDept"> 
   		<div data-role="header" data-theme="b">
   			<a href="deptManager.jsp" data-role="button" data-theme="d">返回</a>
   			<h1 align="center"> 部门添加 </h1>
   		</div>
   		
   		
   		<div data-role="center" data-theme="b">
   			<div data-role="fieldcontain">
					<label for="deptId">部门&nbsp;&nbsp;I&nbsp;D&nbsp;:</label>
					<input type="text" name="deptId" id="deptId" value=""  data-theme="e"/>   						
   			</div>
   					
   			<div data-role="fieldcontain">
   					<label for="deptName">部门名称:</label>
					<input type="text"  name="deptName" id="deptName" value=""  data-theme="e"/>  
   			</div>
   			
   			<div class="ui-grid-a">
   				<div class="ui-block-a">
   					<button data-theme="b" id="addDeptBtn">增&nbsp;&nbsp;加</button>
   				</div>
   				<div class="ui-block-b">
   					<a data-role="button" href="deptManager.jsp" >返&nbsp;&nbsp;回</a>
   				</div>
   			</div>
   			
   		</div>
 		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   </div>
   
   </body>
</html>