package com.laz.arithmetic.sort;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.google.common.base.Joiner;

public class QuickSort {
	public static void main(String[] args) {
		int[] arr = new int[] {3,23,45,5,2,2,5,2,5};
		new QuickSort().quickSort(arr);
		for (int i : arr) {
			System.out.print(i+" ");
		}
		
	}

	public void quickSort(int[] arr) {
		if (arr==null || arr.length<2) {
			return;
		}
		quicksort(arr,0,arr.length-1);
	}

	private void quicksort(int[] arr, int l, int r) {
		if (l<r) {
			//把数组中随机的一个元素与最后一个元素交换，这样以最后一个元素作为基准值实际上就是以数组中随机的一个元素作为基准值
			swap(arr,new Random().nextInt(r-l+1)+l,r);
			int[] p = partition(arr,l,r);
			quicksort(arr,l,p[0]-1);
			quicksort(arr,p[0]+1,r);
		}
		
	}
	public int[] partition(int[] arr,int l,int r) {
		int basic = arr[r];
		int less = l-1;
		int more = r+1;
		while (l<more) {
			if (arr[l]<basic) {
				swap(arr,++less,l++);
			} else if (arr[l]>basic) {
				swap(arr,--more,l);
			} else {
				l++;
			}
		}
		return new int[] {less+1,more-1};
	}
	public void swap(int[] arr, int i,int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}
