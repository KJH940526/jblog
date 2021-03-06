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
		
		Long categoryNo = 0L;
		Long postNo = 0L;
		
		if( post.isPresent() ) {
			postNo = post.get();
			categoryNo = category.get();
		} else if( category.isPresent() ){
			postNo = post.get();
		}

		System.out.println("-------------"+ categoryNo);
		System.out.println("-------------"+ postNo);
		
		BlogVo blogVo = blogService.getLogoAndTitle(id);
		List<CategoryVo> categoryList =  blogService.getCategoryList(id);
		
		System.out.println(categoryList);
		List<PostVo> postList = blogService.getPostList(id);
		PostVo postVo = blogService.getPost(postNo);
		
	
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("postList", postList);
		model.addAttribute("postVo", postVo);
		
		
		
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
		System.out.println("-------------" + authUser + "---------");
		BlogVo blogVo = blogService.getLogoAndTitle(id);
		List<CategoryVo> categoryList =  blogService.getCategoryList(id);
		System.out.println(categoryList);
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
		System.out.println("-------------" + authUser + "---------");
		blogService.writeCategory(id,categoryVo);
		
		return "redirect:/"+id+"/category";
	}
	
	@RequestMapping(value="/category/delete/{no}", method=RequestMethod.GET)
	public String delete(@PathVariable String id,@PathVariable Long no) {
		
		blogService.deleteCategory(no);
		
		return "redirect:/"+id+"/category";
	}
	
	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(
			@AuthUser UserVo authUser,
			@PathVariable String id,
			Model model
			) {
		if(authUser.getId().equals(id) == false){
			return "redirect:/" + authUser.getId(); 
		}
		System.out.println(authUser + "---------");
		BlogVo blogVo = blogService.getLogoAndTitle(id);
		model.addAttribute("blogVo", blogVo);
		
		//카테고리 리스트
		List<CategoryVo> categoryList =  blogService.getCategoryList(id);
		model.addAttribute("categoryList", categoryList);
		
		return "blog/blog-admin-write";
	}
	
	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(
			@AuthUser UserVo authUser,
			@PathVariable String id,
			PostVo postVo
			) {
		if(authUser.getId().equals(id) == false){
			return "redirect:/" + authUser.getId(); 
		}
		System.out.println("=====================" + postVo);
		blogService.writePost(postVo);
		
		return "redirect:/"+id;
	}
	

}
