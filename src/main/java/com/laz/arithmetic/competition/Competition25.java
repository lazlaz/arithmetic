package com.laz.arithmetic.competition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import com.laz.arithmetic.Utils;

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
			Map<Integer, Set<Integer>> map = new HashMap<>();
			for (int i = 0; i < logs.length; i++) {
				int[] log = logs[i];
				Set<Integer> ids = map.getOrDefault(log[0], new HashSet<Integer>());
				ids.add(log[1]);
				map.put(log[0], ids);
			}
			Set<Entry<Integer, Set<Integer>>> ids = map.entrySet();
			for (Entry<Integer, Set<Integer>> id : ids) {
				res[id.getValue().size() - 1]++;
			}
			return res;
		}
	}

	// 1818. 绝对差值和
	@Test
	public void test3() throws IOException {
//		Assert.assertEquals(3, new Solution3().minAbsoluteSumDiff(new int[] { 1, 7, 5 }, new int[] { 2, 3, 5 }));
//		Assert.assertEquals(20, new Solution3().minAbsoluteSumDiff(new int[] { 1,10,4,4,2,7 }, new int[] { 9,3,5,1,7,4}));
//		Assert.assertEquals(9, new Solution3().minAbsoluteSumDiff(new int[] { 1,28,21 }, new int[] { 9,21,20}));
		List<Integer> list1 = new ArrayList<Integer>();
		{
			List<String> lines = IOUtils.readLines(this.getClass().getResourceAsStream("25_3_case_input.txt"));
			for (String line:lines) {
				String[] str = line.split(",");
				for (String num : str) {
					list1.add(Integer.valueOf(num));
				}
			}
		}
		List<Integer> list2 = new ArrayList<Integer>();
		{
			List<String> lines = IOUtils.readLines(this.getClass().getResourceAsStream("25_3_case_input2.txt"));
			for (String line:lines) {
				String[] str = line.split(",");
				for (String num : str) {
					list2.add(Integer.valueOf(num));
				}
			}
		}
		Assert.assertEquals(999979264, new Solution3().minAbsoluteSumDiff(Utils.listToIntArr(list1),Utils.listToIntArr(list2)));
	}

	class Solution3 {
		int MOD = 1000_000_007;
		public int minAbsoluteSumDiff(int[] nums1, int[] nums2) {
			int n = nums1.length;
			int[] minArr = new int[n];
			long res = 0;
			TreeSet<Integer> set = new TreeSet<>();
			for (int i=0;i<n;i++) {
				minArr[i] = Math.abs(nums1[i]-nums2[i]);
				res=(res+minArr[i]);
				set.add(nums1[i]);
			}
			if (res==0) {
				return 0;
			}
			//计算每个min可优化的的值
			int diff = 0;
			for (int i=0;i<n;i++) {
				diff = Math.max(optimize(set,nums2[i],minArr[i]), diff);
			}
			
			return (int)((res-diff)%MOD);
		}

		private int optimize(TreeSet<Integer> set,int num2, int source) {
			int floorV = 0;
			try {
				floorV=set.floor(num2);
			} catch(NullPointerException e) {
				floorV = num2-source;
			}
			int ceilV  = 0;
			try {
				ceilV= set.ceiling(num2);
			} catch(NullPointerException e) {
				ceilV = num2-source;
			}
			int diff = Math.min(Math.abs(num2-floorV)%MOD, Math.abs(num2-ceilV)%MOD);
			return source>diff?(source-diff)%MOD:0;
		}
	}
}
