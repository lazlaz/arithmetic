package com.laz.arithmetic.competition;

import java.util.Comparator;
import java.util.PriorityQueue;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-233/
public class Competiton23 {
	// 1801. 积压订单中的订单总数
	@Test
	public void test2() {
		Assert.assertEquals(6, new Solution2()
				.getNumberOfBacklogOrders(new int[][] { { 10, 5, 0 }, { 15, 2, 1 }, { 25, 1, 1 }, { 30, 4, 0 } }));

		Assert.assertEquals(999999984, new Solution2().getNumberOfBacklogOrders(
				new int[][] { { 7, 1000000000, 1 }, { 15, 3, 0 }, { 5, 999999995, 0 }, { 5, 1, 1 } }));

		Assert.assertEquals(102,
				new Solution2().getNumberOfBacklogOrders(
						new int[][] { { 23, 8, 0 }, { 28, 29, 1 }, { 11, 30, 1 }, { 30, 25, 0 }, { 26, 9, 0 },
								{ 3, 21, 0 }, { 28, 19, 1 }, { 19, 30, 0 }, { 20, 9, 1 }, { 17, 6, 0 } }));
	}

	class Solution2 {
		class Order {
			public int num;
			public int price;

			public Order(int num, int price) {
				this.num = num;
				this.price = price;
			}
		}

		public int getNumberOfBacklogOrders(int[][] orders) {
			final int MOD = 1000_000_007;
			// 采购堆 大根堆
			PriorityQueue<Order> buyQueue = new PriorityQueue<Order>(new Comparator<Order>() {
				@Override
				public int compare(Order o1, Order o2) {
					return o2.price - o1.price;
				}
			});
			// 销售堆 小根堆
			PriorityQueue<Order> sellQueue = new PriorityQueue<Order>(new Comparator<Order>() {
				@Override
				public int compare(Order o1, Order o2) {
					return o1.price - o2.price;
				}
			});

			for (int i = 0; i < orders.length; i++) {
				Order order = new Order(orders[i][1], orders[i][0]);
				// 采购订单
				if (orders[i][2] == 0) {
					// 查看销售堆中是否有低于采购价格的
					while (!sellQueue.isEmpty() && order.num > 0) {
						Order o = sellQueue.peek();
						if (o.price > order.price) {
							break;
						}
						int value = o.num - order.num;
						if (value < 0) {
							order.num = order.num - o.num;
							sellQueue.poll();
						} else if (value == 0) {
							order.num = 0;
							sellQueue.poll();
						} else if (value > 0) {
							order.num = 0;
							o.num = value;
						}
					}
					if (order.num > 0) {
						buyQueue.add(order);
					}
				}
				// 销售订单
				if (orders[i][2] == 1) {
					// 查看采购堆中是否有高于销售的价格
					while (!buyQueue.isEmpty() && order.num > 0) {
						Order o = buyQueue.peek();
						if (o.price < order.price) {
							break;
						}
						int value = o.num - order.num;
						if (value < 0) {
							order.num = order.num - o.num;
							buyQueue.poll();
						} else if (value == 0) {
							order.num = 0;
							buyQueue.poll();
						} else if (value > 0) {
							order.num = 0;
							o.num = value;
						}
					}
					if (order.num > 0) {
						sellQueue.add(order);
					}
				}
			}
			int res = 0;
			// 计算两个堆的数量
			for (Order order : sellQueue) {
				res = (res + order.num) % MOD;
			}
			for (Order order : buyQueue) {
				res = (res + order.num) % MOD;
			}
			return res;
		}
	}

	// 1802 有界数组中指定下标处的最大值
	@Test
	public void test3() {
//		Assert.assertEquals(2, new Solution3().maxValue(4, 2, 6));
//		Assert.assertEquals(3, new Solution3().maxValue(6, 1, 10));
//		Assert.assertEquals(1, new Solution3().maxValue(4, 0, 4));
		Assert.assertEquals(155230825, new Solution3().maxValue(6, 2, 931384943));
	}

	class Solution3 {
		public int maxValue(int n, int index, int maxSum) {
			// 二分法
			int l = 1, r = maxSum;
			int res = 0;
			while (l <= r) {
				int mid = l + (r - l) / 2; // 这种方式可以防止溢出
				// 假设值为mid时，最小的sum值
				long sum = minSum(mid, n, index);
				if (sum == maxSum) {
					return mid;
				}
				if (sum < maxSum) {
					l = mid + 1;
					res = mid;
				}
				if (sum > maxSum) {
					r = mid - 1;
				}
			}
			return res;
		}

