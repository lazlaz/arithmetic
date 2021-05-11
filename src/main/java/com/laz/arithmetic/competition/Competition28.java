package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/biweekly-contest-51/
public class Competition28 {
	// 1845. 座位预约管理系统
	@Test
	public void test2() {
		SeatManager seatManager = new SeatManager(5); // 初始化 SeatManager ，有 5 个座位。
		seatManager.reserve(); // 所有座位都可以预约，所以返回最小编号的座位，也就是 1 。
		seatManager.reserve(); // 可以预约的座位为 [2,3,4,5] ，返回最小编号的座位，也就是 2 。
		seatManager.unreserve(2); // 将座位 2 变为可以预约，现在可预约的座位为 [2,3,4,5] 。
		seatManager.reserve(); // 可以预约的座位为 [2,3,4,5] ，返回最小编号的座位，也就是 2 。
		seatManager.reserve(); // 可以预约的座位为 [3,4,5] ，返回最小编号的座位，也就是 3 。
		seatManager.reserve(); // 可以预约的座位为 [4,5] ，返回最小编号的座位，也就是 4 。
		seatManager.reserve(); // 唯一可以预约的是座位 5 ，所以返回 5 。
		seatManager.unreserve(5); // 将座位 5 变为可以预约，现在可预约的座位为 [5] 。
	}

	class SeatManager {
		PriorityQueue<Integer> order;
		Set<Integer> inOrder;

		public SeatManager(int n) {
			order = new PriorityQueue<>();
			inOrder = new HashSet<>();
			// 初始都可以预约
			for (int i = 1; i <= n; i++) {
				order.add(i);
			}
		}

		public int reserve() {
			int k = order.poll();
			inOrder.add(k);
			return k;
		}

		public void unreserve(int seatNumber) {
			order.add(seatNumber); // 有自动去重功能，有比较
		}
	}

	// 1846. 减小和重新排列数组后的最大元素
	@Test
	public void test3() {
		Assert.assertEquals(2,
				new Solution3().maximumElementAfterDecrementingAndRearranging(new int[] { 2, 2, 1, 2, 1 }));
		Assert.assertEquals(3,
				new Solution3().maximumElementAfterDecrementingAndRearranging(new int[] { 100, 1, 1000 }));
		Assert.assertEquals(5,
				new Solution3().maximumElementAfterDecrementingAndRearranging(new int[] { 1, 2, 3, 4, 5 }));
	}
	//https://leetcode-cn.com/problems/maximum-element-after-decreasing-and-rearranging/solution/jian-xiao-he-zhong-xin-pai-lie-shu-zu-ho-mzee/
	class Solution3 {
		public int maximumElementAfterDecrementingAndRearranging(int[] arr) {
			int n = arr.length;
			if (n == 1) {
				return 1;
			}
			Arrays.sort(arr);
			// 第一个数为1
			arr[0] = 1;
			for (int i = 1; i < n; i++) {
				if (Math.abs(arr[i] - arr[i - 1]) > 1) {
					// 为了值尽可能大，减小arr[i]为arr[i-1]+1
					arr[i] = arr[i - 1] + 1;
				}
			}
			return arr[arr.length - 1];
		}
	}
}
