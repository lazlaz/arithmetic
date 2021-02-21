package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

public class LeetCode20 {
	// 480. 滑动窗口中位数
	@Test
	public void test1() {
//		Assert.assertArrayEquals(new double[] {
//				1,-1,-1,3,5,6
//		}, medianSlidingWindow(new int[] {1,3,-1,-3,5,3,6,7},3),0.00001);

		Assert.assertArrayEquals(new double[] {},
				medianSlidingWindow(new int[] { 2147483647, 1, 2, 3, 4, 5, 6, 7, 2147483647 }, 2), 0.00001);
	}

	// https://leetcode-cn.com/problems/sliding-window-median/solution/hua-dong-chuang-kou-zhong-wei-shu-by-lee-7ai6/
	class Solution1 {
		public double[] medianSlidingWindow(int[] nums, int k) {
			DualHeap dh = new DualHeap(k);
			for (int i = 0; i < k; ++i) {
				dh.insert(nums[i]);
			}
			double[] ans = new double[nums.length - k + 1];
			ans[0] = dh.getMedian();
			for (int i = k; i < nums.length; ++i) {
				dh.insert(nums[i]);
				dh.erase(nums[i - k]);
				ans[i - k + 1] = dh.getMedian();
			}
			return ans;
		}

		class DualHeap {
			// 大根堆，维护较小的一半元素
			private PriorityQueue<Integer> small;
			// 小根堆，维护较大的一半元素
			private PriorityQueue<Integer> large;
			// 哈希表，记录「延迟删除」的元素，key 为元素，value 为需要删除的次数
			private Map<Integer, Integer> delayed;

			private int k;
			// small 和 large 当前包含的元素个数，需要扣除被「延迟删除」的元素
			private int smallSize, largeSize;

			public DualHeap(int k) {
				this.small = new PriorityQueue<Integer>(new Comparator<Integer>() {
					public int compare(Integer num1, Integer num2) {
						return num2.compareTo(num1);
					}
				});
				this.large = new PriorityQueue<Integer>(new Comparator<Integer>() {
					public int compare(Integer num1, Integer num2) {
						return num1.compareTo(num2);
					}
				});
				this.delayed = new HashMap<Integer, Integer>();
				this.k = k;
				this.smallSize = 0;
				this.largeSize = 0;
			}

			public double getMedian() {
				return (k & 1) == 1 ? small.peek() : ((double) small.peek() + large.peek()) / 2;
			}

			public void insert(int num) {
				if (small.isEmpty() || num <= small.peek()) {
					small.offer(num);
					++smallSize;
				} else {
					large.offer(num);
					++largeSize;
				}
				// 保持平衡 small里面的数比large里面的数>=1
				makeBalance();
			}

			public void erase(int num) {
				delayed.put(num, delayed.getOrDefault(num, 0) + 1);
				if (num <= small.peek()) {
					--smallSize;
					if (num == small.peek()) {
						prune(small);
					}
				} else {
					--largeSize;
					if (num == large.peek()) {
						prune(large);
					}
				}
				makeBalance();
			}

			// 不断地弹出 heap 的堆顶元素，并且更新哈希表
			private void prune(PriorityQueue<Integer> heap) {
				while (!heap.isEmpty()) {
					int num = heap.peek();
					if (delayed.containsKey(num)) {
						delayed.put(num, delayed.get(num) - 1);
						if (delayed.get(num) == 0) {
							delayed.remove(num);
						}
						heap.poll();
					} else {
						break;
					}
				}
			}

			// 调整 small 和 large 中的元素个数，使得二者的元素个数满足要求
			private void makeBalance() {
				if (smallSize > largeSize + 1) {
					// small 比 large 元素多 2 个
					large.offer(small.poll());
					--smallSize;
					++largeSize;
					// small 堆顶元素被移除，需要进行 prune
					prune(small);
				} else if (smallSize < largeSize) {
					// large 比 small 元素多 1 个
					small.offer(large.poll());
					++smallSize;
					--largeSize;
					// large 堆顶元素被移除，需要进行 prune
					prune(large);
				}
			}
		}

	}

