package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
		Assert.assertEquals(Arrays.asList(true, false, false), prefixesDivBy5(new int[] { 0, 1, 1 }));
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

	// 947. 移除最多的同行或同列石头
	@Test
	public void test8() {
		Assert.assertEquals(5, new Solution8()
				.removeStones(new int[][] { { 0, 0 }, { 0, 1 }, { 1, 0 }, { 1, 2 }, { 2, 1 }, { 2, 2 } }));

		Assert.assertEquals(0, new Solution8().removeStones(new int[][] { { 0, 0 }, { 1, 1 } }));
	}

	// https://leetcode-cn.com/problems/most-stones-removed-with-same-row-or-column/solution/947-yi-chu-zui-duo-de-tong-xing-huo-tong-ezha/
	class Solution8 {
		public int removeStones(int[][] stones) {
			UnionFind unionFind = new UnionFind();
			for (int[] stone : stones) {
				unionFind.union(stone[0] + 10001, stone[1]);
			}
			return stones.length - unionFind.getCount();

		}

		private class UnionFind {
			private Map<Integer, Integer> parent;
			private int count;

			public UnionFind() {
				// TODO Auto-generated constructor stub
				this.parent = new HashMap<Integer, Integer>();
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
				if (x != parent.get(x)) {
					parent.put(x, find(parent.get(x)));
				}
				return parent.get(x);
			}

			public void union(int x, int y) {
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

	// 1232. 缀点成线
	@Test
	public void test9() {
//		Assert.assertEquals(true,
//				checkStraightLine(new int[][] { { 1, 2 }, { 2, 3 }, { 3, 4 }, { 4, 5 }, { 5, 6 }, { 6, 7 } }));
		Assert.assertEquals(true, checkStraightLine(new int[][] { { 0, 0 }, { 0, 1 }, { 0, -1 } }));
	}

	// https://leetcode-cn.com/problems/check-if-it-is-a-straight-line/solution/san-dian-xiang-chai-zhi-bi-li-chao-99-by-co4x/
	public boolean checkStraightLine(int[][] coordinates) {
		// 斜率
		int n = coordinates.length;
		for (int i = 1; i + 1 < n; i++) {
			if ((long) (coordinates[i][0] - coordinates[i - 1][0])
					* (coordinates[i + 1][1] - coordinates[i][1]) != (long) (coordinates[i][1] - coordinates[i - 1][1])
							* (coordinates[i + 1][0] - coordinates[i][0])) {
				return false;
			}
		}
		return true;

	}

	// 721. 账户合并
	@Test
	public void test10() {
		{
//			List<List<String>> accounts = new ArrayList<>();
//			accounts.add(Arrays.asList("John", "johnsmith@mail.com", "john00@mail.com"));
//			accounts.add(Arrays.asList("John", "johnnybravo@mail.com"));
//			accounts.add(Arrays.asList("John", "johnsmith@mail.com", "john_newyork@mail.com"));
//			accounts.add(Arrays.asList("Mary", "mary@mail.com"));
//			List<List<String>> res = new Solution10().accountsMerge(accounts);
//			for (List<String> list : res) {
//				System.out.println(Joiner.on(" ").join(list));
//			}
		}
		{
			List<List<String>> accounts = new ArrayList<>();
			accounts.add(Arrays.asList("David", "David0@m.com", "David1@m.com"));
			accounts.add(Arrays.asList("David", "David3@m.com", "David4@m.com"));
			accounts.add(Arrays.asList("David", "David4@m.com", "David5@m.com"));
			accounts.add(Arrays.asList("David", "David2@m.com", "David3@m.com"));
			accounts.add(Arrays.asList("David", "David1@m.com", "David2@m.com"));
			List<List<String>> res = new Solution10().accountsMerge(accounts);
			for (List<String> list : res) {
				System.out.println(Joiner.on(" ").join(list));
			}
		}
	}

	// https://leetcode-cn.com/problems/accounts-merge/solution/tu-jie-yi-ran-shi-bing-cha-ji-by-yexiso-5ncf/
	class Solution10 {
		public List<List<String>> accountsMerge(List<List<String>> accounts) {
			// 作用：存储每个邮箱属于哪个账户 ，同时 在遍历邮箱时，判断邮箱是否出现过[去重]
			// 格式：<邮箱，账户id>
			Map<String, Integer> emailToId = new HashMap<>();
			int n = accounts.size();// id个数
			UnionFind myUnion = new UnionFind(n);
			for (int i = 0; i < n; i++) {
				int num = accounts.get(i).size();
				for (int j = 1; j < num; j++) {
					String curEmail = accounts.get(i).get(j);
					// 当前邮箱没有出现过
					if (!emailToId.containsKey(curEmail)) {
						emailToId.put(curEmail, i);
					} else {// 当前邮箱已经出现过，那么代表这两个用户是同一个
						myUnion.union(i, emailToId.get(curEmail));
					}
				}
			}
			// 进行完上面的步骤，同一个用户的所有邮箱已经属于同一个连通域了，但是就算在同一个连通域，不同的邮箱还是可能会对应不同的id
			// 作用： 存储每个账户下的邮箱
			// 格式： <账户id, 邮箱列表> >
			// 注意：这里的key必须是账户id，不能是账户名称，名称可能相同，会造成覆盖
			Map<Integer, List<String>> idToEmails = new HashMap<>();
			// 将同一个连通域内的
			for (Map.Entry<String, Integer> entry : emailToId.entrySet()) {
				int id = myUnion.find(entry.getValue());
				List<String> emails = idToEmails.getOrDefault(id, new ArrayList<>());
				emails.add(entry.getKey());
				idToEmails.put(id, emails);
			}
			// 经过上面的步骤，已经做到了id和邮箱集合对应起来，接下来把用户名对应起来就可以了
			List<List<String>> res = new ArrayList<>();
			for (Map.Entry<Integer, List<String>> entry : idToEmails.entrySet()) {
				List<String> emails = entry.getValue();
				Collections.sort(emails);
				List<String> tmp = new ArrayList<>();
				tmp.add(accounts.get(entry.getKey()).get(0));// 先添加用户名
				tmp.addAll(emails);
				res.add(tmp);
			}
			return res;
		}

		class UnionFind {
			int[] parent;

			public UnionFind(int n) {
				parent = new int[n];
				for (int i = 0; i < n; i++) {
					parent[i] = i;
				}
			}

			public void union(int index1, int index2) {
				parent[find(index2)] = find(index1);
			}

			public int find(int index) {
				if (parent[index] != index) {
					parent[index] = find(parent[index]);
				}
				return parent[index];
			}

		}
	}

	// 989. 数组形式的整数加法
	@Test
	public void test11() {
//		Assert.assertEquals("1,2,3,4", Joiner.on(",").join(addToArrayForm(new int[] {
//				1,2,0,0
//		},34)));
//		
//		Assert.assertEquals("1,0,2,1", Joiner.on(",").join(addToArrayForm(new int[] {
//				2,1,5
//		},806)));

		Assert.assertEquals("4,5,5", Joiner.on(",").join(addToArrayForm(new int[] { 2, 7, 4 }, 181)));
	}

	public List<Integer> addToArrayForm(int[] A, int K) {
		// 获取k的长度
		int kLen = 0;
		int v = K;
		while (v != 0) {
			v = v / 10;
			kLen++;
		}
		// 转换为数组
		int[] kArr = new int[kLen];
		int post = kLen - 1;
		for (int i = 0; i < kArr.length; i++) {
			kArr[i] = K / ((int) Math.pow(10, post));
			K = K % (int) Math.pow(10, post);
			post--;
		}
		int aLen = A.length;
		LinkedList<Integer> res = new LinkedList<Integer>();
		int len = kLen > aLen ? kLen : aLen;
		int aIndex = A.length - 1;
		int bIndex = kArr.length - 1;
		int c = 0;// 进位
		while (len > 0) {
			int a = aIndex >= 0 ? A[aIndex] : 0;
			int b = bIndex >= 0 ? kArr[bIndex] : 0;
			int result = a + b + c;
			if (result >= 10) {
				c = result / 10;
				result = result % 10;
			} else {
				c = 0;
			}
			res.addFirst(result);
			len--;
			aIndex--;
			bIndex--;
		}
		if (c != 0) {
			res.addFirst(c);
		}
		return res;
	}

	// 1319. 连通网络的操作次数
	@Test
	public void test12() {
		Assert.assertEquals(1, new Solution12().makeConnected(4, new int[][] { { 0, 1 }, { 0, 2 }, { 1, 2 } }));
		Assert.assertEquals(2,
				new Solution12().makeConnected(6, new int[][] { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 2 }, { 1, 3 } }));
	}

	class Solution12 {
		public int makeConnected(int n, int[][] connections) {
			// 线条数少于n-1
			if (connections.length < n - 1) {
				return -1;
			}
			UnionFind uf = new UnionFind(n);
			for (int i = 0; i < connections.length; i++) {
				uf.union(connections[i][0], connections[i][1]);
			}
			int count = 0;
			for (int i = 0; i < n; i++) {
				if (uf.find(i) == i) {
					count++;
				}
			}
			return count - 1;
		}

		class UnionFind {
			int[] parent;

			public UnionFind(int n) {
				parent = new int[n];
				for (int i = 0; i < n; i++) {
					parent[i] = i;
				}
			}

			public void union(int x, int y) {
				parent[find(x)] = find(y);
			}

			public int find(int index) {
				if (parent[index] != index) {
					parent[index] = find(parent[index]);
				}
				return parent[index];
			}
		}
	}

	// 674. 最长连续递增序列
	@Test
	public void test13() {
		Assert.assertEquals(3, findLengthOfLCIS(new int[] { 1, 3, 5, 4, 7 }));
		Assert.assertEquals(1, findLengthOfLCIS(new int[] { 2, 2, 2, 2, 2 }));
	}

	public int findLengthOfLCIS(int[] nums) {
		if (nums.length == 0) {
			return 0;
		}
		int l = 0, r = 0;
		int maxLen = 0;
		while (r < nums.length - 1) {
			if (nums[r + 1] > nums[r]) {
				r++;
			} else {
				maxLen = Math.max(maxLen, r - l + 1);
				r++;
				l = r;
			}
		}
		maxLen = Math.max(maxLen, r - l + 1);
		return maxLen;
	}

	// 1128. 等价多米诺骨牌对的数量
	@Test
	public void test14() {
		Assert.assertEquals(1,
				new Solution14().numEquivDominoPairs(new int[][] { { 1, 2 }, { 2, 1 }, { 3, 4 }, { 5, 6 } }));
		Assert.assertEquals(3,
				new Solution14().numEquivDominoPairs(new int[][] { { 1, 2 }, { 1, 2 }, { 1, 1 }, { 1, 2 }, { 2, 2 } }));
	}

	class Solution14 {
		public int numEquivDominoPairs(int[][] dominoes) {
			Map<String, Integer> pairs = new HashMap<String, Integer>();
			int ret = 0;
			for (int[] domino : dominoes) {
				String key = domino[0] < domino[1] ? domino[0] + domino[1] + "" : domino[1] + domino[0] + "";
				int v = pairs.getOrDefault(key, 0);
				ret += v;
				pairs.put(key, (v + 1));
			}
			return ret;
		}

	}

	// 1579. 保证图可完全遍历
	@Test
	public void test15() {
		Assert.assertEquals(2, new Solution15().maxNumEdgesToRemove(4,
				new int[][] { { 3, 1, 2 }, { 3, 2, 3 }, { 1, 1, 3 }, { 1, 2, 4 }, { 1, 1, 2 }, { 2, 3, 4 } }));
	}

	// https://leetcode-cn.com/problems/remove-max-number-of-edges-to-keep-graph-fully-traversable/solution/bao-zheng-tu-ke-wan-quan-bian-li-by-leet-mtrw/
	class Solution15 {
		public int maxNumEdgesToRemove(int n, int[][] edges) {
			UnionFind ufa = new UnionFind(n + 1);
			UnionFind ufb = new UnionFind(n + 1);
			int ans = 0;
			// 公共边
			for (int[] edge : edges) {
				if (edge[0] == 3) {
					// 如果A能连通，则B也能连通，则该边多余
					if (!ufa.union(edge[1], edge[2])) {
						++ans;
					} else {
						ufb.union(edge[1], edge[2]);
					}
				}
			}

			// 独占边
			for (int[] edge : edges) {
				if (edge[0] == 1) {
					// Alice 独占边
					if (!ufa.union(edge[1], edge[2])) {
						++ans;
					}
				} else if (edge[0] == 2) {
					// Bob 独占边
					if (!ufb.union(edge[1], edge[2])) {
						++ans;
					}
				}
			}

			if (ufa.setCount != 2 || ufb.setCount != 2) {
				return -1;
			}
			return ans;
		}

		class UnionFind {
			int[] parent;
			// 当前连通分量数目
			int setCount;

			public UnionFind(int n) {
				this.setCount = n;
				parent = new int[n];
				for (int i = 0; i < n; i++) {
					parent[i] = i;
				}
			}

			public boolean union(int x, int y) {
				x = find(x);
				y = find(y);
				if (x == y) {
					return false;
				}
				parent[find(x)] = find(y);
				--setCount;
				return true;
			}

			public int find(int index) {
				if (parent[index] != index) {
					parent[index] = find(parent[index]);
				}
				return parent[index];
			}

			public boolean connected(int x, int y) {
				x = find(x);
				y = find(y);
				return x == y;
			}

		}
	}

	// 724. 寻找数组的中心索引
	@Test
	public void test16() {
		Assert.assertEquals(3, pivotIndex(new int[] { 1, 7, 3, 6, 5, 6 }));
		Assert.assertEquals(0, pivotIndex(new int[] { -1, -1, -1, 0, 1, 1 }));
	}

	public int pivotIndex(int[] nums) {
		if (nums.length == 0) {
			return -1;
		}
		int n = nums.length;
		int[] preSum = new int[n];
		preSum[0] = nums[0];
		for (int i = 1; i < n; i++) {
			preSum[i] = preSum[i - 1] + nums[i];
		}
		for (int i = 0; i < n; i++) {
			if (i == 0) {
				if (preSum[n - 1] - preSum[i] == 0) {
					return i;
				}
			} else {
				if (preSum[i - 1] == preSum[n - 1] - preSum[i]) {
					return i;
				}
			}
		}
		return -1;
	}

	// 778. 水位上升的泳池中游泳
	@Test
	public void test17() {
		Assert.assertEquals(16, new Solution17().swimInWater(new int[][] { { 0, 1, 2, 3, 4 }, { 24, 23, 22, 21, 5 },
				{ 12, 13, 14, 15, 16 }, { 11, 17, 18, 19, 20 }, { 10, 9, 8, 7, 6 } }));
	}

	// https://leetcode-cn.com/problems/swim-in-rising-water/solution/shui-wei-shang-sheng-de-yong-chi-zhong-y-862o/
	class Solution17 {
		public int swimInWater(int[][] grid) {
			int m = grid.length;
			int n = grid[0].length;
			List<int[]> edges = new ArrayList<int[]>();
			for (int i = 0; i < m; ++i) {
				for (int j = 0; j < n; ++j) {
					int id = i * n + j;
					if (i > 0) {
						edges.add(new int[] { id - n, id, Math.max(grid[i][j], grid[i - 1][j]) });
					}
					if (j > 0) {
						edges.add(new int[] { id - 1, id, Math.max(grid[i][j], grid[i][j - 1]) });
					}
				}
			}
			Collections.sort(edges, new Comparator<int[]>() {
				public int compare(int[] edge1, int[] edge2) {
					return edge1[2] - edge2[2];
				}
			});
			UnionFind uf = new UnionFind(m * n);
			int ans = 0;
			for (int[] edge : edges) {
				int x = edge[0], y = edge[1], v = edge[2];
				uf.unite(x, y);
				if (uf.connected(0, m * n - 1)) {
					ans = v;
					break;
				}
			}
			return ans;
		}

		// 并查集模板
		class UnionFind {
			int[] parent;
			int[] size;
			int n;
			// 当前连通分量数目
			int setCount;

			public UnionFind(int n) {
				this.n = n;
				this.setCount = n;
				this.parent = new int[n];
				this.size = new int[n];
				Arrays.fill(size, 1);
				for (int i = 0; i < n; ++i) {
					parent[i] = i;
				}
			}

			public int findset(int x) {
				return parent[x] == x ? x : (parent[x] = findset(parent[x]));
			}

			public boolean unite(int x, int y) {
				x = findset(x);
				y = findset(y);
				if (x == y) {
					return false;
				}
				if (size[x] < size[y]) {
					int temp = x;
					x = y;
					y = temp;
				}
				parent[y] = x;
				size[x] += size[y];
				--setCount;
				return true;
			}

			public boolean connected(int x, int y) {
				x = findset(x);
				y = findset(y);
				return x == y;
			}
		}
	}

	// 839. 相似字符串组
	@Test
	public void test18() {
		Assert.assertEquals(2, new Solution18().numSimilarGroups(new String[] { "tars", "rats", "arts", "star" }));
	}

	// https://leetcode-cn.com/problems/similar-string-groups/solution/xiang-si-zi-fu-chuan-zu-by-leetcode-solu-8jt9/
	class Solution18 {
		public int numSimilarGroups(String[] strs) {
			int n = strs.length;
			UnionFind uf = new UnionFind(n);
			for (int i = 0; i < strs.length; i++) {
				for (int j = i + 1; j < strs.length; j++) {
					if (uf.connected(i, j)) {
						continue;
					}
					// 判断是否相似
					if (check(strs[j], strs[i])) {
						uf.unite(i, j);
					}
				}
			}
			return uf.setCount;
		}

		private boolean check(String a, String b) {
			int num = 0;
			int len = a.length();
			for (int i = 0; i < len; i++) {
				if (a.charAt(i) != b.charAt(i)) {
					num++;
					if (num > 2) {
						return false;
					}
				}
			}
			return true;
		}
	}

	// 888. 公平的糖果棒交换
	@Test
	public void test19() {
		Assert.assertArrayEquals(new int[] { 1, 2 }, fairCandySwap(new int[] { 1, 1 }, new int[] { 2, 2 }));
	}

	public int[] fairCandySwap(int[] A, int[] B) {
		 int sumA = Arrays.stream(A).sum();
	        int sumB = Arrays.stream(B).sum();
	        int delta = (sumA - sumB) / 2;
	        Set<Integer> rec = new HashSet<Integer>();
	        for (int num : A) {
	            rec.add(num);
	        }
	        int[] ans = new int[2];
	        for (int y : B) {
	            int x = y + delta;
	            if (rec.contains(x)) {
	                ans[0] = x;
	                ans[1] = y;
	                break;
	            }
	        }
	        return ans;

	}
	
	//424. 替换后的最长重复字符
	@Test
	public void test20() {
		//Assert.assertEquals(4, characterReplacement("ABAB",2));
		Assert.assertEquals(4, characterReplacement("AABABBA",1));
	}
	//https://leetcode-cn.com/problems/longest-repeating-character-replacement/solution/tong-guo-ci-ti-liao-jie-yi-xia-shi-yao-shi-hua-don/
	public int characterReplacement(String s, int k) {
		int[] map = new int[26];
		if (s==null) {
			return 0;
		}
		char[] chars = s.toCharArray();
		int left=0,right=0;
		int historyCharMax = 0;
		for (right=0;right<chars.length;right++) {
			int index = chars[right]-'A';
			map[index]++;
			historyCharMax  = Math.max(historyCharMax, map[index]);
			if (right-left+1>historyCharMax+k) {
				map[chars[left]-'A']--;
				left++;
			}
		}
		return chars.length-left;
    }
}
