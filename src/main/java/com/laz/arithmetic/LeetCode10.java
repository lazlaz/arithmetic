package com.laz.arithmetic;

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
	
	//打家劫舍 III
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
	private void backtrack(int first,LinkedList<Integer> curr) {
		if (curr.size() == k) {
			output.add(new LinkedList<Integer>(curr));
		}
		for (int i=first;i<n+1;i++) {
			curr.add(i);
			backtrack(i+1, curr);
			curr.removeLast();
		}
	}
	public List<List<Integer>> combine(int n, int k) {
		this.n = n;
		this.k = k;
		backtrack(1,new LinkedList<Integer>());
		return output;
	}
}
