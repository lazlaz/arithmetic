package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
}
