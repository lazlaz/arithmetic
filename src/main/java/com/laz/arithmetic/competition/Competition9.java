package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

//https://leetcode-cn.com/problems/design-an-ordered-stream/
public class Competition9 {
	// 设计有序流
	@Test
	public void test1() {
		OrderedStream os = new OrderedStream(5);
		System.out.println(Joiner.on(",").join(os.insert(3, "ccccc"))); // 插入 (3, "ccccc")，返回 []
		System.out.println(Joiner.on(",").join(os.insert(1, "aaaaa"))); // 插入 (1, "aaaaa")，返回 ["aaaaa"]
		System.out.println(Joiner.on(",").join(os.insert(2, "bbbbb"))); // 插入 (2, "bbbbb")，返回 ["bbbbb", "ccccc"]
		System.out.println(Joiner.on(",").join(os.insert(5, "eeeee"))); // 插入 (5, "eeeee")，返回 []
		System.out.println(Joiner.on(",").join(os.insert(4, "ddddd"))); // 插入 (4, "ddddd")，返回 ["ddddd", "eeeee"]
	}

	class OrderedStream {
		int ptr = 0;
		private String[] arr;

		public OrderedStream(int n) {
			arr = new String[n];
		}

		public List<String> insert(int id, String value) {
			arr[id - 1] = value;
			if (arr[ptr] == null) {
				return Collections.EMPTY_LIST;
			}
			List<String> res = new ArrayList<String>();
			while (ptr < arr.length && arr[ptr] != null) {
				res.add(arr[ptr]);
				ptr++;
			}
			return res;
		}
	}

	// 确定两个字符串是否接近
	@Test
	public void test2() {
		Assert.assertEquals(true, closeStrings("abc", "bca"));
		Assert.assertEquals(false, closeStrings("a", "aa"));
		Assert.assertEquals(true, closeStrings("cabbba", "abbccc"));
		Assert.assertEquals(false, closeStrings("cabbba", "aabbss"));
	}

