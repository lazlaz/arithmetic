package com.laz.arithmetic;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class LeetCode3 {
	//最长回文串
	@Test
	public void test1() {
		System.out.println(longestPalindrome("civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth"));
	}
	public int longestPalindrome(String s) {
		char[] chars = s.toCharArray();
		Map<Character,Integer> counts= new HashMap<Character,Integer>();
		for (int i=0; i<chars.length; i++) {
			if (counts.get(chars[i]) == null) {
				counts.put(chars[i],1);
			} else {
				int value = counts.get(chars[i])+1;
				counts.put(chars[i],value);
			}
		}
		int result = 0;
		boolean falg = false;
		for (Integer count:counts.values()) {
			if (count%2==0) {
				result+=count;
			} else {
				falg=true;
				result=result+count-1;
			}
		}
		if (falg) {
			result+=1;
		}
		return result;
    }
	
	//按摩师
		@Test
		public void test2() {
			int[] nums = new int[] {2,1,4,5,3,1,1,3};
			System.out.println(massage(nums));
		}
	    public int massage(int[] nums) {
	    	if (nums.length==0) {
	    		return 0;
	    	}
	    	if (nums.length==1) {
	    		return nums[0];
	    	}
	    	if (nums.length==2) {
	    		return nums[0]>nums[1]?nums[0]:nums[1];
	    	}
	    	int[] maxs = new int[nums.length];
	    	maxs[0] = nums[0];
	    	maxs[1] = nums[0]>nums[1]?nums[0]:nums[1];
	    	for (int i=2; i<nums.length;i++) {
	    		if (maxs[i-2]+nums[i]>maxs[i-1]) {
	    			maxs[i] = maxs[i-2]+nums[i];
	    		} else {
	    			maxs[i] = maxs[i-1];
	    		}
	    	}
	    	return maxs[nums.length-1];
	    }
}
