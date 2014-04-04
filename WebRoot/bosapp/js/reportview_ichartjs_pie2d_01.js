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
			        	{name : 'IE',value : 35.75,color:'#9d4a4a'},
			        	{name : 'Chrome',value : 29.84,color:'#5d7f97'},
			        	{name : 'Firefox',value : 24.88,color:'#97b3bc'},
			        	{name : 'Safari',value : 6.77,color:'#a5aaaa'},
			        	{name : 'Opera',value : 2.02,color:'#778088'},
			        	{name : 'Other',value : 0.73,color:'#6f83a5'}
		        	];
        	
			new iChart.Pie2D({
				render : 'ichartjs_content',
				data: data,
				title : 'Top 5 Browsers from 1 to 29 Feb 2012',
				legend : {
					enable : false
				},
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