	public double[] medianSlidingWindow(int[] nums, int k) {
		List<Integer> list = new ArrayList<Integer>();
		int n = nums.length;
		if (n == 0 || n < k) {
			return null;
		}
		double[] res = new double[n - k + 1];
		int index = 0;
		for (int i = 0; i < nums.length; i++) {
			if (list.size() == k) {
				// 移除数字
				list.remove((Integer) (i - k));
			}
			if (!list.isEmpty()) {
				// 插入排序
				int arrIndex = -1;
				for (int j = 0; j < list.size(); j++) {
					if (nums[i] < nums[list.get(j)]) {
						arrIndex = j;
						break;
					}
				}
				if (arrIndex == -1) {
					list.add(i);
				} else {
					list.add(arrIndex, i);
				}
			} else {
				list.add(i);
			}
			if (list.size() == k) {
				// 计算中位数
				if (k % 2 == 0) {
					res[index++] = (nums[list.get(k / 2)] / 2.0 + nums[list.get(k / 2 - 1)] / 2.0);
				} else {
					res[index++] = nums[list.get(k / 2)];
				}
			}
		}

		return res;
	}

	// 643. 子数组最大平均数 I
	@Test
	public void test2() {
		Assert.assertEquals(12.75, findMaxAverage(new int[] { 1, 12, -5, -6, 50, 3 }, 4), 0.001);
		Assert.assertEquals(5, findMaxAverage(new int[] { 5 }, 1), 0.001);
	}

	public double findMaxAverage(int[] nums, int k) {
		double maxSum = 0;
		double sum = 0;
		for (int i = 0; i < k; i++) {
			sum += nums[i];
		}
		maxSum = sum;
		// 少做除法，用int代替double，后面使用dubbo 效率高些
		for (int i = k; i < nums.length; i++) {
			sum = sum + nums[i] - nums[i - k];
			maxSum = Math.max(maxSum, sum);
		}
		return maxSum / k;
	}

	// 1208. 尽可能使字符串相等
	@Test
	public void test3() {
		Assert.assertEquals(3, equalSubstring("abcd", "bcdf", 3));
		Assert.assertEquals(1, equalSubstring("abcd", "cdef", 3));
		Assert.assertEquals(2, equalSubstring("krrgw", "zjxss", 19));
		Assert.assertEquals(4, equalSubstring("krpgjbjjznpzdfy", "nxargkbydxmsgby", 14));
	}

	public int equalSubstring(String s, String t, int maxCost) {
		// 审题，要连续的子串
		int[] diff = new int[s.length()];
		for (int i = 0; i < s.length(); i++) {
			diff[i] = Math.abs(s.charAt(i) - t.charAt(i));
		}
		int l = 0, r = 0;
		int maxCount = 0;
		int sum = 0;
		while (r < diff.length) {
			while (r < diff.length && sum <= maxCost) {
				sum += diff[r];
				r++;
			}
			maxCount = Math.max(maxCount, r - l - 1);
			if (sum > maxCost) {
				sum -= diff[l];
				l++;
			}
		}
		maxCount = Math.max(maxCount, r - l);
		return maxCount;
	}

	// 1423. 可获得的最大点数
	@Test
	public void test4() {
		Assert.assertEquals(12, maxScore(new int[] { 1, 2, 3, 4, 5, 6, 1 }, 3));
	}

	// https://leetcode-cn.com/problems/maximum-points-you-can-obtain-from-cards/solution/ke-huo-de-de-zui-da-dian-shu-by-leetcode-7je9/
	public int maxScore(int[] cardPoints, int k) {
		int n = cardPoints.length;
		// 找出剩余n-k中最小的
		int windowSize = n - k;
		int sum = 0;
		for (int i = 0; i < windowSize; i++) {
			sum += cardPoints[i];
		}
		int minSum = sum;
		for (int i = windowSize; i < n; i++) {
			// 滑动窗口每向右移动一格，增加从右侧进入窗口的元素值，并减少从左侧离开窗口的元素值
			sum += cardPoints[i] - cardPoints[i - windowSize];
			minSum = Math.min(minSum, sum);
		}
		int tolsum = 0;
		for (int h : cardPoints) {
			tolsum += h;
		}
		return tolsum - minSum;
	}

	// 665. 非递减数列
	@Test
	public void test5() {
//		Assert.assertEquals(true, checkPossibility(new int[] {
//				4,2,3
//		}));

		Assert.assertEquals(false, checkPossibility(new int[] { 2, 3, 3, 2, 2 }));
		Assert.assertEquals(true, checkPossibility(new int[] { 5, 7, 1, 8 }));
	}

//https://leetcode-cn.com/problems/non-decreasing-array/solution/fei-di-jian-shu-lie-by-leetcode-solution-zdsm/
	public boolean checkPossibility(int[] nums) {
		int n = nums.length, cnt = 0;
		for (int i = 0; i < n - 1; ++i) {
			int x = nums[i], y = nums[i + 1];
			if (x > y) {
				cnt++;
				if (cnt > 1) {
					return false;
				}
				if (i > 0 && y < nums[i - 1]) {
					nums[i + 1] = x;
				}
			}
		}
		return true;

	}

