$("#reportview_ichartjs_area_01").live( "pageshow" , function(event){
	
});

$("#reportview_ichartjs_area_01").live( "pageinit" , function(event){
	updateTable = function(){
		var project_path = $("#reportview_ichartjs_area_01").attr("project_path");
		var url_init = project_path + "/bosapp/reportview_ireport?time="+Date();
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
			var data = [
			        	{
			        		name : '北京',
			        		value:[-9,-2],
			        		color:'#1f7e92',
			        		line_width:2
			        	}
			       ];
		       
			var chart = new iChart.Area2D({
					render : 'ichartjs_area_01DIV',
					data: data,
					title : '北京2012年平均温度情况',
					width : $(window).get(0).innerWidth-10,
					height : $(window).get(0).innerHeight-250,
					coordinate:{height:'90%',background_color:'#edf8fa'},
					sub_option:{
						hollow_inside:false,//设置一个点的亮色在外环的效果
						point_size:10
					},
					labels:["一月","二月"]
				});
			
			chart.draw();
		}else {
			$("#barChart").html('数据读取有误');							
		}			
	}
	
});
