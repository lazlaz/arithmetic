package com.laz.arithmetic.competition;

import org.junit.Test;

import org.junit.Assert;

//https://leetcode-cn.com/contest/weekly-contest-224/
public class Competition18 {
	@Test
	// 1725. 可以形成最大正方形的矩形数目
	public void test1() {
		Assert.assertEquals(3, countGoodRectangles(new int[][] { { 5, 8 }, { 3, 9 }, { 5, 12 }, { 16, 5 } }));
	}

	public int countGoodRectangles(int[][] rectangles) {
		int[] res = new int[rectangles.length];
		for (int i=0;i<rectangles.length;i++) {
			res[i] =  Math.min(rectangles[i][0], rectangles[i][1]);
		}
		int max = 0;
		for (int i=0;i<res.length;i++) {
			max = Math.max(max, res[i]);
		}
		int count = 0;
		for (int i=0;i<res.length;i++) {
			if (res[i] == max) {
				count++;
			}
		}
		return count;
	}
}
