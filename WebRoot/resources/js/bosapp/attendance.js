//attenceSearchList
$("#attenceSearchList").live("pageinit",function(){
	var currnetPage = 0;		//当前在第几页
	var maxResult = 100;	   //一次最多显示:100条记录
	var totalRecord = 0;	   //共计多少条数据
	var totalPage = 0;		   //共计多少页
	
	var searName;
	var searCardNo;
	var searDeptId;
	var searPostId;
	var startDate;
	var endDate;
	
	//获取所有的部门详情
	var url = "mobile/deptServlet";
	$.post( url , {
			method : 'getAllDept',
			time : new Date().getTime()
	} , function(data){
			$.each( data ,function(index,dept){
				$("#searDeptId").append( "<option value='"+dept.deptId+"'>"+ dept.deptName +"</option>" );					
			});
	} ,"json"  );
	
	//获取所有的职务详情
	var url = "mobile/postServlet";	
		$.post( url , {
		method : 'getAllPosition'				
	} , function(data){
		$.each( data , function(index,position){
			$("#searPostId").append( "<option value='"+position.postId+"'>"+ position.postName +"</option>" );
		});
	} ,"json"  );
	
	
	
	//提交事件
	$("#searchBtn").click(function(){
		searName = $("#searName").val();
		searCardNo = $("#searCardNo").val();
		searDeptId = $("#searDeptId").val();
		searPostId = $("#searPostId").val();
		
		var startYear = $("#startYear").val();
		var startMonth = $("#startMonth").val();
		var startDay = $("#startDay").val();
		var startHour = $("#startHour").val();
		var startMinute = $("#startMinute").val();
		var startSecond = $("#startSecond").val();
		startDate = startYear +"-" + startMonth + "-" + startDay +" " +startHour+":"+startMinute+":"+ startSecond;
		
		var endYear = $("#stopYear").val();
		var endMonth = $("#stopMonth").val();
		var endDay = $("#stopDay").val();
		var endHour = $("#stopHour").val();
		var endMinute = $("#stopMinute").val();
		var endSecond = $("#stopSecond").val();
		endDate = endYear +"-"+endMonth+"-"+endDay+" " +endHour+":"+endMinute+":"+endSecond;
		
		
		//先统计多少条记录
		$.post("mobile/attendanceServlet",{
			method : "getCountRecords",
			name : searName,
			cardNo : searCardNo,
			deptId : searDeptId,
			postId : searPostId,
			startDate : startDate,
			endDate : endDate,
			time : new Date().getTime()
		},function(data){
			totalRecord = parseInt(data);
			totalPage = parseInt( totalRecord % 2 == 0 ? (totalRecord / maxResult) :  (totalRecord / maxResult)+1 );
			
			getAttendanceByWhere( searName,searCardNo,searDeptId,searPostId,startDate,endDate,currnetPage,maxResult );
		},"text");
		
		
		
		
		
		
	});//end click event	
});

function getAttendanceByWhere( searName,searCardNo,searDeptId,searPostId,startDate,endDate,currnetPage ,maxResult ){
	$.post( "mobile/attendanceServlet" , {
			method : "getAttendanceByWhere",
			currnetPage: currnetPage,
			maxResult : maxResult,
			name : searName,
			cardNo : searCardNo,
			deptId : searDeptId,
			postId : searPostId,
			startDate : startDate,
			endDate : endDate,
			time : new Date().getTime()
	},function(data){
			if( data ){
				var tBody="";
				$.each( data , function(index,att){
					tBody+="<tr> <td align='center'>  "+ att.name+"	</td> <td align='center'>"+ att.workDateStr.substring(0,16) +"</td> </tr> <tr> <td colspan='2'> <hr> </td> </tr> ";
				})
				$("#attendBody").append(tBody);
				
				$("#searchAttend").hide();
				$("#attendList").show();
			}			
	},"json");
}