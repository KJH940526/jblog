package com.bitacademy.jblog.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bitacademy.security.AuthInterceptor;
import com.bitacademy.security.AuthUserHandlerMethodArgumentResolver;
import com.bitacademy.security.LoginInterceptor;
import com.bitacademy.security.LogoutInterceptor;

@Configuration
@PropertySource("classpath:config/webconfig.properties")
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private Environment env;
	
	//1. Arugument Resolvers
	@Bean
	public HandlerMethodArgumentResolver authUserHandlerMethodArgumentResolver() {
		return new AuthUserHandlerMethodArgumentResolver();
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(authUserHandlerMethodArgumentResolver());
	}
	
	//2. InterCeptors
		@Bean
		public HandlerInterceptor loginInterceptor() {
			return new LoginInterceptor();
		}
		
		@Bean
		public HandlerInterceptor logoutInterceptor() {
			return new LogoutInterceptor();
		}
		
		@Bean
		public HandlerInterceptor authInterceptor() {
			return new AuthInterceptor();
		}
		
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry
				.addInterceptor(loginInterceptor())
				.addPathPatterns(env.getProperty("web.auth-url"));
			
			registry
			.addInterceptor(logoutInterceptor())
			.addPathPatterns(env.getProperty("web.logout-url"));
			
			registry
			.addInterceptor(authInterceptor())
			.addPathPatterns("/**")
			.excludePathPatterns(env.getProperty("web.auth-url"))
			.excludePathPatterns(env.getProperty("web.logout-url"))
			.excludePathPatterns(env.getProperty("web.statics")+"/**");
		}
		
		// MVC Resource Mapping(URL Magic Mapping)
		@Override
		public void addResourceHandlers(ResourceHandlerRegistry registry) {
			registry
				.addResourceHandler(env.getProperty("resouce.mapping"))
				.addResourceLocations("file:" + env.getProperty("resource.locations"));
		}

	
}
