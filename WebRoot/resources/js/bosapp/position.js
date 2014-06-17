//-----------------------------------------职务处理----------------------------------------------------

/**
 * 添加职务事件
 */
$("#addPosition").live( "pageinit" , function(event){
	$("#addPositionBtn").click(function(event){
		var name = $("#postName").val();
		var id = $("#postId").val();
		
		
		if( !( valiateIdBetwwn(id) ) ){
			$.mobile.changePage( $("#idErrorDialog") , { transition : "pop" ,changeHash: false  ,  reloadPage : true   } );
			return false;
		}
		
		if( !( regIdInt( id ) )  ){
			$.mobile.changePage( $("#idErrorDialog") , { transition : "pop" ,changeHash: false   ,  reloadPage : true  } );
			return false;
		}
		
		if(  !( nameValidate(name) )  ){
			$.mobile.changePage( $("#nameErrorDialog") ,{ transition : "pop" ,changeHash: false  ,  reloadPage : true   } );
			return false;
		}
		
		var url = "mobile/postServlet";		
		$.post( url , {
			postId : $("#postId").val(),			
			postName : name,
			method: 'add'
		} , function(data){
			if( data == "success" ){
				$.mobile.changePage( "success.jsp" ,{ transition : "pop" ,changeHash: false  ,  reloadPage : true   });
			}else{
				$.mobile.changePage( $("#idRepeatDialog") , { transition : "pop" ,changeHash: false  ,  reloadPage : true   } );
			}				
		},"text"); //end post
		
	});
}); 

$("#addPosition").live( "pageshow" , function(event){
	var url = "mobile/postServlet";
	var para = { 
					method : 'countNextId',
					time : new Date().getTime()
				};
	var retunType = "text";
	function callBack(data){
		$("#postId").attr("value",data);
	}	
	
	$.post( url , para , callBack,retunType );	//获取下一条id的值
});



/**
 * 修改职务事件
 */
$('#editPosition').live( "pageshow" , function(event){
		var obj ;
			
			var url = "mobile/postServlet";	
			$.post( url , {
				method : 'getAllPosition'				
			} , function(data){
				obj = data;
				
				$.each( obj , function(index,position){
					$("#postId").append( "<option value='"+position.postId+"'>"+ position.postName +"</option>" );
				});
				
			} ,"json"  );	

	
			$("#postId").bind( "change", function(event) {  
	          	var id = $(this).val() ;
	          	if( id == "--" ){
	          		$("#postName").attr( "value","" );
	          	}
	          	
	          	$.each( obj , function(index,position){
	          		if( id == position.postId ){
	          			$("#postName").attr( "value",position.postName );
	          		}
	          	});	
      		  });
      		  
			//修改按钮事件
      		  $("#updatePositionBtn").click(function(event){
      		  		var id = $("#postId").val();
      		  		var name = $("#postName").val();
      		  		
      		  		if( id == "--" ){
      		  			$.mobile.changePage($("#idSelectErrorDialog") , { transition : "pop" ,changeHash: false  ,  reloadPage : true   } );
      		  			return false;     		  			
      		  		}
      		  		
      		  		if( name.length <= 0){
      		  			$.mobile.changePage( $("#nameErrorDialog") , { transition : "pop" ,changeHash: false  ,  reloadPage : true   } );
      		  			return false;
      		  		}
      		  		
      		  		var url = "mobile/postServlet";      		  		
      		  		$.post( url, {
      		  			postId : id,
      		  			postName : name,
      		  			method : 'update'
      		  		},function(data){
      		  			if( data == "success" ){
      		  				$.mobile.changePage( "success.jsp" , { transition : "pop" ,changeHash: false  ,  reloadPage : true   } );
      		  			}else{
      		  				$.mobile.changePage( "error.jsp" , { transition : "pop" ,changeHash: false  ,  reloadPage : true   } );
      		  			}
      		  		},"text" );
				
      		  });		  
} );


/**
 * 显示所有职务列表事件,此事件不同,是调用每次显示组件时都异步调用获取数据
 */
$("#positionList").live( "pageshow" , function(event){
	
	var url = "mobile/postServlet";	
	$.post( url , {
		method : 'getAllPosition',
		time : new Date().getTime()
	} , function(data){
		$.each(data,function(index,position){
			$("#txt").append( "<tr><td align='center'><b>" + position.postId + "</b></td><td align='center'><b>"+ position.postName +"</b></td></tr><tr><td colspan='2'><hr></td></tr>" );
		});	
	} ,"json" );// end page create event	
});

