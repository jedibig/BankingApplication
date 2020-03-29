package com.java.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.java.dto.User;


public class ImplementUserRepository implements UserRepository{

	static Logger logger = Logger.getLogger(ImplementUserRepository.class);

	@Override
	public boolean insertUser(User user) {
		//this has to change to check if username already exist i think
		try (Connection c = DbUtil.getConnection(); Statement s = c.createStatement();) {
			logger.info("Attempting to save user into database");
			int rows = s.executeUpdate("insert into banking_user(username, name, password, accNumber) values ('" + user.getUsername() + "', '" + 
						user.getName() + "', '" +user.getPassword() + "', )");
			int newrow = s.executeUpdate("insert into banking_account(accnumber, balance) values(, 400)");
			c.commit();
			logger.info("Account created successfully");
			return true;
			
		} catch (SQLException e) {
			System.out.println("Problem saving credentials to db." + e.getMessage() + " Please try again later!");
		return false;}
		
	}

	@Override
	public boolean retrieveUser(User user) {
				try (Connection c = DbUtil.getConnection(); Statement s = c.createStatement();) {
					
					String query = "Select username, password from banking_user";
					ResultSet rs = s.executeQuery(query);
					while(rs.next()) {
						String username = rs.getString("username");
						String password = rs.getString("password");
						if(username.equals(user.getUsername()) && password.equals(user.getPassword())){
							return true;
						}
					}
					System.out.println("Invalid username or password, check again");
				
					
				} catch (SQLException e) {
					System.out.println("Problem retrieving credentials to db." + e.getMessage() + " Please try again!");
				}
				
				return false;
	}

}
