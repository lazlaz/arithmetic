package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode6 {
	//三数之和
	@Test
	public void test1() {
		int[] nums = new int[] {-1,2,4,2,0,-2,-4};
		List<List<Integer>> list = threeSum(nums);
		System.out.println(Joiner.on(",").join(list));
	}
	//思路参考 https://leetcode-cn.com/problems/3sum/solution/hua-jie-suan-fa-15-san-shu-zhi-he-by-guanpengchn/
    public static List<List<Integer>> threeSum(int[] nums) {
    	List<List<Integer>> ans = new ArrayList<List<Integer>>();
    	if(nums == null || nums.length < 3) return ans;
    	Arrays.sort(nums);
    	for (int i=0;i<nums.length;i++) {
    		if (nums[i] > 0) {
    			continue;
    		}
    		if (i>0 && nums[i] == nums[i-1]) {
    			continue;
    		}
    		int L = i+1;
    		int R = nums.length-1;
    		while (L < R) {
    			int sum = nums[i]+nums[L]+nums[R];
    			if (sum == 0) {
    				ans.add(Arrays.asList(nums[i],nums[L],nums[R]));
    				while (L<R && nums[L] == nums[L+1]) L++; // 去重
                    while (L<R && nums[R] == nums[R-1]) R--; // 去重
    				L++;
    				R--;
    			}
    			else if (sum < 0) L++;
                else if (sum > 0) R--;
    		}
    	}
    	return ans;
    }
}
