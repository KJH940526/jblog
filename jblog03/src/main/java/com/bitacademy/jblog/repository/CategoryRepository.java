package com.bitacademy.jblog.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public void insertCategory(String id, CategoryVo categoryVo) {
		System.out.println("C.B.J.R Category ==> " + categoryVo );
		Map<String, Object> map=new HashMap<>();
		map.put("id", id);
		map.put("name", categoryVo.getName());
		map.put("description", categoryVo.getDesc());
		
		sqlSession.insert("category.insertCategory", map);
	}

}
