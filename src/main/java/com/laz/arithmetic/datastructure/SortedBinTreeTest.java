package com.laz.arithmetic.datastructure;

import org.junit.Test;

public class SortedBinTreeTest {
	@Test
	public void test() {
		SortedBinTree<Integer> tree = new SortedBinTree<>();
		tree.insert(5);
		tree.insert(20);
		tree.insert(10);
		tree.insert(11);
		System.out.println(tree.inOrder());
	}
}
