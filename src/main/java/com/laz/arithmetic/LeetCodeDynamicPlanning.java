package com.laz.arithmetic;

import org.junit.Test;

public class LeetCodeDynamicPlanning {
	// 爬楼梯
	@Test
	public void test1() {
		System.out.println(climbStairs(5));
	}

	public int climbStairs(int n) {
		if (n == 1) {
			return 1;
		}
		int[] dp = new int[n + 1];
		dp[1] = 1;
		dp[2] = 2;
		for (int i = 3; i <= n; i++) {
			dp[i] = dp[i - 1] + dp[i - 2];
			System.out.println(dp[i]);
		}
		return dp[n];
	}

	// 买卖股票的最佳时机
	@Test
	public void test2() {
		int[] prices = new int[] { 7, 1, 5, 3, 6, 4 };
		System.out.println(maxProfit(prices));
	}

	public int maxProfit(int[] prices) {
		int max = 0;
		for (int i = 0; i < prices.length; i++) {
			for (int j = i; j < prices.length; j++) {
				int temp = prices[j] - prices[i];
				if (temp > max) {
					max = temp;
				}
			}
		}
		return max;
	}

	// 最大子序和
	@Test
	public void test3() {
		int[] nums = new int[] { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
		System.out.println(maxSubArray(nums));
	}

	// 最大子序和（动态规划）
	@Test
	public void test4() {
		int[] nums = new int[] { -2, 1, -3, 4, -1, 2, 1, -5, 4 };
		System.out.println(maxSubArray2(nums));
	}

	public int maxSubArray2(int[] nums) {
		int sum = 0;
		int res = nums[0];
		for (int i = 0; i < nums.length; i++) {
			if (sum > 0) {
				sum = sum + nums[i];
			} else {
				sum = nums[i];
			}
			res = Math.max(sum, res);

		}
		return res;
	}

	public int maxSubArray(int[] nums) {
		int max = 0;
		if (nums.length == 1) {
			return nums[0];
		}
		max = nums[0];
		for (int i = 0; i < nums.length; i++) {
			int temp = nums[i];
			for (int j = i; j < nums.length - 1; j++) {
				temp = temp + nums[j + 1];
				if (max < temp) {
					max = temp;
				}
			}
			if (max < nums[i]) {
				max = nums[i];
			}
		}
		return max;
	}

	// 打家劫舍
	@Test
	public void test5() {
		int[] nums = new int[] { 4, 1, 2, 7, 5, 3, 1 };
		System.out.println(rob(nums));
	}

	public int rob(int[] nums) {
		int prevMax = 0;
		int currMax = 0;
		for (int x : nums) {
			int temp = currMax;
			currMax = Math.max(prevMax + x, currMax);
			prevMax = temp;
		}
		return currMax;

	}

}
