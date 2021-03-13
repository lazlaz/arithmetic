package com.laz.arithmetic.competition;

import org.junit.Test;

import org.junit.Assert;

//https://leetcode-cn.com/contest/weekly-contest-231/
public class Competition21 {
	// 1785. 构成特定和需要添加的最少元素
	@Test
	public void test2() {
		Assert.assertEquals(2, minElements(new int[] {
				1,-1,1
		},3,-4));
	}

	public int minElements(int[] nums, int limit, int goal) {
		long sum = 0l;
		for (int i=0;i<nums.length;++i) {
			sum+=nums[i];
		}
		long diff = Math.abs(goal-sum);
		int res = 0;
		//贪心,优先减最多
		res=diff%limit==0?(int)(diff/limit):(int)(diff/limit)+1;
		return res;
	}
}
