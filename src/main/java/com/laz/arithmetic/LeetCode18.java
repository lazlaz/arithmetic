package com.laz.arithmetic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

public class LeetCode18 {
	// 最大间距
	@Test
	public void test1() {
		Assert.assertEquals(3, maximumGap(new int[] { 3, 6, 9, 1 }));
	}

	public int maximumGap(int[] nums) {
		if (nums == null || nums.length < 2) {
			return 0;
		}
		Arrays.sort(nums);
		int max = Integer.MIN_VALUE;
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] - nums[i - 1] > max) {
				max = nums[i] - nums[i - 1];
			}
		}
		return max;
	}

	// 翻转对
	@Test
	public void test2() {
		Assert.assertEquals(2, new Solution2().reversePairs(new int[] { 1, 3, 2, 3, 1 }));

		Assert.assertEquals(3, new Solution2().reversePairs(new int[] { 2, 4, 3, 5, 1 }));

		Assert.assertEquals(1, new Solution2().reversePairs(new int[] { -5, -5 }));
		Assert.assertEquals(0, new Solution2()
				.reversePairs(new int[] { 2147483647, 2147483647, 2147483647, 2147483647, 2147483647, 2147483647 }));
	}

	// https://leetcode-cn.com/problems/reverse-pairs/solution/fan-zhuan-dui-by-leetcode-solution/
	class Solution2 {
		public int reversePairs(int[] nums) {
			if (nums.length == 0) {
				return 0;
			}
			return reversePairsRecursive(nums, 0, nums.length - 1);
		}

		public int reversePairsRecursive(int[] nums, int left, int right) {
			if (left == right) {
				return 0;
			} else {
				int mid = (left + right) / 2;
				int n1 = reversePairsRecursive(nums, left, mid);
				int n2 = reversePairsRecursive(nums, mid + 1, right);
				int ret = n1 + n2;

				// 首先统计下标对的数量
				int i = left;
				int j = mid + 1;
				while (i <= mid) {
					while (j <= right && (long) nums[i] > 2 * (long) nums[j]) {
						j++;
					}
					ret += j - mid - 1;
					i++;
				}

				// 随后合并两个排序数组
				int[] sorted = new int[right - left + 1];
				int p1 = left, p2 = mid + 1;
				int p = 0;
				while (p1 <= mid || p2 <= right) {
					if (p1 > mid) {
						sorted[p++] = nums[p2++];
					} else if (p2 > right) {
						sorted[p++] = nums[p1++];
					} else {
						if (nums[p1] < nums[p2]) {
							sorted[p++] = nums[p1++];
						} else {
							sorted[p++] = nums[p2++];
						}
					}
				}
				for (int k = 0; k < sorted.length; k++) {
					nums[left + k] = sorted[k];
				}
				return ret;
			}
		}
	}

	// 三角形的最大周长
	@Test
	public void test3() {
		Assert.assertEquals(5, largestPerimeter(new int[] { 2, 1, 2 }));
	}

	// https://leetcode-cn.com/problems/largest-perimeter-triangle/solution/san-jiao-xing-de-zui-da-zhou-chang-by-leetcode-sol/
	public int largestPerimeter(int[] A) {
		Arrays.sort(A);
		for (int i = A.length - 1; i >= 2; --i) {
			if (A[i - 2] + A[i - 1] > A[i]) {
				return A[i - 2] + A[i - 1] + A[i];
			}
		}
		return 0;
	}

	// 重构字符串
	@Test
	public void test4() {
		Assert.assertEquals("aba", new Solution4().reorganizeString("aab"));
		Assert.assertEquals("", new Solution4().reorganizeString("aaab"));
	    Assert.assertEquals("acabababab", new Solution4().reorganizeString("aaaaabbbbc"));
		Assert.assertEquals("vovlv", new Solution4().reorganizeString("vvvlo"));
		Assert.assertEquals("ababababab", new Solution4().reorganizeString("abbabbaaab"));
		Assert.assertEquals("czcxmwbtcsmlombockgi", new Solution4().reorganizeString("ogccckcwmbmxtsbmozli"));
	}

	class Solution4 {
		public String reorganizeString(String S) {
			int[] arr = new int[26];
			for (int i = 0; i < S.length(); i++) {
				char c = S.charAt(i);
				arr[c - 'a']++;
			}

			StringBuilder sb = new StringBuilder();
			while (sb.length() < S.length()) {
				int max = 0;
				int maxIndex = -1;
				int min = Integer.MAX_VALUE;
				int minIndex = -1;
				// 找到最多的字符
				for (int i = 0; i < arr.length; i++) {
					if (arr[i] > max) {
						max = arr[i];
						maxIndex = i;
					}

				}
				// 找到最少的字符
				for (int i = 0; i < arr.length; i++) {
					if (arr[i] > 0 && arr[i] <= min && i != maxIndex) {
						min = arr[i];
						minIndex = i;
					}
				}
				// 依次最多的字符最少的字符，循环
				char maxC = (char) (maxIndex + 'a');
				sb.append(maxC);
				arr[maxIndex]--;
				max--;
				if (minIndex != -1 && maxIndex != minIndex) {
					char minC = (char) (minIndex + 'a');
					sb.append(minC);
					min--;
					arr[minIndex]--;
				}
				int len = sb.length();
				if (len > 1 && (sb.charAt(len - 1) == sb.charAt(len - 2))) {
					return "";
				}
			}
			return sb.toString();
		}

	}
}