package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

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
		//Assert.assertEquals(5, new Solution10().slidingPuzzle(new int[][] { { 4, 1, 2 }, { 5, 0, 3 } }));
		Assert.assertEquals(0, new Solution10().slidingPuzzle(new int[][] { { 1,2,3 }, { 4,5,0 } }));
	}

	class Solution10 {
		private HashMap<String, Integer> hm1 = null, hm2 = null;
		
		//双向广搜
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
}
