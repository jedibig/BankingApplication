package com.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.java.dto.Transaction;
import com.java.dto.User;
import com.java.exception.AccNumNotFound;
import com.java.exception.DatabaseException;
import com.java.exception.InvalidBalanceException;

public class AccountRepoImpl implements AccountRepository{
	static Logger logger = Logger.getLogger(AccountRepoImpl.class);
	
	@Override
	public void transferMoney(Transaction transaction) throws DatabaseException {
		String querySender = "UPDATE Banking_Account set balance = balance - ? WHERE ACCNUMBER = ?";
		String queryReceiver = "UPDATE Banking_Account set balance = balance + ? WHERE ACCNUMBER = ?";

		try (Connection c = DbUtil.getConnection(); 
			PreparedStatement s = c.prepareStatement(querySender);
			PreparedStatement r = c.prepareStatement(querySender);) {
			
			logger.info("Updating balance of sender account");
			s.setDouble(1, transaction.getNominal());
			s.setLong(2, transaction.getSender());
			int row_s = s.executeUpdate(querySender);
			if(row_s < 0) {
				throw new AccNumNotFound("Trying to update sender balance.");
			}
			
			
			User sender = new User();
			sender.setAccNum(transaction.getSender());
			double currentSenderBalance = getBalance(sender);
			
			if(currentSenderBalance < 0)
				throw new InvalidBalanceException("Not enough balance from sender.");
			
			
			logger.info("Updating balance of receiver account");
			s.setDouble(1, transaction.getNominal());
			s.setLong(2, transaction.getReceiver());
			int row_r = s.executeUpdate(queryReceiver);
			if(row_r < 0) {
				throw new AccNumNotFound("Trying to update receiver balance.");
			}
			
			c.commit();
		} catch (SQLException e) {
			logger.error("Problem transfering money." + e.getMessage());
			throw new DatabaseException("Failed to do one of the update query.");
		}
	}

	@Override
	public double getBalance(User user) throws DatabaseException {
		String query = "SELECT * FROM BANKING_ACCOUNT WHERE ACCNUM = " + user.getAccNum();
		try (Connection c = DbUtil.getConnection(); 
				Statement s = c.createStatement();) {
			
			logger.info("Getting current balance from user");
			ResultSet rows = s.executeQuery(query);
			if(rows.next()) {
				return rows.getDouble("balance");
			} else {
				throw new AccNumNotFound("Account number is wrong.");
			}
		} catch (SQLException e) {
			logger.error("Problem retrieveing balance from db." + e.getMessage());
			throw new DatabaseException("SQL Exception caught");
		}
	}
	
}
