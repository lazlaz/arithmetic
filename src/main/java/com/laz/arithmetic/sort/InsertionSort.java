package com.laz.arithmetic.sort;

import org.junit.Assert;
import org.junit.Test;

//插入排序
public class InsertionSort {
	@Test
	public void test() {
		int[] r = new int[] { 3, 2, 1 };
		insertionSort(r);
		Assert.assertArrayEquals(new int[] { 1, 2, 3 }, r);
	}

	public void insertionSort(int[] arr) {
		int temp;

		for (int i = 1; i < arr.length; i++) {

			// 待排元素小于有序序列的最后一个元素时，向前插入
			if (arr[i] < arr[i - 1]) {
				temp = arr[i];
				for (int j = i; j >= 0; j--) {
					if (j > 0 && arr[j - 1] > temp) {
						arr[j] = arr[j - 1];
					} else {
						arr[j] = temp;
						break;
					}
				}
			}
		}
	}
}
