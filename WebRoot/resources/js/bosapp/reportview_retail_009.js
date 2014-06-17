
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
        dateOrder: 'yyyymmdd',
        dateFormat: 'yyyymmdd',
		lang:'zh',
        startYear:currYear - 5, //开始年份
        endYear:currYear + 5 //结束年份
	};
	
	$("#date_start").val('20140228').scroller('destroy').scroller($.extend(opt['date'], opt['default']));
	
	
	updateTable = function(){
		var project_path = $("#reportview_table").attr("project_path");
		var url_init = project_path + "/bosapp/reportview_retail_009";
		var date_start = $("#date_start").val();
		var param = {"date_start":date_start};
		
		if( !checkIsNull(date_start) ){
			$.mobile.changePage( $("#searchWhereIsNullErrorDialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			return false;
		}
		
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
		$("#Data_T").html("今日:");
		$("#Data_Y").html("昨日:");
		if(json.success){
			//定义数据
			var data = new Array();
			var labels = new Array();
			var tot_amt_actual = new Array();
			
			var end_scale =0;
			var scale_space = 0;
			
			for(var i=1;i<json.msg.length-1;i++){
				tot_amt_actual.push(json.msg[i]["TOT_AMT_ACTUAL"]);
				labels.push(json.msg[i]["HOURS"]);
			}
			
			var child_data= '{"name" : "成交金额","value":['+tot_amt_actual+'],"color":"#1f7e92","line_width":"2"}';
			var child_data_json = JSON.parse(child_data);
			data.push(child_data_json);
			if(json.msg.length>1){
				var chart = new iChart.Area2D({
					render : 'ichartjs_content',
					data: data,
					title : '日时段数据分析(单位：元)',
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
				
				$("#Data_T").html("今日:"+result[1]);
				$("#Data_Y").html("昨日:"+result[2]);
				
			}
			
		}else {
			$("#ichartjs_content").html('数据读取有误');	
		}			
	}
	
});





