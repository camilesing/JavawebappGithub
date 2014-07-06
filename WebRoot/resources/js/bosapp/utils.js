
/**
 * 自定义Dialog 
 * @param {} dom
 * @param {} title
 * @param {} content
 * @param {} returnBtn
 */
	function myDialog(title , content , returnBtn ){
		$("#my-dialog").live( "pageinit" , function(event){
			$("#my-dialog-title").html( title );
			$("#my-dialog-content").html( content );
			$("#my-dialog-return").html("<span class='ui-btn-inner ui-btn-corner-all'> <span class='ui-btn-text'>"+ returnBtn +"</span> </span>" );
		});
	}
	
	/**
	 * myDialog( "错误提示" , "该身份证号码已经存在!" , "返&nbsp;&nbsp;&nbsp;&nbsp;回" );
	 * $.mobile.changePage(   $("#my-dialog") ,{ transition : "pop" ,changeHash: false ,reloadPage : true } );
	 */
	
	
	/**
	 * 验证id是否为: 小于等于5位的数字
	 * @param {} id
	 * @return {Boolean}
	 */
	function valiateIdBetwwn( id ){
		if( id.length < 5 || id.length > 5 ){
			return false;
		}else{
			return true;
		}
	}
	
	
	/**
	 * 验证id是否为0-9之间的数字
	 * @param {} id
	 * @return {Boolean}
	 */
	function regIdInt(id){ 
		var result=id.match(/^[0-9]+$/); 
		if(result==null) return false; 
		return true;  
	}
	
	/**
	 * 验证name是否为空
	 * @param {} name
	 * @return {Boolean}
	 */
	function nameValidate( name ){
		if(  name.length <=0  ){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * ajaxPost提交方式
	 * @param {} url
	 * @param {} param
	 * @param {} fun
	 * @param {} returnType
	 */
	function ajaxPost( url , param , fun , returnType ){
		$.post( url , param , fun , returnType  );
	}
	
	
	/**
	 * 验证提供的obj不为空
	 */
	function checkIsNull(obj){
		if( obj.length <= 0  ){
			return false;
		}
		return true;
	}
	
	/**
	 * 验证手机号码是否正确
	 * @param {} phone
	 * @return {Boolean}
	 */
	function checkPhone(phone){
	   //验证电话号码手机号码，包含至今所有号段   
	  var ab=/^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$/;
	  if(ab.test(phone) == false){
	    return false;
	  }
	  return true;
	}
	
	/**
	 * 验证下拉列表框的值是否为'--'
	 * 如果为:'--'返回false,用户必须从下拉列表框中选择一个
	 * @param {} obj
	 * @return {Boolean}
	 */
	function checkSelected(obj){
		if( obj == "--" ){
			return false;
		}
		return true;
	}
	/**
	 * 跨域异步请求
	 */
	function ajax_jsonp(url,param,return_fun){
		$.ajax({
			async:false,
			url: url,
			type: "GET",
			dataType: 'jsonp',
			jsonp: "callbackfun",
			jsonpCallback:"jsonpCallback",
			data: param,
			timeout: 5000,
			success: function (json) {//客户端jquery预先定义好的callback函数,成功获取跨域服务器上的json数据后,会动态执行这个callback函数
				return_fun(json);
			},
			error:function(){
				var json = {"success":false,"msg":"error","o":""};
				return_fun(json);
			}
		});		
	}
	
	var html5 = {
		    author: 'Henlo',
		    version: '1.0',
		    website: 'http://www.henlo.net'
		}
	html5.utils = {
	    setParam: function(name, value) {
	        localStorage.setItem(name, value)
	    },
	    getParam: function(name) {
	        return localStorage.getItem(name)
	    }
	}
	
	html5.utils.setParam('ProjectUrl', "/Javawebapp")
	
	