<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>

<script type="text/javascript" src="./js/news.js"></script>
  
   <div data-role="page" id="news" project_path="${ctx}"> 
   		<div data-role="header" data-theme="b">
        	<a href="home.jsp" data-ajax="false">返回主页面</a>
   			<h1 align="center"> 收件箱 </h1>
   		</div>
   		   		
   		<div data-role="content" >
            <ul data-role="listview" data-split-icon="gear" data-split-theme="d" data-inset="true" id="news_ul">
               
            </ul>
            <div data-role="popup" id="deal" class="ui-content" style="max-width:280px" data-dismissible="false" >
                <h3>消息阅读</h3>
                <span id="newsdetail" ></span>
                <a href="#" data-rel="back" data-role="button" data-theme="a" data-icon="delete" data-iconpos="notext" class="ui-btn-left">Close</a>
            </div>
   		</div> <!--  end content -->
   		
   		<jsp:include page="divFooter.jsp"></jsp:include>  <!--  end footer -->
   	</div> <!-- end page -->
   	
<%@include file="footer.jsp" %>
