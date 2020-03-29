package com.java.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

@WebFilter("/*")

public class RegistrationAuthentication implements Filter{
	Logger logger = Logger.getLogger(RegistrationAuthentication.class);
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
			
		HttpServletRequest req = (HttpServletRequest) request;
		logger.debug(req.getMethod());
		logger.info("Request generated for URI: " + req.getRequestURI());
		logger.info("Time to fulfill request was: " + System.currentTimeMillis());
		chain.doFilter(request, response);
		
		logger.info("Response generated for url " + req.getRequestURI());
		logger.info("Time generated to fulfill response: " + System.currentTimeMillis());
	}
	
}
