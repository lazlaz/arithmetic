package com.laz.arithmetic.sort;

import org.junit.Assert;
import org.junit.Test;

//选择排序 https://www.runoob.com/w3cnote/selection-sort.html

public class SelectSort {
	@Test
	public void test() {
		{
			int[] arr = new int[] {1,11,111,2,22,222};
			selectSort(arr);
			Assert.assertArrayEquals(new int[] {1,2,11,22,111,222}, arr);
		}	
	}
	public void selectSort(int[] arr)  {
		int n = arr.length;
		for (int i=0;i<n;i++) {
			int minIndex = i;
			for (int j=i+1;j<n;j++) {
				if (arr[j]<arr[minIndex]) {
					minIndex = j;
				}
			}
			SortUtils.swap(arr, minIndex, i);
		}
	}
}
