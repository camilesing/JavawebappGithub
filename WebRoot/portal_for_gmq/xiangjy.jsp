<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/commons/taglibs.jsp"%>
<%@ include file="/WEB-INF/views/commons/yepnope.jsp"%>
<div id="xiangjy"></div>
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

		],
	complete : function() {
			
	Ext.ns("Ext.portal_for_gmq.xiangjy"); // 自定义一个命名空间
	xiangjy = Ext.portal_for_gmq.xiangjy; // 定义命名空间的别名
	xiangjy = {
		all : ctx + '/portal_for_gmq/xiangjy/all',// 加载所有
		save : ctx + "/portal_for_gmq/xiangjy/save",//保存	
		pagesizes:eval('(${fields.pagesizes==null?"{}":fields.pagesizes})'),
		pageSize : 20 // 每页显示的记录数
	};

	/** 改变页的combo */
	xiangjy.pageSizeCombo = new Share.pageSizeCombo({
			value : '20',
			listeners : {
				select : function(comboBox) {
					xiangjy.pageSize = parseInt(comboBox.getValue());
					xiangjy.bbar.pageSize = parseInt(comboBox.getValue());
					xiangjy.store.baseParams.limit = xiangjy.pageSize;
					xiangjy.store.baseParams.start = 0;
					xiangjy.store.load();
				}
			}
		});
	// 覆盖已经设置的。具体设置以当前页面的pageSizeCombo为准
	xiangjy.pageSize = parseInt(xiangjy.pageSizeCombo.getValue());
	/** 基本信息-数据源 */
	xiangjy.store = new Ext.data.Store({
			autoLoad : false,
			remoteSort : true,
			baseParams : {
				head  : '',
				start : 0,
				limit : xiangjy.pageSize
			},
			proxy : new Ext.data.HttpProxy({// 获取数据的方式
				method : 'POST',
				url : xiangjy.all
			}),
			reader : new Ext.data.JsonReader({// 数据读取器
				totalProperty : 'results', // 记录总数
				root : 'rows' // Json中的列表数据根节点
			}, ['id', 'head', 'body', 'begin_date', 'end_date','mxdx',
				'release_time','release_ip','release_per','isdisplay','isread'
				]),
			listeners : {
				'load' : function(store, records, options) {				
					xiangjy.alwaysFun();
				}
			}
		});
	/** 基本信息-选择模式 */
	xiangjy.selModel = new Ext.grid.CheckboxSelectionModel({
			singleSelect : false,
		    isLocked: Ext.emptyFn, 
		    initEvents: function() { 
		        Ext.grid.CheckboxSelectionModel.superclass.initEvents.call(this); 
		        this.grid.on('render', function() { 
		            var view = this.grid.getView(); 
		            view.mainBody.on('mousedown', this.onMouseDown, this); 
		            Ext.fly(view.lockedInnerHd).on('mousedown', this.onHdMouseDown, this); 
		        }, this); 
		    },				
			listeners : {
				'rowselect' : function(selectionModel, rowIndex, record) {
					xiangjy.deleteAction.enable();
				},
				'rowdeselect' : function(selectionModel, rowIndex, record) {
					xiangjy.alwaysFun();
				}
			}
		});
	xiangjy.selModel.sortLock();
	/** 基本信息-数据列 */
	xiangjy.colModel = new Ext.ux.grid.LockingColumnModel({
			defaults : {
				sortable : true,
				width : 140
			},
			columns : [xiangjy.selModel,new Ext.grid.RowNumberer({locked: true}), {
						hidden : true,
						header : '字段ID',
						dataIndex : 'id'
					},{
						hidden : true,
						header : '操作',
						dataIndex : '',
						width : 40,
						renderer : function(v) {
							return '<a href=javascript:xiangjy.viewAction.execute()><img src="${ctx}/resources/images/icons/tabs.gif" boder=0 vspace=0 hspace=0 title="阅读"/></a>';
						}
					}, {
						header : '标题',
						dataIndex : 'head',
						locked: true,
						renderer:function (value, cellmeta, record, rowIndex, columnIndex, store){
							if(record.data.isread==0)
								return '<span style="font-weight:bolder">'+value+'</span>';
							else
								return value;
						
						}
					}, {
						hidden : true,
						header : '内容',
						dataIndex : 'body'
					}, {
						header : '开始时间',
						dataIndex : 'begin_date'
					}, {
						header : '结束时间',
						dataIndex : 'end_date'
					}, {
						header : '面向对象',
						dataIndex : 'mxdx',
						renderer:function (value, cellmeta, record, rowIndex, columnIndex, store){							
							xiangjy.MultiSelect.setValue(value);
							return xiangjy.MultiSelect.getCheckedDisplay();						
						}
					}, {
						header : '发布时间',
						dataIndex : 'release_time'
					}, {
						header : '发布IP',
						dataIndex : 'release_ip'
					},{
						header : '发布人',
						dataIndex : 'release_per'
					},{
						// (0:否;1:是)
						header : '是否显示',
						dataIndex : 'isdisplay',
						renderer : function(v) {
							return Share.map(v,xiangjy.isdisplay , '');
						}
					},{
						// (0:否;1:是)
						header : '已阅',
						dataIndex : 'isread',
						renderer : function(v) {
							return Share.map(v,xiangjy.isdisplay , '');
						}
					}]
		});

	/** 删除 */
	xiangjy.deleteAction = new Ext.Action({
			text : '删除',
			iconCls : 'field_delete',
			disabled : true,
			handler : function() {
				xiangjy.delFun();
			}
		});
	/** 查询 */
	xiangjy.search_xiangh = new Ext.ux.form.SearchField({
			store : xiangjy.store,
			paramName : 'head',
			emptyText : '请输入箱号',
			style : 'margin-left: 5px;'
		});
	
	xiangjy.search_tiaom = new Ext.ux.form.SearchField({
		store : xiangjy.store,
		paramName : 'head',
		emptyText : '请输入标题名称',
		style : 'margin-left: 5px;'
	});
		
	/** 提示 */
	xiangjy.tips = '&nbsp;<font color="red"><b>提示信息</b></font>';
	/** 顶部工具栏 */
	xiangjy.tbar = [xiangjy.search_xiangh,'-',xiangjy.tips];

	/** 底部工具条 */
	xiangjy.bbar = new Ext.PagingToolbar({
			pageSize : xiangjy.pageSize,
			store : xiangjy.store,
			displayInfo : true,
			// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			items : ['-', '&nbsp;', xiangjy.pageSizeCombo]
		});
	/** 基本信息-表格 */
	xiangjy.grid = new Ext.grid.GridPanel({
			store : xiangjy.store,
			colModel : xiangjy.colModel,
			selModel : xiangjy.selModel,
			tbar : xiangjy.tbar,
			bbar : xiangjy.bbar,
			autoScroll : 'auto',
			region : 'center',
			loadMask : true,
			// autoExpandColumn :'fieldDesc',
			stripeRows : true,
			listeners : {},
			viewConfig : {},
			view: new Ext.ux.grid.LockingGridView()
		});

	xiangjy.alwaysFun = function() {
		Share.resetGrid(xiangjy.grid);
		xiangjy.deleteAction.disable();	
	};

	xiangjy.saveFun = function() {
		// 发送请求
		Share.AjaxRequest({
					url : xiangjy.save,
					params : form.getValues(),
					callback : function(json) {
						xiangjy.addWindow.hide();
						xiangjy.alwaysFun();
						xiangjy.store.reload();
					}
				});
	};
	xiangjy.delFun = function() {
		var ids = [];	
	    
	    var sm = xiangjy.grid.getSelectionModel();
		var store = xiangjy.grid.getStore();
		var view = xiangjy.grid.getView();
		for (var i = 0; i < view.getRows().length; i++) {
			if (sm.isSelected(i)) {
				ids.push(store.getAt(i).get('id'));
			}
		}
		
	//	var record = xiangjy.grid.getSelectionModel().getSelected(); 单条记录
		Ext.Msg.confirm('提示', '确定要删除选中的'+ids.length+'条记录吗?', function(btn, text) {
					if (btn == 'yes') {
						// 发送请求
						Share.AjaxRequest({
									url : xiangjy.del + ids,
									callback : function(json) {
										xiangjy.alwaysFun();
										xiangjy.store.reload();
									}
								});
					}
				});
	};

	xiangjy.myPanel = new Ext.Panel({
			id : 'xiangjy' + '_panel',
			renderTo : 'xiangjy',
			layout : 'border',
			boder : false,
//			height : index.tabPanel.getInnerHeight() - 1,
			items : [xiangjy.grid]
		});
			
	}
});



	   				
</script>
