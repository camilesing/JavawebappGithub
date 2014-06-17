<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
  
   <div data-role="page" id="addPosition"> 
   		<div data-role="header" data-theme="b">
   			<a href="positionManager.jsp" data-role="button" data-theme="d">返回</a>
   			<h3 align="left"> 职务添加 </h3>
   		</div>
   		
   		
   		<div data-role="content" data-theme="b">
   			<div data-role="fieldcontain">
					<label for="postId">职务&nbsp;&nbsp;I&nbsp;D&nbsp;:</label>
					<input type="text" name="postId" id="postId" value=""  data-theme="e"/>   						
   			</div>
   					
   			<div data-role="fieldcontain">
   					<label for="postName">职务名称:</label>
					<input type="text" name="postName" id="postName" value=""  data-theme="e"/>  
   			</div>
   			
   			<div class="ui-grid-a">
   				<div class="ui-block-a">
   					<button data-theme="b" id="addPositionBtn">增&nbsp;&nbsp;加</button>
   				</div>
   				<div class="ui-block-b">
   					<a data-role="button" href="positionManager.jsp" data-theme="d">返&nbsp;&nbsp;回</a>
   				</div>
   			</div>
   			
   		</div> <!--  end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   	</div><!--  end page -->	
   		
<%@include file="footer.jsp" %>