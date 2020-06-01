package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LeetCode5 {
	//拥有最多糖果的孩子
	@Test
	public void test1() {
		int[] candies = new int[] {2,3,5,1,3};
		int extraCandies = 3;
		List<Boolean> list = kidsWithCandies(candies, extraCandies);
		for (Boolean b : list) {
			System.out.println(b);
		}
	}
	 public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
		 int max = 0;
		 for (int i=0;i<candies.length;i++) {
			 if (max<candies[i]) {
				 max = candies[i];
			 }
		 }
		 List<Boolean> list = new ArrayList<Boolean>();
		 for (int i=0;i<candies.length;i++) {
			 if (candies[i]+extraCandies>=max) {
				 list.add(true);
			 } else {
				 list.add(false);
			 }
		 }
		 return list;
	 }
}
