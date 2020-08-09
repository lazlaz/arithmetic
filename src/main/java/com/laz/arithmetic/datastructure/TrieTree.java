package com.laz.arithmetic.datastructure;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
	Map<Character, TrieNode> children;
	boolean wordEnd;

	public TrieNode() {
		children = new HashMap<Character, TrieNode>();
		wordEnd = false;
	}
}

/**
 * 字典树
 *
 */
public class TrieTree {
	private TrieNode root;

	public TrieTree() {
		root = new TrieNode();
		root.wordEnd = false;
	}

	public void insert(String word) {
		TrieNode node = root;
		for (int i = 0; i < word.length(); i++) {
			Character c = new Character(word.charAt(i));
			if (!node.children.containsKey(c)) {
				node.children.put(c, new TrieNode());
			}
			node = node.children.get(c);
		}
		node.wordEnd = true;
	}

	public boolean search(String word) {
		TrieNode node = root;
		boolean found = true;
		for (int i = 0; i < word.length(); i++) {
			Character c = new Character(word.charAt(i));
			if (!node.children.containsKey(c)) {
				return false;
			}
			node = node.children.get(c);
		}
		return found && node.wordEnd;
	}

	public boolean startsWith(String prefix) {
		TrieNode node = root;
		boolean found = true;
		for (int i = 0; i < prefix.length(); i++) {
			Character c = new Character(prefix.charAt(i));
			if (!node.children.containsKey(c)) {
				return false;
			}
			node = node.children.get(c);
		}
		return found;
	}
	
}
