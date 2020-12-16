package com.laz.arithmetic.competition;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-219/
public class Competition13 {
	// 比赛中的配对次数
	@Test
	public void test1() {
		Assert.assertEquals(6, numberOfMatches(7));
		Assert.assertEquals(13, numberOfMatches(14));
	}

	public int numberOfMatches(int n) {
		int res = 0;
		while (n > 1) {
			if (n % 2 == 0) {
				res += n / 2;
				n = n / 2;
			} else {
				res += (n - 1) / 2;
				n = (n - 1) / 2 + 1;
			}
		}
		return res;
	}

	// 十-二进制数的最少数目
	@Test
	public void test2() {
		Assert.assertEquals(3, minPartitions("32"));
		Assert.assertEquals(8, minPartitions("82734"));
	}

	public int minPartitions(String n) {
		// 规律，数字最大的数
		int max = 0;
		for (int i = 0; i < n.length(); i++) {
			int v = n.charAt(i) - '0';
			max = Math.max(v, max);
		}
		return max;
	}

	// 石子游戏 VII
	@Test
	public void test3() {
		Assert.assertEquals(122, stoneGameVII(new int[] { 7, 90, 5, 1, 100, 10, 10, 2 }));
	}

	//https://leetcode-cn.com/problems/stone-game-vii/solution/c-qu-jian-dp-si-lu-zheng-li-guo-cheng-by-3h0l/
	public int stoneGameVII(int[] stones) {
		int n = stones.length;
		int[][] sum = new int[n][n];
		for (int i=0;i<n;i++) {
			for (int j=i;j<n;j++) {
				if (i==j) {
					sum[i][j] = stones[i];
				} else {
					sum[i][j] = stones[j]+sum[i][j-1];
				}
			}
		}
		int[][] dp = new int[n][n];
		for (int i=n-1;i>=0;i--) {
			for (int j=i+1;j<n;j++) {
				if (j-i==1) {
					dp[i][j] = Math.max(stones[i],stones[j]);
				} else {
					dp[i][j] = Math.max(sum[i+1][j]-dp[i+1][j], sum[i][j-1]-dp[i][j-1]);
				}
			}
		}
		return dp[0][n-1];
 	}
}
