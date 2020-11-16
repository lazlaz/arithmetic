package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode15 {
	// 比特位计数
	@Test
	public void test() {
		Assert.assertArrayEquals(new int[] { 0, 1, 1, 2, 1, 2 }, countBits(5));
	}

	public int[] countBits(int num) {
		int[] ans = new int[num + 1];
		int i = 0, b = 1;
		// [0, b) is calculated
		while (b <= num) {
			// generate [b, 2b) or [b, num) from [0, b)
			while (i < b && i + b <= num) {
				ans[i + b] = ans[i] + 1;
				++i;
			}
			i = 0; // reset i
			b <<= 1; // b = 2b
		}
		return ans;
	}

	// 有序数组的平方
	@Test
	public void test2() {
		Assert.assertArrayEquals(new int[] { 4, 9, 9, 49, 121 }, sortedSquares(new int[] { -7, -3, 2, 3, 11 }));
	}

	public int[] sortedSquares(int[] A) {
		int[] res = new int[A.length];
		for (int i = 0; i < A.length; i++) {
			res[i] = A[i] * A[i];
		}
		Arrays.sort(res);
		return res;
	}

	// 比较含退格的字符串
	@Test
	public void test3() {
		Assert.assertEquals(true, backspaceCompare("ab#c", "ad#c"));
		Assert.assertEquals(true, backspaceCompare("y#fo##f", "y#f#o##f"));
		Assert.assertEquals(false, backspaceCompare("abcd", "bbcd"));
	}

	public boolean backspaceCompare(String S, String T) {
		Deque<Character> stack1 = new LinkedList<Character>();
		Deque<Character> stack2 = new LinkedList<Character>();
		for (int i = 0; i < S.length(); i++) {
			if (S.charAt(i) == '#') {
				if (stack1.size() > 0) {
					stack1.pop();
				}
			} else {
				stack1.push(S.charAt(i));
			}
		}
		for (int i = 0; i < T.length(); i++) {
			if (T.charAt(i) == '#') {
				if (stack2.size() > 0) {
					stack2.pop();
				}
			} else {
				stack2.push(T.charAt(i));
			}
		}
		if (stack1.size() != stack2.size()) {
			return false;
		}
		int len = stack1.size();
		for (int i = 0; i < len; i++) {
			if (stack1.pop() != stack2.pop()) {
				return false;
			}
		}
		return true;
	}

	// 删除无效的括号
	@Test
	public void test4() {
		List<String> res = new Solution4().removeInvalidParentheses("()())()");
		System.out.println(Joiner.on(",").join(res));
	}

	// https://leetcode-cn.com/problems/remove-invalid-parentheses/solution/301-shan-chu-wu-xiao-de-gua-hao-java-hui-su-xiang-/
	class Solution4 {
		// 用集合存储所有正确的字符串，可避免重复
		private Set<String> set = new HashSet<>();

		public List<String> removeInvalidParentheses(String s) {
			char[] ss = s.toCharArray();
			int open = 0, close = 0;
			// 获取应该去除的左右括号数
			for (char c : ss) {
				if (c == '(')
					open++;
				else if (c == ')')
					if (open > 0)
						open--;
					else
						close++;
			}
			// 回溯
			backTracking(ss, new StringBuilder(), 0, 0, 0, open, close);
			return new ArrayList(set);
		}

		public void backTracking(char[] ss, StringBuilder sb, int index, int open, int close, int openRem,
				int closeRem) {
			/**
			 * 回溯函数 分别对字符串中的每一位置的字符进行处理，最终获得符合要求的字符串加入集合中
			 * 
			 * @param ss       字符串对应的字符数组
			 * @param sb       储存当前处理过且未去除字符的字符串
			 * @param index    当前处理的字符位置
			 * @param open     当前sb中储存的左括号数
			 * @param close    当前sb中储存的右括号数
			 * @param openRem  当前需要去除的左括号数
			 * @param closeRem 当前需要去除的右括号数
			 */
			// 所有字符都处理完毕
			if (index == ss.length) {
				// 如果应去除的左右括号数都变为0，则将sb插入set
				if (openRem == 0 && closeRem == 0)
					set.add(sb.toString());
				return;
			}
			// 去掉当前位置的字符（括号），并处理下一个字符
			if (ss[index] == '(' && openRem > 0 || ss[index] == ')' && closeRem > 0)
				backTracking(ss, sb, index + 1, open, close, openRem - (ss[index] == '(' ? 1 : 0),
						closeRem - (ss[index] == ')' ? 1 : 0));
			// 不去掉当前位置字符
			// 将当前位置字符插入sb
			sb.append(ss[index]);
			// 当前位置不为括号，则直接处理下一个字符
			if (ss[index] != '(' && ss[index] != ')')
				backTracking(ss, sb, index + 1, open, close, openRem, closeRem);
			// 当前位置为左括号，增加左括号计数，处理下一个字符
			else if (ss[index] == '(')
				backTracking(ss, sb, index + 1, open + 1, close, openRem, closeRem);
			// 当前位置为右括号，且当前左括号计数大于右括号计数，则增加右括号计数，处理下一个字符
			else if (open > close)
				backTracking(ss, sb, index + 1, open, close + 1, openRem, closeRem);
			// 去除当前加入sb的字符
			sb.deleteCharAt(sb.length() - 1);
		}
	}

	// 重排链表
	@Test
	public void test5() {
		ListNode head = Utils.createListNode(new Integer[] { 1, 2, 3, 4, 5, 6 });
		reorderList(head);
		Utils.printListNode(head);
	}

	public void reorderList(ListNode head) {
		if (head == null) {
			return;
		}
		ListNode p = head;
		Deque<ListNode> stack = new LinkedList<ListNode>();
		while (p.next != null) {
			stack.push(p);
			p = p.next;
		}
		ListNode q = head;
		while (q != p && q.next != p) {
			ListNode tmp = q.next;
			q.next = p;
			q = tmp;
			p.next = q;
			p = stack.pop();
		}
		if (q == p) {
			q.next = null;
		}
		if (q.next == p) {
			q.next = p;
			p.next = null;
		}
	}

	// 长按键入
	@Test
	public void test6() {
		Assert.assertEquals(true, isLongPressedName("alex", "aaleex"));
		Assert.assertEquals(false, isLongPressedName("saeed", "ssaaedd"));
		Assert.assertEquals(false, isLongPressedName("alex", "aaleexaaaaaaa"));
		Assert.assertEquals(false, isLongPressedName("alex", "aal"));
		Assert.assertEquals(false, isLongPressedName("dfuyalc", "fuuyallc"));
	}

	public boolean isLongPressedName(String name, String typed) {
		if (typed == null || typed.length() == 0) {
			return false;
		}
		int p = 0;
		int q = 0;
		while (p < typed.length() && q < name.length()) {
			if (typed.charAt(p) == name.charAt(q)) {
				p++;
				q++;
			} else if (p > 0 && typed.charAt(p) == typed.charAt(p - 1)) {
				p++;
			} else if (p > 0 && typed.charAt(p) != typed.charAt(p - 1)) {
				return false;
			} else if (typed.charAt(p) != name.charAt(q)) {
				return false;
			}
		}
		if (p == typed.length() && q != name.length()) {
			return false;
		}
		if (p != typed.length() && q == name.length()) {
			while (p < typed.length()) {
				if (p > 0 && typed.charAt(p) == typed.charAt(p - 1)) {
					p++;
				} else {
					return false;
				}
			}
		}
		return true;
	}

	// 除法求值
	@Test
	public void test7() {
		List<List<String>> equations = new ArrayList<List<String>>();
		equations.add(Arrays.asList("a", "b"));
		equations.add(Arrays.asList("b", "c"));
		equations.add(Arrays.asList("d", "a"));
		double[] values = new double[] { 2.0, 3.0, 2.0 };
		List<List<String>> queries = new ArrayList<List<String>>();
		queries.add(Arrays.asList("a", "c"));
		queries.add(Arrays.asList("b", "a"));
		queries.add(Arrays.asList("a", "e"));
		queries.add(Arrays.asList("a", "a"));
		queries.add(Arrays.asList("x", "x"));
		queries.add(Arrays.asList("d", "b"));
		double[] res = new Solution7().calcEquation(equations, values, queries);
		for (double d : res) {
			System.out.print(d + ",");
		}
	}

	// https://leetcode-cn.com/problems/evaluate-division/solution/zhen-zheng-de-xiao-bai-du-neng-kan-dong-de-bing-ch/
	class Solution7 {
		Map<String, String> parents;
		Map<String, Double> val;

		public String find(String x) {

			if (!x.equals(parents.get(x))) {
				String tmpParent = parents.get(x);
				String root = find(tmpParent);
				double oldVal = val.get(x);
				val.put(x, oldVal * val.get(tmpParent));
				parents.put(x, root);
			}
			return parents.get(x);
		}

		public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
			parents = new HashMap<>();
			val = new HashMap<>();
			int i = 0;
			for (List<String> equation : equations) {
				String from = equation.get(0);
				String to = equation.get(1);
				double cur = values[i];
				if (!parents.containsKey(from) && !parents.containsKey(to)) {
					parents.put(from, to);
					val.put(from, cur);
					parents.put(to, to);
					val.put(to, 1.0);
				} else if (!parents.containsKey(from)) {
					parents.put(from, to);
					val.put(from, cur);
				} else if (!parents.containsKey(to)) {
					parents.put(to, from);
					val.put(to, 1 / cur);
				} else {
					String pa = find(from);
					String pb = find(to);
					if (!pa.equals(pb)) {
						parents.put(pa, pb);
						val.put(pa, cur * val.get(to) / val.get(from));
					}
				}
				i++;
			}
			i = 0;
			double[] res = new double[queries.size()];
			for (List<String> query : queries) {
				String from = query.get(0);
				String to = query.get(1);
				if (!parents.containsKey(from) || !parents.containsKey(to)) {
					res[i++] = -1;
					continue;
				}
				String pa = find(from);
				String pb = find(to);
				if (!pa.equals(pb))
					res[i] = -1;
				else {
					res[i] = val.get(from) / val.get(to);
				}
				i++;
			}
			return res;
		}
	}

	// 划分字母区间
	@Test
	public void test8() {
		List<Integer> res = partitionLabels("ababcbacadefegdehijhklij");
		System.out.println(Joiner.on(",").join(res));
	}

	public List<Integer> partitionLabels(String S) {
		int[] last = new int[26];
		for (int i = 0; i < S.length(); i++) {
			last[S.charAt(i) - 'a'] = i;
		}
		int start = 0, end = 0;
		List<Integer> res = new ArrayList<Integer>();
		for (int i = 0; i < S.length(); i++) {
			end = Math.max(end, last[S.charAt(i) - 'a']);
			if (i == end) {
				res.add(end - start + 1);
				start = end + 1;
			}
		}
		return res;
	}

	// 根据身高重建队列
	@Test
	public void test9() {
		Assert.assertArrayEquals(new int[][] { { 5, 0 }, { 7, 0 }, { 5, 2 }, { 6, 1 }, { 4, 4 }, { 7, 1 } },
				reconstructQueue(new int[][] { { 7, 0 }, { 4, 4 }, { 7, 1 }, { 5, 0 }, { 6, 1 }, { 5, 2 } }));
		Assert.assertArrayEquals(new int[][] { { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 }, { 5, 0 }, { 6, 0 }, { 7, 0 } },
				reconstructQueue(new int[][] { { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 }, { 5, 0 }, { 6, 0 }, { 7, 0 } }));
	}

	// https://leetcode-cn.com/problems/queue-reconstruction-by-height/solution/gen-ju-shen-gao-zhong-jian-dui-lie-by-leetcode/
	public int[][] reconstructQueue(int[][] people) {
		//根据h与k排序，身高大的在前，身高相同的情况下，k小的在前
		for (int i = 0; i < people.length; i++) {
			for (int j = 0; j < people.length - i - 1; j++) {
				if (people[j][0] < people[j + 1][0]) {
					int[] temp = people[j];
					people[j] = people[j + 1];
					people[j + 1] = temp;
				} else if (people[j][0] == people[j + 1][0] && people[j][1] > people[j + 1][1]) {
					int[] temp = people[j];
					people[j] = people[j + 1];
					people[j + 1] = temp;
				}
			}
		}
		//根据排序后的k值，在list对应位置出入数组
		List<int[]> list = new LinkedList<int[]>();
		for (int i = 0; i < people.length; i++) {
			list.add(people[i][1], people[i]);
		}
		int[][] res = new int[people.length][2];
		for (int i = 0; i < people.length; i++) {
			res[i] = list.get(i);
		}
		return res;
	}

	// 视频拼接
	@Test
	public void test10() {
		Assert.assertEquals(3,
				videoStitching(new int[][] { { 0, 2 }, { 4, 6 }, { 8, 10 }, { 1, 9 }, { 1, 5 }, { 5, 9 } }, 10));
	}

	// https://leetcode-cn.com/problems/video-stitching/solution/shi-pin-pin-jie-by-leetcode-solution/
	public int videoStitching(int[][] clips, int T) {
		int[] maxn = new int[T];
		int last = 0, ret = 0, pre = 0;
		for (int[] clip : clips) {
			if (clip[0] < T) {
				maxn[clip[0]] = Math.max(maxn[clip[0]], clip[1]);
			}
		}
		for (int i = 0; i < T; i++) {
			last = Math.max(last, maxn[i]);
			if (i == last) {
				return -1;
			}
			if (i == pre) {
				ret++;
				pre = last;
			}
		}
		return ret;
	}

	// 路径总和 III
	@Test
	public void test11() {
//		{
//			TreeNode root = Utils.createTree(new Integer[] {5,4,8,11,null,13,4,7,2,null,null,5,1});
//			Assert.assertEquals(3, pathSum(root,22));
//		}
//		{
//			TreeNode root = Utils.createTree(new Integer[] {1});
//			Assert.assertEquals(0, pathSum(root,0));
//		}
//		{
//			TreeNode root = Utils.createTree(new Integer[] {-2,null,-3});
//			Assert.assertEquals(1, pathSum(root,-3));
//		}
		{
			TreeNode root = Utils.createTree(new Integer[] { 0, 1, 1 });
			Assert.assertEquals(4, new Solution11().pathSum(root, 1));
		}
	}

	class Solution11 {
		int ans = 0;

		// https://leetcode-cn.com/problems/path-sum-iii/solution/437-lu-jing-zong-he-iiishuang-zhong-dfs-by-sunny_s/
		public int pathSum(TreeNode root, int sum) {
			if (root == null) {
				return ans;
			}
			dfs(root, sum);
			pathSum(root.left, sum);
			pathSum(root.right, sum);
			return ans;
		}

		private void dfs(TreeNode root, int sum) {
			if (root == null) {
				return;
			}
			sum = sum - root.val;
			if (sum == 0) {
				ans++;
			}
			dfs(root.left, sum);
			dfs(root.right, sum);
		}
	}

	// 数组中的最长山脉
	@Test
	public void test12() {
		// Assert.assertEquals(5, longestMountain(new int[] {2,1,4,7,3,2,5}));

//		Assert.assertEquals(0, longestMountain(new int[] {2,2,2}));
//		Assert.assertEquals(0, longestMountain(new int[] {2,3}));

		// Assert.assertEquals(11, longestMountain(new int[] {0,1,2,3,4,5,4,3,2,1,0}));
		Assert.assertEquals(0, longestMountain(new int[] { 0, 1, 2, 3, 4, 5 }));
		Assert.assertEquals(3, longestMountain(new int[] { 0, 1, 0, 2, 2 }));
	}

	// https://leetcode-cn.com/problems/longest-mountain-in-array/solution/shu-zu-zhong-de-zui-chang-shan-mai-by-leetcode-sol/
	public int longestMountain(int[] A) {
		int n = A.length;
		if (n == 0) {
			return 0;
		}
		int[] left = new int[n];
		for (int i = 1; i < n; ++i) {
			left[i] = A[i - 1] < A[i] ? left[i - 1] + 1 : 0;
		}
		int[] right = new int[n];
		for (int i = n - 2; i >= 0; --i) {
			right[i] = A[i + 1] < A[i] ? right[i + 1] + 1 : 0;
		}

		int ans = 0;
		for (int i = 0; i < n; ++i) {
			if (left[i] > 0 && right[i] > 0) {
				ans = Math.max(ans, left[i] + right[i] + 1);
			}
		}
		return ans;
	}

	// 有多少小于当前数字的数字
	@Test
	public void test13() {
		Assert.assertArrayEquals(new int[] { 4, 0, 1, 1, 3 }, smallerNumbersThanCurrent(new int[] { 8, 1, 2, 2, 3 }));
		Assert.assertArrayEquals(new int[] { 0, 0, 0, 0 }, smallerNumbersThanCurrent(new int[] { 7, 7, 7, 7 }));
		Assert.assertArrayEquals(new int[] { 2, 1, 0, 3 }, smallerNumbersThanCurrent(new int[] { 6, 5, 4, 8 }));
	}

	public int[] smallerNumbersThanCurrent(int[] nums) {
		int[] res = new int[nums.length];
		if (nums == null || nums.length == 0) {
			return res;
		}
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums.length; i++) {
			map.put(i, nums[i]);
		}
		// 自定义比较器
		Comparator<Map.Entry<Integer, Integer>> valCmp = new Comparator<Map.Entry<Integer, Integer>>() {
			@Override
			public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
				return o1.getValue() - o2.getValue(); // 降序排序，如果想升序就反过来
			}
		};
		List<Map.Entry<Integer, Integer>> list = new ArrayList<Map.Entry<Integer, Integer>>(map.entrySet());
		Collections.sort(list, valCmp);

		for (int i = 0; i < list.size(); i++) {
			Map.Entry<Integer, Integer> v = list.get(i);
			// 排除相等的情况
			int index = i;
			while (index > 0) {
				Map.Entry<Integer, Integer> v2 = list.get(index - 1);
				if (v.getValue() == v2.getValue()) {
					index--;
				} else {
					break;
				}
			}
			res[v.getKey()] = index;
		}
		return res;
	}

	// 二叉树的前序遍历
	@Test
	public void test14() {
		TreeNode root = Utils.createTree(new Integer[] { 1, null, 2, 3 });
		List<Integer> res = new Solution14().preorderTraversal(root);
		System.out.println(Joiner.on(",").join(res));
	}

	class Solution14 {
		List<Integer> res = new ArrayList<Integer>();

		public List<Integer> preorderTraversal(TreeNode root) {
			if (root == null) {
				return res;
			}
			res.add(root.val);
			preorderTraversal(root.left);
			preorderTraversal(root.right);
			return res;
		}
	}

	// 独一无二的出现次数
	@Test
	public void test15() {
		Assert.assertEquals(true, uniqueOccurrences(new int[] { 1, 2, 2, 1, 1, 3 }));
		Assert.assertEquals(false, uniqueOccurrences(new int[] { 1, 2 }));
		Assert.assertEquals(true, uniqueOccurrences(new int[] { -3, 0, 1, -3, 1, 1, 1, -3, 10, 0 }));
	}

	public boolean uniqueOccurrences(int[] arr) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < arr.length; i++) {
			Integer count = map.getOrDefault(arr[i], 0);
			map.put(arr[i], (++count));
		}
		Collection<Integer> collection = map.values();
		List<Integer> list = new ArrayList<Integer>(collection);
		Collections.sort(list);
		if (list.size() > 1) {
			for (int i = 1; i < list.size(); i++) {
				if (list.get(i) == list.get(i - 1)) {
					return false;
				}
			}
		}
		return true;
	}

	// 目标和
	@Test
	public void test16() {
		// Assert.assertEquals(5, new Solution16().findTargetSumWays(new int[] {1, 1, 1,
		// 1, 1}, 3));
		Assert.assertEquals(5699, new Solution16().findTargetSumWays(
				new int[] { 25, 29, 23, 21, 45, 36, 16, 35, 13, 39, 44, 15, 16, 14, 45, 23, 50, 43, 9, 15 }, 32));
	}

	class Solution16 {
		private int res = 0;

		public int findTargetSumWays(int[] nums, int S) {
			dfs(nums, 0, '+', S, 0);
			dfs(nums, 0, '-', S, 0);
			return res;
		}

		private void dfs(int[] nums, int index, char op, int S, int sum) {
			if (op == '+') {
				sum += nums[index];
			}
			if (op == '-') {
				sum -= nums[index];
			}
			if (index >= (nums.length - 1)) {
				if (sum == S) {
					res++;
				}
				return;
			}
			index++;
			dfs(nums, index, '+', S, sum);
			dfs(nums, index, '-', S, sum);
		}
	}

	// 二叉树的直径
	@Test
	public void test17() {
		// Assert.assertEquals(3, new
		// Solution17().diameterOfBinaryTree(Utils.createTree(new Integer[] { 1, 2, 3,
		// 4, 5 })));

		Assert.assertEquals(8,
				new Solution17().diameterOfBinaryTree(
						Utils.createTree(new Integer[] { 4, -7, -3, null, null, -9, -3, 9, -7, -4, null, 6, null, -6,
								-6, null, null, 0, 6, 5, null, 9, null, null, -1, -4, null, null, null, -2 })));
	}

	// https://leetcode-cn.com/problems/diameter-of-binary-tree/solution/hot-100-9er-cha-shu-de-zhi-jing-python3-di-gui-ye-/
	class Solution17 {
		int max = 0;

		public int diameterOfBinaryTree(TreeNode root) {
			depth(root);
			return max;
		}

		private int depth(TreeNode node) {
			if (node == null) {
				return 0;
			}
			int l = depth(node.left);
			int r = depth(node.right);
			// 每个结点都要去判断左子树+右子树的高度是否大于self.max，更新最大值
			max = Math.max(max, l + r);
			return Math.max(l, r) + 1;
		}
	}

	// 求根到叶子节点数字之和
	@Test
	public void test18() {
		Assert.assertEquals(1026, new Solution18().sumNumbers(Utils.createTree(new Integer[] { 4, 9, 0, 5, 1 })));
	}

	class Solution18_2 {
		public int sumNumbers(TreeNode root) {
			return dfs(root, 0);
		}

		public int dfs(TreeNode root, int prevSum) {
			if (root == null) {
				return 0;
			}
			int sum = prevSum * 10 + root.val;
			if (root.left == null && root.right == null) {
				return sum;
			} else {
				return dfs(root.left, sum) + dfs(root.right, sum);
			}
		}
	}

	class Solution18 {
		private int res;

		public int sumNumbers(TreeNode root) {
			dfs(root, "");
			return res;
		}

		private void dfs(TreeNode root, String str) {
			if (root == null) {
				return;
			}
			str += root.val;
			if (root.left == null && root.right == null) {
				if (str != null && !"".equals(str)) {
					int v = Integer.parseInt(str);
					res += v;
				}
				return;
			}
			dfs(root.left, str);
			dfs(root.right, str);
		}
	}

	// 最短无序连续子数组
	@Test
	public void test19() {
//		Assert.assertEquals(5, findUnsortedSubarray(new int[] {2, 6, 4, 8, 10, 9, 15}));
//		Assert.assertEquals(0, findUnsortedSubarray(new int[] {1,2,3,4,5}));
//		Assert.assertEquals(4, findUnsortedSubarray(new int[] {1,3,2,2,2}));
		Assert.assertEquals(0, findUnsortedSubarray(new int[] { 1, 2, 3, 3, 3 }));
	}

	// https://leetcode-cn.com/problems/shortest-unsorted-continuous-subarray/solution/zui-duan-wu-xu-lian-xu-zi-shu-zu-by-leetcode/
	public int findUnsortedSubarray(int[] nums) {
		int[] snums = nums.clone();
		Arrays.sort(snums);
		int start = snums.length, end = 0;
		for (int i = 0; i < snums.length; i++) {
			if (snums[i] != nums[i]) {
				start = Math.min(start, i);
				end = Math.max(end, i);
			}
		}
		return (end - start >= 0 ? end - start + 1 : 0);

	}

	// 任务调度器
	@Test
	public void test20() {
		Assert.assertEquals(8, leastInterval(new char[] {
				'A','A','A','B','B','B'
		},2)); 
	}
	//https://leetcode-cn.com/problems/task-scheduler/solution/ren-wu-diao-du-qi-by-leetcode/
	public int leastInterval(char[] tasks, int n) {
		int[] map = new int[26];
        for (char c: tasks) {
            map[c - 'A']++;
        }
        Arrays.sort(map);
        int time = 0;
        while (map[25] > 0) {
            int i = 0;
            while (i <= n) {
            	//说明已经没有任务了
                if (map[25] == 0)
                    break;
                if (i < 26 && map[25 - i] > 0)
                    map[25 - i]--;
                time++;
                i++;
            }
            Arrays.sort(map);
        }
        return time;
	}
}
