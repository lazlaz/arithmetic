package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;

//第 206 场周赛
public class Competition1 {
	// 二进制矩阵中的特殊位置
	@Test
	public void test1() {
		Assert.assertEquals(1, numSpecial(new int[][] { { 1, 0, 0 }, { 0, 0, 1 }, { 1, 0, 0 } }));

		Assert.assertEquals(2,
				numSpecial(new int[][] { { 0, 0, 0, 1 }, { 1, 0, 0, 0 }, { 0, 1, 1, 0 }, { 0, 0, 0, 0 } }));
	}

	public int numSpecial(int[][] mat) {
		int count = 0;
		int row = mat.length;
		int col = mat[0].length;
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (mat[i][j] == 1) {
					boolean flag = true;
					// 第i行是否为0
					for (int z = 0; z < col; z++) {
						if (mat[i][z] == 1 && z != j) {
							flag = false;
						}
					}
					if (flag) {
						// 第j列是否为0
						for (int z = 0; z < row; z++) {
							if (mat[z][j] == 1 && z != i) {
								flag = false;
							}
						}
						if (flag) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}

	// 连接所有点的最小费用
	@Test
	public void test2() {
		Assert.assertEquals(20,
				minCostConnectPoints(new int[][] { { 0, 0 }, { 2, 2 }, { 3, 10 }, { 5, 2 }, { 7, 0 } }));

//		Assert.assertEquals(18, minCostConnectPoints(new int[][] { { 3, 12 }, { -2, 5 }, { -4, 1 } }));
//
//		Assert.assertEquals(0, minCostConnectPoints(new int[][] { { 0, 0 } }));
//
//		Assert.assertEquals(53, minCostConnectPoints(new int[][] { { 2, -3 }, { -17, -8 }, { 13, 8 }, { -17, -15 } }));
	}

	public int minCostConnectPoints(int[][] points) {
		int n = points.length;
		int ans = 0; // 总共的最短距离
		boolean[] vis = new boolean[n]; // 记录当前已选择的点(true),未选点(false)
		int[] min_dist = new int[n];
		Arrays.fill(min_dist, Integer.MAX_VALUE); // 当前未选点集B离已选点集A的最短距离
		min_dist[0] = 0; // 从第0个点开始加入已选点集A
		for (int i = 0; i < n; ++i) {
			int u = -1; // 记录离当前已选点集A距离最短的点
			int gmin = Integer.MAX_VALUE; // u离当前已选点集A的最短距离
			for (int j = 0; j < n; ++j) {
				if (!vis[j] && min_dist[j] <= gmin) { // 距离已选点集A距离最短
					gmin = min_dist[j];
					u = j;
				}
			}
			ans += gmin;
			vis[u] = true;
			// 更新当前未选点集里离已选点集A的最短距离,每选出一个点u加入到已选点集A中,就更新一次
			for (int j = 0; j < n; ++j) {
				if (!vis[j]) { // 已经选择的点不需要更新
					int new_dist = Math.abs(points[j][0] - points[u][0]) + Math.abs(points[j][1] - points[u][1]);
					// 点j与点u的距离是否小于点j与当前已选点集A的最小距离
					min_dist[j] = Math.min(min_dist[j], new_dist);
				}
			}
		}
		return ans;

	}

	// 利用Prim算法生成最小树 超时
	public int minCostConnectPoints2(int[][] points) {
		int sum = 0;
		int len = points.length;
		if (len == 1) {
			return 0;
		}
		List<Integer> aClass = new ArrayList<Integer>();
		aClass.add(0);
		while (aClass.size() < len) {
			int min = Integer.MAX_VALUE;
			int index = -1;
			for (int i = 0; i < len; i++) {
				if (!aClass.contains(i)) {
					int[] point = points[i];
					for (int j = 0; j < aClass.size(); j++) {
						int[] point2 = points[aClass.get(j)];
						int distance = Math.abs(point[0] - point2[0]) + Math.abs(point[1] - point2[1]);
						if (min > distance) {
							min = distance;
							index = i;
						}
					}
				}
			}
			sum += min;
			aClass.add(index);
		}
		return sum;
	}

	// 检查字符串是否可以通过排序子字符串得到另一个字符串
	@Test
	public void test3() {
		Assert.assertEquals(true, isTransformable("84532", "34852"));
		Assert.assertEquals(false, isTransformable("12345", "12435"));
	}

	public boolean isTransformable(String s, String t) {
		Queue<Integer>[] queue = new Queue[10];
		for (int i = 0; i < 10; i++)
			queue[i] = new LinkedList<>();
		for (int i = 0; i < s.length(); i++)
			queue[s.charAt(i) - '0'].offer(i);
		for (int i = 0; i < t.length(); i++) {
			int digit = t.charAt(i) - '0';
			if (queue[digit].isEmpty())
				return false;
			for (int j = 0; j < digit; j++) {
				if (!queue[j].isEmpty() && queue[j].peek() < queue[digit].peek())
					return false;
			}
			queue[digit].poll();
		}
		return true;
	}

	// 统计不开心的朋友
	@Test
	public void test4() {
		Assert.assertEquals(2, unhappyFriends(4, new int[][] { { 1, 2, 3 }, { 3, 2, 0 }, { 3, 1, 0 }, { 1, 2, 0 } },
				new int[][] { { 0, 1 }, { 2, 3 } }));
	}

	public int unhappyFriends(int n, int[][] preferences, int[][] pairs) {
		Map<Integer, Integer> map = new HashMap<>();
		int ans = 0;
		for (int[] pair : pairs) {
			map.put(pair[0], pair[1]);
		}

		for (int i = 0; i < n; i++) {
			// i配对的人
			int friend = getFriend(i, map);
			// i配对到的是最好的人 直接快乐
			if (friend == preferences[i][0]) {
				System.out.println(i + "是快乐的");
				continue;
			}

			// 查找friend在i这里的亲密度排行
			int x = -1;
			for (int j = 1; j < preferences[i].length; j++) {
				if (preferences[i][j] == friend) {
					x = j;
					break;
				}
			}
			// 再查找比friend亲密度高
			for (int z = 0; z < x; z++) {
				// i的第一位朋友
				int friend1 = preferences[i][z];
				// 与i的第一位朋友配对的人
				int friend2 = getFriend(friend1, map);

				// 判断 这位朋友与i的亲密度是否 比 这位朋友配对到的人亲密度高
				int a = -1, b = -1;
				for (int i1 = 0; i1 < preferences[friend1].length; i1++) {
					if (a != -1 && b != -1) {
						break;
					}
					if (preferences[friend1][i1] == i) {
						a = i1;
					}
					if (preferences[friend1][i1] == friend2) {
						b = i1;
					}
				}

				// 是的话就不开心，并跳出循环，否则开心
				if (a < b) {
					System.out.println(i + "是不开心的");
					ans++;
					break;
				}
			}
		}
		return ans;
	}

	public int getFriend(int i, Map<Integer, Integer> map) {
		int friend = -1;
		if (map.containsKey(i)) {
			friend = map.get(i);
		} else {
			for (Integer key : map.keySet()) {
				if (map.get(key) == i) {
					friend = key;
				}
			}
		}
		return friend;
	}

}
