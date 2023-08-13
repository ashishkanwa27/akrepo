package com.springmvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springmvc.model.User;
import com.springmvc.service.UserService;


@Controller
public class ContactController {
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void commonDataModel(Model m) {
		m.addAttribute("Header","Ashish Kanwar");
		m.addAttribute("desc","home for programmer"); 
	}
	
	@RequestMapping("/contact")
	public String showForm(Model m) {
	
		return "contact";
	}

	@RequestMapping(path="/processform", method = RequestMethod.POST)
	public String handleForm(@ModelAttribute User user,Model model) {
		
		
//		model.addAttribute("name",userName);
//		model.addAttribute("email",userEmail);
//		model.addAttribute("password",userPassword);
		System.out.println(user);
		
		//model.addAttribute("user",user);
		
		int createdUser=this.userService.createUser(user);
		model.addAttribute("msg","User created with id "+createdUser);
		
		return "success";
	}
	
//	@RequestMapping(path="/processform", method = RequestMethod.POST)
//	public String handleForm(@RequestParam(name="email", required=true) String userEmail,
//			@RequestParam("userName") String userName, 
//			@RequestParam("password") String userPassword, Model model) {
//		
//		User user=new User();
//		user.setEmail(userEmail);
//		user.setUserName(userName);
//		user.setPassword(userPassword);
////		model.addAttribute("name",userName);
////		model.addAttribute("email",userEmail);
////		model.addAttribute("password",userPassword);
//		
//		model.addAttribute("user",user);
//		return "success";
//	}
	
}
