package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

public class LeetCode17 {
	// 按奇偶排序数组 II
	@Test
	public void test1() {
		Assert.assertArrayEquals(new int[] { 4, 5, 2, 7 }, sortArrayByParityII(new int[] { 4, 2, 5, 7 }));
	}

	public int[] sortArrayByParityII(int[] A) {
		int[] res = new int[A.length];
		int p = 0, q = 1;
		for (int i = 0; i < A.length; i++) {
			if (A[i] % 2 == 0) {
				res[p] = A[i];
				p = p + 2;
			} else {
				res[q] = A[i];
				q = q + 2;
			}
		}
		return res;
	}

	// 移掉K位数字
	@Test
	public void test2() {
		Assert.assertEquals("1219", removeKdigits("1432219", 3));
	}

	// https://leetcode-cn.com/problems/remove-k-digits/solution/yi-zhao-chi-bian-li-kou-si-dao-ti-ma-ma-zai-ye-b-5/
	public String removeKdigits(String num, int k) {
		Deque<Character> stack = new LinkedList<Character>();
		for (int i = 0; i < num.length(); i++) {
			char c = num.charAt(i);
			while (!stack.isEmpty() && k > 0 && stack.peekLast() > c) {
				stack.pollLast();
				k--;
			}
			stack.offerLast(c);
		}
		// 如果K还没有减完，继续移除
		for (int i = 0; i < k; ++i) {
			stack.pollLast();
		}

		StringBuilder ret = new StringBuilder();
		boolean leadingZero = true;
		while (!stack.isEmpty()) {
			char digit = stack.pollFirst();
			if (leadingZero && digit == '0') {
				continue;
			}
			leadingZero = false;
			ret.append(digit);
		}
		return ret.length() == 0 ? "0" : ret.toString();

	}

	// 距离顺序排列矩阵单元格
	@Test
	public void test3() {
		Assert.assertArrayEquals(new int[][] {
			{0,1},{0,0},{1,1},{1,0}
		}, allCellsDistOrder(2,2,0,1));
	}

	public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
		List<int[]> list = new ArrayList<int[]>();
		for (int i=0;i<R;i++) {
			for (int j=0;j<C;j++) {
				list.add(new int[] {i,j});
			}
		}
		Collections.sort(list, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				int distance1 = Math.abs(o1[0]-r0)+Math.abs(o1[1]-c0);
				int distance2 = Math.abs(o2[0]-r0)+Math.abs(o2[1]-c0);
				return distance1-distance2;
			}
		});
		int[][] res = new int[R*C][2];
		return list.toArray(res);
	}
}
