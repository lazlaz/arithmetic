package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class LeetCode17 {
	// 按奇偶排序数组 II
	@Test
	public void test1() {
		Assert.assertArrayEquals(new int[] { 4, 5, 2, 7 }, sortArrayByParityII(new int[] { 4, 2, 5, 7 }));
	}

	public int[] sortArrayByParityII(int[] A) {
		int[] res = new int[A.length];
		int p = 0, q = 1;
		for (int i = 0; i < A.length; i++) {
			if (A[i] % 2 == 0) {
				res[p] = A[i];
				p = p + 2;
			} else {
				res[q] = A[i];
				q = q + 2;
			}
		}
		return res;
	}

	// 移掉K位数字
	@Test
	public void test2() {
		Assert.assertEquals("1219", removeKdigits("1432219", 3));
	}

	// https://leetcode-cn.com/problems/remove-k-digits/solution/yi-zhao-chi-bian-li-kou-si-dao-ti-ma-ma-zai-ye-b-5/
	public String removeKdigits(String num, int k) {
		Deque<Character> stack = new LinkedList<Character>();
		for (int i = 0; i < num.length(); i++) {
			char c = num.charAt(i);
			while (!stack.isEmpty() && k > 0 && stack.peekLast() > c) {
				stack.pollLast();
				k--;
			}
			stack.offerLast(c);
		}
		// 如果K还没有减完，继续移除
		for (int i = 0; i < k; ++i) {
			stack.pollLast();
		}

		StringBuilder ret = new StringBuilder();
		boolean leadingZero = true;
		while (!stack.isEmpty()) {
			char digit = stack.pollFirst();
			if (leadingZero && digit == '0') {
				continue;
			}
			leadingZero = false;
			ret.append(digit);
		}
		return ret.length() == 0 ? "0" : ret.toString();

	}

	// 距离顺序排列矩阵单元格
	@Test
	public void test3() {
		Assert.assertArrayEquals(new int[][] { { 0, 1 }, { 0, 0 }, { 1, 1 }, { 1, 0 } }, allCellsDistOrder(2, 2, 0, 1));
	}

	public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
		List<int[]> list = new ArrayList<int[]>();
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				list.add(new int[] { i, j });
			}
		}
		Collections.sort(list, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				int distance1 = Math.abs(o1[0] - r0) + Math.abs(o1[1] - c0);
				int distance2 = Math.abs(o2[0] - r0) + Math.abs(o2[1] - c0);
				return distance1 - distance2;
			}
		});
		int[][] res = new int[R * C][2];
		return list.toArray(res);
	}

	// 单词搜索 II
	@Test
	public void test4() {
		{
			List<String> list = new Solution4().findWords(new char[][] { { 'o', 'a', 'a', 'n' }, { 'e', 't', 'a', 'e' },
					{ 'i', 'h', 'k', 'r' }, { 'i', 'f', 'l', 'v' } }, new String[] { "oath", "pea", "eat", "rain" });
			Assert.assertArrayEquals(new String[] { "oath", "eat" }, list.toArray(new String[] {}));
		}
		{
			List<String> list = new Solution4().findWords(new char[][] { { 'a' }, { 'a' } }, new String[] { "aaa" });
			Assert.assertArrayEquals(new String[] {}, list.toArray(new String[] {}));
		}

	}

	class Solution4_2 {

		private Set<String> result = new HashSet<>();

		public List<String> findWords(char[][] board, String[] words) {
			Trie trie = new Trie();
			for (String word : words) {
				trie.insert(word);
			}
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					dfs(trie, board, new StringBuilder(), i, j, new int[board.length][board[0].length]);
				}
			}
			return new ArrayList<>(result);
		}

		private void dfs(Trie parent, char[][] board, StringBuilder prefix, int i, int j, int[][] used) {
			if (i < 0 || j < 0 || i >= board.length || j >= board[0].length || used[i][j] == 1) {
				return;
			}
			Trie trie = parent.indexOf(board[i][j]);
			if (trie == null) {
				return;
			}
			used[i][j] = 1;
			prefix.append(board[i][j]);
			if (trie.isEnd()) {
				result.add(prefix.toString());
			}
			dfs(trie, board, prefix, i - 1, j, used);
			dfs(trie, board, prefix, i + 1, j, used);
			dfs(trie, board, prefix, i, j - 1, used);
			dfs(trie, board, prefix, i, j + 1, used);
			used[i][j] = 0;
			prefix.deleteCharAt(prefix.length() - 1);
		}

		class Trie {
			private Trie[] nexts;

			private boolean isEnd;

			public Trie() {
				nexts = new Trie[26];
				isEnd = false;
			}

			public void insert(String word) {
				Trie temp = this;
				for (int i = 0; i < word.length(); i++) {
					temp = temp.put(word.charAt(i));
				}
				temp.isEnd(true);
			}

			public Trie put(Character c) {
				if (nexts[c - 'a'] == null) {
					nexts[c - 'a'] = new Trie();
				}
				return nexts[c - 'a'];
			}

			private Trie indexOf(char c) {
				return nexts[c - 'a'];
			}

			public Trie indexOf(String prefix) {
				Trie temp = this;
				for (int i = 0; i < prefix.length(); i++) {
					if (temp == null) {
						return null;
					}
					temp = temp.indexOf(prefix.charAt(i));
				}
				return temp;
			}

			public void isEnd(boolean isEnd) {
				this.isEnd = isEnd;
			}

			public boolean isEnd() {
				return this.isEnd;
			}
		}
	}

	// 前缀树+剪枝版本
	class Solution4 {
		private int rows, cols;
		private char[][] board;
		// Trie的根节点
		private Node root;
		// DFS遍历方向
		private int[][] direct = { { 0, 1, 0, -1 }, { -1, 0, 1, 0 } };
		private Set<String> ans;
		// 标记是否已经访问
		private boolean[][] marked;

		// Trie需要的节点
		class Node {
			HashMap<Character, Node> children;
			String word;

			public Node(String word) {
				this.children = new HashMap<>();
				this.word = word;
			}

			public Node() {
				this.children = new HashMap<>();
				this.word = null;
			}
		}

		// Trie的操作
		private void delete(String str) {
			delete(root, str, 0);
		}

		private Node delete(Node current, String str, int d) {
			if (current == null)
				return null;
			if (d == str.length()) {
				if (current.children.isEmpty())
					return null;
				current.word = null;
				return current;
			}
			char c = str.charAt(d);
			Node next = delete(current.children.get(c), str, d + 1);
			current.children.put(c, next);
			if (next == null && current.word == null && current.children.size() <= 1)
				return null;
			return current;
		}

		private void insert(String str) {
			insert(root, str, 0);
		}

		private Node insert(Node current, String str, int d) {
			if (current == null)
				current = new Node();
			if (d == str.length()) {
				current.word = str;
				return current;
			}
			char c = str.charAt(d);
			current.children.put(c, insert(current.children.get(c), str, d + 1));
			return current;
		}

		public List<String> findWords(char[][] board, String[] words) {
			this.rows = board.length;
			this.cols = board[0].length;
			this.board = board;
			this.root = new Node();
			this.ans = new HashSet<>();
			this.marked = new boolean[rows][cols];
			int len = words.length;

			for (int i = 0; i < len; i++) {
				insert(words[i]);
			}
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					char c = board[i][j];
					if (root.children.get(c) != null)
						dfs(i, j, root.children.get(c));
				}
			}
			return new ArrayList<>(this.ans);
		}

		private void dfs(int row, int col, Node current) {
			if (current == null)
				return;
			if (current.word != null) {
				this.ans.add(current.word);
				delete(current.word);
			}
			if (current.children.isEmpty())
				return;
			marked[row][col] = true;
			for (int i = 0; i < 4; i++) {
				int newRow = row + direct[0][i];
				int newCol = col + direct[1][i];
				if (valid(newRow, newCol)) {
					char c = board[newRow][newCol];
					if (current.children.get(c) != null)
						dfs(newRow, newCol, current.children.get(c));
				}
			}
			marked[row][col] = false;
		}

		private boolean valid(int row, int col) {
			return row < rows && row >= 0 && col < cols && col >= 0 && !marked[row][col];
		}
	}

	// 摆动排序 II
	@Test
	public void test5() {
//		{
//			int[] res = new int[] { 1, 5, 1, 1, 6, 4 };
//			new Solution5_2().wiggleSort(res);
//			Assert.assertArrayEquals(new int[] { 1, 6, 1, 5, 1, 4 }, res);
//		}
//
//		{
//			int[] res = new int[] { 1, 3, 2, 2, 3, 1 };
//			new Solution5_2().wiggleSort(res);
//			Assert.assertArrayEquals(new int[] { 2, 3, 1, 3, 1, 2 }, res);
//		}
//		{
//			int[] res = new int[] { 4, 5, 5, 6 };
//			new Solution5_2().wiggleSort(res);
//			Assert.assertArrayEquals(new int[] { 5, 6, 4, 5 }, res);
//		}
		{
			int[] res = new int[] { 2, 1, 1, 2, 1, 3, 3, 3, 1, 3, 1, 3, 2 };
			new Solution5_2().wiggleSort(res);
			Assert.assertArrayEquals(new int[] { 2, 3, 2, 3, 1, 3, 1, 3, 1, 3, 1, 2, 1 }, res);
		}

	}

	// https://leetcode-cn.com/problems/wiggle-sort-ii/solution/yi-bu-yi-bu-jiang-shi-jian-fu-za-du-cong-onlognjia/
	// https://leetcode-cn.com/problems/wiggle-sort-ii/solution/bai-dong-pai-xu-ii-jie-ti-bao-gao-shi-jian-fu-za-d/
	class Solution5 {
		int n = -1;

		public void wiggleSort(int[] nums) {
			// 找到中位数索引
			int midIndex = this.quickSelect(nums, 0, nums.length - 1);
			// 找到中位数
			int mid = nums[midIndex];
			n = nums.length;
			// 三分法
			for (int i = 0, j = 0, k = nums.length - 1; j <= k;) {
				if (nums[V(j)] > mid) {
					swap(nums, V(j++), V(i++));
				} else if (nums[V(j)] < mid) {
					swap(nums, V(j), V(k--));
				} else {
					j++;
				}
			}
		}

		public int V(int i) {
			return (1 + 2 * (i)) % (n | 1);
		}

		public void swap(int[] nums, int i, int j) {
			int t = nums[i];
			nums[i] = nums[j];
			nums[j] = t;
		}

		public int quickSelect(int[] nums, int left, int right) {
			int pivot = nums[left];
			int l = left;
			int r = right;
			while (l < r) {
				while (l < r && nums[r] >= pivot) {
					r--;
				}
				if (l < r) {
					nums[l++] = nums[r];
				}
				while (l < r && nums[l] <= pivot) {
					l++;
				}
				if (l < r) {
					nums[r--] = nums[l];
				}
			}
			nums[l] = pivot;
			if (l == nums.length / 2) {
				return l;
			} else if (l > nums.length / 2) {
				return this.quickSelect(nums, left, l - 1);
			} else {
				return this.quickSelect(nums, l + 1, right);
			}
		}
	}

	class Solution5_2 {
		public void wiggleSort(int[] nums) {
			int n = nums.length;
			Arrays.sort(nums);
			int[] A = new int[n %2==0?n/2:n/2+1];
			int[] B = new int[n / 2];
			System.arraycopy(nums, 0, A, 0, A.length);
			System.arraycopy(nums, A.length, B, 0, B.length);
			int indexA = A.length - 1;
			int indexB = B.length - 1;
			for (int i = 0; i < n; i++) {
				nums[i] = i % 2 == 0 ? A[indexA--] : B[indexB--];
			}
		}
	}

}
