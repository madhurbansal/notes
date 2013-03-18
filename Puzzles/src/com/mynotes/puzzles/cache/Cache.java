
/*****************************************************************************
PROBLEM :
Assuming there is a cache for string-value pairs, write a function for getting
the value for longest prefix match for a given string.
  
OUTPUT :
===============================================================================
Current Cache : {abc=100, aaa=1, abc23=7, abcde=8, aaaaa=12, a12345=4, xyz=3}
Longest prefix match value for abc234567 is : 7
Longest prefix match value for aaaa is : 1
Longest prefix match value for madhur is : null
Longest prefix match value for aa is : null
******************************************************************************/

package com.mynotes.puzzles.cache;

import java.util.HashMap;

public class Cache<V> {

	private Node<V> root;

	private class Node<T> {
		V value;
		HashMap<Character, Node<T>> linkedNodes =
				new HashMap<Character, Node<T>>();
	}

	/**
	 * Get the value associated the with the given search key using full key
	 * match
	 * 
	 * @param searchKey
	 *            search key
	 * @return V value
	 */
	public V get(String searchKey) {
		Node<V> node = get(searchKey, root, 0);
		if (node == null)
			return null;
		else
			return node.value;
	}

	/**
	 * Put a given key value pair into the Cache
	 * 
	 * @param key
	 * @param value
	 *            of type V
	 */
	public void put(String key, V value) {
		root = put(key, value, root, 0);
	}

	/**
	 * Get the value of longest prefix match key with the given search key
	 * 
	 * @param sKey
	 *            search key
	 * @return V value
	 */
	public V getLongestPrefixMatchValue(String sKey) {
		int maxLength = getLongestPrefixMatchLength(sKey, root, 0, 0);
		return get(sKey.substring(0, maxLength));
	}

	/**
	 * Get all key value pairs stored in this cache
	 * 
	 * @return a map of key value pairs
	 */
	public HashMap<String, V> getAllKeys() {
		HashMap<String, V> map = new HashMap<String, V>();
		getAllKeys(root, "", map);
		return map;
	}

	@Override
	public String toString() {
		return getAllKeys().toString();
	}

	// get all the key value pair in this cache
	private void getAllKeys(Node<V> node, String key, HashMap<String, V> map) {
		if (node == null)
			return;
		if (node.value != null)
			map.put(key, node.value);

		for (Character c : node.linkedNodes.keySet()) {
			getAllKeys(node.linkedNodes.get(c), key + c, map);
		}
	}

	// get the length of the longest prefix match with sKey
	private int getLongestPrefixMatchLength(String sKey, Node<V> node,
			int position, int maxLength) {

		if (node == null)
			return maxLength;

		if (node.value != null)
			maxLength = position;

		if (sKey.length() == position) {
			return maxLength;
		}

		Node<V> nextNode = node.linkedNodes.get(sKey.charAt(position));
		return getLongestPrefixMatchLength(sKey, nextNode, position + 1,
				maxLength);

	}

	// insert the key value pair
	private Node<V> put(String key, V val, Node<V> node, int position) {

		if (node == null)
			node = new Node<V>();

		if (key.length() == position) {
			node.value = val;
			return node;
		}

		Character sChar = key.charAt(position);
		node.linkedNodes.put(sChar,
				put(key, val, node.linkedNodes.get(sChar), position + 1));
		return node;
	}

	// return node with the full key match
	private Node<V> get(String sKey, Node<V> node, int position) {

		if (node == null)
			return null;
		if (position == sKey.length())
			return node;

		// get the character to search for
		Character sChar = sKey.charAt(position);

		return get(sKey, node.linkedNodes.get(sChar), position + 1);
	}

	public static void main(String args[]) {

		Cache<Integer> cache = new Cache<Integer>();

		cache.put("aaa", 1);
		cache.put("xyz", 3);
		cache.put("a12345", 4);
		cache.put("abc23", 7);
		cache.put("abcde", 8);
		cache.put("aaaaa", 12);
		cache.put("abc", 100);

		System.out.println("Current Cache : " + cache);

		String chkStr = "abc234567";
		System.out.println("Longest prefix match value for " + chkStr
				+ " is : " + cache.getLongestPrefixMatchValue(chkStr));
		chkStr = "aaaa";
		System.out.println("Longest prefix match value for " + chkStr
				+ " is : " + cache.getLongestPrefixMatchValue(chkStr));
		chkStr = "madhur";
		System.out.println("Longest prefix match value for " + chkStr
				+ " is : " + cache.getLongestPrefixMatchValue(chkStr));
		chkStr = "aa";
		System.out.println("Longest prefix match value for " + chkStr
				+ " is : " + cache.getLongestPrefixMatchValue(chkStr));

	}
}