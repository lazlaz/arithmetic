package com.laz.arithmetic.competition;

import java.util.*;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

public class Competition2 {
	// 1606 找到处理最多请求的服务器
	@Test
	public void test1() {
		{
			List<Integer> ret = busiestServers(3, new int[] { 1, 2, 3, 4, 5 }, new int[] { 5, 2, 3, 3, 3 });
			Assert.assertEquals("1", Joiner.on(",").join(ret));
		}
		{
			List<Integer> ret = busiestServers(3, new int[] { 1, 2, 3, 4 }, new int[] { 1, 2, 1, 2 });
			Assert.assertEquals("0", Joiner.on(",").join(ret));
		}
		{
			List<Integer> ret = busiestServers(3, new int[] { 1, 2, 3 }, new int[] { 10, 11, 12 });
			Assert.assertEquals("0,1,2", Joiner.on(",").join(ret));
		}
		List<Integer> ret = new Solution_1().busiestServers(3, new int[] { 1, 2, 3 }, new int[] { 10, 11, 12 });
		Assert.assertEquals("0,1,2", Joiner.on(",").join(ret));
	}
   	//https://leetcode-cn.com/problems/find-servers-that-handled-most-number-of-requests/solution/zhao-dao-chu-li-zui-duo-qing-qiu-de-fu-w-e0a5/
	class Solution_1 {
		public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
			TreeSet<Integer> available = new TreeSet<Integer>();
			for (int i = 0; i < k; i++) {
				available.add(i);
			}
			//优先队列（大小根堆）
			PriorityQueue<int[]> busy = new PriorityQueue<int[]>((a, b) -> a[0] - b[0]);
			int[] requests = new int[k];
			for (int i = 0; i < arrival.length; i++) {
				//如果队列里面的时间小于等于arrival[i],资源空闲，加入空闲集合中
				while (!busy.isEmpty() && busy.peek()[0] <= arrival[i]) {
					available.add(busy.poll()[1]);
				}
				if (available.isEmpty()) {
					continue;
				}
				Integer p = available.ceiling(i % k);
				if (p == null) {
					p = available.first();
				}
				requests[p]++;
				//arrival[i] + load[i] 完成任务的时间
				busy.offer(new int[]{arrival[i] + load[i], p});
				available.remove(p);
			}
			int maxRequest = Arrays.stream(requests).max().getAsInt();
			List<Integer> ret = new ArrayList<Integer>();
			for (int i = 0; i < k; i++) {
				if (requests[i] == maxRequest) {
					ret.add(i);
				}
			}
			return ret;
		}

	}
	public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
		List<Integer> res = new ArrayList<>();
		int[] map = new int[k];
		TreeSet<Integer> good = new TreeSet<>(); // 空闲服务器列表
		TreeMap<Integer, List<Integer>> mm = new TreeMap<>(); // 上一个任务的完成时间 -> 机器组..

		for (int i = 0; i < k; i++) {
			good.add(i);
		}

		for (int i = 0; i < arrival.length; i++) {
			int index = i % k; // 应该分配给的服务器

			int now = arrival[i]; // 当前时间
			int finishTime = now + load[i]; // 结束时间

			// 根据当前时间得到应该完成工作的机器组
			final SortedMap<Integer, List<Integer>> subMap = mm.subMap(0, now + 1);
			final Iterator<Integer> iterator = subMap.keySet().iterator();
			while (iterator.hasNext()) {
				final Integer key = iterator.next();
				good.addAll(subMap.get(key));
				iterator.remove();
			}

			// 如果当前没有空闲的机器.. 那就扔掉任务
			if (good.size() == 0)
				continue;
			Integer next = good.ceiling(index);

			// 如果上面找不到空闲的机器.. 那就取第一个空闲的机器
			if (next == null)
				next = good.first();

			// 开始工作
			good.remove(next);
			if (!mm.containsKey(finishTime))
				mm.put(finishTime, new ArrayList<>());
			mm.get(finishTime).add(next);

			// 计数
			map[next]++;
		}

		// 找最大
		int max = 0;
		for (int num : map) {
			max = Math.max(max, num);
		}
		for (int i = 0; i < k; i++) {
			if (map[i] == max)
				res.add(i);
		}
		return res;
	}

	// 超时
	public List<Integer> busiestServers2(int k, int[] arrival, int[] load) {
		List<Integer> ret = new ArrayList<Integer>();
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		for (int i = 0; i < arrival.length; i++) {
			int index = i % k;
			int count = 1;
			while (!isFree(index, i, map, arrival[i], load) && count <= k) {
				index++;
				if (index > (k - 1)) {
					index = 0;
				}
				count++;
			}
			if (count <= k) {
				List<Integer> r = map.getOrDefault(index, new ArrayList<Integer>());
				r.add(i);
				map.put(index, r);
				load[i] = load[i] + arrival[i];
			}
		}
		int max = 0;
		for (List<Integer> v : map.values()) {
			if (v.size() > max) {
				max = v.size();
			}
		}
		for (Integer key : map.keySet()) {
			List<Integer> l = map.get(key);
			if (l.size() == max) {
				ret.add(key);
			}
		}
		return ret;
	}

	private boolean isFree(int index, int k, Map<Integer, List<Integer>> map, int arrivalV, int[] load) {
		List<Integer> r = map.getOrDefault(index, new ArrayList<Integer>());
		// 判断是否空闲
		if (r.size() == 0) {
			return true;
		}
		for (Integer i : r) {
			int v = load[i] - arrivalV;
			if (v > 0) {
				return false;
			}
		}
		return true;
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
				if (colSum[col] == 0)
					++col;
			}
		}
		return res;
	}

}
