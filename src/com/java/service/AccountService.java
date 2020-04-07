package com.java.service;

import com.java.dto.JournalEntry;

import com.java.dto.User;
import com.java.exception.*;

public interface AccountService{
	double getBalance(User user) throws DatabaseException;
	String initiateTransfer(int accNum) throws AccNumNotFound, DatabaseException;
	JournalEntry transferFund(JournalEntry journalEntry) throws InvalidBalanceException, AccNumNotFound, DatabaseException;
	JournalEntry depositFund(JournalEntry journalEntry) throws InvalidBalanceException, AccNumNotFound, DatabaseException;
}
