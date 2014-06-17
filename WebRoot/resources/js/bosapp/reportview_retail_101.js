
$("#reportview_table").live( "pageshow" , function(event){
	//updateTable();
});

$("#reportview_table").live( "pageinit" , function(event){
	
	updateTable = function(){
		var project_path = $("#reportview_table").attr("project_path");
		var url_init = project_path + "/bosapp/reportview_retail_101";
		var date_start = $("#date_start").val();
		
		var c_customer_id = $("#select_cus_id").val();
		var param = {"c_customer_id":c_customer_id};

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
	
	$("#refreshCus").click(function(){
		updateCus();
	});
	

	var updateCus = function(){
		var project_path = $("#reportview_table").attr("project_path");
		var url_init = project_path + "/bosapp/c_customer";

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
			data: '',
			success: function(json, textStatus){
				if(parseInt(json.o)>0){
					fun_cus_init(json);
				}
				
				$.mobile.loading('hide');
			}
		}); 
	};
	
	
	var fun_cus_init = function(json){
		var mySelect = document.getElementById("select_cus_id");
		for(var i=0;i<json.msg.length;i++){
			var C_CUSTOMER_ID = json.msg[i]["C_CUSTOMER_ID"];
			var C_CUSTOMER_NAME = json.msg[i]["C_CUSTOMER_NAME"];
			var opp = new Option(C_CUSTOMER_NAME,C_CUSTOMER_ID);
			mySelect.add(opp);			
		}
		$("#select_cus_id").selectmenu('refresh', true);
	}
	
	var fun_reportview_table_init = function(json){
		if(json.success){
			//清理现有的列表
			var table = $("#reportview_table_id");
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
	
	var fun_reportview_ichartjs_init = function(json){
		if(json.success){
			//定义数据
			var data = new Array();
			var labels = new Array();
			var amt_receivable = new Array();
			var amt_receive = new Array();
			
			var end_scale =0;
			var scale_space = 0;
			
			for(var i=1;i<json.msg.length&&i<20;i++){
				
				amt_receivable.push(json.msg[i]["AMT_RECEIVABLE"]);
				
				amt_receive.push(json.msg[i]["AMT_RECEIVE"]);
				
				labels.push(json.msg[i]["C_CUSTOMER_NAME"]);
			}
			
			var child_amt_receivable = '{"name" : "应收款","value":['+amt_receivable+'],"color":"#32bdbc"}';
			var child_json_amt_receivable = JSON.parse(child_amt_receivable);
			data.push(child_json_amt_receivable);
			
			var child_amt_receive = '{"name" : "已收款","value":['+amt_receive+'],"color":"#d75a5e"}';
			var child_json_amt_receive = JSON.parse(child_amt_receive);
			data.push(child_json_amt_receive);
				        
			var chart = new iChart.BarMulti2D({
				render : 'ichartjs_content',
				data: data,
				labels:labels,
				title : {
					text:'经销商应收款分析(TOP20)',
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
						 scale_space:scale_space,
						 scale_share:3
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





