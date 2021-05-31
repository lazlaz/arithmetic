package com.laz.arithmetic.competition;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-243/
public class Competition31 {
	//1881. 插入后的最大值
	@Test
	public void test2() {
		//Assert.assertEquals("999", new Solution2().maxValue("99", 9));
		Assert.assertEquals("-123", new Solution2().maxValue("-13", 2));
	}
	
	class Solution2 {
	    public String maxValue(String n, int x) {
	    	//判断符号
	    	char[] cs = n.toCharArray();
	    	StringBuilder sb = new StringBuilder();
	    	if (cs[0] == '-') {
	    		sb.append('-');
	    		//负数，尽可能保证除符号以外的数更小
	    		int i;
	    		for ( i=1;i<cs.length;i++) {
	    			//找到一个比x大的数字前进行插入
	    			if ((cs[i]-'0')>x) {
	    				break;
	    			}
	    			sb.append(cs[i]);
	    		}
    			sb.append(x);
    			if (i<cs.length) {
    				sb.append(n.substring(i, cs.length));
    			}
	    	} else {
	    		//正数，尽可能保证大
	    		int i;
	    		for ( i=0;i<cs.length;i++) {
	    			//找到一个比x小的数字前进行插入
	    			if ((cs[i]-'0')<x) {
	    				break;
	    			}
	    			sb.append(cs[i]);
	    		}
    			sb.append(x);
    			if (i<cs.length) {
    				sb.append(n.substring(i, cs.length));
    			}
	    	}
	    	return sb.toString();
	    }
	}
}
