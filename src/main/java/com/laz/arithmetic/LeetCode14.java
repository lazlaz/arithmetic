package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class LeetCode14 {
	// 鸡蛋掉落
	@Test
	public void test1() {
		Assert.assertEquals(2, new Solution1().superEggDrop(1, 2));
	}

	class Solution1 {
		public int superEggDrop(int K, int N) {
			return dp(K, N);
		}

		Map<Integer, Integer> memo = new HashMap();

		public int dp(int K, int N) {
			// dp(K, N) 为在状态 (K, N)下最少需要的步数 K 为鸡蛋数，NN 为楼层数
			if (!memo.containsKey(N * 100 + K)) {
				int ans;
				if (N == 0) {
					ans = 0;
				} else if (K == 1) {
					ans = N;
				} else {
					int lo = 1, hi = N;
					while (lo + 1 < hi) {
						int x = (lo + hi) / 2;
						int t1 = dp(K - 1, x - 1);
						int t2 = dp(K, N - x);

						if (t1 < t2) {
							lo = x;
						} else if (t1 > t2) {
							hi = x;
						} else {
							lo = hi = x;
						}
					}

					ans = 1 + Math.min(Math.max(dp(K - 1, lo - 1), dp(K, N - lo)),
							Math.max(dp(K - 1, hi - 1), dp(K, N - hi)));
				}

				memo.put(N * 100 + K, ans);
			}
			return memo.get(N * 100 + K);
		}
	}

	// 树中距离之和
	@Test
	public void test2() {
		Assert.assertArrayEquals(new int[] { 8, 12, 6, 10, 10, 10 }, new Solution2().sumOfDistancesInTree(6,
				new int[][] { { 0, 1 }, { 0, 2 }, { 2, 3 }, { 2, 4 }, { 2, 5 } }));
	}

	class Solution2 {
		int[] ans;
		int[] sz;
		int[] dp;
		List<List<Integer>> graph;

		public int[] sumOfDistancesInTree(int N, int[][] edges) {
			ans = new int[N];
			sz = new int[N];
			dp = new int[N];
			graph = new ArrayList<List<Integer>>();
			for (int i = 0; i < N; ++i) {
				graph.add(new ArrayList<Integer>());
			}
			for (int[] edge : edges) {
				int u = edge[0], v = edge[1];
				graph.get(u).add(v);
				graph.get(v).add(u);
			}
			dfs(0, -1);
			dfs2(0, -1);
			return ans;
		}

		public void dfs(int u, int f) {
			sz[u] = 1;
			dp[u] = 0;
			for (int v : graph.get(u)) {
				if (v == f) {
					continue;
				}
				dfs(v, u);
				dp[u] += dp[v] + sz[v];
				sz[u] += sz[v];
			}
		}

		public void dfs2(int u, int f) {
			ans[u] = dp[u];
			for (int v : graph.get(u)) {
				if (v == f) {
					continue;
				}
				int pu = dp[u], pv = dp[v];
				int su = sz[u], sv = sz[v];

				dp[u] -= dp[v] + sz[v];
				sz[u] -= sz[v];
				dp[v] += dp[u] + sz[u];
				sz[v] += sz[u];

				dfs2(v, u);

				dp[u] = pu;
				dp[v] = pv;
				sz[u] = su;
				sz[v] = sv;
			}
		}

	}

	// 反转字符串
	@Test
	public void test3() {
		char[] s = new char[] { 'h', 'e', 'l', 'l', 'o' };
		reverseString(s);
		Assert.assertArrayEquals(new char[] { 'o', 'l', 'l', 'e', 'h' }, s);
	}

	public void reverseString(char[] s) {
		if (s == null || s.length == 0) {
			return;
		}
		int l = 0, r = s.length - 1;
		while (l <= r) {
			char temp = s[l];
			s[l] = s[r];
			s[r] = temp;
			l++;
			r--;
		}
	}

	// 环形链表 II
	@Test
	public void test4() {
		ListNode head = new ListNode(3);
		ListNode head1 = new ListNode(2);
		ListNode head2 = new ListNode(0);
		ListNode head3 = new ListNode(-4);

		head.next = head1;
		head1.next = head2;
		head2.next = head3;
		head3.next = head1;
		ListNode ret = detectCycle(head);
		System.out.println(ret.val);
	}

	public ListNode detectCycle(ListNode head) {
		Set<ListNode> set = new HashSet<ListNode>();
		while (head != null) {
			set.add(head);
			head = head.next;
			if (set.contains(head)) {
				return head;
			}
		}
		return null;
	}

	// 区间列表的交集
	@Test
	public void test5() {
		int[][] A = new int[][] { { 4, 6 }, { 7, 8 }, { 10, 17 } };
		int[][] B = new int[][] { { 5, 10 } };

		int[][] ret = intervalIntersection2(A, B);
		for (int[] is : ret) {
			for (int i : is) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}

	// https://leetcode-cn.com/problems/interval-list-intersections/solution/jiu-pa-ni-bu-dong-shuang-zhi-zhen-by-hyj8/
	// 优雅的双指针解法
	public int[][] intervalIntersection2(int[][] A, int[][] B) {
		List<int[]> res = new ArrayList<int[]>();
		int i = 0;
		int j = 0;
		while (i < A.length && j < B.length) {
			int start = Math.max(A[i][0], B[j][0]);
			int end = Math.min(A[i][1], B[j][1]);
			if (start <= end) {
				res.add(new int[] { start, end });
			}
			if (A[i][1] < B[j][1]) {
				i++;
			} else {
				j++;
			}
		}
		int[][] r = new int[res.size()][2];
		int index = 0;
		for (int[] rv : res) {
			r[index++] = rv;
		}
		return r;

	}

	public int[][] intervalIntersection(int[][] A, int[][] B) {
		List<int[]> ret = new ArrayList<int[]>();
		int aLen = A.length;
		int bLen = B.length;
		for (int i = 0; i < aLen; i++) {
			for (int j = 0; j < bLen; j++) {
				// 包含
				if (B[j][0] <= A[i][0] && B[j][1] >= A[i][1]) {
					int[] v = new int[2];
					v[0] = A[i][0];
					v[1] = A[i][1];
					ret.add(v);
				} else if (B[j][0] >= A[i][0] && B[j][0] <= A[i][1]) {
					int[] v = new int[2];
					v[0] = B[j][0];
					if (B[j][1] >= A[i][1]) {
						v[1] = A[i][1];
					} else {
						v[1] = B[j][1];
					}
					ret.add(v);
				} else if (B[j][1] <= A[i][1] && B[j][1] >= A[i][0]) {
					int[] v = new int[2];
					v[0] = A[i][0];
					if (B[j][1] <= A[i][1]) {
						v[1] = B[j][1];
					} else {
						v[1] = A[i][1];
					}
					ret.add(v);
				}
				if (B[j][0] > A[i][1]) {
					break;
				}
			}
		}

		int[][] r = new int[ret.size()][2];
		int i = 0;
		for (int[] rv : ret) {
			r[i++] = rv;
		}
		return r;
	}

	// 二叉搜索树的最小绝对差
	@Test
	public void test6() {
		TreeNode root = Utils.createTree(new Integer[] { 1, null, 3, 2, null });
		Assert.assertEquals(1, new Solution6().getMinimumDifference(root));
	}

	class Solution6 {
		int pre;
		int ans;

		public int getMinimumDifference(TreeNode root) {
			ans = Integer.MAX_VALUE;
			pre = -1;
			dfs(root);
			return ans;
		}

		public void dfs(TreeNode root) {
			if (root == null) {
				return;
			}
			dfs(root.left);
			if (pre == -1) {
				pre = root.val;
			} else {
				ans = Math.min(ans, root.val - pre);
				pre = root.val;
			}
			dfs(root.right);
		}

	}

	// 两两交换链表中的节点
	@Test
	public void test7() {
		ListNode head = Utils.createListNode(new Integer[] { 1, 2, 3, 4 });
		ListNode head2 = swapPairs(head);
		Utils.printListNode(head2);
	}

	public ListNode swapPairs(ListNode head) {
		ListNode tmp = new ListNode(-1);
		tmp.next = head;
		ListNode p = tmp, q = head;
		int count = 1;
		while (q != null) {
			q = q.next;
			count++;
			if (count % 2 == 0 && q != null) {
				// 交换
				ListNode tmp1 = q.next;
				ListNode tmp2 = p.next;
				p.next = q;
				q.next = tmp2;
				tmp2.next = tmp1;
				p = q.next;
				q = p;
			}
		}
		return tmp.next;
	}

	// 买卖股票的最佳时机 II
	@Test
	public void test8() {
		Assert.assertEquals(7, new Solution8().maxProfit(new int[] { 7, 1, 5, 3, 6, 4 }));
	}

	class Solution8 {
		// 峰谷法 找到所有谷峰之和
		public int maxProfit(int[] prices) {
			int i = 0;
			int valley = prices[0];
			int peak = prices[0];
			int maxprofit = 0;
			while (i < prices.length - 1) {
				while (i < prices.length - 1 && prices[i] >= prices[i + 1])
					i++;
				valley = prices[i];
				while (i < prices.length - 1 && prices[i] <= prices[i + 1])
					i++;
				peak = prices[i];
				maxprofit += peak - valley;
			}
			return maxprofit;
		}
	}

	// 买卖股票的最佳时机 III
	@Test
	public void test9() {
		Assert.assertEquals(6, new Solution9().maxProfit(new int[] { 3, 3, 5, 0, 0, 3, 1, 4 }));
	}

	class Solution9 {
		// https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-iii/solution/wu-chong-shi-xian-xiang-xi-tu-jie-123mai-mai-gu-pi/
		public int maxProfit(int[] prices) {
			if (prices == null || prices.length == 0) {
				return 0;
			}
			int n = prices.length;
			// 定义二维数组，5种状态
			/**
			 * dp[i][0] 初始化状态 dp[i][1] 第一次买入 dp[i][2] 第一次卖出 dp[i][3] 第二次买入 dp[i][4] 第二次卖出
			 */
			int[][] dp = new int[n][5];
			// 初始化第一天的状态
			dp[0][0] = 0;
			dp[0][1] = -prices[0];
			dp[0][2] = 0;
			dp[0][3] = -prices[0];
			dp[0][4] = 0;
			for (int i = 1; i < n; ++i) {
				// dp[i][0]相当于初始状态，它只能从初始状态转换来
				dp[i][0] = dp[i - 1][0];
				// 处理第一次买入、第一次卖出
				dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
				dp[i][2] = Math.max(dp[i - 1][2], dp[i - 1][1] + prices[i]);
				// 处理第二次买入、第二次卖出
				dp[i][3] = Math.max(dp[i - 1][3], dp[i - 1][2] - prices[i]);
				dp[i][4] = Math.max(dp[i - 1][4], dp[i - 1][3] + prices[i]);
			}
			// 返回最大值
			int a = Math.max(dp[n - 1][0], dp[n - 1][1]); // 第n-1天初始状态和第n-1天买入1比较
			int b = Math.max(dp[n - 1][2], dp[n - 1][3]);// 第n-1天买出1和第n-1天买入2比较
			int c = dp[n - 1][4];// 第n-1天卖出2
			return Math.max(Math.max(a, b), c);
		}
	}

	// 买卖股票的最佳时机
	@Test
	public void test10() {
		int[] prices = new int[] { 7, 1, 5, 3, 6, 4 };
		System.out.println(new Solution10().maxProfit2(prices));
	}

	class Solution10 {
		// 暴力
		public int maxProfit(int[] prices) {
			int max = 0;
			for (int i = 0; i < prices.length; i++) {
				for (int j = i; j < prices.length; j++) {
					int temp = prices[j] - prices[i];
					if (temp > max) {
						max = temp;
					}
				}
			}
			return max;
		}

		public int maxProfit2(int prices[]) {
			int minprice = Integer.MAX_VALUE;
			int maxprofit = 0;
			for (int i = 0; i < prices.length; i++) {
				if (prices[i] < minprice) {
					minprice = prices[i];
				} else if (prices[i] - minprice > maxprofit) {
					maxprofit = prices[i] - minprice;
				}
			}
			return maxprofit;
		}

	}

	// 买卖股票的最佳时机 IV
	@Test
	public void test11() {
		Assert.assertEquals(7, new Solution11().maxProfit(2, new int[] { 3, 2, 6, 5, 0, 3 }));
	}

	class Solution11 {
		public int maxProfit(int k, int[] prices) {
			if (prices == null || prices.length == 0) {
				return 0;
			}
			int n = prices.length;
			// 当k非常大时转为无限次交易
			if (k >= n / 2) {
				// 退化为买卖股票的最佳时机 II
				int dp0 = 0, dp1 = -prices[0];
				for (int i = 1; i < n; ++i) {
					int tmp = dp0;
					dp0 = Math.max(dp0, dp1 + prices[i]);
					dp1 = Math.max(dp1, dp0 - prices[i]);
				}
				return Math.max(dp0, dp1);
			}
			// 定义二维数组，交易了多少次、当前的买卖状态
			int[][] dp = new int[k + 1][2];
			for (int i = 0; i <= k; ++i) {
				dp[i][0] = 0;
				dp[i][1] = -prices[0];
			}
			for (int i = 1; i < n; ++i) {
				for (int j = k; j > 0; --j) {
					// 处理第k次买入
					dp[j - 1][1] = Math.max(dp[j - 1][1], dp[j - 1][0] - prices[i]);
					// 处理第k次卖出
					dp[j][0] = Math.max(dp[j][0], dp[j - 1][1] + prices[i]);

				}
			}
			return dp[k][0];

		}
	}

	// 最佳买卖股票时机含冷冻期
	@Test
	public void test12() {
		Assert.assertEquals(3, new Solution12().maxProfit(new int[] { 1, 2, 3, 0, 2 }));
	}

	class Solution12 {
		public int maxProfit(int[] prices) {
			int n = prices.length;
			if (n < 2) {
				return 0;
			}
			// 初始化第一天和第二天
			int[][] dp = new int[n][2];
			dp[0][0] = 0;
			dp[0][1] = -prices[0];
			dp[1][0] = Math.max(dp[0][0], dp[0][1] + prices[1]);
			dp[1][1] = Math.max(dp[0][1], dp[0][0] - prices[1]);
			for (int i = 2; i < n; ++i) {
				// 求第i天累计卖出最大利润，累计买入的最大利润
				dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]); //0 卖出   
				dp[i][1] = Math.max(dp[i - 1][1], dp[i - 2][0] - prices[i]); //1 买入
			}
			return Math.max(dp[n - 1][0], dp[n - 1][1]);
		}
	}
	
	//买卖股票的最佳时机含手续费
	@Test
	public void test13() {
		Assert.assertEquals(8, new Solution13().maxProfit(new int[] { 1, 3, 2, 8, 4, 9},2));
	}
	class Solution13 {
	    public int maxProfit(int[] prices, int fee) {
	    	int n = prices.length;
	    	int dp0 = 0, dp1 = -prices[0];
			for (int i = 1; i < n; ++i) {
				int tmp = dp0;
				dp0 = Math.max(dp0, dp1 + prices[i]-fee);
				dp1 = Math.max(dp1, dp0 - prices[i]);
			}
			return Math.max(dp0, dp1);
	    }
	}
}