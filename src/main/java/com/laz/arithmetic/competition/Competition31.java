package com.laz.arithmetic.competition;

import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-243/
public class Competition31 {
	//1881. 插入后的最大值
	@Test
	public void test2() {
		//Assert.assertEquals("999", new Solution2().maxValue("99", 9));
		Assert.assertEquals("-123", new Solution2().maxValue("-13", 2));
	}
	
	class Solution2 {
	    public String maxValue(String n, int x) {
	    	//判断符号
	    	char[] cs = n.toCharArray();
	    	StringBuilder sb = new StringBuilder();
	    	if (cs[0] == '-') {
	    		sb.append('-');
	    		//负数，尽可能保证除符号以外的数更小
	    		int i;
	    		for ( i=1;i<cs.length;i++) {
	    			//找到一个比x大的数字前进行插入
	    			if ((cs[i]-'0')>x) {
	    				break;
	    			}
	    			sb.append(cs[i]);
	    		}
    			sb.append(x);
    			if (i<cs.length) {
    				sb.append(n.substring(i, cs.length));
    			}
	    	} else {
	    		//正数，尽可能保证大
	    		int i;
	    		for ( i=0;i<cs.length;i++) {
	    			//找到一个比x小的数字前进行插入
	    			if ((cs[i]-'0')<x) {
	    				break;
	    			}
	    			sb.append(cs[i]);
	    		}
    			sb.append(x);
    			if (i<cs.length) {
    				sb.append(n.substring(i, cs.length));
    			}
	    	}
	    	return sb.toString();
	    }
	}
	
	//1882. 使用服务器处理任务
	@Test
	public void test3() {
//		Assert.assertArrayEquals(new int[] {
//				2,2,0,2,1,2
//		}, new Solution3().assignTasks(new int[] {
//				3,3,2
//		}, new int[] {
//				1,2,3,2,1,2
//		}));
//		
//		Assert.assertArrayEquals(new int[] {
//				1,4,1,4,1,3,2
//		}, new Solution3().assignTasks(new int[] {
//				5,1,4,3,2
//		}, new int[] {
//				2,1,2,4,5,2,1
//		}));
//		
//
//		Assert.assertArrayEquals(new int[] {
//				26,50,47,11,56,31,18,55,32,9,4,2,23,53,43,0,44,30,6,51,29,51,15,17,22,34,38,33,42,3,25,10,49,51,7,58,16,21,19,31,19,12,41,35,45,52,13,59,47,36,1,28,48,39,24,8,46,20,5,54,27,37,14,57,40,59,8,45,4,51,47,7,58,4,31,23,54,7,9,56,2,46,56,1,17,42,11,30,12,44,14,32,7,10,23,1,29,27,6,10,33,24,19,10,35,30,35,10,17,49,50,36,29,1,48,44,7,11,24,57,42,30,10,55,3,20,38,15,7,46,32,21,40,16,59,30,53,17,18,22,51,11,53,36,57,26,5,36,56,55,31,34,57,7,52,37,31,10,0,51,41,2,32,25,0,7,49,47,13,14,24,57,28,4,45,43,39,38,8,2,44,45,29,25,25,12,54,5,44,30,27,23,26,7,33,58,41,25,52,40,58,9,52,40
//		}, new Solution3().assignTasks(new int[] {
//				338,890,301,532,284,930,426,616,919,267,571,140,716,859,980,469,628,490,195,664,925,652,503,301,917,563,82,947,910,451,366,190,253,516,503,721,889,964,506,914,986,718,520,328,341,765,922,139,911,578,86,435,824,321,942,215,147,985,619,865
//		}, new int[] {
//				773,537,46,317,233,34,712,625,336,221,145,227,194,693,981,861,317,308,400,2,391,12,626,265,710,792,620,416,267,611,875,361,494,128,133,157,638,632,2,158,428,284,847,431,94,782,888,44,117,489,222,932,494,948,405,44,185,587,738,164,356,783,276,547,605,609,930,847,39,579,768,59,976,790,612,196,865,149,975,28,653,417,539,131,220,325,252,160,761,226,629,317,185,42,713,142,130,695,944,40,700,122,992,33,30,136,773,124,203,384,910,214,536,767,859,478,96,172,398,146,713,80,235,176,876,983,363,646,166,928,232,699,504,612,918,406,42,931,647,795,139,933,746,51,63,359,303,752,799,836,50,854,161,87,346,507,468,651,32,717,279,139,851,178,934,233,876,797,701,505,878,731,468,884,87,921,782,788,803,994,67,905,309,2,85,200,368,672,995,128,734,157,157,814,327,31,556,394,47,53,755,721,159,843
//		}));
	}
	class Solution3_1 {
		class Server {
			int index;
			int weight;
			long time; //什么时间恢复空闲状态，默认0 考虑task过多超出int范围
			
			@Override
			public boolean equals(Object obj) {
				Server s2 = (Server) obj;
				if (this.index == s2.index) {
					return true;
				}
				return false;
			}
			
