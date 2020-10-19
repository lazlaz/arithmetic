package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.laz.arithmetic.datastructure.SegmentTree2;

//https://leetcode-cn.com/contest/biweekly-contest-37
//第 37 场双周赛
public class Competition5 {
	@Test
	// 删除某些元素后的数组均值
	public void test1() {
		{
			double d = trimMean(new int[] { 6, 2, 7, 5, 1, 2, 0, 3, 10, 2, 5, 0, 5, 5, 0, 8, 7, 6, 8, 0 });
			System.out.println(d);
		}
		{
			double d = trimMean(new int[] { 9, 7, 8, 7, 7, 8, 4, 4, 6, 8, 8, 7, 6, 8, 8, 9, 2, 6, 0, 0, 1, 10, 8, 6, 3,
					3, 5, 1, 10, 9, 0, 7, 10, 0, 10, 4, 1, 10, 6, 9, 3, 6, 0, 0, 2, 7, 0, 6, 7, 2, 9, 7, 7, 3, 0, 1, 6,
					1, 10, 3 });
			System.out.println(d);
		}
		{
			double d = trimMean(new int[] { 4, 8, 4, 10, 0, 7, 1, 3, 7, 8, 8, 3, 4, 1, 6, 2, 1, 1, 8, 0, 9, 8, 0, 3, 9,
					10, 3, 10, 1, 10, 7, 3, 2, 1, 4, 9, 10, 7, 6, 4, 0, 8, 5, 1, 2, 1, 6, 2, 5, 0, 7, 10, 9, 10, 3, 7,
					10, 5, 8, 5, 7, 6, 7, 6, 10, 9, 5, 10, 5, 5, 7, 2, 10, 7, 7, 8, 2, 0, 1, 1 });
			System.out.println(d);
		}
	}

	public double trimMean(int[] arr) {
		Arrays.sort(arr);
		int v = (int) (arr.length * 0.05);
		double sum = 0;
		for (int i = v; i < (arr.length - v); i++) {
			sum += arr[i];
		}
		return sum / (arr.length - 2 * v);
	}

	// 网络信号最好的坐标
	@Test
	public void test2() {
		Assert.assertArrayEquals(new int[] { 2, 1 },
				bestCoordinate(new int[][] { { 1, 2, 5 }, { 2, 1, 7 }, { 3, 1, 9 } }, 2));

		Assert.assertArrayEquals(new int[] { 23, 11 }, bestCoordinate(new int[][] { { 23, 11, 21 } }, 9));

		Assert.assertArrayEquals(new int[] { 1, 2 },
				bestCoordinate(new int[][] { { 1, 2, 13 }, { 2, 1, 7 }, { 0, 1, 9 } }, 2));

		Assert.assertArrayEquals(new int[] { 0, 1 }, bestCoordinate(new int[][] { { 2, 1, 9 }, { 0, 1, 9 } }, 2));
	}

	public int[] bestCoordinate(int[][] towers, int radius) {
		int max = 0;
		int index = 0;
		int len = towers.length;
		for (int i = 0; i < len; i++) {

			int strength = 0;
			// 计算每个点的强度
			for (int j = 0; j < len; j++) {
				// 计算距离
				double distance = 0;
				if (i == j) {
					distance = 0;
				} else {
					distance = Math
							.sqrt(Math.pow(towers[j][0] - towers[i][0], 2) + Math.pow(towers[j][1] - towers[i][1], 2));
				}
				if (distance > radius) {
					strength += 0;
				} else {
					strength += ((int) Math.floor(towers[j][2] / (1 + distance)));
				}

			}

			if (strength > max) {
				max = strength;
				index = i;
			}
			if (strength == max) {
				if (towers[i][0] < towers[index][0]
						|| (towers[i][0] == towers[index][0] && towers[i][1] < towers[index][1])) {
					max = strength;
					index = i;
				}
			}
		}
		int[] res = new int[2];
		res[0] = towers[index][0];
		res[1] = towers[index][1];
		return res;
	}

	// 大小为 K 的不重叠线段的数目
	@Test
	public void test3() {
		Assert.assertEquals(5, new Solution3().numberOfSets(4, 2));
	}

	// https://leetcode-cn.com/problems/number-of-sets-of-k-non-overlapping-line-segments/solution/javadong-tai-gui-hua-jie-fa-by-huanglin/
	class Solution3 {
		static final int MOD = 1_000_000_007;

