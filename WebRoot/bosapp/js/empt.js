
//--------------------------------------------------------------------------添加员工------------------------------------------------------------------
$("#addEmpt").live( "pageshow" , function(event){
	
	//获取部门
	$.post( "mobile/emptServlet" , { 
					method : 'getDepts',time : new Date().getTime()  
			},function callBack( data ){
				//先移除,后添加
				$.each( data , function(index,dept){
						$("#deptId").append( "<option value='"+dept.deptId+"'>" +dept.deptName+ "</option>" );
				});// end each
	} , "json"  );
	
	
	//获取所有职务
	$.post( "mobile/emptServlet" , { 
					method : 'getPosts',time : new Date().getTime()  
			},function callBack( data ){
				$.each( data , function(index,post){
						$("#postId").append( "<option value='"+post.postId+"'>" +post.postName+ "</option>" );
				});// end each
	} , "json"  );
	
	
	//获取民族列表
	$.post( "mobile/emptServlet" , { 
					method : 'getNations',time : new Date().getTime()  
			},function callBack( data ){
				$.each( data , function(index,nation){
						$("#nationId").append( "<option value='"+nation.nationId+"'>" +nation.nationName+ "</option>" );
				});// end each
	} , "json"  );
	
	
	//-------------------------------绑定添加事件----------------------------
	//年龄自己计算  出生日期自己计算
	$("#addEmptBtn").click(function(event){
		var name = $("#name").val();
		var cardNo = $("#cardNo").val();
		var presetAddress = $("#presetAddress").val();
		var mobilePhone = $("#mobilePhone").val();
		var homeTel = $("#homeTel").val();
		var homeAddress = $("#homeAddress").val();
		var nationId = $("#nationId").val();
		var deptId = $("#deptId").val();
		var postId = $("#postId").val();
		var sex = $("input[name='sex']:checked").val();
		var remark = $("#remark").val();
		
		//----------------------------根据身份证号码自动获取:出生年月日和实际年龄----------------------------------------
		var idCard = new clsIDCard(cardNo);					//创建身份证验证对象,通过该对象获得年龄和出生日期
		var birtyDate =idCard.GetBirthDate();				//出生日期
		var currentYear = new Date().getFullYear();			//获得当前年份
		var age = currentYear - idCard.GetBirthYear();      //当前年份  - 出生年份  = 实际年龄
		
		
		//--------------- start validate ------------------------
		var nameFlag = checkIsNull(name);		
		var presAddFlag = checkIsNull(presetAddress);
		var phoneFlag = checkPhone(mobilePhone);
		
		var nationFlag = checkSelected( nationId );
		var deptIdFlag = checkSelected( deptId );
		var postIdFlag = checkSelected( postId );
		
		if( !nameFlag ){
			$.mobile.changePage( $("#empNameErrorDialog") ,{ transition : "pop" ,changeHash: false ,reloadPage : true } );
			return false;
		}
		
		if( !idCard.IsValid() ){
			$.mobile.changePage( $("#cardNoErrorDialog") ,{ transition : "pop" ,changeHash: false ,reloadPage : true } );
			return false;
		}
		
		if( !phoneFlag ){
			$.mobile.changePage( $("#empPhoneErrorDialog") ,{ transition : "pop" ,changeHash: false ,reloadPage : true } );
			return false;
		}
		
		if( !presAddFlag ){
			$.mobile.changePage( $("#empPresetAddressErrorDialog") ,{ transition : "pop" ,changeHash: false ,reloadPage : true } );
			return false;
		}
		
		
		if( !deptIdFlag || !postIdFlag || !nationFlag  ){
			$.mobile.changePage( $("#empSelectedErrorDialog") ,{ transition : "pop" ,changeHash: false ,reloadPage : true} );
			return false;
		}

		//--------------- start ajax ------------------------
		var url = "mobile/emptServlet";
		var param = {
			'method' : 'add',
			'name'    : name,
			'cardNo'  : cardNo,
			'birtyDate' : birtyDate,
			'age'	  : age,
			'presetAddress'	: presetAddress,
			'mobilePhone' : mobilePhone,
			'homeTel' : homeTel,
			'homeAddress' : homeAddress,
			'nationId' : nationId,
			'deptId' : deptId,
			'postId' : postId,
			'sex' : sex,
			'remark' : remark
		};

		$.post( url ,param ,function(data){
			if( data == "success" ){
				$.mobile.changePage( "success.jsp" ,{ transition : "pop" ,changeHash: false ,reloadPage : true } );
				return false;
			}else if( data == "cardNoError" ){
				$.mobile.changePage( $("#cardNoRepeatErrorDialog") ,{ transition : "pop" ,changeHash: false ,reloadPage : true } );
				return false;
			}else{
				$.mobile.changePage( "error.jsp" ,{ transition : "pop" ,changeHash: false ,reloadPage : true } );
				return false;
			}			
		},"text");

		return false;
	});
	
	
	//----绑定身份证号码离开事件
	$("#cardNo").blur(function(event){
		var cardNo = $(this).val();		
		var idCard = new clsIDCard(cardNo);	
		
		if( cardNo =="" ){
			return false;
		}		
		
		if( !idCard.IsValid() ){
				$.mobile.changePage(  $("#cardNoErrorDialog") ,{ transition : "pop" ,changeHash: false ,reloadPage : true } );
			return false;
			
		}else{		
			//1.去服务器去验证是否存在该身份证
			//1.1 如果不存在,把相关数据填入文本框中,如果存在,提示失败
			$.post( "mobile/emptServlet" ,
					{ 'method' : 'getEmployeeByCardNo' , 'cardNo' : cardNo , 'time': new Date().getTime()  } , function(data){
						if( data == "cardNoError" ){
							myDialog( "错误提示" , "该身份证号码已经存在!" , "返&nbsp;&nbsp;&nbsp;&nbsp;回" );
							$.mobile.changePage(   $("#my-dialog") ,{ transition : "pop" ,changeHash: false ,reloadPage : true } );
							return false;							
						}else{
							//如果不存在该身份证,则把相关数据( 出生日期 | 性别 | 年龄 ),填入文本框中
							var local =  idCard.GetLocal();		    //省份
							var sex = idCard.GetSex();				//性别:[1]男  [2]女
							
							$("#homeAddress").val(local);			//设置省份
							
							if( sex ){								//设置性别
								$("#woman").attr("checked",false).checkboxradio("refresh");
								$("#male").attr("checked",true).checkboxradio("refresh");
							}else{
								$("#male").attr("checked",false).checkboxradio("refresh");
								$("#woman").attr("checked",true).checkboxradio("refresh");
							}							
						}//end else						
					}, "text" );				
		}//end else
		
	}); //end cardNo blur event
});



