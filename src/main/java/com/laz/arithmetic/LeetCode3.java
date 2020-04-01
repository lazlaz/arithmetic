package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class LeetCode3 {
	// 最长回文串
	@Test
	public void test1() {
		System.out.println(longestPalindrome(
				"civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth"));
	}

	public int longestPalindrome(String s) {
		char[] chars = s.toCharArray();
		Map<Character, Integer> counts = new HashMap<Character, Integer>();
		for (int i = 0; i < chars.length; i++) {
			if (counts.get(chars[i]) == null) {
				counts.put(chars[i], 1);
			} else {
				int value = counts.get(chars[i]) + 1;
				counts.put(chars[i], value);
			}
		}
		int result = 0;
		boolean falg = false;
		for (Integer count : counts.values()) {
			if (count % 2 == 0) {
				result += count;
			} else {
				falg = true;
				result = result + count - 1;
			}
		}
		if (falg) {
			result += 1;
		}
		return result;
	}

	// 按摩师
	@Test
	public void test2() {
		int[] nums = new int[] { 2, 1, 4, 5, 3, 1, 1, 3 };
		System.out.println(massage(nums));
	}

	public int massage(int[] nums) {
		if (nums.length == 0) {
			return 0;
		}
		if (nums.length == 1) {
			return nums[0];
		}
		if (nums.length == 2) {
			return nums[0] > nums[1] ? nums[0] : nums[1];
		}
		int[] maxs = new int[nums.length];
		maxs[0] = nums[0];
		maxs[1] = nums[0] > nums[1] ? nums[0] : nums[1];
		for (int i = 2; i < nums.length; i++) {
			if (maxs[i - 2] + nums[i] > maxs[i - 1]) {
				maxs[i] = maxs[i - 2] + nums[i];
			} else {
				maxs[i] = maxs[i - 1];
			}
		}
		return maxs[nums.length - 1];
	}

	// 排序数组
	@Test
	public void test3() {
		int[] res = sortArray(new int[] { 5, 1, 1, 2, 0, 0 });
		for (int i : res) {
			System.out.print(i + " ");
		}
		System.out.println();
	}

	public int[] sortArray(int[] nums) {
		// 冒泡超时
//	        for (int i=0; i<nums.length; i++) {
//	        	for (int j=i+1; j<nums.length; j++) {
//	        		if (nums[i]>nums[j]) {
//	        			int temp = nums[i];
//	        			nums[i] = nums[j];
//	        			nums[j] = temp;
//	        		}
//	        	}
//	        }

		for (int i = 0; i < nums.length; i++) {
			int minIndex = i; // 从0索引开始，将索引赋给minIndex
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[j] < nums[minIndex]) {
					minIndex = j;// 找到最小值的索引
				}
			}
			// 将最小元素放到本次循环的前端
			int temp = nums[i];
			nums[i] = nums[minIndex];
			nums[minIndex] = temp;
		}
		return nums;
	}

	// 用队列实现栈
	@Test
	public void test4() {
		int x = 5;
		MyStack obj = new LeetCode3.MyStack();
		obj.push(x);
		int param_2 = obj.pop();
		int param_3 = obj.top();
		boolean param_4 = obj.empty();
		System.out.println(param_2+" "+param_3+" "+param_4);
	}

	class MyStack {
		private List list;
		/** Initialize your data structure here. */
		public MyStack() {
			list = new ArrayList();
		}

		/** Push element x onto stack. */
		public void push(int x) {
			list.add(x);
		}

		/** Removes the element on top of the stack and returns that element. */
		public int pop() {
			if (list.size()-1>=0) {
				int x = (int) list.get(list.size()-1);
				list.remove(list.size()-1);
				return x;
			}
			return 0;
		}

		/** Get the top element. */
		public int top() {
			if (list.size()-1>=0) {
				int x = (int) list.get(list.size()-1);
				return x;
			}
			return 0;
		}

		/** Returns whether the stack is empty. */
		public boolean empty() {
			return list.isEmpty();
		}
	}
	
	//合并排序的数组
	@Test
	public void test5() {
		int[] A = new int[] {0};
		int[] B = new int[] {1};
		merge(A,0,B,B.length);	
		 for (int i = 0; i < A.length; i++)
	            System.out.print(A[i] + ",");
	}
    public void merge(int[] A, int m, int[] B, int n) {
    	int j=0;
    	for (int i=0;i<n+m; i++) {
    		if (i>=m) {
    			A[i] = B[j];
    			j++;
    		}
    	}
    	Arrays.parallelSort(A);
    }
}
