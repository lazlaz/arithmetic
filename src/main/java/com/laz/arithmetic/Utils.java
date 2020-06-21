package com.laz.arithmetic;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Utils {
	public  static TreeNode  createTree(Integer[] arr) {
		Queue<Integer> q=new LinkedList<Integer>(Arrays.asList(arr));
		 if (q.peek()==null) {
			 return null;
        }
		return rdeserialize(q);
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
}