//----------------------------------------------------------------------------修改员工-----------------------------------------------------------------
$("#emptList").live( "pageshow" , function(event){
//	$("#editEmptPage").hide();
//	$("#searchEmptPage").show();
	
	//获取部门
	$.post( "mobile/emptServlet" , { 
					method : 'getDepts',time : new Date().getTime()  
			},function callBack( data ){
				$.each( data , function(index,dept){
						$("#searDeptId").append( "<option value='"+dept.deptId+"'>" +dept.deptName+ "</option>" );
						$("#deptId").append( "<option value='"+dept.deptId+"'>" +dept.deptName+ "</option>" );
				});// end each
	} , "json"  );
	
	
	//获取所有职务
	$.post( "mobile/emptServlet" , { 
					method : 'getPosts',time : new Date().getTime()  
			},function callBack( data ){
				$.each( data , function(index,post){
						$("#searPostId").append( "<option value='"+post.postId+"'>" +post.postName+ "</option>" );
						$("#postId").append( "<option value='"+post.postId+"'>" +post.postName+ "</option>" );
				});// end each
	} , "json"  );	
	
	//绑定搜索员工,获取数据方法
	$("#btnSearch").click(function(event){
		var searchName = $("#searName").val();
		var searchCardNo = $("#searCardNo").val();
		var searchDeptId = $("#searDeptId").val();
		var searchPostId = $("#searPostId").val();
		var searchStatus = $("#searStatus").val();
		
		if( searchName == "" && searchCardNo=="" ){
			$.mobile.changePage(   $("#searchWhereIsNullErrorDialog") ,{ transition : "pop" ,changeHash: false ,reloadPage : true } );
			return false;
		}
		
		
		$.post( "mobile/emptServlet" , {
			'method' : 'getEmployeeByCardNoORName',
			'name' : searchName,
			'cardNo' : searchCardNo,
			'deptId' : searchDeptId,
			'postId' : searchPostId,
			'status' : searchStatus,
			'time' : new Date().getTime()
		},function(data){
			if( data ){
				$("#searchEmptPage").hide();
				$("#editEmptPage").show(300);				
				
				$("#name").val( data.name );
				$("#cardNo").val( data.cardNo );
				$("#birtyDate").val( data.birtyDateStr );
				$("#age").val( data.age );
				var sex = data.sex;
				if( sex == "1" ){
					$("#woman").attr("checked",false).checkboxradio("refresh");
					$("#male").attr("checked",true).checkboxradio("refresh");
				}else{
					$("#male").attr("checked",false).checkboxradio("refresh");
					$("#woman").attr("checked",true).checkboxradio("refresh");
				}
				
				$("#mobilePhone").val( data.mobilePhone );
				$("#presetAddress").val( data.presetAddress );
				$("#homeAddress").val( data.homeAddress );
				$("#homeTel").val( data.homeTel );
				$("#nationId").val( data.nationId );
				$("#remark").val( data.remark );		
				
				//在职状态处理
				$("#status option").each(function(index,item){
					var currentStatus = $(this).val();
					if( currentStatus == data.status ){
						var myswitch = $("#status");
						myswitch[0].selectedIndex = index;
						myswitch .slider("refresh");	
					}					
				});
				
				//所在部门需要特别处理  
				$("#deptId option").each(function(index,item){
					var currentDeptId = $(this).val();
					if( data.deptId == currentDeptId ){
						var myselect = $("#deptId"); 
						myselect[0].selectedIndex = index;
						myselect.selectmenu("refresh");
					}				
				});
				
				//所在职务需要特别处理
				$("#postId option").each(function(index,item){
					var currentPostId = $(this).val();
					if( data.postId == currentPostId ){
						var myselect = $("#postId"); 
						myselect[0].selectedIndex = index;
						myselect.selectmenu("refresh");
					}				
				});
			}else{
				$.mobile.changePage( "error.jsp" ,{ transition : "pop" ,changeHash: false  ,reloadPage : true  } );
				return false;
			}			
		},"json" );		
		return false;
	});
	
	
	
	//绑定修改员工数据方法,并在提交前进行验证是否合格
	// 注意事项: 1. 只可修改基本字段,其它字段不可修改
	// 		   2. 如果选择中离职,那么要更改离职状态 
	 $("#editEmptBtn").click(function(event){
	 	var name = $("#name").val();
		var cardNo = $("#cardNo").val();
		var mobilePhone = $("#mobilePhone").val();
		var presetAddress = $("#presetAddress").val();
		var homeAddress = $("#homeAddress").val();
		var homeTel = $("#homeTel").val();
		var deptId = $("#deptId").val();
		var postId = $("#postId").val();
		var remark = $("#remark").val();
		var status = $("#status").val();
		

		//--------------- start validate ------------------------
		var nameFlag = checkIsNull(name);		
		var presAddFlag = checkIsNull(presetAddress);
		var phoneFlag = checkPhone(mobilePhone);
		
		var deptIdFlag = checkSelected( deptId );
		var postIdFlag = checkSelected( postId );
		
		if( !nameFlag ){
			$.mobile.changePage( $("#empNameErrorDialog") ,{ transition : "pop" ,changeHash: false,reloadPage : true } );
			return false;
		}
		
		if( !presAddFlag ){
			$.mobile.changePage( $("#empPresetAddressErrorDialog") ,{ transition : "pop" ,changeHash: false, reloadPage : true } );
			return false;
		}
		
		if( !phoneFlag ){
			$.mobile.changePage( $("#empPhoneErrorDialog") ,{ transition : "pop" ,changeHash: false, reloadPage : true } );
			return false;
		}
		
		if( !deptIdFlag || !postIdFlag ){
			$.mobile.changePage( $("#empSelectedErrorDialog") ,{ transition : "pop" ,changeHash: false, reloadPage : true } );
			return false;
		}
		
		
		$.post( "mobile/emptServlet" , {
			'method' : 'editEmployeeByCardNo',
			'cardNo' : cardNo,
			'name' : name,
			'mobilePhone' : mobilePhone,
			'presetAddress' : presetAddress,
			'homeAddress' : homeAddress,
			'homeTel' : homeTel,
			'status' : status,
			'deptId' : deptId,
			'postId' : postId,
			'remark' : remark,
			'time' : new Date().getTime()
		} ,function(data){
			if( data == "success" ){
				$.mobile.changePage( "success.jsp" ,{ transition : "pop" , changeHash : false, reloadPage : true } );
				return false;
			}else{
				$.mobile.changePage( "error.jsp" ,{ transition : "pop" , changeHash : false, reloadPage : true }  );
				return false;
			}			
		} , "text" );//end post
		
		return false;
	});	//end editEmptBtn 按钮
});




	var paramater = { 
		"empts" : []	
	};
