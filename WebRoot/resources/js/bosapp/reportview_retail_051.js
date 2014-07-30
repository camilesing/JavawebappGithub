
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
        startYear:currYear - 10, //开始年份
        endYear:currYear + 10 //结束年份
	};
	
	$("#date_start").val('').scroller('destroy').scroller($.extend(opt['date'], opt['default']));
	$("#date_end").val('').scroller('destroy').scroller($.extend(opt['date'], opt['default']));
	
	updateTable = function(){
		var project_path = $("#reportview_table").attr("project_path");
		var url_init = project_path + "/bosapp/reportview_retail_051";
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
					//fun_reportview_ichartjs_init(json);
				}
				
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
						if(!isNaN(jsonobj[x])&&parseInt(jsonobj[x])<0){
							var td = "<td><span style='color:red'>"+jsonobj[x]+"</span></td>";
							tr = tr+td;
						}else{
							var td = "<td >"+jsonobj[x]+"</td>";
							tr = tr+td;
						}	
						
						
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
			var color = ["#f5f5dc","#ffe4c4","#0072E3","#8a2be2","#a52a2a","#deb887","#5f9ea0","#7fff00","#d2691e"]
			var end_scale =0;
			var scale_space = 0;
			var c_i = 0 ;
			for(var i=1;i<json.msg.length;i++){
				c_i = i-1 ;
				if(c_i>8)
					c_i = 8;
				var child = '{"name" : "'+json.msg[i]["ATTRIBNAME"]+'","value" : '+parseInt(json.msg[i]["AMTSALEOUT"]/10000)+',"color":"'+color[c_i]+'"}';
				var child_json = JSON.parse(child);
				data.push(child_json);
			}
			
			var chart = iChart.create({
	            render:"ichartjs_content",
	            width : $(window).get(0).innerWidth,
				height : $(window).get(0).innerHeight,
	            background_color:"#fefefe",
	            gradient:false,
	            color_factor:0.2,
	            border:{
	                  color:"BCBCBC",
	                  width:1
	            },
	            align:"left",
	            offsetx:80,
	            offsety:0,
	            sub_option:{
	                  border:{
	                        color:"#BCBCBC",
	                        width:1
	                  },
	                  label:{
	                        fontweight:500,
	                        fontsize:11,
	                        color:"#4572a7",
	                        sign:"square",
	                        sign_size:12,
	                        border:{
	                              color:"#BCBCBC",
	                              width:1
	                        },
	                        background_color:"#fefefe"
	                  }
	            },
	            turn_off_touchmove:true,
	            shadow:true,
	            shadow_color:"#666666",
	            shadow_blur:2,
	            showpercent:false,
	            column_width:"70%",
	            bar_height:"70%",
	            radius:"60%",
	            title:{
	                  text:"销售出库金额按类别(万元)",
	                  color:"#111111",
	                  fontsize:20,
	                  font:"微软雅黑",
	                  textAlign:"center",
	                  height:30,
	                  offsetx:0,
	                  offsety:0
	            },
	            subtitle:{
	                  text:"",
	                  color:"#111111",
	                  fontsize:16,
	                  font:"微软雅黑",
	                  textAlign:"center",
	                  height:20,
	                  offsetx:0,
	                  offsety:0
	            },
	            footnote:{
	                  text:"",
	                  color:"#111111",
	                  fontsize:12,
	                  font:"微软雅黑",
	                  textAlign:"right",
	                  height:20,
	                  offsetx:0,
	                  offsety:0
	            },
	            legend:{
	                  enable:false,
	                  background_color:"#fefefe",
	                  color:"#333333",
	                  fontsize:12,
	                  border:{
	                        color:"#BCBCBC",
	                        width:1
	                  },
	                  column:1,
	                  align:"right",
	                  valign:"center",
	                  offsetx:0,
	                  offsety:0
	            },
	            coordinate:{
	                  width:"80%",
	                  height:"84%",
	                  background_color:"#ffffff",
	                  axis:{
	                        color:"#a5acb8",
	                        width:[1,"",1,""]
	                  },
	                  grid_color:"#d9d9d9",
	                  label:{
	                        fontweight:500,
	                        color:"#666666",
	                        fontsize:11
	                  }
	            },
	            label:{
	                  fontweight:500,
	                  color:"#666666",
	                  fontsize:11
	            },
	            type:"pie2d",
	            data:data
	      });
	      chart.draw();
			
			
		}else {
			$("#ichartjs_content").html('数据读取有误');	
		}			
	}
	
});





