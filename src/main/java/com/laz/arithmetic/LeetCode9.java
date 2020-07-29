package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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

	// 思路：https://leetcode-cn.com/problems/permutation-sequence/solution/pai-lie-zu-he-zhi-di-kge-pai-lie-golden-monkey-by-/
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

	// 判断子序列
	@Test
	public void test5() {
		Assert.assertEquals(true, isSubsequence("abc", "ahbgdc"));
		Assert.assertEquals(false, isSubsequence("axc", "ahbgdc"));
		Assert.assertEquals(false, isSubsequence("dd", ""));
	}

	public boolean isSubsequence(String s, String t) {
		int j = 0;
		int i = 0;
		while (j < t.length() && i < s.length()) {
			if (s.charAt(i) == t.charAt(j)) {
				i++;
				if (i == s.length()) {
					return true;
				}
			}
			j++;
		}
		if (i == s.length()) {
			return true;
		}
		return false;
	}

	// 旋转链表
	@Test
	public void test6() {
		ListNode list = Utils.createListNode(new Integer[] { 0, 1, 2 });
		ListNode newList = rotateRight(list, 3);
		Utils.printListNode(newList);
	}

	public ListNode rotateRight(ListNode head, int k) {
		// 获取list长度
		ListNode temp = head;
		int count = 0;
		while (temp != null) {
			temp = temp.next;
			count++;
		}
		if (count == 0) {
			return head;
		}
		// 能够反转的数
		int v = k % count;
		temp = head;
		int len = 0;
		ListNode reveseList = null;
		while (temp != null) {
			len++;
			if (count - len == v && temp != null) {
				// 反转此后的数据
				reveseList = temp.next;
				temp.next = null;
				break;
			}
			temp = temp.next;
		}
		// 另后面的数据作为头部，链接前面的数据
		if (reveseList != null) {
			ListNode newHead = reveseList;
			while (reveseList.next != null) {
				reveseList = reveseList.next;
			}
			reveseList.next = head;
			return newHead;
		}
		return head;
	}

	// 不同路径
	@Test
	public void test7() {
		Assert.assertEquals(3, uniquePaths(3, 2));
	}

	public int uniquePaths(int m, int n) {
		// 动态规划dp[i][j]是到达 i, j 最多路径
		int[][] dp = new int[m][n];
		for (int i = 0; i < m; i++) {
			dp[i][0] = 1;
		}
		for (int i = 0; i < n; i++) {
			dp[0][i] = 1;
		}
		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
			}
		}
		return dp[m - 1][n - 1];
	}

	// 有效数字
	@Test
	public void test8() {
		Assert.assertEquals(true, isNumber("-90e3"));
	}

	/**
	 * 
	 * 先设定numSeen，dotSeen和eSeen三种boolean变量，分别代表是否出现数字、点和E 然后遍历目标字符串 1.判断是否属于数字的0~9区间
	 * 2.遇到点的时候，判断前面是否有点或者E，都需要return false
	 * 3.遇到E的时候，判断前面数字是否合理，是否有E，并把numSeen置为false，防止E后无数字
	 * 4.遇到-+的时候，判断是否是第一个，如果不是第一个判断是否在E后面，都不满足则return false 5.其他情况都为false
	 * 
	 * @param s
	 * @return
	 */
	public boolean isNumber(String s) {
		if (s == null || s.length() == 0)
			return false;
		boolean numSeen = false;
		boolean dotSeen = false;
		boolean eSeen = false;
		char arr[] = s.trim().toCharArray();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] >= '0' && arr[i] <= '9') {
				numSeen = true;
			} else if (arr[i] == '.') {
				if (dotSeen || eSeen) {
					return false;
				}
				dotSeen = true;
			} else if (arr[i] == 'E' || arr[i] == 'e') {
				if (eSeen || !numSeen) {
					return false;
				}
				eSeen = true;
				numSeen = false;
			} else if (arr[i] == '+' || arr[i] == '-') {
				if (i != 0 && arr[i - 1] != 'e' && arr[i - 1] != 'E') {
					return false;
				}
			} else {
				return false;
			}
		}
		return numSeen;
	}

	// 寻宝
	@Test
	public void test9() {
		Assert.assertEquals(-1, minimalSteps(new String[] { "S#O", "M.#", "M.T" }));
	}

	int[] dx = { 1, -1, 0, 0 };
	int[] dy = { 0, 0, 1, -1 };
	int n, m;

	public int minimalSteps(String[] maze) {
		n = maze.length;
		m = maze[0].length();
		// 机关 & 石头
		List<int[]> buttons = new ArrayList<int[]>();
		List<int[]> stones = new ArrayList<int[]>();
		// 起点 & 终点
		int sx = -1, sy = -1, tx = -1, ty = -1;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				if (maze[i].charAt(j) == 'M') {
					buttons.add(new int[] { i, j });
				}
				if (maze[i].charAt(j) == 'O') {
					stones.add(new int[] { i, j });
				}
				if (maze[i].charAt(j) == 'S') {
					sx = i;
					sy = j;
				}
				if (maze[i].charAt(j) == 'T') {
					tx = i;
					ty = j;
				}
			}
		}
		int nb = buttons.size();
		int ns = stones.size();
		int[][] startDist = bfs(sx, sy, maze);

		// 边界情况：没有机关
		if (nb == 0) {
			return startDist[tx][ty];
		}
		// 从某个机关到其他机关 / 起点与终点的最短距离。
		int[][] dist = new int[nb][nb + 2];
		for (int i = 0; i < nb; i++) {
			Arrays.fill(dist[i], -1);
		}
		// 中间结果
		int[][][] dd = new int[nb][][];
		for (int i = 0; i < nb; i++) {
			int[][] d = bfs(buttons.get(i)[0], buttons.get(i)[1], maze);
			dd[i] = d;
			// 从某个点到终点不需要拿石头
			dist[i][nb + 1] = d[tx][ty];
		}

		for (int i = 0; i < nb; i++) {
			int tmp = -1;
			for (int k = 0; k < ns; k++) {
				int midX = stones.get(k)[0], midY = stones.get(k)[1];
				if (dd[i][midX][midY] != -1 && startDist[midX][midY] != -1) {
					if (tmp == -1 || tmp > dd[i][midX][midY] + startDist[midX][midY]) {
						tmp = dd[i][midX][midY] + startDist[midX][midY];
					}
				}
			}
			dist[i][nb] = tmp;
			for (int j = i + 1; j < nb; j++) {
				int mn = -1;
				for (int k = 0; k < ns; k++) {
					int midX = stones.get(k)[0], midY = stones.get(k)[1];
					if (dd[i][midX][midY] != -1 && dd[j][midX][midY] != -1) {
						if (mn == -1 || mn > dd[i][midX][midY] + dd[j][midX][midY]) {
							mn = dd[i][midX][midY] + dd[j][midX][midY];
						}
					}
				}
				dist[i][j] = mn;
				dist[j][i] = mn;
			}
		}

		// 无法达成的情形
		for (int i = 0; i < nb; i++) {
			if (dist[i][nb] == -1 || dist[i][nb + 1] == -1) {
				return -1;
			}
		}

		// dp 数组， -1 代表没有遍历到
		int[][] dp = new int[1 << nb][nb];
		for (int i = 0; i < 1 << nb; i++) {
			Arrays.fill(dp[i], -1);
		}
		for (int i = 0; i < nb; i++) {
			dp[1 << i][i] = dist[i][nb];
		}

		// 由于更新的状态都比未更新的大，所以直接从小到大遍历即可
		for (int mask = 1; mask < (1 << nb); mask++) {
			for (int i = 0; i < nb; i++) {
				// 当前 dp 是合法的
				if ((mask & (1 << i)) != 0) {
					for (int j = 0; j < nb; j++) {
						// j 不在 mask 里
						if ((mask & (1 << j)) == 0) {
							int next = mask | (1 << j);
							if (dp[next][j] == -1 || dp[next][j] > dp[mask][i] + dist[i][j]) {
								dp[next][j] = dp[mask][i] + dist[i][j];
							}
						}
					}
				}
			}
		}

		int ret = -1;
		int finalMask = (1 << nb) - 1;
		for (int i = 0; i < nb; i++) {
			if (ret == -1 || ret > dp[finalMask][i] + dist[i][nb + 1]) {
				ret = dp[finalMask][i] + dist[i][nb + 1];
			}
		}

		return ret;
	}

	public int[][] bfs(int x, int y, String[] maze) {
		int[][] ret = new int[n][m];
		for (int i = 0; i < n; i++) {
			Arrays.fill(ret[i], -1);
		}
		ret[x][y] = 0;
		Queue<int[]> queue = new LinkedList<int[]>();
		queue.offer(new int[] { x, y });
		while (!queue.isEmpty()) {
			int[] p = queue.poll();
			int curx = p[0], cury = p[1];
			for (int k = 0; k < 4; k++) {
				int nx = curx + dx[k], ny = cury + dy[k];
				if (inBound(nx, ny) && maze[nx].charAt(ny) != '#' && ret[nx][ny] == -1) {
					ret[nx][ny] = ret[curx][cury] + 1;
					queue.offer(new int[] { nx, ny });
				}
			}
		}
		return ret;
	}

	public boolean inBound(int x, int y) {
		return x >= 0 && x < n && y >= 0 && y < m;
	}

	// 简化路径
	@Test
	public void test10() {
		Assert.assertEquals("/", simplifyPath("/"));
		Assert.assertEquals("/", simplifyPath("/../../../"));
		Assert.assertEquals("/home/foo", simplifyPath("/home//foo/"));
		Assert.assertEquals("/c", simplifyPath("/a/./b/../../c/"));
		Assert.assertEquals("/a/b/c", simplifyPath("/a//b////c/d//././/.."));
		Assert.assertEquals("/c", simplifyPath("/a/../../b/../c//.//"));
	}

	public String simplifyPath(String path) {
		if (path ==null || path.length()==0) {
			return path;
		}
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		//先将字符串拆成/xx /. /..模式
		for (int i=0;i<path.length();i++) {
			if (path.charAt(i) == '/') {
				if (sb.length()>0) {
					list.add(sb.toString());
				}
				sb = new StringBuffer();
			}
			sb.append(path.charAt(i));
		}
		if (sb.length()>0) {
			list.add(sb.toString());
		}
		Deque<String> stack = new LinkedList<String>();
		for (String p : list) {
			stack.push(p);
			if (p.equals("/..")) {
				//移除两位
				stack.pop();
				if (stack.peek()!=null) {
					stack.pop();
				}
			}
			if (p.equals("/.")) {
				//移除一位
				stack.pop();
			}
			if (p.equals("/")) {
				//移除一位
				stack.pop();
			}
		}
		if (stack.size() == 0) {
			return "/";
		}
		String str = "";
		while (stack.peek()!=null) {
			str=stack.pop()+str;
		}
		return str.toString();
	}
}
