<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>

  
   <div data-role="page" id="home"> 
   		<div data-role="header" data-theme="b">
        	<a href="home" data-ajax="false" class="ui-btn-left ui-btn ui-btn-inline ui-mini ui-corner-all ui-btn-icon-left ui-icon-home">主页</a>
   			<h1 align="center">经典报表 </h1>
   		</div>
   		   		
   		<div data-role="content" >
            <ul data-role="listview" data-split-icon="gear" data-split-theme="d" data-inset="true" id="reportviewdetail_ul">
				<li data-role="list-divider" ><span style="color: maroon;">A.销售板块</span></li>
				<li><a href="reportview_retail_003" data-ajax="false"><span class="ul_li_class">销售排行分析(款)TOP10</span></a></li>
				<li><a href="reportview_retail_004" data-ajax="false"><span class="ul_li_class">销售出库分析(类别)</span></a></li>
				<li><a href="reportview_retail_005" data-ajax="false"><span class="ul_li_class">销售出库分析(经销商)</span></a></li>				
				<li data-role="list-divider"><span style="color: maroon;">B.零售板块</span></li>
				<li><a href="reportview_retail_001" data-ajax="false"><span class="ul_li_class">零售汇总报表</span></a></li>
				<li><a href="reportview_retail_009" data-ajax="false"><span class="ul_li_class">零售日数据分析</span></a></li>
				<li><a href="reportview_retail_010" data-ajax="false"><span class="ul_li_class">零售周数据分析</span></a></li>
				<li><a href="reportview_retail_006" data-ajax="false"><span class="ul_li_class">门店零售排行(TOP20)</span></a></li>
				<li><a href="reportview_retail_007" data-ajax="false"><span class="ul_li_class">门店零售排行(END20)</span></a></li>
				<li><a href="reportview_retail_008" data-ajax="false"><span class="ul_li_class">店铺人效分析</span></a></li>
				<li data-role="list-divider"><span style="color: maroon;">C.经销商板块</span></li>
				<li><a href="reportview_retail_101" data-ajax="false"><span class="ul_li_class">应收款分析</span></a></li>
				
				<li data-role="list-divider"><span style="color: maroon;">D.供应商板块</span></li>
				<li><a href="reportview_retail_201" data-ajax="false"><span class="ul_li_class">应付款分析(...)</span></a></li>
				
				<li data-role="list-divider"><span style="color: maroon;">E.未分类</span></li>
				
            </ul>
   		</div> <!--  end content -->
   		
   		<jsp:include page="divFooter.jsp"></jsp:include>  <!--  end footer -->
   	</div> <!-- end page -->
   	
<%@include file="footer.jsp" %>
