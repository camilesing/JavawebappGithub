<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@include file="header.jsp" %>

<script type="text/javascript">
<!--
$(document).on("pageinit","#wenjuan",function(){
	var currYear = (new Date()).getFullYear();	
	var opt={};
	opt.date = {preset : 'date'};
	//opt.datetime = { preset : 'datetime', minDate: new Date(2012,3,10,9,22), maxDate: new Date(2014,7,30,15,44), stepMinute: 5  };
	opt.datetime = {preset : 'datetime'};
	opt.time = {preset : 'time'};
	opt.default = {
		theme: 'android-ics light', //皮肤样式
        display: 'modal', //显示方式 
        mode: 'scroller', //日期选择模式
        dateOrder: 'yyyy-mm',
        dateFormat: 'yyyy-mm',
		lang:'zh',
        startYear:currYear - 10, //开始年份
        endYear:currYear + 10 //结束年份
	};
	
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}
	
	var now = new Date().Format("yyyy-MM-dd");
	
	$("#dianpkysj").val('').scroller('destroy').scroller($.extend(opt['date'], opt['default']));
	$("#zhuangxsj").val('').scroller('destroy').scroller($.extend(opt['date'], opt['default']));
	
	
	fun_submit = function(){
		var project_path = $("#wenjuan").attr("project_path");
		var url_init = project_path + "/bosappaction/wenjuan";
		var dianpmc = $("#dianpmc").val();
		var fuzr = $("#fuzr").val();
		var xingb = $('input[name="xingb"]:checked').val();
		var lianxdh = $("#lianxdh").val();
		var dianpxxdz = $("#dianpxxdz").val();
		var dianpkysj = $("#dianpkysj").val();
		var dianpmj = $("#dianpmj").val();
		var dianpxz = $('input[name="dianpxz"]:checked').val();
		var suossq = $('input[name="suossq"]:checked').val();
		var nianzj = $("#nianzj").val();
		var nianyye = $("#nianyye").val();
		var yingymj = $("#yingymj").val();
		var yuangrs = $("#yuangrs").val();
		var vipsl = $("#vipsl").val();
		var zhuangxsj = $("#zhuangxsj").val();
		var yingyqtpp = $("#yingyqtpp").val();
		var shengf = $("#shengf").val();
		
		//myDialog( "提示" , " " , "返&nbsp;&nbsp;&nbsp;&nbsp;回" );
		
		
		if( !checkIsNull(dianpmc) ){
			$("#my-dialog-content").html( "店铺代码不能空" );
			$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			return false;
		}
		if( !checkIsNull(fuzr) ){
			$("#my-dialog-content").html( "负责人不能空" );
			$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			return false;
		}
		if( !checkIsNull(xingb) ){
			$("#my-dialog-content").html( "性别不能空" );
			$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			return false;
		}
		if( !checkIsNull(lianxdh) ){
			$("#my-dialog-content").html( "手机号码不能空" );
			$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			return false;
		}
		if( !checkIsNull(dianpxxdz) ){
			$("#my-dialog-content").html( "店铺详细地址不能空" );
			$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			return false;
		}
		if( !checkIsNull(yingymj) ){
			$("#my-dialog-content").html( "营业面积不能空" );
			$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			return false;
		}
		if( !checkIsNull(dianpkysj) ){
			$("#my-dialog-content").html( "店铺开业时间不能空" );
			$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			return false;
		}
		if( !checkIsNull(dianpxz) ){
			$("#my-dialog-content").html( "店铺性质不能空" );
			$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			return false;
		}
		if( !checkIsNull(suossq) ){
			$("#my-dialog-content").html( "所属商圈不能空" );
			$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			return false;
		}
		if( !checkIsNull(nianzj) ){
			$("#my-dialog-content").html( "年租金不能空" );
			$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			return false;
		}
		if( !checkIsNull(nianyye) ){
			$("#my-dialog-content").html( "年营业额不能空" );
			$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			return false;
		}
		
		if( !checkIsNull(yuangrs) ){
			$("#my-dialog-content").html( "员工人数不能空" );
			$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			return false;
		}
		if( !checkIsNull(vipsl) ){
			$("#my-dialog-content").html( "VIP数量不能空" );
			$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			return false;
		}
		if( !checkIsNull(zhuangxsj) ){
			$("#my-dialog-content").html( "最近装修时间不能空" );
			$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			return false;
		}
		
		
		var param = {
				"dianpmc":dianpmc,
				"fuzr":fuzr,
				"xingb":xingb,
				"lianxdh":lianxdh,
				"dianpxxdz":dianpxxdz,
				"dianpkysj":dianpkysj,
				"kaiynx":"kaiynx",
				"dianpxz":dianpxz,
				"suossq":suossq,
				"nianzj":nianzj,
				"nianyye":nianyye,
				"yingymj":yingymj,
				"yuangrs":yuangrs,
				"vipsl":vipsl,
				"zhuangxsj":zhuangxsj,
				"shifzxxxx":yingyqtpp,
				"shifxkd":shengf
				};
		
		//清理现有的列表
		$.mobile.showPageLoadingMsg('a', "加载中……",true);
		
		$.ajax({
			type: "post",
			url:  url_init,
			data: param,
			success: function(json, textStatus){
				$("#submitBtn").button("disable");
				$.mobile.hidePageLoadingMsg(''); 
				$("#my-dialog-content").html( "提交成功" );
				$.mobile.changePage($("#my-dialog") ,{ transition : "pop" ,changeHash: true ,reloadPage : true } );
			}
		});
	};
	
	$("#submitBtn").click(function(){
		fun_submit();
	});
	
});
//-->
</script>  
  
   <div data-role="page" id="wenjuan" project_path = "${ctx}" > 
   		<div data-role="header" data-theme="b">
   			<h1 align="center">${sessionScope.truename} 您好! 欢迎参加风笛14冬新品订货会！ </h1>
   		</div>
   		
   		<div data-role="content" data-theme="c" align="center">
   			<table >
   			<tr>
   				<td colspan="1">省份:</td>
   				<td colspan="3"><select name="shengf" id="shengf">
						<option value="京">北京</option>
						<option value="沪">上海</option>
						<option value="津">天津</option>
						<option value="渝">重庆</option>
						<option value="冀">河北</option>
						<option value="豫">河南</option>
						<option value="鄂">湖北</option>
						<option value="湘">湖南</option>
						<option value="苏">江苏</option>
						<option value="赣">江西</option>
						<option value="辽">辽宁</option>
						<option value="吉">吉林</option>
						<option value="黑">黑龙江</option>
						<option value="陕">陕西</option>
						<option value="鲁">山东</option>
						<option value="晋">山西</option>
						<option value="川">四川</option>
						<option value="青">青海</option>
						<option value="皖">安徽</option>
						<option value="琼">海南</option>
						<option value="粤">广东</option>
						<option value="桂">广西</option>
						<option value="贵">贵州</option>
						<option value="浙">浙江</option>
						<option value="闽">福建</option>
						<option value="甘">甘肃</option>
						<option value="云">云南</option>
						<option value="蒙">内蒙古</option>
						<option value="宁">宁夏</option>
						<option value="新">新疆</option>
						<option value="藏">西藏</option>
						<option value="港澳台">港澳台</option>
				    </select></td>
   			</tr>	
   			<tr>
   				<td>店铺代码:</td>
   				<td><input type="text" name="dianpmc" id="dianpmc" value="" data-theme="e"/></td>   			
   				<td>负责人姓名:</td>
   				<td><input type="text" name="fuzr" id="fuzr" value="" data-theme="e"/></td>   			
   			</tr>	
   			<tr>
   				<td>性别:</td>
   				<td>
				    <fieldset data-role="controlgroup" data-type="horizontal">
				        <legend></legend>
				        <input type="radio" name="xingb" id="xingba" value="1" checked="checked">
				        <label for="xingba">男</label>
				        <input type="radio" name="xingb" id="xingbb" value="0">
				        <label for="xingbb">女</label>
				    </fieldset>	
				</td>   			
   				<td>
				   手机号码:
				</td>
   				<td>
				   <input type="text" name="lianxdh" id="lianxdh" value="" data-theme="e"/>
				</td>
   			</tr>
			<tr>
   				<td>
				   店铺详细地址:
				</td>
   				<td colspan="3">
				   <input type="text" name="dianpxxdz" id="dianpxxdz" value="" data-theme="e"/>
				</td>
   			</tr>
   			<tr>
   				<td>
				   营业面积:
				</td>
   				<td>
				   <input type="text" name="yingymj" id="yingymj" value="" data-theme="e"/>
				</td>   			
   				<td>
				   店铺开业时间:
				</td>
   				<td>
				   <input type="text" name="dianpkysj" id="dianpkysj" value="" data-theme="e"/>
				</td>   			
   			</tr>
			<tr>
				<td>
				   店铺性质:
				</td>
   				<td>
				   <fieldset data-role="controlgroup" data-type="horizontal">
				        <legend></legend>
				        <input type="radio" name="dianpxz" id="dianpxza" value="地铺" >
				        <label for="dianpxza">地铺</label>
				        <input type="radio" name="dianpxz" id="dianpxzb" value="商场" checked="checked" >
				        <label for="dianpxzb">商场</label>
				        <input type="radio" name="dianpxz" id="dianpxzc" value="店中店">
				        <label for="dianpxzc">店中店</label>
				    </fieldset>
				</td>
   			
   				<td>
				   所属商圈:
				</td>
   				<td>
				   <fieldset data-role="controlgroup" data-type="horizontal">
				        <legend></legend>
				        <input type="radio" name="suossq" id="suossqa" value="A" checked="checked">
				        <label for="suossqa">A</label>
				        <input type="radio" name="suossq" id="suossqb" value="B">
				        <label for="suossqb">B</label>
				        <input type="radio" name="suossq" id="suossqc" value="C">
				        <label for="suossqc">C</label>
				    </fieldset>
				</td>   			
   			</tr>
			<tr>		
				<td>
				   年租金(万元):
				</td>
   				<td>
				   <input type="number" name="nianzj" pattern="[0-9]*" id="nianzj" value="" data-theme="e"/>
				</td>   			
   				<td>
				   年营业额(万元):
				</td>
   				<td>
				   <input type="number" name="nianyye" pattern="[0-9]*" id="nianyye" value="" data-theme="e"/>
				</td>
   			</tr>
			
			<tr>
   				<td>
				   员工人数:
				</td>
   				<td>
				   <input type="number" name="yuangrs" pattern="[0-9]*" id="yuangrs" value="" data-theme="e">
				</td>   			
   				<td>
				   VIP数量:
				</td>
   				<td>
				   <input type="number" name="vipsl" pattern="[0-9]*" id="vipsl" value="" data-theme="e">
				</td>
   			</tr>
			<tr>
   				<td>
				   营业的其他品牌:
				</td>
   				<td>
				   <input type="text" name="yingyqtpp" id="yingyqtpp" value="" data-theme="e"/>
				</td>   			
   				<td>
				   最近装修月份:
				</td>
   				<td>
				   <input type="text" name="zhuangxsj" id="zhuangxsj" value="" data-theme="e"/>   
				</td>
   			</tr>
   			<tr>
   				<td colspan="4" align="center">
					<label >感谢您的参与配合！</label>
				</td>
   			</tr>
   			<tr>
   				<td colspan="4" align="center">
					<button id="submitBtn" data-theme="b" >提交问卷</button> 
				</td>
   			</tr>
   		
   		</table>
   			
   			
   		</div> <!--  end content -->
   		<jsp:include page="divFooter.jsp"></jsp:include>  <!--  end footer -->
   	</div> <!-- end page -->
   	
<%@include file="footer.jsp" %>
