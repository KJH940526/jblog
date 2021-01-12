package com.bitacademy.jblog.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bitacademy.jblog.service.BlogService;
import com.bitacademy.jblog.vo.BlogVo;
import com.bitacademy.security.Auth;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@RequestMapping({ "", "/{category}", "/{category}/{post}" })
	public String index(
			@PathVariable String id, 
			@PathVariable Optional<Long> category,
			@PathVariable Optional<Long> post, 
			Model model) {

		System.out.println(id +" " + category+" " + post);				
		BlogVo blogVo = blogService.getLogoAndTitle(id);
		
		model.addAttribute("category", category);
		model.addAttribute("blogVo", blogVo);
		
		return "blog/blog-main";
	}
	
	@Auth
	@RequestMapping("/basic")
	public String basic() {
		return "blog/blog-admin-basic";
	}

}
