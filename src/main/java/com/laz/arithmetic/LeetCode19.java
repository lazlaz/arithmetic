package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode19 {
	// 830. 较大分组的位置
	@Test
	public void test1() {
		List<List<Integer>> ret = largeGroupPositions("aaa");
		for (List<Integer> list : ret) {
			System.out.println(Joiner.on(",").join(list));
		}
	}

	public List<List<Integer>> largeGroupPositions(String s) {
		List<List<Integer>> ret = new ArrayList<List<Integer>>();
		char currChar = ' ';
		int start = 0;
		int end = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (currChar == ' ') {
				currChar = c;
				start = i;
			} else if (currChar == c) {

			} else {
				end = i - 1;
				if (end - start >= 2) {
					ret.add(Arrays.asList(start, end));
				}
				currChar = c;
				start = i;
			}
		}
		end = s.length() - 1;
		if (end - start >= 2) {
			ret.add(Arrays.asList(start, end));
		}
		return ret;
	}

	// 547. 省份数量
	@Test
	public void test2() {
		Assert.assertEquals(2, new Solution2().findCircleNum(new int[][] { { 1, 1, 0 }, { 1, 1, 0 }, { 0, 0, 1 } }));
		Assert.assertEquals(3, new Solution2().findCircleNum(new int[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 } }));
	}

	class Solution2 {
		public class UnionFind {
			private int[] parent;
			private int[] height;
			int size;

			public UnionFind(int size) {
				this.size = size;
				this.parent = new int[size];
				this.height = new int[size];
				for (int i = 0; i < size; i++) {
					parent[i] = i;
					height[i] = 1;
				}
			}

			public int find(int element) {
				while (element != parent[element]) {
					element = parent[element];
				}
				return element;
			}

			public boolean isConnected(int firstElement, int secondElement) {
				return find(firstElement) == find(secondElement);
			}

			public void unionElements(int firstElement, int secondElement) {
				int firstRoot = find(firstElement);
				int secondRoot = find(secondElement);
				// 如果已经属于同一个集合了，就不用再合并了。
				if (firstRoot == secondRoot) {
					return;
				}

				if (height[firstRoot] < height[secondRoot]) {
					parent[firstRoot] = secondRoot;
				} else if (height[firstRoot] > height[secondRoot]) {
					parent[secondRoot] = firstRoot;
				} else {
					parent[firstRoot] = secondRoot;
					height[secondRoot] += 1;
				}
			}

		}

		public int findCircleNum(int[][] isConnected) {
			int n = isConnected.length;
			UnionFind union = new UnionFind(n);
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (isConnected[i][j] == 1) {
						union.unionElements(i, j);
					}
				}
			}
			int count = 0;
			for (int i = 0; i < union.parent.length; i++) {
				if (union.parent[i] == i) {
					count++;
				}
			}
			return count;
		}
	}

	// 228. 汇总区间
	@Test
	public void test3() {
		List<String> ret = summaryRanges(new int[] { 0, 1, 2, 4, 5, 7 });
		System.out.println(Joiner.on(",").join(ret));
	}

	public List<String> summaryRanges(int[] nums) {
		List<String> ret = new ArrayList<String>();
		if (nums.length == 0) {
			return ret;
		}
		int start = nums[0];
		int end = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] != end + 1) {
				if (start == end) {
					ret.add(start + "");
				} else {
					ret.add(start + "->" + end);
				}
				start = nums[i];
				end = nums[i];
			} else {
				end = nums[i];
			}
		}
		if (start == end) {
			ret.add(start + "");
		} else {
			ret.add(start + "->" + end);
		}
		return ret;
	}

	// 1202. 交换字符串中的元素
	@Test
	public void test4() {
		List<List<Integer>> pairs = new ArrayList<List<Integer>>();
		pairs.add(Arrays.asList(0, 3));
		pairs.add(Arrays.asList(1, 2));
		pairs.add(Arrays.asList(0, 2));
		Assert.assertEquals("abcd", new Solution4().smallestStringWithSwaps("dcab", pairs));
	}

	// https://leetcode-cn.com/problems/smallest-string-with-swaps/solution/1202-jiao-huan-zi-fu-chuan-zhong-de-yuan-wgab/
	class Solution4 {
		public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
			if (pairs.size() == 0) {
				return s;
			}

			// 第 1 步：将任意交换的结点对输入并查集
			int len = s.length();
			UnionFind unionFind = new UnionFind(len);
			for (List<Integer> pair : pairs) {
				int index1 = pair.get(0);
				int index2 = pair.get(1);
				unionFind.union(index1, index2);
			}

			// 第 2 步：构建映射关系
			char[] charArray = s.toCharArray();
			// key：连通分量的代表元，value：同一个连通分量的字符集合（保存在一个优先队列中）
			Map<Integer, PriorityQueue<Character>> hashMap = new HashMap<>(len);
			for (int i = 0; i < len; i++) {
				int root = unionFind.find(i);
//		            if (hashMap.containsKey(root)) {
//		                hashMap.get(root).offer(charArray[i]);
//		            } else {
//		                PriorityQueue<Character> minHeap = new PriorityQueue<>();
//		                minHeap.offer(charArray[i]);
//		                hashMap.put(root, minHeap);
//		            }
				// 上面六行代码等价于下面一行代码，JDK 1.8 以及以后支持下面的写法
				hashMap.computeIfAbsent(root, key -> new PriorityQueue<>()).offer(charArray[i]);
			}

			// 第 3 步：重组字符串
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < len; i++) {
				int root = unionFind.find(i);
				stringBuilder.append(hashMap.get(root).poll());
			}
			return stringBuilder.toString();
		}

		private class UnionFind {

			private int[] parent;
			/**
			 * 以 i 为根结点的子树的高度（引入了路径压缩以后该定义并不准确）
			 */
			private int[] rank;

			public UnionFind(int n) {
				this.parent = new int[n];
				this.rank = new int[n];
				for (int i = 0; i < n; i++) {
					this.parent[i] = i;
					this.rank[i] = 1;
				}
			}

			public void union(int x, int y) {
				int rootX = find(x);
				int rootY = find(y);
				if (rootX == rootY) {
					return;
				}

				if (rank[rootX] == rank[rootY]) {
					parent[rootX] = rootY;
					// 此时以 rootY 为根结点的树的高度仅加了 1
					rank[rootY]++;
				} else if (rank[rootX] < rank[rootY]) {
					parent[rootX] = rootY;
					// 此时以 rootY 为根结点的树的高度不变
				} else {
					// 同理，此时以 rootX 为根结点的树的高度不变
					parent[rootY] = rootX;
				}
			}

			public int find(int x) {
				if (x != parent[x]) {
					parent[x] = find(parent[x]);
				}
				return parent[x];
			}
		}

	}

	// 1203. 项目管理
	@Test
	public void test5() {
		List<List<Integer>> beforeItems = new ArrayList<List<Integer>>();
		beforeItems.add(Collections.EMPTY_LIST);
		beforeItems.add(Arrays.asList(6));
		beforeItems.add(Arrays.asList(5));
		beforeItems.add(Arrays.asList(6));
		beforeItems.add(Arrays.asList(3, 6));
		beforeItems.add(Collections.EMPTY_LIST);
		beforeItems.add(Collections.EMPTY_LIST);
		beforeItems.add(Collections.EMPTY_LIST);
		System.out.println(
				Arrays.toString(new Solution5().sortItems(8, 2, new int[] { -1, -1, 1, 0, 0, 1, 0, -1 }, beforeItems)));
	}

	// https://leetcode-cn.com/problems/sort-items-by-groups-respecting-dependencies/solution/chao-xiang-xi-shuang-ceng-tuo-bu-pai-xu-5cyuc/
	class Solution5 {
		public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
			List<List<Integer>> groupItem = new ArrayList<>();// 项目分组
			for (int i = 0; i < n + m; i++) {// 初始化小组
				groupItem.add(new ArrayList<>());
			}
			int gId = m;// 新的组号从m开始
			for (int i = 0; i < group.length; i++) {
				if (group[i] == -1)
					group[i] = gId++;// 没有id的加上组id
				groupItem.get(group[i]).add(i);// 同一组的放在一起
			}
			List<List<Integer>> graphInGroup = new ArrayList<>();// 组内拓扑关系
			List<List<Integer>> graphOutGroup = new ArrayList<>();// 组间拓扑关系
			for (int i = 0; i < n + m; i++) {// 初始化拓扑关系
				graphOutGroup.add(new ArrayList<>());
				if (i >= n)
					continue;
				graphInGroup.add(new ArrayList<>());
			}
			List<Integer> groupId = new ArrayList<>();// 所有组id
			for (int i = 0; i < n + m; i++) {
				groupId.add(i);
			}
			// 需要拓扑排序 所以结点的入度必不可少 两个数组分别维护不同结点的入度
			int[] degInGroup = new int[n];// 组内 结点入度 （组内项目入度）
			int[] degOutGroup = new int[n + m];// 组间 结点入度（小组入度）

			for (int i = 0; i < beforeItems.size(); i++) {// 遍历关系
				int curGroupId = group[i];// 当前项目i所属的小组id
				List<Integer> beforeItem = beforeItems.get(i);
				for (Integer item : beforeItem) {
					if (group[item] == curGroupId) {// 同一组 修改组内拓扑
						degInGroup[i]++;// 组内结点的入度+1
						graphInGroup.get(item).add(i);// item 在 i之前
					} else {
						degOutGroup[curGroupId]++;// 小组间的结点入度 + 1
						graphOutGroup.get(group[item]).add(curGroupId);// group[item] 小组 在 curGroupId 之前
					}
				}
			}
			// 组间拓扑排序，也就是小组之间的拓扑排序，需要的参数
			// 小组结点的入度degOutGroup，所有的小组groupId，组间的拓扑关系图graphOutGroup
			List<Integer> outGroupTopSort = topSort(degOutGroup, groupId, graphOutGroup);
			if (outGroupTopSort.size() == 0)
				return new int[0];// 无法拓扑排序 返回

			int[] res = new int[n];
			int index = 0;
			for (Integer gid : outGroupTopSort) {// 遍历排序后的小组id
				List<Integer> items = groupItem.get(gid);// 根据小组id 拿到这一小组中的所有成员
				if (items.size() == 0)
					continue;
				// 组内拓扑排序，需要的参数
				// 组内结点的入度degInGroup，组内的所有的结点groupItem.get(gid)，组内的拓扑关系图graphInGroup
				List<Integer> inGourpTopSort = topSort(degInGroup, groupItem.get(gid), graphInGroup);
				if (inGourpTopSort.size() == 0)
					return new int[0];// 无法拓扑排序 返回
				for (int item : inGourpTopSort) {// 排序后，依次的放入答案集合当中
					res[index++] = item;
				}
			}
			return res;
		}

		public List<Integer> topSort(int[] deg, List<Integer> items, List<List<Integer>> graph) {
			Queue<Integer> queue = new LinkedList<>();
			for (Integer item : items) {
				if (deg[item] == 0)
					queue.offer(item);
			}
			List<Integer> res = new ArrayList<>();
			while (!queue.isEmpty()) {
				int cur = queue.poll();
				res.add(cur);
				for (int neighbor : graph.get(cur)) {
					if (--deg[neighbor] == 0) {
						queue.offer(neighbor);
					}
				}
			}
			return res.size() == items.size() ? res : new ArrayList<>();
		}
	}

	// 684. 冗余连接
	@Test
	public void test6() {
		Assert.assertArrayEquals(new int[] { 1, 4 }, new Solution6_2()
				.findRedundantConnection(new int[][] { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 1, 4 }, { 1, 5 } }));

		Assert.assertArrayEquals(new int[] { 2, 3 },
				new Solution6().findRedundantConnection(new int[][] { { 1, 2 }, { 1, 3 }, { 2, 3 } }));
	}

	// https://leetcode-cn.com/problems/redundant-connection/solution/rong-yu-lian-jie-by-leetcode-solution-pks2/
	// 并查集
	class Solution6_2 {
		public int[] findRedundantConnection(int[][] edges) {
			int nodesCount = edges.length;
			int[] parent = new int[nodesCount + 1];
			for (int i = 1; i <= nodesCount; i++) {
				parent[i] = i;
			}
			for (int i = 0; i < nodesCount; i++) {
				int[] edge = edges[i];
				int node1 = edge[0], node2 = edge[1];
				if (find(parent, node1) != find(parent, node2)) {
					union(parent, node1, node2);
				} else {
					return edge;
				}
			}
			return new int[0];
		}

		public void union(int[] parent, int index1, int index2) {
			parent[find(parent, index1)] = find(parent, index2);
		}

		public int find(int[] parent, int index) {
			if (parent[index] != index) {
				parent[index] = find(parent, parent[index]);
			}
			return parent[index];
		}

	}

	// 找到环，然后遍历环的边
	class Solution6 {
		private int[] res = null;

		public int[] findRedundantConnection(int[][] edges) {
			Map<Integer, Set<Integer>> graph = new HashMap<>();
			// 构造图
			for (int i = 0; i < edges.length; i++) {
				int start = edges[i][0];
				int end = edges[i][1];
				Set<Integer> ends = graph.getOrDefault(start, new HashSet<Integer>());
				ends.add(end);
				graph.put(start, ends);
				Set<Integer> starts = graph.getOrDefault(end, new HashSet<Integer>());
				starts.add(start);
				graph.put(end, starts);
			}
			// 找出环
			int n = graph.size();
			int[] visited = new int[n + 1]; // 白色、灰色、黑色三种状态 0,1,2
			int[] father = new int[n + 1];
			for (Integer node : graph.keySet()) {
				dfs(graph, node, visited, father, edges);
			}
			return res;
		}

		private void dfs(Map<Integer, Set<Integer>> graph, Integer node, int[] visited, int[] father, int[][] edges) {
			if (res != null) {
				return;
			}
			visited[node] = 1;
			Set<Integer> ends = graph.get(node);
			for (Integer n : ends) {
				if (res != null) {
					return;
				}
				if (visited[n] == 1 && n != father[node]) {
					// 获取相关的边
					int key = node;
					int[][] edgesPath = new int[graph.size() + 1][2];
					int index = 0;
					while (father[key] != 0 && key != n) {
						edgesPath[index++] = new int[] { key, father[key] };
						key = father[key];
					}
					edgesPath[index] = new int[] { n, node };
					// 找到最近的边
					for (int i = edges.length - 1; i >= 0; i--) {
						int[] edge = edges[i];
						for (int j = 0; j < edgesPath.length; j++) {
							int[] edgePath = edgesPath[j];
							if (edgePath != null && edgePath[0] == edge[0] && edgePath[1] == edge[1]) {
								res = edge;
								return;
							}
							if (edgePath != null && edgePath[1] == edge[0] && edgePath[0] == edge[1]) {
								res = edge;
								return;
							}
						}
					}
					return;
				} else if (visited[n] == 0) {
					father[n] = node;
					dfs(graph, n, visited, father, edges);
				}

			}
			visited[node] = 2;
		}
	}

	// 1018. 可被 5 整除的二进制前缀
	@Test
	public void test7() {
		Assert.assertEquals(Arrays.asList(true,false,false), prefixesDivBy5(new int[] { 0, 1, 1 }));
	}

	// https://leetcode-cn.com/problems/binary-prefix-divisible-by-5/solution/ke-bei-5-zheng-chu-de-er-jin-zhi-qian-zh-asih/
	public List<Boolean> prefixesDivBy5(int[] A) {
		List<Boolean> list = new ArrayList<Boolean>();
		int prefix = 0;
		int length = A.length;
		for (int i = 0; i < length; i++) {
			prefix = ((prefix << 1) + A[i]) % 5;
			list.add(prefix == 0);
		}
		return list;
	}
	//947. 移除最多的同行或同列石头
	@Test
	public void test8() {
		Assert.assertEquals(5, new Solution8().removeStones(new int[][] {
			{0,0},{0,1},{1,0},{1,2},{2,1},{2,2}
		}));
		
		Assert.assertEquals(0, new Solution8().removeStones(new int[][] {
			{0,0},{1,1}
		}));
	}
	//https://leetcode-cn.com/problems/most-stones-removed-with-same-row-or-column/solution/947-yi-chu-zui-duo-de-tong-xing-huo-tong-ezha/
	class Solution8{
		public int removeStones(int[][] stones) {
			UnionFind unionFind = new UnionFind();
	        for (int[] stone : stones) {
	            unionFind.union(stone[0] + 10001, stone[1]);
	        }
	        return stones.length - unionFind.getCount();

		}
		private class UnionFind {
			private Map<Integer,Integer> parent;
			private  int count;
			public UnionFind() {
				// TODO Auto-generated constructor stub
				this.parent = new HashMap<Integer,Integer>();
				this.count = 0;
			}
			public int getCount() {
				return count;
			}
			public int find(int x) {
				if (!parent.containsKey(x)) {
					parent.put(x, x);
					  // 并查集集中新加入一个结点，结点的父亲结点是它自己，所以连通分量的总数 +1
					count++;
				}
				if (x!=parent.get(x)) {
					 parent.put(x, find(parent.get(x)));
				}
				return parent.get(x);
			}
			public void union(int x,int y) {
				int rootX = find(x);
				int rootY = find(y);
				if (rootX == rootY) {
					return;
				}
				parent.put(rootX, rootY);
				 // 两个连通分量合并成为一个，连通分量的总数 -1
	            count--;
			}
		}
	}
}
