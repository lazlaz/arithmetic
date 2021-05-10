package com.laz.arithmetic.competition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.junit.Test;

//https://leetcode-cn.com/contest/biweekly-contest-51/
public class Competition28 {
	//1845. 座位预约管理系统
	@Test
	public void test2() {
		SeatManager seatManager = new SeatManager(5); // 初始化 SeatManager ，有 5 个座位。
		seatManager.reserve();    // 所有座位都可以预约，所以返回最小编号的座位，也就是 1 。
		seatManager.reserve();    // 可以预约的座位为 [2,3,4,5] ，返回最小编号的座位，也就是 2 。
		seatManager.unreserve(2); // 将座位 2 变为可以预约，现在可预约的座位为 [2,3,4,5] 。
		seatManager.reserve();    // 可以预约的座位为 [2,3,4,5] ，返回最小编号的座位，也就是 2 。
		seatManager.reserve();    // 可以预约的座位为 [3,4,5] ，返回最小编号的座位，也就是 3 。
		seatManager.reserve();    // 可以预约的座位为 [4,5] ，返回最小编号的座位，也就是 4 。
		seatManager.reserve();    // 唯一可以预约的是座位 5 ，所以返回 5 。
		seatManager.unreserve(5); // 将座位 5 变为可以预约，现在可预约的座位为 [5] 。
	}
	class SeatManager {
		PriorityQueue<Integer> order;
		Set<Integer> inOrder;
	    public SeatManager(int n) {
	    	order = new PriorityQueue<>();
	    	inOrder = new HashSet<>();
	    	//初始都可以预约
	    	for (int i=1;i<=n;i++) {
	    		order.add(i);
	    	}
	    }
	    
	    public int reserve() {
	    	int k = order.poll();
	    	inOrder.add(k);
	    	return k;
	    }
	    
	    public void unreserve(int seatNumber) {
	    	order.add(seatNumber); //有自动去重功能，有比较
	    }
	}
	
}
