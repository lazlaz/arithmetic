package com.laz.arithmetic.competition;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/biweekly-contest-50/
public class Competition27 {
	//1828. 统计一个圆中点的数目
	@Test
	public void test2() {
		Assert.assertArrayEquals(new int[] {3,2,2},new Solution2().countPoints2(new int[][] {
			{1,3},{3,3},{5,3},{2,2}
		}, new int[][] {
			{2,3,1},{4,3,1},{1,1,2}
		}));
	}
	class Solution2 {
	    public int[] countPoints(int[][] points, int[][] queries) {
	    	int pointLen = points.length;
	    	int circleNum = queries.length;
	    	int[] res = new int[circleNum];
	    	for (int i=0;i<circleNum;i++) {
	    		int count=0;
	    		//计算有多少点在圆中
	    		for (int j=0;j<pointLen;j++) {
	    			//点到圆心距离
	    			double distance = Math.sqrt(Math.pow(points[j][0]-queries[i][0], 2)+Math.pow(points[j][1]-queries[i][1], 2));
	    			if (distance<=queries[i][2]) {
	    				++count;
	    			}
	    		}
	    		res[i]=count;
	    	}
	    	return res;
	    	
	    }
	    //这种要快些
	    public int[] countPoints2(int[][] points, int[][] queries) {
	        
	        int n = points.length;
	        int q = queries.length;
	        int[] ans = new int[q];
	        int cnt = 0;
	        int res = 0;
	        for(int i = 0;i < q;i++){
	            cnt = 0;
	            int x = queries[i][0];
	            int y = queries[i][1];
	            int r = queries[i][2];
	            for(int j = 0;j < n;j++){
	                int xx = points[j][0];
	                int yy = points[j][1];
	                if((x - xx) * (x - xx) + (y - yy) * (y - yy) <= r * r) cnt++;
	            }
	            ans[res++] = cnt;
	        }
	        return ans;
	    }
	}
	
	//1829. 每个查询的最大异或值
	@Test
	public void test3() {
		Assert.assertArrayEquals(new int[] {0,3,2,3}, new Solution3().getMaximumXor(new int[] {
				0,1,1,3
		}, 2));
		
		Assert.assertArrayEquals(new int[] {5,2,6,5}, new Solution3().getMaximumXor(new int[] {
				2,3,4,7
		}, 3));
		
		Assert.assertArrayEquals(new int[] {4,3,6,4,6,7}, new Solution3().getMaximumXor(new int[] {
				0,1,2,2,5,7
		}, 3));
	}
	class Solution3 {
	    public int[] getMaximumXor(int[] nums, int maximumBit) {
	    	//提示：最大值为 2的maximumBit次方-1
	    	//由于0 <= nums[i] < 2的maximumBit次方这个条件，与k进行异或最大值为每位全位1 ，即2的maximumBit次方-1
	    	int max = (int) (Math.pow(2, maximumBit)-1);
	    	int xorValue = 0;
	    	for (int i=0;i<nums.length;i++) {
	    		xorValue ^= nums[i];	
	    	}
	    	int[] ans  = new int[nums.length];
	    	int index = 0;
	    	for (int i=nums.length-1;i>=0;i--) {
	    		ans[index++] = xorValue^max;
	    		xorValue = xorValue^nums[i];
	    		
	    	}
	    	return ans;
	    }
	}
}
