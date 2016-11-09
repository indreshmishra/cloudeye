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
//		response.setHeader("Access-Control-Allow-Origin", "*");
//		if(request.getMethod().equals("OPTIONS")){
//			response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
//			
//		}
//		response.setHeader("Access-Control-Allow-Origin", "*");
//	    response.setHeader("Access-Control-Allow-Credentials", "true");
//	    response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT,DELETE");
//	    response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization");
		return true;
	}

}
