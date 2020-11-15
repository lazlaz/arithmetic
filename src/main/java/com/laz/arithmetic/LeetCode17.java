package com.laz.arithmetic;

import java.util.Deque;
import java.util.LinkedList;

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

	//移掉K位数字
	@Test
	public void test2() {
		Assert.assertEquals("1219", removeKdigits("1432219", 3));
	}

	// https://leetcode-cn.com/problems/remove-k-digits/solution/yi-zhao-chi-bian-li-kou-si-dao-ti-ma-ma-zai-ye-b-5/
	public String removeKdigits(String num, int k) {
		Deque<Character> stack = new LinkedList<Character>();
		for (int i=0;i<num.length();i++) {
			char c = num.charAt(i);
			while(!stack.isEmpty() && k>0 && stack.peekLast()>c) {
				stack.pollLast();
				k--;
			}
			stack.offerLast(c);
		}
		//如果K还没有减完，继续移除
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
}
