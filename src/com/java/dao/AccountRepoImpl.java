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
			PreparedStatement r = c.prepareStatement(queryReceiver);) {
			
			logger.info("Updating balance of sender account");
			s.setDouble(1, transaction.getNominal());
			s.setInt(2, transaction.getSender());
			logger.debug("Querying " + s);

			int row_s = s.executeUpdate();
			if(row_s < 0) {
				throw new AccNumNotFound("Trying to update sender balance.");
			}
			
			
			User sender = new User();
			sender.setAccNum(transaction.getSender());
			double currentSenderBalance = getBalance(sender);
			
			if(currentSenderBalance < 0)
				throw new InvalidBalanceException("Not enough balance from sender.");
			
			
			logger.info("Updating balance of receiver account");
			r.setDouble(1, transaction.getNominal());
			r.setLong(2, transaction.getReceiver());
			logger.debug("Querying " + s.toString());

			int row_r = r.executeUpdate();
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
		String query = "SELECT * FROM BANKING_ACCOUNT WHERE ACCNUMBER = " + user.getAccNum();
		try (Connection c = DbUtil.getConnection(); 
				Statement s = c.createStatement();) {
			
			logger.info("Getting current balance from user");
			logger.debug("executing query " + query);
			ResultSet rows = s.executeQuery(query);
			logger.debug("query is executed.");

			if(rows.next()) {
				logger.debug("got a row.");
				return rows.getDouble("balance");
			} else {
				logger.debug("no rows retrieved.");
				throw new AccNumNotFound("Account number is wrong.");
			}
		} catch (SQLException e) {
			logger.error("Problem retrieveing balance from db." + e.getMessage());
			throw new DatabaseException("SQL Exception caught");
		}
	}
	
}
