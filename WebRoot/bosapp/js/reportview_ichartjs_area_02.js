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
			        	{
			        		name : 'A产品',
			        		value:[2680,2200,1014,2590,2800,3200,2184,3456,2693,2064,2414,2044],
			        		color:'#01acb6',
			        		line_width:2
			        	}
			       ];
			//创建x轴标签文本   
		    var labels = ["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"];
		       
			var chart = new iChart.Area2D({
					render : 'ichartjs_content',
					data: data,
					title:{
						text:'A产品2013年度订单数据分析',
						color:'#eff4f8',
						background_color:'#1c4156',
						height:40,
						border:{
							enable:true,
							width:[0,0,4,0],//只显示下边框
							color:'#173a4e'
						}
					},					
					padding:'5 1',//设置padding,以便title能占满x轴
					sub_option:{
						label:false,
						hollow_inside:false,//设置一个点的亮色在外环的效果
						point_size:10
					},
					tip:{
						enable:true,
						listeners:{
							 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
							parseText:function(tip,name,value,text,i){
								return labels[i]+"订单数:<span style='color:red'>"+value+"</span>万件";
							}
						}
					},
					width : $(window).get(0).innerWidth,
					height : $(window).get(0).innerHeight,
					background_color:'#0c222f',
					gradient:true,
					shadow:true,
					shadow_blur:2,
					shadow_color:'#4e8bae',
					shadow_offsetx:0,
					shadow_offsety:0,
					gradient_mode:'LinearGradientDownUp',//设置一个从下到上的渐变背景
					border:{
						radius:5
					},
					coordinate:{
						width : $(window).get(0).innerWidth-70,
						height : $(window).get(0).innerHeight-100,
						grid_color:'#506e7d',
						background_color:null,//设置坐标系为透明背景
						scale:[{
							 position:'left',	
							 label:{
								 color:'blue',
								 fontsize:10,
								 fontweight:300
							 },
							 start_scale:0,
							 end_scale:4000,
							 scale_space:500
						},{
							 position:'bottom',	
							 label:{
								 color:'#506673',
								 fontweight:300
							 },
							 labels:labels
						}]
					}
				});
			
			chart.draw();
		}else {
			$("#barChart").html('数据读取有误');							
		}			
	}
	
});
