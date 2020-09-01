package com.laz.arithmetic;

import org.junit.Test;

import java.util.Arrays;

import org.junit.Assert;

public class LeetCode12 {
	// 超级次方
	@Test
	public void test1() {
		Assert.assertEquals(1024, superPow(2, new int[] { 1,0 }));
	}
	int base = 1337;
	int mypow(int a, int k) {
		//(a * b) % k = (a % k)(b % k) % k
		if (k == 0) return 1;
	    a %= base;
	    int res = 1;
	    for (int i = 0; i < k; i++) {
	        // 这里有乘法，是潜在的溢出点
	        res *= a;
	        // 对乘法结果求模
	        res %= base;
	    }
	    return res;
	}
	public int superPow(int a, int[] b) {
		if (b.length==0)
			return 1;
		int last = b[b.length-1];
		int[] newB = Arrays.copyOf(b, b.length-1);
		int part1 = mypow(a, last);
		int part2 = mypow(superPow(a, newB), 10);
		// 每次乘法都要求模
		return (part1 * part2) % base;
	}
}
