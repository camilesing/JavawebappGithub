<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
  
   <div data-role="page" id="empManager"> 
   		<div data-role="header" data-theme="b">
   			<a href="home.jsp" data-role="button" data-theme="d">返回</a>
   			<h1 align="center"> 员工管理 </h1>
   		</div>
   		
   		
   		<div data-role="center" data-theme="b">
   			<table width ="100%" align="center" >
   				<tbody>
   					<tr>
   						<td  align="center">
   							 <a class="aBtnStyle" href="addEmpt.jsp" data-role="button" data-icon="plus"  data-iconpos="top" data-theme="d"> <br> 入职登记</a>
   						</td>
   						<td  align="center"> 
   							<a class="aBtnStyle" id="quitEmpt"  href="quitEmpt.jsp" data-icon="arrow-d" data-role="button" data-iconpos="top" data-theme="d"> <br> 离职登记</a> 
   						</td>
   						
   					</tr>
   					<tr>
   						<td  align="center"> 
   							<a class="aBtnStyle" href="emptDetailed.jsp" data-role="button" data-icon="gear" data-iconpos="top" data-theme="d"> <br> 职员详细 </a>
   						</td>
   						<td  align="center"> 
   							<a class="aBtnStyle" href="editEmpt.jsp" data-role="button" data-icon="check" data-iconpos="top" data-theme="d"> <br> 资料修改 </a>
   						</td>
   					</tr>
   					<tr>
   						<td  align="center"> 
   							<a class="aBtnStyle" href="#" data-role="button" data-icon="refresh" data-iconpos="top" data-theme="d"> <br> 密码管理  </a>
   						</td>
   						<td  align="center"> 
   							<a class="aBtnStyle" href="home.jsp" data-role="button" data-icon="back" data-iconpos="top" data-theme="d"> <br> 返&nbsp;&nbsp;回 </a>
   						</td>
   					</tr>
   				</tbody>
   			</table>
   		</div> <!--  end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
 </div> <!--  end page -->
   		
<%@include file="footer.jsp" %>
