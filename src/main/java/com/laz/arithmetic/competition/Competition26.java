package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-236/
public class Competition26 {
	// 1823. 找出游戏的获胜者
	@Test
	public void test2() {
		Assert.assertEquals(3, new Solution2().findTheWinner(5, 2));
		Assert.assertEquals(1, new Solution2().findTheWinner(6, 5));
	}

	class Solution2 {
		public int findTheWinner(int n, int k) {
			List<Integer> list = new ArrayList<>();
			for (int i = 1; i <= n; i++) {
				list.add(i);
			}
//			int index = 0;
//			while (list.size()>1) {
//				int len = list.size();
//				int limit = len-index;
//				if (k>limit) {
//					//超过了长度，先到末尾，然后从头开始，在用剩余的长度%(len+1)
//					int go = k-limit;
//					index = (go-1)%len;
//				} else {
//					index = index+k-1;
//				}
//				list.remove(index);
//			}
			int idx = 0;
			while (n > 1) {
				idx = (idx + k - 1) % n;
				list.remove(idx);
				n--;
			}
			return list.get(0);
		}
	}

	// 1824. 最少侧跳次数
	@Test
	public void test3() {
		Assert.assertEquals(2, new Solution3().minSideJumps(new int[] { 0, 1, 2, 3, 0 }));
	}

	// https://leetcode-cn.com/problems/minimum-sideway-jumps/solution/dp-dong-tai-gui-hua-by-hu-li-hu-wai-dah5/
	class Solution3 {
		public int minSideJumps(int[] obstacles) {
			//表示当前位置 j 表示停留在第 j 个道的最小次数。
			int[] dp = new int[3]; 
			dp[1] = 0;
			dp[0] = dp[2] = 1;
			int n = obstacles.length;
			for (int i=1;i<n;i++) {
				int obs = obstacles[i];//当前点的障碍物
				//存储上一个点的状态
				int pre0 = dp[0];
				int pre1 = dp[1];
				int pre2 = dp[2];
				//最差就是调5*100000次
				Arrays.fill(dp, 500000); //有障碍则说明不可达，取最大值
				//如果改跑到没有障碍，则次数和前一次一致
				if (obs!=1) 
					dp[0] = pre0;
				if (obs!=2) 
					dp[1] = pre1;
				if (obs!=3) 
					dp[2] = pre2;
				
				//是否有更小的次数到达该点,前提是不能有障碍
				if (obs!=1) 
					dp[0] = Math.min(dp[0], Math.min(dp[1], dp[2])+1);
				if (obs!=2) 
					dp[1] = Math.min(dp[1], Math.min(dp[0], dp[2])+1);
				if (obs!=3) 
					dp[2] = Math.min(dp[2], Math.min(dp[0], dp[1])+1);
			}
			return Arrays.stream(dp).min().orElse(0);
		}
	}

}
