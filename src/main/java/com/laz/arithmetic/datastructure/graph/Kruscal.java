package com.laz.arithmetic.datastructure.graph;

/*
 * Kruskal算法生成最小生成树(邻接矩阵) https://blog.csdn.net/coslay/article/details/47756917
 * https://www.cnblogs.com/skywang12345/archive/2004/01/13/3711510.html
 */
public class Kruscal {
	private int mEdgNum; // 边的数量
	private char[] mVexs; // 顶点集合
	private int[][] mMatrix; // 邻接矩阵
	private static final int INF = Integer.MAX_VALUE; // 最大值

	
	/*
	 * 创建图(用已提供的矩阵)
	 *
	 * 参数说明： vexs -- 顶点数组 matrix-- 矩阵(数据)
	 */
	public Kruscal(char[] vexs, int[][] matrix) {

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
        for(int i=0; i<mVexs.length; i++)
            if(mVexs[i]==ch)
                return i;
        return -1;
    }
	
	/*
	 * 克鲁斯卡尔（Kruskal)最小生成树
	 */
	public void kruskal() {
		int index = 0; // rets数组的索引
		int[] vends = new int[mEdgNum]; // 用于保存"已有最小生成树"中每个顶点在该最小树中的终点。
		EData[] rets = new EData[mEdgNum]; // 结果数组，保存kruskal最小生成树的边
		EData[] edges; // 图对应的所有边

		// 获取"图中所有的边"
		edges = getEdges();
		// 将边按照"权"的大小进行排序(从小到大)
		sortEdges(edges, mEdgNum);

		for (int i = 0; i < mEdgNum; i++) {
			int p1 = getPosition(edges[i].start); // 获取第i条边的"起点"的序号
			int p2 = getPosition(edges[i].end); // 获取第i条边的"终点"的序号

			int m = getEnd(vends, p1); // 获取p1在"已有的最小生成树"中的终点
			int n = getEnd(vends, p2); // 获取p2在"已有的最小生成树"中的终点
			// 如果m!=n，意味着"边i"与"已经添加到最小生成树中的顶点"没有形成环路
			if (m != n) {
				vends[m] = n; // 设置m在"已有的最小生成树"中的终点为n
				rets[index++] = edges[i]; // 保存结果
			}
		}

		// 统计并打印"kruskal最小生成树"的信息
		int length = 0;
		for (int i = 0; i < index; i++)
			length += rets[i].weight;
		System.out.printf("Kruskal=%d: ", length);
		for (int i = 0; i < index; i++)
			System.out.printf("(%c,%c) ", rets[i].start, rets[i].end);
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
		Kruscal pG;
		pG = new Kruscal(vexs, matrix);
		pG.kruskal(); // Kruskal算法生成最小生成树
	}
}
