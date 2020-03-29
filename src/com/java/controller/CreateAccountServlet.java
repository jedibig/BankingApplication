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

//import com.java.dao.DbUtil;

/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(CreateAccountServlet.class);
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter writer = resp.getWriter();
		String name = req.getParameter("name");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User newUser = new User();
		newUser.setName(name);
		newUser.setUsername(username);
		newUser.setPassword(password);
		
		UserAuthenticateImpl user = new UserAuthenticateImpl();
		boolean success = user.registerNewUser(newUser);
		
		if (success) {
			writer.println("<p>Account created successfully<p>");
			writer.println("<a href='login.html'>Go to Login</a>");
		}
		else {
			writer.println("<p>Account was not created successfully<p><br><p>Please try creating your account again.</p>");
			writer.println("<a href='register.html'>Go back to Register Page</a>");
		}
	}

}
