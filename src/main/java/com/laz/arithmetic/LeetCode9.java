package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

	// 矩阵中的最长递增路径
	@Test
	public void test3() {
		Assert.assertEquals(4, longestIncreasingPath(new int[][] { { 9, 9, 4 }, { 6, 6, 8 }, { 2, 1, 1 } }));
	}

	public int[][] dirs = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
	public int rows, columns;

	public int longestIncreasingPath(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		rows = matrix.length;
		columns = matrix[0].length;
		int[][] memo = new int[rows][columns];
		int ans = 0;
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < columns; ++j) {
				ans = Math.max(ans, dfs(matrix, i, j, memo));
			}
		}
		return ans;
	}

	public int dfs(int[][] matrix, int row, int column, int[][] memo) {
		if (memo[row][column] != 0) {
			return memo[row][column];
		}
		++memo[row][column];
		// 遍历移动的四个方向
		for (int[] dir : dirs) {
			int newRow = row + dir[0], newColumn = column + dir[1];
			// 行列在范围内，且值是递增的
			if (newRow >= 0 && newRow < rows && newColumn >= 0 && newColumn < columns
					&& matrix[newRow][newColumn] > matrix[row][column]) {
				memo[row][column] = Math.max(memo[row][column], dfs(matrix, newRow, newColumn, memo) + 1);
			}
		}
		return memo[row][column];
	}

	// 第k个排列
	@Test
	public void test4() {
		Assert.assertEquals("213", getPermutation(3, 3));
	}

	//思路：https://leetcode-cn.com/problems/permutation-sequence/solution/pai-lie-zu-he-zhi-di-kge-pai-lie-golden-monkey-by-/
	public String getPermutation(int n, int k) {
		int[] nums = new int[n];// 生成nums数组
		for (int i = 0; i < n; i++) {
			nums[i] = i + 1;
		}
		boolean[] used = new boolean[n];// 记录当前的索引的数是否被使用过
		return dfs(nums, new ArrayList<String>(), used, 0, n, k);
	}

	/**
	 * @param nums      源数组
	 * @param levelList 每一层的选择
	 * @param used      数组的元素是否被使用过
	 * @param depth     深度，也就是当前使用的元素的索引
	 * @param n         上限值
	 * @param k         第k个
	 * @return 第k个排列
	 */
	private String dfs(int[] nums, List<String> levelList, boolean[] used, int depth, int n, int k) {
		if (depth == n) {// 触发出口条件，开始收集结果集
			StringBuilder res = new StringBuilder();
			for (String s : levelList) {
				res.append(s);
			}
			return res.toString();
		}
		int cur = factorial(n - depth - 1);// 当前的depth也就是索引，有多少排列数
		for (int i = 0; i < n; i++) {
			if (used[i])
				continue;// 当前元素被使用过，做剪枝
			if (cur < k) {// 当前的排列组合数小于k，说明就算这一层排完了，也到不了第k个，剪枝
				k -= cur;
				continue;
			}
			levelList.add(nums[i] + "");// list收的是string类型
			used[i] = true;// 当前元素被使用过标记
			return dfs(nums, levelList, used, depth + 1, n, k);
		}
		return null;
	}

	// 返回n的阶乘，如3!=3*2*1=6
	private int factorial(int n) {
		int res = 1;
		while (n > 0) {
			res *= n--;
		}
		return res;
	}
}
