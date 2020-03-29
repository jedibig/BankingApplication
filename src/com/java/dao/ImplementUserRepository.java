package com.java.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;

import org.apache.log4j.Logger;

import com.java.controller.CreateAccountServlet;
import com.java.dto.User;

//@WebServlet("/newUser")
public class ImplementUserRepository implements UserRepository{

	static Logger logger = Logger.getLogger(ImplementUserRepository.class);
	@Override
	public boolean insertUser(User user) {
		//this has to change to check if username already exist i think
		try (Connection c = DbUtil.getConnection(); Statement s = c.createStatement();) {
			int rows = s.executeUpdate(" insert into banking_user(username, name, password, accNumber) values ( '" + user.getUsername() + "', '" + 
						user.getName() + "' + '" +user.getPassword() + "')");
			int newrow = s.executeUpdate("insert into banking_account(banking_accnum, balance) values( BANKING_ACCNUM.nextval, '" + user.getUsername() +
					      "' )");
			logger.info("attempting to save user into database");
			return true;
			
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
