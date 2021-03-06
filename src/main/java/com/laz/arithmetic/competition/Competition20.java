package com.laz.arithmetic.competition;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-230
public class Competition20 {
	// 5689. 统计匹配检索规则的物品数量
	@Test
	public void test1() {
		List<List<String>> items = new ArrayList<>();
		items.add(Arrays.asList("phone", "blue", "pixel"));
		items.add(Arrays.asList("computer", "silver", "lenovo"));
		items.add(Arrays.asList("phone", "gold", "iphone"));
		Assert.assertEquals(1, countMatches(items, "color", "silver"));
	}

	public int countMatches(List<List<String>> items, String ruleKey, String ruleValue) {
		int ret = 0;
		for (List<String> list : items) {
			if (ruleKey.equals("type")) {
				if (list.get(0).equals(ruleValue)) {
					ret++;
				}
			}
			if (ruleKey.equals("color")) {
				if (list.get(1).equals(ruleValue)) {
					ret++;
				}
			}
			if (ruleKey.equals("name")) {
				if (list.get(2).equals(ruleValue)) {
					ret++;
				}
			}
		}
		return ret;
	}

	// 5690. 最接近目标价格的甜点成本
	@Test
	public void test2() {
		// Assert.assertEquals(10, closestCost(new int[] { 1, 7 }, new int[] { 3, 4 },
		// 10));

		Assert.assertEquals(17, new Solution2().closestCost(new int[] { 2, 3 }, new int[] { 4, 5, 100 }, 18));

		Assert.assertEquals(100, new Solution2().closestCost(new int[] { 2, 3 }, new int[] { 1, 40, 45, 97 }, 100));
	}

	class Solution2 {

		int ans = Integer.MAX_VALUE;

		public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
			for (int i = 0; i < baseCosts.length; i++) {
				int sum = baseCosts[i];
				if (sum == target)
					return target;
				dfs(toppingCosts, sum, target, 0);
				if (ans == target)
					return target;

			}
			return ans;
		}

		public void dfs(int[] toppingCosts, int sum, int target, int index) {
			if (Math.abs(sum - target) < Math.abs(ans - target)) {
				ans = sum;
			} else if (Math.abs(sum - target) == Math.abs(ans - target) && sum < ans) {
				ans = sum;
			} else if (sum > target) {
				return;
			}
			if (sum == target)
				return;
			if (index == toppingCosts.length) {
				return;
			}
			dfs(toppingCosts, sum, target, index + 1);
			dfs(toppingCosts, sum + toppingCosts[index], target, index + 1);
			dfs(toppingCosts, sum + 2 * toppingCosts[index], target, index + 1);
		}
	}

	// 1776. 车队 II
	@Test
	public void test4() {
//		Assert.assertArrayEquals(new double[] { 1.00000, -1.00000, 3.00000, -1.00000 },
//				new Solution4().getCollisionTimes(new int[][] { { 1, 2 }, { 2, 1 }, { 4, 3 }, { 7, 2 } }), 5);
		double[] x = new Solution4().getCollisionTimes(new int[][] { { 3, 1 }, { 9, 4 }, { 19, 4 } });
		System.out.println(Arrays.toString(x));
	}

	// https://leetcode-cn.com/problems/car-fleet-ii/solution/javadan-diao-zhan-jie-jue-che-dui-zhui-j-8744/
	class Solution4 {
		public double[] getCollisionTimes(int[][] cars) {
			// 单调栈
			Deque<Integer> stack = new ArrayDeque();
			int n = cars.length;
			double[] res = new double[n];
			for (int i = n - 1; i >= 0; --i) {
				while (!stack.isEmpty()) {
					if (cars[i][1] <= cars[stack.peek()][1]) { // 追不上,尝试追前前车
						stack.pop();
					} else {
						// 栈顶车撞不上它前面的车，因此，当前车一定可以撞上栈顶车
						if (res[stack.peek()] < 0) {
							break;
						} else {
							double v = (double) (cars[stack.peek()][0] - cars[i][0])
									/ (double) (cars[i][1] - cars[stack.peek()][1]);// 追上的时间
							if (v > res[stack.peek()]) { // 大于了前车追上前前车的时间，则该车改为追上前前车
								stack.pop();
							} else {// 追上
								break;
							}
						}

					}

				}
				if (stack.isEmpty()) {
					res[i] = -1;
				} else {
					res[i] = (double) (cars[stack.peek()][0] - cars[i][0])
							/ (double) (cars[i][1] - cars[stack.peek()][1]);
				}
				stack.push(i);
			}

			return res;
		}
	}
	// 1775. 通过最少操作次数使数组的和相等
		@Test
		public void test3() {
			Assert.assertEquals(3, minOperations(new int[] { 1, 2, 3, 4, 5, 6 }, new int[] { 1, 1, 2, 2, 2, 2 }));
		}

		// https://leetcode-cn.com/problems/equal-sum-arrays-with-minimum-number-of-operations/solution/tong-guo-zui-shao-cao-zuo-ci-shu-shi-shu-o8no/
		// 贪心
		public int minOperations(int[] nums1, int[] nums2) {
			int sum1 = 0;
			int sum2 = 0;
			for (int i = 0; i < nums1.length; i++) {
				sum1 += nums1[i];
			}
			for (int i = 0; i < nums2.length; i++) {
				sum2 += nums2[i];
			}
			if (sum1 == sum2) {
				return 0;
			}
			if (sum1 > sum2) {
				return minOperations(nums2, nums1);
			}
			int[] arr = new int[6];// 存储1-6之间可以操作的次数
			for (int num : nums1) {
				arr[6 - num]++; // 最多可以加
			}
			for (int num : nums2) {
				arr[num - 1]++; // 最多可以减
			}
			int ans = 0;
			int diff = sum2 - sum1;
			for (int i = 5; i >= 1; i--) { // 从最大的数开始操作
				while (arr[i] != 0 && diff > 0) {
					diff -= i;
					arr[i]--;
					ans++;
				}
			}
			return diff > 0 ? -1 : ans;
		}
}
