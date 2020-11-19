package com.laz.arithmetic.sort;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

//快速排序
public class QuickSort {
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
		//把数组中随机的一个元素与第一个元素交换，这样以第一个元素作为基准值实际上就是以数组中随机的一个元素作为基准值
		SortUtils.swap(arr,new Random().nextInt(end-start+1)+start,start);
		int index = partition(arr,start,end);
		quickSort(arr,start,index-1);
		quickSort(arr,index+1,end);
	}

	private int partition(int[] arr, int start, int end) {
		int i=start,j=end+1;
		int ref = arr[start]; //切分参考值
		while (i<j) {
			//右移动i，找到大于等于ref的值
			while (arr[++i]<ref && i<end) {
			}
			//左移动j，找到小于等于ref的值
			while (arr[--j]>ref && j>start) {
			}
			if (i>=j) {
				break;
			}
			SortUtils.swap(arr, i, j);
		}
		SortUtils.swap(arr, start, j);
		return j;
	}

	
}
