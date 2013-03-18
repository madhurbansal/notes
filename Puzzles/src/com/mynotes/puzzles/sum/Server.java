/*******************************************************************************
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

*******************************************************************************/

package com.mynotes.puzzles.sum;

public class Server implements Runnable {

	private Messages msgs;
	private int id;
	private int totalServers;
	private int val;

	public Server(Messages msgs, int id, int totalServers) {
		this.msgs = msgs;
		this.id = id;
		this.totalServers = totalServers;
		this.val = (int) (Math.random() * 100);
	}

	@Override
	public void run() {
		System.out.println("Running Server " + id + " with a random value "
				+ val);
		System.out.println("Sum from server " + id + " is : " + sum());
	}

	private void sendMsgsTo(int child, int val) {
		// always check the child validity before sending a message
		if (child <= totalServers)
			msgs.send(id, child, val);
	}

	private int recvMsgsFrom(int child) {
		// always check the child validity before receiving a message
		if (child <= totalServers) {
			return msgs.recv(child, id);
		} else
			return 0;
	}

	// get the sum of all the servers' values.
	private int sum() {

		int lChild = (id * 2);
		int rChild = (id * 2) + 1;
		int sum = 0;

		if (id == 1) {
			// root node

			// recv from children
			sum = recvMsgsFrom(lChild);
			sum += recvMsgsFrom(rChild);
			sum += val;

			// send the final sum to children
			sendMsgsTo(lChild, sum);
			sendMsgsTo(rChild, sum);
		}

		else if ((id * 2) > totalServers) {
			// leaf nodes

			// send to parent
			sendMsgsTo(id / 2, val);

			// recv the final sum from parent
			sum = recvMsgsFrom(id / 2);
		}

		else {
			// any other node

			// recv from children
			sum = recvMsgsFrom(lChild);
			sum += recvMsgsFrom(rChild);
			sum += val;

			// send to parent
			sendMsgsTo(id / 2, sum);

			// recv from parent
			sum = recvMsgsFrom(id / 2);

			// send it back to children
			sendMsgsTo(lChild, sum);
			sendMsgsTo(rChild, sum);

		}

		return sum;
	}

}
