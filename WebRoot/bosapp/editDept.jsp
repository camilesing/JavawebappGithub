<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>

  
   <div data-role="page" id="editDept"> 
   		<div data-role="header" data-theme="b">
   			<a href="/deptManager.jsp" data-role="button" data-theme="d">返回</a>
   			<h1 align="left"> 修改部门 </h1>
   		</div>
   		
   		
   		<div data-role="center" data-theme="b">
   			<div data-role="fieldcontain">
				<label for="deptId" class="select">部门&nbsp;&nbsp;I&nbsp;D&nbsp;:</label>
   				<select name="deptId" id="deptId" data-theme="b">
   					<option value="--" selected="selected">请选择</option>
   				</select>
   			</div>
   					
   			<div data-role="fieldcontain">
   					<label for="deptName">部门名称:</label>
					<input type="text" name="deptName" id="deptName" value=""  data-theme="e" />  
   			</div>
   			
   			<div class="ui-grid-a">
   				<div class="ui-block-a">
   					<button data-theme="b" id="updateDeptBtn">修&nbsp;&nbsp;改</button>
   				</div>
   				<div class="ui-block-b">
   					<a data-role="button" href="/deptManager.jsp" >返&nbsp;&nbsp;回</a>
   				</div>
   			</div>
   			
   		</div> <!--  end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
	</div> <!-- end page -->
   		
<%@include file="footer.jsp" %>