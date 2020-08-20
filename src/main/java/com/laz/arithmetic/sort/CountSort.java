package com.laz.arithmetic.sort;

import org.junit.Test;

import org.junit.Assert;

/*
 * 计数排序 https://www.cnblogs.com/kyoner/p/10604781.html
 */
public class CountSort {
	@Test
	public void test() {
		int[] arr = new int[] { 9, 3, 5, 4, 9, 1, 2, 7};
		arr = countSort(arr);
		Assert.assertArrayEquals(new int[] { 1,2,3,4,5,7,9,9 }, arr);
	}

	public int[] countSort(int[] array) {
		if (array == null || array.length == 0) {
			return array;
		}
		// 1.得到数列的最大值与最小值，并算出差值d
		int max = array[0];
		int min = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
				max = array[i];
			}
			if (array[i] < min) {
				min = array[i];
			}
		}
		int d = max - min;
		// 2.创建统计数组并计算统计对应元素个数
		int[] countArray = new int[d + 1];
		for (int i = 0; i < array.length; i++) {
			countArray[array[i] - min]++;
		}
		// 3.统计数组变形，后面的元素等于前面的元素之和
		int sum = 0;
		for (int i = 0; i < countArray.length; i++) {
			sum += countArray[i];
			countArray[i] = sum;
		}
		// 4.倒序遍历原始数组，从统计数组找到正确位置，输出到结果数组
		int[] sortedArray = new int[array.length];
		for (int i = array.length - 1; i >= 0; i--) {
			sortedArray[countArray[array[i] - min] - 1] = array[i];
			countArray[array[i] - min]--;
		}
		return sortedArray;
	}
}
