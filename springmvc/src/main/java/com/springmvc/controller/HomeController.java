package com.springmvc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class HomeController {

	@GetMapping("/home")
	
	public String hello(Model model) {
		System.out.println("this is home url");
		
		model.addAttribute("name","Ashish");
		
		return "index";
	}
	 
	@RequestMapping("/help")
	public ModelAndView help() {
		System.out.println("this is help controller");
		//creating modelAndView
		ModelAndView modelAndView=new ModelAndView();
		
		//setting the data
		modelAndView.addObject("name","Uttam");
		
		List<Integer> list=new ArrayList<Integer>();
		list.add(12);
		list.add(121);
		list.add(12121);
		
		modelAndView.addObject("marks",list);
		//setting the view name
		modelAndView.setViewName("help");
		
		return modelAndView;
	}
	
}
