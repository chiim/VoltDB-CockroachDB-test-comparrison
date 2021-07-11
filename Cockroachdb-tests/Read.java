package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.postgresql.ds.PGSimpleDataSource;

public class Read {
	void runIt(){
		PGSimpleDataSource ds = new PGSimpleDataSource();
	    ds.setServerNames(new String[]{"localhost"});
	    ds.setPortNumbers(new int[]{26257});
	    ds.setDatabaseName("bank");
	    ds.setUser("username");
	    ds.setPassword(null);
	    ds.setReWriteBatchedInserts(true);
	    ds.setApplicationName("trest");
	    
	    try (Connection connection = ds.getConnection()) {
		    long startTime = System.nanoTime();
	   	    Statement stmt = connection.createStatement();
	   	    ResultSet rs = stmt.executeQuery("SELECT id, balance FROM accounts");
	   	    
	   	    while (rs.next()) {
	   	    	int id = rs.getInt(1);
	   	    	Float bal = rs.getFloat(2);
	   	    	System.out.printf("ID: %10s\nBalance: %5s\n", id, bal);
	   	    }
	   	    
	   	    	
	   	    long endTime = System.nanoTime();
	   	    long duration = (endTime - startTime);
	   	    System.out.println("It took: " + (duration/1000000) + " milisec");
	   	} catch (SQLException e) {
	   	    System.out.printf("sql state = [%s]\ncause = [%s]\nmessage = [%s]\n",
	   	                      e.getSQLState(), e.getCause(), e.getMessage());
	   	
		}
	}
}
