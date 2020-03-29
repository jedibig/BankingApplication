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
 * Servlet implementation class Deposit
 */
@WebServlet("/account/depositt")
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
			response.getWriter().write("<p>USER INFORMATION NOT FOUND</p><br>");
			response.getWriter().write("<a href='login.html'>Return to log in page.</a>");
			return;
		}
		User user = (User) obj;
		PrintWriter writer = response.getWriter();
		HttpSession req = request.getSession();
		req.setAttribute("user", user);
		
		writer.print("<body>\n" + 
				"        <h1>B13 Banking Application</h1>\n" + 
				"        <h2>Deposit</h2>\n" + 
				"        <form action='./deposit/confirm' method='post'>\n" + 
				"          <label for='senderNum'>Sender Account Number</label>\n" + 
				"          <input type='number' name='senderNum' id='senderNum'><br>\n" + 
				"          <label for='amount'>Amount</label>\n" + 
				"          <input type='number' min='0.00' max='10000.00' step='0.01' name='amount' id='amount'><br>\n" + 
				"          <input type='submit' value='Send'>\n" + 
				"        </form>\n" + 
				"        <form action='./home'><input type='submit' value='Click hereto go back home'/>" + 
				"        </body>");
		
//		User user = new User();// For testing purposes.
//		user.setAccNum(103);
	}

}
