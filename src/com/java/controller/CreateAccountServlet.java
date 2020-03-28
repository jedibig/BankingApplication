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

import org.apache.log4j.Logger;

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
		Double initDeposit= Double.parseDouble(req.getParameter("initDeposit"));
		
		logger.info("Account created successfully");
		try (Connection c = DbUtil.getConnection(); Statement s = c.createStatement();) {
			int rows = s.executeUpdate(" insert into user values ( '" + username + "', '" + name + "', '" + password + "')");
		    logger.info("New Account inserted into the database");
		} catch (SQLException e) {
			writer.println("Problem saving credentials to db." + e.getMessage() + " Please try again later!");
		}
		
		writer.println("<p>Account created successfuly<p>");
		writer.println("<a href='login.html'>Go to Login</a>");
		
	}

}
