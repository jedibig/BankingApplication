package com.java.controller;

import org.apache.log4j.Logger;

import com.java.dto.User;
import com.java.service.AccountUtilImp;
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
	static AccountUtilImp aui = new AccountUtilImp();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Receive request to get balance");
		Object obj = request.getSession().getAttribute("User");
		User user;
		if (!(obj instanceof User)) {
			response.getWriter().write("USER INFORMATION NOT FOUND");
			return;
		}
		user = (User) obj;
		Double balance = aui.getBalance(user);
		if (balance < 0)
			response.getWriter().write("SERVER ERROR. PLEASE TRY AGAIN LATER!");
		
		response.getWriter().append("Balance for user ").append(user.getUsername()).append(": $").append(balance.toString());

		
		response.getWriter().append("Served at: ").append(request.getContextPath());		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
