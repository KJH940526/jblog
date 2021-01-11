package com.bitacademy.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bitacademy.jblog.service.UserService;
import com.bitacademy.jblog.vo.UserVo;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	//new로 만들지 않은 이유는 새로운 userservice는 의존성이 주입되지 않은 컨테이너 밖에서 만들어지기 떄문에
	// 의미가없다!
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		System.out.println("FSDAFSDASDF");
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		UserVo vo = new UserVo();
		vo.setId(id);
		vo.setPassword(password);
		
		UserVo authUser = userService.getUser(vo);
		System.out.println("C.B.S ===> " + authUser);
		
		if(authUser == null) {
			request.setAttribute("userVo", vo);
			request.getRequestDispatcher("/WEB-INF/views/user/login.jsp").forward(request, response);
			return false;
		}
		
		// 세션처리
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", authUser);
		response.sendRedirect(request.getContextPath());
		
		return false;
	}

}
