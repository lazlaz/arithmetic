package com.laz.arithmetic.swordoffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.laz.arithmetic.ListNode;
import com.laz.arithmetic.TreeNode;
import com.laz.arithmetic.Utils;

public class Offer1 {
	// 剑指 Offer 03. 数组中重复的数字
	@Test
	public void test1() {
		Assert.assertEquals(2, findRepeatNumber(new int[] { 2, 3, 1, 0, 2, 5, 3 }));
	}

	public int findRepeatNumber(int[] nums) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums.length; i++) {
			int v = map.getOrDefault(nums[i], 0);
			if (v > 0) {
				return nums[i];
			} else {
				map.put(nums[i], 1);
			}
		}
		return -1;
	}

	// 剑指 Offer 04. 二维数组中的查找
	@Test
	public void test2() {
		Assert.assertEquals(true, findNumberIn2DArray(new int[][] { { 1, 4, 7, 11, 15 }, { 2, 5, 8, 12, 19 },
				{ 3, 6, 9, 16, 22 }, { 10, 13, 14, 17, 24 }, { 18, 21, 23, 26, 30 } }, 5));

		Assert.assertEquals(false, findNumberIn2DArray(new int[][] { { 1, 4, 7, 11, 15 }, { 2, 5, 8, 12, 19 },
				{ 3, 6, 9, 16, 22 }, { 10, 13, 14, 17, 24 }, { 18, 21, 23, 26, 30 } }, 20));
	}

	public boolean findNumberIn2DArray(int[][] matrix, int target) {
		int n = matrix.length;
		if (n == 0) {
			return false;
		}
		int m = matrix[0].length;
		int i = n - 1;
		int j = 0;
		while (i >= 0 && j < m) {
			if (matrix[i][j] < target) {
				j++;
			} else if (matrix[i][j] > target) {
				i--;
			} else {
				return true;
			}
		}
		return false;
	}

	// 剑指 Offer 05. 替换空格
	@Test
	public void test3() {
		Assert.assertEquals("We%20are%20happy.", replaceSpace("We are happy."));
	}

	public String replaceSpace(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ' ') {
				sb.append("%20");
			} else {
				sb.append(s.charAt(i));
			}
		}
		return sb.toString();
	}

	// 剑指 Offer 06. 从尾到头打印链表
	@Test
	public void test4() {
		ListNode head = Utils.createListNode(new Integer[] { 1, 3, 2 });
		int[] res = reversePrint(head);
		Assert.assertArrayEquals(new int[] { 2, 3, 1 }, res);
	}

	public int[] reversePrint(ListNode head) {
		LinkedList<ListNode> stack = new LinkedList<ListNode>();
		ListNode temp = head;
		while (temp != null) {
			stack.push(temp);
			temp = temp.next;
		}
		int size = stack.size();
		int[] print = new int[size];
		for (int i = 0; i < size; i++) {
			print[i] = stack.pop().val;
		}
		return print;
	}

	// 剑指 Offer 07. 重建二叉树
	@Test
	public void test5() {
		TreeNode root = new Solution5().buildTree(new int[] { 3, 9, 20, 15, 7 }, new int[] { 9, 3, 15, 20, 7 });
		Utils.printTreeNode(root);
	}

	class Solution5 {
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

	// 剑指 Offer 10- I. 斐波那契数列
	@Test
	public void test6() {
		Assert.assertEquals(5, fib(5));
		Assert.assertEquals(1, fib(2));
	}

	public int fib(int n) {
		if (n < 1) {
			return 0;
		}
		int MOD = 1000_000_007;
		int[] dp = new int[n + 1];
		dp[0] = 0;
		dp[1] = 1;
		for (int i = 2; i <= n; i++) {
			dp[i] = (dp[i - 1] + dp[i - 2]) % MOD;

		}
		return dp[n];
	}

	// 剑指 Offer 10- II. 青蛙跳台阶问题
	@Test
	public void test7() {
		Assert.assertEquals(2, numWays(2));

		Assert.assertEquals(21, numWays(7));
	}

	public int numWays(int n) {
		if (n == 0) {
			return 1;
		}
		if (n <= 2) {
			return n;
		}
		int[] dp = new int[n + 1];
		dp[0] = 1;
		dp[1] = 1;
		dp[2] = 2;
		for (int i = 3; i <= n; i++) {
			dp[i] = (dp[i - 1] + dp[i - 2]) % 1000000007;
		}
		return dp[n];
	}

	// 剑指 Offer 12. 矩阵中的路径
	@Test
	public void test8() {
		Assert.assertEquals(true, new Solution8().exist(
				new char[][] { { 'A', 'B', 'C', 'E' }, { 'S', 'F', 'C', 'S' }, { 'A', 'D', 'E', 'E' } }, "ABCCED"));

		Assert.assertEquals(false, new Solution8().exist(new char[][] { { 'a', 'b' }, { 'c', 'd' } }, "abcd"));

		Assert.assertEquals(true, new Solution8().exist(new char[][] { { 'a' } }, "a"));
	}

	class Solution8 {
		boolean[][] visited;
		String word;
		int[][] p = new int[][] { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
		boolean res = false;

		public boolean exist(char[][] board, String word) {
			int m = board.length;
			int n = board[0].length;
			visited = new boolean[m][n];
			this.word = word;
			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					dfs(i, j, board, new StringBuilder());
				}
			}
			return res;
		}

		private void dfs(int r, int c, char[][] board, StringBuilder sb) {
			if (res) {
				return;
			}
			if (r < 0 || c < 0 || r >= board.length || c >= board[0].length) {
				return;
			}
			if (visited[r][c]) {
				return;
			}
			if (sb.length() < word.length() && word.charAt(sb.length()) != board[r][c]) {
				return;
			}
			visited[r][c] = true;
			sb.append(board[r][c]);
			if (sb.length() == word.length()) {
				res = true;
			}
			for (int i = 0; i < p.length; i++) {
				dfs(r + p[i][0], c + p[i][1], board, sb);
			}
			visited[r][c] = false;
			sb.delete(sb.length() - 1, sb.length());
		}
	}

	// 剑指 Offer 14- I. 剪绳子
	@Test
	public void test9() {
		Assert.assertEquals(1, cuttingRope(2));

		Assert.assertEquals(18, cuttingRope(8));
	}

	// https://leetcode-cn.com/problems/jian-sheng-zi-lcof/solution/dong-tai-gui-hua-shu-xue-by-sophia_fez/
	public int cuttingRope(int n) {
		int[] dp = new int[n + 1];
		for (int i = 2; i <= n; i++) {
			for (int j = 1; j < i; j++) {
				dp[i] = Math.max(dp[i], Math.max(j * (i - j), j * dp[i - j]));
			}
		}
		return dp[n];
	}

	// 剑指 Offer 14- II. 剪绳子 II
	@Test
	public void test10() {
		Assert.assertEquals(18, new Solution10().cuttingRope(8));

		Assert.assertEquals(953271190, new Solution10().cuttingRope(120));
	}

