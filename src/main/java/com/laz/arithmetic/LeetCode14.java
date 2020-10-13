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
		ListNode head = Utils.createListNode(new Integer[] {1,2,3,4});
		ListNode head2 = swapPairs(head);
		Utils.printListNode(head2);
	}

	public ListNode swapPairs(ListNode head) {
		ListNode tmp = new ListNode(-1);
		tmp.next = head;
		ListNode p=tmp,q=head;
		int count = 1;
		while (q!=null) {
			q = q.next;
			count++;
			if (count%2==0 && q!=null) {
				//交换
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
}
