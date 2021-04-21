package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
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
		Assert.assertEquals(1836311903, numDecodings2("111111111111111111111111111111111111111111111"));
		//Assert.assertEquals(0, new Solution1().numDecodings("111111111111111111111111111111111111111111111"));
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
	class Solution1 {
		int count = 0;
		public int numDecodings(String s) {
			if (s == null) {
				return 0;
			}
			backtrack( 0, s);
			return count;
		}
		
		private void backtrack( int start, String s) {
			int len = s.length();
			if (start < len) {
				String str = s.substring(start, start + 1);
				if (Integer.parseInt(str) > 0) {
					backtrack(start + 1, s);
				}
			}
			if (start + 1 < len) {
				String str = s.substring(start, start + 2);
				if (Integer.parseInt(str) <= 26 && Integer.parseInt(str) >= 10) {
					backtrack(start + 2, s);
				}
			}
			if (start >= len) {
				count++;
			}
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

	// https://leetcode-cn.com/problems/bulb-switcher/solution/bu-jiu-shi-qiu-ping-fang-gen-ma-by-ivan1/
	public int bulbSwitch(int n) {
		return (int) Math.sqrt(n);
	}

	public int bulbSwitch2(int n) {
		int[] arr = new int[n];
		int t = 1;
		for (int i = 1; i <= n; i++) {
			for (int j = t - 1; j < n; j = j + t) {
				arr[j] = arr[j] == 0 ? 1 : 0;
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

	// 反转字符串中的单词 III
	@Test
	public void test16() {
		Assert.assertEquals("s'teL ekat edoCteeL tsetnoc", reverseWords("Let's take LeetCode contest"));
	}

	public String reverseWords(String s) {
		if (s == null || s.length() == 0) {
			return s;
		}
		Deque<Character> stack = new LinkedList<Character>();
		int count = 0;
		StringBuilder sb = new StringBuilder();
		while (count < s.length()) {
			char c = s.charAt(count);
			if (c == ' ') {
				while (!stack.isEmpty()) {
					sb.append(stack.pop());
				}
				sb.append(c);
			} else {
				stack.push(c);
			}
			count++;
		}
		while (!stack.isEmpty()) {
			sb.append(stack.pop());
		}
		return sb.toString();
	}

	// 零钱兑换
	@Test
	public void test17() {
		Assert.assertEquals(3, coinChange(new int[] { 1, 2, 5 }, 11));
		Assert.assertEquals(3, coinChange(new int[] { 2 }, 3));
	}

	public int coinChange(int[] coins, int amount) {
		int max = amount + 1;
		// dp为组成金额 i所需最少的硬币数量
		int[] dp = new int[amount + 1];
		Arrays.fill(dp, max);
		dp[0] = 0;
		for (int i = 1; i <= amount; i++) {
			for (int j = 0; j < coins.length; j++) {
				if (coins[j] <= i) {
					dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
				}
			}
		}
		return dp[amount] > amount ? -1 : dp[amount];
	}

	// 钥匙和房间
	@Test
	public void test18() {
//		{
//			List<List<Integer>> rooms = new ArrayList<List<Integer>>();
//			rooms.add(Arrays.asList(1,3));
//			rooms.add(Arrays.asList(3,0,1));
//			rooms.add(Arrays.asList(2));
//			rooms.add(Arrays.asList(0));
//			Assert.assertEquals(false, canVisitAllRooms(rooms));
//		}
		{
			List<List<Integer>> rooms = new ArrayList<List<Integer>>();
			rooms.add(Arrays.asList(1));
			rooms.add(Arrays.asList(2));
			rooms.add(Arrays.asList(3));
			rooms.add(Arrays.asList());
			Assert.assertEquals(true, canVisitAllRooms(rooms));
		}
	}

	public boolean canVisitAllRooms(List<List<Integer>> rooms) {
		boolean[] visited = new boolean[rooms.size()];
		dfs(rooms, visited, 0);
		for (int i = 1; i < rooms.size(); i++) {
			if (!visited[i]) {
				return false;
			}
		}
		return true;
	}

	private void dfs(List<List<Integer>> rooms, boolean[] visited, int i) {
		for (Integer list : rooms.get(i)) {
			int key = list;
			if (visited[key]) {
				continue;
			}
			visited[key] = true;
			dfs(rooms, visited, key);
		}
	}

	// 俄罗斯套娃信封问题
	@Test
	public void test19() {
		Assert.assertEquals(3, new Solution19().maxEnvelopes(new int[][] { { 5, 4 }, { 6, 4 }, { 6, 7 }, { 2, 3 } }));
	}

	// https://leetcode-cn.com/problems/russian-doll-envelopes/solution/e-luo-si-tao-wa-xin-feng-wen-ti-by-leetc-wj68/
	class Solution19 {
		public int maxEnvelopes(int[][] envelopes) {
			if (envelopes==null || envelopes.length==0) {
				return 0;
			}
			// 先根据第一个元素升序，相同则根据第二个元素降序
			Arrays.sort(envelopes,new Comparator<int[]>() {
				@Override
				public int compare(int[] o1, int[] o2) {
					if (o1[0]==o2[0]) {
						return o2[1]-o1[1];
					}
					return o1[0]-o2[0];
				}
			});
			
			//查找第二个序列中最长递增的长度
			List<Integer> f = new ArrayList<Integer>();
			f.add(envelopes[0][1]);
			for (int i=1;i<envelopes.length;i++) {
				int num = envelopes[i][1];
				//num大于f中的最大值
				if (num>f.get(f.size()-1)) {
					f.add(num);
				} else {
					//二分查找num在f中的位置，进行替换
					//比如原来f中为 1,3,5,10 num=6时，替换为1,3,5,6，这样如果后面有7就可以加入进去，长度更大
					int index = binarySearch(f,num);
					f.set(index,num);
				}
			}
			return f.size();
		}
		private int binarySearch(List<Integer> f, int target) {
			int l=0,r=f.size()-1;
			while (l<r) {
				int mid = l+(r-l)/2;
				if (f.get(mid) < target) {
					l=mid+1;
				} else {
					r = mid;
				}
			}
			return l;
		}
	}

	public int maxEnvelopes(int[][] envelopes) {
		// sort on increasing in first dimension and decreasing in second
		Arrays.sort(envelopes, new Comparator<int[]>() {
			public int compare(int[] arr1, int[] arr2) {
				if (arr1[0] == arr2[0]) {
					return arr2[1] - arr1[1];
				} else {
					return arr1[0] - arr2[0];
				}
			}
		});
		// extract the second dimension and run LIS
		int[] secondDim = new int[envelopes.length];
		for (int i = 0; i < envelopes.length; ++i)
			secondDim[i] = envelopes[i][1];
		return lengthOfLIS(secondDim);
	}

	// https://leetcode-cn.com/problems/longest-increasing-subsequence/solution/dong-tai-gui-hua-er-fen-cha-zhao-tan-xin-suan-fa-p/
	public int lengthOfLIS(int[] nums) {
		int len = nums.length;
		if (len <= 1) {
			return len;
		}
		// tail 数组的定义：长度为 i + 1 的上升子序列的末尾最小是几
		int[] tail = new int[len];
		// 遍历第 1 个数，直接放在有序数组 tail 的开头
		tail[0] = nums[0];
		// end 表示有序数组 tail 的最后一个已经赋值元素的索引
		int end = 0;
		for (int i = 1; i < len; i++) {
			int left = 0;
			// 这里，因为当前遍历的数，有可能比有序数组 tail 数组实际有效的末尾的那个元素还大
			// 【逻辑 1】因此 end + 1 应该落在候选区间里
			int right = end + 1;
			while (left < right) {
				// 选左中位数不是偶然，而是有原因的，原因请见 LeetCode 第 35 题题解
				// int mid = left + (right - left) / 2;
				int mid = (left + right) >>> 1;

				if (tail[mid] < nums[i]) {
					// 中位数肯定不是要找的数，把它写在分支的前面
					left = mid + 1;
				} else {
					right = mid;
				}
			}
			// 因为 【逻辑 1】，因此一定能找到第 1 个大于等于 nums[i] 的元素
			// 因此，无需再单独判断，直接更新即可
			tail[left] = nums[i];

			// 但是 end 的值，需要更新，当前仅当更新位置在当前 end 的下一位
			if (left == end + 1) {
				end++;
			}
		}
		end++;
		return end;
	}

	// 预测赢家
	@Test
	public void test20() {
		Assert.assertEquals(true, PredictTheWinner(new int[] { 1, 5, 233, 7 }));
	}

	public boolean PredictTheWinner(int[] nums) {
		int length = nums.length;
		// dp[i][j] 表示当数组剩下的部分为下标 i 到下标 j 时
		int[][] dp = new int[length][length];
		for (int i = 0; i < length; i++) {
			dp[i][i] = nums[i];
		}
		for (int i = length - 2; i >= 0; i--) {
			for (int j = i + 1; j < length; j++) {
				// dp[i][j] 等于取i的值减去对方i+1到j与我方差值 或 取j的值减去对方i到j-1与我方差值中较大的值
				dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);
			}
		}
		return dp[0][length - 1] >= 0;
	}
}
