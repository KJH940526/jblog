package com.bitacademy.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.jblog.vo.UserVo;

@Repository
public class UserRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public int insertUser(UserVo userVo) {
		System.out.println("C.B.J.R User ==> " + userVo );
		return sqlSession.insert("user.insertUser", userVo);
	}

	public UserVo findByIdAndPassword(UserVo userVo) {
		System.out.println("C.B.J.R User ==> " + userVo );
		return sqlSession.selectOne("user.findByIdAndPassword", userVo);
	}
}
