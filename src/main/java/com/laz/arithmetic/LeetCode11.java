package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode11 {
	// 解码方法
	@Test
	public void test1() {
//		Assert.assertEquals(3, numDecodings("226"));
//		Assert.assertEquals(0, numDecodings("0"));
		Assert.assertEquals(0, numDecodings2("01"));
	}

	public int numDecodings2(String s) {
		int len = s.length();
		if (len == 0) {
			return 0;
		}

		// dp[i] 以 s[i] 结尾的前缀子串有多少种解码方法
		// dp[i] = dp[i - 1] * 1 if s[i] != '0'
		// dp[i] += dp[i - 2] * 1 if 10 <= int(s[i - 1..i]) <= 26
		int[] dp = new int[len];
		char[] charArray = s.toCharArray();
		if (charArray[0] == '0') {
			return 0;
		}
		dp[0] = 1;
		for (int i = 1; i < len; i++) {
			if (charArray[i] != '0') {
				dp[i] = dp[i - 1];
			}

			int num = 10 * (charArray[i - 1] - '0') + (charArray[i] - '0');
			if (num >= 10 && num <= 26) {
				if (i == 1) {
					dp[i]++;
				} else {
					dp[i] += dp[i - 2];
				}
			}
		}
		return dp[len - 1];
	}

	// 回溯算法超时
	public int numDecodings(String s) {
		if (s == null) {
			return 0;
		}
		List<List<String>> ret = new ArrayList<List<String>>();

		backtrack(ret, new ArrayList<String>(), 0, s);
		return ret.size();
	}

	private void backtrack(List<List<String>> ret, List<String> list, int start, String s) {
		int len = s.length();
		if (start < len) {
			String str = s.substring(start, start + 1);
			if (Integer.parseInt(str) > 0) {
				list.add(str);
				backtrack(ret, list, start + 1, s);
			}
		}
		if (start + 1 < len) {
			String str = s.substring(start, start + 2);
			if (Integer.parseInt(str) <= 26 && Integer.parseInt(str) >= 10) {
				list.add(str);
				backtrack(ret, list, start + 2, s);
			}
		}
		if (start >= len) {
			ret.add(new ArrayList<String>(list));
		}
	}

	@Test
	// 回文子串
	public void test2() {
		Assert.assertEquals(6, new Solution2().countSubstrings("aaa"));
		Assert.assertEquals(3, new Solution2().countSubstrings("abc"));
	}

	class Solution2 {
		public int countSubstrings(String s) {
			int n = s.length(), ans = 0;
			for (int i = 0; i < 2 * n - 1; ++i) {
				int l = i / 2, r = i / 2 + i % 2;
				while (l >= 0 && r < n && s.charAt(l) == s.charAt(r)) {
					--l;
					++r;
					++ans;
				}
			}
			return ans;
		}
	}

	// 反转链表 II
	@Test
	public void test3() {
		ListNode head = Utils.createListNode(new Integer[] { 1, 2, 3, 4, 5 });
		ListNode node = reverseBetween(head, 2, 4);
		Utils.printListNode(node);
	}

	public ListNode reverseBetween(ListNode head, int m, int n) {
		// Empty list
		if (head == null) {
			return null;
		}

		// Move the two pointers until they reach the proper starting point
		// in the list.
		ListNode cur = head, prev = null;
		while (m > 1) {
			prev = cur;
			cur = cur.next;
			m--;
			n--;
		}

		// The two pointers that will fix the final connections.
		ListNode con = prev, tail = cur;

		// Iteratively reverse the nodes until n becomes 0.
		ListNode third = null;
		while (n > 0) {
			third = cur.next;
			cur.next = prev;
			prev = cur;
			cur = third;
			n--;
		}

		// Adjust the final connections as explained in the algorithm
		if (con != null) {
			con.next = prev;
		} else {
			head = prev;
		}

		tail.next = cur;
		return head;
	}

	// 扫雷游戏
	@Test
	public void test4() {
//		char[][] board = new char[][] { { 'E', 'E', 'E', 'E', 'E' }, { 'E', 'E', 'M', 'E', 'E' },
//				{ 'E', 'E', 'E', 'E', 'E' }, { 'E', 'E', 'E', 'E', 'E' } };
//		int[] click = new int[] { 3, 0 };

		char[][] board = new char[][] { { 'B', '1', 'E', '1', 'B' }, { 'B', '1', 'M', '1', 'B' },
				{ 'B', '1', '1', '1', 'B' }, { 'B', 'B', 'B', 'B', 'B' } };
		int[] click = new int[] { 1, 2 };
		char[][] ret = updateBoard(board, click);
		for (char[] cs : ret) {
			for (char c : cs) {
				System.out.print(c + "");
			}
			System.out.println();
		}
	}

	public char[][] updateBoard(char[][] board, int[] click) {
		int m = board.length;
		int n = board[0].length;
		char c = board[click[0]][click[1]];
		boolean[][] visited = new boolean[m][n];
		if (c == 'M') {
			board[click[0]][click[1]] = 'X';
			return board;
		}
		dfs(board, visited, click[0], click[1]);
		return board;
	}

	private void dfs(char[][] board, boolean[][] visited, int i, int j) {
		int m = board.length;
		int n = board[0].length;
		if (i < 0 || i >= m) {
			return;
		}
		if (j < 0 || j >= n) {
			return;
		}
		if (visited[i][j]) {
			return;
		}
		char c = board[i][j];
		if (c == 'M') {
			board[i][i] = 'X';
			return;
		}
		if (c == 'E') {
			// 判断周围是否度没有雷
			int[][] v = new int[][] { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 }, { 1, 1 }, { -1, -1 }, { -1, 1 },
					{ 1, -1 } };
			int count = 0;
			for (int k = 0; k < v.length; k++) {
				int row = i + v[k][0];
				int col = j + v[k][1];
				if ((row >= 0 && row < m) && (col >= 0 && col < n)) {
					if (board[row][col] == 'M') {
						count++;
					}
				}
			}
			if (count == 0) {
				board[i][j] = 'B';
				visited[i][j] = true;
				// 递归点击四个方向的方块
				for (int k = 0; k < v.length; k++) {
					int row = i + v[k][0];
					int col = j + v[k][1];
					dfs(board, visited, row, col);
				}
			} else {
				board[i][j] = Character.forDigit(count, 10);
			}
		}

	}

	// 二叉树的最小深度
	@Test
	public void test5() {
//		TreeNode root = Utils.createTree(new Integer[] {3,9,20,null,null,15,7});
//		Assert.assertEquals(2, minDepth(root));

//		TreeNode root2 = Utils.createTree(new Integer[] {1,2});
//		Assert.assertEquals(2, minDepth(root2));

		TreeNode root3 = Utils.createTree(new Integer[] { 1, 2, 3, 4, null, null, 5 });
		Assert.assertEquals(3, minDepth(root3));
	}

	public int minDepth(TreeNode root) {
		if (root == null) {
			return 0;
		}
		if (root.left == null && root.right == null) {
			return 1;
		}
		int min_depth = Integer.MAX_VALUE;
		if (root.left != null) {
			min_depth = Math.min(minDepth(root.left), min_depth);
		}
		if (root.right != null) {
			min_depth = Math.min(minDepth(root.right), min_depth);
		}

		return min_depth + 1;
	}

	// 24 点游戏
	@Test
	public void test6() {
		Assert.assertEquals(true, new Solution6().judgePoint24(new int[] { 4, 1, 8, 7 }));
	}

	class Solution6 {
		static final int TARGET = 24;
		static final double EPSILON = 1e-6;
		static final int ADD = 0, MULTIPLY = 1, SUBTRACT = 2, DIVIDE = 3;

		public boolean judgePoint24(int[] nums) {
			List<Double> list = new ArrayList<Double>();
			for (int num : nums) {
				list.add((double) num);
			}
			return solve(list);
		}

		public boolean solve(List<Double> list) {
			if (list.size() == 0) {
				return false;
			}
			if (list.size() == 1) {
				return Math.abs(list.get(0) - TARGET) < EPSILON;
			}
			int size = list.size();
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (i != j) {
						List<Double> list2 = new ArrayList<Double>();
						for (int k = 0; k < size; k++) {
							if (k != i && k != j) {
								list2.add(list.get(k));
							}
						}
						for (int k = 0; k < 4; k++) {
							if (k < 2 && i > j) {
								continue;
							}
							if (k == ADD) {
								list2.add(list.get(i) + list.get(j));
							} else if (k == MULTIPLY) {
								list2.add(list.get(i) * list.get(j));
							} else if (k == SUBTRACT) {
								list2.add(list.get(i) - list.get(j));
							} else if (k == DIVIDE) {
								if (Math.abs(list.get(j)) < EPSILON) {
									continue;
								} else {
									list2.add(list.get(i) / list.get(j));
								}
							}
							if (solve(list2)) {
								return true;
							}
							list2.remove(list2.size() - 1);
						}
					}
				}
			}
			return false;
		}
	}

	// 在 D 天内送达包裹的能力
	@Test
	public void test7() {
		Assert.assertEquals(15, shipWithinDays(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, 5));
	}

	// https://leetcode-cn.com/problems/capacity-to-ship-packages-within-d-days/solution/zai-dtian-nei-song-da-bao-guo-de-neng-li-by-lenn12/
	public int shipWithinDays(int[] weights, int D) {
		int lo = 0, hi = 0;
		for (int i = 0; i < weights.length; i++) {
			hi += weights[i];
		}
		while (lo < hi) {
			int mid = lo + (hi - lo) / 2;
			if (canShip(weights, D, mid)) {
				hi = mid;
			} else {
				lo = mid + 1;
			}
		}
		return lo;
	}

	private boolean canShip(int[] weights, int D, int K) {
		int cur = K; // cur 表示当前船的可用承载量
		for (int weight : weights) {
			if (weight > K)
				return false;
			if (cur < weight) {
				cur = K;
				D--;
			}
			cur -= weight;
		}
		return D > 0; // 能否在D天内运完
	}

	// 数字范围按位与
	@Test
	public void test8() {
		Assert.assertEquals(4, rangeBitwiseAnd(5, 7));
	}

	public int rangeBitwiseAnd(int m, int n) {
		int shift = 0;
		// 找到公共前缀
		while (m < n) {
			m >>= 1;
			n >>= 1;
			++shift;
		}
		return m << shift;
	}

	// 最长公共子序列
	@Test
	public void test9() {
		Assert.assertEquals(3, longestCommonSubsequence("abcde", "ace"));
	}

	public int longestCommonSubsequence(String text1, String text2) {
		char[] t1 = text1.toCharArray();
		char[] t2 = text2.toCharArray();
		int length1 = t1.length;
		int length2 = t2.length;
		// dp表示s1[1..]和s2[1..]最长子序列 ,默认为0，所以dp[0]情况不用初始化
		int[][] dp = new int[length1 + 1][length2 + 1];
		for (int i = 1; i < length1 + 1; i++) {
			for (int j = 1; j < length2 + 1; j++) {
				if (t1[i - 1] == t2[j - 1]) {
					// 这边找到一个 lcs 的元素，继续往前找
					dp[i][j] = 1 + dp[i - 1][j - 1];
				} else {
					// 谁能让 lcs 最长，就听谁的
					dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
				}
			}
		}
		return dp[length1][length2];
	}

	// 递增子序列
	@Test
	public void test10() {
		List<List<Integer>> ret = findSubsequences(new int[] { 4, 6, 7, 7, 3 });
		for (List<Integer> list : ret) {
			System.out.println(Joiner.on(",").join(list));
		}
	}

	List<Integer> temp = new ArrayList<Integer>();
	List<List<Integer>> ans = new ArrayList<List<Integer>>();

	public List<List<Integer>> findSubsequences(int[] nums) {
		dfs(0, Integer.MIN_VALUE, nums);
		return ans;
	}

	public void dfs(int cur, int last, int[] nums) {
		if (cur == nums.length) {
			if (temp.size() >= 2) {
				ans.add(new ArrayList<Integer>(temp));
			}
			return;
		}
		if (nums[cur] >= last) {
			temp.add(nums[cur]);
			dfs(cur + 1, nums[cur], nums);
			temp.remove(temp.size() - 1);
		}
		if (nums[cur] != last) {
			dfs(cur + 1, last, nums);
		}
	}

	// 最长上升子序列
	@Test
	public void test11() {
		Assert.assertEquals(4, new Solution11().lengthOfLIS(new int[] { 10, 9, 2, 5, 3, 7, 101, 18 }));
		Assert.assertEquals(1, new Solution11().lengthOfLIS(new int[] { 2, 2 }));
	}

	class Solution11 {
		public int lengthOfLIS(int[] nums) {
			if (nums.length == 0) {
				return 0;
			}
			// dp[i]dp[i] 为考虑前 ii 个元素，以第 ii 个数字结尾的最长上升子序列的长度
			int[] dp = new int[nums.length];
			dp[0] = 1;
			int maxans = 1;
			for (int i = 1; i < dp.length; i++) {
				int maxval = 0;
				for (int j = 0; j < i; j++) {
					if (nums[i] > nums[j]) {
						maxval = Math.max(maxval, dp[j]);
					}
				}
				dp[i] = maxval + 1;
				maxans = Math.max(maxans, dp[i]);
			}
			return maxans;
		}

	}

	// 重新安排行程
	@Test
	public void test12() {
		List<List<String>> tickets = new ArrayList<List<String>>();
		tickets.add(Arrays.asList("JFK", "NRT"));
		tickets.add(Arrays.asList("JFK", "KUL"));
		tickets.add(Arrays.asList("KUL", "JFK"));
		List<String> ret = findItinerary(tickets);
		System.out.println(Joiner.on(",").join(ret));
	}

	// https://leetcode-cn.com/problems/reconstruct-itinerary/solution/javadfsjie-fa-by-pwrliang/
	public List<String> findItinerary(List<List<String>> tickets) {
		// 因为逆序插入，所以用链表
		List<String> ans = new LinkedList<>();
		if (tickets == null || tickets.size() == 0)
			return ans;

		Map<String, PriorityQueue<String>> graph = new HashMap<>();

		for (List<String> pair : tickets) {
			// 因为涉及删除操作，我们用链表
			PriorityQueue<String> nbr = graph.computeIfAbsent(pair.get(0), k -> new PriorityQueue<>());
			nbr.add(pair.get(1));
		}

		// 按目的顶点排序

		visit(graph, "JFK", ans);

		return ans;
	}

	// DFS方式遍历图，当走到不能走为止，再将节点加入到答案
	private void visit(Map<String, PriorityQueue<String>> graph, String src, List<String> ans) {
		LinkedList<String> stack = new LinkedList<>();
		stack.push(src);
		while (!stack.isEmpty()) {
			PriorityQueue<String> nbr;

			while ((nbr = graph.get(stack.peek())) != null && nbr.size() > 0) {
				stack.push(nbr.poll());
			}
			ans.add(0, stack.pop());
		}
	}

	// 机器人能否返回原点
	@Test
	public void test13() {
		Assert.assertEquals(true, judgeCircle("UD"));
		Assert.assertEquals(false, judgeCircle("LL"));
	}

	public boolean judgeCircle(String moves) {
		if (moves == null || moves.length() == 0) {
			return true;
		}
		int[] start = new int[] { 0, 0 };
		for (int i = 0; i < moves.length(); i++) {
			char c = moves.charAt(i);
			if (c == 'U') {
				start[0] = start[0] - 1;
			}
			if (c == 'D') {
				start[0] = start[0] + 1;
			}
			if (c == 'L') {
				start[1] = start[1] - 1;
			}
			if (c == 'R') {
				start[1] = start[1] + 1;
			}
		}
		return start[0] == 0 && start[1] == 0;
	}

	// 最短回文串
	@Test
	public void test14() {
		Assert.assertEquals("aaacecaaa", shortestPalindrome("aacecaaa"));
	}

	public String shortestPalindrome(String s) {
		int n = s.length();
		int[] fail = new int[n];
		Arrays.fill(fail, -1);
		for (int i = 1; i < n; ++i) {
			int j = fail[i - 1];
			while (j != -1 && s.charAt(j + 1) != s.charAt(i)) {
				j = fail[j];
			}
			if (s.charAt(j + 1) == s.charAt(i)) {
				fail[i] = j + 1;
			}
		}
		int best = -1;
		for (int i = n - 1; i >= 0; --i) {
			while (best != -1 && s.charAt(best + 1) != s.charAt(i)) {
				best = fail[best];
			}
			if (s.charAt(best + 1) == s.charAt(i)) {
				++best;
			}
		}
		String add = (best == n - 1 ? "" : s.substring(best + 1));
		StringBuffer ans = new StringBuffer(add).reverse();
		ans.append(s);
		return ans.toString();
	}

	// 灯泡开关
	@Test
	public void test15() {
//		Assert.assertEquals(1, bulbSwitch(3));
//		Assert.assertEquals(2, bulbSwitch(4));
		Assert.assertEquals(2, bulbSwitch(6));
		Assert.assertEquals(999, bulbSwitch(999999));
	}
	//https://leetcode-cn.com/problems/bulb-switcher/solution/bu-jiu-shi-qiu-ping-fang-gen-ma-by-ivan1/
	public int bulbSwitch(int n) {
		return (int) Math.sqrt(n);
	}
	
	public int bulbSwitch2(int n) {
		int[] arr = new int[n];
		int t = 1;
		for (int i = 1; i <= n; i++) {
			for (int j = t-1; j < n; j = j + t) {
				arr[j] = arr[j] == 0?1:0;
			}
			t++;
		}
		int count = 0;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == 1) {
				count++;
			}
		}
		return count;
	}
}
