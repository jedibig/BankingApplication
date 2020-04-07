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

import com.java.dto.User;
import com.java.service.ServiceUtility;
import com.java.service.UserAuthenticationService;
import com.java.exception.DatabaseException;
import com.java.exception.PasswordMismatch;
import com.java.exception.UsernameMismatch;
import com.java.exception.UsernameNotFound;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	static Logger logger = Logger.getLogger(LoginServlet.class);
	static UserAuthenticationService userAuth = ServiceUtility.userAuth;
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		PrintWriter writer = resp.getWriter();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		HttpSession session = req.getSession();
		
		User signin = new User();
		signin.setUsername(username);
		signin.setPassword(password);
		
			try {
				User check = userAuth.authenticateUser(signin);
				if(check.getUsername().equals(username) && check.getPassword().equals(password)){
					session.setAttribute("user", check);
					resp.sendRedirect("account/home");
				}
				else {
					writer.print("<p>You were unable to log in.</p>");
				}
			}
			catch (UsernameMismatch e) {
				writer.print("<p>The username was incorrect.</p>");
			} catch (PasswordMismatch e) {
				writer.print("<p>The password was incorrect.</p>");
			} catch (UsernameNotFound e) {
				writer.print("<p>The username was not found.</p>");
			} catch (DatabaseException e) {
				writer.print("<p>There was a problem connecting with our database.</p>");
			}
			writer.print("<a href='/BankingApp/'>Click here</a> to go back to the login page");
	}
}