		private long minSum(long value, long len, long index) {
			// index左边的最小和
			long leftSum = 0;
			if (index >= value) {
				leftSum = value * (value - 1) - (value * (value - 1)) / 2 + (index - (value - 1));
			} else {
				leftSum = index * value - (index * (index + 1)) / 2;
			}

			// index右边的最小和
			long rightSum = 0;
			long rightLen = (len - index) - 1;
			if (rightLen >= value) {
				rightSum = value * (value - 1) - (value * (value - 1)) / 2 + (rightLen - (value - 1));
			} else {
				rightSum = rightLen * value - (rightLen * (rightLen + 1)) / 2;
			}
			return (rightSum + leftSum + value);
		}
	}

	// 1803. 统计异或值在范围内的数对有多少
	@Test
	public void test4() {
		Assert.assertEquals(6, new Solution4().countPairs(new int[] { 1, 4, 2, 7 }, 2, 6));
		Assert.assertEquals(1305, new Solution4().countPairs(new int[] { 5202,7809,40,6748,7221,4423,2803,4528,2255,2204,6140,3802,4257,2735,6416,75,4432,6641,2595,722,4667,2897,4669,6946,2915,3729,4633,1350,8044,8014,2419,4829,6587,5745,6691,7687,406,817,6775,682,3753,2477,3534,2910,5753,1984,2571,1663,3993,3419,2130,5496,3868,2927,1044,6919,122,4452,5345,3041,703,247,2874,6709,1902,1237,2195,6860,6920,5103,2954,5896,145,5323,312,6771,1748,348,7798,6800,5300,7014,2773,763,1599,1869,5920,1763,5924,1896,1860,4452,6968,6325,1258,7466,2220,6066,7607,1428,6774,7990,4533,2971,5159,6184,2165,370,3033,6007,8062,4713,5465,1498,3346,7562,7044,2921,4195,2975,5901,4574,7874,3184,1292,4661,5634,2134,7578,4817,4619 }, 5793, 7046));
	}
	//https://leetcode-cn.com/problems/count-pairs-with-xor-in-a-range/solution/trieshu-jie-jue-yi-huo-wen-ti-by-jackie-o18hp/
	// https://leetcode-cn.com/problems/count-pairs-with-xor-in-a-range/solution/python3-01-trieshu-by-yim-6-7cb7/
	// https://leetcode-cn.com/problems/count-pairs-with-xor-in-a-range/solution/java01zi-dian-shu-by-simon123-t-eqv8/
	class Solution4 {
		class Trie {
			class Node {
				int cnt = 0;
				Node[] child = new Node[2];
			}

			private Node root = null;
			private int n;

			public Trie(int n) {
				this.n = n;
				root = new Node();
			}

			// 查询num与已存在字典树中的异或值小于low的数量
			public int query(int low, int num) {
				int ans = 0;
				Node node = root;
				for (int i = n; i >= 0; i--) {
					if (node == null) {
						return ans;
					}
					// 在二进制从高到低第i位，分别计算low和num的对应值，0或1
					int lk = ((low >> i) & 1);
					int nk = ((num >> i) & 1);
					// 当low在第i位为1时，那我们应统计在第i位异或值为0的所有可能情况,即两个需要异或的数在第i位的值同为nk，其数量=root.child[nk].cnt;
					if (lk == 1) {
						//nk^存在的值相同为0
						if (node.child[nk] != null) {
							ans += node.child[nk].cnt;
						}
						// 统计完nk分支的所有可能后，再计算1^nk分支的所有可能
						node = node.child[1 ^ nk];
					} else {
						// 当lk在第i位为0时，交给下一个为1的位置来计算
						// 当lk在第i位为0时，只有一种情况下可以统计，那就是后面位置全为0，但调用该方法目的是统计<low的异或情况，所以排除这种可能，所以交给下一个为1的位置吧
						node = node.child[nk];
					}

				}
				return ans;
			}

			public void insert(int num) {
				Node node = root;
				// 将数字转换为二进制插入，如2 转换为0000000000000010
				for (int i = n; i >= 0; i--) {
					int k = ((num >> i) & 1);
					if (node.child[k] == null) {
						node.child[k] = new Node();
					}
					node = node.child[k];
					// 孩子数量
					node.cnt++;
				}

			}
		}

		public int countPairs(int[] nums, int low, int high) {
			int max = 15;
			Trie trie = new Trie(max);
			int res = 0;
			for (int num : nums) {
				res += (trie.query(high + 1, num) - trie.query(low, num));
				trie.insert(num);
			}
			return res;
		}
	}
}
