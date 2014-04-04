


$("#reportview_ireport").live( "pageshow" , function(event){
	
});

$("#reportview_ireport").live( "pageinit" , function(event){
	updateTable = function(){
		var project_path = $("#reportview_ireport").attr("project_path");
		var url_init = project_path + "/bosappaction/reportview_ireport?time="+Date();
		var store = $("#store").val();
		var param = {
			"param" : store
		};
		$.ajax({
			type: "post",
			url:  url_init,
			data: param,
			success: function(json, textStatus){
				//打开另外一个页面
				//$.mobile.changePage( url_init+"&param="+store , { transition : "pop" ,changeHash: false ,  reloadPage : true  });
				fun_reportview_ireport_init(json);
			}
		});
	};
	
	$("#refreshIreport").click(function(){
		var project_path = $("#reportview_ireport").attr("project_path");
		var url_init = project_path + "/bosappaction/reportview_ireport";
		var store = $("#store").val();
		window.open(url_init+"?param="+store+"&time="+Date());
		//$.mobile.changePage( url_init+"&param="+store , { transition : "pop" ,changeHash: false ,  reloadPage : true  });
		//updateTable();
	});
	
	var fun_reportview_ireport_init = function(json){			
		if(json.success){	
			//$.mobile.changePage( "reportview_ireport_temp.html" , { transition : "pop" ,changeHash: true ,  reloadPage : true  });
			//$("#barChart").html(json.msg);
			//location.href = "reportview_ireport_temp.html";
			//$.mobile.changePage( "reportview_ireport_temp.html" , { transition : "pop" ,changeHash: false ,  reloadPage : true  });
			
		}else {
			$("#barChart").html('数据读取有误');							
		}			
	}
	
});





