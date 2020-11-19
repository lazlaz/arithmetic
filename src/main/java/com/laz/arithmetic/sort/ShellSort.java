package com.laz.arithmetic.sort;

import org.junit.Assert;
import org.junit.Test;

//希尔排序
public class ShellSort {
	@Test
	public void test() {
		int[] r = new int[] { 7, 6, 5, 4, 3, 2, 1 };
		insertionSort(r);
		Assert.assertArrayEquals(new int[] { 1, 2, 3, 4, 5, 6, 7 }, r);
	}

	public void insertionSort(int[] arr) {
		int N = arr.length;
		int h = 1;

		while (h < N / 3) {
			h = 3 * h + 1; // 1, 4, 13, 40, ...
		}
		while (h >= 1) {
			for (int i = h; i < N; i++) {
				for (int j = i; j >= h; j -= h) {
					if (arr[j] < arr[j - h]) {
						SortUtils.swap(arr, j, j - h);
					}
				}
			}
			h = h / 3;
		}
	}

}
