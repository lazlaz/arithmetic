package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

//210场周赛
public class Competition4 {
	// 括号的最大嵌套深度
	@Test
	public void test1() {
		Assert.assertEquals(3, maxDepth("(1+(2*3)+((8)/4))+1"));
	}

	public int maxDepth(String s) {
		int max = 0, count = 0;
		for (char c : s.toCharArray()) {
			if (c == '(') {
				max = Math.max(max, ++count);
			} else if (c == ')') {
				count--;
			}
		}
		return max;
	}

	// 最大网络秩
	@Test
	public void test2() {
		Assert.assertEquals(5,
				maximalNetworkRank(5, new int[][] { { 0, 1 }, { 0, 3 }, { 1, 2 }, { 1, 3 }, { 2, 3 }, { 2, 4 } }));

		Assert.assertEquals(5,
				maximalNetworkRank(8, new int[][] { { 0, 1 }, { 1, 2 }, { 2, 3 }, { 2, 4 }, { 5, 6 }, { 5, 7 } }));
	}

	public int maximalNetworkRank(int n, int[][] roads) {
		// 计算每个点连接的道路
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		Map<Integer, List<Integer>> edge = new HashMap<Integer, List<Integer>>();
		for (int j = 0; j < roads.length; j++) {
			List<Integer> r = edge.getOrDefault(roads[j][0], new ArrayList<Integer>());
			r.add(roads[j][1]);
			edge.put(roads[j][0], r);
		}
		for (int i = 0; i < n; i++) {
			int count = 0;
			for (int j = 0; j < roads.length; j++) {
				if (roads[j][0] == i || roads[j][1] == i) {
					count++;
				}

			}
			map.put(i, count);
		}
		List<Map.Entry<Integer, Integer>> list = new ArrayList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
			@Override
			public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
				// 按照value值，从大到小排序
				return o2.getValue() - o1.getValue();
			}
		});
		int max = 0;
		int v = list.get(1).getValue();
		// 注意这里遍历的是list，也就是我们将map.Entry放进了list，排序后的集合
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (i == j) {
					continue;
				}
				Map.Entry<Integer, Integer> s1 = list.get(i);
				Map.Entry<Integer, Integer> s2 = list.get(j);
				if (s2.getValue() < v || s1.getValue() < v) {
					break;
				}
				int value = s1.getValue() + s2.getValue();
				if ((edge.get(s1.getKey()) != null && edge.get(s1.getKey()).contains(s2.getKey()))
						|| (edge.get(s2.getKey()) != null && edge.get(s2.getKey()).contains(s1.getKey()))) {
					value = value - 1;
				}
				if (max < value) {
					max = value;
				}
			}

		}

		return max;
	}

	//分割两个字符串得到回文串
	@Test
	public void test3() {
		Assert.assertEquals(true, new Solution3().checkPalindromeFormation("ulacfd","jizalu"));
	}

	class Solution3 {
		public boolean checkPalindromeFormation(String a, String b) {
			int len = a.length();
			if (isPalindrome(a, 0, len) || isPalindrome(b, 0, len))
				return true;

			// prefixA + suffixB
			int index = 0;
			while (a.charAt(index) == b.charAt(len - 1 - index))
				index++;
			if (isPalindrome(a, index, len - index) || isPalindrome(b, index, len - index))
				return true;
			// prefixB + suffixA
			index = 0;
			while (a.charAt(len - 1 - index) == b.charAt(index))
				index++;
			if (isPalindrome(b, index, len - index) || isPalindrome(a, index, len - index))
				return true;

			return false;
		}

		private boolean isPalindrome(String str, int low, int high) {
			// high is not contained
			int time = ((high - low) >> 1);
			for (int i = 0; i < time; i++) {
				if (str.charAt(low + i) != str.charAt(high - 1 - i))
					return false;
			}

			return true;
		}

	}
	
	//统计子树中城市之间最大距离
	@Test
	public void test4() {
		
	}
}
