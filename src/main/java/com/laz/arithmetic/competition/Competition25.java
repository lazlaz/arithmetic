package com.laz.arithmetic.competition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import com.laz.arithmetic.Utils;

//https://leetcode-cn.com/contest/weekly-contest-235/
public class Competition25 {
	// 1817. 查找用户活跃分钟数
	@Test
	public void test2() {
		Assert.assertArrayEquals(new int[] { 0, 2, 0, 0, 0 }, new Solution2()
				.findingUsersActiveMinutes(new int[][] { { 0, 5 }, { 1, 2 }, { 0, 2 }, { 0, 5 }, { 1, 3 } }, 5));
	}

	class Solution2 {
		public int[] findingUsersActiveMinutes(int[][] logs, int k) {
			int[] res = new int[k];
			Arrays.fill(res, 0);
			Map<Integer, Set<Integer>> map = new HashMap<>();
			for (int i = 0; i < logs.length; i++) {
				int[] log = logs[i];
				Set<Integer> ids = map.getOrDefault(log[0], new HashSet<Integer>());
				ids.add(log[1]);
				map.put(log[0], ids);
			}
			Set<Entry<Integer, Set<Integer>>> ids = map.entrySet();
			for (Entry<Integer, Set<Integer>> id : ids) {
				res[id.getValue().size() - 1]++;
			}
			return res;
		}
	}

	// 1818. 绝对差值和
	@Test
	public void test3() throws IOException {
//		Assert.assertEquals(3, new Solution3().minAbsoluteSumDiff(new int[] { 1, 7, 5 }, new int[] { 2, 3, 5 }));
//		Assert.assertEquals(20, new Solution3().minAbsoluteSumDiff(new int[] { 1,10,4,4,2,7 }, new int[] { 9,3,5,1,7,4}));
//		Assert.assertEquals(9, new Solution3().minAbsoluteSumDiff(new int[] { 1,28,21 }, new int[] { 9,21,20}));
		List<Integer> list1 = new ArrayList<Integer>();
		{
			List<String> lines = IOUtils.readLines(this.getClass().getResourceAsStream("25_3_case_input.txt"));
			for (String line : lines) {
				String[] str = line.split(",");
				for (String num : str) {
					list1.add(Integer.valueOf(num));
				}
			}
		}
		List<Integer> list2 = new ArrayList<Integer>();
		{
			List<String> lines = IOUtils.readLines(this.getClass().getResourceAsStream("25_3_case_input2.txt"));
			for (String line : lines) {
				String[] str = line.split(",");
				for (String num : str) {
					list2.add(Integer.valueOf(num));
				}
			}
		}
		Assert.assertEquals(999979264,
				new Solution3().minAbsoluteSumDiff(Utils.listToIntArr(list1), Utils.listToIntArr(list2)));
	}

	class Solution3 {
		int MOD = 1000_000_007;

		public int minAbsoluteSumDiff(int[] nums1, int[] nums2) {
			int n = nums1.length;
			int[] minArr = new int[n];
			long res = 0;
			TreeSet<Integer> set = new TreeSet<>();
			for (int i = 0; i < n; i++) {
				minArr[i] = Math.abs(nums1[i] - nums2[i]);
				res = (res + minArr[i]);
				set.add(nums1[i]);
			}
			if (res == 0) {
				return 0;
			}
			// 计算每个min可优化的的值
			int diff = 0;
			for (int i = 0; i < n; i++) {
				diff = Math.max(optimize(set, nums2[i], minArr[i]), diff);
			}

			return (int) ((res - diff) % MOD);
		}

		private int optimize(TreeSet<Integer> set, int num2, int source) {
			int floorV = 0;
			try {
				floorV = set.floor(num2);
			} catch (NullPointerException e) {
				floorV = num2 - source;
			}
			int ceilV = 0;
			try {
				ceilV = set.ceiling(num2);
			} catch (NullPointerException e) {
				ceilV = num2 - source;
			}
			int diff = Math.min(Math.abs(num2 - floorV) % MOD, Math.abs(num2 - ceilV) % MOD);
			return source > diff ? (source - diff) % MOD : 0;
		}
	}

	// 1819. 序列中不同最大公约数的数目
	@Test
	public void test4() {
		Assert.assertEquals(5, new Solution4().countDifferentSubsequenceGCDs(new int[] { 6, 10, 3 }));
	}

	// https://leetcode-cn.com/problems/number-of-different-subsequences-gcds/solution/mei-ju-mei-ge-ke-neng-de-gong-yue-shu-by-rsd3/
	class Solution4 {
		public int countDifferentSubsequenceGCDs(int[] nums) {
			boolean[] visited = new boolean[200005];
			int max = Integer.MIN_VALUE;
			for (int num : nums) {
				visited[num] = true;
				max = Math.max(max, num);
			}
			int count = 0;
			// 公约数可能的范围【1, max】
			for (int i = 1; i <= max; i++) {
				//存在数组中的值符合条件
				if (visited[i]) {
					count++;
					continue;
				}
				int commonGCD = -1;
				// 检查所有i的倍数
				for (int j = i; j <= max; j += i) {
					// 在数组中的值，才能组成序列
					if (visited[j]) {
						if (commonGCD == -1) {
							commonGCD = j;
						} else {
							commonGCD = gcd(commonGCD, j);
						}
					}
				}
				// 如果这批序列的最大公约数=i,说明i符合条件
				if (i == commonGCD) {
					count++;
				}
			}
			return count;
		}

		// 遍历效率更高
		public int gcd(int x, int y) {
			int temp = 0;
			while (y != 0) {
				temp = x % y;
				x = y;
				y = temp;
			}
			return x;
		}
		// 求最大公约数
//		private int gcd(int x, int y) {
//			if (x == 0) {
//				return y;
//			}
//			return gcd(y % x, x);
//		}

	}
}
