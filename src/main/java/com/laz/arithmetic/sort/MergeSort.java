package com.laz.arithmetic.sort;

import org.junit.Assert;
import org.junit.Test;

/**
 * 归并排序
 *
 */
public class MergeSort {
	@Test
	public void test() {
		int[] arr = new int[] { 3, 23, 45, 5, 2, 2, 5, 2, 5 };
		new MergeSort().mergeSort(arr);
		Assert.assertArrayEquals(new int[] { 2,2,2,3,5,5,5,23,45 }, arr);
	}

	public void mergeSort(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}
		int len = arr.length;
		int[] result = new int[len];
		mergeSortRecursive(arr, result, 0, len - 1);
	}

	private void mergeSortRecursive(int[] arr, int[] result, int start, int end) {
		if (start >= end)
			return;
		int len = end - start, mid = (len >> 1) + start;
		int start1 = start, end1 = mid;
		int start2 = mid + 1, end2 = end;
		mergeSortRecursive(arr, result, start1, end1);
		mergeSortRecursive(arr, result, start2, end2);
		int k = start;
		//合并两个归并结果，保证有序
		while (start1 <= end1 && start2 <= end2)
			result[k++] = arr[start1] < arr[start2] ? arr[start1++] : arr[start2++];
		
		//合并剩余的
		while (start1 <= end1)
			result[k++] = arr[start1++];
		
		//合并剩余的
		while (start2 <= end2)
			result[k++] = arr[start2++];
		for (k = start; k <= end; k++)
			arr[k] = result[k];
	}
}
