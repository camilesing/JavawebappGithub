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
	Ext.ns("Ext.Authority.wzsjjcarpeccancy"); // 自定义一个命名空间
	wzsjjcarpeccancy = Ext.Authority.wzsjjcarpeccancy; // 定义命名空间的别名
	
	function formatQtip(data, metadata, record, rowIndex, columnIndex, store){ 
	   var title ="";
	   var tip =data; 
	   metadata.attr = 'ext:qtitle="' + title + '"' + ' ext:qtip="' + tip + '"';  
	   return data;  
	};
	
	wzsjjcarpeccancy = {
		all : ctx + '/wzsjjcarpeccancy/all',// 加载所有
		save : ctx + "/wzsjjcarpeccancy/save",//保存
		del : ctx + "/wzsjjcarpeccancy/del/",//删除
		verify : ctx + "/wzsjjcarpeccancy/verify/", //车辆缴费验证
		pay_online : ctx + "/wzsjjcarpeccancy/pay_online/", //发送支付请求 在线
		pay_mobilephone : ctx + "/wzsjjcarpeccancy/pay_mobilephone/", //发送支付请求到手机端
		pay_mobilephone_verify : ctx + "/wzsjjcarpeccancy/pay_mobilephone_verify/", //手机端支付确认
		makesure : ctx + "/wzsjjcarpeccancy/makesure/", //确认违章事实
		makesure_verify : ctx + "/wzsjjcarpeccancy/makesure_verify/", 
		clear : ctx + "/wzsjjcarpeccancy/clear/", 
		pageSize : 20, // 每页显示的记录数
		wzsjjdealstatus:eval('(${fields.wzsjjdealstatus==null?"{}":fields.wzsjjdealstatus})'),
		task_count : 60
	};
	
	/** 改变页的combo */
	wzsjjcarpeccancy.pageSizeCombo = new Share.pageSizeCombo({
				value : '20',
				listeners : {
					select : function(comboBox) {
						wzsjjcarpeccancy.pageSize = parseInt(comboBox.getValue());
						wzsjjcarpeccancy.bbar.pageSize = parseInt(comboBox.getValue());
						wzsjjcarpeccancy.store.baseParams.limit = wzsjjcarpeccancy.pageSize;
						wzsjjcarpeccancy.store.baseParams.start = 0;
						wzsjjcarpeccancy.store.load();
					}
				}
			});
	// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
	wzsjjcarpeccancy.pageSize = parseInt(wzsjjcarpeccancy.pageSizeCombo.getValue());
	/** 基本信息-数据源 */
	wzsjjcarpeccancy.store = new Ext.data.Store({
				autoLoad : true,
				remoteSort : true,
				baseParams : {
					start : 0,
					limit : wzsjjcarpeccancy.pageSize
				},
				proxy : new Ext.data.HttpProxy({// 获取数据的方式
					method : 'POST',
					url : wzsjjcarpeccancy.all
				}),
				reader : new Ext.data.JsonReader({// 数据读取器
					totalProperty : 'results', // 记录总数
					root : 'rows' // Json中的列表数据根节点
				}, ['id', 'tongzsbh', 'weifxx', 'fakje', 'chelbm','chep','chulzt','suoyr','lianxfs','shenfzh']),
				listeners : {
					'load' : function(store, records, options) {
						wzsjjcarpeccancy.alwaysFun();
					}
				}
			});
	
	/** 基本信息-选择模式 */
	wzsjjcarpeccancy.selModel = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true,
				listeners : {
					'rowselect' : function(selectionModel, rowIndex, record) {
						wzsjjcarpeccancy.clearAction.enable();
						if(record.data.chulzt=='0'){
							wzsjjcarpeccancy.makesureAction.enable();
						}
						else if(record.data.chulzt=='01'||record.data.chulzt=='02'){
							wzsjjcarpeccancy.makesure_verifyAction.enable();
						}
						else if(record.data.chulzt=='1'||record.data.chulzt=='22'){
							wzsjjcarpeccancy.pay_mobilephoneAction.enable();
							wzsjjcarpeccancy.pay_onlineAction.enable();
						}
						else if(record.data.chulzt=='11'||record.data.chulzt=='12'){
							wzsjjcarpeccancy.pay_mobilephone_verifyAction.enable();
						}
						else if(record.data.chulzt=='21'){
							wzsjjcarpeccancy.pay_onlineAction.enable();
						}
							
					},
					'rowdeselect' : function(selectionModel, rowIndex, record) {
						wzsjjcarpeccancy.alwaysFun();
					}
				}
			});
	/** 基本信息-数据列 */
	wzsjjcarpeccancy.colModel = new Ext.grid.ColumnModel({
				defaults : {
					sortable : true,
					width : 70
				},
				columns : [wzsjjcarpeccancy.selModel, {
							hidden : true,
							header : 'ID',
							dataIndex : 'id'
						}, {
							header : '通知书编号',
							dataIndex : 'tongzsbh',
							width : 120,
							renderer:formatQtip
						}, {
							header : '违法信息',
							width : 260,
							dataIndex : 'weifxx',
							//renderer: formatQtip
							renderer: function (v, m, r) {
						      m.attr = 'style="white-space:normal;word-wrap:break-word;word-break:break-all;"';
						      return v;
						    }
						}, {
							header : '罚款金额(元)',
							dataIndex : 'fakje',
							width : 80
						},{
							header : '车辆编码',
							dataIndex : 'chelbm',
							width : 140
						},{
							header : '车牌',
							dataIndex : 'chep'
						},{
							header : '所有人',
							dataIndex : 'suoyr',
							width : 70
						},{
							header : '联系方式',
							dataIndex : 'lianxfs',
							width : 80
						},{
							header : '身份证号',
							dataIndex : 'shenfzh',
							width : 120
						},{
							header : '处理状态',
							dataIndex : 'chulzt',
							width : 110,
							renderer : function(v) {
								return Share.map(v,wzsjjcarpeccancy.wzsjjdealstatus , '');
							}
						}]
			});
	
	wzsjjcarpeccancy.makesureAction = new Ext.Action({
		text : '违章确认(手机端)',
		iconCls : 'db-icn-ipaper',
		disabled : true,
		handler : function() {
			var record = wzsjjcarpeccancy.grid.getSelectionModel().getSelected();
			Share.AjaxRequest({
				url : wzsjjcarpeccancy.makesure,
				timeout : 300000,
				params : {
					id : record.data.id
				},
				callback : function(json) {
					wzsjjcarpeccancy.store.reload();
				},
				falseFun :function(json) {
					
				}
			});
		}
	});
	
	wzsjjcarpeccancy.makesure_verifyAction = new Ext.Action({
		text : '违章回复验证',
		iconCls : 'db-icn-ipod',
		disabled : true,
		handler : function() {
			var record = wzsjjcarpeccancy.grid.getSelectionModel().getSelected();
			Share.AjaxRequest({
				url : wzsjjcarpeccancy.makesure_verify,
				timeout : 300000,
				params : {
					id : record.data.id
				},
				callback : function(json) {
					wzsjjcarpeccancy.store.reload();
				},
				falseFun :function(json) {
					
				}
			});
		}
	});
	
	wzsjjcarpeccancy.pay_onlineAction = new Ext.Action({
		text : '缴费(在线)',
		iconCls : 'db-icn-money_yen',
		disabled : true,
		handler : function() {
			var record = wzsjjcarpeccancy.grid.getSelectionModel().getSelected();
			var tongzsbh = record.data.tongzsbh;
			var chulzt = record.data.chulzt;
			if(chulzt=='0')
				alert("请先确认 交通违法 事项，再执行缴费动作");
			else if(chulzt=='1'){
				window.open(wzsjjcarpeccancy.pay+"?tongzsbh="+tongzsbh+"&time="+(new Date()).toString());
				wzsjjcarpeccancy.addWindow.setIconClass('db-icn-money_yen'); // 设置窗口的样式
				wzsjjcarpeccancy.addWindow.setTitle('缴费'); // 设置窗口的名称
				wzsjjcarpeccancy.addWindow.show().center();
				wzsjjcarpeccancy.formPanel.getForm().reset();
				wzsjjcarpeccancy.formPanel.getForm().loadRecord(record);
			}
			else if(chulzt=='2')
				alert("已完成缴费");
		}
	});
	
	wzsjjcarpeccancy.pay_mobilephoneAction = new Ext.Action({
		text : '缴费(手机端)',
		iconCls : 'db-icn-wlorb',
		disabled : true,
		handler : function() {
			var record = wzsjjcarpeccancy.grid.getSelectionModel().getSelected();
			Share.AjaxRequest({
				url : wzsjjcarpeccancy.pay_mobilephone,
				timeout : 300000,
				params : {
					id : record.data.id
				},
				callback : function(json) {
					wzsjjcarpeccancy.store.reload();
				},
				falseFun :function(json) {
					
				}
			});
		}
	});
	
	wzsjjcarpeccancy.pay_mobilephone_verifyAction = new Ext.Action({
		text : '缴费回复验证',
		iconCls : 'db-icn-ipod',
		disabled : true,
		handler : function() {
			var record = wzsjjcarpeccancy.grid.getSelectionModel().getSelected();
			Share.AjaxRequest({
				url : wzsjjcarpeccancy.pay_mobilephone_verify,
				timeout : 300000,
				params : {
					id : record.data.id
				},
				callback : function(json) {
					wzsjjcarpeccancy.store.reload();
				},
				falseFun :function(json) {
					
				}
			});
		}
	});
	
	wzsjjcarpeccancy.clearAction = new Ext.Action({
		text : '状态初始化',
		iconCls : 'db-icn-refresh',
		disabled : true,
		handler : function() {
			var record = wzsjjcarpeccancy.grid.getSelectionModel().getSelected();
			Share.AjaxRequest({
				url : wzsjjcarpeccancy.clear,
				timeout : 300000,
				params : {
					id : record.data.id
				},
				callback : function(json) {
					wzsjjcarpeccancy.store.reload();
				},
				falseFun :function(json) {
					
				}
			});
		}
	});
	
	/** 查询 */
	wzsjjcarpeccancy.searchField = new Ext.ux.form.SearchField({
				store : wzsjjcarpeccancy.store,
				paramName : 'chep',
				emptyText : '车牌号',
				style : 'margin-left: 5px;'
			});
	/** 顶部工具栏 */
	wzsjjcarpeccancy.tbar = [wzsjjcarpeccancy.makesureAction,'-',
	                         wzsjjcarpeccancy.makesure_verifyAction,'-',
	                         wzsjjcarpeccancy.pay_onlineAction,'-',
	                         wzsjjcarpeccancy.pay_mobilephoneAction,'-',
	                         wzsjjcarpeccancy.pay_mobilephone_verifyAction,'-',
	                         wzsjjcarpeccancy.clearAction,'-',
	                         wzsjjcarpeccancy.searchField];
	/** 底部工具条 */
	wzsjjcarpeccancy.bbar = new Ext.PagingToolbar({
				pageSize : wzsjjcarpeccancy.pageSize,
				store : wzsjjcarpeccancy.store,
				displayInfo : true,
				// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
				items : ['-', '&nbsp;', wzsjjcarpeccancy.pageSizeCombo]
			});
	/** 基本信息-表格 */
	wzsjjcarpeccancy.grid = new Ext.grid.GridPanel({
				// title : '模块列表',
				store : wzsjjcarpeccancy.store,
				colModel : wzsjjcarpeccancy.colModel,
				selModel : wzsjjcarpeccancy.selModel,
				tbar : wzsjjcarpeccancy.tbar,
				bbar : wzsjjcarpeccancy.bbar,
				autoScroll : 'auto',
				region : 'center',
				loadMask : true,
				// autoExpandColumn :'wzsjjcarpeccancyDesc',
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
	wzsjjcarpeccancy.formPanel = new Ext.form.FormPanel({
				frame : false,
				title : '车辆信息',
				bodyStyle : 'padding:10px;border:0px',
				labelwidth : 50,
				defaultType : 'textfield',
				items : [{
							fieldLabel : '通知书编号',
							readOnly:true,
							value : '',
							name : 'tongzsbh',
							anchor : '99%'
						},{
							fieldLabel : '车牌',
							readOnly:true,
							value : '',
							name : 'chep',
							anchor : '99%'
						},{
							fieldLabel : '车辆编码',
							hidden :true ,
							value : '',
							name : 'chelbm',
							anchor : '99%'
						}]
			});
	/** 编辑新建窗口 */
	wzsjjcarpeccancy.addWindow = new Ext.Window({
				layout : 'fit',
				width : 300,
				height : 180,
				closeAction : 'hide',
				plain : true,
				modal : true,
				resizable : true,
				items : [wzsjjcarpeccancy.formPanel],
				buttons : [
				        {
							text : '已完成',							
							id : 'carpeccancy_verify',
							handler : function() {
								wzsjjcarpeccancy.verifyFun();
							}
						},
						{
							text : '发生错误',
							handler : function() {
								wzsjjcarpeccancy.addWindow.hide();
							}
						}]
			});
	wzsjjcarpeccancy.alwaysFun = function() {
		Share.resetGrid(wzsjjcarpeccancy.grid);
		wzsjjcarpeccancy.makesureAction.disable();
		wzsjjcarpeccancy.makesure_verifyAction.disable();
		wzsjjcarpeccancy.pay_onlineAction.disable();
		wzsjjcarpeccancy.pay_mobilephoneAction.disable();
		wzsjjcarpeccancy.pay_mobilephone_verifyAction.disable();
		wzsjjcarpeccancy.clearAction.disable();
	};
	
	//验证是否已完成缴费
	wzsjjcarpeccancy.verifyFun = function() {
		var form = wzsjjcarpeccancy.formPanel.getForm();
		if (!form.isValid()) {
			return;
		}
		// 发送请求
		Share.AjaxRequest({
			url : wzsjjcarpeccancy.verify,
			timeout : 300000,
			params : {
				tongzsbh : form.findField('tongzsbh').getValue(),
				chelbm :   form.findField('chelbm').getValue()
			},
			callback : function(json) {
				wzsjjcarpeccancy.store.reload();
			},
			falseFun :function(json) {
				
			}
		});
	};
	
	wzsjjcarpeccancy.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [wzsjjcarpeccancy.grid]
		});
		
});
</script>
