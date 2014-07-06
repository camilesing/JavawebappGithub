$("#newsdetail").live( "pagecreate" , function(event){
	
	
});

$("#newsdetail").live( "pageshow" , function(event){
	var fun_newsdetail = function(newsid){
		var ProjectUrl = html5.utils.getParam('ProjectUrl');
		var ProjectUrl = ProjectUrl + "/bosapp/newsdetail";
		
		$.mobile.loading( 'show', {
			  text: "Loading ...",
			  textVisible: true,
			  theme: "b",
			  textonly: false,
			  html: ""
		});
			
		//清除原先的内容
		var param = {"newsid":newsid};
		$.ajax({
			type: "post",
			url:  ProjectUrl,
			data: param,
			success: function(json, textStatus){
				if(json.success){
					var head   = json.msg[0].HEAD ;
					var detail = json.msg[0].DETAIL;
					//$("#newsdetail").html(detail);
					$("#newsdetail_title").html(head);
					$("#newsdetail_content").html(detail);
				}
				$.mobile.loading( "hide" );
			}
		});

	};
	
	var newsid = html5.utils.getParam('newsid');
	fun_newsdetail(newsid);
});

$("#newsdetail").live( "pageinit" , function(event){
	
});




