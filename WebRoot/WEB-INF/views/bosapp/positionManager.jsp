<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
  
   <div data-role="page" id="positionManager"> 
   		<div data-role="header" data-theme="b">
   			<a href="home.jsp" data-role="button" data-theme="d">返回</a>
   			<h1 align="center"> 职务管理 </h1>
   		</div>
   		
   		
   		<div data-role="center" data-theme="b">
   			<table width ="100%">
   				<tbody>
   					<tr>
   						<td  align="center"> 
   							<a class="aBtnStyle" href="addPosition.jsp" data-role="button" data-icon="plus"  data-iconpos="top" data-theme="d"> <br> 添加职务</a>
   						</td>
   						<td  align="center"> 
   							<a class="aBtnStyle" href="editPosition.jsp" data-role="button" data-icon="check" data-iconpos="top" data-theme="d"> <br> 修改职务名称 </a> 
   						</td>
   						
   					</tr>
   					<tr>
   						<td  align="center"> 
   							<a class="aBtnStyle" href="listPosition.jsp" data-icon="arrow-d" data-role="button" data-iconpos="top" data-theme="d"> <br> 查看职务名细</a> 
   						</td>
   						<td  align="center"> 
   							<a class="aBtnStyle" href="home.jsp" data-role="button" data-icon="back" data-iconpos="top"> <br> 返&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;回</a>
   						</td>
   					</tr>
   					
   				</tbody>
   			</table>   	
   					
   		</div> <!--  end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   </div> <!-- end page -->
   		
<%@include file="footer.jsp" %>
