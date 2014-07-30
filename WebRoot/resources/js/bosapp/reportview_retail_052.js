
$("#reportview_table").live( "pageshow" , function(event){
	//updateTable();
});

$("#reportview_table").live( "pageinit" , function(event){
	var currYear = (new Date()).getFullYear();	
	var opt={};
	opt.date = {preset : 'date'};
	//opt.datetime = { preset : 'datetime', minDate: new Date(2012,3,10,9,22), maxDate: new Date(2014,7,30,15,44), stepMinute: 5  };
	opt.datetime = {preset : 'datetime'};
	opt.time = {preset : 'time'};
	opt.default = {
		theme: 'android-ics light', //皮肤样式
        display: 'modal', //显示方式 
        mode: 'scroller', //日期选择模式
        dateFormat: 'yyyymmdd',
		lang:'zh',
        startYear:currYear - 10, //开始年份
        endYear:currYear + 10 //结束年份
	};
	
	$("#date_start").val('').scroller('destroy').scroller($.extend(opt['date'], opt['default']));
	$("#date_end").val('').scroller('destroy').scroller($.extend(opt['date'], opt['default']));
	
	updateTable = function(){
		var project_path = $("#reportview_table").attr("project_path");
		var url_init = project_path + "/bosapp/reportview_retail_052";
		var date_start = $("#date_start").val();
		var date_end = $("#date_end").val();
		var param = {"date_start":date_start,"date_end":date_end};
		//清理现有的列表
		$("#reportview_table_id_01").empty();
		$("#reportview_table_count").html("");
		$("#ichartjs_content").html("");
		
		$.mobile.loading( 'show', {
			  text: "Loading ...",
			  textVisible: true,
			  theme: "b",
			  textonly: false,
			  html: ""
		});
		
		$.ajax({
			type: "post",
			url:  url_init,
			data: param,
			success: function(json, textStatus){
				fun_reportview_table_init(json);
				fun_reportview_ichartjs_init(json);
				$.mobile.loading( 'hide');
			}
		}); 
	};
	
	$("#refreshTable").click(function(){
		updateTable();
	});
	
	var fun_reportview_table_init = function(json){
		if(json.success){
			//清理现有的列表
			var table_01 = $("#reportview_table_id_01");
			//添加表头
			$("#reportview_table_count").html("数据："+json.o+" 条");		
		
			var tr_01_list = ["M_PRODUCT_NAME","QTY","AMTLISTSALE","AMTSALE","MAOL"];
			
			for(var i=0;i<json.msg.length;i++){
				if(i==0){
					var tr_01 ="<thead><tr >";
					var jsonobj = json.msg[i];
					tr_01 = tr_01+"<th >TOP(10)</th> "; //加序号
					
					for(var x in jsonobj){
						for(var y in tr_01_list){
							if(x==tr_01_list[y]){
								var th = "<th >"+jsonobj[x]+"</th>";
								tr_01 = tr_01+th;
							}
						}	
						
					}
					tr_01 = tr_01 +"</tr></thead>";
					
					table_01.append(tr_01);
					table_01.append("<tbody></tbody>");
					
				}else{
					var tr_01 ="<tr>";
					var jsonobj = json.msg[i];
					tr_01 = tr_01+"<td >"+(i+1)+"</td>";
					for(var x in jsonobj){
						for(var y in tr_01_list){
							if(x==tr_01_list[y]){
								var td = "<td >"+jsonobj[x]+"</td>";
								tr_01 = tr_01+td;
							}
						}
					}
					
					tr_01 = tr_01 +"</tr>";
					table_01.append(tr_01);		
				}
			}
			
		}else {
			$("#reportview_table_div_id").html('数据读取有误');							
		}			
	}
	
	var fun_reportview_ichartjs_init = function(json){
		if(json.success){
			//定义数据
			var data = new Array();
			var color = ["#a5c2d5","#cbab4f","#76a871","#76a871","#a56f8f","#c12c44","#a56f8f","#9f7961","#76a871","#6f83a5"]
			var end_scale =0;
			var scale_space = 0;
			
			for(var i=1;i<json.msg.length-1&&i<=10;i++){
				var child = '{"name" : "'+json.msg[i]["M_PRODUCT_NAME"]+'","value" : '+json.msg[i]["QTY"]+',"color":"'+color[i-1]+'"}';
				var child_json = JSON.parse(child);
				data.push(child_json);
			}
			
			new iChart.Bar2D({
				render : 'ichartjs_content',
				data: data,
				title:{
	                  text:"销售排行(款) TOP10",
	                  color:"#111111",
	                  fontsize:20,
	                  font:"微软雅黑",
	                  textAlign:"center",
	                  height:10,
	                  offsetx:0,
	                  offsety:0
	            },
	            border:{
	                  color:"BCBCBC",
	                  width:0
	            },
	            turn_off_touchmove:true,
				showpercent:false,
				decimalsnum:2,
				align:"right",
				width : $(window).get(0).innerWidth-30,
				height : $(window).get(0).innerHeight,
				coordinate:{
					width : $(window).get(0).innerWidth-130,
					height : $(window).get(0).innerHeight-100,
					scale:[{
						 position:'bottom',	
						 start_scale:0,
						 end_scale:0,
						 scale_space:0,
						 listeners:{
							parseText:function(t,x,y){
								return {text:t+""}
							}
						}
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
			$("#ichartjs_content").html('数据读取有误');	
		}			
	}
	
});





