package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class LeetCode11 {
	// 解码方法
	@Test
	public void test1() {
//		Assert.assertEquals(3, numDecodings("226"));
//		Assert.assertEquals(0, numDecodings("0"));
		Assert.assertEquals(0, numDecodings2("01"));
	}

	public int numDecodings2(String s) {
		int len = s.length();
		if (len == 0) {
			return 0;
		}

		// dp[i] 以 s[i] 结尾的前缀子串有多少种解码方法
		// dp[i] = dp[i - 1] * 1 if s[i] != '0'
		// dp[i] += dp[i - 2] * 1 if 10 <= int(s[i - 1..i]) <= 26
		int[] dp = new int[len];
		char[] charArray = s.toCharArray();
		if (charArray[0] == '0') {
			return 0;
		}
		dp[0] = 1;
		for (int i = 1; i < len; i++) {
			if (charArray[i] != '0') {
				dp[i] = dp[i - 1];
			}

			int num = 10 * (charArray[i - 1] - '0') + (charArray[i] - '0');
			if (num >= 10 && num <= 26) {
				if (i == 1) {
					dp[i]++;
				} else {
					dp[i] += dp[i - 2];
				}
			}
		}
		return dp[len - 1];
	}

	// 回溯算法超时
	public int numDecodings(String s) {
		if (s == null) {
			return 0;
		}
		List<List<String>> ret = new ArrayList<List<String>>();

		backtrack(ret, new ArrayList<String>(), 0, s);
		return ret.size();
	}

	private void backtrack(List<List<String>> ret, List<String> list, int start, String s) {
		int len = s.length();
		if (start < len) {
			String str = s.substring(start, start + 1);
			if (Integer.parseInt(str) > 0) {
				list.add(str);
				backtrack(ret, list, start + 1, s);
			}
		}
		if (start + 1 < len) {
			String str = s.substring(start, start + 2);
			if (Integer.parseInt(str) <= 26 && Integer.parseInt(str) >= 10) {
				list.add(str);
				backtrack(ret, list, start + 2, s);
			}
		}
		if (start >= len) {
			ret.add(new ArrayList<String>(list));
		}
	}
}
