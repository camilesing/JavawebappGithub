//-----------------------------------------部门处理----------------------------------------------------

/**
 * 添加部门事件
 */
$("#addDept").live( "pageinit" , function(event){
	
	$("#addDeptBtn").click(function(event){
		var name = $("#deptName").val();
		var id = $("#deptId").val();
		
		if( !( valiateIdBetwwn(id) ) ){
			$.mobile.changePage( $("#idErrorDialog") ,{ transition : "pop" ,changeHash: false ,  reloadPage : true  } );
			return false;
		}
		
		if( !( regIdInt( id ) )  ){
			$.mobile.changePage( $("#idErrorDialog") , { transition : "pop" ,changeHash: false ,  reloadPage : true  } );
			return false;
		}
		
		if(  !( nameValidate(name) )  ){
			$.mobile.changePage( $("#nameErrorDialog") ,{ transition : "pop" ,changeHash: false ,  reloadPage : true  });
			return false;
		}
		
		//start ajax submit
		var url = "mobile/deptServlet";
		var para = {deptId : $("#deptId").val(),			
				    deptName : name,
				    method: 'add'};
		var returnType = "text";		
		
		ajaxPost( url , para , callBack , returnType );
		
		//回调函数
		function callBack(data){
			if( data == "success" ){
				$.mobile.changePage( "success.jsp" ,{ transition : "pop" ,changeHash: false ,  reloadPage : true  } );
			}else{
				$.mobile.changePage( $("#idRepeatDialog") , { transition : "pop" ,changeHash: false ,  reloadPage : true  });
			}	
		} //end callBack		
	});
}); 



$("#addDept").live( "pageshow" , function(event){
	var url = "mobile/deptServlet";
	var para = { 
					method : 'countNextId',
					time : new Date().getTime()
				};
	var retunType = "text";
	function callBack(data){
		$("#deptId").attr("value",data);
	}	
	
	$.post( url , para , callBack,retunType );	//获取下一条id的值
});




/**
 * 修改部门事件
 */
$('#editDept').live( "pageinit" , function(event){
		var obj ;
		
		//获取所有的部门详情
		var url = "mobile/deptServlet";
			$.post( url , {
				method : 'getAllDept',
				time : new Date().getTime()
			} , function(data){
				obj = data;
				
				$.each( obj ,function(index,dept){
					$("#deptId").append( "<option value='"+dept.deptId+"'>"+ dept.deptName +"</option>" );					
				});
			} ,"json"  );	

	
			$("#deptId").bind( "change", function(event) {  
	          	var id = $(this).val() ;
	          	
	          	if( id == "--" ){
	          		$("#deptName").attr( "value","" );
	          	}
	          	
	          	$.each( obj , function(index,dept){
	          		if( id == dept.deptId ){
	          			$("#deptName").attr( "value",dept.deptName );
	          		}	          		
	          	}); 
      		  });
      		  

      		  $("#updateDeptBtn").click(function(event){
      		  		var id = $("#deptId").val();
      		  		var name = $("#deptName").val();
      		  		
      		  		if( id == "--" ){
      		  			$.mobile.changePage( $("#idSelectErrorDialog") ,{ transition : "pop" ,changeHash: false ,  reloadPage : true  } );
      		  			return false;
      		  		}
      		  		
      		  		if( name.length <= 0 ){
      		  			$.mobile.changePage( $("#nameErrorDialog") ,{ transition : "pop" ,changeHash: false ,  reloadPage : true  });
      		  			return false;
      		  		}
      		  		
      		  		var url = "mobile/deptServlet";
      		  		$.post( url, {
      		  			deptId : id,
      		  			deptName : name,
      		  			method : 'update'
      		  		},function(data){
      		  			if( data == "success" ){
      		  				$.mobile.changePage( "success.jsp" ,{ transition : "pop" ,changeHash: false ,  reloadPage : true  } );
      		  			}else{
      		  				$.mobile.changePage( "error.jsp" ,{ transition : "pop" ,changeHash: false ,  reloadPage : true  } );
      		  			}
      		  		},"text" );
				
      		  });		  
} );

/**
 * 显示所有部门列表事件,此事件不同,是调用每次显示组件时都异步调用获取数据
 */
$("#deptList").live( "pageshow" , function(event){
	
	var url = "mobile/deptServlet";
	$.post( url , {
		method : 'getAllDept',
		time : new Date().getTime()
	} , function(data){		
		$.each( data , function(index,dept){
			$("#txt").append( "<tr><td align='center'><b>" + dept.deptId + "</b></td><td align='center'><b>"+ dept.deptName +"</b></td></tr><tr><td colspan='2'><hr></td></tr>" );
		});
		
	} ,"json" );// end page create event	
});