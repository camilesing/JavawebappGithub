package com.authority.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.authority.common.springmvc.DateConvertEditor;
import com.authority.common.utils.WebUtils;
import com.authority.pojo.BaseRoles;
import com.authority.pojo.BaseUsers;
import com.authority.pojo.BaseUsersWeixin;
import com.authority.pojo.Criteria;
import com.authority.pojo.ExceptionReturn;
import com.authority.pojo.ExtReturn;
import com.authority.pojo.PdaReturn;
import com.authority.pojo.Tree;
import com.authority.service.BaseModulesService;
import com.authority.service.BaseRolesService;
import com.authority.service.BaseUsersService;
import com.authority.service.BaseUsersWeixinService;
import com.authority.web.interseptor.WebConstants;
import com.google.code.kaptcha.Constants;

/**
 * 用户登录相关
 * 
 * @author chenxin
 * @date 2011-10-20 上午11:45:00
 */
@Controller
public class LoginController {  

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private BaseUsersService baseUsersService;
	@Autowired
	private BaseModulesService baseModulesService;
	@Autowired
	private BaseRolesService baseRolesService;
	@Autowired
	private BaseUsersWeixinService baseUsersWeixinService;
	
	/** 限制时间 */
	@Value("${limit.millis:3600000}") 
	private Long millis;

