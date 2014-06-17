$("#news").live( "pagecreate" , function(event){
	
	//alert('pagecreate');
	
	
	
});

$("#news").live( "pageshow" , function(event){
	var updateContent = function(){
		var project_path = $("#news").attr("project_path");
		var url_init = project_path + "/bosapp/news";
		//清理现有的列表
		var ul = $("#news_ul");
		for(var i=0;i<ul[0].children.length;)
			ul[0].children[0].remove();
		
	//	$.mobile.showPageLoadingMsg('a', "加载中……",true);
		
		$.mobile.loading( 'show', {
			  text: "Loading ...",
			  textVisible: true,
			  theme: "b",
			  textonly: false,
			  html: ""
		});
		
		var param = {};
		
		$.ajax({
			type: "post",
			url:  url_init,
			data: param,
			success: function(json, textStatus){
				if(parseInt(json.o)>0){
					fun_content_init(json);
				}
				
			//	$.mobile.hidePageLoadingMsg('');
				$.mobile.loading( "hide" );
			}
		});
		
	};


	var fun_content_init = function(json){
		if(json.success){
			
			for(var i=0;i<json.msg.length;i++){
				var li = "";
				if(json.msg[i].READ=="NO")	
					li = "<li ><a href='#deal' data-rel='popup' data-role='button' data-inline='true' newsid='"+json.msg[i].ID+"' ><span style='font-weight:bold;'>"+json.msg[i].HEAD+"</span></a></li>";
				else
					li = "<li ><a href='#deal' data-rel='popup' data-role='button' data-inline='true' newsid='"+json.msg[i].ID+"' ><span style='color: red;'>"+json.msg[i].HEAD+"</span></a></li>";
				
				$("#news_ul").append(li);							
			}
			$("#news_ul").listview('refresh');
			
			
			
			//增加链接 事件    
			$("a").on("click",function(){
				var href = $(this).attr("href");
				if(href=="#deal"){
					newsid = $(this).attr("newsid");
					fun_newsdetail(newsid);
				}					
			}); 
		}

	};

	var fun_newsdetail = function(newsid){
		var project_path = $("#news").attr("project_path");
		var url_init = project_path + "/bosapp/newsdetail";
		
		$.mobile.loading( 'show', {
			  text: "Loading ...",
			  textVisible: true,
			  theme: "b",
			  textonly: false,
			  html: ""
		});
			
		//清除原先的内容
		$("#newsdetail").html("");
		var param = {"newsid":newsid};
		$.ajax({
			type: "post",
			url:  url_init,
			data: param,
			success: function(json, textStatus){
				if(json.success){
					var detail = json.msg[0].DETAIL;
					$("#newsdetail").html(detail);
				}
				$.mobile.loading( "hide" );
			}
		});

	};
	updateContent(); 
});

$("#news").live( "pageinit" , function(event){
	
});




