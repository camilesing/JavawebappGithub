
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
			//提交事件
			$("#loginBtn").click(function(event){
				var name = $("#userName").val();
				var pwd = $("#userPwd").val();
				
				if( name.length <= 0 || pwd.length <= 0 ){
					$.mobile.changePage( $("#loginError") , { transition : "pop" ,changeHash: true ,  reloadPage : true  });
					return false;
				}				
								
				var url = $("#loginForm").attr("action");
				var project_path = $("#loginForm").attr("project_path");
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
							//$.mobile.changePage( "wenjuan.jsp" , { transition : "pop" ,changeHash: true ,  reloadPage : true  });
							location.href = 'wenjuan.jsp'
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

$("#exitPage").live("pageinit",function(event){
	$("#exitBtnOk").click(function(){
		//logOut
		$.mobile.changePage( "index.jsp" , { transition : "pop" ,changeHash: true ,  reloadPage : false  });
	});	
});