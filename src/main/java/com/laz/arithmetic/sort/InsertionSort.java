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
		for (int i=1;i<arr.length;i++) {
			for (int j=i;j>0;j--) {
				if (arr[j]<arr[j-1]) {
					SortUtils.swap(arr, j, j-1);
				}
			}
		}
	}
}
