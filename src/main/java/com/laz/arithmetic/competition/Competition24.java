package com.laz.arithmetic.competition;

import org.junit.Test;

import org.junit.Assert;

//https://leetcode-cn.com/contest/weekly-contest-234/
public class Competition24 {
	//1806. 还原排列的最少操作步数
	@Test
	public void test2() {
		Assert.assertEquals(2, reinitializePermutation(4));
		Assert.assertEquals(4, reinitializePermutation(6));
	}
    public int reinitializePermutation(int n) {
    	int ans = 0;
    	
    	//如果只计算第2个数，回答初始位置的步数呢？
    	int index = 1;
    	while (true) {
    		ans++;
    		if (index%2==0) {
    			index = index/2;
    		} else {
    			index = n/2+(index-1)/2;
    		}
    		if (index==1)  {//重新回到1
    			break;
    		}
    	}
    	return ans;
    }
}
