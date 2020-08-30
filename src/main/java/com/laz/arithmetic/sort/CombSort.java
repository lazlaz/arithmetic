package com.laz.arithmetic.sort;

import org.junit.Assert;
import org.junit.Test;

/*
 * 梳排序
 * https://blog.csdn.net/benjamin_whx/article/details/42461761
 */
public class CombSort {
	@Test
	public void test() {
		int[] number = { 11, 95, 45, 15, 78, 84, 51, 24, 12 };
		combSort(number);
		Assert.assertArrayEquals(new int[] { 11, 12, 15, 24, 45, 51, 78, 84, 95 }, number);
	}

	/**
	 ** 梳排序
	 * 
	 * @param array
	 */
	public  void combSort(int[] array) {
		int gap = array.length;
		boolean swapped = true;
		while (gap > 1 || swapped) {
			if (gap > 1) {
				gap = (int) (gap / 1.3);
			}
			int i = 0;
			swapped = false;
			while (i + gap < array.length) {
				if (array[i] > array[i + gap]) {
					swap(array, i, i + gap);
					swapped = true;
				}
				i++;
			}
		}
	}

	/**
	 * 按从小到大的顺序交换数组
	 * 
	 * @param a 传入的数组
	 * @param b 传入的要交换的数b
	 * @param c 传入的要交换的数c
	 */
	public  void swap(int[] a, int b, int c) {
		if (b == c)
			return;
		int temp = a[b];
		a[b] = a[c];
		a[c] = temp;
	}

}
