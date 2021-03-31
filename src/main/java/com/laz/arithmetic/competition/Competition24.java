package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

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
    
    //1807. 替换字符串中的括号内容
    @Test
    public void test3() {
    	List<List<String>> knowledge = new ArrayList<>();
    	knowledge.add(Arrays.asList("name","bob"));
    	knowledge.add(Arrays.asList("age","two"));
    	Assert.assertEquals("bobistwoyearsold", new Solution3().evaluate("(name)is(age)yearsold", knowledge));
    }
    class Solution3 {
        public String evaluate(String s, List<List<String>> knowledge) {
        	//knowledge转map
        	Map<String,String> map = new HashMap<>();
        	for (List<String> know:knowledge) {
        		map.put(know.get(0), know.get(1));
        	}
        	//替换字符串
        	StringBuilder sb = new StringBuilder();
        	for (int i=0;i<s.length();i++) {
        		if (s.charAt(i) == '(') {
        			StringBuilder key = new StringBuilder();
        			for (int j=i+1;j<s.length();j++) {
        				if (s.charAt(j)==')') {
        					i=j;
        					break;
        				}
        				key.append(s.charAt(j));
        			}
        			String v = map.getOrDefault(key.toString(), "?");
        			sb.append(v);
        		} else {
        			sb.append(s.charAt(i));
        		}
        	}
        	return sb.toString();
        }
    }
}
