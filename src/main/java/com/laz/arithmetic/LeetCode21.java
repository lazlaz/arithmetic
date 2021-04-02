package com.laz.arithmetic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class LeetCode21 {
	// 456. 132 模式
	@Test
	public void test1() {
		Assert.assertEquals(false, find132pattern(new int[] { 1, 2, 3, 4 }));
		Assert.assertEquals(true, find132pattern(new int[] { 3, 1, 4, 2 }));
		Assert.assertEquals(true, find132pattern(new int[] { -1, 3, 2, 0 }));
	}

	public boolean find132pattern(int[] nums) {
		// 找到左侧最小的值并经历
		int n = nums.length;
		int[] leftMin = new int[n];
		leftMin[0] = nums[0];
		for (int i = 1; i < nums.length; i++) {
			leftMin[i] = Math.min(leftMin[i - 1], nums[i]);
		}
		// 单调递减栈
		Deque<Integer> stack = new ArrayDeque<>();
		for (int i = n - 1; i >= 0; i--) {
			int max = Integer.MIN_VALUE; // 当前栈中最大的元素
			while (!stack.isEmpty() && nums[i] > stack.peek()) {
				max = stack.poll();
			}
			// nums[i]>leftMin[i] nums[i]>max max>leftMin[i]满足132模式
			if (max > leftMin[i] && nums[i] > leftMin[i]) {
				return true;
			}
			stack.push(nums[i]);
		}
		return false;
	}

	// 173. 二叉搜索树迭代器
	@Test
	public void test2() {
		TreeNode root = Utils.createTree(new Integer[] { 7, 3, 15, null, null, 9, 20 });
		BSTIterator it = new BSTIterator(root);
		while (it.hasNext()) {
			int n = it.next();
			System.out.println(n);
		}
	}

	class BSTIterator {
		List<Integer> list = new ArrayList<>();
		Iterator<Integer> it;

		public BSTIterator(TreeNode root) {
			dfs(root);
			it = list.iterator();
		}

		private void dfs(TreeNode root) {
			if (root == null) {
				return;
			}
			dfs(root.left);
			list.add(root.val);
			dfs(root.right);
		}

		public int next() {
			return it.next();
		}

		public boolean hasNext() {
			return it.hasNext();
		}
	}

	// 1006. 笨阶乘
	@Test
	public void test3() {
		Assert.assertEquals(7, new Solution3().clumsy(4));
		Assert.assertEquals(12, new Solution3().clumsy(10));
	}

	class Solution3 {
		public int clumsy(int N) {
			Deque<Integer> stack = new LinkedList<Integer>();
			stack.push(N);
			N--;
			int index = 0; // 用于控制乘、除、加、减
			while (N > 0) {
				if (index % 4 == 0) {
					stack.push(stack.pop() * N);
				} else if (index % 4 == 1) {
					stack.push(stack.pop() / N);
				} else if (index % 4 == 2) {
					stack.push(N);
				} else {
					stack.push(-N);
				}
				index++;
				N--;
			}
			// 把栈中所有的数字依次弹出求和
			int sum = 0;
			while (!stack.isEmpty()) {
				sum += stack.pop();
			}
			return sum;
		}
	}
	
	//面试题 17.21. 直方图的水量
	@Test
	public void test4() {
		Assert.assertEquals(6, new Solution4().trap(new int[] {
				0,1,0,2,1,0,1,3,2,1,2,1
		}));
	}
	//https://leetcode-cn.com/problems/volume-of-histogram-lcci/solution/zhi-fang-tu-de-shui-liang-by-leetcode-so-7rla/
	class Solution4 {
		public int trap(int[] height) {
			Deque<Integer> stack = new ArrayDeque<Integer>(); //单独递减
			int n = height.length;
			int ans = 0;
			for (int i=0;i<n;i++) {
				while (!stack.isEmpty()&&height[i]>height[stack.peek()]) {
					int top = stack.pop();
					if (stack.isEmpty()) {
	                    break;
	                }
					//有间隙，可放水计算
					int left = stack.peek();
					int currWidth=(i-left-1);
					int currHeight = Math.min(height[left], height[i])-height[top];
					int area = currWidth*currHeight;
					ans+=area;
				}
				stack.push(i);
			
			}
			return ans;
		}
	}
}
