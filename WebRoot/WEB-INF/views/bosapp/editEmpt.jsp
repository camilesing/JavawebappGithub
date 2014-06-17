<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
  
   <div data-role="page" id="emptList"> 
   		<div data-role="header" data-theme="b" data-position="fixed">
   			<a href="#" data-rel="back"  data-theme="d" data-role="button">返&nbsp;&nbsp;&nbsp;&nbsp;回</a>
   			<h1 align="center">资料修改</h1>
   		</div>   
   		
   		<div data-role="content"  data-theme="b" >
   			<div id="searchEmptPage">
   					<table width="100%">
   						<tbody>
   							<tr>
   								<td> 姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名: </td>
   								<td> <input type="text" name="searName" id="searName" data-theme="e"/> </td>
   							</tr>
   							<tr>
   								<td> 身份证号码: </td>
   								<td> <input type="text" name="searCardNo" id="searCardNo" data-theme="e"/> </td>
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
   								<td> 在&nbsp;职&nbsp;状&nbsp;&nbsp;态: </td>
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
   			</div>   			
   			
   		 			
   			<div style="display:none" id="editEmptPage">
   					
		   			<div data-role="fieldcontain">
							<label for="name">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
							<input type="text" name="name" id="name" value="" data-theme="e"/>   						
		   			</div>
		   			
		   			
		   			<div data-role="fieldcontain">
							<label for="cardNo">身份证号码:</label>
							<input type="text" name="cardNo" id="cardNo" value="" readonly="readonly"/>   						
		   			</div>
		   			
		   			
		   			<div data-role="fieldcontain">
							<label for="birtyDate">出&nbsp;生&nbsp;日&nbsp;期&nbsp;:</label>
							<input type="text" name="birtyDate" id="birtyDate" value="" readonly="readonly"/>   						
		   			</div>
		   			
		   			
		   			
		   			<div data-role="fieldcontain">
							<label for="age">年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄:</label>
							<input type="text" name="age" id="age" value="" readonly="readonly"/>
		   			</div> 
		   			 
		   			
		   			
		   			<div data-role="fieldcontain">  
		   			 	 <fieldset data-role="controlgroup" data-type="horizontal" data-role="fieldcontain">		
			   					<legend>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</legend>
			   					<input type="radio" name="sex" id="male" value="1" />
								<label for="male">男</label>
								
								<input type="radio" name="sex" id="woman" value="0"/>
								<label for="woman">女</label>	
						</fieldset>					 						
		   			</div>
		   			
		   			
		   			<div data-role="fieldcontain">
							<label for="mobilePhone">手&nbsp;机&nbsp;号&nbsp;码&nbsp;:</label>
							<input type="text" name="mobilePhone" id="mobilePhone" value="" data-theme="e"/>   						
		   			</div>
		   			
		   			
		   			<div data-role="fieldcontain">
							<label for="presetAddress">现居住地址:</label>
							<input type="text" name="presetAddress" id="presetAddress" value="" data-theme="e"/>   						
		   			</div>
		   			
		   			
		   			<div data-role="fieldcontain">
							<label for="homeAddress">家&nbsp;庭&nbsp;住&nbsp;址:</label>
							<input type="text" name="homeAddress" id="homeAddress" value=""/> 
		   			</div>
		   			
		   			
		   			
		   			<div data-role="fieldcontain">
							<label for="homeTel">家&nbsp;庭&nbsp;电&nbsp;话:</label>
							<input type="text" name="homeTel" id="homeTel" value=""/>   						
		   			</div>
		   			
		   			 
		   			<div data-role="fieldcontain">
						<label for="status">是&nbsp;否&nbsp;在&nbsp;职:</label>
						<select name="status" id="status" data-role="slider">
							<option value="Y">在职</option>
							<option value="N">离职</option>
						</select> 
					</div>
		   			
		   			
		   			<div data-role="fieldcontain">
							<label for="deptId">所&nbsp;在&nbsp;部&nbsp;门&nbsp;:</label>
							<select name="deptId" id="deptId">
								<option value="--">请选择</option>
							</select>					
		   			</div>
		   			   
		   			<div data-role="fieldcontain">
							<label for="postId">所&nbsp;在&nbsp;职&nbsp;务&nbsp;:</label>
							<select name="postId" id="postId">
								<option value="--">请选择</option>
							</select>					
		   			</div>
		   		
		   			<div data-role="fieldcontain">
							<label for="remark">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label>
							<textarea name="remark" id="remark"></textarea>		
		   			</div>
		   			
		   						
		   			<div class="ui-grid-a">
		   				<div class="ui-block-a">   				
		   					<button data-theme="b" id="editEmptBtn">修&nbsp;&nbsp;改</button>
		   				</div>
		   				<div class="ui-block-b">
		   					<a data-role="button" href="#" data-rel="back" data-theme="d" >返&nbsp;&nbsp;回</a>
		   				</div>
		   			</div>
   			</div>   	

   		</div> <!--  end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   		
   	</div> <!--  end page -->	
   		
<%@include file="footer.jsp" %>