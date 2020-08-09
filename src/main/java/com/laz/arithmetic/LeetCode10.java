package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode10 {
	// 颜色分类
	@Test
	public void test1() {
		int[] arr = new int[] { 2, 0, 2, 1, 1, 0 };
		sortColors(arr);
		Assert.assertArrayEquals(new int[] { 0, 0, 1, 1, 2, 2 }, arr);
	}

	public void sortColors(int[] nums) {
		int p0 = 0, curr = 0;
		int p2 = nums.length - 1;
		while (curr <= p2) {
			if (nums[curr] == 0) {
				// 交换p0 curr p0++ curr++
				int temp = nums[p0];
				nums[p0] = nums[curr];
				nums[curr] = temp;
				p0++;
				curr++;
			} else if (nums[curr] == 1) {
				curr++;
			} else if (nums[curr] == 2) {
				// 交换p2 curr p2-- curr不变
				int temp = nums[p2];
				nums[p2] = nums[curr];
				nums[curr] = temp;
				p2--;
			}
		}
	}

	// 打家劫舍 III
	@Test
	public void test2() {
		TreeNode node = Utils.createTree(new Integer[] { 3, 2, 3, null, 3, null, 1 });
		Assert.assertEquals(7, rob(node));
	}

	// 根节点被选中最大的值
	Map<TreeNode, Integer> f = new HashMap<TreeNode, Integer>();
	// 根节点不被选择最大的值
	Map<TreeNode, Integer> g = new HashMap<TreeNode, Integer>();

	public int rob(TreeNode root) {
		dfs(root);
		return Math.max(f.getOrDefault(root, 0), g.getOrDefault(root, 0));
	}

	private void dfs(TreeNode node) {
		if (node == null) {
			return;
		}
		dfs(node.left);
		dfs(node.right);
		// 根节点被选中的值等于根节点的值加上左子节点不被选择+右子节点不被选择的值
		f.put(node, node.val + g.getOrDefault(node.left, 0) + g.getOrDefault(node.right, 0));

		int left = Math.max(f.getOrDefault(node.left, 0), g.getOrDefault(node.left, 0));
		int right = Math.max(f.getOrDefault(node.right, 0), g.getOrDefault(node.right, 0));
		g.put(node, left + right);
	}

	// 组合
	@Test
	public void test3() {
		List<List<Integer>> res = combine(6, 3);
		for (List<Integer> list : res) {
			System.out.println(Joiner.on(",").join(list));
		}
	}

	private int n;
	private int k;
	List<List<Integer>> output = new LinkedList<List<Integer>>();

	private void backtrack(int first, LinkedList<Integer> curr) {
		if (curr.size() == k) {
			output.add(new LinkedList<Integer>(curr));
		}
		for (int i = first; i < n + 1; i++) {
			curr.add(i);
			backtrack(i + 1, curr);
			curr.removeLast();
		}
	}

	public List<List<Integer>> combine(int n, int k) {
		this.n = n;
		this.k = k;
		backtrack(1, new LinkedList<Integer>());
		return output;
	}

	// 回文对
	@Test
	public void test4() {
		String[] words = new String[] { "a", "" };
		List<List<Integer>> res = palindromePairs(words);
		for (List<Integer> list : res) {
			System.out.println(Joiner.on(",").join(list));
		}
	}

	// 存放每个单词反转后的值
	List<String> wordsRev = new ArrayList<String>();
	// 记录每个反转后的值索引
	Map<String, Integer> indices = new HashMap<String, Integer>();

	public List<List<Integer>> palindromePairs(String[] words) {
		List<List<Integer>> ret = new ArrayList<List<Integer>>();
		if (words == null || words.length == 0) {
			return ret;
		}
		int n = words.length;
		for (String word : words) {
			wordsRev.add(new StringBuffer().append(word).reverse().toString());
		}
		for (int i = 0; i < n; i++) {
			indices.put(wordsRev.get(i), i);
		}
		for (int i = 0; i < n; i++) {
			String word = words[i];
			int m = words[i].length();
			if (m == 0) {
				continue;
			}
			for (int j = 0; j <= m; j++) {
				// 一个单词是右侧是回文，如果左侧找到索引，则 i,leftId组合是回文
				if (isPalindrome(word, j, m - 1)) {
					int leftId = findWord(word, 0, j - 1);
					if (leftId != -1 && leftId != i) {
						ret.add(Arrays.asList(i, leftId));
					}
				}
				// 一个单词是左侧是回文，如果右侧找到索引，则 rightId,i组合是回文
				if (j != 0 && isPalindrome(word, 0, j - 1)) {
					int rightId = findWord(word, j, m - 1);
					if (rightId != -1 && rightId != i) {
						ret.add(Arrays.asList(rightId, i));
					}
				}
			}
		}
		return ret;
	}

	// 判断范围内是否为回文
	public boolean isPalindrome(String s, int left, int right) {
		int len = right - left + 1;
		for (int i = 0; i < len / 2; i++) {
			if (s.charAt(left + i) != s.charAt(right - i)) {
				return false;
			}
		}
		return true;
	}

	// 根据回文找索引
	public int findWord(String s, int left, int right) {
		return indices.getOrDefault(s.substring(left, right + 1), -1);
	}

	// 单词搜索
	@Test
	public void test5() {
		Assert.assertEquals(true, new Solution5().exist(
				new char[][] { { 'A', 'B', 'C', 'E' }, { 'S', 'F', 'C', 'S' }, { 'A', 'D', 'E', 'E' } }, "ABCCED"));
		Assert.assertEquals(false, new Solution5().exist(
				new char[][] { { 'A', 'B', 'C', 'E' }, { 'S', 'F', 'C', 'S' }, { 'A', 'D', 'E', 'E' } }, "ACCED"));

	}

	class Solution5 {
		private boolean[][] marked;
		// x-1,y
		// x,y-1 x,y x,y+1
		// x+1,y
		private int[][] direction = { { -1, 0 }, { 0, -1 }, { 0, 1 }, { 1, 0 } };
		// 盘面上有多少行
		private int m;
		// 盘面上有多少列
		private int n;
		private String word;
		private char[][] board;

		public boolean exist(char[][] board, String word) {
			m = board.length;
			if (m == 0) {
				return false;
			}
			n = board[0].length;
			marked = new boolean[m][n];
			this.word = word;
			this.board = board;

			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					// 深度遍历+回溯
					if (dfs(i, j, 0)) {
						return true;
					}
				}
			}
			return false;
		}

		private boolean dfs(int i, int j, int start) {
			if (start == word.length() - 1) {
				return board[i][j] == word.charAt(start);
			}
			if (board[i][j] == word.charAt(start)) {
				marked[i][j] = true;
				// 四个方向尝试
				for (int k = 0; k < 4; k++) {
					int newX = i + direction[k][0];
					int newY = j + direction[k][1];
					if (inArea(newX, newY) && !marked[newX][newY]) {
						if (dfs(newX, newY, start + 1)) {
							return true;
						}
					}
				}
				marked[i][j] = false;
			}
			return false;
		}

		// 是否在矩阵范围内
		private boolean inArea(int x, int y) {
			return x >= 0 && x < m && y >= 0 && y < n;
		}
	}

	// 删除排序数组中的重复项 II
	@Test
	public void test6() {
		Assert.assertEquals(6, removeDuplicates(new int[] { 1, 1, 2, 2, 3, 3, 3 }));
	}

	public int removeDuplicates(int[] nums) {
		int pos = 0;
		int index = 0;
		int len = nums.length;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		while (pos < len && index < len) {
			int v = nums[index];
			// 利用双指针进行赋值
			nums[pos] = nums[index];
			int count = map.getOrDefault(v, 0);
			if (count == 2) {
				// 改位置不需要了,交换

			} else {
				map.put(v, count + 1);
				pos++;
			}
			index++;
		}
		int ret = pos;
		while (pos < len) {
			nums[pos] = 0;
			pos++;
		}

		return ret;
	}

	// 恢复二叉搜索树
	@Test
	public void test7() {
		TreeNode root = Utils.createTree(new Integer[] { 1, 3, null, null, 2 });
		recoverTree(root);
		Utils.printTreeNode(root);
	}

	public void recoverTree(TreeNode root) {
		TreeNode x = null, y = null, pred = null, predecessor = null;
		while (root != null) {
			if (root.left != null) {
				// predecessor 节点就是当前 root 节点向左走一步，然后一直向右走至无法走为止
				predecessor = root.left;
				while (predecessor.right != null && predecessor.right != root) {
					predecessor = predecessor.right;
				}
				// 让 predecessor 的右指针指向 root，继续遍历左子树
				if (predecessor.right == null) {
					predecessor.right = root;
					root = root.left;
				}
				// 说明左子树已经访问完了，我们需要断开链接
				else {
					if (pred != null && root.val < pred.val) {
						y = root;
						if (x == null) {
							x = pred;
						}
					}
					pred = root;
					predecessor.right = null;
					root = root.right;
				}
			}
			// 如果没有左孩子，则直接访问右孩子
			else {
				if (pred != null && root.val < pred.val) {
					y = root;
					if (x == null) {
						x = pred;
					}
				}
				pred = root;
				root = root.right;
			}
		}
		swap(x, y);
	}

	public void swap(TreeNode x, TreeNode y) {
		int tmp = x.val;
		x.val = y.val;
		y.val = tmp;
	}

	// 复原IP地址
	@Test
	public void test8() {
		List<String> ret = new Solution8().restoreIpAddresses("25525511135");
		System.out.println(Joiner.on(",").join(ret));
	}

	class Solution8 {
		final int SEG_COUNT = 4;
		List<String> ans = new ArrayList<String>();
		int[] segments = new int[SEG_COUNT];

		public List<String> restoreIpAddresses(String s) {
			segments = new int[SEG_COUNT];
			backtrack(s, 0, 0);
			return ans;
		}

		private void backtrack(String s, int segId, int segStart) {
			// 如果找到了 4 段 IP 地址并且遍历完了字符串，那么就是一种答案
			if (segId == SEG_COUNT) {
				if (segStart == s.length()) {
					StringBuffer ipAddr = new StringBuffer();
					for (int i = 0; i < SEG_COUNT; ++i) {
						ipAddr.append(segments[i]);
						if (i != SEG_COUNT - 1) {
							ipAddr.append('.');
						}
					}
					ans.add(ipAddr.toString());
				}
				//如果有4个IP了，但是字符串还有剩，则提前回溯，改ip不符合
				return;
			}
			// 如果还没有找到 4 段 IP 地址就已经遍历完了字符串，那么提前回溯
			if (segStart == s.length()) {
				return;
			}
			// 由于不能有前导零，如果当前数字为 0，那么这一段 IP 地址只能为 0
			if (s.charAt(segStart) == '0') {
				segments[segId] = 0;
				backtrack(s, segId + 1, segStart + 1);
			}
			// 一般情况，枚举每一种可能性并递归
			int addr = 0;
			for (int segEnd = segStart; segEnd < s.length(); ++segEnd) {
				addr = addr * 10 + (s.charAt(segEnd) - '0');
				// 地址需要大于0且小于255才有效
				if (addr > 0 && addr <= 0xFF) {
					segments[segId] = addr;
					backtrack(s, segId + 1, segEnd + 1);
				} else {
					break;
				}
			}
		}
	}

}
