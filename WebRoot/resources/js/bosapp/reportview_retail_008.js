
$("#reportview_table").live( "pageshow" , function(event){
	//updateTable();
	
	
	//$('#selectcmp').append('<option value="hlj" id="where07">HeiLongJiang</option>'); 
	
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
        dateOrder: 'yyyy',
        dateFormat: 'yyyy',
		lang:'zh',
        startYear:currYear - 10, //开始年份
        endYear:currYear + 10 //结束年份
	};
	
	$("#date_start").val('').scroller('destroy').scroller($.extend(opt['date'], opt['default']));
	
	$("#refreshTable").click(function(){
		updateTable();
	});
	
	$("#refreshCus").click(function(){
		updateCus();
	});
	
	updateTable = function(){
		var project_path = $("#reportview_table").attr("project_path");
		var url_init = project_path + "/bosapp/reportview_retail_008";
		var date_start = $("#date_start").val();
		var c_customer_id = $("#select_cus_id").val();
		var param = {"date_start":date_start,"c_customer_id":c_customer_id};
		
		if( !checkIsNull(date_start) ){
			$.mobile.changePage( $("#searchWhereIsNullErrorDialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			return false;
		}
		
		//清理现有的列表
		$("#reportview_table_id_01").empty();
		$("#reportview_table_id_02").empty();
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
				}
				//再请求按经销商统计的年连带率
				var param = {"date_start":date_start,"c_customer_id":c_customer_id,"type":"customer"};
				$.ajax({
					type: "post",
					url:  url_init,
					data: param,
					success: function(json, textStatus){
						if(parseInt(json.o)>0){
							fun_ichartjs_content_init(json);
						}
						$.mobile.loading('hide');
					}
				});
				
			}
		}); 
	};
	
	
	var fun_ichartjs_content_init = function(json){
		
		if(json.success){
			var data = new Array();
			var end_scale =0;
			var scale_space = 0;
			var color = ["#00bfff","#2f4f4f","#8b0000","#8b008b","#b8860b","#dc143c","#a52a2a","#000000","#008000"]
			for(var count=1;count<=parseInt(json.o);count++){
				var cus_value = new Array();
				var cus_name = "";
				var start = (count-1)*12;
				for(var i=start;i<12*count;i++){
					var DOCNO_TOT_QTY = json.msg[i]["DOCNO_TOT_QTY"];
					cus_name = json.msg[i]["C_CUSTOMER_NAME"];
					cus_value.push(DOCNO_TOT_QTY);
				}
				c_i = count-1 ;
				if(c_i>8)
					c_i = 8;
				
				var data_child = '{"name" : "'+cus_name+'","value":['+cus_value+'],"color":"'+color[c_i]+'"}';
				var data_child_json = JSON.parse(data_child);
				data.push(data_child_json);
			}
			
		}
        
		var labels = ["1","2","3","4","5","6","7","8","9","10","11","12"];
		
		var chart = new iChart.Area2D({
			render : 'ichartjs_content',
			data: data,
			title : '年连带率分析(经销商)',
			width : $(window).get(0).innerWidth,
			height : $(window).get(0).innerHeight,
			area_opacity:0.15,
			legend : {
				enable : true
			},
			tip:{
				enable : true,
				listeners:{
					 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
					parseText:function(tip,name,value,text,i){
						return "<span style='color:#005268;font-size:11px;font-weight:600;'>"+name+
								"</span>&nbsp;<span style='color:#005268;font-size:20px;font-weight:600;'>"+value+"</span>";
					}
				}
			},
			sub_option:{
				label:false
			},
			crosshair:{
				enable:true,
				line_color:'#62bce9'
			},
			coordinate:{
				axis : {
					width : [0, 0, 2, 0]
				},
				background_color:'#ffffff',
				height:'90%',
				valid_width:'94%',
				height : $(window).get(0).innerHeight-150,
				scale2grid:false,
				grids:{
					horizontal:{
							way:'share_alike',
							value:8
						}
					}, 
				scale:[{
					 position:'left',	
					 start_scale:0,
					 end_scale:end_scale,
					 scale_space:scale_space,
					 listeners:{
						parseText:function(t,x,y){
							return {text:t+""}
						}
					}
				},{
					 position:'bottom',	
					 start_scale:1,
					 end_scale:12,
					 parseText:function(t,x,y){
						return {textY:y+10}
					 },
					 labels:labels
				}]
			}
		});
		chart.draw();
		
	}
	
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
				$.mobile.loading( 'hide');
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
			var table_01 = $("#reportview_table_id_01");
			var table_02 = $("#reportview_table_id_02");
			//添加表头			
			$("#reportview_table_count").html("数据："+json.o+" 条");
			
			
			var tr_01_list = ["BILLDATE","C_CUSTOMER_NAME","C_STORE_NAME","TOT_AMT_ACTUAL","DOCNO_CNT","DOCNO_AMT_ACTUAL"];
			var tr_02_list = ["BILLDATE","C_CUSTOMER_NAME","C_STORE_NAME","TOT_AMT_ACTUAL","SALESREP_ID","DOCNO_TOT_QTY","SALESREP_AMT_ACTUAL"];
			
			//遍历json数组
			for(var i=0;i<json.msg.length;i++){
				if(i==0){
					var tr_01 ="<thead><tr >";
					var tr_02 ="<thead><tr >";
					var jsonobj = json.msg[i];
					for(var x in jsonobj){
						
						for(var y in tr_01_list){
							if(x==tr_01_list[y]){
								var th = "<th >"+jsonobj[x]+"</th>";
								tr_01 = tr_01+th;
							}
						}	
						
						for(var y in tr_02_list){
							if(x==tr_02_list[y]){
								var th = "<th >"+jsonobj[x]+"</th>";
								tr_02 = tr_02+th;
							}
						}
						
					}
					tr_01 = tr_01 +"</tr></thead>";
					tr_02 = tr_02 +"</tr></thead>";
					
					table_01.append(tr_01);
					table_01.append("<tbody></tbody>");
					
					table_02.append(tr_02);
					table_02.append("<tbody></tbody>");
				}else{						
					var tr_01 ="<tr>";
					var tr_02 ="<tr>";
					
					var jsonobj = json.msg[i];
					for(var x in jsonobj){
						
						for(var y in tr_01_list){
							if(x==tr_01_list[y]){
								var td = "<td >"+jsonobj[x]+"</td>";
								tr_01 = tr_01+td;
							}
						}
						
						for(var y in tr_02_list){
							if(x==tr_02_list[y]){
								var td = "<td >"+jsonobj[x]+"</td>";
								tr_02 = tr_02+td;
							}
						}
						
					}
					
					tr_01 = tr_01 +"</tr>";
					table_01.append(tr_01);		
					
					tr_02 = tr_02 +"</tr>";
					table_02.append(tr_02);	
				}
			}
			//$("#reportview_table_id" ).table("refresh");
			
		}else {
			$("#reportview_table_div_id").html('数据读取有误');							
		}			
	}
	
	
});





