package com.laz.arithmetic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

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

	// 面试题 17.21. 直方图的水量
	@Test
	public void test4() {
		Assert.assertEquals(6, new Solution4().trap(new int[] { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 }));
	}

	// https://leetcode-cn.com/problems/volume-of-histogram-lcci/solution/zhi-fang-tu-de-shui-liang-by-leetcode-so-7rla/
	class Solution4 {
		public int trap(int[] height) {
			Deque<Integer> stack = new ArrayDeque<Integer>(); // 单独递减
			int n = height.length;
			int ans = 0;
			for (int i = 0; i < n; i++) {
				while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
					int top = stack.pop();
					if (stack.isEmpty()) {
						break;
					}
					// 有间隙，可放水计算
					int left = stack.peek();
					int currWidth = (i - left - 1);
					int currHeight = Math.min(height[left], height[i]) - height[top];
					int area = currWidth * currHeight;
					ans += area;
				}
				stack.push(i);

			}
			return ans;
		}
	}

	@Test
	// 80. 删除有序数组中的重复项 II
	public void test5() {
		Assert.assertEquals(5, new Solution5().removeDuplicates(new int[] { 1, 1, 1, 2, 2, 3 }));
		Assert.assertEquals(7, new Solution5().removeDuplicates(new int[] { 0, 0, 1, 1, 1, 1, 2, 3, 3 }));
	}

	class Solution5 {
		public int removeDuplicates(int[] nums) {
			int n = nums.length;
			if (n <= 2) {
				return n;
			}
			int slow = 2, fast = 2;
			while (fast < n) {
				if (nums[slow - 2] != nums[fast]) {
					nums[slow] = nums[fast];
					++slow;
				}
				++fast;
			}
			return slow;
		}
	}

	// 153. 寻找旋转排序数组中的最小值
	@Test
	public void test6() {
		Assert.assertEquals(1, new Solution6().findMin(new int[] { 3, 4, 5, 1, 2 }));
	}

	class Solution6 {
		public int findMin(int[] nums) {
			Deque<Integer> stack = new ArrayDeque<Integer>();
			for (int i = 0; i < nums.length; i++) {
				if (!stack.isEmpty() && stack.peek() > nums[i]) {
					return nums[i];
				}
				stack.push(nums[i]);
			}
			return stack.getLast();
		}
	}

	// 213. 打家劫舍 II
	@Test
	public void test7() {
		Assert.assertEquals(4, new Solution7().rob(new int[] { 1, 2, 3, 1 }));
		Assert.assertEquals(3, new Solution7().rob(new int[] { 2, 3, 2 }));
		Assert.assertEquals(1, new Solution7().rob(new int[] { 1 }));
		Assert.assertEquals(103, new Solution7().rob(new int[] { 1, 3, 1, 3, 100 }));
	}

	class Solution7 {
		public int rob(int[] nums) {
			int length = nums.length;
			if (length == 1) {
				return nums[0];
			} else if (length == 2) {
				return Math.max(nums[0], nums[1]);
			}
			// 偷0~（n-2)之间 或者 偷1~(n-1)之间，去大的值
			return Math.max(robRange(nums, 0, length - 2), robRange(nums, 1, length - 1));
		}

		public int robRange(int[] nums, int start, int end) {
			int n = end - start + 1;
			int[] dp = new int[n];
			dp[0] = nums[start];
			dp[1] = Math.max(nums[start], nums[start + 1]);
			for (int i = 2; i < n; i++) {
				dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i + start]);
			}
			return dp[n - 1];
		}

	}

	// 220. 存在重复元素 III
	@Test
	public void test8() {
//		Assert.assertEquals(true, new Solution8().containsNearbyAlmostDuplicate(new int[] {
//				1,2,3,1
//		}, 3, 0));
//		Assert.assertEquals(false, new Solution8().containsNearbyAlmostDuplicate(new int[] {
//				1,5,9,1,5,9
//		}, 2, 3));
		Assert.assertEquals(false, new Solution8().containsNearbyAlmostDuplicate(new int[] { -2147483648, 2147483647

		}, 1, 1));
	}

	class Solution8 {
		public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
			int n = nums.length;
			TreeSet<Long> ts = new TreeSet<>();
			for (int i = 0; i < n; i++) {
				Long u = nums[i] * 1L;
				// 从 ts 中找到小于等于 u 的最大值（小于等于 u 的最接近 u 的数）
				Long l = ts.floor(u);
				// 从 ts 中找到大于等于 u 的最小值（大于等于 u 的最接近 u 的数）
				Long r = ts.ceiling(u);
				if (l != null && u - l <= t)
					return true;
				if (r != null && r - u <= t)
					return true;
				// 将当前数加到 ts 中，并移除下标范围不在 [max(0, i - k), i) 的数（维持滑动窗口大小为 k）
				ts.add(u);
				if (i >= k)
					ts.remove(nums[i - k] * 1L);
			}
			return false;

		}
	}
}
