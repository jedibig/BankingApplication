package com.java.dao;

import com.java.dto.Transaction;
import com.java.dto.User;
import com.java.exception.DatabaseException;

public interface AccountRepository {
	String getAccountName(int accNumber) throws DatabaseException;
	int transferMoney(Transaction transaction) throws DatabaseException;
	double getBalance(User user) throws DatabaseException;
}


