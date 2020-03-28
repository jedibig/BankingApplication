package com.java.dao;

import java.sql.Connection;
import java.sql.SQLException;


import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class DbUtil{
		//Start of application: Keep 50 open connections in a pool
		//Whenever you need a connection, it will pull up a connection from a pool
		
		//User1 gets-> Connection1 when done-> Close connection
		//User51 tries to get a connection -> wait for sometime for the connection to be freed: 1 second wait
		//User1: 10 mins later, he has not released the connection: Cause timeout: Cause timeout
		//tomcat-dbcp: manages connection pool
		
		private static BasicDataSource ds = new BasicDataSource();
		
		static{
			ds.setUrl(DbPropertiesUtil.getUrl());
			ds.setUsername(DbPropertiesUtil.getUsername());
			ds.setPassword(DbPropertiesUtil.getPassword());
			ds.setDefaultAutoCommit(false);
			ds.setDriverClassName(DbPropertiesUtil.getDriver()); //by-default set as per the driver library in classpath
			ds.setMaxIdle(5); //If no request comes for long, how many open connections are waiting
			ds.setMaxTotal(100); //If many requests are coming, how many maximum connections you want
			ds.setMaxWaitMillis(2000); //101th user is asking for connection, 2 seconds
			ds.setMaxConnLifetimeMillis(5000); 
		}
		
		public static Connection getConnection() throws SQLException{
			return ds.getConnection();
		}
	}