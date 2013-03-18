/******************************************************************************
PROBLEM :
	Method to get the number of ways to get an amount from a bag containing
	infinite coins of given denominations
	
SAMPLE OUTPUT : (with DEBUG true)
===============================================================================
	Calling numPaths for : 4
		Calling numPaths for : 3
			Calling numPaths for : 2
				Calling numPaths for : 1
					Calling numPaths for : 0
					Done
				Done
			Done
		Done
		Calling numPaths for : 2
			Calling numPaths for : 1
				Calling numPaths for : 0
				Done
			Done
			Calling numPaths for : 0
			Done
		Done
		Calling numPaths for : 1
			Calling numPaths for : 0
			Done
		Done
		Calling numPaths for : 0
		Done
	Done

Ways to get the amount 4 are : [[1, 1, 1, 1], [1, 1, 2], [2, 2], [1, 3], [4]]
Number of ways to build amount 4 : 5

===============================================================================
SAMPLE OUTPUT (with DEBUG false)
===============================================================================

Given Denominations : [4 5 10 3 1 2 ]

Ways to get the amount 0 are : []
Number of ways to build amount 0 : 0



Ways to get the amount 1 are : [[1]]
Number of ways to build amount 1 : 1



Ways to get the amount 2 are : [[1, 1], [2]]
Number of ways to build amount 2 : 2



Ways to get the amount 3 are : [[1, 1, 1], [1, 2], [3]]
Number of ways to build amount 3 : 3



Ways to get the amount 4 are : [[1, 1, 1, 1], [1, 1, 2], [2, 2], [1, 3], [4]]
Number of ways to build amount 4 : 5



Ways to get the amount 5 are : [[1, 1, 1, 1, 1], [1, 1, 1, 2], [1, 2, 2], [1, 1, 3], [2, 3], [1, 4], [5]]
Number of ways to build amount 5 : 7


******************************************************************************/


package com.mynotes.puzzles.count;

import java.util.ArrayList;
import java.util.Arrays;

public class Count {

	private int[] coinDenominations;
	private static final boolean DEBUG = false;

	Count(int[] inputAr) {
		this.coinDenominations = inputAr;

		// copy given array to our array
		this.coinDenominations = new int[inputAr.length];
		for (int i = 0; i < inputAr.length; i++) {
			coinDenominations[i] = inputAr[i];
		}

		// sort the array
		Arrays.sort(coinDenominations);
	}

	/**
	 * Get the number of ways to get an amount from a bag containing infinite
	 * coins of given denominations
	 * 
	 * @param amount
	 * @return number of ways
	 */
	public int numWays(int amount) {
		ArrayList<ArrayList<Integer>> paths = numPaths(amount, 0, "");
		System.out.println("\nWays to get the amount " + amount + " are : "
				+ paths);
		return paths.size();
	}

	private void append(int pCoin, ArrayList<ArrayList<Integer>> paths) {

		// append pCoin to the elements of given list
		if (pCoin == 0)
			return;
		for (ArrayList<Integer> path : paths) {
			path.add(pCoin);
		}
	}

	private ArrayList<ArrayList<Integer>> numPaths(int amount, int pCoin,
			String TAB) {

		TAB += "\t";
		if (DEBUG)
			System.out.println(TAB + "Calling numPaths for : " + amount);

		ArrayList<ArrayList<Integer>> paths, retPaths;
		ArrayList<Integer> path;

		// Base condition
		if (amount == 0) {
			paths = new ArrayList<ArrayList<Integer>>();
			path = new ArrayList<Integer>();
			if (pCoin != 0)paths.add(path);
			append(pCoin, paths);

			if (DEBUG)
				System.out.println(TAB + "Done");

			return paths;
		}

		paths = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < coinDenominations.length
				&& coinDenominations[i] <= amount; i++) {

			// Trick to avoid duplicate solutions
			if (pCoin != 0 && coinDenominations[i] > pCoin)
				continue;

			int lAmount = amount - coinDenominations[i];

			retPaths = numPaths(lAmount, coinDenominations[i], TAB);
			append(pCoin, retPaths);

			// update paths
			paths.addAll(retPaths);

		}

		if (DEBUG)
			System.out.println(TAB + "Done");

		return paths;
	}

	public static void main(String args[]) {
		int[] inputAr = new int[] { 4, 5, 10, 3, 1, 2 };

		Count count = new Count(inputAr);
		if (DEBUG) {
			System.out.println("Number of ways to build amount " + 4 + " : "
					+ count.numWays(4));
			return;
		}

		System.out.print("Given Denominations : [");
		for (int i = 0; i < inputAr.length; i++) {
			System.out.print(inputAr[i] + " ");
		}
		System.out.println("]");
		
		for (int i = 0; i < 6; i++) {
			System.out.println("Number of ways to build amount " + i + " : "
					+ count.numWays(i));
			System.out.println("\n");
		}

	}
}
