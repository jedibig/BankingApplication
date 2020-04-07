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
	public int transferMoney(Transaction transaction) throws DatabaseException {
		
		logger.debug("sending money from " + transaction.getSender() +" to " + transaction.getReceiver());
		
		String queryTransID = "select transID_sequence.nextval as transID from dual";
		String queryGetBalance = "select balance, version from Banking_Account where accNumber = ?";
		String querySender = "update Banking_Account set balance = balance - ?, version = version + 1 where accNumber = ? and version = ?";
		String queryReceiver = "UPDATE Banking_Account set balance = balance + ? WHERE ACCNUMBER = ?";
		String queryCreateTrans = "insert into Banking_Transaction (transactionID, senderID, receiverID, nominal) values (?, ?, ?, ?)";

		
		try (Connection c = DbUtil.getConnection(); 
			PreparedStatement s = c.prepareStatement(queryTransID);
			PreparedStatement s1 = c.prepareStatement(queryGetBalance);
			PreparedStatement s2 = c.prepareStatement(querySender);
			PreparedStatement s3 = c.prepareStatement(queryReceiver);
			PreparedStatement s4 = c.prepareStatement(queryCreateTrans);){
			
			logger.debug("getting new transaction id");
			ResultSet rs = s.executeQuery();
			if(!rs.next()) {
				return -1;
			}
			
			int transId = rs.getInt("transID");

			logger.debug("Checking the balance and the version");
			s1.setInt(1, transaction.getSender());
			ResultSet rs1 = s1.executeQuery();
			if(!rs1.next())
				return -1;
			
			double balance = rs1.getDouble("balance");
			int version = rs1.getInt("version");
			logger.debug("balance is: " + balance);
			if(balance < transaction.getNominal())
				throw new InvalidBalanceException("");
			
			
			
			logger.debug("withdrawing money from the sender");
			s2.setDouble(1, transaction.getNominal());
			s2.setInt(2, transaction.getSender());
			s2.setInt(3, version);
			if (s2.executeUpdate() == 0) {
				return -1;
			}

			
			logger.debug("depositing money to the receiver " + transaction.getReceiver());
			s3.setDouble(1, transaction.getNominal());
			s3.setInt(2, transaction.getReceiver());
			if (s3.executeUpdate() == 0) {
				return -1;
			}
			
			logger.debug("inserting transaction");
			s4.setInt(1, transId);
			s4.setInt(2, transaction.getSender());
			s4.setInt(3, transaction.getReceiver());
			s4.setDouble(4, transaction.getNominal());
			if (s4.executeUpdate() == 0) {
				return -1;
			}
			
			c.commit();
			
			return transId;
			
		} catch (SQLException e) {
			logger.error("Problem working with database. " + e.getMessage());
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

	@Override
	public String getAccountName(int accNumber) throws DatabaseException {
		String query = "select name from Banking_User join Banking_Account BA on Banking_User.accNumber = BA.accNumber where BA.accNumber = ?";
		try (Connection c = DbUtil.getConnection(); 
			 PreparedStatement s = c.prepareStatement(query);) {

			s.setInt(1, accNumber);

			ResultSet rs = s.executeQuery();
			String name = null;
			if (rs.next()) {
				name = rs.getString("name");
			} else {
				throw new AccNumNotFound("");
			}
			return name;
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DatabaseException("Fatal Error with database.");
		}

	}

}
