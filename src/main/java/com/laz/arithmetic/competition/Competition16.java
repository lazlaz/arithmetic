package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-222
public class Competition16 {
	// 5641. 卡车上的最大单元数
	@Test
	public void test1() {
		Assert.assertEquals(8, maximumUnits(new int[][] { { 1, 3 }, { 2, 2 }, { 3, 1 } }, 4));
		Assert.assertEquals(91, maximumUnits(new int[][] { { 5, 10 }, { 2, 5 }, { 4, 7 }, { 3, 9 } }, 10));
	}

	public int maximumUnits(int[][] boxTypes, int truckSize) {
		Arrays.sort(boxTypes, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o2[1] - o1[1];
			}
		});
		int ret = 0;
		for (int i = 0; i < boxTypes.length; i++) {
			if (truckSize >= boxTypes[i][0]) {
				ret += boxTypes[i][0] * boxTypes[i][1];
				truckSize -= boxTypes[i][0];
			} else {
				ret += truckSize * boxTypes[i][1];
				break;
			}
		}
		return ret;
	}

	// 5642. 大餐计数
	@Test
	public void test2() {
//		 Assert.assertEquals(4, new Solution2().countPairs(new int[] { 1, 3, 5, 7, 9
//		 }));

		// Assert.assertEquals(15, new Solution2().countPairs(new int[] { 1, 1, 1, 3, 3,
		// 3, 7 }));
		Assert.assertEquals(174,
				new Solution2().countPairs(new int[] { 2, 14, 11, 5, 1744, 2352, 0, 1, 1300, 2796, 0, 4, 376, 1672, 73,
						55, 2006, 42, 10, 6, 0, 2, 2, 0, 0, 1, 0, 1, 0, 2, 271, 241, 1, 63, 1117, 931, 3, 5, 378, 646,
						2, 0, 2, 0, 15, 1 }));
	}

	class Solution2 {
		public int countPairs(int[] deliciousness) {
			Arrays.sort(deliciousness);
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			int count = 0;
			final int MOD = (int) (Math.pow(10, 9) + 7);
			for (int i = 0; i < deliciousness.length; i++) {
				int value = 0;
				if (map.containsKey(deliciousness[i])) {
					int v = map.get(deliciousness[i]);
					if (v != 0) {
						int sum = deliciousness[i]+deliciousness[i];
						if (sum!=0&&powerof2(sum)) {
							count = (count + map.get(deliciousness[i]) - 1) % MOD;
							map.put(deliciousness[i], map.get(deliciousness[i]) - 1);
						} else {
							count = (count + map.get(deliciousness[i])) % MOD;
						}
					}
					continue;
				}
				for (int j = i + 1; j < deliciousness.length; j++) {
					int sum = deliciousness[i] + deliciousness[j];

					if (sum != 0 && powerof2(sum)) {
						value = (value + 1) % MOD;
					}
				}
				map.put(deliciousness[i], value);
				count = (count + value) % MOD;
				System.out.println(count+"   "+deliciousness[i]);
			}
			return count;
		}

		public boolean powerof2(int n) {
			return ((n & (n - 1)) == 0);
		}
	}
	
	//5643. 将数组分成三个子数组的方案数
	@Test
	public void test3() {
		Assert.assertEquals(1, waysToSplit(new int[] {
				1,1,1
		}));
//		
//		Assert.assertEquals(3, waysToSplit(new int[] {
//				1,2,2,2,5,0
//		}));
//		
//		Assert.assertEquals(0, waysToSplit(new int[] {
//				3,2,1
//		}));
		Assert.assertEquals(1, waysToSplit(new int[] {
				0,3,3
		}));
	}
	//https://leetcode-cn.com/problems/ways-to-split-array-into-three-subarrays/solution/c-qian-zhui-he-er-fen-sou-suo-by-bndsbil-ida2/
	public int waysToSplit(int[] nums) {
        int n = nums.length;
        int[] preSums = new int[n+1];
        // 初始化前缀和数组
        for(int i = 1; i <= n; i++) {
        	preSums[i] = preSums[i - 1] + nums[i-1];
        }
        // 返回值 ret
        long ret = 0;
        final int M = (int) (Math.pow(10, 9) + 7);
        
        for(int i = 1; i < n; i++) {
            // 特判,必须在三分之一内
            if(preSums[i] * 3 > preSums[n]) break;
            
            // 第一次二分找右边界
            int l = i + 1, r = n - 1;
            while(l <= r) {
                int mid = (l + r) / 2;
                if(preSums[n] - preSums[mid] < preSums[mid] - preSums[i]) {
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }
            
            // 第二次二分找左边界
            int ll = i + 1, rr = n - 1;
            while(ll <= rr) {
                int mid = (ll + rr) / 2;
                if(preSums[mid] - preSums[i] < preSums[i]) {
                    ll = mid + 1;
                } else {
                    rr = mid - 1;
                }
            }
            ret += l - ll;
        }
        return (int) (ret % M);
    }
	
	//1713. 得到子序列的最少操作次数
	@Test
	public void test4() {
//		Assert.assertEquals(2, minOperations(new int[] {
//				5,1,3
//		}, new int[] {
//				9,4,2,3,4
//		}));	
//		Assert.assertEquals(4, minOperations(new int[] {
//				0,6,4,8,1,3,2
//		}, new int[] {
//				4,7,6,2,3,8,6,1,0
//		}));	
		Assert.assertEquals(7, minOperations(new int[] {
				17,5,7,1,2,19,4,20,10,18
		}, new int[] {
				2,10,5,9,9,17,8,1,19,1
		}));	
	}
	//https://leetcode-cn.com/problems/minimum-operations-to-make-a-subsequence/solution/de-dao-zi-xu-lie-de-zui-shao-cao-zuo-ci-5qeep/
    public int minOperations(int[] target, int[] arr) {
    	Map<Integer,Integer> map = new HashMap<Integer,Integer>();
    	int n = target.length,m = arr.length;
    	//步骤1. 映射target的下标
    	for (int i=0;i<n;i++) {
    		map.put(target[i],i+1);
    	}
    	 //步骤2. 把arr的数字标记上在target上的下标
    	int[] arr2 = new int[m+1];
    	for (int i=0;i<m;i++) {
    		if (map.containsKey(arr[i])) {
    			arr2[i+1] = map.get(arr[i]); //这里下标从1开始
    		}
    	}
    	//步骤3. 贪心法求最长上升子序列
    	List<Integer> list =new ArrayList();
    	for (int i=1;i<=m;i++) {
    		if (arr2[i] == 0) {
    			continue;
    		}
    		if (list.isEmpty() || arr2[i] > list.get(list.size()-1)) {
    			list.add(arr2[i]);
    		}else{
                int l = 0, r = list.size() - 1;
                while(l < r){
                    int mid = l + r >> 1;
                    if(list.get(mid) >= arr2[i]) {
                    	r = mid;
                    } else {
                    	l = mid + 1;
                    }
                }
                list.set(l, arr2[i]);
            }
    		
    	}
    	return n - list.size();
    }
}
