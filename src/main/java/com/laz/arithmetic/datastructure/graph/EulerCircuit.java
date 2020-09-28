package com.laz.arithmetic.datastructure.graph;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * 欧拉路径和欧拉回路 https://blog.csdn.net/seagal890/article/details/94845465
 *
 */
public class EulerCircuit {
	private int V; // 顶点（ vertices）的数量

	// 邻接表（ Adjacency List Representation）表示
	private LinkedList<Integer> adj[];

	// 构造方法
	EulerCircuit(int v) {
		V = v;
		adj = new LinkedList[v];
		for (int i = 0; i < v; ++i)
			adj[i] = new LinkedList();
	}

	// 在图中加入边
	void addEdge(int v, int e) {
		adj[v].add(e);//
		adj[e].add(v); // 无向图
	}

	// DFS搜索算法
	void DFSUtil(int v, boolean visited[]) {
		// 标记当前的结点为访问过的结点visited
		visited[v] = true;

		// 遍历迭代与此顶点相邻的所有顶点
		Iterator<Integer> i = adj[v].listIterator();
		while (i.hasNext()) {
			int n = i.next();
			if (!visited[n])
				DFSUtil(n, visited);
		}
	}

	// 检查所有非零度顶点是否连接
	boolean isConnected() {
		// 标记所有顶点为未访问状态
		boolean visited[] = new boolean[V];
		int i;
		for (i = 0; i < V; i++)
			visited[i] = false;

		// 找到非0度的顶点
		for (i = 0; i < V; i++)
			if (adj[i].size() != 0)
				break;

		if (i == V)
			return true;

		DFSUtil(i, visited);

		for (i = 0; i < V; i++)
			if (visited[i] == false && adj[i].size() > 0)
				return false;

		return true;
	}

	/*
	 * 返回值说明 0 --> 图不是欧拉图 Eulerian则返回0 1 --> 如果图中存在欧拉路则返回1 (Semi-Eulerian) 2 -->
	 * 如果图中存在欧拉回路则返回2 Euler Circuit (Eulerian)
	 */
	int isEulerian() {
		// 检查是否连接了所有非零度顶点
		if (isConnected() == false)
			return 0;

		// 统计奇数度的顶点
		int odd = 0;
		for (int i = 0; i < V; i++)
			if (adj[i].size() % 2 != 0)
				odd++;

		// If count is more than 2, then graph is not Eulerian
		if (odd > 2)
			return 0;

		// If odd count is 2, then semi-eulerian.
		// If odd count is 0, then eulerian
		// Note that odd count can never be 1 for undirected graph
		return (odd == 2) ? 1 : 2;
	}

	// Function to run test cases
	public void test() {
		int res = isEulerian();
		if (res == 0)
			System.out.println("图 不是 欧拉图 Eulerian");
		else if (res == 1)
			System.out.println("图 包含 欧拉路 Euler path");
		else
			System.out.println("图 包含 欧拉回路 Euler cycle");
	}

	public static void main(String args[]) {

		EulerCircuit g1 = new EulerCircuit(5);
		g1.addEdge(1, 0);
		g1.addEdge(0, 2);
		g1.addEdge(2, 1);
		g1.addEdge(0, 3);
		g1.addEdge(3, 4);
		g1.test();

		EulerCircuit g2 = new EulerCircuit(5);
		g2.addEdge(1, 0);
		g2.addEdge(0, 2);
		g2.addEdge(2, 1);
		g2.addEdge(0, 3);
		g2.addEdge(3, 4);
		g2.addEdge(4, 0);
		g2.test();

		EulerCircuit g3 = new EulerCircuit(5);
		g3.addEdge(1, 0);
		g3.addEdge(0, 2);
		g3.addEdge(2, 1);
		g3.addEdge(0, 3);
		g3.addEdge(3, 4);
		g3.addEdge(1, 3);
		g3.test();

		EulerCircuit g4 = new EulerCircuit(6); // 6个顶点，10条边
		g4.addEdge(0, 1);
		g4.addEdge(1, 2);
		g4.addEdge(2, 0);
		g4.addEdge(3, 4);
		g4.addEdge(4, 5);
		g4.addEdge(5, 3);
		g4.addEdge(0, 3);
		g4.addEdge(0, 5);
		g4.addEdge(2, 3);
		g4.addEdge(2, 5);
		g4.test();

		EulerCircuit g5 = new EulerCircuit(5);
		g5.addEdge(0, 1);
		g5.addEdge(0, 2);
		g5.addEdge(1, 2);
		g5.addEdge(1, 3);
		g5.addEdge(1, 4);
		g5.addEdge(4, 2);
		g5.addEdge(4, 3);
		g5.addEdge(2, 3);
		g5.test();

		EulerCircuit g6 = new EulerCircuit(3);
		g6.addEdge(0, 1);
		g6.addEdge(1, 2);
		g6.addEdge(2, 0);
		g6.test();

		EulerCircuit g7 = new EulerCircuit(3);
		g7.test();
	}

}
