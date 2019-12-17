package com.laz.arithmetic;

import java.util.ArrayList;

import org.junit.Test;

public class LetCodeTreeLearn {
	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	// 二叉树的最大深度
	@Test
	public void test1() {
		TreeNode root = new TreeNode(3);
		TreeNode node1 = new TreeNode(9);
		TreeNode node2 = new TreeNode(20);
		TreeNode node3 = new TreeNode(15);
		TreeNode node4 = new TreeNode(7);
		root.left = node1;
		root.right = node2;

		node2.left = node3;
		node2.right = node4;

		System.out.println(maxDepth(root));

	}

	public int maxDepth(TreeNode root) {
		if (root == null) {
			return 0;
		}
		// 递归计算左子树深度
		int l = maxDepth(root.left);
		// 递归计算右子树深度
		int r = maxDepth(root.right);
		// 判断左右子树哪一个大则加一
		return l > r ? l + 1 : r + 1;
	}
}
