package com.bitacademy.jblog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitacademy.jblog.repository.BlogRepository;
import com.bitacademy.jblog.vo.BlogVo;
import com.bitacademy.jblog.vo.CategoryVo;
import com.bitacademy.jblog.vo.PostVo;

@Service
public class BlogService {
	
	private static final String SAVE_PATH = "/jblog-uploads";
	private static final String URL_BASE = "/images";
	
	@Autowired
	private BlogRepository blogRepository;
	
	public void writePost(PostVo postVo) {
		System.out.println(postVo);
		blogRepository.insertPost(postVo);
	}
	
	public List<CategoryVo> getCategoryList(String id){
		return blogRepository.getCategoryList(id);
	}
	
	public void writeCategory(String id, CategoryVo categoryVo) {
		System.out.println(id + " " +categoryVo);
		blogRepository.insertCategory(categoryVo);	
	}

	public List<PostVo> getPostList(String id) {
		return blogRepository.getPostList(id);
	}

	public BlogVo getLogoAndTitle(String id) {
		return blogRepository.getLogoAndTitle(id);
	}
	
	
}
