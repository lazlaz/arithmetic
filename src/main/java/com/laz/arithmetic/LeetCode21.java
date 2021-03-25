package com.laz.arithmetic;

import java.util.ArrayDeque;
import java.util.Deque;

import org.junit.Assert;
import org.junit.Test;

public class LeetCode21 {
	// 456. 132 模式
	@Test
	public void test1() {
		Assert.assertEquals(false, find132pattern(new int[] { 1, 2, 3, 4 }));
		Assert.assertEquals(true, find132pattern(new int[] { 3,1,4,2 }));
		Assert.assertEquals(true, find132pattern(new int[] { -1,3,2,0 }));
	}

	public boolean find132pattern(int[] nums) {
		//找到左侧最小的值并经历
		int n = nums.length;
		int[] leftMin = new int[n];
		leftMin[0] = nums[0];
		for (int i=1;i<nums.length;i++) {
			leftMin[i] = Math.min(leftMin[i-1], nums[i]);
		}
		//单调递减栈
		Deque<Integer> stack = new ArrayDeque<>();
		for (int i=n-1;i>=0;i--) {
			int max = Integer.MIN_VALUE; //当前栈中最大的元素
			while (!stack.isEmpty() && nums[i]>stack.peek()) {
				max = stack.poll();
			}
			//nums[i]>leftMin[i] nums[i]>max max>leftMin[i]满足132模式 
			if (max>leftMin[i] && nums[i]>leftMin[i]) {
				return true;
			}
			stack.push(nums[i]);
		}
		return false;
	}
}
