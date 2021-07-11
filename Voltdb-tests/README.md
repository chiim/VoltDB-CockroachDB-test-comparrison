HOW TO RUN THE VOLTDB TEST SERVER WITH DATABASE

Note: This setup has only been tested for Linux-based systems. We can therefore not guarantee that it works for windows or mac.

----- HOW TO START THE VOLTDB TEST SERVER -----
1. Go to voltdbSetup
2. Open voltdb.properties to edit the operationcount variable to choose how many operations should be performed. 
3. run the shell script "multiple-clusters-on-one-device.sh" to start the VoltDB servers. When doing this for the first time, you may need to check the logs for extra setup procedures.

The folder VoltdbData contains all of the different tables consisting of data with certain datatypes. Follow the voltDB documentation to see how to insert them into the database.

NOTE: There is no table for timestamp because they were created using the testprogram. You can simply create the rows necessary there.