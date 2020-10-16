package com.laz.arithmetic;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class LeetCode15 {
	// 比特位计数
	@Test
	public void test() {
		Assert.assertArrayEquals(new int[] { 0, 1, 1, 2, 1, 2 }, countBits(5));
	}

	public int[] countBits(int num) {
		int[] ans = new int[num + 1];
		int i = 0, b = 1;
		// [0, b) is calculated
		while (b <= num) {
			// generate [b, 2b) or [b, num) from [0, b)
			while (i < b && i + b <= num) {
				ans[i + b] = ans[i] + 1;
				++i;
			}
			i = 0; // reset i
			b <<= 1; // b = 2b
		}
		return ans;
	}
	
	//有序数组的平方
	@Test
	public void test2() {
		Assert.assertArrayEquals(new int[] {4,9,9,49,121}, sortedSquares(new int[]{-7,-3,2,3,11}));
	}
	public int[] sortedSquares(int[] A) {
		int[] res = new int[A.length];
		for (int i=0;i<A.length;i++) {
			res[i] = A[i]*A[i];
		}
		Arrays.sort(res);
		return res;
	}
}
