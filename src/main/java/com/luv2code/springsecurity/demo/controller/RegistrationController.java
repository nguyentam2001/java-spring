package com.luv2code.springsecurity.demo.controller;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.luv2code.springsecurity.demo.entity.User;
import com.luv2code.springsecurity.demo.service.UserService;
import com.luv2code.springsecurity.demo.user.CrmUser;

@Controller
@RequestMapping("/register")
public class RegistrationController {
	@Autowired
	private UserService userService;
	
	private Logger logger= Logger.getLogger(getClass().getName());
	
	
	@InitBinder
	//dùng để cut khoản trắng của chuỗi
	public void initBinber(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor= new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("/showRegistrationForm")
	public String showLogginPage(Model theModel) {
		theModel.addAttribute("crmUser",new CrmUser());
		return "registration-form";
	}
	
	@PostMapping("/processRegistrationForm")
	public String processRegistrationForm(@Valid @ModelAttribute("crmUser")CrmUser crmUser,
											BindingResult theBindingResult,
											Model theModel) {
		String userName= crmUser.getUserName();
		logger.info("Processing registration form for: " + userName);
		//from validation
		if(theBindingResult.hasErrors()) {
			return "registration-form";
		}
		//check database if user is exist
		User existing=userService.findByUserName(userName); 
		if(existing!=null) {
			theModel.addAttribute("crmUser", new CrmUser());
			theModel.addAttribute("registrationError", "User is realy exist");
			 
			logger.warning("User is realy exist");
			return "registration-fom";
		}
		//create user account
		userService.save(crmUser);
		  logger.info("Successfully created user: " + userName);
		  return "registration-confirmation";
	}
	
	
}
