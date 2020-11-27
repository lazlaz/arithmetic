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
		if (n<1) {
			return 0;
		}
		int MOD = 1000_000_007;
		int[] dp = new int[n+1];
		dp[0] = 0;
		dp[1] = 1;
		for (int i=2;i<=n;i++) {
			dp[i] = (dp[i-1]+dp[i-2])%MOD;
			
		}
		return dp[n];
	}
}