	@SuppressWarnings("unused")
	@Autowired
	private MessageSource messageSource;
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Resource(name="njdbcTemplate")
	private NamedParameterJdbcTemplate njdbcTemplate;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	/**
	 * 退出登录
	 */
	@RequestMapping("/logout")
	@ResponseBody
	public Object logout(HttpSession session, Locale locale) {
		try {
			Object weixinid = session.getAttribute("weixinid");
			weixinid = weixinid==null?"":weixinid;
			String msg = "退出系统成功!";
			if(!weixinid.toString().equalsIgnoreCase(""))
				msg = weixinid.toString() ;
			session.removeAttribute(WebConstants.CURRENT_USER);
			session.invalidate();
			return new ExtReturn(true,msg);
			
			// return new ExtReturn(true,
			// messageSource.getMessage("common.login", null, locale));
			// return new ExtReturn(true,
			// messageSource.getMessage("common.logout", new
			// Object[]{"超级管理员","成功！"}, locale));
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}

	/**
	 * 用户菜单treeMenu的数据
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/treeMenu")
	@ResponseBody
	public Object treeMenu(HttpSession session, HttpServletResponse response) {
		try {
			BaseUsers user = (BaseUsers) session.getAttribute(WebConstants.CURRENT_USER);
			// 得到的是根菜单
			Tree tree = this.baseModulesService.selectModulesByUser(user);
			// 返回根菜单下面的子菜单
			return tree.getChildren();
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}

	/**
	 * 用户登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(@RequestParam String account, @RequestParam String password, HttpSession session, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(account)) {
				return new ExtReturn(false, "帐号不能为空！");
			}
			if (StringUtils.isBlank(password)) {
				return new ExtReturn(false, "密码不能为空！");
			}
			Criteria criteria = new Criteria();
			criteria.put("account", account);
			criteria.put("passwordIn", password);
			criteria.put("loginip", this.getIpAddr(request));
			String result = this.baseUsersService.selectByBaseUser(criteria);
			if ("01".equals(result)) {
				BaseUsers baseUser = (BaseUsers) criteria.get("baseUser");
				session.setAttribute(WebConstants.CURRENT_USER, baseUser);
				session.setAttribute("username", "admin");
				
				
				String user_role="";
				String sql_role="select A.ROLE_NAME from BASE_ROLES a,BASE_USER_ROLE b where A.ROLE_ID=B.ROLE_ID and B.USER_ID=?";
				Object[] args={baseUser.getUserId()};
				if(null==(String)jdbcTemplate.queryForObject(sql_role, args, java.lang.String.class))
					;
				else
					user_role=(String)jdbcTemplate.queryForObject(sql_role, args, java.lang.String.class);
				
				session.setAttribute(WebConstants.CURRENT_USER_ROLE, user_role);				
				
				logger.info("{}登陆成功", baseUser.getRealName());
				return new ExtReturn(true, "success");
			} else if ("00".equals(result)) {
				return new ExtReturn(false, "用户名或者密码错误!");
			} else {
				return new ExtReturn(false, result);
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
	
	/**
	 * 用户登录
	 */
	@RequestMapping(value = "/login_portal", method = RequestMethod.POST)
	@ResponseBody
	public Object login_portal(HttpSession session, HttpServletRequest request) {
		try {
			String username = request.getParameter("account");
			String password = request.getParameter("password");
			System.out.println("username:"+username+" ,password:"+password);
			
			String query = "select id,truename from fdfair.users where name=:username and password=:password ";
			String truename = "1",userid="1";
			Boolean success = false;
			/*Map<String,Object> param = new HashMap<String, Object>();
			param.put("username", username);
			param.put("password", password);
			
			List<Map<String,Object>> list = njdbcTemplate.queryForList(query,param);
			if(list.size()>0){
				Map<String,Object> map = list.get(0);
				truename = map.get("truename").toString();
				userid = map.get("id").toString();
				success = true ;
			}else{
				success = false;
			}*/
			success=true;
			
			
			session.setAttribute("userid", userid);
			session.setAttribute("truename", truename);
			
			return new ExtReturn(success, truename);
			
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
		
	}
	
	
	/**
	 * bosapp 登陆
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login_bosapp", method = RequestMethod.POST)
	@ResponseBody
	public Object login_bosapp(HttpSession session, HttpServletRequest request){
		Boolean success=false ;
		String msg = "";
		try {
			Criteria criteria = new Criteria();
			String result ="";
			String weixinid = request.getParameter("weixinid");
			String quick  = request.getParameter("quick");
			quick = quick==null?"":quick;
			weixinid = weixinid==null?"":weixinid;
			Map<String,Object> Param = new HashMap<String, Object>();
			Param.put("WEIXINID", weixinid);
			
//			System.out.println("weixinid:"+weixinid+" quick:"+quick);
			
			if(quick.equalsIgnoreCase("Y")){
				//1.根据 weixinid 查询所判定的用户
				//读取绑定用户
				String query = "select count(*) from HENLO_WEIXINUSER a where a.weixinid = :WEIXINID and exists(" +
						"select 'x' from users b where a.userid= b.email and b.webapp='Y') and a.isactive='Y' ";
				
				if(njdbcTemplate.queryForInt(query,Param)>0)
					result ="01";
				if ("01".equals(result)) {
					session.setAttribute(WebConstants.CURRENT_USER, weixinid);
					session.setAttribute("weixinid", weixinid);
					
					query = "select max(C_CUSTOMERUP_ID) from USERS a where exists(" +
							"select 'x' from HENLO_WEIXINUSER b where a.email = b.userid and b.isactive='Y' ) and webapp='Y' ";				
					session.setAttribute("C_CUSTOMERUP_ID", jdbcTemplate.queryForObject(query, String.class));
					
					query = "select max(C_CUSTOMER_ID) from USERS a where exists(" +
							"select 'x' from HENLO_WEIXINUSER b where a.email = b.userid and b.isactive='Y' ) and webapp='Y' ";			
					session.setAttribute("C_CUSTOMER_ID", jdbcTemplate.queryForObject(query, String.class));

					return new ExtReturn(true, "success");
					
				}else{
					return new ExtReturn(false, "快速登录失败");
				}
			}else{
				String account = request.getParameter("account");
				String password= request.getParameter("password");
				
				//用户验证
				if (StringUtils.isBlank(account)) {
					return new ExtReturn(false, "帐号不能为空！");
				}
				if (StringUtils.isBlank(password)) {
					return new ExtReturn(false, "密码不能为空！");
				}
				WebUtils webUtils = new WebUtils();
				String login_email = webUtils.readValue("config/others/config.properties","lne.login_email");
				
				account = account+login_email;
				String query = "select count(*) from USERS where email='"+account+"' and PASSWORDHASH='"+password+"' and WEBAPP='Y' ";
				
				if(jdbcTemplate.queryForInt(query)>0)
					result ="01";
				
				if ("01".equals(result)) {
					session.setAttribute(WebConstants.CURRENT_USER, account);
					
					query = "select C_CUSTOMERUP_ID from USERS where email='"+account+"'";				
					session.setAttribute("C_CUSTOMERUP_ID", jdbcTemplate.queryForObject(query, String.class));
					
					query = "select C_CUSTOMER_ID from USERS where email='"+account+"'";				
					session.setAttribute("C_CUSTOMER_ID", jdbcTemplate.queryForObject(query, String.class));
					
					//删除原绑定用户，重新绑定
					if(!weixinid.equals("")){
						String delete = "delete from HENLO_WEIXINUSER a where a.weixinid = :WEIXINID ";
						njdbcTemplate.update(delete, Param);
						
						String insert = "insert into HENLO_WEIXINUSER(ID,USERID,WEIXINID,ISACTIVE) SELECT SYS_GUID(),:USERID,:WEIXINID,'Y' FROM DUAL ";
						Param.put("USERID", account);
						
						njdbcTemplate.update(insert, Param);
						
						session.setAttribute("weixinid", weixinid);
					}
					//bos
					query = "select a.id,a.C_STORE_ID||';'||b.Name Store  " +
							"from USERS a " +
							"left join C_STORE b on a.C_STORE_ID = b.ID " +
							"where a.email ='"+account+"'";
					List<Map<String,Object>> list = jdbcTemplate.queryForList(query);
					Map<String,Object> map =  list.get(0);
					
					return new ExtReturn(true, "success");
					
				} else if ("00".equals(result)) {
					return new ExtReturn(false, "用户名或密码错误！");
				} else {
					return new ExtReturn(false, result);
				}
				
			}			
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ExtReturn(success, msg);
		}
		
	}
	
	/**
	 * 用户绑定
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login_bosapp_bd", method = RequestMethod.POST)
	@ResponseBody
	public Object login_bosapp_bd(HttpSession session, HttpServletRequest request){
		Boolean success=false ;
		String msg = "绑定失败";
		try {
			Criteria criteria = new Criteria();
			String result ="";
			String weixinid = request.getParameter("weixinid");
			String code = request.getParameter("code");
			if(weixinid!=null){
				//1.根据 weixinid 查询所判定的用户
				criteria.put("weixinid", weixinid);
				criteria.put("isdisplay", "1");
				if(this.baseUsersWeixinService.countByExample(criteria)>0){
					msg = "已经绑定过";
					success = true ;
				}else{
					String[] code_split = code.split("#");
					if(code_split.length==2){
						String account = code_split[0];
						String password = code_split[1];
						//password md5 加密
						password  = DigestUtils.md5Hex(password);
						criteria.put("account", account);
						criteria.put("passwordIn", password);
						criteria.put("loginip", this.getIpAddr(request));
						result = this.baseUsersService.selectByBaseUser(criteria);
						if ("01".equals(result)) {
							BaseUsers baseUser = (BaseUsers) criteria.get("baseUser");
							//插入到  baseUsersWeixin
							BaseUsersWeixin rdI_baseUsersWeixin = new BaseUsersWeixin();
							rdI_baseUsersWeixin.setAddip(getIpAddr(request));
							rdI_baseUsersWeixin.setIsdisplay("1");
							rdI_baseUsersWeixin.setUserId(baseUser.getUserId());
							rdI_baseUsersWeixin.setWeixinid(weixinid);
							if(baseUsersWeixinService.insertSelective(rdI_baseUsersWeixin)>0){
								msg = "绑定成功";
								success = true ;
							}else{
								msg = "绑定失败";
							}							
						}else if ("00".equals(result)) {
							msg = "用户名或者密码错误!";							
						}else{
							msg = result;							
						}
						
					}
					
				}
				
			}
			
			return new ExtReturn(success, msg);			
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ExtReturn(success, msg);
		}
		
	}
	
	/**
	 * 取消用户绑定
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login_bosapp_bdqx", method = RequestMethod.POST)
	@ResponseBody
	public Object login_bosapp_bdqx(HttpSession session, HttpServletRequest request){
		Boolean success=false ;
		String msg = "取消绑定失败";
		try {
			Criteria criteria = new Criteria();
			String result ="";
			String weixinid = request.getParameter("weixinid");
			if(weixinid!=null){
				//1.根据 weixinid 查询所判定的用户
				criteria.put("weixinid", weixinid);
				criteria.put("isdisplay", "1");
				List<BaseUsersWeixin> list_baseUsersWeixin = baseUsersWeixinService.selectByExample(criteria);
				if(list_baseUsersWeixin.size()>0){
					//取消绑定
					BaseUsersWeixin rdU_baseUsersWeixin = list_baseUsersWeixin.get(0);
					rdU_baseUsersWeixin.setIsdisplay("0");
					if(baseUsersWeixinService.updateByPrimaryKeySelective(rdU_baseUsersWeixin)>0){
						success = true ;
						msg = "取消绑定成功"; 
					}else{
						success = false ;
						msg = "取消绑定失败"; 
					}
				}else{
					success = false ;
					msg = "未绑定用户"; 
				}
				
			}
			
			return new ExtReturn(success, msg);			
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ExtReturn(success, msg);
		}
		
	}
	
	
	/**
	 * 用户登录_PDA
	 */
	@RequestMapping(value = "/login_pda", method = RequestMethod.POST)
	@ResponseBody
	public Object login_pda(@RequestParam String account, @RequestParam String password, HttpSession session, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(account)) {
				return new PdaReturn("N", "帐号不能为空！");
			}
			if (StringUtils.isBlank(password)) {
				return new PdaReturn("N", "密码不能为空！");
			}
			Criteria criteria = new Criteria();
			criteria.put("account", account);
			criteria.put("passwordIn", password);
			criteria.put("loginip", this.getIpAddr(request));
			String result = this.baseUsersService.selectByBaseUser(criteria);
			if ("01".equals(result)) {
				BaseUsers baseUser = (BaseUsers) criteria.get("baseUser");
				session.setAttribute(WebConstants.CURRENT_USER, baseUser);
				
				String user_role="";
				String sql_role="select A.ROLE_NAME from BASE_ROLES a,BASE_USER_ROLE b where A.ROLE_ID=B.ROLE_ID and B.USER_ID=?";
				Object[] args={baseUser.getUserId()};
				if(null==(String)jdbcTemplate.queryForObject(sql_role, args, java.lang.String.class))
					;
				else
					user_role=(String)jdbcTemplate.queryForObject(sql_role, args, java.lang.String.class);
				
				session.setAttribute(WebConstants.CURRENT_USER_ROLE, user_role);				
				
				logger.info("{}登陆成功", baseUser.getRealName());
				return new PdaReturn("Y","登陆成功");
			} else if ("00".equals(result)) {
				return new PdaReturn("N","用户名或者密码错误!");
			} else {
				return new PdaReturn("N",result);
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
	
	/**
	 * 华峰用户登录_PDA
	 */
	@RequestMapping(value = "/login_winceapp", method = RequestMethod.POST)
	@ResponseBody
	public Object login_winceapp(@RequestParam String username, @RequestParam String password, HttpSession session, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(username)) {
				return new PdaReturn("N", "帐号不能为空！");
			}
			if (StringUtils.isBlank(password)) {
				return new PdaReturn("N", "密码不能为空！");
			}
			Criteria criteria = new Criteria();
			criteria.put("account", username);
			criteria.put("passwordIn", password);
			criteria.put("loginip", this.getIpAddr(request));
			String result = this.baseUsersService.selectByBaseUser(criteria);
			if ("01".equals(result)) {
				BaseUsers baseUser = (BaseUsers) criteria.get("baseUser");
				session.setAttribute(WebConstants.CURRENT_USER, baseUser);
				
				String user_role="";
				String sql_role="select A.ROLE_NAME from BASE_ROLES a,BASE_USER_ROLE b where A.ROLE_ID=B.ROLE_ID and B.USER_ID=?";
				Object[] args={baseUser.getUserId()};
				if(null==(String)jdbcTemplate.queryForObject(sql_role, args, java.lang.String.class))
					;
				else
					user_role=(String)jdbcTemplate.queryForObject(sql_role, args, java.lang.String.class);
				
				session.setAttribute(WebConstants.CURRENT_USER_ROLE, user_role);				
				
				logger.info("{}登陆成功", baseUser.getRealName());
				//获取配置信息
				String  message = "",query="" ;
				List<Map<String,Object>> list = null;
				//1 . users
				query = "select account,REAL_NAME,ROLE_NAME,ROLE_DESC from v_base_users where account='"+username+"'";
				list  = jdbcTemplate.queryForList(query);
				Map<String,Object> map = list.get(0);
				message = "users,"+map.get("ACCOUNT").toString()+","+map.get("REAL_NAME").toString()+","+password+","+map.get("ROLE_NAME").toString()+","+map.get("ROLE_DESC").toString()+";";
				
				//2 . role_module 角色（部门） 权限
				query = "select ROLE_NAME, PARENT_MODULE_URL, PARENT_MODULE_NAME, MODULE_URL, MODULE_NAME " +
						"from v_role_module where ROLE_NAME ='"+map.get("ROLE_NAME").toString()+"'" ;
				list.clear();
				list = jdbcTemplate.queryForList(query);
				for (Map<String, Object> maplist : list) {
					message = message +"role_module,"+
							maplist.get("ROLE_NAME").toString()+","+
							maplist.get("PARENT_MODULE_URL").toString()+","+
							maplist.get("PARENT_MODULE_NAME").toString()+","+
							maplist.get("MODULE_URL").toString()+","+
							maplist.get("MODULE_NAME").toString()+";";
				}
				
				//3 . requrl 请求地址信息
				query = "select DISPLAY_FIELD, VALUE_FIELD " +
						"from V_BASE_FIELDS " ;
				list.clear();
				list = jdbcTemplate.queryForList(query);
				for (Map<String, Object> maplist : list) {
					message = message +"requrl,"+
							maplist.get("DISPLAY_FIELD").toString()+","+
							maplist.get("VALUE_FIELD").toString()+";";
				}
				return new PdaReturn("Y",StringUtils.removeEnd(message, ";"));
			} else if ("00".equals(result)) {
				return new PdaReturn("N","用户名或者密码错误!");
			} else {
				return new PdaReturn("N",result);
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}

	/**
	 * 取得客户端真实ip
	 * 
	 * @param request
	 * @return 客户端真实ip
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		logger.debug("1- X-Forwarded-For ip={}", ip);
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			logger.debug("2- Proxy-Client-IP ip={}", ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			logger.debug("3- WL-Proxy-Client-IP ip={}", ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			logger.debug("4- HTTP_CLIENT_IP ip={}", ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			logger.debug("5- HTTP_X_FORWARDED_FOR ip={}", ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			logger.debug("6- getRemoteAddr ip={}", ip);
		}
		logger.info("finally ip={}", ip);
		return ip;
	}

	/**
	 * 转到找回用户密码页面
	 */
	@RequestMapping(value = "/findpwd", method = RequestMethod.GET)
	public String findpwd() {
		return "user/findpwd";
	}
	
	/**
	 * 转到找回用户密码页面
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register() {
		return "user/register";
	}
	
	/**
	 * 找回用户密码
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Object saveregister(BaseUsers user,@RequestParam String captcha,HttpSession session) {
		try {
			if (StringUtils.isBlank(captcha)) {
				return new ExtReturn(false, "验证码不能为空！");
			}
			Object sessionCaptcha =  session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
			if(null==sessionCaptcha){
				return new ExtReturn(false, "验证码已经失效!请重新输入新的验证码！");
			}
			if (!captcha.equalsIgnoreCase((String)sessionCaptcha)) {
				return new ExtReturn(false, "验证码输入不正确,请重新输入！");
			}
			//移除验证码，不能用同一个验证码重复提交来试探密码等，不要像铁道部的网站那样
			session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
			if (user == null) {
				return new ExtReturn(false, "用户不能为空！");
			}
			if (StringUtils.isBlank(user.getAccount())) {
				return new ExtReturn(false, "帐号不能为空！");
			}
			if (StringUtils.isBlank(user.getEmail())) {
				return new ExtReturn(false, "注册邮箱不能为空！");
			}
			Criteria criteria = new Criteria();
			//验证用户是否存在
			criteria.put("account", user.getAccount());
			int count = this.baseUsersService.countByExample(criteria);
			if(count>0){
				return new ExtReturn(false, "帐号已经被注册！");
			}
			//普通用户的角色信息
			Collection<String> roleIds = new ArrayList<String>();			
			Criteria criteria_role = new Criteria();
			criteria_role.put("roleName", "普通账户");
			List<BaseRoles> list_roles = this.baseRolesService.selectByExample(criteria_role);
			for (BaseRoles baseRoles : list_roles) {
				roleIds.add(baseRoles.getRoleId());
			}
			//插入用户
			criteria.clear();			
			criteria.put("user", user);
			user.setPassword(DigestUtils.md5Hex(user.getPassword()));
			criteria.put("roleIds", roleIds);
			String result = this.baseUsersService.saveUser(criteria);
			
			if ("01".equals(result)) {
				return new ExtReturn(true, "用户注册成功");
			} else if ("00".equals(result)) {
				return new ExtReturn(false, "注册失败,请稍后重试！");
			} else {
				return new ExtReturn(false, result);
			}
			
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}

	/**
	 * 找回用户密码
	 */
	@RequestMapping(value = "/findpwd", method = RequestMethod.POST)
	@ResponseBody
	public Object saveFindpwd(BaseUsers user,@RequestParam String captcha,HttpSession session) {
		try {
			if (StringUtils.isBlank(captcha)) {
				return new ExtReturn(false, "验证码不能为空！");
			}
			Object sessionCaptcha =  session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
			if(null==sessionCaptcha){
				return new ExtReturn(false, "验证码已经失效!请重新输入新的验证码！");
			}
			if (!captcha.equalsIgnoreCase((String)sessionCaptcha)) {
				return new ExtReturn(false, "验证码输入不正确,请重新输入！");
			}
			//移除验证码，不能用同一个验证码重复提交来试探密码等，不要想铁道部的网站那样
			session.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
			if (user == null) {
				return new ExtReturn(false, "用户不能为空！");
			}
			if (StringUtils.isBlank(user.getAccount())) {
				return new ExtReturn(false, "帐号不能为空！");
			}
			if (StringUtils.isBlank(user.getEmail())) {
				return new ExtReturn(false, "注册邮箱不能为空！");
			}
			Criteria criteria = new Criteria();
			criteria.put("user", user);
			String result = this.baseUsersService.findPassword(criteria);
			if ("01".equals(result)) {
				return new ExtReturn(true, "邮件发送成功！请登录注册邮箱查收！");
			} else if ("00".equals(result)) {
				return new ExtReturn(false, "邮件发送失败！");
			} else {
				return new ExtReturn(false, result);
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}

	/**
	 * 找回用户密码时的重新设置密码的页面
	 */
	@RequestMapping(value = "/resetpwd/{token}/{userId}", method = RequestMethod.GET)
	public String resetpwd(@PathVariable String token, @PathVariable String userId, Model model) {
		BaseUsers user = this.baseUsersService.selectByPrimaryKey(userId);
		if (user == null || !user.getPassword().equals(token.toLowerCase()) || compareTo(user.getLastLoginTime())) {
			model.addAttribute("error", "链接已经失效！");
			return "user/resetpwd";
		}
		model.addAttribute("t", token);
		model.addAttribute("u", userId);
		return "user/resetpwd";
	}

	/**
	 * 找回用户密码时的重新设置密码的页面
	 */
	@RequestMapping(value = "/resetpwd", method = RequestMethod.POST)
	public String resetpwdSave(@RequestParam String u, @RequestParam String t, @RequestParam String newpwd, @RequestParam String renewpwd,
			Model model) {
		try {
			model.addAttribute("t", t);
			model.addAttribute("u", u);
			if (StringUtils.isBlank(u)) {
				model.addAttribute("msg", "密码修改失败！");
				return "user/resetpwd";
			}
			if (StringUtils.isBlank(t)) {
				model.addAttribute("msg", "密码修改失败！");
				return "user/resetpwd";
			}
			if (StringUtils.isBlank(newpwd)) {
				model.addAttribute("msg", "新密码不能为空！");
				return "user/resetpwd";
			}
			if (StringUtils.isBlank(renewpwd)) {
				model.addAttribute("msg", "确认密码不能为空！");
				return "user/resetpwd";
			}
			if (!renewpwd.equals(newpwd)) {
				model.addAttribute("msg", "新密码和确认密码输入不一致！");
				return "user/resetpwd";
			}
			Criteria criteria = new Criteria();
			criteria.put("token", t);
			criteria.put("userId", u);
			criteria.put("password", newpwd);
			String result = this.baseUsersService.updatePassword(criteria);
			if ("01".equals(result)) {
				model.addAttribute("msg", "密码修改成功！请重新登录");
			} else if ("00".equals(result)) {
				model.addAttribute("msg", "密码修改失败！");
			} else {
				model.addAttribute("msg", result);
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			model.addAttribute("msg", e.getMessage());
		}
		return "user/resetpwd";
	}

	/**
	 * 日期比较
	 * 
	 * @param date
	 * @return
	 */
	private boolean compareTo(Date date) {
		Calendar c = Calendar.getInstance();
		long now = c.getTimeInMillis();
		c.setTime(date);
		long lastly = c.getTimeInMillis();
		// 60分钟1000*60*60;
		return (now - lastly) >= millis;
	}
}
