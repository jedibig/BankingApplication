package com.java.service;

import com.java.dto.Transaction;

import com.java.dto.User;
import com.java.exception.*;

public interface AccountUtility{
	double getBalance(User user) throws DatabaseException;
	String initiateTransfer(int accNum) throws DatabaseException;
	Transaction transferFund(Transaction transaction) throws DatabaseException;
	Transaction depositFund(Transaction transaction) throws DatabaseException;
}
