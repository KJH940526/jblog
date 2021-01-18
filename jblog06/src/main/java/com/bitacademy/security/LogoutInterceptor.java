package com.bitacademy.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogoutInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		Object authUser = session.getAttribute("authUser");
		System.out.println("authUser ============== " + authUser);
		
		//상대방이 /user/logout으로 들어올경우를 방지!!
		if(authUser != null) {
			//로그아웃 처리!
		session.removeAttribute("authUser");
		session.invalidate();
		}
		response.sendRedirect(request.getContextPath());
		return false;
	}

}
