$("#reportview_ichartjs").live( "pageshow" , function(event){
	
});

$("#reportview_ichartjs").live( "pageinit" , function(event){
	updateTable = function(){
		var project_path = $("#reportview_ichartjs").attr("project_path");
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
			//创建数据
			var data = [
			         	{
			         		name : '直营店',
			         		value:[54841,72400,76776,83361],
			         		color:'#338f61'
			         	},
			         	{
			         		name : '加盟店',
			         		value:[22790,33284,52148,68333],
			         		color:'#44BF82'
			         	}
			         ];
	        
			var chart = new iChart.BarStacked2D({
					render : 'ichartjs_content',
					data: data,
					labels:["2004年","2005年","2006年","2007年"],
					align:"right",
					title : {
						text:'全国限额以上连锁零售业情况',
						color:'#698389',
						textAlign:'left',
						padding:'0 40',
						font:'微软雅黑',
						border:{
							enable:true,
							width:[0,0,4,0],
							color:'#698389'
						},
						height:40
					},
					width : $(window).get(0).innerWidth-5,
					height : $(window).get(0).innerHeight,
					bar_height:70,
					//percent : true,
					//showpercent:true,
					gradient : true,//应用背景渐变
					gradient_mode:'LinearGradientDownUp',//渐变类型
					color_factor : 0.1,//渐变因子
					background_color : '#e7eff7',
					sub_option:{
						label:{color:'#fefefe',fontsize:10,fontweight:300},
						border : false
					},
					label:{color:'#516074',font:'微软雅黑',fontsize:10,fontweight:300},
					legend:{
						enable:true,
						background_color : null,
						line_height:25,
						color:'#6d7b8d',
						fontsize:10,
						font:'微软雅黑',
						fontweight:300,
						border : {
							enable : false
						}
					},
					coordinate:{
						background_color : 0,
						grid_color:'#888888',
						axis : {
							color : '#c0d0e0',
							width : 0
						}, 
						scale:[{
							 position:'bottom',	
							 scale_enable : false,
							 start_scale:0,
							 scale_space:40000,
							 end_scale:160000,
							 label:{color:'#516074',fontsize:10,fontweight:300}
						}],
						width:'90%',
						height:'76%'
					}
			});
			
			chart.draw();
			
		}else {
			$("#barChart").html('数据读取有误');							
		}			
	}
	
});
