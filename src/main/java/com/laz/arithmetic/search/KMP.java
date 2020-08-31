package com.laz.arithmetic.search;

import org.junit.Assert;
import org.junit.Test;

/**
 * 字符串模式匹配算法 可以在O(n+m)的时间复杂度以内完成字符串的匹配操作
 *https://blog.csdn.net/bjweimengshu/article/details/104528964/
 */
public class KMP {
	@Test
	public void test() {
		Assert.assertEquals(6, kmp("abcabaabaabcacb", "abaabcac"));
	}

	// KMP算法主体逻辑。str是主串，pattern是模式串
	public static int kmp(String str, String pattern) {
		// 预处理，生成next数组
		int[] next = getNexts(pattern);
		int j = 0;
		// 主循环，遍历主串字符
		for (int i = 0; i < str.length(); i++) {
			while (j > 0 && str.charAt(i) != pattern.charAt(j)) {
				// 遇到坏字符时，查询next数组并改变模式串的起点
				j = next[j];
			}
			if (str.charAt(i) == pattern.charAt(j)) {
				j++;
			}
			if (j == pattern.length()) {
				// 匹配成功，返回下标
				return i - pattern.length() + 1;
			}
		}
		return -1;
	}

	// 生成Next数组
	private static int[] getNexts(String pattern) {
		int[] next = new int[pattern.length()];
		int j = 0;
		for (int i = 2; i < pattern.length(); i++) {
			while (j != 0 && pattern.charAt(j) != pattern.charAt(i - 1)) {
				// 从next[i+1]的求解回溯到 next[j]
				j = next[j];
			}
			if (pattern.charAt(j) == pattern.charAt(i - 1)) {
				j++;
			}
			next[i] = j;
		}
		return next;
	}

}
