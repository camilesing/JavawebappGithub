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
			        	{name : 'UC浏览器',value : 40.0,color:'#4572a7'},
			        	{name : 'QQ浏览器',value : 37.1,color:'#aa4643'},
			        	{name : '欧朋浏览器',value : 13.8,color:'#89a54e'},
			        	{name : '百度浏览器',value : 1.6,color:'#80699b'},
			        	{name : '海豚浏览器',value : 1.4,color:'#92a8cd'},
			        	{name : '天天浏览器',value : 1.2,color:'#db843d'},
			        	{name : '其他',value : 4.9,color:'#a47d7c'}
		        	];

        	
			var chart = new iChart.Donut2D({
				render : 'ichartjs_content',
				data: data,
				title : {
					text : '2012年第3季度中国第三方手机浏览器市场份额',
					color : '#3e576f'
				},
				footnote : {
					text : 'ichartjs.com',
					color : '#486c8f',
					fontsize : 11,
					padding : '0 38'
				},
				center : {
					text:'100%',
					color:'#3e576f',
					shadow:true,
					shadow_blur : 2,
					shadow_color : '#557797',
					shadow_offsetx : 0,
					shadow_offsety : 0,
					fontsize : 12
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
				shadow : true,
				shadow_blur : 6,
				shadow_color : '#aaaaaa',
				shadow_offsetx : 0,
				shadow_offsety : 0,
				background_color:'#fefefe',
				offset_angle:-120,//逆时针偏移120度
				showpercent:true,
				decimalsnum:2,
				width : $(window).get(0).innerWidth,
				height : $(window).get(0).innerHeight,
				radius:120
			});
			
			chart.draw();
			
		}else {
			$("#barChart").html('数据读取有误');							
		}			
	}
	
});
