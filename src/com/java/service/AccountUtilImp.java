package com.java.service;

import com.java.dto.Transaction;
import com.java.dto.User;
import com.java.exception.DatabaseException;
import com.java.exception.AccNumNotFound;
import com.java.exception.InvalidBalanceException;

import org.apache.log4j.Logger;

import com.java.dao.AccountRepoImpl;

public class AccountUtilImp implements AccountUtility {
	static Logger logger = Logger.getLogger(AccountUtilImp.class);
	static AccountRepoImpl ari = new AccountRepoImpl();
	

	@Override
	public double getBalance(User user) {
		try {
			return ari.getBalance(user);
		} catch (AccNumNotFound a) {
			logger.info("No account number found for this user.");
		} catch (DatabaseException e) {
			logger.error("Database error. returning -1.");
		}
		return -1;
	}

	@Override
	public Transaction transferFund(int receiverNum, int accNum, double nominal) throws DatabaseException {

		Transaction transaction = new Transaction(accNum, receiverNum, nominal);
		ari.transferMoney(transaction);
		return transaction;

	}

	@Override

	public Transaction depositFund(int senderNum, int accNum, double nominal) throws DatabaseException {
		
		Transaction transaction = new Transaction();
		return null;
		// TODO Auto-generated method stub

	}

}
