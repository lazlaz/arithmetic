package com.laz.arithmetic.competition;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-242/
public class Competition30 {
	// 5764. 准时到达的列车最小时速
	@Test
	public void test2() {
		Assert.assertEquals(1, new Solution2().minSpeedOnTime(new int[] { 1, 3, 2 }, 6));
		Assert.assertEquals(3, new Solution2().minSpeedOnTime(new int[] { 1, 3, 2 }, 2.7));
		Assert.assertEquals(-1, new Solution2().minSpeedOnTime(new int[] { 1, 3, 2 }, 1.9));
	}

	class Solution2 {
		public int minSpeedOnTime(int[] dist, double hour) {
			if (dist.length > Math.ceil(hour)) return -1;
			int l = 1;
			int r = 1000_000_000;
			while (l <= r) {
				int mid = (l + r) >> 1;
				if (canArrive(dist, mid, hour)) {
					r = mid - 1;
				} else {
					l = mid + 1;
				}
			}
			return l;
		}

		private boolean canArrive(int[] dist, int mid, double hour) {
			int n = dist.length;
			double actualHour = 0;
			for (int i = 0; i < n; i++) {
				if (i != n - 1) {
					// actualHour += Math.ceil((double)dist[i]/mid);
					// 除法的向上取整，比Math.ceil快
					actualHour += (dist[i] + mid - 1) / mid;
				} else {
					actualHour += (double) dist[i] / mid;
				}
			}
			return actualHour <= hour;
		}
	}
}
