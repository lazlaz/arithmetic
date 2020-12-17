package com.laz.arithmetic.competition;

import java.util.Arrays;

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

	// https://leetcode-cn.com/problems/stone-game-vii/solution/c-qu-jian-dp-si-lu-zheng-li-guo-cheng-by-3h0l/
	public int stoneGameVII(int[] stones) {
		int n = stones.length;
		int[][] sum = new int[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				if (i == j) {
					sum[i][j] = stones[i];
				} else {
					sum[i][j] = stones[j] + sum[i][j - 1];
				}
			}
		}
		int[][] dp = new int[n][n];
		for (int i = n - 1; i >= 0; i--) {
			for (int j = i + 1; j < n; j++) {
				if (j - i == 1) {
					dp[i][j] = Math.max(stones[i], stones[j]);
				} else {
					dp[i][j] = Math.max(sum[i + 1][j] - dp[i + 1][j], sum[i][j - 1] - dp[i][j - 1]);
				}
			}
		}
		return dp[0][n - 1];
	}

	// 堆叠长方体的最大高度
	@Test
	public void test4() {
		Assert.assertEquals(190, maxHeight(new int[][] { { 50, 45, 20 }, { 95, 37, 53 }, { 45, 23, 12 } }));
	}

	// https://leetcode-cn.com/problems/maximum-height-by-stacking-cuboids/solution/javadong-tai-gui-hua-jie-jue-dui-die-cha-ad0c/
	public int maxHeight(int[][] cuboids) {
		// // 首先，将各个长方体数组内部的元素（即边长）从小到大进行排序
		for (int[] cuboid : cuboids) {
			Arrays.sort(cuboid);
		}
		// // 随后将整个长方体数组按照最短边长、次长边长、最长边长进行排序
		Arrays.sort(cuboids, (o1, o2) -> {
			if (o1[0] != o2[0]) {
				return Integer.compare(o1[0], o2[0]);
			} else if (o1[1] != o2[1]) {
				return Integer.compare(o1[1], o2[1]);
			} else {
				return Integer.compare(o1[2], o2[2]);
			}
		});
		// dp[i]表示【以第i个长方体为底】的【堆叠长方体】的最大高度
		int[] dp = new int[cuboids.length];
		int maxAns = 0;
		// 显然，我们需要找出可以放在第i个长方体上方的所有【堆叠长方体】的最大高度
		// 加上第i个长方体的高度，就可以得出【以第i个长方体为底】的【堆叠长方体】的最大高度
		for (int i = 0; i < cuboids.length; i++) {
			for (int j = 0; j < i; j++) {
				// 虽然我们已经做了排序，但是还不能保证前面的长方体一定能够放在后面的长方体上方，只能保证后面的长方体一定不能放在前面的长方体上
				// 因此需要依次进行判断前面各个【堆叠长方体】能不能放置在第i个长方体上方，并找出前面各个【堆叠长方体】的最大高度的【最大值】
				if (cuboids[j][1] <= cuboids[i][1] && cuboids[j][2] <= cuboids[i][2]) {
					dp[i] = Math.max(dp[i], dp[j]);
				}
			}
			dp[i] += cuboids[i][2];
			// 更新所有【堆叠长方体】的最大高度
			maxAns = Math.max(maxAns, dp[i]);
		}
		return maxAns;
	}
}
