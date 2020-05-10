package com.laz.arithmetic;

public class Utils {
	public  static TreeNode  createTree(Integer[] arr,int i) {
		TreeNode root = null; // 定义根节点
		if (i >= arr.length) // i >= arr.length 时,表示已经到达了根节点
			return null;
		if (arr[i]==null) {
			return null;
		}
		root = new TreeNode(arr[i]); // 根节点
		root.left = createTree(arr, 2*i+1); // 递归建立左孩子结点
		root.right = createTree(arr, 2*i+2); // 递归建立右孩子结点
		return root;
	}
	
	public static ListNode createListNode(Integer[] arr) {
		ListNode root = null;
		ListNode n = new ListNode(arr[0]);
		root = n;
		for (int i=1; i<arr.length; i++) {
			ListNode node  = new ListNode(arr[i]);
			n.next=node;
			n = node;
		}
		return root;
	}
}
