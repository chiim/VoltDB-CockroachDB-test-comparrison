package voltDBClient;

import java.io.IOException;
import java.util.Scanner;
import  org.voltdb.types.TimestampType;

import org.voltdb.VoltTable;
import org.voltdb.client.*;

public class VoltDBMain {

	public static void main(String[] args) {
		showMenu();
	}
	
	public static void showMenu() {
		
		Scanner scanner = new Scanner(System.in);

		while(true) {
			
		System.out.println("\n--------------------------------------------------");
		System.out.println("Welcome to the benchmark program. Please choose what you wish to benchmark.");
		System.out.println("--------------------------------------------------");
		System.out.println("1. Create\n2. Read\n3. Update\n4. Delete");
		String input = scanner.next();
		int numberOfOperations = getNumberOfOperationsFromUser(scanner);

		switch(input) {
			case "1":
				benchmarkOperation("Insert", numberOfOperations);
				break;
			case "2":
				benchmarkOperation("Select", numberOfOperations);
				break;
			case "3":
				benchmarkOperation("Update", numberOfOperations);
				break;
			case "4":
				benchmarkOperation("Delete", numberOfOperations);
				break;
			default:
				System.out.println("Testing how long TimestampType class takes to create.");
				long startTime = System.nanoTime() / 1000000;
				
				TimestampType timestamp = new TimestampType();
				
				long endTime = System.nanoTime() / 1000000;
				
				long elapsedTime = endTime - startTime;
				System.out.println("It took " + elapsedTime + " seconds");
			}
		}
	}
	
	public static int getNumberOfOperationsFromUser(Scanner scanner) {
		System.out.println("\n--------------------------------------------------");
		System.out.println("Please choose how many calls should be made to the database.");
		System.out.println("--------------------------------------------------");
		System.out.println("1. 10 000\n2. 100 000\n3. 1 000 000\n4. 100 (developer)");
		String input = scanner.next();
		
		switch(input) {
			case "1":
				return 10000;
			case "2":
				return 100000;
			case "3":
				return 1000000;
			case "4":
				return 100;
			default:
				return 10000;
		}
	}

	public static void benchmarkOperation(String operation, int numberOfOperations) {

		Client client = null;
		ClientConfig config = new ClientConfig("", "");
		config.setTopologyChangeAware(true); // Automatically connect to all nodes in the cluster when connection to
												// first node is established.
		try {
			System.out.println("Establishing connection...");
			client = ClientFactory.createClient(config);
			client.createConnection("localhost", 21212);

		} catch (java.io.IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		System.out.println("Successfully connected to the cluster.");
		
		System.out.println("Number of operations to be performed: " + numberOfOperations);
		System.out.println("Starting test on " + operation + " operation...");

		beginTest(operation, numberOfOperations, client);

	}

	public static void beginTest(String operation, int numberOfOperations, Client client) {
		VoltTable[] results = null;
		long startTime = 0;
		long endTime = 0;
		long duration = 0;
		long averageTime = 0;
		
		switch (operation) {
		case "Select":

			try {
					startTime = System.nanoTime() / 1000000;
					
					for (int testNumber = 1; testNumber <= numberOfOperations; testNumber++) {
						client.callProcedure(new dbCallback(startTime, testNumber, numberOfOperations), "Select", 2700.40378 + testNumber);
					}
					System.out.println("All calls have been sent to the database. Now waiting for returned data.");
					Thread.sleep(20000); // Waiting for the database calls to finish.
				
			} catch (Exception e) {
				System.out.println("Test failed.");
				e.printStackTrace();
				System.exit(-1);
			}
			break;
		case "Insert":
			try {
				
				
				startTime = System.nanoTime() / 1000000;
				for (int testNumber = 1; testNumber <= numberOfOperations; testNumber++) {
					client.callProcedure(new dbCallback(startTime, testNumber, numberOfOperations), "Insert", new TimestampType());
				}
				System.out.println("All calls have been sent to the database. Now waiting for returned data.");
				Thread.sleep(60000); // Waiting for the database calls to finish.
			}
			catch (Exception e) {
				System.out.println("Test failed.");
				e.printStackTrace();
				System.exit(-1);
			}
			break;
		case "Update":
			try {
					
				startTime = System.nanoTime() / 1000000;
					for (int testNumber = 1; testNumber <= numberOfOperations; testNumber++) {
						client.callProcedure(new dbCallback(startTime, testNumber, numberOfOperations), 
								"Update",
								new TimestampType("2021-04-20 14:28:24.888000"),
								new TimestampType("2222-04-20 14:28:24.888000"));
					}
					System.out.println("All calls have been sent to the database. Now waiting for returned data.");
					Thread.sleep(20000); // Waiting for the database calls to finish.		
					
			} catch (Exception e) {
				System.out.println("Test failed.");
				e.printStackTrace();
				System.exit(-1);
			}
			break;
		case "Delete":
			try {
				startTime = System.nanoTime() / 1000000;
				for (int testNumber = 1; testNumber <= numberOfOperations; testNumber++) {
					client.callProcedure(new dbCallback(startTime, testNumber, numberOfOperations), "Delete", 2700.40378 + testNumber);
				}
				System.out.println("All calls have been sent to the database. Now waiting for returned data.");
				Thread.sleep(20000); // Waiting for the database calls to finish.	
				} 
			catch (Exception e) {
				System.out.println("Test failed.");
				e.printStackTrace();
				System.exit(-1);
			}
			break;
		}
	}
}