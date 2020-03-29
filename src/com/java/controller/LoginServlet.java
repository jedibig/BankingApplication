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
import com.java.service.UserAuthenticateImpl;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{
	static Logger logger = Logger.getLogger(LoginServlet.class);
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		System.out.println("Connection Successful");
		//PrintWriter writer = resp.getWriter();
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		User signin = new User();
		signin.setUsername(username);
		signin.setPassword(password);
		
		UserAuthenticateImpl userAuth = new UserAuthenticateImpl();
		if(userAuth.authenticateUser(signin)) {
			signin.setPassword(null);
			req.getSession().setAttribute("User", signin);
			logger.debug("sessionID: " + req.getSession().getId());
			resp.sendRedirect("account/home");
		}
		
	}
	
	

	
}