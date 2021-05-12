package com.laz.arithmetic.competition;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

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

	// https://leetcode-cn.com/problems/maximum-element-after-decreasing-and-rearranging/solution/jian-xiao-he-zhong-xin-pai-lie-shu-zu-ho-mzee/
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

	// 1847. 最近的房间
	@Test
	public void test4() {
		Assert.assertArrayEquals(new int[] { 3, -1, 3 }, new Solution4().closestRoom(
				new int[][] { { 2, 2 }, { 1, 2 }, { 3, 2 } }, new int[][] { { 3, 1 }, { 3, 3 }, { 5, 2 } }));

		Assert.assertArrayEquals(new int[] { 2, 1, 3 },
				new Solution4().closestRoom(new int[][] { { 1, 4 }, { 2, 3 }, { 3, 5 }, { 4, 1 }, { 5, 2 } },
						new int[][] { { 2, 3 }, { 2, 4 }, { 2, 5 } }));
	}

	class Solution4_2 {
		public int[] closestRoom(int[][] rooms, int[][] queries) {
			int[][] q = new int[queries.length][3];
			for (int i = 0; i < q.length; i++) {
				q[i][0] = queries[i][0];
				q[i][1] = queries[i][1];
				q[i][2] = i;
			}
			//query根据房间大小排序,降序。 排序后的query，只需要建立一次树，不用多次建立
			Arrays.sort(q, (x, y) -> y[1] - x[1]);
			//room根据房间大小排序,降序
			Arrays.sort(rooms, (x, y) -> y[1] - x[1]);
			TreeSet<Integer> set = new TreeSet<>();
			int idx = 0;
			int[] ans = new int[q.length];
			Arrays.fill(ans, -1);
			for (int i = 0; i < q.length; i++) {
				//大于当前房间的值，加入tree中，必然大于剩下条件的房间，因为query是降序的
				while (idx < rooms.length && rooms[idx][1] >= q[i][1]) {
					set.add(rooms[idx][0]);
					idx += 1;
				}
				//比较最接近query房间id的两个值，谁最合适
				Integer a = set.floor(q[i][0]);
				Integer b = set.ceiling(q[i][0]);
				if (a == null && b == null) {
					ans[q[i][2]] = -1;
				} else if (b == null || a == null) {
					ans[q[i][2]] = (a == null) ? b : a;
				} else {
					ans[q[i][2]] = ((q[i][0] - a) <= (b - q[i][0])) ? a : b;
				}
			}
			return ans;
		}

	}

	// 超时
	class Solution4 {
		public int[] closestRoom(int[][] rooms, int[][] queries) {
			// 根据大小排序rooms
			Arrays.sort(rooms, new Comparator<int[]>() {
				@Override
				public int compare(int[] o1, int[] o2) {
					return o1[1] - o2[1];
				}
			});

			int len = queries.length;
			int[] ans = new int[len];
			for (int i = 0; i < len; i++) {
				int[] query = queries[i];
				// 二分查询不小于query[1]的房间起始编号
				int start = search(query[1], rooms);
				if (start >= rooms.length) {
					ans[i] = -1;
					continue;
				}
				TreeSet<Integer> tree = new TreeSet<Integer>();
				for (int j = start; j < rooms.length; j++) {
					tree.add(rooms[j][0]);
				}
				// 判断tree中最大值是否小于等于query[0]
				Integer r = tree.last();
				if (r <= query[0]) {
					ans[i] = r;
					continue;
				}
				// 判断tree中最小值是否大于等于query[0]
				r = tree.first();
				if (r >= query[0]) {
					ans[i] = r;
					continue;
				}
				// 判断tree中floor和ceiling最接近query[0]的值
				Integer floor = tree.floor(query[0]);
				Integer ceil = tree.ceiling(query[0]);
				ans[i] = Math.abs(floor - query[0]) - Math.abs(ceil - query[0]) <= 0 ? floor : ceil;
			}
			return ans;
		}

		private int search(int v, int[][] rooms) {
			int l = 0, r = rooms.length - 1;
			while (l <= r) {
				int mid = (l + r) >> 1;
				if (rooms[mid][1] > v) {
					r = mid - 1;
				}
				if (rooms[mid][1] < v) {
					l = mid + 1;
				}
				if (rooms[mid][1] == v) {
					// 有可能有重复的，在看看最侧是否还有值
					while (mid >= 0 && rooms[mid][1] == v) {
						mid--;
					}
					return mid + 1;
				}
			}
			return l;
		}
	}
}
