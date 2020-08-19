package com.laz.arithmetic.datastructure.tree;

import org.junit.Assert;
import org.junit.Test;

public class TrieTreeDemo {
	@Test
	public void test() {
		TrieTree tree = new TrieTree();
		tree.insert("abc");
		tree.insert("abd");
		tree.insert("abe");
		
		Assert.assertEquals(true, tree.search("abd"));
		Assert.assertEquals(false, tree.search("abde"));
	}
}
