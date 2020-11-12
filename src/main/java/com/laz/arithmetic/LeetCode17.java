package com.laz.arithmetic;

import org.junit.Test;

import org.junit.Assert;

public class LeetCode17 {
	// 按奇偶排序数组 II
	@Test
	public void test1() {
		Assert.assertArrayEquals(new int[] { 4, 5, 2, 7 }, sortArrayByParityII(new int[] { 4, 2, 5, 7 }));
	}

	public int[] sortArrayByParityII(int[] A) {
		int[] res = new int[A.length];
		int p=0,q=1;
		for (int i=0;i<A.length;i++) {
			if (A[i]%2==0) {
				res[p] = A[i];
				p=p+2;
			} else {
				res[q] = A[i];
				q=q+2;
			}
		}
		return res;
	}
}
