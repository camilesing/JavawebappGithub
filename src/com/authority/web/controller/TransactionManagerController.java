package com.authority.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.authority.common.springmvc.DateConvertEditor;
import com.authority.service.TransactionManagerService;


@Controller
@RequestMapping("/transactionmanager")
public class TransactionManagerController {
	
	private static final Logger logger = LoggerFactory.getLogger(TransactionManagerController.class);
	
	@Autowired
	private TransactionManagerService transactionManagerService;
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateConvertEditor());
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
	
	@RequestMapping("/action")
	@ResponseBody
	public Object action(HttpSession session, HttpServletRequest request) {
		try {
			
			transactionManagerService.action_trans_1();
			//transactionManagerService.action_trans_2();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "success";
	}
}
