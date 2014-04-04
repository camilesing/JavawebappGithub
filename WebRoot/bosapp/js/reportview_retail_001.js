
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
		var url_init = project_path + "/bosappaction/reportview_retail_001";
		var date_start = $("#date_start").val();
		var date_end = $("#date_end").val();
		var param = {"date_start":date_start,"date_end":date_end};
		
		//清理现有的列表
		$("#reportview_table_id").empty();
		$("#reportview_table_count").html("");
		$.mobile.showPageLoadingMsg('a', "加载中……",true);
		
		$.ajax({
			type: "post",
			url:  url_init,
			data: param,
			success: function(json, textStatus){
				fun_reportview_table_init(json);
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
			table.empty();
			//添加表头
			$("#reportview_table_count").html("数据："+json.o+" 条");		
			
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
	
});





