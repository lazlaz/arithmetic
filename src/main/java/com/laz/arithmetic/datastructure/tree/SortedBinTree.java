package com.laz.arithmetic.datastructure.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 排序二叉树 https://www.cnblogs.com/yahuian/p/10813614.html
 */
public class SortedBinTree<T extends Comparable> {
	private static class Node {
		private Object data;
		private Node left;
		private Node right;
		private Node parent;

		public Node(Object data, Node parent, Node left, Node right) {
			this.data = data;
			this.parent = parent;
			this.left = left;
			this.right = right;
		}

		public boolean compareTo(Object object) {
			if (this == object) {
				return true;
			}
			if (object.getClass() == Node.class) {
				Node target = (Node) object;
				return data.equals(target.data) && left == target.left && right == target.right
						&& parent == target.parent;
			}
			return false;
		}

		public String toString() {
			return "data:" + data;
		}
	}

	private Node root;

	public SortedBinTree() {
		root = null;
	}

	public SortedBinTree(String data) {
		root = new Node(data, null, null, null);
	}

	private Node getSuccessor(Node delNode) // 寻找要删除节点的中序后继结点
	{
		Node successor = delNode;
		Node current = delNode.right;

		// 用来寻找后继结点
		while (current != null) {
			successor = current;
			current = current.left;
		}
		// 如果后继结点为要删除结点的右子树的左子，需要预先调整一下要删除结点的右子树
		if (successor != delNode.right) {
			successor.parent.left = successor.right;
			successor.right = delNode.right;
		}
		return successor;
	}

	public boolean delete(T data) // 删除结点
	{
		Node target = getNode(data);
		if (target == null) {
			return false;
		}
		// 要删除结点为叶子结点
		if (target.right == null && target.left == null) {
			if (target == root) {
				root = null; // 整棵树清空
			} else {
				if (target.parent.left == target) {
					target.parent.left = null;
				} else {
					target.parent.right = null;
				}
			}
			return true;
		}
		// 要删除结点有一个子结点
		else if (target.left == null) {
			if (target == root)
				root = target.right;
			else if (target.parent.right == target)
				target.parent.right = target.right;
			else
				target.parent.left = target.right;
			return true;
		} else if (target.right == null) {
			if (target == root)
				root = target.left;
			else if (target.parent.right == target)
				target.parent.right = target.left;
			else
				target.parent.left = target.left;
			return true;
		}
		// 要删除结点有两个子结点
		else {
			Node successor = getSuccessor(target); // 找到要删除结点的后继结点
			if (target == root)
				root = successor;
			else if (target.parent.right == target)
				target.parent.right = successor;
			else
				target.parent.left = successor;
			successor.left = target.left;
			return true;
		}
	}

	public void insert(T data) {
		if (root == null) {
			root = new Node(data, null, null, null);
		} else {
			Node current = root;
			Node parent = null;
			int cmp = 0;
			while (current != null) {
				parent = current;
				cmp = data.compareTo(current.data);
				if (cmp <= 0) {
					current = current.left;
				} else {
					current = current.right;
				}
			}
			Node node = new Node(data, parent, null, null);
			if (cmp < 0) {
				parent.left = node;
			} else {
				parent.right = node;
			}

		}
	}

	private Node getNode(T data) {
		Node current = root;
		while (current != null) {
			int cmp = data.compareTo(current.data);
			if (cmp < 0) {
				current = current.left;
			} else if (cmp > 0) {
				current = current.right;
			} else {
				return current;
			}
		}
		return null;
	}

	// 层次遍历
	public ArrayList<Node> level() {
		ArrayList<Node> list = new ArrayList<>();
		Deque<Node> queue = new LinkedList<Node>();
		if (root != null) {
			queue.add(root);
		}
		while (!queue.isEmpty()) {
			Node node = queue.poll();
			list.add(node);
			if (node.left != null) {
				queue.add(node.left);
			}
			if (node.right != null) {
				queue.add(node.right);
			}
		}
		return list;
	}

	// 中序遍历
	public ArrayList<Node> inOrder() {
		ArrayList<Node> list = new ArrayList<>();
		inOrder(root, list);
		return list;
	}

	private void inOrder(Node r, ArrayList<Node> list) {
		if (r == null) {
			return;
		}
		if (r.left != null) {
			inOrder(r.left, list);
		}
		list.add(r);
		if (r.right != null) {
			inOrder(r.right, list);
		}

	}

}
