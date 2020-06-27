package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Joiner;
import org.junit.Assert;

public class LeetCode7 {
	// 整数转罗马数字
	@Test
	public void test1() {
		int num = 19943;
		// 1994
		// 输出: "MCMXCIV"
		System.out.println(intToRoman(num));
	}

	public String intToRoman(int num) {
		int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
		String[] symbols = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.length && num > 0; i++) {
			while (values[i] <= num) {
				num -= values[i];
				sb.append(symbols[i]);
			}
		}
		return sb.toString();
	}

	// 最接近的三数之和
	@Test
	public void test2() {
		int[] nums = new int[] { -1, 2, 1, -4 };
		System.out.println(threeSumClosest(nums, -4));
	}

	public int threeSumClosest(int[] nums, int target) {
		if (nums == null) {
			return 0;
		}
		Arrays.sort(nums);
		int result = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < nums.length - 2; i++) {
			int left = i + 1;
			int right = nums.length - 1;
			while (left < right) {
				int v = nums[i] + nums[left] + nums[right];
				if (Math.abs(v - target) < min) {
					min = Math.abs(v - target);
					result = v;
				}
				if (v > target) {
					right--;
				}
				if (v < target) {
					left++;
				}
				if (v == target) {
					return v;
				}
			}
		}
		return result;
	}
	
	
	//电话号码的字母组合
	@Test
	public void test3() {
		List<String> list = letterCombinations("4");
		System.out.println(Joiner.on(",").join(list));
	}
	Map<String,String> phone  = new HashMap<String,String>(){{
		put("2","abc");
		put("3","def");
		put("4","ghi");
		put("5","jkl");
		put("6","mno");
		put("7","pqrs");
		put("8","tuv");
		put("9","wxyz");
	}};
	List<String> output = new ArrayList<String>();
	public List<String> letterCombinations(String digits) {
		if (digits.length()!=0) {
			backtrack("",digits);
		}
		return output;
	}
	public void backtrack(String combination, String next_digits) {
		if (next_digits.length() == 0) {
			output.add(combination);
		} else {
			String digit = next_digits.substring(0,1);
			String letters = phone.get(digit);
			for (int i=0;i<letters.length();i++) {
				String letter = phone.get(digit).substring(i,i+1);
				backtrack(combination+letter, next_digits.substring(1));
			}
		}
	}
	
	// 四数之和
	@Test
	public void test4() {
		int[] nums = new int[] {0,0,0,0};
		int target = 0;
		List<List<Integer>> list = fourSum(nums,target);
		for (List<Integer> l : list) {
			System.out.println(Joiner.on(",").join(l));
		}
	}
	public List<List<Integer>> fourSum(int[] nums, int target) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();
		Arrays.sort(nums);
		if (nums==null || nums.length<4) {
			return res;
		}
		int left1=0,left2=1;
		int right1=nums.length-1,right2=nums.length-2;
		Map<String,Boolean> falg = new HashMap<String,Boolean>();
		while (left1<=nums.length-4 && left1<left2 && right2<right1 && left2<right2) {
			List<Integer> list = new ArrayList<Integer>();
			int r = nums[left1]+nums[left2]+nums[right2]+nums[right1];
			if (r == target) {
				list.add(nums[left1]);
				list.add(nums[left2]);
				list.add(nums[right2]);
				list.add(nums[right1]);
				String key = new StringBuffer().append(nums[left1]).append(nums[left2]).append(nums[right2]).append(nums[right1]).toString();
				if (falg.get(key)==null || !falg.get(key)) {
					falg.put(key, true);
					res.add(list);
				}
				right2--;
				left2++;
			}
			if (r>target) {
				right2--;
			}
			if (r<target) {
				left2++;
			}
			if (left2>=right2) {
				if (right1-left1<4) {
					right1=nums.length-1;
					right2=right1-1;
					left1++;
					left2=left1+1;
				} else {
					right1--;
					right2=right1-1;
					left2=left1+1;
				}
				
			}
		}
		return res;
    }
	
	
	//缺失的第一个正数
	@Test
	public void test5() {
		//Assert.assertEquals(3, firstMissingPositive(new int[] {1,2,0}));
		Assert.assertEquals(1, firstMissingPositive(new int[] {3,2,3}));
	}
	
	public int firstMissingPositive(int[] nums) {
		int len = nums.length;
		for (int i=0;i<len;i++) {
			while (nums[i]>0&&nums[i]<=len&&nums[nums[i]-1]!=nums[i]) {
				swap(nums,nums[i]-1,i);
			}
		}
		for (int i=0;i<len;i++) {
			if (nums[i] != i+1) {
				return i+1;
			}
		}
		return len+1;
    }
	public void swap(int[] nums,int index1,int index2) {
		int temp = nums[index1];
		nums[index1] = nums[index2];
		nums[index2] = temp;
	}
}
 