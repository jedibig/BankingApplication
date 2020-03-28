package com.java.service;

import com.java.dto.Transaction;
import com.java.dto.User;

public interface AccountUtility{
	boolean transferFund(Transaction transaction);
	boolean depositFund(Transaction transaction);
	double getBalance(User user);
}
