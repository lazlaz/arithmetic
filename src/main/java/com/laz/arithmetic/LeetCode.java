package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class LeetCode {

	// 多数元素
	@Test
	public void test1() {
		System.out.println(majorityElement(new int[] { 2, 2, 1, 1, 1, 2, 2 }));
	}

	public int majorityElement(int[] nums) {
		Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
		for (int i : nums) {
			if (counts.get(i) == null) {
				counts.put(i, 1);
			} else {
				int count = counts.get(i);
				counts.put(i, ++count);
			}
			if (counts.get(i) > nums.length / 2) {
				return i;
			}
		}
		return 0;

	}

	// 乘积最大子序列
	@Test
	public void test2() {
		System.out.println(maxProduct(new int[] { -3, 0, 1, -2 }));
	}

	public int maxProduct(int[] nums) {
		int max = nums[0];
		for (int j = 0; j < nums.length; j++) {
			int result = nums[j];
			if (result > max) {
				max = result;
			}
			for (int i = j + 1; i < nums.length; i++) {
				result = result * nums[i];
				if (result > max) {
					max = result;
				}
			}

		}
		return max;
	}

	// 单词拆分
	@Test
	public void test3() {
		List<String> wordDict = new ArrayList<String>();
		wordDict.add("leet");
		wordDict.add("code");
		System.out.println(wordBreak("leetcode", wordDict));
	}

	public boolean wordBreak(String s, List<String> wordDict) {
		Set<String> wordDictSet = new HashSet(wordDict);
		boolean[] dp = new boolean[s.length() + 1];
		dp[0] = true;
		for (int i = 1; i <= s.length(); i++) {
			for (int j = 0; j < i; j++) {
				if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
					dp[i] = true;
					break;
				}
			}
		}
		return dp[s.length()];
	}

	// 单词拆分 II
	@Test
	public void test4() {
		List<String> wordDict = new ArrayList<String>();
		wordDict.add("apple");
		wordDict.add("pen");
		wordDict.add("applepen");
		wordDict.add("pine");
		wordDict.add("pineapple");
		System.out.println(wordBreak2("pineapplepenapple", wordDict));
	}

	public List<String> wordBreak2(String s, List<String> wordDict) {
		LinkedList<String>[] dp = new LinkedList[s.length() + 1];
		LinkedList<String> initial = new LinkedList<>();
		initial.add("");
		dp[0] = initial;
		int maxLength = 0;
		for (String str : wordDict) {
			if (maxLength < str.length()) {
				maxLength = str.length();
			}
		}
		for (int i = 1; i <= s.length(); i++) {
			LinkedList<String> list = new LinkedList<>();
			for (int j = 0; j < i; j++) {
				if (dp[j].size() > 0 && wordDict.contains(s.substring(j, i))) {
					for (String l : dp[j]) {
						list.add(l + (l.equals("") ? "" : " ") + s.substring(j, i));
					}
				}
			}
			dp[i] = list;
		}
		return dp[s.length()];

	}

	// 回文数
	@Test
	public void test5() {
		System.out.println(isPalindrome(202));
	}

	public boolean isPalindrome(int x) {
		if (x < 0) {
			return false;
		}
		int res = x;
		int res2 = 0;
		while (res != 0) {
			int z = res % 10;
			res2 = res2 * 10 + z;
			res = res / 10;
		}
		return res2 == x;
	}

	// 移除元素
	@Test
	public void test6() {
		int[] nums = new int[] { 3, 2, 2, 3 };

		System.out.println(removeElement(nums, 3));
		for (int i : nums) {
			System.out.print(i + " ");
		}
	}

	public int removeElement(int[] nums, int val) {
		Arrays.sort(nums);
		int count = 0;
		boolean flag = false;
		int index = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == val) {
				flag = true;
				count++;
				continue;
			}
			if (flag) {
				index = i;
				break;
			}
		}
		if (flag && index == 0) {
			index = nums.length;
		}
		// [0,0,1,2,2,2,3,4] index=6 count=3
		for (int i = (index - count); i < (nums.length - count); i++) {
			nums[i] = nums[i + count];
		}
		return (nums.length - count);
	}

	// 搜索插入位置
	@Test
	public void test7() {
		int[] nums = new int[] { 1, 3, 5, 6 };
		System.out.println(searchInsert(nums, 5));
	}

	public int searchInsert(int[] nums, int target) {
		int length = nums.length;
		int mid, low = 0, high = length;
		if (nums.length == 0) {
			return 0;
		}
		if (nums[nums.length - 1] < target) {
			return nums.length;
		}
		if (nums[0] > target) {
			return 0;
		}
		while (low <= high) {
			mid = (low + high) / 2;
			if (nums[mid] == target) {
				return mid;
			} else if (mid > 1 && nums[mid] > target && nums[mid - 1] < target) {
				return mid;
			} else if (mid < nums.length && nums[mid] < target && nums[mid + 1] > target) {
				return mid + 1;
			} else if (nums[mid] > target) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return 0;
	}

	// 最后一个单词的长度
	@Test
	public void test8() {
		System.out.println(lengthOfLastWord("Hello World"));
	}

	public int lengthOfLastWord(String s) {
		s = s.trim();
		int index = s.lastIndexOf(" ");
		System.out.println(index);
		return s.substring(index+1, s.length()).length();
	}
}
