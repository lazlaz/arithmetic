package com.laz.arithmetic;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class LeetCode18 {
	// 最大间距
	@Test
	public void test1() {
		Assert.assertEquals(3, maximumGap(new int[] { 3, 6, 9, 1 }));
	}

	public int maximumGap(int[] nums) {
		if (nums==null || nums.length<2) {
			return 0;
		}
		Arrays.sort(nums);
		int max = Integer.MIN_VALUE;
		for (int i=1;i<nums.length;i++) {
			if (nums[i]-nums[i-1]>max) {
				max = nums[i]-nums[i-1];
			}
		}
		return max;
	}
}
