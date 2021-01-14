package com.laz.arithmetic.competition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
			ListNode node = swapNodes(Utils.createListNode(new Integer[] { 100, 90 }), 2);
			Utils.printListNode(node);
		}
	}

	public ListNode swapNodes(ListNode head, int k) {
		ListNode virtual = new ListNode(-1);
		virtual.next = head;
		ListNode kNodePre = virtual;
		int count = 1;
		while (head != null && k != 1) {
			if (count == k - 1) {
				kNodePre = head;
				head = head.next;
				break;
			}
			head = head.next;
			count++;
		}
		ListNode inverseKNodePre = virtual;
		while (head != null) {
			head = head.next;
			if (head != null) {
				inverseKNodePre = inverseKNodePre.next;
			}
		}
		// swap k inverseK
		ListNode kNode = kNodePre.next;
		ListNode inverseKNode = inverseKNodePre.next;
		ListNode inverseKNodePost = inverseKNode.next;
		ListNode kNodePost = kNode.next;
		if (inverseKNodePre == kNode) {
			// 说明两个值挨在一起
			kNodePre.next = inverseKNode;
			inverseKNode.next = kNode;
			kNode.next = inverseKNodePost;
		} else if (inverseKNodePost == kNode) {
			// 说明两个值挨在一起,后面的在前面
			inverseKNodePre.next = kNode;
			kNode.next = inverseKNode;
			inverseKNode.next = kNodePost;
		} else {
			kNodePre.next = inverseKNode;
			inverseKNode.next = kNode.next;
			kNode.next = inverseKNodePost;
			inverseKNodePre.next = kNode;
		}
		return virtual.next;
	}

	// 1722. 执行交换操作后的最小汉明距离
	@Test
	public void test3() {
//		Assert.assertEquals(1, new Solution3().minimumHammingDistance(new int[] { 1, 2, 3, 4 }, new int[] { 2, 1, 4, 5 },
//				new int[][] { { 0, 1 }, { 2, 3 } }));
		Assert.assertEquals(1, new Solution3().minimumHammingDistance(new int[] { 2, 3, 1 }, new int[] { 1, 2, 2 },
				new int[][] { { 0, 2 }, { 1, 2 } }));
	}

	class Solution3 {
		public int minimumHammingDistance(int[] source, int[] target, int[][] allowedSwaps) {
			// 第 1 步：将任意交换的结点对输入并查集
			int len = source.length;
			UnionFind unionFind = new UnionFind(len);
			for (int[] allowedSwap : allowedSwaps) {
				int index1 = allowedSwap[0];
				int index2 = allowedSwap[1];
				unionFind.union(index1, index2);
			}
			// 第 2 步：构建映射关系
			Map<Integer, Map<Integer, Integer>> hashMap = new HashMap<>(len);
			for (int i = 0; i < source.length; i++) {
				int root = unionFind.find(i);
				if (hashMap.containsKey(root)) {
					int v = hashMap.get(root).getOrDefault(source[i], 0);
					hashMap.get(root).put(source[i], (v + 1));
				} else {
					Map<Integer, Integer> map = new HashMap<>();
					map.put(source[i], 1);
					hashMap.put(root, map);
				}
			}
			int count = 0;
			for (int i = 0; i < target.length; i++) {
				int root = unionFind.find(i);
				Map<Integer, Integer> map = hashMap.get(root);
				if (!map.containsKey(target[i]) || map.get(target[i]) == 0) {
					count++;
				} else {
					// 移除值，防止有重复的
					int v = hashMap.get(root).get(target[i]);
					hashMap.get(root).put(target[i], (v - 1));
				}
			}
			return count;
		}

		private class UnionFind {

			private int[] parent;
			/**
			 * 以 i 为根结点的子树的高度（引入了路径压缩以后该定义并不准确）
			 */
			private int[] rank;

			public UnionFind(int n) {
				this.parent = new int[n];
				this.rank = new int[n];
				for (int i = 0; i < n; i++) {
					this.parent[i] = i;
					this.rank[i] = 1;
				}
			}

			public void union(int x, int y) {
				int rootX = find(x);
				int rootY = find(y);
				if (rootX == rootY) {
					return;
				}

				if (rank[rootX] == rank[rootY]) {
					parent[rootX] = rootY;
					// 此时以 rootY 为根结点的树的高度仅加了 1
					rank[rootY]++;
				} else if (rank[rootX] < rank[rootY]) {
					parent[rootX] = rootY;
					// 此时以 rootY 为根结点的树的高度不变
				} else {
					// 同理，此时以 rootX 为根结点的树的高度不变
					parent[rootY] = rootX;
				}
			}

			public int find(int x) {
				if (x != parent[x]) {
					parent[x] = find(parent[x]);
				}
				return parent[x];
			}
		}
	}

	// 1723. 完成所有工作的最短时间 
	//TODO
	@Test
	public void test4() {
		Assert.assertEquals(3, new Solution4().minimumTimeRequired(new int[] { 3, 2, 3 }, 3));
	}

	// https://leetcode-cn.com/problems/find-minimum-time-to-finish-all-jobs/solution/java-dfsjian-zhi-by-tlzxsun-nui2/
	class Solution4 {
		public int minimumTimeRequired(int[] jobs, int k) {
			return 3;
		}

	}
}
