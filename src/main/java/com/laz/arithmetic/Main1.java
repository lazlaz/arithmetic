package com.laz.arithmetic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Exchanger;
import java.util.concurrent.SynchronousQueue;

import org.junit.Test;


public class Main1 {
	@Test
	//线程交换
	public void test() {
		final Exchanger<List<Integer>> exchanger = new Exchanger<List<Integer>>();
		new Thread() {
			@Override
			public void run() {
				List<Integer> l = new ArrayList<Integer>(2);
				l.add(1);
				l.add(2);
				try {
					l = exchanger.exchange(l);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Thread1"+l);
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				List<Integer> l = new ArrayList<Integer>(2);
				l.add(4);
				l.add(5);
				try {
					l = exchanger.exchange(l);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Thread2"+l);
			}
		}.start();
	}
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Integer> queue = new
		        SynchronousQueue<>();
		queue.put(1);
		queue.put(2);
		queue.put(3);
		        System. out .print(queue.offer(1) + " ");
		        System. out .print(queue.offer(2) + " ");
		        System. out .print(queue.offer(3) + " ");
		        System. out .print(queue.take() + " ");
		        System. out .println(queue.size());

		long startTime = System.currentTimeMillis();
		final List<Integer> l = new LinkedList<Integer>();
		final Random random = new Random();
		int count = 10000;
		for (int i = 0; i < count; i++) {
			Thread thread = new Thread() {
				public void run() {
					l.add(random.nextInt());
				}
			};
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(System.currentTimeMillis() - startTime);
		System.out.println(l.size());
	}
}
