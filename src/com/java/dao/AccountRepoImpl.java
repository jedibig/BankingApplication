package com.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.java.dto.JournalEntry;
import com.java.dto.User;
import com.java.exception.AccNumNotFound;
import com.java.exception.DatabaseException;
import com.java.exception.InvalidBalanceException;

public class AccountRepoImpl implements AccountRepository{
	static Logger logger = Logger.getLogger(AccountRepoImpl.class);
	
	@Override
	public int transferMoney(JournalEntry journalEntry) throws InvalidBalanceException, AccNumNotFound, DatabaseException {
				
		
		String queryTransID = "select transID_sequence.nextval as transID from dual";
		String queryGetBalance = "select balance, version from Banking_Account where accNumber = ?";
		String querySender = "update Banking_Account set balance = balance - ?, version = version + 1 where accNumber = ? and version = ?";
		String queryReceiver = "UPDATE Banking_Account set balance = balance + ?, version = version + 1 WHERE ACCNUMBER = ?";
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
				logger.error("Could not retrieve new transaction id from database");
				throw new DatabaseException("getting next value failed.");
			}
			
			int transId = rs.getInt("transID");

			logger.debug("Checking the balance and the version");
			s1.setInt(1, journalEntry.getSender());
			ResultSet rs1 = s1.executeQuery();
			if(!rs1.next()) {
				logger.error("Account not found when checking sender balance");
				throw new AccNumNotFound();
			}
			
			double balance = rs1.getDouble("balance");
			int version = rs1.getInt("version");
			logger.debug("balance is: " + balance);
			if(balance < journalEntry.getNominal()) {
				logger.error("Sender balance is less than amount to be transfered.");
				throw new InvalidBalanceException();
			}
			
			
			
			logger.debug("withdrawing money from the sender");
			s2.setDouble(1, journalEntry.getNominal());
			s2.setInt(2, journalEntry.getSender());
			s2.setInt(3, version);
			if (s2.executeUpdate() == 0) {
				logger.error("Account not found or version is invalid when withdrawing balance");
				throw new DatabaseException("Account number not found or invalid balance version");
			}

			
			logger.debug("depositing money to the receiver " + journalEntry.getReceiver());
			s3.setDouble(1, journalEntry.getNominal());
			s3.setInt(2, journalEntry.getReceiver());
			if (s3.executeUpdate() == 0) {
				logger.error("Account not found when depositing to receiver");
				throw new AccNumNotFound();
			}
			
			logger.debug("inserting transaction");
			s4.setInt(1, transId);
			s4.setInt(2, journalEntry.getSender());
			s4.setInt(3, journalEntry.getReceiver());
			s4.setDouble(4, journalEntry.getNominal());
			if (s4.executeUpdate() == 0) {
				logger.error("Got 0 rows when trying to insert new transaction");
				throw new DatabaseException("Failed to insert new transaction");
			}
			
			c.commit();
			
			return transId;
			
		} catch (SQLException e) {
			logger.error("Problem working with database to transfer fund. " + e.getMessage());
			throw new DatabaseException("Failed to perform SQL operation");
		}
	}

	@Override
	public double getBalance(User user) throws DatabaseException {
		String query = "SELECT * FROM BANKING_ACCOUNT WHERE ACCNUMBER = ?";
		try (Connection c = DbUtil.getConnection(); 
			PreparedStatement s = c.prepareStatement(query);) {
			
			logger.info("Getting current balance from user");
			
			s.setInt(1, user.getAccNum());
			ResultSet rows = s.executeQuery(query);

			if(rows.next()) {
				return rows.getDouble("balance");
			} else {
				logger.debug("no rows retrieved from querying balance.");
				throw new AccNumNotFound();
			}
		} catch (SQLException e) {
			logger.error("Problem retrieveing balance from db." + e.getMessage());
			throw new DatabaseException("Failed to perform SQL operation");
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
				logger.error("No corresponding account number found when getting account holder name.");
				throw new AccNumNotFound();
			}
			return name;
		} catch (SQLException e) {
			logger.error("Problem retrieving account name from db: " + e.getMessage());
			throw new DatabaseException("Failed to perform SQL operation");
		}

	}

}
