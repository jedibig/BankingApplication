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
 * Servlet implementation class Deposit
 */
@WebServlet("/account/deposit")
public class DepositServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(DepositServlet.class);
	static AccountUtilImp aui = new AccountUtilImp();

	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("received request to deposit money");
		Object obj = request.getSession().getAttribute("User");
		if (!(obj instanceof User)) {
			response.getWriter().write("USER INFORMATION NOT FOUND");
			return;
		}
		User user = (User) obj;
		int senderID = Integer.parseInt(request.getParameter("senderNum"));
		double amount = Double.parseDouble(request.getParameter("amount"));

		
		Transaction t = new Transaction(senderID, user.getAccNum(), amount);
		if (!aui.transferFund(t)) {
			response.getWriter().write("CANNOT DEPOSIT AT THIS TIME. PLEASE TRY AGAIN LATER");
		} else {
			response.getWriter().write("Success.");

		}
	}

}
