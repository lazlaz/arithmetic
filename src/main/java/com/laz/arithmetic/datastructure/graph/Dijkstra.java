package com.laz.arithmetic.datastructure.graph;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Dijkstra算法详细(单源最短路径算法) https://www.cnblogs.com/bigsai/p/11537975.html
 *
 */
public class Dijkstra {
	class node {
		int x; // 节点编号
		int lenth;// 长度

		public node(int x, int lenth) {
			this.x = x;
			this.lenth = lenth;
		}
	}

	public void find(int[][] map, int start) {
		Comparator<node> com = new Comparator<node>() {
			public int compare(node o1, node o2) {
				return o1.lenth - o2.lenth;
			}
		};
		boolean bool[] = new boolean[map.length];// 判断是否已经确定
		int len[] = new int[map.length];// 长度
		for (int i = 0; i < map.length; i++) {
			len[i] = Integer.MAX_VALUE;
		}
		Queue<node> q1 = new PriorityQueue<node>(com);
		len[start] = 0;// 从0这个点开始
		q1.add(new node(start, 0));
		while (!q1.isEmpty()) {
			node t1 = q1.poll();
			int index = t1.x;// 节点编号
			int length = t1.lenth;// 节点当前点距离
			bool[index] = true;// 抛出的点确定
			for (int i = 0; i < map[index].length; i++) {
				if (map[index][i] > 0 && !bool[i]) {
					node node = new node(i, length + map[index][i]);
					if (len[i] > node.lenth)// 需要更新节点的时候更新节点并加入队列
					{
						len[i] = node.lenth;
						q1.add(node);
					}
				}
			}
		}
		// 打印start到其他店的最短距离
		for (int i = 0; i < 6; i++) {
			System.out.println(len[i]);
		}
	}

	public static void main(String[] args) {
		int[][] map = new int[6][6];// 记录权值，顺便记录链接情况，可以考虑附加邻接表
		Dijkstra dj = new Dijkstra();
		dj.initmap(map);// 初始化
		dj.find(map, 5);
	}

	private static void initmap(int[][] map) {
		map[0][1] = 2;
		map[0][2] = 3;
		map[0][3] = 6;
		map[1][0] = 2;
		map[1][4] = 4;
		map[1][5] = 6;
		map[2][0] = 3;
		map[2][3] = 2;
		map[3][0] = 6;
		map[3][2] = 2;
		map[3][4] = 1;
		map[3][5] = 3;
		map[4][1] = 4;
		map[4][3] = 1;
		map[5][1] = 6;
		map[5][3] = 3;
	}
}
