package com.java.dao;

import com.java.dto.Transaction;
import com.java.dto.User;

public interface AccountRepository {
	void transferMoney(Transaction transaction);
	double getBalance(User user);
}
