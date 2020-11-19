package com.laz.arithmetic.search;

import org.junit.Assert;
import org.junit.Test;

import com.laz.arithmetic.sort.SortUtils;

//基于切分的快速选择算法 ,从数组中，找出第k大的元素
public class QuickSortSearch {
	@Test
	public void test() {
		{
			Assert.assertEquals(3, select(new int[] { 2, 2, 2, 5, 7, 43, 3 }, 4));
		}
		{
			Assert.assertEquals(4, select(new int[] { 6,5,4,3,2 }, 3));
		}
	}

	public int select(int[] arr, int k) {
		k = k-1;
		int l = 0, h = arr.length - 1;
		while (h > l) {
			int index = partition(arr, l, h);
			if (index == k) {
				return arr[index];
			} else if (index > k) {
				h = index-1;
			} else {
				l = index+1;
			}
		}
		return arr[k];
	}

	private int partition(int[] arr, int start, int end) {
		int i = start, j = end + 1;
		int ref = arr[start]; // 切分参考值
		while (i < j) {
			// 右移动i，找到大于等于ref的值
			while (arr[++i] < ref && i < end) {
			}
			// 左移动j，找到小于等于ref的值
			while (arr[--j] > ref && j > start) {
			}
			if (i >= j) {
				break;
			}
			SortUtils.swap(arr, i, j);
		}
		SortUtils.swap(arr, start, j);
		return j;
	}
}
