package com.java.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.java.dto.User;
import com.java.exception.DatabaseException;
import com.java.exception.UsernameExistsException;
import com.java.service.UserAuthenticateImpl;
import com.java.service.UserAuthenticate;



/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(CreateAccountServlet.class);
	private static final long serialVersionUID = 1L;
	
	static UserAuthenticate user = new UserAuthenticateImpl();

       
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String name = req.getParameter("name");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User newUser = new User(username, password, name, 0);
		
		String httpResponse = "<html>";
		try {
			if (user.registerNewUser(newUser)) {
				httpResponse += "<p>Account created successfully<p><a href='/BankingApp'>Click here</a> to go to login page.";
			} 
			
		} catch(UsernameExistsException e) {
			httpResponse += "<p>Username already exist. Please change your username<p><br>";
			httpResponse += "<a href='register.html'>Click here</a> to try again.</html>";

		} catch(DatabaseException e) {
			httpResponse += "<p>Error registering user right now, please try again later.<p><br>";
			httpResponse += "<a href='register.html'>Click here</a> to try again.</html>";

		}
		
		resp.getWriter().print(httpResponse);
	}
}