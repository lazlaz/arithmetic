package com.laz.arithmetic.type;

import org.junit.Assert;
import org.junit.Test;

public class Array {
	// 867. 转置矩阵
	@Test
	public void test1() {
		Assert.assertArrayEquals(new int[][] { { 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 } },
				transpose(new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } }));
	}

	public int[][] transpose(int[][] A) {
		int R = A.length, C = A[0].length;
		int[][] ans = new int[C][R];
		for (int r = 0; r < R; ++r)
			for (int c = 0; c < C; ++c) {
				ans[c][r] = A[r][c];
			}
		return ans;
	}

	// 子矩形查询
	@Test
	public void test2() {
		SubrectangleQueries subrectangleQueries = new SubrectangleQueries(
				new int[][] { { 1, 2, 1 }, { 4, 3, 4 }, { 3, 2, 1 }, { 1, 1, 1 } });
		// 初始的 (4x3) 矩形如下：
		// 1 2 1
		// 4 3 4
		// 3 2 1
		// 1 1 1
		Assert.assertEquals(1, subrectangleQueries.getValue(0, 2));
		; // 返回 1
		subrectangleQueries.updateSubrectangle(0, 0, 3, 2, 5);
		// 此次更新后矩形变为：
		// 5 5 5
		// 5 5 5
		// 5 5 5
		// 5 5 5
		Assert.assertEquals(5, subrectangleQueries.getValue(0, 2));
		Assert.assertEquals(5, subrectangleQueries.getValue(3, 1));
		subrectangleQueries.updateSubrectangle(3, 0, 3, 2, 10);
	}

	class SubrectangleQueries {
		int[][] rectangle;

		public SubrectangleQueries(int[][] rectangle) {
			this.rectangle = rectangle;
		}

		public void updateSubrectangle(int row1, int col1, int row2, int col2, int newValue) {
			if (rectangle == null || rectangle.length == 0) {
				return;
			}
			int r = rectangle.length;
			int n = rectangle[0].length;
			for (int i = row1; i <= row2; i++) {
				for (int j = col1; j <= col2; j++) {
					rectangle[i][j] = newValue;
				}
			}
		}

		public int getValue(int row, int col) {
			return rectangle[row][col];
		}
	}
}
