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
}
