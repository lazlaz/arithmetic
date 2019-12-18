package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

	// 验证二叉搜索树
	@Test
	public void test2() {
		// TreeNode root = new TreeNode(5);
		// TreeNode node1 = new TreeNode(1);
		// TreeNode node2 = new TreeNode(4);
		// TreeNode node3 = new TreeNode(3);
		// TreeNode node4 = new TreeNode(6);
		// root.left = node1;
		// root.right = node2;
		//
		// node2.left = node3;
		// node2.right = node4;

		TreeNode root = new TreeNode(3);
		TreeNode node1 = new TreeNode(1);
		TreeNode node2 = new TreeNode(5);
		TreeNode node3 = new TreeNode(0);
		TreeNode node4 = new TreeNode(2);
		TreeNode node5 = new TreeNode(4);
		TreeNode node6 = new TreeNode(6);

		TreeNode node7 = new TreeNode(3);
		TreeNode node8 = new TreeNode(13);
		root.left = node1;
		root.right = node2;
		node1.left = node3;
		node1.right = node4;
		node2.left = node5;
		node2.right = node6;
		
		node4.left = node8;
		node4.right = node7;
		System.out.println(isValidBST(root));

	}

	public boolean isValidBST(TreeNode root) {
		List<Integer> list = new ArrayList<>();
		inorderTraversal(root, list);
		
		if (list.size()>0) {
			int initVal = list.get(0);
			for (int i=1;i<list.size();i++) {
				if (initVal>=list.get(i)) {
					return false;
				}
				initVal = list.get(i);
			}
		}

		return true;
	}
	private void inorderTraversal(TreeNode treeNode, List<Integer> list) {
		if(treeNode == null) {
			return;
		}
		inorderTraversal(treeNode.left, list);
		list.add(treeNode.val);
		inorderTraversal(treeNode.right, list);
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
