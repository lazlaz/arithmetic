package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
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
    //模式匹配
    @Test
    public void test3() {
    	Assert.assertEquals(true,patternMatching("a", ""));
    	Assert.assertEquals(true,patternMatching("abb", "jwwwwjttwwwwjtt"));
    	Assert.assertEquals(true,patternMatching("", ""));
    	Assert.assertEquals(false,patternMatching("bbbbbbbbbbbbabbbbbbbbbbbbbbbbbbbbbbbabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbabbbbbbbabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbabbbbbbbbbbbbbbbbbbbbb", 
    			"wzwzwzzwzwzwzwzwzwzwzwzmaczcazplafmwanjpnmjpjnjwnzemzolmwelllazyjmnnpomnizpzlywzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzmaczcazplafmwanjpnmjpjnjwnzemzolmwelllazyjmnnpomnizpzlywzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzmaczcazplafmwanjpnmjpjnjwnzemzolmwelllazyjmnnpomnizpzlywzwzwzwzwzwzwzmaczcazplafmwanjpnmjpjnjwnzemzolmwelllazyjmnnpomnizpzlywzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzmaczcazplafmwanjpnmjpjnjwnzemzolmwelllazyjmnnpomnizpzlywzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwzwz"));
    	Assert.assertEquals(true,patternMatching("abb", 
    			"dryqxzysggjljxdxag"));
    	Assert.assertEquals(true,patternMatching("abba", 
    			"dogdogdogdog"));
    	Assert.assertEquals(true,patternMatching("baabab", 
    			"eimmieimmieeimmiee"));
    }
    public boolean patternMatching(String pattern, String value) {
		if (pattern == null || value == null) {
			return false;
		}
		if (pattern.equals("") && value.equals("")) {
			return true;
		}
		if (pattern.equals("")) {
			return false;
		}
		int aCount=0,bCount=0;
		int count = 0;
		int pLen = pattern.length();
		for (int i=0;i<pattern.length();i++) {
			if (pattern.charAt(i) == 'a') {
				aCount++;
			} else {
				bCount++;
			}
		}
		if (pattern.charAt(0) == 'a') {
			count = aCount;
		}
		if (pattern.charAt(0) == 'b') {
			count = bCount;
		}
		int len = value.length();
		if (aCount==0||bCount==0) {
			String v = value.substring(0,value.length()/count);
			if (match(value,pattern,v,"")) {
				return true;
			} else {
				return false;
			}
		}
		for (int i=0;i<=len;i++) {
			String v1 = value.substring(0,i);
			int l = len-v1.length()*count;
			if (l%(pLen-count) != 0) {
				continue;
			}
			for (int j=i;j<=len;j++) {
				for (int z=j;z<=len;z++) {
					String newValue = value.substring(j,z);
					if (newValue.length()+v1.length()*count != value.length()) {
						continue;
					}
					int v2Count = pLen-count;
					String v2 = newValue.substring(0,newValue.length()/v2Count);
					if (v1.equals(v2)) {
						continue;
					}
					if (match(value,pattern,v1,v2)) {
						return true;
					}
				}
			}
			
		}
		return false;
	}
	public boolean match(String value,String pattern,String v1,String v2) {
		Map<String,String> map = new HashMap<String,String>();
		boolean flag = false;//首字符是否找到
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<pattern.length();i++) {
			String k = pattern.charAt(i)+"";
			if (map.get(k) == null) {
				if (flag) {
					map.put(k, v2);
				} else {
					flag = true;
					map.put(k, v1);
				}
			}
			sb.append(map.get(k));
		}
		if (sb.toString().equals(value)) {
			return true;
		}
		return false;
	}
	
	//长度最小的子数组
    @Test
    public void test4() {
    	//Assert.assertEquals(2, minSubArrayLen(7, new int[] {2,3,1,2,4,3}));
    	Assert.assertEquals(3, minSubArrayLen(11, new int[] {1,2,3,4,5}));
    }
    public int minSubArrayLen(int s, int[] nums) {
    	int start=0,end=0;
    	int len = nums.length;
    	int ans = Integer.MAX_VALUE;
    	int sum = 0;
    	while (end<len) {
    		sum += nums[end];
    		while (sum >= s) {
    			ans = Math.min(ans, end-start+1);
    			sum -= nums[start];
    			start++;
    		}
    		end++;
    	}
    	return ans==Integer.MAX_VALUE?0:ans;
    }
    
    //用两个栈实现队列
    @Test
    public void test5() {
    	 CQueue obj = new CQueue();
    	 obj.appendTail(3);
    	 System.out.println(obj.deleteHead());
    	 System.out.println(obj.deleteHead());
    }
    class CQueue {
    	private Deque<Integer> stackIn;
    	private Deque<Integer> stackOut;
        public CQueue() {
        	stackIn =new LinkedList<Integer>();
        	stackOut =new LinkedList<Integer>();
        }
        
        public void appendTail(int value) {
        	if (!stackOut.isEmpty()) {
        		s2s(stackOut,stackIn);
        	}
        	stackIn.push(value);
        }
        /*
         * 一个栈的数据迁移到另一个栈
         */
        private void s2s(Deque<Integer> s1,Deque<Integer> s2) {
        	while (s1.peek()!=null) {
        		s2.push(s1.pop());
        	}
        }
        
        public int deleteHead() {
        	if (!stackIn.isEmpty()) {
        		s2s(stackIn,stackOut);
        	}
        	if (stackOut.isEmpty()) {
        		return -1;
        	}
        	return stackOut.pop();
        }
    }
    
    //两数相除
    @Test
    public void test6() {
    	//Assert.assertEquals(3, divide(10, 3));
    	//Assert.assertEquals(2147483647, divide(-2147483648,-1));
    	Assert.assertEquals(1073741823, divide(2147483647,2));
    }
    
    public int divide(int dividend, int divisor) {
    	//特殊值情况考虑
    	if (dividend==0) {
    		return 0;
    	}
    	if (divisor==1) {
    		return dividend;
    	}
    	if (divisor==-1) {
    		if(dividend>Integer.MIN_VALUE) {
    			return -dividend;// 只要不是最小的那个整数，都是直接返回相反数就好啦
    		}
            return Integer.MAX_VALUE;// 是最小的那个，那就返回最大的整数啦
    	}
    	int sign = 1; 
    	//判断最终结果是正数还是负数
    	if((dividend>0&&divisor<0) || (dividend<0&&divisor>0)){
    		sign = -1;
    	}
    	//都改为负号是因为int 的范围是[2^32, 2^32-1]，如果a是-2^32，转为正数时将会溢出
    	int a = dividend>0?-dividend:dividend;
    	int b = divisor>0?-divisor:divisor;
    	if (a>b) {
    		return 0;
    	}
    	int res = div(a,b);
    	if (sign==-1) {
    		return -res;
    	}
    	return res;
    	
    }
	private int div(int dividend, int divisor) {
		if (dividend>divisor) {
			return 0;
		}
		int count = 1;
		int tb = divisor;
		while ((tb+tb)>=dividend && tb + tb < 0) {
			count = count+count; //最小解加倍
			tb = tb+tb; //值加倍
		}
		return count+div((dividend-tb),divisor);
	}
    
}
