package com.laz.arithmetic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

/**
 * 
 *
 */
public class LeetCode8 {
	// 计算右侧小于当前元素的个数
	@Test
	public void test1() {
		int[] nums = new int[] { 5, 2, 6, 1 };
		List<Integer> res = countSmaller(nums);
		System.out.println(Joiner.on(",").join(res));
	}

	private int[] c;
	private int[] a;

	// 树壮数组+离散化
	public List<Integer> countSmaller(int[] nums) {
		List<Integer> resultList = new ArrayList<Integer>();
		discretization(nums);
		init(nums.length + 5);
		for (int i = nums.length - 1; i >= 0; --i) {
			int id = getId(nums[i]);
			resultList.add(query(id - 1));
			update(id, 1);
		}
		Collections.reverse(resultList);
		return resultList;
	}

	private void init(int length) {
		c = new int[length];
		Arrays.fill(c, 0);
	}

	private int lowBit(int x) {
		return x & (-x);
	}

	private void update(int pos, int k) {
		while (pos < c.length) {
			c[pos] += k;
			pos += lowBit(pos);
		}
	}

	// 查询sum[i]= C[i] + C[i-2k1] + C[(i - 2k1) - 2k2] + .....； 前缀和
	private int query(int pos) {
		int ret = 0;
		while (pos > 0) {
			ret += c[pos];
			pos -= lowBit(pos);
		}

		return ret;
	}

	private void discretization(int[] nums) {
		Set<Integer> set = new HashSet<Integer>();
		for (int num : nums) {
			set.add(num);
		}
		int size = set.size();
		a = new int[size];
		int index = 0;
		for (int num : set) {
			a[index++] = num;
		}
		Arrays.sort(a);
	}

	private int getId(int x) {
		return Arrays.binarySearch(a, x) + 1;
	}

	// 暴力解法超时
	public List<Integer> countSmaller2(int[] nums) {
		List<Integer> res = new ArrayList<Integer>();
		if (nums == null) {
			return res;
		}
		for (int i = 0; i < nums.length; i++) {
			int count = 0;
			for (int j = i; j < nums.length; j++) {
				if (nums[j] < nums[i]) {
					count++;
				}
			}
			res.add(count);
		}
		return res;
	}

	// 字符串相乘
	@Test
	public void test2() {
		Assert.assertEquals("56088", multiply("123", "456"));
	}

	/**
	 * 计算形式 num1 x num2 ------ result
	 */
	public String multiply(String num1, String num2) {
		if (num1.equals("0") || num2.equals("0")) {
			return "0";
		}
		// 保存计算结果
		String res = "0";

		// num2 逐位与 num1 相乘
		for (int i = num2.length() - 1; i >= 0; i--) {
			int carry = 0;
			// 保存 num2 第i位数字与 num1 相乘的结果
			StringBuilder temp = new StringBuilder();
			// 补 0
			for (int j = 0; j < num2.length() - 1 - i; j++) {
				temp.append(0);
			}
			int n2 = num2.charAt(i) - '0';

			// num2 的第 i 位数字 n2 与 num1 相乘
			for (int j = num1.length() - 1; j >= 0 || carry != 0; j--) {
				int n1 = j < 0 ? 0 : num1.charAt(j) - '0';
				int product = (n1 * n2 + carry) % 10;
				temp.append(product);
				carry = (n1 * n2 + carry) / 10;
			}
			// 将当前结果与新计算的结果求和作为新的结果
			res = addStrings(res, temp.reverse().toString());
		}
		return res;
	}

	/**
	 * 对两个字符串数字进行相加，返回字符串形式的和
	 */
	public String addStrings(String num1, String num2) {
		StringBuilder builder = new StringBuilder();
		int carry = 0;
		for (int i = num1.length() - 1, j = num2.length() - 1; i >= 0 || j >= 0 || carry != 0; i--, j--) {
			int x = i < 0 ? 0 : num1.charAt(i) - '0';
			int y = j < 0 ? 0 : num2.charAt(j) - '0';
			int sum = (x + y + carry) % 10;
			builder.append(sum);
			carry = (x + y + carry) / 10;
		}
		return builder.reverse().toString();
	}

