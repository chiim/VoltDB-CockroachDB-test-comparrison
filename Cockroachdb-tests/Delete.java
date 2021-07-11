package com.test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.postgresql.ds.PGSimpleDataSource;

public class Delete {
	void runIt(int ite) {
		PGSimpleDataSource ds = new PGSimpleDataSource();
	     ds.setServerNames(new String[]{"localhost"});
	     ds.setPortNumbers(new int[]{26257});
	     ds.setDatabaseName("bank");
	     ds.setUser("username");
	     ds.setPassword(null);
	     ds.setReWriteBatchedInserts(true);
	     ds.setApplicationName("trest");
	     int iterations = ite;
	     
	    
	    
	    try (Connection connection = ds.getConnection()) {
 	    connection.setAutoCommit(false);
 	    PreparedStatement pstmt = connection.prepareStatement("DELETE FROM accounts WHERE id = ?");
 	    
 	    for (int i = 0; i <= iterations; i+=1) {
 	    	pstmt.setInt(1, i);
     	    pstmt.addBatch();
 	    }
 	    long startTime = System.nanoTime();	
 	    pstmt.executeBatch();
 	    connection.commit();
 	    long endTime = System.nanoTime();
 	    long duration = (endTime - startTime);
 	    System.out.println("It took: " + (duration/1000000) + " milisec");
 	} catch (SQLException e) {
 	    System.out.printf("sql state = [%s]\ncause = [%s]\nmessage = [%s]\n",
 	                      e.getSQLState(), e.getCause(), e.getMessage());
 	}
	}
}
