package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.junit.Test;

public class LeetCode5 {
	//拥有最多糖果的孩子
	@Test
	public void test1() {
		int[] candies = new int[] {2,3,5,1,3};
		int extraCandies = 3;
		List<Boolean> list = kidsWithCandies(candies, extraCandies);
		for (Boolean b : list) {
			System.out.println(b);
		}
	}
	 public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
		 int max = 0;
		 for (int i=0;i<candies.length;i++) {
			 if (max<candies[i]) {
				 max = candies[i];
			 }
		 }
		 List<Boolean> list = new ArrayList<Boolean>();
		 for (int i=0;i<candies.length;i++) {
			 if (candies[i]+extraCandies>=max) {
				 list.add(true);
			 } else {
				 list.add(false);
			 }
		 }
		 return list;
	 }
	 
	 
	 @Test
	 //除自身以外数组的乘积
	 public void test2() {
		 int[] nums = new int[] {1,2,3,4};
		 int[] res  = productExceptSelf(nums);
		 for (int i : res) {
			System.out.println(i);
		}
	 }
	 // 当前数左边的乘积 * 当前数右边的乘积
	 public int[] productExceptSelf(int[] nums) {
		 	int length = nums.length;
	        // L 和 R 分别表示左右两侧的乘积列表
	        int[] L = new int[length];
	        int[] R = new int[length];
	        int[] answer = new int[length];
	        L[0] = 1;
	        for (int i=1;i<length;i++) {
	        	L[i] = nums[i-1]*L[i-1];
	        }
	        R[length-1] = 1;
	        for (int i=length-2;i>=0;i--) {
	        	R[i] = nums[i+1]*R[i+1];
	        }
	        for (int i=0;i<length;i++) {
	        	answer[i] = L[i]*R[i];
	        }
	        return answer; 
	 }
	 
	 @Test
	 //顺时针打印矩阵
	 public void test3() {
		 int[][] matrix = new int[][] {
			 {3},
			 {2},
			 {1}
		 };
		 int[] res  = spiralOrder(matrix);
		 for (int i : res) {
			 System.out.print(i+" ");
		}
	 }
	 public int[] spiralOrder(int[][] matrix) {
		 if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
	            return new int[0];
	     }
		int rows = matrix.length, columns = matrix[0].length;
        int[] order = new int[rows * columns];
        int index = 0;
        int left = 0, right = columns - 1, top = 0, bottom = rows - 1;
        while (left <= right && top <= bottom) {
        	//遍历上方
            for (int column = left; column <= right; column++) {
                order[index++] = matrix[top][column];
            }
            //遍历右方
            for (int row = top + 1; row <= bottom; row++) {
                order[index++] = matrix[row][right];
            }
            if (left < right && top < bottom) {
            	//遍历下方
                for (int column = right - 1; column > left; column--) {
                    order[index++] = matrix[bottom][column];
                }
            	//遍历左方
                for (int row = bottom; row > top; row--) {
                    order[index++] = matrix[row][left];
                }
            }
            left++;
            right--;
            top++;
            bottom--;
        }
        return order;
	 }
	 
	 @Test
	 //反转字符串中的元音字母
	 public void test4() {
		 System.out.println(reverseVowels("hello"));
	 }
	 
	 public String reverseVowels(String s) {
		String letter= "aeiouAEIOU";
		char[] res=new char[s.length()];
		LinkedList<Character> tmp=new LinkedList<Character>();
		int i=0;
		for (char c : s.toCharArray()) {
			 res[i++]  = c;
	         if (letter.indexOf(c) != -1) {
	        	 tmp.push(c);
	         }
		}
		for (int j=0;j<res.length;j++) {
			if (letter.indexOf(res[j]) != -1) {
				res[j]=tmp.pop();
			}
		}
		return new String().valueOf(res);

	 }
	 public String reverseVowels2(String s) {
		 String strs = "aeiouAEIOU";
		 LinkedList<Character> stack = new LinkedList<Character>();
		 for (char c :s.toCharArray()) {
			 if (strs.indexOf(c)!=-1) {
				 stack.push(c);
			 }
		 }
		 StringBuffer sb = new StringBuffer();
		 for (int i=0;i<s.toCharArray().length;i++) {
			 char c1 = s.charAt(i);
			 if (strs.indexOf(c1)!=-1) {
				 sb.append(stack.poll()); 
			 }else {
				 sb.append(c1);
			 }
		 }
		 return sb.toString();
	  }
	 
	 @Test
	 // 两个数组的交集
	 public void test5() {
		 int[] num1 = new int[] {1,2,2,1};
		 int[] num2 = new int[] {2,2};
		 int[] res = intersection(num1, num2);
		 for (int i : res) {
			System.out.println(i);
		}
	 }
	 
	 public int[] intersection(int[] nums1, int[] nums2) {
		 Arrays.parallelSort(nums1);
		 Arrays.parallelSort(nums2);
		 int[] res = null;
		 int[] res2 = null;
		 if (nums1.length<nums2.length) {
			 res  = nums1;
			 res2 = nums2;
		 } else {
			 res  = nums2;
			 res2 = nums1;
		 }
		 List<Integer> list = new ArrayList<Integer>();
		 for (int i=0;i<res.length;i++) {
			 if (list.contains(res[i])) {
				 continue;
			 }
			 for (int j=0;j<res2.length;j++) {
				 if (res2[j]==res[i]) {
					 list.add(res[i]);
					 break;
				 }
				 if (res2[j]>res[i]) {
					 break;
				 }
			 }
		 }
		 return list.stream().mapToInt(Integer::valueOf).toArray();
	 }
}
