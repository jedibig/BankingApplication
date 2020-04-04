package com.java.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
		
		boolean isLoggedIn = (session != null && session.getAttribute("signin") != null);
		
		if(isLoggedIn) {
			logger.info("User is redirected to " + req.getRequestURI());
			chain.doFilter(request, response);
		}
		else {
			logger.info("User has not loggend in properly. Redirected to log in page.");
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect("/BankingApp/index.html");
		}		
	}
}