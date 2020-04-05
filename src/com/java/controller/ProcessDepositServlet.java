package com.java.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.java.dto.Transaction;
import com.java.dto.User;
import com.java.exception.DatabaseException;

import com.java.service.AccountUtility;
import com.java.service.ServiceInstances;

/**
 * Servlet implementation class Deposit
 */
@WebServlet("/account/process-deposit")
public class ProcessDepositServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(ProcessDepositServlet.class);
	static AccountUtility aui = ServiceInstances.accountUtil;

	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("processing deposit");
		Object obj = request.getSession().getAttribute("user");
		if (!(obj instanceof User)) {
			response.getWriter().write("<p>USER INFORMATION NOT FOUND</p><br>");
			response.getWriter().write("<a href='login.html'>Return to log in page.</a>");
			return;
		}
		User user = (User) obj;
		PrintWriter writer = response.getWriter();
	
		int senderID = Integer.parseInt(request.getParameter("senderNum"));
		double amount = Double.parseDouble(request.getParameter("amount"));
		logger.debug("user acc number: " + user.getAccNum());
		Transaction transaction = new Transaction(senderID, user.getAccNum(), amount);

		try {
			transaction = aui.depositFund(transaction);
			if (transaction == null) {
				writer.write("<p>Something happened on our end. please try again later.</p>");
			} else {
				writer.write("<p>Transaction successful with id " + transaction.getTransID() + "</p>");
			}
//		} catch (InvalidBalanceException e) {
//			response.getWriter().write("You have insufficient balance.");
		} catch(DatabaseException e) {
			response.getWriter().write("<p>Cannot transfer at this time. Please try again later</p>");
		}
		
		response.getWriter().write("<form action='home'><input type='submit' value='Go to Home Page'/>");
	}

}
