package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
		char[] s = new char[] {'h','e','l','l','o'};
		reverseString(s);
		Assert.assertArrayEquals(new char[] {'o','l','l','e','h'}, s);
	}

	public void reverseString(char[] s) {
		if (s==null || s.length==0) {
			return ;
		}
		int l = 0,r= s.length-1;
		while (l<=r) {
			char temp = s[l];
			s[l] = s[r];
			s[r] = temp;
			l++;
			r--;
		}
	}

}
