<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

	<!-- 自定义错误Dialog -->
	 <div data-role="dialog" id="my-dialog">
   		<div data-role="header" data-theme="b" data-title="error">
   			<h1 id="my-dialog-title"> </center></h1>
   		</div> <!-- end header -->
   		<div data-role="content" data-theme="b" data-title="error">
   				<center id="my-dialog-content">
   					
   				</center>
   				<center>
    				<a id="my-dialog-return" href="#" data-role="button" data-rel="back" data-inline="true">返&nbsp;&nbsp;回</a>
    			</center>
   		</div> <!-- end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   </div> <!-- end idErrorDialog -->
	
	

	  <%-- 用户退出提示--%>
   <div data-role="dialog" id="exitPage">
	   	<div data-role="header" data-theme="b" >
	   		<h1>提示信息</h1>
	   	</div>
	   	<div data-role="content" data-theme="b">
	   		<h2>
	   			<center>您确定要退出吗?</center>
	   			  <div class="ui-grid-a">
                      <div class="ui-block-a">
                           <a href="#" data-role="button" id="exitBtnOk">确定</a>
                      </div>
                      <div class="ui-block-b">
                          <a href="#" data-role="button" data-rel="back" >取消</a>
                      </div>
                  </div>
	   		</h2>
	   	</div>
	   	<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   </div>  <!-- end page -->

	<%-- 用户登录错误提示 --%>
    <div data-role="dialog" id="loginError">    
    	<div data-role="header" data-theme="b">
    		<h1>提示信息</h1>
    	</div>
    	
    	<div data-role="content" data-theme="b">
    		<center>用户名或密码错误,请重新输入!</center>
    		<center>    		
    			<a href="#" data-role="button" data-rel="back" data-inline="true">返&nbsp;&nbsp;回</a>
    		</center>
    	</div> <!--  end content -->
    	<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
    </div> <!-- end page -->	
    
      <%-- 没有权限时提示 --%>
    <div data-role="dialog" id="loginValidateError">    
    	<div data-role="header" data-theme="b">
    		<h1>提示信息</h1>
    	</div>
    	
    	<div data-role="content" data-theme="b">
    		<center>该用户已被禁用,请与管理员联系</center>
    		<center>    		
    			<a href="#" data-role="button" data-rel="back" data-inline="true">返&nbsp;&nbsp;回</a>
    		</center>
    	</div> <!--  end content -->
    	<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
    </div> <!-- end page -->
    
     <!-- 网络失败 -->
     <div data-role="dialog" id="networkError">    
    	<div data-role="header" data-theme="b">
    		<h1>提示信息</h1>
    	</div>
    	
    	<div data-role="content" data-theme="b">
    		<center>信息提交失败,请稍后重试!</center>
    		<center>    		
    			<a href="#" data-role="button" data-rel="back" data-inline="true">返&nbsp;&nbsp;回</a>
    		</center>
    	</div> <!--  end content -->
    	<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
    </div> <!-- end page -->
	
   <%-- id 不是数字或少于五位时提示 --%>
   <div data-role="dialog" id="idErrorDialog">
   		<div data-role="header" data-theme="b" data-title="error">
   			<h1>提示信息</center></h1>
   		</div> <!-- end header -->
   		<div data-role="content" data-theme="b" data-title="error">
   				<center>
   					id只能是&nbsp;5&nbsp;&nbsp;位的数字,例如:&nbsp;( 00008 ),并且id不能重复
   				</center>
   				<center>    		
    			<a href="#" data-role="button" data-rel="back" data-inline="true">返&nbsp;&nbsp;回</a>
    			</center>
   		</div> <!-- end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   </div> <!-- end idErrorDialog -->
   
   <%-- 未选择ID时的提示 --%>
   <div data-role="dialog" id="idSelectErrorDialog">
   		<div data-role="header" data-theme="b" data-title="error">
   			<h1>提示信息></h1>
   		</div> <!-- end header -->
   		<div data-role="content" data-theme="b" data-title="error">
   				<center>
						请至少选择一个ID
   				</center>
   			<center>    		
    			<a href="#" data-role="button" data-rel="back" data-inline="true">返&nbsp;&nbsp;回</a>
    		</center>
   		</div> <!-- end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   </div> <!-- end idErrorDialog -->
   
   <%-- ID重复时的提示 --%>
   <div data-role="dialog" id="idRepeatDialog">
   		<div data-role="header" data-theme="b" data-title="error">
   			<h1>提示信息</h1>
   		</div> <!-- end header -->
   		<div data-role="content" data-theme="b" data-title="error">
   		   <center>
				您输入的ID已存在!
   			</center>
   		<center>    		
    		<a href="#" data-role="button" data-rel="back" data-inline="true">返&nbsp;&nbsp;回</a>
    	</center>
   		</div> <!-- end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   </div> <!-- end idErrorDialog -->
   
   <%-- name的值是空时提示 --%>
    <div data-role="dialog" id="nameErrorDialog">
   		<div data-role="header" data-theme="b" data-title="error">
   			<h1>提示信息</h1>
   		</div> <!-- end header -->
   		<div data-role="content" data-theme="b" data-title="error">
   				<center>
   					您输入的:&nbsp; '名称'&nbsp;不能为空! &nbsp;
   				</center>
   			<center>    		
    			<a href="#" data-role="button" data-rel="back" data-inline="true">返&nbsp;&nbsp;回</a>
    		</center>
   		</div> <!-- end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   </div> <!-- end "nameErrorDialog" -->
   
   
  <%-- 此处主要针对添加员工处理 --%>
  <%-- 身份值的值是空时提示 --%>
    <div data-role="dialog" id="cardNoErrorDialog">
   		<div data-role="header" data-theme="b" data-title="error">
   			<h1>提示信息</h1>
   		</div> <!-- end header -->
   		<div data-role="content" data-theme="b" data-title="error">
   				<center>
   					您输入的身份证有误!
   				</center>
   			<center>    		
    			<a href="#" data-role="button" data-rel="back" data-inline="true">返&nbsp;&nbsp;回</a>
    		</center>
   		</div> <!-- end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   </div> <!-- end "cardNoErrorDialog" -->
   
   
   <%-- 员工姓名为空时提示 --%>
    <div data-role="dialog" id="empNameErrorDialog">
   		<div data-role="header" data-theme="b" data-title="error">
   			<h1>提示信息</h1>
   		</div> <!-- end header -->
   		<div data-role="content" data-theme="b" data-title="error">
   				<center>
   					姓名不能为空!
   				</center>
   			<center>    		
    			<a href="#" data-role="button" data-rel="back" data-inline="true">返&nbsp;&nbsp;回</a>
    		</center>
   		</div> <!-- end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   </div> <!-- end "empNameErrorDialog" -->
   
    <%-- 手机号码有误时提示 --%>
    <div data-role="dialog" id="empPhoneErrorDialog">
   		<div data-role="header" data-theme="b" data-title="error">
   			<h1>提示信息</h1>
   		</div> <!-- end header -->
   		<div data-role="content" data-theme="b" data-title="error">
   				<center>
   					输入的手机号码有误!
   				</center>
   			<center>    		
    			<a href="#" data-role="button" data-rel="back" data-inline="true">返&nbsp;&nbsp;回</a>
    		</center>
   		</div> <!-- end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   </div> <!-- end "empNameErrorDialog" -->
   
   
    <%-- 现居住地址是为空时提示  --%>
    <div data-role="dialog" id="empPresetAddressErrorDialog">
   		<div data-role="header" data-theme="b" data-title="error">
   			<h1>提示信息</h1>
   		</div> <!-- end header -->
   		<div data-role="content" data-theme="b" data-title="error">
   				<center>
   					现居住地址不能为空!
   				</center>
   			<center>    		
    			<a href="#" data-role="button" data-rel="back" data-inline="true">返&nbsp;&nbsp;回</a>
    		</center>
   		</div> <!-- end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   </div> <!-- end "empPresetAddressErrorDialog" -->
  
   
     <%-- 下拉列表框未选值时提示  --%>
    <div data-role="dialog" id="empSelectedErrorDialog">
   		<div data-role="header" data-theme="b" data-title="error">
   			<h1>提示信息</h1>
   		</div> <!-- end header -->
   		<div data-role="content" data-theme="b" data-title="error">
   				<center>
   					民族 &nbsp; 所在部门 &nbsp; 所在职务 &nbsp;&nbsp;不许为空!
   				</center>
   			<center>    		
    			<a href="#" data-role="button" data-rel="back" data-inline="true">返&nbsp;&nbsp;回</a>
    		</center>
   		</div> <!-- end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   </div> <!-- end "empPresetAddressErrorDialog" -->
  
   
    <%-- 身份证重复时提示 --%>
    <div data-role="dialog" id="cardNoRepeatErrorDialog">
   		<div data-role="header" data-theme="b" data-title="error">
   			<h1>提示信息</h1>
   		</div> <!-- end header -->
   		<div data-role="content" data-theme="b" data-title="error">
   				<center>
   					该身份证号码已经存在!
   				</center>
   			<center>    		
    			<a href="#" data-role="button" data-rel="back" data-inline="true">返&nbsp;&nbsp;回</a>
    		</center>
   		</div> <!-- end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   </div> <!-- end empPresetAddressErrorDialog" -->
   
   
     <%--搜索条件为空时提示 --%>
    <div data-role="dialog" id="searchWhereIsNullErrorDialog">
   		<div data-role="header" data-theme="b" data-title="error">
   			<h1>提示信息</h1>
   		</div> <!-- end header -->
   		<div data-role="content" data-theme="b" data-title="error">
   				<center>
   					搜索条件不能为空!
   				</center>
   			<center>    		
    			<a href="#" data-role="button" data-rel="back" data-inline="true">返&nbsp;&nbsp;回</a>
    		</center>
   		</div> <!-- end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include> <!-- end footer -->
   </div> <!-- end searchWhereIsNullErrorDialog" -->