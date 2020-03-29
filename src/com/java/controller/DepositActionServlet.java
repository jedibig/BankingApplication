package com.java.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.java.dto.Transaction;
import com.java.dto.User;
import com.java.service.AccountUtilImp;


@WebServlet(urlPatterns="/account/deposit/confirm")
public class DepositActionServlet extends GenericServlet{
	static Logger logger = Logger.getLogger(DepositServlet.class);
	static AccountUtilImp aui = new AccountUtilImp();
	
	private static final long serialVersionUID = 1L;

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		logger.info("Deposit Action has been called.");
		int senderID = Integer.parseInt(request.getParameter("senderNum"));
		double amount = Double.parseDouble(request.getParameter("amount"));
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		PrintWriter writer = response.getWriter();
		response.setContentType("text/html");
		User user = (User) session.getAttribute("user");
		
		
		Transaction t = new Transaction(senderID, user.getAccNum(), amount);
		boolean possible = aui.depositFund(t);
		String refereer = req.getHeader("Referer");
		
		if(possible) {
			writer.print("<p>Your transaction was possible.<br> Check your updated balance through the home page.</p>");
			writer.print("<form action='refereer'><input type='submit' value='Go to Home Page'/>'");
		}
		else {
			writer.print("<p>There were problems with this transaction.<br>Try again at a later time.</p>");
			writer.print("<form action='refereer'><input type='submit' value='Go to Home Page'/>'");
		}
		
	}

}
