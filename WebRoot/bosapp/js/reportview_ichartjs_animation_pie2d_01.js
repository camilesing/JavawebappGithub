$("#reportview_ichartjs").live( "pageshow" , function(event){
	
});

$("#reportview_ichartjs").live( "pageinit" , function(event){
	updateTable = function(){
		var project_path = $("#reportview_ichartjs").attr("project_path");
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
				fun_reportview_ichartjs_init(json);
			}
		});
	};
	
	$("#refreshIchartjs").click(function(){
		updateTable();
	});
	
	var fun_reportview_ichartjs_init = function(json){			
		if(json.success){	
			//创建数据
			var data = [
			        	{name : 'Other',value : 0.73,color:'#6f83a5'},
			        	{name : 'IE',value : 35.75,color:'#a5c2d5'},
			        	{name : 'Chrome',value : 29.84,color:'#cbab4f'},
			        	{name : 'Firefox',value : 24.88,color:'#76a871'},
			        	{name : 'Safari',value : 6.77,color:'#9f7961'},
			        	{name : 'Opera',value : 2.02,color:'#a56f8f'}
		        	];
        	
			new iChart.Pie2D({
				render : 'ichartjs_content',
				data: data,
				title : 'Top 5 Browsers from 1 to 29 Feb 2012',
				legend : {
					enable : false
				},
				sub_option : {
					label : {
						background_color:null,
						sign:false,//设置禁用label的小图标
						padding:'0 4',
						border:{
							enable:false,
							color:'#666666'
						},
						fontsize:11,
						fontweight:600,
						color : '#4572a7'
					},
					border : {
						width : 2,
						color : '#ffffff'
					}
				},
				animation:true,
				showpercent:true,
				decimalsnum:2,
				width : $(window).get(0).innerWidth,
				height : $(window).get(0).innerHeight,
				radius:140
			}).draw();
			
		}else {
			$("#barChart").html('数据读取有误');							
		}			
	}
	
});
