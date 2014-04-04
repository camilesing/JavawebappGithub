<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>

<%
	GetCurrnetDate currentDate = new GetCurrnetDate();
%>

   <div data-role="page" id="attenceSearchList" >
        <div data-role="header" data-theme="b">
        	<a href="#" data-rel="back" data-role="button" data-theme="d">返&nbsp;&nbsp;&nbsp;&nbsp;回</a>
        	<h1>考勤查询</h1>
        </div>	
        
    	<div data-role="content">
    	
    	  <div id="searchAttend">
	   		<table width="100%">
	   				<tbody>
	   					<tr>
	   						<td> 姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名: </td>
	   						<td> <input type="text" name="searName" id="searName"  /> </td>
	   					</tr>
	   					<tr>
	   						<td> 身份证号码: </td>
	   						<td> <input type="text" name="searCardNo" id="searCardNo" /> </td>
	   					</tr>
	   					<tr>
	   							<td> 部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门: </td>
	   							<td> 
	   								<select data-theme="b" name="searDeptId" id="searDeptId">
	   									<option value="all">不限</option>
	   								</select>
	   							</td>
	   					</tr>
	   					<tr>
	   						<td> 职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务: </td>
	   							<td> 
	   								<select data-theme="b" name="searPostId" id="searPostId">
	   										<option value="all">不限</option>
	   								</select>
	   							</td>
	   					</tr>
	   					
	   					<tr>
	   						<td colspan="2">&nbsp;<hr></td>
	   					</tr> 
	   					
	   					<tr>
	   						
	   						<td width="30%">起始时间：</td>
	   						<td width="70%" align="left">
	
	   							<select data-theme="b" name="startYear" id="startYear">
	   								<u:datetag startNumber="2010" stopNumber="<%=currentDate.getYear() %>" showTitle="年" selectNumber="<%=currentDate.getYear() %>"/>
	   							</select>
	   							
	   							<select data-theme="b" name="startMonth" id="startMonth">
	   								<u:datetag startNumber="1" stopNumber="12" showTitle="月" selectNumber="<%=currentDate.getMonth() %>"/>
	   							</select>
	   							
	   							<select data-theme="b" name="startDay" id="startDay">
	   								<u:datetag startNumber="1" stopNumber="31" showTitle="日" selectNumber="<%=currentDate.getDay() -1 %>"/>
	   							</select>
	   							
	   							<hr>
	   							
	   							<select data-theme="b" name="startHour" id="startHour">
	   								<u:datetag startNumber="0" stopNumber="23" showTitle="时" selectNumber="0"/>
	   							</select>
	   							
	   							<select data-theme="b" name="startMinute" id="startMinute">
	   								<u:datetag startNumber="0" stopNumber="59" showTitle="分" selectNumber="0"/>
	   							</select> 
	   							
	   							<select data-theme="b" name="startSecond" id="startSecond">
	   								<u:datetag startNumber="0" stopNumber="59" showTitle="秒" selectNumber="0"/>
	   							</select> 
	   							
	   						</td>
	   					</tr>
	   					
	   					
	   					<tr>
	   						<td colspan="2">&nbsp;<hr></td>
	   					</tr>  
	   					
	   					<tr>
	   						<td width="30%">结束时间：</td>
	   						<td width="70%" align="left">
	   							<select data-theme="b" name="stopYear" id="stopYear">
	   								<u:datetag startNumber="2010" stopNumber="<%=currentDate.getYear() + 1 %>" showTitle="年" selectNumber="<%=currentDate.getYear() %>"/>
	   							</select>
	   							
	   							<select data-theme="b" name="stopMonth" id="stopMonth">
	   								<u:datetag startNumber="1" stopNumber="12" showTitle="月" selectNumber="<%=currentDate.getMonth() %>"/>
	   							</select>
	   							
	   							<select data-theme="b" name="stopDay" id="stopDay">
	   								<u:datetag startNumber="1" stopNumber="31" showTitle="日" selectNumber="<%=currentDate.getDay() %>"/>
	   							</select>
	   							<hr>
	   							<select data-theme="b" name="stopHour" id="stopHour">
	   								<u:datetag startNumber="0" stopNumber="23" showTitle="时" selectNumber="2"/>
	   							</select>
	   							
	   							<select data-theme="b" name="stopMinute" id="stopMinute">
	   								<u:datetag startNumber="0" stopNumber="59" showTitle="分" selectNumber="0"/>
	   							</select> 
	   							
	   							<select data-theme="b" name="stopSecond" id="stopSecond">
	   								<u:datetag startNumber="0" stopNumber="59" showTitle="秒" selectNumber="0"/>
	   							</select> 
	   							
	   						</td>
	   					</tr> 					 
	   				</tbody>
	   			</table>
   				<hr>
   				<a href="#" id="searchBtn" data-role="button" data-theme="b" data-icon="search" data-iconpos="right">搜索</a>
   				<a href="home.jsp" data-role="button" data-theme="d"  data-icon="back" data-iconpos="right">返&nbsp;&nbsp;&nbsp;&nbsp;回</a>    
   	    </div> <!-- end searchAttend  -->	
   	    
   	    <div style="display:none" id="attendList">
   	    	<table width="100%">
   	    		<thead>
   	    			<tr>
   	    				<th width="20%" align="center"><b>姓名</b></th>
   	    				<th  width="80%" align="center"><b>打卡时间</b></th>
   	    			</tr>
   	    			<tr>
   	    				<td colspan="2">
   	    					<hr>
   	    				</td>
   	    			</tr>
   	    		</thead>
   	    		<tbody id="attendBody">
   	    			

   	    		</tbody>
   	    	</table> 
   	    	<hr>
   	    	<a href="#" data-role="button" id="loadOthers" data-theme="a">加载更多</a>  	    	
   	    </div> <!--  end attendList -->
   					
    	</div>	<!-- end content -->
    	<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
  </div> <!--  end page -->
    	
<%@include file="footer.jsp" %>
