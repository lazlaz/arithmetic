package com.laz.arithmetic;

import java.util.PriorityQueue;
import java.util.TreeSet;

import org.junit.Test;

public class StackAndTreeTest {
	//堆与TreeSet插入移除效率对比
	/**
	 * 堆耗时2299ms
		红黑树耗时537ms
	 */
	@Test
	public void test() {
		int n = 10000000;
		PriorityQueue<Integer> queue = new PriorityQueue<>();
		TreeSet<Integer> set = new TreeSet<>();
		for (int i=0;i<n;i++) {
			queue.add(i);
			set.add(i);
		}
		{
			long start = System.nanoTime();
			for (int i=0;i<n;i++) {
				set.pollFirst();
			}
			long end = System.nanoTime();
			System.out.println("红黑树耗时"+(end-start)/1000000+"ms");
		}
		{
			long start = System.nanoTime();
			for (int i=0;i<n;i++) {
				queue.poll();
			}
			long end = System.nanoTime();
			System.out.println("堆耗时"+(end-start)/1000000+"ms");
		}
		
	}
}
