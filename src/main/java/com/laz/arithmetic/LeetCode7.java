package com.laz.arithmetic;

import java.util.Arrays;

import org.junit.Test;

public class LeetCode7 {
	//整数转罗马数字
	@Test
	public void test1() {
		int num = 19943;
		//1994
		//输出: "MCMXCIV"
		System.out.println(intToRoman(num));
	}
	 public String intToRoman(int num) {
		 int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};    
		 String[] symbols = {"M","CM","D","CD","C","XC","L","XL","X","IX","V","IV","I"};
		 StringBuffer sb = new StringBuffer();
		 for (int i=0;i<values.length&&num>0;i++) {
			 while (values[i]<=num) {
				 num -= values[i];
				 sb.append(symbols[i]);
			 }
		 }
		 return sb.toString();
	 }
	 
	 //最接近的三数之和
	 @Test
	 public void test2() {
		 int[] nums = new int[] {-1,2,1,-4};
		 System.out.println(threeSumClosest(nums,-4));
	 }
	 public int threeSumClosest(int[] nums, int target) {
		 if (nums==null) {
			 return 0;
		 }
		 Arrays.sort(nums);
		 int result = 0;
		 int min = Integer.MAX_VALUE;
		 for (int i=0;i<nums.length-2;i++) {
			 int left = i+1;
			 int right  =nums.length-1;
			 while (left<right) {
				 int v = nums[i]+nums[left]+nums[right];
				 if (Math.abs(v-target)<min) {
					 min = Math.abs(v-target);
					 result = v;
				 }
				 if (v>target) {
					 right--;
				 }
				 if (v<target) {
					 left++;
				 }
				 if (v==target) {
                     return v;
                 }
			 }
		 }
		 return result;
	 }
}
