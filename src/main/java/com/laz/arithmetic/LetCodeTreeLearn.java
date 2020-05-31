package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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

	// 对称二叉树
	@Test
	public void test3() {

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
		System.out.println(isSymmetric(root));

	}

	// 二叉树的层次遍历
	@Test
	public void test4() {

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
		System.out.println(levelOrder(root));

	}

	public List<List<Integer>> levelOrder(TreeNode root) {
		List<List<Integer>> levels = new ArrayList<List<Integer>>();
		if (root == null)
			return levels;
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(root);
		int level = 0;
		while (!queue.isEmpty()) {
			levels.add(new ArrayList<Integer>());
			int level_length = queue.size();
			for (int i = 0; i < level_length; ++i) {
				TreeNode node = queue.remove();
				levels.get(level).add(node.val);
				if (node.left != null)
					queue.add(node.left);
				if (node.right != null)
					queue.add(node.right);
			}
			level++;
		}
		return levels;
	}
	//利用迭代
	public boolean isSymmetric(TreeNode root) {
		return check(root, root);
	}
	
	public boolean check(TreeNode u, TreeNode v) {
		Queue<TreeNode> q = new LinkedList<TreeNode>();
		q.offer(u);
		q.offer(v);
		
		while(!q.isEmpty()) {
			u = q.poll();
			v = q.poll();
			if (u == null && v == null) {
				continue;
			}
			if ((u == null || v == null) || (u.val != v.val)) {
				return false;
			}
			q.offer(u.left);
			q.offer(v.right);
			
			q.offer(u.right);
	        q.offer(v.left);
		}
		return true;
	}
	

	public boolean isSymmetric2(TreeNode root) {
		return isMirror(root, root);
	}

	public boolean isMirror(TreeNode t1, TreeNode t2) {
		if (t1 == null && t2 == null)
			return true;
		if (t1 == null || t2 == null)
			return false;
		return (t1.val == t2.val) && isMirror(t1.right, t2.left) && isMirror(t1.left, t2.right);
	}

	public boolean isValidBST(TreeNode root) {
		List<Integer> list = new ArrayList<>();
		inorderTraversal(root, list);

		if (list.size() > 0) {
			int initVal = list.get(0);
			for (int i = 1; i < list.size(); i++) {
				if (initVal >= list.get(i)) {
					return false;
				}
				initVal = list.get(i);
			}
		}

		return true;
	}

	private void inorderTraversal(TreeNode treeNode, List<Integer> list) {
		if (treeNode == null) {
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
