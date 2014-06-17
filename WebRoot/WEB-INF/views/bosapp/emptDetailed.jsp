<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
  
   <div data-role="page" id="emptDetailed"> 
   		<div data-role="header" data-theme="b" data-position="fixed">
   			<a href="#" id="return" data-rel="back" data-role="button" data-theme="d">返&nbsp;&nbsp;&nbsp;&nbsp;回</a>
   			<h1 align="center">职员详细信息</h1>
   		</div>   
   		
   		<div data-role="content"  data-theme="c" >
   		
   			<div id="searchEmployessDiv">
   					<table width="100%">
   						<tbody>
   							<tr>
   								<td> 姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名: </td>
   								<td> <input type="text" name="searName" id="searName" /> </td>
   							</tr>
   							<tr>
   								<td> 身份证号码: </td>
   								<td> <input type="text" name="searCardNo" id="searCardNo"/> </td>
   							</tr>
   							<tr>
   								<td> 部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门: </td>
   								<td> 
   									<select name="searDeptId" id="searDeptId" data-theme="b">
   										<option value="all">不限</option>
   									</select>
   								 </td>
   							</tr>
   							<tr>
   								<td> 职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务: </td>
   								<td> 
   									<select name="searPostId" id="searPostId" data-theme="b" >
   										<option value="all">不限</option>
   									</select>
   								 </td>
   							</tr>
   							
   							<tr>
   								<td> 在&nbsp;职&nbsp;状&nbsp;&nbsp;态: </td>
   								<td> 
   									<select name="searStatus" id="searStatus" data-theme="b" >
   										<option value="Y" selected="selected">在职</option>
   										<option value="N">离职</option>
   									</select>
   								 </td>
   							</tr>
   							<tr>
   								<td colspan="2">
   									<div class="fieldcontain">
				   					<input type="button" id="btnSearch" data-theme="b" value="搜索"/>
   									<a data-role="button" data-rel="back" id="btnReturn" data-theme="d">返回</a>
   									</div>
   								</td>
   							</tr>
   						</tbody>
   					</table>
   					<hr>
   			</div> <!--  end searchEmployessDiv -->
   		
   			<!-- 搜索数据展示结果 -->
   			<div style="display:none" id="listEmployeesDiv" class="content-primary">
				<ul data-role="listview" id="listShowEmployess"  data-split-icon="gear" data-split-theme="d" data-filter="true" data-filter-placeholder="Search name or deptname ..." data-inset="false">
	   				<%-- 具体数据内容 --%>
				</ul>
			</div><!-- content-primary -->			
			
			<!-- 显示职员详细信息 -->	
		   	<div  data-role="popup" id="showEmptDetailedDiv" data-overlay-theme="a" data-theme="b" style="max-width:400px" class="ui-corner-all">
		   		<div data-role="header"  data-theme="b" class="ui-corner-top">
		   			<h1 align="center">职员详息信息</h1>
   				</div>  
   				<div data-role="content" data-theme="d" style="padding:15px;" class="ui-corner-bottom"> 
			   		<table width="100%">
			   			<tbody>
			   				<tr>
			   					<td>
			   						<label for="name">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
			   					</td>
			   					<td>
			   						<input type="text"  id="name" value=""  readonly="readonly"/>
			   					</td>
			   				</tr>
			   				
			   				<tr>
			   					<td>
			   						<label for="cardNo">身份证号码:</label>
			   					</td>
			   					<td>
			   						<input type="text" id="cardNo" value="" readonly="readonly"/>  
			   					</td>
			   				</tr>
			   				
			   				<tr>
			   					<td>
			   						<label for="age">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄:</label>
			   					</td>
			   					<td>
			   						<input type="text"  id="age" value="" readonly="readonly"/>
			   					</td>   						
			   				</tr>
			   				
			   				<tr>
			   					<td>
			   						性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:
			   					</td>
			   					<td>
				   					<input type="text"  id="sex" data-theme="b"  value="" readonly="readonly"/>
			   					</td>
			   				</tr>
			   				
			   				<tr>
			   					<td>
			   						<label for="mobilePhone">手&nbsp;机&nbsp;号&nbsp;码&nbsp;:</label>
			   					</td>
			   					<td>
			   						<input type="text"   id="mobilePhone" value="" readonly="readonly"/>
			   					</td>   					
			   				</tr>
			   				
			   				<tr>
			   					<td>
			   						<label for="presetAddress">现居住地址:</label>
			   					</td>
			   					<td>
			   						<input type="text"  id="presetAddress" value="" readonly="readonly"/>
			   					</td>
			   				</tr>
			   				
			   				<tr>
			   					<td>
			   						<label for="homeAddress">家&nbsp;庭&nbsp;住&nbsp;址:</label>
			   					</td>
			   					<td>
			   						<input type="text"   id="homeAddress" value="" readonly="readonly"/> 
			   					</td>
			   				</tr>
			   				
			   				<tr>
			   					<td>
			   						<label for="homeTel">家&nbsp;庭&nbsp;电&nbsp;话:</label>
			   					</td>
			   					<td>
			   						<input type="text"  id="homeTel" value="" readonly="readonly"/> 
			   					</td>
			   				</tr>
			   				
			   				<tr>
			   					<td>
			   						<label for="postId">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门:</label>
			   					</td>
			   					<td>
			   						<input type="text"  id="deptId" value=""  data-theme="b" readonly="readonly"/>
			   					</td>
			   				</tr>
			   				
			   				<tr>
			   					<td>
			   						<label for="postId">职&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;务:</label>
			   					</td>
			   					<td>
			   						<input type="text"  id="postId" value=""  data-theme="b" readonly="readonly"/>
			   					</td>
			   				</tr>
			   				
			   				<tr>
			   					<td>
			   						<label for="status">是&nbsp;否&nbsp;在&nbsp;职:</label>
			   					</td>
			   					<td>
			   						<input type="text"  id="status" value=""  data-theme="b" readonly="readonly"/>
			   					</td>
			   				</tr>
			   				<tr>
			   					<td colspan="2">
			   							<a data-role="button" href="#" data-rel="back" data-theme="a" >返&nbsp;&nbsp;回</a>
			   					</td>
			   				</tr>  				
			   			</tbody>
			   		</table>
			   	</div>	
		   	</div> <!-- end showEmptDetailedDiv -->

   		</div> <!--  end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   		
   		
   	</div> <!--  end page -->	
   	
<%@include file="footer.jsp" %>