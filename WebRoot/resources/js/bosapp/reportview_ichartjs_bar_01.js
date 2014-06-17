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
			        	{name : '鞋区',value : 6078,color:'#97b3bc'},
			        	{name : '运动区',value : 5845,color:'#FF3333'},
			        	{name : '女装区',value : 5008,color:'#cd5c5c'},
			        	{name : '户外区',value : 2662,color:'#006666'},
			        	{name : '休闲区',value : 2445,color:'#CC3333'},
			        	{name : '皮具区',value : 2389,color:'#9d4a4a'},
			        	{name : '男装区',value : 2147,color:'#eee8aa'},
			        	{name : '内衣区',value : 2135,color:'#CCFF66'},
			        	{name : '儿童区',value : 820,color:'#5d7f97'},
			        	{name : '租赁',value : 763,color:'#33FF33'},
			        	{name : '服饰区',value : 421,color:'#330099'},
			        	{name : '家居区',value : 272,color:'#33FF99'},
			        	{name : '毛纺区',value : 261,color:'#ffdead'},
			        	{name : '羽绒服区',value : 252,color:'#CC3399'}
		        	];
        	
			new iChart.Bar2D({
				render : 'ichartjs_content',
				background_color : '#EEEEEE',
				data: data,
				title : '2012上半年品类销售分析',
			//	subtitle : '鞋区,运动区,女装区三大区占据销售的半壁江山',
			//	footnote : '数据来源：ERP基础数据仓库   单位：万元',
				width : $(window).get(0).innerWidth,
				height : $(window).get(0).innerHeight,
				coordinate:{
					width : $(window).get(0).innerWidth-100,
					height : $(window).get(0).innerHeight-100,
					scale:[{
						 position:'bottom',	
						 start_scale:0,
						 end_scale:6000,
						 scale_space:1000
					}]
				},
				sub_option:{
					border:{
						enable : false
					},
					label:{color:'#333333'}
				},
				shadow:true,
				shadow_color:'#8d8d8d',
				shadow_blur:1,
				shadow_offsety:1,
				shadow_offsetx:1,
				legend:{enable:false}
			}).draw();
			
		}else {
			$("#barChart").html('数据读取有误');							
		}			
	}
	
});
