<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<style type="text/css">
	.x-grid-record-0 table{color: black; background: #FFB5B5;}
	.x-grid-record-1 table{color: black; background: #FFBD9D;}
	.x-grid-record-22 table{color: black; background: #9999CC;}
	.x-grid-record-2 table{color: black; background: #DEFFAC;}
</style>
<div id="${param.id}"></div>
<script type="text/javascript">
$(document).ready(function() {
	Ext.ns("Ext.Authority.wzgdshoulywnr"); // 自定义一个命名空间
	wzgdshoulywnr = Ext.Authority.wzgdshoulywnr; // 定义命名空间的别名
	
	function formatQtip(data, metadata, record, rowIndex, columnIndex, store){ 
	   var title ="";
	   var tip =data; 
	   metadata.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';  
	   return data;  
	};
	
	wzgdshoulywnr = {
		all : ctx + '/wzgdshoulywnr/all',// 加载所有
		save : ctx + "/wzgdshoulywnr/save",//保存
		del : ctx + "/wzgdshoulywnr/del/",//删除
		verify : ctx + "/wzgdshoulywnr/verify/", //缴费确认
		pay_online : ctx + "/wzgdshoulywnr/pay_online/", //发送支付请求 在线
		makesure : ctx + "/wzgdshoulywnr/makesure/", //
		makesure_verify : ctx + "/wzgdshoulywnr/makesure_verify/", 
		clear : ctx + "/wzgdshoulywnr/clear/", 
		pageSize : 20, // 每页显示的记录数
		wzsjjdealstatus:eval('(${fields.wzsjjdealstatus==null?"{}":fields.wzsjjdealstatus})'),
		task_count : 60
	};
	
	/** 改变页的combo */
	wzgdshoulywnr.pageSizeCombo = new Share.pageSizeCombo({
				value : '20',
				listeners : {
					select : function(comboBox) {
						wzgdshoulywnr.pageSize = parseInt(comboBox.getValue());
						wzgdshoulywnr.bbar.pageSize = parseInt(comboBox.getValue());
						wzgdshoulywnr.store.baseParams.limit = wzgdshoulywnr.pageSize;
						wzgdshoulywnr.store.baseParams.start = 0;
						wzgdshoulywnr.store.load();
					}
				}
			});
	// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
	wzgdshoulywnr.pageSize = parseInt(wzgdshoulywnr.pageSizeCombo.getValue());
	/** 基本信息-数据源 */
	wzgdshoulywnr.store = new Ext.data.Store({
				autoLoad : true,
				remoteSort : true,
				baseParams : {
					start : 0,
					limit : wzgdshoulywnr.pageSize
				},
				proxy : new Ext.data.HttpProxy({// 获取数据的方式
					method : 'POST',
					url : wzgdshoulywnr.all
				}),
				reader : new Ext.data.JsonReader({// 数据读取器
					totalProperty : 'results', // 记录总数
					root : 'rows' // Json中的列表数据根节点
				}, ['id', 'yonghbm', 'yonghxm','yiddh', 'chanpgm', 'kaisrq','jiesrq','chulzt']),
				listeners : {
					'load' : function(store, records, options) {
						wzgdshoulywnr.alwaysFun();
					}
				}
			});
	
	/** 基本信息-选择模式 */
	wzgdshoulywnr.selModel = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true,
				listeners : {
					'rowselect' : function(selectionModel, rowIndex, record) {
						wzgdshoulywnr.clearAction.enable();
						if(record.data.chulzt=='0'){
							wzgdshoulywnr.makesureAction.enable();
						}
						else if(record.data.chulzt=='01'||record.data.chulzt=='02'){
							wzgdshoulywnr.makesure_verifyAction.enable();
						}
						else if(record.data.chulzt=='1'||record.data.chulzt=='22'){
							wzgdshoulywnr.pay_onlineAction.enable();
						}
						else if(record.data.chulzt=='21'){
							wzgdshoulywnr.pay_onlineAction.enable();
						}
							
					},
					'rowdeselect' : function(selectionModel, rowIndex, record) {
						wzgdshoulywnr.alwaysFun();
					}
				}
			});
	/** 基本信息-数据列 */
	wzgdshoulywnr.colModel = new Ext.grid.ColumnModel({
				defaults : {
					sortable : true,
					width : 70
				},
				columns : [wzgdshoulywnr.selModel, {
							hidden : true,
							header : 'ID',
							dataIndex : 'id'
						}, {
							header : '用户编码',
							dataIndex : 'yonghbm',
							width : 80,
							renderer:formatQtip
						},{
							header : '用户姓名',
							dataIndex : 'yonghxm',
							width : 80,
							renderer:formatQtip
						},{
							header : '移动电话',
							dataIndex : 'yiddh',
							width : 80,
							renderer:formatQtip
						}, {
							header : '产品购买',
							width : 260,
							dataIndex : 'chanpgm',
							//renderer: formatQtip
							renderer: function (v, m, r) {
						      m.attr = 'style="white-space:normal;word-wrap:break-word;word-break:break-all;"';
						      return v;
						    }
						}, {
							header : '开始日期',
							dataIndex : 'kaisrq',
							width : 80
						},{
							header : '结束日期',
							dataIndex : 'jiesrq',
							width : 80
						},{
							header : '处理状态',
							dataIndex : 'chulzt',
							width : 110,
							renderer : function(v) {
								return Share.map(v,wzgdshoulywnr.wzsjjdealstatus , '');
							}
						}]
			});
	
	wzgdshoulywnr.makesureAction = new Ext.Action({
		text : '缴费确认(手机端)',
		iconCls : 'db-icn-ipaper',
		disabled : true,
		handler : function() {
			var record = wzgdshoulywnr.grid.getSelectionModel().getSelected();
			Share.AjaxRequest({
				url : wzgdshoulywnr.makesure,
				timeout : 300000,
				params : {
					id : record.data.id
				},
				callback : function(json) {
					wzgdshoulywnr.store.reload();
				},
				falseFun :function(json) {
					
				}
			});
		}
	});
	
	wzgdshoulywnr.makesure_verifyAction = new Ext.Action({
		text : '回复验证',
		iconCls : 'db-icn-ipod',
		disabled : true,
		handler : function() {
			var record = wzgdshoulywnr.grid.getSelectionModel().getSelected();
			Share.AjaxRequest({
				url : wzgdshoulywnr.makesure_verify,
				timeout : 300000,
				params : {
					id : record.data.id
				},
				callback : function(json) {
					wzgdshoulywnr.store.reload();
				},
				falseFun :function(json) {
					
				}
			});
		}
	});
	
	wzgdshoulywnr.pay_onlineAction = new Ext.Action({
		text : '缴费(在线)',
		iconCls : 'db-icn-money_yen',
		disabled : true,
		handler : function() {
			var record = wzgdshoulywnr.grid.getSelectionModel().getSelected();
			var id = record.data.id;
			var chulzt = record.data.chulzt;
			if(chulzt!='2'){
				window.open(wzgdshoulywnr.pay_online+"?id="+id+"&time="+(new Date()).toString());
				wzgdshoulywnr.addWindow.setIconClass('db-icn-money_yen'); // 设置窗口的样式
				wzgdshoulywnr.addWindow.setTitle('缴费'); // 设置窗口的名称
				wzgdshoulywnr.addWindow.show().center();
				wzgdshoulywnr.formPanel.getForm().reset();
				wzgdshoulywnr.formPanel.getForm().loadRecord(record);
			}
			else if(chulzt=='2')
				alert("已完成缴费");
		}
	});

	wzgdshoulywnr.clearAction = new Ext.Action({
		text : '状态初始化',
		iconCls : 'db-icn-refresh',
		disabled : true,
		handler : function() {
			var record = wzgdshoulywnr.grid.getSelectionModel().getSelected();
			Share.AjaxRequest({
				url : wzgdshoulywnr.clear,
				timeout : 300000,
				params : {
					id : record.data.id
				},
				callback : function(json) {
					wzgdshoulywnr.store.reload();
				},
				falseFun :function(json) {
					
				}
			});
		}
	});
	
	/** 查询 */
	wzgdshoulywnr.searchField = new Ext.ux.form.SearchField({
				store : wzgdshoulywnr.store,
				paramName : 'yonghxm',
				emptyText : '用户姓名',
				style : 'margin-left: 5px;'
			});
	/** 顶部工具栏 */
	wzgdshoulywnr.tbar = [wzgdshoulywnr.makesureAction,'-',
	                         wzgdshoulywnr.makesure_verifyAction,'-',
	                         wzgdshoulywnr.pay_onlineAction,'-',
	                         wzgdshoulywnr.clearAction,'-',
	                         wzgdshoulywnr.searchField];
	/** 底部工具条 */
	wzgdshoulywnr.bbar = new Ext.PagingToolbar({
				pageSize : wzgdshoulywnr.pageSize,
				store : wzgdshoulywnr.store,
				displayInfo : true,
				// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
				items : ['-', '&nbsp;', wzgdshoulywnr.pageSizeCombo]
			});
	/** 基本信息-表格 */
	wzgdshoulywnr.grid = new Ext.grid.GridPanel({
				// title : '模块列表',
				store : wzgdshoulywnr.store,
				colModel : wzgdshoulywnr.colModel,
				selModel : wzgdshoulywnr.selModel,
				tbar : wzgdshoulywnr.tbar,
				bbar : wzgdshoulywnr.bbar,
				autoScroll : 'auto',
				region : 'center',
				loadMask : true,
				// autoExpandColumn :'wzgdshoulywnrDesc',
				stripeRows : true,
				listeners : {},
				viewConfig : {
					forceFit:true,
					getRowClass:function(record,index,p,ds) {
					    var cls = 'white-row';
					    if(record.data.chulzt=='0'||record.data.chulzt=='01'||record.data.chulzt=='02')
					      	cls = 'x-grid-record-0';
					    else if(record.data.chulzt=='1'||record.data.chulzt=='11'||record.data.chulzt=='12'||record.data.chulzt=='21')
					      	cls = 'x-grid-record-1';
					    else if(record.data.chulzt=='22')
					      	cls = 'x-grid-record-22';
					    else if(record.data.chulzt=='2')
					      	cls = 'x-grid-record-2';
						
					    return cls;
					 }
					}
			});
	
	/** 基本信息-详细信息的form */
	wzgdshoulywnr.formPanel = new Ext.form.FormPanel({
				frame : false,
				title : '车辆信息',
				bodyStyle : 'padding:10px;border:0px',
				labelwidth : 50,
				defaultType : 'textfield',
				items : [{
							fieldLabel : 'ID',
							readOnly:true,
							value : '',
							name : 'id',
							anchor : '99%'
						},{
							fieldLabel : '用户姓名',
							readOnly:true,
							value : '',
							name : 'yonghxm',
							anchor : '99%'
						},{
							fieldLabel : '产品购买',
							readOnly:true,
							value : '',
							name : 'chanpgm',
							anchor : '99%'
						}]
			});
	/** 编辑新建窗口 */
	wzgdshoulywnr.addWindow = new Ext.Window({
				layout : 'fit',
				width : 300,
				height : 180,
				closeAction : 'hide',
				plain : true,
				modal : true,
				resizable : true,
				items : [wzgdshoulywnr.formPanel],
				buttons : [
				        {
							text : '已完成',							
							id : 'shoulywnr_verify',
							handler : function() {
								wzgdshoulywnr.verifyFun();
							}
						},
						{
							text : '发生错误',
							handler : function() {
								wzgdshoulywnr.addWindow.hide();
							}
						}]
			});
	wzgdshoulywnr.alwaysFun = function() {
		Share.resetGrid(wzgdshoulywnr.grid);
		wzgdshoulywnr.makesureAction.disable();
		wzgdshoulywnr.makesure_verifyAction.disable();
		wzgdshoulywnr.pay_onlineAction.disable();
		wzgdshoulywnr.clearAction.disable();
	};
	
	//验证是否已完成缴费
	wzgdshoulywnr.verifyFun = function() {
		var form = wzgdshoulywnr.formPanel.getForm();
		if (!form.isValid()) {
			return;
		}
		// 发送请求
		Share.AjaxRequest({
			url : wzgdshoulywnr.verify,
			timeout : 300000,
			params : {
				id : form.findField('id').getValue()
			},
			callback : function(json) {
				wzgdshoulywnr.store.reload();
			},
			falseFun :function(json) {
				
			}
		});
	};
	
	wzgdshoulywnr.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [wzgdshoulywnr.grid]
		});
		
});
</script>
