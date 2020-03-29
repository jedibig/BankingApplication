package com.java.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.java.dto.Transaction;
import com.java.dto.User;
import com.java.service.AccountUtilImp;

/**
 * Servlet implementation class TransferServlet
 */
@WebServlet(urlPatterns="/account/transfer")
public class TransferServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(TransferServlet.class);
	private static final long serialVersionUID = 1L;
	static AccountUtilImp aui = new AccountUtilImp();

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("received request to transfer money");
		Object obj = request.getSession().getAttribute("User");
		if (!(obj instanceof User)) {
			response.getWriter().write("<p>USER INFORMATION NOT FOUND</p><br>");
			response.getWriter().write("<a href='login.html'>Return to log in page.</a>");
			return;
		}
		User user = (User) obj;

		int receiverID = Integer.parseInt(request.getParameter("receiverNum"));
		double amount = Double.parseDouble(request.getParameter("amount"));

		
		Transaction t = new Transaction(user.getAccNum(), receiverID, amount);
		if (!aui.transferFund(t)) {
			response.getWriter().write("CANNOT TRANSFER AT THIS TIME. PLEASE TRY AGAIN LATER");
		} else {
			response.getWriter().write("Success.");
			response.getWriter().write("<form action='home'><input type='submit' value='Go to Home Page'/>'");

		}
	}

}
