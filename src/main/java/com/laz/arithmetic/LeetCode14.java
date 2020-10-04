package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class LeetCode14 {
	// 鸡蛋掉落
	@Test
	public void test1() {
		Assert.assertEquals(2, new Solution1().superEggDrop(1, 2));
	}

	class Solution1 {
		public int superEggDrop(int K, int N) {
			return dp(K, N);
		}

		Map<Integer, Integer> memo = new HashMap();

		public int dp(int K, int N) {
			// dp(K, N) 为在状态 (K, N)下最少需要的步数 K 为鸡蛋数，NN 为楼层数
			if (!memo.containsKey(N * 100 + K)) {
				int ans;
				if (N == 0) {
					ans = 0;
				} else if (K == 1) {
					ans = N;
				} else {
					int lo = 1, hi = N;
					while (lo + 1 < hi) {
						int x = (lo + hi) / 2;
						int t1 = dp(K - 1, x - 1);
						int t2 = dp(K, N - x);

						if (t1 < t2) {
							lo = x;
						} else if (t1 > t2) {
							hi = x;
						} else {
							lo = hi = x;
						}
					}

					ans = 1 + Math.min(Math.max(dp(K - 1, lo - 1), dp(K, N - lo)),
							Math.max(dp(K - 1, hi - 1), dp(K, N - hi)));
				}

				memo.put(N * 100 + K, ans);
			}
			return memo.get(N * 100 + K);
		}
	}

	// 设计停车系统
	@Test
	public void test2() {
		ParkingSystem parkingSystem = new ParkingSystem(1, 1, 0);
		System.out.println(parkingSystem.addCar(1));// 返回 true ，因为有 1 个空的大车位

		System.out.println(parkingSystem.addCar(2));// 返回 true ，因为有 1 个空的中车位
		System.out.println(parkingSystem.addCar(3));// 返回 false ，因为没有空的小车位
		System.out.println(parkingSystem.addCar(1));// 返回 false ，因为没有空的大车位，唯一一个大车位已经被占据了
	}

	class ParkingSystem {
		int big;
		int medium;
		int small;

		public ParkingSystem(int big, int medium, int small) {
			this.big = big;
			this.medium = medium;
			this.small = small;
		}

		public boolean addCar(int carType) {
			switch (carType) {
			case 1:
				if (this.big > 0) {
					this.big--;
					return true;
				}
				break;
			case 2:
				if (this.medium > 0) {
					this.medium--;
					return true;
				}
				break;
			case 3:
				if (this.small > 0) {
					this.small--;
					return true;
				}
				break;
			default:
				break;
			}
			return false;
		}
	}

	// 警告一小时内使用相同员工卡大于等于三次的人
	@Test
	public void test3() {
		{
			String[] keyName = new String[] { "daniel", "daniel", "daniel", "luis", "luis", "luis", "luis" };
			String[] keyTime = new String[] { "10:00", "10:40", "11:00", "09:00", "11:00", "13:00", "15:00" };
			List<String> ret = alertNames(keyName, keyTime);
			System.out.println(Joiner.on(",").join(ret));
		}
		{
			String[] keyName = new String[] { "alice", "alice", "alice", "bob", "bob", "bob", "bob" };
			String[] keyTime = new String[] { "12:01", "12:00", "18:00", "21:00", "21:20", "21:30", "23:00" };
			List<String> ret = alertNames(keyName, keyTime);
			System.out.println(Joiner.on(",").join(ret));
		}
		{
			String[] keyName = new String[] { "leslie", "leslie", "leslie", "clare", "clare", "clare", "clare" };
			String[] keyTime = new String[] { "13:00", "13:20", "14:00", "18:00", "18:51", "19:30", "19:49" };
			List<String> ret = alertNames(keyName, keyTime);
			System.out.println(Joiner.on(",").join(ret));
		}
		{
			String[] keyName = new String[] { "john", "john", "john" };
			String[] keyTime = new String[] { "23:58", "23:59", "00:01" };
			List<String> ret = alertNames(keyName, keyTime);
			System.out.println(Joiner.on(",").join(ret));
		}
		{
			String[] keyName = new String[] { "aa", "aa", "aa", "aa", "aa", "aa", "aa", "aa", "ba", "ba", "ba", "ba",
					"ba", "ba", "ba", "ba", "ca", "ca", "ca", "ca", "ca", "ca", "ca", "ca", "da", "da", "da", "da",
					"da", "da", "da", "da", "ea", "ea", "ea", "ea", "ea", "ea", "ea", "ea", "fa", "fa", "fa", "fa",
					"fa", "fa", "fa", "fa", "ga", "ga", "ga", "ga", "ga", "ga", "ga", "ga", "ha", "ha", "ha", "ha",
					"ha", "ha", "ha", "ha", "ia", "ia", "ia", "ia", "ia", "ia", "ia", "ia", "ja", "ja", "ja", "ja",
					"ja", "ja", "ja", "ja", "ka", "ka", "ka", "ka", "ka", "ka", "ka", "ka", "la", "la", "la", "la",
					"la", "la", "la", "la", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "ma", "na", "na", "na", "na",
					"na", "na", "na", "na", "oa", "oa", "oa", "oa", "oa", "oa", "oa", "oa", "pa", "pa", "pa", "pa",
					"pa", "pa", "pa", "pa", "qa", "qa", "qa", "qa", "qa", "qa", "qa", "qa", "ra", "ra", "ra", "ra",
					"ra", "ra", "ra", "ra", "sa", "sa", "sa", "sa", "sa", "sa", "sa", "sa", "ta", "ta", "ta", "ta",
					"ta", "ta", "ta", "ta", "ua", "ua", "ua", "ua", "ua", "ua", "ua", "ua", "va", "va", "va", "va",
					"va", "va", "va", "va", "wa", "wa", "wa", "wa", "wa", "wa", "wa", "wa", "xa", "xa", "xa", "xa",
					"xa", "xa", "xa", "xa", "ya", "ya", "ya", "ya", "ya", "ya", "ya", "ya", "za", "za", "za", "za",
					"za", "za", "za", "za", "ab", "ab", "ab", "ab", "ab", "ab", "ab", "ab", "bb", "bb", "bb", "bb",
					"bb", "bb", "bb", "bb", "cb", "cb", "cb", "cb", "cb", "cb", "cb", "cb", "db", "db", "db", "db",
					"db", "db", "db", "db" };
			String[] keyTime = new String[] { "03:32", "10:12", "15:52", "08:28", "13:20", "21:40", "02:54", "19:02",
					"21:14", "08:57", "14:36", "02:05", "13:24", "04:27", "21:52", "13:27", "05:58", "07:50", "11:08",
					"23:44", "14:15", "04:31", "15:30", "21:50", "20:50", "07:27", "21:23", "01:09", "14:50", "05:31",
					"05:48", "05:09", "11:06", "12:30", "01:32", "09:31", "01:29", "11:13", "08:11", "01:55", "04:46",
					"03:17", "15:53", "13:22", "14:17", "08:09", "20:52", "12:28", "08:59", "13:21", "07:50", "17:53",
					"14:33", "06:40", "01:26", "02:05", "10:08", "11:40", "15:42", "12:55", "04:12", "07:23", "00:58",
					"19:47", "00:05", "19:36", "05:31", "09:31", "09:52", "23:12", "11:18", "22:00", "11:08", "09:21",
					"11:26", "01:12", "12:48", "10:01", "01:23", "16:31", "14:20", "15:53", "11:41", "14:02", "01:33",
					"23:16", "05:53", "19:05", "13:51", "06:35", "21:36", "03:32", "06:55", "23:42", "15:27", "12:48",
					"12:46", "13:31", "20:15", "11:05", "16:05", "06:19", "04:18", "17:44", "06:53", "20:34", "22:25",
					"12:51", "23:01", "16:32", "21:09", "08:03", "18:39", "07:29", "10:21", "10:23", "05:51", "19:59",
					"15:14", "06:36", "14:15", "10:21", "15:02", "03:08", "17:36", "00:52", "17:17", "20:16", "15:39",
					"01:44", "18:52", "03:10", "00:30", "03:02", "00:48", "21:55", "03:12", "22:36", "20:50", "15:54",
					"18:41", "12:11", "11:03", "01:42", "10:19", "04:32", "01:02", "14:44", "00:44", "18:12", "13:16",
					"05:33", "05:42", "01:45", "20:45", "20:27", "22:18", "04:37", "16:56", "22:07", "01:05", "22:30",
					"15:13", "17:07", "04:47", "18:41", "03:59", "06:36", "06:48", "20:51", "11:19", "15:06", "06:51",
					"17:56", "20:18", "13:28", "00:19", "17:24", "14:45", "14:32", "17:05", "14:54", "02:18", "06:27",
					"10:10", "12:46", "22:51", "15:20", "10:33", "12:59", "12:49", "08:29", "07:29", "22:48", "13:49",
					"17:40", "11:19", "22:19", "11:50", "05:33", "12:18", "06:33", "10:56", "08:12", "22:54", "02:33",
					"10:41", "04:57", "00:37", "01:41", "23:04", "22:31", "00:12", "07:37", "05:16", "22:32", "16:22",
					"01:40", "20:32", "23:33", "04:53", "03:53", "04:25", "06:42", "20:26", "16:54", "10:22", "11:47",
					"13:14", "11:19", "01:39", "02:32", "19:40", "02:57", "03:32", "12:32", "07:15", "00:00", "12:31",
					"23:59" };
			List<String> ret = alertNames(keyName, keyTime);
			System.out.println(Joiner.on(",").join(ret));
		}
	}

	public List<String> alertNames(String[] keyName, String[] keyTime) {
		List<String> ret = new ArrayList<String>();
		Map<String, Set<String>> maps = new HashMap<String, Set<String>>();
		for (int i = 0; i < keyName.length; i++) {
			Set<String> set = maps.getOrDefault(keyName[i], new TreeSet());
			set.add(keyTime[i]);
			maps.put(keyName[i], set);
		}

		for (String name : maps.keySet()) {
			Set<String> set = maps.get(name);
			// 判断时间是否为三场且一小时类
			if (set.size() >= 3) {
				String[] sets = set.toArray(new String[] {});
				for (int i = 0; i < sets.length - 2; i++) {
					int lastTime = -1;
					int timeDiff = 60;
					for (int j = i; j < i + 3; j++) {
						String[] times = sets[j].split(":");
						int hours = Integer.parseInt(times[0]);
						int time = Integer.parseInt(times[1]);
						int datetime = hours * 60 + time;
						if (lastTime >= 0) {
							timeDiff = timeDiff - (datetime - lastTime);
						}
						lastTime = datetime;
					}
					if (timeDiff >= 0) {
						ret.add(name);
						break;
					}
				}
			}
		}
		Collections.sort(ret, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		return ret;
	}

	// 给定行和列的和求可行矩阵
	@Test
	public void test4() {
		int[] rowSum = new int[] { 14, 9 };
		int[] colSum = new int[] { 6, 9, 8 };
		int[][] matrix = restoreMatrix(rowSum, colSum);
		for (int[] is : matrix) {
			for (int c : is) {
				System.out.print(c + " ");
			}
			System.out.println();
		}
	}

	public int[][] restoreMatrix(int[] rowSum, int[] colSum) {
		int m = rowSum.length, n = colSum.length;
        int[][] res = new int[m][n];
        int col = 0;
        for (int i = 0; i < rowSum.length; ++i) {
            int num = rowSum[i];
            while (num > 0) {
                res[i][col] = Math.min(num, colSum[col]);
                num -= res[i][col];
                colSum[col] -= res[i][col];
                if (colSum[col] == 0) ++col;
            }
        }
        return res;
	}

	
}
