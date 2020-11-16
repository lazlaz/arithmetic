package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

//https://leetcode-cn.com/problems/design-an-ordered-stream/
public class Competition9 {
	// 设计有序流
	@Test
	public void test1() {
		OrderedStream os = new OrderedStream(5);
		System.out.println(Joiner.on(",").join(os.insert(3, "ccccc"))); // 插入 (3, "ccccc")，返回 []
		System.out.println(Joiner.on(",").join(os.insert(1, "aaaaa"))); // 插入 (1, "aaaaa")，返回 ["aaaaa"]
		System.out.println(Joiner.on(",").join(os.insert(2, "bbbbb"))); // 插入 (2, "bbbbb")，返回 ["bbbbb", "ccccc"]
		System.out.println(Joiner.on(",").join(os.insert(5, "eeeee"))); // 插入 (5, "eeeee")，返回 []
		System.out.println(Joiner.on(",").join(os.insert(4, "ddddd"))); // 插入 (4, "ddddd")，返回 ["ddddd", "eeeee"]
	}

	class OrderedStream {
		int ptr = 0;
		private String[] arr;

		public OrderedStream(int n) {
			arr = new String[n];
		}

		public List<String> insert(int id, String value) {
			arr[id - 1] = value;
			if (arr[ptr] == null) {
				return Collections.EMPTY_LIST;
			}
			List<String> res = new ArrayList<String>();
			while (ptr < arr.length && arr[ptr] != null) {
				res.add(arr[ptr]);
				ptr++;
			}
			return res;
		}
	}

	// 确定两个字符串是否接近
	@Test
	public void test2() {
		Assert.assertEquals(true, closeStrings("abc", "bca"));
		Assert.assertEquals(false, closeStrings("a", "aa"));
		Assert.assertEquals(true, closeStrings("cabbba", "abbccc"));
		Assert.assertEquals(false, closeStrings("cabbba", "aabbss"));
	}

	public boolean closeStrings(String word1, String word2) {
		if (word1.length() != word2.length()) {
			return false;
		}
		int[] arr1 = new int[26];
		for (int i = 0; i < word1.length(); i++) {
			arr1[word1.charAt(i) - 'a']++;
		}
		int[] arr2 = new int[26];
		for (int i = 0; i < word2.length(); i++) {
			arr2[word2.charAt(i) - 'a']++;
		}
		// 判断是否有存在word1不存在word2的字符，反之也是
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] > 0 && arr2[i] == 0) {
				return false;
			}
			if (arr2[i] > 0 && arr1[i] == 0) {
				return false;
			}
		}
		// 排序后判断数组字符数量是否一一匹配，如abbzzca和babzzcz，字符数量分别为2、2、2、1和3、2、1、1则不可能匹配
		Arrays.sort(arr1);
		Arrays.sort(arr2);
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// 将 x 减到 0 的最小操作数
	@Test
	public void test3() {
		//Assert.assertEquals(2, minOperations(new int[] { 1, 1, 4, 2, 3 }, 5));
		Assert.assertEquals(5, minOperations(new int[] {3,2,20,1,1,3 }, 10));
	}

	// https://leetcode-cn.com/problems/minimum-operations-to-reduce-x-to-zero/solution/java-shuang-bai-qian-zhui-he-hou-zhui-he-twosum-by/
	public int minOperations(int[] nums, int x) {
		int len = nums.length;
		// 前缀和
		int[] preSum = new int[len + 1];

		for (int i = 0; i < len; i++) {
			preSum[i + 1] = preSum[i] + nums[i];
		}

		// 后缀和
		Map<Integer, Integer> map = new HashMap<>();

		int[] latterSum = new int[len + 1];
		map.put(0, 0);
		int idx = 1;
		for (int i = len - 1; i >= 0; i--) {
			latterSum[idx] = latterSum[idx - 1] + nums[i];
			map.put(latterSum[idx], idx);
			idx++;
		}
		if (preSum[len] < x || latterSum[len] < x)
			return -1;// 避免多次计算
		int res = Integer.MAX_VALUE;

		for (int i = 0; i < len + 1; i++) {
			int pre = preSum[i];
			if (map.containsKey(x - pre)) {
				res = Math.min(res, map.get(x - pre) + i);
			}
		}

		return res == Integer.MAX_VALUE ? -1 : res;
	}
}
