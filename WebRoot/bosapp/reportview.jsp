<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
  
   <div data-role="page" id="home"> 
   		<div data-role="header" data-theme="b">
        	<a href="home.jsp" data-ajax="false">返回主页面</a>
   			<h1 align="center"> 报表类型 </h1>
   		</div>
   		   		
   		<div data-role="content" >
            <ul data-role="listview" data-split-icon="gear" data-split-theme="d" data-inset="true" id="reportviewdetail_ul">
				<li data-role="list-divider">A.销售板块-----</li>
				<li><a href="reportview_retail_003.jsp" data-ajax="false">销售排行分析(款)TOP10</a></li>
				<li><a href="reportview_retail_004.jsp" data-ajax="false">销售出库分析(类别)</a></li>
				<li><a href="reportview_retail_005.jsp" data-ajax="false">销售出库分析(经销商)</a></li>				
				<li data-role="list-divider">B.零售板块-----</li>
				<li><a href="reportview_retail_001.jsp" data-ajax="false">零售汇总报表</a></li>
				<li><a href="reportview_retail_006.jsp" data-ajax="false">门店零售排行(TOP20)</a></li>
				<li><a href="reportview_retail_007.jsp" data-ajax="false">门店零售排行(END20)</a></li>
				<li><a href="reportview_retail_008.jsp" data-ajax="false">店铺人效分析</a></li>
				<li data-role="list-divider">C.经销商板块-----</li>
				<li><a href="reportview_retail_101.jsp" data-ajax="false">应收款分析</a></li>
				
				<li data-role="list-divider">D.供应商板块-----</li>
				<li><a href="reportview_retail_201.jsp" data-ajax="false">应付款分析(...)</a></li>
				
				<li data-role="list-divider">E.未分类-----</li>
				
            </ul>
   		</div> <!--  end content -->
   		
   		<jsp:include page="divFooter.jsp"></jsp:include>  <!--  end footer -->
   	</div> <!-- end page -->
   	
<%@include file="footer.jsp" %>
