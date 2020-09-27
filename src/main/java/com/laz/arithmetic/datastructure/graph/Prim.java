package com.laz.arithmetic.datastructure.graph;

/*
 * Prim算法生成最小生成树(邻接矩阵) https://blog.csdn.net/coslay/article/details/47756917
 * https://www.cnblogs.com/skywang12345/archive/2004/01/13/3711510.html
 */
public class Prim {
	private int mEdgNum; // 边的数量
	private char[] mVexs; // 顶点集合
	private int[][] mMatrix; // 邻接矩阵
	private static final int INF = Integer.MAX_VALUE; // 最大值

	/*
	 * 创建图(用已提供的矩阵)
	 *
	 * 参数说明： vexs -- 顶点数组 matrix-- 矩阵(数据)
	 */
	public Prim(char[] vexs, int[][] matrix) {

		// 初始化"顶点数"和"边数"
		int vlen = vexs.length;

		// 初始化"顶点"
		mVexs = new char[vlen];
		for (int i = 0; i < mVexs.length; i++)
			mVexs[i] = vexs[i];

		// 初始化"边"
		mMatrix = new int[vlen][vlen];
		for (int i = 0; i < vlen; i++)
			for (int j = 0; j < vlen; j++)
				mMatrix[i][j] = matrix[i][j];

		// 统计"边"
		mEdgNum = 0;
		for (int i = 0; i < vlen; i++)
			for (int j = i + 1; j < vlen; j++)
				if (mMatrix[i][j] != INF)
					mEdgNum++;
	}

	/*
	 * 打印矩阵队列图
	 */
	public void print() {
		System.out.printf("Martix Graph:\n");
		for (int i = 0; i < mVexs.length; i++) {
			for (int j = 0; j < mVexs.length; j++)
				System.out.printf("%10d ", mMatrix[i][j]);
			System.out.printf("\n");
		}
	}

	/*
	 * 返回ch位置
	 */
	private int getPosition(char ch) {
		for (int i = 0; i < mVexs.length; i++)
			if (mVexs[i] == ch)
				return i;
		return -1;
	}

	public void prim(int start) {
		int num = mVexs.length; // 顶点个数
		int index = 0; // prim最小树的索引，即prims数组的索引
		char[] prims = new char[num]; // prim最小树的结果数组
		int[] weights = new int[num]; // 顶点间边的权值

		// prim最小生成树中第一个数是"图中第start个顶点"，因为是从start开始的。
		prims[index++] = mVexs[start];

		// 初始化"顶点的权值数组"，
		// 将每个顶点的权值初始化为"第start个顶点"到"该顶点"的权值。
		for (int i = 0; i < num; i++)
			weights[i] = mMatrix[start][i];
		// 将第start个顶点的权值初始化为0。
		// 可以理解为"第start个顶点到它自身的距离为0"。
		weights[start] = 0;

		for (int i = 0; i < num; i++) {
			// 由于从start开始的，因此不需要再对第start个顶点进行处理。
			if (start == i)
				continue;

			int j = 0;
			int k = 0;
			int min = INF;
			// 在未被加入到最小生成树的顶点中，找出权值最小的顶点。
			while (j < num) {
				// 若weights[j]=0，意味着"第j个节点已经被排序过"(或者说已经加入了最小生成树中)。
				if (weights[j] != 0 && weights[j] < min) {
					min = weights[j];
					k = j;
				}
				j++;
			}

			// 经过上面的处理后，在未被加入到最小生成树的顶点中，权值最小的顶点是第k个顶点。
			// 将第k个顶点加入到最小生成树的结果数组中
			prims[index++] = mVexs[k];
			// 将"第k个顶点的权值"标记为0，意味着第k个顶点已经排序过了(或者说已经加入了最小树结果中)。
			weights[k] = 0;
			// 当第k个顶点被加入到最小生成树的结果数组中之后，更新其它顶点的权值。
			for (j = 0; j < num; j++) {
				// 当第j个节点没有被处理，并且需要更新时才被更新。
				if (weights[j] != 0 && mMatrix[k][j] < weights[j])
					weights[j] = mMatrix[k][j];
			}
		}

		// 计算最小生成树的权值
		int sum = 0;
		for (int i = 1; i < index; i++) {
			int min = INF;
			// 获取prims[i]在mMatrix中的位置
			int n = getPosition(prims[i]);
			// 在vexs[0...i]中，找出到j的权值最小的顶点。
			for (int j = 0; j < i; j++) {
				int m = getPosition(prims[j]);
				if (mMatrix[m][n] < min)
					min = mMatrix[m][n];
			}
			sum += min;
		}
		// 打印最小生成树
		System.out.printf("PRIM(%c)=%d: ", mVexs[start], sum);
		for (int i = 0; i < index; i++)
			System.out.printf("%c ", prims[i]);
		System.out.printf("\n");
	}

	/*
	 * 获取图中的边
	 */
	private EData[] getEdges() {
		int index = 0;
		EData[] edges;

		edges = new EData[mEdgNum];
		for (int i = 0; i < mVexs.length; i++) {
			for (int j = i + 1; j < mVexs.length; j++) {
				if (mMatrix[i][j] != INF) {
					edges[index++] = new EData(mVexs[i], mVexs[j], mMatrix[i][j]);
				}
			}
		}

		return edges;
	}

	/*
	 * 对边按照权值大小进行排序(由小到大)
	 */
	private void sortEdges(EData[] edges, int elen) {

		for (int i = 0; i < elen; i++) {
			for (int j = i + 1; j < elen; j++) {

				if (edges[i].weight > edges[j].weight) {
					// 交换"边i"和"边j"
					EData tmp = edges[i];
					edges[i] = edges[j];
					edges[j] = tmp;
				}
			}
		}
	}

	/*
	 * 获取i的终点
	 */
	private int getEnd(int[] vends, int i) {
		while (vends[i] != 0)
			i = vends[i];
		return i;
	}

	// 边的结构体
	private static class EData {
		char start; // 边的起点
		char end; // 边的终点
		int weight; // 边的权重

		public EData(char start, char end, int weight) {
			this.start = start;
			this.end = end;
			this.weight = weight;
		}
	};

	public static void main(String[] args) {
		char[] vexs = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
		int matrix[][] = {
				/* A *//* B *//* C *//* D *//* E *//* F *//* G */
				/* A */ { 0, 12, INF, INF, INF, 16, 14 }, /* B */ { 12, 0, 10, INF, INF, 7, INF },
				/* C */ { INF, 10, 0, 3, 5, 6, INF }, /* D */ { INF, INF, 3, 0, 4, INF, INF },
				/* E */ { INF, INF, 5, 4, 0, 2, 8 }, /* F */ { 16, 7, 6, INF, 2, 0, 9 },
				/* G */ { 14, INF, INF, INF, 8, 9, 0 } };
		Prim pG = new Prim(vexs, matrix);
		pG.prim(0); 
	}
}
