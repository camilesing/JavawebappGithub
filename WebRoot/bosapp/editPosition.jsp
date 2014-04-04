<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>

  
   <div data-role="page" id="editPosition"> 
   		<div data-role="header" data-theme="b">
   			<a href="positionManager.jsp" data-role="button" data-theme="d">返回</a>
   			<h1 align="left"> 修改职务 </h1>
   		</div>
   		
   		
   		<div data-role="content" data-theme="b">
   			<div data-role="fieldcontain">
				<label for="postId" class="select">职务&nbsp;&nbsp;I&nbsp;D&nbsp;:</label>
   				<select name="postId" id="postId">
   					<option value="--" selected="selected">请选择</option>
   				</select>
   			</div>
   					
   			<div data-role="fieldcontain">
   					<label for="postName">职务名称:</label>
					<input type="text" name="postName" id="postName" value=""  data-theme="e"/>  
   			</div>
   			
   			<div class="ui-grid-a">
   				<div class="ui-block-a">
   					<button data-theme="b" id="updatePositionBtn">修&nbsp;&nbsp;改</button>
   				</div>
   				<div class="ui-block-b">
   					<a data-role="button" href="positionManager.jsp" >返&nbsp;&nbsp;回</a>
   				</div>
   			</div>
   			
   		</div> <!-- end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
  	</div> <!-- end page -->
   		
<%@include file="footer.jsp" %>