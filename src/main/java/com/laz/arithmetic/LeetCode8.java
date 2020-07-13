package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Joiner;

import org.junit.*;

/**
 * 
 *
 */
public class LeetCode8 {
	// 计算右侧小于当前元素的个数
	@Test
	public void test1() {
		int[] nums = new int[] { 5, 2, 6, 1 };
		List<Integer> res = countSmaller(nums);
		System.out.println(Joiner.on(",").join(res));
	}

	private int[] c;
	private int[] a;

	// 树壮数组+离散化
	public List<Integer> countSmaller(int[] nums) {
		List<Integer> resultList = new ArrayList<Integer>();
		discretization(nums);
		init(nums.length + 5);
		for (int i = nums.length - 1; i >= 0; --i) {
			int id = getId(nums[i]);
			resultList.add(query(id - 1));
			update(id, 1);
		}
		Collections.reverse(resultList);
		return resultList;
	}

	private void init(int length) {
		c = new int[length];
		Arrays.fill(c, 0);
	}

	private int lowBit(int x) {
		return x & (-x);
	}

	private void update(int pos, int k) {
		while (pos < c.length) {
			c[pos] += k;
			pos += lowBit(pos);
		}
	}

	// 查询sum[i]= C[i] + C[i-2k1] + C[(i - 2k1) - 2k2] + .....； 前缀和
	private int query(int pos) {
		int ret = 0;
		while (pos > 0) {
			ret += c[pos];
			pos -= lowBit(pos);
		}

		return ret;
	}

	private void discretization(int[] nums) {
		Set<Integer> set = new HashSet<Integer>();
		for (int num : nums) {
			set.add(num);
		}
		int size = set.size();
		a = new int[size];
		int index = 0;
		for (int num : set) {
			a[index++] = num;
		}
		Arrays.sort(a);
	}

	private int getId(int x) {
		return Arrays.binarySearch(a, x) + 1;
	}

	// 暴力解法超时
	public List<Integer> countSmaller2(int[] nums) {
		List<Integer> res = new ArrayList<Integer>();
		if (nums == null) {
			return res;
		}
		for (int i = 0; i < nums.length; i++) {
			int count = 0;
			for (int j = i; j < nums.length; j++) {
				if (nums[j] < nums[i]) {
					count++;
				}
			}
			res.add(count);
		}
		return res;
	}

	// 字符串相乘
	@Test
	public void test2() {
		Assert.assertEquals("56088", multiply("123", "456"));
	}

	/**
	 * 计算形式 num1 x num2 ------ result
	 */
	public String multiply(String num1, String num2) {
		if (num1.equals("0") || num2.equals("0")) {
			return "0";
		}
		// 保存计算结果
		String res = "0";

		// num2 逐位与 num1 相乘
		for (int i = num2.length() - 1; i >= 0; i--) {
			int carry = 0;
			// 保存 num2 第i位数字与 num1 相乘的结果
			StringBuilder temp = new StringBuilder();
			// 补 0
			for (int j = 0; j < num2.length() - 1 - i; j++) {
				temp.append(0);
			}
			int n2 = num2.charAt(i) - '0';

			// num2 的第 i 位数字 n2 与 num1 相乘
			for (int j = num1.length() - 1; j >= 0 || carry != 0; j--) {
				int n1 = j < 0 ? 0 : num1.charAt(j) - '0';
				int product = (n1 * n2 + carry) % 10;
				temp.append(product);
				carry = (n1 * n2 + carry) / 10;
			}
			// 将当前结果与新计算的结果求和作为新的结果
			res = addStrings(res, temp.reverse().toString());
		}
		return res;
	}

	/**
	 * 对两个字符串数字进行相加，返回字符串形式的和
	 */
	public String addStrings(String num1, String num2) {
		StringBuilder builder = new StringBuilder();
		int carry = 0;
		for (int i = num1.length() - 1, j = num2.length() - 1; i >= 0 || j >= 0 || carry != 0; i--, j--) {
			int x = i < 0 ? 0 : num1.charAt(i) - '0';
			int y = j < 0 ? 0 : num2.charAt(j) - '0';
			int sum = (x + y + carry) % 10;
			builder.append(sum);
			carry = (x + y + carry) / 10;
		}
		return builder.reverse().toString();
	}

	// 地下城游戏
	@Test
	public void test3() {
		int[][] dungeon = new int[][] { { -2, -3, 3 }, { -5, -10, 1 }, { 10, 30, -5 } };
		System.out.println(calculateMinimumHP(dungeon));
	}

