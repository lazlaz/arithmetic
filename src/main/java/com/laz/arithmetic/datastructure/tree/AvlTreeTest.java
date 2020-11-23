package com.laz.arithmetic.datastructure.tree;

import java.util.Random;

import org.junit.Test;

public class AvlTreeTest {
	private AVLBalanceTree<Integer> avlTree = new AVLBalanceTree<Integer>();

	@Test
	public void test() {
		int arr[] = { 3, 2, 1, 4, 5, 6, 7, 16, 15, 14, 13, 12, 11, 10, 8, 9 };
		int i;
		AVLBalanceTree<Integer> tree = new AVLBalanceTree<Integer>();

		System.out.printf("== 依次添加: ");
		for (i = 0; i < arr.length; i++) {
			System.out.printf("%d ", arr[i]);
			tree.insert(arr[i]);
		}

		System.out.printf("\n== 前序遍历: ");
		tree.preOrder();

		System.out.printf("\n== 中序遍历: ");
		tree.inOrder();

		System.out.printf("\n== 后序遍历: ");
		tree.postOrder();
		System.out.printf("\n");

		System.out.printf("== 高度: %d\n", tree.height());
		System.out.printf("== 最小值: %d\n", tree.minimum());
		System.out.printf("== 最大值: %d\n", tree.maximum());
		System.out.printf("== 树的详细信息: \n");
		tree.print();

		i = 8;
		System.out.printf("\n== 删除根节点: %d", i);
		tree.remove(i);

		System.out.printf("\n== 高度: %d", tree.height());
		System.out.printf("\n== 中序遍历: ");
		tree.inOrder();
		System.out.printf("\n== 树的详细信息: \n");
		tree.print();

		// 销毁二叉树
		tree.destroy();
	}

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
		System.out.println(avlTree.search(120));
		avlTree.remove(111);
		avlTree.remove(300);
		System.out.println(avlTree.search(120));
		avlTree.insert(78);
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

	@Test
	public void testAll() {
		Random random = new Random();
		for (int i = 1; i <= 1000000; i++) {
			avlTree.insert(random.nextInt(1000000));
		}
		for (int i = 2000000; i >= 1000000; i--) {
			avlTree.insert(i);
		}
		for (int i = 200000; i < 1400000; i++) {
			int target = random.nextInt(1500000);
			if (avlTree.search(target) != null) {
				avlTree.remove(target);
			}
		}
		System.out.println("Insert Success !");
	}

}
