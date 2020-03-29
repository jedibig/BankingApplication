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

@WebFilter("/account/balance")
public class BalanceFilter implements Filter{
	Logger logger = Logger.getLogger(BalanceFilter.class);
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		
		String balanceRequest = req.getContextPath() + "/account/balance";
		boolean isBalanceRequest = req.getRequestURI().equals(balanceRequest);
		boolean isLoggedIn = (session != null && session.getAttribute("username") != null);
		
	
		if(isLoggedIn || isBalanceRequest) {
			logger.info("User is redirected to /account/balance");
			chain.doFilter(request, response);
		}
		else {
			logger.info("User has not loggend in properly. Redirected to log in page.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.html");
			dispatcher.forward(request, response);
		}		
	}
}
