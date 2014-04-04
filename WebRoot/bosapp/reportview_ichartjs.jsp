<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
  
   <div data-role="page" id="reportview_ichartjs"> 
   		<div data-role="header" data-theme="b">
        	<a href="home.jsp" data-ajax="false">返回</a>
   			<h1 align="center">Ichartjs </h1>
   		</div>
   		   		
   		<div data-role="content" >
            <ul data-role="listview" data-split-icon="gear" data-split-theme="d" data-inset="true" id="reportviewdetail_ul">
				<li data-role="list-divider">面积图</li>
				<li><a href="reportview_ichartjs_area_01.jsp" data-ajax="false" >基本面积图</a></li>
				<li><a href="reportview_ichartjs_area_02.jsp" data-ajax="false">A产品2013年度订单数据分析</a></li>
				<li data-role="list-divider">2D条形图</li>
				<li><a href="reportview_ichartjs_bar_01.jsp" data-ajax="false">2012品类销售分析</a></li>
				<li><a href="reportview_ichartjs_bar_02.jsp" data-ajax="false">限额以上连锁零售业情况</a></li>
				<li data-role="list-divider">3D柱形图</li>
				<li><a href="reportview_ichartjs_column3d_01.jsp" data-ajax="false">产品销售情况</a></li>
				<li data-role="list-divider">2D饼图</li>
				<li><a href="reportview_ichartjs_pie2d_01.jsp" data-ajax="false">操作系统分布情况</a></li>
				<li data-role="list-divider">2D环形图</li>
				<li><a href="reportview_ichartjs_donut2d_01.jsp" data-ajax="false">手机浏览器市场份额</a></li>
				<li data-role="list-divider">3D饼图</li>
				<li data-role="list-divider">动画</li>
				<li><a href="reportview_ichartjs_animation_pie2d_01.jsp" data-ajax="false">带有动画的饼图</a></li>
				<li data-role="list-divider">组合图</li>
				<li><a href="reportview_ichartjs_combination2d_01.jsp" data-ajax="false">2011年度销售5强A</a></li>
            </ul>                    
   		</div> <!--  end content -->
   		
   		
   		<jsp:include page="divFooter.jsp"></jsp:include>  <!--  end footer -->
   	</div> <!-- end page -->
   	
<%@include file="footer.jsp" %>
