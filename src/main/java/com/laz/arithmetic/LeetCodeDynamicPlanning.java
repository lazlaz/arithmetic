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
}
