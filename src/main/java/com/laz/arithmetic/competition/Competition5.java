package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/biweekly-contest-37
//第 37 场双周赛
public class Competition5 {
	@Test
	//删除某些元素后的数组均值
	public void test1() {
		{
			double d = trimMean(new int[] {
					6,2,7,5,1,2,0,3,10,2,5,0,5,5,0,8,7,6,8,0
			});
			System.out.println(d);
		}
		{
			double d = trimMean(new int[] {
					9,7,8,7,7,8,4,4,6,8,8,7,6,8,8,9,2,6,0,0,1,10,8,6,3,3,5,1,10,9,0,7,10,0,10,4,1,10,6,9,3,6,0,0,2,7,0,6,7,2,9,7,7,3,0,1,6,1,10,3
			});
			System.out.println(d);
		}
		{
			double d = trimMean(new int[] {
					4,8,4,10,0,7,1,3,7,8,8,3,4,1,6,2,1,1,8,0,9,8,0,3,9,10,3,10,1,10,7,3,2,1,4,9,10,7,6,4,0,8,5,1,2,1,6,2,5,0,7,10,9,10,3,7,10,5,8,5,7,6,7,6,10,9,5,10,5,5,7,2,10,7,7,8,2,0,1,1
			});
			System.out.println(d);
		}
	}
	public double trimMean(int[] arr) {
		Arrays.sort(arr);
		int v = (int) (arr.length*0.05);
		double sum =0;
		for (int i=v;i<(arr.length-v);i++) {
			sum+=arr[i];
		}
		return sum/(arr.length-2*v);
    }
	
	//网络信号最好的坐标
	@Test
	public void test2() {
		Assert.assertArrayEquals(new int[] {2,1}, bestCoordinate(new int[][] {
			{1,2,5},
			{2,1,7},
			{3,1,9}
		},2));
		
		Assert.assertArrayEquals(new int[] {23,11}, bestCoordinate(new int[][] {
			{23,11,21}
		},9));
		
		Assert.assertArrayEquals(new int[] {1,2}, bestCoordinate(new int[][] {
			{1,2,13},
			{2,1,7},
			{0,1,9}
		},2));
		
		Assert.assertArrayEquals(new int[] {0,1}, bestCoordinate(new int[][] {
			{2,1,9},
			{0,1,9}
		},2));
	}
	public int[] bestCoordinate(int[][] towers, int radius) {
		int max = 0;
		int index = 0;
		int len = towers.length;
		for (int i=0;i<len;i++) {
			
			int strength = 0 ;
			//计算每个点的强度
			for (int j=0;j<len;j++) {
				//计算距离
				double distance = 0;
				if (i==j) {
					distance = 0;
				} else {
					distance=Math.sqrt(Math.pow(towers[j][0]-towers[i][0],2)+Math.pow(towers[j][1]-towers[i][1],2));
				}
				if (distance>radius) {
					strength+=0;
				} else {
					strength+= ((int)Math.floor(towers[j][2]/(1+distance)));
				}
				
			}
			
			if (strength>max) {
				max = strength;
				index = i;
			}
			if (strength==max) {
				if (towers[i][0]<towers[index][0] 
					|| (towers[i][0]==towers[index][0] && towers[i][1]<towers[index][1])) {
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
	
	//大小为 K 的不重叠线段的数目
	@Test
	public void test3() {
		Assert.assertEquals(5, new Solution3().numberOfSets(4, 2));
	}
	//https://leetcode-cn.com/problems/number-of-sets-of-k-non-overlapping-line-segments/solution/javadong-tai-gui-hua-jie-fa-by-huanglin/
	class Solution3 {
	    static final int MOD = 1_000_000_007;
	    
	    /**
	     *  dpT[i][j] 表示前i个点，以最后点为最后一个线段结尾，划分为j段的方案数。
			dp[i][j] 表示前i个点，划分为j段的方案数。
			明显有：
			dpT[i][j] = dpT[i - 1][j] + dp[i - 1][j - 1];
			dp[i][j] = dp[i - 1][j] + dpT[i][j]
	     * @param n
	     * @param k
	     * @return
	     */
	    public int numberOfSets(int n, int k) {
	        int[][] dpT = new int[n + 1][k + 1];
	        int[][] dp = new int[n + 1][k + 1];

	        for (int i = 2; i <=n; i++) {
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

	
	//奇妙序列
	@Test
	public void test4() {
		Fancy fancy = new Fancy();
		fancy.append(2);   // 奇妙序列：[2]
		fancy.addAll(3);   // 奇妙序列：[2+3] -> [5]
		fancy.append(7);   // 奇妙序列：[5, 7]
		fancy.multAll(2);  // 奇妙序列：[5*2, 7*2] -> [10, 14]
		fancy.getIndex(0); // 返回 10
		fancy.addAll(3);   // 奇妙序列：[10+3, 14+3] -> [13, 17]
		fancy.append(10);  // 奇妙序列：[13, 17, 10]
		fancy.multAll(2);  // 奇妙序列：[13*2, 17*2, 10*2] -> [26, 34, 20]
		fancy.getIndex(0); // 返回 26
		fancy.getIndex(1); // 返回 34
		fancy.getIndex(2); // 返回 20
	}
	class Fancy {
		List<Long> list;
	    public Fancy() {
	    	list = new ArrayList<Long>();
	    }
	    
	    public void append(int val) {
	    	list.add((long)val);
	    }
	    
	    public void addAll(int inc) {
	    	for (int i=0;i<list.size();i++) {
	    		list.set(i, list.get(i)+inc);
	    	}
	    }
	    
	    public void multAll(int m) {
	    	for (int i=0;i<list.size();i++) {
	    		list.set(i, (list.get(i)*m)%((int)Math.pow(10, 9)+7));
	    	}
	    }
	    
	    public int getIndex(int idx) {
	    	if (idx>=list.size()) {
	    		return -1;
	    	}
	    	return (int)(list.get(idx)%((int)Math.pow(10, 9)+7));
	    }
	}
}
 