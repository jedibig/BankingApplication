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
import com.java.service.ServiceUtility;
import com.java.service.UserAuthenticationService;



/**
 * Servlet implementation class CreateAccountServlet
 */
@WebServlet("/CreateAccountServlet")
public class CreateAccountServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(CreateAccountServlet.class);
	private static final long serialVersionUID = 1L;

	static UserAuthenticationService user = ServiceUtility.userAuth;

       
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String name = req.getParameter("name").trim();
		String username = req.getParameter("username").trim();
		String password = req.getParameter("password").trim();
		
		User newUser = new User(username, password, name, 0);
		
		String httpResponse = "<html>";
		try {
			if(username == null && password == null && name == null) {
				httpResponse += "<p>Provide information in the provided fields.</p>";
				throw new DatabaseException();
			}
			
			if(username == null || !username.matches("^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$")) {
				httpResponse += "<p>Provide a valid username</p>";
				throw new DatabaseException();
			}
			
			if(password == null || !password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{5,})")) {
				httpResponse += "<p>Provide a valid password</p>";
				throw new DatabaseException();
			}
			
			if (user.registerNewUser(newUser)) {
				httpResponse += "<p>Account created successfully<p><a href='/BankingApp'>Click here</a> to go to login page.";
			} else {
				httpResponse += "<p>Something happened on our end, please try again later.<p><br>";
				httpResponse += "<a href='register.html'>Click here</a> to try again.</html>";
			}
			
		} catch(UsernameExistsException e) {
			httpResponse += "<p>Username already exist. Please change your username<p><br>";
			httpResponse += "<a href='register.html'>Click here</a> to try again.</html>";

		} catch(DatabaseException e) {
			httpResponse += "<p>Error registering user, please try again.<p><br>";
			httpResponse += "<a href='register.html'>Click here</a> to try again.</html>";

		}
		
		resp.getWriter().print(httpResponse);
	}
}