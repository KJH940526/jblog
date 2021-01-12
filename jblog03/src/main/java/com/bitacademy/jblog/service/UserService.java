package com.bitacademy.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bitacademy.jblog.repository.BlogRepository;
import com.bitacademy.jblog.repository.CategoryRepository;
import com.bitacademy.jblog.repository.UserRepository;
import com.bitacademy.jblog.vo.UserVo;
import com.bitacademy.jblog.vo.BlogVo;
import com.bitacademy.jblog.vo.CategoryVo;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Transactional
	public void join(UserVo userVo) {
		
		//회원가입!!
		userRepository.insertUser(userVo);

		//블로그 생성!
		BlogVo blogVo = new BlogVo();
		blogVo.setId(userVo.getId());
		blogVo.setTitle(userVo.getName() + " 블로그");
		blogVo.setLogo("/assets/images/spring-logo.jpg");
		
		blogRepository.insertBlog(blogVo);
		
		//카테고리 생성!
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setName( "미분류" );
		categoryVo.setDesc("");
		categoryVo.setId(userVo.getId());
		
		categoryRepository.insertCategory(categoryVo);	
	}
	
	public UserVo getUser(UserVo userVo) {
		return userRepository.findByIdAndPassword(userVo);
	}
}
