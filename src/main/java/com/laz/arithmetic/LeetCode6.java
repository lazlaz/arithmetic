package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode6 {
	//三数之和
	@Test
	public void test1() {
		int[] nums = new int[] {-1,2,4,2,0,-2,-4};
		List<List<Integer>> list = threeSum(nums);
		System.out.println(Joiner.on(",").join(list));
	}
	//思路参考 https://leetcode-cn.com/problems/3sum/solution/hua-jie-suan-fa-15-san-shu-zhi-he-by-guanpengchn/
    public static List<List<Integer>> threeSum(int[] nums) {
    	List<List<Integer>> ans = new ArrayList<List<Integer>>();
    	if(nums == null || nums.length < 3) return ans;
    	Arrays.sort(nums);
    	for (int i=0;i<nums.length;i++) {
    		if (nums[i] > 0) {
    			continue;
    		}
    		if (i>0 && nums[i] == nums[i-1]) {
    			continue;
    		}
    		int L = i+1;
    		int R = nums.length-1;
    		while (L < R) {
    			int sum = nums[i]+nums[L]+nums[R];
    			if (sum == 0) {
    				ans.add(Arrays.asList(nums[i],nums[L],nums[R]));
    				while (L<R && nums[L] == nums[L+1]) L++; // 去重
                    while (L<R && nums[R] == nums[R-1]) R--; // 去重
    				L++;
    				R--;
    			}
    			else if (sum < 0) L++;
                else if (sum > 0) R--;
    		}
    	}
    	return ans;
    }
    
    //验证回文串
    @Test
    public void test2() {
    	String s= ".,";
    	System.out.println(isPalindrome(s));
    }
    public boolean isPalindrome(String s) {
    	if (s==null || s.length()<=0) {
    		return true;
    	}
        int i=0,j=s.length()-1;
        while(i<j) {
            while (i<s.length()&&!isValid(s.charAt(i))) {
                i++;
            }
             while (i<s.length()&&!isValid(s.charAt(j))) {
                j--;
            }
            if (i<s.length()&&j<s.length()) {
            	if (!(s.charAt(i)+"").equalsIgnoreCase(s.charAt(j)+"")) {
            		return false;
            	}
            }
            i++;
            j--;
        }
        return true;
    }

    public boolean isValid(char c) {
        if (Character.isLetter(c) || Character.isDigit(c)) {
            return true;
        }
        return false;
    }
}
