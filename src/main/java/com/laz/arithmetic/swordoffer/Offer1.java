package com.laz.arithmetic.swordoffer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
		Assert.assertArrayEquals(new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, printNumbers(1));
	}

	public int[] printNumbers(int n) {
		int[] res = new int[(int) (Math.pow(10, n) - 1)];
		int index = 0;
		for (int i = 1; i < Math.pow(10, n); i++) {
			res[index++] = i;
		}
		return res;
	}

	// 剑指 Offer 18. 删除链表的节点
	@Test
	public void test14() {
		ListNode head = Utils.createListNode(new Integer[] { 4, 5, 1, 9 });
		ListNode h = deleteNode(head, 5);
		Utils.printListNode(h);
	}

	public ListNode deleteNode(ListNode head, int val) {
		ListNode tail = new ListNode(-1);
		tail.next = head;
		ListNode pre = tail;
		while (head != null) {
			if (head.val == val) {
				pre.next = head.next;
				break;
			}
			pre = head;
			head = head.next;
		}
		return tail.next;
	}

	// 剑指 Offer 21. 调整数组顺序使奇数位于偶数前面
	@Test
	public void test15() {
		Assert.assertArrayEquals(new int[] { 1, 3, 2, 4 }, exchange(new int[] { 1, 2, 3, 4 }));

	}

	public int[] exchange(int[] nums) {
		int i = 0;
		int j = nums.length - 1;
		int temp;
		while (i < j) {
			while (i < j && (nums[i] & 1) == 1) {
				i++;
			}
			while (i < j && (nums[j] & 1) == 0) {
				j--;
			}
			temp = nums[i];
			nums[i] = nums[j];
			nums[j] = temp;
		}
		return nums;
	}

	// 剑指 Offer 22. 链表中倒数第k个节点
	@Test
	public void test16() {
		ListNode head = Utils.createListNode(new Integer[] { 1, 2, 3, 4, 5 });
		ListNode h = getKthFromEnd(head, 2);
		Utils.printListNode(h);
	}

	// https://leetcode-cn.com/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof/solution/mian-shi-ti-22-lian-biao-zhong-dao-shu-di-kge-j-11/
	public ListNode getKthFromEnd(ListNode head, int k) {
		ListNode former = head, latter = head;
		for (int i = 0; i < k; i++)
			former = former.next;
		while (former != null) {
			former = former.next;
			latter = latter.next;
		}
		return latter;
	}

	// 剑指 Offer 24. 反转链表
	@Test
	public void test17() {
		ListNode head = Utils.createListNode(new Integer[] { 1, 2, 3, 4, 5 });
		ListNode h = reverseList(head);
		Utils.printListNode(h);
	}

	public ListNode reverseList(ListNode head) {
		if (head == null) {
			return head;
		}
		ListNode tail = head;
		ListNode node = head.next;
		while (node != null) {
			ListNode ln = node;
			node = ln.next;
			ln.next = head;
			head = ln;
		}
		tail.next = null;
		return head;
	}

	// 剑指 Offer 25. 合并两个排序的链表
	@Test
	public void test18() {
		ListNode l1 = Utils.createListNode(new Integer[] { -9, 3 });
		ListNode l2 = Utils.createListNode(new Integer[] { 5, 7 });
		ListNode res = mergeTwoLists(l1, l2);
		Utils.printListNode(res);
	}

	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		ListNode tail = new ListNode(-1);
		ListNode p = new ListNode(-1);
		tail = p;
		while (l1 != null && l2 != null) {
			if (l1.val < l2.val) {
				p.next = l1;
				l1 = l1.next;
			} else {
				p.next = l2;
				l2 = l2.next;
			}
			p = p.next;
		}
		while (l1 != null) {
			p.next = l1;
			l1 = l1.next;
			p = p.next;
		}
		while (l2 != null) {
			p.next = l2;
			l2 = l2.next;
			p = p.next;
		}
		return tail.next;
	}

	// 剑指 Offer 26. 树的子结构
	@Test
	public void test19() {
		TreeNode a = Utils.createTree(new Integer[] { 3, 4, 5, 1, 2 });
		TreeNode b = Utils.createTree(new Integer[] { 4, 1 });
		Assert.assertEquals(true, new Solution19().isSubStructure(a, b));
	}

	// https://leetcode-cn.com/problems/shu-de-zi-jie-gou-lcof/solution/di-gui-fang-shi-jie-jue-by-sdwwld/
	class Solution19 {
		public boolean isSubStructure(TreeNode A, TreeNode B) {
			if (A == null || B == null)
				return false;
			// 先从根节点判断B是不是A的子结构，如果不是在分别从左右两个子树判断，
			// 只要有一个为true，就说明B是A的子结构
			return isSub(A, B) || isSubStructure(A.left, B) || isSubStructure(A.right, B);
		}

		boolean isSub(TreeNode A, TreeNode B) {
			// 这里如果B为空，说明B已经访问完了，确定是A的子结构
			if (B == null)
				return true;
			// 如果B不为空A为空，或者这两个节点值不同，说明B树不是
			// A的子结构，直接返回false
			if (A == null || A.val != B.val)
				return false;
			// 当前节点比较完之后还要继续判断左右子节点
			return isSub(A.left, B.left) && isSub(A.right, B.right);
		}

	}

	// 剑指 Offer 30. 包含min函数的栈
	@Test
	public void test20() {
		MinStack minStack = new MinStack();
		minStack.push(-2);
		minStack.push(0);
		minStack.push(-3);
		Assert.assertEquals(-3, minStack.min());
		minStack.pop();
		Assert.assertEquals(0, minStack.top());
		Assert.assertEquals(-2, minStack.min());
	}

	class MinStack {
		private LinkedList<Integer> A, B;

		/** initialize your data structure here. */
		public MinStack() {
			A = new LinkedList<Integer>();
			B = new LinkedList<Integer>();
		}

		public void push(int x) {
			A.push(x);
			if (B.isEmpty() || B.peek() >= x) {
				B.push(x);
			}
		}

		public void pop() {
			Integer v = A.pop();
			if (v.equals(B.peek())) {
				B.pop();
			}
		}

		public int top() {
			return A.peek();
		}

		public int min() {
			return B.peek();
		}
	}

	@Test
	// 剑指 Offer 31. 栈的压入、弹出序列
	public void test21() {
		Assert.assertEquals(true, validateStackSequences(new int[] { 1, 2, 3, 4, 5 }, new int[] { 4, 5, 3, 2, 1 }));
		Assert.assertEquals(false, validateStackSequences(new int[] { 1, 2, 3, 4, 5 }, new int[] { 4, 3, 5, 1, 2 }));
		Assert.assertEquals(true, validateStackSequences(new int[] { 2, 1, 0 }, new int[] { 1, 2, 0 }));
	}

	public boolean validateStackSequences(int[] pushed, int[] popped) {
		LinkedList<Integer> stack = new LinkedList<Integer>();
		int j = 0;
		for (int i = 0; i < pushed.length; i++) {
			while (!stack.isEmpty()) {
				Integer v = stack.peek();
				if (v.equals(popped[j])) {
					stack.pop();
					j++;
				} else {
					break;
				}
			}
			stack.push(pushed[i]);
		}
		while (!stack.isEmpty()) {
			Integer v = stack.pop();
			if (!v.equals(popped[j])) {
				return false;
			}
			j++;
		}
		return true;

	}

	// 剑指 Offer 32 - I. 从上到下打印二叉树
	@Test
	public void test22() {
		TreeNode root = Utils.createTree(new Integer[] { 3, 9, 20, null, null, 15, 7 });
		Assert.assertArrayEquals(new int[] { 3, 9, 20, 15, 7 }, levelOrder(root));
	}

	public int[] levelOrder(TreeNode root) {
		List<Integer> list = new ArrayList<Integer>();
		Queue<TreeNode> q = new LinkedList<TreeNode>();
		q.add(root);
		while (!q.isEmpty()) {
			TreeNode temp = q.poll();
			if (temp == null) {
				continue;
			} else {
				list.add(temp.val);
			}
			if (temp.left != null) {
				q.offer(temp.left); // 迭代操作，向左探索
			}
			if (temp.right != null) {
				q.offer(temp.right);
			}
		}
		return list.stream().mapToInt(Integer::valueOf).toArray();
	}

	// 剑指 Offer 32 - III. 从上到下打印二叉树 III
	@Test
	public void test23() {
		TreeNode root = Utils.createTree(new Integer[] { 3, 9, 20, null, null, 15, 7 });
		List<List<Integer>> res = new Solution23().levelOrder(root);
		for (List<Integer> list : res) {
			System.out.println(Joiner.on(",").join(list));
		}
	}

	class Solution23 {
		public List<List<Integer>> levelOrder(TreeNode root) {
			List<List<Integer>> levels = new ArrayList<List<Integer>>();
			if (root == null)
				return levels;
			Queue<TreeNode> queue = new LinkedList<TreeNode>();
			queue.add(root);
			int level = 0;
			while (!queue.isEmpty()) {
				levels.add(new ArrayList<Integer>());
				int level_length = queue.size();
				for (int i = 0; i < level_length; ++i) {
					TreeNode node = queue.remove();
					levels.get(level).add(node.val);
					if (node.left != null)
						queue.add(node.left);
					if (node.right != null)
						queue.add(node.right);
				}
				if (level % 2 == 1) {
					Collections.reverse(levels.get(level));
				}
				level++;
			}
			return levels;
		}
	}

	// 剑指 Offer 33. 二叉搜索树的后序遍历序列
	@Test
	public void test24() {
		Assert.assertEquals(false, new Solution24().verifyPostorder(new int[] { 1, 6, 3, 2, 5 }));
		Assert.assertEquals(true, new Solution24().verifyPostorder(new int[] { 1, 3, 2, 6, 5 }));
	}

	// https://leetcode-cn.com/problems/er-cha-sou-suo-shu-de-hou-xu-bian-li-xu-lie-lcof/solution/mian-shi-ti-33-er-cha-sou-suo-shu-de-hou-xu-bian-6/
	class Solution24 {
		public boolean verifyPostorder(int[] postorder) {
			return recur(postorder, 0, postorder.length - 1);
		}

		boolean recur(int[] postorder, int i, int j) {
			if (i >= j)
				return true;
			int p = i;
			while (postorder[p] < postorder[j])
				p++;
			int m = p;
			while (postorder[p] > postorder[j])
				p++;
			return p == j && recur(postorder, i, m - 1) && recur(postorder, m, j - 1);
		}
	}

	// 剑指 Offer 36. 二叉搜索树与双向链表
	@Test
	public void test25() {

	}

	class Solution25 {
		class Node {
			public int val;
			public Node left;
			public Node right;

			public Node() {
			}

			public Node(int _val) {
				val = _val;
			}

			public Node(int _val, Node _left, Node _right) {
				val = _val;
				left = _left;
				right = _right;
			}
		};

		public Node treeToDoublyList(Node root) {
			List<Node> list = new ArrayList<Node>();
			inOrder(root, list);
			if (list.size() == 0) {
				return null;
			}
			Node head = null;
			head = list.get(0);
			for (int i = 0; i < list.size() - 1; i++) {
				Node n = list.get(i);
				Node afterN = list.get(i + 1);
				n.right = afterN;
				afterN.left = n;
			}
			head.left = list.get(list.size() - 1);
			list.get(list.size() - 1).right = head;
			return head;

		}

		private void inOrder(Node root, List<Node> list) {
			if (root == null) {
				return;
			}
			inOrder(root.left, list);
			list.add(root);
			inOrder(root.right, list);
		}
	}

	// 剑指 Offer 40. 最小的k个数
	@Test
	public void test26() {
		Assert.assertArrayEquals(new int[] { 1, 2 }, getLeastNumbers(new int[] { 3, 2, 1 }, 2));
	}

	public int[] getLeastNumbers(int[] arr, int k) {
		if (arr.length == 0) {
			return new int[] {};
		}
		Arrays.sort(arr);
		int[] ret = new int[k];
		System.arraycopy(arr, 0, ret, 0, k);
		return ret;
	}

	// 剑指 Offer 38. 字符串的排列
	@Test
	public void test27() {
		Assert.assertArrayEquals(new String[] { "acb", "bca", "abc", "cba", "bac", "cab" },
				new Solution27().permutation("abc"));

		Assert.assertArrayEquals(new String[] { "aba", "aab", "baa" }, new Solution27().permutation("aab"));
	}

	class Solution27 {
		public String[] permutation(String s) {
			int n = s.length();
			Set<String> ret = new HashSet<String>();
			Set<Integer> set = new HashSet<Integer>();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < n; i++) {
				set.add(i);
				sb.append(s.charAt(i));
				dfs(ret, s, set, sb);
				sb.deleteCharAt(sb.length() - 1);
				set.remove(i);
			}
			return ret.toArray(new String[] {});
		}

		private void dfs(Set<String> ret, String s, Set<Integer> set, StringBuilder sb) {
			if (sb.length() == s.length()) {
				ret.add(sb.toString());
			}
			for (int i = 0; i < s.length(); i++) {
				if (!set.contains(i)) {
					set.add(i);
					sb.append(s.charAt(i));
					dfs(ret, s, set, sb);
					sb.deleteCharAt(sb.length() - 1);
					set.remove(i);
				}
			}
		}
	}

	// 剑指 Offer 42. 连续子数组的最大和
	@Test
	public void test28() {
		Assert.assertEquals(6, maxSubArray(new int[] { -2, 1, -3, 4, -1, 2, 1, -5, 4 }));
	}

	// https://leetcode-cn.com/problems/lian-xu-zi-shu-zu-de-zui-da-he-lcof/solution/mian-shi-ti-42-lian-xu-zi-shu-zu-de-zui-da-he-do-2/
	public int maxSubArray(int[] nums) {
		int res = nums[0];
		for (int i = 1; i < nums.length; i++) {
			nums[i] += Math.max(nums[i - 1], 0);
			res = Math.max(res, nums[i]);
		}
		return res;
	}

	// 剑指 Offer 43. 1～n 整数中 1 出现的次数
	@Test
	public void test29() {
//		Assert.assertEquals(5, new Solution29().countDigitOne(12));
//		Assert.assertEquals(6, new Solution29().countDigitOne(13));
		Assert.assertEquals(16, new Solution29().countDigitOne(101));
	}

	// https://leetcode-cn.com/problems/1nzheng-shu-zhong-1chu-xian-de-ci-shu-lcof/solution/javadi-gui-by-xujunyi/
	class Solution29 {
		public int countDigitOne(int n) {
			return f(n);
		}

		private int f(int n) {
			if (n <= 0)
				return 0;
			String s = String.valueOf(n);
			int high = s.charAt(0) - '0';
			int pow = (int) Math.pow(10, s.length() - 1);
			int last = n - high * pow;
			if (high == 1) {
				return f(pow - 1) + last + 1 + f(last);
			} else {
				return pow + high * f(pow - 1) + f(last);
			}
		}
	}

	// 剑指 Offer 44. 数字序列中某一位的数字
	@Test
	public void test30() {
		// Assert.assertEquals(3, findNthDigit(3));
		Assert.assertEquals(4, findNthDigit(19));
	}

	// https://leetcode-cn.com/problems/shu-zi-xu-lie-zhong-mou-yi-wei-de-shu-zi-lcof/solution/mian-shi-ti-44-shu-zi-xu-lie-zhong-mou-yi-wei-de-6/
	public int findNthDigit(int n) {
		int digit = 1;
		long start = 1;
		long count = 9;
		while (n > count) { // 1.
			n -= count;
			digit += 1;
			start *= 10;
			count = digit * start * 9;
		}
		long num = start + (n - 1) / digit; // 2.
		return Long.toString(num).charAt((n - 1) % digit) - '0'; // 3.
	}

	// 剑指 Offer 45. 把数组排成最小的数
	@Test
	public void test31() {
//		Assert.assertEquals("102", minNumber(new int[] { 10, 2 }));
//		Assert.assertEquals("3033459", minNumber(new int[] { 3,30,34,5,9 }));
//		Assert.assertEquals("1399439856075703697382478249389609", minNumber(new int[] {824,938,1399,5607,6973,5703,9609,4398,8247 }));
		Assert.assertEquals("3233343486364668594", minNumber(new int[] { 3, 43, 48, 94, 85, 33, 64, 32, 63, 66 }));
	}

	public String minNumber(int[] nums) {
		List<Integer> list = new ArrayList<Integer>(nums.length);
		for (int num : nums) {
			list.add(num);
		}
		StringBuilder sb = new StringBuilder();
		Collections.sort(list, new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 == o2) {
					return 0;
				}
				char[] char1 = String.valueOf(o1).toCharArray();
				char[] char2 = String.valueOf(o2).toCharArray();
				int i1 = 0, i2 = 0;
				while (i1 < char1.length && i2 < char2.length) {
					if (char1[i1] == char2[i2]) {
						if (i1 == char1.length - 1 && i2 == char2.length - 1) {
							return 0;
						}
						i1++;
						i2++;
					} else if (char1[i1] > char2[i2]) {
						return 1;
					} else if (char1[i1] < char2[i2]) {
						return -1;
					}
					if (i1 == char1.length) {
						i1 = 0;
					}
					if (i2 == char2.length) {
						i2 = 0;
					}
				}
				return 0;
			}
		});
		for (Integer integer : list) {
			sb.append(integer);
		}
		return sb.toString();

	}

	// 剑指 Offer 46. 把数字翻译成字符串
	@Test
	public void test32() {
		Assert.assertEquals(5, translateNum(12258));
	}

	// https://leetcode-cn.com/problems/ba-shu-zi-fan-yi-cheng-zi-fu-chuan-lcof/solution/mian-shi-ti-46-ba-shu-zi-fan-yi-cheng-zi-fu-chua-6/
	public int translateNum(int num) {
		String s = String.valueOf(num);
		int a = 1, b = 1;
		for (int i = 2; i <= s.length(); i++) {
			String tmp = s.substring(i - 2, i);
			int c = tmp.compareTo("10") >= 0 && tmp.compareTo("25") <= 0 ? a + b : a;
			b = a;
			a = c;
		}
		return a;
	}

	// 剑指 Offer 48. 最长不含重复字符的子字符串
	@Test
	public void test33() {
		Assert.assertEquals(3, lengthOfLongestSubstring("abcabcbb"));
		Assert.assertEquals(1, lengthOfLongestSubstring("bbbbb"));
		Assert.assertEquals(3, lengthOfLongestSubstring("pwwkew"));
		Assert.assertEquals(0, lengthOfLongestSubstring(""));
		Assert.assertEquals(3, lengthOfLongestSubstring("dvdf"));
	}

	// https://leetcode-cn.com/problems/zui-chang-bu-han-zhong-fu-zi-fu-de-zi-zi-fu-chuan-lcof/solution/mian-shi-ti-48-zui-chang-bu-han-zhong-fu-zi-fu-d-9/
	public int lengthOfLongestSubstring(String s) {
		Map<Character, Integer> dic = new HashMap<>();
		int res = 0, tmp = 0;
		for (int j = 0; j < s.length(); j++) {
			int i = dic.getOrDefault(s.charAt(j), -1); // 获取索引 i
			dic.put(s.charAt(j), j); // 更新哈希表
			tmp = tmp < j - i ? tmp + 1 : j - i; // dp[j - 1] -> dp[j]
			res = Math.max(res, tmp); // max(dp[j - 1], dp[j])
		}
		return res;

	}

	// 剑指 Offer 49. 丑数
	@Test
	public void test34() {
		Assert.assertEquals(12, new Solution34().nthUglyNumber(10));
	}

	// https://leetcode-cn.com/problems/chou-shu-lcof/solution/chou-shu-ii-qing-xi-de-tui-dao-si-lu-by-mrsate/
	class Solution34 {
		public int nthUglyNumber(int n) {
			int[] dp = new int[n];
			dp[0] = 1;
			int p2 = 0, p3 = 0, p5 = 0;
			for (int i = 1; i < n; i++) {
				dp[i] = Math.min(Math.min(dp[p2] * 2, dp[p3] * 3), dp[p5] * 5);
				if (dp[i] == dp[p2] * 2)
					p2++;
				if (dp[i] == dp[p3] * 3)
					p3++;
				if (dp[i] == dp[p5] * 5)
					p5++;
			}
			return dp[n - 1];
		}

	}

	// 剑指 Offer 50. 第一个只出现一次的字符
	@Test
	public void test35() {
		Assert.assertEquals('b', firstUniqChar("abaccdeff"));
		Assert.assertEquals(' ', firstUniqChar(""));
	}

	public char firstUniqChar(String s) {
		int[] arr = new int[26];
		for (int i = 0; i < s.length(); i++) {
			arr[s.charAt(i) - 'a']++;
		}
		for (int i = 0; i < s.length(); i++) {
			if (arr[s.charAt(i) - 'a'] == 1) {
				return s.charAt(i);
			}
		}
		return ' ';
	}

	// 剑指 Offer 53 - II. 0～n-1中缺失的数字
	@Test
	public void test36() {
		Assert.assertEquals(2, missingNumber(new int[] { 0, 1, 3 }));
		Assert.assertEquals(8, missingNumber(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 9 }));
		Assert.assertEquals(2, missingNumber(new int[] { 0, 1 }));
		Assert.assertEquals(1, missingNumber(new int[] { 0 }));
	}

	public int missingNumber(int[] nums) {
		int n = nums.length;
		for (int i = 0; i < n; i++) {
			if (nums[i] != i) {
				return nums[i] > i ? nums[i] - 1 : nums[i] + 1;
			}
		}
		return n;
	}

	// 剑指 Offer 54. 二叉搜索树的第k大节点
	@Test
	public void test37() {
		TreeNode root = Utils.createTree(new Integer[] { 3, 1, 4, null, 2 });
		Assert.assertEquals(4, new Solution37().kthLargest(root, 1));
	}

	class Solution37 {
		List<Integer> res = new ArrayList<Integer>();

		public int kthLargest(TreeNode root, int k) {
			dfs(root, k);
			return res.get(k - 1);
		}

		private void dfs(TreeNode root, int k) {
			if (res.size() == k) {
				return;
			}
			if (root != null) {
				dfs(root.right, k);
				res.add(root.val);
				dfs(root.left, k);
			}
		}
	}

	// 剑指 Offer 51. 数组中的逆序对
	@Test
	public void test38() {
		// Assert.assertEquals(5, reversePairs(new int[] {7,5,6,4}));
		// Assert.assertEquals(4, reversePairs(new int[] {1,3,2,3,1}));
		// Assert.assertEquals(3, reversePairs(new int[] {1,2,1,2,1}));
		Assert.assertEquals(6,
				reversePairs(new int[] { 2147483647, 2147483647, -2147483647, -2147483647, -2147483647, 2147483647 }));
	}

	public int reversePairs(int[] nums) {
		if (nums.length == 0 || nums.length == 1) {
			return 0;
		}
		int n = nums.length;
		int count = 0;
		List<Integer> list = new ArrayList<Integer>();
		list.add(nums[n - 1]);
		for (int i = n - 2; i >= 0; i--) {
			// 二分查找
			int l = 0, r = list.size() - 1;
			int v = nums[i];
			// 找到v应该放入的下标
			while (l < r) {
				int mid = (l + r) / 2;
				if (list.get(mid) > v) {
					r = mid;
				}
				if (list.get(mid) < v) {
					l = mid + 1;
				}
				if (list.get(mid) == v) {
					l = mid;
					break;
				}
			}
			if (v == list.get(l)) {
				while (l >= 0 && v <= list.get(l)) {
					l--;
				}
				if (l >= 0 && v > list.get(l)) {
					count += l + 1;
				}
				l = l < 0 ? 0 : l;
				list.add(l + 1, v);
			} else if (v > list.get(l)) {
				count += l + 1;
				list.add(l + 1, v);
			} else if (v < list.get(l)) {
				count += l;
				list.add(l, v);
			}

		}
		return count;
	}

	// https://leetcode-cn.com/problems/shu-zu-zhong-de-ni-xu-dui-lcof/solution/java-gui-bing-pai-xu-zui-hao-li-jie-de-b-7gnx/
	class Solution38 {
		public int reversePairs(int[] nums) {
			return mergeSortPlus(nums, 0, nums.length - 1);
		}

		int mergeSortPlus(int[] arr, int arrStart, int arrEnd) {
			if (arrStart >= arrEnd)
				return 0;// 递归结束条件

			int arrMid = arrStart + ((arrEnd - arrStart) >> 1);// 左右数组分裂的中间界点位置

			// 左右分别进行递归并统计逆序对数
			int count = mergeSortPlus(arr, arrStart, arrMid) + mergeSortPlus(arr, arrMid + 1, arrEnd);

			int[] tempArr = new int[arrEnd - arrStart + 1];// 新建一个该层次的临时数组用于左右数组排序后的合并
			int i = arrStart;// 左边数组的移动指针
			int j = arrMid + 1;// 右边数组的移动指针
			int k = 0;// 临时数组tempArr的移动指针

			while (i <= arrMid && j <= arrEnd) {// 左右两数组都还剩有数字未排序时
				if (arr[i] > arr[j]) { // 如果左边大于右边,构成逆序对
					/* 核心代码 */
					// 左数组i位置及其后的数值均比右数值j位置大，故累加上i位置及其后的数值的长度
					count += arrMid - i + 1;
					/* 核心代码 */
					tempArr[k++] = arr[j++]; // 右数组移动到tempArr中
				} else { // 如果左小于等于右，不构成逆序对
					tempArr[k++] = arr[i++]; // 左数组移动到tempArr中
				}
			}
			// 左右两数组有一边已经移动完毕，剩下另一边可进行快速移动
			while (i <= arrMid) // 右边数组已全部被对比过且移动完成，将剩下的左边数组快速移入tempArr中
				tempArr[k++] = arr[i++];

			while (j <= arrEnd) // 左边数组已全部被对比过且移动完成，将剩下的右边数组快速移入tempArr中
				tempArr[k++] = arr[j++];

			// 将tempArr中的数还原回原arr中，需加上arrStart确保在原arr上位置正确
			for (int a = 0; a < tempArr.length; a++)
				arr[a + arrStart] = tempArr[a];

			return count;
		}
	}

	// 剑指 Offer 56 - II. 数组中数字出现的次数 II
	@Test
	public void test39() {
		Assert.assertEquals(4, singleNumber(new int[] { 3, 4, 3, 3 }));
		Assert.assertEquals(1, singleNumber(new int[] { 9, 1, 7, 9, 7, 9, 7 }));
	}

	// https://leetcode-cn.com/problems/shu-zu-zhong-shu-zi-chu-xian-de-ci-shu-ii-lcof/solution/shu-zu-zhong-shu-zi-chu-xian-de-ci-shu-ii-ha-xi-bi/
	// 位运算参考
	public int singleNumber(int[] nums) {
		Set<Integer> containSet = new HashSet<Integer>();
		Set<Integer> noContainSet = new HashSet<Integer>();
		for (int i = 0; i < nums.length; i++) {
			if (containSet.contains(nums[i])) {
				noContainSet.add(nums[i]);
			}
			containSet.add(nums[i]);
		}
		int ret = -1;
		for (int i = 0; i < nums.length; i++) {
			if (!noContainSet.contains(nums[i])) {
				return nums[i];
			}
		}
		return ret;
	}

	// 剑指 Offer 57. 和为s的两个数字
	@Test
	public void test40() {
		Assert.assertArrayEquals(new int[] { 7, 2 }, twoSum(new int[] { 2, 7, 11, 15 }, 9));
	}

	public int[] twoSum(int[] nums, int target) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < nums.length; i++) {
			map.put(nums[i], -1);
			if (target - nums[i] != nums[i] && map.get(target - nums[i]) != null) {
				return new int[] { nums[i], target - nums[i] };
			}
		}
		return null;
	}

	// 剑指 Offer 57 - II. 和为s的连续正数序列
	@Test
	public void test41() {
		Assert.assertArrayEquals(new int[][] { { 2, 3, 4 }, { 4, 5 } }, findContinuousSequence(9));
	}

	// https://leetcode-cn.com/problems/he-wei-sde-lian-xu-zheng-shu-xu-lie-lcof/solution/shi-yao-shi-hua-dong-chuang-kou-yi-ji-ru-he-yong-h/
	public int[][] findContinuousSequence(int target) {
		int i = 1; // 滑动窗口的左边界
		int j = 1; // 滑动窗口的右边界
		int sum = 0; // 滑动窗口中数字的和
		List<int[]> res = new ArrayList<>();

		while (i <= target / 2) {
			if (sum < target) {
				// 右边界向右移动
				sum += j;
				j++;
			} else if (sum > target) {
				// 左边界向右移动
				sum -= i;
				i++;
			} else {
				// 记录结果
				int[] arr = new int[j - i];
				for (int k = i; k < j; k++) {
					arr[k - i] = k;
				}
				res.add(arr);
				// 左边界向右移动
				sum -= i;
				i++;
			}
		}

		return res.toArray(new int[res.size()][]);

	}

	// 剑指 Offer 58 - I. 翻转单词顺序
	@Test
	public void test42() {
		// Assert.assertEquals("blue is sky the", reverseWords("the sky is blue"));
		Assert.assertEquals("world! hello", reverseWords("  hello world!  "));
		Assert.assertEquals("example good a", reverseWords("a good   example"));
		Assert.assertEquals("Alice Loves Bob", reverseWords("  Bob    Loves  Alice   "));
		Assert.assertEquals("bob like even not does Alice", reverseWords("Alice does not even like bob"));
	}

	public String reverseWords(String s) {
		List<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c != ' ') {
				sb.append(c);
			} else {
				if (sb.length() != 0) {
					list.add(sb.toString());
					sb.delete(0, sb.length());
				}
			}
		}
		if (sb.length() != 0) {
			list.add(sb.toString());
		}
		StringBuilder res = new StringBuilder();
		for (int i = list.size() - 1; i >= 0; i--) {
			res.append(list.get(i));
			if (i > 0) {
				res.append(" ");
			}
		}
		return res.toString();
	}

	// 剑指 Offer 58 - II. 左旋转字符串
	@Test
	public void test43() {
		Assert.assertEquals("cdefgab", reverseLeftWords("abcdefg", 2));
	}

	public String reverseLeftWords(String s, int n) {
		StringBuilder sb = new StringBuilder();
		for (int i = n; i < s.length(); i++) {
			sb.append(s.charAt(i));
		}
		for (int i = 0; i < n; i++) {
			sb.append(s.charAt(i));
		}
		return sb.toString();
	}

	// 剑指 Offer 59 - II. 队列的最大值
	@Test
	public void test44() {
		{
//			MaxQueue queue = new MaxQueue();
//			queue.push_back(1); 
//			queue.push_back(2); 
//			Assert.assertEquals(2, queue.max_value());
//			Assert.assertEquals(1, queue.pop_front());
//			Assert.assertEquals(2, queue.max_value());
		}
		{
			MaxQueue queue = new MaxQueue();
			queue.push_back(4);
			queue.push_back(2);
			queue.push_back(0);
			queue.push_back(3);
			Assert.assertEquals(4, queue.max_value());
			queue.pop_front();
			Assert.assertEquals(3, queue.max_value());
			queue.pop_front();
			Assert.assertEquals(3, queue.max_value());
			queue.pop_front();
			Assert.assertEquals(3, queue.max_value());
		}
	}

	class MaxQueue {
		LinkedList<Integer> max = new LinkedList<Integer>();
		LinkedList<Integer> queue = new LinkedList<Integer>();

		public MaxQueue() {

		}

		public int max_value() {
			return max.isEmpty() ? -1 : max.peek();
		}

		public void push_back(int value) {
			queue.offer(value);
			if (max.isEmpty()) {
				max.addFirst(value);
			} else {
				while (!max.isEmpty() && max.peekLast() < value) {
					max.pollLast();
				}
				max.addLast(value);
			}
		}

		public int pop_front() {
			if (queue.isEmpty()) {
				return -1;
			}
			int v = queue.pop();
			if (max.peek() == v) {
				max.pollFirst();
			}
			return v;
		}
	}

	// 剑指 Offer 61. 扑克牌中的顺子
	@Test
	public void test45() {
//		Assert.assertEquals(true, isStraight(new int[] { 1, 2, 3, 4, 5 }));
//		Assert.assertEquals(true, isStraight(new int[] { 0, 0, 1, 2, 5 }));
//		Assert.assertEquals(true, isStraight(new int[] { 0, 0, 8, 5, 4 }));
//		Assert.assertEquals(false, isStraight(new int[] { 8,2,9,7,10 }));
//		Assert.assertEquals(false, isStraight(new int[] { 0,0,2,2,5 }));
//		Assert.assertEquals(false, isStraight(new int[] { 1,2,12,0,3 }));
		Assert.assertEquals(true, isStraight(new int[] { 11, 0, 9, 0, 0 }));
	}

	public boolean isStraight(int[] nums) {
		Arrays.sort(nums);
		int start = -1;
		int zeroCount = 0;
		int index = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == 0) {
				zeroCount++;
			} else {
				index = i;
				break;
			}
		}
		int count = 1;
		start = nums[index];
		while (count < 5 && index < nums.length - 1) {
			int num = nums[index + 1];
			if (num != start + 1) {
				if (zeroCount <= 0) {
					return false;
				}
				zeroCount--;

			} else {
				index++;
			}
			count++;
			start++;
		}
		return true;

	}

	// 剑指 Offer 62. 圆圈中最后剩下的数字
	@Test
	public void test46() {
		Assert.assertEquals(3, lastRemaining(5, 3));
		Assert.assertEquals(2, lastRemaining(10, 17));
	}

	// https://leetcode-cn.com/problems/yuan-quan-zhong-zui-hou-sheng-xia-de-shu-zi-lcof/solution/javajie-jue-yue-se-fu-huan-wen-ti-gao-su-ni-wei-sh/
	public int lastRemaining(int n, int m) {
		int ans = 0;
		// 最后一轮剩下2个人，所以从2开始反推
		for (int i = 2; i <= n; i++) {
			ans = (ans + m) % i;
		}
		return ans;
	}

	// 剑指 Offer 60. n个骰子的点数
	@Test
	public void test47() {
		double[] res = dicesProbability(2);
		for (double d : res) {
			BigDecimal db = new BigDecimal(d);
			db = db.setScale(5, BigDecimal.ROUND_HALF_UP);
			System.out.print(db.doubleValue() + " ");
		}
	}

	// https://leetcode-cn.com/problems/nge-tou-zi-de-dian-shu-lcof/solution/dong-tai-gui-hua-by-shy-14/
	public double[] dicesProbability(int n) {
		// 第i次取j的次数
		int[][] dp = new int[n + 1][6 * n + 1];
		// 初始化
		for (int i = 1; i <= 6; i++) {
			dp[1][i] = 1;
		}
		for (int i = 2; i <= n; i++) {
			for (int j = i; j <= (6 * i); j++) {
				for (int z = 1; z <= 6; z++) {
					if (j - z < i - 1) { // 需要是能要到的数，小于i-1的数都无法摇到
						break;
					}
					dp[i][j] += dp[i - 1][j - z];
				}
			}
		}
		// 总共结果数组大小为5*n+1
		double[] ans = new double[5 * n + 1];

		for (int i = 0; i < ans.length; i++) {
			ans[i] = dp[n][n + i] / (Math.pow(6, n));
		}
		return ans;
	}

	// 剑指 Offer 63. 股票的最大利润
	@Test
	public void test48() {
		Assert.assertEquals(5, maxProfit(new int[] { 7, 1, 5, 3, 6, 4 }));
	}

	public int maxProfit(int[] prices) {
		int n = prices.length;
		if (n == 0) {
			return 0;
		}
		int[][] dp = new int[n + 1][2]; // 两个状态持股与不持股
		dp[1][0] = 0; // 不持股
		dp[1][1] = -prices[0]; // 持股
		for (int i = 2; i <= n; i++) {
			dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i - 1]);
			dp[i][1] = Math.max(dp[i - 1][1], -prices[i - 1]); // 昨天不持股，今天买入股票（注意：只允许交易一次，因此手上的现金数就是当天的股价的相反数）。
		}
		return dp[n][0];
	}

	// 剑指 Offer 64. 求1+2+…+n
	@Test
	public void test49() {
		Assert.assertEquals(6, sumNums(3));
	}

	// https://leetcode-cn.com/problems/qiu-12n-lcof/solution/mian-shi-ti-64-qiu-1-2-nluo-ji-fu-duan-lu-qing-xi-/
	public int sumNums(int n) {
		boolean x = n > 1 && (n += sumNums(n - 1)) > 0;
		return n;
	}
}
