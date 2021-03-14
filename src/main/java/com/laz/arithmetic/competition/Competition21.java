package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-231/
public class Competition21 {
	// 1785. 构成特定和需要添加的最少元素
	@Test
	public void test2() {
		Assert.assertEquals(2, minElements(new int[] { 1, -1, 1 }, 3, -4));
	}

	public int minElements(int[] nums, int limit, int goal) {
		long sum = 0l;
		for (int i = 0; i < nums.length; ++i) {
			sum += nums[i];
		}
		long diff = Math.abs(goal - sum);
		int res = 0;
		// 贪心,优先减最多
		res = diff % limit == 0 ? (int) (diff / limit) : (int) (diff / limit) + 1;
		return res;
	}

	@Test
	// 1786. 从第一个节点出发到最后一个节点的受限路径数
	public void test3() {
		Assert.assertEquals(3, new Solution3().countRestrictedPaths(5, new int[][] { { 1, 2, 3 }, { 1, 3, 3 },
				{ 2, 3, 1 }, { 1, 4, 2 }, { 5, 2, 2 }, { 3, 5, 1 }, { 5, 4, 10 } }));
	}

	class Solution3 {
		final int MOD = 1000000007;

		public int countRestrictedPaths(int n, int[][] edges) {
			int cnt = 0;
			Map<Integer, List<int[]>> map = new HashMap<>();
			// 初始化邻接表
			for (int[] t : edges) {
				int x = t[0];
				int y = t[1];
				map.computeIfAbsent(x, k -> new ArrayList<>()).add(new int[] { y, t[2] });
				map.computeIfAbsent(y, k -> new ArrayList<>()).add(new int[] { x, t[2] });
			}

			// 保存到n点的 最短距离 和 受限路径数 通过迪杰斯特拉算法求，利用堆时间复杂度(M+n)*Logn
			int[] distance = findShortPath(map, n, n);
			Long[] mem = new Long[n + 1];

			//dfs遍历，并根据规则+动态规划进行剪枝
			cnt = (int) findLimitedPathCount(map, 1, n, distance, mem);
			return cnt;
		}

		private long findLimitedPathCount(Map<Integer, List<int[]>> map, int i, int n, int[] distance, Long[] mem) {
			if (mem[i] != null)
				return mem[i];
			if (i == n)
				return 1;
			long cnt = 0;
			List<int[]> list = map.getOrDefault(i, Collections.emptyList());
			for (int[] arr : list) {
				int next = arr[0];
				// 如果相邻节点距离比当前距离小，说明是受限路径
				if (distance[next] < distance[i]) {
					cnt += findLimitedPathCount(map, next, n, distance, mem);
					cnt %= MOD;
				}
			}
			mem[i] = cnt;
			return cnt;
		}

		public int[] findShortPath(Map<Integer, List<int[]>> map, int n, int start) {
			// 初始化distance数组和visit数组，并用最大值填充作为非连接状态INF
			int[] distance = new int[n + 1];
			Arrays.fill(distance, Integer.MAX_VALUE);
			boolean[] visit = new boolean[n + 1];

			// 初始化，索引0和起点的distance为0
			distance[start] = 0;
			distance[0] = 0;

			// 堆优化，将距离作为排序标准。单独用传入距离是因为PriorityQueue的上浮规则决定
			PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> o1[1] - o2[1]);
			// 把起点放进去，距离为0
			queue.offer(new int[] { start, 0 });

			while (!queue.isEmpty()) {
				// 当队列不空，拿出一个源出来
				Integer poll = queue.poll()[0];
				if (visit[poll])
					continue;
				// 标记访问
				visit[poll] = true;
				// 遍历它的相邻节点
				List<int[]> list = map.getOrDefault(poll, Collections.emptyList());
				for (int[] arr : list) {
					int next = arr[0];
					if (visit[next])
						continue;
					// 更新到这个相邻节点的最短距离，与 poll出来的节点增加的距离 比较
					distance[next] = Math.min(distance[next], distance[poll] + arr[1]);
					// 堆中新增节点，这里需要手动传入 next节点堆距离值。否则如果next在队列中，将永远无法上浮。
					queue.offer(new int[] { next, distance[next] });
				}
			}
			return distance;
		}

	}
}
