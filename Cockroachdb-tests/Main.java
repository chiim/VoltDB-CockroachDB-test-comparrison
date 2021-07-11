package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.sql.DataSource;
import org.postgresql.ds.PGSimpleDataSource;

public class Main {
	public static void main(String[] args) throws SQLException, IOException {
		 PGSimpleDataSource ds = new PGSimpleDataSource();
	     ds.setServerNames(new String[]{"localhost"});
	     ds.setPortNumbers(new int[]{26257,26258,26259});
	     ds.setDatabaseName("bank");
	     ds.setUser("username");
	     ds.setPassword(null);
	     ds.setReWriteBatchedInserts(true);
	     ds.setApplicationName("trest");
	    
	     
	    
	    String inputOne = "1";
	    String inputTwo = "2";
	    String inputThree = "3";
	    String inputFour = "4";
	    String inputFive = "5";
	    System.out.println("Select number of iterations: ");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	    String name = reader.readLine();
	    int iterations = Integer.parseInt(name);
	    System.out.println("1 for insert, 2 for read, 3 for update, 4 for delete, 5 to drop table:  ");
	    
	    reader = new BufferedReader(new InputStreamReader(System.in));
	    name = reader.readLine().toString();
	    if (name.equals(inputOne)) {
	    	try (Connection connection = ds.getConnection()) {
	    	    connection.setAutoCommit(false);
	    	    PreparedStatement pstmt1 = connection.prepareStatement("CREATE TABLE IF NOT EXISTS accounts (id INT PRIMARY KEY, balance STRING)");
	    	    pstmt1.execute();
	    	    connection.commit();
	    	    PreparedStatement pstmt = connection.prepareStatement("INSERT INTO accounts (id, balance) VALUES (?, ?)");
	    	    
	    	    for (int i = 0; i <= iterations; i+=1) {
	    	    	pstmt.setInt(1, i);
	    	    	Integer balance = (50+i);
	        	    pstmt.setInt(2, balance);
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
	    
	    else if(name.equals(inputTwo)) {
	    	Read read = new Read();
	    	read.runIt();
	    }
	    
	    else if(name.equals(inputThree)) {
	    	Update update = new Update();
	    	update.runIt(iterations);
	    }
	    
	    else if(name.equals(inputFour)) {
	    	Delete delete = new Delete();
	    	delete.runIt(iterations);
	    }
	    else if(name.equals(inputFive)) {
	    	try (Connection connection = ds.getConnection()) {
	    	    connection.setAutoCommit(false);
	    	    PreparedStatement pstmt1 = connection.prepareStatement("DROP TABLE accounts");
	    	    pstmt1.execute();
	    	    connection.commit();
	    	    
	
	    	} catch (SQLException e) {
	    	    System.out.printf("sql state = [%s]\ncause = [%s]\nmessage = [%s]\n",
	    	                      e.getSQLState(), e.getCause(), e.getMessage());
	    	}
	    }
	}

}
