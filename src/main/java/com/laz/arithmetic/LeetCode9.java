package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
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
//		int[][] intervals = new int[][] { { 1, 3 }, { 6, 9 } };
//		int[] newInterval = new int[] { 2, 5 };
//		int[][] res = insert(intervals, newInterval);
//		for (int[] is : res) {
//			for (int r : is) {
//				System.out.print(r);
//			}
//			System.out.println();
//		}
		
		Assert.assertArrayEquals(new int[][] {
				{1,2},{3,10},{12,16}
		}, insert(new int[][] {
			{1,2},{3,5},{6,7},{8,10},{12,16}
		},new int[] {4,8}));
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
		if (path == null || path.length() == 0) {
			return path;
		}
		List<String> list = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		// 先将字符串拆成/xx /. /..模式
		for (int i = 0; i < path.length(); i++) {
			if (path.charAt(i) == '/') {
				if (sb.length() > 0) {
					list.add(sb.toString());
				}
				sb = new StringBuffer();
			}
			sb.append(path.charAt(i));
		}
		if (sb.length() > 0) {
			list.add(sb.toString());
		}
		Deque<String> stack = new LinkedList<String>();
		for (String p : list) {
			stack.push(p);
			if (p.equals("/..")) {
				// 移除两位
				stack.pop();
				if (stack.peek() != null) {
					stack.pop();
				}
			}
			if (p.equals("/.")) {
				// 移除一位
				stack.pop();
			}
			if (p.equals("/")) {
				// 移除一位
				stack.pop();
			}
		}
		if (stack.size() == 0) {
			return "/";
		}
		String str = "";
		while (stack.peek() != null) {
			str = stack.pop() + str;
		}
		return str.toString();
	}

	// 文本左右对齐
	@Test
	public void test11() {
		String[] words = new String[] { "Science", "is", "what", "we", "understand", "well", "enough", "to", "explain",
				"to", "a", "computer.", "Art", "is", "everything", "else", "we", "do" };
		List<String> rows = fullJustify(words, 20);
		for (String str : rows) {
			System.out.println(str);
		}
	}

	public List<String> fullJustify(String[] words, int maxWidth) {
		List<String> res = new ArrayList<String>();
		int count = 0;
		Deque<String> queue = new LinkedList<String>();
		for (int i = 0; i < words.length; i++) {
			// 每行每个单词书写有固定空格,除最后一个
			queue.offer(words[i]);
			count += words[i].length() + 1;
			if (count > maxWidth + 1) {
				// 先移除最后加入的
				queue.removeLast();
				// 加入一行，计算额外空格数
				int spaceNum = maxWidth - count + words[i].length() + 2;
				// 间隙数
				int spaceCount = queue.size() - 1;
				StringBuilder sb = new StringBuilder();
				// 本行只有一个单词
				if (spaceCount == 0) {
					sb.append(queue.poll() + getSpace(spaceNum));
					res.add(sb.toString());
				} else {
					// 获取余数和除后结果
					int remainder = spaceNum % (spaceCount);
					int r = spaceNum / spaceCount;

					while (queue.peek() != null && queue.size() > 1) {
						sb.append(queue.poll() + " ");
						int spaceN = r;
						if (remainder > 0) {
							spaceN = spaceN + 1;
							remainder--;
						}
						sb.append(getSpace(spaceN));
					}
					sb.append(queue.poll());
					res.add(sb.toString());
				}
				queue.offer(words[i]);
				count = words[i].length() + 1;
			}
		}
		// 最后一行
		if (queue.peek() != null) {
			StringBuilder sb = new StringBuilder();
			int c = 0;
			while (queue.peek() != null && queue.size() > 1) {
				String str = queue.poll();
				c = c + str.length() + 1;
				sb.append(str + " ");
			}
			String str = queue.poll();
			sb.append(str);
			c += str.length();
			sb.append(getSpace(maxWidth - c));
			res.add(sb.toString());
		}
		return res;
	}

	private String getSpace(int spaceN) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= spaceN; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	// N皇后 II
	@Test
	public void test12() {
		Assert.assertEquals(2, new QueenSolution().totalNQueens(4));
	}

	class QueenSolution {
		private List<List<String>> output = new ArrayList<>();

		// 用于标记是否被列方向的皇后被攻击
		int[] rows;
		// 用于标记是否被主对角线方向的皇后攻击
		int[] mains;
		// 用于标记是否被次对角线方向的皇后攻击
		int[] secondary;
		// 用于存储皇后放置的位置
		int[] queens;

		int n;

		public int totalNQueens(int n) {
			// 初始化
			rows = new int[n];
			mains = new int[2 * n - 1];
			secondary = new int[2 * n - 1];
			queens = new int[n];
			this.n = n;
			// 从第一行开始回溯求解 N 皇后
			backtrack(0);
			return output.size();
		}

		// 在一行中放置一个皇后
		private void backtrack(int row) {
			if (row >= n)
				return;
			// 分别尝试在 row 行中的每一列中放置皇后
			for (int col = 0; col < n; col++) {
				// 判断当前放置的皇后是否不被其他皇后的攻击
				if (isNotUnderAttack(row, col)) {
					// 选择，在当前的位置上放置皇后
					placeQueen(row, col);
					// 当当前行是最后一行，则找到了一个解决方案
					if (row == n - 1) {
						addSolution();
					} else {
						// 在下一行中放置皇后
						backtrack(row + 1);
					}
					// 撤销，回溯，即将当前位置的皇后去掉
					removeQueen(row, col);
				}
			}
		}

		// 判断 row 行，col 列这个位置有没有被其他方向的皇后攻击
		private boolean isNotUnderAttack(int row, int col) {
			// 判断的逻辑是：
			// 1. 当前位置的这一列方向没有皇后攻击
			// 2. 当前位置的主对角线方向没有皇后攻击
			// 3. 当前位置的次对角线方向没有皇后攻击
			// row-col已经可以代表主对角线，但是为了防止数组越界加了n-1
			int res = rows[col] + mains[row - col + n - 1] + secondary[row + col];
			// 如果三个方向都没有攻击的话，则 res = 0，即当前位置不被任何的皇后攻击
			return res == 0;
		}

		// 在指定的位置上放置皇后
		private void placeQueen(int row, int col) {
			// 在 row 行，col 列 放置皇后
			queens[row] = col;
			// 当前位置的列方向已经有皇后了
			rows[col] = 1;
			// 当前位置的主对角线方向已经有皇后了
			mains[row - col + n - 1] = 1;
			// 当前位置的次对角线方向已经有皇后了
			secondary[row + col] = 1;
		}

		// 移除指定位置上的皇后
		private void removeQueen(int row, int col) {
			// 移除 row 行上的皇后
			queens[row] = 0;
			// 当前位置的列方向没有皇后了
			rows[col] = 0;
			// 当前位置的主对角线方向没有皇后了
			mains[row - col + n - 1] = 0;
			// 当前位置的次对角线方向没有皇后了
			secondary[row + col] = 0;
		}

		/**
		 * 将满足条件的皇后位置放入output中
		 */
		public void addSolution() {
			List<String> solution = new ArrayList<String>();
			for (int i = 0; i < n; ++i) {
				int col = queens[i];
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < col; ++j)
					sb.append(".");
				sb.append("Q");
				for (int j = 0; j < n - col - 1; ++j)
					sb.append(".");
				solution.add(sb.toString());
			}
			output.add(solution);
		}

	}

	// 最小区间
	@Test
	public void test13() {
		List<List<Integer>> nums = new ArrayList<List<Integer>>();
		{
			List<Integer> num = new ArrayList<Integer>();
			num.add(4);
			num.add(10);
			num.add(15);
			num.add(24);
			num.add(26);
			nums.add(num);
		}
		{
			List<Integer> num = new ArrayList<Integer>();
			num.add(0);
			num.add(9);
			num.add(12);
			num.add(20);
			nums.add(num);
		}
		{
			List<Integer> num = new ArrayList<Integer>();
			num.add(5);
			num.add(18);
			num.add(22);
			num.add(30);
			nums.add(num);
		}
		int[] res = smallestRange(nums);
		for (int i : res) {
			System.out.print(i + " ");
		}
		System.out.println();
	}

	public int[] smallestRange(List<List<Integer>> nums) {
		int rangeLeft = 0, rangeRight = Integer.MAX_VALUE;
		int minRange = rangeRight - rangeLeft;
		int max = Integer.MIN_VALUE;
		int size = nums.size();
		int[] next = new int[size];
		PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				return nums.get(o1).get(next[o1]) - nums.get(o2).get(next[o2]);
			}
		});
		for (int i = 0; i < size; i++) {
			priorityQueue.offer(i);
			max = Math.max(max, nums.get(i).get(0));
		}
		while (true) {
			int minIndex = priorityQueue.poll();
			int curRange = max - nums.get(minIndex).get(next[minIndex]);
			if (curRange < minRange) {
				minRange = curRange;
				rangeLeft = nums.get(minIndex).get(next[minIndex]);
				rangeRight = max;
			}
			next[minIndex]++;
			if (next[minIndex] == nums.get(minIndex).size()) {
				break;
			}
			priorityQueue.offer(minIndex);
			max = Math.max(max, nums.get(minIndex).get(next[minIndex]));
		}
		return new int[] { rangeLeft, rangeRight };
	}

	// 最小覆盖子串
	@Test
	public void test14() {
		Assert.assertEquals("BANC", minWindow("ADOBECODEBANC", "ABC"));
	}

	Map<Character, Integer> ori = new HashMap<Character, Integer>();
	Map<Character, Integer> cnt = new HashMap<Character, Integer>();

	public String minWindow(String s, String t) {
		int tLen = t.length();
		for (int i = 0; i < tLen; i++) {
			char c = t.charAt(i);
			ori.put(c, ori.getOrDefault(c, 0) + 1);
		}
		int l = 0, r = -1;
		int len = Integer.MAX_VALUE, ansL = -1, ansR = -1;
		int sLen = s.length();
		while (r < sLen) {
			++r;
			if (r < sLen && ori.containsKey(s.charAt(r))) {
				cnt.put(s.charAt(r), cnt.getOrDefault(s.charAt(r), 0) + 1);
			}
			while (check() && l <= r) {
				// 是否小于上次的长度
				if (r - l + 1 < len) {
					len = r - l + 1;
					ansL = l;
					ansR = l + len;
				}
				if (ori.containsKey(s.charAt(l))) {
					cnt.put(s.charAt(l), cnt.getOrDefault(s.charAt(l), 0) - 1);
				}
				++l;
			}
			if (len == t.length()) {
				break;
			}
		}
		return ansL == -1 ? "" : s.substring(ansL, ansR);
	}

	/**
	 * 检查是否包含了所有字符
	 * 
	 * @return
	 */
	public boolean check() {
		Iterator iter = ori.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Character key = (Character) entry.getKey();
			Integer val = (Integer) entry.getValue();
			if (cnt.getOrDefault(key, 0) < val) {
				return false;
			}
		}
		return true;
	}

	// 二叉树展开为链表
	@Test
	public void test15() {
		TreeNode root = Utils.createTree(new Integer[] { 1, 2, 5, 3, 4, null, 6 });
		flatten(root);
		System.out.print(root.val + "->");
		while (root.right != null) {
			System.out.print(root.right.val + "->");
			root = root.right;
		}
	}

	public void flatten(TreeNode root) {
		TreeNode curr = root;
		while (curr != null) {
			if (curr.left != null) {
				TreeNode next = curr.left;
				TreeNode predecessor = next;
				while (predecessor.right != null) {
					predecessor = predecessor.right;
				}
				predecessor.right = curr.right;
				curr.left = null;
				curr.right = next;
			}
			curr = curr.right;
		}
	}

	// 编辑距离
	@Test
	public void test16() {
		Assert.assertEquals(3, minDistance("horse", "ros"));
		Assert.assertEquals(5, minDistance("intention", "execution"));
	}

	// 题解：https://leetcode-cn.com/problems/edit-distance/solution/zi-di-xiang-shang-he-zi-ding-xiang-xia-by-powcai-3/
	public int minDistance(String word1, String word2) {
		int n1 = word1.length();
		int n2 = word2.length();
		int[][] dp = new int[n1 + 1][n2 + 1];
		// 第一行
		for (int j = 1; j <= n2; j++) {
			// 做插入操作
			dp[0][j] = dp[0][j - 1] + 1;
		}
		// 第一列
		for (int i = 1; i <= n1; i++) {
			// 做删除操作
			dp[i][0] = dp[i - 1][0] + 1;
		}
		for (int i = 1; i <= n1; i++) {
			for (int j = 1; j <= n2; j++) {
				if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
					dp[i][j] = dp[i - 1][j - 1];
				} else {
					// dp[i-1][j-1] 表示替换操作，dp[i-1][j] 表示删除操作，dp[i][j-1] 表示插入操作
					dp[i][j] = Math.min(Math.min(dp[i - 1][j - 1], dp[i][j - 1]), dp[i - 1][j]) + 1;
				}
			}
		}
		return dp[n1][n2];

	}

	// 矩阵置零
	@Test
	public void test17() {
		int[][] matrix = new int[][] { { 1, 1, 1 }, { 1, 0, 1 }, { 1, 1, 1 } };
		setZeroes(matrix);
		for (int[] is : matrix) {
			for (int i : is) {
				System.out.print(i);
			}
			System.out.println();
		}
	}

	public void setZeroes(int[][] matrix) {
		if (matrix == null || matrix[0] == null) {
			return;
		}
		int m = matrix.length;
		int n = matrix[0].length;
		boolean[][] flag = new boolean[m][n];
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (matrix[i][j] == 0) {
					// 记录未0的下标
					flag[i][j] = true;
				}
			}
		}
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (flag[i][j]) {
					replaceRowCol(matrix, i, j);
				}
			}
		}
	}

	private void replaceRowCol(int[][] matrix, int row, int col) {
		int m = matrix.length;
		int n = matrix[0].length;
		for (int i = 0; i < n; i++) {
			matrix[row][i] = 0;
		}
		for (int i = 0; i < m; i++) {
			matrix[i][col] = 0;
		}
	}

	// 字符串相加
	@Test
	public void test18() {
//		Assert.assertEquals("13", addStrings("11", "2"));
//		Assert.assertEquals("107", addStrings("98", "9"));
		Assert.assertEquals("413", addStrings("408", "5"));
	}

	public String addStrings(String num1, String num2) {
		if (num1.length() < num2.length()) {
			return addStrings(num2, num1);
		}
		int carry = 0;
		StringBuilder sb = new StringBuilder();
		int num1Len = num1.length() - 1;
		int num2Len = num2.length() - 1;
		while (num2Len >= 0) {
			String value = addChar(carry, num1.charAt(num1Len--), num2.charAt(num2Len--));
			if (value.length() == 2) {
				carry = Integer.valueOf(value.substring(0, 1));
				sb.append(value.substring(1, 2));
			} else {
				carry = 0;
				sb.append(value.substring(0, 1));
			}
		}
		while (num1Len >= 0) {
			String value = addChar(carry, num1.charAt(num1Len--), '0');
			if (value.length() == 2) {
				carry = Integer.valueOf(value.substring(0, 1));
				sb.append(value.substring(1, 2));
			} else {
				carry = 0;
				sb.append(value.substring(0, 1));
			}
		}
		if (carry != 0) {
			sb.append(carry);
		}
		return sb.reverse().toString();
	}

	private String addChar(int carry, char charAt, char charAt2) {
		int v = carry + (charAt - '0') + (charAt2 - '0');
		return String.valueOf(v);
	}

	// 搜索二维矩阵
	@Test
	public void test19() {
		Assert.assertEquals(true,
				searchMatrix(new int[][] { { 1, 3, 5, 7 }, { 10, 11, 16, 20 }, { 23, 30, 34, 50 } }, 3));
		Assert.assertEquals(true, searchMatrix(new int[][] { { 1, 3 } }, 3));
	}

	public boolean searchMatrix(int[][] matrix, int target) {
		if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
			return false;
		}
		int m = matrix.length;
		int n = matrix[0].length;
		int len = m * n;
		int l = 0;
		int r = len - 1;
		while (l <= r) {
			int mid = (l + r) / 2;
			int indexR = mid / n;
			int indexC = mid % n;
			if (matrix[indexR][indexC] == target) {
				return true;
			}
			if (matrix[indexR][indexC] > target) {
				r = mid - 1;
			}
			if (matrix[indexR][indexC] < target) {
				l = mid + 1;
			}
		}
		return false;
	}

	// 课程表
	@Test
	public void test20() {
		Assert.assertEquals(false, canFinish(2, new int[][] { { 1, 0 }, { 0, 1 } }));
	}
	//https://leetcode-cn.com/problems/course-schedule/solution/course-schedule-tuo-bu-pai-xu-bfsdfsliang-chong-fa/
	public boolean canFinish(int numCourses, int[][] prerequisites) {
		List<List<Integer>> adjacency = new ArrayList<>();
		for (int i = 0; i < numCourses; i++)
			adjacency.add(new ArrayList<>());
		int[] flags = new int[numCourses];
		for (int[] cp : prerequisites)
			adjacency.get(cp[1]).add(cp[0]);
		for (int i = 0; i < numCourses; i++)
			if (!dfs(adjacency, flags, i))
				return false;
		return true;
	}

	private boolean dfs(List<List<Integer>> adjacency, int[] flags, int i) {
		if (flags[i] == 1)
			return false;
		if (flags[i] == -1)
			return true;
		flags[i] = 1;
		for (Integer j : adjacency.get(i))
			if (!dfs(adjacency, flags, j))
				return false;
		flags[i] = -1;
		return true;
	}
}
