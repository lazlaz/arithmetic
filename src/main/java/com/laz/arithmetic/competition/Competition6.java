package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
		Assert.assertEquals(2,new Solution3_3().minimumEffortPath(new int[][] { { 1, 2, 2 }, { 3, 8, 2 }, { 5, 3, 5 } }));
//		Assert.assertEquals(1, minimumEffortPath(new int[][] { { 1,2,3 }, { 3,8,4 }, { 5,3,5 } }));
//		Assert.assertEquals(0, minimumEffortPath(new int[][] { { 1,2,1,1,1 }, { 1,2,1,2,1 }, { 1,2,1,2,1 },{1,2,1,2,1},{1,1,1,2,1} }));

		// Assert.assertEquals(9, minimumEffortPath(new int[][] { {
		// 1,10,6,7,9,10,4,9}}));

		// Assert.assertEquals(9, minimumEffortPath(new int[][] { {
		// 1,10,6,7,9,10,4,9}}));

		Assert.assertEquals(5, new Solution3_3().minimumEffortPath(new int[][] { { 8, 3, 2, 5, 2, 10, 7, 1, 8, 9 },
				{ 1, 4, 9, 1, 10, 2, 4, 10, 3, 5 }, { 4, 10, 10, 3, 6, 1, 3, 9, 8, 8 },
				{ 4, 4, 6, 10, 10, 10, 2, 10, 8, 8 }, { 9, 10, 2, 4, 1, 2, 2, 6, 5, 7 },
				{ 2, 9, 2, 6, 1, 4, 7, 6, 10, 9 }, { 8, 8, 2, 10, 8, 2, 3, 9, 5, 3 }, { 2, 10, 9, 3, 5, 1, 7, 4, 5, 6 },
				{ 2, 3, 9, 2, 5, 10, 2, 7, 1, 8 }, { 9, 10, 4, 10, 7, 4, 9, 3, 1, 6 } }));
	}

	// https://leetcode-cn.com/problems/path-with-minimum-effort/solution/zui-xiao-ti-li-xiao-hao-lu-jing-by-leetc-3q2j/
	class Solution3_3 {
		public int minimumEffortPath(int[][] heights) {
			int m = heights.length;
			int n = heights[0].length;
			List<int[]> edges = new ArrayList<int[]>();
			for (int i = 0; i < m; ++i) {
				for (int j = 0; j < n; ++j) {
					int id = i * n + j;
					if (i > 0) {
						edges.add(new int[] { id - n, id, Math.abs(heights[i][j] - heights[i - 1][j]) });
					}
					if (j > 0) {
						edges.add(new int[] { id - 1, id, Math.abs(heights[i][j] - heights[i][j - 1]) });
					}
				}
			}
			Collections.sort(edges, new Comparator<int[]>() {
				public int compare(int[] edge1, int[] edge2) {
					return edge1[2] - edge2[2];
				}
			});

			UnionFind uf = new UnionFind(m * n);
			int ans = 0;
			for (int[] edge : edges) {
				int x = edge[0], y = edge[1], v = edge[2];
				uf.unite(x, y);
				if (uf.connected(0, m * n - 1)) {
					ans = v;
					break;
				}
			}
			return ans;
		}
		//并查集模板
		class UnionFind {
			int[] parent;
			int[] size;
			int n;
			// 当前连通分量数目
			int setCount;

			public UnionFind(int n) {
				this.n = n;
				this.setCount = n;
				this.parent = new int[n];
				this.size = new int[n];
				Arrays.fill(size, 1);
				for (int i = 0; i < n; ++i) {
					parent[i] = i;
				}
			}

			public int findset(int x) {
				return parent[x] == x ? x : (parent[x] = findset(parent[x]));
			}

			public boolean unite(int x, int y) {
				x = findset(x);
				y = findset(y);
				if (x == y) {
					return false;
				}
				if (size[x] < size[y]) {
					int temp = x;
					x = y;
					y = temp;
				}
				parent[y] = x;
				size[x] += size[y];
				--setCount;
				return true;
			}

			public boolean connected(int x, int y) {
				x = findset(x);
				y = findset(y);
				return x == y;
			}

		}
	}

	// https://leetcode-cn.com/problems/path-with-minimum-effort/solution/er-fen-cha-zhao-dfs-by-soap88/
	class Solution3_2 {
		int[][] dirs = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };

		public int minimumEffortPath(int[][] heights) {
			int left = 0;
			int right = 1000000;
			// 二分查找搜索最小值
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

		// 检查是否存在一条从(x,y)到终点的路径，该路径中相邻顶点绝对值差不大于max
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

	// 超时
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

	// 矩阵转换后的秩
	@Test
	public void test4() {
//		Assert.assertArrayEquals(new int[][] {
//			{1,2},{2,3}
//		}, new Solution4().matrixRankTransform(new int[][] {
//			{1,2},{3,4}
//		}));

		Assert.assertArrayEquals(new int[][] { { 4, 2, 3 }, { 1, 3, 4 }, { 5, 1, 6 }, { 1, 3, 4 } }, new Solution4()
				.matrixRankTransform(new int[][] { { 20, -21, 14 }, { -19, 4, 19 }, { 22, -47, 24 }, { -19, 4, 19 } }));
	}

	// https://leetcode-cn.com/problems/rank-transform-of-a-matrix/solution/javadai-ma-de-pai-xu-bing-cha-ji-tong-su-yi-dong-b/
	class Solution4 {
		public int[][] matrixRankTransform(int[][] matrix) {
			final int row = matrix.length;
			final int col = matrix[0].length;

			// 对每个坐标状态压缩(state = i * col + j)
			Integer[] indexs = new Integer[row * col];
			for (int i = 0; i < indexs.length; i++) {
				indexs[i] = i;
			}

			// 对坐标点根据matrix的值进行排序。
			Arrays.sort(indexs, new Comparator<Integer>() {
				@Override
				public int compare(Integer t1, Integer t2) {
					return matrix[t1 / col][t1 % col] - matrix[t2 / col][t2 % col];
				}
			});

			int[] minRows = new int[row]; // 第i行的秩号序列，第i行下一个秩号需要大于等于此cell([i, minRows[i]])的值
			int[] minCols = new int[col]; // 第j列的秩号序列，第j列下一个秩号需要大于等于此cell([minCols[j], j])的值
			Arrays.fill(minRows, -1);
			Arrays.fill(minCols, -1);
			int[] leaders = new int[row * col]; // 并查集，表示同行或同列的同样值串起来的一个集合。
			int[] leaderVals = new int[row * col]; // 并查集leader的对应的秩号值。
			for (int i = 0; i < leaders.length; i++) {
				leaders[i] = i;
			}

			int pos = 0;
			while (pos < indexs.length) {

				int val = 1;
				int index = indexs[pos];

				// 排序后的第pos个单元格
				int i = index / col;
				int j = index % col;
				int tmpIndex;
				int tmpVal;

				// 第i行应该分配的下一个秩号
				if (minRows[i] != -1) {
					tmpIndex = i * col + minRows[i];
					int leaderIndex = getLeader(leaders, tmpIndex);
					tmpVal = leaderVals[leaderIndex];
					if (matrix[i][j] == matrix[i][minRows[i]]) {
						// 如果i行有相同的值，合并并查集
						mergeLeader(leaders, index, tmpIndex);
						val = Math.max(val, tmpVal);
					} else {
						val = Math.max(val, tmpVal + 1);
					}
				}

				// 第j列应该分配的下一个秩号
				if (minCols[j] != -1) {
					tmpIndex = minCols[j] * col + j;
					int leaderIndex = getLeader(leaders, tmpIndex);
					tmpVal = leaderVals[leaderIndex];
					if (matrix[i][j] == matrix[minCols[j]][j]) {
						// 如果j列有相同的值，合并并查集
						mergeLeader(leaders, index, tmpIndex);
						val = Math.max(val, tmpVal);
					} else {
						val = Math.max(val, tmpVal + 1);
					}
				}
				// val = Math.max(val, ...)是取i行下一个秩号 和 j列下一个秩号 的较大值

				// 更新i行和j列秩号序列。此时[i, j]为i行和j列的最大秩号
				minRows[i] = j;
				minCols[j] = i;

				// 更新并查集的秩号
				int leader = getLeader(leaders, index);
				leaderVals[leader] = val;
				pos++;

			}

			// 将秩号结果由并查集导出到数组ans
			int[][] ans = new int[row][col];
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					int index = i * col + j;
					ans[i][j] = leaderVals[getLeader(leaders, index)];
				}
			}
			return ans;
		}

		private void mergeLeader(int[] leaders, int index, int tmpIndex) {
			int leader1 = getLeader(leaders, index);
			int leader2 = getLeader(leaders, tmpIndex);
			if (leader1 != leader2) {
				leaders[leader1] = leader2;
			}
		}

		private int getLeader(int[] leaders, int tmpIndex) {
			int leader = leaders[tmpIndex];
			if (leader == leaders[leader]) {
				return leader;
			} else {
				return leaders[tmpIndex] = getLeader(leaders, leader);
			}
		}
	}

}
