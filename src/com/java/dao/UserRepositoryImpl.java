package com.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.java.dto.User;
import com.java.exception.DatabaseException;
import com.java.exception.PasswordMismatch;
import com.java.exception.UsernameExistsException;
import com.java.exception.UsernameMismatch;
import com.java.exception.UsernameNotFound;

public class UserRepositoryImpl implements UserRepository {

	static Logger logger = Logger.getLogger(UserRepositoryImpl.class);

	@Override
	public boolean insertUser(User user) throws DatabaseException {
		
		String checkExist = "select username from Banking_User where username = ?";
		String getAccNumber = "select accNum_sequence.nextval as accNum from dual";
		String query = "insert into Banking_User (username, name, password) values (?, ?, ?)";
		String query2 = "insert into Banking_Account(accNumber) values (?)";
		String query3 = "update Banking_User set accNumber = ? where username = ?";
		
		logger.info("inserting user to database");

		try (Connection c = DbUtil.getConnection();
				PreparedStatement check = c.prepareStatement(checkExist);
				PreparedStatement getAccNum = c.prepareStatement(getAccNumber);
				PreparedStatement s = c.prepareStatement(query);
				PreparedStatement s1 = c.prepareStatement(query2);
				PreparedStatement s2 = c.prepareStatement(query3);){
			
			logger.debug("checking if username has existed");
			check.setString(1, user.getUsername());
			ResultSet rs = check.executeQuery();
			if (rs.next()){
				throw new UsernameExistsException("Username exist when trying to insert new user");
			}
			
			
			logger.debug("geting new accont number");
			ResultSet rs1 = getAccNum.executeQuery();
			int accNum;
			if (rs1.next()){
				accNum = rs1.getInt("accNum");
			} else {
				return false;
			}
			
			logger.debug("inserting new user to db without account number");
			s.setString(1, user.getUsername());
			s.setString(2, user.getName());
			s.setString(3, user.getPassword());
			
			if (s.executeUpdate() <= 0) {
				return false;
			}
			
			s1.setInt(1, accNum);
			logger.debug("creating new bank account");
			if (s1.executeUpdate() <= 0) {
				return false;
			}
			
			logger.debug("updating banking user account");
			s2.setString(2, user.getUsername());
			s2.setInt(1, accNum);
			
			if (s2.executeUpdate() <= 0) {
				return false;
			}
			
			c.commit();

			logger.info("Account created successfully");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DatabaseException("Fatal error. Got SQLException");
		}
		
		return true;
	}

	@Override
	public User retrieveUser(User user) throws DatabaseException{

		String query = "Select username, name, ACCNUMBER, password from banking_user where username = ?";
		try (Connection c = DbUtil.getConnection();
			 PreparedStatement s = c.prepareStatement(query);) {
			
			logger.info("Retrieving information based on client input.");
			s.setString(1, user.getUsername());
			
			ResultSet result = s.executeQuery();
			
			User obj = new User("", "", "", 0);
			
			if(result.next()) {
				logger.info("Information found based on client input.");
				obj = new User();
				obj.setUsername(result.getString("username"));
				obj.setName(result.getString("name"));
				obj.setPassword(result.getString("password"));
				obj.setAccNum(result.getInt("ACCNUMBER"));
				if(!obj.getPassword().equals(user.getPassword())) {
					logger.info("Password entered is incorrect for username");
					throw new PasswordMismatch("Password entered does not match record in db");
				}
			}
			else {
				logger.info("Information was not found based on client input.");
				throw new UsernameNotFound("Account was not found with this username");
			}
			
			if(!obj.getUsername().equals(user.getUsername())) {
				throw new UsernameMismatch("Usernames was incorrect");
			}
			
			if(!obj.getPassword().equals(user.getPassword())){
				throw new PasswordMismatch("Password was incorrect");
			}
			
			return obj;
			
		} catch (SQLException e) {
			logger.error("Problem retrieving credentials to db." + e.getMessage() + " Please try again!");
			throw new DatabaseException("We had a problem gathering the information. Please try again.");
		}
	}
}
