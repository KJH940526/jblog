package com.bitacademy.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//어노테이션을 어디다가 달꺼냐? (메소드타입과 클래스,인터페이스에 넣는다)
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)//어노테이션을 언제까지 유지시킬것인가?
public @interface Auth {
		
	public String value() default "USER";
	
//	public Role role() default Role.USER;
}
