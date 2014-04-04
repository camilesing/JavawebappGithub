<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>

   <div data-role="page" id="addEmpt"> 
   		<div data-role="header" data-theme="b">
   			<a href="#" data-rel="back" data-role="button" data-theme="d">返回</a>
   			<h1 align="center"> 添加员工 </h1>
   		</div>
   		
   		<div data-role="content" data-theme="b">
   			<!-- 姓名 -->   			
   			<div data-role="fieldcontain">
					<label for="name">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:</label>
					<input type="text" name="name" id="name" value="" data-theme="e"/>   						
   			</div>
   			
   			<!-- 身份证号码 -->
   			<div data-role="fieldcontain">
					<label for="cardNo">身份证号码:</label>
					<input type="text" name="cardNo" id="cardNo" value="" data-theme="e"/>   						
   			</div>
   			
   			<!-- 性别 -->
   			<div data-role="fieldcontain">  
   			 	 <fieldset data-role="controlgroup" data-type="horizontal" data-role="fieldcontain">		
	   					<legend>性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别:</legend>
	   					<input type="radio" name="sex" id="male" value="1" checked="checked" />
						<label for="male">男</label>
						
						<input type="radio" name="sex" id="woman" value="0"/>
						<label for="woman">女</label>	
				</fieldset>					 						
   			</div>
   			
   			<!-- 手机号码 -->
   			<div data-role="fieldcontain">
					<label for="mobilePhone">手&nbsp;机&nbsp;号&nbsp;码&nbsp;:</label>
					<input type="text" name="mobilePhone" id="mobilePhone" value="" data-theme="e"/>   						
   			</div>
   			
   			<!-- 现居住地址 -->
   			<div data-role="fieldcontain">
					<label for="presetAddress">现居住地址:</label>
					<input type="text" name="presetAddress" id="presetAddress" value="" data-theme="e"/>   						
   			</div>
   			
   			<!-- 家庭地址 -->
   			<div data-role="fieldcontain">
					<label for="homeAddress">家&nbsp;庭&nbsp;住&nbsp;址:</label>
					 						
					<input type="text" name="homeAddress" id="homeAddress" value=""/> 
   			</div>
   			
   			<!-- 家庭电话 -->
   			<div data-role="fieldcontain">
					<label for="homeTel">家&nbsp;庭&nbsp;电&nbsp;话:</label>
					<input type="text" name="homeTel" id="homeTel" value=""/>   						
   			</div>
   			
   			<!-- 民族 -->
   			<div data-role="fieldcontain">
					<label for="nationId">民&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;族:</label>
					<select name="nationId" id="nationId">
						<option value="--" selected="selected">请选择</option>
					</select>					
   			</div>
   			
   			<!-- 所在部门 -->
   			<div data-role="fieldcontain">
					<label for="deptId">所&nbsp;在&nbsp;部&nbsp;门&nbsp;:</label>
					<select name="deptId" id="deptId">
						<option value="--" selected="selected">请选择</option>
					</select>					
   			</div>
   			
   			<!-- 所在职务 -->
   			<div data-role="fieldcontain">
					<label for="postId">所&nbsp;在&nbsp;职&nbsp;务&nbsp;:</label>
					<select name="postId" id="postId">
						<option value="--" selected="selected">请选择</option>
					</select>					
   			</div>
   			
   			<!-- 备注  -->
   			<div data-role="fieldcontain">
					<label for="remark">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label>
					<textarea name="remark" id="remark"></textarea>		
   			</div>
   			
   			   			
   			<div class="ui-grid-a">
   				<div class="ui-block-a">   				
   					<button data-theme="b" id="addEmptBtn">增&nbsp;&nbsp;加</button>
   				</div>
   				<div class="ui-block-b">
   					<a data-role="button" data-rel="back" href="#" data-theme="d" >返&nbsp;&nbsp;回</a>
   				</div>
   			</div>
   		</div> <!-- end content -->
 		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   </div>
	
<%@include file="footer.jsp" %>