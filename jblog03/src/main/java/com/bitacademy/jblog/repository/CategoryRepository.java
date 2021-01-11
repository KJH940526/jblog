package com.bitacademy.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public int insertCategory(CategoryVo categoryVo) {
		System.out.println("C.B.J.R Category ==> " + categoryVo );
		return sqlSession.insert("category.insertCategory", categoryVo);
	}

}