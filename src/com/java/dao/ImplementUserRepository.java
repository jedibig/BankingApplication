package com.java.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.java.dto.User;

public class ImplementUserRepository implements UserRepository{

	@Override
	public boolean insertUser(User user) {
		
		try (Connection c = DbUtil.getConnection(); Statement s = c.createStatement();) {
			int rows = s.executeUpdate(" insert into user values ( '" + username + "', '" + password + "')");
			System.out.println(rows + " row(s) inserted.");
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
