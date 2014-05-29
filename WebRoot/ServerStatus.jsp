<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>服务器状态监测</title>
    <%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
    <%@ include file="/WEB-INF/views/commons/yepnope.jsp"%>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<style type="text/css">
	   	.x-grid-record-gray table{color: #948d8e;}
		.x-grid-record-red table{color: white; background: red;}
		.x-grid-record-yellow table{color: blue;}
		.x-grid-record-green table{color: white;background: green;}
		.x-grid-record-orange table{color: white;background: orange;}    
    </style>
   
  </head>
  
  <script type="text/javascript" >
	yepnope({
	load : [       
		"${ctx}/resources/css/icons.css",
	 	//LockingGridView
		"${ctx}/resources/extjs/ux/css/LockingGridView.css",
		"${ctx}/resources/extjs/ux/LockingGridView.js",
		//MultiSelect
		"${ctx}/resources/extjs/ux/css/lovcombo.css",
		"${ctx}/resources/extjs/ux/Ext.ux.util.js",
		"${ctx}/resources/extjs/ux/Ext.ux.form.LovCombo.js",
		//Ext.ux.grid.RowEditor
        "${ctx}/resources/extjs/ux/css/RowEditor.css",
        "${ctx}/resources/extjs/ux/RowEditor.js"
		],
	complete : function() {
	Ext.ns("Ext.henlo.serverstatus"); // 自定义一个命名空间
	serverstatus = Ext.henlo.serverstatus; // 定义命名空间的别名
	serverstatus = {
		all : ctx + '/portal/server/all',// 加载所有
		detection : ctx + "/portal/server/detection/",//
		pageSize : 100 // 每页显示的记录数
	};
		
	/** 改变页的combo */
	serverstatus.pageSizeCombo = new Share.pageSizeCombo({
			value : '100',
			listeners : {
				select : function(comboBox) {
					serverstatus.pageSize = parseInt(comboBox.getValue());
					serverstatus.bbar.pageSize = parseInt(comboBox.getValue());
					serverstatus.store.baseParams.limit = serverstatus.pageSize;
					serverstatus.store.baseParams.start = 0;
				}
			}
		});
	// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
	serverstatus.pageSize = parseInt(serverstatus.pageSizeCombo.getValue());
	/** 基本信息-数据源 */
	var fields=Ext.data.Record.create([
    	{name:'ID'},
    	{name:'ADDRESS'},
    	{name:'URL'},
    	{name:'STATUS'},
    	{name:'TIMEOUT'},
    	{name:'EDITTIME',type : "date",dateFormat : 'yyyy-MM-dd H:i:s'}
    ]);
	serverstatus.store = new Ext.data.Store({
			autoLoad : false,
			remoteSort : false,
			baseParams : {
				head  : '',
				start : 0,
				limit : serverstatus.pageSize
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : serverstatus.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['ID', 'ADDRESS','URL','STATUS','TIMEOUT','EDITTIME']),
			listeners : {
				'load' : function(store, records, options) {
					//serverstatus.disableFun();
				}
			}
		});
	/** 基本信息-选择模式 */
	serverstatus.selModel =  new Ext.grid.CheckboxSelectionModel();
	//serverstatus.selModel.sortLock();
	/** 基本信息-数据列 */
	serverstatus.colModel = new Ext.grid.ColumnModel({
			defaults : {
				sortable : true,
				width : 80
			},
			columns : [serverstatus.selModel,new Ext.grid.RowNumberer(), {
						hidden : true,
						header : 'ID',
						dataIndex : 'ID'
					},{
						header : '服务器地址',
						dataIndex : 'ADDRESS',
						width: 100
					},{
						header : '检测页面',
						dataIndex : 'URL',
						width: 280
					}, {
						header : '状态',
						dataIndex : 'STATUS'
					}, {
						header : '超时时间 (毫秒)',
						dataIndex : 'TIMEOUT',
						width: 120
					}, {
						header : '最后检测时间',
						dataIndex : 'EDITTIME',
						width: 150,
						renderer: function(value) {
				             return new Date(value).format("Y-m-d H:i:s");
				 		}
					}]
		});

			
	/** 检测 */
	serverstatus.detectionAction = new Ext.Action({
		text : '----开始检测----',
		iconCls : '',
		disabled : false,
		handler : function() {
			serverstatus.detectionFun();
		}
	});
	
	/** 提示 */
	serverstatus.tips = '&nbsp;<font color="red"><b>点击 开始检测 按钮，查看各集群中服务器的响应状态</b></font>';
	/** 顶部工具栏 */
	serverstatus.tbar = ['-',serverstatus.tips,'-',serverstatus.detectionAction];

	/** 底部工具条 */
	serverstatus.bbar = new Ext.PagingToolbar({
			pageSize : serverstatus.pageSize,
			store : serverstatus.store,
			displayInfo : true,
			// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			items : ['-', '&nbsp;', serverstatus.pageSizeCombo]
		});

	/** 基本信息-表格 */
	serverstatus.grid = new Ext.grid.EditorGridPanel({
			store : serverstatus.store,
			colModel : serverstatus.colModel,
			selModel : serverstatus.selModel,
			tbar : serverstatus.tbar,
			bbar : serverstatus.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			// autoExpandColumn :'fieldDesc',
			stripeRows : true,
	//		plugins: [serverstatus.editor],
			listeners : {},
			viewConfig : {
				getRowClass:function(record,index,p,ds) {
				    var cls = 'white-row';
				    if(record.data.STATUS=="success")
				      	cls = 'x-grid-record-green';
					else if(record.data.STATUS=="failed")
						cls = 'x-grid-record-red';				
				    return cls;
				 }
			}
		});

	serverstatus.alwaysFun = function() {
		Share.resetGrid(serverstatus.grid);
		serverstatus.detectionAction.enable();
	};
	
	serverstatus.disableFun = function(){
		serverstatus.detectionAction.disable();
	};
	
	serverstatus.detectionFun = function() {
		serverstatus.detectionAction.disable();
		Share.AjaxRequest({
			url : serverstatus.detection,
			timeout : 300000,
			method : 'POST',
			showMsg : false,
			params : {
				
			},
			callback : function(json) {
				serverstatus.store.reload();
				serverstatus.detectionAction.enable();
			},
			falseFun :function(json) {
				serverstatus.detectionAction.enable();
			}
		});
	};

	
	serverstatus.myPanel = new Ext.Panel({
			id : 'serverstatus' + '_panel',
			renderTo : 'serverstatus',
			layout : 'border',
			boder : false,			
			items : [serverstatus.grid],
			listeners :{
			//进入页面执行事件设置高度和宽度
				'render':function(){
				    this.setWidth(document.body.offsetWidth+document.body.clientWidth-10);
				  	this.setHeight(document.body.offsetHeight+document.body.clientHeight-20);
				 },
				bodyresize:function() {
					//this.setWidth(window.screen.width-10);
				}
			}
		});
		
	}
});
</script>
<body>
	<div id="serverstatus" ></div>	
</body>
</html>