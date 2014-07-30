
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
				
				$.mobile.loading( 'show', {
					  text: "Loading ...",
					  textVisible: true,
					  theme: "b",
					  textonly: false,
					  html: ""
				});
				
				var weixinid = $("#weixinid").val();
				var ProjectUrl = $("#loginForm").attr("ProjectUrl");
				if(weixinid!=""){
					var Param ={
							weixinid : weixinid,
							quick : "Y"
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
								myDialog( "提示" , "快速登陆失败,请先通过用户名密码登陆" , "返&nbsp;&nbsp;&nbsp;&nbsp;回" );
								$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
							}
						},
						error: function(){
							//请求出错处理
							$.mobile.changePage( $("#networkError") , { transition : "pop" ,changeHash: true ,  reloadPage : true  });
						}
					});
				}else{
					myDialog( "提示" , "获取微信ID失败" , "返&nbsp;&nbsp;&nbsp;&nbsp;回" );
					$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
				}
				
				$.mobile.loading( 'hide');
				
				return false;
			});
			
			//提交事件
			$("#loginBtn").click(function(event){
				var name = $("#userName").val();
				var pwd = $("#userPwd").val();
				var weixinid = $("#weixinid").val();
				
				if( name.length <= 0 || pwd.length <= 0 ){
					$.mobile.changePage( $("#loginError") , { transition : "pop" ,changeHash: true ,  reloadPage : true  });
					return false;
				}				
				
				$.mobile.loading( 'show', {
					  text: "Loading ...",
					  textVisible: true,
					  theme: "b",
					  textonly: false,
					  html: ""
				});
								
				var url = $("#loginForm").attr("action");
				var ProjectUrl = $("#loginForm").attr("ProjectUrl");
				var Param = {
						account : name,
						//password : $.md5(pwd)
						password : pwd,
						weixinid : weixinid,
						quick : "N"
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
				
				$.mobile.loading( 'hide');
				
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