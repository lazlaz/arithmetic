package com.laz.arithmetic.sort;

import org.junit.Assert;
import org.junit.Test;

//堆排序
/**
 * 堆排序的基本思想是：将待排序序列构造成一个大顶堆，此时，整个序列的最大值就是堆顶的根节点。将其与末尾元素进行交换，此时末尾就为最大值。
 * 然后将剩余n-1个元素重新构造成一个堆，这样会得到n个元素的次小值。如此反复执行，便能得到一个有序序列了
 * https://www.cnblogs.com/chengxiao/p/6129630.html
 */
public class HeapSort {
	@Test
	public void test() {
		int[] arr = new int[] { 3, 23, 45, 5, 2, 2, 5, 2, 5 };
		heapSort(arr);
		Assert.assertArrayEquals(new int[] { 2, 2, 2, 3, 5, 5, 5, 23, 45 }, arr);
	}

	private void heapSort(int[] arr) {
		buildMaxHeap(arr);
		// 调整堆结构+交换堆顶元素与末尾元素
		for (int j = arr.length - 1; j > 0; j--) {
			SortUtils.swap(arr, 0, j); // 将堆顶元素与末尾元素进行交换
			adjustHeap(arr, 0, j); // 重新对堆进行调整
		}
	}

	private void buildMaxHeap(int[] arr) {
		for (int i = arr.length / 2 - 1; i >= 0; i--) {
			// 从第一个非叶子结点从下至上，从右至左调整结构
			adjustHeap(arr, i, arr.length);
		}
	}

	private void adjustHeap(int[] arr, int i, int length) {
		int temp = arr[i];
		for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {// 从i结点的左子结点开始，也就是2i+1处开始
			if (k + 1 < length && arr[k] < arr[k + 1]) {// 如果左子结点小于右子结点，k指向右子结点
				k++;
			}
			if (arr[k] > temp) {// 如果子节点大于父节点，将子节点值赋给父节点（不用进行交换）
				arr[i] = arr[k];// 如果子父节点进行了交换，可能会影响子节点，所有子节点又要进行一次比较，看当前值是否比其子节点的值还小
				i = k;
			} else {
				break;
			}
		}
		arr[i] = temp;
	}

	private void sink(int[] nums, int k, int N) {
		while (2 * k <= N) {
			int j = 2 * k;
			if (j < N && nums[j] < nums[j + 1])
				j++;
			if (nums[k] >= nums[j])
				break;
			SortUtils.swap(nums, k, j);
			k = j;
		}
	}
}
