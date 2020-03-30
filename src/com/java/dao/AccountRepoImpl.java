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
		String queryTransID = "select Banking_trans_seq.nextval as transID from dual";
		String querySender = "UPDATE Banking_Account set balance = balance - ? WHERE ACCNUMBER = ?";
		String queryGetBalance = "select balance from Banking_Account where accnumber = ?";
		String queryReceiver = "UPDATE Banking_Account set balance = balance + ? WHERE ACCNUMBER = ?";
		String queryCreateTrans = "insert into BANKING_TRANSACTION values (?,?,?,?)";

		try (Connection con = DbUtil.getConnection(); 
			PreparedStatement t = con.prepareStatement(queryTransID);
			PreparedStatement s = con.prepareStatement(querySender);
			PreparedStatement b = con.prepareStatement(queryGetBalance);
			PreparedStatement r = con.prepareStatement(queryReceiver);
			PreparedStatement c = con.prepareStatement(queryCreateTrans)) {
			
			logger.info("Getting transactionID");
			ResultSet rs = t.executeQuery();
			if(!rs.next()) {
				logger.error("sequence not set up correctly. problem with database. returning");
				throw new DatabaseException("faulty transID sequence");
			}
			int transID = rs.getInt("transID");

			
			logger.info("Updating balance of sender account");
			s.setDouble(1, transaction.getNominal());
			s.setInt(2, transaction.getSender());
			logger.debug("Querying " + s);

			int row_s = s.executeUpdate();
			if(row_s < 0) {
				throw new AccNumNotFound("Trying to update sender balance.");
			}
			
			logger.info("Getting balance of sender to make sure it's okay.");
			b.setInt(1, transaction.getSender());
			ResultSet rows = b.executeQuery();
			if (!rows.next()) {
				throw new AccNumNotFound("Trying to get sender balance.");
			}
			double currentSenderBalance = rows.getDouble("balance");
			logger.debug("Balance: " + currentSenderBalance );
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
			
			logger.info("Creating transaction on the database.");
			c.setInt(1, transID);
			c.setInt(2, transaction.getSender());
			c.setInt(3, transaction.getReceiver());
			c.setDouble(4, transaction.getNominal());
			if (c.executeUpdate() > 0) {
				logger.info("Successfull in transfering money.");
			}
			
			transaction.setTransID(transID);
			
			con.commit();
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
	
}
