package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode19 {
	//830. 较大分组的位置
	@Test
	public void test1() {
		List<List<Integer>> ret = largeGroupPositions("aaa");
		for (List<Integer> list : ret) {
			System.out.println(Joiner.on(",").join(list));
		}
	}
	public List<List<Integer>> largeGroupPositions(String s) {
		List<List<Integer>> ret = new ArrayList<List<Integer>>();
		char currChar = ' ';
		int start = 0;
		int end = 0;
		for (int i=0;i<s.length();i++) {
			char c = s.charAt(i);
			if (currChar==' ') {
				currChar = c;
				start = i;
			} else if (currChar==c){
				
			} else {
				end = i-1;
				if (end-start>=2) {
					ret.add(Arrays.asList(start,end));
				}
				currChar = c;
				start = i;
			}
		}
		end = s.length()-1;
		if (end-start>=2) {
			ret.add(Arrays.asList(start,end));
		}
		return ret;
	}
	
}
