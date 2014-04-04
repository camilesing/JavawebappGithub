
$("#reportview_table").live( "pageshow" , function(event){
	updateTable();
});

$("#reportview_table").live( "pageinit" , function(event){
	updateTable = function(){
		var project_path = $("#reportview_table").attr("project_path");
		var url_init = project_path + "/bosappaction/reportview_tabledetail";
		var param = {};
		$.ajax({
			type: "post",
			url:  url_init,
			data: param,
			success: function(json, textStatus){
				fun_reportview_table_init(json);
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





