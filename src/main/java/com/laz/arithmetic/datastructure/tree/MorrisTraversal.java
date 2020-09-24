package com.laz.arithmetic.datastructure.tree;

/*
 * Morris遍历 （O(1)空间复杂度）
 * https://blog.csdn.net/qq_37480159/article/details/77543083
 */
public class MorrisTraversal {
	public static class Node {
		public int value;
		Node left;
		Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	/**
	 * Morris 先序遍历二叉树(第一次发现节点就打印：①左子树的右孩子为空时打印 ②无左子树时打印)
	 * 
	 * @param head
	 */

	public static void morrisPre(Node head) {
		if (head == null) {
			return;
		}
		Node cur1 = head;// 每次得到的当前node
		Node cur2 = null;// 当前node左子树最右的节点
		while (cur1 != null) {
			cur2 = cur1.left;
			if (cur2 != null) {
				while (cur2.right != null && cur2.right != cur1) {// 找到左子树最右节点
					cur2 = cur2.right;
				}

				if (cur2.right == null) { // 最右点的右指针指向null，
					cur2.right = cur1; // 右指针指向当前节点
					System.out.print(cur1.value + " ");
					cur1 = cur1.left; // 当前node向左孩子移动
					continue;
				} else { // 最右点的右指针已经指向当前节点，让它指向null
					cur2.right = null;
				}
			} else {
				System.out.print(cur1.value + " ");
			}
			cur1 = cur1.right; // 当前node向右孩子移动
		}
		System.out.println();
	}

	/**
	 * Morris中序遍历二叉树(node每次往右移之前打印节点)
	 * 
	 * @param head
	 */
	public static void morrisIn(Node head) {
		if (head == null) {
			return;
		}
		Node cur1 = head;
		Node cur2 = null;
		while (cur1 != null) {
			cur2 = cur1.left;
			if (cur2 != null) {
				while (cur2.right != null && cur2.right != cur1) {
					cur2 = cur2.right;
				}
				if (cur2.right == null) {
					cur2.right = cur1;
					cur1 = cur1.left;
					continue;
				} else {
					cur2.right = null;
				}
			}
			System.out.print(cur1.value + " ");
			cur1 = cur1.right;
		}
		System.out.println();
	}

	/**
	 * Morris后序遍历二叉树(当第二次回到node时，逆序打印左子树的右边界，在打印整树的右边界)
	 * 
	 * @param head
	 */
	public static void morrisPos(Node head) {
		if (head == null) {
			return;
		}
		Node cur1 = head;
		Node cur2 = null;
		while (cur1 != null) {
			cur2 = cur1.left;
			if (cur2 != null) {
				while (cur2.right != null && cur2.right != cur1) {
					cur2 = cur2.right;
				}
				if (cur2.right == null) {
					cur2.right = cur1;
					cur1 = cur1.left;
					continue;
				} else {
					cur2.right = null;
					printEdge(cur1.left);
				}
			}
			cur1 = cur1.right;
		}
		printEdge(head);
		System.out.println();
	}

	public static void printEdge(Node head) {
		Node tail = reverseEdge(head);
		Node cur = tail;
		while (cur != null) {
			System.out.print(cur.value + " ");
			cur = cur.right;
		}
		reverseEdge(tail);
	}

	public static Node reverseEdge(Node from) {
		Node pre = null;
		Node next = null;
		while (from != null) {
			next = from.right;
			from.right = pre;
			pre = from;
			from = next;
		}
		return pre;
	}

	// for test -- print tree
	public static void printTree(Node head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}

	public static void printInOrder(Node head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		printInOrder(head.right, height + 1, "v", len);
		String val = to + head.value + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.left, height + 1, "^", len);
	}

	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		Node head = new Node(4);
		head.left = new Node(2);
		head.right = new Node(6);
		head.left.left = new Node(1);
		head.left.right = new Node(3);
		head.right.left = new Node(5);
		head.right.right = new Node(7);
		printTree(head);
		//morrisPre(head);
		morrisIn(head);
		//morrisPos(head);
		printTree(head);

	}

}
