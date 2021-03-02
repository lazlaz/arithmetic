package com.laz.arithmetic.competition;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-229
public class Competition19 {
	// 5685. 交替合并字符串
	@Test
	public void test1() {
		Assert.assertEquals("apbqcr", mergeAlternately("abc", "pqr"));
		Assert.assertEquals("apbqrs", mergeAlternately("ab", "pqrs"));
		Assert.assertEquals("apbqcd", mergeAlternately("abcd", "pq"));
	}

	public String mergeAlternately(String word1, String word2) {
		StringBuilder sb = new StringBuilder();
		int i = 0, j = 0;
		int count = 0;
		while (i < word1.length() && j < word2.length()) {
			if (count % 2 == 0) {
				sb.append(word1.charAt(i++));
			} else {
				sb.append(word2.charAt(j++));
			}
			count++;
		}
		if (i < word1.length()) {
			sb.append(word1.substring(i, word1.length()));
		}
		if (j < word1.length()) {
			sb.append(word2.substring(j, word2.length()));
		}
		return sb.toString();
	}

	// 1769. 移动所有球到每个盒子所需的最小操作数
	@Test
	public void test2() {
		Assert.assertArrayEquals(new int[] { 1, 1, 3 }, minOperations("110"));

		Assert.assertArrayEquals(new int[] { 11, 8, 5, 4, 3, 4 }, minOperations("001011"));
	}

	public int[] minOperations(String boxes) {
		int[] res = new int[boxes.length()];
		for (int i = 0; i < boxes.length(); i++) {
			int count = 0;
			for (int j = 0; j < boxes.length(); j++) {
				if (boxes.charAt(j) == '1') {
					count = count + Math.abs(j - i);
				}
			}
			res[i] = count;
		}
		return res;
	}

	// https://leetcode-cn.com/problems/minimum-number-of-operations-to-move-all-balls-to-each-box/solution/xia-yi-ge-he-zi-suo-xu-yao-de-cao-zuo-sh-1thw/
	public int[] minOperations2(String boxes) {
		int[] answer = new int[boxes.length()];
		int left = 0, right = 0, total = 0;// 左边盒子的个数，右边盒子的个数，操作数
		// 计算第一个盒子的操作数
		if (boxes.charAt(0) == '1')
			left++;
		for (int i = 1; i < boxes.length(); i++) {
			if (boxes.charAt(i) == '1') {
				right++;
				total += i;
			}
		}
		answer[0] = total;
		// 根据前一个盒子的操作数，计算下一个盒子的操作数
		for (int i = 1; i < boxes.length(); i++) {
			total = total + left - right;
			if (boxes.charAt(i) == '1') {
				left++;
				right--;
			}
			answer[i] = total;
		}
		return answer;

	}

	// 1770. 执行乘法运算的最大分数
	@Test
	public void test3() {
		Assert.assertEquals(14, maximumScore(new int[] { 1, 2, 3 }, new int[] { 3, 2, 1 }));
	}

	// https://leetcode-cn.com/problems/maximum-score-from-performing-multiplication-operations/solution/java-dpjie-fa-by-sadfriedrice-8su0/
	public int maximumScore(int[] nums, int[] multipliers) {
		int m = multipliers.length;
		/**
		 * 
		 * dp[i][j] i代表左边选取了多少个数时的最大值 j代表右边选取了多少个数时的最大值
		 * 
		 * 公式： dp[l][r] = dp[l][r - 1] + mul * （右边最后一个数字); dp[l][r] = max(dp[l][r],dp[l
		 * - 1][r] + mul * (左边最后一个数字));
		 */
		int[][] dp = new int[m + 1][m + 1];
		dp[1][0] = nums[0] * multipliers[0];
		dp[0][1] = nums[nums.length - 1] * multipliers[0];
		for (int i = 2; i <= m; i++) {
			int mul = multipliers[i - 1];
			for (int l = 0; l <= i; l++) {
				int r = i - l;
				int nums_index = nums.length - (i - l);
				if (l == 0) {
					dp[l][r] = dp[l][r - 1] + mul * nums[nums_index];
					continue;
				}
				if (r == 0) {
					dp[l][r] = dp[l - 1][r] + mul * nums[l - 1];
					continue;
				}
				dp[l][r] = dp[l - 1][r] + mul * nums[l - 1];
				dp[l][r] = Math.max(dp[l][r], dp[l][r - 1] + mul * nums[nums_index]);
			}
		}
		int ans = Integer.MIN_VALUE;
		for (int i = 0; i <= m; i++) {
			ans = Math.max(dp[i][m - i], ans);
		}
		return ans;
	}
}
