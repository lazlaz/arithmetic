package com.laz.arithmetic.competition;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-229
public class Competition19 {
	// 5685. 交替合并字符串
	@Test
	public void test1() {
		Assert.assertEquals("apbqcr", mergeAlternately("abc","pqr"));
		Assert.assertEquals("apbqrs", mergeAlternately("ab","pqrs"));
		Assert.assertEquals("apbqcd", mergeAlternately("abcd","pq"));
	}

	public String mergeAlternately(String word1, String word2) {
		StringBuilder sb = new StringBuilder();
		int i=0,j=0;
		int count = 0;
		while (i<word1.length()&&j<word2.length()) {
			if (count%2==0) {
				sb.append(word1.charAt(i++));
			} else {
				sb.append(word2.charAt(j++));
			}
			count++;
		}
		if (i<word1.length()) {
			sb.append(word1.substring(i, word1.length()));
		}
		if (j<word1.length()) {
			sb.append(word2.substring(j, word2.length()));
		}
		return sb.toString();
	}
}
