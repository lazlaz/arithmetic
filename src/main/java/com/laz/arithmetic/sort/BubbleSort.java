package com.laz.arithmetic.sort;

public class BubbleSort {
	public static void main(String[] args) {
		int[] arr = new int[] {3,23,45,5,2,2,5,2,5};
		new BubbleSort().sort(arr);
		for (int i : arr) {
			System.out.print(i+" ");
		}
	}

	public void sort(int[] arr) {
		for (int i=0;i<arr.length;i++) {
			for (int j=0;j<arr.length-i-1;j++) {
				if (arr[j]>arr[j+1]) {
					int temp = arr[j];
					arr[j]  = arr[j+1];
					arr[j+1] = temp;
				}
			}
		}
	}
}
