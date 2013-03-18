/******************************************************************************
PROBLEM :
	Assuming only two calls : send (non-blocking) and recv (blocking), execute
	a function:sum in a given number of servers to get and print sum of values
	of all servers.
	
	Implemented using following classes :
	- Messages.java : messaging subsystem between servers
	- Server.java : Individual server code
	- Sum.java : Initializes servers(main class)
	
SAMPLE OUTPUT 
(note that servers are initialized with random values, hence output may differ)
===============================================================================
Running experiment with 5 servers : 

Running Server 1 with a random value 7
Running Server 2 with a random value 18
Running Server 3 with a random value 24
Running Server 4 with a random value 92
Running Server 5 with a random value 37
Sum from server 1 is : 178
Sum from server 3 is : 178
Sum from server 5 is : 178
Sum from server 4 is : 178
Sum from server 2 is : 178

******************************************************************************/
package com.mynotes.puzzles.sum;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sum {

	public static void main(String args[]) {

		int numServers = 5;
		if (args.length > 0) {
			try {
				numServers = Integer.parseInt(args[0]);
			} catch (NumberFormatException nex) {
				// System.out.println("Unable to parse input arguments, " +
				// "defaulting number of servers");
			}
		}

		System.out.println("Running experiment with " + numServers
				+ " servers : \n");
		ExecutorService exec = Executors.newFixedThreadPool(numServers);
		Messages msgs = new Messages();

		for (int i = 1; i <= numServers; i++) {
			exec.execute(new Server(msgs, i, numServers));
		}

		exec.shutdown();
	}

}
