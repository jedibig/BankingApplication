package com.java.service;

import com.java.dto.Transaction;
import com.java.dto.User;
import com.java.exception.DatabaseException;
import com.java.exception.AccNumNotFound;
import com.java.exception.InvalidBalanceException;


import org.apache.log4j.Logger;

import com.java.dao.AccountRepoImpl;

public class AccountUtilImp implements AccountUtility{
	static Logger logger = Logger.getLogger(AccountUtilImp.class);
	static AccountRepoImpl ari = new AccountRepoImpl();

	@Override
	public boolean transferFund(Transaction transaction) {
		try {
			if (transaction.getNominal() != 0 && transaction.getReceiver() != 0 && transaction.getNominal() >= 0) {
				ari.transferMoney(transaction);
				return true;
			} else {
				logger.error("Invalid parameter.");
			}
			
		} catch (DatabaseException e){
			if (e instanceof AccNumNotFound)
				logger.info("No corresponding account number found.");
			else if (e instanceof InvalidBalanceException)
				logger.info("Insufficient Balance");
			else
				logger.error("Database error.");
			
		}
		return false;
	}

	@Override
	public boolean depositFund(Transaction transaction) {
		try {
			if (transaction.getNominal() != 0 && transaction.getReceiver() != 0 && transaction.getNominal() >= 0) {
				ari.transferMoney(transaction);
				return true;
			} else {
				logger.error("Invalid parameter.");
			}
			
		} catch (DatabaseException e){
			if (e instanceof AccNumNotFound)
				logger.info("No corresponding account number found.");
			else if (e instanceof InvalidBalanceException)
				logger.info("Insufficient Balance");
			else
				logger.error("Database error.");
			
		}
		return false;
	}

	@Override
	public double getBalance(User user) {
		try {
			return ari.getBalance(user);
		} catch(AccNumNotFound a) {
			logger.info("No account number found for this user.");
		} catch(DatabaseException e) {
			logger.error("Database error. returning -1.");
		}
		return -1;
	}

}
