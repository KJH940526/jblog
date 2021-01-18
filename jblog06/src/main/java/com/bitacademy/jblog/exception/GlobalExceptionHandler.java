package com.bitacademy.jblog.exception;


import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
					//Log는 클래스 타입!!
	private static final Log LOGGER = LogFactory.getLog(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception e) throws Exception {
		// 1. 로깅(Logging)
		StringWriter errors = new StringWriter(); //버퍼 화면에 출력하기 위해서 스트링으로 바꾸는 역활!
		e.printStackTrace(new PrintWriter(errors)); //프린트 라이터를 통해서 String라이터에 쓰여짐!
		LOGGER.error(errors.toString());
		
		// 2. 사과(안내 페이지 포워딩, 정상종료)
		ModelAndView mav = new ModelAndView();
		mav.addObject("errors",errors);
		mav.setViewName("error/exception");
		
		return mav;
		
	}
}