	public boolean closeStrings(String word1, String word2) {
		if (word1.length() != word2.length()) {
			return false;
		}
		int[] arr1 = new int[26];
		for (int i = 0; i < word1.length(); i++) {
			arr1[word1.charAt(i) - 'a']++;
		}
		int[] arr2 = new int[26];
		for (int i = 0; i < word2.length(); i++) {
			arr2[word2.charAt(i) - 'a']++;
		}
		// 判断是否有存在word1不存在word2的字符，反之也是
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] > 0 && arr2[i] == 0) {
				return false;
			}
			if (arr2[i] > 0 && arr1[i] == 0) {
				return false;
			}
		}
		// 排序后判断数组字符数量是否一一匹配，如abbzzca和babzzcz，字符数量分别为2、2、2、1和3、2、1、1则不可能匹配
		Arrays.sort(arr1);
		Arrays.sort(arr2);
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// 将 x 减到 0 的最小操作数
	@Test
	public void test3() {
		// Assert.assertEquals(2, minOperations(new int[] { 1, 1, 4, 2, 3 }, 5));
		Assert.assertEquals(5, minOperations(new int[] { 3, 2, 20, 1, 1, 3 }, 10));
	}

	// https://leetcode-cn.com/problems/minimum-operations-to-reduce-x-to-zero/solution/java-shuang-bai-qian-zhui-he-hou-zhui-he-twosum-by/
	public int minOperations(int[] nums, int x) {
		int len = nums.length;
		// 前缀和
		int[] preSum = new int[len + 1];

		for (int i = 0; i < len; i++) {
			preSum[i + 1] = preSum[i] + nums[i];
		}

		// 后缀和
		Map<Integer, Integer> map = new HashMap<>();

		int[] latterSum = new int[len + 1];
		map.put(0, 0);
		int idx = 1;
		for (int i = len - 1; i >= 0; i--) {
			latterSum[idx] = latterSum[idx - 1] + nums[i];
			map.put(latterSum[idx], idx);
			idx++;
		}
		if (preSum[len] < x || latterSum[len] < x)
			return -1;// 避免多次计算
		int res = Integer.MAX_VALUE;

		for (int i = 0; i < len + 1; i++) {
			int pre = preSum[i];
			if (map.containsKey(x - pre)) {
				res = Math.min(res, map.get(x - pre) + i);
			}
		}

		return res == Integer.MAX_VALUE ? -1 : res;
	}

	// 最大化网格幸福感
	@Test
	public void test4() {
		Assert.assertEquals(240, new Solution4().getMaxGridHappiness(2, 3, 1, 2));
	}

	// https://leetcode-cn.com/problems/maximize-grid-happiness/solution/zui-da-hua-wang-ge-xing-fu-gan-by-zerotrac2/
	class Solution4 {
		// 预处理：每一个 mask 的三进制表示
		int mask_span[][] = new int[729][6];
		// dp[位置][轮廓线上的 mask][剩余的内向人数][剩余的外向人数]
		int dp[][][][] = new int[25][729][7][7];
		// 预处理：每一个 mask 去除最高位、乘 3、加上新的最低位的结果
		int truncate[][] = new int[729][3];
		// n3 = n^3
		int m, n, n3;

		public int getMaxGridHappiness(int m, int n, int nx, int wx) {
			this.m = m;
			this.n = n;
			this.n3 = (int) Math.pow(3, n);

			// 预处理
			int highest = this.n3 / 3;
			for (int mask = 0; mask < n3; ++mask) {
				for (int mask_tmp = mask, i = 0; i < n; ++i) {
					// 与方法一不同的是，这里需要反过来存储，这样 [0] 对应最高位，[n-1] 对应最低位
					mask_span[mask][n - i - 1] = mask_tmp % 3;
					mask_tmp /= 3;
				}
				truncate[mask][0] = mask % highest * 3;
				truncate[mask][1] = mask % highest * 3 + 1;
				truncate[mask][2] = mask % highest * 3 + 2;
			}
			//初始化dp值为-1
			for (int i=0;i<dp.length;i++) {
				for (int j=0;j<dp[i].length;j++) {
					for (int w=0;w<dp[i][j].length;w++) {
						for (int z=0;z<dp[i][j][w].length;z++) {
							dp[i][j][w][z] = -1;
						}
					}
				}
			}
			return dfs(0, 0, nx, wx);
		}

		// 如果 x 和 y 相邻，需要加上的分数
		int calc(int x, int y) {
			if (x == 0 || y == 0) {
				return 0;
			}
			// 例如两个内向的人，每个人要 -30，一共 -60，下同
			if (x == 1 && y == 1) {
				return -60;
			}
			if (x == 2 && y == 2) {
				return 40;
			}
			return -10;
		}

		// dfs(位置，轮廓线上的 mask，剩余的内向人数，剩余的外向人数)
		int dfs(int pos, int borderline, int nx, int wx) {
	        // 边界条件：如果已经处理完，或者没有人了
	        if (pos == m * n || nx + wx == 0) {
	            return 0;
	        }
	        // 记忆化
	        if (dp[pos][borderline][nx][wx] != -1) {
	            return dp[pos][borderline][nx][wx];
	        }
	        
	        int x = pos / n, y = pos % n;
	        
	        // 什么都不做
	        int best = dfs(pos + 1, truncate[borderline][0], nx, wx);
	        // 放一个内向的人
	        if (nx > 0) {
	            best = Math.max(best, 120 + calc(1, mask_span[borderline][0]) 
	                                 + (y == 0 ? 0 : calc(1, mask_span[borderline][n - 1])) 
	                                 + dfs(pos + 1, truncate[borderline][1], nx - 1, wx));
	        }
	        // 放一个外向的人
	        if (wx > 0) {
	            best = Math.max(best, 40 + calc(2, mask_span[borderline][0]) 
	                                + (y == 0 ? 0 : calc(2, mask_span[borderline][n - 1])) 
	                                + dfs(pos + 1, truncate[borderline][2], nx, wx - 1));
	        }

	        return dp[pos][borderline][nx][wx] = best;
	    }
	}
}
