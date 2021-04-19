package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

//https://leetcode-cn.com/contest/weekly-contest-236/
public class Competition26 {
	// 1823. 找出游戏的获胜者
	@Test
	public void test2() {
		Assert.assertEquals(3, new Solution2().findTheWinner(5, 2));
		Assert.assertEquals(1, new Solution2().findTheWinner(6, 5));
	}

	class Solution2 {
		public int findTheWinner(int n, int k) {
			List<Integer> list = new ArrayList<>();
			for (int i = 1; i <= n; i++) {
				list.add(i);
			}
//			int index = 0;
//			while (list.size()>1) {
//				int len = list.size();
//				int limit = len-index;
//				if (k>limit) {
//					//超过了长度，先到末尾，然后从头开始，在用剩余的长度%(len+1)
//					int go = k-limit;
//					index = (go-1)%len;
//				} else {
//					index = index+k-1;
//				}
//				list.remove(index);
//			}
			int idx = 0;
			while (n > 1) {
				idx = (idx + k - 1) % n;
				list.remove(idx);
				n--;
			}
			return list.get(0);
		}
	}

	// 1824. 最少侧跳次数
	@Test
	public void test3() {
		Assert.assertEquals(2, new Solution3().minSideJumps(new int[] { 0, 1, 2, 3, 0 }));
	}

	// https://leetcode-cn.com/problems/minimum-sideway-jumps/solution/dp-dong-tai-gui-hua-by-hu-li-hu-wai-dah5/
	class Solution3 {
		public int minSideJumps(int[] obstacles) {
			//表示当前位置 j 表示停留在第 j 个道的最小次数。
			int[] dp = new int[3]; 
			dp[1] = 0;
			dp[0] = dp[2] = 1;
			int n = obstacles.length;
			for (int i=1;i<n;i++) {
				int obs = obstacles[i];//当前点的障碍物
				//存储上一个点的状态
				int pre0 = dp[0];
				int pre1 = dp[1];
				int pre2 = dp[2];
				//最差就是调5*100000次
				Arrays.fill(dp, 500000); //有障碍则说明不可达，取最大值
				//如果改跑到没有障碍，则次数和前一次一致
				if (obs!=1) 
					dp[0] = pre0;
				if (obs!=2) 
					dp[1] = pre1;
				if (obs!=3) 
					dp[2] = pre2;
				
				//是否有更小的次数到达该点,前提是不能有障碍
				if (obs!=1) 
					dp[0] = Math.min(dp[0], Math.min(dp[1], dp[2])+1);
				if (obs!=2) 
					dp[1] = Math.min(dp[1], Math.min(dp[0], dp[2])+1);
				if (obs!=3) 
					dp[2] = Math.min(dp[2], Math.min(dp[0], dp[1])+1);
			}
			return Arrays.stream(dp).min().orElse(0);
		}
	}
	
	//1825. 求出 MK 平均值
	@Test
	public void test4() {
		MKAverage obj = new MKAverage(3, 1); 
		obj.addElement(17612);        
		obj.addElement(74607);       
		obj.calculateMKAverage(); 
		obj.addElement(8272);       
		obj.addElement(33433);      
		obj.calculateMKAverage();
		obj.addElement(15456);       
		obj.addElement(64938);        
		obj.calculateMKAverage(); 
		obj.addElement(99741);        
	}
	//https://leetcode-cn.com/problems/finding-mk-average/solution/c-san-ge-multiset-jian-dan-mo-ni-by-newh-y4q9/
	class MKAverage {
		long sum;//middle的和
	    Queue<Integer> lower; //最小的k个数 
	    Queue<Integer> middleMin; //中间的数
	    Queue<Integer> middleMax; //中间的数
	    Queue<Integer> upper; //最大的k个数 
	    int m;
	    int k;
	    Queue<Integer> nums;
	    public MKAverage(int m, int k) {
	        this.m = m;
	        this.k = k;
	        sum =0;
	        lower = new PriorityQueue<>((u,v)->v-u); //大顶堆
	        middleMin = new PriorityQueue<>(); //小顶堆
	        middleMax = new PriorityQueue<>((u,v)->v-u); //大顶堆
	        upper = new PriorityQueue<>(); //小顶堆
	        nums = new LinkedList<>();
	    }
	    public void addElement(int num) {
	        nums.offer(num);
	        //判断值需要插入到lower upper middle那个堆中
	        if(!lower.isEmpty() && lower.peek()>=num){
	            lower.offer(num);
	        }else if(!upper.isEmpty() && upper.peek()<=num){
	            upper.offer(num);
	        }else{
	            middleMin.offer(num);
	            middleMax.offer(num);
	            sum += num;
	        }
	        //如果lower中的数大于K，移到middlee
	        while(lower.size()>k){
	            int top = lower.poll();
	            middleMin.offer(top);
	            middleMax.offer(top);
	            sum += top;
	        }
	        //如果upper中的数大于K，移到middlee
	        while(upper.size()>k){
	            int down = upper.poll();
	            middleMin.offer(down);
	            middleMax.offer(down);
	            sum += down;
	        }
	        //数量超过m，从对应堆中删除值
	        if(nums.size()>m){
	            int temp = nums.poll();
	            if(lower.peek()>=temp){
	                lower.remove(temp);
	            }else if(upper.peek()<=temp){
	                upper.remove(temp);
	            }else{
	                middleMax.remove(temp);
	                middleMin.remove(temp);
	                //如果是从middle进行删除的，和减少
	                sum -= temp;
	            }
	        }
	        //如果删除后的lower与upper数量少于K
	        if(nums.size()>=m){
	            while(lower.size()<k){
	                int temp = middleMin.poll();
	                middleMax.remove(temp);
	                sum -= temp;
	                lower.offer(temp);
	            }
	            while(upper.size()<k){
	                int temp = middleMax.poll();
	                middleMin.remove(temp);
	                sum -= temp;
	                upper.offer(temp);
	            }
	        }

	    }
	    
