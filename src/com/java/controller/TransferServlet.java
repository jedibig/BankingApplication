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
import com.java.service.AccountUtility;
import com.java.service.ServiceInstances;

/**
 * Servlet implementation class TransferServlet
 */
@WebServlet(urlPatterns="/account/transfer")
public class TransferServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(TransferServlet.class);
	private static final long serialVersionUID = 1L;
	static AccountUtility aui = ServiceInstances.accountUtil;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("received request to transfer money");
		Object obj = request.getSession().getAttribute("user");
		if (!(obj instanceof User)) {
			response.getWriter().write("<p>USER INFORMATION NOT FOUND</p><br>");
			response.getWriter().write("<a href='login.html'>Return to log in page.</a>");
			return;
		}

		int receiverID = Integer.parseInt(request.getParameter("receiverNum"));
		double amount = Double.parseDouble(request.getParameter("amount"));
				
		response.getWriter().write("<html>");
		try {
			logger.debug("getting recepient information.");
			String name = aui.initiateTransfer(receiverID);
			request.setAttribute("receiverName", name);
			request.setAttribute("receiverAccNum",receiverID);
			request.setAttribute("amount", amount);
			
			logger.debug("forwarding request.");
			request.getRequestDispatcher("transfer-confirm").forward(request, response);
			
		} catch(AccNumNotFound e) {
			response.getWriter().write("Corresponding receiver account number cannot be found.");
		} catch(DatabaseException e) {
			response.getWriter().write("CANNOT TRANSFER AT THIS TIME. PLEASE TRY AGAIN LATER");
		}
		
		response.getWriter().write("<form action='home'><input type='submit' value='Go to Home Page'/></html>");
	}

}
