package com.laz.arithmetic.competition;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;



//https://leetcode-cn.com/contest/weekly-contest-220/
public class Competition14 {
	// 5629. 重新格式化电话号码
	@Test
	public void test1() {
		Assert.assertEquals("123-456", reformatNumber("1-23-45 6"));

		Assert.assertEquals("123-456-78", reformatNumber("123 4-5678"));
	}

	public String reformatNumber(String number) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < number.length(); i++) {
			if (number.charAt(i) != ' ' && number.charAt(i) != '-') {
				sb.append(number.charAt(i));
			}
		}
		StringBuilder ret = new StringBuilder();
		int start = 0;
		int end = sb.length();
		while ((end - start) > 4) {
			ret.append(sb.substring(start, start + 3));
			ret.append("-");
			start += 3;
		}
		if (end - start == 4) {
			ret.append(sb.substring(start, start + 2));
			ret.append("-");
			start += 2;
			ret.append(sb.substring(start, start + 2));
		} else {
			ret.append(sb.substring(start, end));
		}
		return ret.toString();
	}

	// 5630. 删除子数组的最大得分
	@Test
	public void test2() {
		Assert.assertEquals(17, maximumUniqueSubarray(new int[] { 4, 2, 4, 5, 6 }));

		Assert.assertEquals(8, maximumUniqueSubarray(new int[] { 5, 2, 1, 2, 5, 2, 1, 2, 5 }));
	}

	public int maximumUniqueSubarray(int[] nums) {

		int max = 0;
		int[] prefSum = new int[nums.length];
		prefSum[0] = nums[0];
		for (int i = 1; i < nums.length; i++) {
			prefSum[i] = prefSum[i - 1] + nums[i];
		}
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int startIndex = 0;
		for (int i = 0; i < nums.length; i++) {
			if (map.containsKey(nums[i])) {
				int sum = prefSum[i - 1] - prefSum[startIndex] + nums[startIndex];
				max = Math.max(max, sum);
				int oldIndex = map.get(nums[i]);
				if (oldIndex >= startIndex) {// 在范围内
					startIndex = map.get(nums[i]) + 1;
				}
			}
			map.put(nums[i], i);
		}
		if (startIndex < nums.length) {
			int sum = prefSum[nums.length - 1] - prefSum[startIndex] + nums[startIndex];
			max = Math.max(max, sum);
		}
		return max;
	}

	// 5631. 跳跃游戏 VI
	@Test
	public void test3() {
		Assert.assertEquals(7, maxResult(new int[] { 1, -1, -2, 4, -7, 3 }, 2));

		Assert.assertEquals(17, maxResult(new int[] { 10, -5, -2, 4, 0, 3 }, 3));

		Assert.assertEquals(0, maxResult(new int[] { 1, -5, -20, 4, -1, 3, -6, -3 }, 2));

		Assert.assertEquals(-3, maxResult(new int[] { 1, -5, -20, 4, -1, 3, -3, -6 }, 2));
	}

	public int maxResult(int[] nums, int k) {
		int ret = nums[0];
		int n = nums.length;
		int index = 0;
		while (index < n - 1) {
			int maxIndex = index + 1;
			for (int i = index + 1; i <= (index + k) && i < n; i++) {
				if (nums[i] > 0) {
					maxIndex = i;
					break;
				}
				if (nums[maxIndex] < nums[i]) {
					maxIndex = i;
				}
			}
			if (nums[maxIndex] < 0 && (index + k) >= n - 1) {
				index = n - 1;
				ret += nums[n - 1];
			} else {
				index = maxIndex;
				ret += nums[maxIndex];
			}
		}
		return ret;
	}

	// 1697. 检查边长度限制的路径是否存在
	@Test
	public void test4() {
		Assert.assertArrayEquals(new boolean[] { false, true },
				new Solution4().distanceLimitedPathsExist(3,
						new int[][] { { 0, 1, 2 }, { 1, 2, 4 }, { 2, 0, 8 }, { 1, 0, 16 } },
						new int[][] { { 0, 1, 2 }, { 0, 2, 5 } }));

		Assert.assertArrayEquals(new boolean[] { true, false },
				new Solution4().distanceLimitedPathsExist(3,
						new int[][] { { 0, 1, 10 }, { 1, 2, 5 }, { 2, 3, 9 }, { 3, 4, 13 } },
						new int[][] { { 0, 4, 14 }, { 1, 4, 13 } }));
		
		Assert.assertArrayEquals(new boolean[] { true, false },
				new Solution4().distanceLimitedPathsExist(50,
						new int[][] {{9,30,62},{30,17,77},{45,3,72},{7,20,65},{47,21,49},{49,36,20},{48,6,40},{15,37,83},{42,2,23},{10,28,17},{16,41,58},{21,12,76},{16,9,7},{19,9,63},{5,47,20},{12,3,18},{44,16,52},{6,32,60},{19,25,25},{35,10,85},{44,25,41},{47,28,91},{39,44,81},{6,28,95},{9,34,56},{47,42,40},{23,42,94},{37,14,5},{17,31,96},{3,0,76},{29,0,100},{9,35,25},{1,13,98},{24,29,39},{0,22,30},{49,37,100},{1,48,29},{5,4,80},{33,12,24},{21,27,87},{19,24,5},{29,24,86},{25,40,23},{34,13,71},{43,31,12},{20,47,68},{36,40,88},{45,2,53},{29,36,60},{39,37,96},{45,42,11},{48,47,84},{37,43,49},{20,18,76},{41,37,14},{34,17,56},{20,44,80},{24,5,53},{20,42,40},{15,48,14},{17,34,23},{7,43,56},{33,1,100},{39,21,85},{11,31,92},{29,18,35},{11,31,75},{45,33,60},{43,10,52},{33,20,85},{4,36,14},{32,42,45},{39,43,95},{45,49,16},{10,46,65},{15,21,90},{11,2,43},{35,23,85},{26,16,34},{4,1,55},{3,4,60},{26,23,54},{23,19,85},{7,8,34},{43,28,96},{7,44,45},{19,43,48},{39,16,82},{30,35,77},{23,18,57},{21,2,4},{21,25,44},{35,42,30},{24,37,87},{11,18,88},{2,16,11},{22,18,23}},
						new int[][]{{9,35,47},{46,10,49},{35,44,20},{20,21,41},{47,41,27},{32,42,27},{20,12,53},{36,37,81},{8,24,3},{13,31,7},{12,1,51},{15,6,4},{2,9,63},{17,44,70},{41,35,37},{15,0,87},{29,35,84},{28,18,70},{13,18,29},{0,42,3},{12,5,30},{16,30,73},{2,49,75},{35,42,63},{48,6,45},{4,49,81},{33,6,40},{6,42,46},{13,5,4},{26,2,35},{40,41,12},{29,48,10},{46,19,38},{7,12,31},{21,10,45},{24,0,62},{24,2,60},{31,49,11},{14,12,36},{42,1,27},{15,12,48},{36,32,72},{35,7,1},{21,39,51},{12,0,98},{20,36,15},{44,34,40},{38,9,18},{8,31,22},{25,42,5},{8,18,13},{33,48,29},{11,36,40},{20,29,60},{43,1,88},{33,49,90},{27,3,55},{6,5,36},{32,21,27},{33,25,36},{34,6,69},{24,29,59},{40,39,55},{22,1,5},{27,39,58},{48,30,31},{49,23,15},{18,36,5},{7,42,2},{3,31,61},{4,5,99},{49,21,11},{43,33,26},{16,7,85},{47,48,7},{4,16,2},{16,3,93},{41,4,64},{10,25,42},{27,8,44},{21,20,45},{32,37,2},{23,9,8},{47,14,79},{13,36,48},{35,0,19},{48,27,87},{38,14,52},{19,0,96},{24,49,75},{14,16,20},{5,31,9},{13,38,33},{46,13,100},{11,23,48},{20,39,63},{21,12,38}}));
	}
	//https://leetcode-cn.com/problems/checking-existence-of-edge-length-limited-paths/solution/java-bing-cha-ji-chi-xian-dai-zhu-shi-by-w6dv/
	//https://leetcode-cn.com/problems/checking-existence-of-edge-length-limited-paths/solution/jie-zhe-ge-wen-ti-ke-pu-yi-xia-shi-yao-j-pn1b/
	class Solution4 {
	    int N = 100005;
	    int[] p = new int[N];
	    public boolean[] distanceLimitedPathsExist(int n, int[][] edgeList, int[][] queries) {
	        boolean[] res = new boolean[queries.length];
	        Pair[] pair = new Pair[queries.length];
	        // 添加一维index
	        for(int i = 0; i < queries.length; i++) {
	            pair[i] = new Pair(queries[i][0], queries[i][1], queries[i][2], i);
	        }
	        // queries 和 edgeList 分别按照边长从小到大排序
	        Arrays.sort(pair, (o1, o2) -> (o1.l - o2.l));
	        Arrays.sort(edgeList, (o1, o2) -> (o1[2] - o2[2]));
	        // 并查集初始化
	        for(int i = 1; i < N; i++) {
	            p[i] = i;
	        }
	        int i = 0;
	        for(Pair query : pair) {
	            int a = query.a;
	            int b = query.b;
	            int l = query.l;
	            int index = query.index;
	            for(; i < edgeList.length; i++) {
	                if(edgeList[i][2] < l) {
	                    p[find(edgeList[i][0])] = find(edgeList[i][1]);//合并
	                }else {
	                    break;
	                }
	            }
	            //查a和b是否在同一个连通块中
	            res[index] = (find(a) == find(b));
	        }
	        return res;
	    }
	    public int find(int x) {
	        if(p[x] != x) {
	            p[x] = find(p[x]);
	        }
	        return p[x];
	    }
	}
	public class Pair {
	    int a, b, l, index;
	    public Pair(int a, int b, int l, int index) {
	        this.a = a;
	        this.b = b;
	        this.l = l;
	        this.index = index;
	    }
	}


}
