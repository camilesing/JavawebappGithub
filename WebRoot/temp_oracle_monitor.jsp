<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.JSONArray"%>
<html>
  <head>   
    <title>oracle monitor</title>
   <link rel="stylesheet" type="text/css" href="resources/extjs/resources/css/ext-all.css" />
   <script type="text/javascript" src="resources/extjs/adapter/ext/ext-base.js"> </script>
   <script type="text/javascript" src="resources/extjs/ext-all.js"></script>
   <script type="text/javascript" src="resources/extjs/ext-lang-zh_CN.js"></script>
   <style type="text/css">
   	.x-grid-record-gray table{color: #948d8e;}
	.x-grid-record-red table{color: white; background: red;}
	.x-grid-record-yellow table{color: blue;}
	.x-grid-record-green table{color: green;}
	.x-grid-record-orange table{color: orange;}    
   </style>
   <script type="text/javascript">
    function report(tab){		
	 	
    	Ext.onReady(function(){
			Ext.BLANK_IMAGE_URL = 'resources/extjs/resources/images/default/s.gif';
			Ext.QuickTips.init();
			Ext.Ajax.timeout=240000;

			var store_ = new Ext.data.Store({ 
			autoLoad : true,
			id : 'store',
			proxy : new Ext.data.HttpProxy({
						url : 'temp_json.jsp?table='+tab
					}),
			reader : new Ext.data.JsonReader({
						totalProperty : 'results',
						root : 'rows'
				 }, ['FIELD_ID','FIELD_NAME','VALUE_FIELD'])
			});
			var grid_ = new Ext.grid.GridPanel({
				id : 'grid_',
				store : store_,
				renderTo:'monitor',
				cm : new Ext.grid.ColumnModel(
					[
					new Ext.grid.RowNumberer(),
					{header : 'FIELD_ID',dataIndex : 'FIELD_ID'},		
					{header : 'FIELD_NAME',dataIndex : 'FIELD_NAME'},	
					{header : '显示.',dataIndex : 'VALUE_FIELD'}	
					]),
				autoHeight : true,
				//height :270,
				autoWidth:true,
				viewConfig : {
					//forceFit : true,
					getRowClass:function(record,index,p,ds) {
				      var cls = 'white-row';
				         switch (record.data.VALUE_FIELD) {
				             case '0' :     cls = 'x-grid-record-green';    break;
				             case '1' :     cls = 'x-grid-record-yellow';     break;
				             case '2' :     cls = 'x-grid-record-orange';      break;
				             case '3' :     cls = 'x-grid-record-red';       break;
				             case '4' :     cls = 'x-grid-record-gray';      break;
				         }
				         return cls;
				     }
					
				}
			
			});
			
		});
    	
	}
    
    </script>
    
  </head>  
  <body>
  	<div id="request"><a href="javascript:report('tab');">图表</a></div>
	<div id="monitor"></div>
	
  </body>
</html>
