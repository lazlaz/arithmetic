package com.laz.arithmetic.datastructure.graph;

/**
 * 弗洛伊德算法（Floyd)
 *
 */
public class Floyd {
	int[][] Matrix;
	char[] Nodes;

	private final int INF = Integer.MAX_VALUE;

	public Floyd(char[] Nodes, int[][] Matrix) {
		this.Nodes = Nodes;
		this.Matrix = Matrix;
	}

	public void floyd() {

		int[][] distance = new int[Nodes.length][Nodes.length];

		// 初始化距离矩阵
		for (int i = 0; i < Nodes.length; i++) {
			for (int j = 0; j < Nodes.length; j++) {
				distance[i][j] = Matrix[i][j];
			}
		}

		// 循环更新矩阵的值
		for (int k = 0; k < Nodes.length; k++) {
			for (int i = 0; i < Nodes.length; i++) {
				for (int j = 0; j < Nodes.length; j++) {
					int temp = (distance[i][k] == INF || distance[k][j] == INF) ? INF : distance[i][k] + distance[k][j];
					if (distance[i][j] > temp) {
						distance[i][j] = temp;
					}
				}
			}
		}

		// 打印floyd最短路径的结果
		System.out.printf("floyd: \n");
		for (int i = 0; i < Nodes.length; i++) {
			for (int j = 0; j < Nodes.length; j++)
				System.out.printf("%12d  ", distance[i][j]);
			System.out.printf("\n");
		}
	}

	public static void main(String[] args) {
		int INF = Integer.MAX_VALUE;
		char[] Nodes = { '1', '2', '3','4' };
		int matrix[][] = {
				/* A *//* B *//* C *//* D */
				/* A */ { 0, 2, 6, 4 }, 
				/* B */ { INF, 0, 3, INF },
				/* C */ { 7, INF, 0, 1 },
				/* D */ { 5, INF, 12, 0 }, };
		Floyd floyd = new Floyd(Nodes, matrix);
		floyd.floyd();
	}

}
