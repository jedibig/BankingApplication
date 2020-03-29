package com.java.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.java.dao.ImplementUserRepository;
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
		user.registerNewUser(newUser);
		
//		HttpSession session = req.getSession();
//		session.setAttribute("newUser", newUser);
//		resp.sendRedirect("newUser");
		
		logger.info("Account created successfully");
		
		writer.println("<p>Account created successfuly<p>");
		writer.println("<a href='login.html'>Go to Login</a>");
		
	}

}
