<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="/WEB-INF/cust_tag.tld" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="ProjectName" value="信息管理系统" />
<c:set var="bosapp" value="bosapp" />
<%--<c:out value="${ctx}"/>--%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html>
  <head>
    <title>${ProjectName}</title>    
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style type="text/css">
		.aBtnStyle{
			margin: 2px 2px  2px 2px;
		}
		.myChart {
			width: 300px;
			height: 200px;
		}
		.note {
        	font-size: 0.8em;
		}
		.notep 
		 {
		 	border-bottom:solid 1px #ccc
		 }
		 #title 
		 {
		 	margin:0px;text-align:center
		 }
		.jqplot-yaxis-tick {
		  white-space: nowrap;
		}
		.ul_li_class{
			font-size: 12px
		}
    </style>
    
    <%--

    <script type="text/javascript" src="${ctx}/resources/loader/yepnope.min.js"></script>
    
    <script type="text/javascript">
    	yepnope({
			load : [ 
					 //jquery mobile 
					 "${ctx}/resources/jquery/jquery-1.7.1.js", 
					 "${ctx}/resources/jquery-mobile/jquery.mobile-1.4.2.js",
					 "${ctx}/resources/jquery-mobile/jquery.mobile-1.4.2.css",
					 "${ctx}/resources/js/json2.js",
					 "${ctx}/resources/jquery/jquery.md5.js",
					 //ichartjs
					 "${ctx}/resources/ichartjs/ichart.1.2.js", 
					 //mobiscroll
					 "${ctx}/resources/mobiscroll/mobiscroll.core-2.5.2.js",
					 "${ctx}/resources/mobiscroll/mobiscroll.core-2.5.2-zh.js",  
					 "${ctx}/resources/mobiscroll/mobiscroll.core-2.5.2.css", 
					 "${ctx}/resources/mobiscroll/mobiscroll.datetime-2.5.1.js", 
					 "${ctx}/resources/mobiscroll/mobiscroll.datetime-2.5.1-zh.js", 
					 "${ctx}/resources/mobiscroll/mobiscroll.android-ics-2.5.2.js", 
					 "${ctx}/resources/mobiscroll//mobiscroll.android-ics-2.5.2.css"
					 ],
			complete : function() {
				$(document).bind("mobileinit", function(){
					$.mobile.transitionFallbacks.slideout = "none";
					$.mobile.loadingMessage = '页面载入中';
					$.mobile.pageLoadErrorMessage = '页面载入失败';
				});

			}
    	});    
    </script>
	  
  --%>
  	
  	<script type="text/javascript" src="${ctx}/resources/jquery/jquery-1.7.1.js"></script>
  	<script type="text/javascript" src="${ctx}/resources/jquery-mobile/jquery.mobile-1.4.2.js"></script>
  	<link href="${ctx}/resources/jquery-mobile/jquery.mobile-1.4.2.css" rel="stylesheet" type="text/css" />
  	
  	<script type="text/javascript" src="${ctx}/resources/js/json2.js"></script>
  	<script type="text/javascript" src="${ctx}/resources/jquery/jquery.md5.js"></script>
  	
  	<script type="text/javascript" src="${ctx}/resources/ichartjs/ichart.1.2.js"></script>
  	
  	<script type="text/javascript" src="${ctx}/resources/js/bosapp/utils.js"></script>
  	
  		
	<script src="${ctx}/resources/mobiscroll/mobiscroll.core-2.5.2.js" type="text/javascript"></script>
	<script src="${ctx}/resources/mobiscroll/mobiscroll.core-2.5.2-zh.js" type="text/javascript"></script>
	
	<link href="${ctx}/resources/mobiscroll/mobiscroll.core-2.5.2.css" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resources/mobiscroll/mobiscroll.animation-2.5.2.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/resources/mobiscroll/mobiscroll.datetime-2.5.1.js" type="text/javascript"></script>
	<script src="${ctx}/resources/mobiscroll/mobiscroll.datetime-2.5.1-zh.js" type="text/javascript"></script>
	
	<script src="${ctx}/resources/mobiscroll/mobiscroll.android-ics-2.5.2.js" type="text/javascript"></script>
	<link href="${ctx}/resources/mobiscroll/mobiscroll.android-ics-2.5.2.css" rel="stylesheet" type="text/css" />  
  	

	<script type="text/javascript">
		$(document).bind("mobileinit", function(){
			$.mobile.transitionFallbacks.slideout = "none";
			$.mobile.loadingMessage = '页面载入中';
			$.mobile.pageLoadErrorMessage = '页面载入失败';
		});
		
	</script>
	

  </head>
  <body>