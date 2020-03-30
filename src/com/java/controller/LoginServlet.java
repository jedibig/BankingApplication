package com.java.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.java.dto.User;
import com.java.service.UserAuthenticateImpl;
import com.java.exception.PasswordMismatch;
import com.java.exception.UsernameNotFound;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	static Logger logger = Logger.getLogger(LoginServlet.class);
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		System.out.println("Connection Successful");
		PrintWriter writer = resp.getWriter();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User signin = new User();
		signin.setUsername(username);
		signin.setPassword(password);
		
		UserAuthenticateImpl userAuth = new UserAuthenticateImpl();
			try {
				userAuth.authenticateUser(signin);
				signin.setPassword(null);
				req.getSession().setAttribute("User", signin);
				logger.debug("sessionID: " + req.getSession().getId());
				resp.sendRedirect("account/home");
			} catch (PasswordMismatch e) {
				writer.println("<!DOCTYPE html>");
				writer.println("<html>");
				writer.println("<head>");
				writer.println("<title>Home Page</title></head>");
				writer.println("<link rel='stylesheet' href='sytle.css'>");
				writer.println("<body>");
				writer.println("<h2>The password you entered is incorrect</h2>");
				writer.println("<a href='/BankingApp'>Try Again</a>");
				writer.println("</body>");
				writer.println("</html>");
			} catch (UsernameNotFound e) {
				writer.println("<!DOCTYPE html>");
				writer.println("<html>");
				writer.println("<head>");
				writer.println("<title>Home Page</title></head>");
				writer.println("<link rel='stylesheet' href='sytle.css'>");
				writer.println("<body>");
				writer.println("<h2>The Username you entered is incorrect</h2>");
				writer.println("<a href='/BankingApp'>Try Again</a>");
				writer.println("</body>");
				writer.println("</html>");
			}		
	}
}