<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="/WEB-INF/cust_tag.tld" prefix="u" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" scope="request"/>
<c:set var="project_name" value="信息管理系统" />
<%--<c:out value="${ctx}"/>--%>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html>
  <head>
    <title>${project_name }</title>    
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<script type="text/javascript" src="../resources/js/json2.js"></script>
	<link rel="stylesheet" type="text/css" href="../resources/jquery-mobile/jquery.mobile-1.3.2.css">
　 	<script type="text/javascript" src="../resources/jquery-mobile/jquery-1.7.1.js"></script>

	<script type="text/javascript">
		$(document).bind("mobileinit", function(){
			$.mobile.transitionFallbacks.slideout = "none";
			$.mobile.loadingMessage = '页面载入中';
			$.mobile.pageLoadErrorMessage = '页面载入失败';
		});
		
	</script>
　 	<script type="text/javascript" src="../resources/jquery-mobile/jquery.mobile-1.3.2.js"></script>
	<script type="text/javascript" src="../resources/ichartjs/ichart.1.2.js"></script>
	
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
		.jqplot-yaxis-tick {
		  white-space: nowrap;
		}
	</style>
	
	<script type="text/javascript" src="../resources/js/webapp/utils.js"></script>
	
	<script src="../resources/mobiscroll/mobiscroll.core-2.5.2.js" type="text/javascript"></script>
	<script src="../resources/mobiscroll/mobiscroll.core-2.5.2-zh.js" type="text/javascript"></script>
	
	<link href="../resources/mobiscroll/mobiscroll.core-2.5.2.css" rel="stylesheet" type="text/css" />
	<link href="../resources/mobiscroll/mobiscroll.animation-2.5.2.css" rel="stylesheet" type="text/css" />
	<script src="../resources/mobiscroll/mobiscroll.datetime-2.5.1.js" type="text/javascript"></script>
	<script src="../resources/mobiscroll/mobiscroll.datetime-2.5.1-zh.js" type="text/javascript"></script>
	
	<script src="../resources/mobiscroll/mobiscroll.android-ics-2.5.2.js" type="text/javascript"></script>
	<link href="../resources/mobiscroll/mobiscroll.android-ics-2.5.2.css" rel="stylesheet" type="text/css" />  
  </head>
  <body>