package com.java.service;

import com.java.dto.JournalEntry;
import com.java.dto.User;
import com.java.exception.DatabaseException;

import org.apache.log4j.Logger;

import com.java.dao.AccountRepoImpl;

public class AccountServiceImpl implements AccountService {
	static Logger logger = Logger.getLogger(AccountServiceImpl.class);
	static AccountRepoImpl ari = new AccountRepoImpl();
	

	@Override
	public double getBalance(User user) throws DatabaseException{
		return ari.getBalance(user);
	}

	@Override
	public JournalEntry transferFund(JournalEntry journalEntry) throws DatabaseException {

		int transId = ari.transferMoney(journalEntry);
		journalEntry.setTransID(transId);
		return transId == -1 ? null : journalEntry;  //TODO use optional objects to be returned instead of null

	}

	@Override

	public JournalEntry depositFund(JournalEntry journalEntry) throws DatabaseException {
		
		int transId = ari.transferMoney(journalEntry);
		journalEntry.setTransID(transId);
		return transId == -1 ? null : journalEntry;

	}

	@Override
	public String initiateTransfer(int accNum) throws DatabaseException {
		return ari.getAccountName(accNum);
	}

}
