package com.laz.arithmetic.datastructure.tree;

import org.junit.Test;

public class SortedBinTreeTest {
	@Test
	public void test() {
		SortedBinTree<Integer> tree = new SortedBinTree<>();
		tree.insert(5);
		tree.insert(20);
		tree.insert(10);
		tree.insert(3);
		tree.insert(8);
		tree.insert(15);
		tree.insert(30);
		tree.insert(4);
		tree.insert(13);
		System.out.println(tree.inOrder());
		tree.delete(10);
		System.out.println(tree.inOrder());

	}
}
