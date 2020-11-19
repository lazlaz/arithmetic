package com.laz.arithmetic.sort;

import org.junit.Assert;
import org.junit.Test;

//冒泡排序
public class BubbleSort {
	@Test
	public void test() {
		int[] arr = new int[] {3,23,45,5,2,2,5,2,5};
		new BubbleSort().sort(arr);
		Assert.assertArrayEquals(new int[] {2,2,2,3,5,5,5,23,45}, arr);
	}

	public void sort(int[] arr) {
		int N = arr.length;
		boolean isSort = false;
		for (int i=N-1;i>=0&&!isSort;i--) {
			isSort=true; //如果数组循环不存在交换，则已经有序，退出排序
			for (int j=0;j<i;j++) {
				if (arr[j]>arr[j+1]) {
					isSort=false;
					SortUtils.swap(arr, j, j+1);
				}
			}
		}
	}
}
