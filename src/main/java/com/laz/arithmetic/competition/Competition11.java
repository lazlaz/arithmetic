package com.laz.arithmetic.competition;

import java.util.Deque;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-217/
public class Competition11 {
	// 最富有客户的资产总量
	@Test
	public void test1() {
		Assert.assertEquals(6, maximumWealth(new int[][] { { 1, 2, 3 }, { 3, 2, 1 } }));

		Assert.assertEquals(17, maximumWealth(new int[][] { { 2, 8, 7 }, { 7, 1, 3 }, { 1, 9, 5 } }));
	}

	public int maximumWealth(int[][] accounts) {
		int maxRes = 0;
		int row = accounts.length;
		int col = accounts[0].length;
		for (int i = 0; i < row; i++) {
			int sum = 0;
			for (int j = 0; j < col; j++) {
				sum += accounts[i][j];
			}
			if (sum > maxRes) {
				maxRes = sum;
			}
		}
		return maxRes;
	}

	// 找出最具竞争力的子序列
	@Test
	public void test2() {
		Assert.assertArrayEquals(new int[] { 2, 6 }, new Solution2().mostCompetitive(new int[] { 3, 5, 2, 6 }, 2));

		Assert.assertArrayEquals(new int[] { 2, 3, 3, 4 },
				new Solution2().mostCompetitive(new int[] { 2, 4, 3, 3, 5, 4, 9, 6 }, 4));
	}

	// https://leetcode-cn.com/problems/find-the-most-competitive-subsequence/solution/java-dan-diao-zhan-by-thedesalizes/
	class Solution2 {
		public int[] mostCompetitive(int[] nums, int k) {
			Deque<Integer> deque = new LinkedList<Integer>();
			int len = nums.length;
			for (int i = 0; i < len; i++) {
				// 当前元素比队尾元素小，将队尾元素出栈
				// 此处需要另外判断数组剩余长度够不够填满栈，不然最后答案长度可能会小于k
				while (!deque.isEmpty() && nums[i] < deque.peek() && k - deque.size() < len - i) {
					deque.pop();
				}
				if (deque.size() <= k) {
					deque.push(nums[i]);
				}
			}
			int[] ret = new int[k];
			int index = 0;
			while (index<k) {
				ret[index++] = deque.pollLast();
			}
			return ret;
		}

	}
}
