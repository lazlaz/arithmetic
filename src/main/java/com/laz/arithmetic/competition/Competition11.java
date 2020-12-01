package com.laz.arithmetic.competition;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeSet;

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
			while (index < k) {
				ret[index++] = deque.pollLast();
			}
			return ret;
		}

	}

	// 使数组互补的最少操作次数
	@Test
	public void test3() {
		Assert.assertEquals(1, minMoves(new int[] { 1, 2, 4, 3 }, 4));
	}

	// https://leetcode-cn.com/problems/minimum-moves-to-make-array-complementary/solution/javaonde-chai-fen-shu-zu-by-liusandao/
	public int minMoves(int[] nums, int limit) {
		int[] diff = new int[limit * 2 + 1]; // 最后和位i时，最少操作次数，用差分数组存储
		for (int i = 0; i < nums.length / 2; i++) {
			int max = Math.max(nums[i], nums[nums.length - i - 1]);
			int min = Math.min(nums[i], nums[nums.length - i - 1]);
			if (min == 1) {
				// 如果存在min==1, 为2的操作次数加1
				diff[2] += 1;
			} else {
				// 如果存在min!=1, 为2的操作次数加2
				diff[2] += 2;
				diff[min + 1] -= 1;
			}
			diff[max + min] -= 1;
			if (max + min + 1 < diff.length) {
				diff[max + min + 1] += 1;
			}
			if (max + limit + 1 < diff.length) {
				diff[max + limit + 1] += 1;
			}
		}
		int now = 0, res = nums.length / 2;
		for (int i = 2; i < diff.length; i++) {
			now += diff[i];
			res = Math.min(res, now);
		}
		return res;
	}

	// 数组的最小偏移量
	@Test
	public void test4() {
		Assert.assertEquals(1, minimumDeviation(new int[] {1,2,3,4}));
	}
	//https://leetcode-cn.com/problems/minimize-deviation-in-array/solution/you-xu-ji-he-you-xian-dui-lie-xun-huan-chu-li-by-m/
	public int minimumDeviation(int[] nums) {
		TreeSet<Integer> set = new TreeSet<>();
        for (int num : nums) {
            set.add(num % 2 == 0 ? num : num * 2);
        }
        int res = set.last() - set.first();
        while (res > 0 && set.last() % 2 == 0) {
            int max = set.last();
            set.remove(max);
            set.add(max / 2);
            res = Math.min(res, set.last() - set.first());
        }
        return res;
	}
}
