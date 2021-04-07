package com.laz.arithmetic.competition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-235/
public class Competition25 {
	// 1817. 查找用户活跃分钟数
	@Test
	public void test2() {
		Assert.assertArrayEquals(new int[] { 0, 2, 0, 0, 0 }, new Solution2()
				.findingUsersActiveMinutes(new int[][] { { 0, 5 }, { 1, 2 }, { 0, 2 }, { 0, 5 }, { 1, 3 } }, 5));
	}

	class Solution2 {
		public int[] findingUsersActiveMinutes(int[][] logs, int k) {
			int[] res = new int[k];
			Arrays.fill(res, 0);
			Map<Integer,Set<Integer>> map = new HashMap<>();
			for (int i=0;i<logs.length;i++) {
				int[] log = logs[i];
				Set<Integer> ids = map.getOrDefault(log[0], new HashSet<Integer>());
				ids.add(log[1]);
				map.put(log[0], ids);
			}
			Set<Entry<Integer, Set<Integer>>>  ids = map.entrySet();
			for (Entry<Integer, Set<Integer>> id : ids) {
				res[id.getValue().size()-1]++;
			}
			return res;
		}
	}
}
