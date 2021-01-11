package com.laz.arithmetic.competition;

import org.junit.Assert;
import org.junit.Test;

import com.laz.arithmetic.ListNode;
import com.laz.arithmetic.Utils;

//https://leetcode-cn.com/contest/weekly-contest-223/
public class Competition17 {
	// 1720. 解码异或后的数组
	@Test
	public void test1() {
		Assert.assertArrayEquals(new int[] { 1, 0, 2, 1 }, decode(new int[] { 1, 2, 3 }, 1));
		Assert.assertArrayEquals(new int[] { 4, 2, 0, 7, 4 }, decode(new int[] { 6, 2, 7, 3 }, 4));
	}

	public int[] decode(int[] encoded, int first) {
		int n = encoded.length;
		int[] ret = new int[n + 1];
		int index = 0;
		ret[index++] = first;
		for (int i = 0; i < encoded.length; i++) {
			ret[index] = ret[index - 1] ^ encoded[i];
			index++;
		}
		return ret;
	}

	// 1721. 交换链表中的节点
	@Test
	public void test2() {
		{
//			ListNode node = swapNodes(Utils.createListNode(new Integer[] {1,2,3,4,5}), 2);
//			Utils.printListNode(node);
		}
		{
//			ListNode node = swapNodes(Utils.createListNode(new Integer[] {7,9,6,6,7,8,3,0,9,5}), 5);
//			Utils.printListNode(node);
		}
		{
			ListNode node = swapNodes(Utils.createListNode(new Integer[] {100,90}), 2);
			Utils.printListNode(node);
		}
	}

	public ListNode swapNodes(ListNode head, int k) {
		ListNode virtual = new ListNode(-1);
		virtual.next = head;
		ListNode kNodePre = virtual;
		int count = 1;
		while (head!=null && k!=1) {
			if (count==k-1) {
				kNodePre = head;
				head = head.next;
				break;
			}
			head = head.next;
			count++;
		}
		ListNode inverseKNodePre = virtual;
		while (head!=null) {
			head = head.next;
			if (head!=null) {
				inverseKNodePre = inverseKNodePre.next;
			}
		}
		//swap k inverseK
		ListNode kNode = kNodePre.next;
		ListNode inverseKNode = inverseKNodePre.next;
		ListNode inverseKNodePost = inverseKNode.next;
		ListNode kNodePost = kNode.next;
		if (inverseKNodePre==kNode) {
			//说明两个值挨在一起
			kNodePre.next = inverseKNode;
			inverseKNode.next = kNode;
			kNode.next = inverseKNodePost;
		} else if (  inverseKNodePost==kNode) {
			//说明两个值挨在一起,后面的在前面
			inverseKNodePre.next = kNode;
			kNode.next = inverseKNode;
			inverseKNode.next = kNodePost;
		}else {
			kNodePre.next = inverseKNode;
			inverseKNode.next = kNode.next;
			kNode.next = inverseKNodePost;
			inverseKNodePre.next = kNode;
		}
		return virtual.next;
	}
}
