package com.bitacademy.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bitacademy.jblog.service.BlogService;
import com.bitacademy.jblog.vo.BlogVo;
import com.bitacademy.jblog.vo.CategoryVo;
import com.bitacademy.jblog.vo.PostVo;
import com.bitacademy.jblog.vo.UserVo;
import com.bitacademy.security.Auth;
import com.bitacademy.security.AuthUser;

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
		
		List<CategoryVo> categoryList =  blogService.getCategoryList(id);	
		List<PostVo> postList = blogService.getPostList(id);
		
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("postList", postList);
		
		
		return "blog/blog-main";
	}
	
	@Auth
	@RequestMapping("/basic")
	public String basic(
			@AuthUser UserVo authUser,
			@PathVariable String id,
			Model model
			) {
		if(authUser.getId().equals(id) == false){
			return "redirect:/" + authUser.getId(); 
		}
		BlogVo blogVo = blogService.getLogoAndTitle(id);
		model.addAttribute("blogVo", blogVo);
		
		return "blog/blog-admin-basic";
	}
	
	@Auth
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String category(
			@AuthUser UserVo authUser,
			@PathVariable String id,
			Model model
			) {
		if(authUser.getId().equals(id) == false){
			return "redirect:/" + authUser.getId(); 
		}
		BlogVo blogVo = blogService.getLogoAndTitle(id);
		List<CategoryVo> categoryList =  blogService.getCategoryList(id);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("blogVo", blogVo);
		
		return "blog/blog-admin-category";
	}
	
	@Auth
	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public String category(
			@AuthUser UserVo authUser,
			@PathVariable String id,
			CategoryVo categoryVo
			) {
		if(authUser.getId().equals(id) == false){
			return "redirect:/" + authUser.getId(); 
		}
		
		blogService.writeCategory(id,categoryVo);
		
		return "redirect:/"+id+"/category";
	}
	
	@RequestMapping(value="/category/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable String id,@PathVariable Long no) {
		
		blogService.deleteCategory(no);
		
		return "redirect:/"+id+"/category";
	}
	
	@Auth
	@RequestMapping("/write")
	public String write(
			@AuthUser UserVo authUser,
			@PathVariable String id,
			Model model
			) {
		if(authUser.getId().equals(id) == false){
			return "redirect:/" + authUser.getId(); 
		}
		BlogVo blogVo = blogService.getLogoAndTitle(id);
		model.addAttribute("blogVo", blogVo);
		
		return "blog/blog-admin-write";
	}

}
