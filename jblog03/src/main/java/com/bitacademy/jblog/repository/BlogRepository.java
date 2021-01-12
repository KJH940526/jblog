package com.bitacademy.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bitacademy.jblog.vo.BlogVo;
import com.bitacademy.jblog.vo.CategoryVo;
import com.bitacademy.jblog.vo.PostVo;

@Repository
public class BlogRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	// 처음 만들때!!
	public int insertBlog(BlogVo blogVo) {
		System.out.println("C.B.J.R BlogRepository ==> " + blogVo );

		return sqlSession.insert("blog.insertBlog", blogVo);
	}
	
	// 타이틀!! 로고
	public BlogVo getLogoAndTitle(String id) {
		return sqlSession.selectOne("blog.getLogoAndTitle", id);
	}
	
	// 카테고리 만들때!
	public int insertCategory(CategoryVo categoryVo) {
		return sqlSession.insert("category.insertCategory", categoryVo);
	}
	
	// 카테고리 지울때!
	public void deleteCategory(long no) {
		sqlSession.delete("post.deletePost", no);
		sqlSession.delete("category.deleteCategory", no);
	}
	
	// 카테고리 리스트!
	public List<CategoryVo> getCategoryList(String id) {
		return sqlSession.selectList("category.getCategoryList", id);
	}
	
	// 포스트!!
	public int insertPost(PostVo postVo) {
		return sqlSession.insert("post.insertPost", postVo);
	}
	
	public int deletePost(PostVo postVo) {
		return sqlSession.delete("post.deletePost", postVo);
	}
	
	// 포스트 리스트
	public List<PostVo> getPostList(String id) {
		return sqlSession.selectList("post.getPostList", id);
	}
	
	//포스트 보기
	public PostVo findByNo(Long no){
		return sqlSession.selectOne("post.findByNo", no);
	}
	
	

	
	
	
	
	

}
