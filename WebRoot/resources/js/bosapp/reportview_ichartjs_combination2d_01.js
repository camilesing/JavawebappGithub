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
			        	{name : 'Alex',value : 10,color:'#849fb4'},
			        	{name : 'Mark',value : 30,color:'#bf9d32'},
			        	{name : 'David',value : 40,color:'#728f1b'},
			        	{name : 'Graham',value : 55,color:'#bf784c'},
			        	{name : 'John',value : 70,color:'#2f8586'}
		        	];
			var data1 = [
				        	{
				        		name : '累计销售',
				        		value:[168,302,332,453,668],
				        		color:'#aa4643',
				        		line_width:3
				        	}
				       ];
	    	
	    	//是否启用动画
			var animation = false;
	    	
			var chart = new iChart.Column3D({
				render : 'ichartjs_content',
				data: data,
				title : {
					text : 'This is a sample for Combination',
					color : '#3e576f'
				},
				subtitle : {
					text : 'Top 5 Sales Person,Oct 2011',
					color : '#6d869f'
				},
				footnote : {
					text : 'ichartjs.com',
					color : '#909090',
					fontsize : 11,
					padding : '0 38'
				},
				width : $(window).get(0).innerWidth-8,
				height : $(window).get(0).innerHeight-100,
				animation : animation,
				animation_duration:600,
				shadow : true,
				shadow_blur : 2,
				shadow_color : '#aaaaaa',
				shadow_offsetx : 1,
				shadow_offsety : 0,
				zScale:0.5,
				xAngle : 50,
				bottom_scale:1.1,
				column_width:90,
				label:{
					color:'#4c4f48'
				},
				sub_option:{
					label : {
						color : '#4c4f48'
					},
					listeners:{
						parseText:function(r,t){
							//自定义柱形图上方label的格式。
							return '$'+t+'k';
						}
					}
				},
				text_space : 16,//坐标系下方的label距离坐标系的距离。
				coordinate:{
					background_color : '#d5d8d1',
					grid_color : '#919d7e',
					color_factor : 0.24,
					board_deep:10,//背面厚度
					pedestal_height:10,//底座高度
					left_board:false,//取消左侧面板
					height:'80%',
					width:'80%',
					shadow:true,//底座的阴影效果
					wall_style:[{//坐标系的各个面样式
						color : '#333333'
					},{
						color : '#b2b2d3'
					}, {
						color : '#a6a6cb'
					},{
						color : '#333333'
					},{
						color : '#74749b'
					},{
						color : '#a6a6cb'
					}],
					scale:[{
						 position:'left',	
						 start_scale:0,
						 scale_space:10,
						 end_scale:80,
						 label:{
							color:'#4c4f48'
						 },
						 listeners:{
							parseText:function(t,x,y){
								//自定义左侧坐标系刻度文本的格式。
								return {text:'$'+t+'k'}
							}
						 }
					},{
						 position:'right',	
						 start_scale:0,
						 scale_space:100,
						 end_scale:800,
						 scaleAlign:'right',
						 label:{
							color:'#aa4643'
						 },
						 listeners:{
							parseText:function(t,x,y){
								//自定义右侧坐标系刻度文本的格式。
								return {text:'$'+t+'k'}
							}
						 }
					}]
				}
			});
			
			
			var line = new iChart.LineBasic2D({
				z_index:1000,
				data: data1,
				label:{
					color:'#4c4f48'
				},
				point_space:chart.get('column_width')+chart.get('column_space'),
				tip:{
					enable:true,
					listeners:{
						 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
						parseText:function(tip,name,value,text,i){
							return "全年销售:"+value+"k";
						}
					}
				},
				scaleAlign : 'right',
				sub_option : {
					smooth:true,
					label:false,
					hollow_inside:false,
					point_size:14
				},
				animation : animation,
				coordinate:chart.coo
			});
			
			chart.plugin(line);
			
			 //利用自定义组件构造左侧说明文本。
			chart.plugin(new iChart.Custom({
					drawFn:function(){
						 //计算位置
						var coo = chart.getCoordinate(),
							x = coo.get('originx'),
							y = coo.get('originy'),
							H = coo.height;
						//在左侧的位置，渲染说明文字。
						chart.target.textAlign('center')
						.textBaseline('middle')
						.textFont('600 13px Verdana')
						.fillText('Sales Figure',x-50,y+H/2,false,'#6d869f', false,false,false,-90);
						
					}
			}));

			chart.draw();
			
		}else {
			$("#barChart").html('数据读取有误');							
		}			
	}
	
});