	// 561. 数组拆分 I
	@Test
	public void test6() {
		Assert.assertEquals(4, arrayPairSum(new int[] { 1, 4, 3, 2 }));
	}

	// https://leetcode-cn.com/problems/array-partition-i/solution/jian-dan-ti-jiu-yong-jian-dan-jie-fa-xia-ew5r/
	public int arrayPairSum(int[] nums) {
		Arrays.sort(nums);
		int sum = 0;
		for (int i = 0; i < nums.length; i = i + 2) {
			sum += nums[i];
		}
		return sum;
	}

	// 566. 重塑矩阵
	@Test
	public void test7() {
		Assert.assertArrayEquals(new int[][] { { 1, 2, 3, 4 } },
				matrixReshape(new int[][] { { 1, 2 }, { 3, 4 } }, 1, 4));
	}

	public int[][] matrixReshape(int[][] nums, int r, int c) {
		int numR = nums.length;
		int numC = nums[0].length;
		int newC = (numR * numC) / r;
		int[][] newNums = new int[r][newC];
		int indexR = 0;
		int indexC = 0;
		if (r * c > numR * numC || r * c < numR * numC) {
			return nums;
		}
		for (int i = 0; i < r; i++) {
			for (int j = 0; j < newC; j++) {
				newNums[i][j] = nums[indexR][indexC];
				indexC++;
				if (indexC >= numC) {
					indexR++;
					indexC = 0;
				}
			}
		}
		return newNums;
	}

	// 995. K 连续位的最小翻转次数
	@Test
	public void test8() {
		// Assert.assertEquals(2, minKBitFlips(new int[] { 0, 1, 0 }, 1));

		Assert.assertEquals(3, minKBitFlips(new int[] { 0, 0, 0, 1, 0, 1, 1, 0 }, 3));
	}

	// https://leetcode-cn.com/problems/minimum-number-of-k-consecutive-bit-flips/solution/hua-dong-chuang-kou-shi-ben-ti-zui-rong-z403l/
	public int minKBitFlips(int[] A, int K) {
		int n = A.length;
		Deque<Integer> queue = new LinkedList<Integer>();
		int res = 0;
		for (int i = 0; i < n; i++) {
			if (!queue.isEmpty() && i >= queue.peek() + K) {
				queue.poll();
			}
			if (queue.size() % 2 == A[i]) {
				if (i + K > n) {
					return -1;
				}
				queue.offer(i);
				res++;
			}
		}
		return res;
	}

	// 1004. 最大连续1的个数 III
	@Test
	public void test9() {
		Assert.assertEquals(6, longestOnes(new int[] { 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0 }, 2));
		Assert.assertEquals(10, longestOnes(new int[] { 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 1, 1, 1, 1 }, 3));
	}

	// https://leetcode-cn.com/problems/max-consecutive-ones-iii/solution/fen-xiang-hua-dong-chuang-kou-mo-ban-mia-f76z/
	public int longestOnes(int[] A, int K) {
		int n = A.length;
		int count = 0;
		int max = 0;
		int l = 0, r = 0;
		while (r < n) {
			if (A[r] == 0) {
				count++;
			}
			while (count > K) {
				if (A[l] == 0) {
					count--;
				}
				l++;
			}
			max = Math.max(max, r - l + 1);
			r++;
		}
		return max;
	}

	// 697. 数组的度
	@Test
	public void test10() {
		// Assert.assertEquals(2, new Solution10_2().findShortestSubArray(new int[] { 1,
		// 2, 2, 3, 1 }));
		Assert.assertEquals(6, new Solution10_2().findShortestSubArray(new int[] { 1, 2, 2, 3, 1, 4, 2, 1 }));
	}

	// https://leetcode-cn.com/problems/degree-of-an-array/solution/xiang-xi-fen-xi-ti-yi-yu-si-lu-jian-ji-d-nvdy/
	class Solution10_2 {
		public int findShortestSubArray(int[] nums) {
			Map<Integer, int[]> map = new HashMap<Integer, int[]>();
			int n = nums.length;
			for (int i = 0; i < n; i++) {
				if (map.containsKey(nums[i])) {
					map.get(nums[i])[0]++;
					map.get(nums[i])[2] = i;
				} else {
					// 存储三个值，分别是数量，起始位置，结束位置
					map.put(nums[i], new int[] { 1, i, i });
				}
			}
			int maxNum = 0, minLen = 0;
			for (Map.Entry<Integer, int[]> entry : map.entrySet()) {
				int[] arr = entry.getValue();
				if (maxNum < arr[0]) {
					maxNum = arr[0];
					minLen = arr[2] - arr[1] + 1;
				} else if (maxNum == arr[0]) {
					if (minLen > arr[2] - arr[1] + 1) {
						minLen = arr[2] - arr[1] + 1;
					}
				}
			}
			return minLen;

		}
	}

