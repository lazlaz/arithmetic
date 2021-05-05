package com.laz.arithmetic;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Utils {
	public  static TreeNode  createTree(Integer[] arr) {
		Queue<Integer> q=new LinkedList<Integer>(Arrays.asList(arr));
		 if (q.peek()==null) {
			 return null;
        } 
		return rdeserialize(q);
	}
	public static int[] listToIntArr(List<Integer> list) {
		return list.stream().mapToInt(Integer::valueOf).toArray();
	}
	@Deprecated
	public  static TreeNode  createTree(Integer[] arr,int i) {
		Queue<Integer> q=new LinkedList<Integer>(Arrays.asList(arr));
		 if (q.peek()==null) {
			 return null;
        }
		return rdeserialize(q);
	}
	 private static TreeNode rdeserialize(Queue<Integer> q) {
		   if (q.peek()==null) {
			    q.poll();
			    return null;
	        }
		   TreeNode root = new TreeNode(q.poll());
		   Queue<TreeNode> qTree=new LinkedList<TreeNode>();
		   TreeNode node = root;
		    while (!q.isEmpty()) {
		    	if (q.peek()!=null) {
		    		node.left = new TreeNode(q.poll());
		    		qTree.offer(node.left);
		    	} else {
		    		q.poll();
		    	}
		    	if (q.peek()!=null) {
		    		node.right = new TreeNode(q.poll());
		    		qTree.offer(node.right);
		    	} else {
		    		q.poll();
		    	}
		    	node = qTree.poll();
		    }
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
	
	public static void printListNode(ListNode node) {
		while(node!=null) {
			System.out.print(node.val+"->");
			node = node.next;
		}
	}
	public static void printTreeNode(TreeNode node) {
		System.out.println(new Utils().new Codec().serialize(node));
	}
	class Codec {
		// Encodes a tree to a single string.
		public String serialize(TreeNode root) {
			if (root == null) {
				return "";
			}
			StringBuffer sb = new StringBuffer();
			Queue<TreeNode> q = new LinkedList();
			q.add(root);
			while (!q.isEmpty()) {
				TreeNode temp = q.poll();
				if (temp == null) {
					sb.append("null,");
					continue;
				} else {
					sb.append(temp.val + ",");
				}
				if (temp.left != null) {
					q.offer(temp.left); // 迭代操作，向左探索
				} else {
					q.offer(null);
				}
				if (temp.right != null) {
					q.offer(temp.right);
				} else {
					q.offer(null);
				}
			}
			return sb.toString();
		}

		// Decodes your encoded data to tree.
		public TreeNode deserialize(String data) {
			String str = data;
			String[] arr = str.split(",");
			return createTree(arr, 0);
		}

		private TreeNode createTree(String[] arr, int index) {
			Queue<String> q = new LinkedList<String>(Arrays.asList(arr));
			if (q.peek() == null) {
				return null;
			}
			return rdeserialize(q);
		}

		private TreeNode rdeserialize(Queue<String> q) {
			if (q.peek() == null || q.peek().equals("")) {
				q.poll();
				return null;
			}
			TreeNode root = new TreeNode(Integer.valueOf(q.poll()));
			Queue<TreeNode> qTree = new LinkedList<TreeNode>();
			TreeNode node = root;
			while (!q.isEmpty()) {
				if (q.peek() != null && !q.peek().equals("null")) {
					node.left = new TreeNode(Integer.valueOf(q.poll()));
					qTree.offer(node.left);
				} else {
					q.poll();
				}
				if (q.peek() != null && !q.peek().equals("null")) {
					node.right = new TreeNode(Integer.valueOf(q.poll()));
					qTree.offer(node.right);
				} else {
					q.poll();
				}
				node = qTree.poll();
			}
			return root;

		}
	}
}
