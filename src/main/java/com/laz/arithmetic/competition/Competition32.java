package com.laz.arithmetic.competition;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-244
public class Competition32 {
	// 5776. 判断矩阵经轮转后是否一致
	@Test
	public void test1() {
		Assert.assertEquals(true,
				new Solution1().findRotation(new int[][] { { 0, 1 }, { 1, 0 } }, new int[][] { { 1, 0 }, { 0, 1 } }));

		Assert.assertEquals(true, new Solution1().findRotation(new int[][] { { 0, 0, 0 }, { 0, 1, 0 }, { 1, 1, 1 } },
				new int[][] { { 1, 1, 1 }, { 0, 1, 0 }, { 0, 0, 0 } }));
	}

	class Solution1 {
		public boolean findRotation(int[][] mat, int[][] target) {
			// 不旋转
			if (match(mat, target)) {
				return true;
			}
			// 旋转90
			int[][] newMat90 = getNewMat(mat);
			if (match(newMat90, target)) {
				return true;
			}

			// 旋转180
			int[][] newMat180 = getNewMat(newMat90);
			if (match(newMat180, target)) {
				return true;
			}
			// 旋转270
			int[][] newMat270 = getNewMat(newMat180);
			if (match(newMat270, target)) {
				return true;
			}
			return false;
		}

		private int[][] getNewMat(int[][] mat) {
			int n = mat.length;
			int[][] newMat = new int[n][n];
			int row = 0;
			int col = 0;
			for (int j = 0; j < n; j++) {
				for (int i = n - 1; i >= 0; i--) {
					newMat[row][col++] = mat[i][j];
				}
				col = 0;
				row++;
			}
			return newMat;
		}

		private boolean match(int[][] mat, int[][] target) {
			int n = mat.length;
			boolean can = true;
			for (int i = 0; i < n; i++) {
				if (!can) {
					break;
				}
				for (int j = 0; j < n; j++) {
					if (mat[i][j] != target[i][j]) {
						can = false;
						break;
					}
				}
			}
			if (can) {
				return can;
			}
			return false;

		}
	}

	// 5777. 使数组元素相等的减少操作次数
	@Test
	public void test2() {
		Assert.assertEquals(3, new Solution2().reductionOperations(new int[] { 5, 1, 3 }));

		Assert.assertEquals(4, new Solution2().reductionOperations(new int[] { 1, 1, 2, 2, 3 }));

		Assert.assertEquals(45, new Solution2().reductionOperations(new int[] { 7, 9, 10, 8, 6, 4, 1, 5, 2, 3 }));

	}

	class Solution2 {
		public int reductionOperations(int[] nums) {
			// 根据key降序
			TreeMap<Integer, Integer> map = new TreeMap<>((o1, o2) -> {
				return o2 - o1;
			});
			for (int i = 0; i < nums.length; i++) {
				int v = map.getOrDefault(nums[i], 0);
				map.put(nums[i], ++v);
			}
			int sum = 0;
			int lastV = 0;

			for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
				// 最后一个数不用管
				if (entry.getKey() == map.lastKey()) {
					break;
				}
				int total = lastV + entry.getValue();
				// 该值变为下一个值的次数
				lastV = total;
				sum += total;
			}
			return sum;
		}
	}

	// 1888. 使二进制字符串字符交替的最少反转次数
	@Test
	public void test3() {
		Assert.assertEquals(2, new Solution3().minFlips("111000"));
	}

	// https://leetcode-cn.com/problems/minimum-number-of-flips-to-make-the-binary-string-alternating/solution/minimum-number-of-flips-by-ikaruga-lu32/
	class Solution3 {
		public int minFlips(String s) {

			String target = "01";
			// 达到01的结果，达到10的结果就是len-cnt
			int cnt = 0;
			// 初始情况下到达01标准的次数
			for (int i = 0; i < s.length(); i++) {
				if ((s.charAt(i) != target.charAt(i % 2))) {
					cnt += 1;
				}
			}

			int ans = Math.min(cnt, s.length() - cnt);
			// 扩充两倍
			StringBuffer sb = new StringBuffer();
			sb.append(s).append(s);
			// 滑动逐步右移动一位
			for (int i = 0; i < s.length(); i++) {
				if (sb.charAt(i) != target.charAt(i % 2)) {
					// 这一位是不符合的，就可以少算一位
					cnt = cnt - 1;
				}
				if (sb.charAt(i + s.length()) != target.charAt((i + s.length()) % 2)) {
					// 这一位是不符合，需要加算一位
					cnt = cnt + 1;
				}
				ans = Math.min(ans, Math.min(cnt, s.length() - cnt));
			}
			return ans;
		}
	}

	// 1889. 装包裹的最小浪费空间
	@Test
	public void test4() {
		Assert.assertEquals(6,
				new Solution4().minWastedSpace(new int[] { 2, 3, 5 }, new int[][] { { 4, 8 }, { 2, 8 } }));
		
		Assert.assertEquals(9,
				new Solution4().minWastedSpace(new int[] { 3,5,8,10,11,12 }, new int[][] { { 12 }, { 11,9 },{10,5,14} }));
		
		
		
	}

	class Solution4 {
		final int MOD = 1000000007;

	    // 查找数组中最后一个小于等于value的元素 并返回其下标
	    public int bsearch(int[] a, int value) {
	        int low = 0;
	        int high = a.length-1;
	        while (low <= high) {
	            int mid = low + ((high - low) >> 1);
	            if (a[mid] > value) {
	                high = mid - 1;
	            } else {
	                if (mid == high || (a[mid + 1] > value)) return mid;
	                else low = mid + 1;
	            }
	        }
	        return -1;
	    }
		public int minWastedSpace(int[] packages, int[][] boxes) {
	    	int n = packages.length;
	    	Arrays.sort(packages);
	    	//前缀和
	    	long[] pre = new long[n+1];
	    	for (int i=1;i<=n;i++) {
	    		pre[i] = pre[i-1]+packages[i-1];
	    	}
	    	long ans = Long.MAX_VALUE;
	    	for (int[] box: boxes) {
	    		Arrays.sort(box);
	    		int boxLen = box.length;
	    		 // 小优化，如果最大包裹的尺寸大于最大箱子的尺寸，那么一定不满足，直接跳过
	            if (packages[n-1]>box[boxLen-1]) {
	                continue;
	            }
	            int start = 0;
	            long total = 0;//浪费空间
	            for (int y:box) {
	            	 // 小优化，如果当前箱子 y 的尺寸小于 pt 指向的包裹，那么无需进行二分查找
	                if (y < packages[start]) {
	                    continue;
	                }
	                int end = bsearch(packages, y);
	                total += (end-start+1)*(long)y - (pre[end+1]-pre[start]);
	                start = end+1;
	                // 小优化，如果所有包裹都已经被放入箱子，可以提前退出
	                if (start == packages.length) {
	                    break;
	                }
	            }
	            ans = Math.min(ans, total);
	    	}
	    	return ans==Long.MAX_VALUE?-1:(int)(ans%MOD);
		}
	}
}
