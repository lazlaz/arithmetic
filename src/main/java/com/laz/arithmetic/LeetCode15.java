package com.laz.arithmetic;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

public class LeetCode15 {
	// 比特位计数
	@Test
	public void test() {
		Assert.assertArrayEquals(new int[] { 0, 1, 1, 2, 1, 2 }, countBits(5));
	}

	public int[] countBits(int num) {
		int[] ans = new int[num + 1];
		int i = 0, b = 1;
		// [0, b) is calculated
		while (b <= num) {
			// generate [b, 2b) or [b, num) from [0, b)
			while (i < b && i + b <= num) {
				ans[i + b] = ans[i] + 1;
				++i;
			}
			i = 0; // reset i
			b <<= 1; // b = 2b
		}
		return ans;
	}
	
	//有序数组的平方
	@Test
	public void test2() {
		Assert.assertArrayEquals(new int[] {4,9,9,49,121}, sortedSquares(new int[]{-7,-3,2,3,11}));
	}
	public int[] sortedSquares(int[] A) {
		int[] res = new int[A.length];
		for (int i=0;i<A.length;i++) {
			res[i] = A[i]*A[i];
		}
		Arrays.sort(res);
		return res;
	}
	
	//比较含退格的字符串
	@Test
	public void test3() {
		Assert.assertEquals(true, backspaceCompare("ab#c","ad#c"));
		Assert.assertEquals(true, backspaceCompare("y#fo##f","y#f#o##f"));
		Assert.assertEquals(false, backspaceCompare("abcd","bbcd"));
	}
	public boolean backspaceCompare(String S, String T) {
	       Deque<Character> stack1 = new LinkedList<Character>();
	       Deque<Character> stack2 = new LinkedList<Character>();
	       for (int i=0;i<S.length();i++) {
	    	   if (S.charAt(i) == '#' ) {
	    		   if (stack1.size()>0) {
	    			   stack1.pop();
	    		   }
	    	   } else {
	    		   stack1.push(S.charAt(i));
	    	   }
	       }
	       for (int i=0;i<T.length();i++) {
	    	   if (T.charAt(i) == '#') {
	    		   if (stack2.size()>0) {
	    			   stack2.pop();
	    		   }
	    	   } else {
	    		   stack2.push(T.charAt(i));
	    	   }
	       }
	       if (stack1.size()!=stack2.size()) {
	    	   return false;
	       }
	       int len = stack1.size();
	       for (int i=0;i<len;i++) {
	    	   if (stack1.pop()!=stack2.pop()) {
	    		   return false;
	    	   }
	       }
	       return true;
	}
}
