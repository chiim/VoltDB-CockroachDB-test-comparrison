package voltDBClient;

import org.voltdb.VoltTable;
import org.voltdb.client.ClientResponse;
import org.voltdb.client.ProcedureCallback;

public class dbCallback implements ProcedureCallback {
	private long _startTime;
	private int _testNumber;
	private int _numberOfOperations;
	
	public dbCallback(long startTime, int testNumber, int numberOfOperations) {
		_startTime = startTime;
		_testNumber = testNumber;
		_numberOfOperations = numberOfOperations;
	}

	@Override
	public void clientCallback(ClientResponse clientResponse) throws Exception {		
		
		if(clientResponse.getStatus() != ClientResponse.SUCCESS) {
			System.err.println(clientResponse.getStatusString());
		}
		else if(_testNumber == _numberOfOperations){
			long endTime = System.nanoTime() / 1000000;
			
			System.out.println("Control check last test number: " + _testNumber);
			long duration = (endTime - _startTime);
			System.out.println("Test completed. It took " + duration + " milliseconds.");
		}		
	}
}
