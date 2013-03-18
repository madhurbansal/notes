/******************************************************************************
PROBLEM :
	Assuming only two calls : send (non-blocking) and recv (blocking), execute
	a function:sum in a given number of servers to get and print sum of values
	of all servers.
	
	Implemented using following classes :
	- Messages.java : messaging subsystem between servers
	- Server.java : Individual server code
	- Sum.java : Initializes servers(main class)
	
SAMPLE OUTPUT :
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

import java.util.HashMap;

public class Messages {

	// a key value map to store and retrieve messages
	private HashMap<String, Integer> kvMap = new HashMap<String, Integer>();

	/**
	 * Non blocking call to send message 'from' a node intended for 'to' node
	 * @param from Message originator id
	 * @param to Message intended receiver id
	 * @param val message value
	 */
	public synchronized void send(int from, int to, int val) {
		String key = from + ":" + to;
		kvMap.put(key, val);

		notifyAll();
	}

	/**
	 * Blocking call to receive a message 'from' a node intended for 'to' node.
	 * @param from Message originator id
	 * @param to Message intended receiver id
	 * @return message value
	 */
	public synchronized int recv(int from, int to) {

		String key = from + ":" + to;
		try {
			while (kvMap.get(key) == null) {
				wait();
			}
		} catch (InterruptedException iex) {
		}
		
		return kvMap.remove(key);

	}

}
