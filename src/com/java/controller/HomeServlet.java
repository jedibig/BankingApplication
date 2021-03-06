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

/**
 * Servlet implementation class HomeServlets
 */
@WebServlet("/account/home")
public class HomeServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(HomeServlet.class);
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter writer = response.getWriter();
		
		Object obj = request.getSession().getAttribute("user");
		if (!(obj instanceof User)) {
			response.getWriter().write("USER INFORMATION NOT FOUND");
			response.getWriter().write("<a href='login.html'>Return to log in page.</a>");
			return;
		}
		User user = (User) obj;
				
		
		writer.println("<!DOCTYPE html>");
		writer.println("<html>");
		writer.println("<head>");
		writer.println("<title>Home Page</title>");
		writer.println("<link rel='stylesheet' href='../style.css'></head>");
		writer.println("<body><h1>B13 Banking Application</h1>");
		writer.println("<h2>Welcome " + user.getName() + "</h2>");
		writer.println("<p>Your account number is " + user.getAccNum() + "</p><br>");
		writer.println("<p>What would you like to do today ? </p>");
		writer.println("<form action='balance'><input type='submit' value='See Balance'/></form>");
		writer.println("<form action='transferpage'><input type='submit' value='Transfer'/></form>");
		writer.println("<form action='depositpage'><input type='submit' value='Deposit'/></form><br>"
				+ "<form action='./logout'><input type='submit' value='Logout'/></form>");
		writer.println("</html>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
