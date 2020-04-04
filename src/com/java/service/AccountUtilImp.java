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

//	@Override
//	public int transferFund(Transaction transaction) {
//		try {
//			if (transaction.getNominal() != 0 && transaction.getReceiver() != 0 && transaction.getNominal() >= 0) {
//				ari.transferMoney(transaction);
//			} else {
//				logger.error("Invalid parameter.");
//				return 3;
//			}
//			
//		} catch (DatabaseException e){
//			if (e instanceof AccNumNotFound){
//				logger.info("No corresponding account number found.");
//				return 1;
//			}
//			else if (e instanceof InvalidBalanceException) {
//				logger.info("Insufficient Balance");
//				return 2;
//			}
//			else {
//				logger.error("Database error.");
//				return -1;
//			}
//		}
//		return 0;
//	}
//
//	@Override
//	public int depositFund(Transaction transaction) {
//		try {
//			if (transaction.getNominal() != 0 && transaction.getReceiver() != 0 && transaction.getNominal() >= 0) {
//				ari.transferMoney(transaction);
//			} else {
//				logger.error("Invalid parameter.");
//				return 3;
//			}
//		} catch (DatabaseException e){
//			if (e instanceof AccNumNotFound){
//				logger.info("No corresponding account number found.");
//				return 1;
//			}
//			else if (e instanceof InvalidBalanceException) {
//				logger.info("Insufficient Balance");
//				return 2;
//			}
//			else {
//				logger.error("Database error.");
//				return -1;
//			}
//		}
//		return 0;
//	}

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

@Override
public void transferFund(Transaction transaction) throws DatabaseException {
	// TODO Auto-generated method stub
	
}

@Override
public void depositFund(Transaction transaction) throws DatabaseException {
	// TODO Auto-generated method stub
	
}

}
