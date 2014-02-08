package com.authority.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.authority.common.springmvc.DateConvertEditor;
import com.authority.common.utils.WebUtils;
import com.authority.pojo.BaseUsers;
import com.authority.pojo.Criteria;
import com.authority.pojo.ExceptionReturn;
import com.authority.pojo.ExtGridReturn;
import com.authority.pojo.ExtPager;
import com.authority.pojo.ExtReturn;
import com.authority.pojo.Wzsjjcarregister;
import com.authority.service.WzsjjcarregisterService;
import com.authority.web.interseptor.WebConstants;

/**
 * 后台资源、系统菜单相关
 * 
 * @author chenxin
 * @date 2011-10-31 下午10:16:24
 */
@Controller
@RequestMapping("/unionpay")
public class WzsjjUnionpayController {

	private static final Logger logger = LoggerFactory.getLogger(WzsjjUnionpayController.class);
	@Autowired
	private WzsjjcarregisterService wzsjjcarregisterService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	/**
	 * index
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String wzsjjcarregister() {
		return "user/wzsjj_carregister";
	}

	/**
	 * 所有信息
	 */
	@RequestMapping("/all")
	@ResponseBody
	public Object all(ExtPager pager, @RequestParam(required = false) String chep,HttpSession session, HttpServletRequest request) {
		try {
			Criteria criteria = new Criteria();
			// 设置分页信息
			if (pager.getLimit() != null && pager.getStart() != null) {
				criteria.setOracleEnd(pager.getLimit() + pager.getStart());
				criteria.setOracleStart(pager.getStart());
			}
			// 排序信息
			if (StringUtils.isNotBlank(pager.getDir()) && StringUtils.isNotBlank(pager.getSort())) {
				criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
			} else {
				criteria.setOrderByClause(" CHEP asc ");
			}
			if (StringUtils.isNotBlank(chep)) {
				criteria.put("chepLike", chep);
			}
			BaseUsers baseUser = (BaseUsers)session.getAttribute(WebConstants.CURRENT_USER);
			
			criteria.put("isdisplay", "1");
			criteria.put("userid", baseUser.getUserId());
			List<Wzsjjcarregister> list = this.wzsjjcarregisterService.selectByExample(criteria);
			int total = this.wzsjjcarregisterService.countByExample(criteria);
			logger.debug("total:{}", total);
			return new ExtGridReturn(total, list);
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
	
	/**
	 * 短信验证
	 * */
	@RequestMapping("/verify")
	@ResponseBody
	public Object verify(HttpSession session, HttpServletRequest request){
		String chep =request.getParameter("chep");
		String mobilephone = request.getParameter("mobilephone");
		
		if(chep==null||chep.equals("")){
			return new ExtReturn(false, "车牌号不能为空");
		}
		
		if(mobilephone==null||mobilephone.equals("")){
			return new ExtReturn(false, "手机号码不能为空");
		}
		//1、发送远程认证 httpservice 或者 webservice
		
		//2、认证通过,生成四位随机数字，以短信形式通知  mobilephone
		java.util.Random r=new java.util.Random(); 
		//String random = String.valueOf(Integer.parseInt(r.nextDouble()*10000/1));
		Double db = r.nextDouble()*10000 ;
		String verifynum = String.valueOf(db.intValue());
		verifynum = StringUtils.leftPad(verifynum, 4, "0");
		Date now = new Date();
		//session 保存验证码,并记录当前时间戳 过期用
		session.setAttribute("verifynum", verifynum); 
		session.setAttribute("verifydate", now);
		//验证码发送到手机端
		
		return new ExtReturn(true,verifynum);
	}

	/**
	 * 保存系统菜单信息
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Object save(HttpSession session, HttpServletRequest request) {
		try {
			String chep =request.getParameter("chep");
			String mobilephone = request.getParameter("mobilephone");
			
			if(chep==null||chep.trim().equals("")){
				return new ExtReturn(false, "车牌号不能为空");
			}
			
			if(mobilephone==null||mobilephone.equals("")){
				return new ExtReturn(false, "手机号码不能为空");
			}
			//查看短信认证码是否已经过期
			Date now = new Date();
			Date old = (Date) session.getAttribute("verifydate");
			long diff = (now.getTime()-old.getTime())/1000;
			if(diff>300){
				return new ExtReturn(false, "验证码过期,请重新获取");
			}
			//再次发送认证，成功后返回 车辆的信息
			String chelsbm = "LJDLAA12XD0029607";
			String fadjbh= "D5690730";
			
			BaseUsers baseUser = (BaseUsers)session.getAttribute(WebConstants.CURRENT_USER);
			
			Wzsjjcarregister wzsjjcarregister = new Wzsjjcarregister();
			Criteria criteria = new Criteria();
			
			wzsjjcarregister.setChep(chep.toUpperCase());//转成大写
			wzsjjcarregister.setChelsbm(chelsbm.toUpperCase());
			wzsjjcarregister.setFadjbh(fadjbh.toUpperCase());
			wzsjjcarregister.setUserid(baseUser.getUserId());
			wzsjjcarregister.setAddwho(baseUser.getAccount());
			wzsjjcarregister.setAddip(WebUtils.getIpAddr(request));
			
			criteria.put("wzsjjcarregister", wzsjjcarregister);
			String result = this.wzsjjcarregisterService.saveCar(criteria);
			if ("01".equals(result)) {
				return new ExtReturn(true, "保存成功！");
			} else if ("00".equals(result)) {
				return new ExtReturn(false, "保存失败！");
			} else {
				return new ExtReturn(false, result);
			}
			
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}

	/**
	 * 删除该模块
	 */
	@RequestMapping("/del/{wzsjjcarregisterId}")
	@ResponseBody
	public Object delete(@PathVariable String wzsjjcarregisterId,HttpSession session, HttpServletRequest request) {
		try {
			if (StringUtils.isBlank(wzsjjcarregisterId)) {
				return new ExtReturn(false, "车辆主键不能为空！");
			}
			BaseUsers baseUser = (BaseUsers)session.getAttribute(WebConstants.CURRENT_USER);
			
			Wzsjjcarregister record = new Wzsjjcarregister();
			record.setId(wzsjjcarregisterId);			
			record.setIsdisplay("0");			
			
			String result = this.wzsjjcarregisterService.updateByPrimaryKeySelective(record);
			if ("01".equals(result)) {
				return new ExtReturn(true, "删除成功！");
			} else if ("00".equals(result)) {
				return new ExtReturn(false, "删除失败！");
			} else {
				return new ExtReturn(false, result);
			}
		} catch (Exception e) {
			logger.error("Exception: ", e);
			return new ExceptionReturn(e);
		}
	}
}
