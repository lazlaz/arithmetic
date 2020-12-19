package com.laz.arithmetic.type;

import org.junit.Assert;
import org.junit.Test;

//动态规划
public class DynamicPlanning {
	// 474. 一和零
	@Test
	public void test1() {
		Assert.assertEquals(4, new Solution1().findMaxForm(new String[] { "10", "0001", "111001", "1", "0" }, 5, 3));
	}

	// https://leetcode-cn.com/problems/ones-and-zeroes/solution/dong-tai-gui-hua-zhuan-huan-wei-0-1-bei-bao-wen-ti/
	class Solution1 {
		private int[] countZeroAndOne(String str) {
			int[] cnt = new int[2];
			for (char c : str.toCharArray()) {
				cnt[c - '0']++;
			}
			return cnt;
		}

		public int findMaxForm(String[] strs, int m, int n) {
			int k = strs.length;
			int[][][] dp = new int[k+1][m + 1][n + 1];
			for (int i = 1; i <= k; i++) {
				int[] cnt = countZeroAndOne(strs[i-1]);
				for (int j=0;j<=m;j++) {
					for (int z=0;z<=n;z++) {
						//不变
						dp[i][j][z] = dp[i - 1][j][z];
	                    int zeros = cnt[0];
	                    int ones = cnt[1];
	                    if (j >= zeros && z >= ones) {
	                    	//那个大取那个
	                        dp[i][j][z] = Math.max(dp[i - 1][j][z], dp[i - 1][j - zeros][z - ones] + 1);
	                    }
					}
				}
			}
			return dp[k][m][n];
		}
	}
}
