package com.laz.arithmetic.datastructure;

import java.util.Random;

import org.junit.Test;

public class AvlTreeTest {
	private AVLBalanceTree<Integer> avlTree = new AVLBalanceTree<Integer>();

	@Test
	public void testInsert() {
		avlTree.insert(100);
		avlTree.insert(120);
		avlTree.insert(300);
		avlTree.insert(500);
		avlTree.insert(111);
		avlTree.insert(92);
		avlTree.insert(77);
		avlTree.insert(125);
		System.out.println(avlTree.contains(120));
		avlTree.remove(120);
		avlTree.remove(125); // 需要单旋转
		System.out.println(avlTree.contains(120));
		avlTree.insert(78); // 需要双旋转
		System.out.println("Insert Success !");
	}

	@Test
	public void testRotate() {
		avlTree.insert(100);
		avlTree.insert(90);
		avlTree.insert(92);
		avlTree.insert(78);
		avlTree.insert(76);
		System.out.println("Insert Success !");
	}

	/**
	 * 通过较大数据进行测试，暂时还没有发现问题
	 */
	@Test
	public void testAll() {
		avlTree.makeEmpty();
		Random random = new Random();
		for (int i = 1; i <= 1000000; i++) {
			avlTree.insert(random.nextInt(1000000));
		}
		for (int i = 2000000; i >= 1000000; i--) {
			avlTree.insert(i);
		}
		/*
		 * for(int i=700000;i>=400000;i--){ avlTree.insert(i); } for(int
		 * i=100000;i<=200000;i++){ avlTree.insert(i); } for(int
		 * i=400000;i<=500000;i++){ avlTree.insert(random.nextInt(600000)); }
		 */
		for (int i = 200000; i < 1400000; i++) {
			int target = random.nextInt(1500000);
			if (avlTree.contains(target)) {
				avlTree.remove(target);
			}
		}
		System.out.println("Insert Success !");
	}

}