	public int calculateMinimumHP(int[][] dungeon) {
		int n = dungeon.length, m = dungeon[0].length;
		int[][] dp = new int[n + 1][m + 1];
		for (int i = 0; i <= n; ++i) {
			Arrays.fill(dp[i], Integer.MAX_VALUE);
		}
		// 令dp[i][j] 表示从坐标 (i,j) 到终点所需的最小初始值
		/**
		 * 边界条件为，当 i=n-1i=n−1 或者 j=m-1j=m−1 时， dp[i][j]dp[i][j] 转移需要用到的
		 * dp[i][j+1]dp[i][j+1] 和 dp[i+1][j]dp[i+1][j] 中有无效值，因此代码实现中给无效值赋值为极大值。
		 * 特别地，dp[n-1][m-1]dp[n−1][m−1] 转移需要用到的 dp[n-1][m]dp[n−1][m]
		 */
		// 必须有1点血
		dp[n][m - 1] = 1;
		dp[n - 1][m] = 1;
		for (int i = n - 1; i >= 0; --i) {
			for (int j = m - 1; j >= 0; --j) {
				int minn = Math.min(dp[i + 1][j], dp[i][j + 1]);
				dp[i][j] = Math.max(minn - dungeon[i][j], 1);
			}
		}
		return dp[0][0];
	}

	// 接雨水
	@Test
	public void test4() {
		Assert.assertEquals(6, trap(new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 }));
	}

	public int trap(int[] height) {
		int left = 0, right = height.length - 1;
		int ans = 0;
		int leftMax = 0, rightMax = 0;
		while (left < right) {
			if (height[left] < height[right]) {
				if (height[left] >= leftMax) {
					leftMax = height[left];
				} else {
					ans += (leftMax - height[left]);
				}
				++left;
			} else {
				if (height[right] >= rightMax) {
					rightMax = height[right];
				} else {
					ans += (rightMax - height[right]);
				}
				--right;
			}
		}
		return ans;
	}

	// 两个数组的交集 II
	@Test
	public void test5() {
		Assert.assertArrayEquals(new int[] { 2, 2 }, intersect(new int[] { 1, 2, 2, 1 }, new int[] { 2, 2 }));
		Assert.assertArrayEquals(new int[] { 4, 9 }, intersect(new int[] { 4, 9, 5 }, new int[] { 9, 4, 9, 8, 4 }));
	}

	public int[] intersect(int[] nums1, int[] nums2) {
		int[] res = new int[] {};
		if (nums1 == null || nums2 == null) {
			return res;
		}
		if (nums1.length == 0 || nums2.length == 0) {
			return res;
		}
		Arrays.sort(nums1);
		Arrays.sort(nums2);
		int i = 0;
		int j = 0;
		List<Integer> list = new ArrayList<Integer>();
		while (i < nums1.length && j < nums2.length) {
			if (nums1[i] == nums2[j]) {
				list.add(nums1[i]);
				j++;
				i++;
			} else if (nums1[i] > nums2[j]) {
				j++;
			} else if (nums1[i] < nums2[j]) {
				i++;
			}
		}
		res = new int[list.size()];
		int count = 0;
		for (Integer integer : list) {
			res[count++] = integer;
		}
		return res;
	}

	// 通配符匹配
	@Test
	public void test6() {
		//Assert.assertEquals(false, isMatch("aa","a"));
		Assert.assertEquals(true, isMatch("aa","a*"));
	}

	public boolean isMatch(String s, String p) {
		int m = s.length();
		int n = p.length();
		boolean[][] dp = new boolean[m + 1][n + 1];
		dp[0][0] = true;
		// dp[i][j] 表示字符串 s的前 i个字符和模式 p的前 j个字符是否能匹配
		for (int i = 1; i <= n; ++i) {
			if (p.charAt(i - 1) == '*') {
				dp[0][i] = true;
			} else {
				break;
			}
		}
		for (int i = 1; i <= m; ++i) {
			for (int j = 1; j <= n; ++j) {
				if (p.charAt(j - 1) == '*') {
					dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
				} else if (p.charAt(j - 1) == '?' || s.charAt(i - 1) == p.charAt(j - 1)) {
					dp[i][j] = dp[i - 1][j - 1];
				}
			}
		}
		return dp[m][n];
	}
}
