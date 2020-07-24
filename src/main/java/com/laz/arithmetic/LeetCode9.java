package com.laz.arithmetic;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Test;

public class LeetCode9 {
	// 最小路径和
	@Test
	public void test1() {
		Assert.assertEquals(7, minPathSum(new int[][] { { 1, 3, 1 }, { 1, 5, 1 }, { 4, 2, 1 } }));
	}

	public int minPathSum(int[][] grid) {
		if (grid == null || grid.length == 0 || grid[0].length == 0) {
			return 0;
		}
		int m = grid.length;
		int n = grid[0].length;
		int[][] dp = new int[m][n];
		dp[0][0] = grid[0][0];
		for (int i = 1; i < n; i++) {
			dp[0][i] = dp[0][i - 1] + grid[0][i];

		}

		for (int i = 1; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (j == 0) {
					dp[i][j] = dp[i - 1][j] + grid[i][j];
				} else {
					dp[i][j] = Math.min(dp[i - 1][j] + grid[i][j], dp[i][j - 1] + grid[i][j]);
				}

			}
		}

		return dp[m - 1][n - 1];

	}

	// 插入区间
	@Test
	public void test2() {
		int[][] intervals = new int[][] { { 1, 3 }, { 6, 9 } };
		int[] newInterval = new int[] { 2, 5 };
		int[][] res = insert(intervals, newInterval);
		for (int[] is : res) {
			for (int r : is) {
				System.out.print(r);
			}
			System.out.println();
		}
	}

	public int[][] insert(int[][] intervals, int[] newInterval) {
		int newStart = newInterval[0], newEnd = newInterval[1];
		int idx = 0, n = intervals.length;
		LinkedList<int[]> output = new LinkedList<int[]>();

		// 将开始小于newStart的值加入输出
		while (idx < n && newStart > intervals[idx][0]) {
			output.add(intervals[idx]);
			idx++;
		}

		// 添加新间隔
		int[] interval = new int[2];
		// 没有开始比newStart小的区间或者newStart大于原有去区间访问
		if (output.isEmpty() || output.getLast()[1] < newStart) {
			output.add(newInterval);
		} else {
			// 合并
			interval = output.removeLast();
			interval[1] = Math.max(interval[1], newEnd);
			output.add(interval);
		}

		// 添加后续区间，并合并
		while (idx < n) {
			interval = intervals[idx++];
			int start = interval[0], end = interval[1];
			if (output.getLast()[1] < start) {
				output.add(interval);
			} else {
				interval = output.removeLast();
				interval[1] = Math.max(interval[1], end);
				output.add(interval);
			}
		}
		return output.toArray(new int[output.size()][2]);
	}

}
