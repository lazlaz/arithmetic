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
	

	//1871. 跳跃游戏 VII
	@Test
	public void test3() {
		Assert.assertEquals(true, new Solution3().canReach("011010", 2, 3));
	}
	//https://leetcode-cn.com/problems/jump-game-vii/solution/tiao-yue-you-xi-vii-by-leetcode-solution-rsyv/
	class Solution3 {
	    public boolean canReach(String s, int minJump, int maxJump) {
	    	//动态规划
	    	int n = s.length();
	    	int[] dp = new int[n]; //能够顺利达到i，且i对应的值为0，为true
	    	int[] pre = new int[n]; //dp[i]前缀和，true为1，false为0，降低时间复杂度
	    	dp[0] = 1;
	    	for (int i=0;i<minJump;i++) {
	    		pre[i] = 1; //minJump之前的点不能正常达到，所以前缀和为1
	    	}
	    	for (int i=minJump;i<n;i++) {
	    		int right = i-minJump;
	    		int left = i-maxJump;
	    		if (s.charAt(i) == '0') {
	    			int v = pre[right]- (left>0?pre[left-1]:0);
	    			if (v>0) { //说明有位置能够顺利到达i
	    				dp[i] = 1;
	    			} else {
	    				dp[i] = 0;
	    			}
	    		} else {
	    			dp[i] = 0;
	    		}
	    		pre[i] = pre[i-1]+dp[i];
	    		
	    	}
	    	return dp[n-1]==1;
	    }
	}
	
	//1872. 石子游戏 VIII
	@Test
	public void test4() {
		Assert.assertEquals(5, new Solution4().stoneGameVIII(new int[] {
				-1,2,-3,4,-5
		}));
	}
	//https://leetcode-cn.com/problems/stone-game-viii/solution/shi-zi-you-xi-viii-by-leetcode-solution-e8dx/
	class Solution4 {
	    public int stoneGameVIII(int[] stones) {
	    	//前缀和
	    	int n = stones.length;
	    	int[]  pre = new int[n+1];
	    	for (int i=0;i<n;i++) {
	    		pre[i+1] = pre[i]+stones[i];
	    	}
	    	//动态规划，dp[i]表示当Alice 可以选择的下标 u 在 [i,n) 范围内时，Alice 与 Bob 分数的最大差值。
	    	int[] dp = new int[n];
	    	dp[n-1] = pre[n];
	    	for (int i=n-2;i>=1;i--) {
	    		dp[i] = Math.max(dp[i+1], pre[i+1]-dp[i+1]);//转移方程
	    	}
	    	return dp[1]; //条件x>1,至少选择两个
	    	
	    }
	}
}
