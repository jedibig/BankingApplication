package com.java.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.java.dto.Transaction;
import com.java.dto.User;
import com.java.service.AccountUtilImp;

/**
 * Servlet implementation class DepositServ
 */
@WebServlet("/account/deposit")
public class DepositServ extends HttpServlet {
	static Logger logger = Logger.getLogger(DepositServ.class);
	static AccountUtilImp aui = new AccountUtilImp();
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Deposit Action has been called.");
		
		logger.debug("SessionID in depactserv" + ((HttpServletRequest)request).getSession().getId());

		int senderID = Integer.parseInt(request.getParameter("senderNum"));
		double amount = Double.parseDouble(request.getParameter("amount"));
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
		
		Object obj = request.getSession().getAttribute("User");
		if (!(obj instanceof User)) {
			response.getWriter().write("<p>USER INFORMATION NOT FOUND</p><br>");
			response.getWriter().write("<a href='login.html'>Return to log in page.</a>");
			return;
		}
		User user = (User) obj;
		
		
		Transaction t = new Transaction(senderID, user.getAccNum(), amount);
		int retVal = aui.depositFund(t);
		
		response.getWriter().write("<html>");
		switch(retVal) {
			case 0:
				response.getWriter().write("<html>Transaction is successful with id: " + t.getTransID());
				break;
			case 1:
				response.getWriter().write("Corresponding account number cannot be found.");
				break;
			case 2:
//				response.getWriter().write("You have insufficient balance.");
//				break;
			case 3:
			case -1:
			default:
				response.getWriter().write("CANNOT TRANSFER AT THIS TIME. PLEASE TRY AGAIN LATER");
		}
		response.getWriter().write("<form action='home'><input type='submit' value='Go to Home Page'/></html>");
	}

}
