package com.laz.arithmetic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.google.common.base.Joiner;

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

	// 363. 矩形区域不超过 K 的最大数值和
	@Test
	public void test9() {
		Assert.assertEquals(2, new Solution9().maxSumSubmatrix(new int[][] { { 1, 0, 1 }, { 0, -2, 3 } }, 2));
	}

	// https://leetcode-cn.com/problems/max-sum-of-rectangle-no-larger-than-k/solution/javacong-bao-li-kai-shi-you-hua-pei-tu-pei-zhu-shi/
	class Solution9 {
		public int maxSumSubmatrix(int[][] matrix, int k) {
			int row = matrix.length;
			int col = matrix[0].length;
			int max = Integer.MIN_VALUE;
			for (int l = 0; l < col; l++) { // 从左边界开始
				int[] rowSum = new int[row]; // 左边界改变才算区域的重新开始
				for (int r = l; r < col; r++) {// 右边界
					for (int i = 0; i < row; i++) { // 计算每一行的和
						rowSum[i] += matrix[i][r];
					}
					// 求 rowSum 连续子数组 的 和
					// 和 尽量大，但不大于 k
					max = Math.max(max, dpmax(rowSum, k));
				}
			}
			return max;
		}

		// 在数组 arr 中，求不超过 k 的最大值
		private int dpmax(int[] arr, int k) {
			// O(rows ^ 2)
			int max = Integer.MIN_VALUE;
			for (int l = 0; l < arr.length; l++) {
				int sum = 0;
				for (int r = l; r < arr.length; r++) {
					sum += arr[r];
					if (sum > max && sum <= k)
						max = sum;
				}
			}
			return max;
		}
	}

	// 368. 最大整除子集
	@Test
	public void test10() {
		List<Integer> list = new Solution10().largestDivisibleSubset(new int[] { 1, 2, 3 });
		Assert.assertEquals("2,1", Joiner.on(",").join(list));
	}

	// https://leetcode-cn.com/problems/largest-divisible-subset/solution/zui-da-zheng-chu-zi-ji-by-leetcode-solut-t4pz/
	class Solution10 {
		public List<Integer> largestDivisibleSubset(int[] nums) {
			int len = nums.length;
			Arrays.sort(nums);

			// 第 1 步：动态规划找出最大子集的个数、最大子集中的最大整数
			int[] dp = new int[len]; // 以 nums[i] 为最大整数的「整除子集」的大小
			Arrays.fill(dp, 1);
			int maxSize = 1;
			int maxVal = dp[0];
			for (int i = 1; i < len; i++) {
				for (int j = 0; j < i; j++) {
					// 题目中说「没有重复元素」很重要
					if (nums[i] % nums[j] == 0) {
						dp[i] = Math.max(dp[i], dp[j] + 1);
					}
				}

				if (dp[i] > maxSize) {
					maxSize = dp[i];
					maxVal = nums[i];
				}
			}

			// 第 2 步：倒推获得最大子集
			List<Integer> res = new ArrayList<Integer>();
			if (maxSize == 1) { // 如果是1个的化，随便选择一个
				res.add(nums[0]);
				return res;
			}

			for (int i = len - 1; i >= 0 && maxSize > 0; i--) {
				if (dp[i] == maxSize && maxVal % nums[i] == 0) {
					res.add(nums[i]);
					maxVal = nums[i];
					maxSize--;
				}
			}
			return res;

		}
	}

	// 740. 删除并获得点数
	@Test
	public void test11() {
		Assert.assertEquals(6, new Solution11().deleteAndEarn(new int[] { 3, 4, 2 }));

		Assert.assertEquals(9, new Solution11().deleteAndEarn(new int[] { 2, 2, 3, 3, 3, 4 }));

		Assert.assertEquals(4, new Solution11().deleteAndEarn(new int[] { 3, 1 }));
	}

	class Solution11 {
		public int deleteAndEarn(int[] nums) {
			int len = nums.length;
			if (len == 1) {
				return nums[0];
			}
			int maxVal = Integer.MIN_VALUE;
			for (int i = 0; i < nums.length; i++) {
				maxVal = Math.max(maxVal, nums[i]);
			}
			int[] sums = new int[maxVal + 1];
			for (int i = 0; i < nums.length; i++) {
				sums[nums[i]] += nums[i];
			}
			// 动态规划求最大
			int[] dp = new int[maxVal + 1];
			dp[1] = sums[1];
			dp[2] = Math.max(sums[1], sums[2]);
			for (int i = 3; i <= maxVal; i++) {
				dp[i] = Math.max(dp[i - 1], dp[i - 2] + sums[i]);
			}
			return dp[maxVal];
		}
	}

	// 1486. 数组异或操作
	@Test
	public void test12() {
		Assert.assertEquals(8, new Solution12().xorOperation(5, 0));
		Assert.assertEquals(8, new Solution12().xorOperation(4, 3));
		Assert.assertEquals(7, new Solution12().xorOperation(1, 7));
	}

	class Solution12 {
		public int xorOperation(int n, int start) {
			int[] num = new int[n];
			int res = 0;
			for (int i = 0; i < n; i++) {
				num[i] = start + 2 * i;
				res = res ^ num[i];
			}
			return res;
		}
	}

	// 1723. 完成所有工作的最短时间
	@Test
	public void test13() {
		Assert.assertEquals(11, new Solution13().minimumTimeRequired(new int[] { 1, 2, 4, 7, 8 }, 2));
	}

	// https://leetcode-cn.com/problems/find-minimum-time-to-finish-all-jobs/solution/wan-cheng-suo-you-gong-zuo-de-zui-duan-s-hrhu/
	class Solution13 {
		public int minimumTimeRequired(int[] jobs, int k) {
			Arrays.sort(jobs);
			// 翻转数组，使其降序排列，从最大的开始分配
			reverse(jobs);
			int l = jobs[0], r = Arrays.stream(jobs).sum();
			while (l < r) {
				int mid = (l + r) >> 1;
				// 如果时间取mid，是否能完成所有工作
				if (check(jobs, k, mid)) {
					r = mid;
				} else {
					l = mid + 1;
				}

			}
			return l;
		}

		private void reverse(int[] jobs) {
			int low = 0, high = jobs.length - 1;
			while (low < high) {
				int temp = jobs[low];
				jobs[low] = jobs[high];
				jobs[high] = temp;
				low++;
				high--;
			}
		}

		private boolean check(int[] jobs, int k, int limit) {
			int[] workloads = new int[k];
			return backtrack(jobs, workloads, 0, limit);
		}

		// 回溯
		public boolean backtrack(int[] jobs, int[] workloads, int i, int limit) {
			if (i >= jobs.length) {
				return true;
			}
			int cur = jobs[i];
			for (int j = 0; j < workloads.length; ++j) {
				if (workloads[j] + cur <= limit) {
					workloads[j] += cur;
					if (backtrack(jobs, workloads, i + 1, limit)) {
						return true;
					}
					workloads[j] -= cur;
				}
				// 如果当前工人未被分配工作，那么下一个工人也必然未被分配工作
				// 或者当前工作恰能使该工人的工作量达到了上限
				// 这两种情况下我们无需尝试继续分配工作
				if (workloads[j] == 0 || workloads[j] + cur == limit) {
					break;
				}
			}
			return false;
		}

	}

	// 1482. 制作 m 束花所需的最少天数
	@Test
	public void test14() {
		Assert.assertEquals(3, new Solution14().minDays(new int[] { 1, 10, 3, 10, 2 }, 3, 1));
	}

	// https://leetcode-cn.com/problems/minimum-number-of-days-to-make-m-bouquets/solution/zhi-zuo-m-shu-hua-suo-xu-de-zui-shao-tia-mxci/
	class Solution14 {
		public int minDays(int[] bloomDay, int m, int k) {
			int dayLen = bloomDay.length;
			if (dayLen / k < m) {
				return -1;
			}
			int low = Integer.MAX_VALUE, high = Integer.MIN_VALUE;
			for (int i = 0; i < bloomDay.length; i++) {
				low = Math.min(bloomDay[i], low);
				high = Math.max(bloomDay[i], high);
			}
			while (low < high) {
				int mid = ((high - low) >> 1) + low;
				if (check(bloomDay, m, k, mid)) {
					high = mid;
				} else {
					low = mid + 1;
				}
			}
			return low;
		}

		private boolean check(int[] bloomDay, int m, int k, int day) {
			int bouquets = 0; // 多少束
			int flowers = 0; // 多少朵
			int length = bloomDay.length;
			for (int i = 0; i < length && bouquets < m; i++) {
				if (bloomDay[i] <= day) {
					flowers++;
					if (flowers == k) {
						bouquets++;
						flowers = 0;
					}
				} else {
					flowers = 0;
				}
			}
			return bouquets >= m;
		}
	}

	// 872. 叶子相似的树
	@Test
	public void test15() {
		TreeNode root1 = Utils.createTree(new Integer[] { 3, 5, 1, 6, 2, 9, 8, null, null, 7, 4 });
		TreeNode root2 = Utils
				.createTree(new Integer[] { 3, 5, 1, 6, 7, 4, 2, null, null, null, null, null, null, 9, 8 });
		Assert.assertEquals(true, new Solution15().leafSimilar(root1, root2));
	}
	//https://leetcode-cn.com/problems/leaf-similar-trees/solution/xiao-ming-zhao-bu-tong-tong-bu-bian-li-b-0te2/
	class Solution15 {
		public boolean leafSimilar(TreeNode root1, TreeNode root2) {
			List<Integer> seq1 = new ArrayList<Integer>();
			if (root1 != null) {
				dfs(root1, seq1);
			}

			List<Integer> seq2 = new ArrayList<Integer>();
			if (root2 != null) {
				dfs(root2, seq2);
			}

			return seq1.equals(seq2);
		}

		public void dfs(TreeNode node, List<Integer> seq) {
			if (node.left == null && node.right == null) {
				seq.add(node.val);
			} else {
				if (node.left != null) {
					dfs(node.left, seq);
				}
				if (node.right != null) {
					dfs(node.right, seq);
				}
			}
		}

	}
	
	//1734. 解码异或后的排列
	@Test
	public void test16() {
		Assert.assertArrayEquals(new int[] {1,2,3}, new Solution16().decode(new int[] {3,1}));
		Assert.assertArrayEquals(new int[] {2,4,1,5,3}, new Solution16().decode(new int[] {6,5,4,6}));
	}
	//https://leetcode-cn.com/problems/decode-xored-permutation/solution/jie-ma-yi-huo-hou-de-pai-lie-by-leetcode-9gw4/
	class Solution16 {
	    public int[] decode(int[] encoded) {
	    		//计算前n个数异或的结果
	    		int n = encoded.length+1;
	    		int total = 0;
	    		for (int i=1;i<=n;i++) {
	    			total^=i;
	    		}
	    		//计算除perm[0]以外的异或结果
	    		int odd=0;
	    		for (int i=1;i<n-1;i+=2) {
	    			odd ^= encoded[i]; //encoded[i]=perm[i] xor perm[i+1]
	    		}
	    		//获取perm中第一个数
	    		int[] prem = new int[n];
	    		prem[0] = total^odd;
	    		//在计算其他数
	    		for (int i=1;i<n;i++) {
	    			prem[i] = encoded[i-1]^prem[i-1];
	    		}
	    		return prem;
	    }	
	}
}
