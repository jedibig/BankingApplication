package com.java.dao;

import com.java.dto.JournalEntry;
import com.java.dto.User;
import com.java.exception.AccNumNotFound;
import com.java.exception.DatabaseException;

public interface AccountRepository {
	String getAccountName(int accNumber) throws AccNumNotFound, DatabaseException;
	int transferMoney(JournalEntry journalEntry) throws DatabaseException;
	double getBalance(User user) throws AccNumNotFound, DatabaseException;
}


