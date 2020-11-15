package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Joiner;

//https://leetcode-cn.com/problems/design-an-ordered-stream/
public class Competition9 {
	//设计有序流
	@Test	
	public void test1() {
		OrderedStream os= new OrderedStream(5);
		System.out.println(Joiner.on(",").join(os.insert(3, "ccccc"))); // 插入 (3, "ccccc")，返回 []
		System.out.println(Joiner.on(",").join(os.insert(1, "aaaaa"))); // 插入 (1, "aaaaa")，返回 ["aaaaa"]
		System.out.println(Joiner.on(",").join(os.insert(2, "bbbbb"))); // 插入 (2, "bbbbb")，返回 ["bbbbb", "ccccc"]
		System.out.println(Joiner.on(",").join(os.insert(5, "eeeee"))); // 插入 (5, "eeeee")，返回 []
		System.out.println(Joiner.on(",").join(os.insert(4, "ddddd"))); // 插入 (4, "ddddd")，返回 ["ddddd", "eeeee"]
	}
	class OrderedStream {
		int ptr = 0;
		private String[] arr;
	    public OrderedStream(int n) {
	    	arr = new String[n];
	    }
	    
	    public List<String> insert(int id, String value) {
	    	arr[id-1]=value;
	    	if (arr[ptr]==null) {
	    		return Collections.EMPTY_LIST;
	    	}
	    	List<String> res = new ArrayList<String>();
	    	while (ptr<arr.length&& arr[ptr]!=null) {
	    		res.add(arr[ptr]);
	    		ptr++;
	    	}
	    	return res;
	    }
	}
}
