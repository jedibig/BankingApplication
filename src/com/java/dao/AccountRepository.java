package com.java.dao;

import com.java.dto.Transaction;
import com.java.dto.User;
import com.java.exception.DatabaseException;
import com.java.exception.RepositoryException;

public interface AccountRepository {
	boolean checkAccountExist(int accNumber);
	Transaction transferMoney(Transaction transaction) throws RepositoryException;
	double getBalance(User user) throws RepositoryException;
}
