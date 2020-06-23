package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Joiner;

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

}
