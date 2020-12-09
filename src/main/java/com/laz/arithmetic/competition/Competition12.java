package com.laz.arithmetic.competition;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-218/
public class Competition12 {
	// 设计 Goal 解析器
	@Test
	public void test1() {
		Assert.assertEquals("Goal", interpret("G()(al)"));
		Assert.assertEquals("Gooooal", interpret("G()()()()(al)"));
		Assert.assertEquals("alGalooG", interpret("(al)G(al)()()G"));
	}

	public String interpret(String command) {
		StringBuilder res = new StringBuilder();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < command.length(); i++) {
			char c = command.charAt(i);
			if (c == 'G') {
				res.append("G");
			} else if (c == '(') {
				sb.append(c);
			} else if (c == ')') {
				sb.append(c);
				if (sb.toString().equals("()")) {
					res.append("o");
				}
				if (sb.toString().equals("(al)")) {
					res.append("al");
				}
				sb = new StringBuilder();
			} else {
				sb.append(c);
			}
		}
		return res.toString();
	}

	// K 和数对的最大数目
	@Test
	public void test2() {
		Assert.assertEquals(2, maxOperations(new int[] { 1, 2, 3, 4 }, 5));

		Assert.assertEquals(1, maxOperations(new int[] { 3, 1, 3, 4, 3 }, 6));
	}

	public int maxOperations(int[] nums, int k) {
		Arrays.sort(nums);
		int p = 0, q = nums.length - 1;
		int res = 0;
		while (p < q) {
			if (nums[p] + nums[q] == k) {
				res++;
				p++;
				q--;
			} else if (nums[p] + nums[q] > k) {
				q--;
			} else if (nums[p] + nums[q] < k) {
				p++;
			}
		}
		return res;
	}

	// 连接连续二进制数字
	@Test
	public void test3() {
		Assert.assertEquals(1, concatenatedBinary(1));
		Assert.assertEquals(27, concatenatedBinary(3));
		Assert.assertEquals(505379714, concatenatedBinary(12));
	}

	public int concatenatedBinary(int n) {
		final int MOD = 1000000007;
		int res = 0,shift = 0;
		for (int i=1;i<=n;i++) {
			if ((i&(i-1))==0) {
				//判断是否为2的冥 2的1次方，2次方等等
				shift++;
			}
			res = (int) ((((long)res<<shift)+i)%MOD);
		}
		return res;
	}
}
