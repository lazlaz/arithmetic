package com.laz.arithmetic.competition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-218/
public class Competition12 {
	// 设计 Goal 解析器
	@Test
	public void test1() {
		Assert.assertEquals("Goal", interpret("G()(al)"));
		Assert.assertEquals("Gooooal", interpret("G()()()()(al)"));
		Assert.assertEquals("alGalooG", interpret("(al)G(al)()()G"));
	}

	public String interpret(String command) {
		StringBuilder res = new StringBuilder();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < command.length(); i++) {
			char c = command.charAt(i);
			if (c == 'G') {
				res.append("G");
			} else if (c == '(') {
				sb.append(c);
			} else if (c == ')') {
				sb.append(c);
				if (sb.toString().equals("()")) {
					res.append("o");
				}
				if (sb.toString().equals("(al)")) {
					res.append("al");
				}
				sb = new StringBuilder();
			} else {
				sb.append(c);
			}
		}
		return res.toString();
	}

	// K 和数对的最大数目
	@Test
	public void test2() {
		Assert.assertEquals(2, maxOperations(new int[] { 1, 2, 3, 4 }, 5));

		Assert.assertEquals(1, maxOperations(new int[] { 3, 1, 3, 4, 3 }, 6));
	}

	public int maxOperations(int[] nums, int k) {
		Arrays.sort(nums);
		int p = 0, q = nums.length - 1;
		int res = 0;
		while (p < q) {
			if (nums[p] + nums[q] == k) {
				res++;
				p++;
				q--;
			} else if (nums[p] + nums[q] > k) {
				q--;
			} else if (nums[p] + nums[q] < k) {
				p++;
			}
		}
		return res;
	}

	// 连接连续二进制数字
	@Test
	public void test3() {
		Assert.assertEquals(1, concatenatedBinary(1));
		Assert.assertEquals(27, concatenatedBinary(3));
		Assert.assertEquals(505379714, concatenatedBinary(12));
	}

	public int concatenatedBinary(int n) {
		final int MOD = 1000000007;
		int res = 0, shift = 0;
		for (int i = 1; i <= n; i++) {
			if ((i & (i - 1)) == 0) {
				// 判断是否为2的冥 2的1次方，2次方等等
				shift++;
			}
			res = (int) ((((long) res << shift) + i) % MOD);
		}
		return res;
	}

	// 最小不兼容性 Dont't Know
	@Test
	public void test4() {
		Assert.assertEquals(4, new Solution4().minimumIncompatibility(new int[] { 1, 2, 1, 4 }, 2));
	}

	// https://leetcode-cn.com/problems/minimum-incompatibility/solution/java-zhuang-tai-ya-suo-dp-dai-zhu-shi-by-fkig/
	class Solution4 {
		public int minimumIncompatibility(int[] nums, int k) {
			int n = nums.length;
			int m = n / k;
			int[] dp = new int[1 << n];
			int[] value = new int[1 << n];
			Arrays.fill(value, -1);
			Arrays.fill(dp, Integer.MAX_VALUE);
			dp[0] = 0;
			// 预处理value数组，value[j]代表 j这个集合的 不兼容性
			for (int i = 0; i < (1 << n); i++) {
				int count = countOne(i);// 计算二进制中1的数量
				if (count == m) {// 条件1：子集中有m个1
					// 条件2：每个数字只能出现一次
					Map<Integer, Integer> map = new HashMap<>();// 来存子集中每个数字出现的次数
					boolean flag = true;
					int max = Integer.MIN_VALUE;
					int min = Integer.MAX_VALUE;
					for (int j = 0; j < n; j++) {
						if ((i >> j & 1) == 1) { // i的第j位=1, 代表选了nums[j]
							min = Math.min(min, nums[j]);
							max = Math.max(max, nums[j]);
							if (map.containsKey(nums[j])) {
								flag = false;
								break;
							} else {
								map.put(nums[j], 1);
							}
						}
					}
					// 如果这个子集合法
					if (flag) {
						value[i] = max - min;// 更新子集i的不兼容性
					}
				}
			}
			// 进行状态转移 ,dp[i]代表当选择的元素集合为i时最小的不兼容性的和
			for (int i = 0; i < (1 << n); i++) {
				if (countOne(i) % m == 0) {// 当前的选择方式组合mask是否有n/k的倍数个1
					// 枚举最后一个选择的子集j
					for (int j = i; j != 0; j = (j - 1) & i) {
						if (value[j] != -1 && dp[i ^ j] != Integer.MAX_VALUE) { // i^j表示从i中去掉j这个集合
							dp[i] = Math.min(dp[i], dp[i ^ j] + value[j]);
						}
					}
				}
			}
			return dp[(1 << n) - 1] == Integer.MAX_VALUE ? -1 : dp[(1 << n) - 1];// 最后返回dp[1111111],表示每个数字都选了以后
		}
		public int countOne(int x) {
			int res = 0;
			while (x != 0) {
				x &= (x - 1);
				res++;
			}
			return res;
		}
	}
}
