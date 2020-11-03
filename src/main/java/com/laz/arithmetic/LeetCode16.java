package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class LeetCode16 {
	// 岛屿的周长
	@Test
	public void test1() {
		Assert.assertEquals(16,
				islandPerimeter(new int[][] { { 0, 1, 0, 0 }, { 1, 1, 1, 0 }, { 0, 1, 0, 0 }, { 1, 1, 0, 0 } }));
	}

	public int islandPerimeter(int[][] grid) {
		int res = 0;
		int[][] dict = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == 1) {
					int count = 4;
					for (int z = 0; z < dict.length; z++) {
						int row = i + dict[z][0];
						int col = j + dict[z][1];
						if (row >= 0 && row < grid.length && col >= 0 && col < grid[i].length) {
							if (grid[row][col] == 1) {
								count--;
							}
						}
					}
					res += count;
				}
			}
		}
		return res;
	}

	// O(1) 时间插入、删除和获取随机元素 - 允许重复
	@Test
	public void test2() {
		// 初始化一个空的集合。
		RandomizedCollection2 collection = new RandomizedCollection2();

		// 向集合中插入 1 。返回 true 表示集合不包含 1 。
		collection.insert(1);

		// 向集合中插入另一个 1 。返回 false 表示集合包含 1 。集合现在包含 [1,1] 。
		collection.insert(1);

		// 向集合中插入 2 ，返回 true 。集合现在包含 [1,1,2] 。
		collection.insert(2);

		// getRandom 应当有 2/3 的概率返回 1 ，1/3 的概率返回 2 。
		collection.getRandom();

		// 从集合中删除 1 ，返回 true 。集合现在包含 [1,2] 。
		collection.remove(1);

		// getRandom 应有相同概率返回 1 和 2 。
		collection.getRandom();
	}

	// https://leetcode-cn.com/problems/insert-delete-getrandom-o1-duplicates-allowed/solution/o1-shi-jian-cha-ru-shan-chu-he-huo-qu-sui-ji-yua-5/
	class RandomizedCollection2 {
		Map<Integer, Set<Integer>> idx;
		List<Integer> nums;

		/** Initialize your data structure here. */
		public RandomizedCollection2() {
			idx = new HashMap<Integer, Set<Integer>>();
			nums = new ArrayList<Integer>();
		}

		/**
		 * Inserts a value to the collection. Returns true if the collection did not
		 * already contain the specified element.
		 */
		public boolean insert(int val) {
			nums.add(val);
			Set<Integer> set = idx.getOrDefault(val, new HashSet<Integer>());
			// 记录当前值的下标
			set.add(nums.size() - 1);
			idx.put(val, set);
			return set.size() == 1;
		}

		/**
		 * Removes a value from the collection. Returns true if the collection contained
		 * the specified element.
		 */
		public boolean remove(int val) {
			if (!idx.containsKey(val)) {
				return false;
			}
			Iterator<Integer> it = idx.get(val).iterator();
			int i = it.next();
			int lastNum = nums.get(nums.size() - 1);
			nums.set(i, lastNum);
			idx.get(val).remove(i);
			idx.get(lastNum).remove(nums.size() - 1);
			if (i < nums.size() - 1) {
				idx.get(lastNum).add(i);
			}
			if (idx.get(val).size() == 0) {
				idx.remove(val);
			}
			nums.remove(nums.size() - 1);
			return true;
		}

		/** Get a random element from the collection. */
		public int getRandom() {
			return nums.get((int) (Math.random() * nums.size()));
		}
	}

	class RandomizedCollection {
		private Map<Integer, Integer> map = new HashMap();
		private int size = 0;

		/** Initialize your data structure here. */
		public RandomizedCollection() {

		}

		/**
		 * Inserts a value to the collection. Returns true if the collection did not
		 * already contain the specified element.
		 */
		public boolean insert(int val) {
			boolean flag = false;
			int v = map.getOrDefault(val, 0);
			if (v == 0) {
				flag = true;
			}
			map.put(val, ++v);
			size++;
			return flag;
		}

		/**
		 * Removes a value from the collection. Returns true if the collection contained
		 * the specified element.
		 */
		public boolean remove(int val) {
			if (map.get(val) == null) {
				return false;
			}
			int v = map.get(val);
			--v;
			if (v == 0) {
				map.remove(val);
			} else {
				map.put(val, v);
			}
			size--;
			return true;
		}

		/** Get a random element from the collection. */
		public int getRandom() {
			int v = new Random().nextInt(size) + 1;
			for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
				if (entry.getValue() - v >= 0) {
					return entry.getKey();
				}
				v = v - entry.getValue();
			}
			return 0;
		}
	}

	// 有效的山脉数组
	@Test
	public void test3() {
		Assert.assertEquals(true, validMountainArray(new int[] {
				0,3,2,1
		}));
		Assert.assertEquals(false, validMountainArray(new int[] {
				3,5,5
		}));
		Assert.assertEquals(false, validMountainArray(new int[] {
				1,2,3,4,5
		}));
		Assert.assertEquals(false, validMountainArray(new int[] {
				5,4,3,2,1
		}));
		Assert.assertEquals(false, validMountainArray(new int[] {
				1,2,3,3,2,1
		}));
	}

	public boolean validMountainArray(int[] A) {
		if (A==null || A.length<3) {
			return false;
		}
		int index = 1;
		while (index<A.length) {
			if (A[index]<A[index-1]) {
				index=index-1;
				break;
			}else if (A[index]>A[index-1]) {
				index++;
			}else {
				return false;
			}
		}
		if (index==0 || index>=A.length) {
			return false;
		}
		while (index<A.length-1) {
			if (A[index]>A[index+1]) {
				index++;
			}else if (A[index]<A[index-1]) {
				return false;
			}
		}
		return true;
	}
}
