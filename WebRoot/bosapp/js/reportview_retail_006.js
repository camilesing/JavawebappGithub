
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
        dateOrder: 'yyyymm',
        dateFormat: 'yyyymm',
		lang:'zh',
        startYear:currYear - 10, //开始年份
        endYear:currYear + 10 //结束年份
	};
	
	$("#date_start").val('').scroller('destroy').scroller($.extend(opt['date'], opt['default']));
	
	updateTable = function(){
		var project_path = $("#reportview_table").attr("project_path");
		var url_init = project_path + "/bosappaction/reportview_retail_006";
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
		$.mobile.showPageLoadingMsg('a', "加载中……",true);
		
		$.ajax({
			type: "post",
			url:  url_init,
			data: param,
			success: function(json, textStatus){
				if(parseInt(json.o)>0){
					fun_reportview_table_init(json);
					fun_reportview_ichartjs_init(json);
				}
				
				$.mobile.hidePageLoadingMsg(''); 
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
			$("#reportview_table_count").html("数据："+json.o+" 条");		
			
			//遍历json数组
			for(var i=0;i<json.msg.length&&i<20;i++){
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
		if(json.success){
			//定义数据
			var data = new Array();
			var labels = new Array();
			var day_amt = new Array();
			var syl = new Array();
			var tot_amt_mark = new Array();
			
			var end_scale =0;
			var scale_space = 0;
			
			for(var i=1;i<json.msg.length&&i<20;i++){
				
				day_amt.push(json.msg[i]["DAY_AMT"]);
				
				tot_amt_mark.push(json.msg[i]["TOT_AMT_MARK"]);
				
				labels.push(json.msg[i]["C_STORE_NAME"]);
			}
			
			var child_day_amt = '{"name" : "已完成量","value":['+day_amt+'],"color":"#32bdbc"}';
			var child_json_day_amt = JSON.parse(child_day_amt);
			data.push(child_json_day_amt);
			
			var child_tot_amt_mark = '{"name" : "指标","value":['+tot_amt_mark+'],"color":"#d75a5e"}';
			var child_json_tot_amt_mark = JSON.parse(child_tot_amt_mark);
			data.push(child_json_tot_amt_mark);
			
			var chart = new iChart.BarMulti2D({
				render : 'ichartjs_content',
				data: data,
				labels:labels,
				title : {
					text:'门店销售排行(TOP20)',
					color:'#585757'
				},
				width : $(window).get(0).innerWidth,
				height : $(window).get(0).innerHeight,
				background_color : '#ffffff',
				legend:{
					enable:true,
					background_color : null,
					border : {
						enable : false
					}
				},
				turn_off_touchmove:true,
				coordinate:{
					scale:[{
						 position:'bottom',	
						 start_scale:0,
						 end_scale:end_scale,
						 scale_space:scale_space
					}],
					background_color : null,
					axis : {
						width : 0
					},
					width : $(window).get(0).innerWidth-150,
					height : $(window).get(0).innerHeight-$(window).get(0).innerHeight/6
				}
			});
			
			chart.draw();
			
		}else {
			$("#ichartjs_content").html('数据读取有误');	
		}			
	}
	
});





