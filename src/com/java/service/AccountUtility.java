package com.java.service;

import com.java.dto.Transaction;
import com.java.dto.User;

public interface AccountUtility{
	int transferFund(Transaction transaction);
	int depositFund(Transaction transaction);
	double getBalance(User user);
}