		/**
		 * dpT[i][j] 表示前i个点，以最后点为最后一个线段结尾，划分为j段的方案数。 dp[i][j] 表示前i个点，划分为j段的方案数。 明显有：
		 * dpT[i][j] = dpT[i - 1][j] + dp[i - 1][j - 1]; dp[i][j] = dp[i - 1][j] +
		 * dpT[i][j]
		 * 
		 * @param n
		 * @param k
		 * @return
		 */
		public int numberOfSets(int n, int k) {
			int[][] dpT = new int[n + 1][k + 1];
			int[][] dp = new int[n + 1][k + 1];

			for (int i = 2; i <= n; i++) {
				dp[i][1] = i * (i - 1) / 2;
				dpT[i][1] = i - 1;
			}

			for (int j = 2; j <= k; j++) {
				dpT[j + 1][j] = 1;
				dp[j + 1][j] = 1;
				for (int i = j + 1; i <= n; i++) {
					dpT[i][j] = dpT[i - 1][j] + dp[i - 1][j - 1];
					dpT[i][j] %= MOD;
					dp[i][j] = dp[i - 1][j] + dpT[i][j];
					dp[i][j] %= MOD;
				}
			}

			return dp[n][k];
		}
	}

	// 奇妙序列
	@Test
	public void test4() {
		Solution4.Fancy fancy = new Solution4().new Fancy();
		fancy.append(2); // 奇妙序列：[2]
		fancy.addAll(3); // 奇妙序列：[2+3] -> [5]
		fancy.append(7); // 奇妙序列：[5, 7]
		fancy.multAll(2); // 奇妙序列：[5*2, 7*2] -> [10, 14]
		fancy.getIndex(0); // 返回 10
		fancy.addAll(3); // 奇妙序列：[10+3, 14+3] -> [13, 17]
		fancy.append(10); // 奇妙序列：[13, 17, 10]
		fancy.multAll(2); // 奇妙序列：[13*2, 17*2, 10*2] -> [26, 34, 20]
		fancy.getIndex(0); // 返回 26
		fancy.getIndex(1); // 返回 34
		fancy.getIndex(2); // 返回 20
	}
	 class Solution4 {
		

		class Fancy {
			    private static final int maxCount = (int)1e5 + 10;
			    SegmentTree2 fancySegmentTree;
			    int capacity;
			    public Fancy() {
			        fancySegmentTree = new SegmentTree2(maxCount);
			        capacity = 0;
			    }

			    public void append(int val) {
			        fancySegmentTree.update(capacity, capacity, 1, val);
			        ++capacity;
			    }

			    public void addAll(int inc) {
			        if (capacity > 0){
			            fancySegmentTree.update(0, capacity - 1, 1, inc);
			        }
			    }

			    public void multAll(int m) {
			        if (capacity > 0){
			            fancySegmentTree.update(0, capacity - 1, m, 0);
			        }
			    }

			    public int getIndex(int idx) {
			        return idx >= capacity ? -1 : (int)fancySegmentTree.query(idx, idx);
			    }
		}

	}
	
	// 普通实现超时，需要优化addAll,multAll时间复杂度，利用线段树延迟更新原理（还是超时）
	class Fancy {
		List<Integer> list;
		static final int MOD = 1_000_000_007;
		boolean[] opt = null; // 操作集合0+,1*
		int optIndex = 0;
		int[] optValue = null;// 操作值集合
		int optValueIndex = 0;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();// 保存index索引下的值，开始经历的操作

		public Fancy() {
			list = new ArrayList<Integer>();
			this.opt = new boolean[1000];
			this.optValue = new int[1000];
		}

		public void append(int val) {
			list.add(val);
			int index = optIndex;
			map.put((list.size() - 1), index);
		}

		public void addAll(int inc) {
			if (optIndex >= opt.length) {
				opt = Arrays.copyOf(opt, opt.length + 1000);
			}
			opt[optIndex] = false;
			optIndex++;

			if (optValueIndex >= optValue.length) {
				optValue = Arrays.copyOf(optValue, optValue.length + 1000);
			}
			optValue[optValueIndex] = inc;
			optValueIndex++;

		}

		public void multAll(int m) {
			if (optIndex >= opt.length) {
				opt = Arrays.copyOf(opt, opt.length + 1000);
			}
			opt[optIndex] = true;
			optIndex++;

			if (optValueIndex >= optValue.length) {
				optValue = Arrays.copyOf(optValue, optValue.length + 1000);
			}
			optValue[optValueIndex] = m;
			optValueIndex++;

		}

		public int getIndex(int idx) {
			if (idx >= list.size()) {
				return -1;
			}
			int v = list.get(idx);
			int index = map.getOrDefault(idx, 0);
			for (int i = index; i < opt.length; i++) {
				if (!opt[i]) {
					v = (int) (((long) v + optValue[i]) % MOD);
				} else {
					v = (int) (((long) v * optValue[i]) % MOD);
				}
			}
			list.set(idx, v);
			map.put(idx, optIndex);
			return v;
		}
	}
}
