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

@WebFilter("/account/transfer")
public class TransferFilter implements Filter{
	Logger logger = Logger.getLogger(TransferFilter.class);
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		
		String transferRequest = req.getContextPath() + "/account/transfer";
		boolean isTransferRequest = req.getRequestURI().equals(transferRequest);
		boolean isTransferPage = req.getRequestURI().endsWith("transfer");
		boolean isLoggedIn = (session != null && session.getAttribute("username") != null);
		
		if(isLoggedIn && (isTransferRequest || isTransferPage)) {
			logger.info("User already accessed transfer page");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/account/balance");
			dispatcher.forward(request, response);
		}
		else if(isLoggedIn || isTransferRequest) {
			logger.info("User is redirected to /account/transfer");
			chain.doFilter(request, response);
		}
		else {
			logger.info("User has not loggend in properly. Redirected to log in page.");
			RequestDispatcher dispatcher = request.getRequestDispatcher("login.html");
			dispatcher.forward(request, response);
		}		
	}
}