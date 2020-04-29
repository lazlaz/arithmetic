package com.laz.arithmetic;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class LeetCode4 {
	//丑数
	@Test
	public void test1() {
		System.out.println(isUgly(8));
	}
	 public boolean isUgly(int num) {
		 if (num == 0) {
			 return false;
		 }
		 if (num == 1) {
			 return true;
		 }
		 while (num%2 == 0 || num%3 == 0 || num%5 == 0) {
			 if (num%2 == 0) {
				 num=num/2;
			 } else if (num%3 == 0) {
				 num=num/3;
			 } else if (num%5 == 0) {
				 num=num/5;
			 }
		 }
		 if (num == 1) {
			 return true;
		 }
		 return false;
	 }
	//单词规律
	@Test
	public void test2() {
		System.out.println(wordPattern("","beef"));
	}
	public boolean wordPattern(String pattern, String str) {
	       String[] p =  pattern.split("");
	       String[] s =  str.split(" ");
	       
	       if (pattern.equals("") && !str.equals("")) {
	    	   return false;
	       }
	       if (!pattern.equals("") && str.equals("")) {
	    	   return false;
	       }
	       if (p.length != s.length) {
	    	   return false;
	       }
	       Map<String,Integer> pMap = new HashMap<String,Integer>();
	       Map<String,Integer> sMap = new HashMap<String,Integer>();
	       int pNum = 0;
	       int sNum = 0;
	       String pString = "";
	       String sString = "";
	       for (String p1:p) {
	    	   if (pMap.get(p1) != null) {
	    		   pString += pMap.get(p1);
	    	   } else {
	    		   pString += pNum;
	    		   pMap.put(p1,pNum);
	    		   pNum++;
	    	   }
	       }
	       for (String s1:s) {
	    	   if (sMap.get(s1) != null) {
	    		   sString += sMap.get(s1);
	    	   } else {
	    		   sString += sNum;
	    		   sMap.put(s1,sNum);
	    		   sNum++;
	    	   }
	       }
	       if (pString.equals(sString)) {
	           return true;
	       }
	       return false;
	}
	
	//区域和检索 - 数组不可变
	@Test
	public void test3() {
		int[] nums = new int[] {-2,0,3,-5,2,-1};
		NumArray obj = new NumArray(nums);
		int param_1 = obj.sumRange(0,2);
		System.out.println(param_1);
	}
	class NumArray {
		private int[] nums;
	    public NumArray(int[] nums) {
	    	this.nums = nums;
	    }
	    
	    public int sumRange(int i, int j) {
	    	int count = 0;
	    	for (int z=i;z<=j;z++) {
	    		count+=nums[z];
	    	}
	    	return count;
	    }
	}
}
