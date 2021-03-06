package com.java.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

@WebFilter("/account/*")
public class ServletFilter implements Filter{
	Logger logger = Logger.getLogger(ServletFilter.class);
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		
		boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
		
		if(isLoggedIn) {
			logger.info("Session is valid.");
			chain.doFilter(request, response);
		}
		else {
			logger.info("User has not loggend in properly. Redirected to log in page.");
			RequestDispatcher reqDispatch = request.getRequestDispatcher("../login.html");
			reqDispatch.forward(request, response);
		}		
		
	}
}