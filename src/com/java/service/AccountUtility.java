package com.java.service;

import com.java.dto.Transaction;

import com.java.dto.User;
import com.java.exception.*;

public interface AccountUtility{
	void transferFund(Transaction transaction) throws DatabaseException;
	void depositFund(Transaction transaction) throws DatabaseException;
	double getBalance(User user) throws DatabaseException;
}