	// 地下城游戏
	@Test
	public void test3() {
		int[][] dungeon = new int[][] { { -2, -3, 3 }, { -5, -10, 1 }, { 10, 30, -5 } };
		System.out.println(calculateMinimumHP(dungeon));
	}

	public int calculateMinimumHP(int[][] dungeon) {
		int n = dungeon.length, m = dungeon[0].length;
		int[][] dp = new int[n + 1][m + 1];
		for (int i = 0; i <= n; ++i) {
			Arrays.fill(dp[i], Integer.MAX_VALUE);
		}
		// 令dp[i][j] 表示从坐标 (i,j) 到终点所需的最小初始值
		/**
		 * 边界条件为，当 i=n-1i=n−1 或者 j=m-1j=m−1 时， dp[i][j]dp[i][j] 转移需要用到的
		 * dp[i][j+1]dp[i][j+1] 和 dp[i+1][j]dp[i+1][j] 中有无效值，因此代码实现中给无效值赋值为极大值。
		 * 特别地，dp[n-1][m-1]dp[n−1][m−1] 转移需要用到的 dp[n-1][m]dp[n−1][m]
		 */
		// 必须有1点血
		dp[n][m - 1] = 1;
		dp[n - 1][m] = 1;
		for (int i = n - 1; i >= 0; --i) {
			for (int j = m - 1; j >= 0; --j) {
				int minn = Math.min(dp[i + 1][j], dp[i][j + 1]);
				dp[i][j] = Math.max(minn - dungeon[i][j], 1);
			}
		}
		return dp[0][0];
	}

