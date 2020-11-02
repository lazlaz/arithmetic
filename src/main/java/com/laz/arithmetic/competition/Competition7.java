package com.laz.arithmetic.competition;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/problems/check-array-formation-through-concatenation/
public class Competition7 {
	// 能否连接形成数组
	@Test
	public void test1() {
		Assert.assertEquals(true, canFormArray(new int[] { 15, 88 }, new int[][] { { 88 }, { 15 } }));

		Assert.assertEquals(true, canFormArray(new int[] { 91, 4, 64, 78 }, new int[][] { { 78 }, { 4, 64 }, { 91 } }));

		Assert.assertEquals(false, canFormArray(new int[] { 49, 18, 16 }, new int[][] { { 16, 18, 49 } }));

		Assert.assertEquals(false, canFormArray(new int[] { 1, 3, 5, 7 }, new int[][] { { 2, 4, 6, 8 } }));
	}

	public boolean canFormArray(int[] arr, int[][] pieces) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < arr.length; i++) {
			map.put(arr[i], i);
		}
		boolean[] flag = new boolean[arr.length];
		for (int i = 0; i < pieces.length; i++) {
			int last = -1;
			for (int j = 0; j < pieces[i].length; j++) {
				int v = pieces[i][j];
				if (map.get(v) == null) {
					return false;
				}
				if (last != -1 && map.get(v) != last + 1) {
					return false;
				}
				flag[map.get(v)] = true;
				last = map.get(v);
			}
		}
		boolean res = true;
		for (int i = 0; i < flag.length; i++) {
			res = res && flag[i];
		}
		return res;
	}

	// 统计字典序元音字符串的数目
	@Test
	public void test2() {
		Assert.assertEquals(15, new Solution2().countVowelStrings(2));
		Assert.assertEquals(66045, new Solution2().countVowelStrings(33));
	}

	class Solution2 {
		int res = 0;

		public int countVowelStrings(int n) {
			char[] dict = new char[] { 'a', 'e', 'i', 'o', 'u' };
			LinkedList<Character> list = new LinkedList<Character>();
			backtrack(dict, list, n);
			return res;
		}

		private void backtrack(char[] dict, LinkedList<Character> list, int n) {
			for (int i = 0; i < dict.length; i++) {
				if (list.size() > 0 && list.getLast() > dict[i]) {
					continue;
				}
				if (list.size() == n) {
					res++;
					return;
				}
				list.add(dict[i]);
				backtrack(dict, list, n);
				list.removeLast();
			}

		}
	}

	// 可以到达的最远建筑
	@Test
	public void test3() {
		Assert.assertEquals(7, new Solution3().furthestBuilding(new int[] { 4, 12, 2, 7, 3, 18, 20, 3, 19 }, 10, 2));

		Assert.assertEquals(4, new Solution3().furthestBuilding(new int[] { 4, 2, 7, 6, 9, 14, 12 }, 5, 1));

		Assert.assertEquals(3, new Solution3().furthestBuilding(new int[] { 14, 3, 19, 3 }, 17, 0));

	}

	// https://leetcode-cn.com/problems/furthest-building-you-can-reach/solution/java-tan-xin-suan-fa-dui-by-kepler-11/
	class Solution3 {
		public int furthestBuilding(int[] heights, int bricks, int ladders) {
			int n = heights.length;
			PriorityQueue<Integer> pq = new PriorityQueue<>();
			int sum = 0;
			for (int i = 0; i < n - 1; i++) {
				int tmp = heights[i + 1] - heights[i];
				if (tmp > 0) {
					pq.add(tmp);
					if (pq.size() > ladders) {
						sum += pq.poll();
					}
					if (sum > bricks) {
						return i;
					}
				}
			}
			return n - 1;
		}
	}
}
