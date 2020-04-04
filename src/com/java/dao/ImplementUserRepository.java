package com.java.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.java.dto.User;
import com.java.exception.PasswordMismatch;
import com.java.exception.UsernameExistsException;
import com.java.exception.UsernameNotFound;

public class ImplementUserRepository implements UserRepository {

	static Logger logger = Logger.getLogger(ImplementUserRepository.class);

	@Override
	public void insertUser(User user) throws UsernameExistsException {
		// this has to change to check if username already exist i think
		try (Connection c = DbUtil.getConnection(); Statement s = c.createStatement();) {
			logger.info("Attempting to save user into database");
			System.out.println("Account added");
			ResultSet userInfo = s.executeQuery(
					"select accnumber from banking_account where username = '" + user.getUsername() + "'");
			if (!userInfo.next()) {
				logger.error("Could not execute query");
			}
			int accNum = userInfo.getInt("Accnumber");

			int rows = s.executeUpdate(
					"insert into banking_user(username, name, password, accNumber) values ('" + user.getUsername()
							+ "', '" + user.getName() + "', '" + user.getPassword() + "', " + accNum + ")");
			if (rows <= 0) {
				logger.error("Could not execute query");
			}
			c.commit();
			logger.info("Account created successfully");
		} catch (SQLException e) {
			throw new UsernameExistsException("Username already exist");
		}
	}

	@Override
	public void retrieveUser(User user) throws PasswordMismatch, UsernameNotFound {
		try (Connection c = DbUtil.getConnection(); Statement s = c.createStatement();) {
			String query = "Select username, name, ACCNUMBER, password from banking_user where username = '"
					+ user.getUsername() + "'";
			logger.debug(query);

			ResultSet rs = s.executeQuery(query);
			if (rs.next()) {
				if (rs.getString("password").equals(user.getPassword())) {
					user.setName(rs.getString("name"));
					user.setAccNum(rs.getInt("ACCNUMBER"));
				} else {
					throw new PasswordMismatch("Your password is incorrect");
				}
			} else {
				throw new UsernameNotFound("Username not found");
			}

		} catch (SQLException e) {
			logger.error("Problem retrieving credentials to db." + e.getMessage() + " Please try again!");
		}
	}
}
