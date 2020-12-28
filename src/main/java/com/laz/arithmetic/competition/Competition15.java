package com.laz.arithmetic.competition;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-221/
public class Competition15 {
	// 1704. 判断字符串的两半是否相似
	@Test
	public void test1() {
		Assert.assertEquals(true, halvesAreAlike("book"));
		Assert.assertEquals(false, halvesAreAlike("textbook"));
		Assert.assertEquals(false, halvesAreAlike("MerryChristmas"));
		Assert.assertEquals(true, halvesAreAlike("AbCdEfGh"));
	}

	public boolean halvesAreAlike(String s) {
		int n = s.length();
		char[] chars = new char[] { 'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U' };
		Set<Character> vowelSet = new HashSet<Character>();
		for (int i = 0; i < chars.length; i++) {
			vowelSet.add(chars[i]);
		}
		int count1 = 0;
		int count2 = 0;
		for (int i = 0; i < n / 2; i++) {
			if (vowelSet.contains(s.charAt(i))) {
				count1++;
			}
		}
		for (int i = n / 2; i < n; i++) {
			if (vowelSet.contains(s.charAt(i))) {
				count2++;
			}
		}
		return count1 == count2;
	}

	// 1705. 吃苹果的最大数目
	@Test
	public void test2() {
		Assert.assertEquals(7, new Solution2().eatenApples(new int[] { 1, 2, 3, 5, 2 }, new int[] { 3, 2, 1, 4, 2 }));
		Assert.assertEquals(5,
				new Solution2().eatenApples(new int[] { 3, 0, 0, 0, 0, 2 }, new int[] { 3, 0, 0, 0, 0, 2 }));
		Assert.assertEquals(1, new Solution2().eatenApples(new int[] { 1 }, new int[] { 2 }));
		Assert.assertEquals(31,
				new Solution2().eatenApples(
						new int[] { 9, 10, 1, 7, 0, 2, 1, 4, 1, 7, 0, 11, 0, 11, 0, 0, 9, 11, 11, 2, 0, 5, 5 },
						new int[] { 3, 19, 1, 14, 0, 4, 1, 8, 2, 7, 0, 13, 0, 13, 0, 0, 2, 2, 13, 1, 0, 3, 7 }));
	}

	class Solution2 {
		public int eatenApples(int[] apples, int[] days) {
			int index = 1;
			int n = apples.length;
			int count = 0;
			int[] arr = new int[100000];
			int findKey = hasApple(arr, index);
			while (findKey!=-1 || index <= n) {
				if (index <= n) {
					// 将苹果根据最大坚持的天数放入
					int day = days[index - 1] + index - 1;
					int num = arr[day];
					arr[day] = num + apples[index - 1];
				}
				findKey = hasApple(arr, index);
				if (findKey!=-1) {
					// 从最近取出苹果进行吃
					int nowNum = arr[findKey];
					if (nowNum > 0) {
						nowNum--;
						count++; // 吃到苹果
						arr[findKey] = nowNum;
					} 
				}
				index++;
			}
			return count;
		}

		private int hasApple(int[] arr, int index) {
			for (int i=index;i<arr.length;i++) {
				if (i >= index && arr[i]>0) {
					return i;
				}
			}
			return -1;
		}
	}
}
