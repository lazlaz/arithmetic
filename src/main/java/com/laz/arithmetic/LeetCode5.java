package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LeetCode5 {
	//拥有最多糖果的孩子
	@Test
	public void test1() {
		int[] candies = new int[] {2,3,5,1,3};
		int extraCandies = 3;
		List<Boolean> list = kidsWithCandies(candies, extraCandies);
		for (Boolean b : list) {
			System.out.println(b);
		}
	}
	 public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
		 int max = 0;
		 for (int i=0;i<candies.length;i++) {
			 if (max<candies[i]) {
				 max = candies[i];
			 }
		 }
		 List<Boolean> list = new ArrayList<Boolean>();
		 for (int i=0;i<candies.length;i++) {
			 if (candies[i]+extraCandies>=max) {
				 list.add(true);
			 } else {
				 list.add(false);
			 }
		 }
		 return list;
	 }
	 
	 
	 @Test
	 //除自身以外数组的乘积
	 public void test2() {
		 int[] nums = new int[] {1,2,3,4};
		 int[] res  = productExceptSelf(nums);
		 for (int i : res) {
			System.out.println(i);
		}
	 }
	 // 当前数左边的乘积 * 当前数右边的乘积
	 public int[] productExceptSelf(int[] nums) {
		 	int length = nums.length;
	        // L 和 R 分别表示左右两侧的乘积列表
	        int[] L = new int[length];
	        int[] R = new int[length];
	        int[] answer = new int[length];
	        L[0] = 1;
	        for (int i=1;i<length;i++) {
	        	L[i] = nums[i-1]*L[i-1];
	        }
	        R[length-1] = 1;
	        for (int i=length-2;i>=0;i--) {
	        	R[i] = nums[i+1]*R[i+1];
	        }
	        for (int i=0;i<length;i++) {
	        	answer[i] = L[i]*R[i];
	        }
	        return answer; 
	 }
}
