<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
  
   <div data-role="page" id="quitSearch"> 
   		<div data-role="header" data-theme="b" data-position="fixed">
   			<a href="#" data-rel="back" data-role="button" data-theme="c">返&nbsp;&nbsp;&nbsp;&nbsp;回</a>
   			<h1 align="center">批量离职登记</h1>
   			<a id="submit" style="display:none" href="#"  data-role="button" data-theme="c">提&nbsp;&nbsp;&nbsp;&nbsp;交</a>
   		</div>   
   		
   		<div data-role="content"  data-theme="b" >
   			<div id="searchQuitEmptPage">
   					<table width="100%">
   						<tbody>
   							<tr>
   								<td> 姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名: </td>
   								<td> <input type="text" name="searName" id="searName" data-theme="b"/> </td>
   							</tr>
   							<tr>
   								<td> 身份证号码: </td>
   								<td> <input type="text" name="searCardNo" id="searCardNo" data-theme="b"/> </td>
   							</tr>
   							<tr>
   								<td> 部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门: </td>
   								<td> 
   									<select name="searDeptId" id="searDeptId">
   										<option value="all">不限</option>
   									</select>
   								 </td>
   							</tr>
   							<tr>
   								<td> 职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务: </td>
   								<td> 
   									<select name="searPostId" id="searPostId">
   										<option value="all">不限</option>
   									</select>
   								 </td>
   							</tr>
   							
   							<tr>
   								<td>是&nbsp;否&nbsp;在&nbsp;职&nbsp;: </td>
   								<td> 
   									<select name="searStatus" id="searStatus">
   										<option value="all">不限</option>
   										<option value="Y">在职</option>
   										<option value="N">离职</option>
   									</select>
   								 </td>
   							</tr>
   							
   							<tr>
   								<td colspan="2">
   									<div class="fieldcontain">
				   					<input type="button" id="btnSearch" value="搜索"/>
   									<a data-role="button" data-rel="back" id="btnReturn" data-theme="d">返回</a>
   									</div>
   								</td>
   							</tr>
   						</tbody>
   					</table>
   					<hr>
   			</div>   <!--  end  searchQuitEmptPage -->			
   		 			
   			<div  id="quitEmployeeManagerPage" style="display:none">
   				<table width="100%">
   					<thead>
   						<tr>
   							<th align="center">姓名</th>
   							<th align="center">部门</th>
   							<th align="center">职务</th>
   							<th align="center">状态</th>
   						</tr>
   					</thead>
   					
   					<tbody id="employess">
   					</tbody>
   				</table>
   				<br>
   				<hr>
   				<center>
   					<a id="loadEndMsg" style="display:none"> <h2>全部加载完毕</h2> </a>
   				</center>
   				<a id="btnLoadOthers" data-theme="a" data-icon="Plus" data-iconpos="right" data-role="button">加载更多...</a>
   			</div> <!-- end quitEmptPage div -->   	

   		</div> <!--  end content -->
   		 <jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   	</div> <!--  end page -->	
   		
<%@include file="footer.jsp" %>