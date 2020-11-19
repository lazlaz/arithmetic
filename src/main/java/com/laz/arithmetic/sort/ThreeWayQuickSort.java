package com.laz.arithmetic.sort;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

//三向切分快速排序
public class ThreeWayQuickSort {
	@Test
	public void test(){
		{
			int[] arr = new int[] {2,2,2,5,7,43,3};
			quickSort(arr);
			Assert.assertArrayEquals(new int[] {2,2,2,3,5,7,43}, arr);
		}	
		{
			int[] arr = new int[] {5,4,3,2,1};
			quickSort(arr);
			Assert.assertArrayEquals(new int[] {1,2,3,4,5}, arr);
		}	
	}

	public void quickSort(int[] arr) {
		if (arr==null || arr.length<2) {
			return;
		}
		quickSort(arr,0,arr.length-1);
	}

	private void quickSort(int[] arr, int start, int end) {
		if (start>=end) {
			return;
		}
		//对于有大量重复元素的数组，可以将数组切分为三部分，分别对应小于、等于和大于切分元素。
		int lt=start,i=start+1,gt=end;
		int ref = arr[start];
		while (i<=gt) {
			if (arr[i]<ref) {
				SortUtils.swap(arr, lt++, i++);
			} else if (arr[i]>ref) {
				SortUtils.swap(arr, i, gt--);
			} else {
				i++;
			}
		}
		quickSort(arr,start,lt-1);
		quickSort(arr,gt+1,end);
	}

	

}
