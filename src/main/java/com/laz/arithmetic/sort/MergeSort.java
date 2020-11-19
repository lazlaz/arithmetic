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
		Assert.assertArrayEquals(new int[] { 2, 2, 2, 3, 5, 5, 5, 23, 45 }, arr);
	}

	public void mergeSort(int[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}
		int len = arr.length;
		int[] result = new int[len];
		divide(arr, result, 0, len - 1);
	}

	private void divide(int[] arr, int[] result, int start, int end) {
		if (start >= end) {
			return;
		}
		// 先拆分
		int len = end - start, mid = start + (len >> 1);
		divide(arr, result, start, mid);
		divide(arr, result, mid + 1, end);
		// 合并
		merge(arr, result, start, mid, end);
	}

	private void merge(int[] arr, int[] result, int start, int mid, int end) {
		int k = start;
		int start1 = start, end1 = mid;
		int start2 = mid + 1, end2 = end;
		// 合并两个归并结果，保证有序
		while (start1 <= end1 && start2 <= end2) {
			result[k++] = arr[start1] < arr[start2] ? arr[start1++] : arr[start2++];
		}
		// 合并剩余的
		while (start1 <= end1)
			result[k++] = arr[start1++];
		// 合并剩余的
		while (start2 <= end2)
			result[k++] = arr[start2++];
		//值复制给以前的数组
		for (int i=start;i<=end;i++) {
			arr[i] = result[i];
		}
	}
}
