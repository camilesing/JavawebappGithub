<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
  
   <div data-role="page" id="reportview_ichartjs"> 
   		<div data-role="header" data-theme="b">
        	<a href="home" data-ajax="false">返回</a>
   			<h1 align="center">Ichartjs </h1>
   		</div>
   		   		
   		<div data-role="content" >
            <ul data-role="listview" data-split-icon="gear" data-split-theme="d" data-inset="true" id="reportviewdetail_ul">
				<li data-role="list-divider"><span style="color: maroon;">面积图</span></li>
				<li><a href="reportview_ichartjs_area_01" data-ajax="false" ><span class="ul_li_class">基本面积图</span></a></li>
				<li><a href="reportview_ichartjs_area_02" data-ajax="false"><span class="ul_li_class">A产品2013年度订单数据分析</span></a></li>
				<li data-role="list-divider"><span style="color: maroon;">2D条形图</span></li>
				<li><a href="reportview_ichartjs_bar_01" data-ajax="false"><span class="ul_li_class">2012品类销售分析</span></a></li>
				<li><a href="reportview_ichartjs_bar_02" data-ajax="false"><span class="ul_li_class">限额以上连锁零售业情况</span></a></li>
				<li data-role="list-divider"><span style="color: maroon;">3D柱形图</span></li>
				<li><a href="reportview_ichartjs_column3d_01" data-ajax="false"><span class="ul_li_class">产品销售情况</span></a></li>
				<li data-role="list-divider"><span style="color: maroon;">2D饼图</span></li>
				<li><a href="reportview_ichartjs_pie2d_01" data-ajax="false"><span class="ul_li_class">操作系统分布情况</span></a></li>
				<li data-role="list-divider"><span style="color: maroon;">2D环形图</span></li>
				<li><a href="reportview_ichartjs_donut2d_01" data-ajax="false"><span class="ul_li_class">手机浏览器市场份额</span></a></li>
				<li data-role="list-divider"><span style="color: maroon;">3D饼图</span></li>
				<li data-role="list-divider"><span style="color: maroon;">动画</span></li>
				<li><a href="reportview_ichartjs_animation_pie2d_01" data-ajax="false"><span class="ul_li_class">带有动画的饼图</span></a></li>
				<li data-role="list-divider"><span style="color: maroon;">组合图</span></li>
				<li><a href="reportview_ichartjs_combination2d_01" data-ajax="false"><span class="ul_li_class">2011年度销售5强A</span></a></li>
            </ul>                    
   		</div> <!--  end content -->
   		
   		
   		<jsp:include page="divFooter.jsp"></jsp:include>  <!--  end footer -->
   	</div> <!-- end page -->
   	
<%@include file="footer.jsp" %>
