package com.bitacademy.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bitacademy.jblog.vo.UserVo;

public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		
		// 1. handler 종류 확인!!
		if(handler instanceof HandlerMethod == false) {
			// DefaultServletHanlder가 처리하는 경우(보통, assets의 정적 자원 접근)
			return true;
		}
		
		//2. casting
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		//3. Handler Method에 @Auth 받아오기!!
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		System.out.println("c.b.s.AuthInterceptor1 ==> "+auth);
		
		//4. Method에 @Auth가 안 붙어 있는 경우, Type(Class)에 붙어 있는지 확인한다.(과제)
		 if(auth == null){
			 auth = handlerMethod.getMethod().getDeclaringClass().getAnnotation(Auth.class);			 
			 System.out.println("c.b.s.AuthInterceptor2 ==> "+auth); //admin
		 }
		
		// 5. Method나 Type(Class)에 @Auth가 없는경우
		if(auth == null) {
			System.out.println("c.b.s.AuthInterceptor3 ==> "+auth);  //방명록
			return true;
		}
		
		// 6. @Auth가 붙어 있기 때문에!! 인증(Authentification) 여부 확인하는 과정
		HttpSession session = request.getSession();
		if(session == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		System.out.println("----------c.b.s.AuthInterceptor authUser-------6번==>" + authUser);
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}  
		
		// 과제!
		// 7. 권한(Authorization) 체크를 위해서 @Auth의 role을 가져오기("USER","ADMIN")!!
		String role = auth.value();
		System.out.println("-----------c.b.s.AuthInterceptor role--------7번==> " + role);
		
		// 8. @Auth의 role이 "USER"인 경우에는 authUser의 role이 "USER", "ADMIN" 상관없음
		if("USER".equals(role)) {
			return true;
		}
		
//		// 9. @Auth의 role이 "ADMIN"인 경우에는 authUser가 "ADMIN" 이어야 한다!!
//		System.out.println("9번 auth : "+auth);
//		System.out.println("9번 role : "+role);
//		System.out.println("------------------- 9번" + authUser.getRole());
//		if(!authUser.getRole().equals(role)) {   
////		if(!"ADMIN".equals(authUser.getRole())) {
//			System.out.println("---------9-1번" + role);
//			response.sendRedirect(request.getContextPath());
//			return false;
//		}
		
		// 여기까지 옸다는것은 @Auth의 role => "ADMIN"
		// authUser의 roel => "ADMIN"
		// 관리자 권한이 확인!!
		
		return true;
	}

}

