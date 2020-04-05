package com.java.service;

import com.java.dto.Transaction;
import com.java.dto.User;
import com.java.exception.DatabaseException;

import org.apache.log4j.Logger;

import com.java.dao.AccountRepoImpl;

public class AccountUtilImp implements AccountUtility {
	static Logger logger = Logger.getLogger(AccountUtilImp.class);
	static AccountRepoImpl ari = new AccountRepoImpl();
	

	@Override
	public double getBalance(User user) throws DatabaseException{
		return ari.getBalance(user);
	}

	@Override
	public Transaction transferFund(Transaction transaction) throws DatabaseException {

		int transId = ari.transferMoney(transaction);
		transaction.setTransID(transId);
		return transId == -1 ? null : transaction;

	}

	@Override

	public Transaction depositFund(Transaction transaction) throws DatabaseException {
		
		int transId = ari.transferMoney(transaction);
		transaction.setTransID(transId);
		return transId == -1 ? null : transaction;

	}

	@Override
	public String initiateTransfer(int accNum) throws DatabaseException {
		return ari.getAccountName(accNum);
	}

}
