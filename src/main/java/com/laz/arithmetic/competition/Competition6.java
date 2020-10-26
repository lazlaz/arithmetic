package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class Competition6 {
	// 按键持续时间最长的键
	@Test
	public void test1() {
//		Assert.assertEquals('c', slowestKey(new int [] {
//			9,29,49,50
//		},"cbcd"));

		Assert.assertEquals('a', slowestKey(new int[] { 12, 23, 36, 46, 62 }, "spuda"));
	}

	public char slowestKey(int[] releaseTimes, String keysPressed) {
		int lastTime = 0;
		int max = 0;
		char res = ' ';
		for (int i = 0; i < keysPressed.length(); i++) {
			char c = keysPressed.charAt(i);
			if (releaseTimes[i] - lastTime > max) {
				max = releaseTimes[i] - lastTime;
				res = c;
			} else if (releaseTimes[i] - lastTime == max) {
				if (c > res) {
					res = c;
				}
			}
			lastTime = releaseTimes[i];
		}
		return res;
	}

	// 等差子数组
	@Test
	public void test2() {
		{
			List<Boolean> res = checkArithmeticSubarrays(new int[] { 4, 6, 5, 9, 3, 7 }, new int[] { 0, 0, 2 },
					new int[] { 2, 3, 5 });
			System.out.println(Joiner.on(",").join(res));
		}

		{

			List<Boolean> res = checkArithmeticSubarrays(new int[] { -12, -9, -3, -12, -6, 15, 20, -25, -20, -15, -10 },
					new int[] { 0, 1, 6, 4, 8, 7 }, new int[] { 4, 4, 9, 7, 9, 10 });
			System.out.println(Joiner.on(",").join(res));
		}

	}

	public List<Boolean> checkArithmeticSubarrays(int[] nums, int[] l, int[] r) {
		List<Boolean> res = new ArrayList<Boolean>();
		for (int i = 0; i < l.length; i++) {
			int newArr[] = Arrays.copyOfRange(nums, l[i], r[i] + 1);
			Arrays.sort(newArr);
			if (newArr.length >= 2) {
				int v = newArr[1] - newArr[0];
				boolean flag = true;
				for (int j = 2; j < newArr.length; j++) {
					if (newArr[j] - newArr[j - 1] != v) {
						flag = false;
						break;
					}
				}
				res.add(flag);
			} else {
				res.add(false);
			}
		}
		return res;
	}

	// 最小体力消耗路径
	@Test
	public void test3() {
//		Assert.assertEquals(2, minimumEffortPath(new int[][] { { 1, 2, 2 }, { 3, 8, 2 }, { 5, 3, 5 } }));
//		Assert.assertEquals(1, minimumEffortPath(new int[][] { { 1,2,3 }, { 3,8,4 }, { 5,3,5 } }));
//		Assert.assertEquals(0, minimumEffortPath(new int[][] { { 1,2,1,1,1 }, { 1,2,1,2,1 }, { 1,2,1,2,1 },{1,2,1,2,1},{1,1,1,2,1} }));

		// Assert.assertEquals(9, minimumEffortPath(new int[][] { {
		// 1,10,6,7,9,10,4,9}}));

		// Assert.assertEquals(9, minimumEffortPath(new int[][] { {
		// 1,10,6,7,9,10,4,9}}));

		Assert.assertEquals(5,
				new Solution3_2().minimumEffortPath(new int[][] { { 8, 3, 2, 5, 2, 10, 7, 1, 8, 9 }, { 1, 4, 9, 1, 10, 2, 4, 10, 3, 5 },
						{ 4, 10, 10, 3, 6, 1, 3, 9, 8, 8 }, { 4, 4, 6, 10, 10, 10, 2, 10, 8, 8 },
						{ 9, 10, 2, 4, 1, 2, 2, 6, 5, 7 }, { 2, 9, 2, 6, 1, 4, 7, 6, 10, 9 },
						{ 8, 8, 2, 10, 8, 2, 3, 9, 5, 3 }, { 2, 10, 9, 3, 5, 1, 7, 4, 5, 6 },
						{ 2, 3, 9, 2, 5, 10, 2, 7, 1, 8 }, { 9, 10, 4, 10, 7, 4, 9, 3, 1, 6 } }));
	}
	//https://leetcode-cn.com/problems/path-with-minimum-effort/solution/er-fen-cha-zhao-dfs-by-soap88/
	class Solution3_2 {
		int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
		public int minimumEffortPath(int[][] heights) {
		    int left = 0;
		    int right = 1000000;
		    //二分查找搜索最小值
		    while (left < right) {
		        int mid = (left + right) >>> 1;
		        if (!dfs(0, 0, mid, heights, new boolean[heights.length][heights[0].length])) {
		            left = mid + 1;
		        } else {
		            right = mid;
		        }
		    }
		    return left;
		}

		//检查是否存在一条从(x,y)到终点的路径，该路径中相邻顶点绝对值差不大于max
		boolean dfs(int x, int y, int max, int[][] h, boolean[][] visited) {
		    if (x == h.length - 1 && y == h[0].length - 1) {
		        return true;
		    }
		    visited[x][y] = true;
		    for (int[] dir : dirs) {
		        int nx = x + dir[0];
		        int ny = y + dir[1];
		        if (nx >= 0 && nx < h.length && ny >= 0 && ny < h[0].length && !visited[nx][ny]
		                && Math.abs(h[nx][ny] - h[x][y]) <= max) {
		            if (dfs(nx, ny, max, h, visited)) {
		                return true;
		            }
		        }
		    }
		    return false;
		}

	}
	//超时
	class Solution3 {
		int min = Integer.MAX_VALUE;
		boolean[][] visited;

		public int minimumEffortPath(int[][] heights) {
			int startX = 0, startY = 0;
			int sum = 0;
			int row = heights.length;
			int col = heights[0].length;
			visited = new boolean[row][col];
			visited[0][0] = true;
			dfs(startX, startY, heights, sum);
			return min;
		}

		private void dfs(int x, int y, int[][] heights, int sum) {
			if (sum >= min) {
				return;
			}
			if (x >= heights.length || y >= heights[0].length || x < 0 || y < 0) {
				return;
			}
			if (x == heights.length - 1 && y == heights[0].length - 1) {
				if (Math.abs(sum) < min) {
					min = Math.abs(sum);
				}
				return;
			}
			// 上
			if (x - 1 >= 0 && !visited[x - 1][y]) {
				visited[x - 1][y] = true;
				int oldSum = sum;
				int v = heights[x][y] - heights[x - 1][y];
				if (Math.abs(v) > sum) {
					sum = Math.abs(v);
				}
				dfs(x - 1, y, heights, sum);
				sum = oldSum;
				visited[x - 1][y] = false;
			}
			// 下
			if (x + 1 < heights.length && !visited[x + 1][y]) {
				visited[x + 1][y] = true;
				int oldSum = sum;
				int v = heights[x][y] - heights[x + 1][y];
				if (Math.abs(v) > sum) {
					sum = Math.abs(v);
				}
				dfs(x + 1, y, heights, sum);
				sum = oldSum;
				visited[x + 1][y] = false;
			}
			// 左
			if (y - 1 >= 0 && !visited[x][y - 1]) {
				visited[x][y - 1] = true;
				int oldSum = sum;
				int v = heights[x][y] - heights[x][y - 1];
				if (Math.abs(v) > sum) {
					sum = Math.abs(v);
				}
				dfs(x, y - 1, heights, sum);
				sum = oldSum;
				visited[x][y - 1] = false;
			}
			// 右
			if (y + 1 < heights[0].length && !visited[x][y + 1]) {
				visited[x][y + 1] = true;
				int oldSum = sum;
				int v = heights[x][y] - heights[x][y + 1];
				if (Math.abs(v) > sum) {
					sum = Math.abs(v);
				}
				dfs(x, y + 1, heights, sum);
				sum = oldSum;
				visited[x][y + 1] = false;
			}

		}	
	}
	
}
