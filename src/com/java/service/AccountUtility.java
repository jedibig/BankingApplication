package com.java.service;

import com.java.dto.Transaction;

import com.java.dto.User;
import com.java.exception.*;

public interface AccountUtility{
	double getBalance(User user) throws DatabaseException;
	Transaction transferFund(int receiverNum, int accNum, double nominal) throws DatabaseException;
	Transaction depositFund(int senderNum, int accNum, double nominal) throws DatabaseException;
}
