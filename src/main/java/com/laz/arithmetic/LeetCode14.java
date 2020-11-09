package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

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

		// 动态规划
		// https://leetcode-cn.com/problems/best-time-to-buy-and-sell-stock-ii/solution/tan-xin-suan-fa-by-liweiwei1419-2/
		public int maxProfit2(int[] prices) {
			int len = prices.length;
			if (len < 2) {
				return 0;
			}
			// dp[i][j] 表示到下标为 i 的这一天，持股状态为 j 时，我们手上拥有的最大现金数。
			// 0：持有现金
			// 1：持有股票
			// 状态转移：0 → 1 → 0 → 1 → 0 → 1 → 0
			int[][] dp = new int[len][2];

			dp[0][0] = 0;
			dp[0][1] = -prices[0];

			for (int i = 1; i < len; i++) {
				// 这两行调换顺序也是可以的
				dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
				dp[i][1] = Math.max(dp[i - 1][1], dp[i - 1][0] - prices[i]);
			}
			return dp[len - 1][0];

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
				dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]); // 0 卖出
				dp[i][1] = Math.max(dp[i - 1][1], dp[i - 2][0] - prices[i]); // 1 买入
			}
			return Math.max(dp[n - 1][0], dp[n - 1][1]);
		}
	}

	// 买卖股票的最佳时机含手续费
	@Test
	public void test13() {
		Assert.assertEquals(8, new Solution13().maxProfit(new int[] { 1, 3, 2, 8, 4, 9 }, 2));
	}

	class Solution13 {
		public int maxProfit(int[] prices, int fee) {
			int n = prices.length;
			int dp0 = 0, dp1 = -prices[0];
			for (int i = 1; i < n; ++i) {
				int tmp = dp0;
				dp0 = Math.max(dp0, dp1 + prices[i] - fee);
				dp1 = Math.max(dp1, dp0 - prices[i]);
			}
			return Math.max(dp0, dp1);
		}
	}

	// 查找常用字符
	@Test
	public void test14() {
		{
			List<String> res = commonChars(new String[] { "bella", "label", "roller" });
			System.out.println(Joiner.on(",").join(res)); // "e","l","l"
		}

		{
			List<String> res = commonChars(new String[] { "cool", "lock", "cook" });
			System.out.println(Joiner.on(",").join(res));// "c","o"
		}
		{
			List<String> res = commonChars(new String[] {});
			System.out.println(Joiner.on(",").join(res));//
		}
	}

	public List<String> commonChars(String[] A) {
		int[] letter = new int[26];
		for (int i = 0; i < A.length; i++) {
			if (i == 0) {
				for (int j = 0; j < A[i].length(); j++) {
					letter[A[i].charAt(j) - 'a']++;
				}
			} else {
				int[] letter2 = new int[26];
				for (int j = 0; j < A[i].length(); j++) {
					letter2[A[i].charAt(j) - 'a']++;
				}
				for (int j = 0; j < letter.length; j++) {
					if (letter[j] > 0 && letter2[j] < letter[j]) {
						letter[j] = letter2[j];
					}
				}
			}
		}
		List<String> res = new ArrayList<String>();
		for (int i = 0; i < letter.length; i++) {
			if (letter[i] > 0) {
				int count = letter[i];
				while (count > 0) {
					char c = (char) (i + 'a');
					res.add(c + "");
					count--;
				}
			}
		}
		return res;
	}

	// 从前序与中序遍历序列构造二叉树
	@Test
	public void test15() {
		TreeNode root = new Solution15().buildTree(new int[] { 3, 9, 20, 15, 7 }, new int[] { 9, 3, 15, 20, 7 });
		Utils.printTreeNode(root);
	}

	class Solution15 {
		int post_idx;
		int[] preorder;
		int[] inorder;
		Map<Integer, Integer> idx_map = new HashMap<Integer, Integer>();

		public TreeNode helper(int in_left, int in_right) {
			// 如果这里没有节点构造二叉树了，就结束
			if (in_left > in_right || post_idx >= preorder.length) {
				return null;
			}

			// 选择 post_idx 位置的元素作为当前子树根节点
			int root_val = preorder[post_idx];
			TreeNode root = new TreeNode(root_val);

			// 根据 root 所在位置分成左右两棵子树
			int index = idx_map.get(root_val);

			post_idx++;
			// 构造左子树
			root.left = helper(in_left, index - 1);
			// 构造右子树
			root.right = helper(index + 1, in_right);
			return root;
		}

		public TreeNode buildTree(int[] preorder, int[] inorder) {
			this.preorder = preorder;
			this.inorder = inorder;
			// 从前序遍历的第一个元素开始
			post_idx = 0;

			// 建立（元素，下标）键值对的哈希表
			int idx = 0;
			for (Integer val : inorder) {
				idx_map.put(val, idx++);
			}

			return helper(0, inorder.length - 1);
		}
	}

	// 最长连续序列
	@Test
	public void test16() {
		Assert.assertEquals(4, longestConsecutive(new int[] { 100, 4, 200, 1, 3, 4, 2 }));
	}

	// https://leetcode-cn.com/problems/longest-consecutive-sequence/solution/zui-chang-lian-xu-xu-lie-by-leetcode-solution/
	public int longestConsecutive(int[] nums) {
		Set<Integer> num_set = new LinkedHashSet<Integer>();
		for (int num : nums) {
			num_set.add(num);
		}

		int longestStreak = 0;

		for (int num : num_set) {
			if (!num_set.contains(num - 1)) {
				int currentNum = num;
				int currentStreak = 1;

				while (num_set.contains(currentNum + 1)) {
					currentNum += 1;
					currentStreak += 1;
				}

				longestStreak = Math.max(longestStreak, currentStreak);
			}
		}

		return longestStreak;

	}

	// 排序链表
	@Test
	public void test17() {
		ListNode head = Utils.createListNode(new Integer[] { 4, 2, 1, 3 });
		head = new Solution17().sortList(head);
		Utils.printListNode(head);
	}

	// 迭代归并
	// https://leetcode-cn.com/problems/sort-list/solution/pai-xu-lian-biao-di-gui-die-dai-xiang-jie-by-cherr/
	class Solution17 {
		public ListNode sortList(ListNode head) {
			int length = getLength(head);
			ListNode dummy = new ListNode(-1);
			dummy.next = head;

			for (int step = 1; step < length; step *= 2) { // 依次将链表分成1块，2块，4块...
				// 每次变换步长，pre指针和cur指针都初始化在链表头
				ListNode pre = dummy;
				ListNode cur = dummy.next;
				while (cur != null) {
					ListNode h1 = cur; // 第一部分头 （第二次循环之后，cur为剩余部分头，不断往后把链表按照步长step分成一块一块...）
					ListNode h2 = split(h1, step); // 第二部分头
					cur = split(h2, step); // 剩余部分的头
					ListNode temp = merge(h1, h2); // 将一二部分排序合并
					pre.next = temp; // 将前面的部分与排序好的部分连接
					while (pre.next != null) {
						pre = pre.next; // 把pre指针移动到排序好的部分的末尾
					}
				}
			}
			return dummy.next;
		}

		public int getLength(ListNode head) {
			// 获取链表长度
			int count = 0;
			while (head != null) {
				count++;
				head = head.next;
			}
			return count;
		}

		public ListNode split(ListNode head, int step) {
			// 断链操作 返回第二部分链表头
			if (head == null)
				return null;
			ListNode cur = head;
			for (int i = 1; i < step && cur.next != null; i++) {
				cur = cur.next;
			}
			ListNode right = cur.next;
			cur.next = null; // 切断连接
			return right;
		}

		public ListNode merge(ListNode h1, ListNode h2) {
			// 合并两个有序链表
			ListNode head = new ListNode(-1);
			ListNode p = head;
			while (h1 != null && h2 != null) {
				if (h1.val < h2.val) {
					p.next = h1;
					h1 = h1.next;
				} else {
					p.next = h2;
					h2 = h2.next;
				}
				p = p.next;
			}
			if (h1 != null)
				p.next = h1;
			if (h2 != null)
				p.next = h2;

			return head.next;
		}
	}

	// 递归归并
	public ListNode sortList(ListNode head) {
		if (head == null || head.next == null)
			return head;
		ListNode fast = head.next, slow = head;
		while (fast != null && fast.next != null) {
			slow = slow.next;
			fast = fast.next.next;
		}
		ListNode tmp = slow.next;
		slow.next = null;
		ListNode left = sortList(head);
		ListNode right = sortList(tmp);
		ListNode h = new ListNode(0);
		ListNode res = h;
		while (left != null && right != null) {
			if (left.val < right.val) {
				h.next = left;
				left = left.next;
			} else {
				h.next = right;
				right = right.next;
			}
			h = h.next;
		}
		h.next = (left != null ? left : right);
		return res.next;
	}

	// 实现 Trie (前缀树)
	@Test
	public void test18() {
		Trie trie = new Trie();

		trie.insert("apple");
		System.out.println(trie.search("apple")); // 返回 true
		System.out.println(trie.search("app"));// 返回 false
		trie.startsWith("app"); // 返回 true
		trie.insert("app");
		System.out.println(trie.search("app")); // 返回 true
	}

	class TrieNode {
		Map<Character, TrieNode> children;
		boolean wordEnd;

		public TrieNode() {
			children = new HashMap<Character, TrieNode>();
			wordEnd = false;
		}
	}

	class Trie {
		private TrieNode root;

		/** Initialize your data structure here. */
		public Trie() {
			root = new TrieNode();
			root.wordEnd = false;
		}

		/** Inserts a word into the trie. */
		public void insert(String word) {
			TrieNode node = root;
			for (int i = 0; i < word.length(); i++) {
				Character c = new Character(word.charAt(i));
				if (!node.children.containsKey(c)) {
					node.children.put(c, new TrieNode());
				}
				node = node.children.get(c);
			}
			node.wordEnd = true;
		}

		/** Returns if the word is in the trie. */
		public boolean search(String word) {
			TrieNode node = root;
			boolean found = true;
			for (int i = 0; i < word.length(); i++) {
				Character c = new Character(word.charAt(i));
				if (!node.children.containsKey(c)) {
					return false;
				}
				node = node.children.get(c);
			}
			return found && node.wordEnd;
		}

		/**
		 * Returns if there is any word in the trie that starts with the given prefix.
		 */
		public boolean startsWith(String prefix) {
			TrieNode node = root;
			boolean found = true;
			for (int i = 0; i < prefix.length(); i++) {
				Character c = new Character(prefix.charAt(i));
				if (!node.children.containsKey(c)) {
					return false;
				}
				node = node.children.get(c);
			}
			return found;
		}
	}

	// 填充每个节点的下一个右侧节点指针
	@Test
	public void test19() {
		Node root1 = new Node(1);
		Node root2 = new Node(2);
		Node root3 = new Node(3);
		Node root4 = new Node(4);
		Node root5 = new Node(5);
		Node root6 = new Node(6);
		Node root7 = new Node(7);

		root1.left = root2;
		root1.right = root3;

		root2.left = root4;
		root2.right = root5;

		root3.left = root6;
		root3.right = root7;
		Node node = new Solution19().connect(root1);
		System.out.println(node);
	}

	class Node {
		public int val;
		public Node left;
		public Node right;
		public Node next;

		public Node() {
		}

		public Node(int _val) {
			val = _val;
		}

		public Node(int _val, Node _left, Node _right, Node _next) {
			val = _val;
			left = _left;
			right = _right;
			next = _next;
		}
	};

	class Solution19 {
		public Node connect(Node root) {
			if (root == null) {
				return root;
			}
			Queue<Node> queue = new LinkedList<Node>();
			queue.add(root);
			while (!queue.isEmpty()) {
				int level_length = queue.size();
				Node last = null;
				for (int i = 0; i < level_length; ++i) {
					Node node = queue.remove();
					if (last != null) {
						last.next = node;
					}
					if (node.left != null)
						queue.add(node.left);
					if (node.right != null)
						queue.add(node.right);
					last = node;
				}
			}
			return root;
		}
	}

	// 完全平方数
	@Test
	public void test20() {
		Assert.assertEquals(2, numSquares(5));
	}

	// https://leetcode-cn.com/problems/perfect-squares/solution/hua-jie-suan-fa-279-wan-quan-ping-fang-shu-by-guan/
	public int numSquares(int n) {
		int[] dp = new int[n + 1]; // 默认初始化值都为0
		for (int i = 1; i <= n; i++) {
			dp[i] = i; // 最坏的情况就是每次+1
			for (int j = 1; j * j <= i; j++) {
				dp[i] = Math.min(dp[i], dp[i - j * j] + 1); // 动态转移方程
			}
		}
		return dp[n];

	}
}