	// 接雨水
	@Test
	public void test4() {
		Assert.assertEquals(6, trap(new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 }));
	}

	public int trap(int[] height) {
		int left = 0, right = height.length - 1;
		int ans = 0;
		int leftMax = 0, rightMax = 0;
		while (left < right) {
			if (height[left] < height[right]) {
				if (height[left] >= leftMax) {
					leftMax = height[left];
				} else {
					ans += (leftMax - height[left]);
				}
				++left;
			} else {
				if (height[right] >= rightMax) {
					rightMax = height[right];
				} else {
					ans += (rightMax - height[right]);
				}
				--right;
			}
		}
		return ans;
	}

	// 两个数组的交集 II
	@Test
	public void test5() {
		Assert.assertArrayEquals(new int[] { 2, 2 }, intersect(new int[] { 1, 2, 2, 1 }, new int[] { 2, 2 }));
		Assert.assertArrayEquals(new int[] { 4, 9 }, intersect(new int[] { 4, 9, 5 }, new int[] { 9, 4, 9, 8, 4 }));
	}

	public int[] intersect(int[] nums1, int[] nums2) {
		int[] res = new int[] {};
		if (nums1 == null || nums2 == null) {
			return res;
		}
		if (nums1.length == 0 || nums2.length == 0) {
			return res;
		}
		Arrays.sort(nums1);
		Arrays.sort(nums2);
		int i = 0;
		int j = 0;
		List<Integer> list = new ArrayList<Integer>();
		while (i < nums1.length && j < nums2.length) {
			if (nums1[i] == nums2[j]) {
				list.add(nums1[i]);
				j++;
				i++;
			} else if (nums1[i] > nums2[j]) {
				j++;
			} else if (nums1[i] < nums2[j]) {
				i++;
			}
		}
		res = new int[list.size()];
		int count = 0;
		for (Integer integer : list) {
			res[count++] = integer;
		}
		return res;
	}

	// 通配符匹配
	@Test
	public void test6() {
		// Assert.assertEquals(false, isMatch("aa","a"));
		Assert.assertEquals(true, isMatch("aa", "a*"));
	}

	public boolean isMatch(String s, String p) {
		int m = s.length();
		int n = p.length();
		boolean[][] dp = new boolean[m + 1][n + 1];
		dp[0][0] = true;
		// dp[i][j] 表示字符串 s的前 i个字符和模式 p的前 j个字符是否能匹配
		for (int i = 1; i <= n; ++i) {
			if (p.charAt(i - 1) == '*') {
				dp[0][i] = true;
			} else {
				break;
			}
		}
		for (int i = 1; i <= m; ++i) {
			for (int j = 1; j <= n; ++j) {
				if (p.charAt(j - 1) == '*') {
					dp[i][j] = dp[i][j - 1] || dp[i - 1][j];
				} else if (p.charAt(j - 1) == '?' || s.charAt(i - 1) == p.charAt(j - 1)) {
					dp[i][j] = dp[i - 1][j - 1];
				}
			}
		}
		return dp[m][n];
	}

	// 三角形最小路径和
	@Test
	public void test7() {
		List<List<Integer>> triangle = new ArrayList<List<Integer>>();
		{
			List<Integer> l = new ArrayList<Integer>();
			l.add(2);
			triangle.add(l);
		}
		{
			List<Integer> l = new ArrayList<Integer>();
			l.add(3);
			l.add(4);
			triangle.add(l);
		}
		{
			List<Integer> l = new ArrayList<Integer>();
			l.add(6);
			l.add(5);
			l.add(7);
			triangle.add(l);
		}
		{
			List<Integer> l = new ArrayList<Integer>();
			l.add(4);
			l.add(1);
			l.add(8);
			l.add(3);
			triangle.add(l);
		}
		System.out.println(minimumTotal(triangle));
	}

	public int minimumTotal(List<List<Integer>> triangle) {
		int n = triangle.size();

		int[][] dp = new int[2][n];
		dp[0][0] = triangle.get(0).get(0);
		for (int i = 1; i < n; i++) {
			int curr = i % 2;
			int prev = 1 - curr;
			dp[curr][0] = dp[prev][0] + triangle.get(i).get(0);
			for (int j = 1; j < i; j++) {
				dp[curr][j] = Math.min(dp[prev][j - 1], dp[prev][j]) + triangle.get(i).get(j);
			}
			dp[curr][i] = dp[prev][i - 1] + triangle.get(i).get(i);
		}
		int min = dp[(n - 1) % 2][0];
		for (int i = 1; i < n; i++) {
			min = Math.min(min, dp[(n - 1) % 2][i]);
		}
		return min;
	}

	// 跳跃游戏 II
	@Test
	public void test8() {
		Assert.assertEquals(2, jump(new int[] { 2, 3, 1, 1, 4 }));
	}

	public int jump(int[] nums) {
		int end = 0;
		int maxPosition = 0;
		int steps = 0;
		for (int i = 0; i < nums.length - 1; i++) {
			// 找能跳的最远的
			maxPosition = Math.max(maxPosition, nums[i] + i);
			if (i == end) { // 遇到边界，就更新边界，并且步数加一
				end = maxPosition;
				steps++;
			}
		}
		return steps;
	}

	// 超时
	public int jump2(int[] nums) {
		int n = nums.length;
		int[] dp = new int[n];
		dp[0] = 0;
		for (int i = 1; i < n; i++) {
			// 默认等于前一步加一
			dp[i] = dp[i - 1] + 1;
			int count = 1;
			for (int j = i - 1; j >= 0; j--) {
				if (nums[j] >= count) {
					// 能够达到
					dp[i] = Math.min(dp[i], dp[j] + 1);
				}
				count++;
			}
		}
		return dp[n - 1];

	}

	// 不同的二叉搜索树
	@Test
	public void test9() {
		Assert.assertEquals(5, numTrees(3));
	}

	public int numTrees(int n) {
		int[] G = new int[n + 1];
		G[0] = 1;
		G[1] = 1;
		for (int i = 2; i <= n; i++) {
			for (int j = 1; j <= i; j++) {
				G[i] += G[j - 1] * G[i - j];
			}
		}
		return G[n];
	}

	// 全排列
	@Test
	public void test10() {
		int[] nums = new int[] { 1, 2, 3 };
		List<List<Integer>> list = permute(nums);
		for (List<Integer> l : list) {
			System.out.println(Joiner.on(",").join(l));
		}
	}

	public List<List<Integer>> permute2(int[] nums) {
		int len = nums.length;

		List<List<Integer>> res = new ArrayList<>(factorial(len));
		if (len == 0) {
			return res;
		}

		// 使用哈希表检测一个数字是否使用过
		Set<Integer> used = new HashSet<>(len);
		Deque<Integer> path = new ArrayDeque<>(len);

		dfs(nums, len, 0, path, used, res);
		return res;
	}

	private int factorial(int n) {
		int res = 1;
		for (int i = 2; i <= n; i++) {
			res *= i;
		}
		return res;
	}

	private void dfs(int[] nums, int len, int depth, Deque<Integer> path, Set<Integer> used, List<List<Integer>> res) {
		if (depth == len) {
			res.add(new ArrayList<>(path));
			return;
		}

		for (int i = 0; i < len; i++) {
			if (!used.contains(i)) {
				used.add(i);
				path.addLast(nums[i]);

				dfs(nums, len, depth + 1, path, used, res);

				path.removeLast();
				used.remove(i);
			}
		}
	}

	public List<List<Integer>> permute(int[] nums) {
		List<List<Integer>> res = new LinkedList();

		List<Integer> output = new ArrayList<Integer>();
		for (int num : nums) {
			output.add(num);
		}
		int n = nums.length;
		backtrackPermute(n, output, res, 0);
		return res;
	}

	public void backtrackPermute(int n, List<Integer> output, List<List<Integer>> res, int first) {
		// 所有数都填完了
		if (first == n)
			res.add(new ArrayList<Integer>(output));
		for (int i = first; i < n; i++) {
			// 动态维护数组
			Collections.swap(output, first, i);
			// 继续递归填下一个数
			backtrackPermute(n, output, res, first + 1);
			// 撤销操作
			Collections.swap(output, first, i);
		}
	}

	// 判断二分图
	@Test
	public void test11() {
		int[][] graph = new int[][] { { 1, 3 }, { 0, 2 }, { 1, 3 }, { 0, 2 } };
		Assert.assertEquals(true, new Solution().isBipartite(graph));
	}

	class Solution {
		private static final int UNCOLORED = 0;
		private static final int RED = 1;
		private static final int GREEN = 2;
		private int[] color;
		private boolean valid;

		public boolean isBipartite(int[][] graph) {
			int n = graph.length;
			valid = true;
			color = new int[n];
			Arrays.fill(color, UNCOLORED);
			for (int i = 0; i < n && valid; i++) {
				if (color[i] == UNCOLORED) {
					dfs(i, RED, graph);
				}
			}
			return valid;
		}

		public void dfs(int node, int c, int[][] graph) {
			color[node] = c; // 改节点为一个色
			int cNei = c == RED ? GREEN : RED; // 其他类节点为相反色
			// 遍历该节点相连的点
			for (int neighbor : graph[node]) {
				if (color[neighbor] == UNCOLORED) {
					dfs(neighbor, cNei, graph);
					if (!valid) {
						return;
					}
				} else if (color[neighbor] != cNei) {
					valid = false;
					return;
				}
			}
		}

		// 广度遍历解法
		public boolean isBipartite2(int[][] graph) {
			int n = graph.length;
			color = new int[n];
			Arrays.fill(color, UNCOLORED);
			for (int i = 0; i < n; ++i) {
				if (color[i] == UNCOLORED) {
					Queue<Integer> queue = new LinkedList<Integer>();
					queue.offer(i);
					color[i] = RED;
					while (!queue.isEmpty()) {
						int node = queue.poll();
						int cNei = color[node] == RED ? GREEN : RED;
						for (int neighbor : graph[node]) {
							if (color[neighbor] == UNCOLORED) {
								queue.offer(neighbor);
								color[neighbor] = cNei;
							} else if (color[neighbor] != cNei) {
								return false;
							}
						}
					}
				}
			}
			return true;
		}
	}

	// 全排列 II
	@Test
	public void test12() {
		int[] nums = new int[] { 1, 1, 2 };
		List<List<Integer>> list = permuteUnique(nums);
		for (List<Integer> l : list) {
			System.out.println(Joiner.on(",").join(l));
		}
	}

	public List<List<Integer>> permuteUnique(int[] nums) {
		int len = nums.length;
		List<List<Integer>> res = new ArrayList<>();
		if (len == 0) {
			return res;
		}
		// 排序（升序或者降序都可以），排序是剪枝的前提
		Arrays.sort(nums);
		boolean[] used = new boolean[len];
		// 使用 Deque 是 Java 官方 Stack 类的建议
		Deque<Integer> path = new ArrayDeque<>(len);
		dfs(nums, len, 0, used, path, res);
		return res;
	}

	private void dfs(int[] nums, int len, int depth, boolean[] used, Deque<Integer> path, List<List<Integer>> res) {
		if (depth == len) {
			res.add(new ArrayList<>(path));
			return;
		}
		for (int i = 0; i < len; ++i) {
			if (used[i]) {
				continue;
			}
			// 剪枝条件：i > 0 是为了保证 nums[i - 1] 有意义
			// 写 !used[i - 1] 是因为 nums[i - 1] 在深度优先遍历的过程中刚刚被撤销选择
			if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
				continue;
			}
			path.addLast(nums[i]);
			used[i] = true;
			dfs(nums, len, depth + 1, used, path, res);
			// 回溯部分的代码，和 dfs 之前的代码是对称的
			used[i] = false;
			path.removeLast();
		}
	}

	// 交错字符串
	@Test
	public void test13() {
		// Assert.assertEquals(true, isInterleave("aabcc","dbbca","aadbbcbcac"));
		Assert.assertEquals(true, isInterleave("ab", "bc", "babc"));
	}

	public boolean isInterleave(String s1, String s2, String s3) {
		int n = s1.length(), m = s2.length(), z = s3.length();
		if (n + m != z) {
			return false;
		}
		// 我们定义 f(i, j) 表示 s_1的前 i 个元素和 s_2的前 j个元素是否能交错组成 s_3的前 i + j 个元素
		boolean[][] dp = new boolean[n + 1][m + 1];
		dp[0][0] = true;
		for (int i = 0; i <= n; i++) {
			for (int j = 0; j <= m; j++) {
				int p = i + j - 1;
				if (i > 0 && s1.charAt(i - 1) == s3.charAt(p)) {
					dp[i][j] = dp[i][j] || dp[i - 1][j];
				}
				if (j > 0 && s2.charAt(j - 1) == s3.charAt(p)) {
					dp[i][j] = dp[i][j] || dp[i][j - 1];
				}
			}
		}
		return dp[n][m];

	}

	// 字母异位词分组
	@Test
	public void test14() {
		String[] strs = new String[] { "eat", "tea", "tan", "ate", "nat", "bat" };
		List<List<String>> res = groupAnagrams(strs);
		for (List<String> list : res) {
			System.out.println(Joiner.on(",").join(list));
		}
	}

	public List<List<String>> groupAnagrams(String[] strs) {
		if (strs.length == 0) {
			return new ArrayList();
		}
		Map<String, List> ans = new HashMap<String, List>();
		int[] count = new int[26];
		for (String s : strs) {
			Arrays.fill(count, 0);
			for (char c : s.toCharArray()) {
				count[c - 'a']++;
			}
			StringBuilder sb = new StringBuilder("");
			for (int i = 0; i < 26; i++) {
				sb.append('#');
				sb.append(count[i]);
			}
			String key = sb.toString();
			if (!ans.containsKey(key)) {
				ans.put(key, new ArrayList());
			}
			ans.get(key).add(s);
		}
		return new ArrayList(ans.values());
	}

}
