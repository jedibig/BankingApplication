package com.java.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;

import com.java.dto.User;

//@WebServlet("/newUser")
public class ImplementUserRepository implements UserRepository{

	@Override
	public boolean insertUser(User user) {
		//static Logger logger = 
		try (Connection c = DbUtil.getConnection(); Statement s = c.createStatement();) {
			int rows = s.executeUpdate(" insert into user(username, name, password) values ( '" + user.getUsername() + "', '" + 
						user.getName() + "' + '" +user.getPassword() + "')");
			int newrow = s.executeUpdate("insert into account(banking_accnum, username, balance) values( BANKING_ACCNUM.nextval, '" + user.getUsername() +
					      "' )");
			
		} catch (SQLException e) {
			System.out.println("Problem saving credentials to db." + e.getMessage() + " Please try again later!");
		}
		return false;
	}

	@Override
	public boolean retrieveUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
