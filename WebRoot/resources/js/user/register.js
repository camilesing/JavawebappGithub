$(document).ready(function() {
//yepnope("${ctx}/resources/js/user/register.js");
Ext.ns("Ext.Authority.register"); // 自定义一个命名空间
register = Ext.Authority.register; // 定义命名空间的别名
register = {
	registerurl : ctx + "/register",
	SEX: eval('(${fields.sex==null?"{}":fields.sex})') //注意括号
};

Ext.apply(Ext.form.VTypes, {
    confirmPwd : function(val, field) {
        if (field.confirmPwd) {
            var firstPwdId = field.confirmPwd.first;
            var secondPwdId = field.confirmPwd.second;
            this.firstField = Ext.getCmp(firstPwdId);
            this.secondField = Ext.getCmp(secondPwdId);
            var firstPwd = this.firstField.getValue();
            var secondPwd = this.secondField.getValue();
            if (firstPwd == secondPwd) {
                return true;
            } else {
                return false;
            }
        }
    },
    confirmPwdText : '两次输入的密码不一致!'
});
// 编辑用户Form
register.password = new Ext.form.TextField({
    fieldLabel: '密码',
    inputType: 'password',
    maxLength: 32,
    allowBlank: false,
    emptyText:'请输入密码',
    id : 'register_password',
    name: 'password',
    regex : /^[\s\S]{0,20}$/,
    regexText : '密码长度不能超过20个字符',
    anchor: '99%'
});

register.confirmPassword = new Ext.form.TextField({
	id : 'register_confirmPassword',
    name : 'confirmPassword',
    fieldLabel : '<font color="red">确认密码</font>',
    confirmPwd : {
        first : 'register_password',
        second : 'register_confirmPassword'
    },
    inputType : 'password',
    vtype : 'confirmPwd',
    allowBlank : false,
    blankText : '确认密码不能为空',
    regex : /^[\s\S]{0,20}$/,
    regexText : '确认密码长度不能超过20个字符',
    anchor: '99%'
});
/*性别*/
register.sexCombo = new Ext.form.ComboBox({
    fieldLabel: '性别',
    hiddenName: 'sex',
    name: 'sex',
    id : 'register_sex',
    triggerAction: 'all',
    mode: 'local',
    store: new Ext.data.ArrayStore({
        fields: ['v', 't'],
        data: Share.map2Ary(register.SEX)
    }),
    valueField: 'v',
    displayField: 't',
    allowBlank: false,
    editable: false,
    anchor: '99%'
}); 

/** 基本信息-详细信息的form */
register.registerFormPanel = new Ext.form.FormPanel({
	renderTo : 'registerFormDiv',
	border : false,
	defaults : {
		xtype : "textfield",
		labelWidth : 50
	},
	bodyStyle : 'padding:5px;background-color:transparent;',
	items : [ 
	{// 帐号
		fieldLabel : '账号',
		emptyText:'请输入帐号,登陆系统用',
		maxLength : 64,
		allowBlank : false,
		name : 'account',
		id:'register_account',
		anchor : '99%'
	},
	{
        fieldLabel: '用户姓名',
        emptyText:'用户真实姓名',
        maxLength: 64,
        allowBlank: false,
        name: 'realName',
        id : 'register_realName',
        anchor: '99%'
    },
	register.password,
	register.confirmPassword,
	{ // 注册邮箱
		fieldLabel : '注册邮箱',
		maxLength : 64,
		allowBlank : false,
		regex : /^[a-zA-Z0-9_\.\-]+\@([a-zA-Z0-9\-]+\.)+[a-zA-Z0-9]{2,4}$/,
		regexText : '请输入有效的邮箱地址',
		emptyText:'请输入有效的邮箱地址',
		name : 'email',
		id:'register_email',
		anchor : '99%'
	}, {
		fieldLabel : '验证码',
		maxLength : 6,
		emptyText:'验证码不区分大小写',
		allowBlank : false,
		name : 'captcha',
		id:'register_captcha',
		anchor : '99%'
	}, {
		xtype : 'box', //或者xtype: 'component',  
		width : 160, //图片宽度  
		height : 40, //图片高度 
		style : 'margin-left: 105px',
		id:'_checkimage',
		autoEl : {
			tag : 'img', //指定为img标签
			alt : "验证码",
			title : '看不清楚？点击获得新图片。',
			onclick : "javacript:$(this).hide().attr('src', ctx + '/checkimage.jpg?r=' + Math.random()).fadeIn();",
			src : ctx + '/checkimage.jpg?r=' + Math.random() //指定url路径  
		}
	} ]
});
// 工具栏
register.registerToolbar = new Ext.Toolbar({
	renderTo : 'registerToolBarDiv',
	items : [ 
	  new Ext.Button({
		text : '注册',
		iconCls : 'send',
		handler : function() {
			var form = register.registerFormPanel.getForm();
			if (form.isValid()) {
				var values = form.getValues();
				// 发送请求
				Share.AjaxRequest({
					url : register.registerurl,
					params : values,
					callback : function(json) {
						// 关闭窗口
						login.registerWindow.close();
					},
					falseFun : function(json) {//失败后想做的个性化操作函数
						if (json.msg.indexOf('验证码已经失效') > -1) {
							$("#_checkimage").click();
							$("#register_captcha").focus().val('');
							return;
						}
						if (json.msg.indexOf('验证码输入不正确') > -1) {
							$("#register_captcha").val('').focus();
							//$("#register_captcha").select().val('');
							//$("#register_captcha").focus().val('');
							return;
						}
						if (json.msg.indexOf('帐号已经被注册') > -1) {
							$("#_checkimage").click();
							$("#register_captcha").val('');
							$("#register_email").val('');
							$("#register_account").val('').focus();
							return;
						}
					}
				});
			}
		}
	}), new Ext.Button({
		text : '取消',
		iconCls : 'cancel',
		handler : function() {
			login.registerWindow.close();
		}
	}) ]
});
var events = "beforecopy beforepaste beforedrag contextmenu selectstart drag paste copy cut dragenter";
$("#register_account").bind(events, function(e) {
	if (e && e.preventDefault)
		e.preventDefault();
	else
		window.event.returnValue = false;
	return false;
});
$("#register_email").bind(events, function(e) {
	if (e && e.preventDefault)
		e.preventDefault();
	else
		window.event.returnValue = false;
	return false;
});
$("#register_captcha").bind(events, function(e) {
	if (e && e.preventDefault)
		e.preventDefault();
	else
		window.event.returnValue = false;
	return false;
});
});