//https://leetcode-cn.com/problems/jian-sheng-zi-ii-lcof/solution/javatan-xin-si-lu-jiang-jie-by-henrylee4/
	class Solution10 {
		public int cuttingRope(int n) {
			if (n == 2) {
				return 1;
			}
			if (n == 3) {
				return 2;
			}
			int mod = (int) 1e9 + 7;
			long res = 1;
			while (n > 4) {
				res *= 3;
				res %= mod;
				n -= 3;
			}
			return (int) (res * n % mod);
		}
	}

	// 剑指 Offer 15. 二进制中1的个数
	@Test
	public void test11() {
		Assert.assertEquals(2, hammingWeight(9));
	}

	// https://leetcode-cn.com/problems/er-jin-zhi-zhong-1de-ge-shu-lcof/solution/liang-chong-fang-fa-yi-zhao-bi-yi-zhao-kuai-chao-z/
	// https://leetcode-cn.com/problems/er-jin-zhi-zhong-1de-ge-shu-lcof/solution/mian-shi-ti-15-er-jin-zhi-zhong-1de-ge-shu-wei-yun/
	public int hammingWeight(int n) {
		int count = 0;
		while (n != 0) {
			n &= (n - 1);
			count++;
		}
		return count;
	}

	// 剑指 Offer 16. 数值的整数次方
	@Test
	public void test12() {
//		Assert.assertEquals(1024.00000d, myPow(2.00000d,10),44);
//		Assert.assertEquals(0.25000d, myPow(2.00000d,-2),44);
		Assert.assertEquals(0.0d, myPow(2d, -2147483648), 44);
	}

	// https://leetcode-cn.com/problems/shu-zhi-de-zheng-shu-ci-fang-lcof/solution/mian-shi-ti-16-shu-zhi-de-zheng-shu-ci-fang-kuai-s/
	public double myPow(double x, int n) {
		if (x == 0)
			return 0;
		long b = n;
		double res = 1.0;
		if (b < 0) {
			x = 1 / x;
			b = -b;
		}
		while (b > 0) {
			if ((b & 1) == 1)
				res *= x;
			x *= x;
			b >>= 1;
		}
		return res;
	}

	// 剑指 Offer 17. 打印从1到最大的n位数
	@Test
	public void test13() {
		Assert.assertArrayEquals(new int[] {1,2,3,4,5,6,7,8,9}, printNumbers(1));
	}

	public int[] printNumbers(int n) {
		int[] res = new int[(int) (Math.pow(10, n)-1)];
		int index = 0;
		for (int i=1;i<Math.pow(10, n);i++) {
			res[index++] = i;
		}
		return res;
	}
	
	//剑指 Offer 18. 删除链表的节点
	@Test
	public void test14() {
		ListNode head = Utils.createListNode(new Integer[] {4,5,1,9});
		ListNode h = deleteNode(head, 5);
		Utils.printListNode(h);
	}
	public ListNode deleteNode(ListNode head, int val) {
		ListNode tail = new ListNode(-1);
		tail.next = head;
		ListNode pre = tail;
		while (head!=null) {
			if (head.val==val) {
				pre.next = head.next;
				break;
			}
			pre = head;
			head = head.next;
		}
		return tail.next;
    }
}
