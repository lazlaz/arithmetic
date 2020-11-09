package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-214/problems/get-maximum-in-generated-array/
public class Competition8 {
	@Test
	public void test1() {
		Assert.assertEquals(3, getMaximumGenerated(7));
		Assert.assertEquals(1, getMaximumGenerated(2));
		Assert.assertEquals(2, getMaximumGenerated(3));
	}

	// 获取生成数组中的最大值
	public int getMaximumGenerated(int n) {
		int max = Integer.MIN_VALUE;
		int[] nums = new int[n + 1];
		for (int i = 0; i <= n; i++) {
			if (i == 0) {
				nums[i] = 0;
			} else if (i == 1) {
				nums[i] = 1;
			} else if (i % 2 == 0) {
				nums[i] = nums[i / 2];
			} else if (i % 2 == 1) {
				nums[i] = nums[i / 2] + nums[i / 2 + 1];
			}
			if (nums[i] > max) {
				max = nums[i];
			}
		}
		return max;
	}

	// 字符频次唯一的最小删除次数
	@Test
	public void test2() {
		Assert.assertEquals(2, minDeletions("ceabaacb"));
		Assert.assertEquals(2, minDeletions("aaabbbcc"));
		Assert.assertEquals(0, minDeletions("aab"));
		Assert.assertEquals(1, minDeletions("accdcdadddbaadbc"));
		Assert.assertEquals(3, minDeletions("abcabc"));
		Assert.assertEquals(2, minDeletions("bbcebab"));
		Assert.assertEquals(3, minDeletions("ddbcce"));
	}

	public int minDeletions(String s) {
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i = 0; i < s.length(); i++) {
			int v = map.getOrDefault(s.charAt(i), 0);
			map.put(s.charAt(i), ++v);
		}
		Set<Integer> set = new HashSet<Integer>();
		int res = 0;
		for (Integer value : map.values()) {
			if (set.contains(value)) {
				for (int v = value - 1; v >= 0; v--) {
					if (!set.contains(v)) {
						res += value - v;
						if (v != 0) {
							set.add(v);
						}
						break;
					}
				}
			} else {
				set.add(value);
			}

		}
		return res;

	}

	// 销售价值减少的颜色球
	@Test
	public void test3() {
//		Assert.assertEquals(19, new Solution3().maxProfit(new int[] {
//				3,5
//		},6));
		Assert.assertEquals(14, new Solution3().maxProfit(new int[] { 2, 5 }, 4));
		Assert.assertEquals(110, new Solution3().maxProfit(new int[] { 2, 8, 4, 10, 6 }, 20));
		Assert.assertEquals(21, new Solution3().maxProfit(new int[] { 1000000000 }, 1000000000));
		Assert.assertEquals(373219333,
				new Solution3().maxProfit(new int[] { 497978859, 167261111, 483575207, 591815159 }, 836556809));
	}

	// https://leetcode-cn.com/problems/sell-diminishing-valued-colored-balls/solution/liang-chong-si-lu-you-hua-tan-xin-suan-fa-you-hua-/
	// https://leetcode-cn.com/problems/sell-diminishing-valued-colored-balls/solution/xiao-shou-jie-zhi-jian-shao-de-yan-se-qiu-by-zerot/
	class Solution3 {
		public int maxProfit(int[] inventory, int orders) {
			int mod = (int) (1e9 + 7);
			int l = 0, r = maxNum(inventory);
			while (l < r) {
				int mid = l + (r - l) / 2;
				if (provideOrders(inventory, mid) <= orders) {
					r = mid;
				} else {
					l = mid + 1;
				}
			}
			long res = 0;
			for (int num : inventory) {
				if (num >= l) {
					orders -= (num - l + 1);
					long first = l, last = num, n = num - l + 1;
					res = (res + ((first + num) * n / 2) % mod) % mod;
				}
			}
			res = (res + (long) orders * (l - 1) % mod) % mod;
			return (int) res;
		}

		private long provideOrders(int[] inventory, int m) {
			long orders = 0;
			for (int num : inventory) {
				orders += Math.max(num - m + 1, 0);
			}
			return orders;
		}

		private int maxNum(int[] inventory) {
			int max = 0;
			for (int num : inventory) {
				max = Math.max(max, num);
			}
			return max;
		}

	}

	// 通过指令创建有序数组
	@Test
	public void test4() {
		Assert.assertEquals(1, new Solution4().createSortedArray(new int[] { 1, 5, 6, 2 }));
	}

	// https://leetcode-cn.com/problems/create-sorted-array-through-instructions/solution/java-frenwick-tree-shuang-bai-by-zanyjoker/
	class Solution4 {
		public int createSortedArray(int[] instructions) {
			int mod = (int) 1e9 + 7;
			long ans = 0;
			Set<Integer> set = new HashSet<>();
			for (int i = 0; i < instructions.length; ++i) {
				set.add(instructions[i]);
			}
			int[] nums = new int[set.size()];
			int index = 0;
			for (int num : set) {
				nums[index++] = num;
			}
			Arrays.sort(nums);
			Map<Integer, Integer> map = new HashMap<>();
			for (int i = 0; i < nums.length; ++i) {
				map.put(nums[i], i + 1);
			}

			BIT bit = new BIT(nums.length);

			for (int i = 0; i < instructions.length; ++i) {

				int t = map.get(instructions[i]);
				int l = bit.getSum(t - 1); //index 左边的和
				int r = bit.getSum(t); //index 右边的和=目前总和i-t左边的和
				// System.out.println(l);
				// System.out.println(r);
				ans += Math.min(l, i - r);
				ans %= mod;
				bit.update(t, 1);
			}
			return (int) ans;
		}
	}

	class BIT {
		int n;
		int[] tree;

		public BIT(int n) {
			this.n = n;
			tree = new int[n + 1];
		}

		public void update(int i, int val) {
			while (i <= n) {
				tree[i] += val;
				i += lowbit(i);
			}
		}

		public int getSum(int i) {
			int res = 0;
			while (i > 0) {
				res += tree[i];
				i -= lowbit(i);
			}
			return res;
		}

		public int lowbit(int x) {
			return x & (-x);
		}
	}
}
