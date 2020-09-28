package com.laz.arithmetic.datastructure.graph;

import java.util.ArrayList;

/**
 * Spfa算法 https://www.cnblogs.com/liuzhen1995/p/6535025.html
 *
 */
public class Spfa {
	public long[] result; // 用于得到第s个顶点到其它顶点之间的最短距离

	// 内部类，用于存放图的具体边数据
	class Edge {
		public int a; // 边的起点
		public int b; // 边的终点
		public int value; // 边的权值

		Edge(int a, int b, int value) {
			this.a = a;
			this.b = b;
			this.value = value;
		}
	}

	/*
	 * 参数n:给定图的顶点个数 参数s:求取第s个顶点到其它所有顶点之间的最短距离 参数edge:给定图的具体边
	 * 函数功能：如果给定图不含负权回路，则可以得到最终结果，如果含有负权回路，则不能得到最终结果
	 */
	public boolean getShortestPaths(int n, int s, Edge[] A) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		result = new long[n];
		boolean[] used = new boolean[n];
		int[] num = new int[n];
		for (int i = 0; i < n; i++) {
			result[i] = Integer.MAX_VALUE;
			used[i] = false;
		}
		result[s] = 0; // 第s个顶点到自身距离为0
		used[s] = true; // 表示第s个顶点进入数组队
		num[s] = 1; // 表示第s个顶点已被遍历一次
		list.add(s); // 第s个顶点入队
		while (list.size() != 0) {
			int a = list.get(0); // 获取数组队中第一个元素
			list.remove(0); // 删除数组队中第一个元素
			for (int i = 0; i < A.length; i++) {
				// 当list数组队的第一个元素等于边A[i]的起点时
				if (a == A[i].a && result[A[i].b] > result[A[i].a] + A[i].value) {
					result[A[i].b] = result[A[i].a] + A[i].value;
					if (!used[A[i].b]) {
						list.add(A[i].b);
						num[A[i].b]++;
						if (num[A[i].b] > n)
							return false;
						used[A[i].b] = true; // 表示边A[i]的终点b已进入数组队
					}
				}
			}
			used[a] = false; // 顶点a出数组对
		}
		return true;
	}

	public static void main(String[] args) {
		Spfa test = new Spfa();
		int n = 6;
		int s = 1;
		int p = 18;
		Edge[] A = new Edge[p];
		int[][] edge = new int[][] { { 0, 1, 6 }, { 0, 2, 3 }, { 1, 2, 2 }, { 1, 3, 5 }, { 2, 3, 3 }, { 2, 4, 4 },
				{ 3, 4, 2 }, { 3, 5, 3 }, { 4, 5, 5 }, { 1, 0, 6 }, { 2, 0, 3 }, { 2, 1, 2 }, { 3, 1, 5 }, { 3, 2, 3 },
				{ 4, 2, 4 }, { 4, 3, 2 }, { 5, 3, 3 }, { 5, 4, 5 } };
		for (int i = 0; i < p; i++) {
			int a = edge[i][0];
			int b = edge[i][1];
			int value = edge[i][2];
			A[i] = test.new Edge(a, b, value);
		}
		if (test.getShortestPaths(n, s, A)) {
			for (int i = 0; i < test.result.length; i++)
				System.out.print(test.result[i] + " ");
		} else
			System.out.println("给定图存在负环，没有最短距离");
	}
}
