<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>

<%
	GetCurrnetDate currentDate = new GetCurrnetDate();
%>

   <div data-role="page" id="attenceManager" >
        <div data-role="header" data-theme="b">
        	<a href="home.jsp" data-role="button" data-rel="back" data-theme="d">返&nbsp;&nbsp;&nbsp;&nbsp;回</a>  
        	<h1>考勤管理</h1>
        </div>	
        
    	<div data-role="content">
   			<table width="100%">
   				<tbody>
   					<tr>
   						<td align="center">
   							<a class="aBtnStyle" href="#" data-role="button" data-icon="plus"  data-iconpos="top" data-theme="d"> <br> 制度设置</a>
   						</td>
   						<td  align="center">
   							<a class="aBtnStyle" href="#" data-role="button" data-icon="info"  data-iconpos="top" data-theme="d"> <br> 班次设置</a>
   						</td>
   					</tr>
   					<tr>
   						<td align="center">
   							<a class="aBtnStyle" href="#" data-role="button" data-icon="plus"  data-iconpos="top" data-theme="d"> <br> 请假类型 </a>
   						</td>
   						<td  align="center">
   							<a class="aBtnStyle" href="#" data-role="button" data-icon="info"  data-iconpos="top" data-theme="d"> <br> 考勤报表</a>
   						</td>
   					</tr>
   					<tr>
   						<td align="center">
   							<a class="aBtnStyle" href="attenceSearchList.jsp" data-role="button" data-icon="plus"  data-iconpos="top" data-theme="d"> <br> 考勤查询 </a>
   						</td>
   						<td  align="center">
   							<a class="aBtnStyle" data-rel="back" href="#" data-role="button" data-icon="info"  data-iconpos="top" data-theme="d"> <br> 返&nbsp;&nbsp;&nbsp;回</a>
   						</td>
   					</tr>
   				</tbody>
   			</table>
    	</div>	<!-- end content -->
    	<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
  </div> <!--  end page -->
    	
<%@include file="footer.jsp" %>