//------------------------------------绑定员工离职搜索页面-------------------------------------------------
$("#quitSearch").live( "pageshow" , function(event){
	paramater["empts"] = [];
//	$("#quitEmployeeManagerPage").hide();
//	$("#searchQuitEmptPage").show();
	
	
	var totalRecords = 0;   //总共多少条记录
	var totalPage = 0;		//总共多少页
	var currentPage = 0;	//当前页面为:0
	var depts ;			    //所有的部门名称
	var posts ;			    //所有职务名称
	
	var searName ;
	var searCardNo;
	var searDeptId;
	var searPostId;
	var searStatus;
	
	//获取部门
	$.post( "mobile/emptServlet" , { 
					method : 'getDepts',time : new Date().getTime()  
			},function callBack( data ){
				depts = data;
				
				$.each( data , function(index,dept){
						$("#searDeptId").append( "<option value='"+dept.deptId+"'>" +dept.deptName+ "</option>" );
				});// end each
	} , "json"  );//end post
	
	
	//获取所有职务
	$.post( "mobile/emptServlet" , { 
					method : 'getPosts',time : new Date().getTime()  
			},function callBack( data ){
				posts = data;		
				
				$.each( data , function(index,post){
						$("#searPostId").append( "<option value='"+post.postId+"'>" +post.postName+ "</option>" );
				});// end each
	} , "json"  ); //end post
	
	
	//绑定搜索事件
	$("#btnSearch").click(function(event){
		searName = $("#searName").val();
		searCardNo = $("#searCardNo").val();
		searDeptId = $("#searDeptId").val();
		searPostId = $("#searPostId").val();
		searStatus = $("#searStatus").val();
		
		
		//根据条件获取数据库有多少条记录
		$.post( "mobile/emptServlet" , {
			method : 'getCountRecords',
			name : searName,
			cardNo : searCardNo,
			deptId : searDeptId,
			postId : searPostId,
			status : searStatus,
			time : new Date().getTime()
		} , function(data){
			totalRecords = parseInt( data );	
			totalPage = parseInt(  totalRecords % 2 == 0 ? (totalRecords/30) : (totalRecords/30)+1 );
		},"text" );
		
		
		//跳转到展示页面事件
		$.post(
			"mobile/emptServlet",{
				method : 'getEmployeesByWhene',
				name : searName,
				cardNo : searCardNo,
				deptId : searDeptId,
				postId : searPostId,
				status : searStatus,
				time : new Date().getTime()				
			},function(data){
				if(data){
					$("#searchQuitEmptPage").hide();
					$("#quitEmployeeManagerPage").show(100);
					
					privateEach( data );
					$("#submit").show();
				}else{
					$.mobile.changePage( "error.jsp" ,{ transition : "pop" ,changeHash: false , reloadPage : true } );
				}								
			},"json");		
		return false;	
	});//end click	
	
	
	//加载更多事件
	$("#btnLoadOthers").bind("click",function(event){
		//如果当前页数等于总共页数,则不准许现提交
		if( currentPage == totalPage ){
			$(this).hide();
			$("#loadEndMsg").show();
			return false;
		}
		
		//先去除页面中已有组件的单击事件,否则,以有的组件会有多次事件
		$("a[name='status']").each(function(){
			$(this).unbind("click");
			
		});
		
		//获取下一页记录
		currentPage++;
		if( currentPage <= totalPage   ){			
			$.post(
			"mobile/emptServlet",{
				method : 'getEmployeesByWhene',
				name : searName,
				cardNo : searCardNo,
				deptId : searDeptId,
				postId : searPostId,
				status : searStatus,
				currentPage : currentPage,
				time : new Date().getTime()				
			},function(data){
				if( data ){
					privateEach(data);  //遍历数据,并且给数据添加相关事件
				}
			},"json");
		}
	});//end 屏幕滚动事件
	
	
	
	//私有遍历方法
	function privateEach(data){
		$.each( data , function(index,emp){
			
			var str = "<tr><td align='center'>"+ emp.name +"</td>";						
			//部门信息
			$.each( depts , function(i,dept){
				if( dept.deptId == emp.deptId ){
					str+="<td align='center'>"+dept.deptName+"</td>";
				}
			});
						
			//职务信息
			$.each( posts , function(i,post){
				if( post.postId == emp.postId ){
					str+="<td align='center'>"+post.postName+"</td>";
				}							
			});
						
			str+="<td  align='center'>";
			if( emp.status == "Y" ){
				str+="<a name='status' cardno=\""+ emp.cardNo +"\"  status='Y'  data-theme='d' data-role='button' href='#' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span' class='ui-btn ui-shadow ui-btn-corner-all ui-btn-up-d'> <span class='ui-btn-inner ui-btn-corner-all'> <span class='ui-btn-text'>在职</span> </span> </a>";							
			}else{
				str+="<a name='status'  cardno=\""+ emp.cardNo +"\"  status='N'  data-theme='e' data-role='button' href='#' data-corners='true' data-shadow='true' data-iconshadow='true' data-wrapperels='span' class='ui-btn ui-shadow ui-btn-corner-all ui-btn-up-e'> <span class='ui-btn-inner ui-btn-corner-all'> <span class='ui-btn-text'>离职</span> </span> </a>";
			}
				str+="</td></tr>";
				$("#employess").append(str);
			});//end eatch	
					
			//给所有的a元素绑定事件
			$("a[name='status']").each(function(){
				$(this).click(function(){
					var cardNo = $(this).attr('cardno');
					var status = $(this).attr('status');

					//如果是在职状态,单击后变成离职职态
					if( status == "Y" ){
						$(this).attr('status',"N");
						$(this).attr('data-theme','e');
						$(this).removeClass("ui-btn-up-d");
						$(this).addClass("ui-btn-up-e");
						$(this).find(".ui-btn-text").html("离职");
						
						//添加到数组之前先判断是否存在这条记录
						for( var i=0;i<paramater["empts"].length;i++ ){
							if( paramater["empts"][i].cardNo ==  cardNo ){
								paramater["empts"][i].status = 'N';
								return false;
							}						
						}
						
						var emp = {
							cardNo : cardNo,
							status : 'N'
						};
						//添加到数组
						paramater["empts"].push(emp);
						
					}else{
						$(this).attr('status',"Y");
						$(this).attr('data-theme','d');
						$(this).removeClass("ui-btn-up-e");
						$(this).addClass("ui-btn-up-d");
						$(this).find(".ui-btn-text").html("在职");
						
						//添加到数组之前先判断是否存在这条记录
						for( var i=0;i<paramater["empts"].length;i++ ){
							if( paramater["empts"][i].cardNo ==  cardNo ){
								paramater["empts"][i].status = 'Y';
								return false;
							}						
						}//end for
						
						var emp = {
							cardNo : cardNo,
							status : 'Y'
						};
						//添加到数组
						paramater["empts"].push(emp);
					}
				});//end click event  				
				
			});//end status each
			
			
	}//end private Each
	
	
	//绑定提交事件
	$("#submit").click(function(event){
		if( paramater["empts"].length ){
			for( var i=0;i<paramater["empts"].length;i++ ){
				paramater["empts"][i] = JSON.stringify(paramater["empts"][i]);
			}

			$.post( "mobile/emptServlet", {
				time: new Date().getTime(),					
				method : 'editEmpStatus',
				status : paramater["empts"]
			}, function(data){
				if( data = "success" ){
					$.mobile.changePage( "success.jsp" ,{ 'transition' : "pop" ,'changeHash': false ,  reloadPage : true } );
					return false;					
				}else{
					$.mobile.changePage( "error.jsp" ,{ 'transition' : "pop" ,'changeHash': false ,  reloadPage : true } );
					return false;
				}
			},"text");
			
		}		
	});// end click
	
});// end quitSearch Page show

























