package com.rwork.cloudeye.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


@Component
public class CountTimeInterceptor  extends HandlerInterceptorAdapter{
	
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
		long startTime = System.currentTimeMillis();
		
		request.setAttribute("starttime", startTime);
		response.setHeader("starttime", startTime+"");
		//response.setHeader("Access-Control-Allow-Origin", "*");
		return true;
	}

}