	class Solution10 {
		public int findShortestSubArray(int[] nums) {
			// 原数组的度
			int max = 0;
			Map<Integer, Integer> map = new HashMap<>();
			for (int i = 0; i < nums.length; i++) {
				int value = map.compute(nums[i], (k, v) -> {
					if (v == null)
						return 1;
					return v + 1;
				});
				max = Math.max(max, value);
			}

			// 找到最小的长度
			int l = 0, r = 0;
			int n = nums.length;
			int minLen = Integer.MAX_VALUE;
			Map<Integer, Integer> values = new HashMap<>();
			while (r < n) {
				while (r < n && maxLen(values) < max) {
					values.compute(nums[r], (k, v) -> {
						if (v == null)
							return 1;
						return v + 1;
					});
					r++;
				}
				while (l < r && maxLen(values) >= max) {
					values.compute(nums[l], (k, v) -> {
						if (v == null)
							return 0;
						return v - 1;
					});
					l++;
				}
				minLen = Math.min(minLen, r - l + 1);
			}
			return minLen;
		}

		private int maxLen(Map<Integer, Integer> values) {
			int maxLen = 0;
			for (Integer v : values.values()) {
				maxLen = Math.max(v, maxLen);
			}
			return maxLen;
		}
	}

	// 1438. 绝对差不超过限制的最长连续子数组
	@Test
	public void test11() {
//		Assert.assertEquals(2, new Solution11().longestSubarray(new int[] { 8, 2, 4, 7 }, 4));
//		Assert.assertEquals(4, new Solution11().longestSubarray(new int[] { 10,1,2,4,7,2 }, 5));
//		Assert.assertEquals(3, new Solution11().longestSubarray(new int[] { 4,2,2,2,4,4,2,2 }, 0));
//		Assert.assertEquals(6, new Solution11().longestSubarray(new int[] { 2,2,2,4,4,2,5,5,5,5,5,2
//				 }, 2));
		int[] nums = new int[100000];
		for (int i = 0; i < 100000; i++) {
			nums[i] = 1;
		}
		Assert.assertEquals(100000, new Solution11().longestSubarray(nums, 10));
	}

	// https://leetcode-cn.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/solution/jue-dui-chai-bu-chao-guo-xian-zhi-de-zui-5bki/
	class Solution12 {
		public int longestSubarray(int[] nums, int limit) {
			TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
			int n = nums.length;
			int left = 0, right = 0;
			int ret = 0;
			while (right < n) {
				map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);
				while (map.lastKey() - map.firstKey() > limit) {
					map.put(nums[left], map.get(nums[left]) - 1);
					if (map.get(nums[left]) == 0) {
						map.remove(nums[left]);
					}
					left++;
				}
				ret = Math.max(ret, right - left + 1);
				right++;
			}
			return ret;
		}
	}

	class Solution11 {
		public int longestSubarray(int[] nums, int limit) {
			int l = 0, r = 0;
			int n = nums.length;
			List<Integer> list = new ArrayList<>();
			int len = 0;
			while (r < n) {
				int min = nums[r];
				int max = nums[r];
				if (list.size() == 0) {
					list.add(nums[r]);
				} else {
					insertValue(list, nums[r]);
				}
				min = list.get(0);
				max = list.get(list.size() - 1);
				while (r < n - 1 && max - min <= limit) {
					r++;
					insertValue(list, nums[r]);
					min = list.get(0);
					max = list.get(list.size() - 1);
				}
				if (max - min <= limit) {
					len = Math.max(len, r - l + 1);
				} else {
					len = Math.max(len, r - l);

				}
				while (l < n && max - min > limit) {
					removeValue(list, nums[l]);
					l++;
					min = list.get(0);
					max = list.get(list.size() - 1);
				}
				r++;
			}
			return len;
		}

		private void removeValue(List<Integer> list, int v) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i) == v) {
					list.remove(i);
					return;
				}
			}
		}

		private void insertValue(List<Integer> list, int v) {
			int index = list.size();
			for (int i = 0; i < list.size(); i++) {
				if (v <= list.get(i)) {
					index = i;
					break;
				}
			}
			list.add(index, v);
		}
	}
}