//----------------------------------------------------------------职员详细列表------------------------------------------------
$("#emptDetailed").live("pageinit",function(event){
	$("#listEmployeesDiv").hide();
	$("#searchEmployessDiv").show();	//先隐藏后再显示
	
	var depts;							//所有的部门
	var posts;							//所有的职务
	
	//获取部门
	$.post( "mobile/emptServlet" , { 
					method : 'getDepts',time : new Date().getTime()  
			},function callBack( data ){
				depts = data;
				$.each( data , function(index,dept){
						$("#searDeptId").append( "<option value='"+dept.deptId+"'>" +dept.deptName+ "</option>" );
						$("#deptId").append( "<option value='"+dept.deptId+"'>" +dept.deptName+ "</option>" );
				});// end each
	} , "json"  );
	
	
	//获取所有职务
	$.post( "mobile/emptServlet" , { 
					method : 'getPosts',time : new Date().getTime()  
			},function callBack( data ){
				posts = data;
				$.each( data , function(index,post){
						$("#searPostId").append( "<option value='"+post.postId+"'>" +post.postName+ "</option>" );
						$("#postId").append( "<option value='"+post.postId+"'>" +post.postName+ "</option>" );
				});// end each
	} , "json"  );	
	
	
	
	var showRecords;					//获取要显示的记录数
	$("#btnSearch").click(function(event){		
		var searName = $("#searName").val();
		var searCardNo = $("#searCardNo").val();
		var searDeptId = $("#searDeptId").val();
		var searPostId = $("#searPostId").val();
		var searStatus = $("#searStatus").val();
		
		//如果部门为空,或者,职务为空时,提示'操作失败'
		if("all" == searDeptId  && "all"  == searPostId ){
			myDialog( "错误提示" , " 部门&nbsp;职务&nbsp;不能为空!" , "返&nbsp;&nbsp;&nbsp;&nbsp;回" );
			$.mobile.changePage( $("#my-dialog") ,{ transition : "pop" ,changeHash: false  ,reloadPage : true  } );
			return false;
		}
		

		//根据给定的条件获取总记录数
		$.post( "mobile/emptServlet" , {
			method : 'getCountRecords',
			name : searName,
			cardNo : searCardNo,
			deptId : searDeptId,
			postId : searPostId,
			status : searStatus,
			time : new Date().getTime()
		} , function(data){
			showRecords = parseInt( data );	
			if( data ){				
				getEmployess(searName,searCardNo,searDeptId,searPostId,searStatus);
				$("#searchEmployessDiv").hide();
				$("#listEmployeesDiv").show();
			}
		},"text" );		
	});	//end btn Search
	
	//获取员工详信信息函数
		function getEmployess(searName,searCardNo,searDeptId,searPostId,searStatus){
			$.post( "mobile/emptServlet", {
					method : 'getEmployees',
					name : searName,
					cardNo : searCardNo,
					deptId : searDeptId,
					postId : searPostId,
					status : searStatus,
					maxResult : showRecords,
					time : new Date().getTime()
			}, function(data){
				
				
				if( data ){
					var liStr="";
					$.each(data,function(index,emp){
						liStr+="<li><a name='showDetailed' cardNo='"+ emp.cardNo +"'  href='#showEmptDetailedDiv' data-rel='popup' data-position-to='window'  data-transition='pop'> ";
							
						//获取部门名称
							$.each(depts,function(i,dept){
								if( dept.deptId == emp.deptId ){							
									liStr+="<h2>"+ emp.name +"</h2> <p>"+dept.deptName+"</p>  ";
								}
							});

							
						liStr+="</a> <a name='showDetailed' cardNo='"+ emp.cardNo +"' href='#showEmptDetailedDiv' data-rel='popup' data-position-to='window'  data-transition='slideup'>详情 </a> </li>";
					}); //end each 获取数据
					
					//添加到数据中
					$("#listShowEmployess").append( liStr );
					$("#listShowEmployess").listview('refresh');
					
					//为所有的name='showDetailed'绑定事件
					$("a[name='showDetailed']").each(function(){
						$(this).click(function(){
							var currentCardNo = $(this).attr("cardNo");
							$.each(data,function(index,emp){
								if( emp.cardNo == currentCardNo ){
									$("#name").attr("value",emp.name);		
									$("#cardNo").attr("value",emp.cardNo);	
									$("#age").attr("value",emp.age);
									
									//性别
									if( emp.sex == 1 ){
										$("#sex").attr("value","男");
									}else{
										$("#sex").attr("value","女");	
									}
									
									$("#mobilePhone").attr("value",emp.mobilePhone);
									$("#presetAddress").attr("value",emp.presetAddress);
									$("#homeAddress").attr("value",emp.homeAddress);
									$("#homeTel").attr("value",emp.homeTel);
									
									//状态
									if( emp.status == "Y" ){
										$("#status").attr("value","在职");
									}else{
										$("#status").attr("value","离职");
									}
									
									//职务
									$.each(posts,function(index,post){
										if( emp.postId == post.postId ){
											$("#postId").attr("value",post.postName);
										}										
									});//end each posts
									
									//部门
									$.each(depts,function(index,dept){
										if( emp.deptId == dept.deptId ){
											$("#deptId").attr("value",dept.deptName);
										}											
									});//end each depts
									
								}//end if
							});	//end each				
						});	//end click					
					});
					
					
				}else{
					
					$.mobile.changePage( "error.jsp" ,{ transition : "pop" ,changeHash: false   ,reloadPage : true } );
					return false;
				}//end else
			},"json");
		}//end getEmployess function
});



		