			@Override
			public int hashCode() {
				return Integer.valueOf(this.index).hashCode();
			}
		}
	    public int[] assignTasks(int[] servers, int[] tasks) {
	    	//小跟堆,存储有效的服务器
	    	PriorityQueue<Server> queue = new PriorityQueue<>((s1,s2)->{
	    		if (s1.weight>s2.weight) {
	    			return 1;
	    		}
	    		if (s1.weight<s2.weight) {
	    			return -1;
	    		}
	    		return s1.index-s2.index;
	    	});
	    	for (int i=0;i<servers.length;i++) {
	    		Server s = new Server();
	    		s.index = i;
	    		s.weight = servers[i];
	    		queue.add(s);
	    	}
	    	//存储正在运行的服务器，根据time排序
	    	PriorityQueue<Server> runServers = new PriorityQueue<>((s1,s2)-> {
	    		if (s1.time==s2.time) {
	    			return 1;
	    		}
	    		return (int) (s1.time-s2.time);
	    	});
	    	int len = tasks.length;
	    	int[] ans = new int[len];
	    	long time = 0;
	    	int index = 0;
	    	while (true) {
	    		if (index>=len) {
	    			break;
	    		}
	    		//空闲的服务器放入queue中
	    		while (!runServers.isEmpty()) {
	    			Server s = runServers.peek();
	    			if (s.time<=time) {
	    				queue.add(runServers.poll());
	    			} else {
	    				break;
	    			}
	    		}
	    		//从queue取空闲服务器运行 如果同一时刻存在多台空闲服务器，可以同时将多项任务分别分配给它们。
	    		while (!queue.isEmpty() && index<=time && index<len) {
	    			Server s = queue.poll();
	    			ans[index] = s.index;
	    			s.time = time+tasks[index]; //下次服务器空闲的时间
	    			index++;
	    			runServers.add(s);
	    		}
	    		//如果没有空闲的服务器，time直接加到下一次空闲的时间,减少循环，节约时间
	    		if (queue.isEmpty()) {
	    			time = runServers.peek().time; 
	    		} else {
	    			time++;
	    		}
	    	}
	    	return ans;
	    }
	}
	class Solution3 {
		class Server {
			int index;
			int weight;
			int time; //什么时间恢复空闲状态，默认0
			
			@Override
			public boolean equals(Object obj) {
				Server s2 = (Server) obj;
				if (this.index == s2.index) {
					return true;
				}
				return false;
			}
			
			@Override
			public int hashCode() {
				return Integer.valueOf(this.index).hashCode();
			}
		}
	    public int[] assignTasks(int[] servers, int[] tasks) {
	    	//小跟堆,存储有效的服务器
	    	PriorityQueue<Server> queue = new PriorityQueue<>((s1,s2)->{
	    		if (s1.weight>s2.weight) {
	    			return 1;
	    		}
	    		if (s1.weight<s2.weight) {
	    			return -1;
	    		}
	    		return s1.index-s2.index;
	    	});
	    	for (int i=0;i<servers.length;i++) {
	    		Server s = new Server();
	    		s.index = i;
	    		s.weight = servers[i];
	    		queue.add(s);
	    	}
	    	//存储正在运行的服务器，根据time排序
	    	PriorityQueue<Server> runServers = new PriorityQueue<>((s1,s2)-> {
	    		if (s1.time==s2.time) {
	    			return 1;
	    		}
	    		return s1.time-s2.time;
	    	});
	    	int len = tasks.length;
	    	int[] ans = new int[len];
	    	int time = 0;
	    	int index = 0;
	    	long start = System.currentTimeMillis();
	    	while (true) {
	    		if (index>=len) {
	    			break;
	    		}
	    		if(time < index) {
	    			time = index;
	    		}
	    		//空闲的服务器放入queue中,直到queue不为空
	    		for(;;) {
	    			while (!runServers.isEmpty() && runServers.peek().time <= time) {
	    				queue.add(runServers.poll());
	    			}
    				if(!queue.isEmpty())
    					break;
    				time = runServers.peek().time; 
	    		}
	    		//从queue取空闲服务器运行 如果同一时刻存在多台空闲服务器，可以同时将多项任务分别分配给它们。
	    		while (!queue.isEmpty() && index<=time && index<len) {
	    			Server s = queue.poll();
	    			ans[index] = s.index;
	    			s.time = time+tasks[index]; //下次服务器空闲的时间
	    			index++;
	    			runServers.add(s);
	    		}
	    	}
	    	long end = System.currentTimeMillis();
	    	System.out.println("时间"+(end-start));
	    	return ans;
	    }
	}
	
	//1883. 准时抵达会议现场的最小跳过休息次数
	@Test
	public void test4() {
		Assert.assertEquals(1, new Solution4().minSkips(new int[] {
				1,3,2
		}, 4, 2));
	}
	//https://leetcode-cn.com/problems/minimum-skips-to-arrive-at-meeting-on-time/solution/minimum-skips-to-arrive-at-meeting-on-ti-dp7v/
	class Solution4 {
		static final double EPS = 1e-7;//控制取整是的精度差
	    public int minSkips(int[] dist, int speed, int hoursBefore) {
	    	int n = dist.length;
	    	double[][] dp = new double[n+1][n+1];//表示经过了dist[0] 到dist[i−1] 的 i 段道路，并且跳过了 j 次的最短用时
	    	for (int i=0;i<n+1;i++) {
	    		Arrays.fill(dp[i], Integer.MAX_VALUE);
	    	}
	    	dp[0][0] = 0;
	    	for (int i=1;i<=n;i++) {
	    		for (int j=0;j<=i;j++) {
	    			//j==i时，最后一步无法跳过
	    			if (j != i) {
	                    dp[i][j] = Math.min(dp[i][j], Math.ceil(dp[i - 1][j] + (double)dist[i - 1] / speed - EPS));
	                }
	                if (j != 0) {
	                	dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1] + (double)dist[i - 1] / speed);
	                }

	    		}
	    	}
	    	//找到dp[n][j]满足条件最小的j
	    	for (int j = 0; j <= n; ++j) {
	            if (dp[n][j] < (hoursBefore + EPS)) {
	                return j;
	            }
	        }
	        return -1;

	    }
	}
}
