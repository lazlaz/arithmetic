package com.laz.arithmetic.datastructure;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 排序二叉树
 *
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
		inOrder(root,list);
		return list;
	}

	private void inOrder(Node r, ArrayList<Node> list) {
		if (r == null) {
			return;
		}
		if (r.left!=null) {
			inOrder(r.left,list);
		}
		list.add(r);
		if (r.right!=null) {
			inOrder(r.right,list);
		}
		
		
	}

}