	    public int calculateMKAverage() {
	        if(nums.size()<m){
	            return -1;
	        }
	        return (int)(sum/(m-2*k));
	    }
	}
	
	class MKAverage2 {
		TreeMap<Integer, Integer> left = new TreeMap<>();
		TreeMap<Integer, Integer> 	right = new TreeMap<>();
	    TreeMap<Integer, Integer> mid = new TreeMap<>();
	    int leftCount = 0, rightCount = 0;
	    int sum = 0;
	    int m, k;
	    Queue<Integer> queue;
	    public MKAverage2(int m, int k) {
	        this.m = m;
	        this.k = k;
	        queue = new LinkedList<>();
	    }
	    
	    public void addElement(int num) {
	        queue.offer(num);
	        if (queue.size() > m) {
	            int target = queue.poll();
	            if (left.containsKey(target)) {
	                leftCount--;
	                remove(target, left);
	            } else if (right.containsKey(target)) {
	                remove(target, right);
	                rightCount--;
	            } else {
	                sum -= target;
	                remove(target, mid);
	            }
	        }
	        sum += num;
	        add(num, mid);
	        while (leftCount < k && !mid.isEmpty()) { // 先加入mid 中之后 优先保证left size 为k 
	        	// 比如刚开始不到m个数的时候 之后是queue.poll 把这个数从left move了 那么必然补充满到k
	            int x = remove(mid.firstKey(), mid);
	            sum -= x;
	            leftCount++;
	            add(x, left);
	        }
	        // 之后 如果left size是k后  我们优先交互 保证 left是最小的k个 剩下的在mid
	        while (!mid.isEmpty() && mid.firstKey() < left.lastKey()) {
	            int temp1 = remove(mid.firstKey(), mid);
	            int temp2 = remove(left.lastKey(), left);
	            sum += temp2 - temp1;
	            add(temp1, left);
	            add(temp2, mid);
	        } 
	        while (rightCount < k && !mid.isEmpty()) {  // 除了最小的 剩下的填满 right 使其size = k
	        // 比如刚开始不到m个数的时候 之后是queue.poll 把这个数从right move了 那么必然补充满到k
	            int x = remove(mid.lastKey(), mid);
	            sum -= x;
	            rightCount++;
	            add(x, right);
	        }
	        // 之后 我们可以交互保证 最大的k个一定在right 剩下的中间的在mid
	        while (!mid.isEmpty() && mid.lastKey() > right.firstKey()) {
	            int temp1 = remove(mid.lastKey(), mid);
	            int temp2 = remove(right.firstKey(), right);
	            sum += temp2 - temp1;
	            add(temp1, right);
	            add(temp2, mid);
	        }
	    }
	    
	    public int calculateMKAverage() {
	        return queue.size() < m? -1 : sum / (m - 2 * k);
	    }

	    public int remove(int num, TreeMap<Integer, Integer> map) {
	        int x = map.get(num);
	        if (x == 1) {
	            map.remove(num);
	        } else {
	            map.put(num, x - 1);
	        }
	        return num;
	    }

	    public void add(int num, TreeMap<Integer, Integer> map) {
	        map.put(num, map.getOrDefault(num, 0) + 1);
	    }
	}
}
