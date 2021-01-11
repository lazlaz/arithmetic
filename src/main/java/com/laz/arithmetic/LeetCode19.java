package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.junit.Test;

import com.google.common.base.Joiner;
import com.laz.arithmetic.datastructure.UnionFind;

import org.junit.Assert;

public class LeetCode19 {
	// 830. 较大分组的位置
	@Test
	public void test1() {
		List<List<Integer>> ret = largeGroupPositions("aaa");
		for (List<Integer> list : ret) {
			System.out.println(Joiner.on(",").join(list));
		}
	}

	public List<List<Integer>> largeGroupPositions(String s) {
		List<List<Integer>> ret = new ArrayList<List<Integer>>();
		char currChar = ' ';
		int start = 0;
		int end = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (currChar == ' ') {
				currChar = c;
				start = i;
			} else if (currChar == c) {

			} else {
				end = i - 1;
				if (end - start >= 2) {
					ret.add(Arrays.asList(start, end));
				}
				currChar = c;
				start = i;
			}
		}
		end = s.length() - 1;
		if (end - start >= 2) {
			ret.add(Arrays.asList(start, end));
		}
		return ret;
	}

	// 547. 省份数量
	@Test
	public void test2() {
		Assert.assertEquals(2, new Solution2().findCircleNum(new int[][] { { 1, 1, 0 }, { 1, 1, 0 }, { 0, 0, 1 } }));
		Assert.assertEquals(3, new Solution2().findCircleNum(new int[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } }));
	}

	class Solution2 {
		public class UnionFind {
			private int[] parent;
			private int[] height;
			int size;

			public UnionFind(int size) {
				this.size = size;
				this.parent = new int[size];
				this.height = new int[size];
				for (int i = 0; i < size; i++) {
					parent[i] = i;
					height[i] = 1;
				}
			}

			public int find(int element) {
				while (element != parent[element]) {
					element = parent[element];
				}
				return element;
			}

			public boolean isConnected(int firstElement, int secondElement) {
				return find(firstElement) == find(secondElement);
			}

			public void unionElements(int firstElement, int secondElement) {
				int firstRoot = find(firstElement);
				int secondRoot = find(secondElement);
				// 如果已经属于同一个集合了，就不用再合并了。
				if (firstRoot == secondRoot) {
					return;
				}

				if (height[firstRoot] < height[secondRoot]) {
					parent[firstRoot] = secondRoot;
				} else if (height[firstRoot] > height[secondRoot]) {
					parent[secondRoot] = firstRoot;
				} else {
					parent[firstRoot] = secondRoot;
					height[secondRoot] += 1;
				}
			}

		}

		public int findCircleNum(int[][] isConnected) {
			int n = isConnected.length;
			UnionFind union = new UnionFind(n);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (isConnected[i][j] == 1) {
						union.unionElements(i, j);
					}
				}
			}
			int count = 0;
			for (int i = 0; i < union.parent.length; i++) {
				if (union.parent[i] == i) {
					count++;
				}
			}
			return count;
		}
	}

	// 228. 汇总区间
	@Test
	public void test3() {
		List<String> ret = summaryRanges(new int[] { 0, 1, 2, 4, 5, 7 });
		System.out.println(Joiner.on(",").join(ret));
	}

	public List<String> summaryRanges(int[] nums) {
		List<String> ret = new ArrayList<String>();
		if (nums.length == 0) {
			return ret;
		}
		int start = nums[0];
		int end = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] != end + 1) {
				if (start == end) {
					ret.add(start + "");
				} else {
					ret.add(start + "->" + end);
				}
				start = nums[i];
				end = nums[i];
			} else {
				end = nums[i];
			}
		}
		if (start == end) {
			ret.add(start + "");
		} else {
			ret.add(start + "->" + end);
		}
		return ret;
	}

	// 1202. 交换字符串中的元素
	@Test
	public void test4() {
		List<List<Integer>> pairs = new ArrayList<List<Integer>>();
		pairs.add(Arrays.asList(0, 3));
		pairs.add(Arrays.asList(1, 2));
		pairs.add(Arrays.asList(0, 2));
		Assert.assertEquals("abcd", new Solution4().smallestStringWithSwaps("dcab", pairs));
	}

	// https://leetcode-cn.com/problems/smallest-string-with-swaps/solution/1202-jiao-huan-zi-fu-chuan-zhong-de-yuan-wgab/
	class Solution4 {
		public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
			if (pairs.size() == 0) {
				return s;
			}

			// 第 1 步：将任意交换的结点对输入并查集
			int len = s.length();
			UnionFind unionFind = new UnionFind(len);
			for (List<Integer> pair : pairs) {
				int index1 = pair.get(0);
				int index2 = pair.get(1);
				unionFind.union(index1, index2);
			}

			// 第 2 步：构建映射关系
			char[] charArray = s.toCharArray();
			// key：连通分量的代表元，value：同一个连通分量的字符集合（保存在一个优先队列中）
			Map<Integer, PriorityQueue<Character>> hashMap = new HashMap<>(len);
			for (int i = 0; i < len; i++) {
				int root = unionFind.find(i);
//		            if (hashMap.containsKey(root)) {
//		                hashMap.get(root).offer(charArray[i]);
//		            } else {
//		                PriorityQueue<Character> minHeap = new PriorityQueue<>();
//		                minHeap.offer(charArray[i]);
//		                hashMap.put(root, minHeap);
//		            }
				// 上面六行代码等价于下面一行代码，JDK 1.8 以及以后支持下面的写法
				hashMap.computeIfAbsent(root, key -> new PriorityQueue<>()).offer(charArray[i]);
			}

			// 第 3 步：重组字符串
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < len; i++) {
				int root = unionFind.find(i);
				stringBuilder.append(hashMap.get(root).poll());
			}
			return stringBuilder.toString();
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

}
