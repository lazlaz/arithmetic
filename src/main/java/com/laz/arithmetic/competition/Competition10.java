package com.laz.arithmetic.competition;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-216
public class Competition10 {
	@Test
	// 检查两个字符串数组是否相等
	public void test1() {
//		Assert.assertEquals(true, arrayStringsAreEqual(new String[] { "ab", "c" }, new String[] { "a", "bc" }));
//
//		Assert.assertEquals(false, arrayStringsAreEqual(new String[] { "a", "cb" }, new String[] { "ab", "c" }));
//
//		Assert.assertEquals(true,
//				arrayStringsAreEqual(new String[] { "abc", "d", "defg" }, new String[] { "abcddefg" }));

		Assert.assertEquals(true,
				arrayStringsAreEqual(
						new String[] { "elqgofejrdsnlielsiawuhsuyccyqprzwisiwhyvmmbbtznuvkishgscdaabgzbfgeuu",
								"kexmuzvgezawpcwio", "bcnyxs", "qxr" },
						new String[] { "elqgofejrdsnlielsiawuhsuyccyqprzwisiwhyvmmbbtznuvkishgsc",
								"daabgzbfgeuukexmuzvgezawpcwiobcnyx", "sqxr" }));
	}

	public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < word1.length; i++) {
			String str = word1[i];
			sb.append(str);
		}
		int index = 0;
		for (int i = 0; i < word2.length; i++) {
			String str = word2[i];
			String str2 = sb.substring(index, index + str.length());
			index += str.length();
			if (!str.equals(str2)) {
				return false;
			}
		}
		return true;
	}

	// 具有给定数值的最小字符串
	@Test
	public void test2() {
		Assert.assertEquals("aay", new Solution2().getSmallestString(3, 27));

		Assert.assertEquals("aaszz", new Solution2().getSmallestString(5, 73));

		Assert.assertEquals("aadzzzzzzzzzzzzzzzzzzzzz", new Solution2().getSmallestString(24, 552));
	}

	class Solution2 {
		// 思路，贪心算法，最后的值尽量选择最大的
		public String getSmallestString(int n, int k) {
			char[] arr = new char[n];
			int index = arr.length-1;
			while (n > 0) {
				if (k == n) {
					for (int i = 1; i <= n; i++) {
						arr[index--] = 'a';
					}
					break;
				}
				if (k - 26 > (n - 1)) {
					arr[index--] = 'z';
					k -= 26;
				} else {
					char c = (char) ('a' + (k - (n - 1) - 1));
					arr[index--] = c;
					k = k - (k - (n - 1));
				}
				n--;
			}
			return new String(arr);
		}
	}


	// 生成平衡数组的方案数
	@Test
	public void test3() {
		Assert.assertEquals(1, waysToMakeFair(new int[] { 2, 1, 6, 4 }));

		Assert.assertEquals(3, waysToMakeFair(new int[] { 1, 1, 1 }));

		Assert.assertEquals(0, waysToMakeFair(new int[] { 1, 2, 3 }));

		Assert.assertEquals(5, waysToMakeFair(new int[] { 1, 1, 1, 1, 1 }));

		Assert.assertEquals(1, waysToMakeFair(new int[] { 1 }));
	}

	public int waysToMakeFair(int[] nums) {
		if (nums.length == 1) {
			return 1;
		}
		int n = nums.length / 2;
		int[] oddSum = new int[n]; // 前缀奇数和
		int[] evenSum = new int[n]; // 前缀偶数和
		if (nums.length % 2 == 1) {
			evenSum = new int[n + 1];
		}
		int index1 = 0;
		int index2 = 0;
		for (int i = 0; i < nums.length; i++) {
			if (i % 2 == 0) {
				if (index1 > 0) {
					evenSum[index1] = evenSum[index1 - 1] + nums[i];
				} else {
					evenSum[index1] = evenSum[index1] + nums[i];
				}
				index1++;
			} else {
				if (index2 > 0) {
					oddSum[index2] = oddSum[index2 - 1] + nums[i];
				} else {
					oddSum[index2] = oddSum[index2] + nums[i];
				}
				index2++;
			}
		}
		int res = 0;
		for (int i = 0; i < nums.length; i++) {
			// 删除下标i
			int postEvenSum = evenSum[evenSum.length - 1] - evenSum[i / 2];
			int postOddSum = 0;
			if (i % 2 != 0) {
				postOddSum = i == 0 ? oddSum[oddSum.length - 1] : oddSum[oddSum.length - 1] - oddSum[i / 2];
			} else {
				postOddSum = i == 0 ? oddSum[oddSum.length - 1] : oddSum[oddSum.length - 1] - oddSum[i / 2 - 1];
			}
			int preEvenSum = evenSum[i / 2];
			int preOddSum = 0;
			if (i % 2 != 0) {
				preOddSum = i == 0 ? 0 : oddSum[i / 2];
			} else {
				preOddSum = i == 0 ? 0 : oddSum[i / 2 - 1];
			}
			if (i % 2 == 0) {
				// 删除的是前面的偶数
				preEvenSum -= nums[i];
			} else {
				preOddSum -= nums[i];
			}
			// 后缀奇数变偶数，偶数变奇数
			if ((preEvenSum + postOddSum) == (preOddSum + postEvenSum)) {
				res++;
			}
		}
		return res;
	}

	// 完成所有任务的最少初始能量
	@Test
	public void test() {
		Assert.assertEquals(8, minimumEffort(new int[][] { { 1, 2 }, { 2, 4 }, { 4, 8 } }));
	}

	public int minimumEffort(int[][] tasks) {
		return 0;
	}
}
