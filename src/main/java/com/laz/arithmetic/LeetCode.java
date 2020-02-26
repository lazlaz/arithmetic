package com.laz.arithmetic;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class LeetCode {

	// 多数元素
	@Test
	public void test1() {
		System.out.println(majorityElement(new int[] { 2, 2, 1, 1, 1, 2, 2 }));
	}

	public int majorityElement(int[] nums) {
		Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
		for (int i : nums) {
			if (counts.get(i) == null) {
				counts.put(i, 1);
			} else {
				int count = counts.get(i);
				counts.put(i, ++count);
			}
			if (counts.get(i) > nums.length / 2) {
				return i;
			}
		}
		return 0;

	}

	// 乘积最大子序列
	@Test
	public void test2() {
		System.out.println(maxProduct(new int[] { -3,0,1,-2}));
	}

	public int maxProduct(int[] nums) {
		int max = nums[0];
		for (int j=0; j<nums.length; j++) {
			int result = nums[j];
			if (result>max) {
				max = result;
			}
			for (int i=j+1; i<nums.length; i++) {
				result = result*nums[i];
				if (result>max) {
					max = result;
				}
			}
		
		}
		return max;
	}
}
