<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>
<script type="text/javascript">
<!--
$(document).on("pageinit","#home",function(){
	$("#system_set").click(function(event){
		//浏览器版本
		var browser={
				versions:function(){
					var u = navigator.userAgent, app = navigator.appVersion;
		            return {         //移动终端浏览器版本信息
		                trident: u.indexOf('Trident') > -1, //IE内核
		                presto: u.indexOf('Presto') > -1, //opera内核
		                webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
		                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
		                mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否为移动终端
		                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
		                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或uc浏览器
		                iPhone: u.indexOf('iPhone') > -1 , //是否为iPhone或者QQHD浏览器
		                iPad: u.indexOf('iPad') > -1, //是否iPad
		                webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
		            };
				}(),
				language:(navigator.browserLanguage || navigator.language).toLowerCase()
		}
	  
		var result = "语言版本: "+browser.language+"<br>"+
					 " 是否为移动终端: "+browser.versions.mobile+"<br>"+
					 " ios终端: "+browser.versions.ios+"<br>"+
					 " android终端: "+browser.versions.android+"<br>"+
					 " 是否为iPhone: "+browser.versions.iPhone+"<br>"+
					 " 是否iPad: "+browser.versions.iPad+"<br>"+
					 navigator.userAgent;
		
		myDialog("消息窗口",result,"确认");
		$.mobile.changePage( "#my-dialog", { role: "dialog" } );
		$.mobile.changePage( $("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );	
		
		
	});
	
	$("#exitPage").live("pageinit",function(event){
		$("#exitBtnOk").click(function(){
			//logOut
			//$.mobile.changePage( "index.jsp" , { transition : "pop" ,changeHash: true ,  reloadPage : true  });
			var url = "${ctx}/logout";
			$.ajax({
				type: "post",
				url: url,
				data: "",
				beforeSend: function(XMLHttpRequest){
					//ShowLoading();
				},					
				success: function(json, textStatus){
					//WeixinJSBridge.call('closeWindow');
					window.location.href = "${ctx}/bosapp?weixinid="+json.msg;
				},
				complete: function(XMLHttpRequest, textStatus){
					//HideLoading();
				},
				error: function(){
					//请求出错处理
					$.mobile.changePage( $("#networkError") , { transition : "pop" ,changeHash: true ,  reloadPage : true  });
				}
			});
		});	
	});
	
	
});
//-->
</script>  
  
   <div data-role="page" id="home" ProjectUrl="${ctx}" > 
   		<div data-role="header" data-theme="b">
   			<h1 align="center"> ${ProjectName} </h1>
   		</div>
   		
   		<div data-role="content" data-theme="c">
   		<span id="document" name="document"></span>
   			<table width ="100%">
   				<tbody>
   					<tr>
   						<td width="50%"  align="center"> 
   							<a class="aBtnStyle" href="#" data-role="button" data-icon="star"  data-iconpos="top" data-theme="d" data-ajax="false"> <br> 流程审批 (<span id="workflowcount" style="color:#F00">1</span>) 条</a>
   						</td>
   						<td align="center"> 
   							<a class="aBtnStyle" href="reportview"  data-icon="grid" data-role="button" data-iconpos="top" data-theme="d" data-ajax="false"> <br> 经典报表</a>
   						</td>
   						
   					</tr>
   					
   					<tr>
   						<td align="center"> 
   							<a class="aBtnStyle" href="news" data-role="button" data-icon="info"  data-iconpos="top" data-theme="d" data-ajax="false"> <br> 消息管理 (<span id="workflowcount" style="color:#F00">10</span>) 条</a>
   						</td>
   						<td align="center"> 
   							<a class="aBtnStyle" href="reportview_ichartjs"  data-icon="grid" data-role="button" data-iconpos="top" data-theme="d" data-ajax="false"> <br> 报表模型</a>
   						</td>
   						
   					</tr>
   					   					
   					<tr>
   						<td  align="center">
   							<a class="aBtnStyle" href="#"  data-role="button"  data-icon="gear" data-iconpos="top" data-theme="d" id="system_set" data-ajax="false"> <br> 系统设置</a> 
   						</td>
   						<td  align="center"> 
   							<a class="aBtnStyle" href="#exitPage"  data-role="button"  data-icon="refresh" data-iconpos="top" data-theme="d"> <br> 退&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;出</a> 
   						</td>
   					</tr>
   				</tbody>
   			</table>
   		</div> <!--  end content -->
   		
   		
   		<jsp:include page="divFooter.jsp"></jsp:include>  <!--  end footer -->
   	</div> <!-- end page -->
   	
<%@include file="footer.jsp" %>
