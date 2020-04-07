package com.java.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.java.dto.JournalEntry;
import com.java.dto.User;
import com.java.exception.DatabaseException;
import com.java.exception.InvalidBalanceException;
import com.java.service.AccountService;
import com.java.service.ServiceUtility;

/**
 * Servlet implementation class ProcessTransferServlet
 */
@WebServlet(urlPatterns="/account/process-transfer")
public class ProcessTransferServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(ProcessTransferServlet.class);
	private static final long serialVersionUID = 1L;
	static AccountService aui = ServiceUtility.accountUtil;

       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("processing transfer");
		Object obj = request.getSession().getAttribute("user");
		if (!(obj instanceof User)) {
			response.getWriter().write("<p>USER INFORMATION NOT FOUND</p><br>");
			response.getWriter().write("<a href='login.html'>Return to log in page.</a>");
			return;
		}
		
		User user = (User) obj;
		
		if (request.getParameter("receiverNum") == null || request.getParameter("amount") == null) {
			response.getWriter().append("Input is empty. Please try again")
								.append("<form action='transferpage'><input type='submit' value='Go back'/></html>");
			return;
		}

		int receiverID = Integer.parseInt(request.getParameter("receiverNum"));
		double amount = Double.parseDouble(request.getParameter("amount"));
//		logger.debug("sending money from "+ user.getAccNum() + " to " + receiverID );
		JournalEntry journalEntry = new JournalEntry(user.getAccNum(), receiverID, amount);
		
		response.getWriter().write("<html>");
		try {
			journalEntry = aui.transferFund(journalEntry);
			if (journalEntry == null) {
				response.getWriter().write("Something happened on our end. please try again later.");
			} else {
				response.getWriter().write("Transaction successful with id " + journalEntry.getTransID());
			}
		} catch (InvalidBalanceException e) {
			response.getWriter().write("You have insufficient balance.");
		} catch(DatabaseException e) {
			response.getWriter().write("CANNOT TRANSFER AT THIS TIME. PLEASE TRY AGAIN LATER");
		}
		
		response.getWriter().write("<form action='home'><input type='submit' value='Go to Home Page'/></html>");
		
	}

}
