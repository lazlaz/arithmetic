package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
		if (nums.length==0) {
			return ret;
		}
		int start = nums[0];
		int end = nums[0];
		for (int i=1;i<nums.length;i++) {
			if (nums[i]!=end+1) {
				if (start==end) {
					ret.add(start+"");
				} else {
					ret.add(start+"->"+end);
				}
				start = nums[i];
				end = nums[i];
			} else {
				end = nums[i];
			}
		}
		if (start==end) {
			ret.add(start+"");
		} else {
			ret.add(start+"->"+end);
		}
		return ret;
	}
}
