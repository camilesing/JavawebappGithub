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
	Ext.ns("Ext.Authority.yqgdrequestdata"); // 自定义一个命名空间
	yqgdrequestdata = Ext.Authority.yqgdrequestdata; // 定义命名空间的别名
	
	function formatQtip(data, metadata, record, rowIndex, columnIndex, store){ 
	   var title ="";
	   var tip =data; 
	   metadata.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';  
	   return data;  
	};
	
	yqgdrequestdata = {
		all : ctx + '/yqgd/all',// 加载所有
		save : ctx + "/yqgd/save",//保存
		del : ctx + "/yqgd/del/",//删除
		requestdata : ctx + "/yqgd/requestdata/",//模拟支付请求
		verify : ctx + "/yqgd/verify/", //缴费确认
		pay_backstage : ctx + "/yqgd/pay_backstage/", //发送支付请求 在线
		clear : ctx + "/yqgd/clear/", //状态初始化
		smssend : ctx + "/yqgd/smssend/",
		smssend_verify : ctx + "/yqgd/smssend_verify/",
		requestdataquery : ctx + "/yqgd/requestdataquery/",
		requestsmsquery : ctx + "/yqgd/requestsmsquery/",
		pageSize : 20, // 每页显示的记录数
		wzsjjdealstatus:eval('(${fields.wzsjjdealstatus==null?"{}":fields.wzsjjdealstatus})'),
		task_count : 60
	};
	
	/** 改变页的combo */
	yqgdrequestdata.pageSizeCombo = new Share.pageSizeCombo({
				value : '20',
				listeners : {
					select : function(comboBox) {
						yqgdrequestdata.pageSize = parseInt(comboBox.getValue());
						yqgdrequestdata.bbar.pageSize = parseInt(comboBox.getValue());
						yqgdrequestdata.store.baseParams.limit = yqgdrequestdata.pageSize;
						yqgdrequestdata.store.baseParams.start = 0;
						yqgdrequestdata.store.load();
					}
				}
			});
	// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
	yqgdrequestdata.pageSize = parseInt(yqgdrequestdata.pageSizeCombo.getValue());
	/** 基本信息-数据源 */
	yqgdrequestdata.store = new Ext.data.Store({
				autoLoad : true,
				remoteSort : true,
				baseParams : {
					start : 0,
					limit : yqgdrequestdata.pageSize
				},
				proxy : new Ext.data.HttpProxy({// 获取数据的方式
					method : 'POST',
					url : yqgdrequestdata.all
				}),
				reader : new Ext.data.JsonReader({// 数据读取器
					totalProperty : 'results', // 记录总数
					root : 'rows' // Json中的列表数据根节点
				}, ['id', 'openbank', 'cardtype','cardno', 'usrname','transamt','chulzt','responsecode','transstat','message','mobilephone','addtime','edittime']),
				listeners : {
					'load' : function(store, records, options) {
						yqgdrequestdata.alwaysFun();
					}
				}
			});
	
	/** 基本信息-选择模式 */
	yqgdrequestdata.selModel = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true,
				listeners : {
					'rowselect' : function(selectionModel, rowIndex, record) {
						yqgdrequestdata.clearAction.enable();
						yqgdrequestdata.requestdataAction.enable();
						if(record.data.chulzt=='1'){
							yqgdrequestdata.pay_backstageAction.enable();
						}
					},
					'rowdeselect' : function(selectionModel, rowIndex, record) {
						yqgdrequestdata.alwaysFun();
					}
				}
			});
	/** 基本信息-数据列 */
	yqgdrequestdata.colModel = new Ext.grid.ColumnModel({
				defaults : {
					sortable : true,
					width : 70
				},
				columns : [yqgdrequestdata.selModel, {
							hidden : true,
							header : 'ID',
							dataIndex : 'id'
						}, {
							header : '开户行',
							dataIndex : 'openbank',
							width : 80,
							renderer:formatQtip
						},{
							header : '卡或折',
							dataIndex : 'cardtype',
							width : 80,
							renderer:formatQtip
						},{
							header : '卡号',
							dataIndex : 'cardno',
							width : 100,
							renderer:formatQtip
						},{
							header : '持卡人',
							dataIndex : 'usrname',
							width : 80,
							renderer:formatQtip
						},{
							header : '交易金额(分)',
							dataIndex : 'transamt',
							width : 80,
							renderer:formatQtip
						},{
							header : '回复信息',
							dataIndex : 'message',
							width : 120,
							renderer:formatQtip
						},{
							header : '移动电话',
							dataIndex : 'mobilephone',
							width : 80
						},{
							header : '处理状态',
							dataIndex : 'chulzt',
							width : 60,
							renderer : function(v) {
								return Share.map(v,yqgdrequestdata.wzsjjdealstatus , '');
							}
						},{
							header : '请求时间',
							dataIndex : 'addtime',
							width : 100
						},{
							header : '最后答复时间',
							dataIndex : 'edittime',
							width : 100
						}
						]
			});
	
	
	yqgdrequestdata.pay_backstageAction = new Ext.Action({
		text : '缴费(在线)',
		iconCls : 'db-icn-money_yen',
		disabled : true,
		handler : function() {
			var record = yqgdrequestdata.grid.getSelectionModel().getSelected();
			var id = record.data.id;
			var chulzt = record.data.chulzt;
			if(chulzt!='2'){
				window.open(yqgdrequestdata.pay_backstage+"?id="+id+"&time="+(new Date()).toString());
				yqgdrequestdata.addWindow.setIconClass('db-icn-money_yen'); // 设置窗口的样式
				yqgdrequestdata.addWindow.setTitle('缴费'); // 设置窗口的名称
				yqgdrequestdata.addWindow.show().center();
				yqgdrequestdata.formPanel.getForm().reset();
				yqgdrequestdata.formPanel.getForm().loadRecord(record);
			}
			else if(chulzt=='2')
				alert("已完成缴费");
		}
	});

	yqgdrequestdata.clearAction = new Ext.Action({
		text : '状态初始化',
		iconCls : 'db-icn-refresh',
		disabled : true,
		handler : function() {
			var record = yqgdrequestdata.grid.getSelectionModel().getSelected();
			Share.AjaxRequest({
				url : yqgdrequestdata.clear,
				timeout : 300000,
				params : {
					id : record.data.id
				},
				callback : function(json) {
					yqgdrequestdata.store.reload();
				},
				falseFun :function(json) {
					
				}
			});
		}
	});
	
	yqgdrequestdata.requestdataAction = new Ext.Action({
		text : '模拟请求',
		iconCls : 'db-icn-refresh',
		disabled : false,
		handler : function() {
			Share.AjaxRequest({
				url : yqgdrequestdata.requestdata,
				timeout : 300000,
				params : {
					reqstr : '中国工商银行|0|6212261203005120596|陈锋|01|330381198501271234|乐清广电扣费|1|18857846128|2;中国工商银行|0|900758403049921840|测试|01|120221198606121502|乐清广电扣费|300|18857846128|1'
				},
				callback : function(json) {
					yqgdrequestdata.store.reload();
				},
				falseFun :function(json) {
					
				}
			});
		}
	});
	
	yqgdrequestdata.smssendAction = new Ext.Action({
		text : '短信发送',
		iconCls : 'db-icn-refresh',
		disabled : false,
		handler : function() {
			Share.AjaxRequest({
				url : yqgdrequestdata.smssend,
				timeout : 300000,
				params : {
					reqstr : '1|18857846128|短信信息测试[回复A B C D];'
				},
				callback : function(json) {
					yqgdrequestdata.store.reload();
				},
				falseFun :function(json) {
					
				}
			});
		}
	});
	
	yqgdrequestdata.smssend_verifyAction = new Ext.Action({
		text : '短信回复验证',
		iconCls : 'db-icn-refresh',
		disabled : false,
		handler : function() {
			Share.AjaxRequest({
				url : yqgdrequestdata.smssend_verify,
				timeout : 300000,
				params : {
					reqstr : ''
				},
				callback : function(json) {
					yqgdrequestdata.store.reload();
				},
				falseFun :function(json) {
					
				}
			});
		}
	});
	
	yqgdrequestdata.requestdataqueryAction = new Ext.Action({
		text : '请求回复结果',
		iconCls : 'db-icn-refresh',
		disabled : false,
		handler : function() {
			Share.AjaxRequest({
				url : yqgdrequestdata.requestdataquery,
				timeout : 300000,
				params : {
					reqstr : '1|900758403049921840;2|900758403049921840;'
				},
				callback : function(json) {
					yqgdrequestdata.store.reload();
				},
				falseFun :function(json) {
					
				}
			});
		}
	});
	
	yqgdrequestdata.requestsmsqueryAction = new Ext.Action({
		text : '短信回复结果',
		iconCls : 'db-icn-refresh',
		disabled : false,
		handler : function() {
			Share.AjaxRequest({
				url : yqgdrequestdata.requestsmsquery,
				timeout : 300000,
				params : {
					reqstr : '1|18857846128;'
				},
				callback : function(json) {
					yqgdrequestdata.store.reload();
				},
				falseFun :function(json) {
					
				}
			});
		}
	});
	
	/** 查询 */
	yqgdrequestdata.searchField = new Ext.ux.form.SearchField({
				store : yqgdrequestdata.store,
				paramName : 'usrname',
				emptyText : '持卡人',
				style : 'margin-left: 5px;'
			});
	/** 顶部工具栏 
	yqgdrequestdata.tbar = [ yqgdrequestdata.requestdataAction,'-',
	                         yqgdrequestdata.pay_backstageAction,'-',
	                         yqgdrequestdata.clearAction,'-',
	                         yqgdrequestdata.smssendAction,'-',
	                         yqgdrequestdata.smssend_verifyAction,'-',
	                         yqgdrequestdata.requestdataqueryAction,'-',
	                         yqgdrequestdata.requestsmsqueryAction,'-',
	                         yqgdrequestdata.searchField];
	*/
	yqgdrequestdata.tbar = [ yqgdrequestdata.searchField];
	/** 底部工具条 */
	yqgdrequestdata.bbar = new Ext.PagingToolbar({
				pageSize : yqgdrequestdata.pageSize,
				store : yqgdrequestdata.store,
				displayInfo : true,
				// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
				items : ['-', '&nbsp;', yqgdrequestdata.pageSizeCombo]
			});
	/** 基本信息-表格 */
	yqgdrequestdata.grid = new Ext.grid.GridPanel({
				// title : '模块列表',
				store : yqgdrequestdata.store,
				colModel : yqgdrequestdata.colModel,
				selModel : yqgdrequestdata.selModel,
				tbar : yqgdrequestdata.tbar,
				bbar : yqgdrequestdata.bbar,
				autoScroll : 'auto',
				region : 'center',
				loadMask : true,
				// autoExpandColumn :'yqgdrequestdataDesc',
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
	yqgdrequestdata.formPanel = new Ext.form.FormPanel({
				frame : false,
				title : '支付信息',
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
							fieldLabel : '持卡人',
							readOnly:true,
							value : '',
							name : 'usrname',
							anchor : '99%'
						},{
							fieldLabel : '交易金额',
							readOnly:true,
							value : '',
							name : 'transamt',
							anchor : '99%'
						}]
			});
	/** 编辑新建窗口 */
	yqgdrequestdata.addWindow = new Ext.Window({
				layout : 'fit',
				width : 300,
				height : 180,
				closeAction : 'hide',
				plain : true,
				modal : true,
				resizable : true,
				items : [yqgdrequestdata.formPanel],
				buttons : [
				        {
							text : '已完成',							
							id : 'shoulywnr_verify',
							handler : function() {
								yqgdrequestdata.verifyFun();
							}
						},
						{
							text : '发生错误',
							handler : function() {
								yqgdrequestdata.addWindow.hide();
							}
						}]
			});
	yqgdrequestdata.alwaysFun = function() {
		Share.resetGrid(yqgdrequestdata.grid);
		yqgdrequestdata.pay_backstageAction.disable();
		yqgdrequestdata.clearAction.disable();
	};
	
	//验证是否已完成缴费
	yqgdrequestdata.verifyFun = function() {
		var form = yqgdrequestdata.formPanel.getForm();
		if (!form.isValid()) {
			return;
		}
		// 发送请求
		Share.AjaxRequest({
			url : yqgdrequestdata.verify,
			timeout : 300000,
			params : {
				id : form.findField('id').getValue()
			},
			callback : function(json) {
				yqgdrequestdata.store.reload();
			},
			falseFun :function(json) {
				
			}
		});
	};
	
	
	yqgdrequestdata.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [yqgdrequestdata.grid]
		});
		
});
</script>
