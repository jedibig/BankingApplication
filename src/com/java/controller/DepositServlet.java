package com.java.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import com.java.dto.User;
import com.java.exception.AccNumNotFound;
import com.java.exception.DatabaseException;
import com.java.service.AccountService;
import com.java.service.ServiceUtility;

/**
 * Servlet implementation class DepositServ
 */
@WebServlet("/account/deposit")
public class DepositServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(DepositServlet.class);
	static AccountService aui = ServiceUtility.accountUtil;
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		logger.info("received request to deposit money");
		Object obj = request.getSession().getAttribute("user");
		if (!(obj instanceof User)) {
			response.getWriter().write("<p>USER INFORMATION NOT FOUND</p><br>");
			response.getWriter().write("<a href='login.html'>Return to log in page.</a>");
			return;
		}
		
		if (request.getParameter("senderNum") == null || request.getParameter("amount") == null) {
			response.getWriter().append("Input is empty. Please try again")
								.append("<form action='transferpage'><input type='submit' value='Go back'/></html>");
			return;
		}

				
		response.getWriter().write("<html>");
		try {
			int senderID = Integer.parseInt(request.getParameter("senderNum"));
			double amount = Double.parseDouble(request.getParameter("amount"));
			
			logger.debug("getting recepient information.");
			String name = aui.initiateTransfer(senderID);
			request.setAttribute("senderName", name);
			request.setAttribute("senderAccNum",senderID);
			request.setAttribute("amount", amount);
			
			logger.debug("forwarding request.");
			request.getRequestDispatcher("deposit-confirm").forward(request, response);
			
		} catch (NumberFormatException e){
			logger.error("error parsing input.");
			response.getWriter().append("Invalid input. Please try again")
								.append("<form action='transferpage'><input type='submit' value='Go back'/></html>");
		} catch(AccNumNotFound e) {
			response.getWriter().write("Corresponding receiver account number cannot be found.");
		} catch(DatabaseException e) {
			response.getWriter().write("CANNOT TRANSFER AT THIS TIME. PLEASE TRY AGAIN LATER");
		}
		
		response.getWriter().write("<form action='home'><input type='submit' value='Go to Home Page'/></html>");
	}

}
