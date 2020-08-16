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

	public void remove(T data) {
		Node target = getNode(data);
		if (target == null) {
			return;
		}
		
		if (target.left == null && target.right == null) {//如果该节点无左右节点
			if (target == root) {
				root = null;
			} else {
				if (target == target.parent.left) {
					target.parent.left = null;
				} else {
					target.parent.right = null;
				}
			}
		} else if (target.left != null && target.right == null) { //有左节点无右节点，左节点变为父的节点
			if (target == root) {
				root = target.left;
			} else {
				if (target == target.parent.left) {
					target.parent.left = target.left;
				} else {
					target.parent.right = target.left;
				}
				target.left.parent = target.parent;
			}
		} else if (target.left == null && target.right != null) { //有右节点无左节点，右节点变为父的节点
			if (target == root) {
				root = target.right;
			} else {
				if (target == target.parent.left) {
					target.parent.left = target.right;
				} else {
					target.parent.right = target.right;
				}
				target.right.parent = target.parent;
			}
		} else {// 左右子树都不为空，用小于删除节点的最大子节点代替它
			Node leftMaxNode = target.left;
			while (leftMaxNode.right != null) {
				leftMaxNode = leftMaxNode.right;
			}
			leftMaxNode.parent.right = null;
			leftMaxNode.parent = target.parent;
			if (target == target.parent.left) {
				target.parent.left = leftMaxNode;
			} else {
				target.parent.right = leftMaxNode;
			}
			leftMaxNode.left = target.left;
			leftMaxNode.right = target.right;
			target.parent = target.left = target.right = null;
		}
		target = null;
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
