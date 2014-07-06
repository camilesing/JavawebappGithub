
$("#reportview_table").live( "pageshow" , function(event){
	//updateTable();
});

$("#reportview_table").live( "pageinit" , function(event){
	updateTable = function(){
		var project_path = $("#reportview_table").attr("project_path");
		var url_init = project_path + "/bosapp/reportview_retail_010";
		var param = {};
		
		//清理现有的列表
		$("#reportview_table_id").empty();
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
				if(parseInt(json.o)>0){
					fun_reportview_table_init(json);
					fun_reportview_ichartjs_init(json);
				}
				
				$.mobile.loading('hide');
			}
		}); 
	};
	
	$("#refreshTable").click(function(){
		updateTable();
	});
	
	var fun_reportview_table_init = function(json){
		if(json.success){
			//清理现有的列表
			var table = $("#reportview_table_id");
			//添加表头
			var result = json.o.split(",");
			$("#reportview_table_count").html("数据："+result[0]+" 条");
			
			//遍历json数组
			for(var i=0;i<json.msg.length;i++){
				if(i==0){
					var tr ="<thead><tr >"
					var jsonobj = json.msg[i];
					for(var x in jsonobj){
						var th = "<th >"+jsonobj[x]+"</th>";
						tr = tr+th;
					}
					tr = tr +"</tr></thead>";
					table.append(tr);
					table.append("<tbody></tbody>");
				}else{
					var tr ="<tr>"
					var jsonobj = json.msg[i];
					for(var x in jsonobj){
						var td = "<td >"+jsonobj[x]+"</td>";
						tr = tr+td;
					}
					tr = tr +"</tr>";
					table.append(tr);			
				}
			}
			//$("#reportview_table_id" ).table("refresh");
			
		}else {
			$("#reportview_table_div_id").html('数据读取有误');							
		}			
	}
	
	var fun_reportview_ichartjs_init = function(json){
		$("#DataResult").html("");
		if(json.success){
			//定义数据
			var data = new Array();
			var labels = new Array();
			var tot_amt_actual = new Array();
			
			var end_scale =0;
			var scale_space = 0;
			
			for(var i=1;i<json.msg.length-1;i++){
				tot_amt_actual.push(json.msg[i]["TOT_AMT_ACTUAL"]);
				labels.push(json.msg[i]["WEEKDAY"]);
			}
			
			var child_data= '{"name" : "成交金额","value":['+tot_amt_actual+'],"color":"#1f7e92","line_width":"2"}';
			var child_data_json = JSON.parse(child_data);
			data.push(child_data_json);
			if(json.msg.length>1){
				var chart = new iChart.Area2D({
					render : 'ichartjs_content',
					data: data,
					title : '周数据分析(单位：元)',
					width : $(window).get(0).innerWidth-10,
					height : $(window).get(0).innerHeight-250,
					coordinate:{height:'90%',background_color:'#f6f9fa'},
					sub_option:{
						hollow_inside:false,//设置一个点的亮色在外环的效果
						point_size:10
					},
					labels:labels
				});
				
				chart.draw();
				
				var result = json.o.split(",");
				
				$("#DataResult").html(result[1]);
				
			}
			
		}else {
			$("#ichartjs_content").html('数据读取有误');	
		}			
	}
	
});





