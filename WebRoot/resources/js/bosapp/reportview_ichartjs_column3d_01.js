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
			         		name : '一月',
			         		value:[45,52,54,60],
			         		color:'#4f81bd'
			         	},
			         	{
			         		name : '二月',
			         		value:[60,80,105,80],
			         		color:'#bd4d4a'
			         	},
			         	{
			         		name : '三月',
			         		value:[50,70,120,100],
			         		color:'#98c045'
			         	}
			         ];
	        
			var chart = new iChart.ColumnStacked3D({
					render : 'ichartjs_content',
					data: data,
					labels:["北京","上海","广州","深圳"],
					title : {
						text:'DPS01A型号产品第一季度4城市销售情况',
						color:'#254d70'
					},
					footnote : '数据来源：销售中心',
					width : $(window).get(0).innerWidth,
					height : $(window).get(0).innerHeight,
					column_width:90,
					background_color : '#ffffff',
					shadow : true,
					shadow_blur : 3,
					shadow_color : '#aaaaaa',
					shadow_offsetx : 1,
					shadow_offsety : 0, 
					sub_option:{
						label:{color:'#f9f9f9',fontsize:12,fontweight:600},
						border : {
							width : 2,
							color : '#ffffff'
						} 
					},
					label:{color:'#254d70',fontsize:12,fontweight:600},
					legend:{
						enable:true,
						background_color : null,
						line_height:25,
						color:'#254d70',
						fontsize:12,
						fontweight:600,
						border : {
							enable : false
						}
					},
					tip:{
						enable :true,
						listeners:{
							//tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
							parseText:function(tip,name,value,text,i){
								return name+":"+value+ '万件';
							}
						} 
					},
					percent:true,//标志为百分比堆积图
					showpercent:true,
					decimalsnum:1,
					text_space : 16,//坐标系下方的label距离坐标系的距离。
					zScale:0.5,
					xAngle : 50,
					bottom_scale:1.1, 
					coordinate:{
						width:'82%',
						height:'80%',
						board_deep:10,//背面厚度
						pedestal_height:10,//底座高度
						left_board:false,//取消左侧面板 
						shadow:true,//底座的阴影效果
						grid_color:'#6a6a80',//网格线
						wall_style:[{//坐标系的各个面样式
						color : '#6a6a80'
						},{
						color : '#b2b2d3'
						}, {
						color : '#a6a6cb'
						},{
						color : '#6a6a80'
						},{
						color : '#74749b'
						},{
						color : '#a6a6cb'
						}], 
						axis : {
							color : '#c0d0e0',
							width : 0
						}, 
						scale:[{
							 position:'left',	
							 scale_enable : false,
							 start_scale:0,
							 scale_space:50,
							 label:{color:'#254d70',fontsize:11,fontweight:600}
						}]
					}
			});
			
			chart.draw();
			
		}else {
			$("#barChart").html('数据读取有误');							
		}			
	}
	
});
