package com.laz.arithmetic.search;

import org.junit.Assert;
import org.junit.Test;


//插值查找
public class InsertionSearch {
	@Test
	public void test() {
		Assert.assertEquals(2, insertionSearch(new int[] { 24, 44, 55, 66, 333 }, 55));
	}

	public int insertionSearch(int[] arr, int target) {
		int low = 0;
		int high = arr.length - 1;

		while (low <= high) { // 必须为 '<=' 否则无法匹配到指定的key；
			int mid = low + (target - arr[low]) / (arr[high] - arr[low]) * (high - low);
			if (arr[mid] == target) {
				return mid;
			} else if (arr[mid] > target) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return -1;
	}
}
