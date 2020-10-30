package com.laz.arithmetic;

import org.junit.Test;

import org.junit.Assert;

public class LeetCode16 {
	// 岛屿的周长
	@Test
	public void test1() {
		Assert.assertEquals(16, islandPerimeter(new int[][] {
			{0,1,0,0},{1,1,1,0},{0,1,0,0},{1,1,0,0}
		}));
	}
	
	public int islandPerimeter(int[][] grid) {
		int res = 0;
		int[][] dict = new int[][] {
			{1,0},
			{0,1},
			{-1,0},
			{0,-1}
		};
		for (int i=0;i<grid.length;i++) {
			for (int j=0;j<grid[i].length;j++) {
				if (grid[i][j] == 1) {
					int count = 4;
					for (int z=0;z<dict.length;z++) {
						int row = i+dict[z][0];
						int col = j+dict[z][1];
						if (row>=0&&row<grid.length && col>=0 && col<grid[i].length) {
							if (grid[row][col] == 1) {
								count--;
							}
						}
					}
					res+=count;
				}
			}
		}
		return res;
	}
}
