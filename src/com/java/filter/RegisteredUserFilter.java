//package com.java.filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.apache.log4j.Logger;
//
//@WebFilter("/home")
//
//public class RegisteredUserFilter implements Filter{
//	Logger logger = Logger.getLogger(RegisteredUserFilter.class);
//	
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpSession session = req.getSession(true);
//		
//		boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
//		
//		String loginURI = req.getContextPath() + "login";
//		
//		boolean isLoginRequest = req.getRequestURI().equals(loginURI);
//		
//		boolean isLoginPage = req.getRequestURI().endsWith("login.html");
//		
//		if(isLoggedIn && (isLoginRequest || isLoginPage)) {
//			logger.info("The user is already logged in, redirected to home page.");
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/home");
//			dispatcher.forward(request, response);
//		}
//		else if (isLoggedIn || isLoginRequest) {
//			logger.info("The user is being redirected to the home page.");
//			chain.doFilter(request, response);
//		}
//		else {
//			logger.info("The user has not logged in, redirected back to log in page.");
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/BankingApp/login.html");
//			dispatcher.forward(request, response);
//		}
//		
//	}
//	
//}
