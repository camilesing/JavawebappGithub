
//-----------------------------------------登录处理----------------------------------------------------
//pagecreate
$("#login").live( "pageinit" , function(event){
			$("#userName").keypress(function(event){
				if( event.keyCode == 13 ){
					$("#userPwd").focus();
					return false;
				}				
			});
			
			$("#userPwd").keypress(function(event){
				if( event.keyCode == 13 ){
					$("#loginBtn").click();					
					return false;
				}
			});
			$("#quickBtn").click(function(event){
				var weixinid = $("#weixinid").val();
				var ProjectUrl = $("#loginForm").attr("ProjectUrl");
				if(weixinid!=""){
					var Param ={
							weixinid : weixinid
						}
					$.ajax({
						type: "post",
						url: $("#loginForm").attr("QuickLogin"),
						data: Param,
						success: function(json, textStatus){
							if(json.success){
								window.location.href = ProjectUrl+"/bosapp/page/home";
								//$.mobile.changePage( "home" , { transition : "pop" ,changeHash: true ,  reloadPage : true  });
							}else {
								myDialog( "提示" , "绑定登陆失败,请重新绑定或通过用户名密码登陆" , "返&nbsp;&nbsp;&nbsp;&nbsp;回" );
								$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
							}
						},
						error: function(){
							//请求出错处理
							$.mobile.changePage( $("#networkError") , { transition : "pop" ,changeHash: true ,  reloadPage : true  });
						}
					});
				}
				
				return false;
			});
			
			//提交事件
			$("#loginBtn").click(function(event){
				var name = $("#userName").val();
				var pwd = $("#userPwd").val();
				
				if( name.length <= 0 || pwd.length <= 0 ){
					$.mobile.changePage( $("#loginError") , { transition : "pop" ,changeHash: true ,  reloadPage : true  });
					return false;
				}				
								
				var url = $("#loginForm").attr("action");
				var ProjectUrl = $("#loginForm").attr("ProjectUrl");
				var Param = {
						account : name,
						password : $.md5(pwd) 
					};
				
				$.ajax({
					type: "post",
					url: url,
					data: Param,
					beforeSend: function(XMLHttpRequest){
						//ShowLoading();
					},					
					success: function(json, textStatus){
						if(json.success){
							window.location.href = ProjectUrl+"/bosapp/page/home";
							//$.mobile.changePage( "home" , { transition : "pop" ,changeHash: true ,  reloadPage : true  });
						}else {
							$.mobile.changePage( $("#loginError") , { transition : "pop" ,changeHash: true ,  reloadPage : true  });
						}
					},
					complete: function(XMLHttpRequest, textStatus){
						//HideLoading();
					},
					error: function(){
						//请求出错处理
						$.mobile.changePage( $("#networkError") , { transition : "pop" ,changeHash: true ,  reloadPage : true  });
					}
				});
				
				return false;
			}); //end loginBtn Event
						
			//重置事件
			$("#resetBtn").click(function(event){
				$("#userName").empty();
				$("#userPwd").empty();
				$("#userName").focus();
			});
			
			
});

/*$("#exitPage").live("pageinit",function(event){
	$("#exitBtnOk").click(function(){
		//logOut
		window.location.href = "/${ctx}/bosapp";
		//$.mobile.changePage( "/${ctx}/bosapp" , { transition : "pop" ,changeHash: true ,  reloadPage : false  });
	});	
});*/