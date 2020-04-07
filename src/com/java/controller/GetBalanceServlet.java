package com.java.controller;

import org.apache.log4j.Logger;

import com.java.dto.User;
import com.java.exception.DatabaseException;
import com.java.service.AccountService;
import com.java.service.ServiceUtility;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetBalanceServlet
 */
@WebServlet("/account/balance")
public class GetBalanceServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(GetBalanceServlet.class);
	private static final long serialVersionUID = 1L;
	static AccountService aui = ServiceUtility.accountUtil;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Receive request to get balance");
		Object obj = request.getSession().getAttribute("user");
		if (!(obj instanceof User)) {
			response.getWriter().write("<p>USER INFORMATION NOT FOUND</p><br>");
			response.getWriter().write("<a href='/BankingApp/index.html'>Return to log in page.</a>");
			return;
		}
		
		User user = (User) obj;
		
		
		try {
			double balance = aui.getBalance(user);
			response.getWriter().append("<html>Balance for user ").append(user.getUsername()).append(": $").append(""+balance);
		} catch (DatabaseException e) {
			response.getWriter().write("SERVER ERROR. PLEASE TRY AGAIN LATER!");

		}
		
		response.getWriter().write("<br/><a href='home'>Return to home page.</a><br/>");

		
	}

}
