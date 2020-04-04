package com.java.dao;

import com.java.dto.Transaction;
import com.java.dto.User;
import com.java.exception.DatabaseException;

public interface AccountRepository {
	void transferMoney(Transaction transaction) throws Exception;
	double getBalance(User user) throws DatabaseException;
}
