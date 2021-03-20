package com.laz.arithmetic.competition;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-232/
public class Competition22 {
	// 1792. 最大平均通过率
	@Test
	public void test3() {
		Assert.assertEquals(0.78333, maxAverageRatio(new int[][] { { 1, 2 }, { 3, 5 }, { 2, 2 } }, 2), 0.00001);
	}

	// https://leetcode-cn.com/problems/maximum-average-pass-ratio/solution/zui-da-ping-jun-tong-guo-lu-by-zerotrac2-84br/
	public double maxAverageRatio(int[][] classes, int extraStudents) {
		// 大根堆，谁+1人增长率最大，选谁
		PriorityQueue<double[]> stack = new PriorityQueue<double[]>(new Comparator<double[]>() {

			@Override
			public int compare(double[] o1, double[] o2) {
				double addRate1 = (o1[0] + 1) / (o1[1] + 1) - o1[0] / o1[1];
				double addRate2 = (o2[0] + 1) / (o2[1] + 1) - o2[0] / o2[1];
				return Double.compare(addRate2, addRate1);
			}
		});

		for (int[] cls : classes) {
			stack.offer(new double[] { cls[0], cls[1] });
		}
		for (int i = 0; i < extraStudents; i++) {
			// 选择增长率最大的出来
			double[] cls = stack.poll();
			stack.offer(new double[] { cls[0] + 1, cls[1] + 1 });
		}
		// 计算增长率
		double sum = 0;
		while (!stack.isEmpty()) {
			double[] v = stack.poll();
			sum += v[0] / v[1];
		}
		return sum / classes.length;
	}

	// 1793. 好子数组的最大分数
	@Test
	public void test4() {
		Assert.assertEquals(15, new Solution4().maximumScore(new int[] { 1, 4, 3, 7, 4, 5 }, 3));
	}

	// https://leetcode-cn.com/problems/maximum-score-of-a-good-subarray/solution/c-shuang-zhi-zhen-tan-xin-zui-jian-ji-zu-b3vf/
	class Solution4 {
		public int maximumScore(int[] nums, int k) {
			int res = 0;
			int l = k, r = k;
			int n = nums.length;
			int min = nums[k]; // 最小值初始化为nums[k];
			while (true) {
				// nums[r]>nums[k]一直加 //向右寻找以nums[k]为最小值的好子数组
				while (r < n && nums[r] >= min) {
					r++;
				}
				// nums[l]>nums[k]一直减 /向左寻找以nums[k]为最小值的好子数组
				while (l >= 0 && nums[l] >= min) {
					l--;
				}
				res = Math.max(res, min * ((r-1) - (l+1) + 1)); // 更新最大可能分数
				if (r >= n && l < 0) {
					break; //遍历完数组，直接退出循环
				}
				if (l >= 0 && r < n) {
					min = Math.max(nums[l], nums[r]); // 更新nums[k] 为左右边界中的较大者
				} else if (l < 0) {
					min = nums[r]; // 左边遍历完了，更新nums[k]为右边界
				} else {
					min = nums[l]; // 右边遍历完了，更新nums[k]为左边界
				}
			}
			return res;
		}
	}
}
