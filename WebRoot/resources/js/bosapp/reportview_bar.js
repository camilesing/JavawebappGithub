$("#reportview_bar").live( "pageinit" , function(event){
	var RocknCoder = RocknCoder || {};
	RocknCoder.Pages = RocknCoder.Pages || {};

	RocknCoder.Pages.Kernel = function (event) {
		var that = this,
			eventType = event.type,
			pageName = $(this).attr("data-rockncoder-jspage");
		if (RocknCoder && RocknCoder.Pages && pageName && RocknCoder.Pages[pageName] && RocknCoder.Pages[pageName][eventType]) {
			RocknCoder.Pages[pageName][eventType].call(that);
		}
	};

	RocknCoder.Pages.Events = function () {
		$("div[data-rockncoder-jspage]").on(
			'pagebeforecreate pagecreate pagebeforeload pagebeforeshow pageshow pagebeforechange pagechange pagebeforehide pagehide pageinit',
			RocknCoder.Pages.Kernel).on(
			"pageinit", RocknCoder.hideAddressBar);
	} ();

	RocknCoder.Pages.manageBarChart = function () {
		var pageshow = function () {
				updateChart();
				$("#refreshBarChart").click(function(){
					updateChart();
				});
			},
			pagehide = function () {
				$("#refreshBarChart").unbind('click');
			},
			updateChart= function(){
				var project_path = $("#reportview_bar").attr("project_path");
				var url_init = project_path+"/bosappaction/reportview_bardetail";
				var store = $("#store").val();
				var date_start = $("#date_start").val();
				var date_end = $("#date_end").val();
				var param = {"store":encodeURIComponent(store),"date_start":date_start,"date_end":date_end};
				$.ajax({
					type: "post",
					url:  url_init,
					data: param,
					success: function(json, textStatus){
						fun_reportview_bar_init(json);
					}
				});
			},
			fun_reportview_bar_init = function(json){
				var cosPoints = [];
				//遍历json数组
				for(var i=0;i<json.msg.length;i++){					
					cosPoints.push([json.msg[i].sales, json.msg[i].store]);							
				}
				
				showChart(cosPoints);		
			},
			showChart = function(cosPoints){
				$.jqplot('barChart', [cosPoints], {				
					title: {
						text: '',  //设置当前图的标题
						show: true,//设置当前图的标题是否显示
					},				
					seriesDefaults:{
						renderer:$.jqplot.BarRenderer,
						shadowAngle: 135,
						rendererOptions: {
							barDirection: 'horizontal'
						},					
						pointLabels: {show: true, location: 'e', edgeTolerance: -15}					
					},				
					axes: {
						yaxis: {
							renderer: $.jqplot.CategoryAxisRenderer
						}
					}
				}).replot({clear: true, resetAxes:true});
			};
		return {
			pageshow: pageshow,
			pagehide: pagehide
		}
	}();
});





