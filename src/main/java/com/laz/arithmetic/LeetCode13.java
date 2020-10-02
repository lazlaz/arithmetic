package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode13 {
	// 字符串的排列
	@Test
	public void test1() {
		Assert.assertEquals(true, checkInclusion("ab", "eidbaooo"));
		Assert.assertEquals(false, checkInclusion("ab", "eidboaoo"));
		Assert.assertEquals(true, checkInclusion("aab", "eidaabaoo"));
	}

	// https://leetcode-cn.com/problems/permutation-in-string/solution/2ge-shu-zu-hua-dong-chuang-kou-si-xiang-by-gfu/
	public boolean checkInclusion(String s1, String s2) {
		int len1 = s1.length(), len2 = s2.length();
		if (len1 > len2)
			return false;
		int[] ch_count1 = new int[26], ch_count2 = new int[26];
		for (int i = 0; i < len1; ++i) {
			++ch_count1[s1.charAt(i) - 'a'];
			++ch_count2[s2.charAt(i) - 'a'];
		}
		for (int i = len1; i < len2; ++i) {
			if (isEqual(ch_count1, ch_count2))
				return true;
			--ch_count2[s2.charAt(i - len1) - 'a'];
			++ch_count2[s2.charAt(i) - 'a'];
		}
		return isEqual(ch_count1, ch_count2);
	}

	private boolean isEqual(int[] ch_count1, int[] ch_count2) {
		for (int i = 0; i < 26; ++i)
			if (ch_count1[i] != ch_count2[i])
				return false;
		return true;
	}

	// 把二叉搜索树转换为累加树
	@Test
	public void test2() {
		TreeNode root = Utils.createTree(new Integer[] { 2, 0, 3, -4, 1 });
		TreeNode n = convertBST(root);
		Utils.printTreeNode(n);
	}

	int sum = 0;

	public TreeNode convertBST(TreeNode root) {
		if (root != null) {
			convertBST(root.right);
			sum += root.val;
			root.val = sum;
			convertBST(root.left);
		}
		return root;
	}

	// 错误的集合
	@Test
	public void test3() {
		Assert.assertArrayEquals(new int[] { 2, 3 }, findErrorNums(new int[] { 1, 2, 2, 4 }));
		Assert.assertArrayEquals(new int[] { 3, 1 }, findErrorNums(new int[] { 3, 2, 3, 4, 6, 5 }));
	}

	public int[] findErrorNums(int[] nums) {
		Set<Integer> set = new HashSet<Integer>();
		int[] ret = new int[2];
		boolean[] visted = new boolean[nums.length + 1];
		for (int i = 0; i < nums.length; i++) {
			if (set.contains(nums[i])) {
				ret[0] = nums[i];
			}
			set.add(nums[i]);
			visted[nums[i]] = true;
		}
		for (int i = 1; i <= nums.length; i++) {
			if (!visted[i]) {
				ret[1] = i;
			}
		}
		return ret;
	}

	// 监控二叉树
	@Test
	public void test4() {
		Assert.assertEquals(2,
				minCameraCover(Utils.createTree(new Integer[] { 0, 0, null, 0, null, 0, null, null, 0 })));
	}

	public int minCameraCover(TreeNode root) {
		int[] array = dfs(root);
		return array[1];
	}

	public int[] dfs(TreeNode root) {
		if (root == null) {
			return new int[] { Integer.MAX_VALUE / 2, 0, 0 };
		}
		int[] leftArray = dfs(root.left);
		int[] rightArray = dfs(root.right);
		int[] array = new int[3];
		array[0] = leftArray[2] + rightArray[2] + 1;
		array[1] = Math.min(array[0], Math.min(leftArray[0] + rightArray[1], rightArray[0] + leftArray[1]));
		array[2] = Math.min(array[0], leftArray[1] + rightArray[1]);
		return array;
	}

	// 二叉搜索树中的搜索
	@Test
	public void test5() {
		TreeNode root = Utils.createTree(new Integer[] { 4, 2, 7, 1, 3 });
		TreeNode node = searchBST(root, 2);
		System.out.println(node == null ? null : node.val);
	}

	public TreeNode searchBST(TreeNode root, int val) {
		if (root == null) {
			return root;
		}
		if (root.val == val) {
			return root;
		}
		if (root.val > val) {
			return searchBST(root.left, val);
		}
		if (root.val < val) {
			return searchBST(root.right, val);
		}
		return null;
	}

	// 二叉搜索树中的插入操作
	@Test
	public void test6() {
		TreeNode root = Utils.createTree(new Integer[] { 4, 2, 7, 1, 3 });
		root = insertIntoBST(root, 5);
		Utils.printTreeNode(root);
	}

	public TreeNode insertIntoBST(TreeNode root, int val) {
		TreeNode node = root;
		while (node != null) {
			// insert into the right subtree
			if (val > node.val) {
				// insert right now
				if (node.right == null) {
					node.right = new TreeNode(val);
					return root;
				} else {
					node = node.right;
				}
			}
			// insert into the left subtree
			else {
				// insert right now
				if (node.left == null) {
					node.left = new TreeNode(val);
					return root;
				} else {
					node = node.left;
				}
			}
		}
		return new TreeNode(val);
	}

	// 合并二叉树
	@Test
	public void test7() {
		TreeNode t1 = Utils.createTree(new Integer[] { 1, 3, 2, 5 });
		TreeNode t2 = Utils.createTree(new Integer[] { 2, 1, 3, null, 4, null, 7 });
		TreeNode t = mergeTrees(t1, t2);
		Utils.printTreeNode(t);
	}

	public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
		if (t1 == null && t2 == null) {
			return t1;
		}
		if (t1 != null && t2 != null) {
			t1.val = t1.val + t2.val;
		}
		if (t1 == null && t2 != null) {
			t1 = new TreeNode(t2.val);
		}
		if (t2 == null) {
			t1.left = mergeTrees(t1.left, null);
			t1.right = mergeTrees(t1.right, null);
		} else {
			t1.left = mergeTrees(t1.left, t2.left);
			t1.right = mergeTrees(t1.right, t2.right);
		}

		return t1;
	}

	// 打开转盘锁
	@Test
	public void test8() {
		Assert.assertEquals(6, openLock(new String[] { "0201", "0101", "0102", "1212", "2002" }, "0202"));
	}

	public int openLock(String[] deadends, String target) {
		Set<String> dead = new HashSet();
		for (String d : deadends)
			dead.add(d);

		Queue<String> queue = new LinkedList();
		queue.offer("0000");
		queue.offer(null);

		Set<String> seen = new HashSet();
		seen.add("0000");

		int depth = 0;
		while (!queue.isEmpty()) {
			String node = queue.poll();
			if (node == null) {
				depth++;
				if (queue.peek() != null)
					queue.offer(null);
			} else if (node.equals(target)) {
				return depth;
			} else if (!dead.contains(node)) {
				for (int i = 0; i < 4; ++i) {
					for (int d = -1; d <= 1; d += 2) {
						int y = ((node.charAt(i) - '0') + d + 10) % 10;
						String nei = node.substring(0, i) + ("" + y) + node.substring(i + 1);
						if (!seen.contains(nei)) {
							seen.add(nei);
							queue.offer(nei);
						}
					}
				}
			}
		}
		return -1;
	}

	// 二叉搜索树中的众数
	@Test
	public void test9() {
		TreeNode root = Utils.createTree(new Integer[] { 1, null, 2, 2 });
		Assert.assertArrayEquals(new int[] { 2 }, findMode(root));
	}

	Map<Integer, Integer> map = new HashMap<Integer, Integer>();

	public int[] findMode(TreeNode root) {
		bst(root);
		int max = 0;
		for (Integer key : map.keySet()) {
			if (map.get(key) > max) {
				max = map.get(key);
			}
		}
		List<Integer> list = new ArrayList<Integer>();
		for (Integer key : map.keySet()) {
			if (map.get(key) == max) {
				list.add(key);
			}
		}
		int[] ret = new int[list.size()];
		int index = 0;
		for (Integer it : list) {
			ret[index++] = it;
		}
		return ret;
	}

	private void bst(TreeNode root) {
		if (root != null) {
			int count = map.getOrDefault(root.val, 0);
			map.put(root.val, ++count);
			bst(root.left);
			bst(root.right);
		}

	}

	// 滑动谜题
	@Test
	public void test10() {
		// Assert.assertEquals(5, new Solution10().slidingPuzzle(new int[][] { { 4, 1, 2
		// }, { 5, 0, 3 } }));
		Assert.assertEquals(0, new Solution10().slidingPuzzle(new int[][] { { 1, 2, 3 }, { 4, 5, 0 } }));
	}

	class Solution10 {
		private HashMap<String, Integer> hm1 = null, hm2 = null;

		// 双向广搜
		public int slidingPuzzle(int[][] board) {
			Queue<Node> q1 = new LinkedList<Node>();
			Queue<Node> q2 = new LinkedList<Node>();
			hm1 = new HashMap<String, Integer>();
			hm2 = new HashMap<String, Integer>();
			int[][] endArr = new int[][] { { 1, 2, 3 }, { 4, 5, 0 } };

			int x1 = 0, y1 = 0, x2 = 1, y2 = 2;
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 3; j++) {
					if (board[i][j] == 0) {
						x1 = i;
						y1 = j;
					}
				}
			}
			Node node1 = new Node(board, 0, x1, y1);
			Node node2 = new Node(endArr, 0, x2, y2);
			hm1.put(node1.getTuString(), 0);
			hm2.put(node2.getTuString(), 0);
			q1.add(node1);
			q2.add(node2);
			return bfs(q1, q2);
		}

		private int bfs(Queue<Node> q1, Queue<Node> q2) {
			while (!q1.isEmpty() || !q2.isEmpty()) {
				if (!q1.isEmpty()) {
					Node node1 = q1.poll();
					// System.out.println(node1.getTuString()+"----1");
					if (hm2.containsKey(node1.getTuString())) {
						return node1.getSum() + hm2.get(node1.getTuString());
					}
					int x = node1.getX();
					int y = node1.getY();
					if (x > 0) {
						int a[][] = node1.getTuCopy();
						a[x][y] = a[x - 1][y];
						a[x - 1][y] = 0;
						Node n = new Node(a, node1.getSum() + 1, x - 1, y);
						String s = n.getTuString();
						if (hm2.containsKey(s)) {
							return n.getSum() + hm2.get(s);
						}
						if (!hm1.containsKey(s)) {
							hm1.put(s, n.getSum());
							q1.add(n);
						}
					}
					if (x < 1) {
						int a[][] = node1.getTuCopy();
						a[x][y] = a[x + 1][y];
						a[x + 1][y] = 0;
						Node n = new Node(a, node1.getSum() + 1, x + 1, y);
						String s = n.getTuString();
						if (hm2.containsKey(s)) {
							return n.getSum() + hm2.get(s);
						}
						if (!hm1.containsKey(s)) {
							hm1.put(s, n.getSum());
							q1.add(n);
						}
					}
					if (y > 0) {
						int a[][] = node1.getTuCopy();
						a[x][y] = a[x][y - 1];
						a[x][y - 1] = 0;
						Node n = new Node(a, node1.getSum() + 1, x, y - 1);
						String s = n.getTuString();
						if (hm2.containsKey(s)) {
							return n.getSum() + hm2.get(s);
						}
						if (!hm1.containsKey(s)) {
							hm1.put(s, n.getSum());
							q1.add(n);
						}
					}
					if (y < 2) {
						int a[][] = node1.getTuCopy();
						a[x][y] = a[x][y + 1];
						a[x][y + 1] = 0;
						Node n = new Node(a, node1.getSum() + 1, x, y + 1);
						String s = n.getTuString();
						if (hm2.containsKey(s)) {
							return n.getSum() + hm2.get(s);
						}
						if (!hm1.containsKey(s)) {
							hm1.put(s, n.getSum());
							q1.add(n);
						}
					}
				}

				if (!q2.isEmpty()) {
					Node node2 = q2.poll();
					if (hm1.containsKey(node2.getTuString())) {
						return node2.getSum() + hm1.get(node2.getTuString());
					}
					int x = node2.getX();
					int y = node2.getY();
					if (x > 0) {
						int a[][] = node2.getTuCopy();
						a[x][y] = a[x - 1][y];
						a[x - 1][y] = 0;
						Node n = new Node(a, node2.getSum() + 1, x - 1, y);
						String s = n.getTuString();
						if (hm1.containsKey(s)) {
							return n.getSum() + hm1.get(s);
						}
						if (!hm2.containsKey(s)) {
							hm2.put(s, n.getSum());
							q2.add(n);
						}
					}
					if (x < 1) {
						int a[][] = node2.getTuCopy();
						a[x][y] = a[x + 1][y];
						a[x + 1][y] = 0;
						Node n = new Node(a, node2.getSum() + 1, x + 1, y);
						String s = n.getTuString();
						if (hm1.containsKey(s)) {
							return n.getSum() + hm1.get(s);
						}
						if (!hm2.containsKey(s)) {
							hm2.put(s, n.getSum());
							q2.add(n);
						}
					}
					if (y > 0) {
						int a[][] = node2.getTuCopy();
						a[x][y] = a[x][y - 1];
						a[x][y - 1] = 0;
						Node n = new Node(a, node2.getSum() + 1, x, y - 1);
						String s = n.getTuString();
						if (hm1.containsKey(s)) {
							return n.getSum() + hm1.get(s);
						}
						if (!hm2.containsKey(s)) {
							hm2.put(s, n.getSum());
							q2.add(n);
						}
					}
					if (y < 2) {
						int a[][] = node2.getTuCopy();
						a[x][y] = a[x][y + 1];
						a[x][y + 1] = 0;
						Node n = new Node(a, node2.getSum() + 1, x, y + 1);
						String s = n.getTuString();
						if (hm1.containsKey(s)) {
							return n.getSum() + hm1.get(s);
						}
						if (!hm2.containsKey(s)) {
							hm2.put(s, n.getSum());
							q2.add(n);
						}
					}
				}
			}
			return -1;
		}

		class Node {
			int tu[][] = new int[2][3];
			int sum = 0;
			int x = 0, y = 0;

			public Node(int[][] tu, int sum, int x, int y) {
				super();
				this.tu = tu;
				this.sum = sum;
				this.x = x;
				this.y = y;
			}

			public int[][] getTuCopy() {
				int a[][] = new int[2][3];
				for (int i = 0; i < 2; i++)
					for (int j = 0; j < 3; j++)
						a[i][j] = tu[i][j];
				return a;
			}

			public String getTuString() {
				StringBuffer sb = new StringBuffer("");
				for (int i = 0; i < 2; i++)
					for (int j = 0; j < 3; j++)
						sb.append(tu[i][j]);
				return sb.toString();
			}

			public void setTu(int[][] tu) {
				this.tu = tu;
			}

			public int getSum() {
				return sum;
			}

			public void setSum(int sum) {
				this.sum = sum;
			}

			public int getX() {
				return x;
			}

			public void setX(int x) {
				this.x = x;
			}

			public int getY() {
				return y;
			}

			public void setY(int y) {
				this.y = y;
			}
		}
	}

	// 从中序与后序遍历序列构造二叉树
	@Test
	public void test11() {
		int[] inorder = new int[] { 9, 3, 15, 20, 7 };
		int[] postorder = new int[] { 9, 15, 7, 20, 3 };
		TreeNode root = new Solution11().buildTree(inorder, postorder);
		Utils.printTreeNode(root);
	}

	class Solution11 {
		int post_idx;
		int[] postorder;
		int[] inorder;
		Map<Integer, Integer> idx_map = new HashMap<Integer, Integer>();

		public TreeNode helper(int in_left, int in_right) {
			// 如果这里没有节点构造二叉树了，就结束
			if (in_left > in_right) {
				return null;
			}

			// 选择 post_idx 位置的元素作为当前子树根节点
			int root_val = postorder[post_idx];
			TreeNode root = new TreeNode(root_val);

			// 根据 root 所在位置分成左右两棵子树
			int index = idx_map.get(root_val);

			// 下标减一
			post_idx--;
			// 构造右子树
			root.right = helper(index + 1, in_right);
			// 构造左子树
			root.left = helper(in_left, index - 1);
			return root;
		}

		public TreeNode buildTree(int[] inorder, int[] postorder) {
			this.postorder = postorder;
			this.inorder = inorder;
			// 从后序遍历的最后一个元素开始
			post_idx = postorder.length - 1;

			// 建立（元素，下标）键值对的哈希表
			int idx = 0;
			for (Integer val : inorder) {
				idx_map.put(val, idx++);
			}

			return helper(0, inorder.length - 1);
		}
	}

	// 路径总和 II
	@Test
	public void test12() {
		TreeNode root = Utils.createTree(new Integer[] { 5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1 });
		List<List<Integer>> ret = pathSum(root, 22);
		for (List<Integer> list : ret) {
			System.out.println(Joiner.on(",").join(list));
		}
	}

	public List<List<Integer>> pathSum(TreeNode root, int sum) {
		List<List<Integer>> ret = new ArrayList<List<Integer>>();
		if (root == null) {
			return ret;
		}
		pathSum(root, sum, ret, new LinkedList<Integer>());
		return ret;
	}

	private void pathSum(TreeNode root, int sum, List<List<Integer>> ret, LinkedList<Integer> arrayList) {
		if (root == null) {
			return;
		}
		arrayList.add(root.val);
		if (root != null && root.left == null && root.right == null) {
			if (sum == root.val) {
				ret.add(new ArrayList(arrayList));
			}
			return;
		}
		if (root.left != null) {
			pathSum(root.left, (sum - root.val), ret, arrayList);
			arrayList.removeLast();
		}
		if (root.right != null) {
			pathSum(root.right, (sum - root.val), ret, arrayList);
			arrayList.removeLast();
		}
	}

	// 考场就座
	@Test
	public void test13() {
		ExamRoom obj = new ExamRoom(10);
		obj.seat();
		obj.seat();
		obj.seat();
		obj.seat();
		obj.leave(4);
		obj.seat();
	}

	class ExamRoom {
		int N;
		TreeSet<Integer> students;

		public ExamRoom(int N) {
			this.N = N;
			students = new TreeSet();
		}

		public int seat() {
			// Let's determine student, the position of the next
			// student to sit down.
			int student = 0;
			if (students.size() > 0) {
				// Tenatively, dist is the distance to the closest student,
				// which is achieved by sitting in the position 'student'.
				// We start by considering the left-most seat.
				int dist = students.first();
				Integer prev = null;
				for (Integer s : students) {
					if (prev != null) {
						// For each pair of adjacent students in positions (prev, s),
						// d is the distance to the closest student;
						// achieved at position prev + d.
						int d = (s - prev) / 2;
						if (d > dist) {
							dist = d;
							student = prev + d;
						}
					}
					prev = s;
				}

				// Considering the right-most seat.
				if (N - 1 - students.last() > dist)
					student = N - 1;
			}

			// Add the student to our sorted TreeSet of positions.
			students.add(student);
			return student;
		}

		public void leave(int p) {
			students.remove(p);
		}

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

	// 填充每个节点的下一个右侧节点指针 II
	@Test
	public void test14() {
		Node root = new Node();
		root.val = 1;

		Node left = new Node();
		left.val = 2;

		Node right = new Node();
		right.val = 3;

		root.left = left;
		root.right = right;

		Node n = connect(root);
		System.out.println(n);
	}

	public Node connect(Node root) {
		if (root == null) {
			return null;
		}
		Queue<Node> queue = new LinkedList<Node>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			int n = queue.size();
			Node last = null;
			for (int i = 1; i <= n; ++i) {
				Node f = queue.poll();
				if (f.left != null) {
					queue.offer(f.left);
				}
				if (f.right != null) {
					queue.offer(f.right);
				}
				if (i != 1) {
					last.next = f;
				}
				last = f;
			}
		}
		return root;

	}

	// 爱吃香蕉的珂珂
	@Test
	public void test15() {
		Assert.assertEquals(23, minEatingSpeed(new int[] { 30, 11, 23, 4, 20 }, 6));
	}

	public int minEatingSpeed(int[] piles, int H) {
		int lo = 1;
		int hi = 1_000_000_000;
		while (lo < hi) {
			int mi = (lo + hi) / 2;
			if (!possible(piles, H, mi))
				lo = mi + 1;
			else
				hi = mi;
		}

		return lo;
	}

	// Can Koko eat all bananas in H hours with eating speed K?
	public boolean possible(int[] piles, int H, int K) {
		int time = 0;
		for (int p : piles)
			time += (p - 1) / K + 1;
		return time <= H;
	}

	// 宝石与石头
	@Test
	public void test16() {
		Assert.assertEquals(3, numJewelsInStones("aA", "aAAbbbb"));
	}

	public int numJewelsInStones(String J, String S) {
		Set<Character> sets = new HashSet();
		for (int i = 0; i < J.length(); i++) {
			sets.add(J.charAt(i));
		}
		int count = 0;
		for (int i = 0; i < S.length(); i++) {
			if (sets.contains(S.charAt(i))) {
				count++;
			}
		}
		return count;
	}

	// 秋叶收藏集
	@Test
	public void test17() {
		Assert.assertEquals(2, minimumOperations("rrryyyrryyyrr"));
	}

	public int minimumOperations(String leaves) {
		// 状态 0 和状态 2 分别表示前面和后面的红色部分，状态 1 表示黄色部分
		int n = leaves.length();
		int[][] f = new int[n][3];
		f[0][0] = leaves.charAt(0) == 'y' ? 1 : 0;
		f[0][1] = f[0][2] = f[1][2] = Integer.MAX_VALUE;
		for (int i = 1; i < n; ++i) {
			int isRed = leaves.charAt(i) == 'r' ? 1 : 0;
			int isYellow = leaves.charAt(i) == 'y' ? 1 : 0;
			f[i][0] = f[i - 1][0] + isYellow;
			f[i][1] = Math.min(f[i - 1][0], f[i - 1][1]) + isRed;
			if (i >= 2) {
				f[i][2] = Math.min(f[i - 1][1], f[i - 1][2]) + isYellow;
			}
		}
		return f[n - 1][2];
	}

	// 二叉树的后序遍历 （迭代遍历）
	@Test
	public void test18() {
		TreeNode root = Utils.createTree(new Integer[] { 3, 9, 4, null, null, 5, 7 });
		List<Integer> ret = postorderTraversal(root);
		System.out.println(Joiner.on(",").join(ret));
	}

	public List<Integer> postorderTraversal(TreeNode root) {
		List<Integer> res = new ArrayList<Integer>();
		if (root == null) {
			return res;
		}
		Deque<TreeNode> stack = new LinkedList<TreeNode>();
		TreeNode prev = null;
		while (root != null || !stack.isEmpty()) {
			while (root != null) {
				stack.push(root);
				root = root.left;
			}
			root = stack.pop();
			if (root.right == null || root.right == prev) {
				res.add(root.val);
				prev = root;
				root = null;
			} else {
				stack.push(root);
				root = root.right;
			}
		}
		return res;
	}

	// 石子游戏
	@Test
	public void test19() {
		Assert.assertEquals(true, stoneGame(new int[] { 5, 3, 4, 5 }));
	}

	// https://leetcode-cn.com/problems/stone-game/solution/ji-yi-hua-di-gui-dong-tai-gui-hua-shu-xue-jie-java/
	public boolean stoneGame(int[] piles) {
		int len = piles.length;
		int[][] memo = new int[len][len];
		for (int i = 0; i < len; i++) {
			// 由于是相对分数，有可能是在负值里面选较大者，因此初始化的时候不能为 0
			Arrays.fill(memo[i], Integer.MIN_VALUE);
			memo[i][i] = piles[i];
		}
		return stoneGame(piles, 0, len - 1, memo) > 0;
	}

	/**
	 * 计算子区间 [left, right] 里先手能够得到的分数
	 *
	 * @param piles
	 * @param left
	 * @param right
	 * @return
	 */
	private int stoneGame(int[] piles, int left, int right, int[][] memo) {
		if (left == right) {
			return piles[left];
		}
		if (memo[left][right] != Integer.MIN_VALUE) {
			return memo[left][right];
		}

		int chooseLeft = piles[left] - stoneGame(piles, left + 1, right, memo);
		int chooseRight = piles[right] - stoneGame(piles, left, right - 1, memo);
		int res = Math.max(chooseLeft, chooseRight);
		memo[left][right] = res;
		return res;
	}

	// 煎饼排序
	@Test
	public void test20() {
		int[] arr = new int[] { 3, 2, 4, 1 };
		List<Integer> ret = pancakeSort(arr);
		System.out.println(Joiner.on(",").join(ret));
	}

	public List<Integer> pancakeSort(int[] A) {
		List<Integer> ans = new ArrayList<Integer>();
		int N = A.length;
		Integer[] B = new Integer[N];
		for (int i = 0; i < N; ++i)
			B[i] = i + 1;
		Arrays.sort(B, (i, j) -> A[j - 1] - A[i - 1]);

		for (int i : B) {
			for (int f : ans)
				if (i <= f)
					i = f + 1 - i; // 执行一次煎饼翻转操作 f，会将位置在 i, i <= f 的元素翻转到位置 f+1 - i 上
			ans.add(i);
			ans.add(N--);
		}
		return ans;
	}
}
