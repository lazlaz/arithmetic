package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Test;

public class LetCodeOther {
	// 合并两个有序数组
	@Test
	public void test1() {
		System.out.println(hammingWeight(-3));
	}

	// you need to treat n as an unsigned value
	public int hammingWeight(int n) {
		char[] chars = Integer.toBinaryString(n).toCharArray();
		int count = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '1') {
				count++;
			}
		}
		return count;
	}

	// 汉明距离
	@Test
	public void test2() {
		System.out.println(hammingDistance(2, 4));
	}

	public int hammingDistance(int x, int y) {
		int z = x ^ y;
		char[] chars = Integer.toBinaryString(z).toCharArray();
		int count = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '1') {
				count++;
			}
		}
		return count;
	}

	// 颠倒二进制位
	@Test
	public void test3() {
		int n = 43261596;
		System.out.println(Integer.toBinaryString(n));
		System.out.println(reverseBits(n));
	}

	// you need treat n as an unsigned value
	public int reverseBits(int n) {
		int res = 0;
		int count = 0;
		// 0010
		while (count < 32) {
			res <<= 1; // res 左移一位空出位置
			res |= (n & 1); // 得到的最低位加过来

			n >>= 1;// 原数字右移一位去掉已经处理过的最低位
			count++;
		}
		System.out.println(Integer.toBinaryString(res));
		return res;
	}

	// 帕斯卡三角形
	@Test
	public void test4() {
		List<List<Integer>> list = generate(5);
		for (List<Integer> list2 : list) {
			for (Integer integer : list2) {
				System.out.print(integer);
			}
			System.out.println();
		}
	}

	public List<List<Integer>> generate(int numRows) {
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		List<Integer> up = new ArrayList<Integer>();
		for (int i = 1; i <= numRows; i++) {
			List<Integer> row = new ArrayList<Integer>();
			if (i == 1) {
				row.add(1);
			} else if (i == 2) {
				row.add(1);
				row.add(1);
			} else {
				row.add(1);
				for (int j = 2; j <= i - 1; j++) {
					Integer ints[] = up.toArray(new Integer[] {});
					row.add(ints[j - 2] + ints[j - 1]);
				}
				row.add(1);
			}
			up = row;
			list.add(row);
		}

		return list;
	}

	// 有效的括号
	@Test
	public void test5() {
		Assert.assertEquals(true, isValid("{[]}"));
	}

	public boolean isValid(String s) {
		Stack<Character> stack = new Stack<Character>();
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (stack.size() > 0) {
				Character c = stack.peek();
				if (c != null) {
					if (c == '(' && chars[i] == ')') {
						stack.pop();
					} else if (c == '{' && chars[i] == '}') {
						stack.pop();
					} else if (c == '[' && chars[i] == ']') {
						stack.pop();
					} else {
						stack.push(chars[i]);
					}
				}
			} else {
				stack.push(chars[i]);
			}

		}
		if (stack.size() == 0) {
			return true;
		}
		return false;
	}

	// 缺失数字
	@Test
	public void test6() {
		int[] nums  = new int[] {9,6,4,2,3,5,7,0,1};
		System.out.println(missingNumber(nums));
	}

	public int missingNumber(int[] nums) {
		for (int i=0; i<=nums.length; i++) {
			boolean find = false;
			for (int j=0; j<nums.length; j++) {
				if (nums[j] == i) {
					find = true;
				}
			}
			if (!find) {
				return i;
			}
		}
		return nums.length;
	}
}
