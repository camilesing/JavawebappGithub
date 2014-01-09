<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<div id="${param.id}"></div>

<script type="text/javascript">
$(document).ready(function() {
	Ext.ns("Ext.Authority.wzsjjcarregister"); // 自定义一个命名空间
	wzsjjcarregister = Ext.Authority.wzsjjcarregister; // 定义命名空间的别名
	
	wzsjjcarregister = {
		all : ctx + '/wzsjjcarregister/all',// 加载所有
		save : ctx + "/wzsjjcarregister/save",//保存
		del : ctx + "/wzsjjcarregister/del/",//删除
		verify : ctx + "/wzsjjcarregister/verify/", //车辆登记验证
		pageSize : 20, // 每页显示的记录数
		task_count : 60
	};
	
	/** 改变页的combo */
	wzsjjcarregister.pageSizeCombo = new Share.pageSizeCombo({
				value : '20',
				listeners : {
					select : function(comboBox) {
						wzsjjcarregister.pageSize = parseInt(comboBox.getValue());
						wzsjjcarregister.bbar.pageSize = parseInt(comboBox.getValue());
						wzsjjcarregister.store.baseParams.limit = wzsjjcarregister.pageSize;
						wzsjjcarregister.store.baseParams.start = 0;
						wzsjjcarregister.store.load();
					}
				}
			});
	// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
	wzsjjcarregister.pageSize = parseInt(wzsjjcarregister.pageSizeCombo.getValue());
	/** 基本信息-数据源 */
	wzsjjcarregister.store = new Ext.data.Store({
				autoLoad : true,
				remoteSort : true,
				baseParams : {
					start : 0,
					limit : wzsjjcarregister.pageSize
				},
				proxy : new Ext.data.HttpProxy({// 获取数据的方式
					method : 'POST',
					url : wzsjjcarregister.all
				}),
				reader : new Ext.data.JsonReader({// 数据读取器
					totalProperty : 'results', // 记录总数
					root : 'rows' // Json中的列表数据根节点
				}, ['id', 'chep', 'chepzl', 'suoyr', 'chelsbm','fadjbh']),
				listeners : {
					'load' : function(store, records, options) {
						wzsjjcarregister.alwaysFun();
					}
				}
			});
	
	/** 基本信息-选择模式 */
	wzsjjcarregister.selModel = new Ext.grid.CheckboxSelectionModel({
				singleSelect : true,
				listeners : {
					'rowselect' : function(selectionModel, rowIndex, record) {
						wzsjjcarregister.deleteAction.enable();
						wzsjjcarregister.editAction.enable();
					},
					'rowdeselect' : function(selectionModel, rowIndex, record) {
						wzsjjcarregister.alwaysFun();
					}
				}
			});
	/** 基本信息-数据列 */
	wzsjjcarregister.colModel = new Ext.grid.ColumnModel({
				defaults : {
					sortable : true,
					width : 140
				},
				columns : [wzsjjcarregister.selModel, {
							hidden : true,
							header : '车辆ID',
							dataIndex : 'id'
						}, {
							header : '车牌',
							dataIndex : 'chep'
						}, {
							header : '车牌种类',
							dataIndex : 'chepzl'
						}, {
							header : '所有人',
							dataIndex : 'suoyr'
						},{
							header : '车辆识别码',
							dataIndex : 'chelsbm'
						},{
							header : '发动机编号',
							dataIndex : 'fadjbh'
						}]
			});
	/** 新建 */
	wzsjjcarregister.addAction = new Ext.Action({
				text : '登记车辆',
				iconCls : 'add',
				handler : function() {
					wzsjjcarregister.addWindow.setIconClass('add'); // 设置窗口的样式
					wzsjjcarregister.addWindow.setTitle('车辆登记管理'); // 设置窗口的名称
					wzsjjcarregister.addWindow.show().center(); // 显示窗口
					wzsjjcarregister.formPanel.getForm().reset(); // 清空表单里面的元素的值.
				}
			});
	/** 编辑 */
	wzsjjcarregister.editAction = new Ext.Action({
				text : '编辑',
				iconCls : 'modify',
				disabled : true,
				handler : function() {
					var record = wzsjjcarregister.grid.getSelectionModel().getSelected();
					wzsjjcarregister.addWindow.setIconClass('modify'); // 设置窗口的样式
					wzsjjcarregister.addWindow.setTitle('编辑模块'); // 设置窗口的名称
					wzsjjcarregister.addWindow.show().center();
					wzsjjcarregister.formPanel.getForm().reset();
					wzsjjcarregister.formPanel.getForm().loadRecord(record);
				}
			});
	/** 删除 */
	wzsjjcarregister.deleteAction = new Ext.Action({
				text : '删除',
				iconCls : 'delete',
				disabled : true,
				handler : function() {
					wzsjjcarregister.delFun();
				}
			});
	/** 查询 */
	wzsjjcarregister.searchField = new Ext.ux.form.SearchField({
				store : wzsjjcarregister.store,
				paramName : 'chep',
				emptyText : '车牌号',
				style : 'margin-left: 5px;'
			});
	/** 顶部工具栏 */
	wzsjjcarregister.tbar = [wzsjjcarregister.addAction,'-',
			wzsjjcarregister.deleteAction, '-', wzsjjcarregister.searchField];
	/** 底部工具条 */
	wzsjjcarregister.bbar = new Ext.PagingToolbar({
				pageSize : wzsjjcarregister.pageSize,
				store : wzsjjcarregister.store,
				displayInfo : true,
				// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
				items : ['-', '&nbsp;', wzsjjcarregister.pageSizeCombo]
			});
	/** 基本信息-表格 */
	wzsjjcarregister.grid = new Ext.grid.GridPanel({
				// title : '模块列表',
				store : wzsjjcarregister.store,
				colModel : wzsjjcarregister.colModel,
				selModel : wzsjjcarregister.selModel,
				tbar : wzsjjcarregister.tbar,
				bbar : wzsjjcarregister.bbar,
				autoScroll : 'auto',
				region : 'center',
				loadMask : true,
				// autoExpandColumn :'wzsjjcarregisterDesc',
				stripeRows : true,
				listeners : {},
				viewConfig : {}
			});
	
	/** 基本信息-详细信息的form */
	wzsjjcarregister.formPanel = new Ext.form.FormPanel({
				frame : false,
				title : '车辆信息',
				bodyStyle : 'padding:10px;border:0px',
				labelwidth : 50,
				defaultType : 'textfield',
				items : [{
							xtype : 'hidden',
							fieldLabel : 'ID',
							name : 'id',
							anchor : '99%'
						}, {
							fieldLabel : '车牌号',
							maxLength : 8,
							minLength : 8,
							allowBlank : false,
							emptyText:'请输入车牌号',
							value : '浙C',
							name : 'chep',
						//	id :'carregister_chep',
							anchor : '99%'
						},{
							fieldLabel : '登记人手机',
							maxLength : 64,
							allowBlank : false,
							emptyText:'输入手机号码,点击认证',
							name : 'mobilephone',
						//	id :'carregister_mobilephone',
							regex : /^((\d{3,4}-)*\d{7,8}(-\d{3,4})*|1\d{10})$/, //3-4位区号+7 8位号码+转机号 
							regexText : '请输入有效的手机号码',
							anchor : '99%'
						}, {
							fieldLabel : '短信验证码',
							maxLength : 4,
							minLength : 4,
							disabled : true,
							allowBlank : false,
							name : 'smsnumber',
							regex : /^[0-9]+$/, //纯数字验证 
							regexText : '请输入有效的验证码',
						//	id : 'carregister_smsnumber',
							anchor : '99%'
						}]
			});
	/** 编辑新建窗口 */
	wzsjjcarregister.addWindow = new Ext.Window({
				layout : 'fit',
				width : 500,
				height : 420,
				closeAction : 'hide',
				plain : true,
				modal : true,
				resizable : true,
				items : [wzsjjcarregister.formPanel],
				buttons : [
				        {
							text : '认证',							
							id : 'carregister_verify',
							handler : function() {
								wzsjjcarregister.verifyFun();
							}
						},
						{
							text : '保存',
							handler : function() {
								wzsjjcarregister.saveFun();
							}
						},{
							text : '重置',
							handler : function() {
								var form = wzsjjcarregister.formPanel.getForm();
								var id = form.findField("id").getValue();
								form.reset();
								if (id != '')
									form.findField("id").setValue(id);
							}
						}]
			});
	wzsjjcarregister.alwaysFun = function() {
		Share.resetGrid(wzsjjcarregister.grid);
		wzsjjcarregister.deleteAction.disable();
		wzsjjcarregister.editAction.disable();
	};
	
	wzsjjcarregister.verifyFun = function() {
		var form = wzsjjcarregister.formPanel.getForm();
		form.findField('smsnumber').setValue('');
		form.findField('smsnumber').disable();
		if (!form.isValid()) {
			return;
		}
		// 发送请求
		Share.AjaxRequest({
			url : wzsjjcarregister.verify,
			params : {
				chep : form.findField('chep').getValue(),
				mobilephone : form.findField('mobilephone').getValue()
			},
			callback : function(json) {
				//激活短信验证码输入框
				form.findField('smsnumber').enable() ;
				
				form.findField('smsnumber').setValue(json.msg);
				//激活任务
				Ext.TaskMgr.start(wzsjjcarregister.task);
			},
			falseFun :function(json) {
				if (json.msg.indexOf('车牌') > -1) {
					form.findField("chep").focus().val('');
					return;
				}
				if (json.msg.indexOf('手机') > -1) {
					form.findField("mobilephone").focus().val('');
					//$("#carregister_mobilephone").focus().val('');
					return;
				}
			}
		});
	};
	wzsjjcarregister.saveFun = function() {
		var form = wzsjjcarregister.formPanel.getForm();
		if (!form.isValid()) {
			return;
		}
		// 发送请求
		Share.AjaxRequest({
			url : wzsjjcarregister.save,
			params : form.getValues(),
			callback : function(json) {
				wzsjjcarregister.addWindow.hide();
				wzsjjcarregister.alwaysFun();
				wzsjjcarregister.store.reload();
			},
			falseFun :function(json) {
				if (json.msg.indexOf('验证码') > -1) {
					form.findField("smsnumber").focus().val('');
					return;
				}				
			}
		});
	};
	wzsjjcarregister.delFun = function() {
		var record = wzsjjcarregister.grid.getSelectionModel().getSelected();
		Ext.Msg.confirm('提示', '确定删除所选已登记车辆?', 
			function(btn, text) {
				if (btn == 'yes') {
					// 发送请求
					Share.AjaxRequest({
						url : wzsjjcarregister.del + record.data.id,
						callback : function(json) {
							wzsjjcarregister.alwaysFun();
							wzsjjcarregister.store.reload();
						}
					});
				}
			});
	};
	wzsjjcarregister.myPanel = new Ext.Panel({
			id : '${param.id}' + '_panel',
			renderTo : '${param.id}',
			layout : 'border',
			boder : false,
			height : index.tabPanel.getInnerHeight() - 1,
			items : [wzsjjcarregister.grid]
		});
	
	//自动任务调度
	wzsjjcarregister.task = {
		run : function() {
			var form = wzsjjcarregister.formPanel.getForm();
			if(wzsjjcarregister.task_count>0){
				Ext.getCmp('carregister_verify').disable()
				Ext.getCmp('carregister_verify').setText(wzsjjcarregister.task_count);
				wzsjjcarregister.task_count--;
			}else{
				wzsjjcarregister.task_count = 60;
				Ext.TaskMgr.stop(wzsjjcarregister.task);
				Ext.getCmp('carregister_verify').enable()
				Ext.getCmp('carregister_verify').setText('认证');
			}
			
		},
		//scope : this,
		interval : 1000 // 1秒刷新一次
	};

		
});
</script>
