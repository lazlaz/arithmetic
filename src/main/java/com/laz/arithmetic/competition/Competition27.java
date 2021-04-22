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
}
