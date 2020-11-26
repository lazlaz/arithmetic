package com.laz.arithmetic.swordoffer;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class Offer1 {
	// 剑指 Offer 03. 数组中重复的数字
	@Test
	public void test1() {
		Assert.assertEquals(2, findRepeatNumber(new int[] { 2, 3, 1, 0, 2, 5, 3 }));
	}

	public int findRepeatNumber(int[] nums) {
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		for (int i=0;i<nums.length;i++) {
			int v = map.getOrDefault(nums[i], 0);
			if (v>0) {
				return nums[i];
			} else {
				map.put(nums[i],1);
			}
 		}
		return -1;
	}
	
	// 剑指 Offer 04. 二维数组中的查找
	@Test
	public void test2() {
		Assert.assertEquals(true, findNumberIn2DArray(new int[][] { { 1, 4, 7, 11, 15 }, { 2, 5, 8, 12, 19 },
				{ 3, 6, 9, 16, 22 }, { 10, 13, 14, 17, 24 }, { 18, 21, 23, 26, 30 } }, 5));
		
		Assert.assertEquals(false, findNumberIn2DArray(new int[][] { { 1, 4, 7, 11, 15 }, { 2, 5, 8, 12, 19 },
			{ 3, 6, 9, 16, 22 }, { 10, 13, 14, 17, 24 }, { 18, 21, 23, 26, 30 } }, 20));
	}

	public boolean findNumberIn2DArray(int[][] matrix, int target) {
		int n = matrix.length;
		if (n==0) {
			return false;
		}
		int m = matrix[0].length;
		int i=n-1;
		int j = 0;
		while (i>=0 && j<m) {
			if (matrix[i][j] <target) {
				j++;
			} else if (matrix[i][j] >target) {
				i--;
			} else {
				return true;
			}
		}
		return false;
	}
	
	//剑指 Offer 05. 替换空格
	@Test
	public void test3() {
		Assert.assertEquals("We%20are%20happy.", replaceSpace("We are happy."));
	}
	public String replaceSpace(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<s.length();i++) {
			if (s.charAt(i) == ' ') {
				sb.append("%20");
			} else {
				sb.append(s.charAt(i));
			}
		}
		return sb.toString();
    }
	
}
