package com.laz.arithmetic.datastructure.heap;

import java.util.Arrays;

import org.junit.Test;

public class HeapSortTest {
	public void toString(int[] array) {
		for (int i : array) {
			System.out.print(i + " ");
		}
	}

	@Test
	public void test() {
		HeapSort hs = new HeapSort();
		int[] array = { 87, 45, 78, 32, 17, 65, 53, 9, 122 };
		System.out.print("构建大根堆：");
		hs.buildMaxHeap(array, array.length);
		System.out.println(Arrays.toString(array));
		System.out.print("\n" + "大根堆排序：");
		hs.heapSort(array);
		System.out.println(Arrays.toString(array));
	}
}
