package com.laz.arithmetic.sort;

import java.util.Random;

public class QuickSort {
	public static void main(String[] args) {
		int[] arr = new int[] {2,2,2,5,7,43,3};
		new QuickSort().quickSort(arr);
		for (int i : arr) {
			System.out.print(i+" ");
		}
	}

	public void quickSort(int[] arr) {
		if (arr==null || arr.length<2) {
			return;
		}
		quickSort(arr,0,arr.length-1);
	}

	private void quickSort(int[] arr, int l, int r) {
		if (l<r) {
			//把数组中随机的一个元素与最后一个元素交换，这样以最后一个元素作为基准值实际上就是以数组中随机的一个元素作为基准值
			swap(arr,new Random().nextInt(r-l+1)+l,r);
			int[] p = partition(arr,l,r);
			quickSort(arr,l,p[0]);
			quickSort(arr,p[1],r);
		}
		
	}
	public int[] partition(int[] arr,int l,int r) {
		int basic = arr[r];
		//l-1 r+1来处理等于的情况
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
		return new int[] {less,more};
	}
	public void swap(int[] arr, int i,int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
}
