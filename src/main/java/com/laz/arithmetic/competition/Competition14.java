package com.laz.arithmetic.competition;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-220/
public class Competition14 {
	// 5629. 重新格式化电话号码
	@Test
	public void test1() {
		Assert.assertEquals("123-456", reformatNumber("1-23-45 6"));

		Assert.assertEquals("123-456-78", reformatNumber("123 4-5678"));
	}

	public String reformatNumber(String number) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < number.length(); i++) {
			if (number.charAt(i) != ' ' && number.charAt(i) != '-') {
				sb.append(number.charAt(i));
			}
		}
		StringBuilder ret = new StringBuilder();
		int start = 0;
		int end = sb.length();
		while ((end - start) > 4) {
			ret.append(sb.substring(start, start + 3));
			ret.append("-");
			start += 3;
		}
		if (end - start == 4) {
			ret.append(sb.substring(start, start + 2));
			ret.append("-");
			start += 2;
			ret.append(sb.substring(start, start + 2));
		} else {
			ret.append(sb.substring(start, end));
		}
		return ret.toString();
	}

	// 5630. 删除子数组的最大得分
	@Test
	public void test2() {
		Assert.assertEquals(17, maximumUniqueSubarray(new int[] { 4, 2, 4, 5, 6 }));

		Assert.assertEquals(8, maximumUniqueSubarray(new int[] { 5, 2, 1, 2, 5, 2, 1, 2, 5 }));
	}

	public int maximumUniqueSubarray(int[] nums) {

		int max = 0;
		int[] prefSum = new int[nums.length];
		prefSum[0] = nums[0];
		for (int i = 1; i < nums.length; i++) {
			prefSum[i] = prefSum[i - 1] + nums[i];
		}
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int startIndex = 0;
		for (int i = 0; i < nums.length; i++) {
			if (map.containsKey(nums[i])) {
				int sum = prefSum[i - 1] - prefSum[startIndex] + nums[startIndex];
				max = Math.max(max, sum);
				int oldIndex = map.get(nums[i]);
				if (oldIndex >= startIndex) {// 在范围内
					startIndex = map.get(nums[i]) + 1;
				}
			}
			map.put(nums[i], i);
		}
		if (startIndex < nums.length) {
			int sum = prefSum[nums.length - 1] - prefSum[startIndex] + nums[startIndex];
			max = Math.max(max, sum);
		}
		return max;
	}

	// 5631. 跳跃游戏 VI
	@Test
	public void test3() {
		Assert.assertEquals(7, maxResult(new int[] { 1, -1, -2, 4, -7, 3 }, 2));
		
		Assert.assertEquals(17, maxResult(new int[] { 10,-5,-2,4,0,3 }, 3));
		
		Assert.assertEquals(0, maxResult(new int[] { 1,-5,-20,4,-1,3,-6,-3 }, 2));
		
		Assert.assertEquals(-3, maxResult(new int[] { 1,-5,-20,4,-1,3,-3,-6 }, 2));
	}

	public int maxResult(int[] nums, int k) {
		int ret = nums[0];
		int n = nums.length;
		int index = 0;
		while (index<n-1) {
			int maxIndex = index+1;
			for (int i=index+1;i<=(index+k) && i<n;i++) {
				if (nums[i]>0) {
					maxIndex = i;
					break;
				}
				if (nums[maxIndex]<nums[i]) {
					maxIndex = i;
				}
			}
			if (nums[maxIndex] < 0 && (index+k)>=n-1) {
				index = n-1;
				ret+=nums[n-1];
			} else {
				index = maxIndex;
				ret+=nums[maxIndex];
			}
		}
		return ret;
	}
}
