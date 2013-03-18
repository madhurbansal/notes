/*******************************************************************************
PROBLEM :
	Methods to retrieve height of a tree and check if the node is balanced
	at every node.
	
SAMPLE OUTPUT :
===============================================================================	
    Running 21 experiments with heap size : 15M
    Current free memory : 15M

      SNo.    Free Memory       NumNodes      Time to Random Create(s)    Height     isBalanced       Time to check Height(s)      Time to check Balance(s)
         0            15M              1                      0.003000         0           true                      0.000000                      0.000000
         1            15M              2                      0.000000         1           true                      0.000000                      0.000000
         2            15M              4                      0.000000         2           true                      0.000000                      0.000000
         3            15M              8                      0.000000         3          false                      0.000000                      0.000000
         4            15M             16                      0.000000         4          false                      0.000000                      0.000000
         5            15M             32                      0.000000         6          false                      0.000000                      0.000000
         6            15M             64                      0.001000         7          false                      0.000000                      0.000000
         7            15M            128                      0.001000         9          false                      0.000000                      0.000000
         8            15M            256                      0.001000         9          false                      0.001000                      0.000000
         9            15M            512                      0.002000        11          false                      0.000000                      0.000000
        10            15M           1024                      0.001000        12          false                      0.000000                      0.000000
        11            15M           2048                      0.002000        13          false                      0.000000                      0.000000
        12            15M           4096                      0.005000        14          false                      0.000000                      0.000000
        13            14M           8192                      0.009000        16          false                      0.001000                      0.000000
        14            14M          16384                      0.013000        17          false                      0.000000                      0.000000
        15            13M          32768                      0.028000        18          false                      0.001000                      0.000000
        16            12M          65536                      0.073000        19          false                      0.002000                      0.000000
        17            12M         131072                      0.162000        20          false                      0.006000                      0.000000
        18             9M         262144                      0.369000        22          false                      0.017000                      0.000000
        19             1M         524288                      0.937000        23          false                      0.036000                      0.000000
        20            14M        1048576                      2.183000        24          false                      0.069000                      0.000000
        21            38M        2097152                      4.431000        25          false                      0.147000                      0.000000

******************************************************************************/

package com.mynotes.puzzles.tree;

public class BinaryTree<T> {

	private Node<T> root;
	private static final String TAB = "  ";
	private static final boolean DEBUG = false;

	private class Node<D> {
		Node<D> left;
		Node<D> right;
		D val;

		Node(D data) {
			left = null;
			right = null;
			val = data;
		}

		public String toString() {
			return (val == null) ? "*" : val.toString();
		}
	}

	/**
	 * Check if the tree is balanced at every node
	 * 
	 * @return boolean
	 */
	public boolean isBalanced() {
		return (isBalanced(root) > 0);
	}

	/**
	 * Return the height of the tree
	 * 
	 * @return integer value, for a single node tree height is 0
	 */
	public int height() {
		return height(root);
	}

	/**
	 * Delete all nodes of the tree to free memory
	 */
	public void emptyTree() {
		root = deleteNodes(root);
	}

	@Override
	public String toString() {
		return getTree(root, "");
	}

	// get height of the tree, height of single node tree = 0
	private int height(Node<T> node) {
		if (node == null)
			return -1;

		int hLTree = height(node.left);
		int hRTree = height(node.right);

		return ((hLTree >= hRTree) ? hLTree : hRTree) + 1;
	}

	private String getTree(Node<T> node, String space) {

		if (node == null)
			return "*";

		space = space + TAB;

		return node.toString() + "\n" + space + getTree(node.left, space)
				+ "\n" + space + getTree(node.right, space);

	}

	// checking balance of the tree at every node
	private int isBalanced(Node<T> node) {

		if (node == null)
			return 0;

		int hLTree = isBalanced(node.left);
		if (hLTree < 0)
			return -1;

		int hRTree = isBalanced(node.right);
		if (hRTree < 0)
			return -1;

		if (Math.abs(hLTree - hRTree) > 1)
			return -1;

		return ((hLTree >= hRTree) ? hLTree : hRTree) + 1;

	}

	/**
	 * just to add one node at a time randomly into the tree
	 */
	private Node<Integer> putIntRandom(Node<Integer> node, int data) {
		if (node == null)
			return (new Node<Integer>(data));

		boolean leftInsert = (Math.random() > 0.5) ? true : false;

		if (leftInsert) {
			node.left = putIntRandom(node.left, data);
		} else {
			node.right = putIntRandom(node.right, data);
		}

		return node;

	}

	private Node<T> deleteNodes(Node<T> node) {
		if (node == null)
			return null;

		node.left = deleteNodes(node.left);
		node.right = deleteNodes(node.right);
		node = null;

		return node;
	}

	/**
	 * Create a binary tree of num nodes by randomly inserting nodes
	 * 
	 * @param num
	 * @return Tree
	 */
	public static BinaryTree<Integer> randomCreate(int num) {
		BinaryTree<Integer> someTree = new BinaryTree<Integer>();

		someTree.root = someTree.putIntRandom(null, 0);
		for (int i = 1; i < num; i++) {
			someTree.putIntRandom(someTree.root, i);
		}

		return someTree;
	}

	public static void main(String args[]) {

		if (DEBUG) {
			BinaryTree<Integer> tree1 = randomCreate(5);

			System.out.println("The height of the tree : " + tree1.height());
			System.out.println("Is Tree balanced : " + tree1.isBalanced());
			System.out.println(tree1);

			tree1.emptyTree();
			System.out.println(tree1);
		}

		// Run experiments

		// Experiment with maximum 2^MAX number of nodes in the tree
		int MAX = 21;
		if (args.length > 0) {
			try {
				MAX = Integer.parseInt(args[0]);
			} catch (NumberFormatException nex) {
				System.out.println("Unable to parse input arg, defaulting MAX");
			}
		}

		int numNodes = 1, height;
		long startTime, endTime;
		double diffC, diffH, diffB;
		boolean treeBalance;
		String freeMemory;
		BinaryTree<Integer> tempTree;

		System.out.println("    Running " + MAX
				+ " experiments with heap size : "
				+ Runtime.getRuntime().totalMemory() / 1048576 + "M");

		System.out.println("    Current free memory : "
				+ Runtime.getRuntime().freeMemory() / 1048576 + "M\n");

		System.out.format("%10s%15s%15s%30s%10s%15s%30s%30s%n", "SNo.",
				"Free Memory", "NumNodes", "Time to Random Create(s)",
				"Height", "isBalanced", "Time to check Height(s)",
				"Time to check Balance(s)");

		for (int i = 0; i <= MAX; i++) {

			freeMemory = Runtime.getRuntime().freeMemory() / 1048576 + "M";

			startTime = System.currentTimeMillis();
			tempTree = randomCreate(numNodes);
			endTime = System.currentTimeMillis();
			diffC = (endTime - startTime) / 1000.0;

			startTime = System.currentTimeMillis();
			height = tempTree.height();
			endTime = System.currentTimeMillis();
			diffH = (endTime - startTime) / 1000.0;

			startTime = System.currentTimeMillis();
			treeBalance = tempTree.isBalanced();
			endTime = System.currentTimeMillis();
			diffB = (endTime - startTime) / 1000.0;

			System.out.format("%10d%15s%15s%30f%10d%15b%30f%30f%n", i,
					freeMemory, numNodes, diffC, height, treeBalance, diffH,
					diffB);
			numNodes = numNodes * 2;

			// free memory
			tempTree.emptyTree();
		}
	}

}
