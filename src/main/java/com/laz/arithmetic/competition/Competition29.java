package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-239/
public class Competition29 {
	//1849. 将字符串拆分为递减的连续值
	@Test
	public void test2() {
//		Assert.assertEquals(false, new Solution2().splitString("1234"));
//		Assert.assertEquals(true, new Solution2().splitString("050043"));
//		Assert.assertEquals(false, new Solution2().splitString("9080701"));
//		Assert.assertEquals(true, new Solution2().splitString("10009998"));
//		Assert.assertEquals(true, new Solution2().splitString("53520515049"));
		Assert.assertEquals(false, new Solution2().splitString("94650723337775781477"));
		Assert.assertEquals(true, new Solution2().splitString("552551550549548547"));
	}
	class Solution2 {
	    boolean ret = false;
		public boolean splitString(String s) {
			StringBuilder sb = new StringBuilder();
			for (int i=0;i<s.length()-1;i++) {
				sb.append(s.charAt(i));
				long preV = Long.parseLong(sb.toString());
				if (preV>=Long.MAX_VALUE/10000) {//如果此值大于了long的多少，根据长度条件，后面不可能有超过的，跳过
					break;
				}
				dfs(s,new StringBuilder(),preV,(i+1));
			}
	    	return ret;
	    }
		private void dfs(String s, StringBuilder sb, long preV, int index) {
			if (index==s.length()) {
				ret = true;
				return;
			}
			for (int i=index;i<s.length();i++) {
				sb.append(s.charAt(i));
				long currV = Long.parseLong(sb.toString());
				if (preV-currV==1) {
					dfs(s,new StringBuilder(),currV,(i+1));
				}else if (currV>=preV) { //继续加是找不到合适的值了，直接break
					break;
				}
			}
		}
	}
}
