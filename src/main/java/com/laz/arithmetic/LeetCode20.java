package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Comparator;

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
				//保持平衡 small里面的数比large里面的数>=1
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
}
