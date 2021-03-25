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
		Assert.assertEquals(6,
				new Solution2().getNumberOfBacklogOrders(new int[][] { { 10, 5, 0 }, { 15, 2, 1 }, { 25, 1, 1 }, { 30, 4, 0 } }));
		
		Assert.assertEquals(999999984,
				new Solution2().getNumberOfBacklogOrders(new int[][] { { 7,1000000000,1 }, { 15,3,0 }, { 5,999999995,0 },{5,1,1}}));

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
	
	//1802 有界数组中指定下标处的最大值
	@Test
	public void test3() {
//		Assert.assertEquals(2, new Solution3().maxValue(4, 2, 6));
//		Assert.assertEquals(3, new Solution3().maxValue(6, 1, 10));
//		Assert.assertEquals(1, new Solution3().maxValue(4, 0, 4));
		Assert.assertEquals(155230825, new Solution3().maxValue(6, 2, 931384943));
	}
	
	class Solution3 {
		public int maxValue(int n,int index,int maxSum) {
			//二分法
			int l = 1,r=maxSum;
			int res=0;
			while (l<=r) {
				int mid = l+(r-l)/2; //这种方式可以防止溢出
				//假设值为mid时，最小的sum值
				long sum = minSum(mid,n,index);
				if (sum==maxSum) {
					return mid;
				}
				if (sum<maxSum) {
					l = mid+1;
					res=mid;
				}
				if (sum>maxSum) {
					r = mid-1;
				}
			}
			return res;
		}

		private long minSum(long value, long len, long index) {
			//index左边的最小和
			long leftSum=0;
			if (index>=value) {
				leftSum=value*(value-1)-(value*(value-1))/2+(index-(value-1));
			} else {
				leftSum=index*value-(index*(index+1))/2;
			}
			
			//index右边的最小和
			long rightSum=0;
			long rightLen = (len-index)-1;
			if (rightLen>=value) {
				rightSum=value*(value-1)-(value*(value-1))/2+(rightLen-(value-1));
			} else {
				rightSum=rightLen*value-(rightLen*(rightLen+1))/2;
			}
			return (rightSum+leftSum+value);
		}
	}
}
