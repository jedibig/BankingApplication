package com.java.dao;

import java.io.IOException;
import java.util.Properties;

public class DbPropertiesUtil {
	static Properties p = new Properties();
	static String url;
	static String driver;
	static String username;
	static String password;
	
	static {
		try {
			p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties"));
			url = p.getProperty("url");
			username = p.getProperty("username");
			password = p.getProperty("password");
			driver = p.getProperty("driver");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static String getUrl() {
		return url;
	}

	static String getDriver() {
		return driver;
	}

	static String getUsername() {
		return username;
	}

	static String getPassword() {
		return password;
	}
}
